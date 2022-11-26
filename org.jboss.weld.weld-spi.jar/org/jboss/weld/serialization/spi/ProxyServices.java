package org.jboss.weld.serialization.spi;

import java.security.ProtectionDomain;
import org.jboss.weld.bootstrap.api.Service;

public interface ProxyServices extends Service {
   /** @deprecated */
   @Deprecated
   ClassLoader getClassLoader(Class var1);

   /** @deprecated */
   @Deprecated
   Class loadBeanClass(String var1);

   default Class defineClass(Class originalClass, String className, byte[] classBytes, int off, int len) throws ClassFormatError {
      return this.defineClass(originalClass, className, classBytes, off, len, (ProtectionDomain)null);
   }

   default Class defineClass(Class originalClass, String className, byte[] classBytes, int off, int len, ProtectionDomain protectionDomain) throws ClassFormatError {
      throw new UnsupportedOperationException("ProxyServices#defineClass(Class<?>, String, byte[], int, int, ProtectionDomain) is not implemented!");
   }

   default Class loadClass(Class originalClass, String classBinaryName) throws ClassNotFoundException {
      throw new UnsupportedOperationException("ProxyServices#loadClass(Class<?>, String) is not implemented!");
   }

   default boolean supportsClassDefining() {
      return false;
   }
}
