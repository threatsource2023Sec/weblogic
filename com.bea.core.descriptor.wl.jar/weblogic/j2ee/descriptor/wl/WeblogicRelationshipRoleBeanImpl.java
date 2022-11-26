package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.EmptyBean;
import weblogic.j2ee.descriptor.EmptyBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicRelationshipRoleBeanImpl extends AbstractDescriptorBean implements WeblogicRelationshipRoleBean, Serializable {
   private EmptyBean _DbCascadeDelete;
   private boolean _EnableQueryCaching;
   private String _GroupName;
   private String _Id;
   private RelationshipRoleMapBean _RelationshipRoleMap;
   private String _RelationshipRoleName;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicRelationshipRoleBeanImpl() {
      this._initializeProperty(-1);
   }

   public WeblogicRelationshipRoleBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WeblogicRelationshipRoleBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRelationshipRoleName() {
      return this._RelationshipRoleName;
   }

   public boolean isRelationshipRoleNameInherited() {
      return false;
   }

   public boolean isRelationshipRoleNameSet() {
      return this._isSet(0);
   }

   public void setRelationshipRoleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RelationshipRoleName;
      this._RelationshipRoleName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getGroupName() {
      return this._GroupName;
   }

   public boolean isGroupNameInherited() {
      return false;
   }

   public boolean isGroupNameSet() {
      return this._isSet(1);
   }

   public void setGroupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GroupName;
      this._GroupName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public RelationshipRoleMapBean getRelationshipRoleMap() {
      return this._RelationshipRoleMap;
   }

   public boolean isRelationshipRoleMapInherited() {
      return false;
   }

   public boolean isRelationshipRoleMapSet() {
      return this._isSet(2);
   }

   public void setRelationshipRoleMap(RelationshipRoleMapBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRelationshipRoleMap() != null && param0 != this.getRelationshipRoleMap()) {
         throw new BeanAlreadyExistsException(this.getRelationshipRoleMap() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         RelationshipRoleMapBean _oldVal = this._RelationshipRoleMap;
         this._RelationshipRoleMap = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public RelationshipRoleMapBean createRelationshipRoleMap() {
      RelationshipRoleMapBeanImpl _val = new RelationshipRoleMapBeanImpl(this, -1);

      try {
         this.setRelationshipRoleMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRelationshipRoleMap(RelationshipRoleMapBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RelationshipRoleMap;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRelationshipRoleMap((RelationshipRoleMapBean)null);
               this._unSet(2);
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

   public EmptyBean getDbCascadeDelete() {
      return this._DbCascadeDelete;
   }

   public boolean isDbCascadeDeleteInherited() {
      return false;
   }

   public boolean isDbCascadeDeleteSet() {
      return this._isSet(3);
   }

   public void setDbCascadeDelete(EmptyBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDbCascadeDelete() != null && param0 != this.getDbCascadeDelete()) {
         throw new BeanAlreadyExistsException(this.getDbCascadeDelete() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EmptyBean _oldVal = this._DbCascadeDelete;
         this._DbCascadeDelete = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public EmptyBean createDbCascadeDelete() {
      EmptyBeanImpl _val = new EmptyBeanImpl(this, -1);

      try {
         this.setDbCascadeDelete(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDbCascadeDelete(EmptyBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DbCascadeDelete;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDbCascadeDelete((EmptyBean)null);
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

   public void setEnableQueryCaching(boolean param0) {
      boolean _oldVal = this._EnableQueryCaching;
      this._EnableQueryCaching = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean getEnableQueryCaching() {
      return this._EnableQueryCaching;
   }

   public boolean isEnableQueryCachingInherited() {
      return false;
   }

   public boolean isEnableQueryCachingSet() {
      return this._isSet(4);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getRelationshipRoleName();
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
         case 22:
            if (s.equals("relationship-role-name")) {
               return info.compareXpaths(this._getPropertyXpath("relationship-role-name"));
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
               this._DbCascadeDelete = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._EnableQueryCaching = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._GroupName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._RelationshipRoleMap = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._RelationshipRoleName = null;
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
                  return 5;
               }
               break;
            case 10:
               if (s.equals("group-name")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("db-cascade-delete")) {
                  return 3;
               }
               break;
            case 20:
               if (s.equals("enable-query-caching")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("relationship-role-map")) {
                  return 2;
               }
               break;
            case 22:
               if (s.equals("relationship-role-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new RelationshipRoleMapBeanImpl.SchemaHelper2();
            case 3:
               return new EmptyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "relationship-role-name";
            case 1:
               return "group-name";
            case 2:
               return "relationship-role-map";
            case 3:
               return "db-cascade-delete";
            case 4:
               return "enable-query-caching";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
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
         indices.add("relationship-role-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicRelationshipRoleBeanImpl bean;

      protected Helper(WeblogicRelationshipRoleBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RelationshipRoleName";
            case 1:
               return "GroupName";
            case 2:
               return "RelationshipRoleMap";
            case 3:
               return "DbCascadeDelete";
            case 4:
               return "EnableQueryCaching";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DbCascadeDelete")) {
            return 3;
         } else if (propName.equals("EnableQueryCaching")) {
            return 4;
         } else if (propName.equals("GroupName")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("RelationshipRoleMap")) {
            return 2;
         } else {
            return propName.equals("RelationshipRoleName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDbCascadeDelete() != null) {
            iterators.add(new ArrayIterator(new EmptyBean[]{this.bean.getDbCascadeDelete()}));
         }

         if (this.bean.getRelationshipRoleMap() != null) {
            iterators.add(new ArrayIterator(new RelationshipRoleMapBean[]{this.bean.getRelationshipRoleMap()}));
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
            childValue = this.computeChildHashValue(this.bean.getDbCascadeDelete());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnableQueryCachingSet()) {
               buf.append("EnableQueryCaching");
               buf.append(String.valueOf(this.bean.getEnableQueryCaching()));
            }

            if (this.bean.isGroupNameSet()) {
               buf.append("GroupName");
               buf.append(String.valueOf(this.bean.getGroupName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getRelationshipRoleMap());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRelationshipRoleNameSet()) {
               buf.append("RelationshipRoleName");
               buf.append(String.valueOf(this.bean.getRelationshipRoleName()));
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
            WeblogicRelationshipRoleBeanImpl otherTyped = (WeblogicRelationshipRoleBeanImpl)other;
            this.computeChildDiff("DbCascadeDelete", this.bean.getDbCascadeDelete(), otherTyped.getDbCascadeDelete(), false);
            this.computeDiff("EnableQueryCaching", this.bean.getEnableQueryCaching(), otherTyped.getEnableQueryCaching(), false);
            this.computeDiff("GroupName", this.bean.getGroupName(), otherTyped.getGroupName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("RelationshipRoleMap", this.bean.getRelationshipRoleMap(), otherTyped.getRelationshipRoleMap(), false);
            this.computeDiff("RelationshipRoleName", this.bean.getRelationshipRoleName(), otherTyped.getRelationshipRoleName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicRelationshipRoleBeanImpl original = (WeblogicRelationshipRoleBeanImpl)event.getSourceBean();
            WeblogicRelationshipRoleBeanImpl proposed = (WeblogicRelationshipRoleBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DbCascadeDelete")) {
                  if (type == 2) {
                     original.setDbCascadeDelete((EmptyBean)this.createCopy((AbstractDescriptorBean)proposed.getDbCascadeDelete()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DbCascadeDelete", (DescriptorBean)original.getDbCascadeDelete());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("EnableQueryCaching")) {
                  original.setEnableQueryCaching(proposed.getEnableQueryCaching());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("GroupName")) {
                  original.setGroupName(proposed.getGroupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("RelationshipRoleMap")) {
                  if (type == 2) {
                     original.setRelationshipRoleMap((RelationshipRoleMapBean)this.createCopy((AbstractDescriptorBean)proposed.getRelationshipRoleMap()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("RelationshipRoleMap", (DescriptorBean)original.getRelationshipRoleMap());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RelationshipRoleName")) {
                  original.setRelationshipRoleName(proposed.getRelationshipRoleName());
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
            WeblogicRelationshipRoleBeanImpl copy = (WeblogicRelationshipRoleBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DbCascadeDelete")) && this.bean.isDbCascadeDeleteSet() && !copy._isSet(3)) {
               Object o = this.bean.getDbCascadeDelete();
               copy.setDbCascadeDelete((EmptyBean)null);
               copy.setDbCascadeDelete(o == null ? null : (EmptyBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EnableQueryCaching")) && this.bean.isEnableQueryCachingSet()) {
               copy.setEnableQueryCaching(this.bean.getEnableQueryCaching());
            }

            if ((excludeProps == null || !excludeProps.contains("GroupName")) && this.bean.isGroupNameSet()) {
               copy.setGroupName(this.bean.getGroupName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("RelationshipRoleMap")) && this.bean.isRelationshipRoleMapSet() && !copy._isSet(2)) {
               Object o = this.bean.getRelationshipRoleMap();
               copy.setRelationshipRoleMap((RelationshipRoleMapBean)null);
               copy.setRelationshipRoleMap(o == null ? null : (RelationshipRoleMapBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RelationshipRoleName")) && this.bean.isRelationshipRoleNameSet()) {
               copy.setRelationshipRoleName(this.bean.getRelationshipRoleName());
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
         this.inferSubTree(this.bean.getDbCascadeDelete(), clazz, annotation);
         this.inferSubTree(this.bean.getRelationshipRoleMap(), clazz, annotation);
      }
   }
}
