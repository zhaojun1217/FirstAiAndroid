#!/usr/bin/env bash
# 发布 test 版本：Git 同步 → Debug 打包 → OSS 上传 → QQ 邮件
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SKILL_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
PROJECT_ROOT="$(cd "$SKILL_DIR/../../.." && pwd)"
CONFIG_FILE="$SKILL_DIR/config.env"

if [[ ! -f "$CONFIG_FILE" ]]; then
  echo "错误: 未找到配置文件 $CONFIG_FILE"
  echo "请执行: cp $SKILL_DIR/config.example.env $CONFIG_FILE 并填写配置"
  exit 1
fi

# shellcheck source=/dev/null
source "$CONFIG_FILE"

cd "$PROJECT_ROOT"

echo "==> [1/4] 同步 Git 分支: $GIT_BRANCH"
git checkout "$GIT_BRANCH"
git fetch origin "$GIT_BRANCH"
git merge "origin/$GIT_BRANCH" --no-edit

echo "==> [2/4] Debug 打包"
./gradlew assembleDebug --no-daemon

BUILD_TIME="$(date +%Y%m%d_%H%M%S)"
APK_SOURCE="$PROJECT_ROOT/app/build/outputs/apk/debug/app-debug.apk"
APK_FILENAME="app-debug-${BUILD_TIME}.apk"
APK_PATH="$PROJECT_ROOT/app/build/outputs/apk/debug/$APK_FILENAME"

if [[ ! -f "$APK_SOURCE" ]]; then
  echo "错误: 未找到 APK: $APK_SOURCE"
  exit 1
fi

cp "$APK_SOURCE" "$APK_PATH"
echo "APK 已生成: $APK_PATH"

echo "==> [3/4] 上传到 OSS"
OSS_OBJECT="${OSS_OBJECT_PREFIX}${APK_FILENAME}"
ossutil cp "$APK_PATH" "oss://${OSS_BUCKET}/${OSS_OBJECT}" \
  -e "$OSS_ENDPOINT" \
  -i "$OSS_ACCESS_KEY_ID" \
  -k "$OSS_ACCESS_KEY_SECRET"

DOWNLOAD_URL="${OSS_DOWNLOAD_BASE_URL}/${OSS_OBJECT}"
echo "下载地址: $DOWNLOAD_URL"

echo "==> [4/4] 发送 QQ 邮件"
COMMIT_HASH="$(git rev-parse --short HEAD)"
python3 "$SCRIPT_DIR/send-mail.py" \
  --apk-name "$APK_FILENAME" \
  --download-url "$DOWNLOAD_URL" \
  --branch "$GIT_BRANCH" \
  --commit "$COMMIT_HASH"

echo ""
echo "========== 发布完成 =========="
echo "分支:     $GIT_BRANCH"
echo "Commit:   $COMMIT_HASH"
echo "APK:      $APK_FILENAME"
echo "下载链接: $DOWNLOAD_URL"
echo "收件人:   $MAIL_TO"
