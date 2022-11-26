package antlr;

import antlr.actions.java.ActionLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class JavaCodeGenerator extends CodeGenerator {
   public static final int NO_MAPPING = -999;
   public static final int CONTINUE_LAST_MAPPING = -888;
   private JavaCodeGeneratorPrintWriterManager printWriterManager;
   private int defaultLine = -999;
   protected int syntacticPredLevel = 0;
   protected boolean genAST = false;
   protected boolean saveText = false;
   String labeledElementType;
   String labeledElementASTType;
   String labeledElementInit;
   String commonExtraArgs;
   String commonExtraParams;
   String commonLocalVars;
   String lt1Value;
   String exceptionThrown;
   String throwNoViable;
   RuleBlock currentRule;
   String currentASTResult;
   Hashtable treeVariableMap = new Hashtable();
   Hashtable declaredASTVariables = new Hashtable();
   int astVarNumber = 1;
   protected static final String NONUNIQUE = new String();
   public static final int caseSizeThreshold = 127;
   private Vector semPreds;

   public JavaCodeGenerator() {
      this.charFormatter = new JavaCharFormatter();
   }

   protected void printAction(String var1) {
      this.printAction(var1, this.defaultLine);
   }

   protected void printAction(String var1, int var2) {
      this.getPrintWriterManager().startMapping(var2);
      super.printAction(var1);
      this.getPrintWriterManager().endMapping();
   }

   public void println(String var1) {
      this.println(var1, this.defaultLine);
   }

   public void println(String var1, int var2) {
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().startSingleSourceLineMapping(var2);
      }

      super.println(var1);
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().endMapping();
      }

   }

   protected void print(String var1) {
      this.print(var1, this.defaultLine);
   }

   protected void print(String var1, int var2) {
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().startMapping(var2);
      }

      super.print(var1);
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().endMapping();
      }

   }

   protected void _print(String var1) {
      this._print(var1, this.defaultLine);
   }

   protected void _print(String var1, int var2) {
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().startMapping(var2);
      }

      super._print(var1);
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().endMapping();
      }

   }

   protected void _println(String var1) {
      this._println(var1, this.defaultLine);
   }

   protected void _println(String var1, int var2) {
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().startMapping(var2);
      }

      super._println(var1);
      if (var2 > 0 || var2 == -888) {
         this.getPrintWriterManager().endMapping();
      }

   }

   protected int addSemPred(String var1) {
      this.semPreds.appendElement(var1);
      return this.semPreds.size() - 1;
   }

   public void exitIfError() {
      if (this.antlrTool.hasError()) {
         this.antlrTool.fatalError("Exiting due to errors.");
      }

   }

   public void gen() {
      try {
         Enumeration var1 = this.behavior.grammars.elements();

         while(var1.hasMoreElements()) {
            Grammar var2 = (Grammar)var1.nextElement();
            var2.setGrammarAnalyzer(this.analyzer);
            var2.setCodeGenerator(this);
            this.analyzer.setGrammar(var2);
            this.setupGrammarParameters(var2);
            var2.generate();
            this.exitIfError();
         }

         for(Enumeration var5 = this.behavior.tokenManagers.elements(); var5.hasMoreElements(); this.exitIfError()) {
            TokenManager var3 = (TokenManager)var5.nextElement();
            if (!var3.isReadOnly()) {
               this.genTokenTypes(var3);
               this.genTokenInterchange(var3);
            }
         }
      } catch (IOException var4) {
         this.antlrTool.reportException(var4, (String)null);
      }

   }

   public void gen(ActionElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("genAction(" + var1 + ")");
         }

         if (var1.isSemPred) {
            this.genSemPred(var1.actionText, var1.line);
         } else {
            if (this.grammar.hasSyntacticPredicate) {
               this.println("if ( inputState.guessing==0 ) {");
               ++this.tabs;
            }

            ActionTransInfo var3 = new ActionTransInfo();
            String var4 = this.processActionForSpecialSymbols(var1.actionText, var1.getLine(), this.currentRule, var3);
            if (var3.refRuleRoot != null) {
               this.println(var3.refRuleRoot + " = (" + this.labeledElementASTType + ")currentAST.root;");
            }

            this.printAction(var4);
            if (var3.assignToRoot) {
               this.println("currentAST.root = " + var3.refRuleRoot + ";");
               this.println("currentAST.child = " + var3.refRuleRoot + "!=null &&" + var3.refRuleRoot + ".getFirstChild()!=null ?", -999);
               ++this.tabs;
               this.println(var3.refRuleRoot + ".getFirstChild() : " + var3.refRuleRoot + ";");
               --this.tabs;
               this.println("currentAST.advanceChildToEnd();");
            }

            if (this.grammar.hasSyntacticPredicate) {
               --this.tabs;
               this.println("}", -999);
            }
         }
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(AlternativeBlock var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen(" + var1 + ")");
      }

      this.println("{", -999);
      this.genBlockPreamble(var1);
      this.genBlockInitAction(var1);
      String var2 = this.currentASTResult;
      if (var1.getLabel() != null) {
         this.currentASTResult = var1.getLabel();
      }

      this.grammar.theLLkAnalyzer.deterministic(var1);
      JavaBlockFinishingInfo var4 = this.genCommonBlock(var1, true);
      this.genBlockFinish(var4, this.throwNoViable, var1.getLine());
      this.println("}", -999);
      this.currentASTResult = var2;
   }

   public void gen(BlockEndElement var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genRuleEnd(" + var1 + ")");
      }

   }

   public void gen(CharLiteralElement var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genChar(" + var1 + ")");
      }

      if (var1.getLabel() != null) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";", var1.getLine());
      }

      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      this.genMatch((GrammarAtom)var1);
      this.saveText = var2;
   }

   public void gen(CharRangeElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
            this.println(var1.getLabel() + " = " + this.lt1Value + ";");
         }

         boolean var3 = this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3);
         if (var3) {
            this.println("_saveIndex=text.length();");
         }

         this.println("matchRange(" + var1.beginText + "," + var1.endText + ");");
         if (var3) {
            this.println("text.setLength(_saveIndex);");
         }
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(LexerGrammar var1) throws IOException {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = -999;
         if (var1.debuggingOutput) {
            this.semPreds = new Vector();
         }

         this.setGrammar(var1);
         if (!(this.grammar instanceof LexerGrammar)) {
            this.antlrTool.panic("Internal error generating lexer");
         }

         this.currentOutput = this.getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
         this.genAST = false;
         this.saveText = true;
         this.tabs = 0;
         this.genHeader();

         try {
            this.defaultLine = this.behavior.getHeaderActionLine("");
            this.println(this.behavior.getHeaderAction(""));
         } finally {
            this.defaultLine = -999;
         }

         this.println("import java.io.InputStream;");
         this.println("import antlr.TokenStreamException;");
         this.println("import antlr.TokenStreamIOException;");
         this.println("import antlr.TokenStreamRecognitionException;");
         this.println("import antlr.CharStreamException;");
         this.println("import antlr.CharStreamIOException;");
         this.println("import antlr.ANTLRException;");
         this.println("import java.io.Reader;");
         this.println("import java.util.Hashtable;");
         this.println("import antlr." + this.grammar.getSuperClass() + ";");
         this.println("import antlr.InputBuffer;");
         this.println("import antlr.ByteBuffer;");
         this.println("import antlr.CharBuffer;");
         this.println("import antlr.Token;");
         this.println("import antlr.CommonToken;");
         this.println("import antlr.RecognitionException;");
         this.println("import antlr.NoViableAltForCharException;");
         this.println("import antlr.MismatchedCharException;");
         this.println("import antlr.TokenStream;");
         this.println("import antlr.ANTLRHashString;");
         this.println("import antlr.LexerSharedInputState;");
         this.println("import antlr.collections.impl.BitSet;");
         this.println("import antlr.SemanticException;");
         this.println(this.grammar.preambleAction.getText());
         String var3 = null;
         if (this.grammar.superClass != null) {
            var3 = this.grammar.superClass;
         } else {
            var3 = "antlr." + this.grammar.getSuperClass();
         }

         if (this.grammar.comment != null) {
            this._println(this.grammar.comment);
         }

         String var4 = "public";
         Token var5 = (Token)this.grammar.options.get("classHeaderPrefix");
         if (var5 != null) {
            String var6 = StringUtils.stripFrontBack(var5.getText(), "\"", "\"");
            if (var6 != null) {
               var4 = var6;
            }
         }

         this.print(var4 + " ");
         this.print("class " + this.grammar.getClassName() + " extends " + var3);
         this.println(" implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ", TokenStream");
         Token var18 = (Token)this.grammar.options.get("classHeaderSuffix");
         if (var18 != null) {
            String var7 = StringUtils.stripFrontBack(var18.getText(), "\"", "\"");
            if (var7 != null) {
               this.print(", " + var7);
            }
         }

         this.println(" {");
         this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null), this.grammar.classMemberAction.getLine());
         this.println("public " + this.grammar.getClassName() + "(InputStream in) {");
         ++this.tabs;
         this.println("this(new ByteBuffer(in));");
         --this.tabs;
         this.println("}");
         this.println("public " + this.grammar.getClassName() + "(Reader in) {");
         ++this.tabs;
         this.println("this(new CharBuffer(in));");
         --this.tabs;
         this.println("}");
         this.println("public " + this.grammar.getClassName() + "(InputBuffer ib) {");
         ++this.tabs;
         if (this.grammar.debuggingOutput) {
            this.println("this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)));");
         } else {
            this.println("this(new LexerSharedInputState(ib));");
         }

         --this.tabs;
         this.println("}");
         this.println("public " + this.grammar.getClassName() + "(LexerSharedInputState state) {");
         ++this.tabs;
         this.println("super(state);");
         if (this.grammar.debuggingOutput) {
            this.println("  ruleNames  = _ruleNames;");
            this.println("  semPredNames = _semPredNames;");
            this.println("  setupDebugging();");
         }

         this.println("caseSensitiveLiterals = " + var1.caseSensitiveLiterals + ";");
         this.println("setCaseSensitive(" + var1.caseSensitive + ");");
         this.println("literals = new Hashtable();");
         Enumeration var19 = this.grammar.tokenManager.getTokenSymbolKeys();

         while(var19.hasMoreElements()) {
            String var8 = (String)var19.nextElement();
            if (var8.charAt(0) == '"') {
               TokenSymbol var9 = this.grammar.tokenManager.getTokenSymbol(var8);
               if (var9 instanceof StringLiteralSymbol) {
                  StringLiteralSymbol var10 = (StringLiteralSymbol)var9;
                  this.println("literals.put(new ANTLRHashString(" + var10.getId() + ", this), new Integer(" + var10.getTokenType() + "));");
               }
            }
         }

         --this.tabs;
         this.println("}");
         Enumeration var20;
         if (this.grammar.debuggingOutput) {
            this.println("private static final String _ruleNames[] = {");
            var20 = this.grammar.rules.elements();
            boolean var21 = false;

            while(var20.hasMoreElements()) {
               GrammarSymbol var23 = (GrammarSymbol)var20.nextElement();
               if (var23 instanceof RuleSymbol) {
                  this.println("  \"" + ((RuleSymbol)var23).getId() + "\",");
               }
            }

            this.println("};");
         }

         this.genNextToken();
         var20 = this.grammar.rules.elements();

         for(int var22 = 0; var20.hasMoreElements(); this.exitIfError()) {
            RuleSymbol var24 = (RuleSymbol)var20.nextElement();
            if (!var24.getId().equals("mnextToken")) {
               this.genRule(var24, false, var22++);
            }
         }

         if (this.grammar.debuggingOutput) {
            this.genSemPredMap();
         }

         this.genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
         this.println("");
         this.println("}");
         this.getPrintWriterManager().finishOutput();
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(OneOrMoreBlock var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("gen+(" + var1 + ")");
         }

         this.println("{", -999);
         this.genBlockPreamble(var1);
         String var4;
         if (var1.getLabel() != null) {
            var4 = "_cnt_" + var1.getLabel();
         } else {
            var4 = "_cnt" + var1.ID;
         }

         this.println("int " + var4 + "=0;");
         String var3;
         if (var1.getLabel() != null) {
            var3 = var1.getLabel();
         } else {
            var3 = "_loop" + var1.ID;
         }

         this.println(var3 + ":");
         this.println("do {");
         ++this.tabs;
         this.genBlockInitAction(var1);
         String var5 = this.currentASTResult;
         if (var1.getLabel() != null) {
            this.currentASTResult = var1.getLabel();
         }

         this.grammar.theLLkAnalyzer.deterministic(var1);
         boolean var7 = false;
         int var8 = this.grammar.maxk;
         if (!var1.greedy && var1.exitLookaheadDepth <= this.grammar.maxk && var1.exitCache[var1.exitLookaheadDepth].containsEpsilon()) {
            var7 = true;
            var8 = var1.exitLookaheadDepth;
         } else if (!var1.greedy && var1.exitLookaheadDepth == Integer.MAX_VALUE) {
            var7 = true;
         }

         if (var7) {
            if (this.DEBUG_CODE_GENERATOR) {
               System.out.println("nongreedy (...)+ loop; exit depth is " + var1.exitLookaheadDepth);
            }

            String var9 = this.getLookaheadTestExpression(var1.exitCache, var8);
            this.println("// nongreedy exit test", -999);
            this.println("if ( " + var4 + ">=1 && " + var9 + ") break " + var3 + ";", -888);
         }

         JavaBlockFinishingInfo var13 = this.genCommonBlock(var1, false);
         this.genBlockFinish(var13, "if ( " + var4 + ">=1 ) { break " + var3 + "; } else {" + this.throwNoViable + "}", var1.getLine());
         this.println(var4 + "++;");
         --this.tabs;
         this.println("} while (true);");
         this.println("}");
         this.currentASTResult = var5;
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(ParserGrammar var1) throws IOException {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = -999;
         if (var1.debuggingOutput) {
            this.semPreds = new Vector();
         }

         this.setGrammar(var1);
         if (!(this.grammar instanceof ParserGrammar)) {
            this.antlrTool.panic("Internal error generating parser");
         }

         this.currentOutput = this.getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
         this.genAST = this.grammar.buildAST;
         this.tabs = 0;
         this.genHeader();

         try {
            this.defaultLine = this.behavior.getHeaderActionLine("");
            this.println(this.behavior.getHeaderAction(""));
         } finally {
            this.defaultLine = -999;
         }

         this.println("import antlr.TokenBuffer;");
         this.println("import antlr.TokenStreamException;");
         this.println("import antlr.TokenStreamIOException;");
         this.println("import antlr.ANTLRException;");
         this.println("import antlr." + this.grammar.getSuperClass() + ";");
         this.println("import antlr.Token;");
         this.println("import antlr.TokenStream;");
         this.println("import antlr.RecognitionException;");
         this.println("import antlr.NoViableAltException;");
         this.println("import antlr.MismatchedTokenException;");
         this.println("import antlr.SemanticException;");
         this.println("import antlr.ParserSharedInputState;");
         this.println("import antlr.collections.impl.BitSet;");
         if (this.genAST) {
            this.println("import antlr.collections.AST;");
            this.println("import java.util.Hashtable;");
            this.println("import antlr.ASTFactory;");
            this.println("import antlr.ASTPair;");
            this.println("import antlr.collections.impl.ASTArray;");
         }

         this.println(this.grammar.preambleAction.getText());
         String var3 = null;
         if (this.grammar.superClass != null) {
            var3 = this.grammar.superClass;
         } else {
            var3 = "antlr." + this.grammar.getSuperClass();
         }

         if (this.grammar.comment != null) {
            this._println(this.grammar.comment);
         }

         String var4 = "public";
         Token var5 = (Token)this.grammar.options.get("classHeaderPrefix");
         if (var5 != null) {
            String var6 = StringUtils.stripFrontBack(var5.getText(), "\"", "\"");
            if (var6 != null) {
               var4 = var6;
            }
         }

         this.print(var4 + " ");
         this.print("class " + this.grammar.getClassName() + " extends " + var3);
         this.println("       implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
         Token var18 = (Token)this.grammar.options.get("classHeaderSuffix");
         if (var18 != null) {
            String var7 = StringUtils.stripFrontBack(var18.getText(), "\"", "\"");
            if (var7 != null) {
               this.print(", " + var7);
            }
         }

         this.println(" {");
         GrammarSymbol var9;
         Enumeration var19;
         if (this.grammar.debuggingOutput) {
            this.println("private static final String _ruleNames[] = {");
            var19 = this.grammar.rules.elements();
            boolean var8 = false;

            while(var19.hasMoreElements()) {
               var9 = (GrammarSymbol)var19.nextElement();
               if (var9 instanceof RuleSymbol) {
                  this.println("  \"" + ((RuleSymbol)var9).getId() + "\",");
               }
            }

            this.println("};");
         }

         this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null), this.grammar.classMemberAction.getLine());
         this.println("");
         this.println("protected " + this.grammar.getClassName() + "(TokenBuffer tokenBuf, int k) {");
         this.println("  super(tokenBuf,k);");
         this.println("  tokenNames = _tokenNames;");
         if (this.grammar.debuggingOutput) {
            this.println("  ruleNames  = _ruleNames;");
            this.println("  semPredNames = _semPredNames;");
            this.println("  setupDebugging(tokenBuf);");
         }

         if (this.grammar.buildAST) {
            this.println("  buildTokenTypeASTClassMap();");
            this.println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
         }

         this.println("}");
         this.println("");
         this.println("public " + this.grammar.getClassName() + "(TokenBuffer tokenBuf) {");
         this.println("  this(tokenBuf," + this.grammar.maxk + ");");
         this.println("}");
         this.println("");
         this.println("protected " + this.grammar.getClassName() + "(TokenStream lexer, int k) {");
         this.println("  super(lexer,k);");
         this.println("  tokenNames = _tokenNames;");
         if (this.grammar.debuggingOutput) {
            this.println("  ruleNames  = _ruleNames;");
            this.println("  semPredNames = _semPredNames;");
            this.println("  setupDebugging(lexer);");
         }

         if (this.grammar.buildAST) {
            this.println("  buildTokenTypeASTClassMap();");
            this.println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
         }

         this.println("}");
         this.println("");
         this.println("public " + this.grammar.getClassName() + "(TokenStream lexer) {");
         this.println("  this(lexer," + this.grammar.maxk + ");");
         this.println("}");
         this.println("");
         this.println("public " + this.grammar.getClassName() + "(ParserSharedInputState state) {");
         this.println("  super(state," + this.grammar.maxk + ");");
         this.println("  tokenNames = _tokenNames;");
         if (this.grammar.buildAST) {
            this.println("  buildTokenTypeASTClassMap();");
            this.println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
         }

         this.println("}");
         this.println("");
         var19 = this.grammar.rules.elements();

         for(int var20 = 0; var19.hasMoreElements(); this.exitIfError()) {
            var9 = (GrammarSymbol)var19.nextElement();
            if (var9 instanceof RuleSymbol) {
               RuleSymbol var10 = (RuleSymbol)var9;
               this.genRule(var10, var10.references.size() == 0, var20++);
            }
         }

         this.genTokenStrings();
         if (this.grammar.buildAST) {
            this.genTokenASTNodeMap();
         }

         this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
         if (this.grammar.debuggingOutput) {
            this.genSemPredMap();
         }

         this.println("");
         this.println("}");
         this.getPrintWriterManager().finishOutput();
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(RuleRefElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("genRR(" + var1 + ")");
         }

         RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
         if (var3 == null || !var3.isDefined()) {
            this.antlrTool.error("Rule '" + var1.targetRule + "' is not defined", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            return;
         }

         if (!(var3 instanceof RuleSymbol)) {
            this.antlrTool.error("'" + var1.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            return;
         }

         this.genErrorTryForElement(var1);
         if (this.grammar instanceof TreeWalkerGrammar && var1.getLabel() != null && this.syntacticPredLevel == 0) {
            this.println(var1.getLabel() + " = _t==ASTNULL ? null : " + this.lt1Value + ";");
         }

         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("_saveIndex=text.length();");
         }

         this.printTabs();
         if (var1.idAssign != null) {
            if (var3.block.returnAction == null) {
               this.antlrTool.warning("Rule '" + var1.targetRule + "' has no return type", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            }

            this._print(var1.idAssign + "=");
         } else if (!(this.grammar instanceof LexerGrammar) && this.syntacticPredLevel == 0 && var3.block.returnAction != null) {
            this.antlrTool.warning("Rule '" + var1.targetRule + "' returns a value", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }

         this.GenRuleInvocation(var1);
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("text.setLength(_saveIndex);");
         }

         if (this.syntacticPredLevel == 0) {
            boolean var4 = this.grammar.hasSyntacticPredicate && (this.grammar.buildAST && var1.getLabel() != null || this.genAST && var1.getAutoGenType() == 1);
            if (var4) {
            }

            if (this.grammar.buildAST && var1.getLabel() != null) {
               this.println(var1.getLabel() + "_AST = (" + this.labeledElementASTType + ")returnAST;");
            }

            if (this.genAST) {
               switch (var1.getAutoGenType()) {
                  case 1:
                     this.println("astFactory.addASTChild(currentAST, returnAST);");
                     break;
                  case 2:
                     this.antlrTool.error("Internal: encountered ^ after rule reference");
               }
            }

            if (this.grammar instanceof LexerGrammar && var1.getLabel() != null) {
               this.println(var1.getLabel() + "=_returnToken;");
            }

            if (var4) {
            }
         }

         this.genErrorCatchForElement(var1);
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(StringLiteralElement var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genString(" + var1 + ")");
      }

      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";", var1.getLine());
      }

      this.genElementAST(var1);
      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      this.genMatch((GrammarAtom)var1);
      this.saveText = var2;
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling();", var1.getLine());
      }

   }

   public void gen(TokenRangeElement var1) {
      this.genErrorTryForElement(var1);
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";", var1.getLine());
      }

      this.genElementAST(var1);
      this.println("matchRange(" + var1.beginText + "," + var1.endText + ");", var1.getLine());
      this.genErrorCatchForElement(var1);
   }

   public void gen(TokenRefElement var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genTokenRef(" + var1 + ")");
      }

      if (this.grammar instanceof LexerGrammar) {
         this.antlrTool.panic("Token reference found in lexer");
      }

      this.genErrorTryForElement(var1);
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";", var1.getLine());
      }

      this.genElementAST(var1);
      this.genMatch((GrammarAtom)var1);
      this.genErrorCatchForElement(var1);
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling();", var1.getLine());
      }

   }

   public void gen(TreeElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         this.println("AST __t" + var1.ID + " = _t;");
         if (var1.root.getLabel() != null) {
            this.println(var1.root.getLabel() + " = _t==ASTNULL ? null :(" + this.labeledElementASTType + ")_t;", var1.root.getLine());
         }

         if (var1.root.getAutoGenType() == 3) {
            this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            var1.root.setAutoGenType(1);
         }

         if (var1.root.getAutoGenType() == 2) {
            this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            var1.root.setAutoGenType(1);
         }

         this.genElementAST(var1.root);
         if (this.grammar.buildAST) {
            this.println("ASTPair __currentAST" + var1.ID + " = currentAST.copy();");
            this.println("currentAST.root = currentAST.child;");
            this.println("currentAST.child = null;");
         }

         if (var1.root instanceof WildcardElement) {
            this.println("if ( _t==null ) throw new MismatchedTokenException();", var1.root.getLine());
         } else {
            this.genMatch(var1.root);
         }

         this.println("_t = _t.getFirstChild();");

         for(int var3 = 0; var3 < var1.getAlternatives().size(); ++var3) {
            Alternative var4 = var1.getAlternativeAt(var3);

            for(AlternativeElement var5 = var4.head; var5 != null; var5 = var5.next) {
               var5.generate();
            }
         }

         if (this.grammar.buildAST) {
            this.println("currentAST = __currentAST" + var1.ID + ";");
         }

         this.println("_t = __t" + var1.ID + ";");
         this.println("_t = _t.getNextSibling();");
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(TreeWalkerGrammar var1) throws IOException {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = -999;
         this.setGrammar(var1);
         if (!(this.grammar instanceof TreeWalkerGrammar)) {
            this.antlrTool.panic("Internal error generating tree-walker");
         }

         this.currentOutput = this.getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
         this.genAST = this.grammar.buildAST;
         this.tabs = 0;
         this.genHeader();

         try {
            this.defaultLine = this.behavior.getHeaderActionLine("");
            this.println(this.behavior.getHeaderAction(""));
         } finally {
            this.defaultLine = -999;
         }

         this.println("import antlr." + this.grammar.getSuperClass() + ";");
         this.println("import antlr.Token;");
         this.println("import antlr.collections.AST;");
         this.println("import antlr.RecognitionException;");
         this.println("import antlr.ANTLRException;");
         this.println("import antlr.NoViableAltException;");
         this.println("import antlr.MismatchedTokenException;");
         this.println("import antlr.SemanticException;");
         this.println("import antlr.collections.impl.BitSet;");
         this.println("import antlr.ASTPair;");
         this.println("import antlr.collections.impl.ASTArray;");
         this.println(this.grammar.preambleAction.getText());
         String var3 = null;
         if (this.grammar.superClass != null) {
            var3 = this.grammar.superClass;
         } else {
            var3 = "antlr." + this.grammar.getSuperClass();
         }

         this.println("");
         if (this.grammar.comment != null) {
            this._println(this.grammar.comment);
         }

         String var4 = "public";
         Token var5 = (Token)this.grammar.options.get("classHeaderPrefix");
         if (var5 != null) {
            String var6 = StringUtils.stripFrontBack(var5.getText(), "\"", "\"");
            if (var6 != null) {
               var4 = var6;
            }
         }

         this.print(var4 + " ");
         this.print("class " + this.grammar.getClassName() + " extends " + var3);
         this.println("       implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
         Token var19 = (Token)this.grammar.options.get("classHeaderSuffix");
         if (var19 != null) {
            String var7 = StringUtils.stripFrontBack(var19.getText(), "\"", "\"");
            if (var7 != null) {
               this.print(", " + var7);
            }
         }

         this.println(" {");
         this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null), this.grammar.classMemberAction.getLine());
         this.println("public " + this.grammar.getClassName() + "() {");
         ++this.tabs;
         this.println("tokenNames = _tokenNames;");
         --this.tabs;
         this.println("}");
         this.println("");
         Enumeration var20 = this.grammar.rules.elements();
         int var8 = 0;

         for(String var9 = ""; var20.hasMoreElements(); this.exitIfError()) {
            GrammarSymbol var10 = (GrammarSymbol)var20.nextElement();
            if (var10 instanceof RuleSymbol) {
               RuleSymbol var11 = (RuleSymbol)var10;
               this.genRule(var11, var11.references.size() == 0, var8++);
            }
         }

         this.genTokenStrings();
         this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
         this.println("}");
         this.println("");
         this.getPrintWriterManager().finishOutput();
      } finally {
         this.defaultLine = var2;
      }
   }

   public void gen(WildcardElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
            this.println(var1.getLabel() + " = " + this.lt1Value + ";");
         }

         this.genElementAST(var1);
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("if ( _t==null ) throw new MismatchedTokenException();");
         } else if (!(this.grammar instanceof LexerGrammar)) {
            this.println("matchNot(" + this.getValueString(1) + ");");
         } else {
            if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
               this.println("_saveIndex=text.length();");
            }

            this.println("matchNot(EOF_CHAR);");
            if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
               this.println("text.setLength(_saveIndex);");
            }
         }

         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("_t = _t.getNextSibling();");
         }
      } finally {
         this.defaultLine = var2;
      }

   }

   public void gen(ZeroOrMoreBlock var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("gen*(" + var1 + ")");
         }

         this.println("{");
         this.genBlockPreamble(var1);
         String var3;
         if (var1.getLabel() != null) {
            var3 = var1.getLabel();
         } else {
            var3 = "_loop" + var1.ID;
         }

         this.println(var3 + ":");
         this.println("do {");
         ++this.tabs;
         this.genBlockInitAction(var1);
         String var4 = this.currentASTResult;
         if (var1.getLabel() != null) {
            this.currentASTResult = var1.getLabel();
         }

         this.grammar.theLLkAnalyzer.deterministic(var1);
         boolean var6 = false;
         int var7 = this.grammar.maxk;
         if (!var1.greedy && var1.exitLookaheadDepth <= this.grammar.maxk && var1.exitCache[var1.exitLookaheadDepth].containsEpsilon()) {
            var6 = true;
            var7 = var1.exitLookaheadDepth;
         } else if (!var1.greedy && var1.exitLookaheadDepth == Integer.MAX_VALUE) {
            var6 = true;
         }

         if (var6) {
            if (this.DEBUG_CODE_GENERATOR) {
               System.out.println("nongreedy (...)* loop; exit depth is " + var1.exitLookaheadDepth);
            }

            String var8 = this.getLookaheadTestExpression(var1.exitCache, var7);
            this.println("// nongreedy exit test");
            this.println("if (" + var8 + ") break " + var3 + ";");
         }

         JavaBlockFinishingInfo var12 = this.genCommonBlock(var1, false);
         this.genBlockFinish(var12, "break " + var3 + ";", var1.getLine());
         --this.tabs;
         this.println("} while (true);");
         this.println("}");
         this.currentASTResult = var4;
      } finally {
         this.defaultLine = var2;
      }

   }

   protected void genAlt(Alternative var1, AlternativeBlock var2) {
      boolean var3 = this.genAST;
      this.genAST = this.genAST && var1.getAutoGen();
      boolean var4 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGen();
      Hashtable var5 = this.treeVariableMap;
      this.treeVariableMap = new Hashtable();
      if (var1.exceptionSpec != null) {
         this.println("try {      // for error handling", var1.head.getLine());
         ++this.tabs;
      }

      for(AlternativeElement var6 = var1.head; !(var6 instanceof BlockEndElement); var6 = var6.next) {
         var6.generate();
      }

      if (this.genAST) {
         if (var2 instanceof RuleBlock) {
            RuleBlock var7 = (RuleBlock)var2;
            if (this.grammar.hasSyntacticPredicate) {
            }

            this.println(var7.getRuleName() + "_AST = (" + this.labeledElementASTType + ")currentAST.root;", -888);
            if (this.grammar.hasSyntacticPredicate) {
            }
         } else if (var2.getLabel() != null) {
            this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }
      }

      if (var1.exceptionSpec != null) {
         --this.tabs;
         this.println("}", -999);
         this.genErrorHandler(var1.exceptionSpec);
      }

      this.genAST = var3;
      this.saveText = var4;
      this.treeVariableMap = var5;
   }

   protected void genBitsets(Vector var1, int var2) {
      this.println("", -999);

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         BitSet var4 = (BitSet)var1.elementAt(var3);
         var4.growToInclude(var2);
         this.genBitSet(var4, var3);
      }

   }

   private void genBitSet(BitSet var1, int var2) {
      int var3 = this.defaultLine;

      try {
         this.defaultLine = -999;
         this.println("private static final long[] mk" + this.getBitsetName(var2) + "() {");
         int var4 = var1.lengthInLongWords();
         if (var4 < 8) {
            this.println("\tlong[] data = { " + var1.toStringOfWords() + "};");
         } else {
            this.println("\tlong[] data = new long[" + var4 + "];");
            long[] var5 = var1.toPackedArray();
            int var6 = 0;

            label100:
            while(true) {
               while(true) {
                  if (var6 >= var5.length) {
                     break label100;
                  }

                  if (var5[var6] == 0L) {
                     ++var6;
                  } else if (var6 + 1 != var5.length && var5[var6] == var5[var6 + 1]) {
                     int var7;
                     for(var7 = var6 + 1; var7 < var5.length && var5[var7] == var5[var6]; ++var7) {
                     }

                     this.println("\tfor (int i = " + var6 + "; i<=" + (var7 - 1) + "; i++) { data[i]=" + var5[var6] + "L; }");
                     var6 = var7;
                  } else {
                     this.println("\tdata[" + var6 + "]=" + var5[var6] + "L;");
                     ++var6;
                  }
               }
            }
         }

         this.println("\treturn data;");
         this.println("}");
         this.println("public static final BitSet " + this.getBitsetName(var2) + " = new BitSet(" + "mk" + this.getBitsetName(var2) + "()" + ");");
      } finally {
         this.defaultLine = var3;
      }

   }

   private void genBlockFinish(JavaBlockFinishingInfo var1, String var2, int var3) {
      int var4 = this.defaultLine;

      try {
         this.defaultLine = var3;
         if (var1.needAnErrorClause && (var1.generatedAnIf || var1.generatedSwitch)) {
            if (var1.generatedAnIf) {
               this.println("else {");
            } else {
               this.println("{");
            }

            ++this.tabs;
            this.println(var2);
            --this.tabs;
            this.println("}");
         }

         if (var1.postscript != null) {
            this.println(var1.postscript);
         }
      } finally {
         this.defaultLine = var4;
      }

   }

   protected void genBlockInitAction(AlternativeBlock var1) {
      if (var1.initAction != null) {
         this.printAction(this.processActionForSpecialSymbols(var1.initAction, var1.getLine(), this.currentRule, (ActionTransInfo)null), var1.getLine());
      }

   }

   protected void genBlockPreamble(AlternativeBlock var1) {
      if (var1 instanceof RuleBlock) {
         RuleBlock var2 = (RuleBlock)var1;
         if (var2.labeledElements != null) {
            for(int var3 = 0; var3 < var2.labeledElements.size(); ++var3) {
               AlternativeElement var4 = (AlternativeElement)var2.labeledElements.elementAt(var3);
               int var5 = this.defaultLine;

               try {
                  this.defaultLine = var4.getLine();
                  if (var4 instanceof RuleRefElement || var4 instanceof AlternativeBlock && !(var4 instanceof RuleBlock) && !(var4 instanceof SynPredBlock)) {
                     if (!(var4 instanceof RuleRefElement) && ((AlternativeBlock)var4).not && this.analyzer.subruleCanBeInverted((AlternativeBlock)var4, this.grammar instanceof LexerGrammar)) {
                        this.println(this.labeledElementType + " " + var4.getLabel() + " = " + this.labeledElementInit + ";");
                        if (this.grammar.buildAST) {
                           this.genASTDeclaration(var4);
                        }
                     } else {
                        if (this.grammar.buildAST) {
                           this.genASTDeclaration(var4);
                        }

                        if (this.grammar instanceof LexerGrammar) {
                           this.println("Token " + var4.getLabel() + "=null;");
                        }

                        if (this.grammar instanceof TreeWalkerGrammar) {
                           this.println(this.labeledElementType + " " + var4.getLabel() + " = " + this.labeledElementInit + ";");
                        }
                     }
                  } else {
                     this.println(this.labeledElementType + " " + var4.getLabel() + " = " + this.labeledElementInit + ";");
                     if (this.grammar.buildAST) {
                        if (var4 instanceof GrammarAtom && ((GrammarAtom)var4).getASTNodeType() != null) {
                           GrammarAtom var6 = (GrammarAtom)var4;
                           this.genASTDeclaration(var4, var6.getASTNodeType());
                        } else {
                           this.genASTDeclaration(var4);
                        }
                     }
                  }
               } finally {
                  this.defaultLine = var5;
               }
            }
         }
      }

   }

   protected void genCases(BitSet var1, int var2) {
      int var3 = this.defaultLine;

      try {
         this.defaultLine = var2;
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("genCases(" + var1 + ")");
         }

         int[] var4 = var1.toArray();
         int var5 = this.grammar instanceof LexerGrammar ? 4 : 1;
         int var6 = 1;
         boolean var7 = true;

         for(int var8 = 0; var8 < var4.length; ++var8) {
            if (var6 == 1) {
               this.print("");
            } else {
               this._print("  ");
            }

            this._print("case " + this.getValueString(var4[var8]) + ":");
            if (var6 == var5) {
               this._println("");
               var7 = true;
               var6 = 1;
            } else {
               ++var6;
               var7 = false;
            }
         }

         if (!var7) {
            this._println("");
         }
      } finally {
         this.defaultLine = var3;
      }

   }

   public JavaBlockFinishingInfo genCommonBlock(AlternativeBlock var1, boolean var2) {
      int var3 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         int var4 = 0;
         boolean var5 = false;
         int var6 = 0;
         JavaBlockFinishingInfo var7 = new JavaBlockFinishingInfo();
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("genCommonBlock(" + var1 + ")");
         }

         boolean var8 = this.genAST;
         this.genAST = this.genAST && var1.getAutoGen();
         boolean var9 = this.saveText;
         this.saveText = this.saveText && var1.getAutoGen();
         String var30;
         if (var1.not && this.analyzer.subruleCanBeInverted(var1, this.grammar instanceof LexerGrammar)) {
            if (this.DEBUG_CODE_GENERATOR) {
               System.out.println("special case: ~(subrule)");
            }

            Lookahead var29 = this.analyzer.look(1, (AlternativeBlock)var1);
            if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
               this.println(var1.getLabel() + " = " + this.lt1Value + ";");
            }

            this.genElementAST(var1);
            var30 = "";
            if (this.grammar instanceof TreeWalkerGrammar) {
               var30 = "_t,";
            }

            this.println("match(" + var30 + this.getBitsetName(this.markBitsetForGen(var29.fset)) + ");");
            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println("_t = _t.getNextSibling();");
            }

            JavaBlockFinishingInfo var35 = var7;
            return var35;
         } else {
            if (var1.getAlternatives().size() == 1) {
               Alternative var10 = var1.getAlternativeAt(0);
               if (var10.synPred != null) {
                  this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), var1.getAlternativeAt(0).synPred.getLine(), var1.getAlternativeAt(0).synPred.getColumn());
               }

               if (var2) {
                  if (var10.semPred != null) {
                     this.genSemPred(var10.semPred, var1.line);
                  }

                  this.genAlt(var10, var1);
                  JavaBlockFinishingInfo var31 = var7;
                  return var31;
               }
            }

            int var28 = 0;

            int var11;
            for(var11 = 0; var11 < var1.getAlternatives().size(); ++var11) {
               Alternative var12 = var1.getAlternativeAt(var11);
               if (suitableForCaseExpression(var12)) {
                  ++var28;
               }
            }

            int var32;
            if (var28 >= this.makeSwitchThreshold) {
               var30 = this.lookaheadString(1);
               var5 = true;
               if (this.grammar instanceof TreeWalkerGrammar) {
                  this.println("if (_t==null) _t=ASTNULL;");
               }

               this.println("switch ( " + var30 + ") {");

               for(var32 = 0; var32 < var1.alternatives.size(); ++var32) {
                  Alternative var13 = var1.getAlternativeAt(var32);
                  if (suitableForCaseExpression(var13)) {
                     Lookahead var14 = var13.cache[1];
                     if (var14.fset.degree() == 0 && !var14.containsEpsilon()) {
                        this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), var13.head.getLine(), var13.head.getColumn());
                     } else {
                        this.genCases(var14.fset, var13.head.getLine());
                        this.println("{", var13.head.getLine());
                        ++this.tabs;
                        this.genAlt(var13, var1);
                        this.println("break;", -999);
                        --this.tabs;
                        this.println("}", -999);
                     }
                  }
               }

               this.println("default:");
               ++this.tabs;
            }

            var11 = this.grammar instanceof LexerGrammar ? this.grammar.maxk : 0;

            int var33;
            for(var32 = var11; var32 >= 0; --var32) {
               if (this.DEBUG_CODE_GENERATOR) {
                  System.out.println("checking depth " + var32);
               }

               for(var33 = 0; var33 < var1.alternatives.size(); ++var33) {
                  Alternative var36 = var1.getAlternativeAt(var33);
                  if (this.DEBUG_CODE_GENERATOR) {
                     System.out.println("genAlt: " + var33);
                  }

                  if (var5 && suitableForCaseExpression(var36)) {
                     if (this.DEBUG_CODE_GENERATOR) {
                        System.out.println("ignoring alt because it was in the switch");
                     }
                  } else {
                     boolean var16 = false;
                     String var15;
                     int var17;
                     if (this.grammar instanceof LexerGrammar) {
                        var17 = var36.lookaheadDepth;
                        if (var17 == Integer.MAX_VALUE) {
                           var17 = this.grammar.maxk;
                        }

                        while(var17 >= 1 && var36.cache[var17].containsEpsilon()) {
                           --var17;
                        }

                        if (var17 != var32) {
                           if (this.DEBUG_CODE_GENERATOR) {
                              System.out.println("ignoring alt because effectiveDepth!=altDepth;" + var17 + "!=" + var32);
                           }
                           continue;
                        }

                        var16 = this.lookaheadIsEmpty(var36, var17);
                        var15 = this.getLookaheadTestExpression(var36, var17);
                     } else {
                        var16 = this.lookaheadIsEmpty(var36, this.grammar.maxk);
                        var15 = this.getLookaheadTestExpression(var36, this.grammar.maxk);
                     }

                     var17 = this.defaultLine;

                     try {
                        this.defaultLine = var36.head.getLine();
                        if (var36.cache[1].fset.degree() > 127 && suitableForCaseExpression(var36)) {
                           if (var4 == 0) {
                              this.println("if " + var15 + " {");
                           } else {
                              this.println("else if " + var15 + " {");
                           }
                        } else if (var16 && var36.semPred == null && var36.synPred == null) {
                           if (var4 == 0) {
                              this.println("{");
                           } else {
                              this.println("else {");
                           }

                           var7.needAnErrorClause = false;
                        } else {
                           if (var36.semPred != null) {
                              ActionTransInfo var18 = new ActionTransInfo();
                              String var19 = this.processActionForSpecialSymbols(var36.semPred, var1.line, this.currentRule, var18);
                              if ((this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar) && this.grammar.debuggingOutput) {
                                 var15 = "(" + var15 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING," + this.addSemPred(this.charFormatter.escapeString(var19)) + "," + var19 + "))";
                              } else {
                                 var15 = "(" + var15 + "&&(" + var19 + "))";
                              }
                           }

                           if (var4 > 0) {
                              if (var36.synPred != null) {
                                 this.println("else {", var36.synPred.getLine());
                                 ++this.tabs;
                                 this.genSynPred(var36.synPred, var15);
                                 ++var6;
                              } else {
                                 this.println("else if " + var15 + " {");
                              }
                           } else if (var36.synPred != null) {
                              this.genSynPred(var36.synPred, var15);
                           } else {
                              if (this.grammar instanceof TreeWalkerGrammar) {
                                 this.println("if (_t==null) _t=ASTNULL;");
                              }

                              this.println("if " + var15 + " {");
                           }
                        }
                     } finally {
                        this.defaultLine = var17;
                     }

                     ++var4;
                     ++this.tabs;
                     this.genAlt(var36, var1);
                     --this.tabs;
                     this.println("}");
                  }
               }
            }

            String var34 = "";

            for(var33 = 1; var33 <= var6; ++var33) {
               var34 = var34 + "}";
            }

            this.genAST = var8;
            this.saveText = var9;
            if (var5) {
               --this.tabs;
               var7.postscript = var34 + "}";
               var7.generatedSwitch = true;
               var7.generatedAnIf = var4 > 0;
            } else {
               var7.postscript = var34;
               var7.generatedSwitch = false;
               var7.generatedAnIf = var4 > 0;
            }

            JavaBlockFinishingInfo var37 = var7;
            return var37;
         }
      } finally {
         this.defaultLine = var3;
      }
   }

   private static boolean suitableForCaseExpression(Alternative var0) {
      return var0.lookaheadDepth == 1 && var0.semPred == null && !var0.cache[1].containsEpsilon() && var0.cache[1].fset.degree() <= 127;
   }

   private void genElementAST(AlternativeElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (this.grammar instanceof TreeWalkerGrammar && !this.grammar.buildAST) {
            if (var1.getLabel() == null) {
               String var11 = this.lt1Value;
               String var12 = "tmp" + this.astVarNumber + "_AST";
               ++this.astVarNumber;
               this.mapTreeVariable(var1, var12);
               this.println(this.labeledElementASTType + " " + var12 + "_in = " + var11 + ";");
            }

            return;
         }

         if (this.grammar.buildAST && this.syntacticPredLevel == 0) {
            boolean var3 = this.genAST && (var1.getLabel() != null || var1.getAutoGenType() != 3);
            if (var1.getAutoGenType() != 3 && var1 instanceof TokenRefElement) {
               var3 = true;
            }

            boolean var4 = this.grammar.hasSyntacticPredicate && var3;
            String var5;
            String var6;
            if (var1.getLabel() != null) {
               var5 = var1.getLabel();
               var6 = var1.getLabel();
            } else {
               var5 = this.lt1Value;
               var6 = "tmp" + this.astVarNumber;
               ++this.astVarNumber;
            }

            if (var3) {
               if (var1 instanceof GrammarAtom) {
                  GrammarAtom var7 = (GrammarAtom)var1;
                  if (var7.getASTNodeType() != null) {
                     this.genASTDeclaration(var1, var6, var7.getASTNodeType());
                  } else {
                     this.genASTDeclaration(var1, var6, this.labeledElementASTType);
                  }
               } else {
                  this.genASTDeclaration(var1, var6, this.labeledElementASTType);
               }
            }

            String var13 = var6 + "_AST";
            this.mapTreeVariable(var1, var13);
            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println(this.labeledElementASTType + " " + var13 + "_in = null;");
            }

            if (var4) {
            }

            if (var1.getLabel() != null) {
               if (var1 instanceof GrammarAtom) {
                  this.println(var13 + " = " + this.getASTCreateString((GrammarAtom)var1, var5) + ";");
               } else {
                  this.println(var13 + " = " + this.getASTCreateString(var5) + ";");
               }
            }

            if (var1.getLabel() == null && var3) {
               var5 = this.lt1Value;
               if (var1 instanceof GrammarAtom) {
                  this.println(var13 + " = " + this.getASTCreateString((GrammarAtom)var1, var5) + ";");
               } else {
                  this.println(var13 + " = " + this.getASTCreateString(var5) + ";");
               }

               if (this.grammar instanceof TreeWalkerGrammar) {
                  this.println(var13 + "_in = " + var5 + ";");
               }
            }

            if (this.genAST) {
               switch (var1.getAutoGenType()) {
                  case 1:
                     this.println("astFactory.addASTChild(currentAST, " + var13 + ");");
                     break;
                  case 2:
                     this.println("astFactory.makeASTRoot(currentAST, " + var13 + ");");
               }
            }

            if (var4) {
            }
         }
      } finally {
         this.defaultLine = var2;
      }

   }

   private void genErrorCatchForElement(AlternativeElement var1) {
      if (var1.getLabel() != null) {
         String var2 = var1.enclosingRuleName;
         if (this.grammar instanceof LexerGrammar) {
            var2 = CodeGenerator.encodeLexerRuleName(var1.enclosingRuleName);
         }

         RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var2);
         if (var3 == null) {
            this.antlrTool.panic("Enclosing rule not found!");
         }

         ExceptionSpec var4 = var3.block.findExceptionSpec(var1.getLabel());
         if (var4 != null) {
            --this.tabs;
            this.println("}", var1.getLine());
            this.genErrorHandler(var4);
         }

      }
   }

   private void genErrorHandler(ExceptionSpec var1) {
      for(int var2 = 0; var2 < var1.handlers.size(); ++var2) {
         ExceptionHandler var3 = (ExceptionHandler)var1.handlers.elementAt(var2);
         int var4 = this.defaultLine;

         try {
            this.defaultLine = var3.action.getLine();
            this.println("catch (" + var3.exceptionTypeAndName.getText() + ") {", var3.exceptionTypeAndName.getLine());
            ++this.tabs;
            if (this.grammar.hasSyntacticPredicate) {
               this.println("if (inputState.guessing==0) {");
               ++this.tabs;
            }

            ActionTransInfo var5 = new ActionTransInfo();
            this.printAction(this.processActionForSpecialSymbols(var3.action.getText(), var3.action.getLine(), this.currentRule, var5));
            if (this.grammar.hasSyntacticPredicate) {
               --this.tabs;
               this.println("} else {");
               ++this.tabs;
               this.println("throw " + this.extractIdOfAction(var3.exceptionTypeAndName) + ";");
               --this.tabs;
               this.println("}");
            }

            --this.tabs;
            this.println("}");
         } finally {
            this.defaultLine = var4;
         }
      }

   }

   private void genErrorTryForElement(AlternativeElement var1) {
      if (var1.getLabel() != null) {
         String var2 = var1.enclosingRuleName;
         if (this.grammar instanceof LexerGrammar) {
            var2 = CodeGenerator.encodeLexerRuleName(var1.enclosingRuleName);
         }

         RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var2);
         if (var3 == null) {
            this.antlrTool.panic("Enclosing rule not found!");
         }

         ExceptionSpec var4 = var3.block.findExceptionSpec(var1.getLabel());
         if (var4 != null) {
            this.println("try { // for error handling", var1.getLine());
            ++this.tabs;
         }

      }
   }

   protected void genASTDeclaration(AlternativeElement var1) {
      this.genASTDeclaration(var1, this.labeledElementASTType);
   }

   protected void genASTDeclaration(AlternativeElement var1, String var2) {
      this.genASTDeclaration(var1, var1.getLabel(), var2);
   }

   protected void genASTDeclaration(AlternativeElement var1, String var2, String var3) {
      if (!this.declaredASTVariables.contains(var1)) {
         this.println(var3 + " " + var2 + "_AST = null;");
         this.declaredASTVariables.put(var1, var1);
      }
   }

   protected void genHeader() {
      this.println("// $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".java\"$", -999);
   }

   private void genLiteralsTest() {
      this.println("_ttype = testLiteralsTable(_ttype);");
   }

   private void genLiteralsTestForPartialToken() {
      this.println("_ttype = testLiteralsTable(new String(text.getBuffer(),_begin,text.length()-_begin),_ttype);");
   }

   protected void genMatch(BitSet var1) {
   }

   protected void genMatch(GrammarAtom var1) {
      if (var1 instanceof StringLiteralElement) {
         if (this.grammar instanceof LexerGrammar) {
            this.genMatchUsingAtomText(var1);
         } else {
            this.genMatchUsingAtomTokenType(var1);
         }
      } else if (var1 instanceof CharLiteralElement) {
         if (this.grammar instanceof LexerGrammar) {
            this.genMatchUsingAtomText(var1);
         } else {
            this.antlrTool.error("cannot ref character literals in grammar: " + var1);
         }
      } else if (var1 instanceof TokenRefElement) {
         this.genMatchUsingAtomText(var1);
      } else if (var1 instanceof WildcardElement) {
         this.gen((WildcardElement)var1);
      }

   }

   protected void genMatchUsingAtomText(GrammarAtom var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         String var3 = "";
         if (this.grammar instanceof TreeWalkerGrammar) {
            var3 = "_t,";
         }

         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("_saveIndex=text.length();");
         }

         this.print(var1.not ? "matchNot(" : "match(");
         this._print(var3, -999);
         if (var1.atomText.equals("EOF")) {
            this._print("Token.EOF_TYPE");
         } else {
            this._print(var1.atomText);
         }

         this._println(");");
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("text.setLength(_saveIndex);");
         }
      } finally {
         this.defaultLine = var2;
      }

   }

   protected void genMatchUsingAtomTokenType(GrammarAtom var1) {
      String var2 = "";
      if (this.grammar instanceof TreeWalkerGrammar) {
         var2 = "_t,";
      }

      Object var3 = null;
      String var4 = var2 + this.getValueString(var1.getType());
      this.println((var1.not ? "matchNot(" : "match(") + var4 + ");", var1.getLine());
   }

   public void genNextToken() {
      int var1 = this.defaultLine;

      try {
         this.defaultLine = -999;
         boolean var2 = false;

         RuleSymbol var4;
         for(int var3 = 0; var3 < this.grammar.rules.size(); ++var3) {
            var4 = (RuleSymbol)this.grammar.rules.elementAt(var3);
            if (var4.isDefined() && var4.access.equals("public")) {
               var2 = true;
               break;
            }
         }

         if (!var2) {
            this.println("");
            this.println("public Token nextToken() throws TokenStreamException {");
            this.println("\ttry {uponEOF();}");
            this.println("\tcatch(CharStreamIOException csioe) {");
            this.println("\t\tthrow new TokenStreamIOException(csioe.io);");
            this.println("\t}");
            this.println("\tcatch(CharStreamException cse) {");
            this.println("\t\tthrow new TokenStreamException(cse.getMessage());");
            this.println("\t}");
            this.println("\treturn new CommonToken(Token.EOF_TYPE, \"\");");
            this.println("}");
            this.println("");
            return;
         }

         RuleBlock var14 = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
         var4 = new RuleSymbol("mnextToken");
         var4.setDefined();
         var4.setBlock(var14);
         var4.access = "private";
         this.grammar.define(var4);
         this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var14);
         String var6 = null;
         if (((LexerGrammar)this.grammar).filterMode) {
            var6 = ((LexerGrammar)this.grammar).filterRule;
         }

         this.println("");
         this.println("public Token nextToken() throws TokenStreamException {");
         ++this.tabs;
         this.println("Token theRetToken=null;");
         this._println("tryAgain:");
         this.println("for (;;) {");
         ++this.tabs;
         this.println("Token _token = null;");
         this.println("int _ttype = Token.INVALID_TYPE;");
         if (((LexerGrammar)this.grammar).filterMode) {
            this.println("setCommitToPath(false);");
            if (var6 != null) {
               if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(var6))) {
                  this.grammar.antlrTool.error("Filter rule " + var6 + " does not exist in this lexer");
               } else {
                  RuleSymbol var7 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(var6));
                  if (!var7.isDefined()) {
                     this.grammar.antlrTool.error("Filter rule " + var6 + " does not exist in this lexer");
                  } else if (var7.access.equals("public")) {
                     this.grammar.antlrTool.error("Filter rule " + var6 + " must be protected");
                  }
               }

               this.println("int _m;");
               this.println("_m = mark();");
            }
         }

         this.println("resetText();");
         this.println("try {   // for char stream error handling");
         ++this.tabs;
         this.println("try {   // for lexical error handling");
         ++this.tabs;

         for(int var15 = 0; var15 < var14.getAlternatives().size(); ++var15) {
            Alternative var8 = var14.getAlternativeAt(var15);
            if (var8.cache[1].containsEpsilon()) {
               RuleRefElement var9 = (RuleRefElement)var8.head;
               String var10 = CodeGenerator.decodeLexerRuleName(var9.targetRule);
               this.antlrTool.warning("public lexical rule " + var10 + " is optional (can match \"nothing\")");
            }
         }

         String var16 = System.getProperty("line.separator");
         JavaBlockFinishingInfo var17 = this.genCommonBlock(var14, false);
         String var18 = "if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}";
         var18 = var18 + var16 + "\t\t\t\t";
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var6 == null) {
               var18 = var18 + "else {consume(); continue tryAgain;}";
            } else {
               var18 = var18 + "else {" + var16 + "\t\t\t\t\tcommit();" + var16 + "\t\t\t\t\ttry {m" + var6 + "(false);}" + var16 + "\t\t\t\t\tcatch(RecognitionException e) {" + var16 + "\t\t\t\t\t\t// catastrophic failure" + var16 + "\t\t\t\t\t\treportError(e);" + var16 + "\t\t\t\t\t\tconsume();" + var16 + "\t\t\t\t\t}" + var16 + "\t\t\t\t\tcontinue tryAgain;" + var16 + "\t\t\t\t}";
            }
         } else {
            var18 = var18 + "else {" + this.throwNoViable + "}";
         }

         this.genBlockFinish(var17, var18, var14.getLine());
         if (((LexerGrammar)this.grammar).filterMode && var6 != null) {
            this.println("commit();");
         }

         this.println("if ( _returnToken==null ) continue tryAgain; // found SKIP token");
         this.println("_ttype = _returnToken.getType();");
         if (((LexerGrammar)this.grammar).getTestLiterals()) {
            this.genLiteralsTest();
         }

         this.println("_returnToken.setType(_ttype);");
         this.println("return _returnToken;");
         --this.tabs;
         this.println("}");
         this.println("catch (RecognitionException e) {");
         ++this.tabs;
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var6 == null) {
               this.println("if ( !getCommitToPath() ) {consume(); continue tryAgain;}");
            } else {
               this.println("if ( !getCommitToPath() ) {");
               ++this.tabs;
               this.println("rewind(_m);");
               this.println("resetText();");
               this.println("try {m" + var6 + "(false);}");
               this.println("catch(RecognitionException ee) {");
               this.println("\t// horrendous failure: error in filter rule");
               this.println("\treportError(ee);");
               this.println("\tconsume();");
               this.println("}");
               this.println("continue tryAgain;");
               --this.tabs;
               this.println("}");
            }
         }

         if (var14.getDefaultErrorHandler()) {
            this.println("reportError(e);");
            this.println("consume();");
         } else {
            this.println("throw new TokenStreamRecognitionException(e);");
         }

         --this.tabs;
         this.println("}");
         --this.tabs;
         this.println("}");
         this.println("catch (CharStreamException cse) {");
         this.println("\tif ( cse instanceof CharStreamIOException ) {");
         this.println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
         this.println("\t}");
         this.println("\telse {");
         this.println("\t\tthrow new TokenStreamException(cse.getMessage());");
         this.println("\t}");
         this.println("}");
         --this.tabs;
         this.println("}");
         --this.tabs;
         this.println("}");
         this.println("");
      } finally {
         this.defaultLine = var1;
      }

   }

   public void genRule(RuleSymbol var1, boolean var2, int var3) {
      this.tabs = 1;
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genRule(" + var1.getId() + ")");
      }

      if (!var1.isDefined()) {
         this.antlrTool.error("undefined rule: " + var1.getId());
      } else {
         RuleBlock var4 = var1.getBlock();
         int var5 = this.defaultLine;

         try {
            this.defaultLine = var4.getLine();
            this.currentRule = var4;
            this.currentASTResult = var1.getId();
            this.declaredASTVariables.clear();
            boolean var6 = this.genAST;
            this.genAST = this.genAST && var4.getAutoGen();
            this.saveText = var4.getAutoGen();
            if (var1.comment != null) {
               this._println(var1.comment);
            }

            this.print(var1.access + " final ");
            if (var4.returnAction != null) {
               this._print(this.extractTypeOfAction(var4.returnAction, var4.getLine(), var4.getColumn()) + " ");
            } else {
               this._print("void ");
            }

            this._print(var1.getId() + "(");
            this._print(this.commonExtraParams);
            if (this.commonExtraParams.length() != 0 && var4.argAction != null) {
               this._print(",");
            }

            if (var4.argAction != null) {
               this._println("");
               ++this.tabs;
               this.println(var4.argAction);
               --this.tabs;
               this.print(")");
            } else {
               this._print(")");
            }

            this._print(" throws " + this.exceptionThrown);
            if (this.grammar instanceof ParserGrammar) {
               this._print(", TokenStreamException");
            } else if (this.grammar instanceof LexerGrammar) {
               this._print(", CharStreamException, TokenStreamException");
            }

            if (var4.throwsSpec != null) {
               if (this.grammar instanceof LexerGrammar) {
                  this.antlrTool.error("user-defined throws spec not allowed (yet) for lexer rule " + var4.ruleName);
               } else {
                  this._print(", " + var4.throwsSpec);
               }
            }

            this._println(" {");
            ++this.tabs;
            if (var4.returnAction != null) {
               this.println(var4.returnAction + ";");
            }

            this.println(this.commonLocalVars);
            if (this.grammar.traceRules) {
               if (this.grammar instanceof TreeWalkerGrammar) {
                  this.println("traceIn(\"" + var1.getId() + "\",_t);");
               } else {
                  this.println("traceIn(\"" + var1.getId() + "\");");
               }
            }

            if (this.grammar instanceof LexerGrammar) {
               if (var1.getId().equals("mEOF")) {
                  this.println("_ttype = Token.EOF_TYPE;");
               } else {
                  this.println("_ttype = " + var1.getId().substring(1) + ";");
               }

               this.println("int _saveIndex;");
            }

            if (this.grammar.debuggingOutput) {
               if (this.grammar instanceof ParserGrammar) {
                  this.println("fireEnterRule(" + var3 + ",0);");
               } else if (this.grammar instanceof LexerGrammar) {
                  this.println("fireEnterRule(" + var3 + ",_ttype);");
               }
            }

            if (this.grammar.debuggingOutput || this.grammar.traceRules) {
               this.println("try { // debugging");
               ++this.tabs;
            }

            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println(this.labeledElementASTType + " " + var1.getId() + "_AST_in = (_t == ASTNULL) ? null : (" + this.labeledElementASTType + ")_t;", -999);
            }

            if (this.grammar.buildAST) {
               this.println("returnAST = null;");
               this.println("ASTPair currentAST = new ASTPair();");
               this.println(this.labeledElementASTType + " " + var1.getId() + "_AST = null;");
            }

            this.genBlockPreamble(var4);
            this.genBlockInitAction(var4);
            this.println("");
            ExceptionSpec var7 = var4.findExceptionSpec("");
            if (var7 != null || var4.getDefaultErrorHandler()) {
               this.println("try {      // for error handling");
               ++this.tabs;
            }

            String var9;
            if (var4.alternatives.size() == 1) {
               Alternative var8 = var4.getAlternativeAt(0);
               var9 = var8.semPred;
               if (var9 != null) {
                  this.genSemPred(var9, this.currentRule.line);
               }

               if (var8.synPred != null) {
                  this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), var8.synPred.getLine(), var8.synPred.getColumn());
               }

               this.genAlt(var8, var4);
            } else {
               this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var4);
               JavaBlockFinishingInfo var14 = this.genCommonBlock(var4, false);
               this.genBlockFinish(var14, this.throwNoViable, var4.getLine());
            }

            if (var7 != null || var4.getDefaultErrorHandler()) {
               --this.tabs;
               this.println("}");
            }

            if (var7 != null) {
               this.genErrorHandler(var7);
            } else if (var4.getDefaultErrorHandler()) {
               this.println("catch (" + this.exceptionThrown + " ex) {");
               ++this.tabs;
               if (this.grammar.hasSyntacticPredicate) {
                  this.println("if (inputState.guessing==0) {");
                  ++this.tabs;
               }

               this.println("reportError(ex);");
               if (!(this.grammar instanceof TreeWalkerGrammar)) {
                  Lookahead var13 = this.grammar.theLLkAnalyzer.FOLLOW(1, var4.endNode);
                  var9 = this.getBitsetName(this.markBitsetForGen(var13.fset));
                  this.println("recover(ex," + var9 + ");");
               } else {
                  this.println("if (_t!=null) {_t = _t.getNextSibling();}");
               }

               if (this.grammar.hasSyntacticPredicate) {
                  --this.tabs;
                  this.println("} else {");
                  this.println("  throw ex;");
                  this.println("}");
               }

               --this.tabs;
               this.println("}");
            }

            if (this.grammar.buildAST) {
               this.println("returnAST = " + var1.getId() + "_AST;");
            }

            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println("_retTree = _t;");
            }

            if (var4.getTestLiterals()) {
               if (var1.access.equals("protected")) {
                  this.genLiteralsTestForPartialToken();
               } else {
                  this.genLiteralsTest();
               }
            }

            if (this.grammar instanceof LexerGrammar) {
               this.println("if ( _createToken && _token==null && _ttype!=Token.SKIP ) {");
               this.println("\t_token = makeToken(_ttype);");
               this.println("\t_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));");
               this.println("}");
               this.println("_returnToken = _token;");
            }

            if (var4.returnAction != null) {
               this.println("return " + this.extractIdOfAction(var4.returnAction, var4.getLine(), var4.getColumn()) + ";");
            }

            if (this.grammar.debuggingOutput || this.grammar.traceRules) {
               --this.tabs;
               this.println("} finally { // debugging");
               ++this.tabs;
               if (this.grammar.debuggingOutput) {
                  if (this.grammar instanceof ParserGrammar) {
                     this.println("fireExitRule(" + var3 + ",0);");
                  } else if (this.grammar instanceof LexerGrammar) {
                     this.println("fireExitRule(" + var3 + ",_ttype);");
                  }
               }

               if (this.grammar.traceRules) {
                  if (this.grammar instanceof TreeWalkerGrammar) {
                     this.println("traceOut(\"" + var1.getId() + "\",_t);");
                  } else {
                     this.println("traceOut(\"" + var1.getId() + "\");");
                  }
               }

               --this.tabs;
               this.println("}");
            }

            --this.tabs;
            this.println("}");
            this.println("");
            this.genAST = var6;
         } finally {
            this.defaultLine = var5;
         }

      }
   }

   private void GenRuleInvocation(RuleRefElement var1) {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         this.getPrintWriterManager().startSingleSourceLineMapping(var1.getLine());
         this._print(var1.targetRule + "(");
         this.getPrintWriterManager().endMapping();
         if (this.grammar instanceof LexerGrammar) {
            if (var1.getLabel() != null) {
               this._print("true");
            } else {
               this._print("false");
            }

            if (this.commonExtraArgs.length() != 0 || var1.args != null) {
               this._print(",");
            }
         }

         this._print(this.commonExtraArgs);
         if (this.commonExtraArgs.length() != 0 && var1.args != null) {
            this._print(",");
         }

         RuleSymbol var3 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
         if (var1.args == null) {
            if (var3.block.argAction != null) {
               this.antlrTool.warning("Missing parameters on reference to rule " + var1.targetRule, this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            }
         } else {
            ActionTransInfo var4 = new ActionTransInfo();
            String var5 = this.processActionForSpecialSymbols(var1.args, 0, this.currentRule, var4);
            if (var4.assignToRoot || var4.refRuleRoot != null) {
               this.antlrTool.error("Arguments of rule reference '" + var1.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            }

            this._print(var5);
            if (var3.block.argAction == null) {
               this.antlrTool.warning("Rule '" + var1.targetRule + "' accepts no arguments", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            }
         }

         this._println(");");
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("_t = _retTree;");
         }
      } finally {
         this.defaultLine = var2;
      }

   }

   protected void genSemPred(String var1, int var2) {
      ActionTransInfo var3 = new ActionTransInfo();
      var1 = this.processActionForSpecialSymbols(var1, var2, this.currentRule, var3);
      String var4 = this.charFormatter.escapeString(var1);
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         var1 = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + this.addSemPred(var4) + "," + var1 + ")";
      }

      this.println("if (!(" + var1 + "))", var2);
      this.println("  throw new SemanticException(\"" + var4 + "\");", var2);
   }

   protected void genSemPredMap() {
      Enumeration var1 = this.semPreds.elements();
      this.println("private String _semPredNames[] = {", -999);

      while(var1.hasMoreElements()) {
         this.println("\"" + var1.nextElement() + "\",", -999);
      }

      this.println("};", -999);
   }

   protected void genSynPred(SynPredBlock var1, String var2) {
      int var3 = this.defaultLine;

      try {
         this.defaultLine = var1.getLine();
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("gen=>(" + var1 + ")");
         }

         this.println("boolean synPredMatched" + var1.ID + " = false;");
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("if (_t==null) _t=ASTNULL;");
         }

         this.println("if (" + var2 + ") {");
         ++this.tabs;
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("AST __t" + var1.ID + " = _t;");
         } else {
            this.println("int _m" + var1.ID + " = mark();");
         }

         this.println("synPredMatched" + var1.ID + " = true;");
         this.println("inputState.guessing++;");
         if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
            this.println("fireSyntacticPredicateStarted();");
         }

         ++this.syntacticPredLevel;
         this.println("try {");
         ++this.tabs;
         this.gen((AlternativeBlock)var1);
         --this.tabs;
         this.println("}");
         this.println("catch (" + this.exceptionThrown + " pe) {");
         ++this.tabs;
         this.println("synPredMatched" + var1.ID + " = false;");
         --this.tabs;
         this.println("}");
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("_t = __t" + var1.ID + ";");
         } else {
            this.println("rewind(_m" + var1.ID + ");");
         }

         this._println("inputState.guessing--;");
         if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
            this.println("if (synPredMatched" + var1.ID + ")");
            this.println("  fireSyntacticPredicateSucceeded();");
            this.println("else");
            this.println("  fireSyntacticPredicateFailed();");
         }

         --this.syntacticPredLevel;
         --this.tabs;
         this.println("}");
         this.println("if ( synPredMatched" + var1.ID + " ) {");
      } finally {
         this.defaultLine = var3;
      }

   }

   public void genTokenStrings() {
      int var1 = this.defaultLine;

      try {
         this.defaultLine = -999;
         this.println("");
         this.println("public static final String[] _tokenNames = {");
         ++this.tabs;
         Vector var2 = this.grammar.tokenManager.getVocabulary();

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            String var4 = (String)var2.elementAt(var3);
            if (var4 == null) {
               var4 = "<" + String.valueOf(var3) + ">";
            }

            if (!var4.startsWith("\"") && !var4.startsWith("<")) {
               TokenSymbol var5 = this.grammar.tokenManager.getTokenSymbol(var4);
               if (var5 != null && var5.getParaphrase() != null) {
                  var4 = StringUtils.stripFrontBack(var5.getParaphrase(), "\"", "\"");
               }
            }

            this.print(this.charFormatter.literalString(var4));
            if (var3 != var2.size() - 1) {
               this._print(",");
            }

            this._println("");
         }

         --this.tabs;
         this.println("};");
      } finally {
         this.defaultLine = var1;
      }
   }

   protected void genTokenASTNodeMap() {
      int var1 = this.defaultLine;

      try {
         this.defaultLine = -999;
         this.println("");
         this.println("protected void buildTokenTypeASTClassMap() {");
         ++this.tabs;
         boolean var2 = false;
         int var3 = 0;
         Vector var4 = this.grammar.tokenManager.getVocabulary();

         for(int var5 = 0; var5 < var4.size(); ++var5) {
            String var6 = (String)var4.elementAt(var5);
            if (var6 != null) {
               TokenSymbol var7 = this.grammar.tokenManager.getTokenSymbol(var6);
               if (var7 != null && var7.getASTNodeType() != null) {
                  ++var3;
                  if (!var2) {
                     this.println("tokenTypeToASTClassMap = new Hashtable();");
                     var2 = true;
                  }

                  this.println("tokenTypeToASTClassMap.put(new Integer(" + var7.getTokenType() + "), " + var7.getASTNodeType() + ".class);");
               }
            }
         }

         if (var3 == 0) {
            this.println("tokenTypeToASTClassMap=null;");
         }

         --this.tabs;
         this.println("};");
      } finally {
         this.defaultLine = var1;
      }
   }

   protected void genTokenTypes(TokenManager var1) throws IOException {
      int var2 = this.defaultLine;

      try {
         this.defaultLine = -999;
         this.currentOutput = this.getPrintWriterManager().setupOutput(this.antlrTool, var1.getName() + TokenTypesFileSuffix);
         this.tabs = 0;
         this.genHeader();

         try {
            this.defaultLine = this.behavior.getHeaderActionLine("");
            this.println(this.behavior.getHeaderAction(""));
         } finally {
            this.defaultLine = -999;
         }

         this.println("public interface " + var1.getName() + TokenTypesFileSuffix + " {");
         ++this.tabs;
         Vector var3 = var1.getVocabulary();
         this.println("int EOF = 1;");
         this.println("int NULL_TREE_LOOKAHEAD = 3;");

         for(int var4 = 4; var4 < var3.size(); ++var4) {
            String var5 = (String)var3.elementAt(var4);
            if (var5 != null) {
               if (var5.startsWith("\"")) {
                  StringLiteralSymbol var6 = (StringLiteralSymbol)var1.getTokenSymbol(var5);
                  if (var6 == null) {
                     this.antlrTool.panic("String literal " + var5 + " not in symbol table");
                  } else if (var6.label != null) {
                     this.println("int " + var6.label + " = " + var4 + ";");
                  } else {
                     String var7 = this.mangleLiteral(var5);
                     if (var7 != null) {
                        this.println("int " + var7 + " = " + var4 + ";");
                        var6.label = var7;
                     } else {
                        this.println("// " + var5 + " = " + var4);
                     }
                  }
               } else if (!var5.startsWith("<")) {
                  this.println("int " + var5 + " = " + var4 + ";");
               }
            }
         }

         --this.tabs;
         this.println("}");
         this.getPrintWriterManager().finishOutput();
         this.exitIfError();
      } finally {
         this.defaultLine = var2;
      }
   }

   public String getASTCreateString(Vector var1) {
      if (var1.size() == 0) {
         return "";
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append("(" + this.labeledElementASTType + ")astFactory.make( (new ASTArray(" + var1.size() + "))");

         for(int var3 = 0; var3 < var1.size(); ++var3) {
            var2.append(".add(" + var1.elementAt(var3) + ")");
         }

         var2.append(")");
         return var2.toString();
      }
   }

   public String getASTCreateString(GrammarAtom var1, String var2) {
      return var1 != null && var1.getASTNodeType() != null ? "(" + var1.getASTNodeType() + ")" + "astFactory.create(" + var2 + ",\"" + var1.getASTNodeType() + "\")" : this.getASTCreateString(var2);
   }

   public String getASTCreateString(String var1) {
      if (var1 == null) {
         var1 = "";
      }

      int var2 = 0;

      int var3;
      for(var3 = 0; var3 < var1.length(); ++var3) {
         if (var1.charAt(var3) == ',') {
            ++var2;
         }
      }

      if (var2 < 2) {
         var3 = var1.indexOf(44);
         int var4 = var1.lastIndexOf(44);
         String var5 = var1;
         if (var2 > 0) {
            var5 = var1.substring(0, var3);
         }

         TokenSymbol var6 = this.grammar.tokenManager.getTokenSymbol(var5);
         if (var6 != null) {
            String var7 = var6.getASTNodeType();
            String var8 = "";
            if (var2 == 0) {
               var8 = ",\"\"";
            }

            if (var7 != null) {
               return "(" + var7 + ")" + "astFactory.create(" + var1 + var8 + ",\"" + var7 + "\")";
            }
         }

         if (this.labeledElementASTType.equals("AST")) {
            return "astFactory.create(" + var1 + ")";
         } else {
            return "(" + this.labeledElementASTType + ")" + "astFactory.create(" + var1 + ")";
         }
      } else {
         return "(" + this.labeledElementASTType + ")astFactory.create(" + var1 + ")";
      }
   }

   protected String getLookaheadTestExpression(Lookahead[] var1, int var2) {
      StringBuffer var3 = new StringBuffer(100);
      boolean var4 = true;
      var3.append("(");

      for(int var5 = 1; var5 <= var2; ++var5) {
         BitSet var6 = var1[var5].fset;
         if (!var4) {
            var3.append(") && (");
         }

         var4 = false;
         if (var1[var5].containsEpsilon()) {
            var3.append("true");
         } else {
            var3.append(this.getLookaheadTestTerm(var5, var6));
         }
      }

      var3.append(")");
      return var3.toString();
   }

   protected String getLookaheadTestExpression(Alternative var1, int var2) {
      int var3 = var1.lookaheadDepth;
      if (var3 == Integer.MAX_VALUE) {
         var3 = this.grammar.maxk;
      }

      return var2 == 0 ? "( true )" : "(" + this.getLookaheadTestExpression(var1.cache, var3) + ")";
   }

   protected String getLookaheadTestTerm(int var1, BitSet var2) {
      String var3 = this.lookaheadString(var1);
      int[] var4 = var2.toArray();
      if (elementsAreRange(var4)) {
         return this.getRangeExpression(var1, var4);
      } else {
         int var6 = var2.degree();
         if (var6 == 0) {
            return "true";
         } else {
            int var7;
            if (var6 >= this.bitsetTestThreshold) {
               var7 = this.markBitsetForGen(var2);
               return this.getBitsetName(var7) + ".member(" + var3 + ")";
            } else {
               StringBuffer var5 = new StringBuffer();

               for(var7 = 0; var7 < var4.length; ++var7) {
                  String var8 = this.getValueString(var4[var7]);
                  if (var7 > 0) {
                     var5.append("||");
                  }

                  var5.append(var3);
                  var5.append("==");
                  var5.append(var8);
               }

               return var5.toString();
            }
         }
      }
   }

   public String getRangeExpression(int var1, int[] var2) {
      if (!elementsAreRange(var2)) {
         this.antlrTool.panic("getRangeExpression called with non-range");
      }

      int var3 = var2[0];
      int var4 = var2[var2.length - 1];
      return "(" + this.lookaheadString(var1) + " >= " + this.getValueString(var3) + " && " + this.lookaheadString(var1) + " <= " + this.getValueString(var4) + ")";
   }

   private String getValueString(int var1) {
      String var2;
      if (this.grammar instanceof LexerGrammar) {
         var2 = this.charFormatter.literalChar(var1);
      } else {
         TokenSymbol var3 = this.grammar.tokenManager.getTokenSymbolAt(var1);
         if (var3 == null) {
            return "" + var1;
         }

         String var4 = var3.getId();
         if (var3 instanceof StringLiteralSymbol) {
            StringLiteralSymbol var5 = (StringLiteralSymbol)var3;
            String var6 = var5.getLabel();
            if (var6 != null) {
               var2 = var6;
            } else {
               var2 = this.mangleLiteral(var4);
               if (var2 == null) {
                  var2 = String.valueOf(var1);
               }
            }
         } else {
            var2 = var4;
         }
      }

      return var2;
   }

   protected boolean lookaheadIsEmpty(Alternative var1, int var2) {
      int var3 = var1.lookaheadDepth;
      if (var3 == Integer.MAX_VALUE) {
         var3 = this.grammar.maxk;
      }

      for(int var4 = 1; var4 <= var3 && var4 <= var2; ++var4) {
         BitSet var5 = var1.cache[var4].fset;
         if (var5.degree() != 0) {
            return false;
         }
      }

      return true;
   }

   private String lookaheadString(int var1) {
      return this.grammar instanceof TreeWalkerGrammar ? "_t.getType()" : "LA(" + var1 + ")";
   }

   private String mangleLiteral(String var1) {
      String var2 = this.antlrTool.literalsPrefix;

      for(int var3 = 1; var3 < var1.length() - 1; ++var3) {
         if (!Character.isLetter(var1.charAt(var3)) && var1.charAt(var3) != '_') {
            return null;
         }

         var2 = var2 + var1.charAt(var3);
      }

      if (this.antlrTool.upperCaseMangledLiterals) {
         var2 = var2.toUpperCase();
      }

      return var2;
   }

   public String mapTreeId(String var1, ActionTransInfo var2) {
      if (this.currentRule == null) {
         return var1;
      } else {
         boolean var3 = false;
         String var4 = var1;
         if (this.grammar instanceof TreeWalkerGrammar) {
            if (!this.grammar.buildAST) {
               var3 = true;
            } else if (var1.length() > 3 && var1.lastIndexOf("_in") == var1.length() - 3) {
               var4 = var1.substring(0, var1.length() - 3);
               var3 = true;
            }
         }

         for(int var5 = 0; var5 < this.currentRule.labeledElements.size(); ++var5) {
            AlternativeElement var6 = (AlternativeElement)this.currentRule.labeledElements.elementAt(var5);
            if (var6.getLabel().equals(var4)) {
               return var3 ? var4 : var4 + "_AST";
            }
         }

         String var7 = (String)this.treeVariableMap.get(var4);
         if (var7 != null) {
            if (var7 == NONUNIQUE) {
               this.antlrTool.error("Ambiguous reference to AST element " + var4 + " in rule " + this.currentRule.getRuleName());
               return null;
            } else if (var7.equals(this.currentRule.getRuleName())) {
               this.antlrTool.error("Ambiguous reference to AST element " + var4 + " in rule " + this.currentRule.getRuleName());
               return null;
            } else {
               return var3 ? var7 + "_in" : var7;
            }
         } else if (var4.equals(this.currentRule.getRuleName())) {
            String var8 = var3 ? var4 + "_AST_in" : var4 + "_AST";
            if (var2 != null && !var3) {
               var2.refRuleRoot = var8;
            }

            return var8;
         } else {
            return var4;
         }
      }
   }

   private void mapTreeVariable(AlternativeElement var1, String var2) {
      if (var1 instanceof TreeElement) {
         this.mapTreeVariable(((TreeElement)var1).root, var2);
      } else {
         String var3 = null;
         if (var1.getLabel() == null) {
            if (var1 instanceof TokenRefElement) {
               var3 = ((TokenRefElement)var1).atomText;
            } else if (var1 instanceof RuleRefElement) {
               var3 = ((RuleRefElement)var1).targetRule;
            }
         }

         if (var3 != null) {
            if (this.treeVariableMap.get(var3) != null) {
               this.treeVariableMap.remove(var3);
               this.treeVariableMap.put(var3, NONUNIQUE);
            } else {
               this.treeVariableMap.put(var3, var2);
            }
         }

      }
   }

   protected String processActionForSpecialSymbols(String var1, int var2, RuleBlock var3, ActionTransInfo var4) {
      if (var1 != null && var1.length() != 0) {
         if (this.grammar == null) {
            return var1;
         } else {
            if (this.grammar.buildAST && var1.indexOf(35) != -1 || this.grammar instanceof TreeWalkerGrammar || (this.grammar instanceof LexerGrammar || this.grammar instanceof ParserGrammar) && var1.indexOf(36) != -1) {
               ActionLexer var5 = new ActionLexer(var1, var3, this, var4);
               var5.setLineOffset(var2);
               var5.setFilename(this.grammar.getFilename());
               var5.setTool(this.antlrTool);

               try {
                  var5.mACTION(true);
                  var1 = var5.getTokenObject().getText();
               } catch (RecognitionException var7) {
                  var5.reportError(var7);
                  return var1;
               } catch (TokenStreamException var8) {
                  this.antlrTool.panic("Error reading action:" + var1);
                  return var1;
               } catch (CharStreamException var9) {
                  this.antlrTool.panic("Error reading action:" + var1);
                  return var1;
               }
            }

            return var1;
         }
      } else {
         return null;
      }
   }

   private void setupGrammarParameters(Grammar var1) {
      Token var2;
      String var3;
      if (var1 instanceof ParserGrammar) {
         this.labeledElementASTType = "AST";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.labeledElementASTType = var3;
               }
            }
         }

         this.labeledElementType = "Token ";
         this.labeledElementInit = "null";
         this.commonExtraArgs = "";
         this.commonExtraParams = "";
         this.commonLocalVars = "";
         this.lt1Value = "LT(1)";
         this.exceptionThrown = "RecognitionException";
         this.throwNoViable = "throw new NoViableAltException(LT(1), getFilename());";
      } else if (var1 instanceof LexerGrammar) {
         this.labeledElementType = "char ";
         this.labeledElementInit = "'\\0'";
         this.commonExtraArgs = "";
         this.commonExtraParams = "boolean _createToken";
         this.commonLocalVars = "int _ttype; Token _token=null; int _begin=text.length();";
         this.lt1Value = "LA(1)";
         this.exceptionThrown = "RecognitionException";
         this.throwNoViable = "throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());";
      } else if (var1 instanceof TreeWalkerGrammar) {
         this.labeledElementASTType = "AST";
         this.labeledElementType = "AST";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.labeledElementASTType = var3;
                  this.labeledElementType = var3;
               }
            }
         }

         if (!var1.hasOption("ASTLabelType")) {
            var1.setOption("ASTLabelType", new Token(6, "AST"));
         }

         this.labeledElementInit = "null";
         this.commonExtraArgs = "_t";
         this.commonExtraParams = "AST _t";
         this.commonLocalVars = "";
         this.lt1Value = "(" + this.labeledElementASTType + ")_t";
         this.exceptionThrown = "RecognitionException";
         this.throwNoViable = "throw new NoViableAltException(_t);";
      } else {
         this.antlrTool.panic("Unknown grammar type");
      }

   }

   public JavaCodeGeneratorPrintWriterManager getPrintWriterManager() {
      if (this.printWriterManager == null) {
         this.printWriterManager = new DefaultJavaCodeGeneratorPrintWriterManager();
      }

      return this.printWriterManager;
   }

   public void setPrintWriterManager(JavaCodeGeneratorPrintWriterManager var1) {
      this.printWriterManager = var1;
   }

   public void setTool(Tool var1) {
      super.setTool(var1);
   }
}
