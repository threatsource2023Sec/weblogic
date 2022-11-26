package com.bea.common.security.internal.legacy.service;

public interface PolicyDeployerProviderConfig {
   String getAuthorizationProviderName();

   boolean isSupportParallelDeploy();

   long getDeployTimeout();
}
