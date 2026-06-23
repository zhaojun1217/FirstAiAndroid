#!/usr/bin/env python3
"""通过 QQ 邮箱 SMTP 发送 test 包下载通知。"""

import argparse
import smtplib
import ssl
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from pathlib import Path


def load_config() -> dict[str, str]:
    skill_dir = Path(__file__).resolve().parent.parent
    config_path = skill_dir / "config.env"
    if not config_path.exists():
        raise FileNotFoundError(f"未找到配置文件: {config_path}")

    config: dict[str, str] = {}
    for line in config_path.read_text(encoding="utf-8").splitlines():
        line = line.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, value = line.split("=", 1)
        config[key.strip()] = value.strip()
    return config


def send_notification(
    apk_name: str,
    download_url: str,
    branch: str,
    commit: str,
) -> None:
    config = load_config()
    mail_from = config["MAIL_FROM"]
    mail_auth_code = config["MAIL_AUTH_CODE"]
    mail_to_list = [addr.strip() for addr in config["MAIL_TO"].split(",") if addr.strip()]

    subject = f"[Test包] {apk_name}"
    body = f"""同事你好，

Test 版本已发布，请通过以下链接下载安装：

下载地址：{download_url}
文件名：  {apk_name}
分支：    {branch}
Commit：  {commit}

如有问题请联系发布人。
"""

    message = MIMEMultipart()
    message["From"] = mail_from
    message["To"] = ", ".join(mail_to_list)
    message["Subject"] = subject
    message.attach(MIMEText(body, "plain", "utf-8"))

    context = ssl.create_default_context()
    with smtplib.SMTP_SSL("smtp.qq.com", 465, context=context) as smtp:
        smtp.login(mail_from, mail_auth_code)
        smtp.sendmail(mail_from, mail_to_list, message.as_string())

    print(f"邮件已发送至: {', '.join(mail_to_list)}")


def main() -> None:
    parser = argparse.ArgumentParser(description="发送 test APK 下载通知邮件")
    parser.add_argument("--apk-name", required=True)
    parser.add_argument("--download-url", required=True)
    parser.add_argument("--branch", required=True)
    parser.add_argument("--commit", required=True)
    args = parser.parse_args()

    send_notification(
        apk_name=args.apk_name,
        download_url=args.download_url,
        branch=args.branch,
        commit=args.commit,
    )


if __name__ == "__main__":
    main()
