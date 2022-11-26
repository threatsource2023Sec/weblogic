package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.bea.diagnostics.notifications.Notification;
import com.bea.diagnostics.notifications.NotificationServiceFactory;
import com.bea.diagnostics.notifications.SMTPNotificationCustomizer;
import com.bea.diagnostics.notifications.SMTPNotificationService;
import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.ExpressionEvaluator;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import javax.mail.Session;
import javax.naming.InitialContext;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFSMTPNotificationBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.notifications.i18n.NotificationsTextTextFormatter;
import weblogic.server.GlobalServiceLocator;

final class SMTPNotificationListener extends WatchNotificationListenerCommon implements WatchNotificationListener, SMTPNotificationCustomizer {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final String SMTP_NOTIFICATION_NAME = "SMTPNotificationName";
   private String[] smtpRecipients;
   private String smtpSubject;
   private String smtpBody;
   private String mailSessionJNDIName;
   private Session mailSession;
   private SMTPNotificationService smtpService;
   private ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();

   SMTPNotificationListener(WLDFSMTPNotificationBean configBean, WatchManagerStatisticsImpl stats) throws NotificationCreateException {
      super(configBean, stats);
      this.smtpRecipients = configBean.getRecipients();
      this.smtpSubject = configBean.getSubject();
      this.smtpBody = configBean.getBody();
      this.mailSessionJNDIName = configBean.getMailSessionJNDIName();
      if (this.smtpRecipients == null) {
         throw new InvalidNotificationException("Email destination must be set and cannot be null");
      } else if (this.mailSessionJNDIName == null) {
         throw new InvalidNotificationException("Mail Session JNDI Name must be set and cannot be null");
      } else {
         this.mailSession = null;
         this.initializeSMTP();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created smtp notification " + this);
         }

      }
   }

   String[] getSMTPRecipients() {
      return this.smtpRecipients;
   }

   String getSMTPSubject() {
      return this.smtpSubject;
   }

   String getSMTPBody() {
      return this.smtpBody;
   }

   String getMailSessionJNDIName() {
      return this.mailSessionJNDIName;
   }

   void setSMTPRecipients(String[] recipients) {
      this.smtpRecipients = recipients;
      if (this.smtpService != null) {
         String list = WatchUtils.getToAddresses(this.smtpRecipients);
         this.smtpService.setSMTPRecipients(list);
      }

   }

   void setSMTPSubject(String aSMTPSubject) {
      this.smtpSubject = aSMTPSubject;

      try {
         this.initializeSMTP();
      } catch (NotificationCreateException var3) {
      }

   }

   void setSMTPBody(String aSMTPBody) {
      this.smtpBody = aSMTPBody;

      try {
         this.initializeSMTP();
      } catch (NotificationCreateException var3) {
      }

   }

   void setMailSessionJNDIName(String mailSessionJNDIName) {
      this.mailSessionJNDIName = mailSessionJNDIName;

      try {
         this.initializeSMTP();
      } catch (NotificationCreateException var3) {
      }

   }

   public synchronized void processWatchNotification(Notification wn) {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Handle smtp notification for " + this);
            debugLogger.debug("Watch notification: " + wn);
         }

         this.getSMTPService().send(wn);
         this.getStatistics().incrementTotalSMTPNotificationsPerformed();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("SMTP send of message suceeded.");
         }
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("SMTP create of message or send failed with exception ", var3);
         }

         this.getStatistics().incrementTotalFailedSMTPNotifications();
         DiagnosticsLogger.logMessagingExceptionInNotification(var3);
      }

   }

   public String toString() {
      return "SMTPNotificationListener - email recipients: " + Arrays.toString(this.smtpRecipients) + " subject: " + this.smtpSubject + " body: " + this.smtpBody + " mailSession: " + this.mailSession + " ndi name: " + this.mailSessionJNDIName;
   }

   private String getDefaultSubject(WatchNotification wn) {
      DiagnosticsTextTextFormatter fm = DiagnosticsTextTextFormatter.getInstance();
      String defaultSubject = fm.getSMTPDefaultSubject(wn.getWatchName(), wn.getWatchSeverityLevel(), wn.getWatchServerName(), wn.getWatchTime());
      return defaultSubject;
   }

   private String getDefaultBody(WatchNotification wn) {
      WatchNotificationInternal wni = (WatchNotificationInternal)wn;
      NotificationsTextTextFormatter fm = NotificationsTextTextFormatter.getInstance();
      StringBuffer buf = new StringBuffer();
      synchronized(buf) {
         buf.append(fm.getSMTPDefaultBodyLine("WatchTime", wn.getWatchTime()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchDomainName", wn.getWatchDomainName()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchServerName", wn.getWatchServerName()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchModule", wni.getModuleName()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchSeverityLevel", wn.getWatchSeverityLevel()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchName", wn.getWatchName()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchRuleType", wn.getWatchRuleType()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchRule", wn.getWatchRule()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchData", wn.getWatchDataToString()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchAlarmType", wn.getWatchAlarmType()));
         buf.append(fm.getSMTPDefaultBodyLine("WatchAlarmResetPeriod", wn.getWatchAlarmResetPeriod()));
         buf.append(fm.getSMTPDefaultBodyLine("SMTPNotificationName", this.getNotificationName()));
      }

      return buf.toString();
   }

   private synchronized void initializeSMTP() throws NotificationCreateException, InvalidNotificationException {
      try {
         InitialContext ctx = new InitialContext();
         this.mailSession = (Session)ctx.lookup(this.mailSessionJNDIName);
      } catch (Exception var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Mail session lookup failed with exception ", var2);
         }

         DiagnosticsLogger.logErrorInMailNotification(var2);
         throw new NotificationCreateException(var2);
      }

      if (this.smtpRecipients != null) {
         String list = WatchUtils.getToAddresses(this.smtpRecipients);
         this.getSMTPService().setSMTPRecipients(list);
      }
   }

   private SMTPNotificationService getSMTPService() throws NotificationCreateException {
      if (this.smtpService == null) {
         try {
            this.smtpService = NotificationServiceFactory.getInstance().createSMTPNotificationService(this.getNotificationName(), this.mailSession, (String)null, this.smtpSubject, this.smtpBody, this);
         } catch (com.bea.diagnostics.notifications.NotificationCreateException var2) {
            throw new NotificationCreateException(var2);
         }
      }

      return this.smtpService;
   }

   public String getSubject(Notification n) {
      if (this.smtpSubject != null && !this.smtpSubject.isEmpty()) {
         EvaluatorFactory evaluatorFactory = (EvaluatorFactory)this.serviceLocator.getService(EvaluatorFactory.class, new Annotation[0]);
         ExpressionEvaluator expressionEvaluator = evaluatorFactory.createEvaluator(new Annotation[0]);

         String var4;
         try {
            expressionEvaluator = evaluatorFactory.createEvaluator(new Annotation[0]);
            ExpressionEvaluationUtil.bindNotification(expressionEvaluator, n);
            var4 = ((String)expressionEvaluator.evaluate(this.smtpSubject, String.class)).toString();
         } finally {
            evaluatorFactory.destroyEvaluator(expressionEvaluator);
         }

         return var4;
      } else {
         return this.getDefaultSubject((WatchNotification)n);
      }
   }

   public String getBody(Notification n) {
      String body = null;
      if (this.smtpBody != null && !this.smtpBody.isEmpty()) {
         try {
            StringWriter sw = new StringWriter(512);
            PrintWriter pw = new PrintWriter(sw);
            EvaluatorFactory evaluatorFactory = (EvaluatorFactory)this.serviceLocator.getService(EvaluatorFactory.class, new Annotation[0]);
            ExpressionEvaluator expressionEvaluator = evaluatorFactory.createEvaluator(new Annotation[0]);

            try {
               expressionEvaluator = evaluatorFactory.createEvaluator(new Annotation[0]);
               ExpressionEvaluationUtil.bindNotification(expressionEvaluator, n);
               pw.println((String)expressionEvaluator.evaluate(this.smtpBody, String.class));
            } finally {
               evaluatorFactory.destroyEvaluator(expressionEvaluator);
            }

            pw.close();
            sw.close();
            body = sw.toString();
         } catch (IOException var11) {
            throw new RuntimeException(var11);
         }
      } else {
         body = this.getDefaultBody((WatchNotification)n);
      }

      return body;
   }
}
