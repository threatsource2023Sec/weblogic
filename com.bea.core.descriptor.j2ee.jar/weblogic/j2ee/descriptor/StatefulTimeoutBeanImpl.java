package weblogic.j2ee.descriptor;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class StatefulTimeoutBeanImpl extends AbstractDescriptorBean implements StatefulTimeoutBean, Serializable {
   private String _Id;
   private long _Timeout;
   private String _Unit;
   private static SchemaHelper2 _schemaHelper;

   public StatefulTimeoutBeanImpl() {
      this._initializeProperty(-1);
   }

   public StatefulTimeoutBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StatefulTimeoutBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(0);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setTimeout(long param0) {
      long _oldVal = this._Timeout;
      this._Timeout = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getTimeout() {
      return this._Timeout;
   }

   public boolean isTimeoutInherited() {
      return false;
   }

   public boolean isTimeoutSet() {
      return this._isSet(1);
   }

   public String getUnit() {
      return !this._isSet(2) ? null : this._Unit;
   }

   public boolean isUnitInherited() {
      return false;
   }

   public boolean isUnitSet() {
      return this._isSet(2);
   }

   public void setUnit(String param0) {
      if (param0 == null) {
         this._unSet(2);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Days", "Hours", "Minutes", "Seconds", "Milliseconds", "Microseconds", "Nanoseconds"};
         param0 = LegalChecks.checkInEnum("Unit", param0, _set);
         String _oldVal = this._Unit;
         this._Unit = param0;
         this._postSet(2, _oldVal, param0);
      }
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Timeout = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._Unit = null;
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
                  return 0;
               }
               break;
            case 4:
               if (s.equals("unit")) {
                  return 2;
               }
               break;
            case 7:
               if (s.equals("timeout")) {
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
               return "id";
            case 1:
               return "timeout";
            case 2:
               return "unit";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private StatefulTimeoutBeanImpl bean;

      protected Helper(StatefulTimeoutBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Id";
            case 1:
               return "Timeout";
            case 2:
               return "Unit";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 0;
         } else if (propName.equals("Timeout")) {
            return 1;
         } else {
            return propName.equals("Unit") ? 2 : super.getPropertyIndex(propName);
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

            if (this.bean.isTimeoutSet()) {
               buf.append("Timeout");
               buf.append(String.valueOf(this.bean.getTimeout()));
            }

            if (this.bean.isUnitSet()) {
               buf.append("Unit");
               buf.append(String.valueOf(this.bean.getUnit()));
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
            StatefulTimeoutBeanImpl otherTyped = (StatefulTimeoutBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Timeout", this.bean.getTimeout(), otherTyped.getTimeout(), false);
            this.computeDiff("Unit", this.bean.getUnit(), otherTyped.getUnit(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StatefulTimeoutBeanImpl original = (StatefulTimeoutBeanImpl)event.getSourceBean();
            StatefulTimeoutBeanImpl proposed = (StatefulTimeoutBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Timeout")) {
                  original.setTimeout(proposed.getTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Unit")) {
                  original.setUnit(proposed.getUnit());
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
            StatefulTimeoutBeanImpl copy = (StatefulTimeoutBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Timeout")) && this.bean.isTimeoutSet()) {
               copy.setTimeout(this.bean.getTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("Unit")) && this.bean.isUnitSet()) {
               copy.setUnit(this.bean.getUnit());
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
