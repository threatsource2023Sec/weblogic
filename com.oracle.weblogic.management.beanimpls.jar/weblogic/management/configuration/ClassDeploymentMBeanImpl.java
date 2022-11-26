package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ClassDeploymentMBeanImpl extends DeploymentMBeanImpl implements ClassDeploymentMBean, Serializable {
   private String _Arguments;
   private String _ClassName;
   private static SchemaHelper2 _schemaHelper;

   public ClassDeploymentMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ClassDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ClassDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClassName() {
      if (!this._isSet(12)) {
         try {
            return this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(12);
   }

   public void setClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("ClassName", param0);
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getArguments() {
      return this._Arguments;
   }

   public boolean isArgumentsInherited() {
      return false;
   }

   public boolean isArgumentsSet() {
      return this._isSet(13);
   }

   public void setArguments(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Arguments;
      this._Arguments = param0;
      this._postSet(13, _oldVal, param0);
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._Arguments = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ClassName = null;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "ClassDeployment";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Arguments")) {
         oldVal = this._Arguments;
         this._Arguments = (String)v;
         this._postSet(13, oldVal, this._Arguments);
      } else if (name.equals("ClassName")) {
         oldVal = this._ClassName;
         this._ClassName = (String)v;
         this._postSet(12, oldVal, this._ClassName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Arguments")) {
         return this._Arguments;
      } else {
         return name.equals("ClassName") ? this._ClassName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("arguments")) {
                  return 13;
               }
               break;
            case 10:
               if (s.equals("class-name")) {
                  return 12;
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
            case 12:
               return "class-name";
            case 13:
               return "arguments";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private ClassDeploymentMBeanImpl bean;

      protected Helper(ClassDeploymentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "ClassName";
            case 13:
               return "Arguments";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Arguments")) {
            return 13;
         } else {
            return propName.equals("ClassName") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isArgumentsSet()) {
               buf.append("Arguments");
               buf.append(String.valueOf(this.bean.getArguments()));
            }

            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
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
            ClassDeploymentMBeanImpl otherTyped = (ClassDeploymentMBeanImpl)other;
            this.computeDiff("Arguments", this.bean.getArguments(), otherTyped.getArguments(), false);
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClassDeploymentMBeanImpl original = (ClassDeploymentMBeanImpl)event.getSourceBean();
            ClassDeploymentMBeanImpl proposed = (ClassDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Arguments")) {
                  original.setArguments(proposed.getArguments());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            ClassDeploymentMBeanImpl copy = (ClassDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Arguments")) && this.bean.isArgumentsSet()) {
               copy.setArguments(this.bean.getArguments());
            }

            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
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
