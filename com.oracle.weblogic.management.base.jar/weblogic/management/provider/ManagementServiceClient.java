package weblogic.management.provider;

import weblogic.management.provider.beaninfo.BeanInfoAccess;

public class ManagementServiceClient {
   public static ClientAccess getClientAccess() {
      return ManagementServiceClient.CLIENT_ACCESS.instance;
   }

   public static BeanInfoAccess getBeanInfoAccess() {
      return ManagementServiceClient.BEANINFO_ACCESS.instance;
   }

   private static Object initializeService(String serviceName) {
      try {
         Class clz = Class.forName(serviceName);
         ClientService service = (ClientService)clz.newInstance();
         return service.initialize();
      } catch (ClassNotFoundException var3) {
         throw new RuntimeException(var3);
      } catch (IllegalAccessException var4) {
         throw new RuntimeException(var4);
      } catch (InstantiationException var5) {
         throw new RuntimeException(var5);
      }
   }

   private static class BEANINFO_ACCESS {
      private static BeanInfoAccess instance = (BeanInfoAccess)ManagementServiceClient.initializeService("weblogic.management.provider.internal.BeanInfoAccessClientService");
   }

   private static class CLIENT_ACCESS {
      private static ClientAccess instance = (ClientAccess)ManagementServiceClient.initializeService("weblogic.management.provider.internal.ClientAccessClientService");
   }

   public interface ClientService {
      Object initialize();
   }
}
