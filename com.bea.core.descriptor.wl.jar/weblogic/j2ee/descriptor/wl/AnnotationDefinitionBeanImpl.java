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

public class AnnotationDefinitionBeanImpl extends AbstractDescriptorBean implements AnnotationDefinitionBean, Serializable {
   private boolean _AllowedOnDeclaration;
   private String _AnnotationClassName;
   private MemberDefinitionBean[] _MemberDefinitions;
   private MembershipConstraintBean _MembershipConstraint;
   private static SchemaHelper2 _schemaHelper;

   public AnnotationDefinitionBeanImpl() {
      this._initializeProperty(-1);
   }

   public AnnotationDefinitionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AnnotationDefinitionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAnnotationClassName() {
      return this._AnnotationClassName;
   }

   public boolean isAnnotationClassNameInherited() {
      return false;
   }

   public boolean isAnnotationClassNameSet() {
      return this._isSet(0);
   }

   public void setAnnotationClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AnnotationClassName;
      this._AnnotationClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public MembershipConstraintBean getMembershipConstraint() {
      return this._MembershipConstraint;
   }

   public boolean isMembershipConstraintInherited() {
      return false;
   }

   public boolean isMembershipConstraintSet() {
      return this._isSet(1);
   }

   public void setMembershipConstraint(MembershipConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMembershipConstraint() != null && param0 != this.getMembershipConstraint()) {
         throw new BeanAlreadyExistsException(this.getMembershipConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MembershipConstraintBean _oldVal = this._MembershipConstraint;
         this._MembershipConstraint = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public MembershipConstraintBean createMembershipConstraint() {
      MembershipConstraintBeanImpl _val = new MembershipConstraintBeanImpl(this, -1);

      try {
         this.setMembershipConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean getAllowedOnDeclaration() {
      return this._AllowedOnDeclaration;
   }

   public boolean isAllowedOnDeclarationInherited() {
      return false;
   }

   public boolean isAllowedOnDeclarationSet() {
      return this._isSet(2);
   }

   public void setAllowedOnDeclaration(boolean param0) {
      boolean _oldVal = this._AllowedOnDeclaration;
      this._AllowedOnDeclaration = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addMemberDefinition(MemberDefinitionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         MemberDefinitionBean[] _new;
         if (this._isSet(3)) {
            _new = (MemberDefinitionBean[])((MemberDefinitionBean[])this._getHelper()._extendArray(this.getMemberDefinitions(), MemberDefinitionBean.class, param0));
         } else {
            _new = new MemberDefinitionBean[]{param0};
         }

         try {
            this.setMemberDefinitions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MemberDefinitionBean[] getMemberDefinitions() {
      return this._MemberDefinitions;
   }

   public boolean isMemberDefinitionsInherited() {
      return false;
   }

   public boolean isMemberDefinitionsSet() {
      return this._isSet(3);
   }

   public void removeMemberDefinition(MemberDefinitionBean param0) {
      MemberDefinitionBean[] _old = this.getMemberDefinitions();
      MemberDefinitionBean[] _new = (MemberDefinitionBean[])((MemberDefinitionBean[])this._getHelper()._removeElement(_old, MemberDefinitionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMemberDefinitions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMemberDefinitions(MemberDefinitionBean[] param0) throws InvalidAttributeValueException {
      MemberDefinitionBean[] param0 = param0 == null ? new MemberDefinitionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MemberDefinitionBean[] _oldVal = this._MemberDefinitions;
      this._MemberDefinitions = (MemberDefinitionBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public MemberDefinitionBean createMemberDefinition() {
      MemberDefinitionBeanImpl _val = new MemberDefinitionBeanImpl(this, -1);

      try {
         this.addMemberDefinition(_val);
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
      return this.getAnnotationClassName();
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
         case 21:
            if (s.equals("annotation-class-name")) {
               return info.compareXpaths(this._getPropertyXpath("annotation-class-name"));
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._AllowedOnDeclaration = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._AnnotationClassName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._MemberDefinitions = new MemberDefinitionBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._MembershipConstraint = null;
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
            case 17:
               if (s.equals("member-definition")) {
                  return 3;
               }
               break;
            case 21:
               if (s.equals("annotation-class-name")) {
                  return 0;
               }

               if (s.equals("membership-constraint")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("allowed-on-declaration")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new MembershipConstraintBeanImpl.SchemaHelper2();
            case 3:
               return new MemberDefinitionBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "annotation-class-name";
            case 1:
               return "membership-constraint";
            case 2:
               return "allowed-on-declaration";
            case 3:
               return "member-definition";
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
            case 3:
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
         indices.add("annotation-class-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AnnotationDefinitionBeanImpl bean;

      protected Helper(AnnotationDefinitionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AnnotationClassName";
            case 1:
               return "MembershipConstraint";
            case 2:
               return "AllowedOnDeclaration";
            case 3:
               return "MemberDefinitions";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowedOnDeclaration")) {
            return 2;
         } else if (propName.equals("AnnotationClassName")) {
            return 0;
         } else if (propName.equals("MemberDefinitions")) {
            return 3;
         } else {
            return propName.equals("MembershipConstraint") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getMemberDefinitions()));
         if (this.bean.getMembershipConstraint() != null) {
            iterators.add(new ArrayIterator(new MembershipConstraintBean[]{this.bean.getMembershipConstraint()}));
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
            if (this.bean.isAllowedOnDeclarationSet()) {
               buf.append("AllowedOnDeclaration");
               buf.append(String.valueOf(this.bean.getAllowedOnDeclaration()));
            }

            if (this.bean.isAnnotationClassNameSet()) {
               buf.append("AnnotationClassName");
               buf.append(String.valueOf(this.bean.getAnnotationClassName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getMemberDefinitions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMemberDefinitions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getMembershipConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            AnnotationDefinitionBeanImpl otherTyped = (AnnotationDefinitionBeanImpl)other;
            this.computeDiff("AllowedOnDeclaration", this.bean.getAllowedOnDeclaration(), otherTyped.getAllowedOnDeclaration(), false);
            this.computeDiff("AnnotationClassName", this.bean.getAnnotationClassName(), otherTyped.getAnnotationClassName(), false);
            this.computeChildDiff("MemberDefinitions", this.bean.getMemberDefinitions(), otherTyped.getMemberDefinitions(), false);
            this.computeChildDiff("MembershipConstraint", this.bean.getMembershipConstraint(), otherTyped.getMembershipConstraint(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnnotationDefinitionBeanImpl original = (AnnotationDefinitionBeanImpl)event.getSourceBean();
            AnnotationDefinitionBeanImpl proposed = (AnnotationDefinitionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowedOnDeclaration")) {
                  original.setAllowedOnDeclaration(proposed.getAllowedOnDeclaration());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("AnnotationClassName")) {
                  original.setAnnotationClassName(proposed.getAnnotationClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MemberDefinitions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMemberDefinition((MemberDefinitionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMemberDefinition((MemberDefinitionBean)update.getRemovedObject());
                  }

                  if (original.getMemberDefinitions() == null || original.getMemberDefinitions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("MembershipConstraint")) {
                  if (type == 2) {
                     original.setMembershipConstraint((MembershipConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getMembershipConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MembershipConstraint", (DescriptorBean)original.getMembershipConstraint());
                  }

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
            AnnotationDefinitionBeanImpl copy = (AnnotationDefinitionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AllowedOnDeclaration")) && this.bean.isAllowedOnDeclarationSet()) {
               copy.setAllowedOnDeclaration(this.bean.getAllowedOnDeclaration());
            }

            if ((excludeProps == null || !excludeProps.contains("AnnotationClassName")) && this.bean.isAnnotationClassNameSet()) {
               copy.setAnnotationClassName(this.bean.getAnnotationClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("MemberDefinitions")) && this.bean.isMemberDefinitionsSet() && !copy._isSet(3)) {
               MemberDefinitionBean[] oldMemberDefinitions = this.bean.getMemberDefinitions();
               MemberDefinitionBean[] newMemberDefinitions = new MemberDefinitionBean[oldMemberDefinitions.length];

               for(int i = 0; i < newMemberDefinitions.length; ++i) {
                  newMemberDefinitions[i] = (MemberDefinitionBean)((MemberDefinitionBean)this.createCopy((AbstractDescriptorBean)oldMemberDefinitions[i], includeObsolete));
               }

               copy.setMemberDefinitions(newMemberDefinitions);
            }

            if ((excludeProps == null || !excludeProps.contains("MembershipConstraint")) && this.bean.isMembershipConstraintSet() && !copy._isSet(1)) {
               Object o = this.bean.getMembershipConstraint();
               copy.setMembershipConstraint((MembershipConstraintBean)null);
               copy.setMembershipConstraint(o == null ? null : (MembershipConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getMemberDefinitions(), clazz, annotation);
         this.inferSubTree(this.bean.getMembershipConstraint(), clazz, annotation);
      }
   }
}
