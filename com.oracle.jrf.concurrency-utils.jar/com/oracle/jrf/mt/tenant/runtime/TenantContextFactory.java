package com.oracle.jrf.mt.tenant.runtime;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public abstract class TenantContextFactory {
   public abstract TenantContext getTenantContext();

   public static TenantContextFactory getInstance() {
      ServiceLocator locator = GlobalServiceLocator.getServiceLocator();
      return (TenantContextFactory)locator.getService(TenantContextFactory.class, new Annotation[0]);
   }
}
