package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConfigurationPropertiesMBeanImpl extends ConfigurationMBeanImpl implements ConfigurationPropertiesMBean, Serializable {
   private ConfigurationPropertyMBean[] _ConfigurationProperties;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ConfigurationPropertiesMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ConfigurationPropertiesMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ConfigurationPropertiesMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ConfigurationPropertiesMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ConfigurationPropertiesMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ConfigurationPropertiesMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ConfigurationPropertiesMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConfigurationPropertiesMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConfigurationPropertiesMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addConfigurationProperty(ConfigurationPropertyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         ConfigurationPropertyMBean[] _new;
         if (this._isSet(10)) {
            _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._extendArray(this.getConfigurationProperties(), ConfigurationPropertyMBean.class, param0));
         } else {
            _new = new ConfigurationPropertyMBean[]{param0};
         }

         try {
            this.setConfigurationProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigurationPropertyMBean[] getConfigurationProperties() {
      ConfigurationPropertyMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         delegateArray = this._getDelegateBean().getConfigurationProperties();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._ConfigurationProperties.length; ++j) {
               if (delegateArray[i].getName().equals(this._ConfigurationProperties[j].getName())) {
                  ((ConfigurationPropertyMBeanImpl)this._ConfigurationProperties[j])._setDelegateBean((ConfigurationPropertyMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  ConfigurationPropertyMBeanImpl mbean = new ConfigurationPropertyMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 10);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((ConfigurationPropertyMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(10)) {
                     this.setConfigurationProperties((ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._extendArray(this._ConfigurationProperties, ConfigurationPropertyMBean.class, mbean)));
                  } else {
                     this.setConfigurationProperties(new ConfigurationPropertyMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new ConfigurationPropertyMBean[0];
      }

      if (this._ConfigurationProperties != null) {
         List removeList = new ArrayList();
         ConfigurationPropertyMBean[] var18 = this._ConfigurationProperties;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            ConfigurationPropertyMBean bn = var18[var5];
            ConfigurationPropertyMBeanImpl bni = (ConfigurationPropertyMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               ConfigurationPropertyMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  ConfigurationPropertyMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            ConfigurationPropertyMBean removeIt = (ConfigurationPropertyMBean)var19.next();
            ConfigurationPropertyMBeanImpl removeItImpl = (ConfigurationPropertyMBeanImpl)removeIt;
            ConfigurationPropertyMBean[] _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._removeElement(this._ConfigurationProperties, ConfigurationPropertyMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setConfigurationProperties(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._ConfigurationProperties;
   }

   public boolean isConfigurationPropertiesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         ConfigurationPropertyMBean[] elements = this.getConfigurationProperties();
         ConfigurationPropertyMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isConfigurationPropertiesSet() {
      return this._isSet(10);
   }

   public void removeConfigurationProperty(ConfigurationPropertyMBean param0) {
      this.destroyConfigurationProperty(param0);
   }

   public void setConfigurationProperties(ConfigurationPropertyMBean[] param0) throws InvalidAttributeValueException {
      ConfigurationPropertyMBean[] param0 = param0 == null ? new ConfigurationPropertyMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._ConfigurationProperties, (Object[])param0, handler, new Comparator() {
            public int compare(ConfigurationPropertyMBean o1, ConfigurationPropertyMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            ConfigurationPropertyMBean bean = (ConfigurationPropertyMBean)var3.next();
            ConfigurationPropertyMBeanImpl beanImpl = (ConfigurationPropertyMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(10);
      ConfigurationPropertyMBean[] _oldVal = this._ConfigurationProperties;
      this._ConfigurationProperties = (ConfigurationPropertyMBean[])param0;
      this._postSet(10, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         ConfigurationPropertiesMBeanImpl source = (ConfigurationPropertiesMBeanImpl)var11.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public ConfigurationPropertyMBean lookupConfigurationProperty(String param0) {
      Object[] aary = (Object[])this.getConfigurationProperties();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ConfigurationPropertyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ConfigurationPropertyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ConfigurationPropertyMBean createConfigurationProperty(String param0) {
      ConfigurationPropertyMBeanImpl lookup = (ConfigurationPropertyMBeanImpl)this.lookupConfigurationProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ConfigurationPropertyMBeanImpl _val = new ConfigurationPropertyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addConfigurationProperty(_val);
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

   public void destroyConfigurationProperty(ConfigurationPropertyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         ConfigurationPropertyMBean[] _old = this.getConfigurationProperties();
         ConfigurationPropertyMBean[] _new = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])this._getHelper()._removeElement(_old, ConfigurationPropertyMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  ConfigurationPropertiesMBeanImpl source = (ConfigurationPropertiesMBeanImpl)var6.next();
                  ConfigurationPropertyMBeanImpl childImpl = (ConfigurationPropertyMBeanImpl)_child;
                  ConfigurationPropertyMBeanImpl lookup = (ConfigurationPropertyMBeanImpl)source.lookupConfigurationProperty(childImpl.getName());
                  if (lookup != null) {
                     source.destroyConfigurationProperty(lookup);
                  }
               }

               this.setConfigurationProperties(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
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
               this._ConfigurationProperties = new ConfigurationPropertyMBean[0];
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
      return "ConfigurationProperties";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ConfigurationProperties")) {
         ConfigurationPropertyMBean[] oldVal = this._ConfigurationProperties;
         this._ConfigurationProperties = (ConfigurationPropertyMBean[])((ConfigurationPropertyMBean[])v);
         this._postSet(10, oldVal, this._ConfigurationProperties);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      return name.equals("ConfigurationProperties") ? this._ConfigurationProperties : super.getValue(name);
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 22:
               if (s.equals("configuration-property")) {
                  return 10;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "configuration-property";
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
      private ConfigurationPropertiesMBeanImpl bean;

      protected Helper(ConfigurationPropertiesMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ConfigurationProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("ConfigurationProperties") ? 10 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
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

            for(int i = 0; i < this.bean.getConfigurationProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigurationProperties()[i]);
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
            ConfigurationPropertiesMBeanImpl otherTyped = (ConfigurationPropertiesMBeanImpl)other;
            this.computeChildDiff("ConfigurationProperties", this.bean.getConfigurationProperties(), otherTyped.getConfigurationProperties(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigurationPropertiesMBeanImpl original = (ConfigurationPropertiesMBeanImpl)event.getSourceBean();
            ConfigurationPropertiesMBeanImpl proposed = (ConfigurationPropertiesMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConfigurationProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigurationProperty((ConfigurationPropertyMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigurationProperty((ConfigurationPropertyMBean)update.getRemovedObject());
                  }

                  if (original.getConfigurationProperties() == null || original.getConfigurationProperties().length == 0) {
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
            ConfigurationPropertiesMBeanImpl copy = (ConfigurationPropertiesMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConfigurationProperties")) && this.bean.isConfigurationPropertiesSet() && !copy._isSet(10)) {
               ConfigurationPropertyMBean[] oldConfigurationProperties = this.bean.getConfigurationProperties();
               ConfigurationPropertyMBean[] newConfigurationProperties = new ConfigurationPropertyMBean[oldConfigurationProperties.length];

               for(int i = 0; i < newConfigurationProperties.length; ++i) {
                  newConfigurationProperties[i] = (ConfigurationPropertyMBean)((ConfigurationPropertyMBean)this.createCopy((AbstractDescriptorBean)oldConfigurationProperties[i], includeObsolete));
               }

               copy.setConfigurationProperties(newConfigurationProperties);
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
         this.inferSubTree(this.bean.getConfigurationProperties(), clazz, annotation);
      }
   }
}
