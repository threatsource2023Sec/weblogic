package weblogic.management.mbeanservers.edit.internal;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class DomainToPartitionLogger {
   private static final String LOCALIZER_CLASS = "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(DomainToPartitionLogger.class.getName());
   }

   public static String logErrorArchiveNull(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194801", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194801";
   }

   public static Loggable logErrorArchiveNullLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194801", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNullPartitionName() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2194802", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194802";
   }

   public static Loggable logNullPartitionNameLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2194802", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidHAPoliciesForPathService(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194803", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194803";
   }

   public static Loggable logInvalidHAPoliciesForPathServiceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194803", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExistingPartition(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194804", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194804";
   }

   public static Loggable logExistingPartitionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194804", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEmptyDomainArchive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194805", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194805";
   }

   public static Loggable logEmptyDomainArchiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194805", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncompatibleDomainArchive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194806", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194806";
   }

   public static Loggable logIncompatibleDomainArchiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194806", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoRealmAvailable(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194807", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194807";
   }

   public static Loggable logNoRealmAvailableLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194807", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTNotForTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194808", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194808";
   }

   public static Loggable logVTNotForTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194808", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTTargetMemberOfCluster(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2194809", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194809";
   }

   public static Loggable logVTTargetMemberOfClusterLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2194809", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTargetNotForAvailableTargets(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2194810", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194810";
   }

   public static Loggable logTargetNotForAvailableTargetsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2194810", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNonExistingVTTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194811", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194811";
   }

   public static Loggable logNonExistingVTTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194811", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTForAvailableTargetNotFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194812", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194812";
   }

   public static Loggable logVTForAvailableTargetNotFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194812", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTNotForDefaultTarget(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194813", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194813";
   }

   public static Loggable logVTNotForDefaultTargetLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194813", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTNotAvailableForRG(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194814", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194814";
   }

   public static Loggable logVTNotAvailableForRGLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194814", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTNotAvailableTargets(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194815", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194815";
   }

   public static Loggable logVTNotAvailableTargetsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194815", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictingResourceGroupTemplate(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194816", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194816";
   }

   public static Loggable logConflictingResourceGroupTemplateLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194816", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictingStore(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2194817", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194817";
   }

   public static Loggable logConflictingStoreLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2194817", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logElementNotAnArray(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194818", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194818";
   }

   public static Loggable logElementNotAnArrayLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194818", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logVTNotAddedAsDefaultTarget(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2194819", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194819";
   }

   public static Loggable logVTNotAddedAsDefaultTargetLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2194819", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestinationAppDirExists(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194820", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194820";
   }

   public static Loggable logDestinationAppDirExistsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194820", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppDeploymentPLanNotThere(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194821", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194821";
   }

   public static Loggable logAppDeploymentPLanNotThereLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194821", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppNotThere(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194822", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194822";
   }

   public static Loggable logAppNotThereLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194822", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDestinationAppPlanExists(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194823", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194823";
   }

   public static Loggable logDestinationAppPlanExistsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194823", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logResourceExcludedFromImport(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194824", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194824";
   }

   public static Loggable logResourceExcludedFromImportLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194824", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logExistingDefaultTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194825", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194825";
   }

   public static Loggable logExistingDefaultTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194825", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJDBCStoreForJMSServer(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194826", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194826";
   }

   public static Loggable logInvalidJDBCStoreForJMSServerLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194826", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRemovingSubDeploymentTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194827", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194827";
   }

   public static Loggable logRemovingSubDeploymentTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194827", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRequiredPoliciesForPathService(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194828", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194828";
   }

   public static Loggable logRequiredPoliciesForPathServiceLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194828", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNewStoreForPathService(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2194829", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194829";
   }

   public static Loggable logNewStoreForPathServiceLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2194829", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToCreateImportReport() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2194830", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194830";
   }

   public static Loggable logFailedToCreateImportReportLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2194830", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logImportCompleted() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("2194831", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194831";
   }

   public static Loggable logImportCompletedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("2194831", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logActivationFailed(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194832", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194832";
   }

   public static Loggable logActivationFailedLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194832", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCreatingJDBCStore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194833", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194833";
   }

   public static Loggable logErrorCreatingJDBCStoreLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194833", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCStoreWithJDBCSystemResource(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194834", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194834";
   }

   public static Loggable logJDBCStoreWithJDBCSystemResourceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194834", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConversionToPartitionedUDTopic(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194835", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194835";
   }

   public static Loggable logConversionToPartitionedUDTopicLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194835", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidSubDeploymentTarget(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("2194836", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194836";
   }

   public static Loggable logInvalidSubDeploymentTargetLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("2194836", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logServerTargetedSubDeployment(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      CatalogMessage catalogMessage = new CatalogMessage("2194837", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194837";
   }

   public static Loggable logServerTargetedSubDeploymentLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
      Loggable l = new Loggable("2194837", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSubDeploymentWithoutTarget(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194838", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194838";
   }

   public static Loggable logSubDeploymentWithoutTargetLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194838", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToUndoUnActivatedChanges(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194839", 16, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194839";
   }

   public static Loggable logUnableToUndoUnActivatedChangesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194839", 16, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSServerHostingUDDInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194840", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194840";
   }

   public static Loggable logJMSServerHostingUDDInvalidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194840", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSServerHostingFSInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194841", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194841";
   }

   public static Loggable logJMSServerHostingFSInvalidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194841", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUsedTargetLibraryAvailable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2194842", 64, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194842";
   }

   public static Loggable logUsedTargetLibraryAvailableLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2194842", 64, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOverridenSubdepTargetInvalid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2194843", 8, args, DomainToPartitionLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2194843";
   }

   public static Loggable logOverridenSubdepTargetInvalidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2194843", 8, args, "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.MessageLoggerInitializer.INSTANCE.messageLogger, DomainToPartitionLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.mbeanservers.edit.internal.DomainToPartitionLogLocalizer", DomainToPartitionLogger.class.getClassLoader());
      private MessageLogger messageLogger = DomainToPartitionLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = DomainToPartitionLogger.findMessageLogger();
      }
   }
}
