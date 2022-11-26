package weblogic.management.provider;

import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.runtime.RuntimeMBean;

public interface RegistrationHandler {
   void registeredCustom(ObjectName var1, Object var2);

   void unregisteredCustom(ObjectName var1);

   void registered(RuntimeMBean var1, DescriptorBean var2);

   void unregistered(RuntimeMBean var1);

   void registered(Service var1);

   void unregistered(Service var1);
}
