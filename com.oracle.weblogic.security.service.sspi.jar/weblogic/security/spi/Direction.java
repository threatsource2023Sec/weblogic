package weblogic.security.spi;

public final class Direction {
   private int value;
   private String name = null;
   public static final Direction ONCE = new Direction(1);
   public static final Direction PRIOR = new Direction(2);
   public static final Direction POST = new Direction(3);

   private Direction(int value) {
      this.value = value;
      if (1 == value) {
         this.name = "ONCE";
      } else if (2 == value) {
         this.name = "PRIOR";
      } else if (3 == value) {
         this.name = "POST";
      }

   }

   public String toString() {
      return this.name;
   }
}
