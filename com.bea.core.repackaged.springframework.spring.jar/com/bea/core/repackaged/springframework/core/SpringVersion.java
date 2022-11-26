package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;

public final class SpringVersion {
   private SpringVersion() {
   }

   @Nullable
   public static String getVersion() {
      Package pkg = SpringVersion.class.getPackage();
      return pkg != null ? pkg.getImplementationVersion() : null;
   }
}
