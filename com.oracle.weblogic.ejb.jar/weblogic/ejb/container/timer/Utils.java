package weblogic.ejb.container.timer;

import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.ModuleRegistry;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.timers.ScheduleExpression;

final class Utils {
   private Utils() {
   }

   public static ScheduleExpression asWeblogicScheduleExpression(javax.ejb.ScheduleExpression se) {
      return se == null ? null : new ScheduleExpression(se.getYear(), se.getMonth(), se.getDayOfMonth(), se.getDayOfWeek(), se.getHour(), se.getMinute(), se.getSecond(), se.getStart(), se.getEnd(), se.getTimezone());
   }

   public static void register(BeanInfo bi, Class clazz, Object object) {
      getModuleRegistry(bi).put(getKey(bi.getEJBName(), clazz), object);
   }

   public static void unregister(BeanInfo bi, Class clazz) {
      getModuleRegistry(bi).remove(getKey(bi.getEJBName(), clazz));
   }

   public static Object getRegisteredValue(String appId, String moduleId, String ejbName, Class clazz) {
      ModuleRegistry mr = getModuleRegistry(appId, moduleId);
      return mr.get(getKey(ejbName, clazz));
   }

   private static ModuleRegistry getModuleRegistry(String applicationId, String moduleId) {
      ApplicationAccess aa = ApplicationAccess.getApplicationAccess();
      ApplicationContextInternal ac = aa.getApplicationContext(applicationId);
      return ac.getModuleContext(moduleId).getRegistry();
   }

   private static ModuleRegistry getModuleRegistry(BeanInfo bi) {
      DeploymentInfo di = bi.getDeploymentInfo();
      return getModuleRegistry(di.getApplicationId(), di.getModuleId());
   }

   private static String getKey(String ejbName, Class type) {
      return ejbName + "#" + type.getName();
   }
}
