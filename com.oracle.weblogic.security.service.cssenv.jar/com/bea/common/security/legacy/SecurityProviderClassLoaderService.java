package com.bea.common.security.legacy;

import weblogic.management.security.ProviderMBean;

public interface SecurityProviderClassLoaderService {
   String SERVICE_NAME = SecurityProviderClassLoaderService.class.getName();

   ClassLoader getClassLoader(ProviderMBean var1);
}
