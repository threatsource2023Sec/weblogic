package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.AuthorizationServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.AdjudicatorMBean;

public class AuthorizationServicesConfigHelperImpl extends BaseServicesConfigImpl implements AuthorizationServicesConfigHelper {
   private ServiceConfigCustomizerImpl roleMappingServiceCustomizer = new ServiceConfigCustomizerImpl(this.getRoleMappingServiceName());
   private ServiceConfigCustomizerImpl accessDecisionServiceCustomizer = new ServiceConfigCustomizerImpl(this.getAccessDecisionServiceName());
   private ServiceConfigCustomizerImpl isProtectedResourceServiceCustomizer = new ServiceConfigCustomizerImpl(this.getIsProtectedResourceServiceName());
   private ServiceConfigCustomizerImpl adjudicationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getAdjudicationServiceName());
   private ServiceConfigCustomizerImpl authorizationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getAuthorizationServiceName());
   private ServiceConfigCustomizerImpl bulkRoleMappingServiceCustomizer = new ServiceConfigCustomizerImpl(this.getBulkRoleMappingServiceName());
   private ServiceConfigCustomizerImpl bulkAccessDecisionServiceCustomizer = new ServiceConfigCustomizerImpl(this.getBulkAccessDecisionServiceName());
   private ServiceConfigCustomizerImpl bulkAdjudicationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getBulkAdjudicationServiceName());
   private ServiceConfigCustomizerImpl bulkAuthorizationServiceCustomizer = new ServiceConfigCustomizerImpl(this.getBulkAuthorizationServiceName());
   private ServiceConfigCustomizerImpl policyConsumerServiceCustomizer = new ServiceConfigCustomizerImpl(this.getPolicyConsumerServiceName());
   private ServiceConfigCustomizerImpl roleConsumerServiceCustomizer = new ServiceConfigCustomizerImpl(this.getRoleConsumerServiceName());
   private ServiceConfigCustomizerImpl policyDeploymentServiceCustomizer = new ServiceConfigCustomizerImpl(this.getPolicyDeploymentServiceName());
   private ServiceConfigCustomizerImpl roleDeploymentServiceCustomizer = new ServiceConfigCustomizerImpl(this.getRoleDeploymentServiceName());

   public String getRoleMappingServiceName() {
      return RoleMappingServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getAccessDecisionServiceName() {
      return AccessDecisionServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getIsProtectedResourceServiceName() {
      return IsProtectedResourceServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getAdjudicationServiceName() {
      return AdjudicationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getAuthorizationServiceName() {
      return AuthorizationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getBulkRoleMappingServiceName() {
      return BulkRoleMappingServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getBulkAccessDecisionServiceName() {
      return BulkAccessDecisionServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getBulkAdjudicationServiceName() {
      return BulkAdjudicationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getBulkAuthorizationServiceName() {
      return BulkAuthorizationServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getPolicyConsumerServiceName() {
      return PolicyConsumerServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getRoleConsumerServiceName() {
      return RoleConsumerServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getPolicyDeploymentServiceName() {
      return PolicyDeploymentServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public String getRoleDeploymentServiceName() {
      return RoleDeploymentServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public AuthorizationServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getRoleMappingServiceCustomizer() {
      return this.roleMappingServiceCustomizer;
   }

   public ServiceConfigCustomizer getAccessDecisionServiceCustomizer() {
      return this.accessDecisionServiceCustomizer;
   }

   public ServiceConfigCustomizer getIsProtectedResourceServiceCustomizer() {
      return this.isProtectedResourceServiceCustomizer;
   }

   public ServiceConfigCustomizer getAdjudicationServiceCustomizer() {
      return this.adjudicationServiceCustomizer;
   }

   public ServiceConfigCustomizer getAuthorizationServiceCustomizer() {
      return this.authorizationServiceCustomizer;
   }

   public ServiceConfigCustomizer getBulkRoleMappingServiceCustomizer() {
      return this.bulkRoleMappingServiceCustomizer;
   }

   public ServiceConfigCustomizer getBulkAccessDecisionServiceCustomizer() {
      return this.bulkAccessDecisionServiceCustomizer;
   }

   public ServiceConfigCustomizer getBulkAdjudicationServiceCustomizer() {
      return this.bulkAdjudicationServiceCustomizer;
   }

   public ServiceConfigCustomizer getBulkAuthorizationServiceCustomizer() {
      return this.bulkAuthorizationServiceCustomizer;
   }

   public ServiceConfigCustomizer getPolicyConsumerServiceCustomizer() {
      return this.policyConsumerServiceCustomizer;
   }

   public ServiceConfigCustomizer getRoleConsumerServiceCustomizer() {
      return this.roleConsumerServiceCustomizer;
   }

   public ServiceConfigCustomizer getPolicyDeploymentServiceCustomizer() {
      return this.policyDeploymentServiceCustomizer;
   }

   public ServiceConfigCustomizer getRoleDeploymentServiceCustomizer() {
      return this.roleDeploymentServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      RealmMBean realm = this.getRealmMBean();
      SecurityProviderConfigHelper providerHelper = this.getSecurityProviderConfigHelper();
      providerHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm.getRoleMappers());
      providerHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm.getAuthorizers());
      AdjudicatorMBean adjudicator = realm.getAdjudicator();
      if (adjudicator != null) {
         providerHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, adjudicator);
      }

      if (!this.roleMappingServiceCustomizer.isServiceRemoved()) {
         RoleMappingServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.roleMappingServiceCustomizer.getServiceName());
      }

      boolean addedADService = false;
      if (!this.accessDecisionServiceCustomizer.isServiceRemoved()) {
         AccessDecisionServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.accessDecisionServiceCustomizer.getServiceName());
         addedADService = true;
      }

      if (!this.isProtectedResourceServiceCustomizer.isServiceRemoved()) {
         IsProtectedResourceServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.isProtectedResourceServiceCustomizer.getServiceName(), addedADService);
      }

      if (!this.adjudicationServiceCustomizer.isServiceRemoved()) {
         AdjudicationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.adjudicationServiceCustomizer.getServiceName());
      }

      if (!this.authorizationServiceCustomizer.isServiceRemoved()) {
         AuthorizationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.authorizationServiceCustomizer.getServiceName());
      }

      if (!this.bulkRoleMappingServiceCustomizer.isServiceRemoved()) {
         BulkRoleMappingServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.bulkRoleMappingServiceCustomizer.getServiceName());
      }

      if (!this.bulkAccessDecisionServiceCustomizer.isServiceRemoved()) {
         BulkAccessDecisionServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.bulkAccessDecisionServiceCustomizer.getServiceName());
      }

      if (!this.bulkAdjudicationServiceCustomizer.isServiceRemoved()) {
         BulkAdjudicationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.bulkAdjudicationServiceCustomizer.getServiceName());
      }

      if (!this.bulkAuthorizationServiceCustomizer.isServiceRemoved()) {
         BulkAuthorizationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.bulkAuthorizationServiceCustomizer.getServiceName());
      }

      if (!this.roleConsumerServiceCustomizer.isServiceRemoved()) {
         RoleConsumerServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.roleConsumerServiceCustomizer.getServiceName());
      }

      if (!this.policyConsumerServiceCustomizer.isServiceRemoved()) {
         PolicyConsumerServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.policyConsumerServiceCustomizer.getServiceName());
      }

      if (!this.roleDeploymentServiceCustomizer.isServiceRemoved()) {
         RoleDeploymentServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.roleDeploymentServiceCustomizer.getServiceName());
      }

      if (!this.policyDeploymentServiceCustomizer.isServiceRemoved()) {
         PolicyDeploymentServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realm, this.policyDeploymentServiceCustomizer.getServiceName());
      }

   }
}
