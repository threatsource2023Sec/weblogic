package weblogic.diagnostics.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DiagnosticsBaseTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DiagnosticsBaseTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.i18n.DiagnosticsBaseTextTextLocalizer", DiagnosticsBaseTextTextFormatter.class.getClassLoader());
   }

   public DiagnosticsBaseTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.i18n.DiagnosticsBaseTextTextLocalizer", DiagnosticsBaseTextTextFormatter.class.getClassLoader());
   }

   public static DiagnosticsBaseTextTextFormatter getInstance() {
      return new DiagnosticsBaseTextTextFormatter();
   }

   public static DiagnosticsBaseTextTextFormatter getInstance(Locale l) {
      return new DiagnosticsBaseTextTextFormatter(l);
   }

   public String getNegativeAgeValueInvalid() {
      String id = "NegativeAgeValueInvalid";
      String subsystem = "DiagnosticsBase";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidLastParam(String arg0) {
      String id = "InvalidLastParam";
      String subsystem = "DiagnosticsBase";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
