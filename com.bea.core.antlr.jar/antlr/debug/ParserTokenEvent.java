package antlr.debug;

public class ParserTokenEvent extends Event {
   private int value;
   private int amount;
   public static int LA = 0;
   public static int CONSUME = 1;

   public ParserTokenEvent(Object var1) {
      super(var1);
   }

   public ParserTokenEvent(Object var1, int var2, int var3, int var4) {
      super(var1);
      this.setValues(var2, var3, var4);
   }

   public int getAmount() {
      return this.amount;
   }

   public int getValue() {
      return this.value;
   }

   void setAmount(int var1) {
      this.amount = var1;
   }

   void setValue(int var1) {
      this.value = var1;
   }

   void setValues(int var1, int var2, int var3) {
      super.setValues(var1);
      this.setAmount(var2);
      this.setValue(var3);
   }

   public String toString() {
      return this.getType() == LA ? "ParserTokenEvent [LA," + this.getAmount() + "," + this.getValue() + "]" : "ParserTokenEvent [consume,1," + this.getValue() + "]";
   }
}
