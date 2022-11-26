package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SecurityBeanImpl extends AbstractDescriptorBean implements SecurityBean, Serializable {
   private String _RealmName;
   private ApplicationSecurityRoleAssignmentBean[] _SecurityRoleAssignments;
   private static SchemaHelper2 _schemaHelper;

   public SecurityBeanImpl() {
      this._initializeProperty(-1);
   }

   public SecurityBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SecurityBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRealmName() {
      return this._RealmName;
   }

   public boolean isRealmNameInherited() {
      return false;
   }

   public boolean isRealmNameSet() {
      return this._isSet(0);
   }

   public void setRealmName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RealmName;
      this._RealmName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addSecurityRoleAssignment(ApplicationSecurityRoleAssignmentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         ApplicationSecurityRoleAssignmentBean[] _new;
         if (this._isSet(1)) {
            _new = (ApplicationSecurityRoleAssignmentBean[])((ApplicationSecurityRoleAssignmentBean[])this._getHelper()._extendArray(this.getSecurityRoleAssignments(), ApplicationSecurityRoleAssignmentBean.class, param0));
         } else {
            _new = new ApplicationSecurityRoleAssignmentBean[]{param0};
         }

         try {
            this.setSecurityRoleAssignments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ApplicationSecurityRoleAssignmentBean[] getSecurityRoleAssignments() {
      return this._SecurityRoleAssignments;
   }

   public boolean isSecurityRoleAssignmentsInherited() {
      return false;
   }

   public boolean isSecurityRoleAssignmentsSet() {
      return this._isSet(1);
   }

   public void removeSecurityRoleAssignment(ApplicationSecurityRoleAssignmentBean param0) {
      this.destroySecurityRoleAssignment(param0);
   }

   public void setSecurityRoleAssignments(ApplicationSecurityRoleAssignmentBean[] param0) throws InvalidAttributeValueException {
      ApplicationSecurityRoleAssignmentBean[] param0 = param0 == null ? new ApplicationSecurityRoleAssignmentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ApplicationSecurityRoleAssignmentBean[] _oldVal = this._SecurityRoleAssignments;
      this._SecurityRoleAssignments = (ApplicationSecurityRoleAssignmentBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public ApplicationSecurityRoleAssignmentBean createSecurityRoleAssignment() {
      ApplicationSecurityRoleAssignmentBeanImpl _val = new ApplicationSecurityRoleAssignmentBeanImpl(this, -1);

      try {
         this.addSecurityRoleAssignment(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurityRoleAssignment(ApplicationSecurityRoleAssignmentBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         ApplicationSecurityRoleAssignmentBean[] _old = this.getSecurityRoleAssignments();
         ApplicationSecurityRoleAssignmentBean[] _new = (ApplicationSecurityRoleAssignmentBean[])((ApplicationSecurityRoleAssignmentBean[])this._getHelper()._removeElement(_old, ApplicationSecurityRoleAssignmentBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSecurityRoleAssignments(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
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
               this._RealmName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._SecurityRoleAssignments = new ApplicationSecurityRoleAssignmentBean[0];
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
               if (s.equals("realm-name")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("security-role-assignment")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ApplicationSecurityRoleAssignmentBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "realm-name";
            case 1:
               return "security-role-assignment";
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
      private SecurityBeanImpl bean;

      protected Helper(SecurityBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RealmName";
            case 1:
               return "SecurityRoleAssignments";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("RealmName")) {
            return 0;
         } else {
            return propName.equals("SecurityRoleAssignments") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getSecurityRoleAssignments()));
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
            if (this.bean.isRealmNameSet()) {
               buf.append("RealmName");
               buf.append(String.valueOf(this.bean.getRealmName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getSecurityRoleAssignments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSecurityRoleAssignments()[i]);
            }

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
            SecurityBeanImpl otherTyped = (SecurityBeanImpl)other;
            this.computeDiff("RealmName", this.bean.getRealmName(), otherTyped.getRealmName(), false);
            this.computeChildDiff("SecurityRoleAssignments", this.bean.getSecurityRoleAssignments(), otherTyped.getSecurityRoleAssignments(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecurityBeanImpl original = (SecurityBeanImpl)event.getSourceBean();
            SecurityBeanImpl proposed = (SecurityBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("RealmName")) {
                  original.setRealmName(proposed.getRealmName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("SecurityRoleAssignments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSecurityRoleAssignment((ApplicationSecurityRoleAssignmentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSecurityRoleAssignment((ApplicationSecurityRoleAssignmentBean)update.getRemovedObject());
                  }

                  if (original.getSecurityRoleAssignments() == null || original.getSecurityRoleAssignments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
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
            SecurityBeanImpl copy = (SecurityBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("RealmName")) && this.bean.isRealmNameSet()) {
               copy.setRealmName(this.bean.getRealmName());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityRoleAssignments")) && this.bean.isSecurityRoleAssignmentsSet() && !copy._isSet(1)) {
               ApplicationSecurityRoleAssignmentBean[] oldSecurityRoleAssignments = this.bean.getSecurityRoleAssignments();
               ApplicationSecurityRoleAssignmentBean[] newSecurityRoleAssignments = new ApplicationSecurityRoleAssignmentBean[oldSecurityRoleAssignments.length];

               for(int i = 0; i < newSecurityRoleAssignments.length; ++i) {
                  newSecurityRoleAssignments[i] = (ApplicationSecurityRoleAssignmentBean)((ApplicationSecurityRoleAssignmentBean)this.createCopy((AbstractDescriptorBean)oldSecurityRoleAssignments[i], includeObsolete));
               }

               copy.setSecurityRoleAssignments(newSecurityRoleAssignments);
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
         this.inferSubTree(this.bean.getSecurityRoleAssignments(), clazz, annotation);
      }
   }
}
