package weblogic.management.provider.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class CAMConfigTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public CAMConfigTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.provider.internal.CAMConfigTextLocalizer", CAMConfigTextFormatter.class.getClassLoader());
   }

   public CAMConfigTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.provider.internal.CAMConfigTextLocalizer", CAMConfigTextFormatter.class.getClassLoader());
   }

   public static CAMConfigTextFormatter getInstance() {
      return new CAMConfigTextFormatter();
   }

   public static CAMConfigTextFormatter getInstance(Locale l) {
      return new CAMConfigTextFormatter(l);
   }

   public String nodeManagerNotAvailOnMachine(String arg0) {
      String id = "nodeManagerNotAvailOnMachine";
      String subsystem = "CAMConfiguration";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
