package weblogic.nodemanager;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class NMPluginTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public NMPluginTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.nodemanager.NMPluginTextLocalizer", NMPluginTextFormatter.class.getClassLoader());
   }

   public NMPluginTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.nodemanager.NMPluginTextLocalizer", NMPluginTextFormatter.class.getClassLoader());
   }

   public static NMPluginTextFormatter getInstance() {
      return new NMPluginTextFormatter();
   }

   public static NMPluginTextFormatter getInstance(Locale l) {
      return new NMPluginTextFormatter(l);
   }

   public String msgProcessAlreadyStarted() {
      String id = "processAlreadyStarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgProcessNotStarted(String arg0) {
      String id = "processNotStarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
