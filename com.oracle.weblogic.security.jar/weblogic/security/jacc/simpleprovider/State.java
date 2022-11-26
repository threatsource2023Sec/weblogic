package weblogic.security.jacc.simpleprovider;

public final class State {
   private int value;
   private String name = null;
   public static final State OPEN = new State(1);
   public static final State DELETED = new State(2);
   public static final State INSERVICE = new State(3);

   private State(int value) {
      this.value = value;
      if (1 == value) {
         this.name = "open";
      } else if (2 == value) {
         this.name = "deleted";
      } else if (3 == value) {
         this.name = "inService";
      }

   }

   public String toString() {
      return this.name;
   }
}
