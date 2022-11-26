package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.SecurityProviderConfig;
import com.bea.common.security.internal.legacy.service.SecurityProviderImpl;
import com.bea.common.security.legacy.ConfigHelperFactory;
import com.bea.common.security.legacy.InternalServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderClassLoaderService;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.authentication.UserPasswordEditorMBean;

public class SecurityProviderConfigHelperImpl implements SecurityProviderConfigHelper {
   private LegacyConfigInfoSpi legacyConfigInfo;
   private ConfigHelperFactory helperFactory;

   public static String _getServiceName(ProviderMBean providerMBean) {
      return SecurityProviderConfigHelperImpl.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   public SecurityProviderConfigHelperImpl(LegacyConfigInfoSpi legacyConfigInfo, ConfigHelperFactory helperFactory) {
      this.legacyConfigInfo = legacyConfigInfo;
      this.helperFactory = helperFactory;
   }

   public String getServiceName(ProviderMBean providerMBean) {
      return _getServiceName(providerMBean);
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, ProviderMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         this.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans[i]);
      }

   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, ProviderMBean providerMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(this.getServiceName(providerMBean), SecurityProviderImpl.class.getName(), false);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.addDependency(SecurityProviderClassLoaderService.SERVICE_NAME);
      RealmMBean realm = providerMBean.getRealm();
      String auditServiceName = null;
      if (!(providerMBean instanceof AuditorMBean)) {
         auditServiceName = this.helperFactory.getAuditServicesConfigHelper(realm).getAuditServiceName();
         serviceConfig.addDependency(auditServiceName);
      }

      if (providerMBean instanceof UserPasswordEditorMBean) {
         serviceConfig.addDependency(this.helperFactory.getAuthenticationServicesConfigHelper(realm).getPasswordValidationServiceName());
      }

      String identityServiceName = this.helperFactory.getIdentityServicesConfigHelper(realm).getIdentityServiceName();
      serviceConfig.addDependency(identityServiceName);
      InternalServicesConfigHelper internalServicesConfigHelper = this.helperFactory.getInternalServicesConfigHelper(realm);
      ServiceConfigCustomizerImpl serviceCustomizer = (ServiceConfigCustomizerImpl)internalServicesConfigHelper.getLDAPSSLSocketFactoryLookupServiceCustomizer();
      String bootStrapServiceName;
      if (!serviceCustomizer.isServiceRemoved()) {
         bootStrapServiceName = internalServicesConfigHelper.getLDAPSSLSocketFactoryLookupServiceName();
         serviceConfig.addDependency(bootStrapServiceName);
      }

      serviceCustomizer = (ServiceConfigCustomizerImpl)internalServicesConfigHelper.getJAXPFactoryServiceCustomizer();
      if (!serviceCustomizer.isServiceRemoved()) {
         bootStrapServiceName = internalServicesConfigHelper.getJAXPFactoryServiceName();
         serviceConfig.addDependency(bootStrapServiceName);
      }

      serviceCustomizer = (ServiceConfigCustomizerImpl)internalServicesConfigHelper.getNamedSQLConnectionLookupServiceCustomizer();
      if (!serviceCustomizer.isServiceRemoved()) {
         bootStrapServiceName = internalServicesConfigHelper.getNamedSQLConnectionLookupServiceName();
         serviceConfig.addDependency(bootStrapServiceName);
      }

      serviceCustomizer = (ServiceConfigCustomizerImpl)internalServicesConfigHelper.getSAMLKeyServiceCustomizer();
      if (!serviceCustomizer.isServiceRemoved()) {
         bootStrapServiceName = internalServicesConfigHelper.getSAMLKeyServiceName();
         serviceConfig.addDependency(bootStrapServiceName);
      }

      serviceCustomizer = (ServiceConfigCustomizerImpl)internalServicesConfigHelper.getStoreServiceCustomizer();
      if (!serviceCustomizer.isServiceRemoved()) {
         bootStrapServiceName = internalServicesConfigHelper.getStoreServiceName();
         serviceConfig.addDependency(bootStrapServiceName);
      }

      bootStrapServiceName = this.helperFactory.getInternalServicesConfigHelper(realm).getBootStrapServiceName();
      serviceConfig.addDependency(bootStrapServiceName);
      serviceConfig.setConfig(new ConfigImpl(providerMBean, auditServiceName, identityServiceName, this.legacyConfigInfo));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements SecurityProviderConfig {
      private ProviderMBean providerMBean;
      private String auditServiceName;
      private String identityServiceName;
      private LegacyConfigInfoSpi legacyConfigInfo;

      private ConfigImpl(ProviderMBean providerMBean, String auditServiceName, String identityServiceName, LegacyConfigInfoSpi legacyConfigInfo) {
         this.providerMBean = null;
         this.auditServiceName = null;
         this.identityServiceName = null;
         this.legacyConfigInfo = null;
         this.providerMBean = providerMBean;
         this.auditServiceName = auditServiceName;
         this.identityServiceName = identityServiceName;
         this.legacyConfigInfo = legacyConfigInfo;
      }

      public ProviderMBean getProviderMBean() {
         return this.providerMBean;
      }

      public String getProviderClassName() {
         return this.providerMBean.getProviderClassName();
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getIdentityServiceName() {
         return this.identityServiceName;
      }

      public LegacyConfigInfoSpi getLegacyConfigInfo() {
         return this.legacyConfigInfo;
      }

      // $FF: synthetic method
      ConfigImpl(ProviderMBean x0, String x1, String x2, LegacyConfigInfoSpi x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
