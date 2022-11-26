package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.AuthenticationServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import java.lang.reflect.Method;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.PasswordValidatorMBean;

public class AuthenticationServicesConfigHelperImpl extends BaseServicesConfigImpl implements AuthenticationServicesConfigHelper {
   private ServiceConfigCustomizerImpl principalValidationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getPrincipalValidationServiceName());
   private ServiceConfigCustomizerImpl jaasAuthenticationConfigurationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getJAASAuthenticationConfigurationServiceName());
   private ServiceConfigCustomizerImpl jaasIdentityAssertionConfigurationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getJAASIdentityAssertionConfigurationServiceName());
   private ServiceConfigCustomizerImpl jaasLoginServiceCustomizer = new ServiceConfigCustomizerImpl(this.getJAASLoginServiceName());
   private ServiceConfigCustomizerImpl identityImpersonationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getIdentityImpersonationServiceName());
   private ServiceConfigCustomizerImpl identityAssertionCallbackServiceCustomizer = new ServiceConfigCustomizerImpl(this.getIdentityAssertionCallbackServiceName());
   private ServiceConfigCustomizerImpl identityAssertionTokenServiceCustomizer = new ServiceConfigCustomizerImpl(this.getIdentityAssertionTokenServiceName());
   private ServiceConfigCustomizerImpl identityAssertionServiceCustomizer = new ServiceConfigCustomizerImpl(this.getIdentityAssertionServiceName());
   private ServiceConfigCustomizerImpl challengeIdentityAssertionTokenServiceCustomizer = new ServiceConfigCustomizerImpl(this.getChallengeIdentityAssertionTokenServiceName());
   private ServiceConfigCustomizerImpl challengeIdentityAssertionServiceCustomizer = new ServiceConfigCustomizerImpl(this.getChallengeIdentityAssertionServiceName());
   private ServiceConfigCustomizerImpl jaasAuthenticationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getJAASAuthenticationServiceName());
   private IdentityCacheServiceConfigCustomizerImpl identityCacheServiceCustomizer = new IdentityCacheServiceConfigCustomizerImpl(this.getIdentityCacheServiceName());
   private ServiceConfigCustomizerImpl negotiateIdentityAsserterServiceCustomizer = new ServiceConfigCustomizerImpl(this.getNegotiateIdentityAsserterServiceName());

   public String getPrincipalValidationServiceName() {
      return PrincipalValidationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getJAASAuthenticationConfigurationServiceName() {
      return JAASAuthenticationConfigurationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getJAASIdentityAssertionConfigurationServiceName() {
      return JAASIdentityAssertionConfigurationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getJAASLoginServiceName() {
      return JAASLoginServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIdentityImpersonationServiceName() {
      return IdentityImpersonationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIdentityAssertionCallbackServiceName() {
      return IdentityAssertionCallbackServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIdentityAssertionTokenServiceName() {
      return IdentityAssertionTokenServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIdentityAssertionServiceName() {
      return IdentityAssertionServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getChallengeIdentityAssertionTokenServiceName() {
      return ChallengeIdentityAssertionTokenServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getChallengeIdentityAssertionServiceName() {
      return ChallengeIdentityAssertionServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getJAASAuthenticationServiceName() {
      return JAASAuthenticationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIdentityCacheServiceName() {
      return IdentityCacheServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getNegotiateIdentityAsserterServiceName() {
      return NegotiateIdentityAsserterServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getPasswordValidationServiceName() {
      return PasswordValidationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIdentityServiceName() {
      return IdentityServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public AuthenticationServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getPrincipalValidationServiceCustomizer() {
      return this.principalValidationServiceCustomizer;
   }

   public ServiceConfigCustomizer getJAASAuthenticationConfigurationServiceCustomizer() {
      return this.jaasAuthenticationConfigurationServiceCustomizer;
   }

   public ServiceConfigCustomizer getJAASIdentityAssertionConfigurationServiceCustomizer() {
      return this.jaasIdentityAssertionConfigurationServiceCustomizer;
   }

   public ServiceConfigCustomizer getJAASLoginServiceCustomizer() {
      return this.jaasLoginServiceCustomizer;
   }

   public ServiceConfigCustomizer getIdentityImpersonationServiceCustomizer() {
      return this.identityImpersonationServiceCustomizer;
   }

   public ServiceConfigCustomizer getIdentityAssertionCallbackServiceCustomizer() {
      return this.identityAssertionCallbackServiceCustomizer;
   }

   public ServiceConfigCustomizer getIdentityAssertionTokenServiceCustomizer() {
      return this.identityAssertionTokenServiceCustomizer;
   }

   public ServiceConfigCustomizer getIdentityAssertionServiceCustomizer() {
      return this.identityAssertionServiceCustomizer;
   }

   public ServiceConfigCustomizer getChallengeIdentityAssertionTokenServiceCustomizer() {
      return this.challengeIdentityAssertionTokenServiceCustomizer;
   }

   public ServiceConfigCustomizer getChallengeIdentityAssertionServiceCustomizer() {
      return this.challengeIdentityAssertionServiceCustomizer;
   }

   public ServiceConfigCustomizer getJAASAuthenticationServiceCustomizer() {
      return this.jaasAuthenticationServiceCustomizer;
   }

   public ServiceConfigCustomizer getNegotiateIdentityAsserterServiceCustomizer() {
      return this.negotiateIdentityAsserterServiceCustomizer;
   }

   public AuthenticationServicesConfigHelper.IdentityCacheServiceConfigCustomizer getIdentityCacheServiceCustomizer() {
      return this.identityCacheServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      this.getSecurityProviderConfigHelper().addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean().getAuthenticationProviders());

      try {
         Method m = this.getRealmMBean().getClass().getMethod("getPasswordValidators");
         if (m != null) {
            PasswordValidatorMBean[] mbeans = null;
            mbeans = (PasswordValidatorMBean[])((PasswordValidatorMBean[])m.invoke(this.getRealmMBean()));
            this.getSecurityProviderConfigHelper().addToConfig(serviceEngineConfig, lifecycleImplLoaderName, mbeans);
         }
      } catch (Throwable var5) {
      }

      if (!this.principalValidationServiceCustomizer.isServiceRemoved()) {
         PrincipalValidationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.principalValidationServiceCustomizer.getServiceName());
      }

      if (!this.jaasAuthenticationConfigurationServiceCustomizer.isServiceRemoved()) {
         JAASAuthenticationConfigurationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.jaasAuthenticationConfigurationServiceCustomizer.getServiceName());
      }

      if (!this.jaasIdentityAssertionConfigurationServiceCustomizer.isServiceRemoved()) {
         JAASIdentityAssertionConfigurationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.jaasIdentityAssertionConfigurationServiceCustomizer.getServiceName());
      }

      if (!this.jaasLoginServiceCustomizer.isServiceRemoved()) {
         JAASLoginServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.jaasLoginServiceCustomizer.getServiceName());
      }

      if (!this.identityImpersonationServiceCustomizer.isServiceRemoved()) {
         IdentityImpersonationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.identityImpersonationServiceCustomizer.getServiceName());
      }

      if (!this.identityAssertionCallbackServiceCustomizer.isServiceRemoved()) {
         IdentityAssertionCallbackServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.identityAssertionCallbackServiceCustomizer.getServiceName());
      }

      if (!this.identityAssertionTokenServiceCustomizer.isServiceRemoved()) {
         IdentityAssertionTokenServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.identityAssertionTokenServiceCustomizer.getServiceName());
      }

      if (!this.identityAssertionServiceCustomizer.isServiceRemoved()) {
         IdentityAssertionServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.identityAssertionServiceCustomizer.getServiceName());
      }

      if (!this.challengeIdentityAssertionTokenServiceCustomizer.isServiceRemoved()) {
         ChallengeIdentityAssertionTokenServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.challengeIdentityAssertionTokenServiceCustomizer.getServiceName());
      }

      if (!this.challengeIdentityAssertionServiceCustomizer.isServiceRemoved()) {
         ChallengeIdentityAssertionServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.challengeIdentityAssertionServiceCustomizer.getServiceName());
      }

      if (!this.jaasAuthenticationServiceCustomizer.isServiceRemoved()) {
         JAASAuthenticationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.jaasAuthenticationServiceCustomizer.getServiceName());
      }

      if (!this.negotiateIdentityAsserterServiceCustomizer.isServiceRemoved()) {
         NegotiateIdentityAsserterServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.negotiateIdentityAsserterServiceCustomizer.getServiceName());
      }

      if (!this.identityCacheServiceCustomizer.isServiceRemoved()) {
         IdentityCacheServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.identityCacheServiceCustomizer.getServiceName(), this.identityCacheServiceCustomizer.isIdentityCacheEnabled(), this.identityCacheServiceCustomizer.getMaxIdentitiesInCache(), this.identityCacheServiceCustomizer.getIdentityCacheTTL(), this.identityCacheServiceCustomizer.getIdentityAssertionDoNotCacheContextElements());
      }

   }

   private static class IdentityCacheServiceConfigCustomizerImpl extends ServiceConfigCustomizerImpl implements AuthenticationServicesConfigHelper.IdentityCacheServiceConfigCustomizer {
      private boolean enabled;
      private int max;
      private long ttl;
      private String[] eleNames;

      private IdentityCacheServiceConfigCustomizerImpl(String serviceName) {
         super(serviceName);
         this.enabled = false;
         this.max = 1000;
         this.ttl = 300000L;
      }

      public void setIdentityCacheEnabled(boolean enabled) {
         this.enabled = enabled;
      }

      private boolean isIdentityCacheEnabled() {
         return this.enabled;
      }

      public void setMaxIdentitiesInCache(int max) {
         this.max = max;
      }

      private int getMaxIdentitiesInCache() {
         return this.max;
      }

      public void setIdentityCacheTTL(long ttl) {
         this.ttl = ttl;
      }

      private long getIdentityCacheTTL() {
         return this.ttl;
      }

      public void setIdentityAssertionDoNotCacheContextElements(String[] eleNames) {
         if (eleNames != null && eleNames.length > 0) {
            this.eleNames = eleNames;
         } else {
            this.eleNames = new String[0];
         }

      }

      private String[] getIdentityAssertionDoNotCacheContextElements() {
         return this.eleNames;
      }

      // $FF: synthetic method
      IdentityCacheServiceConfigCustomizerImpl(String x0, Object x1) {
         this(x0);
      }
   }
}
