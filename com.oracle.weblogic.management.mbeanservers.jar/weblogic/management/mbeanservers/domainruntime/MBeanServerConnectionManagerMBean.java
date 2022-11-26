package weblogic.management.mbeanservers.domainruntime;

import weblogic.management.mbeanservers.Service;

public interface MBeanServerConnectionManagerMBean extends Service {
   String OBJECT_NAME = "com.bea:Name=MBeanServerConnectionManager,Type=" + MBeanServerConnectionManagerMBean.class.getName();

   void notifyNewMBeanServer(String var1);
}
