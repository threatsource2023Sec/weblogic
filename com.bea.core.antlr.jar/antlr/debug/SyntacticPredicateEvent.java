package antlr.debug;

public class SyntacticPredicateEvent extends GuessingEvent {
   public SyntacticPredicateEvent(Object var1) {
      super(var1);
   }

   public SyntacticPredicateEvent(Object var1, int var2) {
      super(var1, var2);
   }

   void setValues(int var1, int var2) {
      super.setValues(var1, var2);
   }

   public String toString() {
      return "SyntacticPredicateEvent [" + this.getGuessing() + "]";
   }
}
