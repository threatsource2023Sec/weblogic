package weblogic.logging;

import weblogic.common.LogServicesUtil;
import weblogic.common.T3Exception;

/** @deprecated */
@Deprecated
public class LogOutputStream {
   public static final String UNKNOWN = "Default";
   private final String channel;

   public LogOutputStream(String c) {
      if (c == null) {
         this.channel = "Default";
      } else {
         Subsystems.add(c);
         this.channel = c;
      }

   }

   public void emergency(String msg) {
      MessageLogger.log(WLLevel.EMERGENCY, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void emergency(String msg, Throwable t) {
      MessageLogger.log(WLLevel.EMERGENCY, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void alert(String msg) {
      MessageLogger.log(WLLevel.ALERT, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void alert(String msg, Throwable t) {
      MessageLogger.log(WLLevel.ALERT, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void critical(String msg) {
      MessageLogger.log(WLLevel.CRITICAL, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void critical(String msg, Throwable t) {
      MessageLogger.log(WLLevel.CRITICAL, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void error(String msg) {
      MessageLogger.log(WLLevel.ERROR, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void error(String msg, Throwable t) {
      MessageLogger.log(WLLevel.ERROR, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void error(Throwable th) {
      this.error("", th);
   }

   public void notice(String msg) {
      MessageLogger.log(WLLevel.NOTICE, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void notice(String msg, Throwable t) {
      MessageLogger.log(WLLevel.NOTICE, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void warning(String msg) {
      MessageLogger.log(WLLevel.WARNING, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void warning(String msg, Throwable t) {
      MessageLogger.log(WLLevel.WARNING, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void info(String msg) {
      MessageLogger.log(WLLevel.INFO, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void info(String msg, Throwable t) {
      MessageLogger.log(WLLevel.INFO, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void debug(String msg) {
      MessageLogger.log(WLLevel.DEBUG, this.channel, LogServicesUtil.encodeLogMsg(msg));
   }

   public void debug(String msg, Throwable t) {
      MessageLogger.log(WLLevel.DEBUG, this.channel, LogServicesUtil.encodeLogMsg(msg), LogServicesUtil.encodeThrowable(t));
   }

   public void log(String msg) throws T3Exception {
      this.info(msg);
   }

   public void log(String msg, Throwable th) throws T3Exception {
      this.info(msg, th);
   }

   public void error(String msg, String stackTrace) throws T3Exception {
      this.error(msg + "\n" + stackTrace);
   }

   public void security(String s) throws T3Exception {
      this.critical(s);
   }
}
