package com.bea.xbean.schema;

import com.bea.xml.ResourceLoader;
import java.io.InputStream;

public class ClassLoaderResourceLoader implements ResourceLoader {
   ClassLoader _classLoader;

   public ClassLoaderResourceLoader(ClassLoader classLoader) {
      this._classLoader = classLoader;
   }

   public InputStream getResourceAsStream(String resourceName) {
      return this._classLoader.getResourceAsStream(resourceName);
   }

   public void close() {
   }
}
