package weblogic.j2ee.descriptor.wl60;

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

public class BaseWeblogicRdbmsBeanBeanImpl extends AbstractDescriptorBean implements BaseWeblogicRdbmsBeanBean, Serializable {
   private String _DataSourceJndiName;
   private String _EjbName;
   private static SchemaHelper2 _schemaHelper;

   public BaseWeblogicRdbmsBeanBeanImpl() {
      this._initializeProperty(-1);
   }

   public BaseWeblogicRdbmsBeanBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BaseWeblogicRdbmsBeanBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(0);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDataSourceJndiName() {
      return this._DataSourceJndiName;
   }

   public boolean isDataSourceJndiNameInherited() {
      return false;
   }

   public boolean isDataSourceJndiNameSet() {
      return this._isSet(1);
   }

   public void setDataSourceJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceJndiName;
      this._DataSourceJndiName = param0;
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
               this._DataSourceJndiName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._EjbName = null;
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
            case 8:
               if (s.equals("ejb-name")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("data-source-jndi-name")) {
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
               return "ejb-name";
            case 1:
               return "data-source-jndi-name";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private BaseWeblogicRdbmsBeanBeanImpl bean;

      protected Helper(BaseWeblogicRdbmsBeanBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EjbName";
            case 1:
               return "DataSourceJndiName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DataSourceJndiName")) {
            return 1;
         } else {
            return propName.equals("EjbName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isDataSourceJndiNameSet()) {
               buf.append("DataSourceJndiName");
               buf.append(String.valueOf(this.bean.getDataSourceJndiName()));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
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
            BaseWeblogicRdbmsBeanBeanImpl otherTyped = (BaseWeblogicRdbmsBeanBeanImpl)other;
            this.computeDiff("DataSourceJndiName", this.bean.getDataSourceJndiName(), otherTyped.getDataSourceJndiName(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BaseWeblogicRdbmsBeanBeanImpl original = (BaseWeblogicRdbmsBeanBeanImpl)event.getSourceBean();
            BaseWeblogicRdbmsBeanBeanImpl proposed = (BaseWeblogicRdbmsBeanBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DataSourceJndiName")) {
                  original.setDataSourceJndiName(proposed.getDataSourceJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
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
            BaseWeblogicRdbmsBeanBeanImpl copy = (BaseWeblogicRdbmsBeanBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DataSourceJndiName")) && this.bean.isDataSourceJndiNameSet()) {
               copy.setDataSourceJndiName(this.bean.getDataSourceJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
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
