package netscape.ldap;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.Socket;

class LDAPConnSetupMgr implements Serializable {
   static final long serialVersionUID = 1519402748245755307L;
   private static final int SERIAL = 0;
   private static final int PARALLEL = 1;
   private static final int CONNECTED = 0;
   private static final int DISCONNECTED = 1;
   private static final int NEVER_USED = 2;
   private static final int INTERRUPTED = 3;
   private static final int FAILED = 4;
   private Socket m_socket = null;
   private Socket m_origSocket = null;
   private LDAPException m_connException = null;
   ServerEntry[] m_dsList;
   private int m_dsIdx = -1;
   LDAPSocketFactory m_factory;
   int m_policy = 0;
   int m_connSetupDelay = -1;
   int m_connectTimeout = 0;
   private transient int m_attemptCnt = 0;

   LDAPConnSetupMgr(String[] var1, int[] var2, LDAPSocketFactory var3) throws LDAPException {
      this.m_dsList = new ServerEntry[var1.length];
      boolean var4 = var3 != null;

      for(int var5 = 0; var5 < var1.length; ++var5) {
         String var6 = var4 ? "ldaps://" : "ldap://";
         var6 = var6 + var1[var5] + ":" + var2[var5];

         try {
            this.m_dsList[var5] = new ServerEntry(new LDAPUrl(var6), 2);
         } catch (MalformedURLException var8) {
            throw new LDAPException("Invalid host:port " + var1[var5] + ":" + var2[var5], 89);
         }
      }

      this.m_factory = var3;
   }

   LDAPConnSetupMgr(String[] var1, LDAPSocketFactory var2) throws LDAPException {
      this.m_dsList = new ServerEntry[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         try {
            LDAPUrl var4 = new LDAPUrl(var1[var3]);
            this.m_dsList[var3] = new ServerEntry(var4, 2);
         } catch (MalformedURLException var5) {
            throw new LDAPException("Malformed LDAP URL " + var1[var3], 89);
         }
      }

      this.m_factory = var2;
   }

   LDAPConnSetupMgr(LDAPUrl[] var1, LDAPSocketFactory var2) throws LDAPException {
      this.m_dsList = new ServerEntry[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.m_dsList[var3] = new ServerEntry(var1[var3], 2);
      }

      this.m_factory = var2;
   }

   synchronized Socket openConnection() throws LDAPException {
      long var1 = 0L;
      long var3 = Long.MAX_VALUE;
      Thread var5 = null;
      this.reset();
      this.sortDsList();
      if (this.m_connectTimeout == 0) {
         this.connect();
      } else {
         var3 = System.currentTimeMillis() + (long)this.m_connectTimeout;
         var5 = new Thread(new Runnable() {
            public void run() {
               LDAPConnSetupMgr.this.connect();
            }
         }, "ConnSetupMgr");
         var5.setDaemon(true);
         var5.start();

         while(this.m_socket == null && this.m_attemptCnt < this.m_dsList.length && (var1 = System.currentTimeMillis()) < var3) {
            try {
               this.wait(var3 - var1);
            } catch (InterruptedException var7) {
               var5.interrupt();
               this.cleanup();
               throw new LDAPInterruptedException("Interrupted connect operation");
            }
         }
      }

      if (this.m_socket != null) {
         return this.m_socket;
      } else if (var5 != null && System.currentTimeMillis() >= var3) {
         var5.interrupt();
         this.cleanup();
         throw new LDAPException("Connect timeout, " + this.getServerList() + " might be unreachable", 91);
      } else if (this.m_connException != null && this.m_dsList.length == 1) {
         throw this.m_connException;
      } else {
         throw new LDAPException("Failed to connect to server " + this.getServerList(), 91);
      }
   }

   private void reset() {
      this.m_socket = null;
      this.m_origSocket = null;
      this.m_connException = null;
      this.m_attemptCnt = 0;

      for(int var1 = 0; var1 < this.m_dsList.length; ++var1) {
         this.m_dsList[var1].connSetupThread = null;
      }

   }

