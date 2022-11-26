package weblogic.cluster.singleton;

import weblogic.protocol.ServerIdentity;

public interface Leasing {
   boolean tryAcquire(String var1) throws LeasingException;

   void acquire(String var1, LeaseObtainedListener var2) throws LeasingException;

   void release(String var1) throws LeasingException;

   void removeFromOutStandingLeasesSet(String var1);

   String findOwner(String var1) throws LeasingException;

   void addLeaseLostListener(LeaseLostListener var1);

   void removeLeaseLostListener(LeaseLostListener var1);

   String[] findExpiredLeases();

   void voidLeases(String var1);

   public static class LeaseOwnerIdentity {
      public static final String DIVIDER = "/";
      public static final String DELIMITER = ".";

      public static String getIdentity(ServerIdentity id) {
         return id.getTransientIdentity().getIdentityAsLong() + "/" + id.getServerName();
      }

      public static String getServerNameFromIdentity(String ownerIdentity) {
         return ownerIdentity.substring(ownerIdentity.indexOf("/") + 1).trim();
      }
   }
}
