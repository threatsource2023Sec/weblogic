package antlr;

abstract class GrammarElement {
   public static final int AUTO_GEN_NONE = 1;
   public static final int AUTO_GEN_CARET = 2;
   public static final int AUTO_GEN_BANG = 3;
   protected Grammar grammar;
   protected int line;
   protected int column;

   public GrammarElement(Grammar var1) {
      this.grammar = var1;
      this.line = -1;
      this.column = -1;
   }

   public GrammarElement(Grammar var1, Token var2) {
      this.grammar = var1;
      this.line = var2.getLine();
      this.column = var2.getColumn();
   }

   public void generate() {
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return this.column;
   }

   public Lookahead look(int var1) {
      return null;
   }

   public abstract String toString();
}
