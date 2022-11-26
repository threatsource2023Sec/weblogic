package weblogic.cache.lld;

import java.io.IOException;
import java.util.Map;
import weblogic.cluster.GroupMessage;
import weblogic.rmi.spi.HostID;

public class LDDGroupMessageUpdater extends LLDGroupMessageInvalidator {
   private static final boolean VERBOSE = true;

   public LDDGroupMessageUpdater(String name, Map m) {
      super(name, m);
   }

   public void onUpdate(CacheEntry entry, Object oldValue) {
      System.out.println("Sending update of: " + entry.getKey());
      this.sendPutMessage(entry.getKey(), entry.getValue());
   }

   public void onCreate(CacheEntry entry) {
      System.out.println("Sending create of: " + entry.getKey());
      this.sendPutMessage(entry.getKey(), entry.getValue());
   }

   private void sendPutMessage(Object key, Object value) {
      try {
         GroupMessage cm = new PutMessage(this.cacheName, key, value);
         this.multicastSession.send(cm);
      } catch (IOException var4) {
         LLDLogger.logMessageException("" + key, var4);
      }

   }

   protected static class PutMessage extends LLDGroupMessageInvalidator.RemoveMessage {
      private final Object value;

      PutMessage(String cn, Object key, Object value) {
         super(cn, key);
         this.value = value;
      }

      public void execute(HostID id) {
         System.out.println("Putting entry: " + this.key);
         Map m = this.getLocalMap();
         System.out.println("got map: " + m);
         if (m != null) {
            m.put(this.key, this.value);
         }
      }
   }
}
