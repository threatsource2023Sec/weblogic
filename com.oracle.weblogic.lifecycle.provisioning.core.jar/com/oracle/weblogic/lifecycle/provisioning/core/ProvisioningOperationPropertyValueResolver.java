package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationProperty;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.FactoryDescriptorsImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class ProvisioningOperationPropertyValueResolver implements JustInTimeInjectionResolver {
   private static final Map factoryClasses = new HashMap();
   private final ServiceLocator serviceLocator;

   @Inject
   public ProvisioningOperationPropertyValueResolver(ServiceLocator serviceLocator) {
      Objects.requireNonNull(serviceLocator);
      this.serviceLocator = serviceLocator;
   }

   public final boolean justInTimeResolution(Injectee failedInjectionPoint) {
      String className = ProvisioningOperationPropertyValueResolver.class.getName();
      String methodName = "justInTimeResolution";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "justInTimeResolution", failedInjectionPoint);
      }

      boolean returnValue = false;
      if (failedInjectionPoint != null) {
         Object requiredType = failedInjectionPoint.getRequiredType();
         if (requiredType instanceof Class) {
            Class requiredClass = (Class)requiredType;
            Class factoryClass = this.getFactoryClass(requiredClass);
            if (factoryClass != null) {
               ProvisioningOperationProperty provisioningOperationProperty = null;
               Collection qualifiers = failedInjectionPoint.getRequiredQualifiers();
               if (qualifiers != null && !qualifiers.isEmpty()) {
                  Iterator var11 = qualifiers.iterator();

                  while(var11.hasNext()) {
                     Annotation a = (Annotation)var11.next();
                     if (a instanceof ProvisioningOperationProperty) {
                        provisioningOperationProperty = (ProvisioningOperationProperty)a;
                        break;
                     }
                  }
               }

               if (provisioningOperationProperty != null) {
                  String name = provisioningOperationProperty.value();
                  if (name != null && !name.isEmpty()) {
                     assert this.serviceLocator != null;

                     DynamicConfiguration dc = ServiceLocatorUtilities.createDynamicConfiguration(this.serviceLocator);

                     assert dc != null;

                     Descriptor factoryDescriptor = BuilderHelper.activeLink(factoryClass).to(factoryClass).to(Factory.class).in(Singleton.class).qualifiedBy(provisioningOperationProperty).build();

                     assert factoryDescriptor != null;

                     Descriptor propertyValueDescriptor = BuilderHelper.activeLink(factoryClass).to(requiredClass).in(PerLookup.class).qualifiedBy(provisioningOperationProperty).buildProvideMethod();

                     assert propertyValueDescriptor != null;

                     FactoryDescriptors factoryDescriptors = new FactoryDescriptorsImpl(factoryDescriptor, propertyValueDescriptor);
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, className, "justInTimeResolution", "Resolving injection point {0} by adding {1} as a factory for injection points of type {2} named {3}...", new Object[]{failedInjectionPoint, factoryClass, requiredClass.getName(), name});
                     }

                     dc.bind(factoryDescriptors, false);
                     dc.commit();
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, className, "justInTimeResolution", "...Done resolving injection point.");
                     }

                     returnValue = true;
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "justInTimeResolution", returnValue);
      }

      return returnValue;
   }

   protected Class getFactoryClass(Class injectionPointClass) {
      Objects.requireNonNull(injectionPointClass);
      return (Class)factoryClasses.get(injectionPointClass);
   }

   static {
      factoryClasses.put(Boolean.TYPE, ProvisioningOperationPropertyValueBooleanFactory.class);
      factoryClasses.put(Boolean.class, ProvisioningOperationPropertyValueBooleanFactory.class);
      factoryClasses.put(Character.TYPE, ProvisioningOperationPropertyValueCharacterFactory.class);
      factoryClasses.put(Character.class, ProvisioningOperationPropertyValueCharacterFactory.class);
      factoryClasses.put(Character[].class, ProvisioningOperationPropertyValueCharacterArrayFactory.class);
      factoryClasses.put(Double.TYPE, ProvisioningOperationPropertyValueDoubleFactory.class);
      factoryClasses.put(Double.class, ProvisioningOperationPropertyValueDoubleFactory.class);
      factoryClasses.put(Float.TYPE, ProvisioningOperationPropertyValueFloatFactory.class);
      factoryClasses.put(Float.class, ProvisioningOperationPropertyValueFloatFactory.class);
      factoryClasses.put(Integer.TYPE, ProvisioningOperationPropertyValueIntegerFactory.class);
      factoryClasses.put(Integer.class, ProvisioningOperationPropertyValueIntegerFactory.class);
      factoryClasses.put(Long.TYPE, ProvisioningOperationPropertyValueLongFactory.class);
      factoryClasses.put(Long.class, ProvisioningOperationPropertyValueLongFactory.class);
      factoryClasses.put(Short.TYPE, ProvisioningOperationPropertyValueShortFactory.class);
      factoryClasses.put(Short.class, ProvisioningOperationPropertyValueShortFactory.class);
      factoryClasses.put(String.class, ProvisioningOperationPropertyValueStringFactory.class);
      factoryClasses.put(char[].class, ProvisioningOperationPropertyValueCharArrayFactory.class);
   }
}
