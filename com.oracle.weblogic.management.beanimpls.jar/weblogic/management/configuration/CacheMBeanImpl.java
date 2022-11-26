package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CacheMBeanImpl extends DeploymentMBeanImpl implements CacheMBean, Serializable {
   private CacheAsyncListenersMBean _AsyncListeners;
   private String _EvictionPolicy;
   private CacheExpirationMBean _Expiration;
   private String _JNDIName;
   private CacheLoaderMBean _Loader;
   private int _MaxCacheUnits;
   private CacheStoreMBean _Store;
   private CacheTransactionMBean _Transactional;
   private String _WorkManager;
   private static SchemaHelper2 _schemaHelper;

   public CacheMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CacheMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CacheMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(12);
   }

   public void setJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getMaxCacheUnits() {
      return this._MaxCacheUnits;
   }

   public boolean isMaxCacheUnitsInherited() {
      return false;
   }

   public boolean isMaxCacheUnitsSet() {
      return this._isSet(13);
   }

   public void setMaxCacheUnits(int param0) {
      LegalChecks.checkMin("MaxCacheUnits", param0, 1);
      int _oldVal = this._MaxCacheUnits;
      this._MaxCacheUnits = param0;
      this._postSet(13, _oldVal, param0);
   }

   public CacheExpirationMBean getExpiration() {
      return this._Expiration;
   }

   public boolean isExpirationInherited() {
      return false;
   }

   public boolean isExpirationSet() {
      return this._isSet(14) || this._isAnythingSet((AbstractDescriptorBean)this.getExpiration());
   }

   public void setExpiration(CacheExpirationMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 14)) {
         this._postCreate(_child);
      }

      CacheExpirationMBean _oldVal = this._Expiration;
      this._Expiration = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getEvictionPolicy() {
      return this._EvictionPolicy;
   }

   public boolean isEvictionPolicyInherited() {
      return false;
   }

   public boolean isEvictionPolicySet() {
      return this._isSet(15);
   }

   public void setEvictionPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"LRU", "NRU", "FIFO", "LFU"};
      param0 = LegalChecks.checkInEnum("EvictionPolicy", param0, _set);
      String _oldVal = this._EvictionPolicy;
      this._EvictionPolicy = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getWorkManager() {
      return this._WorkManager;
   }

   public boolean isWorkManagerInherited() {
      return false;
   }

   public boolean isWorkManagerSet() {
      return this._isSet(16);
   }

   public void setWorkManager(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WorkManager;
      this._WorkManager = param0;
      this._postSet(16, _oldVal, param0);
   }

   public CacheLoaderMBean getLoader() {
      return this._Loader;
   }

   public boolean isLoaderInherited() {
      return false;
   }

   public boolean isLoaderSet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getLoader());
   }

   public void setLoader(CacheLoaderMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      CacheLoaderMBean _oldVal = this._Loader;
      this._Loader = param0;
      this._postSet(17, _oldVal, param0);
   }

   public CacheStoreMBean getStore() {
      return this._Store;
   }

   public boolean isStoreInherited() {
      return false;
   }

   public boolean isStoreSet() {
      return this._isSet(18) || this._isAnythingSet((AbstractDescriptorBean)this.getStore());
   }

   public void setStore(CacheStoreMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 18)) {
         this._postCreate(_child);
      }

      CacheStoreMBean _oldVal = this._Store;
      this._Store = param0;
      this._postSet(18, _oldVal, param0);
   }

   public CacheTransactionMBean getTransactional() {
      return this._Transactional;
   }

   public boolean isTransactionalInherited() {
      return false;
   }

   public boolean isTransactionalSet() {
      return this._isSet(19) || this._isAnythingSet((AbstractDescriptorBean)this.getTransactional());
   }

   public void setTransactional(CacheTransactionMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 19)) {
         this._postCreate(_child);
      }

      CacheTransactionMBean _oldVal = this._Transactional;
      this._Transactional = param0;
      this._postSet(19, _oldVal, param0);
   }

   public CacheAsyncListenersMBean getAsyncListeners() {
      return this._AsyncListeners;
   }

   public boolean isAsyncListenersInherited() {
      return false;
   }

   public boolean isAsyncListenersSet() {
      return this._isSet(20) || this._isAnythingSet((AbstractDescriptorBean)this.getAsyncListeners());
   }

   public void setAsyncListeners(CacheAsyncListenersMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 20)) {
         this._postCreate(_child);
      }

      CacheAsyncListenersMBean _oldVal = this._AsyncListeners;
      this._AsyncListeners = param0;
      this._postSet(20, _oldVal, param0);
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
      return super._isAnythingSet() || this.isAsyncListenersSet() || this.isExpirationSet() || this.isLoaderSet() || this.isStoreSet() || this.isTransactionalSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 20;
      }

      try {
         switch (idx) {
            case 20:
               this._AsyncListeners = new CacheAsyncListenersMBeanImpl(this, 20);
               this._postCreate((AbstractDescriptorBean)this._AsyncListeners);
               if (initOne) {
                  break;
               }
            case 15:
               this._EvictionPolicy = "LFU";
               if (initOne) {
                  break;
               }
            case 14:
               this._Expiration = new CacheExpirationMBeanImpl(this, 14);
               this._postCreate((AbstractDescriptorBean)this._Expiration);
               if (initOne) {
                  break;
               }
            case 12:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._Loader = new CacheLoaderMBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._Loader);
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxCacheUnits = 64;
               if (initOne) {
                  break;
               }
            case 18:
               this._Store = new CacheStoreMBeanImpl(this, 18);
               this._postCreate((AbstractDescriptorBean)this._Store);
               if (initOne) {
                  break;
               }
            case 19:
               this._Transactional = new CacheTransactionMBeanImpl(this, 19);
               this._postCreate((AbstractDescriptorBean)this._Transactional);
               if (initOne) {
                  break;
               }
            case 16:
               this._WorkManager = null;
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
      return "Cache";
   }

   public void putValue(String name, Object v) {
      if (name.equals("AsyncListeners")) {
         CacheAsyncListenersMBean oldVal = this._AsyncListeners;
         this._AsyncListeners = (CacheAsyncListenersMBean)v;
         this._postSet(20, oldVal, this._AsyncListeners);
      } else {
         String oldVal;
         if (name.equals("EvictionPolicy")) {
            oldVal = this._EvictionPolicy;
            this._EvictionPolicy = (String)v;
            this._postSet(15, oldVal, this._EvictionPolicy);
         } else if (name.equals("Expiration")) {
            CacheExpirationMBean oldVal = this._Expiration;
            this._Expiration = (CacheExpirationMBean)v;
            this._postSet(14, oldVal, this._Expiration);
         } else if (name.equals("JNDIName")) {
            oldVal = this._JNDIName;
            this._JNDIName = (String)v;
            this._postSet(12, oldVal, this._JNDIName);
         } else if (name.equals("Loader")) {
            CacheLoaderMBean oldVal = this._Loader;
            this._Loader = (CacheLoaderMBean)v;
            this._postSet(17, oldVal, this._Loader);
         } else if (name.equals("MaxCacheUnits")) {
            int oldVal = this._MaxCacheUnits;
            this._MaxCacheUnits = (Integer)v;
            this._postSet(13, oldVal, this._MaxCacheUnits);
         } else if (name.equals("Store")) {
            CacheStoreMBean oldVal = this._Store;
            this._Store = (CacheStoreMBean)v;
            this._postSet(18, oldVal, this._Store);
         } else if (name.equals("Transactional")) {
            CacheTransactionMBean oldVal = this._Transactional;
            this._Transactional = (CacheTransactionMBean)v;
            this._postSet(19, oldVal, this._Transactional);
         } else if (name.equals("WorkManager")) {
            oldVal = this._WorkManager;
            this._WorkManager = (String)v;
            this._postSet(16, oldVal, this._WorkManager);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AsyncListeners")) {
         return this._AsyncListeners;
      } else if (name.equals("EvictionPolicy")) {
         return this._EvictionPolicy;
      } else if (name.equals("Expiration")) {
         return this._Expiration;
      } else if (name.equals("JNDIName")) {
         return this._JNDIName;
      } else if (name.equals("Loader")) {
         return this._Loader;
      } else if (name.equals("MaxCacheUnits")) {
         return new Integer(this._MaxCacheUnits);
      } else if (name.equals("Store")) {
         return this._Store;
      } else if (name.equals("Transactional")) {
         return this._Transactional;
      } else {
         return name.equals("WorkManager") ? this._WorkManager : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("store")) {
                  return 18;
               }
               break;
            case 6:
               if (s.equals("loader")) {
                  return 17;
               }
            case 7:
            case 8:
            case 11:
            case 14:
            default:
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("expiration")) {
                  return 14;
               }
               break;
            case 12:
               if (s.equals("work-manager")) {
                  return 16;
               }
               break;
            case 13:
               if (s.equals("transactional")) {
                  return 19;
               }
               break;
            case 15:
               if (s.equals("async-listeners")) {
                  return 20;
               }

               if (s.equals("eviction-policy")) {
                  return 15;
               }

               if (s.equals("max-cache-units")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 14:
               return new CacheExpirationMBeanImpl.SchemaHelper2();
            case 15:
            case 16:
            default:
               return super.getSchemaHelper(propIndex);
            case 17:
               return new CacheLoaderMBeanImpl.SchemaHelper2();
            case 18:
               return new CacheStoreMBeanImpl.SchemaHelper2();
            case 19:
               return new CacheTransactionMBeanImpl.SchemaHelper2();
            case 20:
               return new CacheAsyncListenersMBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "jndi-name";
            case 13:
               return "max-cache-units";
            case 14:
               return "expiration";
            case 15:
               return "eviction-policy";
            case 16:
               return "work-manager";
            case 17:
               return "loader";
            case 18:
               return "store";
            case 19:
               return "transactional";
            case 20:
               return "async-listeners";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 14:
               return true;
            case 15:
            case 16:
            default:
               return super.isBean(propIndex);
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private CacheMBeanImpl bean;

      protected Helper(CacheMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "JNDIName";
            case 13:
               return "MaxCacheUnits";
            case 14:
               return "Expiration";
            case 15:
               return "EvictionPolicy";
            case 16:
               return "WorkManager";
            case 17:
               return "Loader";
            case 18:
               return "Store";
            case 19:
               return "Transactional";
            case 20:
               return "AsyncListeners";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AsyncListeners")) {
            return 20;
         } else if (propName.equals("EvictionPolicy")) {
            return 15;
         } else if (propName.equals("Expiration")) {
            return 14;
         } else if (propName.equals("JNDIName")) {
            return 12;
         } else if (propName.equals("Loader")) {
            return 17;
         } else if (propName.equals("MaxCacheUnits")) {
            return 13;
         } else if (propName.equals("Store")) {
            return 18;
         } else if (propName.equals("Transactional")) {
            return 19;
         } else {
            return propName.equals("WorkManager") ? 16 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAsyncListeners() != null) {
            iterators.add(new ArrayIterator(new CacheAsyncListenersMBean[]{this.bean.getAsyncListeners()}));
         }

         if (this.bean.getExpiration() != null) {
            iterators.add(new ArrayIterator(new CacheExpirationMBean[]{this.bean.getExpiration()}));
         }

         if (this.bean.getLoader() != null) {
            iterators.add(new ArrayIterator(new CacheLoaderMBean[]{this.bean.getLoader()}));
         }

         if (this.bean.getStore() != null) {
            iterators.add(new ArrayIterator(new CacheStoreMBean[]{this.bean.getStore()}));
         }

         if (this.bean.getTransactional() != null) {
            iterators.add(new ArrayIterator(new CacheTransactionMBean[]{this.bean.getTransactional()}));
         }

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
            childValue = this.computeChildHashValue(this.bean.getAsyncListeners());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEvictionPolicySet()) {
               buf.append("EvictionPolicy");
               buf.append(String.valueOf(this.bean.getEvictionPolicy()));
            }

            childValue = this.computeChildHashValue(this.bean.getExpiration());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            childValue = this.computeChildHashValue(this.bean.getLoader());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaxCacheUnitsSet()) {
               buf.append("MaxCacheUnits");
               buf.append(String.valueOf(this.bean.getMaxCacheUnits()));
            }

            childValue = this.computeChildHashValue(this.bean.getStore());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTransactional());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWorkManagerSet()) {
               buf.append("WorkManager");
               buf.append(String.valueOf(this.bean.getWorkManager()));
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
            CacheMBeanImpl otherTyped = (CacheMBeanImpl)other;
            this.computeSubDiff("AsyncListeners", this.bean.getAsyncListeners(), otherTyped.getAsyncListeners());
            this.computeDiff("EvictionPolicy", this.bean.getEvictionPolicy(), otherTyped.getEvictionPolicy(), false);
            this.computeSubDiff("Expiration", this.bean.getExpiration(), otherTyped.getExpiration());
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            this.computeSubDiff("Loader", this.bean.getLoader(), otherTyped.getLoader());
            this.computeDiff("MaxCacheUnits", this.bean.getMaxCacheUnits(), otherTyped.getMaxCacheUnits(), true);
            this.computeSubDiff("Store", this.bean.getStore(), otherTyped.getStore());
            this.computeSubDiff("Transactional", this.bean.getTransactional(), otherTyped.getTransactional());
            this.computeDiff("WorkManager", this.bean.getWorkManager(), otherTyped.getWorkManager(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CacheMBeanImpl original = (CacheMBeanImpl)event.getSourceBean();
            CacheMBeanImpl proposed = (CacheMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AsyncListeners")) {
                  if (type == 2) {
                     original.setAsyncListeners((CacheAsyncListenersMBean)this.createCopy((AbstractDescriptorBean)proposed.getAsyncListeners()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AsyncListeners", original.getAsyncListeners());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("EvictionPolicy")) {
                  original.setEvictionPolicy(proposed.getEvictionPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("Expiration")) {
                  if (type == 2) {
                     original.setExpiration((CacheExpirationMBean)this.createCopy((AbstractDescriptorBean)proposed.getExpiration()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Expiration", original.getExpiration());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Loader")) {
                  if (type == 2) {
                     original.setLoader((CacheLoaderMBean)this.createCopy((AbstractDescriptorBean)proposed.getLoader()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Loader", original.getLoader());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MaxCacheUnits")) {
                  original.setMaxCacheUnits(proposed.getMaxCacheUnits());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Store")) {
                  if (type == 2) {
                     original.setStore((CacheStoreMBean)this.createCopy((AbstractDescriptorBean)proposed.getStore()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Store", original.getStore());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("Transactional")) {
                  if (type == 2) {
                     original.setTransactional((CacheTransactionMBean)this.createCopy((AbstractDescriptorBean)proposed.getTransactional()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Transactional", original.getTransactional());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("WorkManager")) {
                  original.setWorkManager(proposed.getWorkManager());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            CacheMBeanImpl copy = (CacheMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AsyncListeners")) && this.bean.isAsyncListenersSet() && !copy._isSet(20)) {
               Object o = this.bean.getAsyncListeners();
               copy.setAsyncListeners((CacheAsyncListenersMBean)null);
               copy.setAsyncListeners(o == null ? null : (CacheAsyncListenersMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EvictionPolicy")) && this.bean.isEvictionPolicySet()) {
               copy.setEvictionPolicy(this.bean.getEvictionPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Expiration")) && this.bean.isExpirationSet() && !copy._isSet(14)) {
               Object o = this.bean.getExpiration();
               copy.setExpiration((CacheExpirationMBean)null);
               copy.setExpiration(o == null ? null : (CacheExpirationMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Loader")) && this.bean.isLoaderSet() && !copy._isSet(17)) {
               Object o = this.bean.getLoader();
               copy.setLoader((CacheLoaderMBean)null);
               copy.setLoader(o == null ? null : (CacheLoaderMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCacheUnits")) && this.bean.isMaxCacheUnitsSet()) {
               copy.setMaxCacheUnits(this.bean.getMaxCacheUnits());
            }

            if ((excludeProps == null || !excludeProps.contains("Store")) && this.bean.isStoreSet() && !copy._isSet(18)) {
               Object o = this.bean.getStore();
               copy.setStore((CacheStoreMBean)null);
               copy.setStore(o == null ? null : (CacheStoreMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Transactional")) && this.bean.isTransactionalSet() && !copy._isSet(19)) {
               Object o = this.bean.getTransactional();
               copy.setTransactional((CacheTransactionMBean)null);
               copy.setTransactional(o == null ? null : (CacheTransactionMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManager")) && this.bean.isWorkManagerSet()) {
               copy.setWorkManager(this.bean.getWorkManager());
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
         this.inferSubTree(this.bean.getAsyncListeners(), clazz, annotation);
         this.inferSubTree(this.bean.getExpiration(), clazz, annotation);
         this.inferSubTree(this.bean.getLoader(), clazz, annotation);
         this.inferSubTree(this.bean.getStore(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactional(), clazz, annotation);
      }
   }
}
