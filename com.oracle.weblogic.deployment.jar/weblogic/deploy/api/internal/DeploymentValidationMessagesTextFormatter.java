package weblogic.deploy.api.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DeploymentValidationMessagesTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DeploymentValidationMessagesTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.deploy.api.internal.DeploymentValidationMessagesTextLocalizer", DeploymentValidationMessagesTextFormatter.class.getClassLoader());
   }

   public DeploymentValidationMessagesTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.deploy.api.internal.DeploymentValidationMessagesTextLocalizer", DeploymentValidationMessagesTextFormatter.class.getClassLoader());
   }

   public static DeploymentValidationMessagesTextFormatter getInstance() {
      return new DeploymentValidationMessagesTextFormatter();
   }

   public static DeploymentValidationMessagesTextFormatter getInstance(Locale l) {
      return new DeploymentValidationMessagesTextFormatter(l);
   }

   public String deploymentValidationFailure() {
      String id = "DEPLOYMENT_VALIDATION_FAILURE";
      String subsystem = "Deployment Validation";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String deploymentValidationPluginClassNotFound(String arg0) {
      String id = "DEPLOYMENT_VALIDATION_PLUGIN_CLASS_NOT_FOUND";
      String subsystem = "Deployment Validation";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
