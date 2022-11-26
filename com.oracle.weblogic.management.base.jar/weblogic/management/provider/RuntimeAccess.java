package weblogic.management.provider;

import org.jvnet.hk2.annotations.Contract;
import weblogic.descriptor.SettableBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.SimplePropertyValueVBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.SecurityRuntimeAccess;

@Contract
public interface RuntimeAccess extends RegistrationManager, SecurityRuntimeAccess {
   ServerRuntimeMBean getServerRuntime();

   void addAccessCallbackClass(String var1);

   AccessCallback[] initializeCallbacks(DomainMBean var1);

   boolean isAdminServer();

   boolean isAdminServerAvailable();

   String getDomainName();

   String getAdminServerName();

   PropertyValueVBean[] getPropertyValues(ConfigurationMBean var1, String[] var2) throws Exception;

   PropertyValueVBean[] getPropertyValues(ConfigurationMBean var1, String[] var2, SettableBean[] var3, String[] var4) throws Exception;

   SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean var1, String[] var2) throws Exception;

   SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean var1, String[] var2, SettableBean[] var3, String[] var4) throws Exception;

   SimplePropertyValueVBean[] getWorkingValues(ConfigurationMBean var1, String[] var2) throws Exception;
}
