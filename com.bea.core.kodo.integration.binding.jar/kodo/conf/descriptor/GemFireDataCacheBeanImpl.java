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

public class GemFireDataCacheBeanImpl extends DataCacheBeanImpl implements GemFireDataCacheBean, Serializable {
   private String _EvictionSchedule;
   private String _GemFireCacheName;
   private static SchemaHelper2 _schemaHelper;

   public GemFireDataCacheBeanImpl() {
      this._initializeProperty(-1);
   }

   public GemFireDataCacheBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GemFireDataCacheBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getGemFireCacheName() {
      return this._GemFireCacheName;
   }

   public boolean isGemFireCacheNameInherited() {
      return false;
   }

   public boolean isGemFireCacheNameSet() {
      return this._isSet(1);
   }

   public void setGemFireCacheName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GemFireCacheName;
      this._GemFireCacheName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getEvictionSchedule() {
      return this._EvictionSchedule;
   }

   public boolean isEvictionScheduleInherited() {
      return false;
   }

   public boolean isEvictionScheduleSet() {
      return this._isSet(2);
   }

   public void setEvictionSchedule(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EvictionSchedule;
      this._EvictionSchedule = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._EvictionSchedule = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._GemFireCacheName = "root/kodo-data-cache";
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
            case 17:
               if (s.equals("eviction-schedule")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("gem-fire-cache-name")) {
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
            case 1:
               return "gem-fire-cache-name";
            case 2:
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
      private GemFireDataCacheBeanImpl bean;

      protected Helper(GemFireDataCacheBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "GemFireCacheName";
            case 2:
               return "EvictionSchedule";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EvictionSchedule")) {
            return 2;
         } else {
            return propName.equals("GemFireCacheName") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isEvictionScheduleSet()) {
               buf.append("EvictionSchedule");
               buf.append(String.valueOf(this.bean.getEvictionSchedule()));
            }

            if (this.bean.isGemFireCacheNameSet()) {
               buf.append("GemFireCacheName");
               buf.append(String.valueOf(this.bean.getGemFireCacheName()));
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
            GemFireDataCacheBeanImpl otherTyped = (GemFireDataCacheBeanImpl)other;
            this.computeDiff("EvictionSchedule", this.bean.getEvictionSchedule(), otherTyped.getEvictionSchedule(), false);
            this.computeDiff("GemFireCacheName", this.bean.getGemFireCacheName(), otherTyped.getGemFireCacheName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GemFireDataCacheBeanImpl original = (GemFireDataCacheBeanImpl)event.getSourceBean();
            GemFireDataCacheBeanImpl proposed = (GemFireDataCacheBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EvictionSchedule")) {
                  original.setEvictionSchedule(proposed.getEvictionSchedule());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("GemFireCacheName")) {
                  original.setGemFireCacheName(proposed.getGemFireCacheName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            GemFireDataCacheBeanImpl copy = (GemFireDataCacheBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EvictionSchedule")) && this.bean.isEvictionScheduleSet()) {
               copy.setEvictionSchedule(this.bean.getEvictionSchedule());
            }

            if ((excludeProps == null || !excludeProps.contains("GemFireCacheName")) && this.bean.isGemFireCacheNameSet()) {
               copy.setGemFireCacheName(this.bean.getGemFireCacheName());
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
