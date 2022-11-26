package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class EnumRefBeanImpl extends AbstractDescriptorBean implements EnumRefBean, Serializable {
   private String[] _DefaultValues;
   private String _EnumClassName;
   private static SchemaHelper2 _schemaHelper;

   public EnumRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public EnumRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EnumRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEnumClassName() {
      return this._EnumClassName;
   }

   public boolean isEnumClassNameInherited() {
      return false;
   }

   public boolean isEnumClassNameSet() {
      return this._isSet(0);
   }

   public void setEnumClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EnumClassName;
      this._EnumClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getDefaultValues() {
      return this._DefaultValues;
   }

   public boolean isDefaultValuesInherited() {
      return false;
   }

   public boolean isDefaultValuesSet() {
      return this._isSet(1);
   }

   public void setDefaultValues(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DefaultValues;
      this._DefaultValues = param0;
      this._postSet(1, _oldVal, param0);
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
               this._DefaultValues = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._EnumClassName = null;
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
            case 13:
               if (s.equals("default-value")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("enum-class-name")) {
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
               return "enum-class-name";
            case 1:
               return "default-value";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EnumRefBeanImpl bean;

      protected Helper(EnumRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EnumClassName";
            case 1:
               return "DefaultValues";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultValues")) {
            return 1;
         } else {
            return propName.equals("EnumClassName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isDefaultValuesSet()) {
               buf.append("DefaultValues");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultValues())));
            }

            if (this.bean.isEnumClassNameSet()) {
               buf.append("EnumClassName");
               buf.append(String.valueOf(this.bean.getEnumClassName()));
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
            EnumRefBeanImpl otherTyped = (EnumRefBeanImpl)other;
            this.computeDiff("DefaultValues", this.bean.getDefaultValues(), otherTyped.getDefaultValues(), false);
            this.computeDiff("EnumClassName", this.bean.getEnumClassName(), otherTyped.getEnumClassName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EnumRefBeanImpl original = (EnumRefBeanImpl)event.getSourceBean();
            EnumRefBeanImpl proposed = (EnumRefBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultValues")) {
                  original.setDefaultValues(proposed.getDefaultValues());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EnumClassName")) {
                  original.setEnumClassName(proposed.getEnumClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            EnumRefBeanImpl copy = (EnumRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultValues")) && this.bean.isDefaultValuesSet()) {
               Object o = this.bean.getDefaultValues();
               copy.setDefaultValues(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EnumClassName")) && this.bean.isEnumClassNameSet()) {
               copy.setEnumClassName(this.bean.getEnumClassName());
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
