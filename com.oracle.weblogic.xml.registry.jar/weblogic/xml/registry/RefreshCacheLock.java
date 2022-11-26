package weblogic.xml.registry;

import java.util.HashSet;
import java.util.Set;

public class RefreshCacheLock {
   private int capacity;
   private Set[] containers;

   public RefreshCacheLock() {
      this(1);
   }

   public RefreshCacheLock(int concurrentCapacity) {
      this.capacity = 0;
      this.containers = null;
      if (concurrentCapacity < 1) {
         throw new IllegalArgumentException("concurrent capacity must be great than 0");
      } else {
         this.capacity = concurrentCapacity;
         this.containers = new Set[this.capacity];

         for(int i = 0; i < this.containers.length; ++i) {
            this.containers[i] = new HashSet();
         }

      }
   }

   public void lock(String publicId, String systemId) {
      Key key = this.createKey(publicId, systemId);
      Set container = this.containers[Math.abs(key.hashCode() % this.capacity)];
      synchronized(container) {
         while(container.contains(key)) {
            try {
               container.wait();
            } catch (InterruptedException var8) {
            }
         }

         if (!container.contains(key)) {
            container.add(key);
         }

      }
   }

   public void unlock(String publicId, String systemId) {
      Key key = this.createKey(publicId, systemId);
      Set container = this.containers[Math.abs(key.hashCode() % this.capacity)];
      synchronized(container) {
         if (container.contains(key)) {
            container.remove(key);
         }

         container.notifyAll();
      }
   }

   private Key createKey(String publicId, String systemId) {
      return new Key(publicId, systemId);
   }

   private class Key {
      private String publicId;
      private String systemId;

      public Key(String publicId, String systemId) {
         this.publicId = publicId;
         this.systemId = systemId;
      }

      public boolean equals(Object o) {
         if (!(o instanceof Key)) {
            return false;
         } else if (this == o) {
            return true;
         } else {
            Key key = (Key)o;
            return (this.publicId == key.publicId || this.publicId != null && this.publicId.equals(key.publicId)) && (this.systemId == key.systemId || this.systemId != null && this.systemId.equals(key.systemId));
         }
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.publicId == null ? 0 : this.publicId.hashCode());
         result = 31 * result + (this.systemId == null ? 0 : this.systemId.hashCode());
         return result;
      }
   }
}
