package com.bea.common.security.saml2.helper;

import com.bea.common.security.utils.ProviderMBeanInvocationHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Hashtable;
import java.util.Map;
import weblogic.management.security.ProviderMBean;
import weblogic.security.providers.utils.SecurityHelper;

public class SAML2SecurityHelperFactory {
   private static Map mapSecurityHelpers = new Hashtable();

   public static SAML2SecurityHelperInt getInstance(ProviderMBean providerMBean) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
      if (providerMBean == null) {
         return null;
      } else {
         ClassLoader cl = providerMBean.getClass().getClassLoader();
         if (Proxy.isProxyClass(providerMBean.getClass())) {
            Object handler = Proxy.getInvocationHandler(providerMBean);
            if (handler instanceof ProviderMBeanInvocationHandler) {
               ProviderMBeanInvocationHandler ph = (ProviderMBeanInvocationHandler)handler;
               cl = ph.getClassLoader();
            }
         }

         String key = SecurityHelper.getKey(providerMBean);
         SAML2SecurityHelperInt securityHelper = (SAML2SecurityHelperInt)mapSecurityHelpers.get(key);
         if (securityHelper == null) {
            Class shClass = Class.forName("com.bea.security.saml2.providers.SAML2SecurityHelper", true, cl);
            Constructor ctor = shClass.getConstructor(ProviderMBean.class);
            securityHelper = (SAML2SecurityHelperInt)ctor.newInstance(providerMBean);
            mapSecurityHelpers.put(key, securityHelper);
         }

         return securityHelper;
      }
   }

   public static void clearInstance(ProviderMBean mBean) {
      if (SecurityHelper.isRealmShutdown(mBean.getRealm().getName())) {
         mapSecurityHelpers.remove(SecurityHelper.getKey(mBean));
      }

   }
}
