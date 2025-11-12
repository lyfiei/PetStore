package csu.web.mypetstore.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private static final String FROM_EMAIL = "2473108081@qq.com"; // 你的邮箱
    private static final String AUTH_CODE = "sjwtiogcrjegdiba";    // 邮箱授权码

    public void sendEmail(String toEmail, String code) {
        String subject = "【MyPetStore】登录验证码";
        String content = "您的验证码是：" + code + "，有效期5分钟，请勿泄露给他人。";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, AUTH_CODE);
            }
        });session.setDebug(true); // 打印调试信息

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");
            Transport.send(message);
            System.out.println("邮件已发送到 " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
