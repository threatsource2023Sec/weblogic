package weblogic.j2ee.descriptor.wl;

import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18ntools.L10nLookup;

public class WebLogicJavaEEDescriptorValidatorLogger {
   private static final String LOCALIZER_CLASS = "weblogic.j2ee.descriptor.wl.WebLogicJavaEEDescriptorValidatorLogLocalizer";

   private static MessageLogger findMessageLogger() {
      return MessageLoggerRegistry.findMessageLogger(WebLogicJavaEEDescriptorValidatorLogger.class.getName());
   }

   public static String logIllegalNegativeValue(String arg0, int arg1) {
      Object[] args = new Object[]{arg0, arg1};
      CatalogMessage catalogMessage = new CatalogMessage("2156250", 16, args, WebLogicJavaEEDescriptorValidatorLogger.MessageLoggerInitializer.LOCALIZER);
      catalogMessage.setStackTraceEnabled(true);
      catalogMessage.setDiagnosticVolume("Off");
      catalogMessage.setExcludePartition(true);
      WebLogicJavaEEDescriptorValidatorLogger.MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);
      return "2156250";
   }

   private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {
      private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
      private static final Localizer LOCALIZER = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.j2ee.descriptor.wl.WebLogicJavaEEDescriptorValidatorLogLocalizer", WebLogicJavaEEDescriptorValidatorLogger.class.getClassLoader());
      private MessageLogger messageLogger = WebLogicJavaEEDescriptorValidatorLogger.findMessageLogger();

      private MessageLoggerInitializer() {
         MessageLoggerRegistry.addMessageLoggerRegistryListener(this);
      }

      public void messageLoggerRegistryUpdated() {
         this.messageLogger = WebLogicJavaEEDescriptorValidatorLogger.findMessageLogger();
      }
   }
}
