package weblogic.management.internal;

import weblogic.protocol.ServerIdentity;

public class AbstractAdminServerIdentity {
   protected static ServerIdentity adminIdentity;

   public static void setBootstrapIdentity(ServerIdentity id) {
      adminIdentity = id;
   }
}