   private String getServerList() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.m_dsList.length; ++var2) {
         var1.append(var2 == 0 ? "" : " ");
         var1.append(this.m_dsList[var2].url.getHost());
         var1.append(":");
         var1.append(this.m_dsList[var2].url.getPort());
      }

      return var1.toString();
   }

   private void connect() {
      if (this.m_policy != 0 && this.m_dsList.length != 1) {
         this.openParallel();
      } else {
         this.openSerial();
      }

   }

   synchronized void invalidateConnection() {
      if (this.m_socket != null) {
         this.m_dsList[this.m_dsIdx].connSetupStatus = 4;
         int var1 = this.m_dsList.length;
         int var2 = 0;
         ServerEntry[] var3 = new ServerEntry[this.m_dsList.length];

         for(int var4 = 0; var4 < var1; ++var4) {
            if (var4 != this.m_dsIdx) {
               var3[var2++] = this.m_dsList[var4];
            }
         }

         var3[var2] = this.m_dsList[this.m_dsIdx];
         this.m_dsList = var3;
         this.m_dsIdx = var2;

         try {
            this.m_socket.close();
         } catch (Exception var17) {
         } finally {
            this.m_socket = null;
         }
      }

      if (this.m_origSocket != null) {
         try {
            this.m_origSocket.close();
         } catch (Exception var15) {
         } finally {
            this.m_origSocket = null;
         }
      }

   }

   void closeConnection() {
      if (this.m_socket != null) {
         this.m_dsList[this.m_dsIdx].connSetupStatus = 1;

         try {
            this.m_socket.close();
         } catch (Exception var14) {
         } finally {
            this.m_socket = null;
         }
      }

      if (this.m_origSocket != null) {
         try {
            this.m_origSocket.close();
         } catch (Exception var12) {
         } finally {
            this.m_origSocket = null;
         }
      }

   }

   Socket getSocket() {
      return this.m_socket;
   }

   void layerSocket(LDAPTLSSocketFactory var1) throws LDAPException {
      Socket var2 = var1.makeSocket(this.m_socket);
      this.m_origSocket = this.m_socket;
      this.m_socket = var2;
   }

   String getHost() {
      return this.m_dsIdx >= 0 ? this.m_dsList[this.m_dsIdx].url.getHost() : this.m_dsList[0].url.getHost();
   }

   int getPort() {
      return this.m_dsIdx >= 0 ? this.m_dsList[this.m_dsIdx].url.getPort() : this.m_dsList[0].url.getPort();
   }

   boolean isSecure() {
      return this.m_dsIdx >= 0 ? this.m_dsList[this.m_dsIdx].url.isSecure() : this.m_dsList[0].url.isSecure();
   }

   LDAPUrl getLDAPUrl() {
      return this.m_dsIdx >= 0 ? this.m_dsList[this.m_dsIdx].url : this.m_dsList[0].url;
   }

   int getConnSetupDelay() {
      return this.m_connSetupDelay / 1000;
   }

   void setConnSetupDelay(int var1) {
      this.m_policy = var1 < 0 ? 0 : 1;
      this.m_connSetupDelay = var1 * 1000;
   }

   int getConnectTimeout() {
      return this.m_connectTimeout / 1000;
   }

   void setConnectTimeout(int var1) {
      this.m_connectTimeout = var1 * 1000;
   }

   boolean isUserDisconnected() {
      return this.m_dsIdx >= 0 && this.m_dsList[this.m_dsIdx].connSetupStatus == 1;
   }

   private void openSerial() {
      for(int var1 = 0; var1 < this.m_dsList.length; ++var1) {
         this.m_dsList[var1].connSetupThread = Thread.currentThread();
         this.connectServer(var1);
         if (this.m_socket != null) {
            return;
         }
      }

   }

   private synchronized void openParallel() {
      for(final int var1 = 0; this.m_socket == null && var1 < this.m_dsList.length; ++var1) {
         String var3 = "ConnSetupMgr " + this.m_dsList[var1].url;
         Thread var4 = new Thread(new Runnable() {
            public void run() {
               LDAPConnSetupMgr.this.connectServer(var1);
            }
         }, var3);
         this.m_dsList[var1].connSetupThread = var4;
         var4.setDaemon(true);
         var4.start();
         if (this.m_connSetupDelay != 0 && var1 < this.m_dsList.length - 1) {
            try {
               this.wait((long)this.m_connSetupDelay);
            } catch (InterruptedException var7) {
               return;
            }
         }
      }

      while(this.m_socket == null && this.m_attemptCnt < this.m_dsList.length) {
         try {
            this.wait();
         } catch (InterruptedException var6) {
         }
      }

   }

   void connectServer(int var1) {
      ServerEntry var2 = this.m_dsList[var1];
      Thread var3 = Thread.currentThread();
      Socket var4 = null;
      LDAPException var5 = null;

      try {
         if (!var2.url.isSecure()) {
            var4 = new Socket(var2.url.getHost(), var2.url.getPort());
         } else {
            LDAPSocketFactory var6 = this.m_factory;
            if (var6 == null) {
               LDAPUrl var10000 = var2.url;
               var6 = LDAPUrl.getSocketFactory();
            }

            if (var6 == null) {
               throw new LDAPException("Can not connect, no socket factory " + var2.url, 80);
            }

            var4 = var6.makeSocket(var2.url.getHost(), var2.url.getPort());
         }

         var4.setTcpNoDelay(true);
      } catch (IOException var9) {
         var5 = new LDAPException("failed to connect to server " + var2.url, 91);
      } catch (LDAPException var10) {
         var5 = var10;
      }

      if (!var3.isInterrupted()) {
         synchronized(this) {
            if (this.m_socket == null && var2.connSetupThread == var3) {
               var2.connSetupThread = null;
               if (var4 != null) {
                  var2.connSetupStatus = 0;
                  this.m_socket = var4;
                  this.m_dsIdx = var1;
                  this.cleanup();
               } else {
                  var2.connSetupStatus = 4;
                  this.m_connException = var5;
               }

               ++this.m_attemptCnt;
               this.notifyAll();
            }

         }
      }
   }

   private synchronized void cleanup() {
      Thread var1 = Thread.currentThread();

      for(int var2 = 0; var2 < this.m_dsList.length; ++var2) {
         ServerEntry var3 = this.m_dsList[var2];
         if (var3.connSetupThread != null && var3.connSetupThread != var1) {
            var3.connSetupStatus = 3;
            var3.connSetupThread.interrupt();
            var3.connSetupThread = null;
         }
      }

   }

   private void sortDsList() {
      int var1 = this.m_dsList.length;

      for(int var2 = 1; var2 < var1; ++var2) {
         for(int var3 = 0; var3 < var2; ++var3) {
            if (this.m_dsList[var2].connSetupStatus < this.m_dsList[var3].connSetupStatus) {
               ServerEntry var4 = this.m_dsList[var3];
               this.m_dsList[var3] = this.m_dsList[var2];
               this.m_dsList[var2] = var4;
            }
         }
      }

   }

   boolean breakConnection() {
      try {
         this.m_socket.close();
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   public String toString() {
      String var1 = "dsIdx=" + this.m_dsIdx + " dsList=";

      for(int var2 = 0; var2 < this.m_dsList.length; ++var2) {
         var1 = var1 + this.m_dsList[var2] + " ";
      }

      return var1;
   }

   class ServerEntry {
      LDAPUrl url;
      int connSetupStatus;
      Thread connSetupThread;

      ServerEntry(LDAPUrl var2, int var3) {
         this.url = var2;
         this.connSetupStatus = var3;
         this.connSetupThread = null;
      }

      public String toString() {
         return "{" + this.url + " status=" + this.connSetupStatus + "}";
      }
   }
}
