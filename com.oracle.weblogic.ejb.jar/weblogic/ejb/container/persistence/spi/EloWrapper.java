package weblogic.ejb.container.persistence.spi;

import javax.ejb.EJBLocalObject;

public final class EloWrapper {
   private EJBLocalObject elo = null;

   public EloWrapper(EJBLocalObject elo) {
      if (elo == null) {
         throw new IllegalArgumentException();
      } else {
         this.elo = elo;
      }
   }

   public EJBLocalObject getEJBLocalObject() {
      return this.elo;
   }

   public Object getPrimaryKey() {
      return this.elo.getPrimaryKey();
   }

   public boolean equals(Object o) {
      if (!(o instanceof EloWrapper)) {
         return false;
      } else {
         EloWrapper other = (EloWrapper)o;
         return this.getPrimaryKey().equals(other.getPrimaryKey());
      }
   }

   public int hashCode() {
      return this.getPrimaryKey().hashCode();
   }
}
