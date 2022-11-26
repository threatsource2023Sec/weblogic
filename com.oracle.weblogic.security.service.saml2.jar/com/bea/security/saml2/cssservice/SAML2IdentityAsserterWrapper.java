package com.bea.security.saml2.cssservice;

import com.bea.common.security.utils.ThreadClassLoaderContextInvocationHandler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;

public class SAML2IdentityAsserterWrapper extends SAML2ProviderWrapper {
   private static final String SAML2_IA_NAME = "com.bea.security.saml2.providers.SAML2IdentityAsserterProviderImpl";

   public String getProviderClassName() {
      return "com.bea.security.saml2.providers.SAML2IdentityAsserterProviderImpl";
   }

   protected InvocationHandler getInvocationHandler(final ClassLoader cl, final Object prov) {
      return new InvocationHandler() {
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (AccessController.class.isAssignableFrom(method.getDeclaringClass())) {
               throw new IllegalArgumentException("Unable to invoke method");
            } else {
               Thread t = Thread.currentThread();
               ClassLoader tcl = t.getContextClassLoader();

               Object result;
               try {
                  t.setContextClassLoader(cl);
                  if ("getIdentityAsserter".equals(method.getName())) {
                     result = method.invoke(prov, args);
                     Object var7;
                     if (result == null) {
                        var7 = null;
                        return var7;
                     }

                     var7 = Proxy.newProxyInstance(cl, result.getClass().getInterfaces(), new ThreadClassLoaderContextInvocationHandler(cl, result));
                     return var7;
                  }

                  result = method.invoke(prov, args);
               } catch (InvocationTargetException var11) {
                  throw var11.getCause();
               } finally {
                  t.setContextClassLoader(tcl);
               }

               return result;
            }
         }
      };
   }
}
