package weblogic.management.deploy;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DeployerRuntimeTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DeployerRuntimeTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.deploy.DeployerRuntimeTextTextLocalizer", DeployerRuntimeTextTextFormatter.class.getClassLoader());
   }

   public DeployerRuntimeTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.deploy.DeployerRuntimeTextTextLocalizer", DeployerRuntimeTextTextFormatter.class.getClassLoader());
   }

   public static DeployerRuntimeTextTextFormatter getInstance() {
      return new DeployerRuntimeTextTextFormatter();
   }

   public static DeployerRuntimeTextTextFormatter getInstance(Locale l) {
      return new DeployerRuntimeTextTextFormatter(l);
   }

   public String init() {
      String id = "INIT";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String running() {
      String id = "RUNNING";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String completed() {
      String id = "Completed";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failed() {
      String id = "Failed";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageActivate() {
      String id = "MESSAGE_ACTIVATE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDeploy() {
      String id = "MESSAGE_DEPLOY";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDeactivate() {
      String id = "MESSAGE_DEACTIVATE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageRemove() {
      String id = "MESSAGE_REMOVE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageUnprepare() {
      String id = "MESSAGE_UNPREPARE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageRedeploy() {
      String id = "MESSAGE_REDEPLOY";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDistribute() {
      String id = "MESSAGE_DISTRIBUTE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messagePrepare() {
      String id = "MESSAGE_PREPARE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStart() {
      String id = "MESSAGE_START";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStop() {
      String id = "MESSAGE_STOP";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageUpdate() {
      String id = "MESSAGE_UPDATE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageUndeploy() {
      String id = "MESSAGE_UNDEPLOY";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageRetire() {
      String id = "MESSAGE_RETIRE";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageExtendLoader() {
      String id = "MESSAGE_EXTEND_LOADER";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String readmeContent() {
      String id = "README_CONTENT";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String deferred() {
      String id = "Deferred";
      String subsystem = "DeployerRuntime";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
