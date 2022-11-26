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

public class CalloutMBeanImpl extends ConfigurationMBeanImpl implements CalloutMBean, Serializable {
   private String _Argument;
   private String _ClassName;
   private String _HookPoint;
   private static SchemaHelper2 _schemaHelper;

   public CalloutMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CalloutMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CalloutMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getHookPoint() {
      return this._HookPoint;
   }

   public boolean isHookPointInherited() {
      return false;
   }

   public boolean isHookPointSet() {
      return this._isSet(10);
   }

   public void setHookPoint(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._HookPoint;
      this._HookPoint = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getClassName() {
      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(11);
   }

   public void setClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getArgument() {
      return this._Argument;
   }

   public boolean isArgumentInherited() {
      return false;
   }

   public boolean isArgumentSet() {
      return this._isSet(12);
   }

   public void setArgument(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Argument;
      this._Argument = param0;
      this._postSet(12, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Argument", this.isArgumentSet());
      LegalChecks.checkIsSet("ClassName", this.isClassNameSet());
      LegalChecks.checkIsSet("HookPoint", this.isHookPointSet());
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._Argument = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ClassName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._HookPoint = null;
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
      return "Callout";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Argument")) {
         oldVal = this._Argument;
         this._Argument = (String)v;
         this._postSet(12, oldVal, this._Argument);
      } else if (name.equals("ClassName")) {
         oldVal = this._ClassName;
         this._ClassName = (String)v;
         this._postSet(11, oldVal, this._ClassName);
      } else if (name.equals("HookPoint")) {
         oldVal = this._HookPoint;
         this._HookPoint = (String)v;
         this._postSet(10, oldVal, this._HookPoint);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Argument")) {
         return this._Argument;
      } else if (name.equals("ClassName")) {
         return this._ClassName;
      } else {
         return name.equals("HookPoint") ? this._HookPoint : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("argument")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("class-name")) {
                  return 11;
               }

               if (s.equals("hook-point")) {
                  return 10;
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
            case 10:
               return "hook-point";
            case 11:
               return "class-name";
            case 12:
               return "argument";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private CalloutMBeanImpl bean;

      protected Helper(CalloutMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "HookPoint";
            case 11:
               return "ClassName";
            case 12:
               return "Argument";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Argument")) {
            return 12;
         } else if (propName.equals("ClassName")) {
            return 11;
         } else {
            return propName.equals("HookPoint") ? 10 : super.getPropertyIndex(propName);
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

            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            if (this.bean.isHookPointSet()) {
               buf.append("HookPoint");
               buf.append(String.valueOf(this.bean.getHookPoint()));
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
            CalloutMBeanImpl otherTyped = (CalloutMBeanImpl)other;
            this.computeDiff("Argument", this.bean.getArgument(), otherTyped.getArgument(), true);
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), true);
            this.computeDiff("HookPoint", this.bean.getHookPoint(), otherTyped.getHookPoint(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CalloutMBeanImpl original = (CalloutMBeanImpl)event.getSourceBean();
            CalloutMBeanImpl proposed = (CalloutMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Argument")) {
                  original.setArgument(proposed.getArgument());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("HookPoint")) {
                  original.setHookPoint(proposed.getHookPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            CalloutMBeanImpl copy = (CalloutMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Argument")) && this.bean.isArgumentSet()) {
               copy.setArgument(this.bean.getArgument());
            }

            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("HookPoint")) && this.bean.isHookPointSet()) {
               copy.setHookPoint(this.bean.getHookPoint());
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
