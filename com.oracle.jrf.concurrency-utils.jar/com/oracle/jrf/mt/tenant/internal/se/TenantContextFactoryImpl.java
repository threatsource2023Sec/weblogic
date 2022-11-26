package com.oracle.jrf.mt.tenant.internal.se;

import com.oracle.jrf.mt.tenant.internal.TenantContextImpl;
import com.oracle.jrf.mt.tenant.runtime.TenantContext;
import com.oracle.jrf.mt.tenant.runtime.TenantContextFactory;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
public class TenantContextFactoryImpl extends TenantContextFactory {
   @Inject
   private ServiceLocator locator;

   public TenantContext getTenantContext() {
      TenantContext tenantContext = this.getTenantContextFromThreadLocal();
      return tenantContext;
   }

   public TenantContextFactory getTenantContextFactory() {
      return this;
   }

   private TenantContext getTenantContextFromThreadLocal() {
      TenantContext ctx = SEThreadLocal.get();
      if (ctx == null) {
         this.initialize();
         ctx = SEThreadLocal.get();
      }

      return ctx;
   }

   void initialize() {
      SEThreadLocal.set(TenantContextImpl.getGlobalContextUUID());
   }
}
