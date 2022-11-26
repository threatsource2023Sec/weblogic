package weblogic.management.mbeanservers.edit.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class EditConfigTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public EditConfigTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.mbeanservers.edit.internal.EditConfigTextLocalizer", EditConfigTextFormatter.class.getClassLoader());
   }

   public EditConfigTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.mbeanservers.edit.internal.EditConfigTextLocalizer", EditConfigTextFormatter.class.getClassLoader());
   }

   public static EditConfigTextFormatter getInstance() {
      return new EditConfigTextFormatter();
   }

   public static EditConfigTextFormatter getInstance(Locale l) {
      return new EditConfigTextFormatter(l);
   }

   public String callerHasNotStartedEditSession() {
      String id = "callerHasNotStartedEditSession";
      String subsystem = "EditConfig";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
