package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public final class Components {
   public static final String COMPONENT_NAME_METADATA_KEY = "componentName";

   private Components() {
   }

   public static final Set getAffiliates(ServiceLocator serviceLocator, ActiveDescriptor activeDescriptor) {
      String className = Components.class.getName();
      String methodName = "getAffiliates";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getAffiliates", new Object[]{serviceLocator, activeDescriptor});
      }

      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(activeDescriptor);
      Set returnValue = new HashSet();
      if (!activeDescriptor.isReified()) {
         activeDescriptor = serviceLocator.reifyDescriptor(activeDescriptor);
      }

      Class implementationClass = activeDescriptor.getImplementationClass();
      if (implementationClass != null) {
         Component component = (Component)implementationClass.getAnnotation(Component.class);
         if (component != null) {
            String[] affiliatesArray = component.affiliates();
            if (affiliatesArray != null && affiliatesArray.length > 0) {
               String[] var9 = affiliatesArray;
               int var10 = affiliatesArray.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String affiliatesArrayMember = var9[var11];
                  if (affiliatesArrayMember != null) {
                     affiliatesArrayMember = affiliatesArrayMember.trim();
                     if (!affiliatesArrayMember.isEmpty()) {
                        returnValue.add(affiliatesArrayMember);
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getAffiliates", returnValue);
      }

      return returnValue;
   }

   public static final Set getAffiliatedProvisioningComponentIds(ServiceLocator serviceLocator, ActiveDescriptor activeDescriptor) {
      String className = Components.class.getName();
      String methodName = "getAffiliatedProvisioningComponentIds";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getAffiliatedProvisioningComponentIds", new Object[]{serviceLocator, activeDescriptor});
      }

      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(activeDescriptor);
      Set returnValue = new HashSet();
      if (!activeDescriptor.isReified()) {
         activeDescriptor = serviceLocator.reifyDescriptor(activeDescriptor);
      }

      Class implementationClass = activeDescriptor.getImplementationClass();
      if (implementationClass != null) {
         Component component = (Component)implementationClass.getAnnotation(Component.class);
         if (component != null) {
            String[] affiliatesArray = component.affiliates();
            if (affiliatesArray != null && affiliatesArray.length > 0) {
               String[] var9 = affiliatesArray;
               int var10 = affiliatesArray.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  String affiliatesArrayMember = var9[var11];
                  if (affiliatesArrayMember != null) {
                     affiliatesArrayMember = affiliatesArrayMember.trim();
                     if (!affiliatesArrayMember.isEmpty()) {
                        returnValue.add(new ProvisioningComponentIdentifier(affiliatesArrayMember));
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getAffiliatedProvisioningComponentIds", returnValue);
      }

      return returnValue;
   }

   public static final String getComponentName(ServiceLocator serviceLocator, Descriptor descriptor) {
      String className = Components.class.getName();
      String methodName = "getComponentName";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getComponentName", new Object[]{serviceLocator, descriptor});
      }

      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(descriptor);
      String componentName = ServiceLocatorUtilities.getOneMetadataField(descriptor, "componentName");
      if (componentName != null) {
         componentName = componentName.trim();
         if (componentName.isEmpty()) {
            componentName = null;
         }
      }

      if (componentName == null) {
         ActiveDescriptor activeDescriptor = serviceLocator.reifyDescriptor(descriptor);

         assert activeDescriptor.isReified();

         Class implementationClass = activeDescriptor.getImplementationClass();
         if (implementationClass != null) {
            Component component = (Component)implementationClass.getAnnotation(Component.class);
            if (component != null) {
               componentName = component.value();
               if (componentName == null) {
                  componentName = "";
               } else {
                  componentName = componentName.trim();
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getComponentName", componentName);
      }

      return componentName;
   }
}
