package weblogic.diagnostics.descriptor.validation;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class WLDFDescriptorValidationTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public WLDFDescriptorValidationTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.descriptor.validation.WLDFDescriptorValidationTextTextLocalizer", WLDFDescriptorValidationTextTextFormatter.class.getClassLoader());
   }

   public WLDFDescriptorValidationTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.descriptor.validation.WLDFDescriptorValidationTextTextLocalizer", WLDFDescriptorValidationTextTextFormatter.class.getClassLoader());
   }

   public static WLDFDescriptorValidationTextTextFormatter getInstance() {
      return new WLDFDescriptorValidationTextTextFormatter();
   }

   public static WLDFDescriptorValidationTextTextFormatter getInstance(Locale l) {
      return new WLDFDescriptorValidationTextTextFormatter(l);
   }

   public String getRestEmptyUserNameorPasswordMsg() {
      String id = "restEmptyUserNameorPassword";
      String subsystem = "WLDFDescriptorValidation";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestEmptyCustoNotificationKeyOrValueMsg() {
      String id = "restEmptyCustoNotificationKeyOrValueMsg";
      String subsystem = "WLDFDescriptorValidation";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalThreadDumpTimeoutText(String arg0, int arg1, int arg2) {
      String id = "IllegalThreadDumpTimeoutText";
      String subsystem = "WLDFDescriptorValidation";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalActionInPartitionScopeText(String arg0, String arg1) {
      String id = "IllegalActionInPartitionScopeText";
      String subsystem = "WLDFDescriptorValidation";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
