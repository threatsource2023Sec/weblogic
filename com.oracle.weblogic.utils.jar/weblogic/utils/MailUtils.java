package weblogic.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {
   public static void sendMail(String smtphost, String from, String to, String subject, String message) throws MessagingException {
      Properties props = new Properties();
      props.put("mail.smtp.host", smtphost);
      Session session = Session.getDefaultInstance(props, (Authenticator)null);
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(from));
      InternetAddress[] address = new InternetAddress[]{new InternetAddress(to)};
      msg.setRecipients(RecipientType.TO, address);
      msg.setSubject(subject);
      msg.setSentDate(new Date());
      msg.setText(message);
      Transport.send(msg);
   }
}
