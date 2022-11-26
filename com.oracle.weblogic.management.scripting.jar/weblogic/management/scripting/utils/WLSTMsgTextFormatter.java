package weblogic.management.scripting.utils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class WLSTMsgTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public WLSTMsgTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.scripting.utils.WLSTMsgTextLocalizer", WLSTMsgTextFormatter.class.getClassLoader());
   }

   public WLSTMsgTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.scripting.utils.WLSTMsgTextLocalizer", WLSTMsgTextFormatter.class.getClassLoader());
   }

   public static WLSTMsgTextFormatter getInstance() {
      return new WLSTMsgTextFormatter();
   }

   public static WLSTMsgTextFormatter getInstance(Locale l) {
      return new WLSTMsgTextFormatter(l);
   }

   public String getInitializingWLST() {
      String id = "INITIALIZING_WLST";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScanningPackage() {
      String id = "SCANNING_PACKAGE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWelcome() {
      String id = "WELCOME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpInfo() {
      String id = "HELP_INFO";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExitEdit() {
      String id = "EXIT_EDIT";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExitingWLST() {
      String id = "EXITING_WLST";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommandLineHelp() {
      String id = "COMMAND_LINE_HELP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertiesFileNull() {
      String id = "PROPERTIES_FILE_NULL";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertiesFileNotFound(String arg0) {
      String id = "PROPERTIES_FILE_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertiesFileNotReadable(String arg0) {
      String id = "PROPERTIES_FILE_NOT_READABLE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownServer(String arg0, String arg1, String arg2) {
      String id = "SHUTDOWN_SERVER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownCluster(String arg0) {
      String id = "SHUTDOWN_CLUSTER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownClusterRequiresName() {
      String id = "SHUTDOWN_CLUSTER_REQUIRES_NAME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotShutdownFromManaged() {
      String id = "CANNOT_SHUTDOWN_FROM_MANAGED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidShutdownEntity(String arg0) {
      String id = "INVALID_SHUTDOWN_ENTITY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerShutdownTaskAvailable(String arg0, String arg1) {
      String id = "SERVER_SHUTDOWN_TASK_AVAILABLE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterShutdownIssued(String arg0) {
      String id = "CLUSTER_SHUTDOWN_ISSUED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLaunchingNodeManager(String arg0, String arg1) {
      String id = "LAUNCHING_NODE_MANAGER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugOn() {
      String id = "DEBUG_ON";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDebugOff() {
      String id = "DEBUG_OFF";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigToScriptOlfFmt(String arg0) {
      String id = "CONFIG_TO_SCRIPT_OLD_FORMAT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigToScriptDeprecated() {
      String id = "CONFIG_TO_SCRIPT_DEPRECATED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFileNotFound(String arg0) {
      String id = "FILE_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigToScriptProblem(String arg0) {
      String id = "CONFIG_TO_SCRIPT_PROBLEM";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectWithEditSession() {
      String id = "DISCONNECT_WITH_EDIT_SESSION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExitWithEditSession() {
      String id = "EXIT_WITH_EDIT_SESSION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectCancelled() {
      String id = "DISCONNECT_CANCELLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExitCancelled() {
      String id = "EXIT_CANCELLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectedFromServer(String arg0) {
      String id = "DISCONNECTED_FROM_SERVER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectedFromPartition(String arg0) {
      String id = "DISCONNECTED_FROM_Partition";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectingToURL(String arg0, String arg1) {
      String id = "CONNECTING_TO_URL";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnected(String arg0) {
      String id = "CONNECTED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToConnect() {
      String id = "FAILED_TO_CONNECT";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClosingAllJMXConnections() {
      String id = "CLOSING_ALL_JMX_CONNECTIONS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDone() {
      String id = "DONE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoStackAvailable() {
      String id = "NO_STACK_AVAILABLE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionOccurredAt(String arg0) {
      String id = "EXCEPTION_OCCURRED_AT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOverrideAddressPortNotSupported() {
      String id = "OVERRIDE_ADDRESS_PORT_NOT_SUPPORTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSpecifyValidType(String arg0) {
      String id = "SPECIFY_VALID_TYPE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEasySyntaxOn() {
      String id = "EASY_SYNTAX_ON";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEasySyntaxOff() {
      String id = "EASY_SYNTAX_OFF";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindIsNotSupported() {
      String id = "FIND_IS_NOT_SUPPORTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSpecifyNameOrType() {
      String id = "SPECIFY_NAME_OR_TYPE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMalformedObjectName() {
      String id = "MALFORMED_OBJECT_NAME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchListenerName(String arg0, String arg1, String arg2) {
      String id = "WATCH_LISTENER_NAME";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddedWatchListener(String arg0, String arg1) {
      String id = "ADDED_WATCH_LISTENER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchAddMBeanNotFound(String arg0) {
      String id = "WATCH_ADD_MBEAN_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWatchRemoveMBeanNotFound(String arg0) {
      String id = "WATCH_REMOVE_MBEAN_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionAddingWatchListener(String arg0) {
      String id = "IOEXCEPTION_ADDING_WATCH_LISTENER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionRemoveingWatchListener(String arg0) {
      String id = "IOEXCEPTION_REMOVEING_WATCH_LISTENER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionDeletingWatchListener(String arg0) {
      String id = "IOEXCEPTION_DELETING_WATCH_LISTENER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoWatchListenersFound(String arg0, String arg1) {
      String id = "NO_WATCH_LISTENERS_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeletedListener(String arg0, String arg1) {
      String id = "DELETED_LISTENER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanNotFound(String arg0) {
      String id = "MBEAN_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerNotFound(String arg0) {
      String id = "LISTENER_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerRemoved(String arg0, String arg1) {
      String id = "LISTENER_REMOVED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInsecureProtocol() {
      String id = "INSECURE_PROTOCOL";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRuntimeMBSNotEnabled() {
      String id = "RUNTIME_MBS_NOT_ENABLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditMBSNotEnabled() {
      String id = "EDIT_MBS_NOT_ENABLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompatibilityMBSNotEnabled() {
      String id = "COMPATIBILITY_MBS_NOT_ENABLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeMBSNotEnabled() {
      String id = "DOMAIN_RUNTIME_MBS_NOT_ENABLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnterUsername() {
      String id = "ENTER_USERNAME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnterPassword() {
      String id = "ENTER_PASSWORD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnterNMPassword() {
      String id = "ENTER_NM_PASSWORD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnterURL(String arg0) {
      String id = "ENTER_URL";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseDefaultURL(String arg0) {
      String id = "USE_DEFAULT_URL";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEmptyUsername() {
      String id = "EMPTY_USERNAME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEmptyPassword() {
      String id = "EMPTY_PASSWORD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidUsernamePasswd() {
      String id = "INVALID_USERNAME_PASSWORD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCdingToBean() {
      String id = "ERROR_CDING_TO_BEAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDeletingABean() {
      String id = "ERROR_DELETING_A_BEAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceNotFound(String arg0) {
      String id = "INSTANCE_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorBrowsingBeans() {
      String id = "ERROR_BROWSING_BEANS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanExceptionOccurred() {
      String id = "MBEAN_EXCEPTION_OCCURRED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotCDToAttribute(String arg0) {
      String id = "CANNOT_CD_TO_ATTRIBUTE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeNotFound(String arg0) {
      String id = "ATTRIBUTE_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRealmInstanceNotFound() {
      String id = "REALM_INSTANCE_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToCustomTree() {
      String id = "LOCATION_CHANGED_TO_CUSTOM_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainCustomTree() {
      String id = "LOCATION_CHANGED_TO_DOMAIN_CUSTOM_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainCustomCommandNotOnMS() {
      String id = "DomainCustomCommandNotOnMS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToEditCustomTree() {
      String id = "LOCATION_CHANGED_TO_EDIT_CUSTOM_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditCustomCommandNotOnMS() {
      String id = "EditCustomCommandNotOnMS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToConfigTree(String arg0) {
      String id = "LOCATION_CHANGED_TO_CONFIG_TREE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToJndiTree() {
      String id = "LOCATION_CHANGED_TO_JNDI_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToRuntimeTree(String arg0) {
      String id = "LOCATION_CHANGED_TO_RUNTIME_TREE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanHasSlash() {
      String id = "MBEAN_HAS_SLASH";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseCustomHelp() {
      String id = "USE_CUSTOM_HELP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseDomainCustomHelp() {
      String id = "USE_DOMAIN_CUSTOM_HELP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUseEditCustomHelp() {
      String id = "USE_EDIT_CUSTOM_HELP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoStubAvailable() {
      String id = "NO_STUB_AVAILABLE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInTree(String arg0) {
      String id = "ALREADY_IN_TREE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorBrowsingTree(String arg0) {
      String id = "ERROR_BROWSING_TREE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBrowsingBackToRoot() {
      String id = "BROWSING_BACK_TO_ROOT";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotFindJndiEntry(String arg0) {
      String id = "CANNOT_FIND_JNDI_ENTRY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindDomainRuntimeMBean() {
      String id = "COULD_NOT_FIND_DOMAIN_RUNTIME_MBEAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotChangeToConfigTree() {
      String id = "CANNOT_CHANGE_TO_CONFIG_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotChangeToRuntimeTree() {
      String id = "CANNOT_CHANGE_TO_RUNTIME_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotBrowsJNDIOfOtherServer() {
      String id = "CANNOT_BROWSE_JNDI_OF_OTHER_SERVER";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratingSingletonServices(String arg0, String arg1) {
      String id = "MIGRATING_SINGLETON_SERVICES";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutoMigrationMustBeEnabled() {
      String id = "AUTO_MIGRATION_MUST_BE_ENABLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratingJmsJta(String arg0, String arg1) {
      String id = "MIGRATING_JMS_JTA";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratingOnlyJta(String arg0, String arg1) {
      String id = "MIGRATING_ONLY_JTA";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotLocateServer(String arg0) {
      String id = "COULD_NOT_LOCATE_SERVER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerDoesNotBelongToCluster(String arg0) {
      String id = "SERVER_DOES_NOT_BELONG_TO_CLUSTER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationSucceeded() {
      String id = "MIGRATION_SUCCEEDED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationFailed(String arg0) {
      String id = "MIGRATION_FAILED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToGetMigrationCoordinator() {
      String id = "FAILED_TO_GET_MIGRATION_COORDINATOR";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedMigration() {
      String id = "FAILED_MIGRATION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotCreateNonChild() {
      String id = "CANNOT_CREATE_NON_CHILD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteNonChild() {
      String id = "CANNOT_DELETE_NON_CHILD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotCreateNonChild1(String arg0) {
      String id = "CANNOT_CREATE_NON_CHILD1";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteNonChild1(String arg0) {
      String id = "CANNOT_DELETE_NON_CHILD1";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteNonChild2(String arg0, String arg1) {
      String id = "CANNOT_DELETE_NON_CHILD2";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingAttribute(String arg0) {
      String id = "ERROR_GETTING_ATTRIBUTE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSettingAttribute(String arg0) {
      String id = "ERROR_SETTING_ATTRIBUTE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserConfigNotFound() {
      String id = "USER_CONFIG_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserConfigPropertyNotFound(String arg0) {
      String id = "USER_CONFIG_PROPERTY_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvokeNotApplicable() {
      String id = "INVOKE_NOT_APPLICABLE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotUseCommandOnMS(String arg0) {
      String id = "CANNOT_USE_COMMAND_ON_MS";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotUseCommandUnlessEditConfig(String arg0) {
      String id = "CANNOT_USE_COMMAND_UNLESS_EDIT_CONFIG";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanTypeMustBeNonNull() {
      String id = "MBEAN_TYPE_MUST_BE_NON_NULL";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getcouldNotDetermineCreate() {
      String id = "COULD_NOT_DETERMINE_CREATE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOptionalSingletonCreated(String arg0) {
      String id = "OPTIONAL_SINGLETON_CREATED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotCreateMBean() {
      String id = "COULD_NOT_CREATE_MBEAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChooseDifferentName(String arg0, String arg1) {
      String id = "CHOOSE_DIFFERENT_NAME";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotCreateUseEditTree() {
      String id = "CANNOT_CREATE_USE_EDIT_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanCreatedSuccessfully(String arg0, String arg1) {
      String id = "MBEAN_CREATED_SUCCESSFULLY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanDeletedSuccessfully(String arg0, String arg1) {
      String id = "MBEAN_DELETED_SUCCESSFULLY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanDeletedSuccessfully1(String arg0) {
      String id = "MBEAN_DELETED_SUCCESSFULLY1";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParameterMayNotBeNull(String arg0) {
      String id = "PARAMETER_MAY_NOT_BE_NULL";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProviderCreatedSuccessfully(String arg0, String arg1, String arg2) {
      String id = "PROVIDER_CREATED_SUCCESSFULLY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProviderDeletedSuccessfully(String arg0, String arg1) {
      String id = "PROVIDER_DELETED_SUCCESSFULLY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProviderClassNotFound() {
      String id = "PROVIDER_CLASS_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCreatingProvider() {
      String id = "ERROR_CREATING_PROVIDER";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetTargetIsForconfig() {
      String id = "GET_TARGET_IS_FOR_CONFIG";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMalformedMBeanPath() {
      String id = "MALFORMED_MBEAN_PATH";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingMBean() {
      String id = "ERROR_GETTING_MBEAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingMBeanArray() {
      String id = "ERROR_GETTING_MBEAN_ARRAY";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorInitializingEncryptionService() {
      String id = "ERROR_INITIALIZING_ENCRYPTION_SERVICE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorEncryptingValue() {
      String id = "ERROR_ENCRYPTING_VALUE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOnInvoke() {
      String id = "ERROR_ON_INVOKE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOnLs() {
      String id = "ERROR_ON_LS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOnLookup() {
      String id = "ERROR_ON_LOOKUP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingCustomBeans() {
      String id = "ERROR_GETTING_CUSTOM_BEANS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionTerminated() {
      String id = "EDIT_SESSION_TERMINATED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantCallEditFunctions() {
      String id = "CANT_CALL_EDIT_FUNCTIONS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNeedEditSessionFor(String arg0) {
      String id = "NEED_EDIT_SESSION_FOR";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartedEditSession() {
      String id = "STARTED_EDIT_SESSION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingEditSession() {
      String id = "STARTING_EDIT_SESSION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExclusiveSession() {
      String id = "EXCLUSIVE_SESSION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditLockHeld(String arg0) {
      String id = "EDIT_LOCK_HELD";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoPermissionForEdit(String arg0) {
      String id = "NO_PERMISSION_FOR_EDIT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavingChanges() {
      String id = "SAVING_CHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavedChanges() {
      String id = "SAVED_CHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoChangesYet() {
      String id = "NO_CHANGES_YET";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidChanges() {
      String id = "INVALID_CHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActivatingChanges() {
      String id = "ACTIVATING_CHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActivatingChangesNonBlocking() {
      String id = "ACTIVATING_CHANGES_NON_BLOCKING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActivationComplete() {
      String id = "ACTIVATION_COMPLETE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActivationTaskCreated() {
      String id = "ACTIVATION_TASK_CREATED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdministratorRequiredString() {
      String id = "ADMINISTRATOR_REQUIRED_STRING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReloginRequired() {
      String id = "RELOGIN_REQUIRED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDisconnecting() {
      String id = "ERROR_DISCONNECTING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorActivating() {
      String id = "ERROR_ACTIVATING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNonDynamicAttributes(String arg0) {
      String id = "NON_DYNAMIC_ATTRIBUTES";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanChanged(String arg0) {
      String id = "MBEAN_CHANGED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributesChanged(String arg0) {
      String id = "ATTRIBUTES_CHANGED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDiscardedAllChanges() {
      String id = "DISCARDED_ALL_CHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDiscardedAllInMemoryChanges() {
      String id = "DISCARDED_ALL_IN_MEMORYCHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartRequired() {
      String id = "RESTART_REQUIRED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSCRestartRequired(String arg0) {
      String id = "SC_RESTART_REQUIRED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartNotRequired() {
      String id = "RESTART_NOT_REQUIRED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartNotRequiredFor(String arg0) {
      String id = "RESTART_NOT_REQUIRED_FOR";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartRequiredFor(String arg0) {
      String id = "RESTART_REQUIRED_FOR";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingRestartInfo() {
      String id = "ERROR_GETTING_RESTART_INFO";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidatingChanges() {
      String id = "VALIDATING_CHANGES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidationSuccess() {
      String id = "ValidationSuccess";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidationErrors() {
      String id = "VALIDATION_ERRORS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsavedChangesAre() {
      String id = "UNSAVED_CHANGES_ARE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnactivatedChangesAre() {
      String id = "UNACTIVATED_CHANGES_ARE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanChanged2(String arg0) {
      String id = "MBEAN_CHANGED2";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationInvoked(String arg0) {
      String id = "OPERATION_INVOKED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeModified(String arg0) {
      String id = "ATTRIBUTE_MODIFIED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributesOldValue(String arg0) {
      String id = "ATTRIBUTES_OLD_VALUE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributesNewValue(String arg0) {
      String id = "ATTRIBUTES_NEW_VALUE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerRestartRequired(String arg0) {
      String id = "SERVER_RESTART_REQUIRED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEaseSyntaxEnabled() {
      String id = "EASE_SYNTAX_ENABLED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSpecifyPropertiesLocation() {
      String id = "SPECIFY_PROPERTIES_LOCATION";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoFileNameSpecified() {
      String id = "NO_FILE_NAME_SPECIFIED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotCreateDM() {
      String id = "COULD_NOT_CREATE_DM";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadingAppFrom(String arg0) {
      String id = "LOADING_APP_FROM";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckSubdirOfApp() {
      String id = "CHECK_SUBDIR_OF_APP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePlan(String arg0) {
      String id = "CREATE_PLAN";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindPlan() {
      String id = "COULD_NOT_FIND_PLAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadedAppAndPlan(String arg0, String arg1) {
      String id = "LOADED_APP_AND_PLAN";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanVariableAssigned(String arg0) {
      String id = "PLAN_VARIABLE_ASSIGNED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotInitConfig() {
      String id = "COULD_NOT_INIT_CONFIG";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotReadConfig() {
      String id = "COULD_NOT_REAd_CONFIG";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidModule() {
      String id = "INVALID_MODULE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAppPathIsDir(String arg0) {
      String id = "APP_PATH_IS_DIR";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanDoesNotExist(String arg0) {
      String id = "PLAN_DOES_NOT_EXIST";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanPathEvaluated(String arg0) {
      String id = "PLAN_PATH_EVALUATED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindMatchingTargets(String arg0, String arg1) {
      String id = "COULD_NOT_FIND_MATCHING_TARGETS";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindMatchingTargets(String arg0) {
      String id = "COULD_NOT_FIND_MATCHING_TARGETS2";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDistributingApplication(String arg0, String arg1) {
      String id = "DISTRIBUTING_APPLICATION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployingApplication(String arg0, String arg1, boolean arg2) {
      String id = "DEPLOYING_APPLICATION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeployingApplicationWithPlan(String arg0, String arg1) {
      String id = "DEPLOYING_APPLICATION_WITH_PLAN";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnsureSubdirOfApp() {
      String id = "ENSURE_SUBDIR_OF_APP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToDistributeApp(String arg0) {
      String id = "FAILED_TO_DISTRIBUTE_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAppDistributionComplete(String arg0) {
      String id = "APP_DISTRIBUTION_COMPLETE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAppDeploymentComplete(String arg0) {
      String id = "APP_DEPLOYMENT_COMPLETE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToDeployApp(String arg0) {
      String id = "FAILED_TO_DEPLOY_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToUndeployApp(String arg0) {
      String id = "FAILED_TO_UNDEPLOY_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToUpdateApp(String arg0) {
      String id = "FAILED_TO_UPDATE_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToStartApp(String arg0) {
      String id = "FAILED_TO_START_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToStopApp(String arg0) {
      String id = "FAILED_TO_STOP_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDistributionStarted() {
      String id = "DISTRIBUTION_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationStarted() {
      String id = "APPLICATION_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentStarted() {
      String id = "DEPLOYMENT_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedeploymentStarted() {
      String id = "REDEPLOYMENT_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndeploymentStarted() {
      String id = "UNDEPLOYMENT_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopStarted() {
      String id = "STOP_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateStarted() {
      String id = "UPDATE_STARTED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDistributingApp(String arg0) {
      String id = "Error_Distributing_App";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDeployingApp(String arg0) {
      String id = "ERROR_DEPLOYING_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionInProgress(String arg0) {
      String id = "EDIT_SESSION_IN_PROGRESS";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationPathNotFound(String arg0) {
      String id = "APPLICATION_PATH_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentFailed() {
      String id = "DeploymentFailed";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedError(String arg0) {
      String id = "UnexpectedError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActionTimedOut(long arg0) {
      String id = "ActionTimedOut";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedeployingApp(String arg0) {
      String id = "REDEPLOYING_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndeployingApp(String arg0) {
      String id = "UNDEPLOYING_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdatingApp(String arg0) {
      String id = "UPDATING_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToRedeployApp(String arg0) {
      String id = "FAILED_TO_REDEPLOY_APP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppRedeploy(String arg0) {
      String id = "COMPLETED_APP_REDEPLOY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppUpdate(String arg0) {
      String id = "COMPLETED_APP_UPDATE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppStart(String arg0) {
      String id = "COMPLETED_APP_START";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppStop(String arg0) {
      String id = "COMPLETED_APP_STOP";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompletedAppUndeploy(String arg0) {
      String id = "COMPLETED_APP_UNDEPLOY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePlanTrue() {
      String id = "Create_PlanTrue";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingApplication(String arg0) {
      String id = "STARTING_APPLICATION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoppingApplication(String arg0) {
      String id = "STOPPING_APPLICATION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanPathNeededToUpdate() {
      String id = "PLAN_PATH_NEEDED_TO_UPDATE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadingConfiguration(String arg0) {
      String id = "LOADING_CONFIGURATION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotLocateConfigXml() {
      String id = "CANNOT_LOCATE_CONFIG_Xml";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionLoadingConfig() {
      String id = "IOExceptionLoadingConfig";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToLoadConfigXml() {
      String id = "Failed_ToLoad_Config_Xml";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionWritingDeploymentScript(String arg0) {
      String id = "IOEXCEPTION_WRITING_DEPLOYMENT_SCRIPT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConvertingResources() {
      String id = "CONVERTING_RESOURCES";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigToScriptComplete(String arg0, String arg1) {
      String id = "CONFIG_TO_SCRIPT_COMPLETE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPasswordDisclaimer(String arg0, String arg1) {
      String id = "PASSWORD_DISCLAIMER";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsAChild(String arg0) {
      String id = "BEAN_IS_A_CHILD";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsDefaulted(String arg0) {
      String id = "BEAN_IS_DEFAULTED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBeanIsAReference(String arg0) {
      String id = "BEAN_IS_A_REFERENCE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionWalkingTree() {
      String id = "EXCEPTION_WALKING_TREE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContinueInBean() {
      String id = "ContinueInBean";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScriptAlreadyExists(String arg0) {
      String id = "SCRIPT_ALREADY_EXISTS";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFindingAppBean() {
      String id = "ERROR_FINDING_APP_BEAN";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFindingParent(String arg0) {
      String id = "ERROR_FINDING_PARENT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingBeanName(String arg0) {
      String id = "ERROR_GETTING_BEAN_NAME";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeSet(String arg0, String arg1) {
      String id = "ATTRIBUTE_SET";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorMakingSetBeans() {
      String id = "ERROR_MAKING_SET_BEANS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionWritingFile(String arg0) {
      String id = "IOEXCEPTION_WRITING_FILE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindBeanOfType(String arg0) {
      String id = "FIND_BEAN_OF_TYPE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNowFinding(String arg0) {
      String id = "NOW_FINDING";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindByTypeResultEmpty() {
      String id = "FIND_BY_TYPE_RESULT_EMPTY";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindByInstanceResultEmpty() {
      String id = "FIND_BY_INSTANCE_RESULT_EMPTY";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidUserOrPassword() {
      String id = "INVALID_USER_OR_PASSWORD";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMalformedManagedServerURL(String arg0) {
      String id = "MALFORMED_MANAGED_SERVER_URL";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNamingException(String arg0) {
      String id = "NAMING_EXCEPTION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentLocationNoLongerExists(String arg0, String arg1) {
      String id = "Current_Location_No_LongerE_xists";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRetrievingAttributeNameValue() {
      String id = "Error_Retrieving_Attribute_Name_Value";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRetrievingOperationInfo() {
      String id = "ERROR_RETRIEVING_OPERATION_INFO";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyRecording(String arg0) {
      String id = "AlreadyRecording";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutputFileIsDir(String arg0) {
      String id = "OutputFileIsDir";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartedRecording(String arg0) {
      String id = "STARTED_RECORDING";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoppedRecording(String arg0) {
      String id = "STOPPED_RECORDING";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotRecording() {
      String id = "NOT_RECORDING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWhileRecording() {
      String id = "ERROR_WHILE_RECORDING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWhileStoppingRecording() {
      String id = "ERROR_WHILE_STOPPING_RECORDING";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWritingCommand(String arg0) {
      String id = "ERROR_WRITING_COMMAND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWroteIniFile(String arg0) {
      String id = "WROTE_INI_FILE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWritingIni() {
      String id = "ERROR_WRITING_INI";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNeedWlsOrNm() {
      String id = "NEED_WLS_OR_NM";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernamePasswordStored(String arg0, String arg1, String arg2) {
      String id = "USERNAME_PASSWORD_STORED";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoAttrDescription(String arg0) {
      String id = "NoAttrDescription";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoChildBeans(String arg0) {
      String id = "NoChildBeans";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsupportedCommand(String arg0) {
      String id = "UnsupportedCommand";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingMBeanInfo(String arg0) {
      String id = "ErrorGettingMBeanInfo";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRequestedThreadDump() {
      String id = "RequestedThreadDump";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpNeedsConnection() {
      String id = "ThreadDumpNeedsConnection";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpServerNotRunning() {
      String id = "ThreadDumpServerNotRunning";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpHeader(String arg0, Date arg1) {
      String id = "ThreadDumpHeader";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpHeader2(String arg0) {
      String id = "ThreadDumpHeader2";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpFooter(String arg0, String arg1) {
      String id = "ThreadDumpFooter";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpFileError() {
      String id = "ThreadDumpFileError";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotCreateParentDir(String arg0) {
      String id = "CouldNotCreateParentDir";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutputFileIsNull() {
      String id = "OutputFileIsNull";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyRedirecting(String arg0, String arg1) {
      String id = "AlreadyRedirecting";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRedirectFileNotFound(String arg0) {
      String id = "RedirectFileNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopRedirect(String arg0) {
      String id = "StopRedirect";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotRedirecting() {
      String id = "NotRedirecting";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOExceptionStoppingRedirect() {
      String id = "IOExceptionStoppingRedirect";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathNotFound() {
      String id = "PathNotFound";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingPath() {
      String id = "ErrorGettingPath";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLostConnection() {
      String id = "LostConnection";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigRuntimeServerNotEnabled() {
      String id = "ConfigRuntimeServerNotEnabled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToConfigRuntime() {
      String id = "LocationChangedToConfigRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToPartitionRoot() {
      String id = "LocationChangedToPartitionRoot";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInConfigRuntime() {
      String id = "AlreadyInConfigRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToConfigRuntime() {
      String id = "ErrorTraversingToConfigRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRuntimeServerNotEnabled() {
      String id = "RuntimeServerNotEnabled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToServerRuntime() {
      String id = "LocationChangedToServerRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToPartitionRuntimeRoot() {
      String id = "LocationChangedToPartitionRuntimeRoot";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInServerRuntime() {
      String id = "AlreadyInServerRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToServerRuntime() {
      String id = "ErrorTraversingToServerRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeNotAvailableOnMS() {
      String id = "DomainRuntimeNotAvailableOnMS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeServerNotEnabled() {
      String id = "DomainRuntimeServerNotEnabled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainConfig() {
      String id = "LocationChangedToDomainConfig";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainPartitionRuntimeRoot() {
      String id = "LocationChangedToDomainPartitionRuntimeRoot";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainRuntime() {
      String id = "LocationChangedToDomainRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToDomainPartitionRuntimeRootRuntime() {
      String id = "LocationChangedToDomainPartitionRuntimeRootRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInDomainConfig() {
      String id = "AlreadyInDomainConfig";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInDomainRuntime() {
      String id = "AlreadyInDomainRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToDomainConfig() {
      String id = "ErrorTraversingToDomainConfig";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToDomainRuntime() {
      String id = "ErrorTraversingToDomainRuntime";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditNotAvailableOnMS() {
      String id = "EditNotAvailableOnMS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditServerNotEnabled() {
      String id = "EditServerNotEnabled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLocationChangedToEdit() {
      String id = "LocationChangedToEdit";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionInProgress() {
      String id = "EditSessionInProgress";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyInEdit() {
      String id = "AlreadyInEdit";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorTraversingToEdit() {
      String id = "ErrorTraversingToEdit";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJSR77NotAvailableOnMS() {
      String id = "JSR77NotAvailableOnMS";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJSR77ServerNotEnabled() {
      String id = "JSR77ServerNotEnabled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultingNMUsername(String arg0) {
      String id = "DefaultingNMUsername";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultingNMPassword(String arg0) {
      String id = "DefaultingNMPassword";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectingToNodeManager() {
      String id = "ConnectingToNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectedToNodeManager() {
      String id = "ConnectedToNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisconnectedFromNodeManager() {
      String id = "DisconnectedFromNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotConnectToNodeManager() {
      String id = "CouldNotConnectToNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentlyConnectedNM(String arg0) {
      String id = "CurrentlyConnectedNM";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConnectedNM() {
      String id = "NotConnectedNM";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMVersion(String arg0) {
      String id = "NMVersion";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKillingServer(String arg0) {
      String id = "KillingServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorKillingServer(String arg0) {
      String id = "ErrorKillingServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKilledServer(String arg0) {
      String id = "KilledServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnrollingMachineInDomain(String arg0) {
      String id = "EnrollingMachineInDomain";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnrolledMachineInDomain(String arg0) {
      String id = "EnrolledMachineInDomain";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToEnrolMachineInDomain() {
      String id = "FailedToEnrolMachineInDomain";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnrollSaltDownloadNotSupported(String arg0) {
      String id = "EnrollSaltDownloadNotSupported";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingServer(String arg0) {
      String id = "StartingServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingServer(String arg0) {
      String id = "ErrorStartingServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartedServer(String arg0) {
      String id = "StartedServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullOrEmptyServerName() {
      String id = "NullOrEmptyServerName";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServerOrCoherenceMBean(String arg0) {
      String id = "NoServerOrCoherenceMBean";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratedBootProperties(String arg0) {
      String id = "GeneratedBootProperties";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSavingBootProperties() {
      String id = "ErrorSavingBootProperties";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGeneratedStartupProperties(String arg0) {
      String id = "GeneratedStartupProperties";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSavingStartupProperties() {
      String id = "ErrorSavingStartupProperties";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingNMClient() {
      String id = "ErrorGettingNMClient";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerAlreadyExists(String arg0) {
      String id = "ListenerAlreadyExists";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoListenersConfigured() {
      String id = "NoListenersConfigured";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoPlanVariablesOverwritten() {
      String id = "NoPlanVariablesOverwritten";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlanVariableOverwritten(String arg0, String arg1) {
      String id = "PlanVariableOverwritten";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchPlanVariable(String arg0) {
      String id = "NoSuchPlanVariable";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchModuleOverride(String arg0) {
      String id = "NoSuchModuleOverride";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoModuleOverrides() {
      String id = "NoModuleOverrides";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingModuleDescriptor(String arg0, String arg1) {
      String id = "CreatingModuleDescriptor";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedModuleDescriptor(String arg0, String arg1) {
      String id = "CreatedModuleDescriptor";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyedModuleOverride(String arg0) {
      String id = "DestroyedModuleOverride";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingVariableAssignment(String arg0, String arg1) {
      String id = "CreatingVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatedVariableAssignment(String arg0) {
      String id = "CreatedVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCreatingVariableAssignment(String arg0) {
      String id = "ErrorCreatingVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGetingVariableAssignment(String arg0) {
      String id = "ErrorGetingVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyingVariableAssignment(String arg0, String arg1) {
      String id = "DestroyingVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotFindModuleDescriptor() {
      String id = "CannotFindModuleDescriptor";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyedVariableAssignment(String arg0) {
      String id = "DestroyedVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDestroyingVariableAssignment(String arg0) {
      String id = "ErrorDestroyingVariableAssignment";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParentIsNotModuleOverride() {
      String id = "ParentIsNotModuleOverride";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotGetDeployableObject() {
      String id = "CouldNotGetDeployableObject";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotOpenDescriptorUri(String arg0) {
      String id = "CouldNotOpenDescriptorUri";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorBuildingDConfigBean(String arg0) {
      String id = "ErrorBuildingDConfigBean";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getModuleDescriptorBeanDoesNotExist(String arg0, String arg1) {
      String id = "ModuleDescriptorBeanDoesNotExist";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unrecognizedOption(String arg0) {
      String id = "UnrecognizedOption";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroupOrResourceNameNull() {
      String id = "GROUP_OR_RES_NAME_NULL";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateGroupName(String arg0) {
      String id = "DUPLICATE_GROUP_NAME";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingResourceBundle() {
      String id = "ERROR_GETTING_RESOURCE_BUNDLE";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGroupNameNotExist(String arg0) {
      String id = "GROUP_NAME_NOT_EXIST";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateCommandName(String arg0) {
      String id = "DUPLICATE_COMMAND_NAME";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoResourceFoundForCmd(String arg0, String arg1) {
      String id = "NO_RESOURCE_FOUND_FOR_CMD";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConnectedAdminServer() {
      String id = "NotConnectedAdminServer";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPythonExecError(String arg0, Exception arg1) {
      String id = "PythonExecError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPythonImportError(String arg0, Exception arg1) {
      String id = "PythonImportError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsupportedServerType(String arg0) {
      String id = "UnsupportedServerType";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInherited() {
      String id = "Inherited";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOccurred(String arg0, String arg1) {
      String id = "ErrorOccurred";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorOccurredUseDumpStack(String arg0, String arg1) {
      String id = "ErrorOccurredUseDumpStack";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndoChanges() {
      String id = "UndoChanges";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopChanges() {
      String id = "StopChanges";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCancelChanges() {
      String id = "CancelChanges";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUndoNotPerformed() {
      String id = "UndoNotPerformed";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditStopped() {
      String id = "EditStopped";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditNotStopped() {
      String id = "EditNotStopped";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditCancelled() {
      String id = "EditCancelled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLaunchingNodeManagerMessage() {
      String id = "LaunchingNodeManagerMessage";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerAlreadyStarted() {
      String id = "NodeManagerAlreadyStarted";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLaunchedNodeManager(String arg0) {
      String id = "LaunchedNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotConfiguredWithDomain(String arg0) {
      String id = "NotConfiguredWithDomain";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotFindWLHOME() {
      String id = "CannotFindWLHOME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWaitingForNodeManager() {
      String id = "WaitingForNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingNodeManager() {
      String id = "ErrorStartingNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionFailed() {
      String id = "ConnectionFailed";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerStarting() {
      String id = "NodeManagerStarting";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerStopped() {
      String id = "NodeManagerStopped";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopNodeManagerComplete(String arg0) {
      String id = "StopNodeManagerComplete";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStoppingNodeManager() {
      String id = "ErrorStoppingNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotStopNodeManager() {
      String id = "CanNotStopNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemStoppingNodeManager() {
      String id = "ProblemStoppingNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunningNMScriptMessage(String arg0, String arg1) {
      String id = "RunningNMScriptMessage";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExportedDiagnosticData(String arg0) {
      String id = "ExportedDiagnosticData";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFormattedSystemResourceControlInfoHeader() {
      String id = "FormattedSystemResourceControlInfoHeader";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFormattedSystemResourceControlInfo(String arg0, boolean arg1, boolean arg2) {
      String id = "FormattedSystemResourceControlInfo";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRetrievingImage(String arg0, String arg1) {
      String id = "RetrievingImage";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRetrievingImageEntry(String arg0, String arg1, String arg2) {
      String id = "RetrievingImageEntry";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCapturingImage() {
      String id = "CapturingImage";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCapturedImage() {
      String id = "CapturedImage";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageFromServer(String arg0, String arg1, String arg2) {
      String id = "CaptureImageFromServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageEntryFromServer(String arg0, String arg1, String arg2, String arg3) {
      String id = "CaptureImageEntryFromServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageFromServerPartition(String arg0, String arg1, String arg2, String arg3) {
      String id = "CaptureImageFromServerPartition";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureImageEntryFromServerPartition(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "CaptureImageEntryFromServerPartition";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCaptureAndSaveImage() {
      String id = "CaptureAndSaveImage";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String beginPurgeCapturedImages() {
      String id = "beginPurgeCapturedImages";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String endPurgeCapturedImages() {
      String id = "endPurgeCapturedImages";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToCreateDirectory(String arg0) {
      String id = "UnableToCreateDirectory";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyConnected() {
      String id = "AlreadyConnected";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemComponentNotFound(String arg0) {
      String id = "SystemComponentNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentNotFound(String arg0) {
      String id = "ComponentNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMachineNotFound(String arg0) {
      String id = "MachineNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentTypeNotFound(String arg0, String arg1) {
      String id = "ComponentTypeNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThereIsNoCompConfDefined(String arg0) {
      String id = "ThereIsNoCompConfDefined";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingComponentInCompConf(String arg0, String arg1) {
      String id = "StartingComponentInCompConf";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemStartingComponentConfiguration(String arg0) {
      String id = "ProblemStartingComponentConfiguration";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartSystemComponent(String arg0) {
      String id = "CouldNotStartSystemComponent";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableStartSomeComps(String arg0) {
      String id = "UnableStartSomeComps";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentInComponentConfigurationStarted(String arg0) {
      String id = "NoComponentInComponentConfigurationStarted";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllCompsStartedSuccessfully(String arg0) {
      String id = "AllCompsStartedSuccessfully";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentConfigurationConfigured(String arg0) {
      String id = "NoComponentConfigurationConfigured";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompsInConfiguration(String arg0, String arg1) {
      String id = "CompsInConfiguration";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateOfComponents() {
      String id = "StateOfComponents";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotActiveComponents(String arg0) {
      String id = "NotActiveComponents";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownComponentConfiguration(String arg0) {
      String id = "getShutdownComponentConfiguration";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownCompConfRequiresName() {
      String id = "ShutdownCompConfRequiresName";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownCompConf() {
      String id = "ErrorShuttingDownCompConf";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCompConfShutdownIssued(String arg0) {
      String id = "CompConfShutdownIssued";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartingComponentInCompConf(String arg0, String arg1) {
      String id = "SoftRestartingComponentInCompConf";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemSoftRestartingComponentConfiguration(String arg0) {
      String id = "ProblemSoftRestartingComponentConfiguration";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotSoftRestartSystemComponent(String arg0) {
      String id = "CouldNotSoftRestartSystemComponent";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableSoftRestartSomeComps(String arg0) {
      String id = "UnableSoftRestartSomeComps";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentInComponentConfigurationSoftRestarted(String arg0) {
      String id = "NoComponentInComponentConfigurationSoftRestarted";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllCompsSoftRestartedSuccessfully(String arg0) {
      String id = "AllCompsSoftRestartedSuccessfully";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentConfigurationStartStatus() {
      String id = "ComponentConfigurationStartStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityConfigurationNotFound(String arg0) {
      String id = "SecurityConfigurationNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddressAndPortNotFound() {
      String id = "AddressAndPortNotFound";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernameAndPasswordNotFound() {
      String id = "UsernameAndPasswordNotFound";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMTypeNotFound() {
      String id = "NMTypeNotFound";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotLoadProperties() {
      String id = "CanNotLoadProperties";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImageCreated(String arg0) {
      String id = "ImageCreated";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImageCaptureFailed(String arg0) {
      String id = "ImageCaptureFailed";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindingNameInRegisteredInstances(String arg0) {
      String id = "FindingNameInRegisteredInstances";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFindingNameInMBeanTypes(String arg0) {
      String id = "FindingNameInMBeanTypes";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoResultsFound() {
      String id = "NoResultsFound";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRunningProcess(String arg0) {
      String id = "ErrorRunningProcess";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKillingProcess(String arg0) {
      String id = "KillingProcess";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingProcess(String arg0) {
      String id = "ErrorGettingProcess";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStatus() {
      String id = "CurrentStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentCommandType(String arg0) {
      String id = "DeploymentCommandType";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentState(String arg0) {
      String id = "DeploymentState";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentNoMsg() {
      String id = "DeploymentNoMsg";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeploymentMsg(String arg0) {
      String id = "DeploymentMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToGetValue(String arg0) {
      String id = "FailedToGetValue";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingServersInCluster(String arg0, String arg1) {
      String id = "StartingServersInCluster";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAllServersStartedSuccessfully(String arg0) {
      String id = "AllServersStartedSuccessfully";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartStatusTask(String arg0, String arg1) {
      String id = "ServerStartStatusTask";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStartStatus() {
      String id = "ClusterStartStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNameStarted(String arg0) {
      String id = "ServerNameStarted";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartStatus() {
      String id = "ServerStartStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoUserOrPasswordBlocking(String arg0) {
      String id = "NoUserOrPasswordBlocking";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedExceptionRetrying() {
      String id = "UnexpectedExceptionRetrying";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotConnectToServer(String arg0) {
      String id = "CouldNotConnectToServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeNamesAndValues() {
      String id = "AttributeNamesAndValues";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationsOnThisMBean() {
      String id = "OperationsOnThisMBean";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorViewingTheMBean() {
      String id = "ErrorViewingTheMBean";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerFileNotLocated(String arg0) {
      String id = "ListenerFileNotLocated";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStateOfServer(String arg0, String arg1) {
      String id = "CurrentStateOfServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingServerState() {
      String id = "ErrorGettingServerState";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServerWithName(String arg0) {
      String id = "NoServerWithName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminServer() {
      String id = "ShouldBeConnectedToAdminServer";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServersInCluster(String arg0, String arg1) {
      String id = "ServersInCluster";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStateOfServers() {
      String id = "StateOfServers";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotActiveServers(String arg0) {
      String id = "NotActiveServers";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerResumedSuccessfully(String arg0) {
      String id = "ServerResumedSuccessfully";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToResume(String arg0, String arg1) {
      String id = "FailedToResume";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerResumeTask(String arg0, String arg1) {
      String id = "ServerResumeTask";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCallResumeStatus() {
      String id = "CallResumeStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerSuspendedSuccessfully(String arg0) {
      String id = "ServerSuspendedSuccessfully";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedToSuspendServer(String arg0, String arg1) {
      String id = "FailedToSuspendServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerSuspendTask(String arg0, String arg1) {
      String id = "ServerSuspendTask";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCallSuspendStatus() {
      String id = "CallSuspendStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingWLSServer() {
      String id = "StartingWLSServer";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartedSuccessfully() {
      String id = "ServerStartedSuccessfully";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckServerOutput() {
      String id = "CheckServerOutput";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindRuntimeMBean() {
      String id = "CouldNotFindRuntimeMBean";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorPopulatingObjectNames() {
      String id = "ErrorPopulatingObjectNames";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCheckingSlashes() {
      String id = "ErrorCheckingSlashes";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidServerName(String arg0) {
      String id = "InvalidServerName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDomainRuntimeServiceAccess() {
      String id = "InvalidDomainRuntimeServiceAccess";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRunningServerOrClusterNotFound(String arg0) {
      String id = "RunningServerOrClusterNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainRuntimeServiceNotFound(String arg0) {
      String id = "DomainRuntimeServiceNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemResourceNotExist(String arg0, String arg1) {
      String id = "SystemResourceNotExist";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWarnSystemResourceNotExist(String arg0, String arg1) {
      String id = "WarnSystemResourceNotExist";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSystemResourceExistsOnServers(String arg0, String arg1) {
      String id = "SystemResourceExistsOnServers";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataNullInputDirName() {
      String id = "MergeDiagnosticDataNullInputDirName";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInputDirDoesNotExist(String arg0) {
      String id = "MergeDiagnosticDataInputDirDoesNotExist";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInvalidOutputFileName(String arg0) {
      String id = "MergeDiagnosticDataInvalidOutputFileName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataStart(String arg0) {
      String id = "MergeDiagnosticDataStart";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataOpenFile(String arg0) {
      String id = "MergeDiagnosticDataOpenFile";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataBuildKeySet() {
      String id = "MergeDiagnosticDataBuildKeySet";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticTotalMergedKeys(int arg0) {
      String id = "MergeDiagnosticTotalMergedKeys";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInvalidDataSet(String arg0) {
      String id = "MergeDiagnosticDataInvalidDataSet";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataUnexpectedFormat(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "MergeDiagnosticDataUnexpectedFormat";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataFileError(String arg0, Throwable arg1) {
      String id = "MergeDiagnosticDataFileError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataTimestampParseError(String arg0, String arg1) {
      String id = "MergeDiagnosticDataTimestampParseError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataUnrecognizedFileFormat(String arg0) {
      String id = "MergeDiagnosticDataUnrecognizedFileFormat";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataInputFileNotFound(String arg0) {
      String id = "MergeDiagnosticDataInputFileNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMergeDiagnosticDataComplete() {
      String id = "MergeDiagnosticDataComplete";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagosticDataInstanceSetChanged(String arg0) {
      String id = "DumpDiagosticDataInstanceSetChanged";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagnosticDataNewCaptureFile(String arg0) {
      String id = "DumpDiagnosticDataNewCaptureFile";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagonsticDataCaptureStart(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      String id = "DumpDiagonsticDataCaptureStart";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagnosticDataCaptureComplete() {
      String id = "DumpDiagnosticDataCaptureComplete";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDeterminingIfCreate() {
      String id = "ErrorDeterminingIfCreate";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingCreator() {
      String id = "ErrorGettingCreator";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingInterfaceClassName() {
      String id = "ErrorGettingInterfaceClassName";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorInScanningForAttrs() {
      String id = "ErrorInScanningForAttrs";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingAttributes() {
      String id = "ErrorGettingAttributes";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWalkingTheTree() {
      String id = "ErrorWalkingTheTree";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFindingTheMBean() {
      String id = "ErrorFindingTheMBean";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionWalkingBean(String arg0, String arg1) {
      String id = "ExceptionWalkingBean";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHarvesterNotEnabled() {
      String id = "HarvesterNotEnabled";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNotRunning(String arg0) {
      String id = "ServerNotRunning";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotFindServerInstance(String arg0) {
      String id = "CanNotFindServerInstance";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifeCycledException() {
      String id = "ServerLifeCycledException";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotFindClusterInstance() {
      String id = "CanNotFindClusterInstance";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownServer() {
      String id = "ErrorShuttingDownServer";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemEnrollingMachine() {
      String id = "ProblemEnrollingMachine";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListenerName(String arg0) {
      String id = "ListenerName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanChangedListener(String arg0) {
      String id = "MBeanChangedListener";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeChanged(String arg0) {
      String id = "AttributeChanged";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAttributeValueChanged(String arg0, String arg1) {
      String id = "AttributeValueChanged";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanName(String arg0) {
      String id = "MBeanName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanUnregistered() {
      String id = "MBeanUnregistered";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorAddingEditListener() {
      String id = "ErrorAddingEditListener";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorAddingCompatibilityChangeListener() {
      String id = "ErrorAddingCompatibilityChangeListener";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingMBeanInfoForMBean() {
      String id = "ErrorGettingMBeanInfoForMBean";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingCustomMBeans() {
      String id = "ErrorGettingCustomMBeans";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToDetermineConfig() {
      String id = "UnableToDetermineConfig";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrrorFileNameRequired() {
      String id = "ErrrorFileNameRequired";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFileNotExist(String arg0) {
      String id = "ErrorFileNotExist";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthenticationFailed() {
      String id = "AuthenticationFailed";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorConnectingToServer() {
      String id = "ErrorConnectingToServer";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUsernameOrPasswordIncorrect() {
      String id = "UsernameOrPasswordIncorrect";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getURLIsMalformed() {
      String id = "URLIsMalformed";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotConnectViaHTTP() {
      String id = "CanNotConnectViaHTTP";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotConnectViaSSL() {
      String id = "CanNotConnectViaSSL";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotConnectViaT3s() {
      String id = "CanNotConnectViaT3s";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingInitialContext(String arg0) {
      String id = "ErrorGettingInitialContext";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToAdminServer(String arg0, String arg1) {
      String id = "ConnectToAdminServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToManaged(String arg0, String arg1) {
      String id = "ConnectToManaged";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectToPartition(String arg0) {
      String id = "ConnectToPartition";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartServer() {
      String id = "CouldNotStartServer";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartServerTimeout() {
      String id = "CouldNotStartServerTimeout";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingServerJVM() {
      String id = "ErrorStartingServerJVM";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRootDirectoryNotEmpty(String arg0) {
      String id = "RootDirectoryNotEmpty";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleExceptionSuspend(String arg0) {
      String id = "ServerLifecycleExceptionSuspend";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminSuspend() {
      String id = "ShouldBeConnectedToAdminSuspend";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleExceptionResume(String arg0) {
      String id = "ServerLifecycleExceptionResume";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminResume() {
      String id = "ShouldBeConnectedToAdminResume";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoClusterConfigured(String arg0) {
      String id = "NoClusterConfigured";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownConnection() {
      String id = "ErrorShuttingDownConnection";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThereIsNoClusterDefined(String arg0) {
      String id = "ThereIsNoClusterDefined";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownCluster() {
      String id = "ErrorShuttingDownCluster";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminStart() {
      String id = "ShouldBeConnectedToAdminStart";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemStartingCluster(String arg0) {
      String id = "ProblemStartingCluster";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingStatusFromLifecycle() {
      String id = "ErrorGettingStatusFromLifecycle";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotStartServerName(String arg0) {
      String id = "CouldNotStartServerName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableStartSomeServers(String arg0) {
      String id = "UnableStartSomeServers";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoServersInClusterStarted(String arg0) {
      String id = "NoServersInClusterStarted";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedAdminOrNM() {
      String id = "ShouldBeConnectedAdminOrNM";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerIsAlreadyRunning(String arg0) {
      String id = "ServerIsAlreadyRunning";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToLookupServerLifeCycle(String arg0) {
      String id = "UnableToLookupServerLifeCycle";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerFailedtoStart(String arg0) {
      String id = "ServerFailedtoStart";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityExceptionOccurred() {
      String id = "SecurityExceptionOccurred";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifecycleException() {
      String id = "ServerLifecycleException";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingServerPlain() {
      String id = "ErrorStartingServerPlain";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSpecifyCorrectEntityType() {
      String id = "SpecifyCorrectEntityType";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShouldBeConnectedToAdminPerform() {
      String id = "ShouldBeConnectedToAdminPerform";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartingServer(String arg0) {
      String id = "RestartingServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRestartingServer(String arg0) {
      String id = "ErrorRestartingServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartedServer(String arg0) {
      String id = "RestartedServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStateOfComponent(String arg0, String arg1) {
      String id = "CurrentStateOfComponent";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorGettingComponentState() {
      String id = "ErrorGettingComponentState";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoComponentWithName(String arg0) {
      String id = "NoComponentWithName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownComponent(String arg0) {
      String id = "SHUTDOWN_COMPONENT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownComponentRequiresName() {
      String id = "SHUTDOWN_COMPONENT_REQUIRES_NAME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThereIsNoComponentDefined(String arg0) {
      String id = "ThereIsNoComponentDefined";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentShutdownSuccess(String arg0) {
      String id = "COMPONENT_SHUTDOWN_SUCCESS";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorShuttingDownComponent() {
      String id = "ErrorShuttingDownComponent";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToLookupComponentLifeCycle(String arg0) {
      String id = "UnableToLookupComponentLifeCycle";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentNameStarted(String arg0) {
      String id = "ComponentNameStarted";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentFailedtoStart(String arg0, String arg1) {
      String id = "ComponentFailedtoStart";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentNotRunning(String arg0) {
      String id = "ComponentNotRunning";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotFindComponentInstance(String arg0) {
      String id = "CanNotFindComponentInstance";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentShutdownTaskAvailable(String arg0, String arg1) {
      String id = "COMPONENT_SHUTDOWN_TASK_AVAILABLE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentStartStatusTask(String arg0, String arg1) {
      String id = "ComponentStartStatusTask";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentStartStatus() {
      String id = "ComponentStartStatus";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentLifecycleException() {
      String id = "ComponentLifecycleException";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorStartingComponentPlain() {
      String id = "ErrorStartingComponentPlain";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingComponent(String arg0) {
      String id = "StartingComponent";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangesDeferred(String arg0) {
      String id = "ChangesDeferred";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangesDeferredError(String arg0) {
      String id = "ChangesDeferredError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueNotObjectName(String arg0) {
      String id = "ValueNotObjectName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoElementWithName(String arg0) {
      String id = "NoElementWithName";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartComponentRequiresName() {
      String id = "SOFT_RESTART_COMPONENT_REQUIRES_NAME";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSoftRestartFromManaged() {
      String id = "CANNOT_SOFT_RESTART_FROM_MANAGED";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartComponent(String arg0) {
      String id = "SOFT_RESTART_COMPONENT";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentSoftRestartTaskAvailable(String arg0, String arg1) {
      String id = "COMPONENT_SOFT_RESTART_TASK_AVAILABLE";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getComponentSoftRestartSuccess(String arg0) {
      String id = "COMPONENT_SOFT_RESTART_SUCCESS";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorSoftRestartingComponent() {
      String id = "ErrorSoftRestartingComponent";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagonsticDataDateHeader() {
      String id = "DumpDiagonsticDataDateHeader";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDumpDiagonsticDataTimestampHeader() {
      String id = "DumpDiagonsticDataTimestampHeader";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueMustBeTrueOrFalse(String arg0, String arg1) {
      String id = "ValueMustBeTrueOrFalse";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueMustBeTrueOrFalseDefaultTrue(String arg0, String arg1) {
      String id = "ValueMustBeTrueOrFalseDefaultTrue";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotLoadPropertiesInModule() {
      String id = "CannotLoadPropertiesInModule";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotSupportedWhileConnected() {
      String id = "NotSupportedWhileConnected";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotSupportedWhenNotConnected() {
      String id = "NotSupportedWhenNotConnected";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotSupportedInPartitionContext() {
      String id = "NotSupportedInPartitionContext";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoppedDraining(String arg0) {
      String id = "StoppedDraining";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNameHeader() {
      String id = "NameHeader";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueHeader() {
      String id = "ValueHeader";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotDeployApp(String arg0) {
      String id = "CouldNotDeployApp";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSettingAttributes(String arg0) {
      String id = "SettingAttributes";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingMBean(String arg0) {
      String id = "CreatingMBean";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorFromNodeManager() {
      String id = "ErrorFromNodeManager";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReadingDomain(String arg0) {
      String id = "ReadingDomain";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerDataAccessMsgText(String arg0) {
      String id = "ServerDataAccessMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPartitionDataAccessMsgText(String arg0, String arg1) {
      String id = "PartitionDataAccessMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPartitionNameMsgText(String arg0) {
      String id = "InvalidPartitionNameMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidLogicalNameMsgText(String arg0) {
      String id = "InvalidLogicalNameMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLDFModuleNameEmptyMsgText() {
      String id = "WLDFModuleNameEmptyMsg";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidCAMComponentName() {
      String id = "InvalidCAMComponentName";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCAMComponentNotFound(String arg0) {
      String id = "CAMComponentNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPullComponentChanges(String arg0, String arg1) {
      String id = "PullComponentChanges";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPullComponentChangesNotFound(String arg0, String arg1) {
      String id = "PullComponentChangesNotFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String enableOverwriteComponentChanges() {
      String id = "enableOverwriteComponentChanges";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String enableOverwriteComponentChangesError(String arg0) {
      String id = "enableOverwriteComponentChangesError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShowComponentChanges(String arg0, String arg1) {
      String id = "ShowComponentChanges";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShowComponentChangesNotSupport(String arg0, String arg1) {
      String id = "ShowComponentChangesNotSupport";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShowComponentChangesError(String arg0, String arg1, String arg2) {
      String id = "ShowComponentChangesError";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShowComponentChangesNoChangeFound(String arg0, String arg1) {
      String id = "ShowComponentChangesNoChangeFound";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getApplicationEmptyDirectory(String arg0) {
      String id = "APPLICATION_EMPTYDIRECTORY";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noTargetsAllowedForRGRGT() {
      String id = "NO_TARGETS_ALLOWED_FOR_RG_RGT";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorRestartingNM() {
      String id = "ErrorRestartingNM";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNodeManagerRestarted() {
      String id = "NodeManagerRestarted";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNamedEditSessionDoesNotExist() {
      String id = "NamedEditSessionDoesNotExist";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNamedEditSessionDoesNotExistCreate(String arg0) {
      String id = "NamedEditSessionDoesNotExistCreate";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionInfoListIntroduction() {
      String id = "EditSessionInfoListIntroduction";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionInfo(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
      String id = "EditSessionInfo";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getYes() {
      String id = "Yes";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNo() {
      String id = "No";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNone() {
      String id = "None";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyDefaultError() {
      String id = "DestroyDefaultError";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyCurrentEditTree() {
      String id = "DestroyCurrentEditTree";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCanNotResolve() {
      String id = "CanNotResolve";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditSessionNotExist(String arg0) {
      String id = "EditSessionNotExist";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutoResolveOK() {
      String id = "AutoResolveOK";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutoResolveConflicts() {
      String id = "AutoResolveConflicts";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutoResolveFail() {
      String id = "AutoResolveFail";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEditConfigIsStale() {
      String id = "EditConfigIsStale";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDependentBeansRestartMessage() {
      String id = "DependentBeansRestartMessage";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangedBean(String arg0) {
      String id = "ChangedBean";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDependentBean(String arg0) {
      String id = "DependentBean";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeprovisionPartitionStartMsg(String arg0, String arg1) {
      String id = "DeprovisionPartitionStartMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeprovisionPartitionCompletedMsg(String arg0, String arg1) {
      String id = "DeprovisionPartitionCompletedMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeprovisionPartitionErrorMsg(String arg0, String arg1, String arg2) {
      String id = "DeprovisionPartitionErrorMsg";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommandNotRunInPreVersion(String arg0) {
      String id = "CommandNotRunInPreVersion";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrintThreadDumpServer(String arg0) {
      String id = "PrintThreadDumpServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorPrintThreadDumpServer(String arg0) {
      String id = "ErrorPrintThreadDumpServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDonePrintThreadDumpServer(String arg0) {
      String id = "DonePrintThreadDumpServer";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFolderNotFound(String arg0) {
      String id = "FOLDER_NOT_FOUND";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTUserNotSpecified() {
      String id = "WLSTUserNotSpecified";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWLSTPasswordNotSpecified() {
      String id = "WLSTPasswordNotSpecified";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMUserNotSpecified() {
      String id = "NMUserNotSpecified";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMPasswordNotSpecified() {
      String id = "NMPasswordNotSpecified";
      String subsystem = "Management";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdateConfiguration(String arg0) {
      String id = "UpdateConfiguration";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidVerboseOption(String arg0) {
      String id = "INVALID_VERBOSE_OPTION";
      String subsystem = "Management";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
