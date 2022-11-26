package kodo.conf.descriptor;

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

public class TangosolDataCacheBeanImpl extends DataCacheBeanImpl implements TangosolDataCacheBean, Serializable {
   private boolean _ClearOnClose;
   private String _EvictionSchedule;
   private String _TangosolCacheName;
   private String _TangosolCacheType;
   private static SchemaHelper2 _schemaHelper;

   public TangosolDataCacheBeanImpl() {
      this._initializeProperty(-1);
   }

   public TangosolDataCacheBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TangosolDataCacheBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getClearOnClose() {
      return this._ClearOnClose;
   }

   public boolean isClearOnCloseInherited() {
      return false;
   }

   public boolean isClearOnCloseSet() {
      return this._isSet(1);
   }

   public void setClearOnClose(boolean param0) {
      boolean _oldVal = this._ClearOnClose;
      this._ClearOnClose = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTangosolCacheType() {
      return this._TangosolCacheType;
   }

   public boolean isTangosolCacheTypeInherited() {
      return false;
   }

   public boolean isTangosolCacheTypeSet() {
      return this._isSet(2);
   }

   public void setTangosolCacheType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"distributed", "replicated", "named"};
      param0 = LegalChecks.checkInEnum("TangosolCacheType", param0, _set);
      String _oldVal = this._TangosolCacheType;
      this._TangosolCacheType = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getTangosolCacheName() {
      return this._TangosolCacheName;
   }

   public boolean isTangosolCacheNameInherited() {
      return false;
   }

   public boolean isTangosolCacheNameSet() {
      return this._isSet(3);
   }

   public void setTangosolCacheName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TangosolCacheName;
      this._TangosolCacheName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getEvictionSchedule() {
      return this._EvictionSchedule;
   }

   public boolean isEvictionScheduleInherited() {
      return false;
   }

   public boolean isEvictionScheduleSet() {
      return this._isSet(4);
   }

   public void setEvictionSchedule(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EvictionSchedule;
      this._EvictionSchedule = param0;
      this._postSet(4, _oldVal, param0);
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
               this._ClearOnClose = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._EvictionSchedule = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._TangosolCacheName = "kodo";
               if (initOne) {
                  break;
               }
            case 2:
               this._TangosolCacheType = "named";
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
            case 14:
               if (s.equals("clear-on-close")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("eviction-schedule")) {
                  return 4;
               }
               break;
            case 19:
               if (s.equals("tangosol-cache-name")) {
                  return 3;
               }

               if (s.equals("tangosol-cache-type")) {
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
               return "clear-on-close";
            case 2:
               return "tangosol-cache-type";
            case 3:
               return "tangosol-cache-name";
            case 4:
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
            case 4:
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
      private TangosolDataCacheBeanImpl bean;

      protected Helper(TangosolDataCacheBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "ClearOnClose";
            case 2:
               return "TangosolCacheType";
            case 3:
               return "TangosolCacheName";
            case 4:
               return "EvictionSchedule";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClearOnClose")) {
            return 1;
         } else if (propName.equals("EvictionSchedule")) {
            return 4;
         } else if (propName.equals("TangosolCacheName")) {
            return 3;
         } else {
            return propName.equals("TangosolCacheType") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isClearOnCloseSet()) {
               buf.append("ClearOnClose");
               buf.append(String.valueOf(this.bean.getClearOnClose()));
            }

            if (this.bean.isEvictionScheduleSet()) {
               buf.append("EvictionSchedule");
               buf.append(String.valueOf(this.bean.getEvictionSchedule()));
            }

            if (this.bean.isTangosolCacheNameSet()) {
               buf.append("TangosolCacheName");
               buf.append(String.valueOf(this.bean.getTangosolCacheName()));
            }

            if (this.bean.isTangosolCacheTypeSet()) {
               buf.append("TangosolCacheType");
               buf.append(String.valueOf(this.bean.getTangosolCacheType()));
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
            TangosolDataCacheBeanImpl otherTyped = (TangosolDataCacheBeanImpl)other;
            this.computeDiff("ClearOnClose", this.bean.getClearOnClose(), otherTyped.getClearOnClose(), false);
            this.computeDiff("EvictionSchedule", this.bean.getEvictionSchedule(), otherTyped.getEvictionSchedule(), false);
            this.computeDiff("TangosolCacheName", this.bean.getTangosolCacheName(), otherTyped.getTangosolCacheName(), false);
            this.computeDiff("TangosolCacheType", this.bean.getTangosolCacheType(), otherTyped.getTangosolCacheType(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TangosolDataCacheBeanImpl original = (TangosolDataCacheBeanImpl)event.getSourceBean();
            TangosolDataCacheBeanImpl proposed = (TangosolDataCacheBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClearOnClose")) {
                  original.setClearOnClose(proposed.getClearOnClose());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EvictionSchedule")) {
                  original.setEvictionSchedule(proposed.getEvictionSchedule());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TangosolCacheName")) {
                  original.setTangosolCacheName(proposed.getTangosolCacheName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("TangosolCacheType")) {
                  original.setTangosolCacheType(proposed.getTangosolCacheType());
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
            TangosolDataCacheBeanImpl copy = (TangosolDataCacheBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClearOnClose")) && this.bean.isClearOnCloseSet()) {
               copy.setClearOnClose(this.bean.getClearOnClose());
            }

            if ((excludeProps == null || !excludeProps.contains("EvictionSchedule")) && this.bean.isEvictionScheduleSet()) {
               copy.setEvictionSchedule(this.bean.getEvictionSchedule());
            }

            if ((excludeProps == null || !excludeProps.contains("TangosolCacheName")) && this.bean.isTangosolCacheNameSet()) {
               copy.setTangosolCacheName(this.bean.getTangosolCacheName());
            }

            if ((excludeProps == null || !excludeProps.contains("TangosolCacheType")) && this.bean.isTangosolCacheTypeSet()) {
               copy.setTangosolCacheType(this.bean.getTangosolCacheType());
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
