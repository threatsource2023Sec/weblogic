package antlr.debug;

public class MessageEvent extends Event {
   private String text;
   public static int WARNING = 0;
   public static int ERROR = 1;

   public MessageEvent(Object var1) {
      super(var1);
   }

   public MessageEvent(Object var1, int var2, String var3) {
      super(var1);
      this.setValues(var2, var3);
   }

   public String getText() {
      return this.text;
   }

   void setText(String var1) {
      this.text = var1;
   }

   void setValues(int var1, String var2) {
      super.setValues(var1);
      this.setText(var2);
   }

   public String toString() {
      return "ParserMessageEvent [" + (this.getType() == WARNING ? "warning," : "error,") + this.getText() + "]";
   }
}
