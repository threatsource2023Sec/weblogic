package weblogic.logging.commons;

import com.bea.logging.MsgIdPrefixConverter;
import org.apache.commons.logging.Log;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.MessageDispatcher;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;

public class LogImpl implements Log, MessageLoggerRegistryListener, Constants {
   private static final boolean DEBUG = false;
   private String name;
   private MessageDispatcher logger;

   public LogImpl(String name) {
      this(name, true);
   }

   LogImpl(String name, boolean register) {
      this.name = "WebLogicCommons";
      if (name == null) {
         throw new IllegalArgumentException();
      } else {
         this.name = name;
         if (register) {
            this.initMessageDispatcher();
            MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
         }

      }
   }

   public boolean isDebugEnabled() {
      return this.logger.isSeverityEnabled(128);
   }

   public boolean isErrorEnabled() {
      return this.logger.isSeverityEnabled(8);
   }

   public boolean isFatalEnabled() {
      return this.logger.isSeverityEnabled(1);
   }

   public boolean isInfoEnabled() {
      return this.logger.isSeverityEnabled(64);
   }

   public boolean isTraceEnabled() {
      return this.logger.isSeverityEnabled(256);
   }

   public boolean isWarnEnabled() {
      return this.logger.isSeverityEnabled(16);
   }

   public void trace(Object object) {
      if (this.isTraceEnabled()) {
         this.trace(object, (Throwable)null);
      }

   }

   public void trace(Object object, Throwable throwable) {
      if (this.isTraceEnabled()) {
         this.doLog(256, object, throwable);
      }

   }

   public void debug(Object object) {
      if (this.isDebugEnabled()) {
         this.debug(object, (Throwable)null);
      }

   }

   public void debug(Object object, Throwable throwable) {
      if (this.isDebugEnabled()) {
         this.doLog(128, object, throwable);
      }

   }

   public void info(Object object) {
      if (this.isInfoEnabled()) {
         this.info(object, (Throwable)null);
      }

   }

   public void info(Object object, Throwable throwable) {
      if (this.isInfoEnabled()) {
         this.doLog(64, object, throwable);
      }

   }

   public void warn(Object object) {
      if (this.isWarnEnabled()) {
         this.warn(object, (Throwable)null);
      }

   }

   public void warn(Object object, Throwable throwable) {
      if (this.isWarnEnabled()) {
         this.doLog(16, object, throwable);
      }

   }

   public void error(Object object) {
      if (this.isErrorEnabled()) {
         this.error(object, (Throwable)null);
      }

   }

   public void error(Object object, Throwable throwable) {
      if (this.isErrorEnabled()) {
         this.doLog(8, object, throwable);
      }

   }

   public void fatal(Object object) {
      if (this.isFatalEnabled()) {
         this.fatal(object, (Throwable)null);
      }

   }

   public void fatal(Object object, Throwable throwable) {
      if (this.isFatalEnabled()) {
         this.doLog(1, object, throwable);
      }

   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (o instanceof LogImpl) {
         LogImpl l = (LogImpl)o;
         return l.name.equals(this.name);
      } else {
         return false;
      }
   }

   private void doLog(int severityLevel, Object object, Throwable throwable) {
      if (object == null) {
         object = "";
      }

      LogMessage logMessage;
      if (object instanceof CommonsLogObject) {
         CommonsLogObject clo = (CommonsLogObject)object;
         logMessage = new LogMessage(clo.getMessageId(), clo.getMessageIdPrefix(), clo.getSubsystemId(), severityLevel, clo.getMessage(), throwable);
      } else {
         logMessage = new LogMessage("000000", MsgIdPrefixConverter.getDefaultMsgIdPrefix(), this.name, severityLevel, object.toString(), throwable);
      }

      this.logger.log(logMessage);
   }

   public void messageLoggerRegistryUpdated() {
      this.initMessageDispatcher();
   }

   private void initMessageDispatcher() {
      this.logger = MessageLoggerRegistry.findMessageLogger(this.name).getMessageDispatcher(this.name);
   }
}
