package com.bea.diagnostics.notifications;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import weblogic.diagnostics.notifications.i18n.NotificationsTextTextFormatter;

public final class SMTPNotificationService extends NotificationServiceAdapter implements NotificationConstants {
   private SMTPNotificationCustomizer customizer;
   private String smtpRecipients;
   private String smtpSubject;
   private String smtpBody;
   private Session mailSession;
   private Transport mailTransport;
   private InternetAddress[] mailToAddresses;
   private static final String DEFAULT_SUBJECT = NotificationsTextTextFormatter.getInstance().getSMTPNotificationDefaultSubjectText();

   SMTPNotificationService(String notificationName, Session mailSession, String recipientList) throws NotificationCreateException, InvalidNotificationException {
      this(notificationName, mailSession, recipientList, (String)null, (String)null, (SMTPNotificationCustomizer)null);
   }

   SMTPNotificationService(String serviceName, Session mailSession, String recipientList, String subject, String body, SMTPNotificationCustomizer customizer) throws NotificationCreateException {
      super(serviceName);
      this.mailSession = mailSession;
      this.customizer = customizer;
      this.smtpRecipients = recipientList;
      this.smtpSubject = subject;
      this.smtpBody = body;
      if (this.mailSession == null) {
         throw new NotificationCreateException(NotificationsTextTextFormatter.getInstance().getMailSessionNotSetText());
      } else {
         this.initializeSMTP();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created smtp notification " + this);
         }

      }
   }

   public String getSMTPRecipients() {
      return this.smtpRecipients;
   }

   public String getSMTPSubject() {
      return this.smtpSubject;
   }

   public String getSMTPBody() {
      return this.smtpBody;
   }

   public void setSMTPRecipients(String recipients) {
      if (recipients == null) {
         throw new InvalidNotificationException(NotificationsTextTextFormatter.getInstance().getInvalidSMTPNotificationRecipientList());
      } else {
         this.smtpRecipients = recipients;
         this.initMailToAddresses();
      }
   }

   public void setSMTPSubject(String aSMTPSubject) {
      this.smtpSubject = aSMTPSubject;
   }

   public void setSMTPBody(String aSMTPBody) {
      this.smtpBody = aSMTPBody;
   }

   public Session getMailSession() {
      return this.mailSession;
   }

   public void setMailSession(Session ms) {
      this.mailSession = ms;

      try {
         this.mailTransport = null;
         this.initMailTransport();
      } catch (NotificationCreateException var3) {
         throw new NotificationRuntimeException(var3);
      }
   }

   public void send(Notification notif) throws NotificationPropagationException {
      if (this.isEnabled()) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Handle smtp notification for " + this);
               debugLogger.debug("Watch notification: " + notif);
            }

            MimeMessage msg = new MimeMessage(this.mailSession);
            if (this.customizer != null) {
               msg.setSubject(this.customizer.getSubject(notif));
            } else if (this.smtpSubject != null && !this.smtpSubject.isEmpty()) {
               msg.setSubject(this.smtpSubject);
            } else {
               msg.setSubject(getDefaultSubject(notif));
            }

            StringWriter sw = new StringWriter(512);
            PrintWriter pw = new PrintWriter(sw);
            if (this.customizer != null) {
               pw.print(this.customizer.getBody(notif));
            } else {
               String defaultBody = this.getDefaultBody(notif);
               if (this.smtpBody != null) {
                  pw.println(this.smtpBody);
               } else {
                  pw.print(defaultBody);
               }
            }

            pw.close();
            sw.close();
            msg.setText(sw.toString());
            msg.setRecipients(RecipientType.TO, this.mailToAddresses);
            msg.setFrom();
            Transport.send(msg);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SMTP send of message suceeded.");
            }

         } catch (Exception var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("SMTP create of message or send failed with exception ", var6);
            }

            throw new NotificationPropagationException(var6);
         }
      }
   }

   public String toString() {
      return "SMTPNotificationListener - email recipients: " + this.smtpRecipients + " subject: " + this.smtpSubject + " body: " + this.smtpBody + " mailSession: " + this.mailSession;
   }

   public static String getDefaultSubject(Notification notif) {
      return DEFAULT_SUBJECT;
   }

   private String getDefaultBody(Notification notif) {
      NotificationsTextTextFormatter fm = NotificationsTextTextFormatter.getInstance();
      StringBuffer buf = new StringBuffer();
      List keys = notif.keyList();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         Object key = it.next();
         buf.append(fm.getSMTPDefaultBodyLine(key.toString(), notif.getValue(key).toString()));
      }

      return buf.toString();
   }

   private void initializeSMTP() throws NotificationCreateException {
      this.initMailTransport();
      this.initMailToAddresses();
   }

   private void initMailToAddresses() {
      if (this.smtpRecipients != null) {
         try {
            this.mailToAddresses = InternetAddress.parse(this.smtpRecipients, false);
         } catch (Exception var2) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Parsing of recepients failed with exception ", var2);
            }

            throw new InvalidNotificationException(var2);
         }
      }
   }

   private void initMailTransport() throws NotificationCreateException {
      if (this.mailTransport == null) {
         try {
            try {
               this.mailTransport = this.mailSession.getTransport("smtp");
            } catch (NoSuchProviderException var4) {
               try {
                  this.mailTransport = this.mailSession.getTransport();
               } catch (Exception var3) {
                  throw var4;
               }
            }
         } catch (Exception var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Transport lookup failed with exception ", var5);
            }

            throw new NotificationCreateException(var5);
         }
      }

   }

   public String getType() {
      return "SMTP";
   }

   public void destroy() {
      super.destroy();
      this.mailSession = null;
      this.customizer = null;
      this.mailToAddresses = null;
      this.mailTransport = null;
   }
}
