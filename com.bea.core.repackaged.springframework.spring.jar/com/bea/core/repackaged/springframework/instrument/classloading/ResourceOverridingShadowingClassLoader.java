package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ResourceOverridingShadowingClassLoader extends ShadowingClassLoader {
   private static final Enumeration EMPTY_URL_ENUMERATION = new Enumeration() {
      public boolean hasMoreElements() {
         return false;
      }

      public URL nextElement() {
         throw new UnsupportedOperationException("Should not be called. I am empty.");
      }
   };
   private Map overrides = new HashMap();

   public ResourceOverridingShadowingClassLoader(ClassLoader enclosingClassLoader) {
      super(enclosingClassLoader);
   }

   public void override(String oldPath, String newPath) {
      this.overrides.put(oldPath, newPath);
   }

   public void suppress(String oldPath) {
      this.overrides.put(oldPath, (Object)null);
   }

   public void copyOverrides(ResourceOverridingShadowingClassLoader other) {
      Assert.notNull(other, (String)"Other ClassLoader must not be null");
      this.overrides.putAll(other.overrides);
   }

   public URL getResource(String requestedPath) {
      if (this.overrides.containsKey(requestedPath)) {
         String overriddenPath = (String)this.overrides.get(requestedPath);
         return overriddenPath != null ? super.getResource(overriddenPath) : null;
      } else {
         return super.getResource(requestedPath);
      }
   }

   @Nullable
   public InputStream getResourceAsStream(String requestedPath) {
      if (this.overrides.containsKey(requestedPath)) {
         String overriddenPath = (String)this.overrides.get(requestedPath);
         return overriddenPath != null ? super.getResourceAsStream(overriddenPath) : null;
      } else {
         return super.getResourceAsStream(requestedPath);
      }
   }

   public Enumeration getResources(String requestedPath) throws IOException {
      if (this.overrides.containsKey(requestedPath)) {
         String overriddenLocation = (String)this.overrides.get(requestedPath);
         return overriddenLocation != null ? super.getResources(overriddenLocation) : EMPTY_URL_ENUMERATION;
      } else {
         return super.getResources(requestedPath);
      }
   }
}
