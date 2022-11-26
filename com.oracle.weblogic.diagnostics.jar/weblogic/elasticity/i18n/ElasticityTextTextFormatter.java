package weblogic.elasticity.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ElasticityTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ElasticityTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.elasticity.i18n.ElasticityTextTextLocalizer", ElasticityTextTextFormatter.class.getClassLoader());
   }

   public ElasticityTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.elasticity.i18n.ElasticityTextTextLocalizer", ElasticityTextTextFormatter.class.getClassLoader());
   }

   public static ElasticityTextTextFormatter getInstance() {
      return new ElasticityTextTextFormatter();
   }

   public static ElasticityTextTextFormatter getInstance(Locale l) {
      return new ElasticityTextTextFormatter(l);
   }

   public String getInvalidScalingFactor(int arg0) {
      String id = "InvalidScalingFactor";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNonexistentOrConfiguredCluster(String arg0) {
      String id = "NonexistentOrStaticCluster";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateMaxServersIllegalClusterSize(String arg0, int arg1, int arg2) {
      String id = "UpdateMaxServersIllegalClusterSize";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainAccessNotFoundText() {
      String id = "DomainAccessNotFoundText";
      String subsystem = "Elasticity";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getElasticActionsNotAllowedOnManagedServersText() {
      String id = "ElasticActionsNotAllowedOnManagedServersText";
      String subsystem = "Elasticity";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWorkFlowProgressUnavailableText(String arg0) {
      String id = "WorkFlowProgressUnavailableText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceChooserInvalidClusterNameText(String arg0) {
      String id = "InstanceChooserInvalidClusterNameText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceChooserScalingOperationAlreadyInProgressText(String arg0) {
      String id = "InstanceChooserScalingOperationAlreadyInProgressText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceChooserCoolingOffPeriodLockoutText(String arg0, long arg1) {
      String id = "InstanceChooserCoolingOffPeriodLockoutText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMetadataPopulatorInvalidClusterNameText(String arg0) {
      String id = "MetadataPopulatorInvalidClusterNameText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMissingScriptInterceptorConfiguration() {
      String id = "MissingScriptInterceptorConfiguration";
      String subsystem = "Elasticity";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterRunningAtMaxSizeText(String arg0, String arg1, int arg2, int arg3, int arg4) {
      String id = "ClusterRunningAtMaxSizeText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterRunningAtMinSizeText(String arg0, String arg1, int arg2, int arg3) {
      String id = "ClusterRunningAtMinSizeText";
      String subsystem = "Elasticity";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
