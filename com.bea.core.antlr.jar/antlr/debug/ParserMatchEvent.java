package antlr.debug;

public class ParserMatchEvent extends GuessingEvent {
   public static int TOKEN = 0;
   public static int BITSET = 1;
   public static int CHAR = 2;
   public static int CHAR_BITSET = 3;
   public static int STRING = 4;
   public static int CHAR_RANGE = 5;
   private boolean inverse;
   private boolean matched;
   private Object target;
   private int value;
   private String text;

   public ParserMatchEvent(Object var1) {
      super(var1);
   }

   public ParserMatchEvent(Object var1, int var2, int var3, Object var4, String var5, int var6, boolean var7, boolean var8) {
      super(var1);
      this.setValues(var2, var3, var4, var5, var6, var7, var8);
   }

   public Object getTarget() {
      return this.target;
   }

   public String getText() {
      return this.text;
   }

   public int getValue() {
      return this.value;
   }

   public boolean isInverse() {
      return this.inverse;
   }

   public boolean isMatched() {
      return this.matched;
   }

   void setInverse(boolean var1) {
      this.inverse = var1;
   }

   void setMatched(boolean var1) {
      this.matched = var1;
   }

   void setTarget(Object var1) {
      this.target = var1;
   }

   void setText(String var1) {
      this.text = var1;
   }

   void setValue(int var1) {
      this.value = var1;
   }

   void setValues(int var1, int var2, Object var3, String var4, int var5, boolean var6, boolean var7) {
      super.setValues(var1, var5);
      this.setValue(var2);
      this.setTarget(var3);
      this.setInverse(var6);
      this.setMatched(var7);
      this.setText(var4);
   }

   public String toString() {
      return "ParserMatchEvent [" + (this.isMatched() ? "ok," : "bad,") + (this.isInverse() ? "NOT " : "") + (this.getType() == TOKEN ? "token," : "bitset,") + this.getValue() + "," + this.getTarget() + "," + this.getGuessing() + "]";
   }
}
