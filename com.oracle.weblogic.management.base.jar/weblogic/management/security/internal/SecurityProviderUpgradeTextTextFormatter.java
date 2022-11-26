package weblogic.management.security.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class SecurityProviderUpgradeTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public SecurityProviderUpgradeTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.security.internal.SecurityProviderUpgradeTextTextLocalizer", SecurityProviderUpgradeTextTextFormatter.class.getClassLoader());
   }

   public SecurityProviderUpgradeTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.security.internal.SecurityProviderUpgradeTextTextLocalizer", SecurityProviderUpgradeTextTextFormatter.class.getClassLoader());
   }

   public static SecurityProviderUpgradeTextTextFormatter getInstance() {
      return new SecurityProviderUpgradeTextTextFormatter();
   }

   public static SecurityProviderUpgradeTextTextFormatter getInstance(Locale l) {
      return new SecurityProviderUpgradeTextTextFormatter(l);
   }

   public String NoJarsUpgraded() {
      String id = "NoJarsUpgraded";
      String subsystem = "SecurityProviderUpgrade";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String completedUpgradeOf(int arg0) {
      String id = "completedUpgradeOf";
      String subsystem = "SecurityProviderUpgrade";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String NoMDFs(String arg0) {
      String id = "NoMDFs";
      String subsystem = "SecurityProviderUpgrade";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String createdNew(String arg0, String arg1) {
      String id = "createdNew";
      String subsystem = "SecurityProviderUpgrade";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String started() {
      String id = "started";
      String subsystem = "SecurityProviderUpgrade";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
