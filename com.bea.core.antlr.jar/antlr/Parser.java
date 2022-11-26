package antlr;

import antlr.collections.AST;
import antlr.collections.impl.BitSet;
import antlr.debug.MessageListener;
import antlr.debug.ParserListener;
import antlr.debug.ParserMatchListener;
import antlr.debug.ParserTokenListener;
import antlr.debug.SemanticPredicateListener;
import antlr.debug.SyntacticPredicateListener;
import antlr.debug.TraceListener;
import java.util.Hashtable;

public abstract class Parser {
   protected ParserSharedInputState inputState;
   protected String[] tokenNames;
   protected AST returnAST;
   protected ASTFactory astFactory;
   protected Hashtable tokenTypeToASTClassMap;
   private boolean ignoreInvalidDebugCalls;
   protected int traceDepth;

   public Parser() {
      this(new ParserSharedInputState());
   }

   public Parser(ParserSharedInputState var1) {
      this.astFactory = null;
      this.tokenTypeToASTClassMap = null;
      this.ignoreInvalidDebugCalls = false;
      this.traceDepth = 0;
      this.inputState = var1;
   }

   public Hashtable getTokenTypeToASTClassMap() {
      return this.tokenTypeToASTClassMap;
   }

   public void addMessageListener(MessageListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addMessageListener() is only valid if parser built for debugging");
      }
   }

   public void addParserListener(ParserListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addParserListener() is only valid if parser built for debugging");
      }
   }

   public void addParserMatchListener(ParserMatchListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addParserMatchListener() is only valid if parser built for debugging");
      }
   }

   public void addParserTokenListener(ParserTokenListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addParserTokenListener() is only valid if parser built for debugging");
      }
   }

   public void addSemanticPredicateListener(SemanticPredicateListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addSemanticPredicateListener() is only valid if parser built for debugging");
      }
   }

   public void addSyntacticPredicateListener(SyntacticPredicateListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addSyntacticPredicateListener() is only valid if parser built for debugging");
      }
   }

   public void addTraceListener(TraceListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("addTraceListener() is only valid if parser built for debugging");
      }
   }

   public abstract void consume() throws TokenStreamException;

   public void consumeUntil(int var1) throws TokenStreamException {
      while(this.LA(1) != 1 && this.LA(1) != var1) {
         this.consume();
      }

   }

   public void consumeUntil(BitSet var1) throws TokenStreamException {
      while(this.LA(1) != 1 && !var1.member(this.LA(1))) {
         this.consume();
      }

   }

   protected void defaultDebuggingSetup(TokenStream var1, TokenBuffer var2) {
   }

   public AST getAST() {
      return this.returnAST;
   }

   public ASTFactory getASTFactory() {
      return this.astFactory;
   }

   public String getFilename() {
      return this.inputState.filename;
   }

   public ParserSharedInputState getInputState() {
      return this.inputState;
   }

   public void setInputState(ParserSharedInputState var1) {
      this.inputState = var1;
   }

   public String getTokenName(int var1) {
      return this.tokenNames[var1];
   }

   public String[] getTokenNames() {
      return this.tokenNames;
   }

   public boolean isDebugMode() {
      return false;
   }

   public abstract int LA(int var1) throws TokenStreamException;

   public abstract Token LT(int var1) throws TokenStreamException;

   public int mark() {
      return this.inputState.input.mark();
   }

   public void match(int var1) throws MismatchedTokenException, TokenStreamException {
      if (this.LA(1) != var1) {
         throw new MismatchedTokenException(this.tokenNames, this.LT(1), var1, false, this.getFilename());
      } else {
         this.consume();
      }
   }

   public void match(BitSet var1) throws MismatchedTokenException, TokenStreamException {
      if (!var1.member(this.LA(1))) {
         throw new MismatchedTokenException(this.tokenNames, this.LT(1), var1, false, this.getFilename());
      } else {
         this.consume();
      }
   }

   public void matchNot(int var1) throws MismatchedTokenException, TokenStreamException {
      if (this.LA(1) == var1) {
         throw new MismatchedTokenException(this.tokenNames, this.LT(1), var1, true, this.getFilename());
      } else {
         this.consume();
      }
   }

   /** @deprecated */
   public static void panic() {
      System.err.println("Parser: panic");
      System.exit(1);
   }

   public void removeMessageListener(MessageListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new RuntimeException("removeMessageListener() is only valid if parser built for debugging");
      }
   }

   public void removeParserListener(ParserListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new RuntimeException("removeParserListener() is only valid if parser built for debugging");
      }
   }

   public void removeParserMatchListener(ParserMatchListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new RuntimeException("removeParserMatchListener() is only valid if parser built for debugging");
      }
   }

   public void removeParserTokenListener(ParserTokenListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new RuntimeException("removeParserTokenListener() is only valid if parser built for debugging");
      }
   }

   public void removeSemanticPredicateListener(SemanticPredicateListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("removeSemanticPredicateListener() is only valid if parser built for debugging");
      }
   }

   public void removeSyntacticPredicateListener(SyntacticPredicateListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new IllegalArgumentException("removeSyntacticPredicateListener() is only valid if parser built for debugging");
      }
   }

   public void removeTraceListener(TraceListener var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new RuntimeException("removeTraceListener() is only valid if parser built for debugging");
      }
   }

   public void reportError(RecognitionException var1) {
      System.err.println(var1);
   }

   public void reportError(String var1) {
      if (this.getFilename() == null) {
         System.err.println("error: " + var1);
      } else {
         System.err.println(this.getFilename() + ": error: " + var1);
      }

   }

   public void reportWarning(String var1) {
      if (this.getFilename() == null) {
         System.err.println("warning: " + var1);
      } else {
         System.err.println(this.getFilename() + ": warning: " + var1);
      }

   }

   public void recover(RecognitionException var1, BitSet var2) throws TokenStreamException {
      this.consume();
      this.consumeUntil(var2);
   }

   public void rewind(int var1) {
      this.inputState.input.rewind(var1);
   }

   public void setASTFactory(ASTFactory var1) {
      this.astFactory = var1;
   }

   public void setASTNodeClass(String var1) {
      this.astFactory.setASTNodeType(var1);
   }

   /** @deprecated */
   public void setASTNodeType(String var1) {
      this.setASTNodeClass(var1);
   }

   public void setDebugMode(boolean var1) {
      if (!this.ignoreInvalidDebugCalls) {
         throw new RuntimeException("setDebugMode() only valid if parser built for debugging");
      }
   }

   public void setFilename(String var1) {
      this.inputState.filename = var1;
   }

   public void setIgnoreInvalidDebugCalls(boolean var1) {
      this.ignoreInvalidDebugCalls = var1;
   }

   public void setTokenBuffer(TokenBuffer var1) {
      this.inputState.input = var1;
   }

   public void traceIndent() {
      for(int var1 = 0; var1 < this.traceDepth; ++var1) {
         System.out.print(" ");
      }

   }

   public void traceIn(String var1) throws TokenStreamException {
      ++this.traceDepth;
      this.traceIndent();
      System.out.println("> " + var1 + "; LA(1)==" + this.LT(1).getText() + (this.inputState.guessing > 0 ? " [guessing]" : ""));
   }

   public void traceOut(String var1) throws TokenStreamException {
      this.traceIndent();
      System.out.println("< " + var1 + "; LA(1)==" + this.LT(1).getText() + (this.inputState.guessing > 0 ? " [guessing]" : ""));
      --this.traceDepth;
   }
}
