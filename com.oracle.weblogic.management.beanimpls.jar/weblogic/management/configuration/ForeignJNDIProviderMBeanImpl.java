package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ForeignJNDIProviderMBeanImpl extends DeploymentMBeanImpl implements ForeignJNDIProviderMBean, Serializable {
   private ForeignJNDILinkMBean[] _ForeignJNDILinks;
   private String _InitialContextFactory;
   private String _Name;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private Properties _Properties;
   private String _ProviderURL;
   private String _User;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ForeignJNDIProviderMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ForeignJNDIProviderMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ForeignJNDIProviderMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ForeignJNDIProviderMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ForeignJNDIProviderMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ForeignJNDIProviderMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ForeignJNDIProviderMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ForeignJNDIProviderMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ForeignJNDIProviderMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._Name;
      }
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public String getInitialContextFactory() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getInitialContextFactory(), this) : this._InitialContextFactory;
   }

   public boolean isInitialContextFactoryInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isInitialContextFactorySet() {
      return this._isSet(12);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setInitialContextFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._InitialContextFactory;
      this._InitialContextFactory = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getProviderURL() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getProviderURL(), this) : this._ProviderURL;
   }

   public boolean isProviderURLInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isProviderURLSet() {
      return this._isSet(13);
   }

   public void setProviderURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._ProviderURL;
      this._ProviderURL = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPassword() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._performMacroSubstitution(this._getDelegateBean().getPassword(), this);
      } else {
         byte[] bEncrypted = this.getPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("Password", bEncrypted);
      }
   }

   public boolean isPasswordInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isPasswordSet() {
      return this.isPasswordEncryptedSet();
   }

   public void setPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
   }

   public byte[] getPasswordEncrypted() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getPasswordEncrypted() : this._getHelper()._cloneArray(this._PasswordEncrypted);
   }

   public String getPasswordEncryptedAsString() {
      byte[] obj = this.getPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isPasswordEncryptedInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isPasswordEncryptedSet() {
      return this._isSet(15);
   }

   public void setPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getUser() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getUser(), this) : this._User;
   }

   public boolean isUserInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isUserSet() {
      return this._isSet(16);
   }

   public void setUser(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String _oldVal = this._User;
      this._User = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public ForeignJNDILinkMBean[] getForeignJNDILinks() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getForeignJNDILinks() : this._ForeignJNDILinks;
   }

   public boolean isForeignJNDILinksInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isForeignJNDILinksSet() {
      return this._isSet(17);
   }

   public void removeForeignJNDILink(ForeignJNDILinkMBean param0) {
      this.destroyForeignJNDILink(param0);
   }

   public void setForeignJNDILinks(ForeignJNDILinkMBean[] param0) throws InvalidAttributeValueException {
      ForeignJNDILinkMBean[] param0 = param0 == null ? new ForeignJNDILinkMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(17);
      ForeignJNDILinkMBean[] _oldVal = this._ForeignJNDILinks;
      this._ForeignJNDILinks = (ForeignJNDILinkMBean[])param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public ForeignJNDILinkMBean lookupForeignJNDILink(String param0) {
      Object[] aary = (Object[])this._ForeignJNDILinks;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ForeignJNDILinkMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ForeignJNDILinkMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public ForeignJNDILinkMBean createForeignJNDILink(String param0) {
      ForeignJNDILinkMBeanImpl lookup = (ForeignJNDILinkMBeanImpl)this.lookupForeignJNDILink(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ForeignJNDILinkMBeanImpl _val = new ForeignJNDILinkMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addForeignJNDILink(_val);
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

   public void addForeignJNDILink(ForeignJNDILinkMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         ForeignJNDILinkMBean[] _new;
         if (this._isSet(17)) {
            _new = (ForeignJNDILinkMBean[])((ForeignJNDILinkMBean[])this._getHelper()._extendArray(this.getForeignJNDILinks(), ForeignJNDILinkMBean.class, param0));
         } else {
            _new = new ForeignJNDILinkMBean[]{param0};
         }

         try {
            this.setForeignJNDILinks(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void destroyForeignJNDILink(ForeignJNDILinkMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         ForeignJNDILinkMBean[] _old = this.getForeignJNDILinks();
         ForeignJNDILinkMBean[] _new = (ForeignJNDILinkMBean[])((ForeignJNDILinkMBean[])this._getHelper()._removeElement(_old, ForeignJNDILinkMBean.class, param0));
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
                  ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var6.next();
                  ForeignJNDILinkMBeanImpl childImpl = (ForeignJNDILinkMBeanImpl)_child;
                  ForeignJNDILinkMBeanImpl lookup = (ForeignJNDILinkMBeanImpl)source.lookupForeignJNDILink(childImpl.getName());
                  if (lookup != null) {
                     source.destroyForeignJNDILink(lookup);
                  }
               }

               this.setForeignJNDILinks(_new);
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

   public void setProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      Properties _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getProperties() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getProperties() : this._Properties;
   }

   public String getPropertiesAsString() {
      return StringHelper.objectToString(this.getProperties());
   }

   public boolean isPropertiesInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isPropertiesSet() {
      return this._isSet(18);
   }

   public void setPropertiesAsString(String param0) {
      try {
         this.setProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of ForeignJNDIProviderMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(15, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ForeignJNDIProviderMBeanImpl source = (ForeignJNDIProviderMBeanImpl)var4.next();
            if (source != null && !source._isSet(15)) {
               source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         if (idx == 14) {
            this._markSet(15, false);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._ForeignJNDILinks = new ForeignJNDILinkMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._InitialContextFactory = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._Properties = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ProviderURL = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._User = null;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
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
      return "ForeignJNDIProvider";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ForeignJNDILinks")) {
         ForeignJNDILinkMBean[] oldVal = this._ForeignJNDILinks;
         this._ForeignJNDILinks = (ForeignJNDILinkMBean[])((ForeignJNDILinkMBean[])v);
         this._postSet(17, oldVal, this._ForeignJNDILinks);
      } else {
         String oldVal;
         if (name.equals("InitialContextFactory")) {
            oldVal = this._InitialContextFactory;
            this._InitialContextFactory = (String)v;
            this._postSet(12, oldVal, this._InitialContextFactory);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("Password")) {
            oldVal = this._Password;
            this._Password = (String)v;
            this._postSet(14, oldVal, this._Password);
         } else if (name.equals("PasswordEncrypted")) {
            byte[] oldVal = this._PasswordEncrypted;
            this._PasswordEncrypted = (byte[])((byte[])v);
            this._postSet(15, oldVal, this._PasswordEncrypted);
         } else if (name.equals("Properties")) {
            Properties oldVal = this._Properties;
            this._Properties = (Properties)v;
            this._postSet(18, oldVal, this._Properties);
         } else if (name.equals("ProviderURL")) {
            oldVal = this._ProviderURL;
            this._ProviderURL = (String)v;
            this._postSet(13, oldVal, this._ProviderURL);
         } else if (name.equals("User")) {
            oldVal = this._User;
            this._User = (String)v;
            this._postSet(16, oldVal, this._User);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ForeignJNDILinks")) {
         return this._ForeignJNDILinks;
      } else if (name.equals("InitialContextFactory")) {
         return this._InitialContextFactory;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("Properties")) {
         return this._Properties;
      } else if (name.equals("ProviderURL")) {
         return this._ProviderURL;
      } else {
         return name.equals("User") ? this._User : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }

               if (s.equals("user")) {
                  return 16;
               }
            case 5:
            case 6:
            case 7:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
               break;
            case 8:
               if (s.equals("password")) {
                  return 14;
               }
               break;
            case 10:
               if (s.equals("properties")) {
                  return 18;
               }
               break;
            case 12:
               if (s.equals("provider-url")) {
                  return 13;
               }
               break;
            case 17:
               if (s.equals("foreign-jndi-link")) {
                  return 17;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 15;
               }
               break;
            case 23:
               if (s.equals("initial-context-factory")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 17:
               return new ForeignJNDILinkMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 12:
               return "initial-context-factory";
            case 13:
               return "provider-url";
            case 14:
               return "password";
            case 15:
               return "password-encrypted";
            case 16:
               return "user";
            case 17:
               return "foreign-jndi-link";
            case 18:
               return "properties";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 17:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 17:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private ForeignJNDIProviderMBeanImpl bean;

      protected Helper(ForeignJNDIProviderMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 12:
               return "InitialContextFactory";
            case 13:
               return "ProviderURL";
            case 14:
               return "Password";
            case 15:
               return "PasswordEncrypted";
            case 16:
               return "User";
            case 17:
               return "ForeignJNDILinks";
            case 18:
               return "Properties";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ForeignJNDILinks")) {
            return 17;
         } else if (propName.equals("InitialContextFactory")) {
            return 12;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Password")) {
            return 14;
         } else if (propName.equals("PasswordEncrypted")) {
            return 15;
         } else if (propName.equals("Properties")) {
            return 18;
         } else if (propName.equals("ProviderURL")) {
            return 13;
         } else {
            return propName.equals("User") ? 16 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getForeignJNDILinks()));
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

            for(int i = 0; i < this.bean.getForeignJNDILinks().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getForeignJNDILinks()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isInitialContextFactorySet()) {
               buf.append("InitialContextFactory");
               buf.append(String.valueOf(this.bean.getInitialContextFactory()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isPropertiesSet()) {
               buf.append("Properties");
               buf.append(String.valueOf(this.bean.getProperties()));
            }

            if (this.bean.isProviderURLSet()) {
               buf.append("ProviderURL");
               buf.append(String.valueOf(this.bean.getProviderURL()));
            }

            if (this.bean.isUserSet()) {
               buf.append("User");
               buf.append(String.valueOf(this.bean.getUser()));
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
            ForeignJNDIProviderMBeanImpl otherTyped = (ForeignJNDIProviderMBeanImpl)other;
            this.computeChildDiff("ForeignJNDILinks", this.bean.getForeignJNDILinks(), otherTyped.getForeignJNDILinks(), true);
            this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            this.computeDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), true);
            this.computeDiff("ProviderURL", this.bean.getProviderURL(), otherTyped.getProviderURL(), true);
            this.computeDiff("User", this.bean.getUser(), otherTyped.getUser(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignJNDIProviderMBeanImpl original = (ForeignJNDIProviderMBeanImpl)event.getSourceBean();
            ForeignJNDIProviderMBeanImpl proposed = (ForeignJNDIProviderMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ForeignJNDILinks")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addForeignJNDILink((ForeignJNDILinkMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeForeignJNDILink((ForeignJNDILinkMBean)update.getRemovedObject());
                  }

                  if (original.getForeignJNDILinks() == null || original.getForeignJNDILinks().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("InitialContextFactory")) {
                  original.setInitialContextFactory(proposed.getInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("Properties")) {
                     original.setProperties(proposed.getProperties() == null ? null : (Properties)proposed.getProperties().clone());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("ProviderURL")) {
                     original.setProviderURL(proposed.getProviderURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("User")) {
                     original.setUser(proposed.getUser());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            ForeignJNDIProviderMBeanImpl copy = (ForeignJNDIProviderMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ForeignJNDILinks")) && this.bean.isForeignJNDILinksSet() && !copy._isSet(17)) {
               ForeignJNDILinkMBean[] oldForeignJNDILinks = this.bean.getForeignJNDILinks();
               ForeignJNDILinkMBean[] newForeignJNDILinks = new ForeignJNDILinkMBean[oldForeignJNDILinks.length];

               for(int i = 0; i < newForeignJNDILinks.length; ++i) {
                  newForeignJNDILinks[i] = (ForeignJNDILinkMBean)((ForeignJNDILinkMBean)this.createCopy((AbstractDescriptorBean)oldForeignJNDILinks[i], includeObsolete));
               }

               copy.setForeignJNDILinks(newForeignJNDILinks);
            }

            if ((excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet()) {
               copy.setProperties(this.bean.getProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("ProviderURL")) && this.bean.isProviderURLSet()) {
               copy.setProviderURL(this.bean.getProviderURL());
            }

            if ((excludeProps == null || !excludeProps.contains("User")) && this.bean.isUserSet()) {
               copy.setUser(this.bean.getUser());
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
         this.inferSubTree(this.bean.getForeignJNDILinks(), clazz, annotation);
      }
   }
}
