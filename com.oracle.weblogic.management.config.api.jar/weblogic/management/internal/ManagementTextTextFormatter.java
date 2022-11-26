package weblogic.management.internal;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class ManagementTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public ManagementTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.management.internal.ManagementTextTextLocalizer", ManagementTextTextFormatter.class.getClassLoader());
   }

   public ManagementTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.management.internal.ManagementTextTextLocalizer", ManagementTextTextFormatter.class.getClassLoader());
   }

   public static ManagementTextTextFormatter getInstance() {
      return new ManagementTextTextFormatter();
   }

   public static ManagementTextTextFormatter getInstance(Locale l) {
      return new ManagementTextTextFormatter(l);
   }

   public String getMethodArgumentsError(String arg0, int arg1, int arg2) {
      String id = "MethodArgumentsError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getParameterError(int arg0, String arg1) {
      String id = "ParameterError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPostProcessArgsMBeanNameError(String arg0) {
      String id = "PostProcessArgsMBeanNameError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPostProcessArgsDomainNameError(String arg0) {
      String id = "PostProcessArgsDomainNameError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessArgsMBeanNameError(String arg0, int arg1) {
      String id = "ProcessArgsMBeanNameError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessArgsMBeanError(String arg0, int arg1) {
      String id = "ProcessArgsMBeanError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessArgsPropError(String arg0) {
      String id = "ProcessArgsPropError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessArgsPropNameValError(String arg0, String arg1) {
      String id = "ProcessArgsPropNameValError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessArgsCmdLineError(String arg0, int arg1) {
      String id = "ProcessArgsCmdLineError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValueObjectFromStringError(String arg0, String arg1) {
      String id = "ValueObjectFromStringError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getObjectFromStringError(String arg0, String arg1) {
      String id = "ObjectFromStringError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOneObjectFromString(String arg0, String arg1) {
      String id = "OneObjectFromString";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPwdInteractively(String arg0) {
      String id = "PwdInteractively";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPwdInteractivelyError() {
      String id = "PwdInteractivelyError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrintExceptionErr() {
      String id = "PrintExceptionErr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPrintExceptionExp() {
      String id = "PrintExceptionExp";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWriting() {
      String id = "ErrorWriting";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoMBeansFound() {
      String id = "NoMBeansFound";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoProp() {
      String id = "NoProp";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMoreThanOneParentsError(String arg0) {
      String id = "MoreThanOneParentsError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigCreateError() {
      String id = "ConfigCreateError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRuntimeCreateError() {
      String id = "RuntimeCreateError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedWhile() {
      String id = "FailedWhile";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotInvoke() {
      String id = "CouldNotInvoke";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRepMBeanNotFound() {
      String id = "RepMBeanNotFound";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemExpRepMBean() {
      String id = "RemExpRepMBean";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToSave() {
      String id = "UnableToSave";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemExpServerRuntime() {
      String id = "RemExpServerRuntime";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceMethNotFound(String arg0, String arg1) {
      String id = "InstanceMethNotFound";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWrongUsernamePwdErr() {
      String id = "WrongUsernamePwdErr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUrlException(String arg0) {
      String id = "UrlException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJndiException() {
      String id = "JndiException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoMBeanNameOrType() {
      String id = "NoMBeanNameOrType";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingUsage() {
      String id = "PingUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectUsage() {
      String id = "ConnectUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTimeUsage() {
      String id = "TimeUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownUsage() {
      String id = "ShutdownUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionUsage() {
      String id = "VersionUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResetPoolUsage() {
      String id = "ResetPoolUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePoolUsage() {
      String id = "CreatePoolUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyPoolUsage() {
      String id = "DestroyPoolUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnablePoolUsage() {
      String id = "EnablePoolUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisablePoolUsage() {
      String id = "DisablePoolUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockUsage() {
      String id = "LockUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnlockUsage() {
      String id = "UnlockUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListUsage() {
      String id = "ListUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpUsage() {
      String id = "ThreadDumpUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogOperationUsage() {
      String id = "LogOperationUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpUsage() {
      String id = "HelpUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetUsage() {
      String id = "GetUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetUsage() {
      String id = "SetUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreateUsage() {
      String id = "CreateUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteUsage() {
      String id = "DeleteUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvokeUsage() {
      String id = "InvokeUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenUsage1() {
      String id = "GenUsage1";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenUsage2() {
      String id = "GenUsage2";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenUsage3() {
      String id = "GenUsage3";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownHostError(String arg0) {
      String id = "UnknownHostError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectFailedError(String arg0) {
      String id = "ConnectFailedError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedConnect(String arg0) {
      String id = "FailedConnect";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGettingLogFileError() {
      String id = "GettingLogFileError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectOutput(int arg0, long arg1) {
      String id = "ConnectOutput";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingCount(int arg0, String arg1, int arg2, String arg3) {
      String id = "PingCount";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGuestShutdown() {
      String id = "GuestShutdown";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownInitialize() {
      String id = "ShutdownInitialize";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnSecException() {
      String id = "ConnSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailShutdown(String arg0) {
      String id = "FailShutdown";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCancelShutdownSecException() {
      String id = "CancelShutdownSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailCancelShutdown() {
      String id = "FailCancelShutdown";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResetPoolSecException(String arg0) {
      String id = "ResetPoolSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailResetPool(String arg0) {
      String id = "FailResetPool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCRPool() {
      String id = "CRPool";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolExists(String arg0) {
      String id = "PoolExists";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePoolSecException(String arg0) {
      String id = "CreatePoolSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailCreatePool(String arg0) {
      String id = "FailCreatePool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSuchPool(String arg0) {
      String id = "NoSuchPool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolDestructSecException(String arg0) {
      String id = "PoolDestructSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailPoolDestroy(String arg0) {
      String id = "FailPoolDestroy";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnablePoolSecException(String arg0) {
      String id = "EnablePoolSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailEnablePool(String arg0) {
      String id = "FailEnablePool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisablePoolSecException(String arg0) {
      String id = "DisablePoolSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailDisablePool(String arg0) {
      String id = "FailDisablePool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJdbcPoolExists(String arg0) {
      String id = "JdbcPoolExists";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJdbcPoolNotExists(String arg0) {
      String id = "JdbcPoolNotExists";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCheckExistSecException(String arg0) {
      String id = "CheckExistSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailCheckExists(String arg0) {
      String id = "FailCheckExists";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockSecException() {
      String id = "LockSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailLock() {
      String id = "FailLock";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnlockSecException() {
      String id = "UnlockSecException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailUnlock() {
      String id = "FailUnlock";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetCredentials() {
      String id = "SetCredentials";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEmpty() {
      String id = "Empty";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getContents(String arg0) {
      String id = "Contents";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommException() {
      String id = "CommException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthException() {
      String id = "AuthException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecException() {
      String id = "SecException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailBinding(String arg0) {
      String id = "FailBinding";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpAvailable() {
      String id = "ThreadDumpAvailable";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecExceptionThreadDump() {
      String id = "SecExceptionThreadDump";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailThreadDump() {
      String id = "FailThreadDump";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthError() {
      String id = "AuthError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownHost(String arg0) {
      String id = "UnknownHost";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolNotExists(String arg0) {
      String id = "PoolNotExists";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGuestDisabledException() {
      String id = "GuestDisabledException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteServerException(String arg0) {
      String id = "CannotDeleteServerException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteClusterException(String arg0) {
      String id = "CannotDeleteClusterException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteMigratableTargetException(String arg0) {
      String id = "CannotDeleteMigratableTargetException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1A(String arg0) {
      String id = "MigratableTargetInvViolation_1A";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1B(String arg0) {
      String id = "MigratableTargetInvViolation_1B";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1C(String arg0) {
      String id = "MigratableTargetInvViolation_1C";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_1D(String arg0) {
      String id = "MigratableTargetInvViolation_1D";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_2(String arg0) {
      String id = "MigratableTargetInvViolation_2";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_3(String arg0) {
      String id = "MigratableTargetInvViolation_3";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetInvViolation_4(String arg0) {
      String id = "MigratableTargetInvViolation_4";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetSubSystemName() {
      String id = "MigratableTargetSubSystemName";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetConstrainedCandidateServersException(String arg0) {
      String id = "CannotSetConstrainedCandidateServersException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetUserPreferredServerException(String arg0) {
      String id = "CannotSetUserPreferredServerException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotRemoveUserPreferredServerException(String arg0) {
      String id = "CannotRemoveUserPreferredServerException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetClusterException(String arg0) {
      String id = "CannotSetClusterException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAutomaticModeNotSupportedException(String arg0) {
      String id = "AutomaticModeNotSupportedException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusInProgress() {
      String id = "MigrationTaskStatusInProgress";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusDone() {
      String id = "MigrationTaskStatusDone";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusFailed() {
      String id = "MigrationTaskStatusFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusCanceled() {
      String id = "MigrationTaskStatusCanceled";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusQIsTheSourceServerDown() {
      String id = "MigrationTaskStatusQIsTheSourceServerDown";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskStatusQIsTheDestinationServerDown() {
      String id = "MigrationTaskStatusQIsTheDestinationServerDown";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskTitle(String arg0, String arg1, String arg2) {
      String id = "MigrationTaskTitle";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskCannotCancelHere() {
      String id = "MigrationTaskCannotCancelHere";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorCandidateServerMustNotBeEmpty() {
      String id = "MigrationTaskErrorCandidateServerMustNotBeEmpty";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorDestinationMustNotBeCurrentlyActiveServer() {
      String id = "MigrationTaskErrorDestinationMustNotBeCurrentlyActiveServer";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorDestinationMustBeMemberOfCandidiateServers() {
      String id = "MigrationTaskErrorDestinationMustBeMemberOfCandidiateServers";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskErrorUnableToDetermineListenAddressFromConfig(String arg0) {
      String id = "MigrationTaskErrorUnableToDetermineListenAddressFromConfig";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineJTAMigrationStarted(String arg0, String arg1) {
      String id = "MigrationTaskLoglineJTAMigrationStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineMigrationStarted(String arg0, String arg1) {
      String id = "MigrationTaskLoglineMigrationStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationErrorDestinationNotAmongCandidateServers(String arg0, String arg1) {
      String id = "MigrationErrorDestinationNotAmongCandidateServers";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationStarted(String arg0, String arg1, String arg2) {
      String id = "MigrationStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationSucceeded(String arg0) {
      String id = "MigrationSucceeded";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationFailed(String arg0, String arg1) {
      String id = "MigrationFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationUnknownDestinationServer(String arg0) {
      String id = "MigrationUnknownDestinationServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationUnknownMigratableTarget(String arg0) {
      String id = "MigrationUnknownMigratableTarget";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationJTAPrefix() {
      String id = "MigrationJTAPrefix";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskUserStopDestinationNotReachable(String arg0) {
      String id = "MigrationTaskUserStopDestinationNotReachable";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String IncorrectMigratableServerName(String arg0) {
      String id = "IncorrectMigratableServerName";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String MigratableServerIsNotInCluster(String arg0) {
      String id = "MigratableServerIsNotInCluster";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String IncorrectDestinationMachine(String arg0) {
      String id = "IncorrectDestinationMachine";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String DestinationMachineUnreachable(String arg0) {
      String id = "DestinationMachineUnreachable";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNameCannotEqualDomainName(String arg0, String arg1) {
      String id = "ServerNameCannotEqualDomainName";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultMigratableSuffix() {
      String id = "DefaultMigratableSuffix";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultServerMigratableTargetNote() {
      String id = "DefaultServerMigratableTargetNote";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineUnableToConnectToDestinationWantToGoAhead(String arg0) {
      String id = "MigrationTaskLoglineUnableToConnectToDestinationWantToGoAhead";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineOperationCanceledWhileAwaitingAnswer() {
      String id = "MigrationTaskLoglineOperationCanceledWhileAwaitingAnswer";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineUserAsksToGoAheadDespiteUnreachableDestination(String arg0) {
      String id = "MigrationTaskLoglineUserAsksToGoAheadDespiteUnreachableDestination";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineUserAsksNOTToGoAheadDespiteUnreachableDestination(String arg0) {
      String id = "MigrationTaskLoglineUserAsksNOTToGoAheadDespiteUnreachableDestination";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineCannotMigrateTransactionRecoveryService(String arg0, String arg1) {
      String id = "MigrationTaskLoglineCannotMigrateTransactionRecoveryService";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineMigrationTaskLoglineCannotMigrateTransactionRecoveryServiceForTheCurrentServiceHost(String arg0) {
      String id = "MigrationTaskLoglineCannotMigrateTransactionRecoveryServiceForTheCurrentServiceHost";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineAskingUserIsSourceDown(String arg0) {
      String id = "MigrationTaskLoglineAskingUserIsSourceDown";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineUserSaysSourceIsDown(String arg0) {
      String id = "MigrationTaskLoglineUserSaysSourceIsDown";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineUserSaysSourceIsNOTDown(String arg0) {
      String id = "MigrationTaskLoglineUserSaysSourceIsNOTDown";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineTryingToConnectToCurrentServer(String arg0) {
      String id = "MigrationTaskLoglineTryingToConnectToCurrentServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineUnableToConnectToCurrentServer(String arg0) {
      String id = "MigrationTaskLoglineUnableToConnectToCurrentServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationTaskLoglineConnectedSuccessfulyToCurrentServer(String arg0) {
      String id = "MigrationTaskLoglineConnectedSuccessfulyToCurrentServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTryingToDeactivateMigratableTarget(String arg0) {
      String id = "TryingToDeactivateMigratableTarget";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeactivationSucceeded() {
      String id = "DeactivationSucceeded";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServiceNotDeactivatedOnCurrentHostingServer(String arg0) {
      String id = "ServiceNotDeactivatedOnCurrentHostingServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServiceWasNotDeactivatedOnCurrentHostingServer(String arg0, String arg1) {
      String id = "ServiceWasNotDeactivatedOnCurrentHostingServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLostConnectionToCurrentHostingServerDeactivation(String arg0) {
      String id = "LostConnectionToCurrentHostingServerDeactivation";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLostConnectionToCurrentHostingServerDeactivationEx(String arg0, String arg1) {
      String id = "LostConnectionToCurrentHostingServerDeactivationEx";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToConnectToCurrentServer(String arg0, String arg1) {
      String id = "UnableToConnectToCurrentServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTryingToContactAdminServer(String arg0) {
      String id = "TryingToContactAdminServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectedToAdminServer() {
      String id = "ConnectedToAdminServer";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpdatedBookkeeping(String arg0, String arg1) {
      String id = "UpdatedBookkeeping";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotSaveConfig(String arg0, String arg1) {
      String id = "CouldNotSaveConfig";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTyingToConnectDestinationServer(String arg0) {
      String id = "TyingToConnectDestinationServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectedSuccessfulyToDestinationServer(String arg0) {
      String id = "ConnectedSuccessfulyToDestinationServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTryingToActivateMigratableTarget(String arg0) {
      String id = "TryingToActivateMigratableTarget";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getActivationSucceeded() {
      String id = "ActivationSucceeded";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnableToConnectToDestinationServer(String arg0, String arg1) {
      String id = "UnableToConnectToDestinationServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableTargetWillBeActivatedOn(String arg0, String arg1) {
      String id = "MigratableTargetWillBeActivatedOn";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableServiceWasNotActivatedOnDestination(String arg0) {
      String id = "MigratableServiceWasNotActivatedOnDestination";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableServiceWasNotActivatedOnDestinationEx(String arg0, String arg1) {
      String id = "MigratableServiceWasNotActivatedOnDestinationEx";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotSetMultipleServersonJMSServerException(String arg0) {
      String id = "JMSServeronSingleServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSingleTargetRequired(String arg0, String arg1, String arg2) {
      String id = "SingleTargetRequired";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLostConnectToDestinationServer(String arg0) {
      String id = "LostConnectToDestinationServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLostConnectToDestinationServerEx(String arg0, String arg1) {
      String id = "LostConnectToDestinationServerEx";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationErrorDestinationNotAmongClusterMembers(String arg0, String arg1) {
      String id = "MigrationErrorDestinationNotAmongClusterMembers";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotDeleteMachineException(String arg0) {
      String id = "CannotDeleteMachineException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalDestKeyOrder() {
      String id = "IllegalDestKeyOrder";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalDestKeyList() {
      String id = "IllegalDestKeyList";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreIsOwnedException(String arg0) {
      String id = "StoreIsOwnedException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalErrorDestination(String arg0, String arg1) {
      String id = "IllegalErrorDestination";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalTimeToDeliverOverride() {
      String id = "IllegalTimeToDeliverOverride";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalTimeToDeliverOverrideWithException(String arg0) {
      String id = "IllegalTimeToDeliverOverrideWithException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalAddTargetException(String arg0, String arg1) {
      String id = "IllegalAddTargetException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalSetTargetsException(String arg0) {
      String id = "IllegalSetTargetsException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalDDMemberException(String arg0, String arg1) {
      String id = "IllegalDDMemberException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalDDMemberListException(String arg0) {
      String id = "IllegalDDMemberListException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalSetTargetsWithMembersException(String arg0) {
      String id = "IllegalSetTargetsWithMembersException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotFindInstance(String arg0) {
      String id = "CouldNotFindInstance";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidParameterError(String arg0) {
      String id = "InvalidParameterError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluralPing() {
      String id = "PluralPing";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSingularPing() {
      String id = "SingularPing";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSingularByte() {
      String id = "SingularByte";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluralByte() {
      String id = "PluralByte";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSSubSystemName() {
      String id = "JMSSubSystemName";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSDDInvViolation_2(String arg0) {
      String id = "JMSDDInvViolation_2";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecurityMBeanNameError(String arg0, String arg1) {
      String id = "SecurityMBeanNameViolation";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullSourceException(String arg0) {
      String id = "NullSourceException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullTargetException(String arg0) {
      String id = "NullTargetException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSameSourceTargetException(String arg0) {
      String id = "SameSourceTargetException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNameArgRequired() {
      String id = "ServerNameArgRequired";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminServerUrlRequired(String arg0) {
      String id = "AdminServerUrlRequired";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerAlreadyRunning(String arg0) {
      String id = "ServerAlreadyRunning";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNotConfigured(String arg0, String arg1) {
      String id = "ServerNotConfigured";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUserNotValid(String arg0, String arg1) {
      String id = "UserNotValid";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPasswordNotValid(String arg0) {
      String id = "getPasswordNotValid";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartedSuccessfully(String arg0) {
      String id = "ServerStartedSuccessfully";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartingServerFailed(String arg0) {
      String id = "StartingServerFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExceptionMsg(String arg0) {
      String id = "ExceptionMsg";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCurrentStateOfServer(String arg0, String arg1) {
      String id = "CurrentStateOfServer";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUrlOfAdminServerRequired(String arg0) {
      String id = "UrlOfAdminServerRequired";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerSuspendedSuccessfully(String arg0) {
      String id = "ServerSuspendedSuccessfully";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerForceSuspendedSuccessfully(String arg0) {
      String id = "ServerForceSuspendedSuccessfully";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSuspendingServerFailed(String arg0) {
      String id = "SuspendingServerFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForceSuspendingServerFailed(String arg0) {
      String id = "ForceSuspendingServerFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerResumedSuccessfully(String arg0) {
      String id = "ServerResumedSuccessfully";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResumingServerFailed(String arg0) {
      String id = "ResumingServerFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShuttingViaNonAdminNotAllowed() {
      String id = "ShuttingViaNonAdminNotAllowed";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerShutdownSuccessfully(String arg0) {
      String id = "ServerShutdownSuccessfully";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShuttingdownServerFailed(String arg0) {
      String id = "ShuttingdownServerFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerForceShutdownSuccessfully(String arg0) {
      String id = "ServerForceShutdownSuccessfully";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoSTANDBYModeWithoutAdminPort() {
      String id = "NoSTANDBYModeWithoutAdminPort";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerIsCurrentlyInSUSPENDINGState() {
      String id = "ServerIsCurrentlyInSUSPENDINGState";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStateChangeOperationNotAllowed() {
      String id = "ServerStateChangeOperationNotAllowed";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerCurrentlyIsInRESUMEState() {
      String id = "ServerCurrentlyIsInRESUMEState";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForceShuttingdownServerFailed(String arg0) {
      String id = "ForceShuttingdownServerFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerCurrentlyShuttingDown() {
      String id = "ServerCurrentlyShuttingDown";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLifeCycleOperationTimedOut() {
      String id = "ServerLifeCycleOperationTimedOut";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThisServerLifeCycleOperationIsCurrentlyNotSupported() {
      String id = "ThisServerLifeCycleOperationIsCurrentlyNotSupported";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenerateDefaultConfigInteractively(String arg0, String arg1) {
      String id = "getGenerateDefaultConfigInteractively";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAffirmitaveGenerateConfigText() {
      String id = "getAffirmitaveGenerateConfigText";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNegativeGenerateConfigText() {
      String id = "getNegativeGenerateConfigText";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPleaseConfirmDeny(String arg0, String arg1) {
      String id = "getPleaseConfirmDeny";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenConfigInteractivelyError(String arg0) {
      String id = "genConfigInteractivelyError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedToLocateConfigFile(String arg0) {
      String id = "failedToLocateConfigFile";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noConfigFileWillNotGenerate(String arg0, String arg1) {
      String id = "noConfigFileWillNotGenerate";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cantGenerateConfigFileInMSI(String arg0) {
      String id = "cantGenerateConfigFileInMSI";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantGenerateConfigFileServerNameEmpty(String arg0) {
      String id = "getCantGenerateConfigFileServerNameEmpty";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotChangeMachineWhileServerIsRunningException(String arg0) {
      String id = "CannotChangeMachineWhileServerIsRunningException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidMulticastAddressException(String arg0, String arg1) {
      String id = "InvalidMulticastAddressException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotChangeClusterWhileServerIsRunningException(String arg0) {
      String id = "CannotChangeClusterWhileServerIsRunningException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCantGetLogFileWithoutHttpUrl() {
      String id = "cantGetLogFileWithoutHttpUrl";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnlockServerWithOutLock() {
      String id = "UnlockServerWithOutLock";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFJNDINameCannotBeNullException(String arg0) {
      String id = "JMSCFJNDINameCannotBeNullException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFConflictWithDefaultsException(String arg0) {
      String id = "JMSCFConflictWithDefaultsException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFJNDIConflictWithDefaultsException(String arg0) {
      String id = "JMSCFJNDIConflictWithDefaultsException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSDestJNDINameConflictException(String arg0) {
      String id = "JMSDestJNDINameConflictException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSCFJNDINameConflictException(String arg0) {
      String id = "JMSCFJNDINameConflictException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSJNDINameConflictException(String arg0) {
      String id = "JMSJNDINameConflictException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalExpirationPolicy() {
      String id = "IllegalExpirationPolicy";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalJMSJDBCPrefix() {
      String id = "IllegalJMSJDBCPrefix";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQueryUsage() {
      String id = "QueryUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPatternNotUnderstood() {
      String id = "PatternNotUnderstood";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPostProcessArgsTargetError() {
      String id = "PostProcessArgsTargetError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPostProcessArgsDestinationError() {
      String id = "PostProcessArgsDestinationError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownSequenceInitiated() {
      String id = "ShutdownSequenceInitiated";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoUserNameNoPassword() {
      String id = "NoUserNameNoPassword";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBatchUsage() {
      String id = "BatchUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExecCommand() {
      String id = "ExecCoomand";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommonSample() {
      String id = "commonSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingArgs1() {
      String id = "pingArgs1";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingArgs2() {
      String id = "PingArgs2";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingDescription() {
      String id = "PingDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingExample() {
      String id = "PingExample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectArgs() {
      String id = "ConnectArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectDescription() {
      String id = "ConnectDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectSample() {
      String id = "ConnectSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionDescription() {
      String id = "VersionDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVersionSample() {
      String id = "VersionSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResetDescription() {
      String id = "ResetDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResetSample() {
      String id = "ResetSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePoolDescription() {
      String id = "CreatePoolDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePoolSample() {
      String id = "CreatePoolSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatePoolArgs() {
      String id = "CreatePoolArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyPoolDescription() {
      String id = "DestroyPoolDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyPoolSample() {
      String id = "DestroyPoolSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestroyPoolArgs() {
      String id = "DestroyPoolArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnablePoolDescription() {
      String id = "EnablePoolDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnablePoolSample() {
      String id = "EnablePoolSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEnablePoolArgs() {
      String id = "EnablePoolArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisablePoolSample() {
      String id = "DisablePoolSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisablePoolArgs() {
      String id = "DisablePoolArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisablePoolDescription() {
      String id = "DisablePoolDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExistsPoolUsage() {
      String id = "ExistsPoolUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExistsPoolDescription() {
      String id = "ExistsPoolDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExistsPoolSample() {
      String id = "ExistsPoolSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExistsPoolArgs() {
      String id = "ExistsPoolArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockDescription() {
      String id = "LockDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockSample() {
      String id = "LockSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockArgs() {
      String id = "LockArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnLockDescription() {
      String id = "UnLockDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnLockSample() {
      String id = "UnLockSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListDescription() {
      String id = "ListDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListSample() {
      String id = "ListSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getListArgs() {
      String id = "ListArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpDescription() {
      String id = "ThreadDumpDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getThreadDumpSample() {
      String id = "ThreadDumpSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogDescription() {
      String id = "LogDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogSample() {
      String id = "LogSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogArgs() {
      String id = "LogArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetDescription() {
      String id = "GetDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetSampleForType() {
      String id = "GetSampleForType";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetSampleForON() {
      String id = "GetSampleForON";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetSampleForProperty() {
      String id = "GetSampleForProperty";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetArgs() {
      String id = "GetArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetSampleForType() {
      String id = "SetSampleForType";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetSampleForON() {
      String id = "SetSampleForON";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetDescription() {
      String id = "SetDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetArgs() {
      String id = "SetArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreateDescription() {
      String id = "CreateDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreateArgs() {
      String id = "CreateArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreateWithName() {
      String id = "CreateWithName";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreateWithON() {
      String id = "CreateWithON";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteType() {
      String id = "DeleteType";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteWithON() {
      String id = "DeleteWithON";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteDescription() {
      String id = "DeleteDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDeleteArgs() {
      String id = "DeleteArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvokeDescription() {
      String id = "InvokeDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvokeSample() {
      String id = "InvokeSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvokeArgs() {
      String id = "InvokeArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQueryDescription() {
      String id = "QueryDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQuerySample() {
      String id = "QuerySample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQueryArgs() {
      String id = "QueryArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateUsage() {
      String id = "MigrateUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateDescription() {
      String id = "MigrateDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateSampleJTA() {
      String id = "MigrateSampleJTA";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateSampleJMS() {
      String id = "MigrateSampleJMS";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateArgs() {
      String id = "MigrateArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateServerUsage() {
      String id = "MigrateServerUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateServerDescription() {
      String id = "MigrateServerDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateServerSample() {
      String id = "MigrateServerSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateServerArgs() {
      String id = "MigrateServerArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPostProcessArgsMigratableServerError() {
      String id = "PostProcessArgsMigratableServerError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPostProcessArgsDestinationMachineError() {
      String id = "PostProcessArgsDestinationMachineError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartUsage() {
      String id = "StartUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartDescription() {
      String id = "StartDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartSample() {
      String id = "StartSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartArgs() {
      String id = "StartArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSTARTINSTANDBYUsage() {
      String id = "STARTINSTANDBYUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartInStandByDescription() {
      String id = "StartInStandByDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartInStandbySample() {
      String id = "StartInStandbySample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartInStandbyArgs() {
      String id = "StartInStandbyArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResumeUsage() {
      String id = "ResumeUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResumeDescription() {
      String id = "ResumeDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResumeSample() {
      String id = "ResumeSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResumeArgs() {
      String id = "ResumeArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutDownSample() {
      String id = "ShutDownSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutDownArgs() {
      String id = "ShutDownArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutDownDescription() {
      String id = "ShutDownDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForceSDUsage() {
      String id = "ForceSDUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForceSDSample() {
      String id = "ForceSDSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForceSDArgs() {
      String id = "ForceSDArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetStateUsage() {
      String id = "GetStateUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetStateDescription() {
      String id = "GetStateDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetStateSample() {
      String id = "GetStateSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGetStateArgs() {
      String id = "GetStateArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBatchDescription() {
      String id = "BatchDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBatchSample() {
      String id = "BatchSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBatchArgs() {
      String id = "BatchArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getForceSDDescription() {
      String id = "ForceSDDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInfoStr() {
      String id = "InfoStr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerAdmin() {
      String id = "serverAdmin";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolStr() {
      String id = "poolStr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMbeanStr() {
      String id = "mbeanStr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommUsage() {
      String id = "commUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWhere() {
      String id = "where";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDesr() {
      String id = "Desr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getExamples() {
      String id = "examples";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getgetHelp() {
      String id = "getHelp";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingClusterUsage() {
      String id = "pingclusterUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getpingclusDesr() {
      String id = "pingclusDesr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getpingclusterSample() {
      String id = "pingclusterSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getpingClusterArgs() {
      String id = "pingClusterArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoOfServers(int arg0, String arg1, String arg2) {
      String id = "NoOfServers";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoneAlive() {
      String id = "NoneAlive";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAreAlive(String arg0) {
      String id = "AreAlive";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIsAlive(String arg0) {
      String id = "isAlive";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoclusterDefined(String arg0) {
      String id = "NoclusterDefined";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoClustersDefined() {
      String id = "NoClustersDefined";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInstanceAlreadyExists(String arg0) {
      String id = "InstanceAlreadyExists";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidMBeanType(String arg0) {
      String id = "InvalidMBeanType";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockSuccess(String arg0) {
      String id = "LockSuccess";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLockFailed(String arg0) {
      String id = "LockFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnLockSuccess(String arg0) {
      String id = "UnlockSuccess";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnlockFail(String arg0) {
      String id = "UnlockFail";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminDescr() {
      String id = "AdminDescr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInfo() {
      String id = "Info";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdmin() {
      String id = "admin";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJdbc() {
      String id = "jdbc";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMbean() {
      String id = "mbean";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAll() {
      String id = "all";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getShutdownSuccess(String arg0) {
      String id = "ShutdownSuccess";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String InvalidMulticastAddress(String arg0) {
      String id = "InvalidMulticastAddress";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String InvalidClusterAddress(String arg0) {
      String id = "InvalidClusterAddress";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDoNotEditConfigMessage(Date arg0) {
      String id = "MSG_DONOTEDITCONFIG";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidTargetsException(String arg0, String arg1) {
      String id = "EXCEPTION_INVALID_TARGETS";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnresolvedReferenceException(String arg0, String arg1, String arg2) {
      String id = "EXCEPTION_UNRESOLVED_REFERENCE";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getjdbcConTestSuc(String arg0) {
      String id = "jdbcConTestSuc";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getjdbcTestUnsuc(String arg0) {
      String id = "jdbcTestUnsuc";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTestPoolDescription() {
      String id = "TestPoolDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTestPoolUsage1() {
      String id = "TestPoolUsage1";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTestPoolSample() {
      String id = "TestPoolSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTestPoolArgs() {
      String id = "TestPoolArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getconnPoolSuc(String arg0) {
      String id = "connPoolSuc";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMissPool() {
      String id = "missPool";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getmissStartupValue(String arg0, String arg1) {
      String id = "missStartupValue";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolLessThanOne(String arg0) {
      String id = "PoolLessThanOne";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolInLessThanZero(String arg0, int arg1) {
      String id = "PoolInLessThanZero";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailtedToConnect1() {
      String id = "FailedToConnect1";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailtedToConnect2(String arg0) {
      String id = "FailedToConnect2";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotSupported() {
      String id = "NotSupported";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getshutNotSupported() {
      String id = "shutNotSupported";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNeedNodeManager() {
      String id = "NeedNodeManager";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotChangeClusterWhileServerReferredToInMigratableTarget(String arg0, String arg1) {
      String id = "CannotChangeClusterWhileServerReferredToInMigratableTarget";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCouldNotCheckReadonlyOfConfig() {
      String id = "CouldNotCheckReadonlyOfConfig";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationFailedDueToReadonlyConfigFile(String arg0, String arg1) {
      String id = "MigrationFailedDueToReadonlyConfigFile";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getclusterStartUsage() {
      String id = "clusterStartUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStartDescription() {
      String id = "getClusterStartDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStartSample() {
      String id = "ClusterStartSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartClusterArgs() {
      String id = "StartClusterArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStopUsage() {
      String id = "clusterStopUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStopDescription() {
      String id = "stopClusterDesc";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStopSample() {
      String id = "ClusterStopSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStopClusterArgs() {
      String id = "StopClusterArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSpecCN() {
      String id = "specCN";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getstartingServersInCluster() {
      String id = "startingServersInCluster";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSerStarted(String arg0) {
      String id = "serStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getspecCNS() {
      String id = "specCNS";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSDC() {
      String id = "SDC";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSDCS(String arg0) {
      String id = "SDCS";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVCCUsage() {
      String id = "VCCUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVCCDescription() {
      String id = "VCCDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVCCSample() {
      String id = "VCCSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getVCCArgs() {
      String id = "VCCArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getaliveServersStates() {
      String id = "aliveServersStates";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedCreateAdminUser() {
      String id = "FailedCreateAdminUser";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidadminurl(String arg0) {
      String id = "invalidadminurl";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWSSubSystemName() {
      String id = "WSSubSystemName";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReliableStoreIsOwnedException(String arg0) {
      String id = "ReliableStoreIsOwnedException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReliableStoreIsNotValid(String arg0) {
      String id = "ReliableStoreIsNotValid";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSValidStoreException(String arg0) {
      String id = "JMSValidStoreException";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getClusterStr() {
      String id = "clusterStr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCluster() {
      String id = "getCluster";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String purgeMSIStarted() {
      String id = "purgeMSIStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String purgeMSISucceeded() {
      String id = "purgeMSISucceeded";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String purgeMSIFailed(String arg0) {
      String id = "purgeMSIFailed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHelpUrl() {
      String id = "HelpUrl";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorUndeployingPool(String arg0, Exception arg1) {
      String id = "ErrorUndeployingPool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorDeletingPool(String arg0, Exception arg1) {
      String id = "ErrorDeletingPool";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPoolDeleted(String arg0) {
      String id = "PoolDeleted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartInStandbyModeDeperecated() {
      String id = "StartInStandbyDeprecated";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTimeFormatErrorMessage(String arg0, String arg1) {
      String id = "LogRotationStartTimeFormatError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getResetSuccess(String arg0) {
      String id = "ResetSuccess";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreUserConfigUsage() {
      String id = "StoreUserConfigUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreUserConfigDescription() {
      String id = "StoreUserConfigDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreUserConfigSample() {
      String id = "StoreUserConfigSample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreUserConfigArgs() {
      String id = "StoreUserConfigArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreUserConfigExample() {
      String id = "StoreUserConfigExample";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminConfigStr() {
      String id = "AdminConfigStr";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAdminConfig() {
      String id = "adminconfig";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEncryptionError() {
      String id = "encryptionError";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGenUserConfigUsage1() {
      String id = "GenUserConfigUsage1";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommUserConfigUsage() {
      String id = "commUserConfigUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMultipleStores(String arg0) {
      String id = "JMSTwoPersistentStores";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPagingDirectoryAndStore(String arg0) {
      String id = "JMSPagingDirectoryAndStore";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStoreTargetMismatch(String arg0) {
      String id = "JMSStoreTargetMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigratableFileStoreDirectoryMissing(String arg0, String arg1) {
      String id = "MigratableFileStoreDirectoryMissing";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSServerMigratableStore(String arg0) {
      String id = "JMSServerMigratableStore";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSBytesMaxOverThreshold(String arg0) {
      String id = "JMSBytesMaxOverThreshold";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSMessagesMaxOverThreshold(String arg0) {
      String id = "JMSMessagesMaxOverThreshold";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSBytesThresholdsReversed(String arg0) {
      String id = "JMSBytesThresholdsReversed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSMessagesThresholdsReversed(String arg0) {
      String id = "JMSMessagesThresholdsReversed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerMigrationTaskTitle(String arg0) {
      String id = "ServerMigrationTaskTitle";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSourceMachineDown(String arg0, String arg1) {
      String id = "SourceMachineDown";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDestinationMachineDown(String arg0) {
      String id = "DestinationMachineDown";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrationInProgress(String arg0) {
      String id = "MigrationInProgress";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateAllUsage() {
      String id = "MigrateAllUsage";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateAllDescription() {
      String id = "MigrateAllDescription";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMigrateAllArgs() {
      String id = "MigrateAllArgs";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidJMSMessagesMaximum() {
      String id = "InvalidJMSMessagesMaximum";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidJNDIName(String arg0) {
      String id = "InvalidJNDIName";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getImplicitUpgradePrompt(String arg0, String arg1, String arg2) {
      String id = "getImplicitUpgradePrompt";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNotPureWLSDomainText(String arg0) {
      String id = "getNotPureWLSDomain";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotModifyDomainTarget(String arg0) {
      String id = "getCannotModifyDomainTarget";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessMBeanNameOrTypeError(String arg0) {
      String id = "ProcessMBeanNameOrTypeError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSAFAgentSingleTarget(String arg0) {
      String id = "SAFAgentSingleTarget";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMBeanServerLabel() {
      String id = "MBeanServerLabel";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTypeClassLabel() {
      String id = "TypeClassLabel";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceStoreTargetMismatch(String arg0) {
      String id = "PathServiceStoreTargetMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartupWithoutAdminChannel() {
      String id = "StartupWithoutAdminChannel";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsetOnTargetted(String arg0, String arg1) {
      String id = "UnsetOnTargetted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalRetryDelayBaseAndMax(String arg0, long arg1, long arg2) {
      String id = "IllegalRetryDelayBaseAndMax";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalSAFAgentTargets(String arg0) {
      String id = "IllegalSAFAgentTargets";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIllegalSAFAgentMigratableTargets() {
      String id = "IllegalSAFAgentMigratableTargets";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAcknowledgeIntervalNotValid(long arg0) {
      String id = "AcknowledgeIntervalNotValid";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMgmtOperationsIllegal() {
      String id = "MgmtOperationsIllegal";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMgmtOperationsIllegalDomainRuntime() {
      String id = "MgmtOperationsIllegalDomainRuntime";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingAlreadyStarted() {
      String id = "RecordingAlreadyStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingAlreadyStopped() {
      String id = "RecordingAlreadyStopped";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordFailedDueToRecordingNotStarted(String arg0) {
      String id = "RecordFailedDueToRecordingNotStarted";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getMissingRecordingFileName() {
      String id = "MissingRecordingFileName";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecordingIOException() {
      String id = "RecordingIOException";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFileCannotBeAbsolute(String arg0) {
      String id = "getFileCannotBeAbsolute";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReceivingAgentInvlid4MT(String arg0) {
      String id = "ReceivingAgentInvlid4MT";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unAvailableConfigWizardCompoment() {
      String id = "ConfigurationWizardClassesNotAvailable";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPingArgs3() {
      String id = "pingArgs3";
      String subsystem = "Management Text";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidNumberRange(String arg0, String arg1, String arg2, String arg3, String arg4) {
      String id = "InvalidNumberRange";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJMSSpaceOverloadLoggingPercentReversed(String arg0) {
      String id = "JMSSpaceOverloadLoggingPercentReversed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceStoreInvalidDistributionPolicy(String arg0) {
      String id = "PathServiceStoreInvalidDistributionPolicy";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceStoreInvalidMigrationPolicy(String arg0) {
      String id = "PathServiceStoreInvalidMigrationPolicy";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidStoreMigrationPolicy(String arg0) {
      String id = "InvalidStoreMigrationPolicy";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidStoreHAPolicies(String arg0, String arg1, String arg2, String arg3) {
      String id = "InvalidStoreHAPolicies";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlwaysMigrationPolicyIsNotAllowedForBridges(String arg0) {
      String id = "getAlwaysMigrationPolicyIsNotAllowedForBridges";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceStoreCannotBeShared(String arg0, String arg1) {
      String id = "PathServiceStoreCannotBeShared";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSAFAgentWithStoreNotDistributed(String arg0) {
      String id = "SAFAgentWithStoreNotDistributed";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCannotEnableAutoMigrationWithoutLeasing(String arg0, String arg1, String arg2) {
      String id = "CannotEnableAutoMigrationWithoutLeasing";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerScopeMismatch(String arg0, String arg1, String arg2, String arg3) {
      String id = "JMSServerScopeMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultStoreInRGScopeError(String arg0, String arg1) {
      String id = "DefaultStoreInPartitionScope";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDefaultStoreInPathServiceInRGError(String arg0, String arg1) {
      String id = "DefaultStoreInPathServiceInRGError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBridgeScopeMismatch(String arg0, String arg1, String arg2, String arg3) {
      String id = "BridgeScopeMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getJDBCStoreScopeMismatch(String arg0, String arg1, String arg2, String arg3) {
      String id = "JDBCStoreScopeMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceScopeMismatch(String arg0, String arg1, String arg2, String arg3) {
      String id = "PathServiceScopeMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReceivingSAFAgentInvlidForRG(String arg0) {
      String id = "ReceivingSAFAgentInvlidForRG";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSubDepToTargetScopeMismatch(String arg0, String arg1, String arg2) {
      String id = "SubdepToTargetScopeMismatch";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSubdepInRGTargetRuleError(String arg0, String arg1) {
      String id = "SubdepInRGTargetRuleError";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSubdepJMSServerDPValidation(String arg0, String arg1) {
      String id = "SubdepJMSServerDPValidation";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCustomStoreMigrationPolicyUnsupported(String arg0, String arg1, String arg2) {
      String id = "CustomStoreMigrationPolicyUnsupported";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceRefersNullStoreInCluster(String arg0) {
      String id = "PathServiceRefersNullStoreInCluster";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceStoreInvalidMigrationPolicyInRG(String arg0, String arg1) {
      String id = "PathServiceStoreInvalidMigrationPolicyInRG";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPathServiceStoreInvalidDistributionPolicyInRG(String arg0, String arg1) {
      String id = "PathServiceStoreInvalidDistributionPolicyInRG";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScriptInterceptorNoScriptConfiguredText(String arg0) {
      String id = "ScriptInterceptorNoScriptConfiguredText";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionCachingWorkerCountValidation(String arg0, String arg1, String arg2) {
      String id = "ConnectionCachingWorkerCountValidation";
      String subsystem = "Management Text";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
