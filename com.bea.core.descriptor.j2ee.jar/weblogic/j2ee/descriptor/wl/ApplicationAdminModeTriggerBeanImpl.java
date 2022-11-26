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

public class ApplicationAdminModeTriggerBeanImpl extends AbstractDescriptorBean implements ApplicationAdminModeTriggerBean, Serializable {
   private String _Id;
   private int _MaxStuckThreadTime;
   private int _StuckThreadCount;
   private static SchemaHelper2 _schemaHelper;

   public ApplicationAdminModeTriggerBeanImpl() {
      this._initializeProperty(-1);
   }

   public ApplicationAdminModeTriggerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ApplicationAdminModeTriggerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxStuckThreadTime() {
      return this._MaxStuckThreadTime;
   }

   public boolean isMaxStuckThreadTimeInherited() {
      return false;
   }

   public boolean isMaxStuckThreadTimeSet() {
      return this._isSet(0);
   }

   public void setMaxStuckThreadTime(int param0) {
      int _oldVal = this._MaxStuckThreadTime;
      this._MaxStuckThreadTime = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getStuckThreadCount() {
      return this._StuckThreadCount;
   }

   public boolean isStuckThreadCountInherited() {
      return false;
   }

   public boolean isStuckThreadCountSet() {
      return this._isSet(1);
   }

   public void setStuckThreadCount(int param0) {
      int _oldVal = this._StuckThreadCount;
      this._StuckThreadCount = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getId();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 2:
            if (s.equals("id")) {
               return info.compareXpaths(this._getPropertyXpath("id"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._MaxStuckThreadTime = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._StuckThreadCount = 0;
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
            case 2:
               if (s.equals("id")) {
                  return 2;
               }
               break;
            case 18:
               if (s.equals("stuck-thread-count")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("max-stuck-thread-time")) {
                  return 0;
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
               return "max-stuck-thread-time";
            case 1:
               return "stuck-thread-count";
            case 2:
               return "id";
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
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("id");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ApplicationAdminModeTriggerBeanImpl bean;

      protected Helper(ApplicationAdminModeTriggerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MaxStuckThreadTime";
            case 1:
               return "StuckThreadCount";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("MaxStuckThreadTime")) {
            return 0;
         } else {
            return propName.equals("StuckThreadCount") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMaxStuckThreadTimeSet()) {
               buf.append("MaxStuckThreadTime");
               buf.append(String.valueOf(this.bean.getMaxStuckThreadTime()));
            }

            if (this.bean.isStuckThreadCountSet()) {
               buf.append("StuckThreadCount");
               buf.append(String.valueOf(this.bean.getStuckThreadCount()));
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
            ApplicationAdminModeTriggerBeanImpl otherTyped = (ApplicationAdminModeTriggerBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MaxStuckThreadTime", this.bean.getMaxStuckThreadTime(), otherTyped.getMaxStuckThreadTime(), true);
            this.computeDiff("StuckThreadCount", this.bean.getStuckThreadCount(), otherTyped.getStuckThreadCount(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ApplicationAdminModeTriggerBeanImpl original = (ApplicationAdminModeTriggerBeanImpl)event.getSourceBean();
            ApplicationAdminModeTriggerBeanImpl proposed = (ApplicationAdminModeTriggerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MaxStuckThreadTime")) {
                  original.setMaxStuckThreadTime(proposed.getMaxStuckThreadTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StuckThreadCount")) {
                  original.setStuckThreadCount(proposed.getStuckThreadCount());
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
            ApplicationAdminModeTriggerBeanImpl copy = (ApplicationAdminModeTriggerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxStuckThreadTime")) && this.bean.isMaxStuckThreadTimeSet()) {
               copy.setMaxStuckThreadTime(this.bean.getMaxStuckThreadTime());
            }

            if ((excludeProps == null || !excludeProps.contains("StuckThreadCount")) && this.bean.isStuckThreadCountSet()) {
               copy.setStuckThreadCount(this.bean.getStuckThreadCount());
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
