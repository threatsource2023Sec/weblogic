package weblogic.j2ee;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class J2EELogger {
   private static final String LOCALIZER_CLASS = "weblogic.j2ee.J2EELogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(J2EELogger.class.getName());
   }

   public static String logErrorDeployingApplication(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160001", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160001";
   }

   public static String logMBeanCreationFailure(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160032", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160032";
   }

   public static Loggable logMBeanCreationFailureLoggable(String arg0, String arg1, String arg2, Throwable arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160032", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToUndeployMailSession(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160039", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160039";
   }

   public static String logDeployedMailSession(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160040", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160040";
   }

   public static String logUndeployedMailSession(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160041", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160041";
   }

   public static String logDebug(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160058", 128, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160058";
   }

   public static String logErrorCheckingWebService(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160069", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160069";
   }

   public static String logInvalidEJBLinkQualificationInEJBDescriptor(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160083", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160083";
   }

   public static Loggable logInvalidEJBLinkQualificationInEJBDescriptorLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("160083", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidEJBLinkQualification(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160084", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160084";
   }

   public static Loggable logInvalidEJBLinkQualificationLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160084", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidQualifiedEJBLinkInEJBDescriptor(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("160085", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160085";
   }

   public static Loggable logInvalidQualifiedEJBLinkInEJBDescriptorLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("160085", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidQualifiedEJBLink(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160086", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160086";
   }

   public static Loggable logInvalidQualifiedEJBLinkLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("160086", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBLinkInEJBDescriptorPointsToInvalidBean(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160087", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160087";
   }

   public static Loggable logEJBLinkInEJBDescriptorPointsToInvalidBeanLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160087", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBLinkPointsToInvalidBean(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160088", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160088";
   }

   public static Loggable logEJBLinkPointsToInvalidBeanLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160088", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUnqualifiedEJBLinkInEJBDescriptor(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160089", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160089";
   }

   public static Loggable logInvalidUnqualifiedEJBLinkInEJBDescriptorLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160089", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidUnqualifiedEJBLink(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160090", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160090";
   }

   public static Loggable logInvalidUnqualifiedEJBLinkLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160090", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAmbiguousEJBLinkInEJBDescriptor(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160091", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160091";
   }

   public static Loggable logAmbiguousEJBLinkInEJBDescriptorLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("160091", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAmbiguousEJBLink(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160092", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160092";
   }

   public static Loggable logAmbiguousEJBLinkLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160092", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncorrectInterfacesForEJBRefTypeInEJBDescriptor(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("160093", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160093";
   }

   public static Loggable logIncorrectInterfacesForEJBRefTypeInEJBDescriptorLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("160093", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncorrectInterfacesForEJBRefType(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160094", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160094";
   }

   public static Loggable logIncorrectInterfacesForEJBRefTypeLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("160094", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncorrectInterfaceNameForEJBRefInEJBDescriptor(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("160095", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160095";
   }

   public static String logIncorrectInterfaceNameForEJBRef(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160096", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160096";
   }

   public static String logDescriptorUsesInvalidEncoding(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160098", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160098";
   }

   public static Loggable logDescriptorUsesInvalidEncodingLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160098", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotDeployRole(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160100", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160100";
   }

   public static Loggable logCouldNotDeployRoleLoggable(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160100", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToResolveEJBLink(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160101", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160101";
   }

   public static Loggable logUnableToResolveEJBLinkLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160101", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEjbLocalRefNotVisible(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160104", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160104";
   }

   public static String logAppcSourceArgDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160106", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160106";
   }

   public static Loggable logAppcSourceArgDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160106", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcSourceFileNotAccessible(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160108", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160108";
   }

   public static Loggable logAppcSourceFileNotAccessibleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160108", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcErrorCopyingFiles(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160109", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160109";
   }

   public static Loggable logAppcErrorCopyingFilesLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160109", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcCouldNotCreateDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160110", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160110";
   }

   public static Loggable logAppcCouldNotCreateDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160110", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcCanNotWriteToDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160111", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160111";
   }

   public static Loggable logAppcCanNotWriteToDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160111", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcNoValidModuleFoundInDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160112", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160112";
   }

   public static Loggable logAppcNoValidModuleFoundInDirectoryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160112", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcJarNotValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160113", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160113";
   }

   public static Loggable logAppcJarNotValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160113", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcWarNotValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160114", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160114";
   }

   public static Loggable logAppcWarNotValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160114", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcEarNotValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160115", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160115";
   }

   public static Loggable logAppcEarNotValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160115", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnableToContinueProcessingFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160117", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160117";
   }

   public static Loggable logAppcUnableToContinueProcessingFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160117", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcErrorAccessingFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160118", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160118";
   }

   public static Loggable logAppcErrorAccessingFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160118", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcErrorProcessingFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160119", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160119";
   }

   public static Loggable logAppcErrorProcessingFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160119", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcCantFindDeclaredModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160120", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160120";
   }

   public static Loggable logAppcCantFindDeclaredModuleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160120", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcErrorsEncounteredCompilingModule(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160121", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160121";
   }

   public static Loggable logAppcErrorsEncounteredCompilingModuleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160121", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcErrorsValidatingEar(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160122", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160122";
   }

   public static Loggable logAppcErrorsValidatingEarLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160122", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnableToCreateOutputArchiveRestore(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160123", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160123";
   }

   public static Loggable logAppcUnableToCreateOutputArchiveRestoreLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160123", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnableToCreateOutputArchive(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160124", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160124";
   }

   public static Loggable logAppcUnableToCreateOutputArchiveLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160124", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnableToDeleteBackupArchive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160125", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160125";
   }

   public static Loggable logAppcUnableToDeleteBackupArchiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160125", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnableToCreateBackupArchive(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160126", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160126";
   }

   public static Loggable logAppcUnableToCreateBackupArchiveLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160126", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnableToDeleteArchive(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160127", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160127";
   }

   public static Loggable logAppcUnableToDeleteArchiveLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160127", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logTooManyArgsForAppc() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160128", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160128";
   }

   public static Loggable logTooManyArgsForAppcLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160128", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcMissingModuleAltDDFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160129", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160129";
   }

   public static Loggable logAppcMissingModuleAltDDFileLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160129", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcMissingApplicationAltDDFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160130", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160130";
   }

   public static Loggable logAppcMissingApplicationAltDDFileLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160130", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncorrectRefTypeForEJBRefInEJBDescriptor(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160132", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160132";
   }

   public static String logIncorrectRefTypeForEJBRef(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160133", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160133";
   }

   public static String logInvalidEntityCacheRefDeclared(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160134", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160134";
   }

   public static Loggable logInvalidEntityCacheRefDeclaredLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160134", 16, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToResolveMessageDestinationLink(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160137", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160137";
   }

   public static Loggable logUnableToResolveMessageDestinationLinkLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160137", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCouldNotSetAppActiveVersionState(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160138", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160138";
   }

   public static Loggable logCouldNotSetAppActiveVersionStateLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160138", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logContextPathSetForNonWarLibRef(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160139", 32, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160139";
   }

   public static String logUnresolvedOptionalPackages(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160140", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160140";
   }

   public static String logLibraryInitError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160141", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160141";
   }

   public static Loggable logLibraryInitErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160141", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryWithIllegalSpecVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160142", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160142";
   }

   public static Loggable logLibraryWithIllegalSpecVersionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160142", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryWithIllegalMBeanSpecVersion(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160143", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160143";
   }

   public static Loggable logLibraryWithIllegalMBeanSpecVersionLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160143", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryRegistrationError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160144", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160144";
   }

   public static Loggable logLibraryRegistrationErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160144", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryInfoMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160145", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160145";
   }

   public static Loggable logLibraryInfoMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160145", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcLibraryInfoMismatch(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160146", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160146";
   }

   public static Loggable logAppcLibraryInfoMismatchLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160146", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcLibraryRegistrationFailed() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160147", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160147";
   }

   public static Loggable logAppcLibraryRegistrationFailedLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160147", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcUnreferencedLibraries(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160148", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160148";
   }

   public static String logUnresolvedLibraryReferences(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160149", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160149";
   }

   public static Loggable logUnresolvedLibraryReferencesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160149", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIllegalAppLibSpecVersionRef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160150", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160150";
   }

   public static Loggable logIllegalAppLibSpecVersionRefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160150", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logRegisteredLibrary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160151", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160151";
   }

   public static String logUnknownLibraryType(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160152", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160152";
   }

   public static Loggable logUnknownLibraryTypeLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160152", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryCleanupWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160153", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160153";
   }

   public static String logLibraryCleanupError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160154", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160154";
   }

   public static Loggable logLibraryCleanupErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160154", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFindLibrary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160155", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160155";
   }

   public static Loggable logCannotFindLibraryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160155", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotProcessLibdir(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160156", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160156";
   }

   public static Loggable logCannotProcessLibdirLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160156", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logLibraryIsNotAppLibrary(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160157", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160157";
   }

   public static Loggable logLibraryIsNotAppLibraryLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160157", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCannotFindExtensionNameWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160158", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160158";
   }

   public static String logIllegalOptPackSpecVersionRefWarning(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160159", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160159";
   }

   public static String logAppcErrorParsingEARDescriptors(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160161", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160161";
   }

   public static Loggable logAppcErrorParsingEARDescriptorsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160161", 16, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcNoApplicationDDFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160162", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160162";
   }

   public static Loggable logAppcNoApplicationDDFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160162", 16, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcNoModulesFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160163", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160163";
   }

   public static Loggable logAppcNoModulesFoundLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160163", 16, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorImportingLibrary(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160164", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160164";
   }

   public static Loggable logErrorImportingLibraryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160164", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSplitDirNotSupportedForLibraries(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160165", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160165";
   }

   public static Loggable logSplitDirNotSupportedForLibrariesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160165", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logValidPlanMerged(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160166", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160166";
   }

   public static String logNoEJBDeploymentsFoundForLinkRef(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160167", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160167";
   }

   public static String logDescriptorMergeError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160168", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160168";
   }

   public static Loggable logDescriptorMergeErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160168", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDescriptorParseError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160169", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160169";
   }

   public static String logLibraryImport(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160170", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160170";
   }

   public static String logAppcPlanArgDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160171", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160171";
   }

   public static Loggable logAppcPlanArgDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160171", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcPlanArgWrongType() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160172", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160172";
   }

   public static Loggable logAppcPlanArgWrongTypeLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160172", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcPlanFileNotAccessible(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160173", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160173";
   }

   public static Loggable logAppcPlanFileNotAccessibleLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160173", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAppcPlanParseError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160174", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160174";
   }

   public static Loggable logAppcPlanParseErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160174", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJMSResourceLinkInJ2EEComponent(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160175", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160175";
   }

   public static Loggable logInvalidJMSResourceLinkInJ2EEComponentLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160175", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJMSResourceSpecifiedInResourceLinkNotFound(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("160176", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160176";
   }

   public static Loggable logJMSResourceSpecifiedInResourceLinkNotFoundLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("160176", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidApplication(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160177", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160177";
   }

   public static Loggable logInvalidApplicationLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160177", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoringRollbackUpdateError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160180", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160180";
   }

   public static String logIgnoringUndeploymentError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160181", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160181";
   }

   public static String logIgnoringAdminModeErrro(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160182", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160182";
   }

   public static String logUnabletoFindLifecycleJar(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160183", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160183";
   }

   public static Loggable logUnabletoFindLifecycleJarLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160183", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidJDBCResourceLinkInJ2EEComponent(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160184", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160184";
   }

   public static Loggable logInvalidJDBCResourceLinkInJ2EEComponentLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160184", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logJDBCResourceSpecifiedInResourceLinkNotFound(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("160185", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160185";
   }

   public static Loggable logJDBCResourceSpecifiedInResourceLinkNotFoundLoggable(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      Loggable l = new Loggable("160185", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompilingEarModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160186", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160186";
   }

   public static String logAppcFailedWithError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160187", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160187";
   }

   public static String logUnresolvedLibraryReferencesWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160188", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160188";
   }

   public static Loggable logUnresolvedLibraryReferencesWarningLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160188", 16, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUrisDidntMatchModules(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160189", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160189";
   }

   public static Loggable logUrisDidntMatchModulesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160189", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDeprecatedWeblogicParam(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160191", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160191";
   }

   public static String logUnknownWeblogicParam(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160192", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160192";
   }

   public static String logAppcRarNotValid(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160193", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160193";
   }

   public static Loggable logAppcRarNotValidLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160193", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logModulesAlreadyRunningError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160194", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160194";
   }

   public static Loggable logModulesAlreadyRunningErrorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160194", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIgnoreAppVersionListenerForNonVersionApp(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160195", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160195";
   }

   public static String logUnabletoFindSingletonJar(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160196", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160196";
   }

   public static Loggable logUnabletoFindSingletonJarLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160196", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToValidateDescriptor(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160197", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160197";
   }

   public static String logIncorrectInterfaceForEJBAnnotationTarget(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160198", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160198";
   }

   public static Loggable logIncorrectInterfaceForEJBAnnotationTargetLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160198", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToAutoLinkEjbRefMultipleInterfaces(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("160199", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160199";
   }

   public static Loggable logFailedToAutoLinkEjbRefMultipleInterfacesLoggable(String arg0, String arg1, String arg2, String arg3, String arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      Loggable l = new Loggable("160199", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logFailedToAutoLinkEjbRefNoMatches(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160200", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160200";
   }

   public static Loggable logFailedToAutoLinkEjbRefNoMatchesLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160200", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEJBRefTargetDoesNotImplementInterface(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("160201", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160201";
   }

   public static Loggable logEJBRefTargetDoesNotImplementInterfaceLoggable(String arg0, String arg1, String arg2, String arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      Loggable l = new Loggable("160201", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPersistenceUnitLogConfigurationSpecified(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160202", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160202";
   }

   public static String logPersistenceUnitIdPropertySpecified(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160203", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160203";
   }

   public static String logRunAsPrincipalNotFound(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160204", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160204";
   }

   public static Loggable logRunAsPrincipalNotFoundLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160204", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logAttemptToBumpUpPrivilegesWithRunAs(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160205", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160205";
   }

   public static Loggable logAttemptToBumpUpPrivilegesWithRunAsLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160205", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOpenJPAPersistenceUnitUsesApplicationJars(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160206", 128, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160206";
   }

   public static String logFailedToCreateEjbRefMultipleInterfaces(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160207", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160207";
   }

   public static Loggable logFailedToCreateEjbRefMultipleInterfacesLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160207", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logModuleUriDoesNotExist(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160210", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160210";
   }

   public static Loggable logModuleUriDoesNotExistLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160210", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logPendingWorkInQueues(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160211", 32, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160211";
   }

   public static String logInternalError(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160212", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160212";
   }

   public static Loggable logInternalErrorLoggable(Throwable arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160212", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logReadmeContent() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160213", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160213";
   }

   public static Loggable logReadmeContentLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160213", 64, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logOutputLocationExists(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160214", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160214";
   }

   public static String logNotWebModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160215", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160215";
   }

   public static Loggable logNotWebModuleLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160215", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logSendDeploymentEventError(String arg0, String arg1, Throwable arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160216", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160216";
   }

   public static String logSendVetoableDeployEventError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160217", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160217";
   }

   public static String logFilteringConfigurationIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160218", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160218";
   }

   public static String logUsingDefaultPersistenceProvider(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160219", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160219";
   }

   public static String logCompilationComplete() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160220", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160220";
   }

   public static String logCharEnvEntryHasLengthZero() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160221", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160221";
   }

   public static Loggable logCharEnvEntryHasLengthZeroLoggable() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160221", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnableToCreateJMSConnectionFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160222", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160222";
   }

   public static String logNoJNDIForResourceEnvRef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160223", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160223";
   }

   public static Loggable logNoJNDIForResourceEnvRefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160223", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logErrorCleaningReferences(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160224", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160224";
   }

   public static String getJPAUnsupportedOperationMsg() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160225", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logWarningPersistenceConfigurationFileIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160226", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160226";
   }

   public static String logWarningPersistenceUnitConfigurationIgnored(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160227", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160227";
   }

   public static String logAppMergeFailedWithError() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("160228", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160228";
   }

   public static String logIgnoringPlanFile(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160229", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160229";
   }

   public static String logUnidentifiedApplication(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160231", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160231";
   }

   public static String logCreatedWorkingDirectory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160230", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160230";
   }

   public static String logUnidentifiedWLModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160232", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160232";
   }

   public static String logUnidentifiedModule(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160233", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160233";
   }

   public static String logClassLoadFailedWhenLookupHandleTypeImplementations(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160237", 128, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160237";
   }

   public static String logMissingMessageDestinationDescriptor(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160238", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160238";
   }

   public static Loggable logMissingMessageDestinationDescriptorLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160238", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logConflictedEnvEntry(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160239", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160239";
   }

   public static Loggable logConflictedEnvEntryLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160239", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logNoJNDIForResourceRef(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160240", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160240";
   }

   public static Loggable logNoJNDIForResourceRefLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160240", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logEnvEntriesValidationErrors(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160241", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160241";
   }

   public static Loggable logEnvEntriesValidationErrorsLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("160241", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDatasourceOverwrittenWarning(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160242", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160242";
   }

   public static String getTransRequiredForProcedureQueryMsg() {
      Object[] args = new Object[0];
      Loggable l = new Loggable("160243", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l.getMessage();
   }

   public static String logInvalidNameSpace(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160244", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160244";
   }

   public static Loggable logInvalidNameSpaceLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160244", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateEnvEntryValue(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160245", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160245";
   }

   public static Loggable logDuplicateEnvEntryValueLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160245", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDuplicateInjectionTargetWarning(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160246", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160246";
   }

   public static String logRealmNameInDDIgnoredWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160247", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160247";
   }

   public static String logUnableToParseClassFile(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160248", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160248";
   }

   public static String logSkipSchemaGenerationForServerStartup(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160249", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160249";
   }

   public static String logComponentMBeanFactoryNotRegisteredAsDeploymentFactory(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160250", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160250";
   }

   public static String logComponentMBeanFactoryInstantiationFailed(String arg0, Exception arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160251", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160251";
   }

   public static String logEnvEntryInconsistenciesDetected(String arg0, RuntimeException arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160252", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160252";
   }

   public static String logMultipleEnvEntryReport(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160253", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160253";
   }

   public static String logApplicationCacheFileReadingException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160254", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160254";
   }

   public static String logApplicationCacheFileWritingException(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160255", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160255";
   }

   public static String logApplicationCacheFileObjectStreamClosureException(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160256", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160256";
   }

   public static String logAppNameAlreadyExistsInDeploymentScope(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160257", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160257";
   }

   public static Loggable logAppNameAlreadyExistsInDeploymentScopeLoggable(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("160257", 8, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logDirectoryExpected(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160258", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160258";
   }

   public static String logDotDecimalNameExpected(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("160259", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160259";
   }

   public static String logNoValidVersionFound(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160260", 8, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160260";
   }

   public static String logExceptionInMultiVersionFileCreation(String arg0, String arg1, Exception arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("160261", 16, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160261";
   }

   public static String logApplicationHashVersion(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160262", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160262";
   }

   public static Loggable logApplicationHashVersionLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160262", 64, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logApplicationIncludedFiles(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("160263", 64, args, J2EELogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "160263";
   }

   public static Loggable logApplicationIncludedFilesLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("160263", 64, args, "weblogic.j2ee.J2EELogLocalizer", J2EELogger.MessageLoggerInitializer.INSTANCE.messageLogger, J2EELogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.j2ee.J2EELogLocalizer", J2EELogger.class.getClassLoader());
      private MessageLogger messageLogger = J2EELogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = J2EELogger.findMessageLogger();
      }
   }
}
