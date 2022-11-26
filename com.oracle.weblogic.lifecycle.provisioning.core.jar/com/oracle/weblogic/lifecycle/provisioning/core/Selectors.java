package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.api.Selector;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ServiceLocator;

final class Selectors {
   private Selectors() {
   }

   static final boolean isSelected(ServiceLocator serviceLocator, ProvisioningResource provisioningResource, ResourceHandlingMethodDescriptor resourceHandlingMethodDescriptor) {
      String className = Selectors.class.getName();
      String methodName = "isSelected";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "isSelected", new Object[]{serviceLocator, provisioningResource, resourceHandlingMethodDescriptor});
      }

      Method method;
      HandlesResources handlesResources;
      if (resourceHandlingMethodDescriptor == null) {
         handlesResources = null;
         method = null;
      } else {
         handlesResources = resourceHandlingMethodDescriptor.getHandlesResourcesQualifier();
         method = resourceHandlingMethodDescriptor.getMethod();
      }

      boolean returnValue;
      if (handlesResources != null && method != null) {
         Class selectorClass = handlesResources.selectorClass();
         if (selectorClass == null) {
            returnValue = false;
         } else {
            Selector selector = null;
            if (serviceLocator == null) {
               try {
                  selector = (Selector)selectorClass.getDeclaredConstructor().newInstance();
               } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException var12) {
                  throw new IllegalStateException(var12);
               }
            } else {
               selector = (Selector)serviceLocator.getService(selectorClass, new Annotation[0]);
               if (selector == null) {
                  selector = (Selector)serviceLocator.createAndInitialize(selectorClass);
               }
            }

            assert selector != null;

            returnValue = selector.select(method, provisioningResource);
         }
      } else {
         returnValue = false;
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "isSelected", returnValue);
      }

      return returnValue;
   }
}
