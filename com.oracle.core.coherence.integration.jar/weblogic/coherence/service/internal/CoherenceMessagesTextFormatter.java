package weblogic.coherence.service.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class CoherenceMessagesTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public CoherenceMessagesTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.coherence.service.internal.CoherenceMessagesTextLocalizer", CoherenceMessagesTextFormatter.class.getClassLoader());
   }

   public CoherenceMessagesTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.coherence.service.internal.CoherenceMessagesTextLocalizer", CoherenceMessagesTextFormatter.class.getClassLoader());
   }

   public static CoherenceMessagesTextFormatter getInstance() {
      return new CoherenceMessagesTextFormatter();
   }

   public static CoherenceMessagesTextFormatter getInstance(Locale l) {
      return new CoherenceMessagesTextFormatter(l);
   }

   public String getCheckDeploymentNameNotContainPartitionPrefixMsg(String arg0, String arg1) {
      String id = "checkDeploymentNameNotContainPartitionPrefix";
      String subsystem = "CoherenceIntegration";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
