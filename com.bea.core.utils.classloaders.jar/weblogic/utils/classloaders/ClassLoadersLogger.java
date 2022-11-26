package weblogic.utils.classloaders;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class ClassLoadersLogger {
   private static final String LOCALIZER_CLASS = "weblogic.utils.classloaders.ClassLoadersLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ClassLoadersLogger.class.getName());
   }

   public static String wrongCompilerVersion(String arg0, Error arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162500", 8, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162500";
   }

   public static String unexpectedClassFormatError(String arg0, Error arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162501", 8, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162501";
   }

   public static String errorReadingManifestFile(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162502", 16, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162502";
   }

   public static String preProcessorClassNotFound(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162503", 16, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162503";
   }

   public static String errorInitializingPreProcessorClass(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162504", 16, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162504";
   }

   public static String errorPreProcessingClass(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2162505", 16, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162505";
   }

   public static String errorReadingJarFile(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162506", 16, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162506";
   }

   public static String symlinkCycleDetected(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2162507", 8, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162507";
   }

   public static String foundCorruptedJarFile(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2162508", 16, args, ClassLoadersLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ClassLoadersLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2162508";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.utils.classloaders.ClassLoadersLogLocalizer", ClassLoadersLogger.class.getClassLoader());
      private MessageLogger messageLogger = ClassLoadersLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ClassLoadersLogger.findMessageLogger();
      }
   }
}
