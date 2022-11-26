package weblogic.management.deploy.utils;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class MBeanHomeToolTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public MBeanHomeToolTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.deploy.utils.MBeanHomeToolTextLocalizer", MBeanHomeToolTextFormatter.class.getClassLoader());
   }

   public MBeanHomeToolTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.deploy.utils.MBeanHomeToolTextLocalizer", MBeanHomeToolTextFormatter.class.getClassLoader());
   }

   public static MBeanHomeToolTextFormatter getInstance() {
      return new MBeanHomeToolTextFormatter();
   }

   public static MBeanHomeToolTextFormatter getInstance(Locale l) {
      return new MBeanHomeToolTextFormatter(l);
   }

   public String usageAdminUrl(String arg0) {
      String id = "USAGE_ADMINURL";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleAdminUrl() {
      String id = "EXAMPLE_ADMINURL";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUser() {
      String id = "USAGE_USER";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleUser() {
      String id = "EXAMPLE_USER";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePassword() {
      String id = "USAGE_PASSWORD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdPassword() {
      String id = "USAGE_AD_PASSWORD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String examplePassword() {
      String id = "EXAMPLE_PASSWORD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorOnConnect(String arg0, String arg1, String arg2) {
      String id = "ERROR_ON_CONNECT";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String promptUsername() {
      String id = "PROMPT_USERNAME";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String promptPassword(String arg0) {
      String id = "PROMPT_PASSWORD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionNoPassword() {
      String id = "EXCEPTION_NO__PASSWORD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleUserConfig() {
      String id = "EXAMPLE_USERCONFIG";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUserConfig() {
      String id = "USAGE_USERCONFIG";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleUserKey() {
      String id = "EXAMPLE_USERKEY";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUserkey() {
      String id = "USAGE_USERKEY";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageIDD() {
      String id = "USAGE_IDD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleIDD() {
      String id = "EXAMPLE_IDD";
      String subsystem = "MBeanHomeTool";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
