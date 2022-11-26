package com.bea.common.security.legacy;

import weblogic.security.spi.SecurityProvider;

public interface SecurityProviderWrapper {
   SecurityProvider getProvider(ClassLoader var1) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
