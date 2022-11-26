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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ForeignServerOverrideMBeanImpl extends ConfigurationMBeanImpl implements ForeignServerOverrideMBean, Serializable {
   private String _ConnectionURL;
   private ForeignConnectionFactoryOverrideMBean[] _ForeignConnectionFactories;
   private ForeignDestinationOverrideMBean[] _ForeignDestinations;
   private String _InitialContextFactory;
   private PartitionPropertyMBean[] _JNDIProperties;
   private String _JNDIPropertiesCredential;
   private byte[] _JNDIPropertiesCredentialEncrypted;
   private static SchemaHelper2 _schemaHelper;

   public ForeignServerOverrideMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ForeignServerOverrideMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ForeignServerOverrideMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addForeignDestination(ForeignDestinationOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         ForeignDestinationOverrideMBean[] _new;
         if (this._isSet(10)) {
            _new = (ForeignDestinationOverrideMBean[])((ForeignDestinationOverrideMBean[])this._getHelper()._extendArray(this.getForeignDestinations(), ForeignDestinationOverrideMBean.class, param0));
         } else {
            _new = new ForeignDestinationOverrideMBean[]{param0};
         }

         try {
            this.setForeignDestinations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignDestinationOverrideMBean[] getForeignDestinations() {
      return this._ForeignDestinations;
   }

   public boolean isForeignDestinationsInherited() {
      return false;
   }

   public boolean isForeignDestinationsSet() {
      return this._isSet(10);
   }

   public void removeForeignDestination(ForeignDestinationOverrideMBean param0) {
      this.destroyForeignDestination(param0);
   }

   public void setForeignDestinations(ForeignDestinationOverrideMBean[] param0) throws InvalidAttributeValueException {
      ForeignDestinationOverrideMBean[] param0 = param0 == null ? new ForeignDestinationOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignDestinationOverrideMBean[] _oldVal = this._ForeignDestinations;
      this._ForeignDestinations = (ForeignDestinationOverrideMBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public ForeignDestinationOverrideMBean createForeignDestination(String param0) {
      ForeignDestinationOverrideMBeanImpl lookup = (ForeignDestinationOverrideMBeanImpl)this.lookupForeignDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignDestinationOverrideMBeanImpl _val = new ForeignDestinationOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignDestination(_val);
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

   public void destroyForeignDestination(ForeignDestinationOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         ForeignDestinationOverrideMBean[] _old = this.getForeignDestinations();
         ForeignDestinationOverrideMBean[] _new = (ForeignDestinationOverrideMBean[])((ForeignDestinationOverrideMBean[])this._getHelper()._removeElement(_old, ForeignDestinationOverrideMBean.class, param0));
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
               this.setForeignDestinations(_new);
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

   public ForeignDestinationOverrideMBean lookupForeignDestination(String param0) {
      Object[] aary = (Object[])this._ForeignDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignDestinationOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignDestinationOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addForeignConnectionFactory(ForeignConnectionFactoryOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ForeignConnectionFactoryOverrideMBean[] _new;
         if (this._isSet(11)) {
            _new = (ForeignConnectionFactoryOverrideMBean[])((ForeignConnectionFactoryOverrideMBean[])this._getHelper()._extendArray(this.getForeignConnectionFactories(), ForeignConnectionFactoryOverrideMBean.class, param0));
         } else {
            _new = new ForeignConnectionFactoryOverrideMBean[]{param0};
         }

         try {
            this.setForeignConnectionFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ForeignConnectionFactoryOverrideMBean[] getForeignConnectionFactories() {
      return this._ForeignConnectionFactories;
   }

   public boolean isForeignConnectionFactoriesInherited() {
      return false;
   }

   public boolean isForeignConnectionFactoriesSet() {
      return this._isSet(11);
   }

   public void removeForeignConnectionFactory(ForeignConnectionFactoryOverrideMBean param0) {
      this.destroyForeignConnectionFactory(param0);
   }

   public void setForeignConnectionFactories(ForeignConnectionFactoryOverrideMBean[] param0) throws InvalidAttributeValueException {
      ForeignConnectionFactoryOverrideMBean[] param0 = param0 == null ? new ForeignConnectionFactoryOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignConnectionFactoryOverrideMBean[] _oldVal = this._ForeignConnectionFactories;
      this._ForeignConnectionFactories = (ForeignConnectionFactoryOverrideMBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public ForeignConnectionFactoryOverrideMBean createForeignConnectionFactory(String param0) {
      ForeignConnectionFactoryOverrideMBeanImpl lookup = (ForeignConnectionFactoryOverrideMBeanImpl)this.lookupForeignConnectionFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignConnectionFactoryOverrideMBeanImpl _val = new ForeignConnectionFactoryOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignConnectionFactory(_val);
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

   public void destroyForeignConnectionFactory(ForeignConnectionFactoryOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         ForeignConnectionFactoryOverrideMBean[] _old = this.getForeignConnectionFactories();
         ForeignConnectionFactoryOverrideMBean[] _new = (ForeignConnectionFactoryOverrideMBean[])((ForeignConnectionFactoryOverrideMBean[])this._getHelper()._removeElement(_old, ForeignConnectionFactoryOverrideMBean.class, param0));
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
               this.setForeignConnectionFactories(_new);
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

   public ForeignConnectionFactoryOverrideMBean lookupForeignConnectionFactory(String param0) {
      Object[] aary = (Object[])this._ForeignConnectionFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignConnectionFactoryOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignConnectionFactoryOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public String getInitialContextFactory() {
      return this._InitialContextFactory;
   }

   public boolean isInitialContextFactoryInherited() {
      return false;
   }

   public boolean isInitialContextFactorySet() {
      return this._isSet(12);
   }

   public void setInitialContextFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InitialContextFactory;
      this._InitialContextFactory = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getConnectionURL() {
      return this._ConnectionURL;
   }

   public boolean isConnectionURLInherited() {
      return false;
   }

   public boolean isConnectionURLSet() {
      return this._isSet(13);
   }

   public void setConnectionURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionURL;
      this._ConnectionURL = param0;
      this._postSet(13, _oldVal, param0);
   }

   public byte[] getJNDIPropertiesCredentialEncrypted() {
      return this._getHelper()._cloneArray(this._JNDIPropertiesCredentialEncrypted);
   }

   public String getJNDIPropertiesCredentialEncryptedAsString() {
      byte[] obj = this.getJNDIPropertiesCredentialEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isJNDIPropertiesCredentialEncryptedInherited() {
      return false;
   }

   public boolean isJNDIPropertiesCredentialEncryptedSet() {
      return this._isSet(14);
   }

   public void setJNDIPropertiesCredentialEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setJNDIPropertiesCredentialEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getJNDIPropertiesCredential() {
      byte[] bEncrypted = this.getJNDIPropertiesCredentialEncrypted();
      return bEncrypted == null ? null : this._decrypt("JNDIPropertiesCredential", bEncrypted);
   }

   public boolean isJNDIPropertiesCredentialInherited() {
      return false;
   }

   public boolean isJNDIPropertiesCredentialSet() {
      return this.isJNDIPropertiesCredentialEncryptedSet();
   }

   public void setJNDIPropertiesCredential(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setJNDIPropertiesCredentialEncrypted(param0 == null ? null : this._encrypt("JNDIPropertiesCredential", param0));
   }

   public void addJNDIProperty(PartitionPropertyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         PartitionPropertyMBean[] _new;
         if (this._isSet(16)) {
            _new = (PartitionPropertyMBean[])((PartitionPropertyMBean[])this._getHelper()._extendArray(this.getJNDIProperties(), PartitionPropertyMBean.class, param0));
         } else {
            _new = new PartitionPropertyMBean[]{param0};
         }

         try {
            this.setJNDIProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PartitionPropertyMBean[] getJNDIProperties() {
      return this._JNDIProperties;
   }

   public boolean isJNDIPropertiesInherited() {
      return false;
   }

   public boolean isJNDIPropertiesSet() {
      return this._isSet(16);
   }

   public void removeJNDIProperty(PartitionPropertyMBean param0) {
      this.destroyJNDIProperty(param0);
   }

   public void setJNDIProperties(PartitionPropertyMBean[] param0) throws InvalidAttributeValueException {
      PartitionPropertyMBean[] param0 = param0 == null ? new PartitionPropertyMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PartitionPropertyMBean[] _oldVal = this._JNDIProperties;
      this._JNDIProperties = (PartitionPropertyMBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public PartitionPropertyMBean createJNDIProperty(String param0) {
      PartitionPropertyMBeanImpl lookup = (PartitionPropertyMBeanImpl)this.lookupJNDIProperty(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         PartitionPropertyMBeanImpl _val = new PartitionPropertyMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJNDIProperty(_val);
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

   public void destroyJNDIProperty(PartitionPropertyMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         PartitionPropertyMBean[] _old = this.getJNDIProperties();
         PartitionPropertyMBean[] _new = (PartitionPropertyMBean[])((PartitionPropertyMBean[])this._getHelper()._removeElement(_old, PartitionPropertyMBean.class, param0));
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
               this.setJNDIProperties(_new);
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

   public PartitionPropertyMBean lookupJNDIProperty(String param0) {
      Object[] aary = (Object[])this._JNDIProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PartitionPropertyMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PartitionPropertyMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setJNDIPropertiesCredentialEncrypted(byte[] param0) {
      byte[] _oldVal = this._JNDIPropertiesCredentialEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: JNDIPropertiesCredentialEncrypted of ForeignServerOverrideMBean");
      } else {
         this._getHelper()._clearArray(this._JNDIPropertiesCredentialEncrypted);
         this._JNDIPropertiesCredentialEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(14, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 15) {
            this._markSet(14, false);
         }
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._ConnectionURL = "No-Override";
               if (initOne) {
                  break;
               }
            case 11:
               this._ForeignConnectionFactories = new ForeignConnectionFactoryOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._ForeignDestinations = new ForeignDestinationOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._InitialContextFactory = "No-Override";
               if (initOne) {
                  break;
               }
            case 16:
               this._JNDIProperties = new PartitionPropertyMBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._JNDIPropertiesCredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._JNDIPropertiesCredentialEncrypted = null;
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
      return "ForeignServerOverride";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ConnectionURL")) {
         oldVal = this._ConnectionURL;
         this._ConnectionURL = (String)v;
         this._postSet(13, oldVal, this._ConnectionURL);
      } else if (name.equals("ForeignConnectionFactories")) {
         ForeignConnectionFactoryOverrideMBean[] oldVal = this._ForeignConnectionFactories;
         this._ForeignConnectionFactories = (ForeignConnectionFactoryOverrideMBean[])((ForeignConnectionFactoryOverrideMBean[])v);
         this._postSet(11, oldVal, this._ForeignConnectionFactories);
      } else if (name.equals("ForeignDestinations")) {
         ForeignDestinationOverrideMBean[] oldVal = this._ForeignDestinations;
         this._ForeignDestinations = (ForeignDestinationOverrideMBean[])((ForeignDestinationOverrideMBean[])v);
         this._postSet(10, oldVal, this._ForeignDestinations);
      } else if (name.equals("InitialContextFactory")) {
         oldVal = this._InitialContextFactory;
         this._InitialContextFactory = (String)v;
         this._postSet(12, oldVal, this._InitialContextFactory);
      } else if (name.equals("JNDIProperties")) {
         PartitionPropertyMBean[] oldVal = this._JNDIProperties;
         this._JNDIProperties = (PartitionPropertyMBean[])((PartitionPropertyMBean[])v);
         this._postSet(16, oldVal, this._JNDIProperties);
      } else if (name.equals("JNDIPropertiesCredential")) {
         oldVal = this._JNDIPropertiesCredential;
         this._JNDIPropertiesCredential = (String)v;
         this._postSet(15, oldVal, this._JNDIPropertiesCredential);
      } else if (name.equals("JNDIPropertiesCredentialEncrypted")) {
         byte[] oldVal = this._JNDIPropertiesCredentialEncrypted;
         this._JNDIPropertiesCredentialEncrypted = (byte[])((byte[])v);
         this._postSet(14, oldVal, this._JNDIPropertiesCredentialEncrypted);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionURL")) {
         return this._ConnectionURL;
      } else if (name.equals("ForeignConnectionFactories")) {
         return this._ForeignConnectionFactories;
      } else if (name.equals("ForeignDestinations")) {
         return this._ForeignDestinations;
      } else if (name.equals("InitialContextFactory")) {
         return this._InitialContextFactory;
      } else if (name.equals("JNDIProperties")) {
         return this._JNDIProperties;
      } else if (name.equals("JNDIPropertiesCredential")) {
         return this._JNDIPropertiesCredential;
      } else {
         return name.equals("JNDIPropertiesCredentialEncrypted") ? this._JNDIPropertiesCredentialEncrypted : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("jndi-property")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("foreign-destination")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("initial-context-factory")) {
                  return 12;
               }
               break;
            case 26:
               if (s.equals("foreign-connection-factory")) {
                  return 11;
               }

               if (s.equals("jndi-properties-credential")) {
                  return 15;
               }
               break;
            case 36:
               if (s.equals("jndi-properties-credential-encrypted")) {
                  return 14;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ForeignDestinationOverrideMBeanImpl.SchemaHelper2();
            case 11:
               return new ForeignConnectionFactoryOverrideMBeanImpl.SchemaHelper2();
            case 16:
               return new PartitionPropertyMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "foreign-destination";
            case 11:
               return "foreign-connection-factory";
            case 12:
               return "initial-context-factory";
            case 13:
               return "connection-url";
            case 14:
               return "jndi-properties-credential-encrypted";
            case 15:
               return "jndi-properties-credential";
            case 16:
               return "jndi-property";
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
            case 11:
               return true;
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.isArray(propIndex);
            case 16:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 16:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
      private ForeignServerOverrideMBeanImpl bean;

      protected Helper(ForeignServerOverrideMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ForeignDestinations";
            case 11:
               return "ForeignConnectionFactories";
            case 12:
               return "InitialContextFactory";
            case 13:
               return "ConnectionURL";
            case 14:
               return "JNDIPropertiesCredentialEncrypted";
            case 15:
               return "JNDIPropertiesCredential";
            case 16:
               return "JNDIProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionURL")) {
            return 13;
         } else if (propName.equals("ForeignConnectionFactories")) {
            return 11;
         } else if (propName.equals("ForeignDestinations")) {
            return 10;
         } else if (propName.equals("InitialContextFactory")) {
            return 12;
         } else if (propName.equals("JNDIProperties")) {
            return 16;
         } else if (propName.equals("JNDIPropertiesCredential")) {
            return 15;
         } else {
            return propName.equals("JNDIPropertiesCredentialEncrypted") ? 14 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getForeignConnectionFactories()));
         iterators.add(new ArrayIterator(this.bean.getForeignDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJNDIProperties()));
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
            if (this.bean.isConnectionURLSet()) {
               buf.append("ConnectionURL");
               buf.append(String.valueOf(this.bean.getConnectionURL()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getForeignConnectionFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignConnectionFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getForeignDestinations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignDestinations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isInitialContextFactorySet()) {
               buf.append("InitialContextFactory");
               buf.append(String.valueOf(this.bean.getInitialContextFactory()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJNDIProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJNDIProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJNDIPropertiesCredentialSet()) {
               buf.append("JNDIPropertiesCredential");
               buf.append(String.valueOf(this.bean.getJNDIPropertiesCredential()));
            }

            if (this.bean.isJNDIPropertiesCredentialEncryptedSet()) {
               buf.append("JNDIPropertiesCredentialEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJNDIPropertiesCredentialEncrypted())));
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
            ForeignServerOverrideMBeanImpl otherTyped = (ForeignServerOverrideMBeanImpl)other;
            this.addRestartElements("ConnectionURL", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), true);
            this.addRestartElements("ForeignConnectionFactories", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeChildDiff("ForeignConnectionFactories", this.bean.getForeignConnectionFactories(), otherTyped.getForeignConnectionFactories(), true);
            this.addRestartElements("ForeignDestinations", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeChildDiff("ForeignDestinations", this.bean.getForeignDestinations(), otherTyped.getForeignDestinations(), true);
            this.addRestartElements("InitialContextFactory", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), true);
            this.addRestartElements("JNDIProperties", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeChildDiff("JNDIProperties", this.bean.getJNDIProperties(), otherTyped.getJNDIProperties(), true);
            this.addRestartElements("JNDIPropertiesCredentialEncrypted", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("JNDIPropertiesCredentialEncrypted", this.bean.getJNDIPropertiesCredentialEncrypted(), otherTyped.getJNDIPropertiesCredentialEncrypted(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignServerOverrideMBeanImpl original = (ForeignServerOverrideMBeanImpl)event.getSourceBean();
            ForeignServerOverrideMBeanImpl proposed = (ForeignServerOverrideMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionURL")) {
                  original.setConnectionURL(proposed.getConnectionURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ForeignConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignConnectionFactory((ForeignConnectionFactoryOverrideMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignConnectionFactory((ForeignConnectionFactoryOverrideMBean)update.getRemovedObject());
                  }

                  if (original.getForeignConnectionFactories() == null || original.getForeignConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("ForeignDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignDestination((ForeignDestinationOverrideMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignDestination((ForeignDestinationOverrideMBean)update.getRemovedObject());
                  }

                  if (original.getForeignDestinations() == null || original.getForeignDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("InitialContextFactory")) {
                  original.setInitialContextFactory(proposed.getInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("JNDIProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJNDIProperty((PartitionPropertyMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJNDIProperty((PartitionPropertyMBean)update.getRemovedObject());
                  }

                  if (original.getJNDIProperties() == null || original.getJNDIProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
               } else if (!prop.equals("JNDIPropertiesCredential")) {
                  if (prop.equals("JNDIPropertiesCredentialEncrypted")) {
                     original.setJNDIPropertiesCredentialEncrypted(proposed.getJNDIPropertiesCredentialEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else {
                     super.applyPropertyUpdate(event, update);
                  }
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
            ForeignServerOverrideMBeanImpl copy = (ForeignServerOverrideMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ForeignConnectionFactories")) && this.bean.isForeignConnectionFactoriesSet() && !copy._isSet(11)) {
               ForeignConnectionFactoryOverrideMBean[] oldForeignConnectionFactories = this.bean.getForeignConnectionFactories();
               ForeignConnectionFactoryOverrideMBean[] newForeignConnectionFactories = new ForeignConnectionFactoryOverrideMBean[oldForeignConnectionFactories.length];

               for(i = 0; i < newForeignConnectionFactories.length; ++i) {
                  newForeignConnectionFactories[i] = (ForeignConnectionFactoryOverrideMBean)((ForeignConnectionFactoryOverrideMBean)this.createCopy((AbstractDescriptorBean)oldForeignConnectionFactories[i], includeObsolete));
               }

               copy.setForeignConnectionFactories(newForeignConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignDestinations")) && this.bean.isForeignDestinationsSet() && !copy._isSet(10)) {
               ForeignDestinationOverrideMBean[] oldForeignDestinations = this.bean.getForeignDestinations();
               ForeignDestinationOverrideMBean[] newForeignDestinations = new ForeignDestinationOverrideMBean[oldForeignDestinations.length];

               for(i = 0; i < newForeignDestinations.length; ++i) {
                  newForeignDestinations[i] = (ForeignDestinationOverrideMBean)((ForeignDestinationOverrideMBean)this.createCopy((AbstractDescriptorBean)oldForeignDestinations[i], includeObsolete));
               }

               copy.setForeignDestinations(newForeignDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIProperties")) && this.bean.isJNDIPropertiesSet() && !copy._isSet(16)) {
               PartitionPropertyMBean[] oldJNDIProperties = this.bean.getJNDIProperties();
               PartitionPropertyMBean[] newJNDIProperties = new PartitionPropertyMBean[oldJNDIProperties.length];

               for(i = 0; i < newJNDIProperties.length; ++i) {
                  newJNDIProperties[i] = (PartitionPropertyMBean)((PartitionPropertyMBean)this.createCopy((AbstractDescriptorBean)oldJNDIProperties[i], includeObsolete));
               }

               copy.setJNDIProperties(newJNDIProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIPropertiesCredentialEncrypted")) && this.bean.isJNDIPropertiesCredentialEncryptedSet()) {
               Object o = this.bean.getJNDIPropertiesCredentialEncrypted();
               copy.setJNDIPropertiesCredentialEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
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
         this.inferSubTree(this.bean.getForeignConnectionFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getForeignDestinations(), clazz, annotation);
         this.inferSubTree(this.bean.getJNDIProperties(), clazz, annotation);
      }
   }
}
