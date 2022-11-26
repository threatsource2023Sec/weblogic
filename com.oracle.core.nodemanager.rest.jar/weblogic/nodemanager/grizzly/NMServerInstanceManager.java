package weblogic.nodemanager.grizzly;

import weblogic.nodemanager.server.NMServer;

public class NMServerInstanceManager {
   private static NMServer nmServer;

   static void setInstance(NMServer server) {
      nmServer = server;
   }

   public static NMServer getInstance() {
      return nmServer;
   }
}
