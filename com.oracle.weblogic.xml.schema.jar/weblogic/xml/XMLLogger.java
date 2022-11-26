package weblogic.xml;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class XMLLogger {
   private static final String LOCALIZER_CLASS = "weblogic.xml.XMLLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(XMLLogger.class.getName());
   }

   public static String logEntityCacheRejection(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130000", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130000";
   }

   public static String logEntityCacheBroken() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("130001", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130001";
   }

   public static String logEntityCacheRoundTripFailure(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130002", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130002";
   }

   public static String logXMLRegistryException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130003", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130003";
   }

   public static String logCacheRejection(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130006", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130006";
   }

   public static String logCacheMemoryPurge(int arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130007", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130007";
   }

   public static String logCacheDiskPurge(int arg0, long arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130008", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130008";
   }

   public static String logCacheDiskRejection(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130009", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130009";
   }

   public static String logCacheEntryAdd(String arg0, String arg1, long arg2, String arg3, long arg4) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      CatalogMessage catalogMessage = new CatalogMessage("130010", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130010";
   }

   public static String logCacheEntryDelete(String arg0, String arg1, long arg2, long arg3, long arg4, long arg5) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      CatalogMessage catalogMessage = new CatalogMessage("130011", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130011";
   }

   public static String logCacheEntryPersist(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("130012", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130012";
   }

   public static String logCacheEntryLoad(String arg0, String arg1, long arg2, long arg3) {
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      CatalogMessage catalogMessage = new CatalogMessage("130013", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130013";
   }

   public static String logCacheStatisticsCheckpoint() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("130014", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130014";
   }

   public static String logCacheCreation(long arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("130015", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130015";
   }

   public static String logCacheLoad(long arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("130016", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130016";
   }

   public static String logCacheClose(long arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130017", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130017";
   }

   public static String logCacheCorrupted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130018", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130018";
   }

   public static String logCacheEntryCorrupted(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130019", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130019";
   }

   public static String logCacheStatisticsCorrupted(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130020", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130020";
   }

   public static String logCacheEntrySaveError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130021", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130021";
   }

   public static String logCacheSaveError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130022", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130022";
   }

   public static String logCacheStatisticsSaveError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130023", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130023";
   }

   public static String logCacheOutOfMemoryOnEntryLoad(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130024", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130024";
   }

   public static String logCacheOutOfMemoryOnLoad(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130025", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130025";
   }

   public static String logCacheOutOfMemoryOnStatisticsLoad(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130026", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130026";
   }

   public static String logCacheMemoryWarningExceeds(long arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("130027", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130027";
   }

   public static String logCacheMemoryWarningClose(long arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("130028", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130028";
   }

   public static String logCacheUnexpectedProblem(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130029", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130029";
   }

   public static String logCacheEntryReadError(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130030", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130030";
   }

   public static String logCacheReadError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130031", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130031";
   }

   public static String logCacheStatisticsReadError(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130032", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130032";
   }

   public static String logParserConfigurationException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130033", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130033";
   }

   public static String logSAXException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130034", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130034";
   }

   public static String logIOException(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130035", 8, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130035";
   }

   public static String logIntializingXMLRegistry() {
      Object[] args = new Object[0];
      CatalogMessage catalogMessage = new CatalogMessage("130036", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130036";
   }

   public static String logStackTrace(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("130037", 64, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130037";
   }

   public static String logPropertyNotAccepted(String arg0, String arg1, String arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("130038", 128, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130038";
   }

   public static String logWarning(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("130039", 16, args, XMLLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      XMLLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "130039";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.xml.XMLLogLocalizer", XMLLogger.class.getClassLoader());
      private MessageLogger messageLogger = XMLLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = XMLLogger.findMessageLogger();
      }
   }
}
