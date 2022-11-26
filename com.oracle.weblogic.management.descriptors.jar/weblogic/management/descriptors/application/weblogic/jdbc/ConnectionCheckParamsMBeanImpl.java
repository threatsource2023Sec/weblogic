package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ConnectionCheckParamsMBeanImpl extends XMLElementMBeanDelegate implements ConnectionCheckParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_tableName = false;
   private String tableName;
   private boolean isSet_testFrequencySeconds = false;
   private int testFrequencySeconds;
   private boolean isSet_initSQL = false;
   private String initSQL;
   private boolean isSet_inactiveConnectionTimeoutSeconds = false;
   private int inactiveConnectionTimeoutSeconds;
   private boolean isSet_connectionReserveTimeoutSeconds = false;
   private int connectionReserveTimeoutSeconds;
   private boolean isSet_checkOnReserveEnabled = false;
   private boolean checkOnReserveEnabled;
   private boolean isSet_refreshMinutes = false;
   private int refreshMinutes;
   private boolean isSet_connectionCreationRetryFrequencySeconds = false;
   private int connectionCreationRetryFrequencySeconds;
   private boolean isSet_checkOnCreateEnabled = false;
   private boolean checkOnCreateEnabled;
   private boolean isSet_checkOnReleaseEnabled = false;
   private boolean checkOnReleaseEnabled;

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.tableName;
      this.tableName = value;
      this.isSet_tableName = value != null;
      this.checkChange("tableName", old, this.tableName);
   }

   public int getTestFrequencySeconds() {
      return this.testFrequencySeconds;
   }

   public void setTestFrequencySeconds(int value) {
      int old = this.testFrequencySeconds;
      this.testFrequencySeconds = value;
      this.isSet_testFrequencySeconds = value != -1;
      this.checkChange("testFrequencySeconds", old, this.testFrequencySeconds);
   }

   public String getInitSQL() {
      return this.initSQL;
   }

   public void setInitSQL(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.initSQL;
      this.initSQL = value;
      this.isSet_initSQL = value != null;
      this.checkChange("initSQL", old, this.initSQL);
   }

   public int getInactiveConnectionTimeoutSeconds() {
      return this.inactiveConnectionTimeoutSeconds;
   }

   public void setInactiveConnectionTimeoutSeconds(int value) {
      int old = this.inactiveConnectionTimeoutSeconds;
      this.inactiveConnectionTimeoutSeconds = value;
      this.isSet_inactiveConnectionTimeoutSeconds = value != -1;
      this.checkChange("inactiveConnectionTimeoutSeconds", old, this.inactiveConnectionTimeoutSeconds);
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this.connectionReserveTimeoutSeconds;
   }

   public void setConnectionReserveTimeoutSeconds(int value) {
      int old = this.connectionReserveTimeoutSeconds;
      this.connectionReserveTimeoutSeconds = value;
      this.isSet_connectionReserveTimeoutSeconds = value != -1;
      this.checkChange("connectionReserveTimeoutSeconds", old, this.connectionReserveTimeoutSeconds);
   }

   public boolean isCheckOnReserveEnabled() {
      return this.checkOnReserveEnabled;
   }

   public void setCheckOnReserveEnabled(boolean value) {
      boolean old = this.checkOnReserveEnabled;
      this.checkOnReserveEnabled = value;
      this.isSet_checkOnReserveEnabled = true;
      this.checkChange("checkOnReserveEnabled", old, this.checkOnReserveEnabled);
   }

   public int getRefreshMinutes() {
      return this.refreshMinutes;
   }

   public void setRefreshMinutes(int value) {
      int old = this.refreshMinutes;
      this.refreshMinutes = value;
      this.isSet_refreshMinutes = value != -1;
      this.checkChange("refreshMinutes", old, this.refreshMinutes);
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this.connectionCreationRetryFrequencySeconds;
   }

   public void setConnectionCreationRetryFrequencySeconds(int value) {
      int old = this.connectionCreationRetryFrequencySeconds;
      this.connectionCreationRetryFrequencySeconds = value;
      this.isSet_connectionCreationRetryFrequencySeconds = value != -1;
      this.checkChange("connectionCreationRetryFrequencySeconds", old, this.connectionCreationRetryFrequencySeconds);
   }

   public boolean isCheckOnCreateEnabled() {
      return this.checkOnCreateEnabled;
   }

   public void setCheckOnCreateEnabled(boolean value) {
      boolean old = this.checkOnCreateEnabled;
      this.checkOnCreateEnabled = value;
      this.isSet_checkOnCreateEnabled = true;
      this.checkChange("checkOnCreateEnabled", old, this.checkOnCreateEnabled);
   }

   public boolean isCheckOnReleaseEnabled() {
      return this.checkOnReleaseEnabled;
   }

   public void setCheckOnReleaseEnabled(boolean value) {
      boolean old = this.checkOnReleaseEnabled;
      this.checkOnReleaseEnabled = value;
      this.isSet_checkOnReleaseEnabled = true;
      this.checkChange("checkOnReleaseEnabled", old, this.checkOnReleaseEnabled);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<connection-check-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</connection-check-params>\n");
      return result.toString();
   }
}
