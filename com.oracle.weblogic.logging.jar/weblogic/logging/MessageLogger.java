package weblogic.logging;

import com.bea.logging.MsgIdPrefixConverter;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18ntools.L10nLookup;

public final class MessageLogger {
   private static final boolean USE_PREFIX = true;
   private static final String PREFIX_PROP = "weblogic.MessageIdPrefixEnabled";
   private static boolean usePrefix = true;
   private static Method getLoggerMethod;

   public static void log(String messageId, int severity, Object[] args, String className) {
      log(new CatalogMessage(messageId, severity, args, className));
   }

   public static void log(CatalogMessage catalogMsg) {
      Object[] args = catalogMsg.getArguments();
      WLLogRecord logRecord = new WLLogRecord(Level.OFF, (String)null);
      logRecord.setParameters(args);

      try {
         String msgId = catalogMsg.getMessageId();
         if (usePrefix) {
            try {
               String prefix = catalogMsg.getMessageIdPrefix();
               if (prefix != null) {
                  msgId = MsgIdPrefixConverter.convertMsgIdPrefix(prefix) + "-" + catalogMsg.getMessageId();
               }
            } catch (NumberFormatException var6) {
            }
         }

         logRecord.setId(msgId);
         Level level = WLLevel.getLevel(catalogMsg.getSeverity());
         logRecord.setLevel(level);
         logRecord.setLoggerName(catalogMsg.getSubsystem());
         logRecord.setMessage(catalogMsg.getMessage());
         logRecord.setParameters(args);
         logRecord.setDiagnosticVolume(catalogMsg.getDiagnosticVolume());
      } catch (MissingResourceException var7) {
         logRecord.setLoggerName("Unknown");
         logRecord.setMessage("Message text not found - " + var7.getMessage());
      }

      if (catalogMsg.isStackTraceEnabled()) {
         logRecord.setThrown(getThrowable(args));
      }

      try {
         Logger kernelLogger = (Logger)getLoggerMethod.invoke((Object)null);
         kernelLogger.log(logRecord);
      } catch (Exception var5) {
         throw new AssertionError(var5);
      }
   }

   public static void log(LogMessage logMsg) {
      if (logMsg instanceof CatalogMessage) {
         CatalogMessage catMsg = (CatalogMessage)logMsg;
         log(catMsg);
      } else {
         WLLogRecord logRecord = new WLLogRecord(Level.OFF, (String)null);
         String msgId = logMsg.getMessageId();
         if (msgId != null && msgId.length() > 0) {
            String prefix = usePrefix ? logMsg.getMessageIdPrefix() : "";
            if (prefix != null && prefix.length() > 0) {
               msgId = MsgIdPrefixConverter.convertMsgIdPrefix(prefix) + "-" + logMsg.getMessageId();
            }
         }

         logRecord.setId(msgId);
         Level level = WLLevel.getLevel(logMsg.getSeverity());
         logRecord.setLevel(level);
         logRecord.setLoggerName(logMsg.getSubsystem());
         logRecord.setMessage(logMsg.getMessage());
         logRecord.setThrown(logMsg.getThrowable());

         try {
            Logger kernelLogger = (Logger)getLoggerMethod.invoke((Object)null);
            kernelLogger.log(logRecord);
         } catch (Exception var5) {
            throw new AssertionError(var5);
         }
      }
   }

   private static Throwable getThrowable(Object[] args) {
      if (args == null) {
         return null;
      } else {
         int candidateIndex = args.length - 1;
         if (candidateIndex >= 0) {
            Object throwableCandidate = args[candidateIndex];
            if (throwableCandidate instanceof Throwable) {
               return (Throwable)throwableCandidate;
            }
         }

         return null;
      }
   }

   public static void log(Level level, String subsystem, String msg) {
      log(level, subsystem, msg, (Throwable)null);
   }

   public static void log(Level level, String subsystem, String msg, Throwable thrown) {
      WLLogRecord logRecord = new WLLogRecord(level, msg, thrown);
      logRecord.setLoggerName(subsystem);

      try {
         Logger kernelLogger = (Logger)getLoggerMethod.invoke((Object)null);
         kernelLogger.log(logRecord);
      } catch (Exception var6) {
         throw new AssertionError(var6);
      }
   }

   public static void setUsePrefix(boolean p) {
      usePrefix = p;
   }

   public static String localizeMessage(String id, Object[] args, String ln) {
      try {
         Localizer localizer = L10nLookup.getLocalizer(Locale.getDefault(), ln);
         String body = localizer.getBody(id);
         return MessageFormat.format(body, args);
      } catch (MissingResourceException var5) {
         return "Error message text was not found: " + var5.getMessage();
      }
   }

   static {
      try {
         String prefixProp = System.getProperty("weblogic.MessageIdPrefixEnabled");
         if (prefixProp != null) {
            setUsePrefix(Boolean.getBoolean("weblogic.MessageIdPrefixEnabled"));
         }
      } catch (Exception var2) {
      }

      try {
         Class clz = Class.forName("weblogic.kernel.KernelLogManager");
         getLoggerMethod = clz.getMethod("getLogger");
      } catch (Exception var1) {
         throw new AssertionError(var1);
      }
   }
}
