package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JDBCXAParamsBeanImpl extends SettableBeanImpl implements JDBCXAParamsBean, Serializable {
   private boolean _KeepLogicalConnOpenOnRelease;
   private boolean _KeepXaConnTillTxComplete;
   private boolean _NeedTxCtxOnClose;
   private boolean _NewXaConnForCommit;
   private boolean _RecoverOnlyOnce;
   private boolean _ResourceHealthMonitoring;
   private boolean _RollbackLocalTxUponConnClose;
   private boolean _XaEndOnlyOnce;
   private int _XaRetryDurationSeconds;
   private int _XaRetryIntervalSeconds;
   private boolean _XaSetTransactionTimeout;
   private int _XaTransactionTimeout;
   private static SchemaHelper2 _schemaHelper;

   public JDBCXAParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCXAParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCXAParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isKeepXaConnTillTxComplete() {
      return this._KeepXaConnTillTxComplete;
   }

   public boolean isKeepXaConnTillTxCompleteInherited() {
      return false;
   }

   public boolean isKeepXaConnTillTxCompleteSet() {
      return this._isSet(0);
   }

   public void setKeepXaConnTillTxComplete(boolean param0) {
      boolean _oldVal = this._KeepXaConnTillTxComplete;
      this._KeepXaConnTillTxComplete = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isNeedTxCtxOnClose() {
      return this._NeedTxCtxOnClose;
   }

   public boolean isNeedTxCtxOnCloseInherited() {
      return false;
   }

   public boolean isNeedTxCtxOnCloseSet() {
      return this._isSet(1);
   }

   public void setNeedTxCtxOnClose(boolean param0) {
      boolean _oldVal = this._NeedTxCtxOnClose;
      this._NeedTxCtxOnClose = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isXaEndOnlyOnce() {
      return this._XaEndOnlyOnce;
   }

   public boolean isXaEndOnlyOnceInherited() {
      return false;
   }

   public boolean isXaEndOnlyOnceSet() {
      return this._isSet(2);
   }

   public void setXaEndOnlyOnce(boolean param0) {
      boolean _oldVal = this._XaEndOnlyOnce;
      this._XaEndOnlyOnce = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isNewXaConnForCommit() {
      return this._NewXaConnForCommit;
   }

   public boolean isNewXaConnForCommitInherited() {
      return false;
   }

   public boolean isNewXaConnForCommitSet() {
      return this._isSet(3);
   }

   public void setNewXaConnForCommit(boolean param0) {
      boolean _oldVal = this._NewXaConnForCommit;
      this._NewXaConnForCommit = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isKeepLogicalConnOpenOnRelease() {
      return this._KeepLogicalConnOpenOnRelease;
   }

   public boolean isKeepLogicalConnOpenOnReleaseInherited() {
      return false;
   }

   public boolean isKeepLogicalConnOpenOnReleaseSet() {
      return this._isSet(4);
   }

   public void setKeepLogicalConnOpenOnRelease(boolean param0) {
      boolean _oldVal = this._KeepLogicalConnOpenOnRelease;
      this._KeepLogicalConnOpenOnRelease = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isResourceHealthMonitoring() {
      return this._ResourceHealthMonitoring;
   }

   public boolean isResourceHealthMonitoringInherited() {
      return false;
   }

   public boolean isResourceHealthMonitoringSet() {
      return this._isSet(5);
   }

   public void setResourceHealthMonitoring(boolean param0) {
      boolean _oldVal = this._ResourceHealthMonitoring;
      this._ResourceHealthMonitoring = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isRecoverOnlyOnce() {
      return this._RecoverOnlyOnce;
   }

   public boolean isRecoverOnlyOnceInherited() {
      return false;
   }

   public boolean isRecoverOnlyOnceSet() {
      return this._isSet(6);
   }

   public void setRecoverOnlyOnce(boolean param0) {
      boolean _oldVal = this._RecoverOnlyOnce;
      this._RecoverOnlyOnce = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean isXaSetTransactionTimeout() {
      return this._XaSetTransactionTimeout;
   }

   public boolean isXaSetTransactionTimeoutInherited() {
      return false;
   }

   public boolean isXaSetTransactionTimeoutSet() {
      return this._isSet(7);
   }

   public void setXaSetTransactionTimeout(boolean param0) {
      boolean _oldVal = this._XaSetTransactionTimeout;
      this._XaSetTransactionTimeout = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getXaTransactionTimeout() {
      return this._XaTransactionTimeout;
   }

   public boolean isXaTransactionTimeoutInherited() {
      return false;
   }

   public boolean isXaTransactionTimeoutSet() {
      return this._isSet(8);
   }

   public void setXaTransactionTimeout(int param0) {
      int _oldVal = this._XaTransactionTimeout;
      this._XaTransactionTimeout = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isRollbackLocalTxUponConnClose() {
      return this._RollbackLocalTxUponConnClose;
   }

   public boolean isRollbackLocalTxUponConnCloseInherited() {
      return false;
   }

   public boolean isRollbackLocalTxUponConnCloseSet() {
      return this._isSet(9);
   }

   public void setRollbackLocalTxUponConnClose(boolean param0) {
      boolean _oldVal = this._RollbackLocalTxUponConnClose;
      this._RollbackLocalTxUponConnClose = param0;
      this._postSet(9, _oldVal, param0);
   }

   public int getXaRetryDurationSeconds() {
      return this._XaRetryDurationSeconds;
   }

   public boolean isXaRetryDurationSecondsInherited() {
      return false;
   }

   public boolean isXaRetryDurationSecondsSet() {
      return this._isSet(10);
   }

   public void setXaRetryDurationSeconds(int param0) {
      int _oldVal = this._XaRetryDurationSeconds;
      this._XaRetryDurationSeconds = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getXaRetryIntervalSeconds() {
      return this._XaRetryIntervalSeconds;
   }

   public boolean isXaRetryIntervalSecondsInherited() {
      return false;
   }

   public boolean isXaRetryIntervalSecondsSet() {
      return this._isSet(11);
   }

   public void setXaRetryIntervalSeconds(int param0) {
      int _oldVal = this._XaRetryIntervalSeconds;
      this._XaRetryIntervalSeconds = param0;
      this._postSet(11, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._XaRetryDurationSeconds = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._XaRetryIntervalSeconds = 60;
               if (initOne) {
                  break;
               }
            case 8:
               this._XaTransactionTimeout = 0;
               if (initOne) {
                  break;
               }
            case 4:
               this._KeepLogicalConnOpenOnRelease = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._KeepXaConnTillTxComplete = true;
               if (initOne) {
                  break;
               }
            case 1:
               this._NeedTxCtxOnClose = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._NewXaConnForCommit = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._RecoverOnlyOnce = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._ResourceHealthMonitoring = true;
               if (initOne) {
                  break;
               }
            case 9:
               this._RollbackLocalTxUponConnClose = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._XaEndOnlyOnce = false;
               if (initOne) {
                  break;
               }
            case 7:
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 16:
               if (s.equals("xa-end-only-once")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("recover-only-once")) {
                  return 6;
               }
            case 18:
            case 19:
            case 21:
            case 23:
            case 24:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            default:
               break;
            case 20:
               if (s.equals("need-tx-ctx-on-close")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("xa-transaction-timeout")) {
                  return 8;
               }

               if (s.equals("new-xa-conn-for-commit")) {
                  return 3;
               }
               break;
            case 25:
               if (s.equals("xa-retry-duration-seconds")) {
                  return 10;
               }

               if (s.equals("xa-retry-interval-seconds")) {
                  return 11;
               }
               break;
            case 26:
               if (s.equals("resource-health-monitoring")) {
                  return 5;
               }

               if (s.equals("xa-set-transaction-timeout")) {
                  return 7;
               }
               break;
            case 29:
               if (s.equals("keep-xa-conn-till-tx-complete")) {
                  return 0;
               }
               break;
            case 33:
               if (s.equals("keep-logical-conn-open-on-release")) {
                  return 4;
               }

               if (s.equals("rollback-local-tx-upon-conn-close")) {
                  return 9;
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
               return "keep-xa-conn-till-tx-complete";
            case 1:
               return "need-tx-ctx-on-close";
            case 2:
               return "xa-end-only-once";
            case 3:
               return "new-xa-conn-for-commit";
            case 4:
               return "keep-logical-conn-open-on-release";
            case 5:
               return "resource-health-monitoring";
            case 6:
               return "recover-only-once";
            case 7:
               return "xa-set-transaction-timeout";
            case 8:
               return "xa-transaction-timeout";
            case 9:
               return "rollback-local-tx-upon-conn-close";
            case 10:
               return "xa-retry-duration-seconds";
            case 11:
               return "xa-retry-interval-seconds";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JDBCXAParamsBeanImpl bean;

      protected Helper(JDBCXAParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "KeepXaConnTillTxComplete";
            case 1:
               return "NeedTxCtxOnClose";
            case 2:
               return "XaEndOnlyOnce";
            case 3:
               return "NewXaConnForCommit";
            case 4:
               return "KeepLogicalConnOpenOnRelease";
            case 5:
               return "ResourceHealthMonitoring";
            case 6:
               return "RecoverOnlyOnce";
            case 7:
               return "XaSetTransactionTimeout";
            case 8:
               return "XaTransactionTimeout";
            case 9:
               return "RollbackLocalTxUponConnClose";
            case 10:
               return "XaRetryDurationSeconds";
            case 11:
               return "XaRetryIntervalSeconds";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("XaRetryDurationSeconds")) {
            return 10;
         } else if (propName.equals("XaRetryIntervalSeconds")) {
            return 11;
         } else if (propName.equals("XaTransactionTimeout")) {
            return 8;
         } else if (propName.equals("KeepLogicalConnOpenOnRelease")) {
            return 4;
         } else if (propName.equals("KeepXaConnTillTxComplete")) {
            return 0;
         } else if (propName.equals("NeedTxCtxOnClose")) {
            return 1;
         } else if (propName.equals("NewXaConnForCommit")) {
            return 3;
         } else if (propName.equals("RecoverOnlyOnce")) {
            return 6;
         } else if (propName.equals("ResourceHealthMonitoring")) {
            return 5;
         } else if (propName.equals("RollbackLocalTxUponConnClose")) {
            return 9;
         } else if (propName.equals("XaEndOnlyOnce")) {
            return 2;
         } else {
            return propName.equals("XaSetTransactionTimeout") ? 7 : super.getPropertyIndex(propName);
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
            if (this.bean.isXaRetryDurationSecondsSet()) {
               buf.append("XaRetryDurationSeconds");
               buf.append(String.valueOf(this.bean.getXaRetryDurationSeconds()));
            }

            if (this.bean.isXaRetryIntervalSecondsSet()) {
               buf.append("XaRetryIntervalSeconds");
               buf.append(String.valueOf(this.bean.getXaRetryIntervalSeconds()));
            }

            if (this.bean.isXaTransactionTimeoutSet()) {
               buf.append("XaTransactionTimeout");
               buf.append(String.valueOf(this.bean.getXaTransactionTimeout()));
            }

            if (this.bean.isKeepLogicalConnOpenOnReleaseSet()) {
               buf.append("KeepLogicalConnOpenOnRelease");
               buf.append(String.valueOf(this.bean.isKeepLogicalConnOpenOnRelease()));
            }

            if (this.bean.isKeepXaConnTillTxCompleteSet()) {
               buf.append("KeepXaConnTillTxComplete");
               buf.append(String.valueOf(this.bean.isKeepXaConnTillTxComplete()));
            }

            if (this.bean.isNeedTxCtxOnCloseSet()) {
               buf.append("NeedTxCtxOnClose");
               buf.append(String.valueOf(this.bean.isNeedTxCtxOnClose()));
            }

            if (this.bean.isNewXaConnForCommitSet()) {
               buf.append("NewXaConnForCommit");
               buf.append(String.valueOf(this.bean.isNewXaConnForCommit()));
            }

            if (this.bean.isRecoverOnlyOnceSet()) {
               buf.append("RecoverOnlyOnce");
               buf.append(String.valueOf(this.bean.isRecoverOnlyOnce()));
            }

            if (this.bean.isResourceHealthMonitoringSet()) {
               buf.append("ResourceHealthMonitoring");
               buf.append(String.valueOf(this.bean.isResourceHealthMonitoring()));
            }

            if (this.bean.isRollbackLocalTxUponConnCloseSet()) {
               buf.append("RollbackLocalTxUponConnClose");
               buf.append(String.valueOf(this.bean.isRollbackLocalTxUponConnClose()));
            }

            if (this.bean.isXaEndOnlyOnceSet()) {
               buf.append("XaEndOnlyOnce");
               buf.append(String.valueOf(this.bean.isXaEndOnlyOnce()));
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
            JDBCXAParamsBeanImpl otherTyped = (JDBCXAParamsBeanImpl)other;
            this.computeDiff("XaRetryDurationSeconds", this.bean.getXaRetryDurationSeconds(), otherTyped.getXaRetryDurationSeconds(), false);
            this.computeDiff("XaRetryIntervalSeconds", this.bean.getXaRetryIntervalSeconds(), otherTyped.getXaRetryIntervalSeconds(), false);
            this.computeDiff("XaTransactionTimeout", this.bean.getXaTransactionTimeout(), otherTyped.getXaTransactionTimeout(), false);
            this.computeDiff("KeepLogicalConnOpenOnRelease", this.bean.isKeepLogicalConnOpenOnRelease(), otherTyped.isKeepLogicalConnOpenOnRelease(), false);
            this.computeDiff("KeepXaConnTillTxComplete", this.bean.isKeepXaConnTillTxComplete(), otherTyped.isKeepXaConnTillTxComplete(), false);
            this.computeDiff("NeedTxCtxOnClose", this.bean.isNeedTxCtxOnClose(), otherTyped.isNeedTxCtxOnClose(), false);
            this.computeDiff("NewXaConnForCommit", this.bean.isNewXaConnForCommit(), otherTyped.isNewXaConnForCommit(), false);
            this.computeDiff("RecoverOnlyOnce", this.bean.isRecoverOnlyOnce(), otherTyped.isRecoverOnlyOnce(), false);
            this.computeDiff("ResourceHealthMonitoring", this.bean.isResourceHealthMonitoring(), otherTyped.isResourceHealthMonitoring(), false);
            this.computeDiff("RollbackLocalTxUponConnClose", this.bean.isRollbackLocalTxUponConnClose(), otherTyped.isRollbackLocalTxUponConnClose(), false);
            this.computeDiff("XaEndOnlyOnce", this.bean.isXaEndOnlyOnce(), otherTyped.isXaEndOnlyOnce(), false);
            this.computeDiff("XaSetTransactionTimeout", this.bean.isXaSetTransactionTimeout(), otherTyped.isXaSetTransactionTimeout(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCXAParamsBeanImpl original = (JDBCXAParamsBeanImpl)event.getSourceBean();
            JDBCXAParamsBeanImpl proposed = (JDBCXAParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("XaRetryDurationSeconds")) {
                  original.setXaRetryDurationSeconds(proposed.getXaRetryDurationSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("XaRetryIntervalSeconds")) {
                  original.setXaRetryIntervalSeconds(proposed.getXaRetryIntervalSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("XaTransactionTimeout")) {
                  original.setXaTransactionTimeout(proposed.getXaTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("KeepLogicalConnOpenOnRelease")) {
                  original.setKeepLogicalConnOpenOnRelease(proposed.isKeepLogicalConnOpenOnRelease());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("KeepXaConnTillTxComplete")) {
                  original.setKeepXaConnTillTxComplete(proposed.isKeepXaConnTillTxComplete());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("NeedTxCtxOnClose")) {
                  original.setNeedTxCtxOnClose(proposed.isNeedTxCtxOnClose());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("NewXaConnForCommit")) {
                  original.setNewXaConnForCommit(proposed.isNewXaConnForCommit());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RecoverOnlyOnce")) {
                  original.setRecoverOnlyOnce(proposed.isRecoverOnlyOnce());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ResourceHealthMonitoring")) {
                  original.setResourceHealthMonitoring(proposed.isResourceHealthMonitoring());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("RollbackLocalTxUponConnClose")) {
                  original.setRollbackLocalTxUponConnClose(proposed.isRollbackLocalTxUponConnClose());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("XaEndOnlyOnce")) {
                  original.setXaEndOnlyOnce(proposed.isXaEndOnlyOnce());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("XaSetTransactionTimeout")) {
                  original.setXaSetTransactionTimeout(proposed.isXaSetTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            JDBCXAParamsBeanImpl copy = (JDBCXAParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("XaRetryDurationSeconds")) && this.bean.isXaRetryDurationSecondsSet()) {
               copy.setXaRetryDurationSeconds(this.bean.getXaRetryDurationSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("XaRetryIntervalSeconds")) && this.bean.isXaRetryIntervalSecondsSet()) {
               copy.setXaRetryIntervalSeconds(this.bean.getXaRetryIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("XaTransactionTimeout")) && this.bean.isXaTransactionTimeoutSet()) {
               copy.setXaTransactionTimeout(this.bean.getXaTransactionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepLogicalConnOpenOnRelease")) && this.bean.isKeepLogicalConnOpenOnReleaseSet()) {
               copy.setKeepLogicalConnOpenOnRelease(this.bean.isKeepLogicalConnOpenOnRelease());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepXaConnTillTxComplete")) && this.bean.isKeepXaConnTillTxCompleteSet()) {
               copy.setKeepXaConnTillTxComplete(this.bean.isKeepXaConnTillTxComplete());
            }

            if ((excludeProps == null || !excludeProps.contains("NeedTxCtxOnClose")) && this.bean.isNeedTxCtxOnCloseSet()) {
               copy.setNeedTxCtxOnClose(this.bean.isNeedTxCtxOnClose());
            }

            if ((excludeProps == null || !excludeProps.contains("NewXaConnForCommit")) && this.bean.isNewXaConnForCommitSet()) {
               copy.setNewXaConnForCommit(this.bean.isNewXaConnForCommit());
            }

            if ((excludeProps == null || !excludeProps.contains("RecoverOnlyOnce")) && this.bean.isRecoverOnlyOnceSet()) {
               copy.setRecoverOnlyOnce(this.bean.isRecoverOnlyOnce());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceHealthMonitoring")) && this.bean.isResourceHealthMonitoringSet()) {
               copy.setResourceHealthMonitoring(this.bean.isResourceHealthMonitoring());
            }

            if ((excludeProps == null || !excludeProps.contains("RollbackLocalTxUponConnClose")) && this.bean.isRollbackLocalTxUponConnCloseSet()) {
               copy.setRollbackLocalTxUponConnClose(this.bean.isRollbackLocalTxUponConnClose());
            }

            if ((excludeProps == null || !excludeProps.contains("XaEndOnlyOnce")) && this.bean.isXaEndOnlyOnceSet()) {
               copy.setXaEndOnlyOnce(this.bean.isXaEndOnlyOnce());
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
