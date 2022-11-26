package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CacheStoreMBeanImpl extends ConfigurationMBeanImpl implements CacheStoreMBean, Serializable {
   private int _BufferMaxSize;
   private int _BufferWriteAttempts;
   private long _BufferWriteTimeout;
   private String _CustomStore;
   private int _StoreBatchSize;
   private String _WorkManager;
   private String _WritePolicy;
   private static SchemaHelper2 _schemaHelper;

   public CacheStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CacheStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CacheStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCustomStore() {
      return this._CustomStore;
   }

   public boolean isCustomStoreInherited() {
      return false;
   }

   public boolean isCustomStoreSet() {
      return this._isSet(10);
   }

   public void setCustomStore(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CustomStore;
      this._CustomStore = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getWritePolicy() {
      if (!this._isSet(11)) {
         try {
            return this.getCustomStore() != null ? "WriteThrough" : "None";
         } catch (NullPointerException var2) {
         }
      }

      return this._WritePolicy;
   }

   public boolean isWritePolicyInherited() {
      return false;
   }

   public boolean isWritePolicySet() {
      return this._isSet(11);
   }

   public void setWritePolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"None", "WriteThrough", "WriteBehind"};
      param0 = LegalChecks.checkInEnum("WritePolicy", param0, _set);
      String _oldVal = this._WritePolicy;
      this._WritePolicy = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getWorkManager() {
      if (!this._isSet(12)) {
         try {
            return ((CacheMBean)this.getParent()).getWorkManager();
         } catch (NullPointerException var2) {
         }
      }

      return this._WorkManager;
   }

   public boolean isWorkManagerInherited() {
      return false;
   }

   public boolean isWorkManagerSet() {
      return this._isSet(12);
   }

   public void setWorkManager(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("WorkManager", param0);
      String _oldVal = this._WorkManager;
      this._WorkManager = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getBufferMaxSize() {
      return this._BufferMaxSize;
   }

   public boolean isBufferMaxSizeInherited() {
      return false;
   }

   public boolean isBufferMaxSizeSet() {
      return this._isSet(13);
   }

   public void setBufferMaxSize(int param0) {
      LegalChecks.checkMin("BufferMaxSize", param0, 1);
      int _oldVal = this._BufferMaxSize;
      this._BufferMaxSize = param0;
      this._postSet(13, _oldVal, param0);
   }

   public long getBufferWriteTimeout() {
      return this._BufferWriteTimeout;
   }

   public boolean isBufferWriteTimeoutInherited() {
      return false;
   }

   public boolean isBufferWriteTimeoutSet() {
      return this._isSet(14);
   }

   public void setBufferWriteTimeout(long param0) {
      LegalChecks.checkMin("BufferWriteTimeout", param0, 1L);
      long _oldVal = this._BufferWriteTimeout;
      this._BufferWriteTimeout = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getBufferWriteAttempts() {
      return this._BufferWriteAttempts;
   }

   public boolean isBufferWriteAttemptsInherited() {
      return false;
   }

   public boolean isBufferWriteAttemptsSet() {
      return this._isSet(15);
   }

   public void setBufferWriteAttempts(int param0) {
      LegalChecks.checkMin("BufferWriteAttempts", param0, 1);
      int _oldVal = this._BufferWriteAttempts;
      this._BufferWriteAttempts = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getStoreBatchSize() {
      return this._StoreBatchSize;
   }

   public boolean isStoreBatchSizeInherited() {
      return false;
   }

   public boolean isStoreBatchSizeSet() {
      return this._isSet(16);
   }

   public void setStoreBatchSize(int param0) {
      LegalChecks.checkMin("StoreBatchSize", param0, 1);
      int _oldVal = this._StoreBatchSize;
      this._StoreBatchSize = param0;
      this._postSet(16, _oldVal, param0);
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._BufferMaxSize = 100;
               if (initOne) {
                  break;
               }
            case 15:
               this._BufferWriteAttempts = 1;
               if (initOne) {
                  break;
               }
            case 14:
               this._BufferWriteTimeout = 100L;
               if (initOne) {
                  break;
               }
            case 10:
               this._CustomStore = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._StoreBatchSize = 1;
               if (initOne) {
                  break;
               }
            case 12:
               this._WorkManager = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._WritePolicy = null;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "CacheStore";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("BufferMaxSize")) {
         oldVal = this._BufferMaxSize;
         this._BufferMaxSize = (Integer)v;
         this._postSet(13, oldVal, this._BufferMaxSize);
      } else if (name.equals("BufferWriteAttempts")) {
         oldVal = this._BufferWriteAttempts;
         this._BufferWriteAttempts = (Integer)v;
         this._postSet(15, oldVal, this._BufferWriteAttempts);
      } else if (name.equals("BufferWriteTimeout")) {
         long oldVal = this._BufferWriteTimeout;
         this._BufferWriteTimeout = (Long)v;
         this._postSet(14, oldVal, this._BufferWriteTimeout);
      } else {
         String oldVal;
         if (name.equals("CustomStore")) {
            oldVal = this._CustomStore;
            this._CustomStore = (String)v;
            this._postSet(10, oldVal, this._CustomStore);
         } else if (name.equals("StoreBatchSize")) {
            oldVal = this._StoreBatchSize;
            this._StoreBatchSize = (Integer)v;
            this._postSet(16, oldVal, this._StoreBatchSize);
         } else if (name.equals("WorkManager")) {
            oldVal = this._WorkManager;
            this._WorkManager = (String)v;
            this._postSet(12, oldVal, this._WorkManager);
         } else if (name.equals("WritePolicy")) {
            oldVal = this._WritePolicy;
            this._WritePolicy = (String)v;
            this._postSet(11, oldVal, this._WritePolicy);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("BufferMaxSize")) {
         return new Integer(this._BufferMaxSize);
      } else if (name.equals("BufferWriteAttempts")) {
         return new Integer(this._BufferWriteAttempts);
      } else if (name.equals("BufferWriteTimeout")) {
         return new Long(this._BufferWriteTimeout);
      } else if (name.equals("CustomStore")) {
         return this._CustomStore;
      } else if (name.equals("StoreBatchSize")) {
         return new Integer(this._StoreBatchSize);
      } else if (name.equals("WorkManager")) {
         return this._WorkManager;
      } else {
         return name.equals("WritePolicy") ? this._WritePolicy : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("custom-store")) {
                  return 10;
               }

               if (s.equals("work-manager")) {
                  return 12;
               }

               if (s.equals("write-policy")) {
                  return 11;
               }
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            default:
               break;
            case 15:
               if (s.equals("buffer-max-size")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("store-batch-size")) {
                  return 16;
               }
               break;
            case 20:
               if (s.equals("buffer-write-timeout")) {
                  return 14;
               }
               break;
            case 21:
               if (s.equals("buffer-write-attempts")) {
                  return 15;
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
            case 10:
               return "custom-store";
            case 11:
               return "write-policy";
            case 12:
               return "work-manager";
            case 13:
               return "buffer-max-size";
            case 14:
               return "buffer-write-timeout";
            case 15:
               return "buffer-write-attempts";
            case 16:
               return "store-batch-size";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private CacheStoreMBeanImpl bean;

      protected Helper(CacheStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "CustomStore";
            case 11:
               return "WritePolicy";
            case 12:
               return "WorkManager";
            case 13:
               return "BufferMaxSize";
            case 14:
               return "BufferWriteTimeout";
            case 15:
               return "BufferWriteAttempts";
            case 16:
               return "StoreBatchSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BufferMaxSize")) {
            return 13;
         } else if (propName.equals("BufferWriteAttempts")) {
            return 15;
         } else if (propName.equals("BufferWriteTimeout")) {
            return 14;
         } else if (propName.equals("CustomStore")) {
            return 10;
         } else if (propName.equals("StoreBatchSize")) {
            return 16;
         } else if (propName.equals("WorkManager")) {
            return 12;
         } else {
            return propName.equals("WritePolicy") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isBufferMaxSizeSet()) {
               buf.append("BufferMaxSize");
               buf.append(String.valueOf(this.bean.getBufferMaxSize()));
            }

            if (this.bean.isBufferWriteAttemptsSet()) {
               buf.append("BufferWriteAttempts");
               buf.append(String.valueOf(this.bean.getBufferWriteAttempts()));
            }

            if (this.bean.isBufferWriteTimeoutSet()) {
               buf.append("BufferWriteTimeout");
               buf.append(String.valueOf(this.bean.getBufferWriteTimeout()));
            }

            if (this.bean.isCustomStoreSet()) {
               buf.append("CustomStore");
               buf.append(String.valueOf(this.bean.getCustomStore()));
            }

            if (this.bean.isStoreBatchSizeSet()) {
               buf.append("StoreBatchSize");
               buf.append(String.valueOf(this.bean.getStoreBatchSize()));
            }

            if (this.bean.isWorkManagerSet()) {
               buf.append("WorkManager");
               buf.append(String.valueOf(this.bean.getWorkManager()));
            }

            if (this.bean.isWritePolicySet()) {
               buf.append("WritePolicy");
               buf.append(String.valueOf(this.bean.getWritePolicy()));
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
            CacheStoreMBeanImpl otherTyped = (CacheStoreMBeanImpl)other;
            this.computeDiff("BufferMaxSize", this.bean.getBufferMaxSize(), otherTyped.getBufferMaxSize(), false);
            this.computeDiff("BufferWriteAttempts", this.bean.getBufferWriteAttempts(), otherTyped.getBufferWriteAttempts(), true);
            this.computeDiff("BufferWriteTimeout", this.bean.getBufferWriteTimeout(), otherTyped.getBufferWriteTimeout(), true);
            this.computeDiff("CustomStore", this.bean.getCustomStore(), otherTyped.getCustomStore(), true);
            this.computeDiff("StoreBatchSize", this.bean.getStoreBatchSize(), otherTyped.getStoreBatchSize(), true);
            this.computeDiff("WorkManager", this.bean.getWorkManager(), otherTyped.getWorkManager(), true);
            this.computeDiff("WritePolicy", this.bean.getWritePolicy(), otherTyped.getWritePolicy(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CacheStoreMBeanImpl original = (CacheStoreMBeanImpl)event.getSourceBean();
            CacheStoreMBeanImpl proposed = (CacheStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BufferMaxSize")) {
                  original.setBufferMaxSize(proposed.getBufferMaxSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("BufferWriteAttempts")) {
                  original.setBufferWriteAttempts(proposed.getBufferWriteAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("BufferWriteTimeout")) {
                  original.setBufferWriteTimeout(proposed.getBufferWriteTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("CustomStore")) {
                  original.setCustomStore(proposed.getCustomStore());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("StoreBatchSize")) {
                  original.setStoreBatchSize(proposed.getStoreBatchSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("WorkManager")) {
                  original.setWorkManager(proposed.getWorkManager());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("WritePolicy")) {
                  original.setWritePolicy(proposed.getWritePolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            CacheStoreMBeanImpl copy = (CacheStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BufferMaxSize")) && this.bean.isBufferMaxSizeSet()) {
               copy.setBufferMaxSize(this.bean.getBufferMaxSize());
            }

            if ((excludeProps == null || !excludeProps.contains("BufferWriteAttempts")) && this.bean.isBufferWriteAttemptsSet()) {
               copy.setBufferWriteAttempts(this.bean.getBufferWriteAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("BufferWriteTimeout")) && this.bean.isBufferWriteTimeoutSet()) {
               copy.setBufferWriteTimeout(this.bean.getBufferWriteTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomStore")) && this.bean.isCustomStoreSet()) {
               copy.setCustomStore(this.bean.getCustomStore());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreBatchSize")) && this.bean.isStoreBatchSizeSet()) {
               copy.setStoreBatchSize(this.bean.getStoreBatchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManager")) && this.bean.isWorkManagerSet()) {
               copy.setWorkManager(this.bean.getWorkManager());
            }

            if ((excludeProps == null || !excludeProps.contains("WritePolicy")) && this.bean.isWritePolicySet()) {
               copy.setWritePolicy(this.bean.getWritePolicy());
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
