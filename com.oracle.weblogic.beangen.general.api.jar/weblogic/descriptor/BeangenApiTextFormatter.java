package weblogic.descriptor;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class BeangenApiTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public BeangenApiTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.descriptor.BeangenApiTextLocalizer", BeangenApiTextFormatter.class.getClassLoader());
   }

   public BeangenApiTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.descriptor.BeangenApiTextLocalizer", BeangenApiTextFormatter.class.getClassLoader());
   }

   public static BeangenApiTextFormatter getInstance() {
      return new BeangenApiTextFormatter();
   }

   public static BeangenApiTextFormatter getInstance(Locale l) {
      return new BeangenApiTextFormatter(l);
   }

   public String getBeanAlreadyExistsString(String arg0) {
      String id = "BeanAlreadyExistsString";
      String subsystem = "BeangenApi";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanFailuresOccurredString() {
      String id = "BeanFailuresOccurredString";
      String subsystem = "BeangenApi";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
