package com.bea.common.security.internal.legacy.service;

public interface RoleDeployerProviderConfig {
   String getRoleProviderName();

   boolean isSupportParallelDeploy();

   long getDeployTimeout();
}
