package weblogic.transaction.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class TXExceptionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.transaction.internal.TXExceptionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(TXExceptionLogger.class.getName());
   }

   public static String logUnresolvedLLROnePhaseCommit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("119000", 16, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119000";
   }

   public static Loggable logUnresolvedLLROnePhaseCommitLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("119000", 16, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnresolvedLLRTwoPhaseCommit(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("119001", 16, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119001";
   }

   public static Loggable logUnresolvedLLRTwoPhaseCommitLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("119001", 16, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedLLRRecover(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("119002", 4, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119002";
   }

   public static Loggable logFailedLLRRecoverLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("119002", 4, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCorruptedLLRRecord(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("119003", 4, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119003";
   }

   public static Loggable logCorruptedLLRRecordLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("119003", 4, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUserPreferredServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("119004", 8, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119004";
   }

   public static Loggable logInvalidUserPreferredServerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("119004", 8, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJTAFailedAndForceShutdown() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("119005", 8, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119005";
   }

   public static Loggable logJTAFailedAndForceShutdownLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("119005", 8, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedPrimaryStoreRecover(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("119006", 4, args, TXExceptionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "119006";
   }

   public static Loggable logFailedPrimaryStoreRecoverLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("119006", 4, args, "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, TXExceptionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.transaction.internal.TXExceptionLogLocalizer", TXExceptionLogger.class.getClassLoader());
      private MessageLogger messageLogger = TXExceptionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = TXExceptionLogger.findMessageLogger();
      }
   }
}
