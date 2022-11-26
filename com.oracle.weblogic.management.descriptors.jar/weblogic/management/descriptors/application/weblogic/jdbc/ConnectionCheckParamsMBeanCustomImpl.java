package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ConnectionCheckParamsMBeanCustomImpl extends XMLElementMBeanDelegate implements ConnectionCheckParamsMBean {
   private String descrEncoding = null;
   private String descriptorVersion = null;
   private Integer inactiveConnectionTimeoutSeconds;
   private Integer creationRetryFrequencySeconds;
   private Integer reserveTimeoutSeconds;
   private Integer testFrequencySeconds;
   private Boolean checkOnCreateEnabled;
   private Boolean checkOnReleaseEnabled;
   private Boolean checkOnReserveEnabled;
   private String tableName;
   private String initTableName;
   private Integer refreshMinutes;

   public void setEncoding(String encoding) {
      this.descrEncoding = encoding;
   }

   public void setVersion(String version) {
      String old = this.descriptorVersion;
      this.descriptorVersion = version;
      this.checkChange("version", old, version);
   }

   public void setConnectionCreationRetryFrequencySeconds(int value) {
      Integer old = this.creationRetryFrequencySeconds;
      this.creationRetryFrequencySeconds = new Integer(value);
      this.checkChange("creationRetryFrequencySeconds", old, this.creationRetryFrequencySeconds);
   }

   public void setConnectionReserveTimeoutSeconds(int value) {
      Integer old = this.reserveTimeoutSeconds;
      this.reserveTimeoutSeconds = new Integer(value);
      this.checkChange("connectionReserveTimeoutSeconds", old, this.reserveTimeoutSeconds);
   }

   public void setTestFrequencySeconds(int value) {
      Integer old = this.testFrequencySeconds;
      this.testFrequencySeconds = new Integer(value);
      this.checkChange("testFrequencySeconds", old, this.testFrequencySeconds);
   }

   public void setInactiveConnectionTimeoutSeconds(int value) {
      Integer old = this.inactiveConnectionTimeoutSeconds;
      this.inactiveConnectionTimeoutSeconds = new Integer(value);
      this.checkChange("inactiveConnectionTimeoutSeconds", old, this.inactiveConnectionTimeoutSeconds);
   }

   public void setCheckOnCreateEnabled(boolean value) {
      Boolean old = this.checkOnCreateEnabled;
      this.checkOnCreateEnabled = new Boolean(value);
      this.checkChange("checkOnCreateEnabled", old, this.checkOnCreateEnabled);
   }

   public void setCheckOnReleaseEnabled(boolean value) {
      Boolean old = this.checkOnReleaseEnabled;
      this.checkOnReleaseEnabled = new Boolean(value);
      this.checkChange("checkonreleaseenabled", old, this.checkOnReleaseEnabled);
   }

   public void setCheckOnReserveEnabled(boolean value) {
      Boolean old = this.checkOnReserveEnabled;
      this.checkOnReserveEnabled = new Boolean(value);
      this.checkChange("checkOnReserveEnabled", old, this.checkOnReserveEnabled);
   }

   public void setInitSQL(String value) {
      String old = this.initTableName;
      this.initTableName = value;
      this.checkChange("initSQL", old, value);
   }

   public void setTableName(String value) {
      String old = this.tableName;
      this.tableName = value;
      this.checkChange("tableName", old, this.tableName);
   }

   public void setRefreshMinutes(int value) {
      Integer old = this.refreshMinutes;
      this.refreshMinutes = new Integer(value);
      this.checkChange("refreshMinutes", old, this.refreshMinutes);
   }

   public String getEncoding() {
      return this.descrEncoding;
   }

   public String getVersion() {
      return this.descriptorVersion;
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this.creationRetryFrequencySeconds != null ? this.creationRetryFrequencySeconds : 0;
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this.reserveTimeoutSeconds != null ? this.reserveTimeoutSeconds : 10;
   }

   public int getTestFrequencySeconds() {
      return this.testFrequencySeconds != null ? this.testFrequencySeconds : 0;
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this.inactiveConnectionTimeoutSeconds != null ? this.inactiveConnectionTimeoutSeconds : 0;
   }

   public boolean isCheckOnCreateEnabled() {
      return this.checkOnCreateEnabled != null ? this.checkOnCreateEnabled : false;
   }

   public boolean isCheckOnReleaseEnabled() {
      return this.checkOnReleaseEnabled != null ? this.checkOnReleaseEnabled : false;
   }

   public boolean isCheckOnReserveEnabled() {
      return this.checkOnReserveEnabled != null ? this.checkOnReserveEnabled : false;
   }

   public String getInitSQL() {
      return this.initTableName;
   }

   public String getTableName() {
      return this.tableName;
   }

   public int getRefreshMinutes() {
      return this.refreshMinutes != null ? this.refreshMinutes : 0;
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<connection-check-params");
      result.append(">\n");
      if (null != this.getTableName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<table-name>").append(this.getTableName()).append("</table-name>\n");
      }

      result.append(ToXML.indent(indentLevel + 2)).append("<check-on-reserve-enabled>").append(ToXML.capitalize((new Boolean(this.isCheckOnReserveEnabled())).toString())).append("</check-on-reserve-enabled>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<check-on-release-enabled>").append(ToXML.capitalize((new Boolean(this.isCheckOnReleaseEnabled())).toString())).append("</check-on-release-enabled>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<refresh-minutes>").append(this.getRefreshMinutes()).append("</refresh-minutes>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<check-on-create-enabled>").append(ToXML.capitalize((new Boolean(this.isCheckOnCreateEnabled())).toString())).append("</check-on-create-enabled>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<connection-reserve-timeout-seconds>").append(this.getConnectionReserveTimeoutSeconds()).append("</connection-reserve-timeout-seconds>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<connection-creation-retry-frequency-seconds>").append(this.getConnectionCreationRetryFrequencySeconds()).append("</connection-creation-retry-frequency-seconds>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<inactive-connection-timeout-seconds>").append(this.getInactiveConnectionTimeoutSeconds()).append("</inactive-connection-timeout-seconds>\n");
      result.append(ToXML.indent(indentLevel + 2)).append("<test-frequency-seconds>").append(this.getTestFrequencySeconds()).append("</test-frequency-seconds>\n");
      if (null != this.getInitSQL()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<init-sql>").append(this.getInitSQL()).append("</init-sql>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</connection-check-params>\n");
      return result.toString();
   }
}
