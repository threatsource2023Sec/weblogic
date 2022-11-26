package weblogic.nodemanager.client;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.Socket;

abstract class SSLClient extends NMServerClient {
   public static final int LISTEN_PORT = 5556;
   private static Constructor sslClientConstructor;
   private static final String[] clients = new String[]{"weblogic.nodemanager.client.DefaultSSLClient", "weblogic.nodemanager.client.ThinSSLClient"};

   static SSLClient getSSLClient() {
      if (sslClientConstructor == null) {
         String clientClassName = System.getProperty("weblogic.nodemanager.SSLClientClass");
         String[] var1 = clients;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String platform = var1[var3];
            if (clientClassName == null || clientClassName.isEmpty() || platform.equals(clientClassName)) {
               try {
                  sslClientConstructor = Class.forName(platform).getConstructor();
               } catch (Throwable var6) {
               }

               if (sslClientConstructor != null) {
                  break;
               }
            }
         }
      }

      if (sslClientConstructor != null) {
         try {
            return (SSLClient)sslClientConstructor.newInstance();
         } catch (Throwable var7) {
         }
      }

      throw new IllegalArgumentException("Cannot locate platform specific SSLClient");
   }

   protected abstract Socket createSocket(String var1, int var2, int var3) throws IOException;
}
