package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;

public interface AuthorizationServicesConfigHelper {
   String getRoleMappingServiceName();

   String getAccessDecisionServiceName();

   String getIsProtectedResourceServiceName();

   String getAdjudicationServiceName();

   String getAuthorizationServiceName();

   String getBulkRoleMappingServiceName();

   String getBulkAccessDecisionServiceName();

   String getBulkAdjudicationServiceName();

   String getBulkAuthorizationServiceName();

   String getPolicyConsumerServiceName();

   String getRoleConsumerServiceName();

   String getPolicyDeploymentServiceName();

   String getRoleDeploymentServiceName();

   ServiceConfigCustomizer getRoleMappingServiceCustomizer();

   ServiceConfigCustomizer getAccessDecisionServiceCustomizer();

   ServiceConfigCustomizer getIsProtectedResourceServiceCustomizer();

   ServiceConfigCustomizer getAdjudicationServiceCustomizer();

   ServiceConfigCustomizer getAuthorizationServiceCustomizer();

   ServiceConfigCustomizer getBulkRoleMappingServiceCustomizer();

   ServiceConfigCustomizer getBulkAccessDecisionServiceCustomizer();

   ServiceConfigCustomizer getBulkAdjudicationServiceCustomizer();

   ServiceConfigCustomizer getBulkAuthorizationServiceCustomizer();

   ServiceConfigCustomizer getPolicyConsumerServiceCustomizer();

   ServiceConfigCustomizer getRoleConsumerServiceCustomizer();

   ServiceConfigCustomizer getPolicyDeploymentServiceCustomizer();

   ServiceConfigCustomizer getRoleDeploymentServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);
}
