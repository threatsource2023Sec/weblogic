package weblogic.servlet;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class HTTPTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public HTTPTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.servlet.HTTPTextTextLocalizer", HTTPTextTextFormatter.class.getClassLoader());
   }

   public HTTPTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.servlet.HTTPTextTextLocalizer", HTTPTextTextFormatter.class.getClassLoader());
   }

   public static HTTPTextTextFormatter getInstance() {
      return new HTTPTextTextFormatter();
   }

   public static HTTPTextTextFormatter getInstance(Locale l) {
      return new HTTPTextTextFormatter(l);
   }

   public String getRefreshPageHTML(String arg0, String arg1) {
      String id = "getRefreshPageHTML";
      String subsystem = "HTTP";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
