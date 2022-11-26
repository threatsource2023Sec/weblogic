package antlr;

import antlr.collections.impl.Vector;
import java.util.Hashtable;

public class RuleBlock extends AlternativeBlock {
   protected String ruleName;
   protected String argAction;
   protected String throwsSpec;
   protected String returnAction;
   protected RuleEndElement endNode;
   protected boolean testLiterals;
   Vector labeledElements;
   protected boolean[] lock;
   protected Lookahead[] cache;
   Hashtable exceptionSpecs;
   protected boolean defaultErrorHandler;
   protected String ignoreRule;

   public RuleBlock(Grammar var1, String var2) {
      super(var1);
      this.argAction = null;
      this.throwsSpec = null;
      this.returnAction = null;
      this.testLiterals = false;
      this.defaultErrorHandler = true;
      this.ignoreRule = null;
      this.ruleName = var2;
      this.labeledElements = new Vector();
      this.cache = new Lookahead[var1.maxk + 1];
      this.exceptionSpecs = new Hashtable();
      this.setAutoGen(var1 instanceof ParserGrammar);
   }

   public RuleBlock(Grammar var1, String var2, int var3, boolean var4) {
      this(var1, var2);
      this.line = var3;
      this.setAutoGen(var4);
   }

   public void addExceptionSpec(ExceptionSpec var1) {
      if (this.findExceptionSpec(var1.label) != null) {
         if (var1.label != null) {
            this.grammar.antlrTool.error("Rule '" + this.ruleName + "' already has an exception handler for label: " + var1.label);
         } else {
            this.grammar.antlrTool.error("Rule '" + this.ruleName + "' already has an exception handler");
         }
      } else {
         this.exceptionSpecs.put(var1.label == null ? "" : var1.label.getText(), var1);
      }

   }

   public ExceptionSpec findExceptionSpec(Token var1) {
      return (ExceptionSpec)this.exceptionSpecs.get(var1 == null ? "" : var1.getText());
   }

   public ExceptionSpec findExceptionSpec(String var1) {
      return (ExceptionSpec)this.exceptionSpecs.get(var1 == null ? "" : var1);
   }

   public void generate() {
      this.grammar.generator.gen((AlternativeBlock)this);
   }

   public boolean getDefaultErrorHandler() {
      return this.defaultErrorHandler;
   }

   public RuleEndElement getEndElement() {
      return this.endNode;
   }

   public String getIgnoreRule() {
      return this.ignoreRule;
   }

   public String getRuleName() {
      return this.ruleName;
   }

   public boolean getTestLiterals() {
      return this.testLiterals;
   }

   public boolean isLexerAutoGenRule() {
      return this.ruleName.equals("nextToken");
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public void prepareForAnalysis() {
      super.prepareForAnalysis();
      this.lock = new boolean[this.grammar.maxk + 1];
   }

   public void setDefaultErrorHandler(boolean var1) {
      this.defaultErrorHandler = var1;
   }

   public void setEndElement(RuleEndElement var1) {
      this.endNode = var1;
   }

   public void setOption(Token var1, Token var2) {
      if (var1.getText().equals("defaultErrorHandler")) {
         if (var2.getText().equals("true")) {
            this.defaultErrorHandler = true;
         } else if (var2.getText().equals("false")) {
            this.defaultErrorHandler = false;
         } else {
            this.grammar.antlrTool.error("Value for defaultErrorHandler must be true or false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else if (var1.getText().equals("testLiterals")) {
         if (!(this.grammar instanceof LexerGrammar)) {
            this.grammar.antlrTool.error("testLiterals option only valid for lexer rules", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else if (var2.getText().equals("true")) {
            this.testLiterals = true;
         } else if (var2.getText().equals("false")) {
            this.testLiterals = false;
         } else {
            this.grammar.antlrTool.error("Value for testLiterals must be true or false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else if (var1.getText().equals("ignore")) {
         if (!(this.grammar instanceof LexerGrammar)) {
            this.grammar.antlrTool.error("ignore option only valid for lexer rules", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            this.ignoreRule = var2.getText();
         }
      } else if (var1.getText().equals("paraphrase")) {
         if (!(this.grammar instanceof LexerGrammar)) {
            this.grammar.antlrTool.error("paraphrase option only valid for lexer rules", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            TokenSymbol var3 = this.grammar.tokenManager.getTokenSymbol(this.ruleName);
            if (var3 == null) {
               this.grammar.antlrTool.panic("cannot find token associated with rule " + this.ruleName);
            }

            var3.setParaphrase(var2.getText());
         }
      } else if (var1.getText().equals("generateAmbigWarnings")) {
         if (var2.getText().equals("true")) {
            this.generateAmbigWarnings = true;
         } else if (var2.getText().equals("false")) {
            this.generateAmbigWarnings = false;
         } else {
            this.grammar.antlrTool.error("Value for generateAmbigWarnings must be true or false", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else {
         this.grammar.antlrTool.error("Invalid rule option: " + var1.getText(), this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }

   }

   public String toString() {
      String var1 = " FOLLOW={";
      Lookahead[] var2 = this.endNode.cache;
      int var3 = this.grammar.maxk;
      boolean var4 = true;

      for(int var5 = 1; var5 <= var3; ++var5) {
         if (var2[var5] != null) {
            var1 = var1 + var2[var5].toString(",", this.grammar.tokenManager.getVocabulary());
            var4 = false;
            if (var5 < var3 && var2[var5 + 1] != null) {
               var1 = var1 + ";";
            }
         }
      }

      var1 = var1 + "}";
      if (var4) {
         var1 = "";
      }

      return this.ruleName + ": " + super.toString() + " ;" + var1;
   }
}
