package weblogic.management.runtime;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.provider.RegistrationManager;

@Contract
public interface RuntimeMBeanHelper {
   String getServerName();

   RuntimeMBean getDefaultParent();

   RegistrationManager getRegistrationManager();

   boolean isParentRequired(RuntimeMBean var1);

   boolean isParentRequired(String var1);
}
