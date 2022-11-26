package weblogic.server.channels;

import weblogic.security.SSL.jsseadapter.JaSSLSupport;
import weblogic.security.utils.SSLSetup;

public abstract class SslSupportFacade {
   private static volatile SslSupportFacade instance;

   public static String[] getEnabledSslProtocols(String[] supportedProtocols, String minimumTLSProtocolVersion, boolean ssLv2HelloEnabled) {
      return getInstance().doGetEnabledProtocols(supportedProtocols, minimumTLSProtocolVersion, ssLv2HelloEnabled);
   }

   private static SslSupportFacade getInstance() {
      return instance != null ? instance : createInstance();
   }

   private static synchronized SslSupportFacade createInstance() {
      if (instance != null) {
         return instance;
      } else {
         instance = new SslSupportFacadeImpl();
         return instance;
      }
   }

   public static void sslSetupLogInfo(String message) {
      getInstance().doSSlSetupLogInfo(message);
   }

   public static boolean isDebugEnabled() {
      return getInstance().doIsDebugEnabled();
   }

   abstract boolean doIsDebugEnabled();

   public abstract String[] doGetEnabledProtocols(String[] var1, String var2, boolean var3);

   public abstract void doSSlSetupLogInfo(String var1);

   static class SslSupportFacadeImpl extends SslSupportFacade {
      public String[] doGetEnabledProtocols(String[] supportedProtocols, String minimumTLSProtocolVersion, boolean ssLv2HelloEnabled) {
         return JaSSLSupport.getEnabledProtocols(supportedProtocols, minimumTLSProtocolVersion, ssLv2HelloEnabled);
      }

      public void doSSlSetupLogInfo(String message) {
         SSLSetup.info(message);
      }

      public boolean doIsDebugEnabled() {
         return SSLSetup.isDebugEnabled(3);
      }
   }
}
