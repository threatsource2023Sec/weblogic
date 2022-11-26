package weblogic.nodemanager;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class NodeManagerRestTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public NodeManagerRestTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.nodemanager.NodeManagerRestTextLocalizer", NodeManagerRestTextFormatter.class.getClassLoader());
   }

   public NodeManagerRestTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.nodemanager.NodeManagerRestTextLocalizer", NodeManagerRestTextFormatter.class.getClassLoader());
   }

   public static NodeManagerRestTextFormatter getInstance() {
      return new NodeManagerRestTextFormatter();
   }

   public static NodeManagerRestTextFormatter getInstance(Locale l) {
      return new NodeManagerRestTextFormatter(l);
   }

   public String msgNMServerStopped() {
      String id = "msgNMServerStopped";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgContextNotInitialized() {
      String id = "msgContextNotInitialized";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgAddressInUse(String arg0, String arg1, Exception arg2) {
      String id = "msgAddressInUse";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgGrizzlyLoggerNotInitialized() {
      String id = "msgGrizzlyLoggerNotInitialized";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgAuthenticationFailed() {
      String id = "msgAuthenticationFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDomainNotRegistered(String arg0) {
      String id = "msgDomainNotRegistered";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorProcessingReq() {
      String id = "msgErrorProcessingReq";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDomainNotFound(String arg0) {
      String id = "msgDomainNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerNotRegistered(String arg0, String arg1) {
      String id = "msgServerNotRegistered";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerNotConfigured() {
      String id = "msgServerNotConfigured";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgJobNotFound(String arg0) {
      String id = "msgJobNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNMReadConfigErr() {
      String id = "msgNMReadConfigErr";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgUnknownErr() {
      String id = "msgUnknownErr";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStartingServer(String arg0) {
      String id = "msgStartingServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgKillingServer(String arg0) {
      String id = "msgKillingServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMandatoryArgumentsNull() {
      String id = "msgMandatoryArgumentsNull";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgGrizzlyReadError() {
      String id = "msgGrizzlyReadError";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMethodNotAllowed() {
      String id = "msgMethodNotAllowed";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDomainRegistrationEnabledNotAllowed() {
      String id = "msgDomainRegistrationFlagIgnored";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
