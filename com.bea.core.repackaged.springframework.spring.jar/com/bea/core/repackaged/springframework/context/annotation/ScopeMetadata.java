package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.util.Assert;

public class ScopeMetadata {
   private String scopeName = "singleton";
   private ScopedProxyMode scopedProxyMode;

   public ScopeMetadata() {
      this.scopedProxyMode = ScopedProxyMode.NO;
   }

   public void setScopeName(String scopeName) {
      Assert.notNull(scopeName, (String)"'scopeName' must not be null");
      this.scopeName = scopeName;
   }

   public String getScopeName() {
      return this.scopeName;
   }

   public void setScopedProxyMode(ScopedProxyMode scopedProxyMode) {
      Assert.notNull(scopedProxyMode, (String)"'scopedProxyMode' must not be null");
      this.scopedProxyMode = scopedProxyMode;
   }

   public ScopedProxyMode getScopedProxyMode() {
      return this.scopedProxyMode;
   }
}
