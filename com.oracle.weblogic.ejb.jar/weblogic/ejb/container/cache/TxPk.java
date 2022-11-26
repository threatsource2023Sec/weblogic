package weblogic.ejb.container.cache;

import javax.transaction.Transaction;

public final class TxPk {
   private static final boolean debug = false;
   private final Transaction tx;
   private final Object pk;
   private final int hashCode;

   public TxPk(Transaction tx, Object pk) {
      this.tx = tx;
      this.pk = pk;
      this.hashCode = tx.hashCode() ^ pk.hashCode();
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
      } else if (!(o instanceof TxPk)) {
         return false;
      } else {
         TxPk other = (TxPk)o;
         return this.hashCode == other.hashCode && eq(this.tx, other.tx) && eq(this.pk, other.pk);
      }
   }

   public Transaction getTx() {
      return this.tx;
   }

   public Object getPk() {
      return this.pk;
   }

   public String toString() {
      return "(tx=" + this.tx + ", pk=" + this.pk + ")";
   }
}
