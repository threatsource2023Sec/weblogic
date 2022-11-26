package weblogic.jdbc;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class JDBCTextTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public JDBCTextTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.jdbc.JDBCTextTextLocalizer", JDBCTextTextFormatter.class.getClassLoader());
   }

   public JDBCTextTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.jdbc.JDBCTextTextLocalizer", JDBCTextTextFormatter.class.getClassLoader());
   }

   public static JDBCTextTextFormatter getInstance() {
      return new JDBCTextTextFormatter();
   }

   public static JDBCTextTextFormatter getInstance(Locale l) {
      return new JDBCTextTextFormatter(l);
   }

   public String executionExeption(String arg0, String arg1) {
      String id = "executionExeption";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cachedName(String arg0) {
      String id = "cachedName";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String driverLoading(String arg0) {
      String id = "driverLoading";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String logStreamStarted(String arg0) {
      String id = "logStreamStarted";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String driverLoadingError(String arg0, String arg1) {
      String id = "driverLoadingError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociBlobOutputStreamCloseError(String arg0) {
      String id = "ociBlobOutputStreamCloseError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociBlobInputStreamCloseError(String arg0) {
      String id = "ociBlobInputStreamCloseError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociBlobUnexpextedStreamTypeError() {
      String id = "ociBlobUnexpextedStreamTypeError";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociClobOutputStreamCloseError(String arg0) {
      String id = "ociClobOutputStreamCloseError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociClobReaderCloseError(String arg0) {
      String id = "ociClobReaderCloseError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociClobOutStreamCloseError(String arg0) {
      String id = "ociClobOutStreamCloseError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociClobWriterCloseError(String arg0) {
      String id = "ociClobWriterCloseError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String ociClobUnexpextedStreamTypeError() {
      String id = "ociClobUnexpextedStreamTypeError";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String setQueryTimeourUnspprtd(String arg0) {
      String id = "setQueryTimeourUnspprtd";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String cannotHandleUrlWarning(String arg0, String arg1) {
      String id = "cannotHandleUrlWarning";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String baseHandlerAppendWarning(String arg0) {
      String id = "baseHandlerAppendWarning";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorMessage(String arg0, Exception arg1) {
      String id = "errorMessage";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String debugMessage(String arg0) {
      String id = "debugMessage";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String setConnTimeoutError(String arg0) {
      String id = "setConnTimeoutError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String strangeOptionWarning(String arg0) {
      String id = "strangeOptionWarning";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String spServerInfoError(String arg0) {
      String id = "spServerInfoError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String readSleepingError(String arg0) {
      String id = "readSleepingError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String closingStreamError(String arg0) {
      String id = "closingStreamError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warningReadingError(String arg0) {
      String id = "warningReadingError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String parametersFillingError() {
      String id = "parametersFillingError";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String connectingInfo(String arg0, long arg1) {
      String id = "connectingInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String connectedInfo(long arg0) {
      String id = "connectedInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String loggingInDoneInfo(long arg0) {
      String id = "loggingInDoneInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String loadingLibraryInfo(String arg0) {
      String id = "loadingLibraryInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String loadedLibraryInfo(long arg0) {
      String id = "loadedLibraryInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String parsingInfo(String arg0) {
      String id = "parsingInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String executingInfo(String arg0) {
      String id = "executingInfo";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warningMessage(String arg0) {
      String id = "warningMessage";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String startLoading(String arg0) {
      String id = "startLoading";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedLoading(String arg0, String arg1) {
      String id = "failedLoading";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String checkingURL(String arg0, String arg1) {
      String id = "checkingURL";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warningMsg1() {
      String id = "warningMsg1";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getBigDecimal(int arg0) {
      String id = "getBigDecimal";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String warningMsg2(String arg0) {
      String id = "warningMsg2";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidConnUrlError(String arg0) {
      String id = "invalidConnUrlError";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dbNameReqd() {
      String id = "dbNameReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dbUsernameReqd() {
      String id = "dbUsernameReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dbPasswordReqd() {
      String id = "dbPasswordReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dbHostReqd() {
      String id = "dbHostReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dbPortReqd() {
      String id = "dbPortReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String informixSvrNameReqd() {
      String id = "informixSvrNameReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nskCatalogNameReqd() {
      String id = "nskCatalogNameReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nskUserNameReqd() {
      String id = "nskUserNameReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String neonDSNameReqd() {
      String id = "neonDSNameReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String oracleUserIdReqd() {
      String id = "oracleUserIdReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dbServiceReqd() {
      String id = "dbServiceReqd";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String testPoolException(String arg0) {
      String id = "testPoolException";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String testPoolQueryFailed(String arg0) {
      String id = "testPoolQueryFailed";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String testPoolIsValid() {
      String id = "testPoolIsValid";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nameNull() {
      String id = "nameNull";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badDRCP() {
      String id = "badDRCP";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badProxy() {
      String id = "badProxy";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badProxy2() {
      String id = "badProxy2";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badPinned() {
      String id = "badPinned";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidHarvestMaxCount(int arg0) {
      String id = "invalidHarvestMaxCount";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidTriggerCount(int arg0, int arg1) {
      String id = "invalidTriggerCount";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String poolNotFound(String arg0) {
      String id = "poolNotFound";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String driverNotFound(String arg0) {
      String id = "driverNotFound";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidHostPort(String arg0) {
      String id = "invalidHostPort";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String connectionClosed() {
      String id = "connectionClosed";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String attachFailed() {
      String id = "attachFailed";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String detachFailed() {
      String id = "detachFailed";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String pingNotSupported() {
      String id = "pingNotSupported";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String isValidNotSupported() {
      String id = "isValidNotSupported";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String adminDestroyed(String arg0, String arg1) {
      String id = "adminDestroyed";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String adminDisabled(String arg0, String arg1) {
      String id = "adminDisabled";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String tooManyFailures() {
      String id = "tooManyFailures";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failureMDS() {
      String id = "failureMDS";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String hungThreads() {
      String id = "hungThreads";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String byConsole() {
      String id = "byConsole";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String identityNotSupported() {
      String id = "identityNotSupported";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String connectionClosed2() {
      String id = "connectionClosed2";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String featureNotSupported() {
      String id = "featureNotSupported";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullPS() {
      String id = "nullPS";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String nullPC() {
      String id = "nullPC";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String negativeTimeout() {
      String id = "negativeTimeout";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String closeNotSupported(String arg0) {
      String id = "closeNotSupported";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String proxyNotSupported(String arg0) {
      String id = "proxyNotSupported";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String applyOnDRCP() {
      String id = "applyOnDRCP";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String invalidLabelKey(String arg0) {
      String id = "invalidLabelKey";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getConnectionLabelsDRCP() {
      String id = "getConnectionLabelsDRCP";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnmatchedConnectionLabelsDRCP() {
      String id = "getUnmatchedConnectionLabelsDRCP";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String removeConnectionLabelDRCP() {
      String id = "removeConnectionLabelDRCP";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badHarvestable(boolean arg0) {
      String id = "badHarvestable";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String accessHarvested() {
      String id = "accessHarvested";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String harvestCallbackSet() {
      String id = "harvestCallbackSet";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dsInactive(String arg0) {
      String id = "dsInactive";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badConnectionToInstance() {
      String id = "badConnectionToInstance";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badLabelPinned() {
      String id = "badLabelPinned";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badLabelIdentity() {
      String id = "badLabelIdentity";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badPool(String arg0) {
      String id = "badPool";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badLabelPooled() {
      String id = "badLabelPooled";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedBeginRequest(String arg0) {
      String id = "failedBeginRequest";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedBeginRequest2() {
      String id = "failedBeginRequest2";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badCICallback(String arg0) {
      String id = "badCICallback";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badCICallback2() {
      String id = "badCICallback2";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badPinnedAuditable() {
      String id = "badPinnedAuditable";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String poolAllocate(String arg0, String arg1) {
      String id = "poolAllocate";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badConnect() {
      String id = "badConnect";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badFactoryInit(String arg0) {
      String id = "badFactoryInit";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String unavailableSecurity() {
      String id = "unavailableSecurity";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badIdentityProxy() {
      String id = "badIdentityProxy";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badConnectionRelease(String arg0) {
      String id = "badConnectionRelease";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badProxyNotOracle() {
      String id = "badProxyNotOracle";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badProxyRelease(String arg0) {
      String id = "badProxyRelease";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorProxy() {
      String id = "errorProxy";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badProxyConnection(String arg0) {
      String id = "badProxyConnection";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String failedCICallback(String arg0) {
      String id = "failedCICallback";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badEndRequest() {
      String id = "badEndRequest";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String badXAReplayTestTable() {
      String id = "badXAReplayTestTable";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String criticalDataSourceFailed(String arg0) {
      String id = "criticalDataSourceFailed";
      String subsystem = "JDBC";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String dataSourceListWithNonMDSType() {
      String id = "dataSourceListWithNonMDSType";
      String subsystem = "JDBC";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
