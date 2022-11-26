package weblogic.management.security;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.audit.AuditorMBeanImpl;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBeanImpl;
import weblogic.management.security.authentication.PasswordValidatorMBean;
import weblogic.management.security.authentication.PasswordValidatorMBeanImpl;
import weblogic.management.security.authentication.UserLockoutManagerMBean;
import weblogic.management.security.authentication.UserLockoutManagerMBeanImpl;
import weblogic.management.security.authorization.AdjudicatorMBean;
import weblogic.management.security.authorization.AdjudicatorMBeanImpl;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.AuthorizerMBeanImpl;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.authorization.RoleMapperMBeanImpl;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.credentials.CredentialMapperMBeanImpl;
import weblogic.management.security.pk.CertPathBuilderMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.security.pk.CertPathProviderMBeanImpl;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class RealmMBeanImpl extends AbstractCommoConfigurationBean implements RealmMBean, Serializable {
   private AdjudicatorMBean _Adjudicator;
   private String[] _AdjudicatorTypes;
   private String[] _AuditorTypes;
   private AuditorMBean[] _Auditors;
   private String _AuthMethods;
   private String[] _AuthenticationProviderTypes;
   private AuthenticationProviderMBean[] _AuthenticationProviders;
   private String[] _AuthorizerTypes;
   private AuthorizerMBean[] _Authorizers;
   private boolean _AutoRestartOnNonDynamicChanges;
   private CertPathBuilderMBean _CertPathBuilder;
   private String[] _CertPathProviderTypes;
   private CertPathProviderMBean[] _CertPathProviders;
   private boolean _CombinedRoleMappingEnabled;
   private String _CompatibilityObjectName;
   private String[] _CredentialMapperTypes;
   private CredentialMapperMBean[] _CredentialMappers;
   private boolean _DefaultRealm;
   private boolean _DelegateMBeanAuthorization;
   private boolean _DeployCredentialMappingIgnored;
   private boolean _DeployPolicyIgnored;
   private boolean _DeployRoleIgnored;
   private boolean _DeployableProviderSynchronizationEnabled;
   private Integer _DeployableProviderSynchronizationTimeout;
   private boolean _EnableWebLogicPrincipalValidatorCache;
   private boolean _FullyDelegateAuthorization;
   private boolean _IdentityAssertionCacheEnabled;
   private int _IdentityAssertionCacheTTL;
   private String[] _IdentityAssertionDoNotCacheContextElements;
   private String[] _IdentityAssertionHeaderNamePrecedence;
   private String _ManagementIdentityDomain;
   private Integer _MaxWebLogicPrincipalsInCache;
   private String _Name;
   private String[] _PasswordValidatorTypes;
   private PasswordValidatorMBean[] _PasswordValidators;
   private RDBMSSecurityStoreMBean _RDBMSSecurityStore;
   private int _RetireTimeoutSeconds;
   private String[] _RoleMapperTypes;
   private RoleMapperMBean[] _RoleMappers;
   private String _SecurityDDModel;
   private UserLockoutManagerMBean _UserLockoutManager;
   private boolean _ValidateDDSecurityData;
   private transient RealmImpl _customizer;
   private static SchemaHelper2 _schemaHelper;

   public RealmMBeanImpl() {
      try {
         this._customizer = new RealmImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public RealmMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new RealmImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public RealmMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new RealmImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public void addAuditor(AuditorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         AuditorMBean[] _new;
         if (this._isSet(2)) {
            _new = (AuditorMBean[])((AuditorMBean[])this._getHelper()._extendArray(this.getAuditors(), AuditorMBean.class, param0));
         } else {
            _new = new AuditorMBean[]{param0};
         }

         try {
            this.setAuditors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AuditorMBean[] getAuditors() {
      return this._Auditors;
   }

   public boolean isAuditorsInherited() {
      return false;
   }

   public boolean isAuditorsSet() {
      return this._isSet(2);
   }

   public void removeAuditor(AuditorMBean param0) {
      this.destroyAuditor(param0);
   }

   public void setAuditors(AuditorMBean[] param0) throws InvalidAttributeValueException {
      AuditorMBean[] param0 = param0 == null ? new AuditorMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AuditorMBean[] _oldVal = this._Auditors;
      this._Auditors = (AuditorMBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getAuditorTypes() {
      return this._customizer.getAuditorTypes();
   }

   public boolean isAuditorTypesInherited() {
      return false;
   }

   public boolean isAuditorTypesSet() {
      return this._isSet(3);
   }

   public void setAuditorTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._AuditorTypes = param0;
   }

   public AuditorMBean createAuditor(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createAuditor(param0, param1);
   }

   public AuditorMBean createAuditor(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createAuditor(param0);
   }

   public AuditorMBean createAuditor(Class param0) throws JMException {
      AuditorMBeanImpl _val = (AuditorMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addAuditor(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public AuditorMBean createAuditor(Class param0, String param1) throws JMException {
      AuditorMBeanImpl _val = (AuditorMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addAuditor(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyAuditor(AuditorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         AuditorMBean[] _old = this.getAuditors();
         AuditorMBean[] _new = (AuditorMBean[])((AuditorMBean[])this._getHelper()._removeElement(_old, AuditorMBean.class, param0));
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
               this.setAuditors(_new);
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

   public AuditorMBean lookupAuditor(String param0) {
      Object[] aary = (Object[])this._Auditors;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AuditorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AuditorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addAuthenticationProvider(AuthenticationProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         AuthenticationProviderMBean[] _new;
         if (this._isSet(4)) {
            _new = (AuthenticationProviderMBean[])((AuthenticationProviderMBean[])this._getHelper()._extendArray(this.getAuthenticationProviders(), AuthenticationProviderMBean.class, param0));
         } else {
            _new = new AuthenticationProviderMBean[]{param0};
         }

         try {
            this.setAuthenticationProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AuthenticationProviderMBean[] getAuthenticationProviders() {
      return this._AuthenticationProviders;
   }

   public boolean isAuthenticationProvidersInherited() {
      return false;
   }

   public boolean isAuthenticationProvidersSet() {
      return this._isSet(4);
   }

   public void removeAuthenticationProvider(AuthenticationProviderMBean param0) {
      this.destroyAuthenticationProvider(param0);
   }

   public void setAuthenticationProviders(AuthenticationProviderMBean[] param0) throws InvalidAttributeValueException {
      AuthenticationProviderMBean[] param0 = param0 == null ? new AuthenticationProviderMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AuthenticationProviderMBean[] _oldVal = this._AuthenticationProviders;
      this._AuthenticationProviders = (AuthenticationProviderMBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public String[] getAuthenticationProviderTypes() {
      return this._customizer.getAuthenticationProviderTypes();
   }

   public boolean isAuthenticationProviderTypesInherited() {
      return false;
   }

   public boolean isAuthenticationProviderTypesSet() {
      return this._isSet(5);
   }

   public void setAuthenticationProviderTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._AuthenticationProviderTypes = param0;
   }

   public AuthenticationProviderMBean createAuthenticationProvider(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createAuthenticationProvider(param0, param1);
   }

   public AuthenticationProviderMBean createAuthenticationProvider(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createAuthenticationProvider(param0);
   }

   public AuthenticationProviderMBean createAuthenticationProvider(Class param0) throws JMException {
      AuthenticationProviderMBeanImpl _val = (AuthenticationProviderMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addAuthenticationProvider(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public AuthenticationProviderMBean createAuthenticationProvider(Class param0, String param1) throws JMException {
      AuthenticationProviderMBeanImpl _val = (AuthenticationProviderMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addAuthenticationProvider(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyAuthenticationProvider(AuthenticationProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         AuthenticationProviderMBean[] _old = this.getAuthenticationProviders();
         AuthenticationProviderMBean[] _new = (AuthenticationProviderMBean[])((AuthenticationProviderMBean[])this._getHelper()._removeElement(_old, AuthenticationProviderMBean.class, param0));
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
               this.setAuthenticationProviders(_new);
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

   public AuthenticationProviderMBean lookupAuthenticationProvider(String param0) {
      Object[] aary = (Object[])this._AuthenticationProviders;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AuthenticationProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AuthenticationProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addRoleMapper(RoleMapperMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         RoleMapperMBean[] _new;
         if (this._isSet(6)) {
            _new = (RoleMapperMBean[])((RoleMapperMBean[])this._getHelper()._extendArray(this.getRoleMappers(), RoleMapperMBean.class, param0));
         } else {
            _new = new RoleMapperMBean[]{param0};
         }

         try {
            this.setRoleMappers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public RoleMapperMBean[] getRoleMappers() {
      return this._RoleMappers;
   }

   public boolean isRoleMappersInherited() {
      return false;
   }

   public boolean isRoleMappersSet() {
      return this._isSet(6);
   }

   public void removeRoleMapper(RoleMapperMBean param0) {
      this.destroyRoleMapper(param0);
   }

   public void setRoleMappers(RoleMapperMBean[] param0) throws InvalidAttributeValueException {
      RoleMapperMBean[] param0 = param0 == null ? new RoleMapperMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      RoleMapperMBean[] _oldVal = this._RoleMappers;
      this._RoleMappers = (RoleMapperMBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public String[] getRoleMapperTypes() {
      return this._customizer.getRoleMapperTypes();
   }

   public boolean isRoleMapperTypesInherited() {
      return false;
   }

   public boolean isRoleMapperTypesSet() {
      return this._isSet(7);
   }

   public void setRoleMapperTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._RoleMapperTypes = param0;
   }

   public RoleMapperMBean createRoleMapper(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createRoleMapper(param0, param1);
   }

   public RoleMapperMBean createRoleMapper(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createRoleMapper(param0);
   }

   public RoleMapperMBean createRoleMapper(Class param0) throws JMException {
      RoleMapperMBeanImpl _val = (RoleMapperMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addRoleMapper(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public RoleMapperMBean createRoleMapper(Class param0, String param1) throws JMException {
      RoleMapperMBeanImpl _val = (RoleMapperMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addRoleMapper(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyRoleMapper(RoleMapperMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         RoleMapperMBean[] _old = this.getRoleMappers();
         RoleMapperMBean[] _new = (RoleMapperMBean[])((RoleMapperMBean[])this._getHelper()._removeElement(_old, RoleMapperMBean.class, param0));
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
               this.setRoleMappers(_new);
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

   public RoleMapperMBean lookupRoleMapper(String param0) {
      Object[] aary = (Object[])this._RoleMappers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      RoleMapperMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (RoleMapperMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addAuthorizer(AuthorizerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         AuthorizerMBean[] _new;
         if (this._isSet(8)) {
            _new = (AuthorizerMBean[])((AuthorizerMBean[])this._getHelper()._extendArray(this.getAuthorizers(), AuthorizerMBean.class, param0));
         } else {
            _new = new AuthorizerMBean[]{param0};
         }

         try {
            this.setAuthorizers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AuthorizerMBean[] getAuthorizers() {
      return this._Authorizers;
   }

   public boolean isAuthorizersInherited() {
      return false;
   }

   public boolean isAuthorizersSet() {
      return this._isSet(8);
   }

   public void removeAuthorizer(AuthorizerMBean param0) {
      this.destroyAuthorizer(param0);
   }

   public void setAuthorizers(AuthorizerMBean[] param0) throws InvalidAttributeValueException {
      AuthorizerMBean[] param0 = param0 == null ? new AuthorizerMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AuthorizerMBean[] _oldVal = this._Authorizers;
      this._Authorizers = (AuthorizerMBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public String[] getAuthorizerTypes() {
      return this._customizer.getAuthorizerTypes();
   }

   public boolean isAuthorizerTypesInherited() {
      return false;
   }

   public boolean isAuthorizerTypesSet() {
      return this._isSet(9);
   }

   public void setAuthorizerTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._AuthorizerTypes = param0;
   }

   public AuthorizerMBean createAuthorizer(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createAuthorizer(param0, param1);
   }

   public AuthorizerMBean createAuthorizer(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createAuthorizer(param0);
   }

   public AuthorizerMBean createAuthorizer(Class param0) throws JMException {
      AuthorizerMBeanImpl _val = (AuthorizerMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addAuthorizer(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public AuthorizerMBean createAuthorizer(Class param0, String param1) throws JMException {
      AuthorizerMBeanImpl _val = (AuthorizerMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addAuthorizer(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyAuthorizer(AuthorizerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         AuthorizerMBean[] _old = this.getAuthorizers();
         AuthorizerMBean[] _new = (AuthorizerMBean[])((AuthorizerMBean[])this._getHelper()._removeElement(_old, AuthorizerMBean.class, param0));
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
               this.setAuthorizers(_new);
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

   public AuthorizerMBean lookupAuthorizer(String param0) {
      Object[] aary = (Object[])this._Authorizers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      AuthorizerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (AuthorizerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public AdjudicatorMBean getAdjudicator() {
      return this._Adjudicator;
   }

   public boolean isAdjudicatorInherited() {
      return false;
   }

   public boolean isAdjudicatorSet() {
      return this._isSet(10);
   }

   public void setAdjudicator(AdjudicatorMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAdjudicator() != null && param0 != this.getAdjudicator()) {
         throw new BeanAlreadyExistsException(this.getAdjudicator() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 10)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AdjudicatorMBean _oldVal = this._Adjudicator;
         this._Adjudicator = param0;
         this._postSet(10, _oldVal, param0);
      }
   }

   public String[] getAdjudicatorTypes() {
      return this._customizer.getAdjudicatorTypes();
   }

   public boolean isAdjudicatorTypesInherited() {
      return false;
   }

   public boolean isAdjudicatorTypesSet() {
      return this._isSet(11);
   }

   public void setAdjudicatorTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._AdjudicatorTypes = param0;
   }

   public AdjudicatorMBean createAdjudicator(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createAdjudicator(param0, param1);
   }

   public AdjudicatorMBean createAdjudicator(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createAdjudicator(param0);
   }

   public AdjudicatorMBean createAdjudicator(Class param0) throws JMException {
      AdjudicatorMBeanImpl _val = (AdjudicatorMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.setAdjudicator(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public AdjudicatorMBean createAdjudicator(Class param0, String param1) throws JMException {
      AdjudicatorMBeanImpl _val = (AdjudicatorMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.setAdjudicator(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyAdjudicator() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Adjudicator;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAdjudicator((AdjudicatorMBean)null);
               this._unSet(10);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addCredentialMapper(CredentialMapperMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         CredentialMapperMBean[] _new;
         if (this._isSet(12)) {
            _new = (CredentialMapperMBean[])((CredentialMapperMBean[])this._getHelper()._extendArray(this.getCredentialMappers(), CredentialMapperMBean.class, param0));
         } else {
            _new = new CredentialMapperMBean[]{param0};
         }

         try {
            this.setCredentialMappers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CredentialMapperMBean[] getCredentialMappers() {
      return this._CredentialMappers;
   }

   public boolean isCredentialMappersInherited() {
      return false;
   }

   public boolean isCredentialMappersSet() {
      return this._isSet(12);
   }

   public void removeCredentialMapper(CredentialMapperMBean param0) {
      this.destroyCredentialMapper(param0);
   }

   public void setCredentialMappers(CredentialMapperMBean[] param0) throws InvalidAttributeValueException {
      CredentialMapperMBean[] param0 = param0 == null ? new CredentialMapperMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CredentialMapperMBean[] _oldVal = this._CredentialMappers;
      this._CredentialMappers = (CredentialMapperMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public String[] getCredentialMapperTypes() {
      return this._customizer.getCredentialMapperTypes();
   }

   public boolean isCredentialMapperTypesInherited() {
      return false;
   }

   public boolean isCredentialMapperTypesSet() {
      return this._isSet(13);
   }

   public void setCredentialMapperTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._CredentialMapperTypes = param0;
   }

   public CredentialMapperMBean createCredentialMapper(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createCredentialMapper(param0, param1);
   }

   public CredentialMapperMBean createCredentialMapper(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createCredentialMapper(param0);
   }

   public CredentialMapperMBean createCredentialMapper(Class param0) throws JMException {
      CredentialMapperMBeanImpl _val = (CredentialMapperMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addCredentialMapper(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public CredentialMapperMBean createCredentialMapper(Class param0, String param1) throws JMException {
      CredentialMapperMBeanImpl _val = (CredentialMapperMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addCredentialMapper(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyCredentialMapper(CredentialMapperMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         CredentialMapperMBean[] _old = this.getCredentialMappers();
         CredentialMapperMBean[] _new = (CredentialMapperMBean[])((CredentialMapperMBean[])this._getHelper()._removeElement(_old, CredentialMapperMBean.class, param0));
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
               this.setCredentialMappers(_new);
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

   public CredentialMapperMBean lookupCredentialMapper(String param0) {
      Object[] aary = (Object[])this._CredentialMappers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CredentialMapperMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CredentialMapperMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addCertPathProvider(CertPathProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         CertPathProviderMBean[] _new;
         if (this._isSet(14)) {
            _new = (CertPathProviderMBean[])((CertPathProviderMBean[])this._getHelper()._extendArray(this.getCertPathProviders(), CertPathProviderMBean.class, param0));
         } else {
            _new = new CertPathProviderMBean[]{param0};
         }

         try {
            this.setCertPathProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CertPathProviderMBean[] getCertPathProviders() {
      return this._CertPathProviders;
   }

   public boolean isCertPathProvidersInherited() {
      return false;
   }

   public boolean isCertPathProvidersSet() {
      return this._isSet(14);
   }

   public void removeCertPathProvider(CertPathProviderMBean param0) {
      this.destroyCertPathProvider(param0);
   }

   public void setCertPathProviders(CertPathProviderMBean[] param0) throws InvalidAttributeValueException {
      CertPathProviderMBean[] param0 = param0 == null ? new CertPathProviderMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      CertPathProviderMBean[] _oldVal = this._CertPathProviders;
      this._CertPathProviders = (CertPathProviderMBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public String[] getCertPathProviderTypes() {
      return this._customizer.getCertPathProviderTypes();
   }

   public boolean isCertPathProviderTypesInherited() {
      return false;
   }

   public boolean isCertPathProviderTypesSet() {
      return this._isSet(15);
   }

   public void setCertPathProviderTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._CertPathProviderTypes = param0;
   }

   public CertPathProviderMBean createCertPathProvider(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createCertPathProvider(param0, param1);
   }

   public CertPathProviderMBean createCertPathProvider(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createCertPathProvider(param0);
   }

   public CertPathProviderMBean createCertPathProvider(Class param0) throws JMException {
      CertPathProviderMBeanImpl _val = (CertPathProviderMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addCertPathProvider(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public CertPathProviderMBean createCertPathProvider(Class param0, String param1) throws JMException {
      CertPathProviderMBeanImpl _val = (CertPathProviderMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addCertPathProvider(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyCertPathProvider(CertPathProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         CertPathProviderMBean[] _old = this.getCertPathProviders();
         CertPathProviderMBean[] _new = (CertPathProviderMBean[])((CertPathProviderMBean[])this._getHelper()._removeElement(_old, CertPathProviderMBean.class, param0));
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
               this.setCertPathProviders(_new);
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

   public CertPathProviderMBean lookupCertPathProvider(String param0) {
      Object[] aary = (Object[])this._CertPathProviders;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CertPathProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CertPathProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public CertPathBuilderMBean getCertPathBuilder() {
      return this._CertPathBuilder;
   }

   public String getCertPathBuilderAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCertPathBuilder();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isCertPathBuilderInherited() {
      return false;
   }

   public boolean isCertPathBuilderSet() {
      return this._isSet(16);
   }

   public void setCertPathBuilderAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, CertPathBuilderMBean.class, new ReferenceManager.Resolver(this, 16) {
            public void resolveReference(Object value) {
               try {
                  RealmMBeanImpl.this.setCertPathBuilder((CertPathBuilderMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         CertPathBuilderMBean _oldVal = this._CertPathBuilder;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._CertPathBuilder);
      }

   }

   public void setCertPathBuilder(CertPathBuilderMBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return RealmMBeanImpl.this.getCertPathBuilder();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      CertPathBuilderMBean _oldVal = this._CertPathBuilder;
      this._CertPathBuilder = param0;
      this._postSet(16, _oldVal, param0);
   }

   public UserLockoutManagerMBean getUserLockoutManager() {
      return this._UserLockoutManager;
   }

   public boolean isUserLockoutManagerInherited() {
      return false;
   }

   public boolean isUserLockoutManagerSet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getUserLockoutManager());
   }

   public void setUserLockoutManager(UserLockoutManagerMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      UserLockoutManagerMBean _oldVal = this._UserLockoutManager;
      this._UserLockoutManager = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isDeployRoleIgnored() {
      return this._DeployRoleIgnored;
   }

   public boolean isDeployRoleIgnoredInherited() {
      return false;
   }

   public boolean isDeployRoleIgnoredSet() {
      return this._isSet(18);
   }

   public void setDeployRoleIgnored(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._DeployRoleIgnored;
      this._DeployRoleIgnored = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isDeployPolicyIgnored() {
      return this._DeployPolicyIgnored;
   }

   public boolean isDeployPolicyIgnoredInherited() {
      return false;
   }

   public boolean isDeployPolicyIgnoredSet() {
      return this._isSet(19);
   }

   public void setDeployPolicyIgnored(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._DeployPolicyIgnored;
      this._DeployPolicyIgnored = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isDeployCredentialMappingIgnored() {
      return this._DeployCredentialMappingIgnored;
   }

   public boolean isDeployCredentialMappingIgnoredInherited() {
      return false;
   }

   public boolean isDeployCredentialMappingIgnoredSet() {
      return this._isSet(20);
   }

   public void setDeployCredentialMappingIgnored(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._DeployCredentialMappingIgnored;
      this._DeployCredentialMappingIgnored = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isFullyDelegateAuthorization() {
      return this._FullyDelegateAuthorization;
   }

   public boolean isFullyDelegateAuthorizationInherited() {
      return false;
   }

   public boolean isFullyDelegateAuthorizationSet() {
      return this._isSet(21);
   }

   public void setFullyDelegateAuthorization(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._FullyDelegateAuthorization;
      this._FullyDelegateAuthorization = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean isValidateDDSecurityData() {
      return this._ValidateDDSecurityData;
   }

   public boolean isValidateDDSecurityDataInherited() {
      return false;
   }

   public boolean isValidateDDSecurityDataSet() {
      return this._isSet(22);
   }

   public void setValidateDDSecurityData(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._ValidateDDSecurityData;
      this._ValidateDDSecurityData = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getSecurityDDModel() {
      return this._SecurityDDModel;
   }

   public boolean isSecurityDDModelInherited() {
      return false;
   }

   public boolean isSecurityDDModelSet() {
      return this._isSet(23);
   }

   public void setSecurityDDModel(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"DDOnly", "CustomRoles", "CustomRolesAndPolicies", "Advanced"};
      param0 = LegalChecks.checkInEnum("SecurityDDModel", param0, _set);
      String _oldVal = this._SecurityDDModel;
      this._SecurityDDModel = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isCombinedRoleMappingEnabled() {
      return this._CombinedRoleMappingEnabled;
   }

   public boolean isCombinedRoleMappingEnabledInherited() {
      return false;
   }

   public boolean isCombinedRoleMappingEnabledSet() {
      return this._isSet(24);
   }

   public void setCombinedRoleMappingEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._CombinedRoleMappingEnabled;
      this._CombinedRoleMappingEnabled = param0;
      this._postSet(24, _oldVal, param0);
   }

   public void validate() throws ErrorCollectionException {
      try {
         this._customizer.validate();
      } catch (ErrorCollectionException var2) {
         throw new UndeclaredThrowableException(var2);
      }
   }

   public boolean isDefaultRealm() {
      return this._customizer.isDefaultRealm();
   }

   public boolean isDefaultRealmInherited() {
      return false;
   }

   public boolean isDefaultRealmSet() {
      return this._isSet(25);
   }

   public void setDefaultRealm(boolean param0) throws InvalidAttributeValueException {
      this._customizer.setDefaultRealm(param0);
   }

   public boolean isEnableWebLogicPrincipalValidatorCache() {
      return this._EnableWebLogicPrincipalValidatorCache;
   }

   public boolean isEnableWebLogicPrincipalValidatorCacheInherited() {
      return false;
   }

   public boolean isEnableWebLogicPrincipalValidatorCacheSet() {
      return this._isSet(26);
   }

   public void setEnableWebLogicPrincipalValidatorCache(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._EnableWebLogicPrincipalValidatorCache;
      this._EnableWebLogicPrincipalValidatorCache = param0;
      this._postSet(26, _oldVal, param0);
   }

   public Integer getMaxWebLogicPrincipalsInCache() {
      return this._MaxWebLogicPrincipalsInCache;
   }

   public boolean isMaxWebLogicPrincipalsInCacheInherited() {
      return false;
   }

   public boolean isMaxWebLogicPrincipalsInCacheSet() {
      return this._isSet(27);
   }

   public void setMaxWebLogicPrincipalsInCache(Integer param0) throws InvalidAttributeValueException {
      RealmValidator.validateMaxWebLogicPrincipalsInCache(param0);
      Integer _oldVal = this._MaxWebLogicPrincipalsInCache;
      this._MaxWebLogicPrincipalsInCache = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(28);
   }

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(28, _oldVal, param0);
   }

   public boolean isDelegateMBeanAuthorization() {
      if (!this._isSet(29)) {
         return this._isSecureModeEnabled();
      } else {
         return this._DelegateMBeanAuthorization;
      }
   }

   public boolean isDelegateMBeanAuthorizationInherited() {
      return false;
   }

   public boolean isDelegateMBeanAuthorizationSet() {
      return this._isSet(29);
   }

   public void setDelegateMBeanAuthorization(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._DelegateMBeanAuthorization;
      this._DelegateMBeanAuthorization = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getAuthMethods() {
      return this._AuthMethods;
   }

   public boolean isAuthMethodsInherited() {
      return false;
   }

   public boolean isAuthMethodsSet() {
      return this._isSet(30);
   }

   public void setAuthMethods(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AuthMethods;
      this._AuthMethods = param0;
      this._postSet(30, _oldVal, param0);
   }

   public String getCompatibilityObjectName() {
      return this._customizer.getCompatibilityObjectName();
   }

   public boolean isCompatibilityObjectNameInherited() {
      return false;
   }

   public boolean isCompatibilityObjectNameSet() {
      return this._isSet(31);
   }

   public void setCompatibilityObjectName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityObjectName;
      this._CompatibilityObjectName = param0;
      this._postSet(31, _oldVal, param0);
   }

   public RDBMSSecurityStoreMBean getRDBMSSecurityStore() {
      return this._RDBMSSecurityStore;
   }

   public boolean isRDBMSSecurityStoreInherited() {
      return false;
   }

   public boolean isRDBMSSecurityStoreSet() {
      return this._isSet(32);
   }

   public void setRDBMSSecurityStore(RDBMSSecurityStoreMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getRDBMSSecurityStore() != null && param0 != this.getRDBMSSecurityStore()) {
         throw new BeanAlreadyExistsException(this.getRDBMSSecurityStore() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 32)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         RDBMSSecurityStoreMBean _oldVal = this._RDBMSSecurityStore;
         this._RDBMSSecurityStore = param0;
         this._postSet(32, _oldVal, param0);
      }
   }

   public RDBMSSecurityStoreMBean createRDBMSSecurityStore() throws JMException {
      RDBMSSecurityStoreMBeanImpl _val = new RDBMSSecurityStoreMBeanImpl(this, -1);

      try {
         this.setRDBMSSecurityStore(_val);
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

   public RDBMSSecurityStoreMBean createRDBMSSecurityStore(String param0) throws JMException {
      RDBMSSecurityStoreMBeanImpl _val = new RDBMSSecurityStoreMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setRDBMSSecurityStore(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyRDBMSSecurityStore() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._RDBMSSecurityStore;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setRDBMSSecurityStore((RDBMSSecurityStoreMBean)null);
               this._unSet(32);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PasswordValidatorMBean createPasswordValidator(Class param0) throws JMException {
      PasswordValidatorMBeanImpl _val = (PasswordValidatorMBeanImpl)this._createChildBean(param0, -1);

      try {
         this.addPasswordValidator(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof JMException) {
            throw (JMException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public PasswordValidatorMBean createPasswordValidator(Class param0, String param1) throws JMException {
      PasswordValidatorMBeanImpl _val = (PasswordValidatorMBeanImpl)this._createChildBean(param0, -1);

      try {
         _val.setName(param1);
         this.addPasswordValidator(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else if (var5 instanceof JMException) {
            throw (JMException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public PasswordValidatorMBean createPasswordValidator(String param0, String param1) throws ClassNotFoundException, JMException {
      return this._customizer.createPasswordValidator(param0, param1);
   }

   public PasswordValidatorMBean createPasswordValidator(String param0) throws ClassNotFoundException, JMException {
      return this._customizer.createPasswordValidator(param0);
   }

   public String[] getPasswordValidatorTypes() {
      return this._customizer.getPasswordValidatorTypes();
   }

   public boolean isPasswordValidatorTypesInherited() {
      return false;
   }

   public boolean isPasswordValidatorTypesSet() {
      return this._isSet(33);
   }

   public void setPasswordValidatorTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._PasswordValidatorTypes = param0;
   }

   public void addPasswordValidator(PasswordValidatorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 34)) {
         PasswordValidatorMBean[] _new;
         if (this._isSet(34)) {
            _new = (PasswordValidatorMBean[])((PasswordValidatorMBean[])this._getHelper()._extendArray(this.getPasswordValidators(), PasswordValidatorMBean.class, param0));
         } else {
            _new = new PasswordValidatorMBean[]{param0};
         }

         try {
            this.setPasswordValidators(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public PasswordValidatorMBean[] getPasswordValidators() {
      return this._PasswordValidators;
   }

   public boolean isPasswordValidatorsInherited() {
      return false;
   }

   public boolean isPasswordValidatorsSet() {
      return this._isSet(34);
   }

   public void removePasswordValidator(PasswordValidatorMBean param0) {
      this.destroyPasswordValidator(param0);
   }

   public void setPasswordValidators(PasswordValidatorMBean[] param0) throws InvalidAttributeValueException {
      PasswordValidatorMBean[] param0 = param0 == null ? new PasswordValidatorMBeanImpl[0] : param0;
      ProviderValidator.validateProviders((ProviderMBean[])param0);

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 34)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PasswordValidatorMBean[] _oldVal = this._PasswordValidators;
      this._PasswordValidators = (PasswordValidatorMBean[])param0;
      this._postSet(34, _oldVal, param0);
   }

   public PasswordValidatorMBean lookupPasswordValidator(String param0) {
      Object[] aary = (Object[])this._PasswordValidators;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PasswordValidatorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PasswordValidatorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyPasswordValidator(PasswordValidatorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 34);
         PasswordValidatorMBean[] _old = this.getPasswordValidators();
         PasswordValidatorMBean[] _new = (PasswordValidatorMBean[])((PasswordValidatorMBean[])this._getHelper()._removeElement(_old, PasswordValidatorMBean.class, param0));
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
               this.setPasswordValidators(_new);
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

   public boolean isDeployableProviderSynchronizationEnabled() {
      return this._DeployableProviderSynchronizationEnabled;
   }

   public boolean isDeployableProviderSynchronizationEnabledInherited() {
      return false;
   }

   public boolean isDeployableProviderSynchronizationEnabledSet() {
      return this._isSet(35);
   }

   public void setDeployableProviderSynchronizationEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._DeployableProviderSynchronizationEnabled;
      this._DeployableProviderSynchronizationEnabled = param0;
      this._postSet(35, _oldVal, param0);
   }

   public Integer getDeployableProviderSynchronizationTimeout() {
      return this._DeployableProviderSynchronizationTimeout;
   }

   public boolean isDeployableProviderSynchronizationTimeoutInherited() {
      return false;
   }

   public boolean isDeployableProviderSynchronizationTimeoutSet() {
      return this._isSet(36);
   }

   public void setDeployableProviderSynchronizationTimeout(Integer param0) throws InvalidAttributeValueException {
      Integer _oldVal = this._DeployableProviderSynchronizationTimeout;
      this._DeployableProviderSynchronizationTimeout = param0;
      this._postSet(36, _oldVal, param0);
   }

   public boolean isAutoRestartOnNonDynamicChanges() {
      if (!this._isSet(37)) {
         try {
            return !this.isDefaultRealm();
         } catch (NullPointerException var2) {
         }
      }

      return this._AutoRestartOnNonDynamicChanges;
   }

   public boolean isAutoRestartOnNonDynamicChangesInherited() {
      return false;
   }

   public boolean isAutoRestartOnNonDynamicChangesSet() {
      return this._isSet(37);
   }

   public void setAutoRestartOnNonDynamicChanges(boolean param0) {
      boolean _oldVal = this._AutoRestartOnNonDynamicChanges;
      this._AutoRestartOnNonDynamicChanges = param0;
      this._postSet(37, _oldVal, param0);
   }

   public int getRetireTimeoutSeconds() {
      return this._RetireTimeoutSeconds;
   }

   public boolean isRetireTimeoutSecondsInherited() {
      return false;
   }

   public boolean isRetireTimeoutSecondsSet() {
      return this._isSet(38);
   }

   public void setRetireTimeoutSeconds(int param0) {
      LegalChecks.checkMin("RetireTimeoutSeconds", param0, 1);
      int _oldVal = this._RetireTimeoutSeconds;
      this._RetireTimeoutSeconds = param0;
      this._postSet(38, _oldVal, param0);
   }

   public String getManagementIdentityDomain() {
      return this._ManagementIdentityDomain;
   }

   public boolean isManagementIdentityDomainInherited() {
      return false;
   }

   public boolean isManagementIdentityDomainSet() {
      return this._isSet(39);
   }

   public void setManagementIdentityDomain(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ManagementIdentityDomain;
      this._ManagementIdentityDomain = param0;
      this._postSet(39, _oldVal, param0);
   }

   public String[] getIdentityAssertionHeaderNamePrecedence() {
      return this._IdentityAssertionHeaderNamePrecedence;
   }

   public boolean isIdentityAssertionHeaderNamePrecedenceInherited() {
      return false;
   }

   public boolean isIdentityAssertionHeaderNamePrecedenceSet() {
      return this._isSet(40);
   }

   public void setIdentityAssertionHeaderNamePrecedence(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._IdentityAssertionHeaderNamePrecedence;
      this._IdentityAssertionHeaderNamePrecedence = param0;
      this._postSet(40, _oldVal, param0);
   }

   public boolean isIdentityAssertionCacheEnabled() {
      return this._IdentityAssertionCacheEnabled;
   }

   public boolean isIdentityAssertionCacheEnabledInherited() {
      return false;
   }

   public boolean isIdentityAssertionCacheEnabledSet() {
      return this._isSet(41);
   }

   public void setIdentityAssertionCacheEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._IdentityAssertionCacheEnabled;
      this._IdentityAssertionCacheEnabled = param0;
      this._postSet(41, _oldVal, param0);
   }

   public int getIdentityAssertionCacheTTL() {
      return this._IdentityAssertionCacheTTL;
   }

   public boolean isIdentityAssertionCacheTTLInherited() {
      return false;
   }

   public boolean isIdentityAssertionCacheTTLSet() {
      return this._isSet(42);
   }

   public void setIdentityAssertionCacheTTL(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("IdentityAssertionCacheTTL", param0, 0);
      int _oldVal = this._IdentityAssertionCacheTTL;
      this._IdentityAssertionCacheTTL = param0;
      this._postSet(42, _oldVal, param0);
   }

   public String[] getIdentityAssertionDoNotCacheContextElements() {
      return this._IdentityAssertionDoNotCacheContextElements;
   }

   public boolean isIdentityAssertionDoNotCacheContextElementsInherited() {
      return false;
   }

   public boolean isIdentityAssertionDoNotCacheContextElementsSet() {
      return this._isSet(43);
   }

   public void setIdentityAssertionDoNotCacheContextElements(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._IdentityAssertionDoNotCacheContextElements;
      this._IdentityAssertionDoNotCacheContextElements = param0;
      this._postSet(43, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _postCreate() {
      this._customizer._postCreate();
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
      return super._isAnythingSet() || this.isUserLockoutManagerSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._Adjudicator = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._AdjudicatorTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._AuditorTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Auditors = new AuditorMBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._AuthMethods = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._AuthenticationProviderTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._AuthenticationProviders = new AuthenticationProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 9:
               this._AuthorizerTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._Authorizers = new AuthorizerMBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._CertPathBuilder = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._CertPathProviderTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._CertPathProviders = new CertPathProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._CompatibilityObjectName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._CredentialMapperTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._CredentialMappers = new CredentialMapperMBean[0];
               if (initOne) {
                  break;
               }
            case 36:
               this._DeployableProviderSynchronizationTimeout = new Integer(60000);
               if (initOne) {
                  break;
               }
            case 42:
               this._IdentityAssertionCacheTTL = 300;
               if (initOne) {
                  break;
               }
            case 43:
               this._IdentityAssertionDoNotCacheContextElements = StringHelper.split(new String[0]);
               if (initOne) {
                  break;
               }
            case 40:
               this._IdentityAssertionHeaderNamePrecedence = new String[0];
               if (initOne) {
                  break;
               }
            case 39:
               this._ManagementIdentityDomain = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._MaxWebLogicPrincipalsInCache = new Integer(500);
               if (initOne) {
                  break;
               }
            case 28:
               this._Name = "Realm";
               if (initOne) {
                  break;
               }
            case 33:
               this._PasswordValidatorTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 34:
               this._PasswordValidators = new PasswordValidatorMBean[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._RDBMSSecurityStore = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._RetireTimeoutSeconds = 60;
               if (initOne) {
                  break;
               }
            case 7:
               this._RoleMapperTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._RoleMappers = new RoleMapperMBean[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._SecurityDDModel = "DDOnly";
               if (initOne) {
                  break;
               }
            case 17:
               this._UserLockoutManager = new UserLockoutManagerMBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._UserLockoutManager);
               if (initOne) {
                  break;
               }
            case 37:
               this._AutoRestartOnNonDynamicChanges = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._CombinedRoleMappingEnabled = true;
               if (initOne) {
                  break;
               }
            case 25:
               this._customizer.setDefaultRealm(false);
               if (initOne) {
                  break;
               }
            case 29:
               this._DelegateMBeanAuthorization = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._DeployCredentialMappingIgnored = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._DeployPolicyIgnored = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._DeployRoleIgnored = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._DeployableProviderSynchronizationEnabled = false;
               if (initOne) {
                  break;
               }
            case 26:
               this._EnableWebLogicPrincipalValidatorCache = true;
               if (initOne) {
                  break;
               }
            case 21:
               this._FullyDelegateAuthorization = false;
               if (initOne) {
                  break;
               }
            case 41:
               this._IdentityAssertionCacheEnabled = true;
               if (initOne) {
                  break;
               }
            case 22:
               this._ValidateDDSecurityData = false;
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.RealmMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 28;
               }
            case 5:
            case 6:
            case 8:
            case 9:
            case 14:
            case 30:
            case 31:
            case 34:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 44:
            case 45:
            case 46:
            default:
               break;
            case 7:
               if (s.equals("auditor")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("authorizer")) {
                  return 8;
               }
               break;
            case 11:
               if (s.equals("adjudicator")) {
                  return 10;
               }

               if (s.equals("role-mapper")) {
                  return 6;
               }
               break;
            case 12:
               if (s.equals("auditor-type")) {
                  return 3;
               }

               if (s.equals("auth-methods")) {
                  return 30;
               }
               break;
            case 13:
               if (s.equals("default-realm")) {
                  return 25;
               }
               break;
            case 15:
               if (s.equals("authorizer-type")) {
                  return 9;
               }
               break;
            case 16:
               if (s.equals("adjudicator-type")) {
                  return 11;
               }

               if (s.equals("role-mapper-type")) {
                  return 7;
               }

               if (s.equals("securitydd-model")) {
                  return 23;
               }
               break;
            case 17:
               if (s.equals("cert-path-builder")) {
                  return 16;
               }

               if (s.equals("credential-mapper")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("cert-path-provider")) {
                  return 14;
               }

               if (s.equals("password-validator")) {
                  return 34;
               }
               break;
            case 19:
               if (s.equals("deploy-role-ignored")) {
                  return 18;
               }
               break;
            case 20:
               if (s.equals("rdbms-security-store")) {
                  return 32;
               }

               if (s.equals("user-lockout-manager")) {
                  return 17;
               }
               break;
            case 21:
               if (s.equals("deploy-policy-ignored")) {
                  return 19;
               }
               break;
            case 22:
               if (s.equals("credential-mapper-type")) {
                  return 13;
               }

               if (s.equals("retire-timeout-seconds")) {
                  return 38;
               }
               break;
            case 23:
               if (s.equals("authentication-provider")) {
                  return 4;
               }

               if (s.equals("cert-path-provider-type")) {
                  return 15;
               }

               if (s.equals("password-validator-type")) {
                  return 33;
               }
               break;
            case 24:
               if (s.equals("validatedd-security-data")) {
                  return 22;
               }
               break;
            case 25:
               if (s.equals("compatibility-object-name")) {
                  return 31;
               }
               break;
            case 26:
               if (s.equals("management-identity-domain")) {
                  return 39;
               }
               break;
            case 27:
               if (s.equals("identity-assertion-cachettl")) {
                  return 42;
               }
               break;
            case 28:
               if (s.equals("authentication-provider-type")) {
                  return 5;
               }

               if (s.equals("delegatem-bean-authorization")) {
                  return 29;
               }

               if (s.equals("fully-delegate-authorization")) {
                  return 21;
               }
               break;
            case 29:
               if (s.equals("combined-role-mapping-enabled")) {
                  return 24;
               }
               break;
            case 32:
               if (s.equals("identity-assertion-cache-enabled")) {
                  return 41;
               }
               break;
            case 33:
               if (s.equals("max-web-logic-principals-in-cache")) {
                  return 27;
               }

               if (s.equals("deploy-credential-mapping-ignored")) {
                  return 20;
               }
               break;
            case 35:
               if (s.equals("auto-restart-on-non-dynamic-changes")) {
                  return 37;
               }
               break;
            case 41:
               if (s.equals("identity-assertion-header-name-precedence")) {
                  return 40;
               }
               break;
            case 42:
               if (s.equals("enable-web-logic-principal-validator-cache")) {
                  return 26;
               }
               break;
            case 43:
               if (s.equals("deployable-provider-synchronization-timeout")) {
                  return 36;
               }

               if (s.equals("deployable-provider-synchronization-enabled")) {
                  return 35;
               }
               break;
            case 47:
               if (s.equals("identity-assertion-do-not-cache-context-element")) {
                  return 43;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new AuditorMBeanImpl.SchemaHelper2();
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            default:
               return super.getSchemaHelper(propIndex);
            case 4:
               return new AuthenticationProviderMBeanImpl.SchemaHelper2();
            case 6:
               return new RoleMapperMBeanImpl.SchemaHelper2();
            case 8:
               return new AuthorizerMBeanImpl.SchemaHelper2();
            case 10:
               return new AdjudicatorMBeanImpl.SchemaHelper2();
            case 12:
               return new CredentialMapperMBeanImpl.SchemaHelper2();
            case 14:
               return new CertPathProviderMBeanImpl.SchemaHelper2();
            case 17:
               return new UserLockoutManagerMBeanImpl.SchemaHelper2();
            case 32:
               return new RDBMSSecurityStoreMBeanImpl.SchemaHelper2();
            case 34:
               return new PasswordValidatorMBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "auditor";
            case 3:
               return "auditor-type";
            case 4:
               return "authentication-provider";
            case 5:
               return "authentication-provider-type";
            case 6:
               return "role-mapper";
            case 7:
               return "role-mapper-type";
            case 8:
               return "authorizer";
            case 9:
               return "authorizer-type";
            case 10:
               return "adjudicator";
            case 11:
               return "adjudicator-type";
            case 12:
               return "credential-mapper";
            case 13:
               return "credential-mapper-type";
            case 14:
               return "cert-path-provider";
            case 15:
               return "cert-path-provider-type";
            case 16:
               return "cert-path-builder";
            case 17:
               return "user-lockout-manager";
            case 18:
               return "deploy-role-ignored";
            case 19:
               return "deploy-policy-ignored";
            case 20:
               return "deploy-credential-mapping-ignored";
            case 21:
               return "fully-delegate-authorization";
            case 22:
               return "validatedd-security-data";
            case 23:
               return "securitydd-model";
            case 24:
               return "combined-role-mapping-enabled";
            case 25:
               return "default-realm";
            case 26:
               return "enable-web-logic-principal-validator-cache";
            case 27:
               return "max-web-logic-principals-in-cache";
            case 28:
               return "name";
            case 29:
               return "delegatem-bean-authorization";
            case 30:
               return "auth-methods";
            case 31:
               return "compatibility-object-name";
            case 32:
               return "rdbms-security-store";
            case 33:
               return "password-validator-type";
            case 34:
               return "password-validator";
            case 35:
               return "deployable-provider-synchronization-enabled";
            case 36:
               return "deployable-provider-synchronization-timeout";
            case 37:
               return "auto-restart-on-non-dynamic-changes";
            case 38:
               return "retire-timeout-seconds";
            case 39:
               return "management-identity-domain";
            case 40:
               return "identity-assertion-header-name-precedence";
            case 41:
               return "identity-assertion-cache-enabled";
            case 42:
               return "identity-assertion-cachettl";
            case 43:
               return "identity-assertion-do-not-cache-context-element";
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
            case 10:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 42:
            default:
               return super.isArray(propIndex);
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 40:
               return true;
            case 43:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            default:
               return super.isBean(propIndex);
            case 4:
               return true;
            case 6:
               return true;
            case 8:
               return true;
            case 10:
               return true;
            case 12:
               return true;
            case 14:
               return true;
            case 17:
               return true;
            case 32:
               return true;
            case 34:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private RealmMBeanImpl bean;

      protected Helper(RealmMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Auditors";
            case 3:
               return "AuditorTypes";
            case 4:
               return "AuthenticationProviders";
            case 5:
               return "AuthenticationProviderTypes";
            case 6:
               return "RoleMappers";
            case 7:
               return "RoleMapperTypes";
            case 8:
               return "Authorizers";
            case 9:
               return "AuthorizerTypes";
            case 10:
               return "Adjudicator";
            case 11:
               return "AdjudicatorTypes";
            case 12:
               return "CredentialMappers";
            case 13:
               return "CredentialMapperTypes";
            case 14:
               return "CertPathProviders";
            case 15:
               return "CertPathProviderTypes";
            case 16:
               return "CertPathBuilder";
            case 17:
               return "UserLockoutManager";
            case 18:
               return "DeployRoleIgnored";
            case 19:
               return "DeployPolicyIgnored";
            case 20:
               return "DeployCredentialMappingIgnored";
            case 21:
               return "FullyDelegateAuthorization";
            case 22:
               return "ValidateDDSecurityData";
            case 23:
               return "SecurityDDModel";
            case 24:
               return "CombinedRoleMappingEnabled";
            case 25:
               return "DefaultRealm";
            case 26:
               return "EnableWebLogicPrincipalValidatorCache";
            case 27:
               return "MaxWebLogicPrincipalsInCache";
            case 28:
               return "Name";
            case 29:
               return "DelegateMBeanAuthorization";
            case 30:
               return "AuthMethods";
            case 31:
               return "CompatibilityObjectName";
            case 32:
               return "RDBMSSecurityStore";
            case 33:
               return "PasswordValidatorTypes";
            case 34:
               return "PasswordValidators";
            case 35:
               return "DeployableProviderSynchronizationEnabled";
            case 36:
               return "DeployableProviderSynchronizationTimeout";
            case 37:
               return "AutoRestartOnNonDynamicChanges";
            case 38:
               return "RetireTimeoutSeconds";
            case 39:
               return "ManagementIdentityDomain";
            case 40:
               return "IdentityAssertionHeaderNamePrecedence";
            case 41:
               return "IdentityAssertionCacheEnabled";
            case 42:
               return "IdentityAssertionCacheTTL";
            case 43:
               return "IdentityAssertionDoNotCacheContextElements";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Adjudicator")) {
            return 10;
         } else if (propName.equals("AdjudicatorTypes")) {
            return 11;
         } else if (propName.equals("AuditorTypes")) {
            return 3;
         } else if (propName.equals("Auditors")) {
            return 2;
         } else if (propName.equals("AuthMethods")) {
            return 30;
         } else if (propName.equals("AuthenticationProviderTypes")) {
            return 5;
         } else if (propName.equals("AuthenticationProviders")) {
            return 4;
         } else if (propName.equals("AuthorizerTypes")) {
            return 9;
         } else if (propName.equals("Authorizers")) {
            return 8;
         } else if (propName.equals("CertPathBuilder")) {
            return 16;
         } else if (propName.equals("CertPathProviderTypes")) {
            return 15;
         } else if (propName.equals("CertPathProviders")) {
            return 14;
         } else if (propName.equals("CompatibilityObjectName")) {
            return 31;
         } else if (propName.equals("CredentialMapperTypes")) {
            return 13;
         } else if (propName.equals("CredentialMappers")) {
            return 12;
         } else if (propName.equals("DeployableProviderSynchronizationTimeout")) {
            return 36;
         } else if (propName.equals("IdentityAssertionCacheTTL")) {
            return 42;
         } else if (propName.equals("IdentityAssertionDoNotCacheContextElements")) {
            return 43;
         } else if (propName.equals("IdentityAssertionHeaderNamePrecedence")) {
            return 40;
         } else if (propName.equals("ManagementIdentityDomain")) {
            return 39;
         } else if (propName.equals("MaxWebLogicPrincipalsInCache")) {
            return 27;
         } else if (propName.equals("Name")) {
            return 28;
         } else if (propName.equals("PasswordValidatorTypes")) {
            return 33;
         } else if (propName.equals("PasswordValidators")) {
            return 34;
         } else if (propName.equals("RDBMSSecurityStore")) {
            return 32;
         } else if (propName.equals("RetireTimeoutSeconds")) {
            return 38;
         } else if (propName.equals("RoleMapperTypes")) {
            return 7;
         } else if (propName.equals("RoleMappers")) {
            return 6;
         } else if (propName.equals("SecurityDDModel")) {
            return 23;
         } else if (propName.equals("UserLockoutManager")) {
            return 17;
         } else if (propName.equals("AutoRestartOnNonDynamicChanges")) {
            return 37;
         } else if (propName.equals("CombinedRoleMappingEnabled")) {
            return 24;
         } else if (propName.equals("DefaultRealm")) {
            return 25;
         } else if (propName.equals("DelegateMBeanAuthorization")) {
            return 29;
         } else if (propName.equals("DeployCredentialMappingIgnored")) {
            return 20;
         } else if (propName.equals("DeployPolicyIgnored")) {
            return 19;
         } else if (propName.equals("DeployRoleIgnored")) {
            return 18;
         } else if (propName.equals("DeployableProviderSynchronizationEnabled")) {
            return 35;
         } else if (propName.equals("EnableWebLogicPrincipalValidatorCache")) {
            return 26;
         } else if (propName.equals("FullyDelegateAuthorization")) {
            return 21;
         } else if (propName.equals("IdentityAssertionCacheEnabled")) {
            return 41;
         } else {
            return propName.equals("ValidateDDSecurityData") ? 22 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAdjudicator() != null) {
            iterators.add(new ArrayIterator(new AdjudicatorMBean[]{this.bean.getAdjudicator()}));
         }

         iterators.add(new ArrayIterator(this.bean.getAuditors()));
         iterators.add(new ArrayIterator(this.bean.getAuthenticationProviders()));
         iterators.add(new ArrayIterator(this.bean.getAuthorizers()));
         iterators.add(new ArrayIterator(this.bean.getCertPathProviders()));
         iterators.add(new ArrayIterator(this.bean.getCredentialMappers()));
         iterators.add(new ArrayIterator(this.bean.getPasswordValidators()));
         if (this.bean.getRDBMSSecurityStore() != null) {
            iterators.add(new ArrayIterator(new RDBMSSecurityStoreMBean[]{this.bean.getRDBMSSecurityStore()}));
         }

         iterators.add(new ArrayIterator(this.bean.getRoleMappers()));
         if (this.bean.getUserLockoutManager() != null) {
            iterators.add(new ArrayIterator(new UserLockoutManagerMBean[]{this.bean.getUserLockoutManager()}));
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
            childValue = this.computeChildHashValue(this.bean.getAdjudicator());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAdjudicatorTypesSet()) {
               buf.append("AdjudicatorTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAdjudicatorTypes())));
            }

            if (this.bean.isAuditorTypesSet()) {
               buf.append("AuditorTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAuditorTypes())));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getAuditors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAuditors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAuthMethodsSet()) {
               buf.append("AuthMethods");
               buf.append(String.valueOf(this.bean.getAuthMethods()));
            }

            if (this.bean.isAuthenticationProviderTypesSet()) {
               buf.append("AuthenticationProviderTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAuthenticationProviderTypes())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAuthenticationProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAuthenticationProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAuthorizerTypesSet()) {
               buf.append("AuthorizerTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAuthorizerTypes())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAuthorizers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAuthorizers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCertPathBuilderSet()) {
               buf.append("CertPathBuilder");
               buf.append(String.valueOf(this.bean.getCertPathBuilder()));
            }

            if (this.bean.isCertPathProviderTypesSet()) {
               buf.append("CertPathProviderTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCertPathProviderTypes())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCertPathProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCertPathProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCompatibilityObjectNameSet()) {
               buf.append("CompatibilityObjectName");
               buf.append(String.valueOf(this.bean.getCompatibilityObjectName()));
            }

            if (this.bean.isCredentialMapperTypesSet()) {
               buf.append("CredentialMapperTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCredentialMapperTypes())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCredentialMappers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCredentialMappers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDeployableProviderSynchronizationTimeoutSet()) {
               buf.append("DeployableProviderSynchronizationTimeout");
               buf.append(String.valueOf(this.bean.getDeployableProviderSynchronizationTimeout()));
            }

            if (this.bean.isIdentityAssertionCacheTTLSet()) {
               buf.append("IdentityAssertionCacheTTL");
               buf.append(String.valueOf(this.bean.getIdentityAssertionCacheTTL()));
            }

            if (this.bean.isIdentityAssertionDoNotCacheContextElementsSet()) {
               buf.append("IdentityAssertionDoNotCacheContextElements");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIdentityAssertionDoNotCacheContextElements())));
            }

            if (this.bean.isIdentityAssertionHeaderNamePrecedenceSet()) {
               buf.append("IdentityAssertionHeaderNamePrecedence");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIdentityAssertionHeaderNamePrecedence())));
            }

            if (this.bean.isManagementIdentityDomainSet()) {
               buf.append("ManagementIdentityDomain");
               buf.append(String.valueOf(this.bean.getManagementIdentityDomain()));
            }

            if (this.bean.isMaxWebLogicPrincipalsInCacheSet()) {
               buf.append("MaxWebLogicPrincipalsInCache");
               buf.append(String.valueOf(this.bean.getMaxWebLogicPrincipalsInCache()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPasswordValidatorTypesSet()) {
               buf.append("PasswordValidatorTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordValidatorTypes())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPasswordValidators().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPasswordValidators()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getRDBMSSecurityStore());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRetireTimeoutSecondsSet()) {
               buf.append("RetireTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getRetireTimeoutSeconds()));
            }

            if (this.bean.isRoleMapperTypesSet()) {
               buf.append("RoleMapperTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRoleMapperTypes())));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getRoleMappers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getRoleMappers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSecurityDDModelSet()) {
               buf.append("SecurityDDModel");
               buf.append(String.valueOf(this.bean.getSecurityDDModel()));
            }

            childValue = this.computeChildHashValue(this.bean.getUserLockoutManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAutoRestartOnNonDynamicChangesSet()) {
               buf.append("AutoRestartOnNonDynamicChanges");
               buf.append(String.valueOf(this.bean.isAutoRestartOnNonDynamicChanges()));
            }

            if (this.bean.isCombinedRoleMappingEnabledSet()) {
               buf.append("CombinedRoleMappingEnabled");
               buf.append(String.valueOf(this.bean.isCombinedRoleMappingEnabled()));
            }

            if (this.bean.isDefaultRealmSet()) {
               buf.append("DefaultRealm");
               buf.append(String.valueOf(this.bean.isDefaultRealm()));
            }

            if (this.bean.isDelegateMBeanAuthorizationSet()) {
               buf.append("DelegateMBeanAuthorization");
               buf.append(String.valueOf(this.bean.isDelegateMBeanAuthorization()));
            }

            if (this.bean.isDeployCredentialMappingIgnoredSet()) {
               buf.append("DeployCredentialMappingIgnored");
               buf.append(String.valueOf(this.bean.isDeployCredentialMappingIgnored()));
            }

            if (this.bean.isDeployPolicyIgnoredSet()) {
               buf.append("DeployPolicyIgnored");
               buf.append(String.valueOf(this.bean.isDeployPolicyIgnored()));
            }

            if (this.bean.isDeployRoleIgnoredSet()) {
               buf.append("DeployRoleIgnored");
               buf.append(String.valueOf(this.bean.isDeployRoleIgnored()));
            }

            if (this.bean.isDeployableProviderSynchronizationEnabledSet()) {
               buf.append("DeployableProviderSynchronizationEnabled");
               buf.append(String.valueOf(this.bean.isDeployableProviderSynchronizationEnabled()));
            }

            if (this.bean.isEnableWebLogicPrincipalValidatorCacheSet()) {
               buf.append("EnableWebLogicPrincipalValidatorCache");
               buf.append(String.valueOf(this.bean.isEnableWebLogicPrincipalValidatorCache()));
            }

            if (this.bean.isFullyDelegateAuthorizationSet()) {
               buf.append("FullyDelegateAuthorization");
               buf.append(String.valueOf(this.bean.isFullyDelegateAuthorization()));
            }

            if (this.bean.isIdentityAssertionCacheEnabledSet()) {
               buf.append("IdentityAssertionCacheEnabled");
               buf.append(String.valueOf(this.bean.isIdentityAssertionCacheEnabled()));
            }

            if (this.bean.isValidateDDSecurityDataSet()) {
               buf.append("ValidateDDSecurityData");
               buf.append(String.valueOf(this.bean.isValidateDDSecurityData()));
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
            RealmMBeanImpl otherTyped = (RealmMBeanImpl)other;
            this.computeChildDiff("Adjudicator", this.bean.getAdjudicator(), otherTyped.getAdjudicator(), false);
            this.computeChildDiff("Auditors", this.bean.getAuditors(), otherTyped.getAuditors(), false, true);
            this.computeDiff("AuthMethods", this.bean.getAuthMethods(), otherTyped.getAuthMethods(), false);
            this.computeChildDiff("AuthenticationProviders", this.bean.getAuthenticationProviders(), otherTyped.getAuthenticationProviders(), false, true);
            this.computeChildDiff("Authorizers", this.bean.getAuthorizers(), otherTyped.getAuthorizers(), false, true);
            this.computeDiff("CertPathBuilder", this.bean.getCertPathBuilder(), otherTyped.getCertPathBuilder(), false);
            this.computeChildDiff("CertPathProviders", this.bean.getCertPathProviders(), otherTyped.getCertPathProviders(), false, true);
            this.computeDiff("CompatibilityObjectName", this.bean.getCompatibilityObjectName(), otherTyped.getCompatibilityObjectName(), false);
            this.computeChildDiff("CredentialMappers", this.bean.getCredentialMappers(), otherTyped.getCredentialMappers(), false, true);
            this.computeDiff("DeployableProviderSynchronizationTimeout", this.bean.getDeployableProviderSynchronizationTimeout(), otherTyped.getDeployableProviderSynchronizationTimeout(), false);
            this.computeDiff("IdentityAssertionCacheTTL", this.bean.getIdentityAssertionCacheTTL(), otherTyped.getIdentityAssertionCacheTTL(), false);
            this.computeDiff("IdentityAssertionDoNotCacheContextElements", this.bean.getIdentityAssertionDoNotCacheContextElements(), otherTyped.getIdentityAssertionDoNotCacheContextElements(), false);
            this.computeDiff("IdentityAssertionHeaderNamePrecedence", this.bean.getIdentityAssertionHeaderNamePrecedence(), otherTyped.getIdentityAssertionHeaderNamePrecedence(), true, true);
            this.computeDiff("ManagementIdentityDomain", this.bean.getManagementIdentityDomain(), otherTyped.getManagementIdentityDomain(), false);
            this.computeDiff("MaxWebLogicPrincipalsInCache", this.bean.getMaxWebLogicPrincipalsInCache(), otherTyped.getMaxWebLogicPrincipalsInCache(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("PasswordValidators", this.bean.getPasswordValidators(), otherTyped.getPasswordValidators(), false, true);
            this.computeChildDiff("RDBMSSecurityStore", this.bean.getRDBMSSecurityStore(), otherTyped.getRDBMSSecurityStore(), false);
            this.computeDiff("RetireTimeoutSeconds", this.bean.getRetireTimeoutSeconds(), otherTyped.getRetireTimeoutSeconds(), true);
            this.computeChildDiff("RoleMappers", this.bean.getRoleMappers(), otherTyped.getRoleMappers(), false, true);
            this.computeDiff("SecurityDDModel", this.bean.getSecurityDDModel(), otherTyped.getSecurityDDModel(), true);
            this.computeSubDiff("UserLockoutManager", this.bean.getUserLockoutManager(), otherTyped.getUserLockoutManager());
            this.computeDiff("AutoRestartOnNonDynamicChanges", this.bean.isAutoRestartOnNonDynamicChanges(), otherTyped.isAutoRestartOnNonDynamicChanges(), true);
            this.computeDiff("CombinedRoleMappingEnabled", this.bean.isCombinedRoleMappingEnabled(), otherTyped.isCombinedRoleMappingEnabled(), true);
            this.computeDiff("DelegateMBeanAuthorization", this.bean.isDelegateMBeanAuthorization(), otherTyped.isDelegateMBeanAuthorization(), false);
            this.computeDiff("DeployCredentialMappingIgnored", this.bean.isDeployCredentialMappingIgnored(), otherTyped.isDeployCredentialMappingIgnored(), true);
            this.computeDiff("DeployPolicyIgnored", this.bean.isDeployPolicyIgnored(), otherTyped.isDeployPolicyIgnored(), true);
            this.computeDiff("DeployRoleIgnored", this.bean.isDeployRoleIgnored(), otherTyped.isDeployRoleIgnored(), true);
            this.computeDiff("DeployableProviderSynchronizationEnabled", this.bean.isDeployableProviderSynchronizationEnabled(), otherTyped.isDeployableProviderSynchronizationEnabled(), false);
            this.computeDiff("EnableWebLogicPrincipalValidatorCache", this.bean.isEnableWebLogicPrincipalValidatorCache(), otherTyped.isEnableWebLogicPrincipalValidatorCache(), false);
            this.computeDiff("FullyDelegateAuthorization", this.bean.isFullyDelegateAuthorization(), otherTyped.isFullyDelegateAuthorization(), false);
            this.computeDiff("IdentityAssertionCacheEnabled", this.bean.isIdentityAssertionCacheEnabled(), otherTyped.isIdentityAssertionCacheEnabled(), false);
            this.computeDiff("ValidateDDSecurityData", this.bean.isValidateDDSecurityData(), otherTyped.isValidateDDSecurityData(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RealmMBeanImpl original = (RealmMBeanImpl)event.getSourceBean();
            RealmMBeanImpl proposed = (RealmMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Adjudicator")) {
                  if (type == 2) {
                     original.setAdjudicator((AdjudicatorMBean)this.createCopy((AbstractDescriptorBean)proposed.getAdjudicator()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Adjudicator", original.getAdjudicator());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (!prop.equals("AdjudicatorTypes") && !prop.equals("AuditorTypes")) {
                  if (prop.equals("Auditors")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addAuditor((AuditorMBean)update.getAddedObject());
                        }

                        this.reorderArrayObjects(original.getAuditors(), proposed.getAuditors());
                     } else if (type == 3) {
                        original.removeAuditor((AuditorMBean)update.getRemovedObject());
                     } else {
                        this.reorderArrayObjects(original.getAuditors(), proposed.getAuditors());
                     }

                     if (original.getAuditors() == null || original.getAuditors().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     }
                  } else if (prop.equals("AuthMethods")) {
                     original.setAuthMethods(proposed.getAuthMethods());
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  } else if (!prop.equals("AuthenticationProviderTypes")) {
                     if (prop.equals("AuthenticationProviders")) {
                        if (type == 2) {
                           if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                              update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                              original.addAuthenticationProvider((AuthenticationProviderMBean)update.getAddedObject());
                           }

                           this.reorderArrayObjects(original.getAuthenticationProviders(), proposed.getAuthenticationProviders());
                        } else if (type == 3) {
                           original.removeAuthenticationProvider((AuthenticationProviderMBean)update.getRemovedObject());
                        } else {
                           this.reorderArrayObjects(original.getAuthenticationProviders(), proposed.getAuthenticationProviders());
                        }

                        if (original.getAuthenticationProviders() == null || original.getAuthenticationProviders().length == 0) {
                           original._conditionalUnset(update.isUnsetUpdate(), 4);
                        }
                     } else if (!prop.equals("AuthorizerTypes")) {
                        if (prop.equals("Authorizers")) {
                           if (type == 2) {
                              if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                 update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                 original.addAuthorizer((AuthorizerMBean)update.getAddedObject());
                              }

                              this.reorderArrayObjects(original.getAuthorizers(), proposed.getAuthorizers());
                           } else if (type == 3) {
                              original.removeAuthorizer((AuthorizerMBean)update.getRemovedObject());
                           } else {
                              this.reorderArrayObjects(original.getAuthorizers(), proposed.getAuthorizers());
                           }

                           if (original.getAuthorizers() == null || original.getAuthorizers().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 8);
                           }
                        } else if (prop.equals("CertPathBuilder")) {
                           original.setCertPathBuilderAsString(proposed.getCertPathBuilderAsString());
                           original._conditionalUnset(update.isUnsetUpdate(), 16);
                        } else if (!prop.equals("CertPathProviderTypes")) {
                           if (prop.equals("CertPathProviders")) {
                              if (type == 2) {
                                 if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                    update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                    original.addCertPathProvider((CertPathProviderMBean)update.getAddedObject());
                                 }

                                 this.reorderArrayObjects(original.getCertPathProviders(), proposed.getCertPathProviders());
                              } else if (type == 3) {
                                 original.removeCertPathProvider((CertPathProviderMBean)update.getRemovedObject());
                              } else {
                                 this.reorderArrayObjects(original.getCertPathProviders(), proposed.getCertPathProviders());
                              }

                              if (original.getCertPathProviders() == null || original.getCertPathProviders().length == 0) {
                                 original._conditionalUnset(update.isUnsetUpdate(), 14);
                              }
                           } else if (prop.equals("CompatibilityObjectName")) {
                              original._conditionalUnset(update.isUnsetUpdate(), 31);
                           } else if (!prop.equals("CredentialMapperTypes")) {
                              if (prop.equals("CredentialMappers")) {
                                 if (type == 2) {
                                    if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                       update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                       original.addCredentialMapper((CredentialMapperMBean)update.getAddedObject());
                                    }

                                    this.reorderArrayObjects(original.getCredentialMappers(), proposed.getCredentialMappers());
                                 } else if (type == 3) {
                                    original.removeCredentialMapper((CredentialMapperMBean)update.getRemovedObject());
                                 } else {
                                    this.reorderArrayObjects(original.getCredentialMappers(), proposed.getCredentialMappers());
                                 }

                                 if (original.getCredentialMappers() == null || original.getCredentialMappers().length == 0) {
                                    original._conditionalUnset(update.isUnsetUpdate(), 12);
                                 }
                              } else if (prop.equals("DeployableProviderSynchronizationTimeout")) {
                                 original.setDeployableProviderSynchronizationTimeout(proposed.getDeployableProviderSynchronizationTimeout());
                                 original._conditionalUnset(update.isUnsetUpdate(), 36);
                              } else if (prop.equals("IdentityAssertionCacheTTL")) {
                                 original.setIdentityAssertionCacheTTL(proposed.getIdentityAssertionCacheTTL());
                                 original._conditionalUnset(update.isUnsetUpdate(), 42);
                              } else if (prop.equals("IdentityAssertionDoNotCacheContextElements")) {
                                 original.setIdentityAssertionDoNotCacheContextElements(proposed.getIdentityAssertionDoNotCacheContextElements());
                                 original._conditionalUnset(update.isUnsetUpdate(), 43);
                              } else if (prop.equals("IdentityAssertionHeaderNamePrecedence")) {
                                 original.setIdentityAssertionHeaderNamePrecedence(proposed.getIdentityAssertionHeaderNamePrecedence());
                                 original._conditionalUnset(update.isUnsetUpdate(), 40);
                              } else if (prop.equals("ManagementIdentityDomain")) {
                                 original.setManagementIdentityDomain(proposed.getManagementIdentityDomain());
                                 original._conditionalUnset(update.isUnsetUpdate(), 39);
                              } else if (prop.equals("MaxWebLogicPrincipalsInCache")) {
                                 original.setMaxWebLogicPrincipalsInCache(proposed.getMaxWebLogicPrincipalsInCache());
                                 original._conditionalUnset(update.isUnsetUpdate(), 27);
                              } else if (prop.equals("Name")) {
                                 original.setName(proposed.getName());
                                 original._conditionalUnset(update.isUnsetUpdate(), 28);
                              } else if (!prop.equals("PasswordValidatorTypes")) {
                                 if (prop.equals("PasswordValidators")) {
                                    if (type == 2) {
                                       if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                          update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                          original.addPasswordValidator((PasswordValidatorMBean)update.getAddedObject());
                                       }

                                       this.reorderArrayObjects(original.getPasswordValidators(), proposed.getPasswordValidators());
                                    } else if (type == 3) {
                                       original.removePasswordValidator((PasswordValidatorMBean)update.getRemovedObject());
                                    } else {
                                       this.reorderArrayObjects(original.getPasswordValidators(), proposed.getPasswordValidators());
                                    }

                                    if (original.getPasswordValidators() == null || original.getPasswordValidators().length == 0) {
                                       original._conditionalUnset(update.isUnsetUpdate(), 34);
                                    }
                                 } else if (prop.equals("RDBMSSecurityStore")) {
                                    if (type == 2) {
                                       original.setRDBMSSecurityStore((RDBMSSecurityStoreMBean)this.createCopy((AbstractDescriptorBean)proposed.getRDBMSSecurityStore()));
                                    } else {
                                       if (type != 3) {
                                          throw new AssertionError("Invalid type: " + type);
                                       }

                                       original._destroySingleton("RDBMSSecurityStore", original.getRDBMSSecurityStore());
                                    }

                                    original._conditionalUnset(update.isUnsetUpdate(), 32);
                                 } else if (prop.equals("RetireTimeoutSeconds")) {
                                    original.setRetireTimeoutSeconds(proposed.getRetireTimeoutSeconds());
                                    original._conditionalUnset(update.isUnsetUpdate(), 38);
                                 } else if (!prop.equals("RoleMapperTypes")) {
                                    if (prop.equals("RoleMappers")) {
                                       if (type == 2) {
                                          if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                                             update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                                             original.addRoleMapper((RoleMapperMBean)update.getAddedObject());
                                          }

                                          this.reorderArrayObjects(original.getRoleMappers(), proposed.getRoleMappers());
                                       } else if (type == 3) {
                                          original.removeRoleMapper((RoleMapperMBean)update.getRemovedObject());
                                       } else {
                                          this.reorderArrayObjects(original.getRoleMappers(), proposed.getRoleMappers());
                                       }

                                       if (original.getRoleMappers() == null || original.getRoleMappers().length == 0) {
                                          original._conditionalUnset(update.isUnsetUpdate(), 6);
                                       }
                                    } else if (prop.equals("SecurityDDModel")) {
                                       original.setSecurityDDModel(proposed.getSecurityDDModel());
                                       original._conditionalUnset(update.isUnsetUpdate(), 23);
                                    } else if (prop.equals("UserLockoutManager")) {
                                       if (type == 2) {
                                          original.setUserLockoutManager((UserLockoutManagerMBean)this.createCopy((AbstractDescriptorBean)proposed.getUserLockoutManager()));
                                       } else {
                                          if (type != 3) {
                                             throw new AssertionError("Invalid type: " + type);
                                          }

                                          original._destroySingleton("UserLockoutManager", original.getUserLockoutManager());
                                       }

                                       original._conditionalUnset(update.isUnsetUpdate(), 17);
                                    } else if (prop.equals("AutoRestartOnNonDynamicChanges")) {
                                       original.setAutoRestartOnNonDynamicChanges(proposed.isAutoRestartOnNonDynamicChanges());
                                       original._conditionalUnset(update.isUnsetUpdate(), 37);
                                    } else if (prop.equals("CombinedRoleMappingEnabled")) {
                                       original.setCombinedRoleMappingEnabled(proposed.isCombinedRoleMappingEnabled());
                                       original._conditionalUnset(update.isUnsetUpdate(), 24);
                                    } else if (!prop.equals("DefaultRealm")) {
                                       if (prop.equals("DelegateMBeanAuthorization")) {
                                          original.setDelegateMBeanAuthorization(proposed.isDelegateMBeanAuthorization());
                                          original._conditionalUnset(update.isUnsetUpdate(), 29);
                                       } else if (prop.equals("DeployCredentialMappingIgnored")) {
                                          original.setDeployCredentialMappingIgnored(proposed.isDeployCredentialMappingIgnored());
                                          original._conditionalUnset(update.isUnsetUpdate(), 20);
                                       } else if (prop.equals("DeployPolicyIgnored")) {
                                          original.setDeployPolicyIgnored(proposed.isDeployPolicyIgnored());
                                          original._conditionalUnset(update.isUnsetUpdate(), 19);
                                       } else if (prop.equals("DeployRoleIgnored")) {
                                          original.setDeployRoleIgnored(proposed.isDeployRoleIgnored());
                                          original._conditionalUnset(update.isUnsetUpdate(), 18);
                                       } else if (prop.equals("DeployableProviderSynchronizationEnabled")) {
                                          original.setDeployableProviderSynchronizationEnabled(proposed.isDeployableProviderSynchronizationEnabled());
                                          original._conditionalUnset(update.isUnsetUpdate(), 35);
                                       } else if (prop.equals("EnableWebLogicPrincipalValidatorCache")) {
                                          original.setEnableWebLogicPrincipalValidatorCache(proposed.isEnableWebLogicPrincipalValidatorCache());
                                          original._conditionalUnset(update.isUnsetUpdate(), 26);
                                       } else if (prop.equals("FullyDelegateAuthorization")) {
                                          original.setFullyDelegateAuthorization(proposed.isFullyDelegateAuthorization());
                                          original._conditionalUnset(update.isUnsetUpdate(), 21);
                                       } else if (prop.equals("IdentityAssertionCacheEnabled")) {
                                          original.setIdentityAssertionCacheEnabled(proposed.isIdentityAssertionCacheEnabled());
                                          original._conditionalUnset(update.isUnsetUpdate(), 41);
                                       } else if (prop.equals("ValidateDDSecurityData")) {
                                          original.setValidateDDSecurityData(proposed.isValidateDDSecurityData());
                                          original._conditionalUnset(update.isUnsetUpdate(), 22);
                                       } else {
                                          super.applyPropertyUpdate(event, update);
                                       }
                                    }
                                 }
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
            RealmMBeanImpl copy = (RealmMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Adjudicator")) && this.bean.isAdjudicatorSet() && !copy._isSet(10)) {
               Object o = this.bean.getAdjudicator();
               copy.setAdjudicator((AdjudicatorMBean)null);
               copy.setAdjudicator(o == null ? null : (AdjudicatorMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("Auditors")) && this.bean.isAuditorsSet() && !copy._isSet(2)) {
               AuditorMBean[] oldAuditors = this.bean.getAuditors();
               AuditorMBean[] newAuditors = new AuditorMBean[oldAuditors.length];

               for(i = 0; i < newAuditors.length; ++i) {
                  newAuditors[i] = (AuditorMBean)((AuditorMBean)this.createCopy((AbstractDescriptorBean)oldAuditors[i], includeObsolete));
               }

               copy.setAuditors(newAuditors);
            }

            if ((excludeProps == null || !excludeProps.contains("AuthMethods")) && this.bean.isAuthMethodsSet()) {
               copy.setAuthMethods(this.bean.getAuthMethods());
            }

            if ((excludeProps == null || !excludeProps.contains("AuthenticationProviders")) && this.bean.isAuthenticationProvidersSet() && !copy._isSet(4)) {
               AuthenticationProviderMBean[] oldAuthenticationProviders = this.bean.getAuthenticationProviders();
               AuthenticationProviderMBean[] newAuthenticationProviders = new AuthenticationProviderMBean[oldAuthenticationProviders.length];

               for(i = 0; i < newAuthenticationProviders.length; ++i) {
                  newAuthenticationProviders[i] = (AuthenticationProviderMBean)((AuthenticationProviderMBean)this.createCopy((AbstractDescriptorBean)oldAuthenticationProviders[i], includeObsolete));
               }

               copy.setAuthenticationProviders(newAuthenticationProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("Authorizers")) && this.bean.isAuthorizersSet() && !copy._isSet(8)) {
               AuthorizerMBean[] oldAuthorizers = this.bean.getAuthorizers();
               AuthorizerMBean[] newAuthorizers = new AuthorizerMBean[oldAuthorizers.length];

               for(i = 0; i < newAuthorizers.length; ++i) {
                  newAuthorizers[i] = (AuthorizerMBean)((AuthorizerMBean)this.createCopy((AbstractDescriptorBean)oldAuthorizers[i], includeObsolete));
               }

               copy.setAuthorizers(newAuthorizers);
            }

            if ((excludeProps == null || !excludeProps.contains("CertPathBuilder")) && this.bean.isCertPathBuilderSet()) {
               copy._unSet(copy, 16);
               copy.setCertPathBuilderAsString(this.bean.getCertPathBuilderAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("CertPathProviders")) && this.bean.isCertPathProvidersSet() && !copy._isSet(14)) {
               CertPathProviderMBean[] oldCertPathProviders = this.bean.getCertPathProviders();
               CertPathProviderMBean[] newCertPathProviders = new CertPathProviderMBean[oldCertPathProviders.length];

               for(i = 0; i < newCertPathProviders.length; ++i) {
                  newCertPathProviders[i] = (CertPathProviderMBean)((CertPathProviderMBean)this.createCopy((AbstractDescriptorBean)oldCertPathProviders[i], includeObsolete));
               }

               copy.setCertPathProviders(newCertPathProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("CompatibilityObjectName")) && this.bean.isCompatibilityObjectNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialMappers")) && this.bean.isCredentialMappersSet() && !copy._isSet(12)) {
               CredentialMapperMBean[] oldCredentialMappers = this.bean.getCredentialMappers();
               CredentialMapperMBean[] newCredentialMappers = new CredentialMapperMBean[oldCredentialMappers.length];

               for(i = 0; i < newCredentialMappers.length; ++i) {
                  newCredentialMappers[i] = (CredentialMapperMBean)((CredentialMapperMBean)this.createCopy((AbstractDescriptorBean)oldCredentialMappers[i], includeObsolete));
               }

               copy.setCredentialMappers(newCredentialMappers);
            }

            if ((excludeProps == null || !excludeProps.contains("DeployableProviderSynchronizationTimeout")) && this.bean.isDeployableProviderSynchronizationTimeoutSet()) {
               copy.setDeployableProviderSynchronizationTimeout(this.bean.getDeployableProviderSynchronizationTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityAssertionCacheTTL")) && this.bean.isIdentityAssertionCacheTTLSet()) {
               copy.setIdentityAssertionCacheTTL(this.bean.getIdentityAssertionCacheTTL());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("IdentityAssertionDoNotCacheContextElements")) && this.bean.isIdentityAssertionDoNotCacheContextElementsSet()) {
               o = this.bean.getIdentityAssertionDoNotCacheContextElements();
               copy.setIdentityAssertionDoNotCacheContextElements(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityAssertionHeaderNamePrecedence")) && this.bean.isIdentityAssertionHeaderNamePrecedenceSet()) {
               o = this.bean.getIdentityAssertionHeaderNamePrecedence();
               copy.setIdentityAssertionHeaderNamePrecedence(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ManagementIdentityDomain")) && this.bean.isManagementIdentityDomainSet()) {
               copy.setManagementIdentityDomain(this.bean.getManagementIdentityDomain());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxWebLogicPrincipalsInCache")) && this.bean.isMaxWebLogicPrincipalsInCacheSet()) {
               copy.setMaxWebLogicPrincipalsInCache(this.bean.getMaxWebLogicPrincipalsInCache());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordValidators")) && this.bean.isPasswordValidatorsSet() && !copy._isSet(34)) {
               PasswordValidatorMBean[] oldPasswordValidators = this.bean.getPasswordValidators();
               PasswordValidatorMBean[] newPasswordValidators = new PasswordValidatorMBean[oldPasswordValidators.length];

               for(i = 0; i < newPasswordValidators.length; ++i) {
                  newPasswordValidators[i] = (PasswordValidatorMBean)((PasswordValidatorMBean)this.createCopy((AbstractDescriptorBean)oldPasswordValidators[i], includeObsolete));
               }

               copy.setPasswordValidators(newPasswordValidators);
            }

            if ((excludeProps == null || !excludeProps.contains("RDBMSSecurityStore")) && this.bean.isRDBMSSecurityStoreSet() && !copy._isSet(32)) {
               Object o = this.bean.getRDBMSSecurityStore();
               copy.setRDBMSSecurityStore((RDBMSSecurityStoreMBean)null);
               copy.setRDBMSSecurityStore(o == null ? null : (RDBMSSecurityStoreMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("RetireTimeoutSeconds")) && this.bean.isRetireTimeoutSecondsSet()) {
               copy.setRetireTimeoutSeconds(this.bean.getRetireTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RoleMappers")) && this.bean.isRoleMappersSet() && !copy._isSet(6)) {
               RoleMapperMBean[] oldRoleMappers = this.bean.getRoleMappers();
               RoleMapperMBean[] newRoleMappers = new RoleMapperMBean[oldRoleMappers.length];

               for(i = 0; i < newRoleMappers.length; ++i) {
                  newRoleMappers[i] = (RoleMapperMBean)((RoleMapperMBean)this.createCopy((AbstractDescriptorBean)oldRoleMappers[i], includeObsolete));
               }

               copy.setRoleMappers(newRoleMappers);
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityDDModel")) && this.bean.isSecurityDDModelSet()) {
               copy.setSecurityDDModel(this.bean.getSecurityDDModel());
            }

            if ((excludeProps == null || !excludeProps.contains("UserLockoutManager")) && this.bean.isUserLockoutManagerSet() && !copy._isSet(17)) {
               Object o = this.bean.getUserLockoutManager();
               copy.setUserLockoutManager((UserLockoutManagerMBean)null);
               copy.setUserLockoutManager(o == null ? null : (UserLockoutManagerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AutoRestartOnNonDynamicChanges")) && this.bean.isAutoRestartOnNonDynamicChangesSet()) {
               copy.setAutoRestartOnNonDynamicChanges(this.bean.isAutoRestartOnNonDynamicChanges());
            }

            if ((excludeProps == null || !excludeProps.contains("CombinedRoleMappingEnabled")) && this.bean.isCombinedRoleMappingEnabledSet()) {
               copy.setCombinedRoleMappingEnabled(this.bean.isCombinedRoleMappingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DelegateMBeanAuthorization")) && this.bean.isDelegateMBeanAuthorizationSet()) {
               copy.setDelegateMBeanAuthorization(this.bean.isDelegateMBeanAuthorization());
            }

            if ((excludeProps == null || !excludeProps.contains("DeployCredentialMappingIgnored")) && this.bean.isDeployCredentialMappingIgnoredSet()) {
               copy.setDeployCredentialMappingIgnored(this.bean.isDeployCredentialMappingIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("DeployPolicyIgnored")) && this.bean.isDeployPolicyIgnoredSet()) {
               copy.setDeployPolicyIgnored(this.bean.isDeployPolicyIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("DeployRoleIgnored")) && this.bean.isDeployRoleIgnoredSet()) {
               copy.setDeployRoleIgnored(this.bean.isDeployRoleIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("DeployableProviderSynchronizationEnabled")) && this.bean.isDeployableProviderSynchronizationEnabledSet()) {
               copy.setDeployableProviderSynchronizationEnabled(this.bean.isDeployableProviderSynchronizationEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableWebLogicPrincipalValidatorCache")) && this.bean.isEnableWebLogicPrincipalValidatorCacheSet()) {
               copy.setEnableWebLogicPrincipalValidatorCache(this.bean.isEnableWebLogicPrincipalValidatorCache());
            }

            if ((excludeProps == null || !excludeProps.contains("FullyDelegateAuthorization")) && this.bean.isFullyDelegateAuthorizationSet()) {
               copy.setFullyDelegateAuthorization(this.bean.isFullyDelegateAuthorization());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityAssertionCacheEnabled")) && this.bean.isIdentityAssertionCacheEnabledSet()) {
               copy.setIdentityAssertionCacheEnabled(this.bean.isIdentityAssertionCacheEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidateDDSecurityData")) && this.bean.isValidateDDSecurityDataSet()) {
               copy.setValidateDDSecurityData(this.bean.isValidateDDSecurityData());
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
         this.inferSubTree(this.bean.getAdjudicator(), clazz, annotation);
         this.inferSubTree(this.bean.getAuditors(), clazz, annotation);
         this.inferSubTree(this.bean.getAuthenticationProviders(), clazz, annotation);
         this.inferSubTree(this.bean.getAuthorizers(), clazz, annotation);
         this.inferSubTree(this.bean.getCertPathBuilder(), clazz, annotation);
         this.inferSubTree(this.bean.getCertPathProviders(), clazz, annotation);
         this.inferSubTree(this.bean.getCredentialMappers(), clazz, annotation);
         this.inferSubTree(this.bean.getPasswordValidators(), clazz, annotation);
         this.inferSubTree(this.bean.getRDBMSSecurityStore(), clazz, annotation);
         this.inferSubTree(this.bean.getRoleMappers(), clazz, annotation);
         this.inferSubTree(this.bean.getUserLockoutManager(), clazz, annotation);
      }
   }
}
