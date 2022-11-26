package weblogic.cache.webapp;

import java.io.Serializable;
import java.util.NoSuchElementException;
import weblogic.cache.CacheException;

public class KeySet implements Serializable, Comparable {
   private transient CacheSystem cs;
   private transient StringBuffer sb = new StringBuffer();
   private String key;
   public static final char KEY_SEPARATOR = '\u0000';

   public KeySet(CacheSystem cs) {
      this.cs = cs;
   }

   public Object addKey(String scope, String key) throws CacheException {
      if (this.cs == null) {
         throw new CacheException("You cannot add more keys once you have called getKey()");
      } else {
         try {
            Object value = this.cs.getValueFromScope(scope, key);
            this.sb.append('\u0000');
            this.sb.append(value == null ? "" : value.toString());
            return value;
         } catch (NoSuchElementException var4) {
            throw new CacheException("Invalid key/keyScope attribute, key = " + key);
         }
      }
   }

   public String getKey() {
      String var1;
      try {
         var1 = this.key == null ? (this.key = this.sb.toString()) : this.key;
      } finally {
         this.cs = null;
         this.sb = null;
      }

      return var1;
   }

   public boolean equals(Object o) {
      if (o instanceof KeySet) {
         KeySet keySet = (KeySet)o;
         return keySet.getKey().equals(this.getKey());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.key.hashCode();
   }

   public int compareTo(Object o) {
      if (o instanceof KeySet) {
         KeySet keySet = (KeySet)o;
         return this.getKey().compareTo(keySet.getKey());
      } else {
         return -1;
      }
   }
}
