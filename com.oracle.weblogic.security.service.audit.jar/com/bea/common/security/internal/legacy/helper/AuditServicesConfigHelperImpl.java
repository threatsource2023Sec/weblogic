package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.AuditServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import weblogic.management.security.RealmMBean;

public class AuditServicesConfigHelperImpl extends BaseServicesConfigImpl implements AuditServicesConfigHelper {
   ServiceConfigCustomizerImpl auditServiceCustomizer = new ServiceConfigCustomizerImpl(this.getAuditServiceName());

   public String getAuditServiceName() {
      return AuditServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public AuditServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getAuditServiceCustomizer() {
      return this.auditServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      this.getSecurityProviderConfigHelper().addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean().getAuditors());
      if (!this.auditServiceCustomizer.isServiceRemoved()) {
         AuditServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.auditServiceCustomizer.getServiceName());
      }

   }
}
