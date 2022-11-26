package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.security.AccessControlContext;
import java.security.AccessController;

public class SimpleSecurityContextProvider implements SecurityContextProvider {
   @Nullable
   private final AccessControlContext acc;

   public SimpleSecurityContextProvider() {
      this((AccessControlContext)null);
   }

   public SimpleSecurityContextProvider(@Nullable AccessControlContext acc) {
      this.acc = acc;
   }

   public AccessControlContext getAccessControlContext() {
      return this.acc != null ? this.acc : AccessController.getContext();
   }
}
