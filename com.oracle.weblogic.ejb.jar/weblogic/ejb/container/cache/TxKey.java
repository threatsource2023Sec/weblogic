package weblogic.ejb.container.cache;

import javax.transaction.Transaction;

public final class TxKey {
   private static final boolean debug = false;
   private final Transaction tx;
   private final CacheKey key;
   private final int hashCode;

   public TxKey(Transaction tx, CacheKey key) {
      this.tx = tx;
      this.key = key;
      this.hashCode = tx.hashCode() ^ key.hashCode();
   }

   public int hashCode() {
      return this.hashCode;
   }

   private static boolean eq(Object a, Object b) {
      return a == b || a.equals(b);
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TxKey)) {
         return false;
      } else {
         TxKey other = (TxKey)o;
         return this.hashCode == other.hashCode && eq(this.tx, other.tx) && eq(this.key, other.key);
      }
   }

   public Transaction getTx() {
      return this.tx;
   }

   public CacheKey getKey() {
      return this.key;
   }

   public String toString() {
      return "(tx=" + this.tx + ", key=" + this.key + ")";
   }
}
