package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class MemberDefinitionBeanImpl extends AbstractDescriptorBean implements MemberDefinitionBean, Serializable {
   private String _AnnotationRef;
   private EnumRefBean _EnumRef;
   private boolean _IsArray;
   private boolean _IsRequired;
   private String _MemberName;
   private SimpleTypeDefinitionBean _SimpleTypeDefinition;
   private static SchemaHelper2 _schemaHelper;

   public MemberDefinitionBeanImpl() {
      this._initializeProperty(-1);
   }

   public MemberDefinitionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MemberDefinitionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMemberName() {
      return this._MemberName;
   }

   public boolean isMemberNameInherited() {
      return false;
   }

   public boolean isMemberNameSet() {
      return this._isSet(0);
   }

   public void setMemberName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MemberName;
      this._MemberName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean getIsArray() {
      return this._IsArray;
   }

   public boolean isIsArrayInherited() {
      return false;
   }

   public boolean isIsArraySet() {
      return this._isSet(1);
   }

   public void setIsArray(boolean param0) {
      boolean _oldVal = this._IsArray;
      this._IsArray = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean getIsRequired() {
      return this._IsRequired;
   }

   public boolean isIsRequiredInherited() {
      return false;
   }

   public boolean isIsRequiredSet() {
      return this._isSet(2);
   }

   public void setIsRequired(boolean param0) {
      boolean _oldVal = this._IsRequired;
      this._IsRequired = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getAnnotationRef() {
      return this._AnnotationRef;
   }

   public boolean isAnnotationRefInherited() {
      return false;
   }

   public boolean isAnnotationRefSet() {
      return this._isSet(3);
   }

   public void setAnnotationRef(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AnnotationRef;
      this._AnnotationRef = param0;
      this._postSet(3, _oldVal, param0);
   }

   public EnumRefBean getEnumRef() {
      return this._EnumRef;
   }

   public boolean isEnumRefInherited() {
      return false;
   }

   public boolean isEnumRefSet() {
      return this._isSet(4);
   }

   public void setEnumRef(EnumRefBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getEnumRef() != null && param0 != this.getEnumRef()) {
         throw new BeanAlreadyExistsException(this.getEnumRef() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EnumRefBean _oldVal = this._EnumRef;
         this._EnumRef = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public EnumRefBean createEnumRef() {
      EnumRefBeanImpl _val = new EnumRefBeanImpl(this, -1);

      try {
         this.setEnumRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SimpleTypeDefinitionBean getSimpleTypeDefinition() {
      return this._SimpleTypeDefinition;
   }

   public boolean isSimpleTypeDefinitionInherited() {
      return false;
   }

   public boolean isSimpleTypeDefinitionSet() {
      return this._isSet(5);
   }

   public void setSimpleTypeDefinition(SimpleTypeDefinitionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSimpleTypeDefinition() != null && param0 != this.getSimpleTypeDefinition()) {
         throw new BeanAlreadyExistsException(this.getSimpleTypeDefinition() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SimpleTypeDefinitionBean _oldVal = this._SimpleTypeDefinition;
         this._SimpleTypeDefinition = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public SimpleTypeDefinitionBean createSimpleTypeDefinition() {
      SimpleTypeDefinitionBeanImpl _val = new SimpleTypeDefinitionBeanImpl(this, -1);

      try {
         this.setSimpleTypeDefinition(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getMemberName();
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
         case 11:
            if (s.equals("member-name")) {
               return info.compareXpaths(this._getPropertyXpath("member-name"));
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._AnnotationRef = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._EnumRef = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._IsArray = false;
               if (initOne) {
                  break;
               }
            case 2:
               this._IsRequired = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._MemberName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._SimpleTypeDefinition = null;
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
               if (s.equals("enum-ref")) {
                  return 4;
               }

               if (s.equals("is-array")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("is-required")) {
                  return 2;
               }

               if (s.equals("member-name")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("annotation-ref")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("simple-type-definition")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new EnumRefBeanImpl.SchemaHelper2();
            case 5:
               return new SimpleTypeDefinitionBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "member-name";
            case 1:
               return "is-array";
            case 2:
               return "is-required";
            case 3:
               return "annotation-ref";
            case 4:
               return "enum-ref";
            case 5:
               return "simple-type-definition";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
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
         indices.add("member-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MemberDefinitionBeanImpl bean;

      protected Helper(MemberDefinitionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MemberName";
            case 1:
               return "IsArray";
            case 2:
               return "IsRequired";
            case 3:
               return "AnnotationRef";
            case 4:
               return "EnumRef";
            case 5:
               return "SimpleTypeDefinition";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AnnotationRef")) {
            return 3;
         } else if (propName.equals("EnumRef")) {
            return 4;
         } else if (propName.equals("IsArray")) {
            return 1;
         } else if (propName.equals("IsRequired")) {
            return 2;
         } else if (propName.equals("MemberName")) {
            return 0;
         } else {
            return propName.equals("SimpleTypeDefinition") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getEnumRef() != null) {
            iterators.add(new ArrayIterator(new EnumRefBean[]{this.bean.getEnumRef()}));
         }

         if (this.bean.getSimpleTypeDefinition() != null) {
            iterators.add(new ArrayIterator(new SimpleTypeDefinitionBean[]{this.bean.getSimpleTypeDefinition()}));
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
            if (this.bean.isAnnotationRefSet()) {
               buf.append("AnnotationRef");
               buf.append(String.valueOf(this.bean.getAnnotationRef()));
            }

            childValue = this.computeChildHashValue(this.bean.getEnumRef());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIsArraySet()) {
               buf.append("IsArray");
               buf.append(String.valueOf(this.bean.getIsArray()));
            }

            if (this.bean.isIsRequiredSet()) {
               buf.append("IsRequired");
               buf.append(String.valueOf(this.bean.getIsRequired()));
            }

            if (this.bean.isMemberNameSet()) {
               buf.append("MemberName");
               buf.append(String.valueOf(this.bean.getMemberName()));
            }

            childValue = this.computeChildHashValue(this.bean.getSimpleTypeDefinition());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            MemberDefinitionBeanImpl otherTyped = (MemberDefinitionBeanImpl)other;
            this.computeDiff("AnnotationRef", this.bean.getAnnotationRef(), otherTyped.getAnnotationRef(), false);
            this.computeChildDiff("EnumRef", this.bean.getEnumRef(), otherTyped.getEnumRef(), false);
            this.computeDiff("IsArray", this.bean.getIsArray(), otherTyped.getIsArray(), false);
            this.computeDiff("IsRequired", this.bean.getIsRequired(), otherTyped.getIsRequired(), false);
            this.computeDiff("MemberName", this.bean.getMemberName(), otherTyped.getMemberName(), false);
            this.computeChildDiff("SimpleTypeDefinition", this.bean.getSimpleTypeDefinition(), otherTyped.getSimpleTypeDefinition(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MemberDefinitionBeanImpl original = (MemberDefinitionBeanImpl)event.getSourceBean();
            MemberDefinitionBeanImpl proposed = (MemberDefinitionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AnnotationRef")) {
                  original.setAnnotationRef(proposed.getAnnotationRef());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("EnumRef")) {
                  if (type == 2) {
                     original.setEnumRef((EnumRefBean)this.createCopy((AbstractDescriptorBean)proposed.getEnumRef()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EnumRef", (DescriptorBean)original.getEnumRef());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("IsArray")) {
                  original.setIsArray(proposed.getIsArray());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("IsRequired")) {
                  original.setIsRequired(proposed.getIsRequired());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MemberName")) {
                  original.setMemberName(proposed.getMemberName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SimpleTypeDefinition")) {
                  if (type == 2) {
                     original.setSimpleTypeDefinition((SimpleTypeDefinitionBean)this.createCopy((AbstractDescriptorBean)proposed.getSimpleTypeDefinition()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SimpleTypeDefinition", (DescriptorBean)original.getSimpleTypeDefinition());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            MemberDefinitionBeanImpl copy = (MemberDefinitionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AnnotationRef")) && this.bean.isAnnotationRefSet()) {
               copy.setAnnotationRef(this.bean.getAnnotationRef());
            }

            if ((excludeProps == null || !excludeProps.contains("EnumRef")) && this.bean.isEnumRefSet() && !copy._isSet(4)) {
               Object o = this.bean.getEnumRef();
               copy.setEnumRef((EnumRefBean)null);
               copy.setEnumRef(o == null ? null : (EnumRefBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("IsArray")) && this.bean.isIsArraySet()) {
               copy.setIsArray(this.bean.getIsArray());
            }

            if ((excludeProps == null || !excludeProps.contains("IsRequired")) && this.bean.isIsRequiredSet()) {
               copy.setIsRequired(this.bean.getIsRequired());
            }

            if ((excludeProps == null || !excludeProps.contains("MemberName")) && this.bean.isMemberNameSet()) {
               copy.setMemberName(this.bean.getMemberName());
            }

            if ((excludeProps == null || !excludeProps.contains("SimpleTypeDefinition")) && this.bean.isSimpleTypeDefinitionSet() && !copy._isSet(5)) {
               Object o = this.bean.getSimpleTypeDefinition();
               copy.setSimpleTypeDefinition((SimpleTypeDefinitionBean)null);
               copy.setSimpleTypeDefinition(o == null ? null : (SimpleTypeDefinitionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getEnumRef(), clazz, annotation);
         this.inferSubTree(this.bean.getSimpleTypeDefinition(), clazz, annotation);
      }
   }
}
