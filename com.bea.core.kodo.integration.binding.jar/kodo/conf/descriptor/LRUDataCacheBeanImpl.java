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

public class LRUDataCacheBeanImpl extends DataCacheBeanImpl implements LRUDataCacheBean, Serializable {
   private int _CacheSize;
   private String _EvictionSchedule;
   private int _SoftReferenceSize;
   private static SchemaHelper2 _schemaHelper;

   public LRUDataCacheBeanImpl() {
      this._initializeProperty(-1);
   }

   public LRUDataCacheBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LRUDataCacheBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getCacheSize() {
      return this._CacheSize;
   }

   public boolean isCacheSizeInherited() {
      return false;
   }

   public boolean isCacheSizeSet() {
      return this._isSet(1);
   }

   public void setCacheSize(int param0) {
      int _oldVal = this._CacheSize;
      this._CacheSize = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getSoftReferenceSize() {
      return this._SoftReferenceSize;
   }

   public boolean isSoftReferenceSizeInherited() {
      return false;
   }

   public boolean isSoftReferenceSizeSet() {
      return this._isSet(2);
   }

   public void setSoftReferenceSize(int param0) {
      int _oldVal = this._SoftReferenceSize;
      this._SoftReferenceSize = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getEvictionSchedule() {
      return this._EvictionSchedule;
   }

   public boolean isEvictionScheduleInherited() {
      return false;
   }

   public boolean isEvictionScheduleSet() {
      return this._isSet(3);
   }

   public void setEvictionSchedule(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EvictionSchedule;
      this._EvictionSchedule = param0;
      this._postSet(3, _oldVal, param0);
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
               this._CacheSize = 1000;
               if (initOne) {
                  break;
               }
            case 3:
               this._EvictionSchedule = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._SoftReferenceSize = -1;
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

   public static class SchemaHelper2 extends DataCacheBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("cache-size")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("eviction-schedule")) {
                  return 3;
               }
               break;
            case 19:
               if (s.equals("soft-reference-size")) {
                  return 2;
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
            case 1:
               return "cache-size";
            case 2:
               return "soft-reference-size";
            case 3:
               return "eviction-schedule";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends DataCacheBeanImpl.Helper {
      private LRUDataCacheBeanImpl bean;

      protected Helper(LRUDataCacheBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "CacheSize";
            case 2:
               return "SoftReferenceSize";
            case 3:
               return "EvictionSchedule";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheSize")) {
            return 1;
         } else if (propName.equals("EvictionSchedule")) {
            return 3;
         } else {
            return propName.equals("SoftReferenceSize") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isCacheSizeSet()) {
               buf.append("CacheSize");
               buf.append(String.valueOf(this.bean.getCacheSize()));
            }

            if (this.bean.isEvictionScheduleSet()) {
               buf.append("EvictionSchedule");
               buf.append(String.valueOf(this.bean.getEvictionSchedule()));
            }

            if (this.bean.isSoftReferenceSizeSet()) {
               buf.append("SoftReferenceSize");
               buf.append(String.valueOf(this.bean.getSoftReferenceSize()));
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
            LRUDataCacheBeanImpl otherTyped = (LRUDataCacheBeanImpl)other;
            this.computeDiff("CacheSize", this.bean.getCacheSize(), otherTyped.getCacheSize(), false);
            this.computeDiff("EvictionSchedule", this.bean.getEvictionSchedule(), otherTyped.getEvictionSchedule(), false);
            this.computeDiff("SoftReferenceSize", this.bean.getSoftReferenceSize(), otherTyped.getSoftReferenceSize(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LRUDataCacheBeanImpl original = (LRUDataCacheBeanImpl)event.getSourceBean();
            LRUDataCacheBeanImpl proposed = (LRUDataCacheBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheSize")) {
                  original.setCacheSize(proposed.getCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EvictionSchedule")) {
                  original.setEvictionSchedule(proposed.getEvictionSchedule());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SoftReferenceSize")) {
                  original.setSoftReferenceSize(proposed.getSoftReferenceSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            LRUDataCacheBeanImpl copy = (LRUDataCacheBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheSize")) && this.bean.isCacheSizeSet()) {
               copy.setCacheSize(this.bean.getCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("EvictionSchedule")) && this.bean.isEvictionScheduleSet()) {
               copy.setEvictionSchedule(this.bean.getEvictionSchedule());
            }

            if ((excludeProps == null || !excludeProps.contains("SoftReferenceSize")) && this.bean.isSoftReferenceSizeSet()) {
               copy.setSoftReferenceSize(this.bean.getSoftReferenceSize());
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
