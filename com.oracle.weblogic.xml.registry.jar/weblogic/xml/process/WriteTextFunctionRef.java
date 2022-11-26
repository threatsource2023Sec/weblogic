package weblogic.xml.process;

public final class WriteTextFunctionRef extends FunctionRef {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private String text;
   private String fromVar;

   public WriteTextFunctionRef(String textVal) {
      super("WRITE_TEXT");
      this.text = textVal;
   }

   public void setFromVar(String val) {
      this.fromVar = val;
   }

   public String getFromVar() {
      return this.fromVar;
   }

   public String getText() {
      return this.text;
   }
}
