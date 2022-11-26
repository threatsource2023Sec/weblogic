package antlr;

public class LLkParser extends Parser {
   int k;

   public LLkParser(int var1) {
      this.k = var1;
   }

   public LLkParser(ParserSharedInputState var1, int var2) {
      super(var1);
      this.k = var2;
   }

   public LLkParser(TokenBuffer var1, int var2) {
      this.k = var2;
      this.setTokenBuffer(var1);
   }

   public LLkParser(TokenStream var1, int var2) {
      this.k = var2;
      TokenBuffer var3 = new TokenBuffer(var1);
      this.setTokenBuffer(var3);
   }

   public void consume() throws TokenStreamException {
      this.inputState.input.consume();
   }

   public int LA(int var1) throws TokenStreamException {
      return this.inputState.input.LA(var1);
   }

   public Token LT(int var1) throws TokenStreamException {
      return this.inputState.input.LT(var1);
   }

   private void trace(String var1, String var2) throws TokenStreamException {
      this.traceIndent();
      System.out.print(var1 + var2 + (this.inputState.guessing > 0 ? "; [guessing]" : "; "));

      for(int var3 = 1; var3 <= this.k; ++var3) {
         if (var3 != 1) {
            System.out.print(", ");
         }

         if (this.LT(var3) != null) {
            System.out.print("LA(" + var3 + ")==" + this.LT(var3).getText());
         } else {
            System.out.print("LA(" + var3 + ")==null");
         }
      }

      System.out.println("");
   }

   public void traceIn(String var1) throws TokenStreamException {
      ++this.traceDepth;
      this.trace("> ", var1);
   }

   public void traceOut(String var1) throws TokenStreamException {
      this.trace("< ", var1);
      --this.traceDepth;
   }
}
