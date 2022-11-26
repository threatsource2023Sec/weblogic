package weblogic.nodemanager;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class NodeManagerCommonTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public NodeManagerCommonTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.nodemanager.NodeManagerCommonTextLocalizer", NodeManagerCommonTextFormatter.class.getClassLoader());
   }

   public NodeManagerCommonTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.nodemanager.NodeManagerCommonTextLocalizer", NodeManagerCommonTextFormatter.class.getClassLoader());
   }

   public static NodeManagerCommonTextFormatter getInstance() {
      return new NodeManagerCommonTextFormatter();
   }

   public static NodeManagerCommonTextFormatter getInstance(Locale l) {
      return new NodeManagerCommonTextFormatter(l);
   }

   public String getScriptExecutionFailure(String arg0, int arg1) {
      String id = "scriptExecutionFailure";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidCommand(String arg0) {
      String id = "invalidCommand";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsupportedSSLMinimumProtocolVersion(String arg0, String arg1) {
      String id = "logUnsupportedSSLMinimumProtocolVersion";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
