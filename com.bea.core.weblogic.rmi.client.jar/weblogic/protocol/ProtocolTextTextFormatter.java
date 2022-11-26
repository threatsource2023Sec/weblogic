package weblogic.protocol;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ProtocolTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ProtocolTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.protocol.ProtocolTextTextLocalizer", ProtocolTextTextFormatter.class.getClassLoader());
   }

   public ProtocolTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.protocol.ProtocolTextTextLocalizer", ProtocolTextTextFormatter.class.getClassLoader());
   }

   public static ProtocolTextTextFormatter getInstance() {
      return new ProtocolTextTextFormatter();
   }

   public static ProtocolTextTextFormatter getInstance(Locale l) {
      return new ProtocolTextTextFormatter(l);
   }

   public String msgFailDefaultServerURL() {
      String id = "msgFailDefaultServerURL";
      String subsystem = "PROTOCOL";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerURLMissingHost(String arg0) {
      String id = "msgServerURLMissingHost";
      String subsystem = "PROTOCOL";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerURLMissingPort(String arg0) {
      String id = "msgServerURLMissingPort";
      String subsystem = "PROTOCOL";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerURLMissingSquareBrackets(String arg0) {
      String id = "msgServerURLMissingSquareBrackets";
      String subsystem = "PROTOCOL";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
