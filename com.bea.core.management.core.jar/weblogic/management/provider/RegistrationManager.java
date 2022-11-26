package weblogic.management.provider;

import javax.management.ObjectName;
import org.jvnet.hk2.annotations.Contract;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.runtime.RuntimeMBean;

@Contract
public interface RegistrationManager {
   RuntimeMBean lookupRuntime(DescriptorBean var1);

   DescriptorBean lookupConfigurationBean(RuntimeMBean var1);

   void registerCustomBean(ObjectName var1, Object var2);

   void unregisterCustomBean(ObjectName var1);

   ObjectName[] getCustomMBeans();

   Object lookupCustomMBean(ObjectName var1);

   /** @deprecated */
   @Deprecated
   void register(RuntimeMBean var1, DescriptorBean var2);

   /** @deprecated */
   @Deprecated
   void unregister(RuntimeMBean var1);

   void registerBeanRelationship(RuntimeMBean var1, DescriptorBean var2);

   void unregisterBeanRelationship(RuntimeMBean var1);

   void registerService(Service var1);

   void unregisterService(Service var1);

   Service findService(String var1, String var2);

   Service[] getRootServices();

   Service[] getServices();

   void addRegistrationHandler(RegistrationHandler var1);

   void initiateRegistrationHandler(RegistrationHandler var1);

   void removeRegistrationHandler(RegistrationHandler var1);

   RuntimeMBean[] getRuntimeMBeans();
}
