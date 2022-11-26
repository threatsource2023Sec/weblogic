package weblogic.j2ee.descriptor;

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

public class ActivationSpecBeanImpl extends AbstractDescriptorBean implements ActivationSpecBean, Serializable {
   private String _ActivationSpecClass;
   private ConfigPropertyBean[] _ConfigProperties;
   private String _Id;
   private RequiredConfigPropertyBean[] _RequiredConfigProperties;
   private static SchemaHelper2 _schemaHelper;

   public ActivationSpecBeanImpl() {
      this._initializeProperty(-1);
   }

   public ActivationSpecBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ActivationSpecBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getActivationSpecClass() {
      return this._ActivationSpecClass;
   }

   public boolean isActivationSpecClassInherited() {
      return false;
   }

   public boolean isActivationSpecClassSet() {
      return this._isSet(0);
   }

   public void setActivationSpecClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ActivationSpecClass;
      this._ActivationSpecClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addRequiredConfigProperty(RequiredConfigPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         RequiredConfigPropertyBean[] _new;
         if (this._isSet(1)) {
            _new = (RequiredConfigPropertyBean[])((RequiredConfigPropertyBean[])this._getHelper()._extendArray(this.getRequiredConfigProperties(), RequiredConfigPropertyBean.class, param0));
         } else {
            _new = new RequiredConfigPropertyBean[]{param0};
         }

         try {
            this.setRequiredConfigProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RequiredConfigPropertyBean[] getRequiredConfigProperties() {
      return this._RequiredConfigProperties;
   }

   public boolean isRequiredConfigPropertiesInherited() {
      return false;
   }

   public boolean isRequiredConfigPropertiesSet() {
      return this._isSet(1);
   }

   public void removeRequiredConfigProperty(RequiredConfigPropertyBean param0) {
      this.destroyRequiredConfigProperty(param0);
   }

   public void setRequiredConfigProperties(RequiredConfigPropertyBean[] param0) throws InvalidAttributeValueException {
      RequiredConfigPropertyBean[] param0 = param0 == null ? new RequiredConfigPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      RequiredConfigPropertyBean[] _oldVal = this._RequiredConfigProperties;
      this._RequiredConfigProperties = (RequiredConfigPropertyBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public RequiredConfigPropertyBean createRequiredConfigProperty() {
      RequiredConfigPropertyBeanImpl _val = new RequiredConfigPropertyBeanImpl(this, -1);

      try {
         this.addRequiredConfigProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRequiredConfigProperty(RequiredConfigPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         RequiredConfigPropertyBean[] _old = this.getRequiredConfigProperties();
         RequiredConfigPropertyBean[] _new = (RequiredConfigPropertyBean[])((RequiredConfigPropertyBean[])this._getHelper()._removeElement(_old, RequiredConfigPropertyBean.class, param0));
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
               this.setRequiredConfigProperties(_new);
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

   public void addConfigProperty(ConfigPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ConfigPropertyBean[] _new;
         if (this._isSet(2)) {
            _new = (ConfigPropertyBean[])((ConfigPropertyBean[])this._getHelper()._extendArray(this.getConfigProperties(), ConfigPropertyBean.class, param0));
         } else {
            _new = new ConfigPropertyBean[]{param0};
         }

         try {
            this.setConfigProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigPropertyBean[] getConfigProperties() {
      return this._ConfigProperties;
   }

   public boolean isConfigPropertiesInherited() {
      return false;
   }

   public boolean isConfigPropertiesSet() {
      return this._isSet(2);
   }

   public void removeConfigProperty(ConfigPropertyBean param0) {
      this.destroyConfigProperty(param0);
   }

   public void setConfigProperties(ConfigPropertyBean[] param0) throws InvalidAttributeValueException {
      ConfigPropertyBean[] param0 = param0 == null ? new ConfigPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConfigPropertyBean[] _oldVal = this._ConfigProperties;
      this._ConfigProperties = (ConfigPropertyBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ConfigPropertyBean createConfigProperty() {
      ConfigPropertyBeanImpl _val = new ConfigPropertyBeanImpl(this, -1);

      try {
         this.addConfigProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConfigProperty(ConfigPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         ConfigPropertyBean[] _old = this.getConfigProperties();
         ConfigPropertyBean[] _new = (ConfigPropertyBean[])((ConfigPropertyBean[])this._getHelper()._removeElement(_old, ConfigPropertyBean.class, param0));
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
               this.setConfigProperties(_new);
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
               this._ActivationSpecClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ConfigProperties = new ConfigPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RequiredConfigProperties = new RequiredConfigPropertyBean[0];
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
            case 15:
               if (s.equals("config-property")) {
                  return 2;
               }
               break;
            case 20:
               if (s.equals("activationspec-class")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("required-config-property")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new RequiredConfigPropertyBeanImpl.SchemaHelper2();
            case 2:
               return new ConfigPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "activationspec-class";
            case 1:
               return "required-config-property";
            case 2:
               return "config-property";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ActivationSpecBeanImpl bean;

      protected Helper(ActivationSpecBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ActivationSpecClass";
            case 1:
               return "RequiredConfigProperties";
            case 2:
               return "ConfigProperties";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivationSpecClass")) {
            return 0;
         } else if (propName.equals("ConfigProperties")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 3;
         } else {
            return propName.equals("RequiredConfigProperties") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigProperties()));
         iterators.add(new ArrayIterator(this.bean.getRequiredConfigProperties()));
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
            if (this.bean.isActivationSpecClassSet()) {
               buf.append("ActivationSpecClass");
               buf.append(String.valueOf(this.bean.getActivationSpecClass()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getConfigProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRequiredConfigProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRequiredConfigProperties()[i]);
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
            ActivationSpecBeanImpl otherTyped = (ActivationSpecBeanImpl)other;
            this.computeDiff("ActivationSpecClass", this.bean.getActivationSpecClass(), otherTyped.getActivationSpecClass(), false);
            this.computeChildDiff("ConfigProperties", this.bean.getConfigProperties(), otherTyped.getConfigProperties(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("RequiredConfigProperties", this.bean.getRequiredConfigProperties(), otherTyped.getRequiredConfigProperties(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ActivationSpecBeanImpl original = (ActivationSpecBeanImpl)event.getSourceBean();
            ActivationSpecBeanImpl proposed = (ActivationSpecBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActivationSpecClass")) {
                  original.setActivationSpecClass(proposed.getActivationSpecClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ConfigProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigProperty((ConfigPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigProperty((ConfigPropertyBean)update.getRemovedObject());
                  }

                  if (original.getConfigProperties() == null || original.getConfigProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RequiredConfigProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addRequiredConfigProperty((RequiredConfigPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRequiredConfigProperty((RequiredConfigPropertyBean)update.getRemovedObject());
                  }

                  if (original.getRequiredConfigProperties() == null || original.getRequiredConfigProperties().length == 0) {
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
            ActivationSpecBeanImpl copy = (ActivationSpecBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActivationSpecClass")) && this.bean.isActivationSpecClassSet()) {
               copy.setActivationSpecClass(this.bean.getActivationSpecClass());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ConfigProperties")) && this.bean.isConfigPropertiesSet() && !copy._isSet(2)) {
               ConfigPropertyBean[] oldConfigProperties = this.bean.getConfigProperties();
               ConfigPropertyBean[] newConfigProperties = new ConfigPropertyBean[oldConfigProperties.length];

               for(i = 0; i < newConfigProperties.length; ++i) {
                  newConfigProperties[i] = (ConfigPropertyBean)((ConfigPropertyBean)this.createCopy((AbstractDescriptorBean)oldConfigProperties[i], includeObsolete));
               }

               copy.setConfigProperties(newConfigProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("RequiredConfigProperties")) && this.bean.isRequiredConfigPropertiesSet() && !copy._isSet(1)) {
               RequiredConfigPropertyBean[] oldRequiredConfigProperties = this.bean.getRequiredConfigProperties();
               RequiredConfigPropertyBean[] newRequiredConfigProperties = new RequiredConfigPropertyBean[oldRequiredConfigProperties.length];

               for(i = 0; i < newRequiredConfigProperties.length; ++i) {
                  newRequiredConfigProperties[i] = (RequiredConfigPropertyBean)((RequiredConfigPropertyBean)this.createCopy((AbstractDescriptorBean)oldRequiredConfigProperties[i], includeObsolete));
               }

               copy.setRequiredConfigProperties(newRequiredConfigProperties);
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
         this.inferSubTree(this.bean.getConfigProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getRequiredConfigProperties(), clazz, annotation);
      }
   }
}
