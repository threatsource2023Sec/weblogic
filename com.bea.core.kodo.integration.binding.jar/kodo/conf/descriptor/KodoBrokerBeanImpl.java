package kodo.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class KodoBrokerBeanImpl extends BrokerImplBeanImpl implements KodoBrokerBean, Serializable {
   private int _AutoClear;
   private int _AutoDetach;
   private int _DetachState;
   private boolean _DetachedNew;
   private boolean _EvictFromDataCache;
   private boolean _IgnoreChanges;
   private boolean _LargeTransaction;
   private boolean _Multithreaded;
   private boolean _NontransactionalRead;
   private boolean _NontransactionalWrite;
   private boolean _Optimistic;
   private boolean _OrderDirtyObjects;
   private boolean _PopulateDataCache;
   private int _RestoreState;
   private boolean _RetainState;
   private boolean _SyncWithManagedTransactions;
   private static SchemaHelper2 _schemaHelper;

   public KodoBrokerBeanImpl() {
      this._initializeProperty(-1);
   }

   public KodoBrokerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public KodoBrokerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getLargeTransaction() {
      return this._LargeTransaction;
   }

   public boolean isLargeTransactionInherited() {
      return false;
   }

   public boolean isLargeTransactionSet() {
      return this._isSet(0);
   }

   public void setLargeTransaction(boolean param0) {
      boolean _oldVal = this._LargeTransaction;
      this._LargeTransaction = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getAutoClear() {
      return this._AutoClear;
   }

   public boolean isAutoClearInherited() {
      return false;
   }

   public boolean isAutoClearSet() {
      return this._isSet(1);
   }

   public void setAutoClear(int param0) {
      int _oldVal = this._AutoClear;
      this._AutoClear = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getDetachState() {
      return this._DetachState;
   }

   public boolean isDetachStateInherited() {
      return false;
   }

   public boolean isDetachStateSet() {
      return this._isSet(2);
   }

   public void setDetachState(int param0) {
      int _oldVal = this._DetachState;
      this._DetachState = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean getNontransactionalRead() {
      return this._NontransactionalRead;
   }

   public boolean isNontransactionalReadInherited() {
      return false;
   }

   public boolean isNontransactionalReadSet() {
      return this._isSet(3);
   }

   public void setNontransactionalRead(boolean param0) {
      boolean _oldVal = this._NontransactionalRead;
      this._NontransactionalRead = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean getRetainState() {
      return this._RetainState;
   }

   public boolean isRetainStateInherited() {
      return false;
   }

   public boolean isRetainStateSet() {
      return this._isSet(4);
   }

   public void setRetainState(boolean param0) {
      boolean _oldVal = this._RetainState;
      this._RetainState = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean getEvictFromDataCache() {
      return this._EvictFromDataCache;
   }

   public boolean isEvictFromDataCacheInherited() {
      return false;
   }

   public boolean isEvictFromDataCacheSet() {
      return this._isSet(5);
   }

   public void setEvictFromDataCache(boolean param0) {
      boolean _oldVal = this._EvictFromDataCache;
      this._EvictFromDataCache = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean getDetachedNew() {
      return this._DetachedNew;
   }

   public boolean isDetachedNewInherited() {
      return false;
   }

   public boolean isDetachedNewSet() {
      return this._isSet(6);
   }

   public void setDetachedNew(boolean param0) {
      boolean _oldVal = this._DetachedNew;
      this._DetachedNew = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean getOptimistic() {
      return this._Optimistic;
   }

   public boolean isOptimisticInherited() {
      return false;
   }

   public boolean isOptimisticSet() {
      return this._isSet(7);
   }

   public void setOptimistic(boolean param0) {
      boolean _oldVal = this._Optimistic;
      this._Optimistic = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean getNontransactionalWrite() {
      return this._NontransactionalWrite;
   }

   public boolean isNontransactionalWriteInherited() {
      return false;
   }

   public boolean isNontransactionalWriteSet() {
      return this._isSet(8);
   }

   public void setNontransactionalWrite(boolean param0) {
      boolean _oldVal = this._NontransactionalWrite;
      this._NontransactionalWrite = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean getSyncWithManagedTransactions() {
      return this._SyncWithManagedTransactions;
   }

   public boolean isSyncWithManagedTransactionsInherited() {
      return false;
   }

   public boolean isSyncWithManagedTransactionsSet() {
      return this._isSet(9);
   }

   public void setSyncWithManagedTransactions(boolean param0) {
      boolean _oldVal = this._SyncWithManagedTransactions;
      this._SyncWithManagedTransactions = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean getMultithreaded() {
      return this._Multithreaded;
   }

   public boolean isMultithreadedInherited() {
      return false;
   }

   public boolean isMultithreadedSet() {
      return this._isSet(10);
   }

   public void setMultithreaded(boolean param0) {
      boolean _oldVal = this._Multithreaded;
      this._Multithreaded = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean getPopulateDataCache() {
      return this._PopulateDataCache;
   }

   public boolean isPopulateDataCacheInherited() {
      return false;
   }

   public boolean isPopulateDataCacheSet() {
      return this._isSet(11);
   }

   public void setPopulateDataCache(boolean param0) {
      boolean _oldVal = this._PopulateDataCache;
      this._PopulateDataCache = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean getIgnoreChanges() {
      return this._IgnoreChanges;
   }

   public boolean isIgnoreChangesInherited() {
      return false;
   }

   public boolean isIgnoreChangesSet() {
      return this._isSet(12);
   }

   public void setIgnoreChanges(boolean param0) {
      boolean _oldVal = this._IgnoreChanges;
      this._IgnoreChanges = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getAutoDetach() {
      return this._AutoDetach;
   }

   public boolean isAutoDetachInherited() {
      return false;
   }

   public boolean isAutoDetachSet() {
      return this._isSet(13);
   }

   public void setAutoDetach(int param0) {
      int _oldVal = this._AutoDetach;
      this._AutoDetach = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getRestoreState() {
      return this._RestoreState;
   }

   public boolean isRestoreStateInherited() {
      return false;
   }

   public boolean isRestoreStateSet() {
      return this._isSet(14);
   }

   public void setRestoreState(int param0) {
      int _oldVal = this._RestoreState;
      this._RestoreState = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean getOrderDirtyObjects() {
      return this._OrderDirtyObjects;
   }

   public boolean isOrderDirtyObjectsInherited() {
      return false;
   }

   public boolean isOrderDirtyObjectsSet() {
      return this._isSet(15);
   }

   public void setOrderDirtyObjects(boolean param0) {
      boolean _oldVal = this._OrderDirtyObjects;
      this._OrderDirtyObjects = param0;
      this._postSet(15, _oldVal, param0);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._AutoClear = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._AutoDetach = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._DetachState = 1;
               if (initOne) {
                  break;
               }
            case 6:
               this._DetachedNew = true;
               if (initOne) {
                  break;
               }
            case 5:
               this._EvictFromDataCache = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._IgnoreChanges = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._LargeTransaction = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._Multithreaded = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._NontransactionalRead = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._NontransactionalWrite = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._Optimistic = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._OrderDirtyObjects = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._PopulateDataCache = true;
               if (initOne) {
                  break;
               }
            case 14:
               this._RestoreState = 1;
               if (initOne) {
                  break;
               }
            case 4:
               this._RetainState = false;
               if (initOne) {
                  break;
               }
            case 9:
               this._SyncWithManagedTransactions = false;
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

   public static class SchemaHelper2 extends BrokerImplBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("auto-clear")) {
                  return 1;
               }

               if (s.equals("optimistic")) {
                  return 7;
               }
               break;
            case 11:
               if (s.equals("auto-detach")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("detach-state")) {
                  return 2;
               }

               if (s.equals("detached-new")) {
                  return 6;
               }

               if (s.equals("retain-state")) {
                  return 4;
               }
               break;
            case 13:
               if (s.equals("multithreaded")) {
                  return 10;
               }

               if (s.equals("restore-state")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("ignore-changes")) {
                  return 12;
               }
            case 15:
            case 16:
            case 18:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
               break;
            case 17:
               if (s.equals("large-transaction")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("order-dirty-objects")) {
                  return 15;
               }

               if (s.equals("populate-data-cache")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("evict-from-data-cache")) {
                  return 5;
               }

               if (s.equals("nontransactional-read")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("nontransactional-write")) {
                  return 8;
               }
               break;
            case 30:
               if (s.equals("sync-with-managed-transactions")) {
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
               return "large-transaction";
            case 1:
               return "auto-clear";
            case 2:
               return "detach-state";
            case 3:
               return "nontransactional-read";
            case 4:
               return "retain-state";
            case 5:
               return "evict-from-data-cache";
            case 6:
               return "detached-new";
            case 7:
               return "optimistic";
            case 8:
               return "nontransactional-write";
            case 9:
               return "sync-with-managed-transactions";
            case 10:
               return "multithreaded";
            case 11:
               return "populate-data-cache";
            case 12:
               return "ignore-changes";
            case 13:
               return "auto-detach";
            case 14:
               return "restore-state";
            case 15:
               return "order-dirty-objects";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends BrokerImplBeanImpl.Helper {
      private KodoBrokerBeanImpl bean;

      protected Helper(KodoBrokerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LargeTransaction";
            case 1:
               return "AutoClear";
            case 2:
               return "DetachState";
            case 3:
               return "NontransactionalRead";
            case 4:
               return "RetainState";
            case 5:
               return "EvictFromDataCache";
            case 6:
               return "DetachedNew";
            case 7:
               return "Optimistic";
            case 8:
               return "NontransactionalWrite";
            case 9:
               return "SyncWithManagedTransactions";
            case 10:
               return "Multithreaded";
            case 11:
               return "PopulateDataCache";
            case 12:
               return "IgnoreChanges";
            case 13:
               return "AutoDetach";
            case 14:
               return "RestoreState";
            case 15:
               return "OrderDirtyObjects";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutoClear")) {
            return 1;
         } else if (propName.equals("AutoDetach")) {
            return 13;
         } else if (propName.equals("DetachState")) {
            return 2;
         } else if (propName.equals("DetachedNew")) {
            return 6;
         } else if (propName.equals("EvictFromDataCache")) {
            return 5;
         } else if (propName.equals("IgnoreChanges")) {
            return 12;
         } else if (propName.equals("LargeTransaction")) {
            return 0;
         } else if (propName.equals("Multithreaded")) {
            return 10;
         } else if (propName.equals("NontransactionalRead")) {
            return 3;
         } else if (propName.equals("NontransactionalWrite")) {
            return 8;
         } else if (propName.equals("Optimistic")) {
            return 7;
         } else if (propName.equals("OrderDirtyObjects")) {
            return 15;
         } else if (propName.equals("PopulateDataCache")) {
            return 11;
         } else if (propName.equals("RestoreState")) {
            return 14;
         } else if (propName.equals("RetainState")) {
            return 4;
         } else {
            return propName.equals("SyncWithManagedTransactions") ? 9 : super.getPropertyIndex(propName);
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
            if (this.bean.isAutoClearSet()) {
               buf.append("AutoClear");
               buf.append(String.valueOf(this.bean.getAutoClear()));
            }

            if (this.bean.isAutoDetachSet()) {
               buf.append("AutoDetach");
               buf.append(String.valueOf(this.bean.getAutoDetach()));
            }

            if (this.bean.isDetachStateSet()) {
               buf.append("DetachState");
               buf.append(String.valueOf(this.bean.getDetachState()));
            }

            if (this.bean.isDetachedNewSet()) {
               buf.append("DetachedNew");
               buf.append(String.valueOf(this.bean.getDetachedNew()));
            }

            if (this.bean.isEvictFromDataCacheSet()) {
               buf.append("EvictFromDataCache");
               buf.append(String.valueOf(this.bean.getEvictFromDataCache()));
            }

            if (this.bean.isIgnoreChangesSet()) {
               buf.append("IgnoreChanges");
               buf.append(String.valueOf(this.bean.getIgnoreChanges()));
            }

            if (this.bean.isLargeTransactionSet()) {
               buf.append("LargeTransaction");
               buf.append(String.valueOf(this.bean.getLargeTransaction()));
            }

            if (this.bean.isMultithreadedSet()) {
               buf.append("Multithreaded");
               buf.append(String.valueOf(this.bean.getMultithreaded()));
            }

            if (this.bean.isNontransactionalReadSet()) {
               buf.append("NontransactionalRead");
               buf.append(String.valueOf(this.bean.getNontransactionalRead()));
            }

            if (this.bean.isNontransactionalWriteSet()) {
               buf.append("NontransactionalWrite");
               buf.append(String.valueOf(this.bean.getNontransactionalWrite()));
            }

            if (this.bean.isOptimisticSet()) {
               buf.append("Optimistic");
               buf.append(String.valueOf(this.bean.getOptimistic()));
            }

            if (this.bean.isOrderDirtyObjectsSet()) {
               buf.append("OrderDirtyObjects");
               buf.append(String.valueOf(this.bean.getOrderDirtyObjects()));
            }

            if (this.bean.isPopulateDataCacheSet()) {
               buf.append("PopulateDataCache");
               buf.append(String.valueOf(this.bean.getPopulateDataCache()));
            }

            if (this.bean.isRestoreStateSet()) {
               buf.append("RestoreState");
               buf.append(String.valueOf(this.bean.getRestoreState()));
            }

            if (this.bean.isRetainStateSet()) {
               buf.append("RetainState");
               buf.append(String.valueOf(this.bean.getRetainState()));
            }

            if (this.bean.isSyncWithManagedTransactionsSet()) {
               buf.append("SyncWithManagedTransactions");
               buf.append(String.valueOf(this.bean.getSyncWithManagedTransactions()));
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
            KodoBrokerBeanImpl otherTyped = (KodoBrokerBeanImpl)other;
            this.computeDiff("AutoClear", this.bean.getAutoClear(), otherTyped.getAutoClear(), false);
            this.computeDiff("AutoDetach", this.bean.getAutoDetach(), otherTyped.getAutoDetach(), false);
            this.computeDiff("DetachState", this.bean.getDetachState(), otherTyped.getDetachState(), false);
            this.computeDiff("DetachedNew", this.bean.getDetachedNew(), otherTyped.getDetachedNew(), false);
            this.computeDiff("EvictFromDataCache", this.bean.getEvictFromDataCache(), otherTyped.getEvictFromDataCache(), false);
            this.computeDiff("IgnoreChanges", this.bean.getIgnoreChanges(), otherTyped.getIgnoreChanges(), false);
            this.computeDiff("LargeTransaction", this.bean.getLargeTransaction(), otherTyped.getLargeTransaction(), false);
            this.computeDiff("Multithreaded", this.bean.getMultithreaded(), otherTyped.getMultithreaded(), false);
            this.computeDiff("NontransactionalRead", this.bean.getNontransactionalRead(), otherTyped.getNontransactionalRead(), false);
            this.computeDiff("NontransactionalWrite", this.bean.getNontransactionalWrite(), otherTyped.getNontransactionalWrite(), false);
            this.computeDiff("Optimistic", this.bean.getOptimistic(), otherTyped.getOptimistic(), false);
            this.computeDiff("OrderDirtyObjects", this.bean.getOrderDirtyObjects(), otherTyped.getOrderDirtyObjects(), false);
            this.computeDiff("PopulateDataCache", this.bean.getPopulateDataCache(), otherTyped.getPopulateDataCache(), false);
            this.computeDiff("RestoreState", this.bean.getRestoreState(), otherTyped.getRestoreState(), false);
            this.computeDiff("RetainState", this.bean.getRetainState(), otherTyped.getRetainState(), false);
            this.computeDiff("SyncWithManagedTransactions", this.bean.getSyncWithManagedTransactions(), otherTyped.getSyncWithManagedTransactions(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KodoBrokerBeanImpl original = (KodoBrokerBeanImpl)event.getSourceBean();
            KodoBrokerBeanImpl proposed = (KodoBrokerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoClear")) {
                  original.setAutoClear(proposed.getAutoClear());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("AutoDetach")) {
                  original.setAutoDetach(proposed.getAutoDetach());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DetachState")) {
                  original.setDetachState(proposed.getDetachState());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DetachedNew")) {
                  original.setDetachedNew(proposed.getDetachedNew());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("EvictFromDataCache")) {
                  original.setEvictFromDataCache(proposed.getEvictFromDataCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("IgnoreChanges")) {
                  original.setIgnoreChanges(proposed.getIgnoreChanges());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LargeTransaction")) {
                  original.setLargeTransaction(proposed.getLargeTransaction());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Multithreaded")) {
                  original.setMultithreaded(proposed.getMultithreaded());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("NontransactionalRead")) {
                  original.setNontransactionalRead(proposed.getNontransactionalRead());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("NontransactionalWrite")) {
                  original.setNontransactionalWrite(proposed.getNontransactionalWrite());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Optimistic")) {
                  original.setOptimistic(proposed.getOptimistic());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("OrderDirtyObjects")) {
                  original.setOrderDirtyObjects(proposed.getOrderDirtyObjects());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PopulateDataCache")) {
                  original.setPopulateDataCache(proposed.getPopulateDataCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RestoreState")) {
                  original.setRestoreState(proposed.getRestoreState());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("RetainState")) {
                  original.setRetainState(proposed.getRetainState());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SyncWithManagedTransactions")) {
                  original.setSyncWithManagedTransactions(proposed.getSyncWithManagedTransactions());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
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
            KodoBrokerBeanImpl copy = (KodoBrokerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoClear")) && this.bean.isAutoClearSet()) {
               copy.setAutoClear(this.bean.getAutoClear());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoDetach")) && this.bean.isAutoDetachSet()) {
               copy.setAutoDetach(this.bean.getAutoDetach());
            }

            if ((excludeProps == null || !excludeProps.contains("DetachState")) && this.bean.isDetachStateSet()) {
               copy.setDetachState(this.bean.getDetachState());
            }

            if ((excludeProps == null || !excludeProps.contains("DetachedNew")) && this.bean.isDetachedNewSet()) {
               copy.setDetachedNew(this.bean.getDetachedNew());
            }

            if ((excludeProps == null || !excludeProps.contains("EvictFromDataCache")) && this.bean.isEvictFromDataCacheSet()) {
               copy.setEvictFromDataCache(this.bean.getEvictFromDataCache());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreChanges")) && this.bean.isIgnoreChangesSet()) {
               copy.setIgnoreChanges(this.bean.getIgnoreChanges());
            }

            if ((excludeProps == null || !excludeProps.contains("LargeTransaction")) && this.bean.isLargeTransactionSet()) {
               copy.setLargeTransaction(this.bean.getLargeTransaction());
            }

            if ((excludeProps == null || !excludeProps.contains("Multithreaded")) && this.bean.isMultithreadedSet()) {
               copy.setMultithreaded(this.bean.getMultithreaded());
            }

            if ((excludeProps == null || !excludeProps.contains("NontransactionalRead")) && this.bean.isNontransactionalReadSet()) {
               copy.setNontransactionalRead(this.bean.getNontransactionalRead());
            }

            if ((excludeProps == null || !excludeProps.contains("NontransactionalWrite")) && this.bean.isNontransactionalWriteSet()) {
               copy.setNontransactionalWrite(this.bean.getNontransactionalWrite());
            }

            if ((excludeProps == null || !excludeProps.contains("Optimistic")) && this.bean.isOptimisticSet()) {
               copy.setOptimistic(this.bean.getOptimistic());
            }

            if ((excludeProps == null || !excludeProps.contains("OrderDirtyObjects")) && this.bean.isOrderDirtyObjectsSet()) {
               copy.setOrderDirtyObjects(this.bean.getOrderDirtyObjects());
            }

            if ((excludeProps == null || !excludeProps.contains("PopulateDataCache")) && this.bean.isPopulateDataCacheSet()) {
               copy.setPopulateDataCache(this.bean.getPopulateDataCache());
            }

            if ((excludeProps == null || !excludeProps.contains("RestoreState")) && this.bean.isRestoreStateSet()) {
               copy.setRestoreState(this.bean.getRestoreState());
            }

            if ((excludeProps == null || !excludeProps.contains("RetainState")) && this.bean.isRetainStateSet()) {
               copy.setRetainState(this.bean.getRetainState());
            }

            if ((excludeProps == null || !excludeProps.contains("SyncWithManagedTransactions")) && this.bean.isSyncWithManagedTransactionsSet()) {
               copy.setSyncWithManagedTransactions(this.bean.getSyncWithManagedTransactions());
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
