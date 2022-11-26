package weblogic.buzzmessagebus;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

public class BuzzMessageBusHttpLogger {
   private static final String LOCALIZER_CLASS = "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(BuzzMessageBusHttpLogger.class.getName());
   }

   public static String logInvalidHttpHeaderLength(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2191000", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191000";
   }

   public static Loggable logInvalidHttpHeaderLengthLoggable(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2191000", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logIncompleteHttpHeader(String arg0) {
      Object[] args = new Object[]{arg0};
      CatalogMessage catalogMessage = new CatalogMessage("2191001", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191001";
   }

   public static Loggable logIncompleteHttpHeaderLoggable(String arg0) {
      Object[] args = new Object[]{arg0};
      Loggable l = new Loggable("2191001", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logInvalidFrameTypeForHttpBuzzSubprotocolHandler(String arg0, byte arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2191002", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191002";
   }

   public static Loggable logInvalidFrameTypeForHttpBuzzSubprotocolHandlerLoggable(String arg0, byte arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2191002", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHttpBuzzSubprotocolHandlerReceiptError(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2191003", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(false);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191003";
   }

   public static Loggable logHttpBuzzSubprotocolHandlerReceiptErrorLoggable(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2191003", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(false);
      l.setExcludePartition(true);
      return l;
   }

   public static String logHttpBuzzSubprotocolHandlerReceiveError(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2191004", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191004";
   }

   public static Loggable logHttpBuzzSubprotocolHandlerReceiveErrorLoggable(String arg0, Throwable arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2191004", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logCompleteMessageTimeout(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      CatalogMessage catalogMessage = new CatalogMessage("2191005", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191005";
   }

   public static Loggable logCompleteMessageTimeoutLoggable(String arg0, String arg1, long arg2) {
      Object[] args = new Object[]{arg0, arg1, arg2};
      Loggable l = new Loggable("2191005", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   public static String logUnsupportedBuzzHttpCloseYourId(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2191006", 4, args, BuzzMessageBusHttpLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2191006";
   }

   public static Loggable logUnsupportedBuzzHttpCloseYourIdLoggable(String arg0, long arg1) {
      Object[] args = new Object[]{arg0, arg1};
      Loggable l = new Loggable("2191006", 4, args, "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.MessageLoggerInitializer.INSTANCE.messageLogger, BuzzMessageBusHttpLogger.class.getClassLoader());
      l.setStackTraceEnabled(true);
      l.setExcludePartition(true);
      return l;
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.buzzmessagebus.BuzzMessageBusHttpLogLocalizer", BuzzMessageBusHttpLogger.class.getClassLoader());
      private MessageLogger messageLogger = BuzzMessageBusHttpLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = BuzzMessageBusHttpLogger.findMessageLogger();
      }
   }
}
