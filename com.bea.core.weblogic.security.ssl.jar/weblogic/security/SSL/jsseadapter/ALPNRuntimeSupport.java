package weblogic.security.SSL.jsseadapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import weblogic.security.SecurityLogger;

public class ALPNRuntimeSupport {
   private static final Method APPLICATION_PROTOCOL_GETTER_METHOD = getApplicationProtocolGetterMethod();
   private static final Method HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR_SETTER_METHOD = getHandshakeApplicationProtocolSelectorSetterMethod();
   public static final String[] SUPPORTED_APPLICATION_PROTOCOLS = new String[]{"h2"};

   public static void setHandshakeApplicationProtocolSelector(SSLEngine engine, BiFunction selector) {
      if (engine instanceof JaSSLEngine) {
         engine = ((JaSSLEngine)engine).getDelegate();
      }

      if (HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR_SETTER_METHOD != null) {
         try {
            HANDSHAKE_APPLICATION_PROTOCOL_SELECTOR_SETTER_METHOD.invoke(engine, selector);
         } catch (InvocationTargetException | IllegalAccessException var3) {
         }
      }

   }

   public static String getApplicationProtocol(SSLEngine engine) {
      if (engine instanceof JaSSLEngine) {
         engine = ((JaSSLEngine)engine).getDelegate();
      }

      if (APPLICATION_PROTOCOL_GETTER_METHOD != null) {
         try {
            return (String)APPLICATION_PROTOCOL_GETTER_METHOD.invoke(engine);
         } catch (InvocationTargetException | IllegalAccessException var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String chooseApplicationProtocol(List clientProtocols, String[] supportedProtocols) {
      if (clientProtocols != null && !clientProtocols.isEmpty()) {
         String[] var2 = supportedProtocols;
         int var3 = supportedProtocols.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String protocol = var2[var4];
            if (clientProtocols.contains(protocol)) {
               return protocol;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static Method getHandshakeApplicationProtocolSelectorSetterMethod() {
      try {
         return SSLEngine.class.getMethod("setHandshakeApplicationProtocolSelector", BiFunction.class);
      } catch (NoSuchMethodException var1) {
         return null;
      }
   }

   private static Method getApplicationProtocolGetterMethod() {
      try {
         return SSLEngine.class.getMethod("getApplicationProtocol");
      } catch (NoSuchMethodException var1) {
         if (!isSSLEngineImplAdapted()) {
            SecurityLogger.logJdk8SslNotEndorsed();
         }

         return null;
      }
   }

   private static boolean isSSLEngineImplAdapted() {
      return false;
   }
}
