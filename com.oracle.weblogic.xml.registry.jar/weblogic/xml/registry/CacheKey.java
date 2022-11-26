package weblogic.xml.registry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

class CacheKey implements Serializable {
   static final long serialVersionUID = 1L;
   String registry = null;
   String publicKey = null;
   String systemKey = null;
   transient int hash = 0;

   public CacheKey(String registry, String publicKey, String systemKey) {
      this.registry = registry.intern();
      if (publicKey != null) {
         this.publicKey = publicKey.intern();
         this.hash = publicKey.hashCode();
      } else if (systemKey != null) {
         this.systemKey = systemKey.intern();
         this.hash = systemKey.hashCode();
      }

   }

   public String toString() {
      if (this.publicKey != null) {
         return this.registry + ":PID=" + this.publicKey;
      } else {
         return this.systemKey != null ? this.registry + ":SID=" + this.systemKey : "UNKNOWN";
      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof CacheKey)) {
         return false;
      } else {
         CacheKey candidate = (CacheKey)obj;
         if (this.registry != candidate.registry) {
            return false;
         } else if (this.publicKey != null && this.publicKey == candidate.publicKey) {
            return true;
         } else {
            return this.systemKey != null && this.systemKey == candidate.systemKey;
         }
      }
   }

   public int hashCode() {
      return this.hash;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.registry = this.registry.intern();
      if (this.publicKey != null) {
         this.publicKey = this.publicKey.intern();
         this.hash = this.publicKey.hashCode();
      } else if (this.systemKey != null) {
         this.systemKey = this.systemKey.intern();
         this.hash = this.systemKey.hashCode();
      }

   }
}
