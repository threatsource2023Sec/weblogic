package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PoolParamsMBeanImpl extends XMLElementMBeanDelegate implements PoolParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_sizeParams = false;
   private SizeParamsMBean sizeParams;
   private boolean isSet_loginDelaySeconds = false;
   private int loginDelaySeconds;
   private boolean isSet_removeInfectedConnectionsEnabled = false;
   private boolean removeInfectedConnectionsEnabled;
   private boolean isSet_secondsToTrustAnIdlePoolConnection = false;
   private int secondsToTrustAnIdlePoolConnection;
   private boolean isSet_jdbcxaDebugLevel = false;
   private int jdbcxaDebugLevel;
   private boolean isSet_leakProfilingEnabled = false;
   private boolean leakProfilingEnabled;
   private boolean isSet_xaParams = false;
   private XaParamsMBean xaParams;
   private boolean isSet_connectionCheckParams = false;
   private ConnectionCheckParamsMBean connectionCheckParams;

   public SizeParamsMBean getSizeParams() {
      return this.sizeParams;
   }

   public void setSizeParams(SizeParamsMBean value) {
      SizeParamsMBean old = this.sizeParams;
      this.sizeParams = value;
      this.isSet_sizeParams = value != null;
      this.checkChange("sizeParams", old, this.sizeParams);
   }

   public int getLoginDelaySeconds() {
      return this.loginDelaySeconds;
   }

   public void setLoginDelaySeconds(int value) {
      int old = this.loginDelaySeconds;
      this.loginDelaySeconds = value;
      this.isSet_loginDelaySeconds = value != -1;
      this.checkChange("loginDelaySeconds", old, this.loginDelaySeconds);
   }

   public boolean isRemoveInfectedConnectionsEnabled() {
      return this.removeInfectedConnectionsEnabled;
   }

   public void setRemoveInfectedConnectionsEnabled(boolean value) {
      boolean old = this.removeInfectedConnectionsEnabled;
      this.removeInfectedConnectionsEnabled = value;
      this.isSet_removeInfectedConnectionsEnabled = true;
      this.checkChange("removeInfectedConnectionsEnabled", old, this.removeInfectedConnectionsEnabled);
   }

   public int getSecondsToTrustAnIdlePoolConnection() {
      return this.secondsToTrustAnIdlePoolConnection;
   }

   public void setSecondsToTrustAnIdlePoolConnection(int value) {
      int old = this.secondsToTrustAnIdlePoolConnection;
      this.secondsToTrustAnIdlePoolConnection = value;
      this.isSet_secondsToTrustAnIdlePoolConnection = value != -1;
      this.checkChange("secondsToTrustAnIdlePoolConnection", old, this.secondsToTrustAnIdlePoolConnection);
   }

   public int getJDBCXADebugLevel() {
      return this.jdbcxaDebugLevel;
   }

   public void setJDBCXADebugLevel(int value) {
      int old = this.jdbcxaDebugLevel;
      this.jdbcxaDebugLevel = value;
      this.isSet_jdbcxaDebugLevel = value != -1;
      this.checkChange("jdbcxaDebugLevel", old, this.jdbcxaDebugLevel);
   }

   public boolean isLeakProfilingEnabled() {
      return this.leakProfilingEnabled;
   }

   public void setLeakProfilingEnabled(boolean value) {
      boolean old = this.leakProfilingEnabled;
      this.leakProfilingEnabled = value;
      this.isSet_leakProfilingEnabled = true;
      this.checkChange("leakProfilingEnabled", old, this.leakProfilingEnabled);
   }

   public XaParamsMBean getXaParams() {
      return this.xaParams;
   }

   public void setXaParams(XaParamsMBean value) {
      XaParamsMBean old = this.xaParams;
      this.xaParams = value;
      this.isSet_xaParams = value != null;
      this.checkChange("xaParams", old, this.xaParams);
   }

   public ConnectionCheckParamsMBean getConnectionCheckParams() {
      return this.connectionCheckParams;
   }

   public void setConnectionCheckParams(ConnectionCheckParamsMBean value) {
      ConnectionCheckParamsMBean old = this.connectionCheckParams;
      this.connectionCheckParams = value;
      this.isSet_connectionCheckParams = value != null;
      this.checkChange("connectionCheckParams", old, this.connectionCheckParams);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<pool-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</pool-params>\n");
      return result.toString();
   }
}
