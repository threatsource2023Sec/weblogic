package antlr;

import antlr.collections.AST;

public class NoViableAltException extends RecognitionException {
   public Token token;
   public AST node;

   public NoViableAltException(AST var1) {
      super("NoViableAlt", "<AST>", var1.getLine(), var1.getColumn());
      this.node = var1;
   }

   public NoViableAltException(Token var1, String var2) {
      super("NoViableAlt", var2, var1.getLine(), var1.getColumn());
      this.token = var1;
   }

   public String getMessage() {
      if (this.token != null) {
         return "unexpected token: " + this.token.getText();
      } else {
         return this.node == TreeParser.ASTNULL ? "unexpected end of subtree" : "unexpected AST node: " + this.node.toString();
      }
   }
}
