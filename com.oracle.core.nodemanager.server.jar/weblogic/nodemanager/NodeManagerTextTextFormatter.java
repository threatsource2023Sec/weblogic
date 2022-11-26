package weblogic.nodemanager;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class NodeManagerTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public NodeManagerTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.nodemanager.NodeManagerTextTextLocalizer", NodeManagerTextTextFormatter.class.getClassLoader());
   }

   public NodeManagerTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.nodemanager.NodeManagerTextTextLocalizer", NodeManagerTextTextFormatter.class.getClassLoader());
   }

   public static NodeManagerTextTextFormatter getInstance() {
      return new NodeManagerTextTextFormatter();
   }

   public static NodeManagerTextTextFormatter getInstance(Locale l) {
      return new NodeManagerTextTextFormatter(l);
   }

   public String cmdFailedSvr(String arg0, String arg1) {
      String id = "cmdFailedSvr";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cmdFailedSvrReason(String arg0, String arg1, String arg2) {
      String id = "cmdFailedSvrReason";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cmdTimedOut(String arg0, String arg1, long arg2) {
      String id = "cmdTimedOut";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSvrImplClass(String arg0) {
      String id = "msgSvrImplClass";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNoRestServerProviders() {
      String id = "msgNoRestServerProviders";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerSuspending(String arg0) {
      String id = "msgServerSuspending";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgShuttingDown(String arg0) {
      String id = "msgShuttingDown";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgShutDown() {
      String id = "msgShutDown";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStarting(String arg0) {
      String id = "msgStarting";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStartingType(String arg0, String arg1) {
      String id = "msgStartingType";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStarted(String arg0, String arg1, long arg2) {
      String id = "msgStarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgKilling(String arg0) {
      String id = "msgKilling";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgKilled() {
      String id = "msgKilled";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgEmptyTemplate() {
      String id = "msgEmptyTemplate";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWrongJava(String arg0, String arg1, String arg2) {
      String id = "msgWrongJava";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgFoundBootIdFile(String arg0) {
      String id = "msgFoundBootIdFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorMonProcFile(String arg0) {
      String id = "msgErrorMonProcFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSavedLogsDir(String arg0) {
      String id = "msgErrorSavedLogsDir";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorIntLogDir(String arg0) {
      String id = "msgErrorIntLogDir";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSvrInfo(String arg0) {
      String id = "msgErrorSvrInfo";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUpdtFile(String arg0) {
      String id = "msgErrorUpdtFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSvrInfoFile(String arg0) {
      String id = "msgErrorSvrInfoFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSvrMonState(String arg0) {
      String id = "msgErrorSvrMonState";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorRecovery(String arg0) {
      String id = "msgErrorRecovery";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFoundFile(String arg0, String arg1, String arg2) {
      String id = "msgErrorFoundFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFileMissing(String arg0) {
      String id = "msgErrorFileMissing";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFileCreate(String arg0) {
      String id = "msgErrorFileCreate";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFileWrite(String arg0) {
      String id = "msgErrorFileWrite";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFileClose(String arg0) {
      String id = "msgErrorFileClose";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoRestarting(int arg0) {
      String id = "msgInfoRestarting";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorRestarting(String arg0) {
      String id = "msgErrorRestarting";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSvrShutdown(String arg0) {
      String id = "msgSvrShutdown";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorQueryTimeout(String arg0) {
      String id = "msgErrorQueryTimeout";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorRestart(String arg0) {
      String id = "msgErrorRestart";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoKillingFailed(String arg0) {
      String id = "msgInfoKillingFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorKillFailed(String arg0) {
      String id = "msgErrorKillFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWarnIgnoreTimeoutFailed(String arg0) {
      String id = "msgWarnIgnoreTimeoutFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorQueryFailed(String arg0) {
      String id = "msgErrorQueryFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWarnIgnoreFailed() {
      String id = "msgWarnIgnoreFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUnexpected() {
      String id = "msgErrorUnexpected";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoRestarted(String arg0) {
      String id = "msgInfoRestarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWarnRestartMax() {
      String id = "msgWarnRestartMax";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorCheckHealth(String arg0, String arg1) {
      String id = "msgErrorCheckHealth";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUpdateRegistry(String arg0) {
      String id = "msgErrorUpdateRegistry";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorRetrieval(String arg0, String arg1) {
      String id = "msgErrorRetrieval";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorHostVerifier(String arg0) {
      String id = "msgErrorHostVerifier";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoPlainListen(String arg0) {
      String id = "msgInfoPlainListen";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorPlainListenFail(String arg0, String arg1) {
      String id = "msgErrorPlainListenFail";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorPlainAcceptFail(String arg0) {
      String id = "msgErrorPlainAcceptFail";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoSecureListen(String arg0) {
      String id = "msgInfoSecureListen";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSecureListenFail(String arg0, String arg1) {
      String id = "msgErrorSecureListenFail";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSecureAcceptFail(String arg0) {
      String id = "msgErrorSecureAcceptFail";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoStarting() {
      String id = "msgInfoStarting";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoUsage() {
      String id = "msgInfoUsage";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoDebug(int arg0) {
      String id = "msgInfoDebug";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoNative(boolean arg0) {
      String id = "msgInfoNative";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoListenerType(String arg0) {
      String id = "msgInfoListenerType";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNonSecure() {
      String id = "msgErrorNonSecure";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoListenAddr(String arg0) {
      String id = "msgInfoListenAddr";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoListenPort(int arg0) {
      String id = "msgInfoListenPort";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoStartTemplate(String arg0) {
      String id = "msgInfoStartTemplate";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoTrustedHosts(String arg0) {
      String id = "msgInfoTrustedHosts";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoReverseDns(boolean arg0) {
      String id = "msgInfoReverseDns";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoHostVerif(boolean arg0) {
      String id = "msgInfoHostVerif";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoWLHome(String arg0) {
      String id = "msgInfoWLHome";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoBEAHome(String arg0) {
      String id = "msgInfoBEAHome";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoScavangerSecs(int arg0) {
      String id = "msgInfoScavangerSecs";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNMCmdFailedNoPort(String arg0) {
      String id = "msgErrorNMCmdFailedNoPort";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNMCmdFailedFileCreate(String arg0, String arg1, String arg2) {
      String id = "msgErrorNMCmdFailedFileCreate";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNMCmdFailedReason(String arg0, String arg1) {
      String id = "msgErrorNMCmdFailedReason";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNotify(String arg0, String arg1) {
      String id = "msgErrorNotify";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWarnOldPortProp() {
      String id = "msgWarnOldPortProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgWarnOldAddrProp() {
      String id = "msgWarnOldAddrProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUnsuppCipher() {
      String id = "msgErrorUnsuppCipher";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSockClose1(String arg0) {
      String id = "msgErrorSockClose1";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorSockClose2(String arg0) {
      String id = "msgErrorSockClose2";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSvrHostsMigSvcs(String arg0) {
      String id = "msgSvrHostsMigSvcs";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNativeKillFailed(String arg0, String arg1, long arg2) {
      String id = "msgNativeKillFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNodeManagerPropertiesOpenFailed(String arg0, Exception arg1) {
      String id = "msgErrorNodeManagerPropertiesOpenFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNodeManagerPropertiesCloseFailed(String arg0, Exception arg1) {
      String id = "msgErrorNodeManagerPropertiesCloseFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNodeManagerPropertiesWriteFailed(String arg0, Exception arg1) {
      String id = "msgErrorNodeManagerPropertiesWriteFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorRequiredNodeManagerPropertyNotSet(String arg0, String arg1) {
      String id = "msgErrorRequiredNodeManagerPropertyNotSet";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorIllegalNodeManagerKeyStoresPropertyValue(String arg0, String arg1, String arg2, String arg3) {
      String id = "msgErrorIllegalNodeManagerKeyStoresPropertyValue";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorIllegalNodeManagerProperty(String arg0, String arg1, String arg2) {
      String id = "msgErrorIllegalNodeManagerProperty";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFileNotFound(String arg0) {
      String id = "msgErrorFileNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUnknownKeyStoreType(String arg0, Exception arg1) {
      String id = "msgErrorUnknownKeyStoreType";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorLoadKeyStoreCertificateException(String arg0, String arg1, Exception arg2) {
      String id = "msgErrorLoadKeyStoreCertificateException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorLoadKeyStoreNoSuchAlgorithmException(String arg0, String arg1, Exception arg2) {
      String id = "msgErrorLoadKeyStoreNoSuchAlgorithmException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorLoadKeyStoreIOException(String arg0, String arg1, Exception arg2) {
      String id = "msgErrorLoadKeyStoreIOException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFileCloseFailed(String arg0, Exception arg1) {
      String id = "msgErrorFileCloseFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUnknownIdentityAlias(String arg0, String arg1) {
      String id = "msgErrorUnknownIdentityAlias";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorIdentityAliasNotKeyEntry(String arg0, String arg1) {
      String id = "msgErrorIdentityAliasNotKeyEntry";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorKeyNotPrivateKey(String arg0, String arg1) {
      String id = "msgErrorKeyNotPrivateKey";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFindPrivateKeyNoSuchAlgorithmException(String arg0, String arg1, Exception arg2) {
      String id = "msgErrorFindPrivateKeyNoSuchAlgorithmException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorFindPrivateKeyUnrecoverableKeyException(String arg0, String arg1, Exception arg2) {
      String id = "msgErrorFindPrivateKeyUnrecoverableKeyException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUnexpectedKeyStoreException(String arg0, Exception arg1) {
      String id = "msgErrorUnexpectedKeyStoreException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorCertificateNotX509Certificate(String arg0, String arg1, String arg2) {
      String id = "msgErrorCertificateNotX509Certificate";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNoTrustedCAs(String arg0) {
      String id = "msgErrorNoTrustedCAs";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorEmptyIdentityCertificateChain(String arg0, String arg1) {
      String id = "msgErrorIdentityCertificateChain";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoReadingIdentityFromKeyStore(String arg0, String arg1, boolean arg2, String arg3) {
      String id = "msgInfoReadingIdentityFromKeyStore";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoReadingTrustedCAsFromKeyStore(String arg0, String arg1, boolean arg2) {
      String id = "msgInfoReadingTrustedCAsFromKeyStore";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoReadingCertificateChainFromFile(String arg0) {
      String id = "msgInfoReadingCertificateChainFromFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInfoReadingPrivateKeyFromFile(String arg0) {
      String id = "msgInfoReadingPrivateKeyFromFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorReadCertificateChainException(String arg0, Exception arg1) {
      String id = "msgErrorReadCertificateChainException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorReadPrivateKeyException(String arg0, Exception arg1) {
      String id = "msgErrorReadPrivateKeyException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorUnexpectedSSLContextWrapperException(Exception arg0) {
      String id = "msgErrorUnexpectedSSLContextWrapperException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logVersionMismatchMessage(String arg0, double arg1, double arg2) {
      String id = "logVersionMismatchMessage";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logNoLibraryMessage(String arg0, String arg1) {
      String id = "logNoLibraryMessage";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logReconfigureSSLMsg() {
      String id = "logReconfigureSSLMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSSLConnToNMestablished(String arg0) {
      String id = "msgSSLConnToNMestablished";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgListeningForCommands() {
      String id = "msgListeningForCommands";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStateTransitionSent(String arg0) {
      String id = "msgStateTransitionSent";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNMNotReachable() {
      String id = "msgNMNotReachable";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCurrentStateOfServer(String arg0) {
      String id = "msgCurrentStateOfServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRegisteringNMAgent(String arg0) {
      String id = "msgRegisteringNMAgent";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRegisteredNMAgent(String arg0) {
      String id = "msgRegisteredNMAgent";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSentToNM(String arg0) {
      String id = "msgSentToNM";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCommandListenerReady() {
      String id = "msgCommandListenerReady";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String responseSentToNM(String arg0) {
      String id = "responseSentToNM";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgSeqNumber(String arg0) {
      String id = "msgSeqNumber";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNoSeqNumber() {
      String id = "msgNoSeqNumber";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorNodeManagerPropertiesDecryptionFailed(String arg0, String arg1, String arg2, String arg3) {
      String id = "msgErrorNodeManagerPropertiesDecryptionFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgMonitoringServer(String arg0) {
      String id = "msgMonitoringServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStartupModeUpdateSent(String arg0) {
      String id = "msgStartupModeUpdateSent";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgStartupModeUpdateFailed(String arg0) {
      String id = "msgStartupModeUpdateFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerStateResponseFailed(String arg0) {
      String id = "msgServerStateResponseFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRegisteringNMAgentForStartupMode(String arg0) {
      String id = "msgRegisteringNMAgentForStartupMode";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgExceptionWhileMonitoring(String arg0, String arg1) {
      String id = "msgExceptionWhileMonitoring";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgServerShuttingDown(String arg0) {
      String id = "msgServerShuttingDown";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRequestFailed(String arg0, String arg1, String arg2) {
      String id = "msgRequestFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRequestCancelled(String arg0, String arg1, String arg2) {
      String id = "msgRequestCancelled";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgNoConnFromManagedServer(String arg0, String arg1, int arg2) {
      String id = "msgNoConnFromManagedServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgFailedToSendException(String arg0, String arg1) {
      String id = "msgFailedToSendException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgFailedToSendCancelMessage(String arg0, String arg1) {
      String id = "msgFailedToSendCancelMessage";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorDecrypting(String arg0, String arg1) {
      String id = "msgErrorDecrypting";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logConvertEncryptedProps() {
      String id = "ConvertEncryptedProps";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logConvertEncryptionService() {
      String id = "ConvertEncryptionService";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logEncryptionConvertDone() {
      String id = "EncryptionConvertDone";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logFinishPartialESConvert() {
      String id = "FinishPartialESConvert";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logDeleteOldEncryptService() {
      String id = "DeletingOldEncryptionService";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logNoEncryptConvert() {
      String id = "NoEncryptConvert";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logCheckEncryption() {
      String id = "CheckEncryption";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgConversionError(String arg0, String arg1, String arg2) {
      String id = "ConversionError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorConvertSvrInfoFile(String arg0) {
      String id = "msgErrorConvertSvrInfoFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logServiceFound(String arg0) {
      String id = "logServiceFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSleepForRestartDelay(int arg0) {
      String id = "SleepForRestartDelay";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartupFailedRestartable() {
      String id = "StartupFailedRestartable";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerFailedToStart() {
      String id = "ServerFailedToStart";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRecoveringServerProcess() {
      String id = "RecoveringServerProcess";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRotatedOutputLog(String arg0) {
      String id = "RotatedOutputLog";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCreatingDirectory(String arg0) {
      String id = "CreatingDirectory";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorCreatingDirectory(String arg0) {
      String id = "ErrorCreatingDirectory";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerAlreadyRunningOrStarting(String arg0, String arg1) {
      String id = "ServerAlreadyRunning";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBootIdentitySaved(String arg0) {
      String id = "BootIdentitySaved";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartupPropertiesLoaded(String arg0) {
      String id = "StartupPropertiesLoaded";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartupPropertiesSaved(String arg0) {
      String id = "StartupPropertiesSaved";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangeFileOwnershipSucceeded(String arg0, String arg1, String arg2) {
      String id = "ChangeFileOwnershipSucceeded";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangeFileOwnershipFailed(String arg0, String arg1, String arg2) {
      String id = "ChangeFileOwnershipFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String scriptNotFound(String arg0, String arg1) {
      String id = "scriptNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String scriptDirNotFound(String arg0, String arg1) {
      String id = "scriptDirNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidParam(String arg0, String arg1) {
      String id = "invalidParam";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingNetMaskProp(String arg0) {
      String id = "missingNetMaskProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainInitError(String arg0, String arg1) {
      String id = "DomainInitError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDynamicDomainRegistrationNotAllowed(String arg0, String arg1) {
      String id = "DynamicDomainRegistrationNotAllowed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWrongLocationOfTheDomainTaken(String arg0, String arg1, String arg2) {
      String id = "WrongLocationOfTheDomainTaken";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNewLocationOfTheDomainRegistered(String arg0, String arg1, String arg2) {
      String id = "NewLocationOfTheDomainRegistered";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidCommand(String arg0) {
      String id = "invalidCommand";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidIntProperty(String arg0) {
      String id = "invalidIntProperty";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidLongProperty(String arg0) {
      String id = "invalidLongProperty";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertiesFileNotFound(String arg0) {
      String id = "getPropertiesFileNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertiesFileNotWritable(String arg0) {
      String id = "getPropertiesFileNotWritable";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainDirNotFound(String arg0) {
      String id = "getDomainDirNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDomainSalt(String arg0) {
      String id = "invalidDomainSalt";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSaltFileNotFound() {
      String id = "saltFileNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorLoadingSalt() {
      String id = "errorLoadingSalt";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDataFileNotFound(String arg0) {
      String id = "getDataFileNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDataFile(String arg0) {
      String id = "invalidDataFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEncryptionServiceFailure() {
      String id = "enctyptionServiceFailure";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUncaughtHandlerException(String arg0) {
      String id = "uncaughtHandlerException";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorClosingSocket(String arg0) {
      String id = "errorClosingSocket";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDisabledCommand(String arg0) {
      String id = "disabledCommand";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidCommandSyntax(String arg0) {
      String id = "invalidCommandSyntax";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainError(String arg0, String arg1) {
      String id = "domainError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainIOError(String arg0, String arg1) {
      String id = "domainIOError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerDirIOError(String arg0, String arg1, String arg2) {
      String id = "serverDirIOError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getQuitMsg() {
      String id = "quitMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSetDomainMsg(String arg0) {
      String id = "setDomainMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainCredChg(String arg0) {
      String id = "domainCredChgMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSrvrPropsUpdate(String arg0) {
      String id = "srvrPropsUpdate";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorWritingConfig(String arg0, String arg1) {
      String id = "errorWritingConfig";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerDirError(String arg0, String arg1, String arg2) {
      String id = "serverDirError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSrvrMsg(String arg0) {
      String id = "srvrMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMUserMsg(String arg0) {
      String id = "nmUserMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPassError() {
      String id = "passError";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthError(String arg0, String arg1) {
      String id = "authError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPassMsg() {
      String id = "passMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutputLogNotFound(String arg0, String arg1, String arg2) {
      String id = "outputLogNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerLogFile() {
      String id = "serverLogFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMLogFile() {
      String id = "nmLogFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScriptMsg(String arg0) {
      String id = "scriptMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getScriptError(String arg0) {
      String id = "scriptError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartError(String arg0, String arg1, String arg2) {
      String id = "serverStartError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStartedMsg(String arg0, String arg1) {
      String id = "serverStartedMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStopped(String arg0, String arg1) {
      String id = "serverStopped";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerStopError(String arg0, String arg1, String arg2) {
      String id = "serverStopError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerKilled(String arg0, String arg1) {
      String id = "serverKilled";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainNull() {
      String id = "domainNull";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNull() {
      String id = "serverNull";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAuthNull() {
      String id = "authNull";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getGreeting(String arg0) {
      String id = "greeting";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInheritedSocket(String arg0) {
      String id = "inheritedSocket";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlainListenerStarted(String arg0) {
      String id = "plainListenerStarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPlainListenerStartedHost(String arg0, String arg1) {
      String id = "plainListenerStartedHost";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedConnection(String arg0, String arg1) {
      String id = "failedConnection";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRotatedMsg(String arg0) {
      String id = "rotatedMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRotatedErrorLogMsg() {
      String id = "rotatedErrorLogMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRotationError(String arg0, String arg1) {
      String id = "rotationError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidScriptTimeout(String arg0, String arg1) {
      String id = "invalidScriptTimeout";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidLogLevel(String arg0, String arg1) {
      String id = "invalidLogLevel";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLogFormatterError(String arg0) {
      String id = "formatterError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNativeLibraryLoadError() {
      String id = "nativeLibraryLoadError";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNativeLibraryLoadErrorForPid() {
      String id = "nativeLibraryLoadErrorForPid";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNativeLibraryNA() {
      String id = "nativeLibraryNA";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNativeLibraryNAForPid() {
      String id = "nativeLibraryNAForPid";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWritingPidFileError(String arg0) {
      String id = "writingPidFileError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemovingProp(String arg0) {
      String id = "removingProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddingProp(String arg0, String arg1) {
      String id = "addingProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPropValue(String arg0, String arg1) {
      String id = "invalidPropValue";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSettingVersion(String arg0) {
      String id = "settingVersion";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getReloadingDomainsFile(String arg0) {
      String id = "reloadingDomainsFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDomainsFile(String arg0) {
      String id = "invalidDomainsFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainsFileNotFound(String arg0) {
      String id = "domainsNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorReadingDomainsFile(String arg0) {
      String id = "errorReadingDomainsFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadingDomainsFile(String arg0) {
      String id = "loadingDomainsFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStdOutErrStreams(String arg0, String arg1) {
      String id = "stdoutErrStreams";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInputStream(String arg0) {
      String id = "inputStream";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidNMPropFile(String arg0) {
      String id = "invalidNMPropFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorReadingNMPropFile(String arg0) {
      String id = "errorReadingNMPropFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadedNMProps(String arg0) {
      String id = "loadedNMProps";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMPropsNotFound(String arg0) {
      String id = "nmPropsNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavingNMProps(String arg0) {
      String id = "savingNMProps";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConfigError(String arg0) {
      String id = "configError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartMonitorConfigError(String arg0) {
      String id = "startMonitorConfigError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getStartMonitorIOError(String arg0) {
      String id = "startMonitorIOError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFatalError() {
      String id = "fatalError";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnrecognizedOption(String arg0) {
      String id = "unrecognizedOption";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidArgument() {
      String id = "invalidArgument";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNameNull() {
      String id = "serverNameNull";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainNameNull() {
      String id = "domainNameNull";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBadDomain(String arg0) {
      String id = "badDomain";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getErrorUpdatingSecretFile(String arg0) {
      String id = "errorUpdatingSecretFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnsupportedCipher(String arg0) {
      String id = "unsupportedCipher";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecureSocketListener(String arg0) {
      String id = "secureSocketListener";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSecureSocketListenerHost(String arg0, String arg1) {
      String id = "secureSocketListenerHost";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String upgradeToSecure() {
      String id = "upgradeToSecure";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFailedSecureConnection(String arg0, String arg1) {
      String id = "failedSecureConnection";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMDataPropsMigrated(String arg0, String arg1) {
      String id = "nmDataPropsMigrated";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMDataPropsMigrateError(String arg0, String arg1) {
      String id = "nmDataPropsMigrateError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMDataPropsRenamed(String arg0, String arg1) {
      String id = "nmDataPropsRenamed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMDataPropsRenameError(String arg0) {
      String id = "nmDataPropsRenameError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSavingUpgradedProps(String arg0) {
      String id = "savingUpgradedProps";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNMDirError(String arg0) {
      String id = "nmDirError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUpgradeStarted(String arg0) {
      String id = "upgradeStarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainSaltFileNotFound() {
      String id = "domainSaltFileNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getWorkingDirectory(String arg0) {
      String id = "workingDirectory";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOutFile(String arg0) {
      String id = "outFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getLoadingIDStore(String arg0) {
      String id = "loadIDStore";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownIDStoreType(String arg0) {
      String id = "unknownIDStoreType";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIDStoreNotFound(String arg0) {
      String id = "idStoreNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIDAlgorithmNotFound() {
      String id = "idAlgorithmNotFound";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCertificatesNotLoaded() {
      String id = "certificatesNotLoaded";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIncorrectIDPassword() {
      String id = "incorectIDPassword";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownKeyStoreID(String arg0) {
      String id = "unknownKeyStoreID";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIdentityStoreNotInit() {
      String id = "identityStoreNotInit";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoCertificate(String arg0) {
      String id = "keyStoreNoCertificate";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoX509() {
      String id = "noX509";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCertificateFileNF(String arg0) {
      String id = "certificateFileNF";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidCertFile(String arg0) {
      String id = "invalidCertFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getKeyFileNotFound(String arg0) {
      String id = "keyFileNF";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidKeyFile(String arg0) {
      String id = "invalidKeyFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEncryptingProp(String arg0) {
      String id = "encryptingProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getHostNotSet() {
      String id = "hostNotSet";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgInvalidCoherenceOperationalConfigFile(String arg0) {
      String id = "msgInvalidCoherenceOperationalConfigFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String credentialsFileEmpty() {
      String id = "credentialsFileEmpty";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDuplicateRegisteredDomain(String arg0, String arg1, String arg2, String arg3) {
      String id = "duplicateDomain";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidRegisteredDomain(String arg0, String arg1) {
      String id = "invalidRegisteredDomain";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnregisteredDomainName(String arg0) {
      String id = "unregisteredDomainName";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String missingSrvrMigProp() {
      String id = "missingSrvrMigProp";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unknownIPRange(String arg0) {
      String id = "unknownIPRange";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidNetMask(String arg0, String arg1) {
      String id = "invalidNetMask";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidSrvrType(String arg0) {
      String id = "invalidSrvrType";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSrvrTypeMsg(String arg0) {
      String id = "srvrTypeMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getChangeListMsg(String arg0) {
      String id = "changeListMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSyncChangeListMsg(String arg0) {
      String id = "syncChangeListMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPutFileMsg(String arg0, String arg1) {
      String id = "putFileMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPutFileErrorMsg(String arg0, String arg1) {
      String id = "putFileErrorMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidateChangeListMsg(String arg0) {
      String id = "validateChangeListMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getValidateChangeListErrorMsg(String arg0, String arg1) {
      String id = "validateChangeListErrorMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommitChangeListMsg(String arg0) {
      String id = "commitChangeListMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRollbackChangeListMsg(String arg0) {
      String id = "rollbackChangeListMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDiagnosticsMsg(String arg0) {
      String id = "diagnosticsMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvocationMsg(String arg0) {
      String id = "invocationMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPluginNotFoundErrorMsg(String arg0, String arg1) {
      String id = "pluginNotFoundErrorMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getPropertiesMsg() {
      String id = "propertiesMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerSoftRestart(String arg0, String arg1) {
      String id = "serverSoftRestart";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getSoftRestartError(String arg0, String arg1, String arg2) {
      String id = "softRestartError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgCoherenceStartupScriptNotSpecified() {
      String id = "msgCoherenceStartupScriptNotSpecified";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAddressInUse(String arg0, String arg1, Exception arg2) {
      String id = "addressInUse";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String softRestartUnsupported(String arg0, String arg1) {
      String id = "softRestartUnsupported";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noStartupConfig(String arg0, String arg1) {
      String id = "noStartupConfig";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unexpectedExFromPluginNoLegalExThrown(String arg0, String arg1, String arg2) {
      String id = "unexpectedExFromPluginNoLegalExThrown";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unexpectedExFromPluginWithLegalExThrown(String arg0, String arg1, String arg2, String arg3) {
      String id = "unexpectedExFromPluginWithLegalExThrown";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2, arg3};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String serverCommandError(String arg0, String arg1, String arg2) {
      String id = "serverCommandError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedCommandFailure(String arg0, String arg1) {
      String id = "getUnexpectedCommandFailure";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoveInstance(String arg0, String arg1) {
      String id = "removeInstance";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRemoveError(String arg0, String arg1, String arg2) {
      String id = "serverRemoveError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitState(String arg0, String arg1) {
      String id = "initState";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInitStateError(String arg0, String arg1, String arg2) {
      String id = "initStateError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String serverStarted(String arg0) {
      String id = "serverStarted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String serverIsNotAlive(String arg0, String arg1) {
      String id = "serverIsNotAlive";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String IllegalServerTypeForNMCommand(String arg0, String arg1, String arg2) {
      String id = "IllegalServerTypeForNMCommand";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String exceptionWithoutDetailedMessage(String arg0) {
      String id = "exceptionWithoutDetailedMessage";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProcessDestroyFailed(long arg0) {
      String id = "processDestroyFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainCredChgFailed(String arg0, String arg1) {
      String id = "domainCredChgFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String adjustedState(String arg0, String arg1, String arg2) {
      String id = "adjustedState";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String noProcessControl() {
      String id = "noProcessControl";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String propertyNotAppliedInEnv(String arg0) {
      String id = "propertyNotAppliedInEnv";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String propertyDeprecated(String arg0, String arg1) {
      String id = "propertyDeprecated";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotSpecifyBoth(String arg0, String arg1) {
      String id = "cannotSpecifyBoth";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgErrorLockFile(String arg0) {
      String id = "msgErrorLockFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String domainRegistrationPropDeprecated(String arg0) {
      String id = "domainRegistrationPropDeprecated";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String rotationPropertyDeprecated(String arg0) {
      String id = "rotationPropertyDeprecated";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String propertyNotAppliedWithNativeVersion(String arg0) {
      String id = "propertyNotAppliedWithNativeVersion";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgForGetFile(String arg0) {
      String id = "msgForGetFile";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgForGetChangeList(String arg0) {
      String id = "msgForGetChangeList";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartMsg() {
      String id = "RestartMsg";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgRestartNMProcessFailure(String arg0) {
      String id = "RestartNMProcessFailure";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgIllegalNMProcessArguments(String arg0) {
      String id = "illegalNMProcessArguments";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String illegalLogCount(int arg0) {
      String id = "illegalLogCount";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProblemShuttingDownForRestart(String arg0) {
      String id = "problemShuttingDownForRestart";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unableProperlyRestart(String arg0) {
      String id = "unableProperlyRestart";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getRestartAllFailed(String arg0, String arg1) {
      String id = "restartAllFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String msgFailedSavingStateForAliveServer(String arg0) {
      String id = "msgFailedSavingStateForAliveServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerPrintThreadDump(String arg0) {
      String id = "serverPrintThreadDump";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String checkJavaVersionFailed(String arg0, String arg1) {
      String id = "checkJavaVersionFailed";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
