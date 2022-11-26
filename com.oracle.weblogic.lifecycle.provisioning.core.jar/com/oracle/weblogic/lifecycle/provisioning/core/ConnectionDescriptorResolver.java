package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConnectionDescriptor;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;
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
public class ConnectionDescriptorResolver implements JustInTimeInjectionResolver {
   private final ServiceLocator locator;

   @Inject
   public ConnectionDescriptorResolver(ServiceLocator serviceLocator) {
      Objects.requireNonNull(serviceLocator);
      this.locator = serviceLocator;
   }

   public final boolean justInTimeResolution(Injectee failedInjectionPoint) {
      String className = ConnectionDescriptorResolver.class.getName();
      String methodName = "justInTimeResolution";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "justInTimeResolution", failedInjectionPoint);
      }

      boolean returnValue = false;
      if (failedInjectionPoint != null) {
         ServiceLocator locator = this.locator;

         assert locator != null;

         Object requiredType = failedInjectionPoint.getRequiredType();
         if (requiredType instanceof Class && DataSource.class.isAssignableFrom((Class)requiredType)) {
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
               if (name != null && !name.isEmpty()) {
                  DynamicConfiguration dc = ServiceLocatorUtilities.createDynamicConfiguration(locator);

                  assert dc != null;

                  Class factoryClass = this.getConnectionDescriptorFactoryClass();
                  if (factoryClass == null) {
                     throw new IllegalStateException("this.getConnectionDescriptorFactoryClass() == null");
                  }

                  Descriptor connectionDescriptorFactoryDescriptor = BuilderHelper.activeLink(factoryClass).to(factoryClass).to(Factory.class).in(Singleton.class).named(name).build();

                  assert connectionDescriptorFactoryDescriptor != null;

                  Descriptor connectionDescriptorDescriptor = BuilderHelper.activeLink(factoryClass).to(ConnectionDescriptor.class).to(DataSource.class).in(PerLookup.class).named(name).buildProvideMethod();

                  assert connectionDescriptorDescriptor != null;

                  FactoryDescriptors factoryDescriptors = new FactoryDescriptorsImpl(connectionDescriptorFactoryDescriptor, connectionDescriptorDescriptor);
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "justInTimeResolution", "Resolving injection point {0} by adding {1} as a factory for injection points of type {2} named {3}...", new Object[]{failedInjectionPoint, factoryClass, ConnectionDescriptor.class.getName(), name});
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

   protected Class getConnectionDescriptorFactoryClass() {
      return ConnectionDescriptorFactory.class;
   }
}
