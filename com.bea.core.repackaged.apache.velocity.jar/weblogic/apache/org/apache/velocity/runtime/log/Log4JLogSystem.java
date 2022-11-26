package weblogic.apache.org.apache.velocity.runtime.log;

import java.util.Enumeration;
import weblogic.apache.org.apache.log4j.Appender;
import weblogic.apache.org.apache.log4j.Category;
import weblogic.apache.org.apache.log4j.Layout;
import weblogic.apache.org.apache.log4j.PatternLayout;
import weblogic.apache.org.apache.log4j.Priority;
import weblogic.apache.org.apache.log4j.RollingFileAppender;
import weblogic.apache.org.apache.log4j.net.SMTPAppender;
import weblogic.apache.org.apache.log4j.net.SocketAppender;
import weblogic.apache.org.apache.log4j.net.SyslogAppender;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;

/** @deprecated */
public class Log4JLogSystem implements LogSystem {
   private RuntimeServices rsvc = null;
   protected Category logger = null;
   protected Layout layout = null;
   private String logfile = "";

   public void init(RuntimeServices rs) {
      this.rsvc = rs;
      this.logfile = this.rsvc.getString("runtime.log");

      try {
         this.internalInit();
         this.logVelocityMessage(0, "Log4JLogSystem initialized using logfile " + this.logfile);
      } catch (Exception var3) {
         System.out.println("PANIC : error configuring Log4JLogSystem : " + var3);
      }

   }

   private void internalInit() throws Exception {
      this.logger = Category.getInstance("");
      this.logger.setAdditivity(false);
      this.logger.setPriority(Priority.DEBUG);
      String pattern = this.rsvc.getString("runtime.log.logsystem.log4j.pattern");
      if (pattern == null || pattern.length() == 0) {
         pattern = "%d - %m%n";
      }

      this.layout = new PatternLayout(pattern);
      this.configureFile();
      this.configureRemote();
      this.configureSyslog();
      this.configureEmail();
   }

   private void configureFile() throws Exception {
      int backupFiles = this.rsvc.getInt("runtime.log.logsystem.log4j.file.backups", 1);
      int fileSize = this.rsvc.getInt("runtime.log.logsystem.log4j.file.size", 100000);
      Appender appender = new RollingFileAppender(this.layout, this.logfile, true);
      ((RollingFileAppender)appender).setMaxBackupIndex(backupFiles);
      if (fileSize > -1) {
         ((RollingFileAppender)appender).setMaximumFileSize((long)fileSize);
      }

      this.logger.addAppender(appender);
   }

   private void configureRemote() throws Exception {
      String remoteHost = this.rsvc.getString("runtime.log.logsystem.log4j.remote.host");
      int remotePort = this.rsvc.getInt("runtime.log.logsystem.log4j.remote.port", 1099);
      if (remoteHost != null && !remoteHost.trim().equals("") && remotePort > 0) {
         Appender appender = new SocketAppender(remoteHost, remotePort);
         this.logger.addAppender(appender);
      }
   }

   private void configureSyslog() throws Exception {
      String syslogHost = this.rsvc.getString("runtime.log.logsystem.log4j.syslogd.host");
      String syslogFacility = this.rsvc.getString("runtime.log.logsystem.log4j.syslogd.facility");
      if (syslogHost != null && !syslogHost.trim().equals("") && syslogFacility != null) {
         Appender appender = new SyslogAppender();
         ((SyslogAppender)appender).setLayout(this.layout);
         ((SyslogAppender)appender).setSyslogHost(syslogHost);
         ((SyslogAppender)appender).setFacility(syslogFacility);
         this.logger.addAppender(appender);
      }
   }

   private void configureEmail() throws Exception {
      String smtpHost = this.rsvc.getString("runtime.log.logsystem.log4j.email.server");
      String emailFrom = this.rsvc.getString("runtime.log.logsystem.log4j.email.from");
      String emailTo = this.rsvc.getString("runtime.log.logsystem.log4j.email.to");
      String emailSubject = this.rsvc.getString("runtime.log.logsystem.log4j.email.subject");
      String bufferSize = this.rsvc.getString("runtime.log.logsystem.log4j.email.buffer.size");
      if (smtpHost != null && !smtpHost.trim().equals("") && emailFrom != null && !smtpHost.trim().equals("") && emailTo != null && !emailTo.trim().equals("") && emailSubject != null && !emailSubject.trim().equals("") && bufferSize != null && !bufferSize.trim().equals("")) {
         SMTPAppender appender = new SMTPAppender();
         appender.setSMTPHost(smtpHost);
         appender.setFrom(emailFrom);
         appender.setTo(emailTo);
         appender.setSubject(emailSubject);
         appender.setBufferSize(Integer.parseInt(bufferSize));
         appender.setLayout(this.layout);
         appender.activateOptions();
         this.logger.addAppender(appender);
      }
   }

   public void logVelocityMessage(int level, String message) {
      switch (level) {
         case 0:
            this.logger.debug(message);
            break;
         case 1:
            this.logger.info(message);
            break;
         case 2:
            this.logger.warn(message);
            break;
         case 3:
            this.logger.error(message);
            break;
         default:
            this.logger.debug(message);
      }

   }

   protected void finalize() throws Throwable {
      this.shutdown();
   }

   public void shutdown() {
      Enumeration appenders = this.logger.getAllAppenders();

      while(appenders.hasMoreElements()) {
         Appender appender = (Appender)appenders.nextElement();
         appender.close();
      }

   }
}
