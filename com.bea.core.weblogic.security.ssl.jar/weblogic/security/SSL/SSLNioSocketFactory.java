package weblogic.security.SSL;

import java.net.SocketException;
import javax.net.SocketFactory;
import weblogic.security.utils.SSLSetup;

public class SSLNioSocketFactory extends SSLSocketFactory {
   public SSLNioSocketFactory() {
   }

   private SSLNioSocketFactory(SSLClientInfo sslCI) {
      this.setSSLClientInfo(sslCI);
   }

   protected SSLNioSocketFactory(javax.net.ssl.SSLSocketFactory factory) {
      super(factory);
   }

   public static SocketFactory getDefault() {
      if (defFactory == null) {
         Class var0 = SSLNioSocketFactory.class;
         synchronized(SSLNioSocketFactory.class) {
            if (defFactory == null) {
               defFactory = new SSLNioSocketFactory();
            }
         }
      }

      return defFactory;
   }

   public static SSLSocketFactory getInstance(SSLClientInfo sslCI) {
      return new SSLNioSocketFactory(sslCI);
   }

   public void setSSLClientInfo(SSLClientInfo sslCI) {
      try {
         if (sslCI != null && !sslCI.isNioSet()) {
            sslCI.setNio(true);
         }

         this.jsseFactory = sslCI == null ? SSLSetup.getSSLContext(sslCI).getSSLNioSocketFactory() : sslCI.getSSLSocketFactory();
      } catch (SocketException var3) {
         SSLSetup.debug(3, var3, "Failed to create context");
         throw new RuntimeException("Failed to update factory: " + var3.getMessage());
      }
   }
}
