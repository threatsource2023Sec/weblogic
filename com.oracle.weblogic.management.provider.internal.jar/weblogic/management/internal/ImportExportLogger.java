package weblogic.management.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ImportExportLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.internal.ImportExportLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ImportExportLogger.class.getName());
   }

   public static String logErrorRddNotInZip(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("213500", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213500";
   }

   public static Loggable logErrorRddNotInZipLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("213500", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPartitionToExportNotInDomain(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("213501", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213501";
   }

   public static Loggable logPartitionToExportNotInDomainLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("213501", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRGTAlreadyExistDuringImport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("213502", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213502";
   }

   public static Loggable logRGTAlreadyExistDuringImportLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("213502", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToDeleterPendingFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("213503", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213503";
   }

   public static Loggable logFailedToDeleterPendingFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("213503", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailToOverWriteImportedFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("213504", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213504";
   }

   public static Loggable logFailToOverWriteImportedFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("213504", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logApplicationNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("213505", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213505";
   }

   public static Loggable logApplicationNotFoundLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("213505", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStagedApplicationNotFound(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("213506", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213506";
   }

   public static Loggable logStagedApplicationNotFoundLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("213506", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalReferenceAttribute(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("213507", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213507";
   }

   public static Loggable logIllegalReferenceAttributeLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("213507", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalNameChange(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("213508", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213508";
   }

   public static Loggable logIllegalNameChangeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("213508", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExportFailForRestartRequired(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("213509", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213509";
   }

   public static Loggable logExportFailForRestartRequiredLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("213509", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOperationFailException(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("213510", 8, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213510";
   }

   public static Loggable logOperationFailExceptionLoggable(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("213510", 8, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOperationSucceeded(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("213511", 64, args, ImportExportLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "213511";
   }

   public static Loggable logOperationSucceededLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("213511", 64, args, "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ImportExportLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.internal.ImportExportLogLocalizer", ImportExportLogger.class.getClassLoader());
      private MessageLogger messageLogger = ImportExportLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ImportExportLogger.findMessageLogger();
      }
   }
}
