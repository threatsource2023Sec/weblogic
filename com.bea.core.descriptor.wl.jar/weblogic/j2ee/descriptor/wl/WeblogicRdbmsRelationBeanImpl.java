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

public class WeblogicRdbmsRelationBeanImpl extends AbstractDescriptorBean implements WeblogicRdbmsRelationBean, Serializable {
   private String _Id;
   private String _RelationName;
   private String _TableName;
   private WeblogicRelationshipRoleBean[] _WeblogicRelationshipRoles;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicRdbmsRelationBeanImpl() {
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsRelationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WeblogicRdbmsRelationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRelationName() {
      return this._RelationName;
   }

   public boolean isRelationNameInherited() {
      return false;
   }

   public boolean isRelationNameSet() {
      return this._isSet(0);
   }

   public void setRelationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RelationName;
      this._RelationName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(1);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addWeblogicRelationshipRole(WeblogicRelationshipRoleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         WeblogicRelationshipRoleBean[] _new;
         if (this._isSet(2)) {
            _new = (WeblogicRelationshipRoleBean[])((WeblogicRelationshipRoleBean[])this._getHelper()._extendArray(this.getWeblogicRelationshipRoles(), WeblogicRelationshipRoleBean.class, param0));
         } else {
            _new = new WeblogicRelationshipRoleBean[]{param0};
         }

         try {
            this.setWeblogicRelationshipRoles(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WeblogicRelationshipRoleBean[] getWeblogicRelationshipRoles() {
      return this._WeblogicRelationshipRoles;
   }

   public boolean isWeblogicRelationshipRolesInherited() {
      return false;
   }

   public boolean isWeblogicRelationshipRolesSet() {
      return this._isSet(2);
   }

   public void removeWeblogicRelationshipRole(WeblogicRelationshipRoleBean param0) {
      this.destroyWeblogicRelationshipRole(param0);
   }

   public void setWeblogicRelationshipRoles(WeblogicRelationshipRoleBean[] param0) throws InvalidAttributeValueException {
      WeblogicRelationshipRoleBean[] param0 = param0 == null ? new WeblogicRelationshipRoleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WeblogicRelationshipRoleBean[] _oldVal = this._WeblogicRelationshipRoles;
      this._WeblogicRelationshipRoles = (WeblogicRelationshipRoleBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public WeblogicRelationshipRoleBean createWeblogicRelationshipRole() {
      WeblogicRelationshipRoleBeanImpl _val = new WeblogicRelationshipRoleBeanImpl(this, -1);

      try {
         this.addWeblogicRelationshipRole(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWeblogicRelationshipRole(WeblogicRelationshipRoleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         WeblogicRelationshipRoleBean[] _old = this.getWeblogicRelationshipRoles();
         WeblogicRelationshipRoleBean[] _new = (WeblogicRelationshipRoleBean[])((WeblogicRelationshipRoleBean[])this._getHelper()._removeElement(_old, WeblogicRelationshipRoleBean.class, param0));
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
               this.setWeblogicRelationshipRoles(_new);
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
      return this.getRelationName();
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
         case 13:
            if (s.equals("relation-name")) {
               return info.compareXpaths(this._getPropertyXpath("relation-name"));
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._RelationName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._TableName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._WeblogicRelationshipRoles = new WeblogicRelationshipRoleBean[0];
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
            case 10:
               if (s.equals("table-name")) {
                  return 1;
               }
               break;
            case 13:
               if (s.equals("relation-name")) {
                  return 0;
               }
               break;
            case 26:
               if (s.equals("weblogic-relationship-role")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new WeblogicRelationshipRoleBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "relation-name";
            case 1:
               return "table-name";
            case 2:
               return "weblogic-relationship-role";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
         indices.add("relation-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicRdbmsRelationBeanImpl bean;

      protected Helper(WeblogicRdbmsRelationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RelationName";
            case 1:
               return "TableName";
            case 2:
               return "WeblogicRelationshipRoles";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("RelationName")) {
            return 0;
         } else if (propName.equals("TableName")) {
            return 1;
         } else {
            return propName.equals("WeblogicRelationshipRoles") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWeblogicRelationshipRoles()));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isRelationNameSet()) {
               buf.append("RelationName");
               buf.append(String.valueOf(this.bean.getRelationName()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWeblogicRelationshipRoles().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWeblogicRelationshipRoles()[i]);
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
            WeblogicRdbmsRelationBeanImpl otherTyped = (WeblogicRdbmsRelationBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("RelationName", this.bean.getRelationName(), otherTyped.getRelationName(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeChildDiff("WeblogicRelationshipRoles", this.bean.getWeblogicRelationshipRoles(), otherTyped.getWeblogicRelationshipRoles(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicRdbmsRelationBeanImpl original = (WeblogicRdbmsRelationBeanImpl)event.getSourceBean();
            WeblogicRdbmsRelationBeanImpl proposed = (WeblogicRdbmsRelationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RelationName")) {
                  original.setRelationName(proposed.getRelationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WeblogicRelationshipRoles")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWeblogicRelationshipRole((WeblogicRelationshipRoleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWeblogicRelationshipRole((WeblogicRelationshipRoleBean)update.getRemovedObject());
                  }

                  if (original.getWeblogicRelationshipRoles() == null || original.getWeblogicRelationshipRoles().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            WeblogicRdbmsRelationBeanImpl copy = (WeblogicRdbmsRelationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("RelationName")) && this.bean.isRelationNameSet()) {
               copy.setRelationName(this.bean.getRelationName());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("WeblogicRelationshipRoles")) && this.bean.isWeblogicRelationshipRolesSet() && !copy._isSet(2)) {
               WeblogicRelationshipRoleBean[] oldWeblogicRelationshipRoles = this.bean.getWeblogicRelationshipRoles();
               WeblogicRelationshipRoleBean[] newWeblogicRelationshipRoles = new WeblogicRelationshipRoleBean[oldWeblogicRelationshipRoles.length];

               for(int i = 0; i < newWeblogicRelationshipRoles.length; ++i) {
                  newWeblogicRelationshipRoles[i] = (WeblogicRelationshipRoleBean)((WeblogicRelationshipRoleBean)this.createCopy((AbstractDescriptorBean)oldWeblogicRelationshipRoles[i], includeObsolete));
               }

               copy.setWeblogicRelationshipRoles(newWeblogicRelationshipRoles);
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
         this.inferSubTree(this.bean.getWeblogicRelationshipRoles(), clazz, annotation);
      }
   }
}
