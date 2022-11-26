package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EjbRelationBeanImpl extends AbstractDescriptorBean implements EjbRelationBean, Serializable {
   private String[] _Descriptions;
   private String _EjbRelationName;
   private EjbRelationshipRoleBean[] _EjbRelationshipRoles;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public EjbRelationBeanImpl() {
      this._initializeProperty(-1);
   }

   public EjbRelationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EjbRelationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getEjbRelationName() {
      return this._EjbRelationName;
   }

   public boolean isEjbRelationNameInherited() {
      return false;
   }

   public boolean isEjbRelationNameSet() {
      return this._isSet(1);
   }

   public void setEjbRelationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbRelationName;
      this._EjbRelationName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addEjbRelationshipRole(EjbRelationshipRoleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         EjbRelationshipRoleBean[] _new;
         if (this._isSet(2)) {
            _new = (EjbRelationshipRoleBean[])((EjbRelationshipRoleBean[])this._getHelper()._extendArray(this.getEjbRelationshipRoles(), EjbRelationshipRoleBean.class, param0));
         } else {
            _new = new EjbRelationshipRoleBean[]{param0};
         }

         try {
            this.setEjbRelationshipRoles(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbRelationshipRoleBean[] getEjbRelationshipRoles() {
      return this._EjbRelationshipRoles;
   }

   public boolean isEjbRelationshipRolesInherited() {
      return false;
   }

   public boolean isEjbRelationshipRolesSet() {
      return this._isSet(2);
   }

   public void removeEjbRelationshipRole(EjbRelationshipRoleBean param0) {
      this.destroyEjbRelationshipRole(param0);
   }

   public void setEjbRelationshipRoles(EjbRelationshipRoleBean[] param0) throws InvalidAttributeValueException {
      EjbRelationshipRoleBean[] param0 = param0 == null ? new EjbRelationshipRoleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EjbRelationshipRoleBean[] _oldVal = this._EjbRelationshipRoles;
      this._EjbRelationshipRoles = (EjbRelationshipRoleBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public EjbRelationshipRoleBean createEjbRelationshipRole() {
      EjbRelationshipRoleBeanImpl _val = new EjbRelationshipRoleBeanImpl(this, -1);

      try {
         this.addEjbRelationshipRole(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbRelationshipRole(EjbRelationshipRoleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         EjbRelationshipRoleBean[] _old = this.getEjbRelationshipRoles();
         EjbRelationshipRoleBean[] _new = (EjbRelationshipRoleBean[])((EjbRelationshipRoleBean[])this._getHelper()._removeElement(_old, EjbRelationshipRoleBean.class, param0));
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
               this.setEjbRelationshipRoles(_new);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEjbRelationName();
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
         case 17:
            if (s.equals("ejb-relation-name")) {
               return info.compareXpaths(this._getPropertyXpath("ejb-relation-name"));
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._EjbRelationName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._EjbRelationshipRoles = new EjbRelationshipRoleBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
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
            case 2:
               if (s.equals("id")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("ejb-relation-name")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("ejb-relationship-role")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new EjbRelationshipRoleBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "ejb-relation-name";
            case 2:
               return "ejb-relationship-role";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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
         indices.add("ejb-relation-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EjbRelationBeanImpl bean;

      protected Helper(EjbRelationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "EjbRelationName";
            case 2:
               return "EjbRelationshipRoles";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("EjbRelationName")) {
            return 1;
         } else if (propName.equals("EjbRelationshipRoles")) {
            return 2;
         } else {
            return propName.equals("Id") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getEjbRelationshipRoles()));
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
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isEjbRelationNameSet()) {
               buf.append("EjbRelationName");
               buf.append(String.valueOf(this.bean.getEjbRelationName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getEjbRelationshipRoles().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbRelationshipRoles()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            EjbRelationBeanImpl otherTyped = (EjbRelationBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("EjbRelationName", this.bean.getEjbRelationName(), otherTyped.getEjbRelationName(), false);
            this.computeChildDiff("EjbRelationshipRoles", this.bean.getEjbRelationshipRoles(), otherTyped.getEjbRelationshipRoles(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EjbRelationBeanImpl original = (EjbRelationBeanImpl)event.getSourceBean();
            EjbRelationBeanImpl proposed = (EjbRelationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("EjbRelationName")) {
                  original.setEjbRelationName(proposed.getEjbRelationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EjbRelationshipRoles")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbRelationshipRole((EjbRelationshipRoleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbRelationshipRole((EjbRelationshipRoleBean)update.getRemovedObject());
                  }

                  if (original.getEjbRelationshipRoles() == null || original.getEjbRelationshipRoles().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            EjbRelationBeanImpl copy = (EjbRelationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRelationName")) && this.bean.isEjbRelationNameSet()) {
               copy.setEjbRelationName(this.bean.getEjbRelationName());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRelationshipRoles")) && this.bean.isEjbRelationshipRolesSet() && !copy._isSet(2)) {
               EjbRelationshipRoleBean[] oldEjbRelationshipRoles = this.bean.getEjbRelationshipRoles();
               EjbRelationshipRoleBean[] newEjbRelationshipRoles = new EjbRelationshipRoleBean[oldEjbRelationshipRoles.length];

               for(int i = 0; i < newEjbRelationshipRoles.length; ++i) {
                  newEjbRelationshipRoles[i] = (EjbRelationshipRoleBean)((EjbRelationshipRoleBean)this.createCopy((AbstractDescriptorBean)oldEjbRelationshipRoles[i], includeObsolete));
               }

               copy.setEjbRelationshipRoles(newEjbRelationshipRoles);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
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
         this.inferSubTree(this.bean.getEjbRelationshipRoles(), clazz, annotation);
      }
   }
}
