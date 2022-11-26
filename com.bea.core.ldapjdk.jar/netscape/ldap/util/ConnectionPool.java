package netscape.ldap.util;

import java.util.Date;
import java.util.Vector;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPException;

public class ConnectionPool {
   private int poolSize;
   private int poolMax;
   private String host;
   private int port;
   private String authdn;
   private String authpw;
   private LDAPConnection ldc;
   private Vector pool;
   private boolean debugMode;

   public ConnectionPool(int var1, int var2, String var3, int var4, String var5, String var6) throws LDAPException {
      this(var1, var2, var3, var4, var5, var6, (LDAPConnection)null);
   }

   public ConnectionPool(int var1, int var2, String var3, int var4) throws LDAPException {
      this(var1, var2, var3, var4, "", "");
   }

   public ConnectionPool(String var1, int var2) throws LDAPException {
      this(10, 20, var1, var2, "", "");
   }

   public ConnectionPool(int var1, int var2, LDAPConnection var3) throws LDAPException {
      this(var1, var2, var3.getHost(), var3.getPort(), var3.getAuthenticationDN(), var3.getAuthenticationPassword(), (LDAPConnection)var3.clone());
   }

   private ConnectionPool(int var1, int var2, String var3, int var4, String var5, String var6, LDAPConnection var7) throws LDAPException {
      this.ldc = null;
      this.poolSize = var1;
      this.poolMax = var2;
      this.host = var3;
      this.port = var4;
      this.authdn = var5;
      this.authpw = var6;
      this.ldc = var7;
      this.debugMode = false;
      this.createPool();
   }

   public void destroy() {
      for(int var1 = 0; var1 < this.pool.size(); ++var1) {
         this.disconnect((LDAPConnectionObject)this.pool.elementAt(var1));
      }

      this.pool.removeAllElements();
   }

   public LDAPConnection getConnection() {
      LDAPConnection var1;
      while((var1 = this.getConnFromPool()) == null) {
         synchronized(this.pool) {
            try {
               this.pool.wait();
            } catch (InterruptedException var5) {
            }
         }
      }

      return var1;
   }

   public LDAPConnection getConnection(int var1) {
      LDAPConnection var2;
      while((var2 = this.getConnFromPool()) == null) {
         long var5 = System.currentTimeMillis();
         if (var1 <= 0) {
            return var2;
         }

         synchronized(this.pool) {
            try {
               this.pool.wait((long)var1);
            } catch (InterruptedException var10) {
               return null;
            }
         }

         long var3 = System.currentTimeMillis();
         var1 = (int)((long)var1 - (var3 - var5));
      }

      return var2;
   }

   protected synchronized LDAPConnection getConnFromPool() {
      LDAPConnection var1 = null;
      LDAPConnectionObject var2 = null;
      int var3 = this.pool.size();

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         LDAPConnectionObject var5 = (LDAPConnectionObject)this.pool.elementAt(var4);
         if (var5.isAvailable()) {
            var2 = var5;
            break;
         }
      }

      if (var2 == null) {
         if (this.poolMax >= 0 && (this.poolMax <= 0 || var3 >= this.poolMax)) {
            this.debug("All pool connections in use");
         } else {
            var4 = this.addConnection();
            if (var4 >= 0) {
               var2 = (LDAPConnectionObject)this.pool.elementAt(var4);
            }
         }
      }

      if (var2 != null) {
         var2.setInUse(true);
         var1 = var2.getLDAPConn();
      }

