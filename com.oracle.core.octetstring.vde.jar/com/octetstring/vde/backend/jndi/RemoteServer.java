package com.octetstring.vde.backend.jndi;

public class RemoteServer {
   private String hostname = null;
   private int port = 389;
   private int weight = 0;
   private boolean readOnly = false;
   private int connectionCount = 0;
   private int operationCount = 0;
   private int openConnections = 0;

   public RemoteServer(String serverString) {
      if (serverString.endsWith("+")) {
         this.readOnly = true;
         serverString = serverString.substring(0, serverString.length() - 1);
      }

      int ix = serverString.indexOf(":");
      if (ix < 0) {
         ix = serverString.indexOf("#");
         if (ix < 0) {
            this.hostname = serverString;
            return;
         }
      }

      this.hostname = serverString.substring(0, ix - 1);
      int wix = serverString.indexOf("#");
      if (wix != -1) {
         this.weight = Integer.parseInt(serverString.substring(wix + 1));
      }

      if (wix != ix && wix != -1) {
         this.port = Integer.parseInt(serverString.substring(ix + 1, wix - 1));
      }

      if (wix == -1) {
         this.port = Integer.parseInt(serverString.substring(ix + 1));
      }

   }

   public String getHostname() {
      return this.hostname;
   }

   public int getPort() {
      return this.port;
   }

   public int getWeight() {
      return this.weight;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public int getConnectionCount() {
      return this.connectionCount;
   }

   public int getOperationCount() {
      return this.operationCount;
   }

   public int getOpenConnections() {
      return this.openConnections;
   }

   public void incrementCC() {
      ++this.connectionCount;
   }

   public void incrementOC() {
      ++this.operationCount;
   }

   public void incrementOpen() {
      ++this.openConnections;
   }

   public void decrementOpen() {
      --this.openConnections;
   }

   public void zeroOpen() {
      this.openConnections = 0;
   }
}
