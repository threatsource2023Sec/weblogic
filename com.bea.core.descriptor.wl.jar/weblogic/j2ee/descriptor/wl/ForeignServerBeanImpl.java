package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ForeignServerBeanImpl extends TargetableBeanImpl implements ForeignServerBean, Serializable {
   private String _ConnectionURL;
   private ForeignConnectionFactoryBean[] _ForeignConnectionFactories;
   private ForeignDestinationBean[] _ForeignDestinations;
   private String _InitialContextFactory;
   private PropertyBean[] _JNDIProperties;
   private String _JNDIPropertiesCredential;
   private byte[] _JNDIPropertiesCredentialEncrypted;
   private static SchemaHelper2 _schemaHelper;

   public ForeignServerBeanImpl() {
      this._initializeProperty(-1);
   }

   public ForeignServerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ForeignServerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addForeignDestination(ForeignDestinationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         ForeignDestinationBean[] _new;
         if (this._isSet(5)) {
            _new = (ForeignDestinationBean[])((ForeignDestinationBean[])this._getHelper()._extendArray(this.getForeignDestinations(), ForeignDestinationBean.class, param0));
         } else {
            _new = new ForeignDestinationBean[]{param0};
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

   public ForeignDestinationBean[] getForeignDestinations() {
      return this._ForeignDestinations;
   }

   public boolean isForeignDestinationsInherited() {
      return false;
   }

   public boolean isForeignDestinationsSet() {
      return this._isSet(5);
   }

   public void removeForeignDestination(ForeignDestinationBean param0) {
      this.destroyForeignDestination(param0);
   }

   public void setForeignDestinations(ForeignDestinationBean[] param0) throws InvalidAttributeValueException {
      ForeignDestinationBean[] param0 = param0 == null ? new ForeignDestinationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignDestinationBean[] _oldVal = this._ForeignDestinations;
      this._ForeignDestinations = (ForeignDestinationBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public ForeignDestinationBean createForeignDestination(String param0) {
      ForeignDestinationBeanImpl lookup = (ForeignDestinationBeanImpl)this.lookupForeignDestination(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignDestinationBeanImpl _val = new ForeignDestinationBeanImpl(this, -1);

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

   public void destroyForeignDestination(ForeignDestinationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         ForeignDestinationBean[] _old = this.getForeignDestinations();
         ForeignDestinationBean[] _new = (ForeignDestinationBean[])((ForeignDestinationBean[])this._getHelper()._removeElement(_old, ForeignDestinationBean.class, param0));
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

   public ForeignDestinationBean lookupForeignDestination(String param0) {
      Object[] aary = (Object[])this._ForeignDestinations;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignDestinationBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignDestinationBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addForeignConnectionFactory(ForeignConnectionFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         ForeignConnectionFactoryBean[] _new;
         if (this._isSet(6)) {
            _new = (ForeignConnectionFactoryBean[])((ForeignConnectionFactoryBean[])this._getHelper()._extendArray(this.getForeignConnectionFactories(), ForeignConnectionFactoryBean.class, param0));
         } else {
            _new = new ForeignConnectionFactoryBean[]{param0};
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

   public ForeignConnectionFactoryBean[] getForeignConnectionFactories() {
      return this._ForeignConnectionFactories;
   }

   public boolean isForeignConnectionFactoriesInherited() {
      return false;
   }

   public boolean isForeignConnectionFactoriesSet() {
      return this._isSet(6);
   }

   public void removeForeignConnectionFactory(ForeignConnectionFactoryBean param0) {
      this.destroyForeignConnectionFactory(param0);
   }

   public void setForeignConnectionFactories(ForeignConnectionFactoryBean[] param0) throws InvalidAttributeValueException {
      ForeignConnectionFactoryBean[] param0 = param0 == null ? new ForeignConnectionFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ForeignConnectionFactoryBean[] _oldVal = this._ForeignConnectionFactories;
      this._ForeignConnectionFactories = (ForeignConnectionFactoryBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public ForeignConnectionFactoryBean createForeignConnectionFactory(String param0) {
      ForeignConnectionFactoryBeanImpl lookup = (ForeignConnectionFactoryBeanImpl)this.lookupForeignConnectionFactory(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignConnectionFactoryBeanImpl _val = new ForeignConnectionFactoryBeanImpl(this, -1);

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

   public void destroyForeignConnectionFactory(ForeignConnectionFactoryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         ForeignConnectionFactoryBean[] _old = this.getForeignConnectionFactories();
         ForeignConnectionFactoryBean[] _new = (ForeignConnectionFactoryBean[])((ForeignConnectionFactoryBean[])this._getHelper()._removeElement(_old, ForeignConnectionFactoryBean.class, param0));
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

   public ForeignConnectionFactoryBean lookupForeignConnectionFactory(String param0) {
      Object[] aary = (Object[])this._ForeignConnectionFactories;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignConnectionFactoryBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignConnectionFactoryBeanImpl)it.previous();
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
      return this._isSet(7);
   }

   public void setInitialContextFactory(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("InitialContextFactory", param0);
      JMSModuleValidator.validateForeignServerInitialContextFactory(param0);
      String _oldVal = this._InitialContextFactory;
      this._InitialContextFactory = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getConnectionURL() {
      return this._ConnectionURL;
   }

   public boolean isConnectionURLInherited() {
      return false;
   }

   public boolean isConnectionURLSet() {
      return this._isSet(8);
   }

   public void setConnectionURL(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionURL;
      this._ConnectionURL = param0;
      this._postSet(8, _oldVal, param0);
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
      return this._isSet(9);
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

   public void addJNDIProperty(PropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         PropertyBean[] _new;
         if (this._isSet(11)) {
            _new = (PropertyBean[])((PropertyBean[])this._getHelper()._extendArray(this.getJNDIProperties(), PropertyBean.class, param0));
         } else {
            _new = new PropertyBean[]{param0};
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

   public PropertyBean[] getJNDIProperties() {
      return this._JNDIProperties;
   }

   public boolean isJNDIPropertiesInherited() {
      return false;
   }

   public boolean isJNDIPropertiesSet() {
      return this._isSet(11);
   }

   public void removeJNDIProperty(PropertyBean param0) {
      this.destroyJNDIProperty(param0);
   }

   public void setJNDIProperties(PropertyBean[] param0) throws InvalidAttributeValueException {
      PropertyBean[] param0 = param0 == null ? new PropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PropertyBean[] _oldVal = this._JNDIProperties;
      this._JNDIProperties = (PropertyBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public PropertyBean createJNDIProperty(String param0) {
      PropertyBeanImpl _val = new PropertyBeanImpl(this, -1);

      try {
         _val.setKey(param0);
         this.addJNDIProperty(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyJNDIProperty(PropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         PropertyBean[] _old = this.getJNDIProperties();
         PropertyBean[] _new = (PropertyBean[])((PropertyBean[])this._getHelper()._removeElement(_old, PropertyBean.class, param0));
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

   public PropertyBean lookupJNDIProperty(String param0) {
      Object[] aary = (Object[])this._JNDIProperties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PropertyBeanImpl)it.previous();
      } while(!bean.getKey().equals(param0));

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
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: JNDIPropertiesCredentialEncrypted of ForeignServerBean");
      } else {
         this._getHelper()._clearArray(this._JNDIPropertiesCredentialEncrypted);
         this._JNDIPropertiesCredentialEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(9, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 10) {
            this._markSet(9, false);
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._ConnectionURL = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._ForeignConnectionFactories = new ForeignConnectionFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._ForeignDestinations = new ForeignDestinationBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._InitialContextFactory = "weblogic.jndi.WLInitialContextFactory";
               if (initOne) {
                  break;
               }
            case 11:
               this._JNDIProperties = new PropertyBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._JNDIPropertiesCredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 9:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonEmptyString("InitialContextFactory", "weblogic.jndi.WLInitialContextFactory");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property InitialContextFactory in ForeignServerBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends TargetableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("jndi-property")) {
                  return 11;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 8;
               }
               break;
            case 19:
               if (s.equals("foreign-destination")) {
                  return 5;
               }
               break;
            case 23:
               if (s.equals("initial-context-factory")) {
                  return 7;
               }
               break;
            case 26:
               if (s.equals("foreign-connection-factory")) {
                  return 6;
               }

               if (s.equals("jndi-properties-credential")) {
                  return 10;
               }
               break;
            case 36:
               if (s.equals("jndi-properties-credential-encrypted")) {
                  return 9;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new ForeignDestinationBeanImpl.SchemaHelper2();
            case 6:
               return new ForeignConnectionFactoryBeanImpl.SchemaHelper2();
            case 11:
               return new PropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "foreign-destination";
            case 6:
               return "foreign-connection-factory";
            case 7:
               return "initial-context-factory";
            case 8:
               return "connection-url";
            case 9:
               return "jndi-properties-credential-encrypted";
            case 10:
               return "jndi-properties-credential";
            case 11:
               return "jndi-property";
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
            case 11:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 6:
               return true;
            case 11:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
            case 6:
            default:
               return super.isConfigurable(propIndex);
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends TargetableBeanImpl.Helper {
      private ForeignServerBeanImpl bean;

      protected Helper(ForeignServerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "ForeignDestinations";
            case 6:
               return "ForeignConnectionFactories";
            case 7:
               return "InitialContextFactory";
            case 8:
               return "ConnectionURL";
            case 9:
               return "JNDIPropertiesCredentialEncrypted";
            case 10:
               return "JNDIPropertiesCredential";
            case 11:
               return "JNDIProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionURL")) {
            return 8;
         } else if (propName.equals("ForeignConnectionFactories")) {
            return 6;
         } else if (propName.equals("ForeignDestinations")) {
            return 5;
         } else if (propName.equals("InitialContextFactory")) {
            return 7;
         } else if (propName.equals("JNDIProperties")) {
            return 11;
         } else if (propName.equals("JNDIPropertiesCredential")) {
            return 10;
         } else {
            return propName.equals("JNDIPropertiesCredentialEncrypted") ? 9 : super.getPropertyIndex(propName);
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
            ForeignServerBeanImpl otherTyped = (ForeignServerBeanImpl)other;
            this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), true);
            this.computeChildDiff("ForeignConnectionFactories", this.bean.getForeignConnectionFactories(), otherTyped.getForeignConnectionFactories(), true);
            this.computeChildDiff("ForeignDestinations", this.bean.getForeignDestinations(), otherTyped.getForeignDestinations(), true);
            this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), true);
            this.computeChildDiff("JNDIProperties", this.bean.getJNDIProperties(), otherTyped.getJNDIProperties(), true);
            this.computeDiff("JNDIPropertiesCredentialEncrypted", this.bean.getJNDIPropertiesCredentialEncrypted(), otherTyped.getJNDIPropertiesCredentialEncrypted(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignServerBeanImpl original = (ForeignServerBeanImpl)event.getSourceBean();
            ForeignServerBeanImpl proposed = (ForeignServerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionURL")) {
                  original.setConnectionURL(proposed.getConnectionURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ForeignConnectionFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignConnectionFactory((ForeignConnectionFactoryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignConnectionFactory((ForeignConnectionFactoryBean)update.getRemovedObject());
                  }

                  if (original.getForeignConnectionFactories() == null || original.getForeignConnectionFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("ForeignDestinations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignDestination((ForeignDestinationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignDestination((ForeignDestinationBean)update.getRemovedObject());
                  }

                  if (original.getForeignDestinations() == null || original.getForeignDestinations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("InitialContextFactory")) {
                  original.setInitialContextFactory(proposed.getInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("JNDIProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJNDIProperty((PropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJNDIProperty((PropertyBean)update.getRemovedObject());
                  }

                  if (original.getJNDIProperties() == null || original.getJNDIProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (!prop.equals("JNDIPropertiesCredential")) {
                  if (prop.equals("JNDIPropertiesCredentialEncrypted")) {
                     original.setJNDIPropertiesCredentialEncrypted(proposed.getJNDIPropertiesCredentialEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
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
            ForeignServerBeanImpl copy = (ForeignServerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ForeignConnectionFactories")) && this.bean.isForeignConnectionFactoriesSet() && !copy._isSet(6)) {
               ForeignConnectionFactoryBean[] oldForeignConnectionFactories = this.bean.getForeignConnectionFactories();
               ForeignConnectionFactoryBean[] newForeignConnectionFactories = new ForeignConnectionFactoryBean[oldForeignConnectionFactories.length];

               for(i = 0; i < newForeignConnectionFactories.length; ++i) {
                  newForeignConnectionFactories[i] = (ForeignConnectionFactoryBean)((ForeignConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)oldForeignConnectionFactories[i], includeObsolete));
               }

               copy.setForeignConnectionFactories(newForeignConnectionFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("ForeignDestinations")) && this.bean.isForeignDestinationsSet() && !copy._isSet(5)) {
               ForeignDestinationBean[] oldForeignDestinations = this.bean.getForeignDestinations();
               ForeignDestinationBean[] newForeignDestinations = new ForeignDestinationBean[oldForeignDestinations.length];

               for(i = 0; i < newForeignDestinations.length; ++i) {
                  newForeignDestinations[i] = (ForeignDestinationBean)((ForeignDestinationBean)this.createCopy((AbstractDescriptorBean)oldForeignDestinations[i], includeObsolete));
               }

               copy.setForeignDestinations(newForeignDestinations);
            }

            if ((excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIProperties")) && this.bean.isJNDIPropertiesSet() && !copy._isSet(11)) {
               PropertyBean[] oldJNDIProperties = this.bean.getJNDIProperties();
               PropertyBean[] newJNDIProperties = new PropertyBean[oldJNDIProperties.length];

               for(i = 0; i < newJNDIProperties.length; ++i) {
                  newJNDIProperties[i] = (PropertyBean)((PropertyBean)this.createCopy((AbstractDescriptorBean)oldJNDIProperties[i], includeObsolete));
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
