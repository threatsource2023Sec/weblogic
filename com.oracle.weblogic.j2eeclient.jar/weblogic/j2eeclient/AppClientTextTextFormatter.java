package weblogic.j2eeclient;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class AppClientTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public AppClientTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.j2eeclient.AppClientTextTextLocalizer", AppClientTextTextFormatter.class.getClassLoader());
   }

   public AppClientTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.j2eeclient.AppClientTextTextLocalizer", AppClientTextTextFormatter.class.getClassLoader());
   }

   public static AppClientTextTextFormatter getInstance() {
      return new AppClientTextTextFormatter();
   }

   public static AppClientTextTextFormatter getInstance(Locale l) {
      return new AppClientTextTextFormatter(l);
   }

   public String wordContaining() {
      String id = "wordContaining";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String wordExample() {
      String id = "wordExample";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String clientNameMeaning() {
      String id = "clientNameMeaning";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String serverNameMeaning() {
      String id = "serverNameMeaning";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDestinationNotFoundWithLink(String arg0, String arg1, String arg2, String arg3) {
      String id = "messageDestinationNotFoundWithLink";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDestinationDescriptorNotFoundWithName(String arg0, String arg1, String arg2, String arg3) {
      String id = "messageDestinationDescriptorNotFoundWithName";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDestinationDescriptorNotFoundWithRef(String arg0, String arg1, String arg2, String arg3) {
      String id = "messageDestinationDescriptorNotFoundWithRef";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String persistentInitAvailableInScope(String arg0) {
      String id = "persistentInitAvailableInScope";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String persistentCtxWithoutInjectionTgt() {
      String id = "persistentCtxWithoutInjectionTgt";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String persistentCtxWithManyInjectionTgts() {
      String id = "persistentCtxWithManyInjectionTgts";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToCreateEnvEntry(String arg0, String arg1, Throwable arg2) {
      String id = "unableToCreateEnvEntry";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String envEntryWithNoValueOrTgt(String arg0) {
      String id = "envEntryWithNoValueOrTgt";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingJndiNameForTag(String arg0, String arg1) {
      String id = "missingJndiNameForTag";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingJndiNameForTags(String arg0, String arg1, String arg2, String arg3) {
      String id = "missingJndiNameForTags";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String fileNotFound(String arg0) {
      String id = "fileNotFound";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String keystoreHasNoAlias() {
      String id = "keystoreHasNoAlias";
      String subsystem = "Application Client";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noCertificateForAlias(String arg0) {
      String id = "noCertificateForAlias";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noKeyFoundForAlias(String arg0) {
      String id = "noKeyFoundForAlias";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String clientJarMissingManifest(String arg0) {
      String id = "clientJarMissingManifest";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingMainAttribute(String arg0, String arg1) {
      String id = "missingMainAttribute";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String callbackClassNotFound(String arg0) {
      String id = "callbackClassNotFound";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToCleanupManagedBeans(Exception arg0) {
      String id = "unableToCleanupManagedBeans";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String mainClassNotFound(String arg0) {
      String id = "mainClassNotFound";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String methodNotFound(String arg0, String arg1, String arg2, String arg3) {
      String id = "methodNotFound";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableToUnbindDatasources(Exception arg0) {
      String id = "unableToUnbindDatasources";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String methodShouldBePublic(String arg0, String arg1) {
      String id = "methodShouldBePublic";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String activeApplicationNotFoundOnServer(String arg0, String arg1) {
      String id = "activeApplicationNotFoundOnServer";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noActiveServerApplicationFound(String arg0) {
      String id = "noActiveServerApplicationFound";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noApplicationFoundOnServer(String arg0) {
      String id = "noApplicationFoundOnServer";
      String subsystem = "Application Client";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
