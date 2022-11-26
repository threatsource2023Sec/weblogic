package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class MigratableRMIServiceMBeanImpl extends DeploymentMBeanImpl implements MigratableRMIServiceMBean, Serializable {
   private String _Argument;
   private String _Classname;
   private static SchemaHelper2 _schemaHelper;

   public MigratableRMIServiceMBeanImpl() {
      this._initializeProperty(-1);
   }

   public MigratableRMIServiceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MigratableRMIServiceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClassname() {
      return this._Classname;
   }

   public boolean isClassnameInherited() {
      return false;
   }

   public boolean isClassnameSet() {
      return this._isSet(12);
   }

   public void setClassname(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Classname;
      this._Classname = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getArgument() {
      return this._Argument;
   }

   public boolean isArgumentInherited() {
      return false;
   }

   public boolean isArgumentSet() {
      return this._isSet(13);
   }

   public void setArgument(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Argument;
      this._Argument = param0;
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
               this._Argument = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Classname = null;
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
      return "MigratableRMIService";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Argument")) {
         oldVal = this._Argument;
         this._Argument = (String)v;
         this._postSet(13, oldVal, this._Argument);
      } else if (name.equals("Classname")) {
         oldVal = this._Classname;
         this._Classname = (String)v;
         this._postSet(12, oldVal, this._Classname);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Argument")) {
         return this._Argument;
      } else {
         return name.equals("Classname") ? this._Classname : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("argument")) {
                  return 13;
               }
               break;
            case 9:
               if (s.equals("classname")) {
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
               return "classname";
            case 13:
               return "argument";
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
      private MigratableRMIServiceMBeanImpl bean;

      protected Helper(MigratableRMIServiceMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "Classname";
            case 13:
               return "Argument";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Argument")) {
            return 13;
         } else {
            return propName.equals("Classname") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isArgumentSet()) {
               buf.append("Argument");
               buf.append(String.valueOf(this.bean.getArgument()));
            }

            if (this.bean.isClassnameSet()) {
               buf.append("Classname");
               buf.append(String.valueOf(this.bean.getClassname()));
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
            MigratableRMIServiceMBeanImpl otherTyped = (MigratableRMIServiceMBeanImpl)other;
            this.computeDiff("Argument", this.bean.getArgument(), otherTyped.getArgument(), false);
            this.computeDiff("Classname", this.bean.getClassname(), otherTyped.getClassname(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MigratableRMIServiceMBeanImpl original = (MigratableRMIServiceMBeanImpl)event.getSourceBean();
            MigratableRMIServiceMBeanImpl proposed = (MigratableRMIServiceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Argument")) {
                  original.setArgument(proposed.getArgument());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Classname")) {
                  original.setClassname(proposed.getClassname());
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
            MigratableRMIServiceMBeanImpl copy = (MigratableRMIServiceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Argument")) && this.bean.isArgumentSet()) {
               copy.setArgument(this.bean.getArgument());
            }

            if ((excludeProps == null || !excludeProps.contains("Classname")) && this.bean.isClassnameSet()) {
               copy.setClassname(this.bean.getClassname());
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
