package weblogic.descriptor.beangen;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class BeangenBeangenTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public BeangenBeangenTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.descriptor.beangen.BeangenBeangenTextLocalizer", BeangenBeangenTextFormatter.class.getClassLoader());
   }

   public BeangenBeangenTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.descriptor.beangen.BeangenBeangenTextLocalizer", BeangenBeangenTextFormatter.class.getClassLoader());
   }

   public static BeangenBeangenTextFormatter getInstance() {
      return new BeangenBeangenTextFormatter();
   }

   public static BeangenBeangenTextFormatter getInstance(Locale l) {
      return new BeangenBeangenTextFormatter(l);
   }

   public String getCheckInEnumString(String arg0, String arg1, String arg2) {
      String id = "CheckInEnumString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckInRangeString(String arg0, String arg1, String arg2, String arg3) {
      String id = "CheckInRangeString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckNonNullString(String arg0) {
      String id = "CheckNonNullString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckNonEmptyStringString(String arg0) {
      String id = "CheckNonEmptyString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVoidCheckIsSetStringString(String arg0) {
      String id = "CheckIsSetString";
      String subsystem = "BeangenDescriptor";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
