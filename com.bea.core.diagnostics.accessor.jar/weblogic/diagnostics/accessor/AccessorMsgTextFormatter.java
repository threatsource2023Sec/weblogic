package weblogic.diagnostics.accessor;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class AccessorMsgTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public AccessorMsgTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.diagnostics.accessor.AccessorMsgTextLocalizer", AccessorMsgTextFormatter.class.getClassLoader());
   }

   public AccessorMsgTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.diagnostics.accessor.AccessorMsgTextLocalizer", AccessorMsgTextFormatter.class.getClassLoader());
   }

   public static AccessorMsgTextFormatter getInstance() {
      return new AccessorMsgTextFormatter();
   }

   public static AccessorMsgTextFormatter getInstance(Locale l) {
      return new AccessorMsgTextFormatter(l);
   }

   public String getFailedToDistributeApp(String arg0) {
      String id = "FAILED_TO_DISTRIBUTE_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAppDistributionComplete(String arg0) {
      String id = "APP_DISTRIBUTION_COMPLETE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAppDeploymentComplete(String arg0) {
      String id = "APP_DEPLOYMENT_COMPLETE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToDeployApp(String arg0) {
      String id = "FAILED_TO_DEPLOY_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToUndeployApp(String arg0) {
      String id = "FAILED_TO_UNDEPLOY_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToUpdateApp(String arg0) {
      String id = "FAILED_TO_UPDATE_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToStartApp(String arg0) {
      String id = "FAILED_TO_START_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToStopApp(String arg0) {
      String id = "FAILED_TO_STOP_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDistributionStarted() {
      String id = "DISTRIBUTION_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationStarted() {
      String id = "APPLICATION_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentStarted() {
      String id = "DEPLOYMENT_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedeploymentStarted() {
      String id = "REDEPLOYMENT_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndeploymentStarted() {
      String id = "UNDEPLOYMENT_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopStarted() {
      String id = "STOP_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateStarted() {
      String id = "UPDATE_STARTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDistributingApp(String arg0) {
      String id = "Error_Distributing_App";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDeployingApp(String arg0) {
      String id = "ERROR_DEPLOYING_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionInProgress(String arg0) {
      String id = "EDIT_SESSION_IN_PROGRESS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationPathNotFound(String arg0) {
      String id = "APPLICATION_PATH_NOT_FOUND";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentFailed() {
      String id = "DeploymentFailed";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedError(String arg0) {
      String id = "UnexpectedError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActionTimedOut(long arg0) {
      String id = "ActionTimedOut";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedeployingApp(String arg0) {
      String id = "REDEPLOYING_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndeployingApp(String arg0) {
      String id = "UNDEPLOYING_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdatingApp(String arg0) {
      String id = "UPDATING_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToRedeployApp(String arg0) {
      String id = "FAILED_TO_REDEPLOY_APP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppRedeploy(String arg0) {
      String id = "COMPLETED_APP_REDEPLOY";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppUpdate(String arg0) {
      String id = "COMPLETED_APP_UPDATE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppStart(String arg0) {
      String id = "COMPLETED_APP_START";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppStop(String arg0) {
      String id = "COMPLETED_APP_STOP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppUndeploy(String arg0) {
      String id = "COMPLETED_APP_UNDEPLOY";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePlanTrue() {
      String id = "Create_PlanTrue";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String createPlanMacroError() {
      String id = "Create_Plan_Macro_Error";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String planMacroRequiresTemplateError() {
      String id = "Plan_Macro_Requires_Template_Error";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingApplication(String arg0) {
      String id = "STARTING_APPLICATION";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoppingApplication(String arg0) {
      String id = "STOPPING_APPLICATION";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanPathNeededToUpdate() {
      String id = "PLAN_PATH_NEEDED_TO_UPDATE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadingConfiguration(String arg0) {
      String id = "LOADING_CONFIGURATION";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotLocateConfigXml() {
      String id = "CANNOT_LOCATE_CONFIG_Xml";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionLoadingConfig() {
      String id = "IOExceptionLoadingConfig";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToLoadConfigXml() {
      String id = "Failed_ToLoad_Config_Xml";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionWritingDeploymentScript(String arg0) {
      String id = "IOEXCEPTION_WRITING_DEPLOYMENT_SCRIPT";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConvertingResources() {
      String id = "CONVERTING_RESOURCES";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigToScriptComplete(String arg0, String arg1) {
      String id = "CONFIG_TO_SCRIPT_COMPLETE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPasswordDisclaimer(String arg0, String arg1) {
      String id = "PASSWORD_DISCLAIMER";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsAChild(String arg0) {
      String id = "BEAN_IS_A_CHILD";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsDefaulted(String arg0) {
      String id = "BEAN_IS_DEFAULTED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsAReference(String arg0) {
      String id = "BEAN_IS_A_REFERENCE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionWalkingTree() {
      String id = "EXCEPTION_WALKING_TREE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContinueInBean() {
      String id = "ContinueInBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScriptAlreadyExists(String arg0) {
      String id = "SCRIPT_ALREADY_EXISTS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFindingAppBean() {
      String id = "ERROR_FINDING_APP_BEAN";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFindingParent(String arg0) {
      String id = "ERROR_FINDING_PARENT";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingBeanName(String arg0) {
      String id = "ERROR_GETTING_BEAN_NAME";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeSet(String arg0, String arg1) {
      String id = "ATTRIBUTE_SET";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorMakingSetBeans() {
      String id = "ERROR_MAKING_SET_BEANS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionWritingFile(String arg0) {
      String id = "IOEXCEPTION_WRITING_FILE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindBeanOfType(String arg0) {
      String id = "FIND_BEAN_OF_TYPE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNowFinding(String arg0) {
      String id = "NOW_FINDING";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindByTypeResultEmpty() {
      String id = "FIND_BY_TYPE_RESULT_EMPTY";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindByInstanceResultEmpty() {
      String id = "FIND_BY_INSTANCE_RESULT_EMPTY";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidUserOrPassword() {
      String id = "INVALID_USER_OR_PASSWORD";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMalformedManagedServerURL(String arg0) {
      String id = "MALFORMED_MANAGED_SERVER_URL";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNamingException(String arg0) {
      String id = "NAMING_EXCEPTION";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentLocationNoLongerExists(String arg0, String arg1) {
      String id = "Current_Location_No_LongerE_xists";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRetrievingAttributeNameValue() {
      String id = "Error_Retrieving_Attribute_Name_Value";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRetrievingOperationInfo() {
      String id = "ERROR_RETRIEVING_OPERATION_INFO";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyRecording(String arg0) {
      String id = "AlreadyRecording";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutputFileIsDir(String arg0) {
      String id = "OutputFileIsDir";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartedRecording(String arg0) {
      String id = "STARTED_RECORDING";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoppedRecording(String arg0) {
      String id = "STOPPED_RECORDING";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotRecording() {
      String id = "NOT_RECORDING";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWhileRecording() {
      String id = "ERROR_WHILE_RECORDING";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWhileStoppingRecording() {
      String id = "ERROR_WHILE_STOPPING_RECORDING";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWritingCommand(String arg0) {
      String id = "ERROR_WRITING_COMMAND";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWroteIniFile(String arg0) {
      String id = "WROTE_INI_FILE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWritingIni() {
      String id = "ERROR_WRITING_INI";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNeedWlsOrNm() {
      String id = "NEED_WLS_OR_NM";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernamePasswordStored(String arg0, String arg1, String arg2) {
      String id = "USERNAME_PASSWORD_STORED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoAttrDescription(String arg0) {
      String id = "NoAttrDescription";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoChildBeans(String arg0) {
      String id = "NoChildBeans";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsupportedCommand(String arg0) {
      String id = "UnsupportedCommand";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingMBeanInfo(String arg0) {
      String id = "ErrorGettingMBeanInfo";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRequestedThreadDump() {
      String id = "RequestedThreadDump";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpNeedsConnection() {
      String id = "ThreadDumpNeedsConnection";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpServerNotRunning() {
      String id = "ThreadDumpServerNotRunning";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpHeader(String arg0, Date arg1) {
      String id = "ThreadDumpHeader";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpHeader2(String arg0) {
      String id = "ThreadDumpHeader2";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpFooter(String arg0, String arg1) {
      String id = "ThreadDumpFooter";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpFileError() {
      String id = "ThreadDumpFileError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotCreateParentDir(String arg0) {
      String id = "CouldNotCreateParentDir";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutputFileIsNull() {
      String id = "OutputFileIsNull";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyRedirecting(String arg0, String arg1) {
      String id = "AlreadyRedirecting";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectFileNotFound(String arg0) {
      String id = "RedirectFileNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopRedirect(String arg0) {
      String id = "StopRedirect";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotRedirecting() {
      String id = "NotRedirecting";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionStoppingRedirect() {
      String id = "IOExceptionStoppingRedirect";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathNotFound() {
      String id = "PathNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingPath() {
      String id = "ErrorGettingPath";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLostConnection() {
      String id = "LostConnection";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigRuntimeServerNotEnabled() {
      String id = "ConfigRuntimeServerNotEnabled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToConfigRuntime() {
      String id = "LocationChangedToConfigRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInConfigRuntime() {
      String id = "AlreadyInConfigRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToConfigRuntime() {
      String id = "ErrorTraversingToConfigRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRuntimeServerNotEnabled() {
      String id = "RuntimeServerNotEnabled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToServerRuntime() {
      String id = "LocationChangedToServerRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInServerRuntime() {
      String id = "AlreadyInServerRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToServerRuntime() {
      String id = "ErrorTraversingToServerRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeNotAvailableOnMS() {
      String id = "DomainRuntimeNotAvailableOnMS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeServerNotEnabled() {
      String id = "DomainRuntimeServerNotEnabled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainConfig() {
      String id = "LocationChangedToDomainConfig";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainRuntime() {
      String id = "LocationChangedToDomainRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInDomainConfig() {
      String id = "AlreadyInDomainConfig";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInDomainRuntime() {
      String id = "AlreadyInDomainRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToDomainConfig() {
      String id = "ErrorTraversingToDomainConfig";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToDomainRuntime() {
      String id = "ErrorTraversingToDomainRuntime";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditNotAvailableOnMS() {
      String id = "EditNotAvailableOnMS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditServerNotEnabled() {
      String id = "EditServerNotEnabled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToEdit() {
      String id = "LocationChangedToEdit";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionInProgress() {
      String id = "EditSessionInProgress";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInEdit() {
      String id = "AlreadyInEdit";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToEdit() {
      String id = "ErrorTraversingToEdit";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJSR77NotAvailableOnMS() {
      String id = "JSR77NotAvailableOnMS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJSR77ServerNotEnabled() {
      String id = "JSR77ServerNotEnabled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultingNMUsername(String arg0) {
      String id = "DefaultingNMUsername";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultingNMPassword(String arg0) {
      String id = "DefaultingNMPassword";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectingToNodeManager() {
      String id = "ConnectingToNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectedToNodeManager() {
      String id = "ConnectedToNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectedFromNodeManager() {
      String id = "DisconnectedFromNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotConnectToNodeManager() {
      String id = "CouldNotConnectToNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentlyConnectedNM(String arg0) {
      String id = "CurrentlyConnectedNM";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConnectedNM() {
      String id = "NotConnectedNM";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMVersion(String arg0) {
      String id = "NMVersion";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKillingServer(String arg0) {
      String id = "KillingServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorKillingServer(String arg0) {
      String id = "ErrorKillingServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKilledServer(String arg0) {
      String id = "KilledServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnrollingMachineInDomain(String arg0) {
      String id = "EnrollingMachineInDomain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnrolledMachineInDomain(String arg0) {
      String id = "EnrolledMachineInDomain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToEnrolMachineInDomain() {
      String id = "FailedToEnrolMachineInDomain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingServer(String arg0) {
      String id = "StartingServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingServer(String arg0, String arg1) {
      String id = "ErrorStartingServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartedServer(String arg0) {
      String id = "StartedServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullOrEmptyServerName() {
      String id = "NullOrEmptyServerName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServerOrCoherenceMBean(String arg0) {
      String id = "NoServerOrCoherenceMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratedBootProperties(String arg0) {
      String id = "GeneratedBootProperties";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSavingBootProperties() {
      String id = "ErrorSavingBootProperties";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratedStartupProperties(String arg0) {
      String id = "GeneratedStartupProperties";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSavingStartupProperties() {
      String id = "ErrorSavingStartupProperties";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingNMClient() {
      String id = "ErrorGettingNMClient";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerAlreadyExists(String arg0) {
      String id = "ListenerAlreadyExists";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoListenersConfigured() {
      String id = "NoListenersConfigured";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoPlanVariablesOverwritten() {
      String id = "NoPlanVariablesOverwritten";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanVariableOverwritten(String arg0, String arg1) {
      String id = "PlanVariableOverwritten";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchPlanVariable(String arg0) {
      String id = "NoSuchPlanVariable";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchModuleOverride(String arg0) {
      String id = "NoSuchModuleOverride";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoModuleOverrides() {
      String id = "NoModuleOverrides";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingModuleDescriptor(String arg0, String arg1) {
      String id = "CreatingModuleDescriptor";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedModuleDescriptor(String arg0, String arg1) {
      String id = "CreatedModuleDescriptor";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyedModuleOverride(String arg0) {
      String id = "DestroyedModuleOverride";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingVariableAssignment(String arg0, String arg1) {
      String id = "CreatingVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedVariableAssignment(String arg0) {
      String id = "CreatedVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCreatingVariableAssignment(String arg0) {
      String id = "ErrorCreatingVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGetingVariableAssignment(String arg0) {
      String id = "ErrorGetingVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyingVariableAssignment(String arg0, String arg1) {
      String id = "DestroyingVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotFindModuleDescriptor() {
      String id = "CannotFindModuleDescriptor";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyedVariableAssignment(String arg0) {
      String id = "DestroyedVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDestroyingVariableAssignment(String arg0) {
      String id = "ErrorDestroyingVariableAssignment";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParentIsNotModuleOverride() {
      String id = "ParentIsNotModuleOverride";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotGetDeployableObject() {
      String id = "CouldNotGetDeployableObject";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotOpenDescriptorUri(String arg0) {
      String id = "CouldNotOpenDescriptorUri";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorBuildingDConfigBean(String arg0) {
      String id = "ErrorBuildingDConfigBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleDescriptorBeanDoesNotExist(String arg0, String arg1) {
      String id = "ModuleDescriptorBeanDoesNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unrecognizedOption(String arg0) {
      String id = "UnrecognizedOption";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroupOrResourceNameNull() {
      String id = "GROUP_OR_RES_NAME_NULL";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateGroupName(String arg0) {
      String id = "DUPLICATE_GROUP_NAME";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingResourceBundle() {
      String id = "ERROR_GETTING_RESOURCE_BUNDLE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroupNameNotExist(String arg0) {
      String id = "GROUP_NAME_NOT_EXIST";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateCommandName(String arg0) {
      String id = "DUPLICATE_COMMAND_NAME";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoResourceFoundForCmd(String arg0, String arg1) {
      String id = "NO_RESOURCE_FOUND_FOR_CMD";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConnectedAdminServer() {
      String id = "NotConnectedAdminServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPythonExecError(String arg0, Exception arg1) {
      String id = "PythonExecError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPythonImportError(String arg0, Exception arg1) {
      String id = "PythonImportError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsupportedServerType(String arg0) {
      String id = "UnsupportedServerType";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInherited() {
      String id = "Inherited";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOccurred(String arg0, String arg1) {
      String id = "ErrorOccurred";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOccurredUseDumpStack(String arg0, String arg1) {
      String id = "ErrorOccurredUseDumpStack";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndoChanges() {
      String id = "UndoChanges";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopChanges() {
      String id = "StopChanges";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCancelChanges() {
      String id = "CancelChanges";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndoNotPerformed() {
      String id = "UndoNotPerformed";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditStopped() {
      String id = "EditStopped";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditNotStopped() {
      String id = "EditNotStopped";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditCancelled() {
      String id = "EditCancelled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLaunchingNodeManagerMessage() {
      String id = "LaunchingNodeManagerMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerAlreadyStarted() {
      String id = "NodeManagerAlreadyStarted";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLaunchedNodeManager(String arg0) {
      String id = "LaunchedNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConfiguredWithDomain(String arg0) {
      String id = "NotConfiguredWithDomain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotFindWLHOME() {
      String id = "CannotFindWLHOME";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWaitingForNodeManager() {
      String id = "WaitingForNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingNodeManager() {
      String id = "ErrorStartingNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionFailed() {
      String id = "ConnectionFailed";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerStarting() {
      String id = "NodeManagerStarting";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerStopped() {
      String id = "NodeManagerStopped";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopNodeManagerComplete(String arg0) {
      String id = "StopNodeManagerComplete";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStoppingNodeManager() {
      String id = "ErrorStoppingNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotStopNodeManager() {
      String id = "CanNotStopNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemStoppingNodeManager() {
      String id = "ProblemStoppingNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunningNMScriptMessage(String arg0, String arg1) {
      String id = "RunningNMScriptMessage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExportedDiagnosticData(String arg0) {
      String id = "ExportedDiagnosticData";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFormattedSystemResourceControlInfoHeader() {
      String id = "FormattedSystemResourceControlInfoHeader";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFormattedSystemResourceControlInfo(String arg0, boolean arg1, boolean arg2) {
      String id = "FormattedSystemResourceControlInfo";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRetrievingImage(String arg0, String arg1) {
      String id = "RetrievingImage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRetrievingImageEntry(String arg0, String arg1, String arg2) {
      String id = "RetrievingImageEntry";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCapturingImage() {
      String id = "CapturingImage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCapturedImage() {
      String id = "CapturedImage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageFromServer(String arg0, String arg1, String arg2) {
      String id = "CaptureImageFromServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageEntryFromServer(String arg0, String arg1, String arg2, String arg3) {
      String id = "CaptureImageEntryFromServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageFromServerPartition(String arg0, String arg1, String arg2, String arg3) {
      String id = "CaptureImageFromServerPartition";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageEntryFromServerPartition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "CaptureImageEntryFromServerPartition";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureAndSaveImage() {
      String id = "CaptureAndSaveImage";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToCreateDirectory(String arg0) {
      String id = "UnableToCreateDirectory";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyConnected() {
      String id = "AlreadyConnected";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemComponentNotFound(String arg0) {
      String id = "SystemComponentNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentNotFound(String arg0) {
      String id = "ComponentNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMachineNotFound(String arg0) {
      String id = "MachineNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentTypeNotFound(String arg0, String arg1) {
      String id = "ComponentTypeNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThereIsNoCompConfDefined(String arg0) {
      String id = "ThereIsNoCompConfDefined";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingComponentInCompConf(String arg0, String arg1) {
      String id = "StartingComponentInCompConf";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemStartingComponentConfiguration(String arg0) {
      String id = "ProblemStartingComponentConfiguration";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartSystemComponent(String arg0) {
      String id = "CouldNotStartSystemComponent";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableStartSomeComps(String arg0) {
      String id = "UnableStartSomeComps";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentInComponentConfigurationStarted(String arg0) {
      String id = "NoComponentInComponentConfigurationStarted";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllCompsStartedSuccessfully(String arg0) {
      String id = "AllCompsStartedSuccessfully";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentConfigurationConfigured(String arg0) {
      String id = "NoComponentConfigurationConfigured";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompsInConfiguration(String arg0, String arg1) {
      String id = "CompsInConfiguration";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateOfComponents() {
      String id = "StateOfComponents";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotActiveComponents(String arg0) {
      String id = "NotActiveComponents";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownComponentConfiguration(String arg0) {
      String id = "getShutdownComponentConfiguration";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownCompConfRequiresName() {
      String id = "ShutdownCompConfRequiresName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownCompConf() {
      String id = "ErrorShuttingDownCompConf";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompConfShutdownIssued(String arg0) {
      String id = "CompConfShutdownIssued";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartingComponentInCompConf(String arg0, String arg1) {
      String id = "SoftRestartingComponentInCompConf";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemSoftRestartingComponentConfiguration(String arg0) {
      String id = "ProblemSoftRestartingComponentConfiguration";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotSoftRestartSystemComponent(String arg0) {
      String id = "CouldNotSoftRestartSystemComponent";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableSoftRestartSomeComps(String arg0) {
      String id = "UnableSoftRestartSomeComps";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentInComponentConfigurationSoftRestarted(String arg0) {
      String id = "NoComponentInComponentConfigurationSoftRestarted";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllCompsSoftRestartedSuccessfully(String arg0) {
      String id = "AllCompsSoftRestartedSuccessfully";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentConfigurationStartStatus() {
      String id = "ComponentConfigurationStartStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityConfigurationNotFound(String arg0) {
      String id = "SecurityConfigurationNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddressAndPortNotFound() {
      String id = "AddressAndPortNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernameAndPasswordNotFound() {
      String id = "UsernameAndPasswordNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMTypeNotFound() {
      String id = "NMTypeNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotLoadProperties() {
      String id = "CanNotLoadProperties";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImageCreated(String arg0) {
      String id = "ImageCreated";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImageCaptureFailed(String arg0) {
      String id = "ImageCaptureFailed";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindingNameInRegisteredInstances(String arg0) {
      String id = "FindingNameInRegisteredInstances";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindingNameInMBeanTypes(String arg0) {
      String id = "FindingNameInMBeanTypes";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoResultsFound() {
      String id = "NoResultsFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRunningProcess(String arg0) {
      String id = "ErrorRunningProcess";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKillingProcess(String arg0) {
      String id = "KillingProcess";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingProcess(String arg0) {
      String id = "ErrorGettingProcess";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStatus() {
      String id = "CurrentStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentCommandType(String arg0) {
      String id = "DeploymentCommandType";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentState(String arg0) {
      String id = "DeploymentState";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentNoMsg() {
      String id = "DeploymentNoMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentMsg(String arg0) {
      String id = "DeploymentMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToGetValue(String arg0) {
      String id = "FailedToGetValue";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingServersInCluster(String arg0, String arg1) {
      String id = "StartingServersInCluster";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllServersStartedSuccessfully(String arg0) {
      String id = "AllServersStartedSuccessfully";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartStatusTask(String arg0, String arg1) {
      String id = "ServerStartStatusTask";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStartStatus() {
      String id = "ClusterStartStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNameStarted(String arg0) {
      String id = "ServerNameStarted";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartStatus() {
      String id = "ServerStartStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoUserOrPasswordBlocking(String arg0) {
      String id = "NoUserOrPasswordBlocking";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedExceptionRetrying() {
      String id = "UnexpectedExceptionRetrying";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotConnectToServer(String arg0) {
      String id = "CouldNotConnectToServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeNamesAndValues() {
      String id = "AttributeNamesAndValues";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationsOnThisMBean() {
      String id = "OperationsOnThisMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorViewingTheMBean() {
      String id = "ErrorViewingTheMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerFileNotLocated(String arg0) {
      String id = "ListenerFileNotLocated";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStateOfServer(String arg0, String arg1) {
      String id = "CurrentStateOfServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingServerState() {
      String id = "ErrorGettingServerState";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServerWithName(String arg0) {
      String id = "NoServerWithName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminServer() {
      String id = "ShouldBeConnectedToAdminServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServersInCluster(String arg0, String arg1) {
      String id = "ServersInCluster";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateOfServers() {
      String id = "StateOfServers";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotActiveServers(String arg0) {
      String id = "NotActiveServers";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerResumedSuccessfully(String arg0) {
      String id = "ServerResumedSuccessfully";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToResume(String arg0, String arg1) {
      String id = "FailedToResume";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerResumeTask(String arg0, String arg1) {
      String id = "ServerResumeTask";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCallResumeStatus() {
      String id = "CallResumeStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerSuspendedSuccessfully(String arg0) {
      String id = "ServerSuspendedSuccessfully";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToSuspendServer(String arg0, String arg1) {
      String id = "FailedToSuspendServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerSuspendTask(String arg0, String arg1) {
      String id = "ServerSuspendTask";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCallSuspendStatus() {
      String id = "CallSuspendStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingWLSServer() {
      String id = "StartingWLSServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartedSuccessfully() {
      String id = "ServerStartedSuccessfully";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckServerOutput() {
      String id = "CheckServerOutput";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindRuntimeMBean() {
      String id = "CouldNotFindRuntimeMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorPopulatingObjectNames() {
      String id = "ErrorPopulatingObjectNames";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCheckingSlashes() {
      String id = "ErrorCheckingSlashes";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidServerName(String arg0) {
      String id = "InvalidServerName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDomainRuntimeServiceAccess() {
      String id = "InvalidDomainRuntimeServiceAccess";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunningServerOrClusterNotFound(String arg0) {
      String id = "RunningServerOrClusterNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeServiceNotFound(String arg0) {
      String id = "DomainRuntimeServiceNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemResourceNotExist(String arg0, String arg1) {
      String id = "SystemResourceNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWarnSystemResourceNotExist(String arg0, String arg1) {
      String id = "WarnSystemResourceNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemResourceExistsOnServers(String arg0, String arg1) {
      String id = "SystemResourceExistsOnServers";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataNullInputDirName() {
      String id = "MergeDiagnosticDataNullInputDirName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInputDirDoesNotExist(String arg0) {
      String id = "MergeDiagnosticDataInputDirDoesNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInvalidOutputFileName(String arg0) {
      String id = "MergeDiagnosticDataInvalidOutputFileName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataStart(String arg0) {
      String id = "MergeDiagnosticDataStart";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataOpenFile(String arg0) {
      String id = "MergeDiagnosticDataOpenFile";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataBuildKeySet() {
      String id = "MergeDiagnosticDataBuildKeySet";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticTotalMergedKeys(int arg0) {
      String id = "MergeDiagnosticTotalMergedKeys";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInvalidDataSet(String arg0) {
      String id = "MergeDiagnosticDataInvalidDataSet";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataUnexpectedFormat(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "MergeDiagnosticDataUnexpectedFormat";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataFileError(String arg0, Throwable arg1) {
      String id = "MergeDiagnosticDataFileError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataTimestampParseError(String arg0, String arg1) {
      String id = "MergeDiagnosticDataTimestampParseError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataUnrecognizedFileFormat(String arg0) {
      String id = "MergeDiagnosticDataUnrecognizedFileFormat";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInputFileNotFound(String arg0) {
      String id = "MergeDiagnosticDataInputFileNotFound";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataComplete() {
      String id = "MergeDiagnosticDataComplete";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagosticDataInstanceSetChanged(String arg0) {
      String id = "DumpDiagosticDataInstanceSetChanged";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagnosticDataNewCaptureFile(String arg0) {
      String id = "DumpDiagnosticDataNewCaptureFile";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagonsticDataCaptureStart(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      String id = "DumpDiagonsticDataCaptureStart";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagnosticDataCaptureComplete() {
      String id = "DumpDiagnosticDataCaptureComplete";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDeterminingIfCreate() {
      String id = "ErrorDeterminingIfCreate";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingCreator() {
      String id = "ErrorGettingCreator";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingInterfaceClassName() {
      String id = "ErrorGettingInterfaceClassName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorInScanningForAttrs() {
      String id = "ErrorInScanningForAttrs";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingAttributes() {
      String id = "ErrorGettingAttributes";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWalkingTheTree() {
      String id = "ErrorWalkingTheTree";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFindingTheMBean() {
      String id = "ErrorFindingTheMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionWalkingBean(String arg0, String arg1) {
      String id = "ExceptionWalkingBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterNotEnabled() {
      String id = "HarvesterNotEnabled";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNotRunning(String arg0) {
      String id = "ServerNotRunning";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotFindServerInstance(String arg0) {
      String id = "CanNotFindServerInstance";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifeCycledException() {
      String id = "ServerLifeCycledException";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotFindClusterInstance() {
      String id = "CanNotFindClusterInstance";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownServer() {
      String id = "ErrorShuttingDownServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemEnrollingMachine() {
      String id = "ProblemEnrollingMachine";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerName(String arg0) {
      String id = "ListenerName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanChangedListener(String arg0) {
      String id = "MBeanChangedListener";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeChanged(String arg0) {
      String id = "AttributeChanged";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeValueChanged(String arg0, String arg1) {
      String id = "AttributeValueChanged";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanName(String arg0) {
      String id = "MBeanName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanUnregistered() {
      String id = "MBeanUnregistered";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorAddingEditListener() {
      String id = "ErrorAddingEditListener";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorAddingCompatibilityChangeListener() {
      String id = "ErrorAddingCompatibilityChangeListener";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingMBeanInfoForMBean() {
      String id = "ErrorGettingMBeanInfoForMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingCustomMBeans() {
      String id = "ErrorGettingCustomMBeans";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToDetermineConfig() {
      String id = "UnableToDetermineConfig";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrrorFileNameRequired() {
      String id = "ErrrorFileNameRequired";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFileNotExist(String arg0) {
      String id = "ErrorFileNotExist";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthenticationFailed() {
      String id = "AuthenticationFailed";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorConnectingToServer() {
      String id = "ErrorConnectingToServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernameOrPasswordIncorrect() {
      String id = "UsernameOrPasswordIncorrect";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLIsMalformed() {
      String id = "URLIsMalformed";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotConnectViaHTTP() {
      String id = "CanNotConnectViaHTTP";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotConnectViaSSL() {
      String id = "CanNotConnectViaSSL";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotConnectViaT3s() {
      String id = "CanNotConnectViaT3s";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingInitialContext(String arg0) {
      String id = "ErrorGettingInitialContext";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToAdminServer(String arg0, String arg1) {
      String id = "ConnectToAdminServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToManaged(String arg0, String arg1) {
      String id = "ConnectToManaged";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartServer() {
      String id = "CouldNotStartServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartServerTimeout() {
      String id = "CouldNotStartServerTimeout";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingServerJVM() {
      String id = "ErrorStartingServerJVM";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRootDirectoryNotEmpty(String arg0) {
      String id = "RootDirectoryNotEmpty";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleExceptionSuspend(String arg0) {
      String id = "ServerLifecycleExceptionSuspend";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminSuspend() {
      String id = "ShouldBeConnectedToAdminSuspend";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleExceptionResume(String arg0) {
      String id = "ServerLifecycleExceptionResume";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminResume() {
      String id = "ShouldBeConnectedToAdminResume";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoClusterConfigured(String arg0) {
      String id = "NoClusterConfigured";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownConnection() {
      String id = "ErrorShuttingDownConnection";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThereIsNoClusterDefined(String arg0) {
      String id = "ThereIsNoClusterDefined";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownCluster() {
      String id = "ErrorShuttingDownCluster";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminStart() {
      String id = "ShouldBeConnectedToAdminStart";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemStartingCluster(String arg0) {
      String id = "ProblemStartingCluster";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingStatusFromLifecycle() {
      String id = "ErrorGettingStatusFromLifecycle";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartServerName(String arg0) {
      String id = "CouldNotStartServerName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableStartSomeServers(String arg0) {
      String id = "UnableStartSomeServers";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServersInClusterStarted(String arg0) {
      String id = "NoServersInClusterStarted";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedAdminOrNM() {
      String id = "ShouldBeConnectedAdminOrNM";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerIsAlreadyRunning(String arg0) {
      String id = "ServerIsAlreadyRunning";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToLookupServerLifeCycle(String arg0) {
      String id = "UnableToLookupServerLifeCycle";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerFailedtoStart(String arg0) {
      String id = "ServerFailedtoStart";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityExceptionOccurred() {
      String id = "SecurityExceptionOccurred";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleException() {
      String id = "ServerLifecycleException";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingServerPlain() {
      String id = "ErrorStartingServerPlain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSpecifyCorrectEntityType() {
      String id = "SpecifyCorrectEntityType";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminPerform() {
      String id = "ShouldBeConnectedToAdminPerform";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartingServer(String arg0) {
      String id = "RestartingServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRestartingServer(String arg0) {
      String id = "ErrorRestartingServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartedServer(String arg0) {
      String id = "RestartedServer";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStateOfComponent(String arg0, String arg1) {
      String id = "CurrentStateOfComponent";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingComponentState() {
      String id = "ErrorGettingComponentState";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentWithName(String arg0) {
      String id = "NoComponentWithName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownComponent(String arg0) {
      String id = "SHUTDOWN_COMPONENT";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownComponentRequiresName() {
      String id = "SHUTDOWN_COMPONENT_REQUIRES_NAME";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThereIsNoComponentDefined(String arg0) {
      String id = "ThereIsNoComponentDefined";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentShutdownSuccess(String arg0) {
      String id = "COMPONENT_SHUTDOWN_SUCCESS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownComponent() {
      String id = "ErrorShuttingDownComponent";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToLookupComponentLifeCycle(String arg0) {
      String id = "UnableToLookupComponentLifeCycle";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentNameStarted(String arg0) {
      String id = "ComponentNameStarted";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentFailedtoStart(String arg0, String arg1) {
      String id = "ComponentFailedtoStart";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentNotRunning(String arg0) {
      String id = "ComponentNotRunning";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotFindComponentInstance(String arg0) {
      String id = "CanNotFindComponentInstance";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentShutdownTaskAvailable(String arg0, String arg1) {
      String id = "COMPONENT_SHUTDOWN_TASK_AVAILABLE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentStartStatusTask(String arg0, String arg1) {
      String id = "ComponentStartStatusTask";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentStartStatus() {
      String id = "ComponentStartStatus";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentLifecycleException() {
      String id = "ComponentLifecycleException";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingComponentPlain() {
      String id = "ErrorStartingComponentPlain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingComponent(String arg0) {
      String id = "StartingComponent";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangesDeferred(String arg0) {
      String id = "ChangesDeferred";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangesDeferredError(String arg0) {
      String id = "ChangesDeferredError";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueNotObjectName(String arg0) {
      String id = "ValueNotObjectName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoElementWithName(String arg0) {
      String id = "NoElementWithName";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartComponentRequiresName() {
      String id = "SOFT_RESTART_COMPONENT_REQUIRES_NAME";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSoftRestartFromManaged() {
      String id = "CANNOT_SOFT_RESTART_FROM_MANAGED";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartComponent(String arg0) {
      String id = "SOFT_RESTART_COMPONENT";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentSoftRestartTaskAvailable(String arg0, String arg1) {
      String id = "COMPONENT_SOFT_RESTART_TASK_AVAILABLE";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentSoftRestartSuccess(String arg0) {
      String id = "COMPONENT_SOFT_RESTART_SUCCESS";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSoftRestartingComponent() {
      String id = "ErrorSoftRestartingComponent";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueMustBeTrueOrFalse(String arg0, String arg1) {
      String id = "ValueMustBeTrueOrFalse";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueMustBeTrueOrFalseDefaultTrue(String arg0, String arg1) {
      String id = "ValueMustBeTrueOrFalseDefaultTrue";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotLoadPropertiesInModule() {
      String id = "CannotLoadPropertiesInModule";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotSupportedWhileConnected() {
      String id = "NotSupportedWhileConnected";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotSupportedWhenNotConnected() {
      String id = "NotSupportedWhenNotConnected";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoppedDraining(String arg0) {
      String id = "StoppedDraining";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNameHeader() {
      String id = "NameHeader";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueHeader() {
      String id = "ValueHeader";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotDeployApp(String arg0) {
      String id = "CouldNotDeployApp";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSettingAttributes(String arg0) {
      String id = "SettingAttributes";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingMBean(String arg0) {
      String id = "CreatingMBean";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFromNodeManager() {
      String id = "ErrorFromNodeManager";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReadingDomain(String arg0) {
      String id = "ReadingDomain";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerDataAccessMsgText(String arg0) {
      String id = "ServerDataAccessMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPartitionDataAccessMsgText(String arg0, String arg1) {
      String id = "PartitionDataAccessMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPartitionNameMsgText(String arg0) {
      String id = "InvalidPartitionNameMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidLogicalNameMsgText(String arg0) {
      String id = "InvalidLogicalNameMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLDFModuleNameEmptyMsgText() {
      String id = "WLDFModuleNameEmptyMsg";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagonsticDataDateHeader() {
      String id = "DumpDiagonsticDataDateHeader";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagonsticDataTimestampHeader() {
      String id = "DumpDiagonsticDataTimestampHeader";
      String subsystem = "Diagnostics";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
