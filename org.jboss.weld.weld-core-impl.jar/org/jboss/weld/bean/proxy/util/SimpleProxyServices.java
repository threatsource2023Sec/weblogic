package org.jboss.weld.bean.proxy.util;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.serialization.spi.ProxyServices;

public class SimpleProxyServices implements ProxyServices {
   public ClassLoader getClassLoader(Class proxiedBeanType) {
      return proxiedBeanType.getClassLoader();
   }

   public void cleanup() {
   }

   /** @deprecated */
   @Deprecated
   public Class loadBeanClass(final String className) {
      try {
         return (Class)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return Class.forName(className, true, SimpleProxyServices.this.getClassLoader(this.getClass()));
            }
         });
      } catch (PrivilegedActionException var3) {
         throw BeanLogger.LOG.cannotLoadClass(className, var3.getException());
      }
   }
}
