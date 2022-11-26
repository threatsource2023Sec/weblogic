package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SimpleTypeDefinitionBeanImpl extends AbstractDescriptorBean implements SimpleTypeDefinitionBean, Serializable {
   private String _BaseType;
   private MemberConstraintBean _Constraint;
   private String[] _DefaultValue;
   private boolean _RequiresEncryption;
   private static SchemaHelper2 _schemaHelper;

   public SimpleTypeDefinitionBeanImpl() {
      this._initializeProperty(-1);
   }

   public SimpleTypeDefinitionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SimpleTypeDefinitionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getBaseType() {
      return this._BaseType;
   }

   public boolean isBaseTypeInherited() {
      return false;
   }

   public boolean isBaseTypeSet() {
      return this._isSet(0);
   }

   public void setBaseType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BaseType;
      this._BaseType = param0;
      this._postSet(0, _oldVal, param0);
   }

   public MemberConstraintBean getConstraint() {
      return this._Constraint;
   }

   public boolean isConstraintInherited() {
      return false;
   }

   public boolean isConstraintSet() {
      return this._isSet(1);
   }

   public void setConstraint(MemberConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConstraint() != null && param0 != this.getConstraint()) {
         throw new BeanAlreadyExistsException(this.getConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MemberConstraintBean _oldVal = this._Constraint;
         this._Constraint = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public MemberConstraintBean createConstraint() {
      MemberConstraintBeanImpl _val = new MemberConstraintBeanImpl(this, -1);

      try {
         this.setConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean getRequiresEncryption() {
      return this._RequiresEncryption;
   }

   public boolean isRequiresEncryptionInherited() {
      return false;
   }

   public boolean isRequiresEncryptionSet() {
      return this._isSet(2);
   }

   public void setRequiresEncryption(boolean param0) {
      boolean _oldVal = this._RequiresEncryption;
      this._RequiresEncryption = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getDefaultValue() {
      return this._DefaultValue;
   }

   public boolean isDefaultValueInherited() {
      return false;
   }

   public boolean isDefaultValueSet() {
      return this._isSet(3);
   }

   public void setDefaultValue(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DefaultValue;
      this._DefaultValue = param0;
      this._postSet(3, _oldVal, param0);
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
               this._BaseType = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Constraint = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._DefaultValue = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._RequiresEncryption = false;
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
            case 9:
               if (s.equals("base-type")) {
                  return 0;
               }
               break;
            case 10:
               if (s.equals("constraint")) {
                  return 1;
               }
               break;
            case 13:
               if (s.equals("default-value")) {
                  return 3;
               }
               break;
            case 19:
               if (s.equals("requires-encryption")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new MemberConstraintBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "base-type";
            case 1:
               return "constraint";
            case 2:
               return "requires-encryption";
            case 3:
               return "default-value";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SimpleTypeDefinitionBeanImpl bean;

      protected Helper(SimpleTypeDefinitionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "BaseType";
            case 1:
               return "Constraint";
            case 2:
               return "RequiresEncryption";
            case 3:
               return "DefaultValue";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BaseType")) {
            return 0;
         } else if (propName.equals("Constraint")) {
            return 1;
         } else if (propName.equals("DefaultValue")) {
            return 3;
         } else {
            return propName.equals("RequiresEncryption") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConstraint() != null) {
            iterators.add(new ArrayIterator(new MemberConstraintBean[]{this.bean.getConstraint()}));
         }

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
            if (this.bean.isBaseTypeSet()) {
               buf.append("BaseType");
               buf.append(String.valueOf(this.bean.getBaseType()));
            }

            childValue = this.computeChildHashValue(this.bean.getConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDefaultValueSet()) {
               buf.append("DefaultValue");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultValue())));
            }

            if (this.bean.isRequiresEncryptionSet()) {
               buf.append("RequiresEncryption");
               buf.append(String.valueOf(this.bean.getRequiresEncryption()));
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
            SimpleTypeDefinitionBeanImpl otherTyped = (SimpleTypeDefinitionBeanImpl)other;
            this.computeDiff("BaseType", this.bean.getBaseType(), otherTyped.getBaseType(), false);
            this.computeChildDiff("Constraint", this.bean.getConstraint(), otherTyped.getConstraint(), false);
            this.computeDiff("DefaultValue", this.bean.getDefaultValue(), otherTyped.getDefaultValue(), false);
            this.computeDiff("RequiresEncryption", this.bean.getRequiresEncryption(), otherTyped.getRequiresEncryption(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SimpleTypeDefinitionBeanImpl original = (SimpleTypeDefinitionBeanImpl)event.getSourceBean();
            SimpleTypeDefinitionBeanImpl proposed = (SimpleTypeDefinitionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BaseType")) {
                  original.setBaseType(proposed.getBaseType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Constraint")) {
                  if (type == 2) {
                     original.setConstraint((MemberConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Constraint", (DescriptorBean)original.getConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DefaultValue")) {
                  original.setDefaultValue(proposed.getDefaultValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RequiresEncryption")) {
                  original.setRequiresEncryption(proposed.getRequiresEncryption());
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
            SimpleTypeDefinitionBeanImpl copy = (SimpleTypeDefinitionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BaseType")) && this.bean.isBaseTypeSet()) {
               copy.setBaseType(this.bean.getBaseType());
            }

            if ((excludeProps == null || !excludeProps.contains("Constraint")) && this.bean.isConstraintSet() && !copy._isSet(1)) {
               Object o = this.bean.getConstraint();
               copy.setConstraint((MemberConstraintBean)null);
               copy.setConstraint(o == null ? null : (MemberConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultValue")) && this.bean.isDefaultValueSet()) {
               Object o = this.bean.getDefaultValue();
               copy.setDefaultValue(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresEncryption")) && this.bean.isRequiresEncryptionSet()) {
               copy.setRequiresEncryption(this.bean.getRequiresEncryption());
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
         this.inferSubTree(this.bean.getConstraint(), clazz, annotation);
      }
   }
}
