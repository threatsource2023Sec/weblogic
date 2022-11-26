package weblogic.management.utils.federatedconfig;

import java.util.Iterator;
import java.util.Set;

public interface FederatedConfigLocator {
   Iterator sources(Set var1, String var2) throws Exception;

   boolean registerUpdateListener(FederatedConfig.UpdateListener var1);

   boolean unregisterUpdateListener(FederatedConfig.UpdateListener var1);
}
