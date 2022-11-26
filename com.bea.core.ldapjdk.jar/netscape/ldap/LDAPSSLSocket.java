package netscape.ldap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;

class LDAPSSLSocket extends Socket {
   private final boolean m_debug = true;
   private Object m_socket;
   private Hashtable m_methodLookup = new Hashtable();
   private String m_packageName = null;

   public LDAPSSLSocket(String var1, int var2, String var3) throws LDAPException {
      this.m_packageName = var3;

      try {
         Class var4 = Class.forName(this.m_packageName);
         Constructor[] var5 = var4.getConstructors();

         for(int var6 = 0; var6 < var5.length; ++var6) {
            Class[] var7 = var5[var6].getParameterTypes();
            if (var7.length == 2 && var7[0].getName().equals("java.lang.String") && var7[1].getName().equals("int")) {
               Object[] var8 = new Object[]{var1, new Integer(var2)};
               this.m_socket = var5[var6].newInstance(var8);
               return;
            }
         }

         throw new LDAPException("No appropriate constructor in " + this.m_packageName, 89);
      } catch (ClassNotFoundException var9) {
         throw new LDAPException("Class " + this.m_packageName + " not found", 80);
      } catch (Exception var10) {
         throw new LDAPException("Failed to create SSL socket", 91);
      }
   }

   public LDAPSSLSocket(String var1, int var2, String var3, Object var4) throws LDAPException {
      this.m_packageName = var3;
      String var5 = null;
      if (var4 != null) {
         var5 = var4.getClass().getName();
      }

      try {
         Class var6 = Class.forName(this.m_packageName);
         Constructor[] var7 = var6.getConstructors();

         for(int var8 = 0; var8 < var7.length; ++var8) {
            Class[] var9 = var7[var8].getParameterTypes();
            if (var4 == null) {
               throw new LDAPException("Cipher Suites is required");
            }

            if (var9.length == 3 && var9[0].getName().equals("java.lang.String") && var9[1].getName().equals("int") && var9[2].getName().equals(var5)) {
               Object[] var10 = new Object[]{var1, new Integer(var2), var4};
               this.m_socket = var7[var8].newInstance(var10);
               return;
            }
         }

         throw new LDAPException("No appropriate constructor in " + this.m_packageName, 89);
      } catch (ClassNotFoundException var11) {
         throw new LDAPException("Class " + this.m_packageName + " not found", 80);
      } catch (Exception var12) {
         throw new LDAPException("Failed to create SSL socket", 91);
      }
   }

   public InputStream getInputStream() {
      try {
         Object var1 = this.invokeMethod(this.m_socket, "getInputStream", (Object[])null);
         return (InputStream)var1;
      } catch (LDAPException var2) {
         this.printDebug(var2.toString());
         return null;
      }
   }

   public OutputStream getOutputStream() {
      try {
         Object var1 = this.invokeMethod(this.m_socket, "getOutputStream", (Object[])null);
         return (OutputStream)var1;
      } catch (LDAPException var2) {
         this.printDebug(var2.toString());
         return null;
      }
   }

   public void close() throws IOException {
      try {
         this.invokeMethod(this.m_socket, "close", (Object[])null);
      } catch (LDAPException var2) {
         this.printDebug(var2.toString());
      }

   }

   public void close(boolean var1) throws IOException {
      try {
         Object[] var2 = new Object[]{new Boolean(var1)};
         this.invokeMethod(this.m_socket, "close", var2);
      } catch (LDAPException var3) {
         this.printDebug(var3.toString());
      }

   }

   public InetAddress getInetAddress() {
      try {
         Object var1 = this.invokeMethod(this.m_socket, "getInetAddress", (Object[])null);
         return (InetAddress)var1;
      } catch (LDAPException var2) {
         this.printDebug(var2.toString());
         return null;
      }
   }

   public int getLocalPort() {
      try {
         Object var1 = this.invokeMethod(this.m_socket, "getLocalPort", (Object[])null);
         return (Integer)var1;
      } catch (LDAPException var2) {
         this.printDebug(var2.toString());
         return -1;
      }
   }

   public int getPort() {
      try {
         Object var1 = this.invokeMethod(this.m_socket, "getPort", (Object[])null);
         return (Integer)var1;
      } catch (LDAPException var2) {
         this.printDebug(var2.toString());
         return -1;
      }
   }

   private Object invokeMethod(Object var1, String var2, Object[] var3) throws LDAPException {
      try {
         Method var4 = this.getMethod(var2);
         return var4 != null ? var4.invoke(var1, var3) : null;
      } catch (Exception var5) {
         throw new LDAPException("Invoking " + var2 + ": " + var5.toString(), 89);
      }
   }

   private Method getMethod(String var1) throws LDAPException {
      try {
         Method var2 = null;
         if ((var2 = (Method)((Method)this.m_methodLookup.get(var1))) != null) {
            return var2;
         } else {
            Class var3 = Class.forName(this.m_packageName);
            Method[] var4 = var3.getMethods();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               if (var4[var5].getName().equals(var1)) {
                  this.m_methodLookup.put(var1, var4[var5]);
                  return var4[var5];
               }
            }

            throw new LDAPException("Method " + var1 + " not found in " + this.m_packageName);
         }
      } catch (ClassNotFoundException var6) {
         throw new LDAPException("Class " + this.m_packageName + " not found");
      }
   }

   private void printDebug(String var1) {
      System.out.println(var1);
   }
}
