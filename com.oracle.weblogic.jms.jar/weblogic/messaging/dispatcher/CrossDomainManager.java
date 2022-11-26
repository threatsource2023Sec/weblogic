package weblogic.messaging.dispatcher;

import weblogic.kernel.KernelStatus;

public class CrossDomainManager {
   private static final String T3CLIENT_UTIL_CLASS = "weblogic.messaging.dispatcher.T3ClientCrossDomainUtil";
   private static final String IIOPCLIENT_UTIL_CLASS = "weblogic.messaging.dispatcher.IIOPClientCrossDomainUtil";
   private static final String SERVER_UTIL_CLASS = "weblogic.messaging.dispatcher.ServerCrossDomainUtil";
   private static CrossDomainUtil util;

   static void ensureInitialized() {
      Class utilClass;
      if (KernelStatus.isServer()) {
         try {
            utilClass = Class.forName("weblogic.messaging.dispatcher.ServerCrossDomainUtil");
         } catch (ClassNotFoundException var7) {
            throw new AssertionError(var7);
         }
      } else {
         try {
            utilClass = Class.forName("weblogic.messaging.dispatcher.T3ClientCrossDomainUtil");
         } catch (ClassNotFoundException var6) {
            try {
               utilClass = Class.forName("weblogic.messaging.dispatcher.IIOPClientCrossDomainUtil");
            } catch (ClassNotFoundException var5) {
               throw new AssertionError(var5);
            }
         }
      }

      try {
         util = (CrossDomainUtil)utilClass.newInstance();
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      } catch (InstantiationException var4) {
         throw new AssertionError(var4);
      }
   }

   public static CrossDomainUtil getCrossDomainUtil() {
      return util;
   }

   static {
      ensureInitialized();
   }
}
