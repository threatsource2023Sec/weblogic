package org.cryptacular.io;

import java.io.InputStream;

public class ClassPathResource implements Resource {
   private final String classPath;
   private final ClassLoader classLoader;

   public ClassPathResource(String path) {
      this(path, Thread.currentThread().getContextClassLoader());
   }

   public ClassPathResource(String path, ClassLoader loader) {
      if (path.startsWith("/")) {
         this.classPath = path.substring(1);
      } else {
         this.classPath = path;
      }

      this.classLoader = loader;
   }

   public InputStream getInputStream() {
      return this.classLoader.getResourceAsStream(this.classPath);
   }
}
