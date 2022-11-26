package weblogic.common.resourcepool;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class CommonTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public CommonTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.common.resourcepool.CommonTextTextLocalizer", CommonTextTextFormatter.class.getClassLoader());
   }

   public CommonTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.common.resourcepool.CommonTextTextLocalizer", CommonTextTextFormatter.class.getClassLoader());
   }

   public static CommonTextTextFormatter getInstance() {
      return new CommonTextTextFormatter();
   }

   public static CommonTextTextFormatter getInstance(Locale l) {
      return new CommonTextTextFormatter(l);
   }

   public String stateRunning() {
      String id = "stateRunning";
      String subsystem = "Common";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateSuspended() {
      String id = "stateSuspended";
      String subsystem = "Common";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateShutdown() {
      String id = "stateShutdown";
      String subsystem = "Common";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateUnknown() {
      String id = "stateUnknown";
      String subsystem = "Common";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateUnhealthy() {
      String id = "stateUnhealthy";
      String subsystem = "Common";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerShutdownSuccessfully(String arg0) {
      String id = "ServerShutdownSuccessfully";
      String subsystem = "Common";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
