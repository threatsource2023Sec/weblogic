package netscape.ldap.factory;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPTLSSocketFactory;

public class JSSESocketFactory implements LDAPTLSSocketFactory, Serializable {
   static final long serialVersionUID = 6834205777733266610L;
   protected String[] suites;
   protected SSLSocketFactory factory;

   public JSSESocketFactory() {
      this((String[])null, (SSLSocketFactory)null);
   }

   public JSSESocketFactory(String[] var1) {
      this(var1, (SSLSocketFactory)null);
   }

   public JSSESocketFactory(String[] var1, SSLSocketFactory var2) {
      this.suites = var1;
      this.factory = var2 != null ? var2 : (SSLSocketFactory)SSLSocketFactory.getDefault();
   }

   public Socket makeSocket(String var1, int var2) throws LDAPException {
      SSLSocket var3 = null;

      try {
         var3 = (SSLSocket)this.factory.createSocket(var1, var2);
         if (this.suites != null) {
            var3.setEnabledCipherSuites(this.suites);
         }

         var3.startHandshake();
         return var3;
      } catch (UnknownHostException var5) {
         throw new LDAPException("JSSESocketFactory.makeSocket - Unknown host: " + var1, 91);
      } catch (IOException var6) {
         throw new LDAPException("JSSESocketFactory.makeSocket " + var1 + ":" + var2 + ", " + var6.getMessage(), 91);
      }
   }

   public Socket makeSocket(Socket var1) throws LDAPException {
      SSLSocket var2 = null;
      String var3 = var1.getInetAddress().getHostName();
      int var4 = var1.getPort();

      try {
         var2 = (SSLSocket)this.factory.createSocket(var1, var3, var4, true);
         if (this.suites != null) {
            var2.setEnabledCipherSuites(this.suites);
         }

         var2.startHandshake();
         return var2;
      } catch (IOException var6) {
         throw new LDAPException("JSSESocketFactory - start TLS, " + var6.getMessage(), 112);
      }
   }
}
