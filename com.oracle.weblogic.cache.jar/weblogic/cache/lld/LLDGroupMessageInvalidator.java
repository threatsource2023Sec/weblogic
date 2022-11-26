package weblogic.cache.lld;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.GroupMessage;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.rmi.spi.HostID;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class LLDGroupMessageInvalidator implements ChangeListener {
   private static final boolean VERBOSE = false;
   private static final Map caches = new ConcurrentWeakHashMap();
   protected final String cacheName;
   protected final MulticastSession multicastSession;

   public LLDGroupMessageInvalidator(String name, Map m) {
      this.cacheName = name;
      caches.put(this.cacheName, m);
      ClusterServices cs = Locator.locateClusterServices();
      if (cs == null) {
         throw new RuntimeException("This server is not in a cluster.");
      } else {
         this.multicastSession = cs.createMulticastSession(new CacheStateRecoverListener(), -1, false);
      }
   }

   public void onCreate(CacheEntry entry) {
   }

   public void onUpdate(CacheEntry entry, Object oldValue) {
   }

   public void onDelete(CacheEntry entry) {
      try {
         GroupMessage cm = new RemoveMessage(this.cacheName, entry.getKey());
         this.multicastSession.send(cm);
      } catch (IOException var3) {
         LLDLogger.logMessageException("" + entry.getKey(), var3);
      }

   }

   public void onClear() {
      try {
         GroupMessage cm = new ClearMessage(this.cacheName);
         this.multicastSession.send(cm);
      } catch (IOException var2) {
         LLDLogger.logMessageException("<all keys>", var2);
      }

   }

   protected class CacheStateRecoverListener implements RecoverListener {
      public GroupMessage createRecoverMessage() {
         return new ClearMessage(LLDGroupMessageInvalidator.this.cacheName);
      }
   }

   protected static class ClearMessage extends Message {
      ClearMessage(String cn) {
         super(cn);
      }

      public void execute(HostID id) {
         Map m = this.getLocalMap();
         if (m != null) {
            m.clear();
         }
      }
   }

   protected static class RemoveMessage extends Message {
      protected final Object key;

      RemoveMessage(String cn, Object k) {
         super(cn);
         this.key = k;
      }

      public void execute(HostID id) {
         Map m = this.getLocalMap();
         if (m != null) {
            m.remove(this.key);
         }
      }
   }

   protected abstract static class Message implements Serializable, GroupMessage {
      protected final String cacheName;

      protected Message(String cn) {
         this.cacheName = cn;
      }

      protected Map getLocalMap() {
         return (Map)LLDGroupMessageInvalidator.caches.get(this.cacheName);
      }

      public abstract void execute(HostID var1);
   }
}
