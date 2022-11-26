package antlr.debug;

public abstract class GuessingEvent extends Event {
   private int guessing;

   public GuessingEvent(Object var1) {
      super(var1);
   }

   public GuessingEvent(Object var1, int var2) {
      super(var1, var2);
   }

   public int getGuessing() {
      return this.guessing;
   }

   void setGuessing(int var1) {
      this.guessing = var1;
   }

   void setValues(int var1, int var2) {
      super.setValues(var1);
      this.setGuessing(var2);
   }
}
