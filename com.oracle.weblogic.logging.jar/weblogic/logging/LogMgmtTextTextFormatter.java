package weblogic.logging;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class LogMgmtTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public LogMgmtTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.logging.LogMgmtTextTextLocalizer", LogMgmtTextTextFormatter.class.getClassLoader());
   }

   public LogMgmtTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.logging.LogMgmtTextTextLocalizer", LogMgmtTextTextFormatter.class.getClassLoader());
   }

   public static LogMgmtTextTextFormatter getInstance() {
      return new LogMgmtTextTextFormatter();
   }

   public static LogMgmtTextTextFormatter getInstance(Locale l) {
      return new LogMgmtTextTextFormatter(l);
   }

   public String getHandlerErrorMsg(String arg0, String arg1) {
      String id = "HandlerErrorMsg";
      String subsystem = "LogMgmtText";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHandlerClosingMsg(String arg0) {
      String id = "HandlerClosingMsg";
      String subsystem = "LogMgmtText";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
