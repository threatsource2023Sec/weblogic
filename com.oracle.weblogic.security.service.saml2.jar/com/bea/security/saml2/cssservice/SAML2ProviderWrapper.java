package com.bea.security.saml2.cssservice;

import com.bea.common.security.legacy.SecurityProviderWrapper;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import weblogic.security.spi.SecurityProvider;

public abstract class SAML2ProviderWrapper implements SecurityProviderWrapper {
   public SecurityProvider getProvider(ClassLoader cl) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      Object prov = Class.forName(this.getProviderClassName(), true, cl).newInstance();
      return (SecurityProvider)Proxy.newProxyInstance(cl, prov.getClass().getInterfaces(), this.getInvocationHandler(cl, prov));
   }

   protected abstract String getProviderClassName();

   protected abstract InvocationHandler getInvocationHandler(ClassLoader var1, Object var2);
}