      return var1;
   }

   public synchronized void close(LDAPConnection var1) {
      int var2 = this.find(var1);
      if (var2 != -1) {
         LDAPConnectionObject var3 = (LDAPConnectionObject)this.pool.elementAt(var2);
         if (this.ldc == null || !this.ldc.getAuthenticationMethod().equals("sasl")) {
            boolean var4 = false;
            if (var1.getAuthenticationDN() == null) {
               var4 = this.authdn != null;
            } else if (!var1.getAuthenticationDN().equalsIgnoreCase(this.authdn)) {
               var4 = true;
            }

            if (var4) {
               try {
                  this.debug("user changed credentials-resetting");
                  var1.bind(this.authdn, this.authpw);
               } catch (LDAPException var8) {
                  this.debug("unable to reauth during close as " + this.authdn);
                  this.debug(var8.toString());
               }
            }
         }

         var3.setInUse(false);
         synchronized(this.pool) {
            this.pool.notifyAll();
         }
      }

   }

   public void printPool() {
      System.out.println("--ConnectionPool--");

      for(int var1 = 0; var1 < this.pool.size(); ++var1) {
         LDAPConnectionObject var2 = (LDAPConnectionObject)this.pool.elementAt(var1);
         System.out.println("" + var1 + "=" + var2);
      }

   }

   private void disconnect(LDAPConnectionObject var1) {
      if (var1 != null && var1.isAvailable()) {
         LDAPConnection var2 = var1.getLDAPConn();
         if (var2 != null && var2.isConnected()) {
            try {
               var2.disconnect();
            } catch (LDAPException var4) {
               this.debug("disconnect: " + var4.toString());
            }
         }

         var1.setLDAPConn((LDAPConnection)null);
      }

   }

   private void createPool() throws LDAPException {
      if (this.poolSize <= 0) {
         throw new LDAPException("ConnectionPoolSize invalid");
      } else {
         if (this.poolMax < this.poolSize) {
            this.debug("ConnectionPoolMax is invalid, set to " + this.poolSize);
            this.poolMax = this.poolSize;
         }

         this.debug("****Initializing LDAP Pool****");
         this.debug("LDAP host = " + this.host + " on port " + this.port);
         this.debug("Number of connections=" + this.poolSize);
         this.debug("Maximum number of connections=" + this.poolMax);
         this.debug("******");
         this.pool = new Vector();
         this.setUpPool(this.poolSize);
      }
   }

   private int addConnection() {
      int var1 = -1;
      this.debug("adding a connection to pool...");

      try {
         int var2 = this.pool.size() + 1;
         this.setUpPool(var2);
         if (var2 == this.pool.size()) {
            var1 = var2 - 1;
         }
      } catch (Exception var3) {
         this.debug("Adding a connection: " + var3.toString());
      }

      return var1;
   }

   private synchronized void setUpPool(int var1) throws LDAPException {
      while(this.pool.size() < var1) {
         LDAPConnectionObject var2 = new LDAPConnectionObject();
         LDAPConnection var3 = this.ldc != null ? (LDAPConnection)this.ldc.clone() : new LDAPConnection();
         var2.setLDAPConn(var3);

         try {
            if (var3.isConnected()) {
               var3.reconnect();
            } else {
               try {
                  var3.connect(3, this.host, this.port, this.authdn, this.authpw);
               } catch (LDAPException var5) {
                  if (var5.getLDAPResultCode() != 2) {
                     throw var5;
                  }

                  var3.connect(2, this.host, this.port, this.authdn, this.authpw);
               }
            }
         } catch (LDAPException var6) {
            this.debug("Creating pool:" + var6.toString());
            this.debug("aborting....");
            throw var6;
         }

         var2.setInUse(false);
         this.pool.addElement(var2);
      }

   }

   private int find(LDAPConnection var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < this.pool.size(); ++var2) {
            LDAPConnectionObject var3 = (LDAPConnectionObject)this.pool.elementAt(var2);
            if (var3.getLDAPConn() == var1) {
               return var2;
            }
         }
      }

      return -1;
   }

   public synchronized void setDebug(boolean var1) {
      this.debugMode = var1;
   }

   public boolean getDebug() {
      return this.debugMode;
   }

   private void debug(String var1) {
      if (this.debugMode) {
         System.out.println("ConnectionPool (" + new Date() + ") : " + var1);
      }

   }

   private void debug(String var1, boolean var2) {
      if (this.debugMode || var2) {
         System.out.println("ConnectionPool (" + new Date() + ") : " + var1);
      }

   }

   class LDAPConnectionObject {
      private LDAPConnection ld;
      private boolean inUse;

      LDAPConnection getLDAPConn() {
         return this.ld;
      }

      void setLDAPConn(LDAPConnection var1) {
         this.ld = var1;
      }

      void setInUse(boolean var1) {
         this.inUse = var1;
      }

      boolean isAvailable() {
         return !this.inUse;
      }

      public String toString() {
         return "LDAPConnection=" + this.ld + ",inUse=" + this.inUse;
      }
   }
}
