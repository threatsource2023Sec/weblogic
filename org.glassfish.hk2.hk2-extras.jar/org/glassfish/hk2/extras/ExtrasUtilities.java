package org.glassfish.hk2.extras;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import org.glassfish.hk2.api.DuplicateServiceException;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.messaging.TopicDistributionService;
import org.glassfish.hk2.extras.events.internal.DefaultTopicDistributionService;
import org.glassfish.hk2.extras.hk2bridge.internal.Hk2BridgeImpl;
import org.glassfish.hk2.extras.interception.internal.DefaultInterceptionService;
import org.glassfish.hk2.extras.operation.internal.OperationManagerImpl;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class ExtrasUtilities {
   public static final String HK2BRIDGE_LOCATOR_ID = "org.jvnet.hk2.hk2bridge.locator.id";
   public static final String HK2BRIDGE_SERVICE_ID = "org.jvnet.hk2.hk2bridge.service.id";
   private static final String BRIDGE_NAME_PREFIX = "LocatorBridge(";
   private static final String COMMA = ",";
   private static final String BRIDGE_NAME_POSTFIX = ")";

   public static void enableDefaultInterceptorServiceImplementation(ServiceLocator locator) {
      if (locator.getBestDescriptor(BuilderHelper.createContractFilter(DefaultInterceptionService.class.getName())) == null) {
         try {
            ServiceLocatorUtilities.addClasses(locator, true, new Class[]{DefaultInterceptionService.class});
         } catch (MultiException var2) {
            if (!isDupException(var2)) {
               throw var2;
            }
         }
      }

   }

   public static void enableOperations(ServiceLocator locator) {
      if (locator.getBestDescriptor(BuilderHelper.createContractFilter(OperationManagerImpl.class.getName())) == null) {
         try {
            ServiceLocatorUtilities.addClasses(locator, true, new Class[]{OperationManagerImpl.class});
         } catch (MultiException var2) {
            if (!isDupException(var2)) {
               throw var2;
            }
         }

      }
   }

   private static String getBridgeName(ServiceLocator into, ServiceLocator from) {
      return "LocatorBridge(" + from.getLocatorId() + "," + into.getLocatorId() + ")";
   }

   private static void checkParentage(ServiceLocator a, ServiceLocator b) {
      for(ServiceLocator originalA = a; a != null; a = a.getParent()) {
         if (a.getLocatorId() == b.getLocatorId()) {
            throw new IllegalStateException("Locator " + originalA + " is a child of or is the same as locator " + b);
         }
      }

   }

   public static void bridgeServiceLocator(ServiceLocator into, ServiceLocator from) {
      checkParentage(into, from);
      checkParentage(from, into);
      String bridgeName = getBridgeName(into, from);
      if (from.getService(Hk2BridgeImpl.class, bridgeName, new Annotation[0]) != null) {
         throw new IllegalStateException("There is already a bridge from locator " + from.getName() + " to " + into.getName());
      } else {
         DescriptorImpl di = BuilderHelper.createDescriptorFromClass(Hk2BridgeImpl.class);
         di.setName(bridgeName);
         ServiceLocatorUtilities.addOneDescriptor(from, di);
         Hk2BridgeImpl bridge = (Hk2BridgeImpl)from.getService(Hk2BridgeImpl.class, bridgeName, new Annotation[0]);
         bridge.setRemote(into);
      }
   }

   public static void unbridgeServiceLocator(ServiceLocator into, ServiceLocator from) {
      checkParentage(into, from);
      checkParentage(from, into);
      String bridgeName = getBridgeName(into, from);
      ServiceHandle handle = from.getServiceHandle(Hk2BridgeImpl.class, bridgeName, new Annotation[0]);
      if (handle != null) {
         handle.destroy();
         ServiceLocatorUtilities.removeFilter(from, BuilderHelper.createNameAndContractFilter(Hk2BridgeImpl.class.getName(), bridgeName));
      }
   }

   public static void enableTopicDistribution(ServiceLocator locator) {
      if (locator == null) {
         throw new IllegalArgumentException();
      } else if (locator.getService(TopicDistributionService.class, "HK2TopicDistributionService", new Annotation[0]) == null) {
         try {
            ServiceLocatorUtilities.addClasses(locator, true, new Class[]{DefaultTopicDistributionService.class});
         } catch (MultiException var2) {
            if (!isDupException(var2)) {
               throw var2;
            }
         }

      }
   }

   private static boolean isDupException(MultiException me) {
      boolean atLeastOne = false;
      Iterator var2 = me.getErrors().iterator();

      Throwable error;
      do {
         if (!var2.hasNext()) {
            return atLeastOne;
         }

         error = (Throwable)var2.next();
         atLeastOne = true;
      } while(error instanceof DuplicateServiceException);

      return false;
   }
}
