package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class XAParamsBeanImpl extends AbstractDescriptorBean implements XAParamsBean, Serializable {
   private int _DebugLevel;
   private boolean _EndOnlyOnceEnabled;
   private boolean _KeepConnUntilTxCompleteEnabled;
   private boolean _KeepLogicalConnOpenOnRelease;
   private boolean _LocalTransactionSupported;
   private boolean _NewConnForCommitEnabled;
   private int _PreparedStatementCacheSize;
   private boolean _RecoverOnlyOnceEnabled;
   private boolean _ResourceHealthMonitoringEnabled;
   private boolean _RollbackLocaltxUponConnclose;
   private boolean _TxContextOnCloseNeeded;
   private int _XaRetryDurationSeconds;
   private boolean _XaSetTransactionTimeout;
   private int _XaTransactionTimeout;
   private static SchemaHelper2 _schemaHelper;

   public XAParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public XAParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public XAParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getDebugLevel() {
      return this._DebugLevel;
   }

   public boolean isDebugLevelInherited() {
      return false;
   }

   public boolean isDebugLevelSet() {
      return this._isSet(0);
   }

   public void setDebugLevel(int param0) {
      int _oldVal = this._DebugLevel;
      this._DebugLevel = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isKeepConnUntilTxCompleteEnabled() {
      return this._KeepConnUntilTxCompleteEnabled;
   }

   public boolean isKeepConnUntilTxCompleteEnabledInherited() {
      return false;
   }

   public boolean isKeepConnUntilTxCompleteEnabledSet() {
      return this._isSet(1);
   }

   public void setKeepConnUntilTxCompleteEnabled(boolean param0) {
      boolean _oldVal = this._KeepConnUntilTxCompleteEnabled;
      this._KeepConnUntilTxCompleteEnabled = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isEndOnlyOnceEnabled() {
      return this._EndOnlyOnceEnabled;
   }

   public boolean isEndOnlyOnceEnabledInherited() {
      return false;
   }

   public boolean isEndOnlyOnceEnabledSet() {
      return this._isSet(2);
   }

   public void setEndOnlyOnceEnabled(boolean param0) {
      boolean _oldVal = this._EndOnlyOnceEnabled;
      this._EndOnlyOnceEnabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isRecoverOnlyOnceEnabled() {
      return this._RecoverOnlyOnceEnabled;
   }

   public boolean isRecoverOnlyOnceEnabledInherited() {
      return false;
   }

   public boolean isRecoverOnlyOnceEnabledSet() {
      return this._isSet(3);
   }

   public void setRecoverOnlyOnceEnabled(boolean param0) {
      boolean _oldVal = this._RecoverOnlyOnceEnabled;
      this._RecoverOnlyOnceEnabled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isTxContextOnCloseNeeded() {
      return this._TxContextOnCloseNeeded;
   }

   public boolean isTxContextOnCloseNeededInherited() {
      return false;
   }

   public boolean isTxContextOnCloseNeededSet() {
      return this._isSet(4);
   }

   public void setTxContextOnCloseNeeded(boolean param0) {
      boolean _oldVal = this._TxContextOnCloseNeeded;
      this._TxContextOnCloseNeeded = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isNewConnForCommitEnabled() {
      return this._NewConnForCommitEnabled;
   }

   public boolean isNewConnForCommitEnabledInherited() {
      return false;
   }

   public boolean isNewConnForCommitEnabledSet() {
      return this._isSet(5);
   }

   public void setNewConnForCommitEnabled(boolean param0) {
      boolean _oldVal = this._NewConnForCommitEnabled;
      this._NewConnForCommitEnabled = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getPreparedStatementCacheSize() {
      return this._PreparedStatementCacheSize;
   }

   public boolean isPreparedStatementCacheSizeInherited() {
      return false;
   }

   public boolean isPreparedStatementCacheSizeSet() {
      return this._isSet(6);
   }

   public void setPreparedStatementCacheSize(int param0) {
      int _oldVal = this._PreparedStatementCacheSize;
      this._PreparedStatementCacheSize = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean isKeepLogicalConnOpenOnRelease() {
      return this._KeepLogicalConnOpenOnRelease;
   }

   public boolean isKeepLogicalConnOpenOnReleaseInherited() {
      return false;
   }

   public boolean isKeepLogicalConnOpenOnReleaseSet() {
      return this._isSet(7);
   }

   public void setKeepLogicalConnOpenOnRelease(boolean param0) {
      boolean _oldVal = this._KeepLogicalConnOpenOnRelease;
      this._KeepLogicalConnOpenOnRelease = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean isLocalTransactionSupported() {
      return this._LocalTransactionSupported;
   }

   public boolean isLocalTransactionSupportedInherited() {
      return false;
   }

   public boolean isLocalTransactionSupportedSet() {
      return this._isSet(8);
   }

   public void setLocalTransactionSupported(boolean param0) {
      boolean _oldVal = this._LocalTransactionSupported;
      this._LocalTransactionSupported = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isResourceHealthMonitoringEnabled() {
      return this._ResourceHealthMonitoringEnabled;
   }

   public boolean isResourceHealthMonitoringEnabledInherited() {
      return false;
   }

   public boolean isResourceHealthMonitoringEnabledSet() {
      return this._isSet(9);
   }

   public void setResourceHealthMonitoringEnabled(boolean param0) {
      boolean _oldVal = this._ResourceHealthMonitoringEnabled;
      this._ResourceHealthMonitoringEnabled = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isXaSetTransactionTimeout() {
      return this._XaSetTransactionTimeout;
   }

   public boolean isXaSetTransactionTimeoutInherited() {
      return false;
   }

   public boolean isXaSetTransactionTimeoutSet() {
      return this._isSet(10);
   }

   public void setXaSetTransactionTimeout(boolean param0) {
      boolean _oldVal = this._XaSetTransactionTimeout;
      this._XaSetTransactionTimeout = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getXaTransactionTimeout() {
      return this._XaTransactionTimeout;
   }

   public boolean isXaTransactionTimeoutInherited() {
      return false;
   }

   public boolean isXaTransactionTimeoutSet() {
      return this._isSet(11);
   }

   public void setXaTransactionTimeout(int param0) {
      int _oldVal = this._XaTransactionTimeout;
      this._XaTransactionTimeout = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isRollbackLocaltxUponConnclose() {
      return this._RollbackLocaltxUponConnclose;
   }

   public boolean isRollbackLocaltxUponConncloseInherited() {
      return false;
   }

   public boolean isRollbackLocaltxUponConncloseSet() {
      return this._isSet(12);
   }

   public void setRollbackLocaltxUponConnclose(boolean param0) {
      boolean _oldVal = this._RollbackLocaltxUponConnclose;
      this._RollbackLocaltxUponConnclose = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getXaRetryDurationSeconds() {
      return this._XaRetryDurationSeconds;
   }

   public boolean isXaRetryDurationSecondsInherited() {
      return false;
   }

   public boolean isXaRetryDurationSecondsSet() {
      return this._isSet(13);
   }

   public void setXaRetryDurationSeconds(int param0) {
      int _oldVal = this._XaRetryDurationSeconds;
      this._XaRetryDurationSeconds = param0;
      this._postSet(13, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._DebugLevel = 0;
               if (initOne) {
                  break;
               }
            case 6:
               this._PreparedStatementCacheSize = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._XaRetryDurationSeconds = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._XaTransactionTimeout = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._EndOnlyOnceEnabled = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._KeepConnUntilTxCompleteEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._KeepLogicalConnOpenOnRelease = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._LocalTransactionSupported = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._NewConnForCommitEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._RecoverOnlyOnceEnabled = false;
               if (initOne) {
                  break;
               }
            case 9:
               this._ResourceHealthMonitoringEnabled = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._RollbackLocaltxUponConnclose = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._TxContextOnCloseNeeded = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._XaSetTransactionTimeout = false;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("debug-level")) {
                  return 0;
               }
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 28:
            case 30:
            case 32:
            default:
               break;
            case 21:
               if (s.equals("end-only-once-enabled")) {
                  return 2;
               }
               break;
            case 22:
               if (s.equals("xa-transaction-timeout")) {
                  return 11;
               }
               break;
            case 25:
               if (s.equals("xa-retry-duration-seconds")) {
                  return 13;
               }

               if (s.equals("recover-only-once-enabled")) {
                  return 3;
               }
               break;
            case 26:
               if (s.equals("tx-context-on-close-needed")) {
                  return 4;
               }

               if (s.equals("xa-set-transaction-timeout")) {
                  return 10;
               }
               break;
            case 27:
               if (s.equals("local-transaction-supported")) {
                  return 8;
               }

               if (s.equals("new-conn-for-commit-enabled")) {
                  return 5;
               }
               break;
            case 29:
               if (s.equals("prepared-statement-cache-size")) {
                  return 6;
               }
               break;
            case 31:
               if (s.equals("rollback-localtx-upon-connclose")) {
                  return 12;
               }
               break;
            case 33:
               if (s.equals("keep-logical-conn-open-on-release")) {
                  return 7;
               }
               break;
            case 34:
               if (s.equals("resource-health-monitoring-enabled")) {
                  return 9;
               }
               break;
            case 35:
               if (s.equals("keep-conn-until-tx-complete-enabled")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "debug-level";
            case 1:
               return "keep-conn-until-tx-complete-enabled";
            case 2:
               return "end-only-once-enabled";
            case 3:
               return "recover-only-once-enabled";
            case 4:
               return "tx-context-on-close-needed";
            case 5:
               return "new-conn-for-commit-enabled";
            case 6:
               return "prepared-statement-cache-size";
            case 7:
               return "keep-logical-conn-open-on-release";
            case 8:
               return "local-transaction-supported";
            case 9:
               return "resource-health-monitoring-enabled";
            case 10:
               return "xa-set-transaction-timeout";
            case 11:
               return "xa-transaction-timeout";
            case 12:
               return "rollback-localtx-upon-connclose";
            case 13:
               return "xa-retry-duration-seconds";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private XAParamsBeanImpl bean;

      protected Helper(XAParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DebugLevel";
            case 1:
               return "KeepConnUntilTxCompleteEnabled";
            case 2:
               return "EndOnlyOnceEnabled";
            case 3:
               return "RecoverOnlyOnceEnabled";
            case 4:
               return "TxContextOnCloseNeeded";
            case 5:
               return "NewConnForCommitEnabled";
            case 6:
               return "PreparedStatementCacheSize";
            case 7:
               return "KeepLogicalConnOpenOnRelease";
            case 8:
               return "LocalTransactionSupported";
            case 9:
               return "ResourceHealthMonitoringEnabled";
            case 10:
               return "XaSetTransactionTimeout";
            case 11:
               return "XaTransactionTimeout";
            case 12:
               return "RollbackLocaltxUponConnclose";
            case 13:
               return "XaRetryDurationSeconds";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DebugLevel")) {
            return 0;
         } else if (propName.equals("PreparedStatementCacheSize")) {
            return 6;
         } else if (propName.equals("XaRetryDurationSeconds")) {
            return 13;
         } else if (propName.equals("XaTransactionTimeout")) {
            return 11;
         } else if (propName.equals("EndOnlyOnceEnabled")) {
            return 2;
         } else if (propName.equals("KeepConnUntilTxCompleteEnabled")) {
            return 1;
         } else if (propName.equals("KeepLogicalConnOpenOnRelease")) {
            return 7;
         } else if (propName.equals("LocalTransactionSupported")) {
            return 8;
         } else if (propName.equals("NewConnForCommitEnabled")) {
            return 5;
         } else if (propName.equals("RecoverOnlyOnceEnabled")) {
            return 3;
         } else if (propName.equals("ResourceHealthMonitoringEnabled")) {
            return 9;
         } else if (propName.equals("RollbackLocaltxUponConnclose")) {
            return 12;
         } else if (propName.equals("TxContextOnCloseNeeded")) {
            return 4;
         } else {
            return propName.equals("XaSetTransactionTimeout") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isDebugLevelSet()) {
               buf.append("DebugLevel");
               buf.append(String.valueOf(this.bean.getDebugLevel()));
            }

            if (this.bean.isPreparedStatementCacheSizeSet()) {
               buf.append("PreparedStatementCacheSize");
               buf.append(String.valueOf(this.bean.getPreparedStatementCacheSize()));
            }

            if (this.bean.isXaRetryDurationSecondsSet()) {
               buf.append("XaRetryDurationSeconds");
               buf.append(String.valueOf(this.bean.getXaRetryDurationSeconds()));
            }

            if (this.bean.isXaTransactionTimeoutSet()) {
               buf.append("XaTransactionTimeout");
               buf.append(String.valueOf(this.bean.getXaTransactionTimeout()));
            }

            if (this.bean.isEndOnlyOnceEnabledSet()) {
               buf.append("EndOnlyOnceEnabled");
               buf.append(String.valueOf(this.bean.isEndOnlyOnceEnabled()));
            }

            if (this.bean.isKeepConnUntilTxCompleteEnabledSet()) {
               buf.append("KeepConnUntilTxCompleteEnabled");
               buf.append(String.valueOf(this.bean.isKeepConnUntilTxCompleteEnabled()));
            }

            if (this.bean.isKeepLogicalConnOpenOnReleaseSet()) {
               buf.append("KeepLogicalConnOpenOnRelease");
               buf.append(String.valueOf(this.bean.isKeepLogicalConnOpenOnRelease()));
            }

            if (this.bean.isLocalTransactionSupportedSet()) {
               buf.append("LocalTransactionSupported");
               buf.append(String.valueOf(this.bean.isLocalTransactionSupported()));
            }

            if (this.bean.isNewConnForCommitEnabledSet()) {
               buf.append("NewConnForCommitEnabled");
               buf.append(String.valueOf(this.bean.isNewConnForCommitEnabled()));
            }

            if (this.bean.isRecoverOnlyOnceEnabledSet()) {
               buf.append("RecoverOnlyOnceEnabled");
               buf.append(String.valueOf(this.bean.isRecoverOnlyOnceEnabled()));
            }

            if (this.bean.isResourceHealthMonitoringEnabledSet()) {
               buf.append("ResourceHealthMonitoringEnabled");
               buf.append(String.valueOf(this.bean.isResourceHealthMonitoringEnabled()));
            }

            if (this.bean.isRollbackLocaltxUponConncloseSet()) {
               buf.append("RollbackLocaltxUponConnclose");
               buf.append(String.valueOf(this.bean.isRollbackLocaltxUponConnclose()));
            }

            if (this.bean.isTxContextOnCloseNeededSet()) {
               buf.append("TxContextOnCloseNeeded");
               buf.append(String.valueOf(this.bean.isTxContextOnCloseNeeded()));
            }

            if (this.bean.isXaSetTransactionTimeoutSet()) {
               buf.append("XaSetTransactionTimeout");
               buf.append(String.valueOf(this.bean.isXaSetTransactionTimeout()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            XAParamsBeanImpl otherTyped = (XAParamsBeanImpl)other;
            this.computeDiff("DebugLevel", this.bean.getDebugLevel(), otherTyped.getDebugLevel(), false);
            this.computeDiff("PreparedStatementCacheSize", this.bean.getPreparedStatementCacheSize(), otherTyped.getPreparedStatementCacheSize(), false);
            this.computeDiff("XaRetryDurationSeconds", this.bean.getXaRetryDurationSeconds(), otherTyped.getXaRetryDurationSeconds(), false);
            this.computeDiff("XaTransactionTimeout", this.bean.getXaTransactionTimeout(), otherTyped.getXaTransactionTimeout(), false);
            this.computeDiff("EndOnlyOnceEnabled", this.bean.isEndOnlyOnceEnabled(), otherTyped.isEndOnlyOnceEnabled(), false);
            this.computeDiff("KeepConnUntilTxCompleteEnabled", this.bean.isKeepConnUntilTxCompleteEnabled(), otherTyped.isKeepConnUntilTxCompleteEnabled(), false);
            this.computeDiff("KeepLogicalConnOpenOnRelease", this.bean.isKeepLogicalConnOpenOnRelease(), otherTyped.isKeepLogicalConnOpenOnRelease(), false);
            this.computeDiff("LocalTransactionSupported", this.bean.isLocalTransactionSupported(), otherTyped.isLocalTransactionSupported(), false);
            this.computeDiff("NewConnForCommitEnabled", this.bean.isNewConnForCommitEnabled(), otherTyped.isNewConnForCommitEnabled(), false);
            this.computeDiff("RecoverOnlyOnceEnabled", this.bean.isRecoverOnlyOnceEnabled(), otherTyped.isRecoverOnlyOnceEnabled(), false);
            this.computeDiff("ResourceHealthMonitoringEnabled", this.bean.isResourceHealthMonitoringEnabled(), otherTyped.isResourceHealthMonitoringEnabled(), false);
            this.computeDiff("RollbackLocaltxUponConnclose", this.bean.isRollbackLocaltxUponConnclose(), otherTyped.isRollbackLocaltxUponConnclose(), false);
            this.computeDiff("TxContextOnCloseNeeded", this.bean.isTxContextOnCloseNeeded(), otherTyped.isTxContextOnCloseNeeded(), false);
            this.computeDiff("XaSetTransactionTimeout", this.bean.isXaSetTransactionTimeout(), otherTyped.isXaSetTransactionTimeout(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            XAParamsBeanImpl original = (XAParamsBeanImpl)event.getSourceBean();
            XAParamsBeanImpl proposed = (XAParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DebugLevel")) {
                  original.setDebugLevel(proposed.getDebugLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PreparedStatementCacheSize")) {
                  original.setPreparedStatementCacheSize(proposed.getPreparedStatementCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("XaRetryDurationSeconds")) {
                  original.setXaRetryDurationSeconds(proposed.getXaRetryDurationSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("XaTransactionTimeout")) {
                  original.setXaTransactionTimeout(proposed.getXaTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("EndOnlyOnceEnabled")) {
                  original.setEndOnlyOnceEnabled(proposed.isEndOnlyOnceEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("KeepConnUntilTxCompleteEnabled")) {
                  original.setKeepConnUntilTxCompleteEnabled(proposed.isKeepConnUntilTxCompleteEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("KeepLogicalConnOpenOnRelease")) {
                  original.setKeepLogicalConnOpenOnRelease(proposed.isKeepLogicalConnOpenOnRelease());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("LocalTransactionSupported")) {
                  original.setLocalTransactionSupported(proposed.isLocalTransactionSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("NewConnForCommitEnabled")) {
                  original.setNewConnForCommitEnabled(proposed.isNewConnForCommitEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("RecoverOnlyOnceEnabled")) {
                  original.setRecoverOnlyOnceEnabled(proposed.isRecoverOnlyOnceEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ResourceHealthMonitoringEnabled")) {
                  original.setResourceHealthMonitoringEnabled(proposed.isResourceHealthMonitoringEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("RollbackLocaltxUponConnclose")) {
                  original.setRollbackLocaltxUponConnclose(proposed.isRollbackLocaltxUponConnclose());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("TxContextOnCloseNeeded")) {
                  original.setTxContextOnCloseNeeded(proposed.isTxContextOnCloseNeeded());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("XaSetTransactionTimeout")) {
                  original.setXaSetTransactionTimeout(proposed.isXaSetTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            XAParamsBeanImpl copy = (XAParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DebugLevel")) && this.bean.isDebugLevelSet()) {
               copy.setDebugLevel(this.bean.getDebugLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("PreparedStatementCacheSize")) && this.bean.isPreparedStatementCacheSizeSet()) {
               copy.setPreparedStatementCacheSize(this.bean.getPreparedStatementCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("XaRetryDurationSeconds")) && this.bean.isXaRetryDurationSecondsSet()) {
               copy.setXaRetryDurationSeconds(this.bean.getXaRetryDurationSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("XaTransactionTimeout")) && this.bean.isXaTransactionTimeoutSet()) {
               copy.setXaTransactionTimeout(this.bean.getXaTransactionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("EndOnlyOnceEnabled")) && this.bean.isEndOnlyOnceEnabledSet()) {
               copy.setEndOnlyOnceEnabled(this.bean.isEndOnlyOnceEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepConnUntilTxCompleteEnabled")) && this.bean.isKeepConnUntilTxCompleteEnabledSet()) {
               copy.setKeepConnUntilTxCompleteEnabled(this.bean.isKeepConnUntilTxCompleteEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepLogicalConnOpenOnRelease")) && this.bean.isKeepLogicalConnOpenOnReleaseSet()) {
               copy.setKeepLogicalConnOpenOnRelease(this.bean.isKeepLogicalConnOpenOnRelease());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalTransactionSupported")) && this.bean.isLocalTransactionSupportedSet()) {
               copy.setLocalTransactionSupported(this.bean.isLocalTransactionSupported());
            }

            if ((excludeProps == null || !excludeProps.contains("NewConnForCommitEnabled")) && this.bean.isNewConnForCommitEnabledSet()) {
               copy.setNewConnForCommitEnabled(this.bean.isNewConnForCommitEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RecoverOnlyOnceEnabled")) && this.bean.isRecoverOnlyOnceEnabledSet()) {
               copy.setRecoverOnlyOnceEnabled(this.bean.isRecoverOnlyOnceEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceHealthMonitoringEnabled")) && this.bean.isResourceHealthMonitoringEnabledSet()) {
               copy.setResourceHealthMonitoringEnabled(this.bean.isResourceHealthMonitoringEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RollbackLocaltxUponConnclose")) && this.bean.isRollbackLocaltxUponConncloseSet()) {
               copy.setRollbackLocaltxUponConnclose(this.bean.isRollbackLocaltxUponConnclose());
            }

            if ((excludeProps == null || !excludeProps.contains("TxContextOnCloseNeeded")) && this.bean.isTxContextOnCloseNeededSet()) {
               copy.setTxContextOnCloseNeeded(this.bean.isTxContextOnCloseNeeded());
            }

            if ((excludeProps == null || !excludeProps.contains("XaSetTransactionTimeout")) && this.bean.isXaSetTransactionTimeoutSet()) {
               copy.setXaSetTransactionTimeout(this.bean.isXaSetTransactionTimeout());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
