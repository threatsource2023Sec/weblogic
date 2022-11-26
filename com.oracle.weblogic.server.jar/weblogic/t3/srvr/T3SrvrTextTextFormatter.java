package weblogic.t3.srvr;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class T3SrvrTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public T3SrvrTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.t3.srvr.T3SrvrTextTextLocalizer", T3SrvrTextTextFormatter.class.getClassLoader());
   }

   public T3SrvrTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.t3.srvr.T3SrvrTextTextLocalizer", T3SrvrTextTextFormatter.class.getClassLoader());
   }

   public static T3SrvrTextTextFormatter getInstance() {
      return new T3SrvrTextTextFormatter();
   }

   public static T3SrvrTextTextFormatter getInstance(Locale l) {
      return new T3SrvrTextTextFormatter(l);
   }

   public String getServerShutdownSuccessfully(String arg0) {
      String id = "ServerShutdownSuccessfully";
      String subsystem = "T3Srvr Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartupWithoutAdminChannel() {
      String id = "StartupWithoutAdminChannel";
      String subsystem = "T3Srvr Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
