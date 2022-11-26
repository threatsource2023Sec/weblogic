package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.core.ConfigurableObjectInputStream;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.RMIClassLoader;

public class CodebaseAwareObjectInputStream extends ConfigurableObjectInputStream {
   private final String codebaseUrl;

   public CodebaseAwareObjectInputStream(InputStream in, String codebaseUrl) throws IOException {
      this(in, (ClassLoader)null, codebaseUrl);
   }

   public CodebaseAwareObjectInputStream(InputStream in, @Nullable ClassLoader classLoader, String codebaseUrl) throws IOException {
      super(in, classLoader);
      this.codebaseUrl = codebaseUrl;
   }

   public CodebaseAwareObjectInputStream(InputStream in, @Nullable ClassLoader classLoader, boolean acceptProxyClasses) throws IOException {
      super(in, classLoader, acceptProxyClasses);
      this.codebaseUrl = null;
   }

   protected Class resolveFallbackIfPossible(String className, ClassNotFoundException ex) throws IOException, ClassNotFoundException {
      if (this.codebaseUrl == null) {
         throw ex;
      } else {
         return RMIClassLoader.loadClass(this.codebaseUrl, className);
      }
   }

   protected ClassLoader getFallbackClassLoader() throws IOException {
      return RMIClassLoader.getClassLoader(this.codebaseUrl);
   }
}
