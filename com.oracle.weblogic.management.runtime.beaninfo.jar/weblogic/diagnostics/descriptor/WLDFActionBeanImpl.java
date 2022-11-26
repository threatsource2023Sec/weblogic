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
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFActionBeanImpl extends WLDFNotificationBeanImpl implements WLDFActionBean, Serializable {
   private WLDFArrayPropertyBean[] _ArrayProperties;
   private WLDFConfigurationPropertyBean[] _ConfigurationProperties;
   private WLDFEncryptedPropertyBean[] _EncryptedProperties;
   private WLDFConfigurationPropertiesBean[] _MapProperties;
   private WLDFPropertyBean[] _Properties;
   private String _Type;
   private transient ActionBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WLDFActionBeanImpl() {
      try {
         this._customizer = new ActionBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WLDFActionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ActionBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WLDFActionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ActionBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getType() {
      return this._Type;
   }

   public boolean isTypeInherited() {
      return false;
   }

   public boolean isTypeSet() {
      return this._isSet(4);
   }

   public void setType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Type;
      this._Type = param0;
      this._postSet(4, _oldVal, param0);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         WLDFPropertyBean[] _new;
         if (this._isSet(5)) {
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
      return this._isSet(5);
   }

   public void removeProperty(WLDFPropertyBean param0) {
      this.destroyProperty(param0);
   }

   public void setProperties(WLDFPropertyBean[] param0) throws InvalidAttributeValueException {
      WLDFPropertyBean[] param0 = param0 == null ? new WLDFPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFPropertyBean[] _oldVal = this._Properties;
      this._Properties = (WLDFPropertyBean[])param0;
      this._postSet(5, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 5);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         WLDFEncryptedPropertyBean[] _new;
         if (this._isSet(6)) {
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
      return this._isSet(6);
   }

   public void removeEncryptedProperty(WLDFEncryptedPropertyBean param0) {
      this.destroyEncryptedProperty(param0);
   }

   public void setEncryptedProperties(WLDFEncryptedPropertyBean[] param0) throws InvalidAttributeValueException {
      WLDFEncryptedPropertyBean[] param0 = param0 == null ? new WLDFEncryptedPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFEncryptedPropertyBean[] _oldVal = this._EncryptedProperties;
      this._EncryptedProperties = (WLDFEncryptedPropertyBean[])param0;
      this._postSet(6, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 6);
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

   public WLDFConfigurationPropertiesBean createMapProperty(String param0) {
      WLDFConfigurationPropertiesBeanImpl lookup = (WLDFConfigurationPropertiesBeanImpl)this.lookupMapProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFConfigurationPropertiesBeanImpl _val = new WLDFConfigurationPropertiesBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMapProperty(_val);
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

   public WLDFConfigurationPropertiesBean lookupMapProperty(String param0) {
      Object[] aary = (Object[])this._MapProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFConfigurationPropertiesBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFConfigurationPropertiesBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMapProperty(WLDFConfigurationPropertiesBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         WLDFConfigurationPropertiesBean[] _new;
         if (this._isSet(7)) {
            _new = (WLDFConfigurationPropertiesBean[])((WLDFConfigurationPropertiesBean[])this._getHelper()._extendArray(this.getMapProperties(), WLDFConfigurationPropertiesBean.class, param0));
         } else {
            _new = new WLDFConfigurationPropertiesBean[]{param0};
         }

         try {
            this.setMapProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFConfigurationPropertiesBean[] getMapProperties() {
      return this._MapProperties;
   }

   public boolean isMapPropertiesInherited() {
      return false;
   }

   public boolean isMapPropertiesSet() {
      return this._isSet(7);
   }

   public void removeMapProperty(WLDFConfigurationPropertiesBean param0) {
      this.destroyMapProperty(param0);
   }

   public void setMapProperties(WLDFConfigurationPropertiesBean[] param0) throws InvalidAttributeValueException {
      WLDFConfigurationPropertiesBean[] param0 = param0 == null ? new WLDFConfigurationPropertiesBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFConfigurationPropertiesBean[] _oldVal = this._MapProperties;
      this._MapProperties = (WLDFConfigurationPropertiesBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public void destroyMapProperty(WLDFConfigurationPropertiesBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         WLDFConfigurationPropertiesBean[] _old = this.getMapProperties();
         WLDFConfigurationPropertiesBean[] _new = (WLDFConfigurationPropertiesBean[])((WLDFConfigurationPropertiesBean[])this._getHelper()._removeElement(_old, WLDFConfigurationPropertiesBean.class, param0));
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
               this.setMapProperties(_new);
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

   public WLDFArrayPropertyBean createArrayProperty(String param0) {
      WLDFArrayPropertyBeanImpl lookup = (WLDFArrayPropertyBeanImpl)this.lookupArrayProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFArrayPropertyBeanImpl _val = new WLDFArrayPropertyBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addArrayProperty(_val);
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

   public void addArrayProperty(WLDFArrayPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         WLDFArrayPropertyBean[] _new;
         if (this._isSet(8)) {
            _new = (WLDFArrayPropertyBean[])((WLDFArrayPropertyBean[])this._getHelper()._extendArray(this.getArrayProperties(), WLDFArrayPropertyBean.class, param0));
         } else {
            _new = new WLDFArrayPropertyBean[]{param0};
         }

         try {
            this.setArrayProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFArrayPropertyBean[] getArrayProperties() {
      return this._ArrayProperties;
   }

   public boolean isArrayPropertiesInherited() {
      return false;
   }

   public boolean isArrayPropertiesSet() {
      return this._isSet(8);
   }

   public void removeArrayProperty(WLDFArrayPropertyBean param0) {
      this.destroyArrayProperty(param0);
   }

   public void setArrayProperties(WLDFArrayPropertyBean[] param0) throws InvalidAttributeValueException {
      WLDFArrayPropertyBean[] param0 = param0 == null ? new WLDFArrayPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WLDFArrayPropertyBean[] _oldVal = this._ArrayProperties;
      this._ArrayProperties = (WLDFArrayPropertyBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public WLDFArrayPropertyBean lookupArrayProperty(String param0) {
      Object[] aary = (Object[])this._ArrayProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFArrayPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFArrayPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyArrayProperty(WLDFArrayPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         WLDFArrayPropertyBean[] _old = this.getArrayProperties();
         WLDFArrayPropertyBean[] _new = (WLDFArrayPropertyBean[])((WLDFArrayPropertyBean[])this._getHelper()._removeElement(_old, WLDFArrayPropertyBean.class, param0));
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
               this.setArrayProperties(_new);
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
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
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
      return this._isSet(9);
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
         if (this._setParent(_child, this, 9)) {
            this._postCreate(_child);
         }
      }

      WLDFConfigurationPropertyBean[] _oldVal = this._ConfigurationProperties;
      this._ConfigurationProperties = (WLDFConfigurationPropertyBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public WLDFConfigurationPropertyBean lookupConfigurationProperty(String param0) {
      Object[] aary = (Object[])this._ConfigurationProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFConfigurationPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFConfigurationPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WatchNotificationValidators.validateActionBean(this);
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._ArrayProperties = new WLDFArrayPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._ConfigurationProperties = new WLDFConfigurationPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._EncryptedProperties = new WLDFEncryptedPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._MapProperties = new WLDFConfigurationPropertiesBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Properties = new WLDFPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Type = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
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

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("type")) {
                  return 4;
               }
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            default:
               break;
            case 8:
               if (s.equals("property")) {
                  return 5;
               }
               break;
            case 12:
               if (s.equals("map-property")) {
                  return 7;
               }
               break;
            case 14:
               if (s.equals("array-property")) {
                  return 8;
               }
               break;
            case 18:
               if (s.equals("encrypted-property")) {
                  return 6;
               }
               break;
            case 22:
               if (s.equals("configuration-property")) {
                  return 9;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new WLDFPropertyBeanImpl.SchemaHelper2();
            case 6:
               return new WLDFEncryptedPropertyBeanImpl.SchemaHelper2();
            case 7:
               return new WLDFConfigurationPropertiesBeanImpl.SchemaHelper2();
            case 8:
               return new WLDFArrayPropertyBeanImpl.SchemaHelper2();
            case 9:
               return new WLDFConfigurationPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "type";
            case 5:
               return "property";
            case 6:
               return "encrypted-property";
            case 7:
               return "map-property";
            case 8:
               return "array-property";
            case 9:
               return "configuration-property";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
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

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFActionBeanImpl bean;

      protected Helper(WLDFActionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "Type";
            case 5:
               return "Properties";
            case 6:
               return "EncryptedProperties";
            case 7:
               return "MapProperties";
            case 8:
               return "ArrayProperties";
            case 9:
               return "ConfigurationProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ArrayProperties")) {
            return 8;
         } else if (propName.equals("ConfigurationProperties")) {
            return 9;
         } else if (propName.equals("EncryptedProperties")) {
            return 6;
         } else if (propName.equals("MapProperties")) {
            return 7;
         } else if (propName.equals("Properties")) {
            return 5;
         } else {
            return propName.equals("Type") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getArrayProperties()));
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         iterators.add(new ArrayIterator(this.bean.getEncryptedProperties()));
         iterators.add(new ArrayIterator(this.bean.getMapProperties()));
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
            for(i = 0; i < this.bean.getArrayProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getArrayProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

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

            for(i = 0; i < this.bean.getMapProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMapProperties()[i]);
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

            if (this.bean.isTypeSet()) {
               buf.append("Type");
               buf.append(String.valueOf(this.bean.getType()));
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
            WLDFActionBeanImpl otherTyped = (WLDFActionBeanImpl)other;
            this.computeChildDiff("ArrayProperties", this.bean.getArrayProperties(), otherTyped.getArrayProperties(), true);
            this.computeChildDiff("ConfigurationProperties", this.bean.getConfigurationProperties(), otherTyped.getConfigurationProperties(), true);
            this.computeChildDiff("EncryptedProperties", this.bean.getEncryptedProperties(), otherTyped.getEncryptedProperties(), true);
            this.computeChildDiff("MapProperties", this.bean.getMapProperties(), otherTyped.getMapProperties(), true);
            this.computeChildDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), true);
            this.computeDiff("Type", this.bean.getType(), otherTyped.getType(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFActionBeanImpl original = (WLDFActionBeanImpl)event.getSourceBean();
            WLDFActionBeanImpl proposed = (WLDFActionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ArrayProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addArrayProperty((WLDFArrayPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeArrayProperty((WLDFArrayPropertyBean)update.getRemovedObject());
                  }

                  if (original.getArrayProperties() == null || original.getArrayProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("ConfigurationProperties")) {
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
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("MapProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMapProperty((WLDFConfigurationPropertiesBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMapProperty((WLDFConfigurationPropertiesBean)update.getRemovedObject());
                  }

                  if (original.getMapProperties() == null || original.getMapProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("Type")) {
                  original.setType(proposed.getType());
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
            WLDFActionBeanImpl copy = (WLDFActionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ArrayProperties")) && this.bean.isArrayPropertiesSet() && !copy._isSet(8)) {
               WLDFArrayPropertyBean[] oldArrayProperties = this.bean.getArrayProperties();
               WLDFArrayPropertyBean[] newArrayProperties = new WLDFArrayPropertyBean[oldArrayProperties.length];

               for(i = 0; i < newArrayProperties.length; ++i) {
                  newArrayProperties[i] = (WLDFArrayPropertyBean)((WLDFArrayPropertyBean)this.createCopy((AbstractDescriptorBean)oldArrayProperties[i], includeObsolete));
               }

               copy.setArrayProperties(newArrayProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("ConfigurationProperties")) && this.bean.isConfigurationPropertiesSet() && !copy._isSet(9)) {
               WLDFConfigurationPropertyBean[] oldConfigurationProperties = this.bean.getConfigurationProperties();
               WLDFConfigurationPropertyBean[] newConfigurationProperties = new WLDFConfigurationPropertyBean[oldConfigurationProperties.length];

               for(i = 0; i < newConfigurationProperties.length; ++i) {
                  newConfigurationProperties[i] = (WLDFConfigurationPropertyBean)((WLDFConfigurationPropertyBean)this.createCopy((AbstractDescriptorBean)oldConfigurationProperties[i], includeObsolete));
               }

               copy.setConfigurationProperties(newConfigurationProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("EncryptedProperties")) && this.bean.isEncryptedPropertiesSet() && !copy._isSet(6)) {
               WLDFEncryptedPropertyBean[] oldEncryptedProperties = this.bean.getEncryptedProperties();
               WLDFEncryptedPropertyBean[] newEncryptedProperties = new WLDFEncryptedPropertyBean[oldEncryptedProperties.length];

               for(i = 0; i < newEncryptedProperties.length; ++i) {
                  newEncryptedProperties[i] = (WLDFEncryptedPropertyBean)((WLDFEncryptedPropertyBean)this.createCopy((AbstractDescriptorBean)oldEncryptedProperties[i], includeObsolete));
               }

               copy.setEncryptedProperties(newEncryptedProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("MapProperties")) && this.bean.isMapPropertiesSet() && !copy._isSet(7)) {
               WLDFConfigurationPropertiesBean[] oldMapProperties = this.bean.getMapProperties();
               WLDFConfigurationPropertiesBean[] newMapProperties = new WLDFConfigurationPropertiesBean[oldMapProperties.length];

               for(i = 0; i < newMapProperties.length; ++i) {
                  newMapProperties[i] = (WLDFConfigurationPropertiesBean)((WLDFConfigurationPropertiesBean)this.createCopy((AbstractDescriptorBean)oldMapProperties[i], includeObsolete));
               }

               copy.setMapProperties(newMapProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(5)) {
               WLDFPropertyBean[] oldProperties = this.bean.getProperties();
               WLDFPropertyBean[] newProperties = new WLDFPropertyBean[oldProperties.length];

               for(i = 0; i < newProperties.length; ++i) {
                  newProperties[i] = (WLDFPropertyBean)((WLDFPropertyBean)this.createCopy((AbstractDescriptorBean)oldProperties[i], includeObsolete));
               }

               copy.setProperties(newProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("Type")) && this.bean.isTypeSet()) {
               copy.setType(this.bean.getType());
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
         this.inferSubTree(this.bean.getArrayProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getConfigurationProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getEncryptedProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getMapProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}
