package antlr.debug;

public class NewLineEvent extends Event {
   private int line;

   public NewLineEvent(Object var1) {
      super(var1);
   }

   public NewLineEvent(Object var1, int var2) {
      super(var1);
      this.setValues(var2);
   }

   public int getLine() {
      return this.line;
   }

   void setLine(int var1) {
      this.line = var1;
   }

   void setValues(int var1) {
      this.setLine(var1);
   }

   public String toString() {
      return "NewLineEvent [" + this.line + "]";
   }
}
