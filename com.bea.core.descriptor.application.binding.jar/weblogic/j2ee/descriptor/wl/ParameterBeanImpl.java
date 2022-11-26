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

public class ParameterBeanImpl extends AbstractDescriptorBean implements ParameterBean, Serializable {
   private String _Description;
   private String _ParamName;
   private String _ParamValue;
   private static SchemaHelper2 _schemaHelper;

   public ParameterBeanImpl() {
      this._initializeProperty(-1);
   }

   public ParameterBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ParameterBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getParamName() {
      return this._ParamName;
   }

   public boolean isParamNameInherited() {
      return false;
   }

   public boolean isParamNameSet() {
      return this._isSet(1);
   }

   public void setParamName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParamName;
      this._ParamName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getParamValue() {
      return this._ParamValue;
   }

   public boolean isParamValueInherited() {
      return false;
   }

   public boolean isParamValueSet() {
      return this._isSet(2);
   }

   public void setParamValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParamValue;
      this._ParamValue = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ParamName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ParamValue = null;
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
               if (s.equals("param-name")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("param-value")) {
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
            case 0:
               return "description";
            case 1:
               return "param-name";
            case 2:
               return "param-value";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ParameterBeanImpl bean;

      protected Helper(ParameterBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "ParamName";
            case 2:
               return "ParamValue";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("ParamName")) {
            return 1;
         } else {
            return propName.equals("ParamValue") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isParamNameSet()) {
               buf.append("ParamName");
               buf.append(String.valueOf(this.bean.getParamName()));
            }

            if (this.bean.isParamValueSet()) {
               buf.append("ParamValue");
               buf.append(String.valueOf(this.bean.getParamValue()));
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
            ParameterBeanImpl otherTyped = (ParameterBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("ParamName", this.bean.getParamName(), otherTyped.getParamName(), false);
            this.computeDiff("ParamValue", this.bean.getParamValue(), otherTyped.getParamValue(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ParameterBeanImpl original = (ParameterBeanImpl)event.getSourceBean();
            ParameterBeanImpl proposed = (ParameterBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ParamName")) {
                  original.setParamName(proposed.getParamName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ParamValue")) {
                  original.setParamValue(proposed.getParamValue());
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
            ParameterBeanImpl copy = (ParameterBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("ParamName")) && this.bean.isParamNameSet()) {
               copy.setParamName(this.bean.getParamName());
            }

            if ((excludeProps == null || !excludeProps.contains("ParamValue")) && this.bean.isParamValueSet()) {
               copy.setParamValue(this.bean.getParamValue());
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
