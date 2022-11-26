package weblogic.i18ntools;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;

public class CatInfoTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public CatInfoTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.i18ntools.CatInfoTextLocalizer", CatInfoTextFormatter.class.getClassLoader());
   }

   public CatInfoTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.i18ntools.CatInfoTextLocalizer", CatInfoTextFormatter.class.getClassLoader());
   }

   public static CatInfoTextFormatter getInstance() {
      return new CatInfoTextFormatter();
   }

   public static CatInfoTextFormatter getInstance(Locale l) {
      return new CatInfoTextFormatter(l);
   }

   public String helpOption() {
      String id = "HELP_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String verboseOption() {
      String id = "VERBOSE_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String retiredOption() {
      String id = "RETIRED_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String detailOption() {
      String id = "DETAIL_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nodetailOption() {
      String id = "NO_DETAIL_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String idOption() {
      String id = "ID_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String subsystemOption() {
      String id = "SUBSYSTEM_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String langOption() {
      String id = "LANG_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String countryOption() {
      String id = "COUNTRY_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String variantOption() {
      String id = "VARIANT_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String setLocale(String arg0) {
      String id = "SET_LOCALE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usingLocale(String arg0) {
      String id = "USING_LOCALE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidId(String arg0) {
      String id = "INVALID_ID";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String lookupKey(String arg0) {
      String id = "LOOKUP_KEY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String gettingBody(String arg0) {
      String id = "GETTING_BODY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noInfo(String arg0) {
      String id = "NO_INFO";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String version(String arg0) {
      String id = "VERSION";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String l10nPackage(String arg0) {
      String id = "L10N_PACKAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String i18nPackage(String arg0) {
      String id = "I18N_PACKAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String subsystem(String arg0) {
      String id = "SUBSYSTEM";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String severity(int arg0) {
      String id = "SEVERITY";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String severity(String arg0) {
      String id = "SEVERITY_STRING";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stackTrace(boolean arg0) {
      String id = "STACK_TRACE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String gettingDetail(String arg0) {
      String id = "GETTING_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDetail(String arg0) {
      String id = "MESSAGE_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cause(String arg0) {
      String id = "CAUSE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String action(String arg0) {
      String id = "ACTION";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noDetail(String arg0) {
      String id = "NO_DETAIL";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noSuchMessage(String arg0) {
      String id = "NO_SUCH_MESSAGE";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noId() {
      String id = "NO_ID";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String retiredMessage(String arg0, String arg1) {
      String id = "RETIRED";
      String subsystem = "I18N";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noText() {
      String id = "NO_TEXT";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String debugOption() {
      String id = "DEBUG_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String subsystemsOption() {
      String id = "SUBSYSTEMS_OPTION";
      String subsystem = "I18N";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
