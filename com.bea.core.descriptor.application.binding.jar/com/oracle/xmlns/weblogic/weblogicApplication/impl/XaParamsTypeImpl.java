package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicApplication.XaParamsType;
import javax.xml.namespace.QName;

public class XaParamsTypeImpl extends XmlComplexContentImpl implements XaParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName DEBUGLEVEL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "debug-level");
   private static final QName KEEPCONNUNTILTXCOMPLETEENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "keep-conn-until-tx-complete-enabled");
   private static final QName ENDONLYONCEENABLED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "end-only-once-enabled");
   private static final QName RECOVERONLYONCEENABLED$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "recover-only-once-enabled");
   private static final QName TXCONTEXTONCLOSENEEDED$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "tx-context-on-close-needed");
   private static final QName NEWCONNFORCOMMITENABLED$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "new-conn-for-commit-enabled");
   private static final QName PREPAREDSTATEMENTCACHESIZE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "prepared-statement-cache-size");
   private static final QName KEEPLOGICALCONNOPENONRELEASE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "keep-logical-conn-open-on-release");
   private static final QName LOCALTRANSACTIONSUPPORTED$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "local-transaction-supported");
   private static final QName RESOURCEHEALTHMONITORINGENABLED$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "resource-health-monitoring-enabled");
   private static final QName XASETTRANSACTIONTIMEOUT$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xa-set-transaction-timeout");
   private static final QName XATRANSACTIONTIMEOUT$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "xa-transaction-timeout");
   private static final QName ROLLBACKLOCALTXUPONCONNCLOSE$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "rollback-localtx-upon-connclose");

   public XaParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEBUGLEVEL$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DEBUGLEVEL$0, 0);
         return target;
      }
   }

   public boolean isSetDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEBUGLEVEL$0) != 0;
      }
   }

   public void setDebugLevel(int debugLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEBUGLEVEL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEBUGLEVEL$0);
         }

         target.setIntValue(debugLevel);
      }
   }

   public void xsetDebugLevel(XmlInt debugLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DEBUGLEVEL$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(DEBUGLEVEL$0);
         }

         target.set(debugLevel);
      }
   }

   public void unsetDebugLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEBUGLEVEL$0, 0);
      }
   }

   public TrueFalseType getKeepConnUntilTxCompleteEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(KEEPCONNUNTILTXCOMPLETEENABLED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetKeepConnUntilTxCompleteEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEEPCONNUNTILTXCOMPLETEENABLED$2) != 0;
      }
   }

   public void setKeepConnUntilTxCompleteEnabled(TrueFalseType keepConnUntilTxCompleteEnabled) {
      this.generatedSetterHelperImpl(keepConnUntilTxCompleteEnabled, KEEPCONNUNTILTXCOMPLETEENABLED$2, 0, (short)1);
   }

   public TrueFalseType addNewKeepConnUntilTxCompleteEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(KEEPCONNUNTILTXCOMPLETEENABLED$2);
         return target;
      }
   }

   public void unsetKeepConnUntilTxCompleteEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEEPCONNUNTILTXCOMPLETEENABLED$2, 0);
      }
   }

   public TrueFalseType getEndOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENDONLYONCEENABLED$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEndOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENDONLYONCEENABLED$4) != 0;
      }
   }

   public void setEndOnlyOnceEnabled(TrueFalseType endOnlyOnceEnabled) {
      this.generatedSetterHelperImpl(endOnlyOnceEnabled, ENDONLYONCEENABLED$4, 0, (short)1);
   }

   public TrueFalseType addNewEndOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENDONLYONCEENABLED$4);
         return target;
      }
   }

   public void unsetEndOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENDONLYONCEENABLED$4, 0);
      }
   }

   public TrueFalseType getRecoverOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(RECOVERONLYONCEENABLED$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRecoverOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RECOVERONLYONCEENABLED$6) != 0;
      }
   }

   public void setRecoverOnlyOnceEnabled(TrueFalseType recoverOnlyOnceEnabled) {
      this.generatedSetterHelperImpl(recoverOnlyOnceEnabled, RECOVERONLYONCEENABLED$6, 0, (short)1);
   }

   public TrueFalseType addNewRecoverOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(RECOVERONLYONCEENABLED$6);
         return target;
      }
   }

   public void unsetRecoverOnlyOnceEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RECOVERONLYONCEENABLED$6, 0);
      }
   }

   public TrueFalseType getTxContextOnCloseNeeded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(TXCONTEXTONCLOSENEEDED$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTxContextOnCloseNeeded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TXCONTEXTONCLOSENEEDED$8) != 0;
      }
   }

   public void setTxContextOnCloseNeeded(TrueFalseType txContextOnCloseNeeded) {
      this.generatedSetterHelperImpl(txContextOnCloseNeeded, TXCONTEXTONCLOSENEEDED$8, 0, (short)1);
   }

   public TrueFalseType addNewTxContextOnCloseNeeded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(TXCONTEXTONCLOSENEEDED$8);
         return target;
      }
   }

   public void unsetTxContextOnCloseNeeded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TXCONTEXTONCLOSENEEDED$8, 0);
      }
   }

   public TrueFalseType getNewConnForCommitEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(NEWCONNFORCOMMITENABLED$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetNewConnForCommitEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NEWCONNFORCOMMITENABLED$10) != 0;
      }
   }

   public void setNewConnForCommitEnabled(TrueFalseType newConnForCommitEnabled) {
      this.generatedSetterHelperImpl(newConnForCommitEnabled, NEWCONNFORCOMMITENABLED$10, 0, (short)1);
   }

   public TrueFalseType addNewNewConnForCommitEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(NEWCONNFORCOMMITENABLED$10);
         return target;
      }
   }

   public void unsetNewConnForCommitEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NEWCONNFORCOMMITENABLED$10, 0);
      }
   }

   public int getPreparedStatementCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PREPAREDSTATEMENTCACHESIZE$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetPreparedStatementCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PREPAREDSTATEMENTCACHESIZE$12, 0);
         return target;
      }
   }

   public boolean isSetPreparedStatementCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREPAREDSTATEMENTCACHESIZE$12) != 0;
      }
   }

   public void setPreparedStatementCacheSize(int preparedStatementCacheSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PREPAREDSTATEMENTCACHESIZE$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PREPAREDSTATEMENTCACHESIZE$12);
         }

         target.setIntValue(preparedStatementCacheSize);
      }
   }

   public void xsetPreparedStatementCacheSize(XmlInt preparedStatementCacheSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PREPAREDSTATEMENTCACHESIZE$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(PREPAREDSTATEMENTCACHESIZE$12);
         }

         target.set(preparedStatementCacheSize);
      }
   }

   public void unsetPreparedStatementCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREPAREDSTATEMENTCACHESIZE$12, 0);
      }
   }

   public TrueFalseType getKeepLogicalConnOpenOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(KEEPLOGICALCONNOPENONRELEASE$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetKeepLogicalConnOpenOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEEPLOGICALCONNOPENONRELEASE$14) != 0;
      }
   }

   public void setKeepLogicalConnOpenOnRelease(TrueFalseType keepLogicalConnOpenOnRelease) {
      this.generatedSetterHelperImpl(keepLogicalConnOpenOnRelease, KEEPLOGICALCONNOPENONRELEASE$14, 0, (short)1);
   }

   public TrueFalseType addNewKeepLogicalConnOpenOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(KEEPLOGICALCONNOPENONRELEASE$14);
         return target;
      }
   }

   public void unsetKeepLogicalConnOpenOnRelease() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEEPLOGICALCONNOPENONRELEASE$14, 0);
      }
   }

   public TrueFalseType getLocalTransactionSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(LOCALTRANSACTIONSUPPORTED$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLocalTransactionSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALTRANSACTIONSUPPORTED$16) != 0;
      }
   }

   public void setLocalTransactionSupported(TrueFalseType localTransactionSupported) {
      this.generatedSetterHelperImpl(localTransactionSupported, LOCALTRANSACTIONSUPPORTED$16, 0, (short)1);
   }

   public TrueFalseType addNewLocalTransactionSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(LOCALTRANSACTIONSUPPORTED$16);
         return target;
      }
   }

   public void unsetLocalTransactionSupported() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALTRANSACTIONSUPPORTED$16, 0);
      }
   }

   public TrueFalseType getResourceHealthMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(RESOURCEHEALTHMONITORINGENABLED$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceHealthMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEHEALTHMONITORINGENABLED$18) != 0;
      }
   }

   public void setResourceHealthMonitoringEnabled(TrueFalseType resourceHealthMonitoringEnabled) {
      this.generatedSetterHelperImpl(resourceHealthMonitoringEnabled, RESOURCEHEALTHMONITORINGENABLED$18, 0, (short)1);
   }

   public TrueFalseType addNewResourceHealthMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(RESOURCEHEALTHMONITORINGENABLED$18);
         return target;
      }
   }

   public void unsetResourceHealthMonitoringEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEHEALTHMONITORINGENABLED$18, 0);
      }
   }

   public TrueFalseType getXaSetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(XASETTRANSACTIONTIMEOUT$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetXaSetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XASETTRANSACTIONTIMEOUT$20) != 0;
      }
   }

   public void setXaSetTransactionTimeout(TrueFalseType xaSetTransactionTimeout) {
      this.generatedSetterHelperImpl(xaSetTransactionTimeout, XASETTRANSACTIONTIMEOUT$20, 0, (short)1);
   }

   public TrueFalseType addNewXaSetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(XASETTRANSACTIONTIMEOUT$20);
         return target;
      }
   }

   public void unsetXaSetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XASETTRANSACTIONTIMEOUT$20, 0);
      }
   }

   public int getXaTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XATRANSACTIONTIMEOUT$22, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetXaTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(XATRANSACTIONTIMEOUT$22, 0);
         return target;
      }
   }

   public boolean isSetXaTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(XATRANSACTIONTIMEOUT$22) != 0;
      }
   }

   public void setXaTransactionTimeout(int xaTransactionTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XATRANSACTIONTIMEOUT$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XATRANSACTIONTIMEOUT$22);
         }

         target.setIntValue(xaTransactionTimeout);
      }
   }

   public void xsetXaTransactionTimeout(XmlInt xaTransactionTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(XATRANSACTIONTIMEOUT$22, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(XATRANSACTIONTIMEOUT$22);
         }

         target.set(xaTransactionTimeout);
      }
   }

   public void unsetXaTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(XATRANSACTIONTIMEOUT$22, 0);
      }
   }

   public TrueFalseType getRollbackLocaltxUponConnclose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ROLLBACKLOCALTXUPONCONNCLOSE$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRollbackLocaltxUponConnclose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROLLBACKLOCALTXUPONCONNCLOSE$24) != 0;
      }
   }

   public void setRollbackLocaltxUponConnclose(TrueFalseType rollbackLocaltxUponConnclose) {
      this.generatedSetterHelperImpl(rollbackLocaltxUponConnclose, ROLLBACKLOCALTXUPONCONNCLOSE$24, 0, (short)1);
   }

   public TrueFalseType addNewRollbackLocaltxUponConnclose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ROLLBACKLOCALTXUPONCONNCLOSE$24);
         return target;
      }
   }

   public void unsetRollbackLocaltxUponConnclose() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROLLBACKLOCALTXUPONCONNCLOSE$24, 0);
      }
   }
}
