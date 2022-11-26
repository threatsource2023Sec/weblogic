package weblogic.management;

import java.rmi.Remote;
import java.util.Set;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.ObjectName;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.runtime.RuntimeMBean;

/** @deprecated */
@Deprecated
public interface MBeanHome extends Remote {
   String JNDI_NAME = "weblogic.management.home";
   String LOCAL_JNDI_NAME = "weblogic.management.home.localhome";
   String ADMIN_JNDI_NAME = "weblogic.management.adminhome";

   RemoteMBeanServer getMBeanServer();

   WebLogicMBean getMBean(ObjectName var1) throws InstanceNotFoundException;

   Object getProxy(ObjectName var1) throws InstanceNotFoundException;

   WebLogicMBean getMBean(String var1, String var2, String var3, String var4) throws InstanceNotFoundException;

   WebLogicMBean getMBean(String var1, String var2, String var3) throws InstanceNotFoundException;

   WebLogicMBean getMBean(String var1, String var2) throws InstanceNotFoundException;

   WebLogicMBean getMBean(String var1, Class var2) throws InstanceNotFoundException;

   Set getMBeansByType(String var1);

   Set getAllMBeans(String var1);

   Set getAllMBeans();

   DomainMBean getActiveDomain();

   ConfigurationMBean getAdminMBean(String var1, String var2, String var3) throws InstanceNotFoundException;

   ConfigurationMBean getAdminMBean(String var1, String var2) throws InstanceNotFoundException;

   ConfigurationMBean getConfigurationMBean(String var1, String var2) throws InstanceNotFoundException;

   RuntimeMBean getRuntimeMBean(String var1, String var2) throws InstanceNotFoundException;

   WebLogicMBean createAdminMBean(String var1, String var2, String var3, ConfigurationMBean var4) throws MBeanCreationException;

   WebLogicMBean createAdminMBean(String var1, String var2, String var3) throws MBeanCreationException;

   WebLogicMBean createAdminMBean(String var1, String var2) throws MBeanCreationException;

   void addManagedHome(MBeanHome var1, String var2, String var3);

   String getDomainName();

   void deleteMBean(ObjectName var1) throws InstanceNotFoundException, MBeanRegistrationException;

   void deleteMBean(WebLogicMBean var1) throws InstanceNotFoundException, MBeanRegistrationException;
}
