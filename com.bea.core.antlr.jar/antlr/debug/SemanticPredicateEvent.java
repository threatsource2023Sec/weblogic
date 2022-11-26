package antlr.debug;

public class SemanticPredicateEvent extends GuessingEvent {
   public static final int VALIDATING = 0;
   public static final int PREDICTING = 1;
   private int condition;
   private boolean result;

   public SemanticPredicateEvent(Object var1) {
      super(var1);
   }

   public SemanticPredicateEvent(Object var1, int var2) {
      super(var1, var2);
   }

   public int getCondition() {
      return this.condition;
   }

   public boolean getResult() {
      return this.result;
   }

   void setCondition(int var1) {
      this.condition = var1;
   }

   void setResult(boolean var1) {
      this.result = var1;
   }

   void setValues(int var1, int var2, boolean var3, int var4) {
      super.setValues(var1, var4);
      this.setCondition(var2);
      this.setResult(var3);
   }

   public String toString() {
      return "SemanticPredicateEvent [" + this.getCondition() + "," + this.getResult() + "," + this.getGuessing() + "]";
   }
}
