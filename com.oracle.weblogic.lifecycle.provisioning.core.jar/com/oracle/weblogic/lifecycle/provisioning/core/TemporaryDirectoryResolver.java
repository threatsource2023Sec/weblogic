package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.TemporaryDirectory;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.FactoryDescriptorsImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class TemporaryDirectoryResolver implements JustInTimeInjectionResolver {
   private final ServiceLocator serviceLocator;

   @Inject
   public TemporaryDirectoryResolver(ServiceLocator serviceLocator) {
      Objects.requireNonNull(serviceLocator);
      this.serviceLocator = serviceLocator;
   }

   public boolean justInTimeResolution(Injectee failedInjectionPoint) {
      String className = TemporaryDirectoryResolver.class.getName();
      String methodName = "justInTimeResolution";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "justInTimeResolution", failedInjectionPoint);
      }

      boolean returnValue = false;
      if (failedInjectionPoint != null) {
         ServiceLocator locator = this.serviceLocator;

         assert locator != null;

         Object requiredType = failedInjectionPoint.getRequiredType();
         if (requiredType instanceof Class && Path.class.isAssignableFrom((Class)requiredType)) {
            Collection qualifiers = failedInjectionPoint.getRequiredQualifiers();
            if (qualifiers != null && !qualifiers.isEmpty()) {
               TemporaryDirectory td = null;
               Iterator var10 = qualifiers.iterator();

               while(var10.hasNext()) {
                  Annotation a = (Annotation)var10.next();
                  if (a instanceof TemporaryDirectory) {
                     td = (TemporaryDirectory)a;
                     break;
                  }
               }

               if (td != null) {
                  DynamicConfiguration dc = ServiceLocatorUtilities.createDynamicConfiguration(locator);

                  assert dc != null;

                  AbstractActiveDescriptor factoryDescriptor = BuilderHelper.createConstantDescriptor(new TemporaryDirectoryFactory(td));

                  assert factoryDescriptor != null;

                  factoryDescriptor.setScope("javax.inject.Singleton");
                  Descriptor pathDescriptor = BuilderHelper.activeLink(TemporaryDirectoryFactory.class).to(Path.class).in(PerLookup.class).qualifiedBy(td).buildProvideMethod();

                  assert pathDescriptor != null;

                  FactoryDescriptors factoryDescriptors = new FactoryDescriptorsImpl(factoryDescriptor, pathDescriptor);
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "justInTimeResolution", "Resolving injection point {0} by adding {1} as a factory for injection points of type {2} qualified by {3}...", new Object[]{failedInjectionPoint, TemporaryDirectoryFactory.class, Path.class.getName(), td});
                  }

                  dc.bind(factoryDescriptors, false);
                  dc.commit();
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "justInTimeResolution", "...Done resolving injection point");
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
}
