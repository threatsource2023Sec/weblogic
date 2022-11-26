package weblogic.entitlement.rules;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class PredicateTextFormatter extends BaseTextFormatter {
   private weblogic.i18n.Localizer l10n;

   public PredicateTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.entitlement.rules.PredicateTextLocalizer", PredicateTextFormatter.class.getClassLoader());
   }

   public PredicateTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.entitlement.rules.PredicateTextLocalizer", PredicateTextFormatter.class.getClassLoader());
   }

   public static PredicateTextFormatter getInstance() {
      return new PredicateTextFormatter();
   }

   public static PredicateTextFormatter getInstance(Locale l) {
      return new PredicateTextFormatter(l);
   }

   public String getInvalidDayOfWeekMessage(String arg0) {
      String id = "InvalidDayOfWeek";
      String subsystem = "Security";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidArgumentTypeMessage(String arg0) {
      String id = "InvalidArgumentType";
      String subsystem = "Security";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidAttributeNameMessage(String arg0) {
      String id = "InvalidAttributeName";
      String subsystem = "Security";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAM_PM(String arg0) {
      String id = "AM_PM";
      String subsystem = "Security";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
