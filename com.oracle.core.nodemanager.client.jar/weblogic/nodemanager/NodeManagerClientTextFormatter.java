package weblogic.nodemanager;

import java.text.MessageFormat;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;

public class NodeManagerClientTextFormatter extends BaseTextFormatter {
   private Localizer l10n;

   public NodeManagerClientTextFormatter() {
      this.l10n = L10nLookup.getLocalizer(Locale.getDefault(), "weblogic.nodemanager.NodeManagerClientTextLocalizer", NodeManagerClientTextFormatter.class.getClassLoader());
   }

   public NodeManagerClientTextFormatter(Locale l) {
      this.l10n = L10nLookup.getLocalizer(l, "weblogic.nodemanager.NodeManagerClientTextLocalizer", NodeManagerClientTextFormatter.class.getClassLoader());
   }

   public static NodeManagerClientTextFormatter getInstance() {
      return new NodeManagerClientTextFormatter();
   }

   public static NodeManagerClientTextFormatter getInstance(Locale l) {
      return new NodeManagerClientTextFormatter(l);
   }

   public String getOperationTimedOut(String arg0) {
      String id = "operationTimedOut";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getOperationInterrupted(String arg0) {
      String id = "operationInterrupted";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getDomainNotSet() {
      String id = "domainNotSet";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getServerNotSet() {
      String id = "serverNotSet";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getCommandNotAvailable(String arg0) {
      String id = "commandNotAvailable";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPath(String arg0) {
      String id = "invalidPath";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getIOInterrupted() {
      String id = "IOInterrupted";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownSHError(int arg0) {
      String id = "unknownSHError";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getAlreadyConnected() {
      String id = "alreadyConnected";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoPassword() {
      String id = "noPassword";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoUser() {
      String id = "noUser";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNoConnect(String arg0, String arg1) {
      String id = "noConnect";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getEndOfStream() {
      String id = "endOfStream";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnexpectedResponse(String arg0) {
      String id = "unexpectedResponse";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getProtocolTLSAlertException() {
      String id = "protocolTLSAlertException";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownProtocolException() {
      String id = "unknownProtocolException";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getUnknownClient(String arg0) {
      String id = "unknownClient";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getNullOrEmpty(String arg0) {
      String id = "nullOrEmpty";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidHostName() {
      String id = "invalidHostName";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPort(String arg0) {
      String id = "invalidPort";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDomain() {
      String id = "invalidDomain";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidDomainDir() {
      String id = "invalidDomainDir";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidServerName() {
      String id = "invalidServerName";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidNMHome() {
      String id = "invalidNMHome";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidUser() {
      String id = "invalidUser";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidPwd() {
      String id = "invalidPwd";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getInvalidScriptName() {
      String id = "invalidScriptName";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
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

   public String getServerTypeNotSet() {
      String id = "serverTypeNotSet";
      String subsystem = "Node Manager";
      Object[] args = new Object[0];
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getTxIDRequired(String arg0) {
      String id = "txIDRequired";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String getFileToSendNotReadable(String arg0) {
      String id = "fileToSendNotReadable";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorFromServer(String arg0) {
      String id = "errorFromServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String errorTalkWithServer(String arg0, String arg1, String arg2) {
      String id = "errorTalkWithServer";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0, arg1, arg2};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }

   public String restartNMTimeout(String arg0) {
      String id = "restartNodeManagerTimeout";
      String subsystem = "Node Manager";
      Object[] args = new Object[]{arg0};
      String output = MessageFormat.format(this.l10n.get(id), args);
      return output;
   }
}
