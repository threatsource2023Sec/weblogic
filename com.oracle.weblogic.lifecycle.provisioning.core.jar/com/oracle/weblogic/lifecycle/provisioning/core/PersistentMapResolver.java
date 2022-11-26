package com.oracle.weblogic.lifecycle.provisioning.core;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.FactoryDescriptorsImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;
import weblogic.store.PersistentMap;

@Service
@Singleton
public final class PersistentMapResolver implements JustInTimeInjectionResolver {
   private final ServiceLocator locator;

   @Inject
   public PersistentMapResolver(ServiceLocator serviceLocator) {
      Objects.requireNonNull(serviceLocator);
      this.locator = serviceLocator;
   }

   public boolean justInTimeResolution(Injectee failedInjectionPoint) {
      String className = PersistentMapResolver.class.getName();
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
            Named named = null;
            Collection qualifiers = failedInjectionPoint.getRequiredQualifiers();
            if (qualifiers != null && !qualifiers.isEmpty()) {
               Iterator var10 = qualifiers.iterator();

               while(var10.hasNext()) {
                  Annotation a = (Annotation)var10.next();
                  if (a instanceof Named) {
                     named = (Named)a;
                     break;
                  }
               }
            }

            if (named != null) {
               String name = named.value();
               if (name != null && !name.isEmpty() && "weblogic.store.PersistentMap".equals(requiredClass.getName())) {
                  DynamicConfiguration dc = ServiceLocatorUtilities.createDynamicConfiguration(this.locator);

                  assert dc != null;

                  Class factoryClass = this.getPersistentMapFactoryClass();
                  if (factoryClass == null) {
                     throw new IllegalStateException("this.getPersistentMapFactoryClass() == null");
                  }

                  Descriptor persistentMapFactoryDescriptor = BuilderHelper.activeLink(factoryClass).to(factoryClass).to(Factory.class).in(Singleton.class).named(name).build();

                  assert persistentMapFactoryDescriptor != null;

                  Descriptor persistentMapDescriptor = BuilderHelper.activeLink(factoryClass).to(PersistentMap.class).in(Singleton.class).named(name).buildProvideMethod();

                  assert persistentMapDescriptor != null;

                  FactoryDescriptors factoryDescriptors = new FactoryDescriptorsImpl(persistentMapFactoryDescriptor, persistentMapDescriptor);
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "justInTimeResolution", "Resolving injection point {0} by adding {1} as a factory for injection points of type {2} named {3}...", new Object[]{failedInjectionPoint, factoryClass, PersistentMap.class.getName(), name});
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

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "justInTimeResolution", returnValue);
      }

      return returnValue;
   }

   protected Class getPersistentMapFactoryClass() {
      return PersistentMapFactory.class;
   }
}
