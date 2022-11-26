package netscape.ldap.factory;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPTLSSocketFactory;
import org.mozilla.jss.CryptoManager;
import org.mozilla.jss.crypto.AlreadyInitializedException;
import org.mozilla.jss.crypto.X509Certificate;
import org.mozilla.jss.ssl.SSLCertificateApprovalCallback;
import org.mozilla.jss.ssl.SSLClientCertificateSelectionCallback;
import org.mozilla.jss.ssl.SSLSocket;

public class JSSSocketFactory implements Serializable, LDAPTLSSocketFactory, SSLCertificateApprovalCallback {
   static final long serialVersionUID = -6926469178017736903L;

   public JSSSocketFactory() throws LDAPException {
      initialize(".");
   }

   public JSSSocketFactory(String var1) throws LDAPException {
      initialize(var1);
   }

   public static void initialize(String var0) throws LDAPException {
      try {
         CryptoManager.initialize(var0);
      } catch (AlreadyInitializedException var2) {
      } catch (Exception var3) {
         throw new LDAPException("Failed to initialize JSSSocketFactory: " + var3.getMessage(), 80);
      }

   }

   public Socket makeSocket(String var1, int var2) throws LDAPException {
      SSLSocket var3 = null;

      try {
         var3 = new SSLSocket(var1, var2, (InetAddress)null, 0, this, (SSLClientCertificateSelectionCallback)null);
         var3.forceHandshake();
         return var3;
      } catch (UnknownHostException var5) {
         throw new LDAPException("JSSSocketFactory.makeSocket - Unknown host: " + var1, 91);
      } catch (Exception var6) {
         throw new LDAPException("JSSSocketFactory.makeSocket " + var1 + ":" + var2 + ", " + var6.getMessage(), 91);
      }
   }

   public boolean approve(X509Certificate var1, SSLCertificateApprovalCallback.ValidityStatus var2) {
      return true;
   }

   public Socket makeSocket(Socket var1) throws LDAPException {
      SSLSocket var2 = null;
      String var3 = var1.getInetAddress().getHostName();
      int var4 = var1.getPort();

      try {
         var2 = new SSLSocket(var1, var3, this, (SSLClientCertificateSelectionCallback)null);
         var2.forceHandshake();
         return var2;
      } catch (Exception var6) {
         throw new LDAPException("JSSSocketFactory - start TLS, " + var6.getMessage(), 112);
      }
   }
}
