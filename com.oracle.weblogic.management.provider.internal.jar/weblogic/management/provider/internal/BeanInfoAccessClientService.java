package weblogic.management.provider.internal;

import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementServiceClient;

public class BeanInfoAccessClientService implements ManagementServiceClient.ClientService {
   public Object initialize() {
      if (Kernel.isServer()) {
         throw new RuntimeException("ClientAccess can only be initialized in a client environment");
      } else {
         return BeanInfoAccessSingleton.getInstance();
      }
   }
}
