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
import weblogic.descriptor.internal.CompoundKey;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AdminObjectGroupBeanImpl extends AbstractDescriptorBean implements AdminObjectGroupBean, Serializable {
   private String _AdminObjectClass;
   private AdminObjectInstanceBean[] _AdminObjectInstances;
   private String _AdminObjectInterface;
   private ConfigPropertiesBean _DefaultProperties;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public AdminObjectGroupBeanImpl() {
      this._initializeProperty(-1);
   }

   public AdminObjectGroupBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AdminObjectGroupBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAdminObjectInterface() {
      return this._AdminObjectInterface;
   }

   public boolean isAdminObjectInterfaceInherited() {
      return false;
   }

   public boolean isAdminObjectInterfaceSet() {
      return this._isSet(0);
   }

   public void setAdminObjectInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdminObjectInterface;
      this._AdminObjectInterface = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getAdminObjectClass() {
      return this._AdminObjectClass;
   }

   public boolean isAdminObjectClassInherited() {
      return false;
   }

   public boolean isAdminObjectClassSet() {
      return this._isSet(1);
   }

   public void setAdminObjectClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdminObjectClass;
      this._AdminObjectClass = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addAdminObjectInstance(AdminObjectInstanceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         AdminObjectInstanceBean[] _new;
         if (this._isSet(2)) {
            _new = (AdminObjectInstanceBean[])((AdminObjectInstanceBean[])this._getHelper()._extendArray(this.getAdminObjectInstances(), AdminObjectInstanceBean.class, param0));
         } else {
            _new = new AdminObjectInstanceBean[]{param0};
         }

         try {
            this.setAdminObjectInstances(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AdminObjectInstanceBean[] getAdminObjectInstances() {
      return this._AdminObjectInstances;
   }

   public boolean isAdminObjectInstancesInherited() {
      return false;
   }

   public boolean isAdminObjectInstancesSet() {
      return this._isSet(2);
   }

   public void removeAdminObjectInstance(AdminObjectInstanceBean param0) {
      this.destroyAdminObjectInstance(param0);
   }

   public void setAdminObjectInstances(AdminObjectInstanceBean[] param0) throws InvalidAttributeValueException {
      AdminObjectInstanceBean[] param0 = param0 == null ? new AdminObjectInstanceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AdminObjectInstanceBean[] _oldVal = this._AdminObjectInstances;
      this._AdminObjectInstances = (AdminObjectInstanceBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public AdminObjectInstanceBean createAdminObjectInstance() {
      AdminObjectInstanceBeanImpl _val = new AdminObjectInstanceBeanImpl(this, -1);

      try {
         this.addAdminObjectInstance(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAdminObjectInstance(AdminObjectInstanceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         AdminObjectInstanceBean[] _old = this.getAdminObjectInstances();
         AdminObjectInstanceBean[] _new = (AdminObjectInstanceBean[])((AdminObjectInstanceBean[])this._getHelper()._removeElement(_old, AdminObjectInstanceBean.class, param0));
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
               this.setAdminObjectInstances(_new);
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

   public ConfigPropertiesBean getDefaultProperties() {
      return this._DefaultProperties;
   }

   public boolean isDefaultPropertiesInherited() {
      return false;
   }

   public boolean isDefaultPropertiesSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getDefaultProperties());
   }

   public void setDefaultProperties(ConfigPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      ConfigPropertiesBean _oldVal = this._DefaultProperties;
      this._DefaultProperties = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return new CompoundKey(new Object[]{this.getAdminObjectClass(), this.getAdminObjectInterface()});
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
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
      return super._isAnythingSet() || this.isDefaultPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._AdminObjectClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._AdminObjectInstances = new AdminObjectInstanceBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._AdminObjectInterface = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._DefaultProperties = new ConfigPropertiesBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._DefaultProperties);
               if (initOne) {
                  break;
               }
            case 4:
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
                  return 4;
               }
               break;
            case 18:
               if (s.equals("admin-object-class")) {
                  return 1;
               }

               if (s.equals("default-properties")) {
                  return 3;
               }
               break;
            case 21:
               if (s.equals("admin-object-instance")) {
                  return 2;
               }
               break;
            case 22:
               if (s.equals("admin-object-interface")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new AdminObjectInstanceBeanImpl.SchemaHelper2();
            case 3:
               return new ConfigPropertiesBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "admin-object-interface";
            case 1:
               return "admin-object-class";
            case 2:
               return "admin-object-instance";
            case 3:
               return "default-properties";
            case 4:
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
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKeyComponent(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isKeyComponent(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("admin-object-class");
         indices.add("admin-object-interface");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AdminObjectGroupBeanImpl bean;

      protected Helper(AdminObjectGroupBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AdminObjectInterface";
            case 1:
               return "AdminObjectClass";
            case 2:
               return "AdminObjectInstances";
            case 3:
               return "DefaultProperties";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdminObjectClass")) {
            return 1;
         } else if (propName.equals("AdminObjectInstances")) {
            return 2;
         } else if (propName.equals("AdminObjectInterface")) {
            return 0;
         } else if (propName.equals("DefaultProperties")) {
            return 3;
         } else {
            return propName.equals("Id") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAdminObjectInstances()));
         if (this.bean.getDefaultProperties() != null) {
            iterators.add(new ArrayIterator(new ConfigPropertiesBean[]{this.bean.getDefaultProperties()}));
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
            if (this.bean.isAdminObjectClassSet()) {
               buf.append("AdminObjectClass");
               buf.append(String.valueOf(this.bean.getAdminObjectClass()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getAdminObjectInstances().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAdminObjectInstances()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAdminObjectInterfaceSet()) {
               buf.append("AdminObjectInterface");
               buf.append(String.valueOf(this.bean.getAdminObjectInterface()));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultProperties());
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
            AdminObjectGroupBeanImpl otherTyped = (AdminObjectGroupBeanImpl)other;
            this.computeDiff("AdminObjectClass", this.bean.getAdminObjectClass(), otherTyped.getAdminObjectClass(), false);
            this.computeChildDiff("AdminObjectInstances", this.bean.getAdminObjectInstances(), otherTyped.getAdminObjectInstances(), false);
            this.computeDiff("AdminObjectInterface", this.bean.getAdminObjectInterface(), otherTyped.getAdminObjectInterface(), false);
            this.computeSubDiff("DefaultProperties", this.bean.getDefaultProperties(), otherTyped.getDefaultProperties());
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AdminObjectGroupBeanImpl original = (AdminObjectGroupBeanImpl)event.getSourceBean();
            AdminObjectGroupBeanImpl proposed = (AdminObjectGroupBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdminObjectClass")) {
                  original.setAdminObjectClass(proposed.getAdminObjectClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("AdminObjectInstances")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAdminObjectInstance((AdminObjectInstanceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAdminObjectInstance((AdminObjectInstanceBean)update.getRemovedObject());
                  }

                  if (original.getAdminObjectInstances() == null || original.getAdminObjectInstances().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("AdminObjectInterface")) {
                  original.setAdminObjectInterface(proposed.getAdminObjectInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DefaultProperties")) {
                  if (type == 2) {
                     original.setDefaultProperties((ConfigPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DefaultProperties", (DescriptorBean)original.getDefaultProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
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
            AdminObjectGroupBeanImpl copy = (AdminObjectGroupBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdminObjectClass")) && this.bean.isAdminObjectClassSet()) {
               copy.setAdminObjectClass(this.bean.getAdminObjectClass());
            }

            if ((excludeProps == null || !excludeProps.contains("AdminObjectInstances")) && this.bean.isAdminObjectInstancesSet() && !copy._isSet(2)) {
               AdminObjectInstanceBean[] oldAdminObjectInstances = this.bean.getAdminObjectInstances();
               AdminObjectInstanceBean[] newAdminObjectInstances = new AdminObjectInstanceBean[oldAdminObjectInstances.length];

               for(int i = 0; i < newAdminObjectInstances.length; ++i) {
                  newAdminObjectInstances[i] = (AdminObjectInstanceBean)((AdminObjectInstanceBean)this.createCopy((AbstractDescriptorBean)oldAdminObjectInstances[i], includeObsolete));
               }

               copy.setAdminObjectInstances(newAdminObjectInstances);
            }

            if ((excludeProps == null || !excludeProps.contains("AdminObjectInterface")) && this.bean.isAdminObjectInterfaceSet()) {
               copy.setAdminObjectInterface(this.bean.getAdminObjectInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultProperties")) && this.bean.isDefaultPropertiesSet() && !copy._isSet(3)) {
               Object o = this.bean.getDefaultProperties();
               copy.setDefaultProperties((ConfigPropertiesBean)null);
               copy.setDefaultProperties(o == null ? null : (ConfigPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getAdminObjectInstances(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultProperties(), clazz, annotation);
      }
   }
}
