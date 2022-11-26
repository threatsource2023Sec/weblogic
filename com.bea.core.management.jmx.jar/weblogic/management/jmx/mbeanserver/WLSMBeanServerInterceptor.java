package weblogic.management.jmx.mbeanserver;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerConnection;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

public interface WLSMBeanServerInterceptor extends MBeanServerConnection {
   void setNextMBeanServerConnection(MBeanServerConnection var1);

   MBeanServerConnection getNextMBeanServerConnection();

   ClassLoader getClassLoaderFor(ObjectName var1) throws InstanceNotFoundException;

   ObjectInstance registerMBean(Object var1, ObjectName var2) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException;
}
