flowchart TD
A["你说：发布test版本"] --> B["Cursor 加载 publish-test Skill"]
B --> C["git fetch + merge origin/test"]
C --> D["./gradlew assembleDebug"]
D --> E["APK 重命名 app-debug-时间戳.apk"]
E --> F["ossutil 上传到阿里云 OSS"]
F --> G["QQ SMTP 发邮件给同事"]
G --> H["汇报下载链接"]
