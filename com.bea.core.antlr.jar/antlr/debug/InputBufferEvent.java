package antlr.debug;

public class InputBufferEvent extends Event {
   char c;
   int lookaheadAmount;
   public static final int CONSUME = 0;
   public static final int LA = 1;
   public static final int MARK = 2;
   public static final int REWIND = 3;

   public InputBufferEvent(Object var1) {
      super(var1);
   }

   public InputBufferEvent(Object var1, int var2, char var3, int var4) {
      super(var1);
      this.setValues(var2, var3, var4);
   }

   public char getChar() {
      return this.c;
   }

   public int getLookaheadAmount() {
      return this.lookaheadAmount;
   }

   void setChar(char var1) {
      this.c = var1;
   }

   void setLookaheadAmount(int var1) {
      this.lookaheadAmount = var1;
   }

   void setValues(int var1, char var2, int var3) {
      super.setValues(var1);
      this.setChar(var2);
      this.setLookaheadAmount(var3);
   }

   public String toString() {
      return "CharBufferEvent [" + (this.getType() == 0 ? "CONSUME, " : "LA, ") + this.getChar() + "," + this.getLookaheadAmount() + "]";
   }
}
