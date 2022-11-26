package com.bea.common.security.legacy;

import com.bea.common.engine.SecurityServiceRuntimeException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import weblogic.management.security.RealmMBean;

public abstract class ConfigHelperFactory {
   /** @deprecated */
   @Deprecated
   public static ConfigHelperFactory getInstance(ClassLoader cssImplLoader) {
      return getInstance(cssImplLoader, (RealmMBean)null, (LegacyDomainInfo)null);
   }

   public static ConfigHelperFactory getInstance(ClassLoader cssImplLoader, RealmMBean realm, LegacyDomainInfo legacyDomainInfo) {
      try {
         Class cls = Class.forName("com.bea.common.security.internal.legacy.helper.ConfigHelperFactoryImpl", false, cssImplLoader);
         Constructor c = cls.getConstructor(RealmMBean.class, LegacyDomainInfo.class);
         return (ConfigHelperFactory)c.newInstance(realm, legacyDomainInfo);
      } catch (ClassNotFoundException var5) {
         throw new SecurityServiceRuntimeException(var5);
      } catch (InstantiationException var6) {
         throw new SecurityServiceRuntimeException(var6);
      } catch (IllegalAccessException var7) {
         throw new SecurityServiceRuntimeException(var7);
      } catch (NoSuchMethodException var8) {
         throw new SecurityServiceRuntimeException(var8);
      } catch (InvocationTargetException var9) {
         throw new SecurityServiceRuntimeException(var9.getCause());
      }
   }

   public abstract AuditServicesConfigHelper getAuditServicesConfigHelper(RealmMBean var1);

   public abstract AuthenticationServicesConfigHelper getAuthenticationServicesConfigHelper(RealmMBean var1);

   public abstract AuthorizationServicesConfigHelper getAuthorizationServicesConfigHelper(RealmMBean var1);

   public abstract CertPathServicesConfigHelper getCertPathServicesConfigHelper(RealmMBean var1);

   public abstract CredentialMappingServicesConfigHelper getCredentialMappingServicesConfigHelper(RealmMBean var1);

   public abstract SecurityProviderConfigHelper getSecurityProviderConfigHelper();

   public abstract IdentityServicesConfigHelper getIdentityServicesConfigHelper(RealmMBean var1);

   public abstract InternalServicesConfigHelper getInternalServicesConfigHelper(RealmMBean var1);

   public abstract SAMLSingleSignOnServiceConfigHelper getSAMLSingleSignOnServiceConfigHelper(RealmMBean var1);

   public abstract SecurityTokenServicesConfigHelper getSecurityTokenServicesConfigHelper(RealmMBean var1);

   public abstract SAML2SingleSignOnServicesConfigHelper getSAML2SingleSignOnServicesConfigHelper(RealmMBean var1);

   public abstract LoginSessionServiceConfigHelper getLoginSessionServiceConfigHelper(RealmMBean var1);
}
