package weblogic.deploy.internal;

import java.util.Comparator;
import java.util.HashMap;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.management.configuration.BasicDeploymentMBean;

public class DeploymentOrder {
   public static final DeploymentType[] DEPLOYMENT_ORDER;
   public static final Comparator COMPARATOR;
   private static final HashMap orderMap;

   public static final boolean isBeforeDeploymentHandler(BasicDeploymentMBean bean) {
      for(int i = 0; i < DEPLOYMENT_ORDER.length; ++i) {
         DeploymentType depType = DEPLOYMENT_ORDER[i];
         if (depType == DeploymentType.DEPLOYMENT_HANDLER) {
            return false;
         }

         if (depType.isInstance(bean)) {
            return true;
         }
      }

      return true;
   }

   public static int getCachedDeploymentOrder(BasicDeploymentMBean mbean) {
      Integer order = (Integer)orderMap.get(mbean);
      return order != null ? order : mbean.getDeploymentOrder();
   }

   public static Object addToDeploymentOrderCache(BasicDeploymentMBean mbean, int order) {
      return orderMap.put(mbean, new Integer(order));
   }

   public static Object removeFromDeploymentOrderCache(BasicDeploymentMBean mbean) {
      return orderMap.remove(mbean);
   }

   static {
      DEPLOYMENT_ORDER = new DeploymentType[]{DeploymentType.CACHE, DeploymentType.INTERNAL_APP, DeploymentType.JDBC_SYS_RES, DeploymentType.DEPLOYMENT_HANDLER, DeploymentType.JMS_SYS_RES, DeploymentType.RESOURCE_DEPENDENT_DEPLOYMENT_HANDLER, DeploymentType.STARTUP_CLASS, DeploymentType.WLDF_SYS_RES, DeploymentType.LIBRARY, DeploymentType.DEFAULT_APP, DeploymentType.COHERENCE_CLUSTER_SYS_RES, DeploymentType.CUSTOM_SYS_RES};
      COMPARATOR = new Comparator() {
         public int compare(Object o1, Object o2) {
            if (o1 == o2) {
               return 0;
            } else {
               for(int i = 0; i < DeploymentOrder.DEPLOYMENT_ORDER.length; ++i) {
                  DeploymentType depType = DeploymentOrder.DEPLOYMENT_ORDER[i];
                  if (depType.isInstance(o1) && !depType.isInstance(o2)) {
                     return -1;
                  }

                  if (!depType.isInstance(o1) && depType.isInstance(o2)) {
                     return 1;
                  }

                  if (depType.isInstance(o1) && depType.isInstance(o2)) {
                     return depType.compare(o1, o2);
                  }
               }

               String msg = DeploymentServiceLogger.unrecognizedTypes(o1.getClass().getName(), o2.getClass().getName());
               throw new ClassCastException(msg);
            }
         }
      };
      orderMap = new HashMap();
   }
}
