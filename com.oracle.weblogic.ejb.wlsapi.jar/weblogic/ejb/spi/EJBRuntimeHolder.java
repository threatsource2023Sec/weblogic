package weblogic.ejb.spi;

import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;

public interface EJBRuntimeHolder extends RuntimeMBean {
   EJBRuntimeMBean[] getEJBRuntimes();

   EJBRuntimeMBean getEJBRuntime(String var1);

   void addEJBRuntimeMBean(String var1, EJBRuntimeMBean var2);

   void removeEJBRuntimeMBean(String var1);

   void removeAllEJBRuntimeMBeans();

   boolean addWorkManagerRuntime(WorkManagerRuntimeMBean var1);
}
