package weblogic.coherence.app.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class InitParamBeanImpl extends SettableBeanImpl implements InitParamBean, Serializable {
   private String _ParamType;
   private String _ParamValue;
   private static SchemaHelper2 _schemaHelper;

   public InitParamBeanImpl() {
      this._initializeProperty(-1);
   }

   public InitParamBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InitParamBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getParamType() {
      return this._ParamType;
   }

   public boolean isParamTypeInherited() {
      return false;
   }

   public boolean isParamTypeSet() {
      return this._isSet(0);
   }

   public void setParamType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParamType;
      this._ParamType = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getParamValue() {
      return this._ParamValue;
   }

   public boolean isParamValueInherited() {
      return false;
   }

   public boolean isParamValueSet() {
      return this._isSet(1);
   }

   public void setParamValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParamValue;
      this._ParamValue = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ParamType = null;
               if (initOne) {
                  break;
               }
            case 1:
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("param-type")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("param-value")) {
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
               return "param-type";
            case 1:
               return "param-value";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private InitParamBeanImpl bean;

      protected Helper(InitParamBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ParamType";
            case 1:
               return "ParamValue";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ParamType")) {
            return 0;
         } else {
            return propName.equals("ParamValue") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isParamTypeSet()) {
               buf.append("ParamType");
               buf.append(String.valueOf(this.bean.getParamType()));
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
            InitParamBeanImpl otherTyped = (InitParamBeanImpl)other;
            this.computeDiff("ParamType", this.bean.getParamType(), otherTyped.getParamType(), false);
            this.computeDiff("ParamValue", this.bean.getParamValue(), otherTyped.getParamValue(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InitParamBeanImpl original = (InitParamBeanImpl)event.getSourceBean();
            InitParamBeanImpl proposed = (InitParamBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ParamType")) {
                  original.setParamType(proposed.getParamType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ParamValue")) {
                  original.setParamValue(proposed.getParamValue());
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
            InitParamBeanImpl copy = (InitParamBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ParamType")) && this.bean.isParamTypeSet()) {
               copy.setParamType(this.bean.getParamType());
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
