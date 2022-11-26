package weblogic.protocol;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class ServerIdentityManager {
   private static final ConcurrentHashMap identityMap = new ConcurrentHashMap(31);
   private static final ConcurrentHashMap identityNameMap = new ConcurrentHashMap(31);
   private static final ConcurrentHashMap domainServerMap = new ConcurrentHashMap(31);

   public static ServerIdentity findServerIdentityFromTransient(Identity identity) {
      if (!ServerIdentityManager.Initializer.checkInitialized) {
         throw new AssertionError("Can not get here");
      } else {
         return (ServerIdentity)identityMap.get(identity);
      }
   }

   public static ServerIdentity findServerIdentityFromPersistent(Identity identity) {
      if (!ServerIdentityManager.Initializer.checkInitialized) {
         throw new AssertionError("Can not get here");
      } else {
         return (ServerIdentity)identityMap.get(identity);
      }
   }

   public static ServerIdentity findServerIdentity(String domainName, String serverName) {
      if (!ServerIdentityManager.Initializer.checkInitialized) {
         throw new AssertionError("Can not get here");
      } else {
         return (ServerIdentity)identityNameMap.get(new CompositeKey(domainName, serverName));
      }
   }

   public static synchronized boolean recordIdentity(ServerIdentity id) {
      String domainName = id.getDomainName();
      String serverName = id.getServerName();
      ServerIdentity old = (ServerIdentity)identityMap.putIfAbsent(id.getTransientIdentity(), id);
      if (old != null) {
         if (!old.getServerName().equals(serverName) && !old.getDomainName().equals(domainName)) {
            throw new AssertionError("Found two servers with the same identity " + id + "\t" + old);
         } else {
            return true;
         }
      } else {
         identityMap.put(id.getPersistentIdentity(), id);
         if (domainName != null) {
            identityNameMap.put(new CompositeKey(domainName, serverName), id);
            HashSet serverList = (HashSet)domainServerMap.get(domainName);
            if (serverList == null) {
               serverList = new HashSet();
            }

            if (serverList.add(serverName)) {
               domainServerMap.put(domainName, serverList);
            }
         }

         return true;
      }
   }

   public static synchronized void removeIdentity(ServerIdentity id) {
      removeTransientAndPersistentIdentity(id);
      CompositeKey key = new CompositeKey(id.getDomainName(), id.getServerName());
      ServerIdentity oldId = (ServerIdentity)identityNameMap.get(key);
      if (oldId != null && id.equals(oldId) && id.getTransientIdentity().equals(oldId.getTransientIdentity())) {
         identityNameMap.remove(key);
      }

   }

   public static synchronized void removeTransientAndPersistentIdentity(ServerIdentity id) {
      identityMap.remove(id.getTransientIdentity());
      ServerIdentity oldId = (ServerIdentity)identityMap.get(id.getPersistentIdentity());
      if (oldId != null && id.equals(oldId) && id.getTransientIdentity().equals(oldId.getTransientIdentity())) {
         identityMap.remove(id.getPersistentIdentity());
         String domainName = id.getDomainName();
         String serverName = id.getServerName();
         if (domainName != null && serverName != null) {
            HashSet serverList = (HashSet)domainServerMap.get(domainName);
            if (serverList != null) {
               serverList.remove(serverName);
            }
         }
      }

   }

   static synchronized String[] getConnectedServers(String domainName) {
      if (domainName == null) {
         return null;
      } else {
         HashSet serverList = (HashSet)domainServerMap.get(domainName);
         if (serverList == null) {
            return null;
         } else {
            String[] array = new String[serverList.size()];
            serverList.toArray(array);
            return array;
         }
      }
   }

   static synchronized boolean isConnected(String serverName, String domainName) {
      if (serverName != null && domainName != null) {
         HashSet serverList = (HashSet)domainServerMap.get(domainName);
         return serverList != null && serverList.contains(serverName);
      } else {
         return false;
      }
   }

   private static class CompositeKey {
      private final String domainName;
      private final String serverName;
      private int hash;

      private CompositeKey(String dName, String sName) {
         this.hash = -1;
         this.domainName = dName;
         this.serverName = sName;
      }

      public int hashCode() {
         if (this.hash == -1) {
            int result = this.domainName != null ? this.domainName.hashCode() : 0;
            this.hash = 31 * result + (this.serverName != null ? this.serverName.hashCode() : 0);
         }

         return this.hash;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof CompositeKey)) {
            return false;
         } else {
            boolean var10000;
            label43: {
               label29: {
                  CompositeKey other = (CompositeKey)obj;
                  if (this.domainName != null) {
                     if (!this.domainName.equals(other.domainName)) {
                        break label29;
                     }
                  } else if (other.domainName != null) {
                     break label29;
                  }

                  if (this.serverName != null) {
                     if (this.serverName.equals(other.serverName)) {
                        break label43;
                     }
                  } else if (other.serverName == null) {
                     break label43;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      // $FF: synthetic method
      CompositeKey(String x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class Initializer {
      private static final boolean checkInitialized = ServerIdentityManager.recordIdentity(LocalServerIdentity.getIdentity());
   }
}
