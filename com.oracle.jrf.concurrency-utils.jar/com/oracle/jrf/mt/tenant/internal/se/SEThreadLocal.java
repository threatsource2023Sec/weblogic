package com.oracle.jrf.mt.tenant.internal.se;

import com.oracle.jrf.mt.tenant.runtime.TenantContext;

public class SEThreadLocal {
   public static final InheritableThreadLocal userThreadLocal = new InheritableThreadLocal();

   public static void set(TenantContext context) {
      userThreadLocal.set(context);
   }

   public static void unset() {
      userThreadLocal.remove();
   }

   public static TenantContext get() {
      return (TenantContext)userThreadLocal.get();
   }
}
