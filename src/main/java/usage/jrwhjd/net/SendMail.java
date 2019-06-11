package usage.jrwhjd.net;

import com.sun.mail.util.MailSSLSocketFactory;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * 发送邮件工具类.
 */
@Slf4j
public class SendMail implements Runnable {

  private String email;// 收件人邮箱
  private String content;

  private String myMail;
  private String smtp;
  private String regCode;
  private String subject;


  public static void main(String[] args) {
    // 异步发送(后一个邮件的发送不用等待前一封邮件发送结束)
    SendMail sm = new SendMail("smtp服务器的登录用户, 一般是发件人的邮件地址", "smtp.163.com", "smtp服务器的验证码");
    sm.sendTo("收件人的邮件地址", "test", "<html><p><font color='green'>green</font></p></html>");
    new Thread(sm).start();
    System.out.println("sending mail....");
    sm.sendTo("如果有另一个收件人的邮件地址", "test", "<html><p><font color='red'>red</font></p></html>");
    new Thread(sm).start();
    System.out.println("sending other mail....");
    // 在短时间内向多个地址发送邮件有可能被smtp提供方判断为恶意邮件发送, 请注意
  }


  /**
   * 创建邮件发送对象.
   *
   * @param mailFrom 寄件人邮件地址
   * @param smtpHost smtp 服务器域名或ip
   * @param regCode 认证 smtp 服务器的授权码(并非寄件人邮件的密码)
   */
  public SendMail(String mailFrom, String smtpHost, String regCode) {
    this.myMail = mailFrom;
    this.smtp = smtpHost;
    this.regCode = regCode;
  }


  /**
   * 执行邮件发送动作
   *
   * @param email 收件人电子邮件地址
   * @param subject 邮件标题
   * @param content 邮件内容, 可以是 html 文本
   */
  public void sendTo(String email, String subject, String content) {
    this.email = email;
    this.subject = subject;
    this.content = content;
  }


  public void run() {

    Properties p = new Properties();
    //发送邮件协议名称
    p.setProperty("mail.transport.protocol", "smtp");
    //设置邮件服务器主机名
    p.setProperty("mail.host", smtp);

    try {
      //QQ邮箱需要下面这段代码，163邮箱不需要
      MailSSLSocketFactory sf = new MailSSLSocketFactory();
      sf.setTrustAllHosts(true);
      p.setProperty("mail.smtp.auth", "true");
      p.put("mail.smtp.ssl.enable", "true");
      p.put("mail.smtp.ssl.socketFactory", sf);

      // 1.获取默认session对象
      Session session = Session.getDefaultInstance(p, new Authenticator() {
        public PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(myMail, regCode); // 发件人邮箱账号、授权码
        }
      });

      // 2.创建邮件对象
      // Message message = new MimeMessage(session);
      MimeMessage message = new MimeMessage(session);
      // 2.1设置发件人
      message.setFrom(new InternetAddress(myMail));
      // 2.2设置接收人
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
      // 2.3设置邮件主题
      message.setSubject(subject);
      // 2.4设置邮件内容
      message.setContent(content, "text/html;charset=UTF-8");
      // 3.发送邮件
      Transport.send(message);
      log.info("向 {} 发送邮件[{}] 成功", email, subject);
    } catch (Exception e) {
      log.warn("向 {} 发送邮件[{}] 失败", email, subject, e);
    }
  }
}