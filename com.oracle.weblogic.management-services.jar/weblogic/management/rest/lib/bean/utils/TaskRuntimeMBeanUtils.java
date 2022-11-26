package weblogic.management.rest.lib.bean.utils;

import weblogic.management.runtime.DeploymentProgressObjectMBean;
import weblogic.management.runtime.TaskRuntimeMBean;

class TaskRuntimeMBeanUtils {
   public static boolean isTaskRuntimeMBean(Class type) throws Exception {
      return isType(TaskRuntimeMBean.class, type);
   }

   public static boolean isDeploymentProgressObjectMBean(Class type) throws Exception {
      return isType(DeploymentProgressObjectMBean.class, type);
   }

   private static boolean isType(Class typeWant, Class typeHave) throws Exception {
      if (typeWant.isAssignableFrom(typeHave)) {
         return true;
      } else {
         return typeHave.isArray() ? isType(typeWant, typeHave.getComponentType()) : false;
      }
   }
}
