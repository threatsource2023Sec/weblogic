package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PoolParamsMBeanCustomImpl extends XMLElementMBeanDelegate implements PoolParamsMBean {
   private String descrEncoding = null;
   private String descriptorVersion = null;
   private SizeParamsMBean sizeParams;
   private XaParamsMBean xaParams;
   private Integer loginDelay;
   private Integer secondsToTrustAnIdlePoolConnection;
   private Boolean leakProfilingEnabled;
   private ConnectionCheckParamsMBean connCheckParams;
   private Integer xaDebugLevel;
   private Boolean removeConnsEnabled;

   public void setEncoding(String encoding) {
      this.descrEncoding = encoding;
   }

   public void setVersion(String version) {
      String old = this.descriptorVersion;
      this.descriptorVersion = version;
      this.checkChange("version", old, version);
   }

   public void setSizeParams(SizeParamsMBean newVal) {
      this.sizeParams = newVal;
   }

   public void setXaParams(XaParamsMBean newVal) {
      this.xaParams = newVal;
   }

   public void setLoginDelaySeconds(int newVal) {
      this.loginDelay = new Integer(newVal);
   }

   public void setSecondsToTrustAnIdlePoolConnection(int newVal) {
      this.secondsToTrustAnIdlePoolConnection = new Integer(newVal);
   }

   public void setLeakProfilingEnabled(boolean enabled) {
      this.leakProfilingEnabled = new Boolean(enabled);
   }

   public void setConnectionCheckParams(ConnectionCheckParamsMBean params) {
      this.connCheckParams = params;
   }

   public void setJDBCXADebugLevel(int level) {
      Integer old = this.xaDebugLevel;
      this.xaDebugLevel = new Integer(level);
      this.checkChange("JDBCXADebugLevel", old, this.xaDebugLevel);
   }

   public void setRemoveInfectedConnectionsEnabled(boolean enabled) {
      Boolean old = this.removeConnsEnabled;
      this.removeConnsEnabled = new Boolean(enabled);
      this.checkChange("RemoveInfectedConnectionsEnabled", old, this.removeConnsEnabled);
   }

   public String getEncoding() {
      return this.descrEncoding;
   }

   public String getVersion() {
      return this.descriptorVersion;
   }

   public SizeParamsMBean getSizeParams() {
      return this.sizeParams;
   }

   public XaParamsMBean getXaParams() {
      return this.xaParams;
   }

   public int getLoginDelaySeconds() {
      return this.loginDelay != null ? this.loginDelay : 0;
   }

   public int getSecondsToTrustAnIdlePoolConnection() {
      return this.secondsToTrustAnIdlePoolConnection != null ? this.secondsToTrustAnIdlePoolConnection : 0;
   }

   public boolean isLeakProfilingEnabled() {
      return this.leakProfilingEnabled != null ? this.leakProfilingEnabled : false;
   }

   public ConnectionCheckParamsMBean getConnectionCheckParams() {
      return this.connCheckParams;
   }

   public int getJDBCXADebugLevel() {
      return this.xaDebugLevel != null ? this.xaDebugLevel : 0;
   }

   public boolean isRemoveInfectedConnectionsEnabled() {
      return this.removeConnsEnabled != null ? this.removeConnsEnabled : true;
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<pool-params");
      result.append(">\n");
      if (null != this.getSizeParams()) {
         result.append(this.getSizeParams().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getXaParams()) {
         result.append(this.getXaParams().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append("<login-delay-seconds>").append(this.getLoginDelaySeconds()).append("</login-delay-seconds>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<secs-to-trust-an-idle-pool-con>").append(this.getSecondsToTrustAnIdlePoolConnection()).append("</secs-to-trust-an-idle-pool-con>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<leak-profiling-enabled>").append(ToXML.capitalize((new Boolean(this.isLeakProfilingEnabled())).toString())).append("</leak-profiling-enabled>\n");
      if (null != this.getConnectionCheckParams()) {
         result.append(this.getConnectionCheckParams().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append("<jdbcxa-debug-level>").append(this.getJDBCXADebugLevel()).append("</jdbcxa-debug-level>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<remove-infected-connections-enabled>").append(ToXML.capitalize((new Boolean(this.isRemoveInfectedConnectionsEnabled())).toString())).append("</remove-infected-connections-enabled>\n");
      result.append(ToXML.indent(indentLevel)).append("</pool-params>\n");
      return result.toString();
   }
}
