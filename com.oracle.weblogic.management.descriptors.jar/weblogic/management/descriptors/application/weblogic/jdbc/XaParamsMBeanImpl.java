package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class XaParamsMBeanImpl extends XMLElementMBeanDelegate implements XaParamsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_recoverOnlyOnceEnabled = false;
   private boolean recoverOnlyOnceEnabled;
   private boolean isSet_newConnForCommitEnabled = false;
   private boolean newConnForCommitEnabled;
   private boolean isSet_rollbackLocalTxUponConnClose = false;
   private boolean rollbackLocalTxUponConnClose;
   private boolean isSet_xaTransactionTimeout = false;
   private int xaTransactionTimeout;
   private boolean isSet_keepConnUntilTxCompleteEnabled = false;
   private boolean keepConnUntilTxCompleteEnabled;
   private boolean isSet_localTransactionSupported = false;
   private boolean localTransactionSupported;
   private boolean isSet_keepLogicalConnOpenOnRelease = false;
   private boolean keepLogicalConnOpenOnRelease;
   private boolean isSet_preparedStatementCacheSize = false;
   private int preparedStatementCacheSize;
   private boolean isSet_resourceHealthMonitoringEnabled = false;
   private boolean resourceHealthMonitoringEnabled;
   private boolean isSet_endOnlyOnceEnabled = false;
   private boolean endOnlyOnceEnabled;
   private boolean isSet_txContextOnCloseNeeded = false;
   private boolean txContextOnCloseNeeded;
   private boolean isSet_xaSetTransactionTimeout = false;
   private boolean xaSetTransactionTimeout;
   private boolean isSet_xaRetryDurationSeconds = false;
   private int xaRetryDurationSeconds;
   private boolean isSet_xaRetryIntervalSeconds = false;
   private int xaRetryIntervalSeconds;
   private boolean isSet_debugLevel = false;
   private int debugLevel;

   public boolean isRecoverOnlyOnceEnabled() {
      return this.recoverOnlyOnceEnabled;
   }

   public void setRecoverOnlyOnceEnabled(boolean value) {
      boolean old = this.recoverOnlyOnceEnabled;
      this.recoverOnlyOnceEnabled = value;
      this.isSet_recoverOnlyOnceEnabled = true;
      this.checkChange("recoverOnlyOnceEnabled", old, this.recoverOnlyOnceEnabled);
   }

   public boolean isNewConnForCommitEnabled() {
      return this.newConnForCommitEnabled;
   }

   public void setNewConnForCommitEnabled(boolean value) {
      boolean old = this.newConnForCommitEnabled;
      this.newConnForCommitEnabled = value;
      this.isSet_newConnForCommitEnabled = true;
      this.checkChange("newConnForCommitEnabled", old, this.newConnForCommitEnabled);
   }

   public boolean getRollbackLocalTxUponConnClose() {
      return this.rollbackLocalTxUponConnClose;
   }

   public void setRollbackLocalTxUponConnClose(boolean value) {
      boolean old = this.rollbackLocalTxUponConnClose;
      this.rollbackLocalTxUponConnClose = value;
      this.isSet_rollbackLocalTxUponConnClose = true;
      this.checkChange("rollbackLocalTxUponConnClose", old, this.rollbackLocalTxUponConnClose);
   }

   public int getXATransactionTimeout() {
      return this.xaTransactionTimeout;
   }

   public void setXATransactionTimeout(int value) {
      int old = this.xaTransactionTimeout;
      this.xaTransactionTimeout = value;
      this.isSet_xaTransactionTimeout = value != -1;
      this.checkChange("xaTransactionTimeout", old, this.xaTransactionTimeout);
   }

   public boolean isKeepConnUntilTxCompleteEnabled() {
      return this.keepConnUntilTxCompleteEnabled;
   }

   public void setKeepConnUntilTxCompleteEnabled(boolean value) {
      boolean old = this.keepConnUntilTxCompleteEnabled;
      this.keepConnUntilTxCompleteEnabled = value;
      this.isSet_keepConnUntilTxCompleteEnabled = true;
      this.checkChange("keepConnUntilTxCompleteEnabled", old, this.keepConnUntilTxCompleteEnabled);
   }

   public boolean isLocalTransactionSupported() {
      return this.localTransactionSupported;
   }

   public void setLocalTransactionSupported(boolean value) {
      boolean old = this.localTransactionSupported;
      this.localTransactionSupported = value;
      this.isSet_localTransactionSupported = true;
      this.checkChange("localTransactionSupported", old, this.localTransactionSupported);
   }

   public boolean getKeepLogicalConnOpenOnRelease() {
      return this.keepLogicalConnOpenOnRelease;
   }

   public void setKeepLogicalConnOpenOnRelease(boolean value) {
      boolean old = this.keepLogicalConnOpenOnRelease;
      this.keepLogicalConnOpenOnRelease = value;
      this.isSet_keepLogicalConnOpenOnRelease = true;
      this.checkChange("keepLogicalConnOpenOnRelease", old, this.keepLogicalConnOpenOnRelease);
   }

   public int getPreparedStatementCacheSize() {
      return this.preparedStatementCacheSize;
   }

   public void setPreparedStatementCacheSize(int value) {
      int old = this.preparedStatementCacheSize;
      this.preparedStatementCacheSize = value;
      this.isSet_preparedStatementCacheSize = value != -1;
      this.checkChange("preparedStatementCacheSize", old, this.preparedStatementCacheSize);
   }

   public boolean isResourceHealthMonitoringEnabled() {
      return this.resourceHealthMonitoringEnabled;
   }

   public void setResourceHealthMonitoringEnabled(boolean value) {
      boolean old = this.resourceHealthMonitoringEnabled;
      this.resourceHealthMonitoringEnabled = value;
      this.isSet_resourceHealthMonitoringEnabled = true;
      this.checkChange("resourceHealthMonitoringEnabled", old, this.resourceHealthMonitoringEnabled);
   }

   public boolean isEndOnlyOnceEnabled() {
      return this.endOnlyOnceEnabled;
   }

   public void setEndOnlyOnceEnabled(boolean value) {
      boolean old = this.endOnlyOnceEnabled;
      this.endOnlyOnceEnabled = value;
      this.isSet_endOnlyOnceEnabled = true;
      this.checkChange("endOnlyOnceEnabled", old, this.endOnlyOnceEnabled);
   }

   public boolean isTxContextOnCloseNeeded() {
      return this.txContextOnCloseNeeded;
   }

   public void setTxContextOnCloseNeeded(boolean value) {
      boolean old = this.txContextOnCloseNeeded;
      this.txContextOnCloseNeeded = value;
      this.isSet_txContextOnCloseNeeded = true;
      this.checkChange("txContextOnCloseNeeded", old, this.txContextOnCloseNeeded);
   }

   public boolean getXASetTransactionTimeout() {
      return this.xaSetTransactionTimeout;
   }

   public void setXASetTransactionTimeout(boolean value) {
      boolean old = this.xaSetTransactionTimeout;
      this.xaSetTransactionTimeout = value;
      this.isSet_xaSetTransactionTimeout = true;
      this.checkChange("xaSetTransactionTimeout", old, this.xaSetTransactionTimeout);
   }

   public int getXARetryDurationSeconds() {
      return this.xaRetryDurationSeconds;
   }

   public void setXARetryDurationSeconds(int value) {
      int old = this.xaRetryDurationSeconds;
      this.xaRetryDurationSeconds = value;
      this.isSet_xaRetryDurationSeconds = value != -1;
      this.checkChange("xaRetryDurationSeconds", old, this.xaRetryDurationSeconds);
   }

   public int getXARetryIntervalSeconds() {
      return this.xaRetryIntervalSeconds;
   }

   public void setXARetryIntervalSeconds(int value) {
      int old = this.xaRetryIntervalSeconds;
      this.xaRetryIntervalSeconds = value;
      this.isSet_xaRetryIntervalSeconds = value != -1;
      this.checkChange("xaRetryIntervalSeconds", old, this.xaRetryIntervalSeconds);
   }

   public int getDebugLevel() {
      return this.debugLevel;
   }

   public void setDebugLevel(int value) {
      int old = this.debugLevel;
      this.debugLevel = value;
      this.isSet_debugLevel = value != -1;
      this.checkChange("debugLevel", old, this.debugLevel);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<xa-params");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</xa-params>\n");
      return result.toString();
   }
}
