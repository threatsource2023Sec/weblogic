package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SecurityConfiguration;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.RealmMBeanImpl;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SecurityConfigurationMBeanImpl extends ConfigurationMBeanImpl implements SecurityConfigurationMBean, Serializable {
   private String _AdministrativeIdentityDomain;
   private boolean _AnonymousAdminLookupEnabled;
   private long _BootAuthenticationMaxRetryDelay;
   private int _BootAuthenticationRetryCount;
   private CertRevocMBean _CertRevoc;
   private boolean _ClearTextCredentialAccessEnabled;
   private boolean _CompatibilityConnectionFiltersEnabled;
   private String _ConnectionFilter;
   private String[] _ConnectionFilterRules;
   private boolean _ConnectionLoggerEnabled;
   private boolean _ConsoleFullDelegationEnabled;
   private String _Credential;
   private byte[] _CredentialEncrypted;
   private boolean _CredentialGenerated;
   private boolean _CrossDomainSecurityEnabled;
   private RealmMBean _DefaultRealm;
   private RealmMBean _DefaultRealmInternal;
   private boolean _DowngradeUntrustedPrincipals;
   private boolean _DynamicallyCreated;
   private byte[] _EncryptedAESSecretKey;
   private byte[] _EncryptedSecretKey;
   private boolean _EnforceStrictURLPattern;
   private boolean _EnforceValidBasicAuthCredentials;
   private String[] _ExcludedDomainNames;
   private boolean _IdentityDomainAwareProvidersRequired;
   private boolean _IdentityDomainDefaultEnabled;
   private JASPICMBean _JASPIC;
   private String _Name;
   private String _NodeManagerPassword;
   private byte[] _NodeManagerPasswordEncrypted;
   private String _NodeManagerUsername;
   private int _NonceTimeoutSeconds;
   private boolean _PrincipalEqualsCaseInsensitive;
   private boolean _PrincipalEqualsCompareDnAndGuid;
   private String _RealmBootStrapVersion;
   private RealmMBean[] _Realms;
   private boolean _RemoteAnonymousJNDIEnabled;
   private boolean _RemoteAnonymousRMIIIOPEnabled;
   private boolean _RemoteAnonymousRMIT3Enabled;
   private byte[] _Salt;
   private SecureModeMBean _SecureMode;
   private String[] _Tags;
   private boolean _UseKSSForDemo;
   private String _WebAppFilesCaseInsensitive;
   private transient SecurityConfiguration _customizer;
   private static SchemaHelper2 _schemaHelper;

   public SecurityConfigurationMBeanImpl() {
      try {
         this._customizer = new SecurityConfiguration(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SecurityConfigurationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SecurityConfiguration(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SecurityConfigurationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SecurityConfiguration(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public SecureModeMBean getSecureMode() {
      return this._SecureMode;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isSecureModeInherited() {
      return false;
   }

   public boolean isSecureModeSet() {
      return this._isSet(10) || this._isAnythingSet((AbstractDescriptorBean)this.getSecureMode());
   }

   public void setSecureMode(SecureModeMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 10)) {
         this._postCreate(_child);
      }

      SecureModeMBean _oldVal = this._SecureMode;
      this._SecureMode = param0;
      this._postSet(10, _oldVal, param0);
   }

   public JASPICMBean getJASPIC() {
      return this._JASPIC;
   }

   public boolean isJASPICInherited() {
      return false;
   }

   public boolean isJASPICSet() {
      return this._isSet(11) || this._isAnythingSet((AbstractDescriptorBean)this.getJASPIC());
   }

   public void setJASPIC(JASPICMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 11)) {
         this._postCreate(_child);
      }

      JASPICMBean _oldVal = this._JASPIC;
      this._JASPIC = param0;
      this._postSet(11, _oldVal, param0);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public RealmMBean createRealm(String param0) throws JMException {
      return this._customizer.createRealm(param0);
   }

   public RealmMBean createRealm() throws JMException {
      RealmMBeanImpl _val = new RealmMBeanImpl(this, -1);

      try {
         this.addRealm(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else if (var3 instanceof JMException) {
            throw (JMException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyRealm(RealmMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         RealmMBean[] _old = this.getRealms();
         RealmMBean[] _new = (RealmMBean[])((RealmMBean[])this._getHelper()._removeElement(_old, RealmMBean.class, param0));
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
               this.setRealms(_new);
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

   public void addRealm(RealmMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         RealmMBean[] _new;
         if (this._isSet(12)) {
            _new = (RealmMBean[])((RealmMBean[])this._getHelper()._extendArray(this.getRealms(), RealmMBean.class, param0));
         } else {
            _new = new RealmMBean[]{param0};
         }

         try {
            this.setRealms(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RealmMBean[] getRealms() {
      return this._Realms;
   }

   public boolean isRealmsInherited() {
      return false;
   }

   public boolean isRealmsSet() {
      return this._isSet(12);
   }

   public void removeRealm(RealmMBean param0) {
      this.destroyRealm(param0);
   }

   public void setRealms(RealmMBean[] param0) throws InvalidAttributeValueException {
      RealmMBean[] param0 = param0 == null ? new RealmMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      RealmMBean[] _oldVal = this._Realms;
      this._Realms = (RealmMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public RealmMBean lookupRealm(String param0) {
      Object[] aary = (Object[])this._Realms;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      RealmMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (RealmMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public RealmMBean[] findRealms() {
      return this._customizer.findRealms();
   }

   public RealmMBean findDefaultRealm() {
      return this._customizer.findDefaultRealm();
   }

   public RealmMBean findRealm(String param0) {
      return this._customizer.findRealm(param0);
   }

   public RealmMBean getDefaultRealm() {
      return this._DefaultRealm;
   }

   public String getDefaultRealmAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDefaultRealm();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDefaultRealmInherited() {
      return false;
   }

   public boolean isDefaultRealmSet() {
      return this._isSet(13);
   }

   public void setDefaultRealmAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, RealmMBean.class, new ReferenceManager.Resolver(this, 13) {
            public void resolveReference(Object value) {
               try {
                  SecurityConfigurationMBeanImpl.this.setDefaultRealm((RealmMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         RealmMBean _oldVal = this._DefaultRealm;
         this._initializeProperty(13);
         this._postSet(13, _oldVal, this._DefaultRealm);
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setDefaultRealm(RealmMBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 13, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SecurityConfigurationMBeanImpl.this.getDefaultRealm();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      RealmMBean _oldVal = this._DefaultRealm;
      this._DefaultRealm = param0;
      this._postSet(13, _oldVal, param0);
   }

   public byte[] getSalt() {
      return this._customizer.getSalt();
   }

   public boolean isSaltInherited() {
      return false;
   }

   public boolean isSaltSet() {
      return this._isSet(14);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setSalt(byte[] param0) throws InvalidAttributeValueException {
      this._Salt = param0;
   }

   public byte[] getEncryptedSecretKey() {
      return this._customizer.getEncryptedSecretKey();
   }

   public boolean isEncryptedSecretKeyInherited() {
      return false;
   }

   public boolean isEncryptedSecretKeySet() {
      return this._isSet(15);
   }

   public void setEncryptedSecretKey(byte[] param0) throws InvalidAttributeValueException {
      this._EncryptedSecretKey = param0;
   }

   public boolean isAnonymousAdminLookupEnabled() {
      return this._AnonymousAdminLookupEnabled;
   }

   public boolean isAnonymousAdminLookupEnabledInherited() {
      return false;
   }

   public boolean isAnonymousAdminLookupEnabledSet() {
      return this._isSet(16);
   }

   public void setAnonymousAdminLookupEnabled(boolean param0) {
      boolean _oldVal = this._AnonymousAdminLookupEnabled;
      this._AnonymousAdminLookupEnabled = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isClearTextCredentialAccessEnabled() {
      return this._ClearTextCredentialAccessEnabled;
   }

   public boolean isClearTextCredentialAccessEnabledInherited() {
      return false;
   }

   public boolean isClearTextCredentialAccessEnabledSet() {
      return this._isSet(17);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setClearTextCredentialAccessEnabled(boolean param0) {
      boolean _oldVal = this._ClearTextCredentialAccessEnabled;
      this._ClearTextCredentialAccessEnabled = param0;
      this._postSet(17, _oldVal, param0);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public boolean isCredentialGenerated() {
      return this._CredentialGenerated;
   }

   public boolean isCredentialGeneratedInherited() {
      return false;
   }

   public boolean isCredentialGeneratedSet() {
      return this._isSet(18);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setCredentialGenerated(boolean param0) {
      boolean _oldVal = this.isCredentialGenerated();
      this._customizer.setCredentialGenerated(param0);
      this._postSet(18, _oldVal, param0);
   }

   public byte[] generateCredential() {
      return this._customizer.generateCredential();
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getCredential() {
      byte[] bEncrypted = this.getCredentialEncrypted();
      return bEncrypted == null ? null : this._decrypt("Credential", bEncrypted);
   }

   public boolean isCredentialInherited() {
      return false;
   }

   public boolean isCredentialSet() {
      return this.isCredentialEncryptedSet();
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setCredential(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setCredentialEncrypted(param0 == null ? null : this._encrypt("Credential", param0));
   }

   public byte[] getCredentialEncrypted() {
      return this._getHelper()._cloneArray(this._CredentialEncrypted);
   }

   public String getCredentialEncryptedAsString() {
      byte[] obj = this.getCredentialEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCredentialEncryptedInherited() {
      return false;
   }

   public boolean isCredentialEncryptedSet() {
      return this._isSet(20);
   }

   public void setCredentialEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCredentialEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getWebAppFilesCaseInsensitive() {
      return this._WebAppFilesCaseInsensitive;
   }

   public boolean isWebAppFilesCaseInsensitiveInherited() {
      return false;
   }

   public boolean isWebAppFilesCaseInsensitiveSet() {
      return this._isSet(21);
   }

   public void setWebAppFilesCaseInsensitive(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"os", "true", "false"};
      param0 = LegalChecks.checkInEnum("WebAppFilesCaseInsensitive", param0, _set);
      String _oldVal = this._WebAppFilesCaseInsensitive;
      this._WebAppFilesCaseInsensitive = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getRealmBootStrapVersion() {
      if (!this._isSet(22)) {
         try {
            return "1";
         } catch (NullPointerException var2) {
         }
      }

      return this._RealmBootStrapVersion;
   }

   public boolean isRealmBootStrapVersionInherited() {
      return false;
   }

   public boolean isRealmBootStrapVersionSet() {
      return this._isSet(22);
   }

   public void setRealmBootStrapVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"unknown", "1"};
      param0 = LegalChecks.checkInEnum("RealmBootStrapVersion", param0, _set);
      String _oldVal = this._RealmBootStrapVersion;
      this._RealmBootStrapVersion = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getConnectionFilter() {
      return this._ConnectionFilter;
   }

   public boolean isConnectionFilterInherited() {
      return false;
   }

   public boolean isConnectionFilterSet() {
      return this._isSet(23);
   }

   public void setConnectionFilter(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFilter;
      this._ConnectionFilter = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String[] getConnectionFilterRules() {
      return this._ConnectionFilterRules;
   }

   public boolean isConnectionFilterRulesInherited() {
      return false;
   }

   public boolean isConnectionFilterRulesSet() {
      return this._isSet(24);
   }

   public void setConnectionFilterRules(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      this._getHelper()._ensureNonNullElements(param0);
      String[] _oldVal = this._ConnectionFilterRules;
      this._ConnectionFilterRules = param0;
      this._postSet(24, _oldVal, param0);
   }

   public boolean getConnectionLoggerEnabled() {
      return this._ConnectionLoggerEnabled;
   }

   public boolean isConnectionLoggerEnabledInherited() {
      return false;
   }

   public boolean isConnectionLoggerEnabledSet() {
      return this._isSet(25);
   }

   public void setConnectionLoggerEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._ConnectionLoggerEnabled;
      this._ConnectionLoggerEnabled = param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean getCompatibilityConnectionFiltersEnabled() {
      return this._CompatibilityConnectionFiltersEnabled;
   }

   public boolean isCompatibilityConnectionFiltersEnabledInherited() {
      return false;
   }

   public boolean isCompatibilityConnectionFiltersEnabledSet() {
      return this._isSet(26);
   }

   public void setCompatibilityConnectionFiltersEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._CompatibilityConnectionFiltersEnabled;
      this._CompatibilityConnectionFiltersEnabled = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getNodeManagerUsername() {
      return this._NodeManagerUsername;
   }

   public boolean isNodeManagerUsernameInherited() {
      return false;
   }

   public boolean isNodeManagerUsernameSet() {
      return this._isSet(27);
   }

   public void setNodeManagerUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NodeManagerUsername;
      this._NodeManagerUsername = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getNodeManagerPassword() {
      byte[] bEncrypted = this.getNodeManagerPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("NodeManagerPassword", bEncrypted);
   }

   public boolean isNodeManagerPasswordInherited() {
      return false;
   }

   public boolean isNodeManagerPasswordSet() {
      return this.isNodeManagerPasswordEncryptedSet();
   }

   public void setNodeManagerPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      NMPasswordValidator.validatePassword(param0);
      this.setNodeManagerPasswordEncrypted(param0 == null ? null : this._encrypt("NodeManagerPassword", param0));
   }

   public byte[] getNodeManagerPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._NodeManagerPasswordEncrypted);
   }

   public String getNodeManagerPasswordEncryptedAsString() {
      byte[] obj = this.getNodeManagerPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isNodeManagerPasswordEncryptedInherited() {
      return false;
   }

   public boolean isNodeManagerPasswordEncryptedSet() {
      return this._isSet(29);
   }

   public void setNodeManagerPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setNodeManagerPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isPrincipalEqualsCaseInsensitive() {
      return this._PrincipalEqualsCaseInsensitive;
   }

   public boolean isPrincipalEqualsCaseInsensitiveInherited() {
      return false;
   }

   public boolean isPrincipalEqualsCaseInsensitiveSet() {
      return this._isSet(30);
   }

   public void setPrincipalEqualsCaseInsensitive(boolean param0) {
      boolean _oldVal = this._PrincipalEqualsCaseInsensitive;
      this._PrincipalEqualsCaseInsensitive = param0;
      this._postSet(30, _oldVal, param0);
   }

   public boolean isPrincipalEqualsCompareDnAndGuid() {
      return this._PrincipalEqualsCompareDnAndGuid;
   }

   public boolean isPrincipalEqualsCompareDnAndGuidInherited() {
      return false;
   }

   public boolean isPrincipalEqualsCompareDnAndGuidSet() {
      return this._isSet(31);
   }

   public void setPrincipalEqualsCompareDnAndGuid(boolean param0) {
      boolean _oldVal = this._PrincipalEqualsCompareDnAndGuid;
      this._PrincipalEqualsCompareDnAndGuid = param0;
      this._postSet(31, _oldVal, param0);
   }

   public boolean getDowngradeUntrustedPrincipals() {
      return this._DowngradeUntrustedPrincipals;
   }

   public boolean isDowngradeUntrustedPrincipalsInherited() {
      return false;
   }

   public boolean isDowngradeUntrustedPrincipalsSet() {
      return this._isSet(32);
   }

   public void setDowngradeUntrustedPrincipals(boolean param0) {
      boolean _oldVal = this._DowngradeUntrustedPrincipals;
      this._DowngradeUntrustedPrincipals = param0;
      this._postSet(32, _oldVal, param0);
   }

   public boolean getEnforceStrictURLPattern() {
      return this._EnforceStrictURLPattern;
   }

   public boolean isEnforceStrictURLPatternInherited() {
      return false;
   }

   public boolean isEnforceStrictURLPatternSet() {
      return this._isSet(33);
   }

   public void setEnforceStrictURLPattern(boolean param0) {
      boolean _oldVal = this._EnforceStrictURLPattern;
      this._EnforceStrictURLPattern = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean getEnforceValidBasicAuthCredentials() {
      return this._EnforceValidBasicAuthCredentials;
   }

   public boolean isEnforceValidBasicAuthCredentialsInherited() {
      return false;
   }

   public boolean isEnforceValidBasicAuthCredentialsSet() {
      return this._isSet(34);
   }

   public void setEnforceValidBasicAuthCredentials(boolean param0) {
      boolean _oldVal = this._EnforceValidBasicAuthCredentials;
      this._EnforceValidBasicAuthCredentials = param0;
      this._postSet(34, _oldVal, param0);
   }

   public boolean isConsoleFullDelegationEnabled() {
      return this._ConsoleFullDelegationEnabled;
   }

   public boolean isConsoleFullDelegationEnabledInherited() {
      return false;
   }

   public boolean isConsoleFullDelegationEnabledSet() {
      return this._isSet(35);
   }

   public void setConsoleFullDelegationEnabled(boolean param0) {
      boolean _oldVal = this._ConsoleFullDelegationEnabled;
      this._ConsoleFullDelegationEnabled = param0;
      this._postSet(35, _oldVal, param0);
   }

   public RealmMBean getDefaultRealmInternal() {
      return this._customizer.getDefaultRealmInternal();
   }

   public String getDefaultRealmInternalAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDefaultRealmInternal();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDefaultRealmInternalInherited() {
      return false;
   }

   public boolean isDefaultRealmInternalSet() {
      return this._isSet(36);
   }

   public void setDefaultRealmInternalAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, RealmMBean.class, new ReferenceManager.Resolver(this, 36) {
            public void resolveReference(Object value) {
               try {
                  SecurityConfigurationMBeanImpl.this.setDefaultRealmInternal((RealmMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         RealmMBean _oldVal = this._DefaultRealmInternal;
         this._initializeProperty(36);
         this._postSet(36, _oldVal, this._DefaultRealmInternal);
      }

   }

   public void setDefaultRealmInternal(RealmMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 36, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SecurityConfigurationMBeanImpl.this.getDefaultRealmInternal();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      RealmMBean _oldVal = this.getDefaultRealmInternal();
      this._customizer.setDefaultRealmInternal(param0);
      this._postSet(36, _oldVal, param0);
   }

   public String[] getExcludedDomainNames() {
      return this._ExcludedDomainNames;
   }

   public boolean isExcludedDomainNamesInherited() {
      return false;
   }

   public boolean isExcludedDomainNamesSet() {
      return this._isSet(37);
   }

   public void setExcludedDomainNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ExcludedDomainNames;
      this._ExcludedDomainNames = param0;
      this._postSet(37, _oldVal, param0);
   }

   public boolean isCrossDomainSecurityEnabled() {
      return this._CrossDomainSecurityEnabled;
   }

   public boolean isCrossDomainSecurityEnabledInherited() {
      return false;
   }

   public boolean isCrossDomainSecurityEnabledSet() {
      return this._isSet(38);
   }

   public void setCrossDomainSecurityEnabled(boolean param0) {
      boolean _oldVal = this._CrossDomainSecurityEnabled;
      this._CrossDomainSecurityEnabled = param0;
      this._postSet(38, _oldVal, param0);
   }

   public byte[] getEncryptedAESSecretKey() {
      return this._customizer.getEncryptedAESSecretKey();
   }

   public boolean isEncryptedAESSecretKeyInherited() {
      return false;
   }

   public boolean isEncryptedAESSecretKeySet() {
      return this._isSet(39);
   }

   public void setEncryptedAESSecretKey(byte[] param0) throws InvalidAttributeValueException {
      this._EncryptedAESSecretKey = param0;
   }

   public CertRevocMBean getCertRevoc() {
      return this._CertRevoc;
   }

   public boolean isCertRevocInherited() {
      return false;
   }

   public boolean isCertRevocSet() {
      return this._isSet(40) || this._isAnythingSet((AbstractDescriptorBean)this.getCertRevoc());
   }

   public void setCertRevoc(CertRevocMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 40)) {
         this._postCreate(_child);
      }

      CertRevocMBean _oldVal = this._CertRevoc;
      this._CertRevoc = param0;
      this._postSet(40, _oldVal, param0);
   }

   public boolean isUseKSSForDemo() {
      return this._UseKSSForDemo;
   }

   public boolean isUseKSSForDemoInherited() {
      return false;
   }

   public boolean isUseKSSForDemoSet() {
      return this._isSet(41);
   }

   public void setUseKSSForDemo(boolean param0) {
      SecurityLegalHelper.validateUseKSSForDemo(param0);
      boolean _oldVal = this._UseKSSForDemo;
      this._UseKSSForDemo = param0;
      this._postSet(41, _oldVal, param0);
   }

   public String getAdministrativeIdentityDomain() {
      return this._AdministrativeIdentityDomain;
   }

   public boolean isAdministrativeIdentityDomainInherited() {
      return false;
   }

   public boolean isAdministrativeIdentityDomainSet() {
      return this._isSet(42);
   }

   public void setAdministrativeIdentityDomain(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdministrativeIdentityDomain;
      this._AdministrativeIdentityDomain = param0;
      this._postSet(42, _oldVal, param0);
   }

   public boolean isIdentityDomainAwareProvidersRequired() {
      return this._IdentityDomainAwareProvidersRequired;
   }

   public boolean isIdentityDomainAwareProvidersRequiredInherited() {
      return false;
   }

   public boolean isIdentityDomainAwareProvidersRequiredSet() {
      return this._isSet(43);
   }

   public void setIdentityDomainAwareProvidersRequired(boolean param0) {
      boolean _oldVal = this._IdentityDomainAwareProvidersRequired;
      this._IdentityDomainAwareProvidersRequired = param0;
      this._postSet(43, _oldVal, param0);
   }

   public boolean isIdentityDomainDefaultEnabled() {
      if (!this._isSet(44)) {
         try {
            return System.getProperty("weblogic.security.IdentityDomainDefaultEnabled") != null ? Boolean.getBoolean("weblogic.security.IdentityDomainDefaultEnabled") : true;
         } catch (NullPointerException var2) {
         }
      }

      return this._IdentityDomainDefaultEnabled;
   }

   public boolean isIdentityDomainDefaultEnabledInherited() {
      return false;
   }

   public boolean isIdentityDomainDefaultEnabledSet() {
      return this._isSet(44);
   }

   public void setIdentityDomainDefaultEnabled(boolean param0) {
      boolean _oldVal = this._IdentityDomainDefaultEnabled;
      this._IdentityDomainDefaultEnabled = param0;
      this._postSet(44, _oldVal, param0);
   }

   public int getNonceTimeoutSeconds() {
      return this._NonceTimeoutSeconds;
   }

   public boolean isNonceTimeoutSecondsInherited() {
      return false;
   }

   public boolean isNonceTimeoutSecondsSet() {
      return this._isSet(45);
   }

   public void setNonceTimeoutSeconds(int param0) {
      LegalChecks.checkMin("NonceTimeoutSeconds", param0, 15);
      int _oldVal = this._NonceTimeoutSeconds;
      this._NonceTimeoutSeconds = param0;
      this._postSet(45, _oldVal, param0);
   }

   public boolean isRemoteAnonymousJNDIEnabled() {
      if (!this._isSet(46)) {
         return !this._isSecureModeEnabled();
      } else {
         return this._RemoteAnonymousJNDIEnabled;
      }
   }

   public boolean isRemoteAnonymousJNDIEnabledInherited() {
      return false;
   }

   public boolean isRemoteAnonymousJNDIEnabledSet() {
      return this._isSet(46);
   }

   public void setRemoteAnonymousJNDIEnabled(boolean param0) {
      boolean _oldVal = this._RemoteAnonymousJNDIEnabled;
      this._RemoteAnonymousJNDIEnabled = param0;
      this._postSet(46, _oldVal, param0);
   }

   public int getBootAuthenticationRetryCount() {
      return this._BootAuthenticationRetryCount;
   }

   public boolean isBootAuthenticationRetryCountInherited() {
      return false;
   }

   public boolean isBootAuthenticationRetryCountSet() {
      return this._isSet(47);
   }

   public void setBootAuthenticationRetryCount(int param0) {
      LegalChecks.checkMin("BootAuthenticationRetryCount", param0, 0);
      int _oldVal = this._BootAuthenticationRetryCount;
      this._BootAuthenticationRetryCount = param0;
      this._postSet(47, _oldVal, param0);
   }

   public long getBootAuthenticationMaxRetryDelay() {
      return this._BootAuthenticationMaxRetryDelay;
   }

   public boolean isBootAuthenticationMaxRetryDelayInherited() {
      return false;
   }

   public boolean isBootAuthenticationMaxRetryDelaySet() {
      return this._isSet(48);
   }

   public void setBootAuthenticationMaxRetryDelay(long param0) {
      long _oldVal = this._BootAuthenticationMaxRetryDelay;
      this._BootAuthenticationMaxRetryDelay = param0;
      this._postSet(48, _oldVal, param0);
   }

   public boolean isRemoteAnonymousRMIT3Enabled() {
      return this._RemoteAnonymousRMIT3Enabled;
   }

   public boolean isRemoteAnonymousRMIT3EnabledInherited() {
      return false;
   }

   public boolean isRemoteAnonymousRMIT3EnabledSet() {
      return this._isSet(49);
   }

   public void setRemoteAnonymousRMIT3Enabled(boolean param0) {
      boolean _oldVal = this._RemoteAnonymousRMIT3Enabled;
      this._RemoteAnonymousRMIT3Enabled = param0;
      this._postSet(49, _oldVal, param0);
   }

   public boolean isRemoteAnonymousRMIIIOPEnabled() {
      return this._RemoteAnonymousRMIIIOPEnabled;
   }

   public boolean isRemoteAnonymousRMIIIOPEnabledInherited() {
      return false;
   }

   public boolean isRemoteAnonymousRMIIIOPEnabledSet() {
      return this._isSet(50);
   }

   public void setRemoteAnonymousRMIIIOPEnabled(boolean param0) {
      boolean _oldVal = this._RemoteAnonymousRMIIIOPEnabled;
      this._RemoteAnonymousRMIIIOPEnabled = param0;
      this._postSet(50, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      SecurityLegalHelper.validateSecurityConfiguration(this);
   }

   public void setCredentialEncrypted(byte[] param0) {
      byte[] _oldVal = this._CredentialEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CredentialEncrypted of SecurityConfigurationMBean");
      } else {
         this._getHelper()._clearArray(this._CredentialEncrypted);
         this._CredentialEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(20, _oldVal, param0);
      }
   }

   public void setNodeManagerPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._NodeManagerPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: NodeManagerPasswordEncrypted of SecurityConfigurationMBean");
      } else {
         this._getHelper()._clearArray(this._NodeManagerPasswordEncrypted);
         this._NodeManagerPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(29, _oldVal, param0);
      }
   }

   protected void _postCreate() {
      this._customizer._postCreate();
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
         if (idx == 19) {
            this._markSet(20, false);
         }

         if (idx == 28) {
            this._markSet(29, false);
         }
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet() || this.isCertRevocSet() || this.isJASPICSet() || this.isSecureModeSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 42;
      }

      try {
         switch (idx) {
            case 42:
               this._AdministrativeIdentityDomain = null;
               if (initOne) {
                  break;
               }
            case 48:
               this._BootAuthenticationMaxRetryDelay = 60000L;
               if (initOne) {
                  break;
               }
            case 47:
               this._BootAuthenticationRetryCount = 0;
               if (initOne) {
                  break;
               }
            case 40:
               this._CertRevoc = new CertRevocMBeanImpl(this, 40);
               this._postCreate((AbstractDescriptorBean)this._CertRevoc);
               if (initOne) {
                  break;
               }
            case 26:
               this._CompatibilityConnectionFiltersEnabled = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._ConnectionFilter = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._ConnectionFilterRules = new String[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._ConnectionLoggerEnabled = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._CredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._CredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._DefaultRealm = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._customizer.setDefaultRealmInternal((RealmMBean)null);
               if (initOne) {
                  break;
               }
            case 32:
               this._DowngradeUntrustedPrincipals = false;
               if (initOne) {
                  break;
               }
            case 39:
               this._EncryptedAESSecretKey = new byte[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._EncryptedSecretKey = new byte[0];
               if (initOne) {
                  break;
               }
            case 33:
               this._EnforceStrictURLPattern = true;
               if (initOne) {
                  break;
               }
            case 34:
               this._EnforceValidBasicAuthCredentials = true;
               if (initOne) {
                  break;
               }
            case 37:
               this._ExcludedDomainNames = new String[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._JASPIC = new JASPICMBeanImpl(this, 11);
               this._postCreate((AbstractDescriptorBean)this._JASPIC);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 28:
               this._NodeManagerPasswordEncrypted = "".getBytes();
               if (initOne) {
                  break;
               }
            case 29:
               this._NodeManagerPasswordEncrypted = "".getBytes();
               if (initOne) {
                  break;
               }
            case 27:
               this._NodeManagerUsername = "";
               if (initOne) {
                  break;
               }
            case 45:
               this._NonceTimeoutSeconds = 120;
               if (initOne) {
                  break;
               }
            case 22:
               this._RealmBootStrapVersion = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Realms = new RealmMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._Salt = new byte[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._SecureMode = new SecureModeMBeanImpl(this, 10);
               this._postCreate((AbstractDescriptorBean)this._SecureMode);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 21:
               this._WebAppFilesCaseInsensitive = "false";
               if (initOne) {
                  break;
               }
            case 16:
               this._AnonymousAdminLookupEnabled = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._ClearTextCredentialAccessEnabled = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._ConsoleFullDelegationEnabled = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._customizer.setCredentialGenerated(true);
               if (initOne) {
                  break;
               }
            case 38:
               this._CrossDomainSecurityEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 43:
               this._IdentityDomainAwareProvidersRequired = false;
               if (initOne) {
                  break;
               }
            case 44:
               this._IdentityDomainDefaultEnabled = false;
               if (initOne) {
                  break;
               }
            case 30:
               this._PrincipalEqualsCaseInsensitive = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._PrincipalEqualsCompareDnAndGuid = false;
               if (initOne) {
                  break;
               }
            case 46:
               this._RemoteAnonymousJNDIEnabled = true;
               if (initOne) {
                  break;
               }
            case 50:
               this._RemoteAnonymousRMIIIOPEnabled = true;
               if (initOne) {
                  break;
               }
            case 49:
               this._RemoteAnonymousRMIT3Enabled = true;
               if (initOne) {
                  break;
               }
            case 41:
               this._UseKSSForDemo = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "SecurityConfiguration";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AdministrativeIdentityDomain")) {
         oldVal = this._AdministrativeIdentityDomain;
         this._AdministrativeIdentityDomain = (String)v;
         this._postSet(42, oldVal, this._AdministrativeIdentityDomain);
      } else {
         boolean oldVal;
         if (name.equals("AnonymousAdminLookupEnabled")) {
            oldVal = this._AnonymousAdminLookupEnabled;
            this._AnonymousAdminLookupEnabled = (Boolean)v;
            this._postSet(16, oldVal, this._AnonymousAdminLookupEnabled);
         } else if (name.equals("BootAuthenticationMaxRetryDelay")) {
            long oldVal = this._BootAuthenticationMaxRetryDelay;
            this._BootAuthenticationMaxRetryDelay = (Long)v;
            this._postSet(48, oldVal, this._BootAuthenticationMaxRetryDelay);
         } else {
            int oldVal;
            if (name.equals("BootAuthenticationRetryCount")) {
               oldVal = this._BootAuthenticationRetryCount;
               this._BootAuthenticationRetryCount = (Integer)v;
               this._postSet(47, oldVal, this._BootAuthenticationRetryCount);
            } else if (name.equals("CertRevoc")) {
               CertRevocMBean oldVal = this._CertRevoc;
               this._CertRevoc = (CertRevocMBean)v;
               this._postSet(40, oldVal, this._CertRevoc);
            } else if (name.equals("ClearTextCredentialAccessEnabled")) {
               oldVal = this._ClearTextCredentialAccessEnabled;
               this._ClearTextCredentialAccessEnabled = (Boolean)v;
               this._postSet(17, oldVal, this._ClearTextCredentialAccessEnabled);
            } else if (name.equals("CompatibilityConnectionFiltersEnabled")) {
               oldVal = this._CompatibilityConnectionFiltersEnabled;
               this._CompatibilityConnectionFiltersEnabled = (Boolean)v;
               this._postSet(26, oldVal, this._CompatibilityConnectionFiltersEnabled);
            } else if (name.equals("ConnectionFilter")) {
               oldVal = this._ConnectionFilter;
               this._ConnectionFilter = (String)v;
               this._postSet(23, oldVal, this._ConnectionFilter);
            } else {
               String[] oldVal;
               if (name.equals("ConnectionFilterRules")) {
                  oldVal = this._ConnectionFilterRules;
                  this._ConnectionFilterRules = (String[])((String[])v);
                  this._postSet(24, oldVal, this._ConnectionFilterRules);
               } else if (name.equals("ConnectionLoggerEnabled")) {
                  oldVal = this._ConnectionLoggerEnabled;
                  this._ConnectionLoggerEnabled = (Boolean)v;
                  this._postSet(25, oldVal, this._ConnectionLoggerEnabled);
               } else if (name.equals("ConsoleFullDelegationEnabled")) {
                  oldVal = this._ConsoleFullDelegationEnabled;
                  this._ConsoleFullDelegationEnabled = (Boolean)v;
                  this._postSet(35, oldVal, this._ConsoleFullDelegationEnabled);
               } else if (name.equals("Credential")) {
                  oldVal = this._Credential;
                  this._Credential = (String)v;
                  this._postSet(19, oldVal, this._Credential);
               } else {
                  byte[] oldVal;
                  if (name.equals("CredentialEncrypted")) {
                     oldVal = this._CredentialEncrypted;
                     this._CredentialEncrypted = (byte[])((byte[])v);
                     this._postSet(20, oldVal, this._CredentialEncrypted);
                  } else if (name.equals("CredentialGenerated")) {
                     oldVal = this._CredentialGenerated;
                     this._CredentialGenerated = (Boolean)v;
                     this._postSet(18, oldVal, this._CredentialGenerated);
                  } else if (name.equals("CrossDomainSecurityEnabled")) {
                     oldVal = this._CrossDomainSecurityEnabled;
                     this._CrossDomainSecurityEnabled = (Boolean)v;
                     this._postSet(38, oldVal, this._CrossDomainSecurityEnabled);
                  } else {
                     RealmMBean oldVal;
                     if (name.equals("DefaultRealm")) {
                        oldVal = this._DefaultRealm;
                        this._DefaultRealm = (RealmMBean)v;
                        this._postSet(13, oldVal, this._DefaultRealm);
                     } else if (name.equals("DefaultRealmInternal")) {
                        oldVal = this._DefaultRealmInternal;
                        this._DefaultRealmInternal = (RealmMBean)v;
                        this._postSet(36, oldVal, this._DefaultRealmInternal);
                     } else if (name.equals("DowngradeUntrustedPrincipals")) {
                        oldVal = this._DowngradeUntrustedPrincipals;
                        this._DowngradeUntrustedPrincipals = (Boolean)v;
                        this._postSet(32, oldVal, this._DowngradeUntrustedPrincipals);
                     } else if (name.equals("DynamicallyCreated")) {
                        oldVal = this._DynamicallyCreated;
                        this._DynamicallyCreated = (Boolean)v;
                        this._postSet(7, oldVal, this._DynamicallyCreated);
                     } else if (name.equals("EncryptedAESSecretKey")) {
                        oldVal = this._EncryptedAESSecretKey;
                        this._EncryptedAESSecretKey = (byte[])((byte[])v);
                        this._postSet(39, oldVal, this._EncryptedAESSecretKey);
                     } else if (name.equals("EncryptedSecretKey")) {
                        oldVal = this._EncryptedSecretKey;
                        this._EncryptedSecretKey = (byte[])((byte[])v);
                        this._postSet(15, oldVal, this._EncryptedSecretKey);
                     } else if (name.equals("EnforceStrictURLPattern")) {
                        oldVal = this._EnforceStrictURLPattern;
                        this._EnforceStrictURLPattern = (Boolean)v;
                        this._postSet(33, oldVal, this._EnforceStrictURLPattern);
                     } else if (name.equals("EnforceValidBasicAuthCredentials")) {
                        oldVal = this._EnforceValidBasicAuthCredentials;
                        this._EnforceValidBasicAuthCredentials = (Boolean)v;
                        this._postSet(34, oldVal, this._EnforceValidBasicAuthCredentials);
                     } else if (name.equals("ExcludedDomainNames")) {
                        oldVal = this._ExcludedDomainNames;
                        this._ExcludedDomainNames = (String[])((String[])v);
                        this._postSet(37, oldVal, this._ExcludedDomainNames);
                     } else if (name.equals("IdentityDomainAwareProvidersRequired")) {
                        oldVal = this._IdentityDomainAwareProvidersRequired;
                        this._IdentityDomainAwareProvidersRequired = (Boolean)v;
                        this._postSet(43, oldVal, this._IdentityDomainAwareProvidersRequired);
                     } else if (name.equals("IdentityDomainDefaultEnabled")) {
                        oldVal = this._IdentityDomainDefaultEnabled;
                        this._IdentityDomainDefaultEnabled = (Boolean)v;
                        this._postSet(44, oldVal, this._IdentityDomainDefaultEnabled);
                     } else if (name.equals("JASPIC")) {
                        JASPICMBean oldVal = this._JASPIC;
                        this._JASPIC = (JASPICMBean)v;
                        this._postSet(11, oldVal, this._JASPIC);
                     } else if (name.equals("Name")) {
                        oldVal = this._Name;
                        this._Name = (String)v;
                        this._postSet(2, oldVal, this._Name);
                     } else if (name.equals("NodeManagerPassword")) {
                        oldVal = this._NodeManagerPassword;
                        this._NodeManagerPassword = (String)v;
                        this._postSet(28, oldVal, this._NodeManagerPassword);
                     } else if (name.equals("NodeManagerPasswordEncrypted")) {
                        oldVal = this._NodeManagerPasswordEncrypted;
                        this._NodeManagerPasswordEncrypted = (byte[])((byte[])v);
                        this._postSet(29, oldVal, this._NodeManagerPasswordEncrypted);
                     } else if (name.equals("NodeManagerUsername")) {
                        oldVal = this._NodeManagerUsername;
                        this._NodeManagerUsername = (String)v;
                        this._postSet(27, oldVal, this._NodeManagerUsername);
                     } else if (name.equals("NonceTimeoutSeconds")) {
                        oldVal = this._NonceTimeoutSeconds;
                        this._NonceTimeoutSeconds = (Integer)v;
                        this._postSet(45, oldVal, this._NonceTimeoutSeconds);
                     } else if (name.equals("PrincipalEqualsCaseInsensitive")) {
                        oldVal = this._PrincipalEqualsCaseInsensitive;
                        this._PrincipalEqualsCaseInsensitive = (Boolean)v;
                        this._postSet(30, oldVal, this._PrincipalEqualsCaseInsensitive);
                     } else if (name.equals("PrincipalEqualsCompareDnAndGuid")) {
                        oldVal = this._PrincipalEqualsCompareDnAndGuid;
                        this._PrincipalEqualsCompareDnAndGuid = (Boolean)v;
                        this._postSet(31, oldVal, this._PrincipalEqualsCompareDnAndGuid);
                     } else if (name.equals("RealmBootStrapVersion")) {
                        oldVal = this._RealmBootStrapVersion;
                        this._RealmBootStrapVersion = (String)v;
                        this._postSet(22, oldVal, this._RealmBootStrapVersion);
                     } else if (name.equals("Realms")) {
                        RealmMBean[] oldVal = this._Realms;
                        this._Realms = (RealmMBean[])((RealmMBean[])v);
                        this._postSet(12, oldVal, this._Realms);
                     } else if (name.equals("RemoteAnonymousJNDIEnabled")) {
                        oldVal = this._RemoteAnonymousJNDIEnabled;
                        this._RemoteAnonymousJNDIEnabled = (Boolean)v;
                        this._postSet(46, oldVal, this._RemoteAnonymousJNDIEnabled);
                     } else if (name.equals("RemoteAnonymousRMIIIOPEnabled")) {
                        oldVal = this._RemoteAnonymousRMIIIOPEnabled;
                        this._RemoteAnonymousRMIIIOPEnabled = (Boolean)v;
                        this._postSet(50, oldVal, this._RemoteAnonymousRMIIIOPEnabled);
                     } else if (name.equals("RemoteAnonymousRMIT3Enabled")) {
                        oldVal = this._RemoteAnonymousRMIT3Enabled;
                        this._RemoteAnonymousRMIT3Enabled = (Boolean)v;
                        this._postSet(49, oldVal, this._RemoteAnonymousRMIT3Enabled);
                     } else if (name.equals("Salt")) {
                        oldVal = this._Salt;
                        this._Salt = (byte[])((byte[])v);
                        this._postSet(14, oldVal, this._Salt);
                     } else if (name.equals("SecureMode")) {
                        SecureModeMBean oldVal = this._SecureMode;
                        this._SecureMode = (SecureModeMBean)v;
                        this._postSet(10, oldVal, this._SecureMode);
                     } else if (name.equals("Tags")) {
                        oldVal = this._Tags;
                        this._Tags = (String[])((String[])v);
                        this._postSet(9, oldVal, this._Tags);
                     } else if (name.equals("UseKSSForDemo")) {
                        oldVal = this._UseKSSForDemo;
                        this._UseKSSForDemo = (Boolean)v;
                        this._postSet(41, oldVal, this._UseKSSForDemo);
                     } else if (name.equals("WebAppFilesCaseInsensitive")) {
                        oldVal = this._WebAppFilesCaseInsensitive;
                        this._WebAppFilesCaseInsensitive = (String)v;
                        this._postSet(21, oldVal, this._WebAppFilesCaseInsensitive);
                     } else if (name.equals("customizer")) {
                        SecurityConfiguration oldVal = this._customizer;
                        this._customizer = (SecurityConfiguration)v;
                     } else {
                        super.putValue(name, v);
                     }
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AdministrativeIdentityDomain")) {
         return this._AdministrativeIdentityDomain;
      } else if (name.equals("AnonymousAdminLookupEnabled")) {
         return new Boolean(this._AnonymousAdminLookupEnabled);
      } else if (name.equals("BootAuthenticationMaxRetryDelay")) {
         return new Long(this._BootAuthenticationMaxRetryDelay);
      } else if (name.equals("BootAuthenticationRetryCount")) {
         return new Integer(this._BootAuthenticationRetryCount);
      } else if (name.equals("CertRevoc")) {
         return this._CertRevoc;
      } else if (name.equals("ClearTextCredentialAccessEnabled")) {
         return new Boolean(this._ClearTextCredentialAccessEnabled);
      } else if (name.equals("CompatibilityConnectionFiltersEnabled")) {
         return new Boolean(this._CompatibilityConnectionFiltersEnabled);
      } else if (name.equals("ConnectionFilter")) {
         return this._ConnectionFilter;
      } else if (name.equals("ConnectionFilterRules")) {
         return this._ConnectionFilterRules;
      } else if (name.equals("ConnectionLoggerEnabled")) {
         return new Boolean(this._ConnectionLoggerEnabled);
      } else if (name.equals("ConsoleFullDelegationEnabled")) {
         return new Boolean(this._ConsoleFullDelegationEnabled);
      } else if (name.equals("Credential")) {
         return this._Credential;
      } else if (name.equals("CredentialEncrypted")) {
         return this._CredentialEncrypted;
      } else if (name.equals("CredentialGenerated")) {
         return new Boolean(this._CredentialGenerated);
      } else if (name.equals("CrossDomainSecurityEnabled")) {
         return new Boolean(this._CrossDomainSecurityEnabled);
      } else if (name.equals("DefaultRealm")) {
         return this._DefaultRealm;
      } else if (name.equals("DefaultRealmInternal")) {
         return this._DefaultRealmInternal;
      } else if (name.equals("DowngradeUntrustedPrincipals")) {
         return new Boolean(this._DowngradeUntrustedPrincipals);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EncryptedAESSecretKey")) {
         return this._EncryptedAESSecretKey;
      } else if (name.equals("EncryptedSecretKey")) {
         return this._EncryptedSecretKey;
      } else if (name.equals("EnforceStrictURLPattern")) {
         return new Boolean(this._EnforceStrictURLPattern);
      } else if (name.equals("EnforceValidBasicAuthCredentials")) {
         return new Boolean(this._EnforceValidBasicAuthCredentials);
      } else if (name.equals("ExcludedDomainNames")) {
         return this._ExcludedDomainNames;
      } else if (name.equals("IdentityDomainAwareProvidersRequired")) {
         return new Boolean(this._IdentityDomainAwareProvidersRequired);
      } else if (name.equals("IdentityDomainDefaultEnabled")) {
         return new Boolean(this._IdentityDomainDefaultEnabled);
      } else if (name.equals("JASPIC")) {
         return this._JASPIC;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NodeManagerPassword")) {
         return this._NodeManagerPassword;
      } else if (name.equals("NodeManagerPasswordEncrypted")) {
         return this._NodeManagerPasswordEncrypted;
      } else if (name.equals("NodeManagerUsername")) {
         return this._NodeManagerUsername;
      } else if (name.equals("NonceTimeoutSeconds")) {
         return new Integer(this._NonceTimeoutSeconds);
      } else if (name.equals("PrincipalEqualsCaseInsensitive")) {
         return new Boolean(this._PrincipalEqualsCaseInsensitive);
      } else if (name.equals("PrincipalEqualsCompareDnAndGuid")) {
         return new Boolean(this._PrincipalEqualsCompareDnAndGuid);
      } else if (name.equals("RealmBootStrapVersion")) {
         return this._RealmBootStrapVersion;
      } else if (name.equals("Realms")) {
         return this._Realms;
      } else if (name.equals("RemoteAnonymousJNDIEnabled")) {
         return new Boolean(this._RemoteAnonymousJNDIEnabled);
      } else if (name.equals("RemoteAnonymousRMIIIOPEnabled")) {
         return new Boolean(this._RemoteAnonymousRMIIIOPEnabled);
      } else if (name.equals("RemoteAnonymousRMIT3Enabled")) {
         return new Boolean(this._RemoteAnonymousRMIT3Enabled);
      } else if (name.equals("Salt")) {
         return this._Salt;
      } else if (name.equals("SecureMode")) {
         return this._SecureMode;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UseKSSForDemo")) {
         return new Boolean(this._UseKSSForDemo);
      } else if (name.equals("WebAppFilesCaseInsensitive")) {
         return this._WebAppFilesCaseInsensitive;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }

               if (s.equals("salt")) {
                  return 14;
               }
               break;
            case 5:
               if (s.equals("realm")) {
                  return 12;
               }
               break;
            case 6:
               if (s.equals("jaspic")) {
                  return 11;
               }
            case 7:
            case 8:
            case 9:
            case 12:
            case 14:
            case 16:
            case 18:
            case 27:
            case 28:
            case 32:
            case 34:
            case 37:
            case 38:
            case 39:
            default:
               break;
            case 10:
               if (s.equals("cert-revoc")) {
                  return 40;
               }

               if (s.equals("credential")) {
                  return 19;
               }
               break;
            case 11:
               if (s.equals("secure-mode")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("default-realm")) {
                  return 13;
               }
               break;
            case 15:
               if (s.equals("usekss-for-demo")) {
                  return 41;
               }
               break;
            case 17:
               if (s.equals("connection-filter")) {
                  return 23;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("credential-encrypted")) {
                  return 20;
               }

               if (s.equals("encrypted-secret-key")) {
                  return 15;
               }

               if (s.equals("excluded-domain-name")) {
                  return 37;
               }

               if (s.equals("credential-generated")) {
                  return 18;
               }
               break;
            case 21:
               if (s.equals("node-manager-password")) {
                  return 28;
               }

               if (s.equals("node-manager-username")) {
                  return 27;
               }

               if (s.equals("nonce-timeout-seconds")) {
                  return 45;
               }
               break;
            case 22:
               if (s.equals("connection-filter-rule")) {
                  return 24;
               }

               if (s.equals("default-realm-internal")) {
                  return 36;
               }
               break;
            case 23:
               if (s.equals("encryptedaes-secret-key")) {
                  return 39;
               }
               break;
            case 24:
               if (s.equals("realm-boot-strap-version")) {
                  return 22;
               }
               break;
            case 25:
               if (s.equals("connection-logger-enabled")) {
                  return 25;
               }
               break;
            case 26:
               if (s.equals("enforce-strict-url-pattern")) {
                  return 33;
               }
               break;
            case 29:
               if (s.equals("cross-domain-security-enabled")) {
                  return 38;
               }

               if (s.equals("remote-anonymous-jndi-enabled")) {
                  return 46;
               }

               if (s.equals("remote-anonymousrmit3-enabled")) {
                  return 49;
               }
               break;
            case 30:
               if (s.equals("administrative-identity-domain")) {
                  return 42;
               }

               if (s.equals("downgrade-untrusted-principals")) {
                  return 32;
               }

               if (s.equals("web-app-files-case-insensitive")) {
                  return 21;
               }

               if (s.equals("anonymous-admin-lookup-enabled")) {
                  return 16;
               }
               break;
            case 31:
               if (s.equals("boot-authentication-retry-count")) {
                  return 47;
               }

               if (s.equals("node-manager-password-encrypted")) {
                  return 29;
               }

               if (s.equals("console-full-delegation-enabled")) {
                  return 35;
               }

               if (s.equals("identity-domain-default-enabled")) {
                  return 44;
               }

               if (s.equals("remote-anonymousrmiiiop-enabled")) {
                  return 50;
               }
               break;
            case 33:
               if (s.equals("principal-equals-case-insensitive")) {
                  return 30;
               }
               break;
            case 35:
               if (s.equals("boot-authentication-max-retry-delay")) {
                  return 48;
               }
               break;
            case 36:
               if (s.equals("enforce-valid-basic-auth-credentials")) {
                  return 34;
               }

               if (s.equals("clear-text-credential-access-enabled")) {
                  return 17;
               }

               if (s.equals("principal-equals-compare-dn-and-guid")) {
                  return 31;
               }
               break;
            case 40:
               if (s.equals("compatibility-connection-filters-enabled")) {
                  return 26;
               }

               if (s.equals("identity-domain-aware-providers-required")) {
                  return 43;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new SecureModeMBeanImpl.SchemaHelper2();
            case 11:
               return new JASPICMBeanImpl.SchemaHelper2();
            case 12:
               return new RealmMBeanImpl.SchemaHelper2();
            case 40:
               return new CertRevocMBeanImpl.SchemaHelper2();
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
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "secure-mode";
            case 11:
               return "jaspic";
            case 12:
               return "realm";
            case 13:
               return "default-realm";
            case 14:
               return "salt";
            case 15:
               return "encrypted-secret-key";
            case 16:
               return "anonymous-admin-lookup-enabled";
            case 17:
               return "clear-text-credential-access-enabled";
            case 18:
               return "credential-generated";
            case 19:
               return "credential";
            case 20:
               return "credential-encrypted";
            case 21:
               return "web-app-files-case-insensitive";
            case 22:
               return "realm-boot-strap-version";
            case 23:
               return "connection-filter";
            case 24:
               return "connection-filter-rule";
            case 25:
               return "connection-logger-enabled";
            case 26:
               return "compatibility-connection-filters-enabled";
            case 27:
               return "node-manager-username";
            case 28:
               return "node-manager-password";
            case 29:
               return "node-manager-password-encrypted";
            case 30:
               return "principal-equals-case-insensitive";
            case 31:
               return "principal-equals-compare-dn-and-guid";
            case 32:
               return "downgrade-untrusted-principals";
            case 33:
               return "enforce-strict-url-pattern";
            case 34:
               return "enforce-valid-basic-auth-credentials";
            case 35:
               return "console-full-delegation-enabled";
            case 36:
               return "default-realm-internal";
            case 37:
               return "excluded-domain-name";
            case 38:
               return "cross-domain-security-enabled";
            case 39:
               return "encryptedaes-secret-key";
            case 40:
               return "cert-revoc";
            case 41:
               return "usekss-for-demo";
            case 42:
               return "administrative-identity-domain";
            case 43:
               return "identity-domain-aware-providers-required";
            case 44:
               return "identity-domain-default-enabled";
            case 45:
               return "nonce-timeout-seconds";
            case 46:
               return "remote-anonymous-jndi-enabled";
            case 47:
               return "boot-authentication-retry-count";
            case 48:
               return "boot-authentication-max-retry-delay";
            case 49:
               return "remote-anonymousrmit3-enabled";
            case 50:
               return "remote-anonymousrmiiiop-enabled";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 24:
               return true;
            case 37:
               return true;
            case 39:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 40:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private SecurityConfigurationMBeanImpl bean;

      protected Helper(SecurityConfigurationMBeanImpl bean) {
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
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "SecureMode";
            case 11:
               return "JASPIC";
            case 12:
               return "Realms";
            case 13:
               return "DefaultRealm";
            case 14:
               return "Salt";
            case 15:
               return "EncryptedSecretKey";
            case 16:
               return "AnonymousAdminLookupEnabled";
            case 17:
               return "ClearTextCredentialAccessEnabled";
            case 18:
               return "CredentialGenerated";
            case 19:
               return "Credential";
            case 20:
               return "CredentialEncrypted";
            case 21:
               return "WebAppFilesCaseInsensitive";
            case 22:
               return "RealmBootStrapVersion";
            case 23:
               return "ConnectionFilter";
            case 24:
               return "ConnectionFilterRules";
            case 25:
               return "ConnectionLoggerEnabled";
            case 26:
               return "CompatibilityConnectionFiltersEnabled";
            case 27:
               return "NodeManagerUsername";
            case 28:
               return "NodeManagerPassword";
            case 29:
               return "NodeManagerPasswordEncrypted";
            case 30:
               return "PrincipalEqualsCaseInsensitive";
            case 31:
               return "PrincipalEqualsCompareDnAndGuid";
            case 32:
               return "DowngradeUntrustedPrincipals";
            case 33:
               return "EnforceStrictURLPattern";
            case 34:
               return "EnforceValidBasicAuthCredentials";
            case 35:
               return "ConsoleFullDelegationEnabled";
            case 36:
               return "DefaultRealmInternal";
            case 37:
               return "ExcludedDomainNames";
            case 38:
               return "CrossDomainSecurityEnabled";
            case 39:
               return "EncryptedAESSecretKey";
            case 40:
               return "CertRevoc";
            case 41:
               return "UseKSSForDemo";
            case 42:
               return "AdministrativeIdentityDomain";
            case 43:
               return "IdentityDomainAwareProvidersRequired";
            case 44:
               return "IdentityDomainDefaultEnabled";
            case 45:
               return "NonceTimeoutSeconds";
            case 46:
               return "RemoteAnonymousJNDIEnabled";
            case 47:
               return "BootAuthenticationRetryCount";
            case 48:
               return "BootAuthenticationMaxRetryDelay";
            case 49:
               return "RemoteAnonymousRMIT3Enabled";
            case 50:
               return "RemoteAnonymousRMIIIOPEnabled";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdministrativeIdentityDomain")) {
            return 42;
         } else if (propName.equals("BootAuthenticationMaxRetryDelay")) {
            return 48;
         } else if (propName.equals("BootAuthenticationRetryCount")) {
            return 47;
         } else if (propName.equals("CertRevoc")) {
            return 40;
         } else if (propName.equals("CompatibilityConnectionFiltersEnabled")) {
            return 26;
         } else if (propName.equals("ConnectionFilter")) {
            return 23;
         } else if (propName.equals("ConnectionFilterRules")) {
            return 24;
         } else if (propName.equals("ConnectionLoggerEnabled")) {
            return 25;
         } else if (propName.equals("Credential")) {
            return 19;
         } else if (propName.equals("CredentialEncrypted")) {
            return 20;
         } else if (propName.equals("DefaultRealm")) {
            return 13;
         } else if (propName.equals("DefaultRealmInternal")) {
            return 36;
         } else if (propName.equals("DowngradeUntrustedPrincipals")) {
            return 32;
         } else if (propName.equals("EncryptedAESSecretKey")) {
            return 39;
         } else if (propName.equals("EncryptedSecretKey")) {
            return 15;
         } else if (propName.equals("EnforceStrictURLPattern")) {
            return 33;
         } else if (propName.equals("EnforceValidBasicAuthCredentials")) {
            return 34;
         } else if (propName.equals("ExcludedDomainNames")) {
            return 37;
         } else if (propName.equals("JASPIC")) {
            return 11;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NodeManagerPassword")) {
            return 28;
         } else if (propName.equals("NodeManagerPasswordEncrypted")) {
            return 29;
         } else if (propName.equals("NodeManagerUsername")) {
            return 27;
         } else if (propName.equals("NonceTimeoutSeconds")) {
            return 45;
         } else if (propName.equals("RealmBootStrapVersion")) {
            return 22;
         } else if (propName.equals("Realms")) {
            return 12;
         } else if (propName.equals("Salt")) {
            return 14;
         } else if (propName.equals("SecureMode")) {
            return 10;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("WebAppFilesCaseInsensitive")) {
            return 21;
         } else if (propName.equals("AnonymousAdminLookupEnabled")) {
            return 16;
         } else if (propName.equals("ClearTextCredentialAccessEnabled")) {
            return 17;
         } else if (propName.equals("ConsoleFullDelegationEnabled")) {
            return 35;
         } else if (propName.equals("CredentialGenerated")) {
            return 18;
         } else if (propName.equals("CrossDomainSecurityEnabled")) {
            return 38;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("IdentityDomainAwareProvidersRequired")) {
            return 43;
         } else if (propName.equals("IdentityDomainDefaultEnabled")) {
            return 44;
         } else if (propName.equals("PrincipalEqualsCaseInsensitive")) {
            return 30;
         } else if (propName.equals("PrincipalEqualsCompareDnAndGuid")) {
            return 31;
         } else if (propName.equals("RemoteAnonymousJNDIEnabled")) {
            return 46;
         } else if (propName.equals("RemoteAnonymousRMIIIOPEnabled")) {
            return 50;
         } else if (propName.equals("RemoteAnonymousRMIT3Enabled")) {
            return 49;
         } else {
            return propName.equals("UseKSSForDemo") ? 41 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCertRevoc() != null) {
            iterators.add(new ArrayIterator(new CertRevocMBean[]{this.bean.getCertRevoc()}));
         }

         if (this.bean.getJASPIC() != null) {
            iterators.add(new ArrayIterator(new JASPICMBean[]{this.bean.getJASPIC()}));
         }

         iterators.add(new ArrayIterator(this.bean.getRealms()));
         if (this.bean.getSecureMode() != null) {
            iterators.add(new ArrayIterator(new SecureModeMBean[]{this.bean.getSecureMode()}));
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
            if (this.bean.isAdministrativeIdentityDomainSet()) {
               buf.append("AdministrativeIdentityDomain");
               buf.append(String.valueOf(this.bean.getAdministrativeIdentityDomain()));
            }

            if (this.bean.isBootAuthenticationMaxRetryDelaySet()) {
               buf.append("BootAuthenticationMaxRetryDelay");
               buf.append(String.valueOf(this.bean.getBootAuthenticationMaxRetryDelay()));
            }

            if (this.bean.isBootAuthenticationRetryCountSet()) {
               buf.append("BootAuthenticationRetryCount");
               buf.append(String.valueOf(this.bean.getBootAuthenticationRetryCount()));
            }

            childValue = this.computeChildHashValue(this.bean.getCertRevoc());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCompatibilityConnectionFiltersEnabledSet()) {
               buf.append("CompatibilityConnectionFiltersEnabled");
               buf.append(String.valueOf(this.bean.getCompatibilityConnectionFiltersEnabled()));
            }

            if (this.bean.isConnectionFilterSet()) {
               buf.append("ConnectionFilter");
               buf.append(String.valueOf(this.bean.getConnectionFilter()));
            }

            if (this.bean.isConnectionFilterRulesSet()) {
               buf.append("ConnectionFilterRules");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConnectionFilterRules())));
            }

            if (this.bean.isConnectionLoggerEnabledSet()) {
               buf.append("ConnectionLoggerEnabled");
               buf.append(String.valueOf(this.bean.getConnectionLoggerEnabled()));
            }

            if (this.bean.isCredentialSet()) {
               buf.append("Credential");
               buf.append(String.valueOf(this.bean.getCredential()));
            }

            if (this.bean.isCredentialEncryptedSet()) {
               buf.append("CredentialEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCredentialEncrypted())));
            }

            if (this.bean.isDefaultRealmSet()) {
               buf.append("DefaultRealm");
               buf.append(String.valueOf(this.bean.getDefaultRealm()));
            }

            if (this.bean.isDefaultRealmInternalSet()) {
               buf.append("DefaultRealmInternal");
               buf.append(String.valueOf(this.bean.getDefaultRealmInternal()));
            }

            if (this.bean.isDowngradeUntrustedPrincipalsSet()) {
               buf.append("DowngradeUntrustedPrincipals");
               buf.append(String.valueOf(this.bean.getDowngradeUntrustedPrincipals()));
            }

            if (this.bean.isEncryptedAESSecretKeySet()) {
               buf.append("EncryptedAESSecretKey");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEncryptedAESSecretKey())));
            }

            if (this.bean.isEncryptedSecretKeySet()) {
               buf.append("EncryptedSecretKey");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEncryptedSecretKey())));
            }

            if (this.bean.isEnforceStrictURLPatternSet()) {
               buf.append("EnforceStrictURLPattern");
               buf.append(String.valueOf(this.bean.getEnforceStrictURLPattern()));
            }

            if (this.bean.isEnforceValidBasicAuthCredentialsSet()) {
               buf.append("EnforceValidBasicAuthCredentials");
               buf.append(String.valueOf(this.bean.getEnforceValidBasicAuthCredentials()));
            }

            if (this.bean.isExcludedDomainNamesSet()) {
               buf.append("ExcludedDomainNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExcludedDomainNames())));
            }

            childValue = this.computeChildHashValue(this.bean.getJASPIC());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNodeManagerPasswordSet()) {
               buf.append("NodeManagerPassword");
               buf.append(String.valueOf(this.bean.getNodeManagerPassword()));
            }

            if (this.bean.isNodeManagerPasswordEncryptedSet()) {
               buf.append("NodeManagerPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getNodeManagerPasswordEncrypted())));
            }

            if (this.bean.isNodeManagerUsernameSet()) {
               buf.append("NodeManagerUsername");
               buf.append(String.valueOf(this.bean.getNodeManagerUsername()));
            }

            if (this.bean.isNonceTimeoutSecondsSet()) {
               buf.append("NonceTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getNonceTimeoutSeconds()));
            }

            if (this.bean.isRealmBootStrapVersionSet()) {
               buf.append("RealmBootStrapVersion");
               buf.append(String.valueOf(this.bean.getRealmBootStrapVersion()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getRealms().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRealms()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSaltSet()) {
               buf.append("Salt");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSalt())));
            }

            childValue = this.computeChildHashValue(this.bean.getSecureMode());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isWebAppFilesCaseInsensitiveSet()) {
               buf.append("WebAppFilesCaseInsensitive");
               buf.append(String.valueOf(this.bean.getWebAppFilesCaseInsensitive()));
            }

            if (this.bean.isAnonymousAdminLookupEnabledSet()) {
               buf.append("AnonymousAdminLookupEnabled");
               buf.append(String.valueOf(this.bean.isAnonymousAdminLookupEnabled()));
            }

            if (this.bean.isClearTextCredentialAccessEnabledSet()) {
               buf.append("ClearTextCredentialAccessEnabled");
               buf.append(String.valueOf(this.bean.isClearTextCredentialAccessEnabled()));
            }

            if (this.bean.isConsoleFullDelegationEnabledSet()) {
               buf.append("ConsoleFullDelegationEnabled");
               buf.append(String.valueOf(this.bean.isConsoleFullDelegationEnabled()));
            }

            if (this.bean.isCredentialGeneratedSet()) {
               buf.append("CredentialGenerated");
               buf.append(String.valueOf(this.bean.isCredentialGenerated()));
            }

            if (this.bean.isCrossDomainSecurityEnabledSet()) {
               buf.append("CrossDomainSecurityEnabled");
               buf.append(String.valueOf(this.bean.isCrossDomainSecurityEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isIdentityDomainAwareProvidersRequiredSet()) {
               buf.append("IdentityDomainAwareProvidersRequired");
               buf.append(String.valueOf(this.bean.isIdentityDomainAwareProvidersRequired()));
            }

            if (this.bean.isIdentityDomainDefaultEnabledSet()) {
               buf.append("IdentityDomainDefaultEnabled");
               buf.append(String.valueOf(this.bean.isIdentityDomainDefaultEnabled()));
            }

            if (this.bean.isPrincipalEqualsCaseInsensitiveSet()) {
               buf.append("PrincipalEqualsCaseInsensitive");
               buf.append(String.valueOf(this.bean.isPrincipalEqualsCaseInsensitive()));
            }

            if (this.bean.isPrincipalEqualsCompareDnAndGuidSet()) {
               buf.append("PrincipalEqualsCompareDnAndGuid");
               buf.append(String.valueOf(this.bean.isPrincipalEqualsCompareDnAndGuid()));
            }

            if (this.bean.isRemoteAnonymousJNDIEnabledSet()) {
               buf.append("RemoteAnonymousJNDIEnabled");
               buf.append(String.valueOf(this.bean.isRemoteAnonymousJNDIEnabled()));
            }

            if (this.bean.isRemoteAnonymousRMIIIOPEnabledSet()) {
               buf.append("RemoteAnonymousRMIIIOPEnabled");
               buf.append(String.valueOf(this.bean.isRemoteAnonymousRMIIIOPEnabled()));
            }

            if (this.bean.isRemoteAnonymousRMIT3EnabledSet()) {
               buf.append("RemoteAnonymousRMIT3Enabled");
               buf.append(String.valueOf(this.bean.isRemoteAnonymousRMIT3Enabled()));
            }

            if (this.bean.isUseKSSForDemoSet()) {
               buf.append("UseKSSForDemo");
               buf.append(String.valueOf(this.bean.isUseKSSForDemo()));
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
            SecurityConfigurationMBeanImpl otherTyped = (SecurityConfigurationMBeanImpl)other;
            this.computeDiff("AdministrativeIdentityDomain", this.bean.getAdministrativeIdentityDomain(), otherTyped.getAdministrativeIdentityDomain(), false);
            this.computeDiff("BootAuthenticationMaxRetryDelay", this.bean.getBootAuthenticationMaxRetryDelay(), otherTyped.getBootAuthenticationMaxRetryDelay(), false);
            this.computeDiff("BootAuthenticationRetryCount", this.bean.getBootAuthenticationRetryCount(), otherTyped.getBootAuthenticationRetryCount(), false);
            this.computeSubDiff("CertRevoc", this.bean.getCertRevoc(), otherTyped.getCertRevoc());
            this.computeDiff("CompatibilityConnectionFiltersEnabled", this.bean.getCompatibilityConnectionFiltersEnabled(), otherTyped.getCompatibilityConnectionFiltersEnabled(), true);
            this.computeDiff("ConnectionFilter", this.bean.getConnectionFilter(), otherTyped.getConnectionFilter(), true);
            this.computeDiff("ConnectionFilterRules", this.bean.getConnectionFilterRules(), otherTyped.getConnectionFilterRules(), true);
            this.computeDiff("ConnectionLoggerEnabled", this.bean.getConnectionLoggerEnabled(), otherTyped.getConnectionLoggerEnabled(), true);
            this.computeDiff("CredentialEncrypted", this.bean.getCredentialEncrypted(), otherTyped.getCredentialEncrypted(), true);
            this.computeDiff("DefaultRealm", this.bean.getDefaultRealm(), otherTyped.getDefaultRealm(), false);
            this.computeDiff("DefaultRealmInternal", this.bean.getDefaultRealmInternal(), otherTyped.getDefaultRealmInternal(), false);
            this.computeDiff("DowngradeUntrustedPrincipals", this.bean.getDowngradeUntrustedPrincipals(), otherTyped.getDowngradeUntrustedPrincipals(), true);
            this.computeDiff("EnforceStrictURLPattern", this.bean.getEnforceStrictURLPattern(), otherTyped.getEnforceStrictURLPattern(), false);
            this.computeDiff("EnforceValidBasicAuthCredentials", this.bean.getEnforceValidBasicAuthCredentials(), otherTyped.getEnforceValidBasicAuthCredentials(), false);
            this.computeDiff("ExcludedDomainNames", this.bean.getExcludedDomainNames(), otherTyped.getExcludedDomainNames(), true);
            this.computeSubDiff("JASPIC", this.bean.getJASPIC(), otherTyped.getJASPIC());
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NodeManagerPasswordEncrypted", this.bean.getNodeManagerPasswordEncrypted(), otherTyped.getNodeManagerPasswordEncrypted(), true);
            this.computeDiff("NodeManagerUsername", this.bean.getNodeManagerUsername(), otherTyped.getNodeManagerUsername(), true);
            this.computeDiff("NonceTimeoutSeconds", this.bean.getNonceTimeoutSeconds(), otherTyped.getNonceTimeoutSeconds(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RealmBootStrapVersion", this.bean.getRealmBootStrapVersion(), otherTyped.getRealmBootStrapVersion(), false);
            }

            this.computeChildDiff("Realms", this.bean.getRealms(), otherTyped.getRealms(), true);
            this.computeSubDiff("SecureMode", this.bean.getSecureMode(), otherTyped.getSecureMode());
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("WebAppFilesCaseInsensitive", this.bean.getWebAppFilesCaseInsensitive(), otherTyped.getWebAppFilesCaseInsensitive(), false);
            this.computeDiff("AnonymousAdminLookupEnabled", this.bean.isAnonymousAdminLookupEnabled(), otherTyped.isAnonymousAdminLookupEnabled(), false);
            this.computeDiff("ClearTextCredentialAccessEnabled", this.bean.isClearTextCredentialAccessEnabled(), otherTyped.isClearTextCredentialAccessEnabled(), true);
            this.computeDiff("ConsoleFullDelegationEnabled", this.bean.isConsoleFullDelegationEnabled(), otherTyped.isConsoleFullDelegationEnabled(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("CredentialGenerated", this.bean.isCredentialGenerated(), otherTyped.isCredentialGenerated(), false);
            }

            this.computeDiff("CrossDomainSecurityEnabled", this.bean.isCrossDomainSecurityEnabled(), otherTyped.isCrossDomainSecurityEnabled(), true);
            this.computeDiff("IdentityDomainAwareProvidersRequired", this.bean.isIdentityDomainAwareProvidersRequired(), otherTyped.isIdentityDomainAwareProvidersRequired(), true);
            this.computeDiff("IdentityDomainDefaultEnabled", this.bean.isIdentityDomainDefaultEnabled(), otherTyped.isIdentityDomainDefaultEnabled(), true);
            this.computeDiff("PrincipalEqualsCaseInsensitive", this.bean.isPrincipalEqualsCaseInsensitive(), otherTyped.isPrincipalEqualsCaseInsensitive(), true);
            this.computeDiff("PrincipalEqualsCompareDnAndGuid", this.bean.isPrincipalEqualsCompareDnAndGuid(), otherTyped.isPrincipalEqualsCompareDnAndGuid(), true);
            this.computeDiff("RemoteAnonymousJNDIEnabled", this.bean.isRemoteAnonymousJNDIEnabled(), otherTyped.isRemoteAnonymousJNDIEnabled(), false);
            this.computeDiff("RemoteAnonymousRMIIIOPEnabled", this.bean.isRemoteAnonymousRMIIIOPEnabled(), otherTyped.isRemoteAnonymousRMIIIOPEnabled(), true);
            this.computeDiff("RemoteAnonymousRMIT3Enabled", this.bean.isRemoteAnonymousRMIT3Enabled(), otherTyped.isRemoteAnonymousRMIT3Enabled(), true);
            this.computeDiff("UseKSSForDemo", this.bean.isUseKSSForDemo(), otherTyped.isUseKSSForDemo(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecurityConfigurationMBeanImpl original = (SecurityConfigurationMBeanImpl)event.getSourceBean();
            SecurityConfigurationMBeanImpl proposed = (SecurityConfigurationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdministrativeIdentityDomain")) {
                  original.setAdministrativeIdentityDomain(proposed.getAdministrativeIdentityDomain());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("BootAuthenticationMaxRetryDelay")) {
                  original.setBootAuthenticationMaxRetryDelay(proposed.getBootAuthenticationMaxRetryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("BootAuthenticationRetryCount")) {
                  original.setBootAuthenticationRetryCount(proposed.getBootAuthenticationRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 47);
               } else if (prop.equals("CertRevoc")) {
                  if (type == 2) {
                     original.setCertRevoc((CertRevocMBean)this.createCopy((AbstractDescriptorBean)proposed.getCertRevoc()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CertRevoc", original.getCertRevoc());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("CompatibilityConnectionFiltersEnabled")) {
                  original.setCompatibilityConnectionFiltersEnabled(proposed.getCompatibilityConnectionFiltersEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("ConnectionFilter")) {
                  original.setConnectionFilter(proposed.getConnectionFilter());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ConnectionFilterRules")) {
                  original.setConnectionFilterRules(proposed.getConnectionFilterRules());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("ConnectionLoggerEnabled")) {
                  original.setConnectionLoggerEnabled(proposed.getConnectionLoggerEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (!prop.equals("Credential")) {
                  if (prop.equals("CredentialEncrypted")) {
                     original.setCredentialEncrypted(proposed.getCredentialEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("DefaultRealm")) {
                     original.setDefaultRealmAsString(proposed.getDefaultRealmAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("DefaultRealmInternal")) {
                     original.setDefaultRealmInternalAsString(proposed.getDefaultRealmInternalAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  } else if (prop.equals("DowngradeUntrustedPrincipals")) {
                     original.setDowngradeUntrustedPrincipals(proposed.getDowngradeUntrustedPrincipals());
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
                  } else if (!prop.equals("EncryptedAESSecretKey") && !prop.equals("EncryptedSecretKey")) {
                     if (prop.equals("EnforceStrictURLPattern")) {
                        original.setEnforceStrictURLPattern(proposed.getEnforceStrictURLPattern());
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (prop.equals("EnforceValidBasicAuthCredentials")) {
                        original.setEnforceValidBasicAuthCredentials(proposed.getEnforceValidBasicAuthCredentials());
                        original._conditionalUnset(update.isUnsetUpdate(), 34);
                     } else if (prop.equals("ExcludedDomainNames")) {
                        original.setExcludedDomainNames(proposed.getExcludedDomainNames());
                        original._conditionalUnset(update.isUnsetUpdate(), 37);
                     } else if (prop.equals("JASPIC")) {
                        if (type == 2) {
                           original.setJASPIC((JASPICMBean)this.createCopy((AbstractDescriptorBean)proposed.getJASPIC()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JASPIC", original.getJASPIC());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (!prop.equals("NodeManagerPassword")) {
                        if (prop.equals("NodeManagerPasswordEncrypted")) {
                           original.setNodeManagerPasswordEncrypted(proposed.getNodeManagerPasswordEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 29);
                        } else if (prop.equals("NodeManagerUsername")) {
                           original.setNodeManagerUsername(proposed.getNodeManagerUsername());
                           original._conditionalUnset(update.isUnsetUpdate(), 27);
                        } else if (prop.equals("NonceTimeoutSeconds")) {
                           original.setNonceTimeoutSeconds(proposed.getNonceTimeoutSeconds());
                           original._conditionalUnset(update.isUnsetUpdate(), 45);
                        } else if (prop.equals("RealmBootStrapVersion")) {
                           original.setRealmBootStrapVersion(proposed.getRealmBootStrapVersion());
                           original._conditionalUnset(update.isUnsetUpdate(), 22);
                        } else if (prop.equals("Realms")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addRealm((RealmMBean)update.getAddedObject());
                              }
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removeRealm((RealmMBean)update.getRemovedObject());
                           }

                           if (original.getRealms() == null || original.getRealms().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 12);
                           }
                        } else if (!prop.equals("Salt")) {
                           if (prop.equals("SecureMode")) {
                              if (type == 2) {
                                 original.setSecureMode((SecureModeMBean)this.createCopy((AbstractDescriptorBean)proposed.getSecureMode()));
                              } else {
                                 if (type != 3) {
                                    throw new AssertionError("Invalid type: " + type);
                                 }

                                 original._destroySingleton("SecureMode", original.getSecureMode());
                              }

                              original._conditionalUnset(update.isUnsetUpdate(), 10);
                           } else if (prop.equals("Tags")) {
                              if (type == 2) {
                                 update.resetAddedObject(update.getAddedObject());
                                 original.addTag((String)update.getAddedObject());
                              } else {
                                 if (type != 3) {
                                    throw new AssertionError("Invalid type: " + type);
                                 }

                                 original.removeTag((String)update.getRemovedObject());
                              }

                              if (original.getTags() == null || original.getTags().length == 0) {
                                 original._conditionalUnset(update.isUnsetUpdate(), 9);
                              }
                           } else if (prop.equals("WebAppFilesCaseInsensitive")) {
                              original.setWebAppFilesCaseInsensitive(proposed.getWebAppFilesCaseInsensitive());
                              original._conditionalUnset(update.isUnsetUpdate(), 21);
                           } else if (prop.equals("AnonymousAdminLookupEnabled")) {
                              original.setAnonymousAdminLookupEnabled(proposed.isAnonymousAdminLookupEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 16);
                           } else if (prop.equals("ClearTextCredentialAccessEnabled")) {
                              original.setClearTextCredentialAccessEnabled(proposed.isClearTextCredentialAccessEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 17);
                           } else if (prop.equals("ConsoleFullDelegationEnabled")) {
                              original.setConsoleFullDelegationEnabled(proposed.isConsoleFullDelegationEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 35);
                           } else if (prop.equals("CredentialGenerated")) {
                              original.setCredentialGenerated(proposed.isCredentialGenerated());
                              original._conditionalUnset(update.isUnsetUpdate(), 18);
                           } else if (prop.equals("CrossDomainSecurityEnabled")) {
                              original.setCrossDomainSecurityEnabled(proposed.isCrossDomainSecurityEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 38);
                           } else if (!prop.equals("DynamicallyCreated")) {
                              if (prop.equals("IdentityDomainAwareProvidersRequired")) {
                                 original.setIdentityDomainAwareProvidersRequired(proposed.isIdentityDomainAwareProvidersRequired());
                                 original._conditionalUnset(update.isUnsetUpdate(), 43);
                              } else if (prop.equals("IdentityDomainDefaultEnabled")) {
                                 original.setIdentityDomainDefaultEnabled(proposed.isIdentityDomainDefaultEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 44);
                              } else if (prop.equals("PrincipalEqualsCaseInsensitive")) {
                                 original.setPrincipalEqualsCaseInsensitive(proposed.isPrincipalEqualsCaseInsensitive());
                                 original._conditionalUnset(update.isUnsetUpdate(), 30);
                              } else if (prop.equals("PrincipalEqualsCompareDnAndGuid")) {
                                 original.setPrincipalEqualsCompareDnAndGuid(proposed.isPrincipalEqualsCompareDnAndGuid());
                                 original._conditionalUnset(update.isUnsetUpdate(), 31);
                              } else if (prop.equals("RemoteAnonymousJNDIEnabled")) {
                                 original.setRemoteAnonymousJNDIEnabled(proposed.isRemoteAnonymousJNDIEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 46);
                              } else if (prop.equals("RemoteAnonymousRMIIIOPEnabled")) {
                                 original.setRemoteAnonymousRMIIIOPEnabled(proposed.isRemoteAnonymousRMIIIOPEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 50);
                              } else if (prop.equals("RemoteAnonymousRMIT3Enabled")) {
                                 original.setRemoteAnonymousRMIT3Enabled(proposed.isRemoteAnonymousRMIT3Enabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 49);
                              } else if (prop.equals("UseKSSForDemo")) {
                                 original.setUseKSSForDemo(proposed.isUseKSSForDemo());
                                 original._conditionalUnset(update.isUnsetUpdate(), 41);
                              } else {
                                 super.applyPropertyUpdate(event, update);
                              }
                           }
                        }
                     }
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
            SecurityConfigurationMBeanImpl copy = (SecurityConfigurationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdministrativeIdentityDomain")) && this.bean.isAdministrativeIdentityDomainSet()) {
               copy.setAdministrativeIdentityDomain(this.bean.getAdministrativeIdentityDomain());
            }

            if ((excludeProps == null || !excludeProps.contains("BootAuthenticationMaxRetryDelay")) && this.bean.isBootAuthenticationMaxRetryDelaySet()) {
               copy.setBootAuthenticationMaxRetryDelay(this.bean.getBootAuthenticationMaxRetryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("BootAuthenticationRetryCount")) && this.bean.isBootAuthenticationRetryCountSet()) {
               copy.setBootAuthenticationRetryCount(this.bean.getBootAuthenticationRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("CertRevoc")) && this.bean.isCertRevocSet() && !copy._isSet(40)) {
               Object o = this.bean.getCertRevoc();
               copy.setCertRevoc((CertRevocMBean)null);
               copy.setCertRevoc(o == null ? null : (CertRevocMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CompatibilityConnectionFiltersEnabled")) && this.bean.isCompatibilityConnectionFiltersEnabledSet()) {
               copy.setCompatibilityConnectionFiltersEnabled(this.bean.getCompatibilityConnectionFiltersEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFilter")) && this.bean.isConnectionFilterSet()) {
               copy.setConnectionFilter(this.bean.getConnectionFilter());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("ConnectionFilterRules")) && this.bean.isConnectionFilterRulesSet()) {
               o = this.bean.getConnectionFilterRules();
               copy.setConnectionFilterRules(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionLoggerEnabled")) && this.bean.isConnectionLoggerEnabledSet()) {
               copy.setConnectionLoggerEnabled(this.bean.getConnectionLoggerEnabled());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("CredentialEncrypted")) && this.bean.isCredentialEncryptedSet()) {
               o = this.bean.getCredentialEncrypted();
               copy.setCredentialEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRealm")) && this.bean.isDefaultRealmSet()) {
               copy._unSet(copy, 13);
               copy.setDefaultRealmAsString(this.bean.getDefaultRealmAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRealmInternal")) && this.bean.isDefaultRealmInternalSet()) {
               copy._unSet(copy, 36);
               copy.setDefaultRealmInternalAsString(this.bean.getDefaultRealmInternalAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DowngradeUntrustedPrincipals")) && this.bean.isDowngradeUntrustedPrincipalsSet()) {
               copy.setDowngradeUntrustedPrincipals(this.bean.getDowngradeUntrustedPrincipals());
            }

            if ((excludeProps == null || !excludeProps.contains("EnforceStrictURLPattern")) && this.bean.isEnforceStrictURLPatternSet()) {
               copy.setEnforceStrictURLPattern(this.bean.getEnforceStrictURLPattern());
            }

            if ((excludeProps == null || !excludeProps.contains("EnforceValidBasicAuthCredentials")) && this.bean.isEnforceValidBasicAuthCredentialsSet()) {
               copy.setEnforceValidBasicAuthCredentials(this.bean.getEnforceValidBasicAuthCredentials());
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludedDomainNames")) && this.bean.isExcludedDomainNamesSet()) {
               o = this.bean.getExcludedDomainNames();
               copy.setExcludedDomainNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("JASPIC")) && this.bean.isJASPICSet() && !copy._isSet(11)) {
               Object o = this.bean.getJASPIC();
               copy.setJASPIC((JASPICMBean)null);
               copy.setJASPIC(o == null ? null : (JASPICMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NodeManagerPasswordEncrypted")) && this.bean.isNodeManagerPasswordEncryptedSet()) {
               o = this.bean.getNodeManagerPasswordEncrypted();
               copy.setNodeManagerPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("NodeManagerUsername")) && this.bean.isNodeManagerUsernameSet()) {
               copy.setNodeManagerUsername(this.bean.getNodeManagerUsername());
            }

            if ((excludeProps == null || !excludeProps.contains("NonceTimeoutSeconds")) && this.bean.isNonceTimeoutSecondsSet()) {
               copy.setNonceTimeoutSeconds(this.bean.getNonceTimeoutSeconds());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RealmBootStrapVersion")) && this.bean.isRealmBootStrapVersionSet()) {
               copy.setRealmBootStrapVersion(this.bean.getRealmBootStrapVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("Realms")) && this.bean.isRealmsSet() && !copy._isSet(12)) {
               RealmMBean[] oldRealms = this.bean.getRealms();
               RealmMBean[] newRealms = new RealmMBean[oldRealms.length];

               for(int i = 0; i < newRealms.length; ++i) {
                  newRealms[i] = (RealmMBean)((RealmMBean)this.createCopy((AbstractDescriptorBean)oldRealms[i], includeObsolete));
               }

               copy.setRealms(newRealms);
            }

            if ((excludeProps == null || !excludeProps.contains("SecureMode")) && this.bean.isSecureModeSet() && !copy._isSet(10)) {
               Object o = this.bean.getSecureMode();
               copy.setSecureMode((SecureModeMBean)null);
               copy.setSecureMode(o == null ? null : (SecureModeMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WebAppFilesCaseInsensitive")) && this.bean.isWebAppFilesCaseInsensitiveSet()) {
               copy.setWebAppFilesCaseInsensitive(this.bean.getWebAppFilesCaseInsensitive());
            }

            if ((excludeProps == null || !excludeProps.contains("AnonymousAdminLookupEnabled")) && this.bean.isAnonymousAdminLookupEnabledSet()) {
               copy.setAnonymousAdminLookupEnabled(this.bean.isAnonymousAdminLookupEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ClearTextCredentialAccessEnabled")) && this.bean.isClearTextCredentialAccessEnabledSet()) {
               copy.setClearTextCredentialAccessEnabled(this.bean.isClearTextCredentialAccessEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ConsoleFullDelegationEnabled")) && this.bean.isConsoleFullDelegationEnabledSet()) {
               copy.setConsoleFullDelegationEnabled(this.bean.isConsoleFullDelegationEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("CredentialGenerated")) && this.bean.isCredentialGeneratedSet()) {
               copy.setCredentialGenerated(this.bean.isCredentialGenerated());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossDomainSecurityEnabled")) && this.bean.isCrossDomainSecurityEnabledSet()) {
               copy.setCrossDomainSecurityEnabled(this.bean.isCrossDomainSecurityEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityDomainAwareProvidersRequired")) && this.bean.isIdentityDomainAwareProvidersRequiredSet()) {
               copy.setIdentityDomainAwareProvidersRequired(this.bean.isIdentityDomainAwareProvidersRequired());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityDomainDefaultEnabled")) && this.bean.isIdentityDomainDefaultEnabledSet()) {
               copy.setIdentityDomainDefaultEnabled(this.bean.isIdentityDomainDefaultEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PrincipalEqualsCaseInsensitive")) && this.bean.isPrincipalEqualsCaseInsensitiveSet()) {
               copy.setPrincipalEqualsCaseInsensitive(this.bean.isPrincipalEqualsCaseInsensitive());
            }

            if ((excludeProps == null || !excludeProps.contains("PrincipalEqualsCompareDnAndGuid")) && this.bean.isPrincipalEqualsCompareDnAndGuidSet()) {
               copy.setPrincipalEqualsCompareDnAndGuid(this.bean.isPrincipalEqualsCompareDnAndGuid());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteAnonymousJNDIEnabled")) && this.bean.isRemoteAnonymousJNDIEnabledSet()) {
               copy.setRemoteAnonymousJNDIEnabled(this.bean.isRemoteAnonymousJNDIEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteAnonymousRMIIIOPEnabled")) && this.bean.isRemoteAnonymousRMIIIOPEnabledSet()) {
               copy.setRemoteAnonymousRMIIIOPEnabled(this.bean.isRemoteAnonymousRMIIIOPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteAnonymousRMIT3Enabled")) && this.bean.isRemoteAnonymousRMIT3EnabledSet()) {
               copy.setRemoteAnonymousRMIT3Enabled(this.bean.isRemoteAnonymousRMIT3Enabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UseKSSForDemo")) && this.bean.isUseKSSForDemoSet()) {
               copy.setUseKSSForDemo(this.bean.isUseKSSForDemo());
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
         this.inferSubTree(this.bean.getCertRevoc(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultRealm(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultRealmInternal(), clazz, annotation);
         this.inferSubTree(this.bean.getJASPIC(), clazz, annotation);
         this.inferSubTree(this.bean.getRealms(), clazz, annotation);
         this.inferSubTree(this.bean.getSecureMode(), clazz, annotation);
      }
   }
}
