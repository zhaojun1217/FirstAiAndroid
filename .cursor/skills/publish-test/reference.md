# 发布 Test 版本 — 详细参考

## 一、整体架构

```mermaid
flowchart LR
    A[用户: 发布test版本] --> B[Cursor Agent + Skill]
    B --> C[Git fetch/merge]
    C --> D[gradlew assembleDebug]
    D --> E[重命名 APK 加时间戳]
    E --> F[ossutil 上传 OSS]
    F --> G[Python SMTP 发 QQ 邮件]
    G --> H[汇报下载链接]
```

## 二、你需要准备的东西

| 项目 | 说明 |
|------|------|
| GitHub test 分支 | 远程需有 `test`（或你自定义的分支名） |
| 阿里云 OSS | Bucket、Endpoint、AccessKey（建议 RAM 子账号） |
| QQ 邮箱 | 开启 SMTP，生成授权码 |
| ossutil | [官方安装文档](https://help.aliyun.com/document_detail/120075.html) |
| Python 3 | macOS 自带，用于发邮件 |

### 当前仓库 Git 状态说明

- 远程：`origin/main`
- 本地另有 `test1` 分支
- 若要用 `test` 分支：可 `git checkout -b test` 后 `git push -u origin test`，或将 `config.env` 中 `GIT_BRANCH=test1`

## 三、Gradle：打包时自动加时间戳（可选）

在 `app/build.gradle.kts` 的 `android { }` 块之后添加：

```kotlin
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

androidComponents {
    onVariants { variant ->
        val buildTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        variant.outputs.forEach { output ->
            val buildType = variant.buildType ?: "unknown"
            output.outputFileName.set("app-${buildType}-${buildTime}.apk")
        }
    }
}
```

不修改 Gradle 时，由 `publish-test.sh` 在构建后复制并重命名 APK，效果相同。

## 四、阿里云 OSS

### 4.1 创建 Bucket

1. 登录 [OSS 控制台](https://oss.console.aliyun.com/)
2. 创建 Bucket（如 `firstai-android-test`）
3. 记录 **Endpoint**（如 `oss-cn-hangzhou.aliyuncs.com`）

### 4.2 RAM 子账号（推荐）

1. 创建 RAM 用户，勾选「OpenAPI 调用访问」
2. 授权策略示例（仅允许指定前缀上传）：

```json
{
  "Version": "1",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["oss:PutObject", "oss:GetObject"],
      "Resource": ["acs:oss:*:*:your-bucket-name/apk/test/*"]
    }
  ]
}
```

### 4.3 安装 ossutil（macOS）

```bash
curl -o ossutil.zip https://gosspublic.alicdn.com/ossutil/1.7.19/ossutilmac64.zip
unzip ossutil.zip
chmod +x ossutilmac64
sudo mv ossutilmac64 /usr/local/bin/ossutil
ossutil config  # 交互式配置，或与脚本一样用 -e -i -k 参数
```

### 4.4 下载链接

- **公共读 Bucket**：`https://{bucket}.{endpoint}/{object-key}`
- **私有 Bucket**：需签名 URL，可在脚本中调用 `ossutil sign` 生成临时链接

```bash
ossutil sign "oss://bucket/apk/test/app-debug-xxx.apk" --timeout 604800
```

## 五、QQ 邮箱 SMTP

### 5.1 开启 SMTP 并获取授权码

1. 登录 [QQ 邮箱](https://mail.qq.com) → 设置 → 账户
2. 开启「POP3/SMTP服务」或「IMAP/SMTP服务」
3. 按提示用手机发送短信，获得 **16 位授权码**

### 5.2 SMTP 参数

| 参数 | 值 |
|------|-----|
| 服务器 | smtp.qq.com |
| 端口 | 465（SSL） |
| 用户名 | 完整 QQ 邮箱地址 |
| 密码 | 授权码（不是 QQ 登录密码） |

## 六、配置文件

复制示例并编辑：

```bash
cp .cursor/skills/publish-test/config.example.env \
   .cursor/skills/publish-test/config.env
```

将 `config.env` 加入 `.gitignore`。

## 七、故障排查

| 现象 | 可能原因 |
|------|----------|
| `git merge` 冲突 | 本地与远程 test 分支有分歧，需手动解决 |
| `assembleDebug` 失败 | JDK/SDK 未配置，检查 `local.properties` |
| ossutil 403 | AccessKey 权限不足或路径错误 |
| 邮件发送失败 | 用了 QQ 密码而非授权码；或未开 SMTP |
| 同事打不开链接 | Bucket 私有且未发签名 URL |

## 八、Skill 文件结构

```
.cursor/skills/publish-test/
├── SKILL.md              # Agent 主流程（本仓库已创建）
├── reference.md          # 本文档
├── config.example.env    # 配置模板
└── scripts/
    ├── publish-test.sh   # 一键脚本
    └── send-mail.py      # QQ 邮件
```
