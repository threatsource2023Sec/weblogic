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

public class EnumDefinitionBeanImpl extends AbstractDescriptorBean implements EnumDefinitionBean, Serializable {
   private String _EnumClassName;
   private String[] _EnumValues;
   private static SchemaHelper2 _schemaHelper;

   public EnumDefinitionBeanImpl() {
      this._initializeProperty(-1);
   }

   public EnumDefinitionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EnumDefinitionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String[] getEnumValues() {
      return this._EnumValues;
   }

   public boolean isEnumValuesInherited() {
      return false;
   }

   public boolean isEnumValuesSet() {
      return this._isSet(1);
   }

   public void setEnumValues(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._EnumValues;
      this._EnumValues = param0;
      this._postSet(1, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEnumClassName();
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
         case 15:
            if (s.equals("enum-class-name")) {
               return info.compareXpaths(this._getPropertyXpath("enum-class-name"));
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._EnumClassName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._EnumValues = new String[0];
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
            case 10:
               if (s.equals("enum-value")) {
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
               return "enum-value";
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

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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
         indices.add("enum-class-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EnumDefinitionBeanImpl bean;

      protected Helper(EnumDefinitionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EnumClassName";
            case 1:
               return "EnumValues";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EnumClassName")) {
            return 0;
         } else {
            return propName.equals("EnumValues") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isEnumClassNameSet()) {
               buf.append("EnumClassName");
               buf.append(String.valueOf(this.bean.getEnumClassName()));
            }

            if (this.bean.isEnumValuesSet()) {
               buf.append("EnumValues");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEnumValues())));
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
            EnumDefinitionBeanImpl otherTyped = (EnumDefinitionBeanImpl)other;
            this.computeDiff("EnumClassName", this.bean.getEnumClassName(), otherTyped.getEnumClassName(), false);
            this.computeDiff("EnumValues", this.bean.getEnumValues(), otherTyped.getEnumValues(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EnumDefinitionBeanImpl original = (EnumDefinitionBeanImpl)event.getSourceBean();
            EnumDefinitionBeanImpl proposed = (EnumDefinitionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EnumClassName")) {
                  original.setEnumClassName(proposed.getEnumClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EnumValues")) {
                  original.setEnumValues(proposed.getEnumValues());
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
            EnumDefinitionBeanImpl copy = (EnumDefinitionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EnumClassName")) && this.bean.isEnumClassNameSet()) {
               copy.setEnumClassName(this.bean.getEnumClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("EnumValues")) && this.bean.isEnumValuesSet()) {
               Object o = this.bean.getEnumValues();
               copy.setEnumValues(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
