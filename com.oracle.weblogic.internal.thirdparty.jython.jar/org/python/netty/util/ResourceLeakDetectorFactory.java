package org.python.netty.util;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public abstract class ResourceLeakDetectorFactory {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetectorFactory.class);
   private static volatile ResourceLeakDetectorFactory factoryInstance = new DefaultResourceLeakDetectorFactory();

   public static ResourceLeakDetectorFactory instance() {
      return factoryInstance;
   }

   public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory factory) {
      factoryInstance = (ResourceLeakDetectorFactory)ObjectUtil.checkNotNull(factory, "factory");
   }

   public final ResourceLeakDetector newResourceLeakDetector(Class resource) {
      return this.newResourceLeakDetector(resource, 128);
   }

   /** @deprecated */
   @Deprecated
   public abstract ResourceLeakDetector newResourceLeakDetector(Class var1, int var2, long var3);

   public ResourceLeakDetector newResourceLeakDetector(Class resource, int samplingInterval) {
      return this.newResourceLeakDetector(resource, 128, Long.MAX_VALUE);
   }

   private static final class DefaultResourceLeakDetectorFactory extends ResourceLeakDetectorFactory {
      private final Constructor obsoleteCustomClassConstructor;
      private final Constructor customClassConstructor;

      DefaultResourceLeakDetectorFactory() {
         String customLeakDetector;
         try {
            customLeakDetector = (String)AccessController.doPrivileged(new PrivilegedAction() {
               public String run() {
                  return SystemPropertyUtil.get("org.python.netty.customResourceLeakDetector");
               }
            });
         } catch (Throwable var3) {
            ResourceLeakDetectorFactory.logger.error("Could not access System property: io.netty.customResourceLeakDetector", var3);
            customLeakDetector = null;
         }

         if (customLeakDetector == null) {
            this.obsoleteCustomClassConstructor = this.customClassConstructor = null;
         } else {
            this.obsoleteCustomClassConstructor = obsoleteCustomClassConstructor(customLeakDetector);
            this.customClassConstructor = customClassConstructor(customLeakDetector);
         }

      }

      private static Constructor obsoleteCustomClassConstructor(String customLeakDetector) {
         try {
            Class detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
            if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
               return detectorClass.getConstructor(Class.class, Integer.TYPE, Long.TYPE);
            }

            ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object)customLeakDetector);
         } catch (Throwable var2) {
            ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, var2);
         }

         return null;
      }

      private static Constructor customClassConstructor(String customLeakDetector) {
         try {
            Class detectorClass = Class.forName(customLeakDetector, true, PlatformDependent.getSystemClassLoader());
            if (ResourceLeakDetector.class.isAssignableFrom(detectorClass)) {
               return detectorClass.getConstructor(Class.class, Integer.TYPE);
            }

            ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object)customLeakDetector);
         } catch (Throwable var2) {
            ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", customLeakDetector, var2);
         }

         return null;
      }

      public ResourceLeakDetector newResourceLeakDetector(Class resource, int samplingInterval, long maxActive) {
         ResourceLeakDetector leakDetector;
         if (this.obsoleteCustomClassConstructor != null) {
            try {
               leakDetector = (ResourceLeakDetector)this.obsoleteCustomClassConstructor.newInstance(resource, samplingInterval, maxActive);
               ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", (Object)this.obsoleteCustomClassConstructor.getDeclaringClass().getName());
               return leakDetector;
            } catch (Throwable var6) {
               ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.obsoleteCustomClassConstructor.getDeclaringClass().getName(), resource, var6);
            }
         }

         leakDetector = new ResourceLeakDetector(resource, samplingInterval, maxActive);
         ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object)leakDetector);
         return leakDetector;
      }

      public ResourceLeakDetector newResourceLeakDetector(Class resource, int samplingInterval) {
         ResourceLeakDetector leakDetector;
         if (this.customClassConstructor != null) {
            try {
               leakDetector = (ResourceLeakDetector)this.customClassConstructor.newInstance(resource, samplingInterval);
               ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", (Object)this.customClassConstructor.getDeclaringClass().getName());
               return leakDetector;
            } catch (Throwable var4) {
               ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.customClassConstructor.getDeclaringClass().getName(), resource, var4);
            }
         }

         leakDetector = new ResourceLeakDetector(resource, samplingInterval);
         ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object)leakDetector);
         return leakDetector;
      }
   }
}
