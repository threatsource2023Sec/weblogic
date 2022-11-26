package netscape.ldap;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;

public class LDAPSSLSocketFactory implements LDAPSSLSocketFactoryExt, Serializable {
   static final long serialVersionUID = -3331456736649381427L;
   private boolean m_clientAuth = false;
   private String m_packageName = "netscape.net.SSLSocket";
   private Object m_cipherSuites = null;

   public LDAPSSLSocketFactory() {
   }

   public LDAPSSLSocketFactory(boolean var1) {
      this.m_clientAuth = var1;
   }

   public LDAPSSLSocketFactory(String var1) {
      this.m_packageName = new String(var1);
   }

   public LDAPSSLSocketFactory(String var1, boolean var2) {
      this.m_packageName = new String(var1);
      this.m_clientAuth = var2;
   }

   public LDAPSSLSocketFactory(String var1, Object var2) {
      this.m_packageName = new String(var1);
      this.m_cipherSuites = var2;
   }

   public LDAPSSLSocketFactory(String var1, Object var2, boolean var3) {
      this.m_packageName = new String(var1);
      this.m_cipherSuites = var2;
      this.m_clientAuth = var3;
   }

   public void enableClientAuth() {
      this.m_clientAuth = true;
   }

   public void enableClientAuth(String var1, String var2, String var3, String var4, String var5) throws LDAPException {
      throw new LDAPException("Client auth not supported now");
   }

   public boolean isClientAuth() {
      return this.m_clientAuth;
   }

   public String getSSLSocketImpl() {
      return this.m_packageName;
   }

   public Object getCipherSuites() {
      return this.m_cipherSuites;
   }

   public Socket makeSocket(String var1, int var2) throws LDAPException {
      Socket var3 = null;
      if (this.m_clientAuth) {
         try {
            String[] var4 = new String[]{"java.lang.String"};
            Method var14 = DynamicInvoker.getMethod("netscape.security.PrivilegeManager", "enablePrivilege", var4);
            if (var14 != null) {
               Object[] var6 = new Object[]{new String("ClientAuth")};
               var14.invoke((Object)null, var6);
            }
         } catch (Exception var12) {
            String var5 = "LDAPSSLSocketFactory.makeSocket: invoking enablePrivilege: " + var12.toString();
            throw new LDAPException(var5, 89);
         }
      }

      try {
         String var13 = null;
         if (this.m_cipherSuites != null) {
            var13 = this.m_cipherSuites.getClass().getName();
         }

         Class var15 = Class.forName(this.m_packageName);
         Constructor[] var16 = var15.getConstructors();

         for(int var7 = 0; var7 < var16.length; ++var7) {
            Class[] var8 = var16[var7].getParameterTypes();
            Object[] var9;
            if (this.m_cipherSuites == null && var8.length == 2 && var8[0].getName().equals("java.lang.String") && var8[1].getName().equals("int")) {
               var9 = new Object[]{var1, new Integer(var2)};
               var3 = (Socket)((Socket)var16[var7].newInstance(var9));
               return var3;
            }

            if (this.m_cipherSuites != null && var8.length == 3 && var8[0].getName().equals("java.lang.String") && var8[1].getName().equals("int") && var8[2].getName().equals(var13)) {
               var9 = new Object[]{var1, new Integer(var2), this.m_cipherSuites};
               var3 = (Socket)((Socket)var16[var7].newInstance(var9));
               return var3;
            }
         }

         throw new LDAPException("No appropriate constructor in " + this.m_packageName, 89);
      } catch (ClassNotFoundException var10) {
         throw new LDAPException("Class " + this.m_packageName + " not found", 89);
      } catch (Exception var11) {
         throw new LDAPException("Failed to create SSL socket", 91);
      }
   }
}
