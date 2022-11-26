package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ResourceManagementMBeanImpl extends ConfigurationMBeanImpl implements ResourceManagementMBean, Serializable {
   private ResourceManagerMBean[] _ResourceManagers;
   private static SchemaHelper2 _schemaHelper;

   public ResourceManagementMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ResourceManagementMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ResourceManagementMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addResourceManager(ResourceManagerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         ResourceManagerMBean[] _new;
         if (this._isSet(10)) {
            _new = (ResourceManagerMBean[])((ResourceManagerMBean[])this._getHelper()._extendArray(this.getResourceManagers(), ResourceManagerMBean.class, param0));
         } else {
            _new = new ResourceManagerMBean[]{param0};
         }

         try {
            this.setResourceManagers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceManagerMBean[] getResourceManagers() {
      return this._ResourceManagers;
   }

   public boolean isResourceManagersInherited() {
      return false;
   }

   public boolean isResourceManagersSet() {
      return this._isSet(10);
   }

   public void removeResourceManager(ResourceManagerMBean param0) {
      this.destroyResourceManager(param0);
   }

   public void setResourceManagers(ResourceManagerMBean[] param0) throws InvalidAttributeValueException {
      ResourceManagerMBean[] param0 = param0 == null ? new ResourceManagerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ResourceManagerMBean[] _oldVal = this._ResourceManagers;
      this._ResourceManagers = (ResourceManagerMBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public ResourceManagerMBean lookupResourceManager(String param0) {
      Object[] aary = (Object[])this._ResourceManagers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ResourceManagerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ResourceManagerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ResourceManagerMBean createResourceManager(String param0) {
      ResourceManagerMBeanImpl lookup = (ResourceManagerMBeanImpl)this.lookupResourceManager(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ResourceManagerMBeanImpl _val = new ResourceManagerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addResourceManager(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyResourceManager(ResourceManagerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         ResourceManagerMBean[] _old = this.getResourceManagers();
         ResourceManagerMBean[] _new = (ResourceManagerMBean[])((ResourceManagerMBean[])this._getHelper()._removeElement(_old, ResourceManagerMBean.class, param0));
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
               this.setResourceManagers(_new);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._ResourceManagers = new ResourceManagerMBean[0];
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
      return "ResourceManagement";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ResourceManagers")) {
         ResourceManagerMBean[] oldVal = this._ResourceManagers;
         this._ResourceManagers = (ResourceManagerMBean[])((ResourceManagerMBean[])v);
         this._postSet(10, oldVal, this._ResourceManagers);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      return name.equals("ResourceManagers") ? this._ResourceManagers : super.getValue(name);
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 16:
               if (s.equals("resource-manager")) {
                  return 10;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ResourceManagerMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "resource-manager";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isBean(propIndex);
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
      private ResourceManagementMBeanImpl bean;

      protected Helper(ResourceManagementMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ResourceManagers";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("ResourceManagers") ? 10 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getResourceManagers()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getResourceManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceManagers()[i]);
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
            ResourceManagementMBeanImpl otherTyped = (ResourceManagementMBeanImpl)other;
            this.computeChildDiff("ResourceManagers", this.bean.getResourceManagers(), otherTyped.getResourceManagers(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceManagementMBeanImpl original = (ResourceManagementMBeanImpl)event.getSourceBean();
            ResourceManagementMBeanImpl proposed = (ResourceManagementMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ResourceManagers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceManager((ResourceManagerMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceManager((ResourceManagerMBean)update.getRemovedObject());
                  }

                  if (original.getResourceManagers() == null || original.getResourceManagers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            ResourceManagementMBeanImpl copy = (ResourceManagementMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ResourceManagers")) && this.bean.isResourceManagersSet() && !copy._isSet(10)) {
               ResourceManagerMBean[] oldResourceManagers = this.bean.getResourceManagers();
               ResourceManagerMBean[] newResourceManagers = new ResourceManagerMBean[oldResourceManagers.length];

               for(int i = 0; i < newResourceManagers.length; ++i) {
                  newResourceManagers[i] = (ResourceManagerMBean)((ResourceManagerMBean)this.createCopy((AbstractDescriptorBean)oldResourceManagers[i], includeObsolete));
               }

               copy.setResourceManagers(newResourceManagers);
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
         this.inferSubTree(this.bean.getResourceManagers(), clazz, annotation);
      }
   }
}
