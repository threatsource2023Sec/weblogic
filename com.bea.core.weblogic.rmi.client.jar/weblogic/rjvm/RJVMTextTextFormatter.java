package weblogic.rjvm;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class RJVMTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public RJVMTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.rjvm.RJVMTextTextLocalizer", RJVMTextTextFormatter.class.getClassLoader());
   }

   public RJVMTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.rjvm.RJVMTextTextLocalizer", RJVMTextTextFormatter.class.getClassLoader());
   }

   public static RJVMTextTextFormatter getInstance() {
      return new RJVMTextTextFormatter();
   }

   public static RJVMTextTextFormatter getInstance(Locale l) {
      return new RJVMTextTextFormatter(l);
   }

   public String msgNoRouter() {
      String id = "msgNoRouter";
      String subsystem = "RJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRandIncorrectNumber() {
      String id = "msgRandIncorrectNumber";
      String subsystem = "RJVM";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgDestinationUnreachable(String arg0, int arg1) {
      String id = "msgDestinationUnreachable";
      String subsystem = "RJVM";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
