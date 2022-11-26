package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EjbRelationshipRoleBeanImpl extends AbstractDescriptorBean implements EjbRelationshipRoleBean, Serializable {
   private EmptyBean _CascadeDelete;
   private CmrFieldBean _CmrField;
   private String[] _Descriptions;
   private String _EjbRelationshipRoleName;
   private String _Id;
   private String _Multiplicity;
   private RelationshipRoleSourceBean _RelationshipRoleSource;
   private static SchemaHelper2 _schemaHelper;

   public EjbRelationshipRoleBeanImpl() {
      this._initializeProperty(-1);
   }

   public EjbRelationshipRoleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EjbRelationshipRoleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getEjbRelationshipRoleName() {
      return this._EjbRelationshipRoleName;
   }

   public boolean isEjbRelationshipRoleNameInherited() {
      return false;
   }

   public boolean isEjbRelationshipRoleNameSet() {
      return this._isSet(1);
   }

   public void setEjbRelationshipRoleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbRelationshipRoleName;
      this._EjbRelationshipRoleName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getMultiplicity() {
      return this._Multiplicity;
   }

   public boolean isMultiplicityInherited() {
      return false;
   }

   public boolean isMultiplicitySet() {
      return this._isSet(2);
   }

   public void setMultiplicity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"One", "Many"};
      param0 = LegalChecks.checkInEnum("Multiplicity", param0, _set);
      String _oldVal = this._Multiplicity;
      this._Multiplicity = param0;
      this._postSet(2, _oldVal, param0);
   }

   public EmptyBean getCascadeDelete() {
      return this._CascadeDelete;
   }

   public boolean isCascadeDeleteInherited() {
      return false;
   }

   public boolean isCascadeDeleteSet() {
      return this._isSet(3);
   }

   public void setCascadeDelete(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCascadeDelete() != null && param0 != this.getCascadeDelete()) {
         throw new BeanAlreadyExistsException(this.getCascadeDelete() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._CascadeDelete;
         this._CascadeDelete = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public EmptyBean createCascadeDelete() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setCascadeDelete(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCascadeDelete(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CascadeDelete;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCascadeDelete((EmptyBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public RelationshipRoleSourceBean getRelationshipRoleSource() {
      return this._RelationshipRoleSource;
   }

   public boolean isRelationshipRoleSourceInherited() {
      return false;
   }

   public boolean isRelationshipRoleSourceSet() {
      return this._isSet(4);
   }

   public void setRelationshipRoleSource(RelationshipRoleSourceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRelationshipRoleSource() != null && param0 != this.getRelationshipRoleSource()) {
         throw new BeanAlreadyExistsException(this.getRelationshipRoleSource() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         RelationshipRoleSourceBean _oldVal = this._RelationshipRoleSource;
         this._RelationshipRoleSource = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public RelationshipRoleSourceBean createRelationshipRoleSource() {
      RelationshipRoleSourceBeanImpl _val = new RelationshipRoleSourceBeanImpl(this, -1);

      try {
         this.setRelationshipRoleSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRelationshipRoleSource(RelationshipRoleSourceBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RelationshipRoleSource;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRelationshipRoleSource((RelationshipRoleSourceBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public CmrFieldBean getCmrField() {
      return this._CmrField;
   }

   public boolean isCmrFieldInherited() {
      return false;
   }

   public boolean isCmrFieldSet() {
      return this._isSet(5);
   }

   public void setCmrField(CmrFieldBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCmrField() != null && param0 != this.getCmrField()) {
         throw new BeanAlreadyExistsException(this.getCmrField() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CmrFieldBean _oldVal = this._CmrField;
         this._CmrField = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public CmrFieldBean createCmrField() {
      CmrFieldBeanImpl _val = new CmrFieldBeanImpl(this, -1);

      try {
         this.setCmrField(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCmrField(CmrFieldBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CmrField;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCmrField((CmrFieldBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
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
      return this._isSet(6);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(6, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEjbRelationshipRoleName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Multiplicity", this.isMultiplicitySet());
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 26:
            if (s.equals("ejb-relationship-role-name")) {
               return info.compareXpaths(this._getPropertyXpath("ejb-relationship-role-name"));
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
               this._CascadeDelete = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._CmrField = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._EjbRelationshipRoleName = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Multiplicity = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._RelationshipRoleSource = null;
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
                  return 6;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            default:
               break;
            case 9:
               if (s.equals("cmr-field")) {
                  return 5;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("multiplicity")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("cascade-delete")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("relationship-role-source")) {
                  return 4;
               }
               break;
            case 26:
               if (s.equals("ejb-relationship-role-name")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new EmptyBeanImpl.SchemaHelper2();
            case 4:
               return new RelationshipRoleSourceBeanImpl.SchemaHelper2();
            case 5:
               return new CmrFieldBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "ejb-relationship-role-name";
            case 2:
               return "multiplicity";
            case 3:
               return "cascade-delete";
            case 4:
               return "relationship-role-source";
            case 5:
               return "cmr-field";
            case 6:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
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
         indices.add("ejb-relationship-role-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EjbRelationshipRoleBeanImpl bean;

      protected Helper(EjbRelationshipRoleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "EjbRelationshipRoleName";
            case 2:
               return "Multiplicity";
            case 3:
               return "CascadeDelete";
            case 4:
               return "RelationshipRoleSource";
            case 5:
               return "CmrField";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CascadeDelete")) {
            return 3;
         } else if (propName.equals("CmrField")) {
            return 5;
         } else if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("EjbRelationshipRoleName")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 6;
         } else if (propName.equals("Multiplicity")) {
            return 2;
         } else {
            return propName.equals("RelationshipRoleSource") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCascadeDelete() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getCascadeDelete()}));
         }

         if (this.bean.getCmrField() != null) {
            iterators.add(new ArrayIterator(new CmrFieldBean[]{this.bean.getCmrField()}));
         }

         if (this.bean.getRelationshipRoleSource() != null) {
            iterators.add(new ArrayIterator(new RelationshipRoleSourceBean[]{this.bean.getRelationshipRoleSource()}));
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
            childValue = this.computeChildHashValue(this.bean.getCascadeDelete());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCmrField());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isEjbRelationshipRoleNameSet()) {
               buf.append("EjbRelationshipRoleName");
               buf.append(String.valueOf(this.bean.getEjbRelationshipRoleName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMultiplicitySet()) {
               buf.append("Multiplicity");
               buf.append(String.valueOf(this.bean.getMultiplicity()));
            }

            childValue = this.computeChildHashValue(this.bean.getRelationshipRoleSource());
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
            EjbRelationshipRoleBeanImpl otherTyped = (EjbRelationshipRoleBeanImpl)other;
            this.computeChildDiff("CascadeDelete", this.bean.getCascadeDelete(), otherTyped.getCascadeDelete(), false);
            this.computeChildDiff("CmrField", this.bean.getCmrField(), otherTyped.getCmrField(), false);
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("EjbRelationshipRoleName", this.bean.getEjbRelationshipRoleName(), otherTyped.getEjbRelationshipRoleName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Multiplicity", this.bean.getMultiplicity(), otherTyped.getMultiplicity(), false);
            this.computeChildDiff("RelationshipRoleSource", this.bean.getRelationshipRoleSource(), otherTyped.getRelationshipRoleSource(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EjbRelationshipRoleBeanImpl original = (EjbRelationshipRoleBeanImpl)event.getSourceBean();
            EjbRelationshipRoleBeanImpl proposed = (EjbRelationshipRoleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CascadeDelete")) {
                  if (type == 2) {
                     original.setCascadeDelete((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getCascadeDelete()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CascadeDelete", (DescriptorBean)original.getCascadeDelete());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("CmrField")) {
                  if (type == 2) {
                     original.setCmrField((CmrFieldBean)this.createCopy((AbstractDescriptorBean)proposed.getCmrField()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CmrField", (DescriptorBean)original.getCmrField());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Descriptions")) {
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
               } else if (prop.equals("EjbRelationshipRoleName")) {
                  original.setEjbRelationshipRoleName(proposed.getEjbRelationshipRoleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Multiplicity")) {
                  original.setMultiplicity(proposed.getMultiplicity());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RelationshipRoleSource")) {
                  if (type == 2) {
                     original.setRelationshipRoleSource((RelationshipRoleSourceBean)this.createCopy((AbstractDescriptorBean)proposed.getRelationshipRoleSource()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RelationshipRoleSource", (DescriptorBean)original.getRelationshipRoleSource());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            EjbRelationshipRoleBeanImpl copy = (EjbRelationshipRoleBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CascadeDelete")) && this.bean.isCascadeDeleteSet() && !copy._isSet(3)) {
               Object o = this.bean.getCascadeDelete();
               copy.setCascadeDelete((EmptyBean)null);
               copy.setCascadeDelete(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CmrField")) && this.bean.isCmrFieldSet() && !copy._isSet(5)) {
               Object o = this.bean.getCmrField();
               copy.setCmrField((CmrFieldBean)null);
               copy.setCmrField(o == null ? null : (CmrFieldBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbRelationshipRoleName")) && this.bean.isEjbRelationshipRoleNameSet()) {
               copy.setEjbRelationshipRoleName(this.bean.getEjbRelationshipRoleName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Multiplicity")) && this.bean.isMultiplicitySet()) {
               copy.setMultiplicity(this.bean.getMultiplicity());
            }

            if ((excludeProps == null || !excludeProps.contains("RelationshipRoleSource")) && this.bean.isRelationshipRoleSourceSet() && !copy._isSet(4)) {
               Object o = this.bean.getRelationshipRoleSource();
               copy.setRelationshipRoleSource((RelationshipRoleSourceBean)null);
               copy.setRelationshipRoleSource(o == null ? null : (RelationshipRoleSourceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getCascadeDelete(), clazz, annotation);
         this.inferSubTree(this.bean.getCmrField(), clazz, annotation);
         this.inferSubTree(this.bean.getRelationshipRoleSource(), clazz, annotation);
      }
   }
}
