package antlr;

import antlr.collections.AST;
import antlr.collections.impl.BitSet;

public class TreeParser {
   public static ASTNULLType ASTNULL = new ASTNULLType();
   protected AST _retTree;
   protected TreeParserSharedInputState inputState = new TreeParserSharedInputState();
   protected String[] tokenNames;
   protected AST returnAST;
   protected ASTFactory astFactory = new ASTFactory();
   protected int traceDepth = 0;

   public AST getAST() {
      return this.returnAST;
   }

   public ASTFactory getASTFactory() {
      return this.astFactory;
   }

   public String getTokenName(int var1) {
      return this.tokenNames[var1];
   }

   public String[] getTokenNames() {
      return this.tokenNames;
   }

   protected void match(AST var1, int var2) throws MismatchedTokenException {
      if (var1 == null || var1 == ASTNULL || var1.getType() != var2) {
         throw new MismatchedTokenException(this.getTokenNames(), var1, var2, false);
      }
   }

   public void match(AST var1, BitSet var2) throws MismatchedTokenException {
      if (var1 == null || var1 == ASTNULL || !var2.member(var1.getType())) {
         throw new MismatchedTokenException(this.getTokenNames(), var1, var2, false);
      }
   }

   protected void matchNot(AST var1, int var2) throws MismatchedTokenException {
      if (var1 == null || var1 == ASTNULL || var1.getType() == var2) {
         throw new MismatchedTokenException(this.getTokenNames(), var1, var2, true);
      }
   }

   /** @deprecated */
   public static void panic() {
      System.err.println("TreeWalker: panic");
      Utils.error("");
   }

   public void reportError(RecognitionException var1) {
      System.err.println(var1.toString());
   }

   public void reportError(String var1) {
      System.err.println("error: " + var1);
   }

   public void reportWarning(String var1) {
      System.err.println("warning: " + var1);
   }

   public void setASTFactory(ASTFactory var1) {
      this.astFactory = var1;
   }

   /** @deprecated */
   public void setASTNodeType(String var1) {
      this.setASTNodeClass(var1);
   }

   public void setASTNodeClass(String var1) {
      this.astFactory.setASTNodeType(var1);
   }

   public void traceIndent() {
      for(int var1 = 0; var1 < this.traceDepth; ++var1) {
         System.out.print(" ");
      }

   }

   public void traceIn(String var1, AST var2) {
      ++this.traceDepth;
      this.traceIndent();
      System.out.println("> " + var1 + "(" + (var2 != null ? var2.toString() : "null") + ")" + (this.inputState.guessing > 0 ? " [guessing]" : ""));
   }

   public void traceOut(String var1, AST var2) {
      this.traceIndent();
      System.out.println("< " + var1 + "(" + (var2 != null ? var2.toString() : "null") + ")" + (this.inputState.guessing > 0 ? " [guessing]" : ""));
      --this.traceDepth;
   }
}
