package weblogic.management.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class ApplicationPollerLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.deploy.internal.ApplicationPollerLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(ApplicationPollerLogger.class.getName());
   }

   public static String logActivate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149400", 64, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149400";
   }

   public static String logRemove(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149401", 64, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149401";
   }

   public static String logThrowableOnActivate(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149408", 8, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149408";
   }

   public static String logWarnOnManagedServerTargets(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149403", 16, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149403";
   }

   public static String logThrowableOnDeactivate(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149407", 8, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149407";
   }

   public static String logThrowableOnServerStartup(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149409", 8, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149409";
   }

   public static String logUncaughtThrowable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149410", 8, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149410";
   }

   public static String logIOException(IOException arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149411", 8, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149411";
   }

   public static Loggable logIOExceptionLoggable(IOException arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149411", 8, args, "weblogic.management.deploy.internal.ApplicationPollerLogLocalizer", ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApplicationPollerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRedeployingOnStartup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149412", 64, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149412";
   }

   public static String logCouldnotCreateAutodeployDir(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149414", 16, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149414";
   }

   public static Loggable logCouldnotCreateAutodeployDirLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149414", 16, args, "weblogic.management.deploy.internal.ApplicationPollerLogLocalizer", ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApplicationPollerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExceptionWhileMigrating(Exception arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149415", 8, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149415";
   }

   public static Loggable logExceptionWhileMigratingLoggable(Exception arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("149415", 8, args, "weblogic.management.deploy.internal.ApplicationPollerLogLocalizer", ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger, ApplicationPollerLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logApplicationMigrated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("149416", 32, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149416";
   }

   public static String logFileHeld(File arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("149417", 16, args, ApplicationPollerLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      ApplicationPollerLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "149417";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.deploy.internal.ApplicationPollerLogLocalizer", ApplicationPollerLogger.class.getClassLoader());
      private MessageLogger messageLogger = ApplicationPollerLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = ApplicationPollerLogger.findMessageLogger();
      }
   }
}
