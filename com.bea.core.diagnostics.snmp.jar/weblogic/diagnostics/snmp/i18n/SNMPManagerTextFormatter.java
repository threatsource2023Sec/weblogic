package weblogic.diagnostics.snmp.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class SNMPManagerTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public SNMPManagerTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.snmp.i18n.SNMPManagerTextLocalizer", SNMPManagerTextFormatter.class.getClassLoader());
   }

   public SNMPManagerTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.snmp.i18n.SNMPManagerTextLocalizer", SNMPManagerTextFormatter.class.getClassLoader());
   }

   public static SNMPManagerTextFormatter getInstance() {
      return new SNMPManagerTextFormatter();
   }

   public static SNMPManagerTextFormatter getInstance(Locale l) {
      return new SNMPManagerTextFormatter(l);
   }

   public String getUsageText() {
      String id = "UsageText";
      String subsystem = "SNMP";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
