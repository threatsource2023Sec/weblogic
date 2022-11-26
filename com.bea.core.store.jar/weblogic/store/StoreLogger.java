package weblogic.store;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class StoreLogger {
   private static final String LOCALIZER_CLASS = "weblogic.store.StoreLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(StoreLogger.class.getName());
   }

   public static String logOpeningPersistentStore(String arg0, String arg1, String arg2, boolean arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("280008", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280008";
   }

   public static String logPersistentStoreOpened(String arg0, String arg1, int arg2, String arg3, boolean arg4, int arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("280009", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280009";
   }

   public static String logRecoveryNotComplete() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280012", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280012";
   }

   public static Loggable logRecoveryNotCompleteLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280012", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRecordTooLong(Number arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280013", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280013";
   }

   public static Loggable logRecordTooLongLoggable(Number arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280013", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorWritingToStore() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280019", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280019";
   }

   public static Loggable logErrorWritingToStoreLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280019", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorReadingFromStore() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280020", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280020";
   }

   public static Loggable logErrorReadingFromStoreLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280020", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorOpeningFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280021", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280021";
   }

   public static String logErrorCreatingFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280024", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280024";
   }

   public static Loggable logErrorCreatingFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280024", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoAccessToFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280026", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280026";
   }

   public static Loggable logNoAccessToFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280026", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreRecordNotFound(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280029", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280029";
   }

   public static Loggable logStoreRecordNotFoundLoggable(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280029", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreNotOpen(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280031", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280031";
   }

   public static Loggable logStoreNotOpenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280031", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreFatalError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280032", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280032";
   }

   public static Loggable logStoreFatalErrorLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280032", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCantAccessDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280035", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280035";
   }

   public static Loggable logCantAccessDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280035", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logMissingFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280036", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280036";
   }

   public static Loggable logMissingFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("280036", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTooManyFilesCreated(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280037", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280037";
   }

   public static Loggable logTooManyFilesCreatedLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280037", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFlushingFile() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280038", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280038";
   }

   public static Loggable logErrorFlushingFileLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280038", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRecordHandle(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280039", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280039";
   }

   public static Loggable logInvalidRecordHandleLoggable(long arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280039", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStoreRecord(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280040", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280040";
   }

   public static Loggable logInvalidStoreRecordLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280040", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStoreRecordVersion(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280041", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280041";
   }

   public static Loggable logInvalidStoreRecordVersionLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280041", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFileIsADirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280042", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280042";
   }

   public static Loggable logFileIsADirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280042", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDirectoryNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280044", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280044";
   }

   public static Loggable logDirectoryNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280044", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDirectoryNotADirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280045", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280045";
   }

   public static Loggable logDirectoryNotADirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280045", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoAccessToDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280046", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280046";
   }

   public static Loggable logNoAccessToDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280046", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCreateFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280051", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280051";
   }

   public static Loggable logCreateFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280051", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280052", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280052";
   }

   public static Loggable logReadFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280052", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidWritePolicy(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280055", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280055";
   }

   public static Loggable logInvalidWritePolicyLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280055", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistentStoreException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280056", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280056";
   }

   public static String logStoreAlreadyOpen(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280057", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280057";
   }

   public static Loggable logStoreAlreadyOpenLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280057", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidStoreConnectionName(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280059", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280059";
   }

   public static Loggable logInvalidStoreConnectionNameLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280059", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFatalExceptionEncountered(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280060", 1, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280060";
   }

   public static String logStoreDeploymentFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280061", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280061";
   }

   public static String logStoreShutdownFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280062", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280062";
   }

   public static String logInvalidTableReference(String arg0, String arg1, String arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280063", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280063";
   }

   public static Loggable logInvalidTableReferenceLoggable(String arg0, String arg1, String arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("280063", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreException(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280064", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280064";
   }

   public static Loggable logJDBCStoreExceptionLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("280064", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logWrappedJDBCStoreException(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("280065", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280065";
   }

   public static Loggable logWrappedJDBCStoreExceptionLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("280065", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidTableReference2(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280066", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280066";
   }

   public static Loggable logInvalidTableReference2Loggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280066", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreTableCreateSuccess(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280067", 32, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280067";
   }

   public static String logJDBCStoreTableCreateFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280068", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280068";
   }

   public static Loggable logJDBCStoreTableCreateFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280068", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreCreateUnknownDatabase(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280069", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280069";
   }

   public static Loggable logJDBCStoreCreateUnknownDatabaseLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("280069", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreCreateDDLFileNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280070", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280070";
   }

   public static Loggable logJDBCStoreCreateDDLFileNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280070", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreOpened(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280071", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280071";
   }

   public static String logJDBCStoreOpenFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280072", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280072";
   }

   public static String logInvalidFileVersion(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280073", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280073";
   }

   public static Loggable logInvalidFileVersionLoggable(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("280073", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnresolvableTransaction(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280074", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280074";
   }

   public static String logJDBCStoreTableOwnershipRefreshFailed(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280075", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280075";
   }

   public static String logJDBCStoreTableOwnershipWait(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280076", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280076";
   }

   public static String logJDBCStoreTableUnexpectedOwner(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280077", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280077";
   }

   public static String logWrongConnectionForHandle() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280078", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280078";
   }

   public static Loggable logWrongConnectionForHandleLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280078", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreDescription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280079", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280079";
   }

   public static Loggable getStoreDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280079", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreCreateCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280080", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280080";
   }

   public static Loggable getStoreCreateCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280080", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreReadCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280081", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280081";
   }

   public static Loggable getStoreReadCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280081", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreUpdateCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280082", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280082";
   }

   public static Loggable getStoreUpdateCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280082", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreDeleteCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280083", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280083";
   }

   public static Loggable getStoreDeleteCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280083", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreObjectCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280084", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280084";
   }

   public static Loggable getStoreObjectCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280084", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStorePhysicalWriteCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280085", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280085";
   }

   public static Loggable getStorePhysicalWriteCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280085", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreConnectionDescription() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280086", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280086";
   }

   public static Loggable getStoreConnectionDescriptionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280086", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreConnectionCreateCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280087", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280087";
   }

   public static Loggable getStoreConnectionCreateCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280087", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreConnectionReadCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280088", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280088";
   }

   public static Loggable getStoreConnectionReadCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280088", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreConnectionUpdateCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280089", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280089";
   }

   public static Loggable getStoreConnectionUpdateCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280089", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreConnectionDeleteCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280090", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280090";
   }

   public static Loggable getStoreConnectionDeleteCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280090", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String getStoreConnectionObjectCountAttribute() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280091", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280091";
   }

   public static Loggable getStoreConnectionObjectCountAttributeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280091", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidIntegerProperty(String arg0, String arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280092", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280092";
   }

   public static String logOutOfBlockSizeRange(String arg0, int arg1, int arg2, int arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("280093", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280093";
   }

   public static String logSizeNotPowerOfTwo(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280094", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280094";
   }

   public static String logBlockSizeIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280095", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280095";
   }

   public static String logInvalidDirectModeIgnored(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280096", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280096";
   }

   public static String logDualHandleOpenFailed(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280097", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280097";
   }

   public static String logWritePolicyDowngraded(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280101", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280101";
   }

   public static String logCacheSignatureVerificationFailed(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280102", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280102";
   }

   public static String logCacheInfo(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280103", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280103";
   }

   public static String logIncompatibleDirectIOAlignment(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280104", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280104";
   }

   public static String logFileOpenError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280105", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280105";
   }

   public static Loggable logFileOpenErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("280105", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFileMappingError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280106", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280106";
   }

   public static String logStoreRecordAlreadyDeleted(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280107", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280107";
   }

   public static Loggable logStoreRecordAlreadyDeletedLoggable(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("280107", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logStoreRecordAlreadyExists(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280108", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280108";
   }

   public static Loggable logStoreRecordAlreadyExistsLoggable(long arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("280108", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNativeDriverLoadFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280109", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280109";
   }

   public static String logInfoAttributeChanged(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280110", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280110";
   }

   public static String logInfoStringAttributeChanged(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280111", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280111";
   }

   public static String logStoreJDBCIgnoreAssertionError(String arg0, int arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280112", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280112";
   }

   public static String logJDBCStoreInitialRACInstance(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280113", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280113";
   }

   public static String logJDBCStoreNotEnoughConnection(String arg0, int arg1, int arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280114", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280114";
   }

   public static String logReplicatedStoreMemoryUsageInfo(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280115", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280115";
   }

   public static String logReplicatedStoreMemoryUsageWarning(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280116", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280116";
   }

   public static String logReplicatedStoreDaemonMemoryUsageInfo(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280117", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280117";
   }

   public static String logReplicatedStoreDaemonMemoryUsageWarning(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280118", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280118";
   }

   public static String logReplicatedStoreDaemonMemoryUsageError(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280119", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280119";
   }

   public static String logOpeningReplicatedStore(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280120", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280120";
   }

   public static String logReplicatedStoreOpened(String arg0, String arg1, String arg2, int arg3, int arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("280121", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280121";
   }

   public static String logMissingRegion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280122", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280122";
   }

   public static Loggable logMissingRegionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("280122", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTooManyRegionsCreated(int arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280123", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280123";
   }

   public static Loggable logTooManyRegionsCreatedLoggable(int arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280123", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorFlushingRegion() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280124", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280124";
   }

   public static Loggable logErrorFlushingRegionLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280124", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidRegionVersion(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280125", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280125";
   }

   public static Loggable logInvalidRegionVersionLoggable(String arg0, String arg1, int arg2, int arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("280125", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReplicatedStoreBlockSizeIgnored(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280126", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280126";
   }

   public static String logRegionOpenError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280127", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280127";
   }

   public static Loggable logRegionOpenErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("280127", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorInitializingStore() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280128", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280128";
   }

   public static Loggable logErrorInitializingStoreLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("280128", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreConnectionPolicy(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("280129", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280129";
   }

   public static String logJDBCStoreConnectionPolicyIncompatibile(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("280130", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280130";
   }

   public static Loggable logJDBCStoreConnectionPolicyIncompatibileLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("280130", 8, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStorePiggyBackCommitDisabled(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280131", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280131";
   }

   public static String logJDBCStoreUseUncachedConnections(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280132", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280132";
   }

   public static String logJDBCStoreVersionRecMissing(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280133", 8, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280133";
   }

   public static String logJDBCStoreRestoreMissingVersionRec(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("280134", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280134";
   }

   public static String logNonFatalFailureWhileOpening(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("280135", 64, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280135";
   }

   public static Loggable logNonFatalFailureWhileOpeningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("280135", 64, args, "weblogic.store.StoreLogLocalizer", StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger, StoreLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReplicatedStoreDeprecated() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("280136", 16, args, StoreLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      StoreLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "280136";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.store.StoreLogLocalizer", StoreLogger.class.getClassLoader());
      private MessageLogger messageLogger = StoreLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = StoreLogger.findMessageLogger();
      }
   }
}
