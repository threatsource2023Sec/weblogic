package weblogic.jndi;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class JNDILogger {
   private static final String LOCALIZER_CLASS = "weblogic.jndi.JNDILogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(JNDILogger.class.getName());
   }

   public static String logObsoleteProp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("050000", 16, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050000";
   }

   public static String logDiffThread() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("050001", 16, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050001";
   }

   public static String logCannotReplicateObjectInCluster(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("050002", 8, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050002";
   }

   public static String logCannotCreateInitialContext(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("050003", 8, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050003";
   }

   public static String logUnableToBind(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("050004", 8, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050004";
   }

   public static String logUnableToUnBind(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("050005", 8, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050005";
   }

   public static String logExternalAppLookupWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("050006", 16, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050006";
   }

   public static String logGlobalResourceLookupWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("050007", 16, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050007";
   }

   public static String logIsCrossPartitionAccessWarning(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("050008", 16, args, JNDILogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      JNDILogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "050008";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jndi.JNDILogLocalizer", JNDILogger.class.getClassLoader());
      private MessageLogger messageLogger = JNDILogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = JNDILogger.findMessageLogger();
      }
   }
}
