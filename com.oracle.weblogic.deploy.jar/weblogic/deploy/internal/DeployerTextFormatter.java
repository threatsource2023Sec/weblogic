package weblogic.deploy.internal;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class DeployerTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public DeployerTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.deploy.internal.DeployerTextLocalizer", DeployerTextFormatter.class.getClassLoader());
   }

   public DeployerTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.deploy.internal.DeployerTextLocalizer", DeployerTextFormatter.class.getClassLoader());
   }

   public static DeployerTextFormatter getInstance() {
      return new DeployerTextFormatter();
   }

   public static DeployerTextFormatter getInstance(Locale l) {
      return new DeployerTextFormatter(l);
   }

   public String usageHeader() {
      String id = "USAGE_HEADER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageArgs() {
      String id = "USAGE_ARGS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageTrailer() {
      String id = "USAGE_TRAILER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageVerbose() {
      String id = "USAGE_VERBOSE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDebug() {
      String id = "USAGE_DEBUG";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDefaultTargets() {
      String id = "USAGE_DEFAULT_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUseNonexclusiveLock() {
      String id = "USAGE_USE_NONEXCLUSIVE_LOCK";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageNoDefaultTargets() {
      String id = "USAGE_NO_DEFAULT_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageExamples() {
      String id = "USAGE_EXAMPLES";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showExamples() {
      String id = "SHOW_EXAMPLES";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageNoWait() {
      String id = "USAGE_NOWAIT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageTimeout() {
      String id = "USAGE_TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDeploymentOrder() {
      String id = "USAGE_DEPLOYMENT_ORDER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageNoStage() {
      String id = "USAGE_NOSTAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageStage() {
      String id = "USAGE_STAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageExternalStage() {
      String id = "USAGE_EXTERNAL_STAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUpload() {
      String id = "USAGE_UPLOAD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDeleteFiles() {
      String id = "USAGE_DELETE_FILES";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageRemote() {
      String id = "USAGE_REMOTE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSource() {
      String id = "USAGE_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdSource() {
      String id = "USAGE_AD_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleSource() {
      String id = "EXAMPLE_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleUploadSrc() {
      String id = "EXAMPLE_UPLOAD_SRC";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUploadSrc() {
      String id = "USAGE_UPLOAD_SRC";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageName() {
      String id = "USAGE_NAME";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAltAppDD() {
      String id = "USAGE_ALT_APP_DD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleAltAppDD() {
      String id = "EXAMPLE_ALT_APP_DD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAltWebDD() {
      String id = "USAGE_ALT_WEB_DD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleAltWebDD() {
      String id = "EXAMPLE_ALT_WEB_DD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageStart() {
      String id = "USAGE_START";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdStart() {
      String id = "USAGE_AD_START";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdStop() {
      String id = "USAGE_AD_STOP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageStop() {
      String id = "USAGE_STOP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDistribute() {
      String id = "USAGE_DISTRIBUTE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdDistribute() {
      String id = "USAGE_AD_DISTRIBUTE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdRedeploy() {
      String id = "USAGE_AD_REDEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageRedeploy() {
      String id = "USAGE_REDEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUndeploy() {
      String id = "USAGE_UNDEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdUndeploy() {
      String id = "USAGE_AD_UNDEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdDeploy() {
      String id = "USAGE_AD_DEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageExtendLoader() {
      String id = "USAGE_EXTENDLOADER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdExtendLoader() {
      String id = "USAGE_AD_EXTENDLOADER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleName() {
      String id = "EXAMPLE_NAME";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleTimeout() {
      String id = "EXAMPLE_TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleDeploymentOrder() {
      String id = "EXAMPLE_DEPLOYMENT_ORDER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageTargets() {
      String id = "USAGE_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdTargets() {
      String id = "USAGE_AD_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleTargets() {
      String id = "EXAMPLE_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageId() {
      String id = "USAGE_ID";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdId() {
      String id = "USAGE_AD_ID";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleId() {
      String id = "EXAMPLE_ID";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageActivate() {
      String id = "USAGE_ACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDeploy() {
      String id = "USAGE_DEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageDeactivate() {
      String id = "USAGE_DEACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUnprepare() {
      String id = "USAGE_UNPREPARE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageRemove() {
      String id = "USAGE_REMOVE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageCancel() {
      String id = "USAGE_CANCEL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageList() {
      String id = "USAGE_LIST";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageListApps() {
      String id = "USAGE_LIST_APPS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMultipleActions(String arg0) {
      String id = "ERROR_MULTIPLE_ACTIONS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMissingAction() {
      String id = "ERROR_MISSING_ACTION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMissingTargets() {
      String id = "ERROR_MISSING_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMissingName() {
      String id = "ERROR_MISSING_NAME";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMissingId() {
      String id = "ERROR_MISSING_ID";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMissingSourceForUpload() {
      String id = "ERROR_MISSING_SOURCE_FOR_UPLOAD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorSourceNotAllowed() {
      String id = "ERROR_SOURCE_NOT_ALLOWED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoStageOnlyForActivate() {
      String id = "ERROR_NOSTAGE_ONLY_FOR_ACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorStageOnlyForActivate() {
      String id = "ERROR_STAGE_ONLY_FOR_ACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorExternalStageOnlyForActivate() {
      String id = "ERROR_EXTERNAL_STAGE_ONLY_FOR_ACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorStageOrNoStage() {
      String id = "ERROR_STAGE_OR_NOSTAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNameNotAllowed(String arg0) {
      String id = "ERROR_NAME_NOT_ALLOWED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorTaskHasRunToCompletion(String arg0) {
      String id = "ERROR_TASK_HAS_RUN_TO_COMPLETION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorTaskNotFound(String arg0) {
      String id = "ERROR_TASK_NOT_FOUND";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorUnableToAccessDeployer(String arg0, String arg1) {
      String id = "ERROR_UNABLE_TO_ACCESS_DEPLOYER";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorLostConnection() {
      String id = "ERROR_LOST_CONNECTION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoComponentAllowedOnRemove() {
      String id = "ERROR_NO_COMPONENT_ALLOWED_ON_REMOVE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showListHeader() {
      String id = "SHOW_LIST_HEADER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showDeploymentNotification(String arg0, String arg1, String arg2, String arg3) {
      String id = "SHOW_DEPLOYMENT_NOTIFICATION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageWaitingForNotifications() {
      String id = "MESSAGE_WAITING_FOR_NOTIFICATIONS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNoRealTargets() {
      String id = "MESSAGE_NO_REAL_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoRealTargets(String arg0) {
      String id = "ERROR_NO_REAL_TARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNoTargetsRunning() {
      String id = "MESSAGE_NO_REAL_TARGETS_RUNNING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noMessage() {
      String id = "NO_MESSAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationActivating() {
      String id = "MESSAGE_NOTIFICATION_ACTIVATING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationActivated() {
      String id = "MESSAGE_NOTIFICATION_ACTIVATED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationPreparing() {
      String id = "MESSAGE_NOTIFICATION_PREPARING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationPrepared() {
      String id = "MESSAGE_NOTIFICATION_PREPARED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationUnpreparing() {
      String id = "MESSAGE_NOTIFICATION_UNPREPARING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationUnprepared() {
      String id = "MESSAGE_NOTIFICATION_UNPREPARED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationDeactivating() {
      String id = "MESSAGE_NOTIFICATION_DEACTIVATING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationDeactivated() {
      String id = "MESSAGE_NOTIFICATION_DEACTIVATED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationDistributing() {
      String id = "MESSAGE_NOTIFICATION_DISTRIBUTING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationDistributed() {
      String id = "MESSAGE_NOTIFICATION_DISTRIBUTED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageNotificationFailed() {
      String id = "MESSAGE_NOTIFICATION_FAILED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageActivate() {
      String id = "MESSAGE_ACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDeactivate() {
      String id = "MESSAGE_DEACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageUnprepare() {
      String id = "MESSAGE_UNPREPARE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageRemove() {
      String id = "MESSAGE_REMOVE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDistribute() {
      String id = "MESSAGE_DISTRIBUTE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStart() {
      String id = "MESSAGE_START";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStop() {
      String id = "MESSAGE_STOP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageUndeploy() {
      String id = "MESSAGE_UNDEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageRetire() {
      String id = "MESSAGE_RETIRE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageRedeploy() {
      String id = "MESSAGE_REDEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageDeploy() {
      String id = "MESSAGE_DEPLOY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageList() {
      String id = "MESSAGE_LIST";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageListApps() {
      String id = "MESSAGE_LIST_APPS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageCancel() {
      String id = "MESSAGE_CANCEL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageServer() {
      String id = "MESSAGE_SERVER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageCluster() {
      String id = "MESSAGE_CLUSTER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageJMSServer() {
      String id = "MESSAGE_JMS_SERVER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageHost() {
      String id = "MESSAGE_HOST";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageTarget() {
      String id = "MESSAGE_TARGET";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageUnknown() {
      String id = "MESSAGE_UNKNOWN";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStateFailed() {
      String id = "MESSAGE_STATE_FAILED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStateInProgress() {
      String id = "MESSAGE_STATE_IN_PROGRESS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStateInit() {
      String id = "MESSAGE_STATE_INIT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStateSuccess() {
      String id = "MESSAGE_STATE_SUCCESS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStartedTask(String arg0, String arg1) {
      String id = "MESSAGE_STARTED_TASK";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageEnforceClusterConstraints() {
      String id = "USAGE_ENFORCE_CLUSTER_CONSTRAINTS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorInitDeployer(String arg0) {
      String id = "ERROR_INIT_DEPLOYER";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoSourceSpecified() {
      String id = "ERROR_NO_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMultipleSourceSpecified() {
      String id = "ERROR_MULTIPLE_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorUnexpectedConnectionFailure(String arg0) {
      String id = "ERROR_UNEXPECTED_CONN";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noSourceAltAppDD(String arg0) {
      String id = "NO_SOURCE_ALT_APP_DD";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noSourceAltWebDD(String arg0) {
      String id = "NO_SOURCE_ALT_WEB_DD";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noAppToList() {
      String id = "NO_APP_TO_LIST";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String appsFound() {
      String id = "APPS_FOUND";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warningListDeprecated() {
      String id = "WARNING_LIST_DEPRECATED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cancelFailed(String arg0) {
      String id = "CANCEL_FAILED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cancelSucceeded(String arg0) {
      String id = "CANCEL_SUCCEEDED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageModuleException(String arg0, String arg1) {
      String id = "MESSAGE_MODULE_EXCEPTION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageIOException(String arg0) {
      String id = "MESSAGE_IO_EXCEPTION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorFilesIllegal() {
      String id = "FILES_ILLEGAL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorFilesIllegalWithSource() {
      String id = "FILES_ILLEGAL_WITH_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorFilesIllegalInDeactivate() {
      String id = "FILES_ILLEGAL_IN_DEACTIVATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorSourceIllegal() {
      String id = "SOURCE_ILLEGAL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ignoringSource() {
      String id = "IGNORING_SOURCE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageOutput() {
      String id = "USAGE_OUTPUT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String timeOut(String arg0) {
      String id = "TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String successfulTransition(String arg0, String arg1, String arg2, String arg3) {
      String id = "SUCCESS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedTransition(String arg0, String arg1, String arg2, String arg3) {
      String id = "FAILURE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String appNotification(String arg0, String arg1, String arg2) {
      String id = "APP_NOTIF";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String moduleException(String arg0, String arg1) {
      String id = "MOD_ERROR";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateRunning() {
      String id = "STATE_RUNNING";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateCompleted() {
      String id = "STATE_COMP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateFailed() {
      String id = "STATE_FAILED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateInit() {
      String id = "STATE_INIT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unknown() {
      String id = "UNKNOWN";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String allTaskStatus(String arg0, String arg1, String arg2) {
      String id = "TASK_STATUS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showTargetState(String arg0, String arg1, String arg2, String arg3) {
      String id = "SHOW_TARGET_STATE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleAppVersion() {
      String id = "EXAMPLE_APP_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAppVersion() {
      String id = "USAGE_APP_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageNoVersion() {
      String id = "USAGE_NO_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String examplePlanVersion() {
      String id = "EXAMPLE_PLAN_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePlanVersion() {
      String id = "USAGE_PLAN_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleRetireTimeout() {
      String id = "EXAMPLE_RETIRE_TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageRetireTimeout() {
      String id = "USAGE_RETIRE_TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorRetireTimeoutIllegal(String arg0) {
      String id = "ERROR_RETIRE_TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorRetireTimeoutGracefulIllegal(String arg0, String arg1) {
      String id = "ERROR_RETIRE_TIMEOUT_GRACEFUL";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSubModuleTargets() {
      String id = "USAGE_SUBMODULETARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String paramSubModuleTargets() {
      String id = "PARAM_SUBMODULETARGETS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSecurityModel() {
      String id = "USAGE_SECURITYMODEL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSecurityEnabled() {
      String id = "USAGE_SECURITYVALIDATION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageLibrary() {
      String id = "USAGE_LIB_MODULE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleLibSpecVersion() {
      String id = "EXAMPLE_LIB_SPEC_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageLibSpecVersion() {
      String id = "USAGE_LIB_SPEC_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleLibImplVersion() {
      String id = "EXAMPLE_LIB_IMPL_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageLibImplVersion() {
      String id = "USAGE_LIB_IMPL_VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorOptionOnlyWithDeploy(String arg0) {
      String id = "ILLEGAL_OPTION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdminMode() {
      String id = "USAGE_ADMIN_MODE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorAdminModeIllegal(String arg0) {
      String id = "ERROR_ADMIN_MODE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageGraceful() {
      String id = "USAGE_GRACEFUL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorGracefulIllegal(String arg0) {
      String id = "ERROR_GRACEFUL";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageIgnoreSessions(String arg0) {
      String id = "USAGE_IGNORE_SESSIONS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorIgnoreSessionsIllegal(String arg0, String arg1) {
      String id = "ERROR_IGNORE_SESSIONS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageRmiGracePeriod(String arg0) {
      String id = "USAGE_RMI_GRACE_PERIOD";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleRmiGracePeriod() {
      String id = "EXAMPLE_RMI_GRACE_PERIOD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorRMIGracePeriodIllegal(String arg0, String arg1) {
      String id = "ERROR_RMI_GRACE_PERIOD";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAllVersions(String arg0) {
      String id = "USAGE_ALL_VERSIONS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorAllVersionsIllegal1(String arg0, String arg1) {
      String id = "ERROR_ALL_VERSIONS1";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorAllVersionsIllegal2(String arg0, String arg1) {
      String id = "ERROR_ALL_VERSIONS2";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageUpdate() {
      String id = "USAGE_UPDATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageRemovePlanOverride() {
      String id = "USAGE_REMOVE_PLAN_OVERRIDE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String advanced() {
      String id = "ADVANCED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String version() {
      String id = "VERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String help() {
      String id = "HELP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badFormat(String arg0) {
      String id = "BAD_FORMAT";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String deprecated(String arg0, String arg1) {
      String id = "DEPRECATED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorOptionNotAllowed(String arg0, String arg1) {
      String id = "OPTION_NOT_ALLOWED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String examplePlan() {
      String id = "EXAMPLE_PLAN";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePlan() {
      String id = "USAGE_PLAN";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorFailedOp() {
      String id = "ERROR_FAILED_OP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noTask(String arg0) {
      String id = "NO_TASK";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorHelperFailure() {
      String id = "ERROR_HELPER_FAILURE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMissingDelta() {
      String id = "ERROR_MISSING_DELTA";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoSuchFile(String arg0) {
      String id = "ERROR_NOSUCHFILE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoSuchTarget(String arg0) {
      String id = "ERROR_NOSUCHTARGET";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorModSubMod(String arg0) {
      String id = "ERROR_MODSUBMOD";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorNoPlan(String arg0) {
      String id = "ERROR_NO_PLAN";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String infoOptions(String arg0) {
      String id = "INFO_OPTIONS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdUpdate() {
      String id = "USAGE_AD_UPDATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdCancel() {
      String id = "USAGE_AD_CANCEL";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdListtask() {
      String id = "USAGE_AD_LISTTASK";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdList() {
      String id = "USAGE_AD_LIST";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdListapps() {
      String id = "USAGE_AD_LISTAPPS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidLibVersion(String arg0) {
      String id = "INVALID_LIBVERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidFileVersion(String arg0, String arg1) {
      String id = "INVALID_FILEVERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidVersionNoVersion() {
      String id = "INVALID_VERSIONNOVERSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noLib() {
      String id = "NO_LIB";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidTargetSyntax(String arg0) {
      String id = "INVALID_TARGET_SYNTAX";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorUploadDelete(String arg0) {
      String id = "ERROR_UPLOAD_DELETE";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSourceRootForUpload() {
      String id = "USAGE_SOURCE_ROOT_FOR_UPLOAD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleSourceRootForUpload() {
      String id = "EXAMPLE_SOURCE_ROOT_FOR_UPLOAD";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorSourceAndSourceRootNotAllowed() {
      String id = "ERROR_SOURCE_AND_SOURCEROOTFORUPLOAD_NOT_ALLOWED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageSAFAgent() {
      String id = "MESSAGE_SAF_AGENT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageAdPurgeTasks() {
      String id = "USAGE_AD_PURGE_TASKS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showRetiredTasks(String arg0) {
      String id = "SHOW_RETIRED_TASKS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showNoRetiredTasks() {
      String id = "SHOW_NO_RETIRED_TASKS";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String lastKnownStatus(String arg0, String arg1) {
      String id = "LAST_KNOWN_STATUS";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String pendingOperation() {
      String id = "PENDING_OPERATION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorGettingDeployerRuntime() {
      String id = "DEPLOYERRUNTIME_ERROR";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String deploymentFailed() {
      String id = "DEPLOYMENT_FAILED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String messageStateDeferred() {
      String id = "MESSAGE_STATE_DEFERRED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String stateDeferred() {
      String id = "STATE_DEFERRED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showTargetAssignmentsHeader() {
      String id = "SHOW_TARGETASSIGNMENTS_HEADER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noneSpecified() {
      String id = "NONE_SPECIFIED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String showDwpExamples() {
      String id = "SHOW_DWP_EXAMPLES";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String appNotDeployed(String arg0, String arg1) {
      String id = "APP_NOT_DEPLOYED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSucceedIfNameUsed() {
      String id = "USAGE_SUCCEED_IF_NAME_USED";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMultiVersions(String arg0, String arg1) {
      String id = "ERROR_MULTIPLE_VERSIONS_FOUND";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cancelTimeout(String arg0) {
      String id = "CANCEL_TIMEOUT";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String planCreated(String arg0) {
      String id = "PLAN_CREATED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePlanStage() {
      String id = "USAGE_PLAN_STAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePlanNoStage() {
      String id = "USAGE_PLAN_NOSTAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePlanExternalStage() {
      String id = "USAGE_PLAN_EXTERNAL_STAGE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSpecifiedTargetsOnly() {
      String id = "USAGE_SPECIFIED_TARGETS_ONLY";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usagePartition() {
      String id = "USAGE_PARTITION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageEditSession() {
      String id = "USAGE_EDIT_SESSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageResourceGroup() {
      String id = "USAGE_RESOURCE_GROUP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageResourceGroupTemplate() {
      String id = "USAGE_RESOURCE_GROUP_TEMPLATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String examplePartition() {
      String id = "EXAMPLE_PARTITION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleEditSession() {
      String id = "EXAMPLE_EDIT_SESSION";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleResourceGroup() {
      String id = "EXAMPLE_RESOURCE_GROUP";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exampleResourceGroupTemplate() {
      String id = "EXAMPLE_RESOURCE_GROUP_TEMPLATE";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorEmptyDirectory(String arg0) {
      String id = "ERROR_EMPTYDIRECTORY";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noTargetsAllowedForRGRGT() {
      String id = "NO_TARGETS_ALLOWED_FOR_RG_RGT";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String paramRequired(String arg0) {
      String id = "PARAM_REQUIRED";
      String subsystem = "Deployer";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String paramSpecifiedModules() {
      String id = "PARAM_SPECIFIED_MODULES";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String usageSpecifiedModules() {
      String id = "USAGE_SPECIFIED_MODULES";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String mtOptionWithNonMTServer() {
      String id = "MTOPTION_WITH_NON-MT_SERVER";
      String subsystem = "Deployer";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
