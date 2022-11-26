package weblogic.diagnostics.descriptor;

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

public class WLDFConfigurationPropertiesBeanImpl extends WLDFConfigurationPropertyBeanImpl implements WLDFConfigurationPropertiesBean, Serializable {
   private WLDFConfigurationPropertyBean[] _ConfigurationProperties;
   private WLDFEncryptedPropertyBean[] _EncryptedProperties;
   private WLDFPropertyBean[] _Properties;
   private transient ConfigurationPropertiesCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WLDFConfigurationPropertiesBeanImpl() {
      try {
         this._customizer = new ConfigurationPropertiesCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WLDFConfigurationPropertiesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ConfigurationPropertiesCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WLDFConfigurationPropertiesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ConfigurationPropertiesCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public WLDFPropertyBean createProperty(String param0) {
      WLDFPropertyBeanImpl lookup = (WLDFPropertyBeanImpl)this.lookupProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFPropertyBeanImpl _val = new WLDFPropertyBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addProperty(_val);
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

   public void addProperty(WLDFPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         WLDFPropertyBean[] _new;
         if (this._isSet(2)) {
            _new = (WLDFPropertyBean[])((WLDFPropertyBean[])this._getHelper()._extendArray(this.getProperties(), WLDFPropertyBean.class, param0));
         } else {
            _new = new WLDFPropertyBean[]{param0};
         }

         try {
            this.setProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFPropertyBean[] getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(2);
   }

   public void removeProperty(WLDFPropertyBean param0) {
      this.destroyProperty(param0);
   }

   public void setProperties(WLDFPropertyBean[] param0) throws InvalidAttributeValueException {
      WLDFPropertyBean[] param0 = param0 == null ? new WLDFPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFPropertyBean[] _oldVal = this._Properties;
      this._Properties = (WLDFPropertyBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public WLDFPropertyBean lookupProperty(String param0) {
      Object[] aary = (Object[])this._Properties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyProperty(WLDFPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         WLDFPropertyBean[] _old = this.getProperties();
         WLDFPropertyBean[] _new = (WLDFPropertyBean[])((WLDFPropertyBean[])this._getHelper()._removeElement(_old, WLDFPropertyBean.class, param0));
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
               this.setProperties(_new);
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

   public WLDFEncryptedPropertyBean createEncryptedProperty(String param0) {
      WLDFEncryptedPropertyBeanImpl lookup = (WLDFEncryptedPropertyBeanImpl)this.lookupEncryptedProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFEncryptedPropertyBeanImpl _val = new WLDFEncryptedPropertyBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addEncryptedProperty(_val);
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

   public void addEncryptedProperty(WLDFEncryptedPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         WLDFEncryptedPropertyBean[] _new;
         if (this._isSet(3)) {
            _new = (WLDFEncryptedPropertyBean[])((WLDFEncryptedPropertyBean[])this._getHelper()._extendArray(this.getEncryptedProperties(), WLDFEncryptedPropertyBean.class, param0));
         } else {
            _new = new WLDFEncryptedPropertyBean[]{param0};
         }

         try {
            this.setEncryptedProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFEncryptedPropertyBean[] getEncryptedProperties() {
      return this._EncryptedProperties;
   }

   public boolean isEncryptedPropertiesInherited() {
      return false;
   }

   public boolean isEncryptedPropertiesSet() {
      return this._isSet(3);
   }

   public void removeEncryptedProperty(WLDFEncryptedPropertyBean param0) {
      this.destroyEncryptedProperty(param0);
   }

   public void setEncryptedProperties(WLDFEncryptedPropertyBean[] param0) throws InvalidAttributeValueException {
      WLDFEncryptedPropertyBean[] param0 = param0 == null ? new WLDFEncryptedPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFEncryptedPropertyBean[] _oldVal = this._EncryptedProperties;
      this._EncryptedProperties = (WLDFEncryptedPropertyBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public WLDFEncryptedPropertyBean lookupEncryptedProperty(String param0) {
      Object[] aary = (Object[])this._EncryptedProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFEncryptedPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFEncryptedPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyEncryptedProperty(WLDFEncryptedPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         WLDFEncryptedPropertyBean[] _old = this.getEncryptedProperties();
         WLDFEncryptedPropertyBean[] _new = (WLDFEncryptedPropertyBean[])((WLDFEncryptedPropertyBean[])this._getHelper()._removeElement(_old, WLDFEncryptedPropertyBean.class, param0));
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
               this.setEncryptedProperties(_new);
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

   public void addConfigurationProperty(WLDFConfigurationPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         WLDFConfigurationPropertyBean[] _new = (WLDFConfigurationPropertyBean[])((WLDFConfigurationPropertyBean[])this._getHelper()._extendArray(this.getConfigurationProperties(), WLDFConfigurationPropertyBean.class, param0));

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

   public WLDFConfigurationPropertyBean[] getConfigurationProperties() {
      return this._customizer.getConfigurationProperties();
   }

   public boolean isConfigurationPropertiesInherited() {
      return false;
   }

   public boolean isConfigurationPropertiesSet() {
      return this._isSet(4);
   }

   public void removeConfigurationProperty(WLDFConfigurationPropertyBean param0) {
      WLDFConfigurationPropertyBean[] _old = this.getConfigurationProperties();
      WLDFConfigurationPropertyBean[] _new = (WLDFConfigurationPropertyBean[])((WLDFConfigurationPropertyBean[])this._getHelper()._removeElement(_old, WLDFConfigurationPropertyBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setConfigurationProperties(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setConfigurationProperties(WLDFConfigurationPropertyBean[] param0) throws InvalidAttributeValueException {
      WLDFConfigurationPropertyBean[] param0 = param0 == null ? new WLDFConfigurationPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._postCreate(_child);
         }
      }

      WLDFConfigurationPropertyBean[] _oldVal = this._ConfigurationProperties;
      this._ConfigurationProperties = (WLDFConfigurationPropertyBean[])param0;
      this._postSet(4, _oldVal, param0);
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
      return super._isAnythingSet() || this.isConfigurationPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ConfigurationProperties = new WLDFConfigurationPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._EncryptedProperties = new WLDFEncryptedPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Properties = new WLDFPropertyBean[0];
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/2.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFConfigurationPropertyBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("property")) {
                  return 2;
               }
               break;
            case 18:
               if (s.equals("encrypted-property")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("configuration-property")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new WLDFPropertyBeanImpl.SchemaHelper2();
            case 3:
               return new WLDFEncryptedPropertyBeanImpl.SchemaHelper2();
            case 4:
               return new WLDFConfigurationPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "property";
            case 3:
               return "encrypted-property";
            case 4:
               return "configuration-property";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
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
            case 4:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WLDFConfigurationPropertyBeanImpl.Helper {
      private WLDFConfigurationPropertiesBeanImpl bean;

      protected Helper(WLDFConfigurationPropertiesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Properties";
            case 3:
               return "EncryptedProperties";
            case 4:
               return "ConfigurationProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConfigurationProperties")) {
            return 4;
         } else if (propName.equals("EncryptedProperties")) {
            return 3;
         } else {
            return propName.equals("Properties") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         iterators.add(new ArrayIterator(this.bean.getEncryptedProperties()));
         iterators.add(new ArrayIterator(this.bean.getProperties()));
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

            int i;
            for(i = 0; i < this.bean.getConfigurationProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigurationProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEncryptedProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEncryptedProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getProperties()[i]);
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
            WLDFConfigurationPropertiesBeanImpl otherTyped = (WLDFConfigurationPropertiesBeanImpl)other;
            this.computeChildDiff("ConfigurationProperties", this.bean.getConfigurationProperties(), otherTyped.getConfigurationProperties(), true);
            this.computeChildDiff("EncryptedProperties", this.bean.getEncryptedProperties(), otherTyped.getEncryptedProperties(), true);
            this.computeChildDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFConfigurationPropertiesBeanImpl original = (WLDFConfigurationPropertiesBeanImpl)event.getSourceBean();
            WLDFConfigurationPropertiesBeanImpl proposed = (WLDFConfigurationPropertiesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConfigurationProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigurationProperty((WLDFConfigurationPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigurationProperty((WLDFConfigurationPropertyBean)update.getRemovedObject());
                  }

                  if (original.getConfigurationProperties() == null || original.getConfigurationProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("EncryptedProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEncryptedProperty((WLDFEncryptedPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEncryptedProperty((WLDFEncryptedPropertyBean)update.getRemovedObject());
                  }

                  if (original.getEncryptedProperties() == null || original.getEncryptedProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addProperty((WLDFPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeProperty((WLDFPropertyBean)update.getRemovedObject());
                  }

                  if (original.getProperties() == null || original.getProperties().length == 0) {
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
            WLDFConfigurationPropertiesBeanImpl copy = (WLDFConfigurationPropertiesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ConfigurationProperties")) && this.bean.isConfigurationPropertiesSet() && !copy._isSet(4)) {
               WLDFConfigurationPropertyBean[] oldConfigurationProperties = this.bean.getConfigurationProperties();
               WLDFConfigurationPropertyBean[] newConfigurationProperties = new WLDFConfigurationPropertyBean[oldConfigurationProperties.length];

               for(i = 0; i < newConfigurationProperties.length; ++i) {
                  newConfigurationProperties[i] = (WLDFConfigurationPropertyBean)((WLDFConfigurationPropertyBean)this.createCopy((AbstractDescriptorBean)oldConfigurationProperties[i], includeObsolete));
               }

               copy.setConfigurationProperties(newConfigurationProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("EncryptedProperties")) && this.bean.isEncryptedPropertiesSet() && !copy._isSet(3)) {
               WLDFEncryptedPropertyBean[] oldEncryptedProperties = this.bean.getEncryptedProperties();
               WLDFEncryptedPropertyBean[] newEncryptedProperties = new WLDFEncryptedPropertyBean[oldEncryptedProperties.length];

               for(i = 0; i < newEncryptedProperties.length; ++i) {
                  newEncryptedProperties[i] = (WLDFEncryptedPropertyBean)((WLDFEncryptedPropertyBean)this.createCopy((AbstractDescriptorBean)oldEncryptedProperties[i], includeObsolete));
               }

               copy.setEncryptedProperties(newEncryptedProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(2)) {
               WLDFPropertyBean[] oldProperties = this.bean.getProperties();
               WLDFPropertyBean[] newProperties = new WLDFPropertyBean[oldProperties.length];

               for(i = 0; i < newProperties.length; ++i) {
                  newProperties[i] = (WLDFPropertyBean)((WLDFPropertyBean)this.createCopy((AbstractDescriptorBean)oldProperties[i], includeObsolete));
               }

               copy.setProperties(newProperties);
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
         this.inferSubTree(this.bean.getEncryptedProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}
