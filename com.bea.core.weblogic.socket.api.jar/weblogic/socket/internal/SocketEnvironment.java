package weblogic.socket.internal;

import java.net.Socket;

public abstract class SocketEnvironment {
   private static SocketEnvironment singleton;

   public static SocketEnvironment getSocketEnvironment() {
      if (singleton == null) {
         try {
            singleton = (SocketEnvironment)Class.forName("weblogic.socket.WLSSocketEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            try {
               singleton = (SocketEnvironment)Class.forName("weblogic.socket.ClientSocketEnvironmentImpl").newInstance();
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2.toString());
            }
         }
      }

      return singleton;
   }

   public static void setSocketEnvironment(SocketEnvironment helper) {
      singleton = helper;
   }

   public abstract boolean serverThrottleEnabled();

   public abstract Socket getWeblogicSocket(Socket var1);

   public abstract boolean isJSSE();

   public abstract void serverThrottleDecrementOpenSocketCount();
}
