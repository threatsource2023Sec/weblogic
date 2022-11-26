package antlr.debug;

public class TraceEvent extends GuessingEvent {
   private int ruleNum;
   private int data;
   public static int ENTER = 0;
   public static int EXIT = 1;
   public static int DONE_PARSING = 2;

   public TraceEvent(Object var1) {
      super(var1);
   }

   public TraceEvent(Object var1, int var2, int var3, int var4, int var5) {
      super(var1);
      this.setValues(var2, var3, var4, var5);
   }

   public int getData() {
      return this.data;
   }

   public int getRuleNum() {
      return this.ruleNum;
   }

   void setData(int var1) {
      this.data = var1;
   }

   void setRuleNum(int var1) {
      this.ruleNum = var1;
   }

   void setValues(int var1, int var2, int var3, int var4) {
      super.setValues(var1, var3);
      this.setRuleNum(var2);
      this.setData(var4);
   }

   public String toString() {
      return "ParserTraceEvent [" + (this.getType() == ENTER ? "enter," : "exit,") + this.getRuleNum() + "," + this.getGuessing() + "]";
   }
}
