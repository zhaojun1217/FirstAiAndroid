---
name: publish-test
description: >-
  发布 Android test 版本：拉取并合并远程 test 分支、本地 debug 打包（APK 带时间戳）、
  上传阿里云 OSS、通过 QQ 邮箱发送下载链接给同事。
  在用户说「发布test版本」「发测试包」「publish test」时使用。
---

# 发布 Test 版本

端到端自动化：Git 同步 → Debug 打包 → OSS 上传 → QQ 邮件通知。

## 前置检查

执行前确认：

1. 已复制 `config.example.env` 为 `config.env` 并填写真实配置（**勿提交** `config.env`）
2. 已安装：`git`、`java`、`ossutil`（或 Python + `oss2`）
3. 远程存在目标分支（默认 `test`）；本地分支名与 `GIT_BRANCH` 一致
4. QQ 邮箱已开启 SMTP 并使用**授权码**（非 QQ 密码）

## 工作流程

按顺序执行，任一步失败则停止并报告错误：

```
任务进度：
- [ ] 1. 同步 Git（fetch + merge）
- [ ] 2. Debug 打包（带时间戳 APK）
- [ ] 3. 上传 OSS
- [ ] 4. 发送 QQ 邮件
- [ ] 5. 向用户汇报下载链接与构建信息
```

### 步骤 1：同步 Git

在项目根目录执行 `scripts/publish-test.sh` 的 Git 阶段，或手动：

```bash
source .cursor/skills/publish-test/config.env
git checkout "$GIT_BRANCH"
git fetch origin "$GIT_BRANCH"
git merge "origin/$GIT_BRANCH" --no-edit
```

**冲突处理**：若 merge 冲突，停止流程，列出冲突文件，请用户解决后重新执行。

**无远程分支**：若 `origin/test` 不存在，告知用户先在 GitHub 创建并推送 test 分支。

### 步骤 2：Debug 打包

```bash
./gradlew assembleDebug --no-daemon
```

APK 默认输出：`app/build/outputs/apk/debug/app-debug.apk`

打包后由脚本复制为带时间戳的文件名，例如 `app-debug-20260622_153045.apk`。

也可在 `app/build.gradle.kts` 中启用 Gradle 重命名（见 [reference.md](reference.md)）。

### 步骤 3：上传 OSS

使用 `ossutil`：

```bash
ossutil cp "$APK_PATH" "oss://${OSS_BUCKET}/${OSS_OBJECT_PREFIX}${APK_FILENAME}" \
  -e "$OSS_ENDPOINT" -i "$OSS_ACCESS_KEY_ID" -k "$OSS_ACCESS_KEY_SECRET"
```

下载地址格式：`https://${OSS_BUCKET}.${OSS_ENDPOINT}/${OSS_OBJECT_PREFIX}${APK_FILENAME}`

若 Bucket 为公共读或使用自定义域名，按 `config.env` 中 `OSS_DOWNLOAD_BASE_URL` 拼接。

### 步骤 4：发送 QQ 邮件

```bash
python3 .cursor/skills/publish-test/scripts/send-mail.py \
  --apk-name "$APK_FILENAME" \
  --download-url "$DOWNLOAD_URL" \
  --branch "$GIT_BRANCH" \
  --commit "$(git rev-parse --short HEAD)"
```

收件人列表来自 `config.env` 的 `MAIL_TO`（逗号分隔）。

### 步骤 5：汇报

向用户输出：

- APK 文件名与本地路径
- OSS 下载链接
- 当前 commit、分支
- 邮件是否发送成功及收件人

## 一键执行

配置完成后，在项目根目录：

```bash
bash .cursor/skills/publish-test/scripts/publish-test.sh
```

Agent 在用户说「发布test版本」时：

1. 读取本 skill
2. 检查 `config.env` 是否存在；不存在则引导用户从 `config.example.env` 复制
3. 执行 `publish-test.sh`
4. 根据输出向用户汇报结果

## 安全要求

- `config.env` 必须在 `.gitignore` 中
- **禁止**在对话或 commit 中输出 AccessKey、SMTP 授权码
- OSS 建议使用 RAM 子账号，仅授予目标路径的读写权限

## 延伸阅读

- Gradle APK 命名、ossutil 安装、QQ 邮箱配置：[reference.md](reference.md)
