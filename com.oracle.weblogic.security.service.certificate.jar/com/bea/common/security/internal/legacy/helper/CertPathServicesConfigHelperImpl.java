package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.CertPathServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import weblogic.management.security.RealmMBean;

public class CertPathServicesConfigHelperImpl extends BaseServicesConfigImpl implements CertPathServicesConfigHelper {
   private ServiceConfigCustomizerImpl certPathBuilderServiceCustomizer = new ServiceConfigCustomizerImpl(this.getCertPathBuilderServiceName());
   private ServiceConfigCustomizerImpl certPathValidatorServiceCustomizer = new ServiceConfigCustomizerImpl(this.getCertPathValidatorServiceName());

   public String getCertPathBuilderServiceName() {
      return CertPathBuilderServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getCertPathValidatorServiceName() {
      return CertPathValidatorServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public CertPathServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getCertPathBuilderServiceCustomizer() {
      return this.certPathBuilderServiceCustomizer;
   }

   public ServiceConfigCustomizer getCertPathValidatorServiceCustomizer() {
      return this.certPathValidatorServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      this.getSecurityProviderConfigHelper().addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean().getCertPathProviders());
      if (!this.certPathBuilderServiceCustomizer.isServiceRemoved()) {
         CertPathBuilderServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.certPathBuilderServiceCustomizer.getServiceName(), this.certPathValidatorServiceCustomizer.isServiceRemoved());
      }

      if (!this.certPathValidatorServiceCustomizer.isServiceRemoved()) {
         CertPathValidatorServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.certPathValidatorServiceCustomizer.getServiceName());
      }

   }
}
