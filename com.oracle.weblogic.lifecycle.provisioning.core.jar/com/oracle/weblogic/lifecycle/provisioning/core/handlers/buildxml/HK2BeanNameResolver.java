package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import java.util.Objects;
import javax.el.BeanNameResolver;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;

public class HK2BeanNameResolver extends BeanNameResolver {
   private final ServiceLocator serviceLocator;

   public HK2BeanNameResolver(ServiceLocator serviceLocator) {
      Objects.requireNonNull(serviceLocator);
      this.serviceLocator = serviceLocator;
   }

   public boolean isNameResolved(String name) {
      if (name != null && this.serviceLocator != null) {
         return this.serviceLocator.getBestDescriptor(BuilderHelper.createNameFilter(name)) != null;
      } else {
         return super.isNameResolved(name);
      }
   }

   public Object getBean(String name) {
      Object returnValue = null;
      if (name != null && this.serviceLocator != null) {
         ActiveDescriptor descriptor = this.serviceLocator.getBestDescriptor(BuilderHelper.createNameFilter(name));
         if (descriptor != null) {
            returnValue = org.glassfish.hk2.utilities.ServiceLocatorUtilities.getService(this.serviceLocator, descriptor);
         }
      }

      if (returnValue == null) {
         returnValue = super.getBean(name);
      }

      return returnValue;
   }
}
