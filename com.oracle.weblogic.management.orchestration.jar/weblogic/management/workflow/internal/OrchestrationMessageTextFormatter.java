package weblogic.management.workflow.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class OrchestrationMessageTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public OrchestrationMessageTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.workflow.internal.OrchestrationMessageTextLocalizer", OrchestrationMessageTextFormatter.class.getClassLoader());
   }

   public OrchestrationMessageTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.workflow.internal.OrchestrationMessageTextLocalizer", OrchestrationMessageTextFormatter.class.getClassLoader());
   }

   public static OrchestrationMessageTextFormatter getInstance() {
      return new OrchestrationMessageTextFormatter();
   }

   public static OrchestrationMessageTextFormatter getInstance(Locale l) {
      return new OrchestrationMessageTextFormatter(l);
   }

   public String Fail() {
      String id = "Fail";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDecisionProceed() {
      String id = "getDecisionProceed";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDecisionFail() {
      String id = "getDecisionFail";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDecisionRetry() {
      String id = "getDecisionRetry";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDecisionRevert() {
      String id = "getDecisionRevert";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationExecute() {
      String id = "getOperationExecute";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationRetry() {
      String id = "getOperationRetry";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationRevert() {
      String id = "getOperationRevert";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationResume() {
      String id = "getOperationResume";
      String subsystem = "MgmtOrchestration";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
