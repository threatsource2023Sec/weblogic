package netscape.ldap;

import java.io.Serializable;
import java.net.Socket;

public class LDAPSSLSocketWrapFactory implements LDAPSSLSocketFactoryExt, Serializable {
   static final long serialVersionUID = -4171548771815037740L;
   private boolean m_clientAuth = false;
   private String m_packageName = null;
   private Object m_cipherSuites = null;

   public LDAPSSLSocketWrapFactory(String var1) {
      this.m_packageName = new String(var1);
   }

   public LDAPSSLSocketWrapFactory(String var1, Object var2) {
      this.m_packageName = new String(var1);
      this.m_cipherSuites = var2;
   }

   public Socket makeSocket(String var1, int var2) throws LDAPException {
      LDAPSSLSocket var3 = null;

      try {
         if (this.m_cipherSuites == null) {
            var3 = new LDAPSSLSocket(var1, var2, this.m_packageName);
         } else {
            var3 = new LDAPSSLSocket(var1, var2, this.m_packageName, this.m_cipherSuites);
         }

         return var3;
      } catch (Exception var5) {
         System.err.println("Exception: " + var5.toString());
         throw new LDAPException("Failed to create SSL socket", 91);
      }
   }

   public boolean isClientAuth() {
      return this.m_clientAuth;
   }

   public void enableClientAuth() throws LDAPException {
      throw new LDAPException("Client Authentication is not implemented yet.");
   }

   public String getSSLSocketImpl() {
      return this.m_packageName;
   }

   public Object getCipherSuites() {
      return this.m_cipherSuites;
   }
}
