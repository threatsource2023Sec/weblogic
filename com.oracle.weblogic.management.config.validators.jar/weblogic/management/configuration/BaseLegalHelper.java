package weblogic.management.configuration;

import java.util.ArrayList;
import javax.management.InstanceNotFoundException;
import weblogic.management.Admin;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;

public class BaseLegalHelper {
   protected static ConfigurationMBean nameToMBean(WebLogicObjectName name) {
      if (name == null) {
         return null;
      } else {
         try {
            return (ConfigurationMBean)Admin.getInstance().getMBeanHome().getMBean(name);
         } catch (InstanceNotFoundException var2) {
            throw new AssertionError("mbean not found " + name + ", " + var2);
         }
      }
   }

   protected static ConfigurationMBean[] namesToMBeans(WebLogicObjectName[] names, Object[] resultType) {
      ArrayList list = new ArrayList(names.length);

      for(int i = 0; i < names.length; ++i) {
         list.add(nameToMBean(names[i]));
      }

      ConfigurationMBean[] result = (ConfigurationMBean[])((ConfigurationMBean[])list.toArray(resultType));
      return result;
   }

   static boolean isExalogicOptimizationsEnabled(WebLogicMBean bean) {
      return findDMBParent(bean).isExalogicOptimizationsEnabled();
   }

   private static DomainMBean findDMBParent(WebLogicMBean bean) {
      while(bean != null && !(bean instanceof DomainMBean)) {
         bean = bean.getParent();
      }

      if (bean == null) {
         throw new NullPointerException();
      } else {
         return (DomainMBean)bean;
      }
   }
}
