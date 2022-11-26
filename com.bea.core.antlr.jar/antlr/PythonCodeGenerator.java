package antlr;

import antlr.actions.python.ActionLexer;
import antlr.actions.python.CodeLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

public class PythonCodeGenerator extends CodeGenerator {
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
   public static final String initHeaderAction = "__init__";
   public static final String mainHeaderAction = "__main__";
   String lexerClassName;
   String parserClassName;
   String treeWalkerClassName;
   RuleBlock currentRule;
   String currentASTResult;
   Hashtable treeVariableMap = new Hashtable();
   Hashtable declaredASTVariables = new Hashtable();
   int astVarNumber = 1;
   protected static final String NONUNIQUE = new String();
   public static final int caseSizeThreshold = 127;
   private Vector semPreds;

   protected void printTabs() {
      for(int var1 = 0; var1 < this.tabs; ++var1) {
         this.currentOutput.print("    ");
      }

   }

   public PythonCodeGenerator() {
      this.charFormatter = new PythonCharFormatter();
      this.DEBUG_CODE_GENERATOR = true;
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

   protected void checkCurrentOutputStream() {
      try {
         if (this.currentOutput == null) {
            throw new NullPointerException();
         }
      } catch (Exception var2) {
         Utils.error("current output is not set");
      }

   }

   protected String extractIdOfAction(String var1, int var2, int var3) {
      var1 = this.removeAssignmentFromDeclaration(var1);
      var1 = var1.trim();
      return var1;
   }

   protected String extractTypeOfAction(String var1, int var2, int var3) {
      return "";
   }

   protected void flushTokens() {
      try {
         boolean var1 = false;
         this.checkCurrentOutputStream();
         this.println("");
         this.println("### import antlr.Token ");
         this.println("from antlr import Token");
         this.println("### >>>The Known Token Types <<<");
         PrintWriter var2 = this.currentOutput;

         for(Enumeration var3 = this.behavior.tokenManagers.elements(); var3.hasMoreElements(); this.exitIfError()) {
            TokenManager var4 = (TokenManager)var3.nextElement();
            if (!var4.isReadOnly()) {
               if (!var1) {
                  this.genTokenTypes(var4);
                  var1 = true;
               }

               this.currentOutput = var2;
               this.genTokenInterchange(var4);
               this.currentOutput = var2;
            }
         }
      } catch (Exception var5) {
         this.exitIfError();
      }

      this.checkCurrentOutputStream();
      this.println("");
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
      } catch (IOException var3) {
         this.antlrTool.reportException(var3, (String)null);
      }

   }

   public void gen(ActionElement var1) {
      if (var1.isSemPred) {
         this.genSemPred(var1.actionText, var1.line);
      } else {
         if (this.grammar.hasSyntacticPredicate) {
            this.println("if not self.inputState.guessing:");
            ++this.tabs;
         }

         ActionTransInfo var2 = new ActionTransInfo();
         String var3 = this.processActionForSpecialSymbols(var1.actionText, var1.getLine(), this.currentRule, var2);
         if (var2.refRuleRoot != null) {
            this.println(var2.refRuleRoot + " = currentAST.root");
         }

         this.printAction(var3);
         if (var2.assignToRoot) {
            this.println("currentAST.root = " + var2.refRuleRoot + "");
            this.println("if (" + var2.refRuleRoot + " != None) and (" + var2.refRuleRoot + ".getFirstChild() != None):");
            ++this.tabs;
            this.println("currentAST.child = " + var2.refRuleRoot + ".getFirstChild()");
            --this.tabs;
            this.println("else:");
            ++this.tabs;
            this.println("currentAST.child = " + var2.refRuleRoot);
            --this.tabs;
            this.println("currentAST.advanceChildToEnd()");
         }

         if (this.grammar.hasSyntacticPredicate) {
            --this.tabs;
         }
      }

   }

   public void gen(AlternativeBlock var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen(" + var1 + ")");
      }

      this.genBlockPreamble(var1);
      this.genBlockInitAction(var1);
      String var2 = this.currentASTResult;
      if (var1.getLabel() != null) {
         this.currentASTResult = var1.getLabel();
      }

      this.grammar.theLLkAnalyzer.deterministic(var1);
      int var4 = this.tabs;
      PythonBlockFinishingInfo var5 = this.genCommonBlock(var1, true);
      this.genBlockFinish(var5, this.throwNoViable);
      this.tabs = var4;
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
         this.println(var1.getLabel() + " = " + this.lt1Value);
      }

      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      this.genMatch((GrammarAtom)var1);
      this.saveText = var2;
   }

   String toString(boolean var1) {
      String var2;
      if (var1) {
         var2 = "True";
      } else {
         var2 = "False";
      }

      return var2;
   }

   public void gen(CharRangeElement var1) {
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value);
      }

      boolean var2 = this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3);
      if (var2) {
         this.println("_saveIndex = self.text.length()");
      }

      this.println("self.matchRange(u" + var1.beginText + ", u" + var1.endText + ")");
      if (var2) {
         this.println("self.text.setLength(_saveIndex)");
      }

   }

   public void gen(LexerGrammar var1) throws IOException {
      if (var1.debuggingOutput) {
         this.semPreds = new Vector();
      }

      this.setGrammar(var1);
      if (!(this.grammar instanceof LexerGrammar)) {
         this.antlrTool.panic("Internal error generating lexer");
      }

      this.setupOutput(this.grammar.getClassName());
      this.genAST = false;
      this.saveText = true;
      this.tabs = 0;
      this.genHeader();
      this.println("### import antlr and other modules ..");
      this.println("import sys");
      this.println("import antlr");
      this.println("");
      this.println("version = sys.version.split()[0]");
      this.println("if version < '2.2.1':");
      ++this.tabs;
      this.println("False = 0");
      --this.tabs;
      this.println("if version < '2.3':");
      ++this.tabs;
      this.println("True = not False");
      --this.tabs;
      this.println("### header action >>> ");
      this.printActionCode(this.behavior.getHeaderAction(""), 0);
      this.println("### header action <<< ");
      this.println("### preamble action >>> ");
      this.printActionCode(this.grammar.preambleAction.getText(), 0);
      this.println("### preamble action <<< ");
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = "antlr." + this.grammar.getSuperClass();
      }

      String var3 = "";
      Token var4 = (Token)this.grammar.options.get("classHeaderPrefix");
      if (var4 != null) {
         String var5 = StringUtils.stripFrontBack(var4.getText(), "\"", "\"");
         if (var5 != null) {
            ;
         }
      }

      this.println("### >>>The Literals<<<");
      this.println("literals = {}");
      Enumeration var9 = this.grammar.tokenManager.getTokenSymbolKeys();

      while(var9.hasMoreElements()) {
         String var6 = (String)var9.nextElement();
         if (var6.charAt(0) == '"') {
            TokenSymbol var7 = this.grammar.tokenManager.getTokenSymbol(var6);
            if (var7 instanceof StringLiteralSymbol) {
               StringLiteralSymbol var8 = (StringLiteralSymbol)var7;
               this.println("literals[u" + var8.getId() + "] = " + var8.getTokenType());
            }
         }
      }

      this.println("");
      this.flushTokens();
      this.genJavadocComment(this.grammar);
      this.println("class " + this.lexerClassName + "(" + var2 + ") :");
      ++this.tabs;
      this.printGrammarAction(this.grammar);
      this.println("def __init__(self, *argv, **kwargs) :");
      ++this.tabs;
      this.println(var2 + ".__init__(self, *argv, **kwargs)");
      this.println("self.caseSensitiveLiterals = " + this.toString(var1.caseSensitiveLiterals));
      this.println("self.setCaseSensitive(" + this.toString(var1.caseSensitive) + ")");
      this.println("self.literals = literals");
      Enumeration var10;
      if (this.grammar.debuggingOutput) {
         this.println("ruleNames[] = [");
         var10 = this.grammar.rules.elements();
         boolean var11 = false;
         ++this.tabs;

         while(var10.hasMoreElements()) {
            GrammarSymbol var13 = (GrammarSymbol)var10.nextElement();
            if (var13 instanceof RuleSymbol) {
               this.println("\"" + ((RuleSymbol)var13).getId() + "\",");
            }
         }

         --this.tabs;
         this.println("]");
      }

      this.genHeaderInit(this.grammar);
      --this.tabs;
      this.genNextToken();
      this.println("");
      var10 = this.grammar.rules.elements();

      for(int var12 = 0; var10.hasMoreElements(); this.exitIfError()) {
         RuleSymbol var14 = (RuleSymbol)var10.nextElement();
         if (!var14.getId().equals("mnextToken")) {
            this.genRule(var14, false, var12++);
         }
      }

      if (this.grammar.debuggingOutput) {
         this.genSemPredMap();
      }

      this.genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
      this.println("");
      this.genHeaderMain(this.grammar);
      this.currentOutput.close();
      this.currentOutput = null;
   }

   protected void genHeaderMain(Grammar var1) {
      String var2 = var1.getClassName() + "." + "__main__";
      String var3 = this.behavior.getHeaderAction(var2);
      if (isEmpty(var3)) {
         var3 = this.behavior.getHeaderAction("__main__");
      }

      int var4;
      if (isEmpty(var3)) {
         if (var1 instanceof LexerGrammar) {
            var4 = this.tabs;
            this.tabs = 0;
            this.println("### __main__ header action >>> ");
            this.genLexerTest();
            this.tabs = 0;
            this.println("### __main__ header action <<< ");
            this.tabs = var4;
         }
      } else {
         var4 = this.tabs;
         this.tabs = 0;
         this.println("");
         this.println("### __main__ header action >>> ");
         this.printMainFunc(var3);
         this.tabs = 0;
         this.println("### __main__ header action <<< ");
         this.tabs = var4;
      }

   }

   protected void genHeaderInit(Grammar var1) {
      String var2 = var1.getClassName() + "." + "__init__";
      String var3 = this.behavior.getHeaderAction(var2);
      if (isEmpty(var3)) {
         var3 = this.behavior.getHeaderAction("__init__");
      }

      if (!isEmpty(var3)) {
         int var4 = this.tabs;
         this.println("### __init__ header action >>> ");
         this.printActionCode(var3, 0);
         this.tabs = var4;
         this.println("### __init__ header action <<< ");
      }

   }

   protected void printMainFunc(String var1) {
      int var2 = this.tabs;
      this.tabs = 0;
      this.println("if __name__ == '__main__':");
      ++this.tabs;
      this.printActionCode(var1, 0);
      --this.tabs;
      this.tabs = var2;
   }

   public void gen(OneOrMoreBlock var1) {
      int var4 = this.tabs;
      this.genBlockPreamble(var1);
      String var3;
      if (var1.getLabel() != null) {
         var3 = "_cnt_" + var1.getLabel();
      } else {
         var3 = "_cnt" + var1.ID;
      }

      this.println("" + var3 + "= 0");
      this.println("while True:");
      ++this.tabs;
      var4 = this.tabs;
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
         this.println("### nongreedy (...)+ loop; exit depth is " + var1.exitLookaheadDepth);
         String var9 = this.getLookaheadTestExpression(var1.exitCache, var8);
         this.println("### nongreedy exit test");
         this.println("if " + var3 + " >= 1 and " + var9 + ":");
         ++this.tabs;
         this.println("break");
         --this.tabs;
      }

      int var11 = this.tabs;
      PythonBlockFinishingInfo var10 = this.genCommonBlock(var1, false);
      this.genBlockFinish(var10, "break");
      this.tabs = var11;
      this.tabs = var4;
      this.println(var3 + " += 1");
      this.tabs = var4;
      --this.tabs;
      this.println("if " + var3 + " < 1:");
      ++this.tabs;
      this.println(this.throwNoViable);
      --this.tabs;
      this.currentASTResult = var5;
   }

   public void gen(ParserGrammar var1) throws IOException {
      if (var1.debuggingOutput) {
         this.semPreds = new Vector();
      }

      this.setGrammar(var1);
      if (!(this.grammar instanceof ParserGrammar)) {
         this.antlrTool.panic("Internal error generating parser");
      }

      this.setupOutput(this.grammar.getClassName());
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.genHeader();
      this.println("### import antlr and other modules ..");
      this.println("import sys");
      this.println("import antlr");
      this.println("");
      this.println("version = sys.version.split()[0]");
      this.println("if version < '2.2.1':");
      ++this.tabs;
      this.println("False = 0");
      --this.tabs;
      this.println("if version < '2.3':");
      ++this.tabs;
      this.println("True = not False");
      --this.tabs;
      this.println("### header action >>> ");
      this.printActionCode(this.behavior.getHeaderAction(""), 0);
      this.println("### header action <<< ");
      this.println("### preamble action>>>");
      this.printActionCode(this.grammar.preambleAction.getText(), 0);
      this.println("### preamble action <<<");
      this.flushTokens();
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = "antlr." + this.grammar.getSuperClass();
      }

      this.genJavadocComment(this.grammar);
      String var3 = "";
      Token var4 = (Token)this.grammar.options.get("classHeaderPrefix");
      if (var4 != null) {
         String var5 = StringUtils.stripFrontBack(var4.getText(), "\"", "\"");
         if (var5 != null) {
            ;
         }
      }

      this.print("class " + this.parserClassName + "(" + var2);
      this.println("):");
      ++this.tabs;
      GrammarSymbol var7;
      Enumeration var9;
      if (this.grammar.debuggingOutput) {
         this.println("_ruleNames = [");
         var9 = this.grammar.rules.elements();
         boolean var6 = false;
         ++this.tabs;

         while(var9.hasMoreElements()) {
            var7 = (GrammarSymbol)var9.nextElement();
            if (var7 instanceof RuleSymbol) {
               this.println("\"" + ((RuleSymbol)var7).getId() + "\",");
            }
         }

         --this.tabs;
         this.println("]");
      }

      this.printGrammarAction(this.grammar);
      this.println("");
      this.println("def __init__(self, *args, **kwargs):");
      ++this.tabs;
      this.println(var2 + ".__init__(self, *args, **kwargs)");
      this.println("self.tokenNames = _tokenNames");
      if (this.grammar.debuggingOutput) {
         this.println("self.ruleNames  = _ruleNames");
         this.println("self.semPredNames = _semPredNames");
         this.println("self.setupDebugging(self.tokenBuf)");
      }

      if (this.grammar.buildAST) {
         this.println("self.buildTokenTypeASTClassMap()");
         this.println("self.astFactory = antlr.ASTFactory(self.getTokenTypeToASTClassMap())");
         if (this.labeledElementASTType != null) {
            this.println("self.astFactory.setASTNodeClass(" + this.labeledElementASTType + ")");
         }
      }

      this.genHeaderInit(this.grammar);
      this.println("");
      var9 = this.grammar.rules.elements();

      for(int var10 = 0; var9.hasMoreElements(); this.exitIfError()) {
         var7 = (GrammarSymbol)var9.nextElement();
         if (var7 instanceof RuleSymbol) {
            RuleSymbol var8 = (RuleSymbol)var7;
            this.genRule(var8, var8.references.size() == 0, var10++);
         }
      }

      if (this.grammar.buildAST) {
         this.genTokenASTNodeMap();
      }

      this.genTokenStrings();
      this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
      if (this.grammar.debuggingOutput) {
         this.genSemPredMap();
      }

      this.println("");
      this.tabs = 0;
      this.genHeaderMain(this.grammar);
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void gen(RuleRefElement var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genRR(" + var1 + ")");
      }

      RuleSymbol var2 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
      if (var2 != null && var2.isDefined()) {
         if (!(var2 instanceof RuleSymbol)) {
            this.antlrTool.error("'" + var1.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            this.genErrorTryForElement(var1);
            if (this.grammar instanceof TreeWalkerGrammar && var1.getLabel() != null && this.syntacticPredLevel == 0) {
               this.println(var1.getLabel() + " = antlr.ifelse(_t == antlr.ASTNULL, None, " + this.lt1Value + ")");
            }

            if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
               this.println("_saveIndex = self.text.length()");
            }

            this.printTabs();
            if (var1.idAssign != null) {
               if (var2.block.returnAction == null) {
                  this.antlrTool.warning("Rule '" + var1.targetRule + "' has no return type", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
               }

               this._print(var1.idAssign + "=");
            } else if (!(this.grammar instanceof LexerGrammar) && this.syntacticPredLevel == 0 && var2.block.returnAction != null) {
               this.antlrTool.warning("Rule '" + var1.targetRule + "' returns a value", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            }

            this.GenRuleInvocation(var1);
            if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
               this.println("self.text.setLength(_saveIndex)");
            }

            if (this.syntacticPredLevel == 0) {
               boolean var3 = this.grammar.hasSyntacticPredicate && (this.grammar.buildAST && var1.getLabel() != null || this.genAST && var1.getAutoGenType() == 1);
               if (var3) {
               }

               if (this.grammar.buildAST && var1.getLabel() != null) {
                  this.println(var1.getLabel() + "_AST = self.returnAST");
               }

               if (this.genAST) {
                  switch (var1.getAutoGenType()) {
                     case 1:
                        this.println("self.addASTChild(currentAST, self.returnAST)");
                        break;
                     case 2:
                        this.antlrTool.error("Internal: encountered ^ after rule reference");
                  }
               }

               if (this.grammar instanceof LexerGrammar && var1.getLabel() != null) {
                  this.println(var1.getLabel() + " = self._returnToken");
               }

               if (var3) {
               }
            }

            this.genErrorCatchForElement(var1);
         }
      } else {
         this.antlrTool.error("Rule '" + var1.targetRule + "' is not defined", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }
   }

   public void gen(StringLiteralElement var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genString(" + var1 + ")");
      }

      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + "");
      }

      this.genElementAST(var1);
      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      this.genMatch((GrammarAtom)var1);
      this.saveText = var2;
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling()");
      }

   }

   public void gen(TokenRangeElement var1) {
      this.genErrorTryForElement(var1);
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value);
      }

      this.genElementAST(var1);
      this.println("self.matchRange(u" + var1.beginText + ", u" + var1.endText + ")");
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
         this.println(var1.getLabel() + " = " + this.lt1Value + "");
      }

      this.genElementAST(var1);
      this.genMatch((GrammarAtom)var1);
      this.genErrorCatchForElement(var1);
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling()");
      }

   }

   public void gen(TreeElement var1) {
      this.println("_t" + var1.ID + " = _t");
      if (var1.root.getLabel() != null) {
         this.println(var1.root.getLabel() + " = antlr.ifelse(_t == antlr.ASTNULL, None, _t)");
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
         this.println("_currentAST" + var1.ID + " = currentAST.copy()");
         this.println("currentAST.root = currentAST.child");
         this.println("currentAST.child = None");
      }

      if (var1.root instanceof WildcardElement) {
         this.println("if not _t: raise antlr.MismatchedTokenException()");
      } else {
         this.genMatch(var1.root);
      }

      this.println("_t = _t.getFirstChild()");

      for(int var2 = 0; var2 < var1.getAlternatives().size(); ++var2) {
         Alternative var3 = var1.getAlternativeAt(var2);

         for(AlternativeElement var4 = var3.head; var4 != null; var4 = var4.next) {
            var4.generate();
         }
      }

      if (this.grammar.buildAST) {
         this.println("currentAST = _currentAST" + var1.ID + "");
      }

      this.println("_t = _t" + var1.ID + "");
      this.println("_t = _t.getNextSibling()");
   }

   public void gen(TreeWalkerGrammar var1) throws IOException {
      this.setGrammar(var1);
      if (!(this.grammar instanceof TreeWalkerGrammar)) {
         this.antlrTool.panic("Internal error generating tree-walker");
      }

      this.setupOutput(this.grammar.getClassName());
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.genHeader();
      this.println("### import antlr and other modules ..");
      this.println("import sys");
      this.println("import antlr");
      this.println("");
      this.println("version = sys.version.split()[0]");
      this.println("if version < '2.2.1':");
      ++this.tabs;
      this.println("False = 0");
      --this.tabs;
      this.println("if version < '2.3':");
      ++this.tabs;
      this.println("True = not False");
      --this.tabs;
      this.println("### header action >>> ");
      this.printActionCode(this.behavior.getHeaderAction(""), 0);
      this.println("### header action <<< ");
      this.flushTokens();
      this.println("### user code>>>");
      this.printActionCode(this.grammar.preambleAction.getText(), 0);
      this.println("### user code<<<");
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = "antlr." + this.grammar.getSuperClass();
      }

      this.println("");
      String var3 = "";
      Token var4 = (Token)this.grammar.options.get("classHeaderPrefix");
      if (var4 != null) {
         String var5 = StringUtils.stripFrontBack(var4.getText(), "\"", "\"");
         if (var5 != null) {
            ;
         }
      }

      this.genJavadocComment(this.grammar);
      this.println("class " + this.treeWalkerClassName + "(" + var2 + "):");
      ++this.tabs;
      this.println("");
      this.println("# ctor ..");
      this.println("def __init__(self, *args, **kwargs):");
      ++this.tabs;
      this.println(var2 + ".__init__(self, *args, **kwargs)");
      this.println("self.tokenNames = _tokenNames");
      this.genHeaderInit(this.grammar);
      --this.tabs;
      this.println("");
      this.printGrammarAction(this.grammar);
      Enumeration var10 = this.grammar.rules.elements();
      int var6 = 0;

      for(String var7 = ""; var10.hasMoreElements(); this.exitIfError()) {
         GrammarSymbol var8 = (GrammarSymbol)var10.nextElement();
         if (var8 instanceof RuleSymbol) {
            RuleSymbol var9 = (RuleSymbol)var8;
            this.genRule(var9, var9.references.size() == 0, var6++);
         }
      }

      this.genTokenStrings();
      this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
      this.tabs = 0;
      this.genHeaderMain(this.grammar);
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void gen(WildcardElement var1) {
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + "");
      }

      this.genElementAST(var1);
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("if not _t:");
         ++this.tabs;
         this.println("raise antlr.MismatchedTokenException()");
         --this.tabs;
      } else if (this.grammar instanceof LexerGrammar) {
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("_saveIndex = self.text.length()");
         }

         this.println("self.matchNot(antlr.EOF_CHAR)");
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("self.text.setLength(_saveIndex)");
         }
      } else {
         this.println("self.matchNot(" + this.getValueString(1, false) + ")");
      }

      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling()");
      }

   }

   public void gen(ZeroOrMoreBlock var1) {
      int var2 = this.tabs;
      this.genBlockPreamble(var1);
      this.println("while True:");
      ++this.tabs;
      var2 = this.tabs;
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
         this.println("###  nongreedy exit test");
         this.println("if (" + var8 + "):");
         ++this.tabs;
         this.println("break");
         --this.tabs;
      }

      int var10 = this.tabs;
      PythonBlockFinishingInfo var9 = this.genCommonBlock(var1, false);
      this.genBlockFinish(var9, "break");
      this.tabs = var10;
      this.tabs = var2;
      --this.tabs;
      this.currentASTResult = var4;
   }

   protected void genAlt(Alternative var1, AlternativeBlock var2) {
      boolean var3 = this.genAST;
      this.genAST = this.genAST && var1.getAutoGen();
      boolean var4 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGen();
      Hashtable var5 = this.treeVariableMap;
      this.treeVariableMap = new Hashtable();
      if (var1.exceptionSpec != null) {
         this.println("try:");
         ++this.tabs;
      }

      this.println("pass");

      for(AlternativeElement var6 = var1.head; !(var6 instanceof BlockEndElement); var6 = var6.next) {
         var6.generate();
      }

      if (this.genAST) {
         if (var2 instanceof RuleBlock) {
            RuleBlock var7 = (RuleBlock)var2;
            if (this.grammar.hasSyntacticPredicate) {
            }

            this.println(var7.getRuleName() + "_AST = currentAST.root");
            if (this.grammar.hasSyntacticPredicate) {
            }
         } else if (var2.getLabel() != null) {
            this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }
      }

      if (var1.exceptionSpec != null) {
         --this.tabs;
         this.genErrorHandler(var1.exceptionSpec);
      }

      this.genAST = var3;
      this.saveText = var4;
      this.treeVariableMap = var5;
   }

   protected void genBitsets(Vector var1, int var2) {
      this.println("");

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         BitSet var4 = (BitSet)var1.elementAt(var3);
         var4.growToInclude(var2);
         this.genBitSet(var4, var3);
      }

   }

   private void genBitSet(BitSet var1, int var2) {
      int var3 = this.tabs;
      this.tabs = 0;
      this.println("");
      this.println("### generate bit set");
      this.println("def mk" + this.getBitsetName(var2) + "(): ");
      ++this.tabs;
      int var4 = var1.lengthInLongWords();
      if (var4 < 8) {
         this.println("### var1");
         this.println("data = [ " + var1.toStringOfWords() + "]");
      } else {
         this.println("data = [0L] * " + var4 + " ### init list");
         long[] var5 = var1.toPackedArray();
         int var6 = 0;

         label38:
         while(true) {
            while(true) {
               if (var6 >= var5.length) {
                  break label38;
               }

               if (var5[var6] == 0L) {
                  ++var6;
               } else if (var6 + 1 != var5.length && var5[var6] == var5[var6 + 1]) {
                  int var7;
                  for(var7 = var6 + 1; var7 < var5.length && var5[var7] == var5[var6]; ++var7) {
                  }

                  long var8 = var5[var6];
                  this.println("for x in xrange(" + var6 + ", " + var7 + "):");
                  ++this.tabs;
                  this.println("data[x] = " + var8 + "L");
                  --this.tabs;
                  var6 = var7;
               } else {
                  this.println("data[" + var6 + "] =" + var5[var6] + "L");
                  ++var6;
               }
            }
         }
      }

      this.println("return data");
      --this.tabs;
      this.println(this.getBitsetName(var2) + " = antlr.BitSet(mk" + this.getBitsetName(var2) + "())");
      this.tabs = var3;
   }

   private void genBlockFinish(PythonBlockFinishingInfo var1, String var2) {
      if (var1.needAnErrorClause && (var1.generatedAnIf || var1.generatedSwitch)) {
         if (var1.generatedAnIf) {
            this.println("else:");
         }

         ++this.tabs;
         this.println(var2);
         --this.tabs;
      }

      if (var1.postscript != null) {
         this.println(var1.postscript);
      }

   }

   private void genBlockFinish1(PythonBlockFinishingInfo var1, String var2) {
      if (var1.needAnErrorClause && (var1.generatedAnIf || var1.generatedSwitch)) {
         if (var1.generatedAnIf) {
            this.println("else:");
         }

         ++this.tabs;
         this.println(var2);
         --this.tabs;
         if (var1.generatedAnIf) {
         }
      }

      if (var1.postscript != null) {
         this.println(var1.postscript);
      }

   }

   protected void genBlockInitAction(AlternativeBlock var1) {
      if (var1.initAction != null) {
         this.printAction(this.processActionForSpecialSymbols(var1.initAction, var1.getLine(), this.currentRule, (ActionTransInfo)null));
      }

   }

   protected void genBlockPreamble(AlternativeBlock var1) {
      if (var1 instanceof RuleBlock) {
         RuleBlock var2 = (RuleBlock)var1;
         if (var2.labeledElements != null) {
            for(int var3 = 0; var3 < var2.labeledElements.size(); ++var3) {
               AlternativeElement var4 = (AlternativeElement)var2.labeledElements.elementAt(var3);
               if (var4 instanceof RuleRefElement || var4 instanceof AlternativeBlock && !(var4 instanceof RuleBlock) && !(var4 instanceof SynPredBlock)) {
                  if (!(var4 instanceof RuleRefElement) && ((AlternativeBlock)var4).not && this.analyzer.subruleCanBeInverted((AlternativeBlock)var4, this.grammar instanceof LexerGrammar)) {
                     this.println(var4.getLabel() + " = " + this.labeledElementInit);
                     if (this.grammar.buildAST) {
                        this.genASTDeclaration(var4);
                     }
                  } else {
                     if (this.grammar.buildAST) {
                        this.genASTDeclaration(var4);
                     }

                     if (this.grammar instanceof LexerGrammar) {
                        this.println(var4.getLabel() + " = None");
                     }

                     if (this.grammar instanceof TreeWalkerGrammar) {
                        this.println(var4.getLabel() + " = " + this.labeledElementInit);
                     }
                  }
               } else {
                  this.println(var4.getLabel() + " = " + this.labeledElementInit);
                  if (this.grammar.buildAST) {
                     if (var4 instanceof GrammarAtom && ((GrammarAtom)var4).getASTNodeType() != null) {
                        GrammarAtom var5 = (GrammarAtom)var4;
                        this.genASTDeclaration(var4, var5.getASTNodeType());
                     } else {
                        this.genASTDeclaration(var4);
                     }
                  }
               }
            }
         }
      }

   }

   protected void genCases(BitSet var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genCases(" + var1 + ")");
      }

      int[] var2 = var1.toArray();
      boolean var3 = this.grammar instanceof LexerGrammar ? true : true;
      boolean var4 = true;
      boolean var5 = true;
      this.print("elif la1 and la1 in ");
      int var6;
      if (!(this.grammar instanceof LexerGrammar)) {
         this._print("[");

         for(var6 = 0; var6 < var2.length; ++var6) {
            this._print(this.getValueString(var2[var6], false));
            if (var6 + 1 < var2.length) {
               this._print(",");
            }
         }

         this._print("]:\n");
      } else {
         this._print("u'");

         for(var6 = 0; var6 < var2.length; ++var6) {
            this._print(this.getValueString(var2[var6], false));
         }

         this._print("':\n");
      }
   }

   public PythonBlockFinishingInfo genCommonBlock(AlternativeBlock var1, boolean var2) {
      int var3 = this.tabs;
      int var4 = 0;
      boolean var5 = false;
      int var6 = 0;
      PythonBlockFinishingInfo var7 = new PythonBlockFinishingInfo();
      boolean var8 = this.genAST;
      this.genAST = this.genAST && var1.getAutoGen();
      boolean var9 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGen();
      String var21;
      if (var1.not && this.analyzer.subruleCanBeInverted(var1, this.grammar instanceof LexerGrammar)) {
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("special case: ~(subrule)");
         }

         Lookahead var20 = this.analyzer.look(1, (AlternativeBlock)var1);
         if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
            this.println(var1.getLabel() + " = " + this.lt1Value);
         }

         this.genElementAST(var1);
         var21 = "";
         if (this.grammar instanceof TreeWalkerGrammar) {
            var21 = "_t, ";
         }

         this.println("self.match(" + var21 + this.getBitsetName(this.markBitsetForGen(var20.fset)) + ")");
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("_t = _t.getNextSibling()");
         }

         return var7;
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
               return var7;
            }
         }

         int var19 = 0;

         int var11;
         for(var11 = 0; var11 < var1.getAlternatives().size(); ++var11) {
            Alternative var12 = var1.getAlternativeAt(var11);
            if (suitableForCaseExpression(var12)) {
               ++var19;
            }
         }

         int var22;
         if (var19 >= this.makeSwitchThreshold) {
            var21 = this.lookaheadString(1);
            var5 = true;
            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println("if not _t:");
               ++this.tabs;
               this.println("_t = antlr.ASTNULL");
               --this.tabs;
            }

            this.println("la1 = " + var21);
            this.println("if False:");
            ++this.tabs;
            this.println("pass");
            --this.tabs;

            for(var22 = 0; var22 < var1.alternatives.size(); ++var22) {
               Alternative var13 = var1.getAlternativeAt(var22);
               if (suitableForCaseExpression(var13)) {
                  Lookahead var14 = var13.cache[1];
                  if (var14.fset.degree() == 0 && !var14.containsEpsilon()) {
                     this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), var13.head.getLine(), var13.head.getColumn());
                  } else {
                     this.genCases(var14.fset);
                     ++this.tabs;
                     this.genAlt(var13, var1);
                     --this.tabs;
                  }
               }
            }

            this.println("else:");
            ++this.tabs;
         }

         var11 = this.grammar instanceof LexerGrammar ? this.grammar.maxk : 0;

         for(var22 = var11; var22 >= 0; --var22) {
            for(int var23 = 0; var23 < var1.alternatives.size(); ++var23) {
               Alternative var25 = var1.getAlternativeAt(var23);
               if (this.DEBUG_CODE_GENERATOR) {
                  System.out.println("genAlt: " + var23);
               }

               if (var5 && suitableForCaseExpression(var25)) {
                  if (this.DEBUG_CODE_GENERATOR) {
                     System.out.println("ignoring alt because it was in the switch");
                  }
               } else {
                  boolean var16 = false;
                  String var15;
                  if (this.grammar instanceof LexerGrammar) {
                     int var17 = var25.lookaheadDepth;
                     if (var17 == Integer.MAX_VALUE) {
                        var17 = this.grammar.maxk;
                     }

                     while(var17 >= 1 && var25.cache[var17].containsEpsilon()) {
                        --var17;
                     }

                     if (var17 != var22) {
                        if (this.DEBUG_CODE_GENERATOR) {
                           System.out.println("ignoring alt because effectiveDepth!=altDepth" + var17 + "!=" + var22);
                        }
                        continue;
                     }

                     var16 = this.lookaheadIsEmpty(var25, var17);
                     var15 = this.getLookaheadTestExpression(var25, var17);
                  } else {
                     var16 = this.lookaheadIsEmpty(var25, this.grammar.maxk);
                     var15 = this.getLookaheadTestExpression(var25, this.grammar.maxk);
                  }

                  if (var25.cache[1].fset.degree() > 127 && suitableForCaseExpression(var25)) {
                     if (var4 == 0) {
                        this.println("<m1> if " + var15 + ":");
                     } else {
                        this.println("<m2> elif " + var15 + ":");
                     }
                  } else if (var16 && var25.semPred == null && var25.synPred == null) {
                     if (var4 == 0) {
                        this.println("##<m3> <closing");
                     } else {
                        this.println("else: ## <m4>");
                        ++this.tabs;
                     }

                     var7.needAnErrorClause = false;
                  } else {
                     if (var25.semPred != null) {
                        ActionTransInfo var26 = new ActionTransInfo();
                        String var18 = this.processActionForSpecialSymbols(var25.semPred, var1.line, this.currentRule, var26);
                        if ((this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar) && this.grammar.debuggingOutput) {
                           var15 = "(" + var15 + " and fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING, " + this.addSemPred(this.charFormatter.escapeString(var18)) + ", " + var18 + "))";
                        } else {
                           var15 = "(" + var15 + " and (" + var18 + "))";
                        }
                     }

                     if (var4 > 0) {
                        if (var25.synPred != null) {
                           this.println("else:");
                           ++this.tabs;
                           this.genSynPred(var25.synPred, var15);
                           ++var6;
                        } else {
                           this.println("elif " + var15 + ":");
                        }
                     } else if (var25.synPred != null) {
                        this.genSynPred(var25.synPred, var15);
                     } else {
                        if (this.grammar instanceof TreeWalkerGrammar) {
                           this.println("if not _t:");
                           ++this.tabs;
                           this.println("_t = antlr.ASTNULL");
                           --this.tabs;
                        }

                        this.println("if " + var15 + ":");
                     }
                  }

                  ++var4;
                  ++this.tabs;
                  this.genAlt(var25, var1);
                  --this.tabs;
               }
            }
         }

         String var24 = "";
         this.genAST = var8;
         this.saveText = var9;
         if (var5) {
            var7.postscript = var24;
            var7.generatedSwitch = true;
            var7.generatedAnIf = var4 > 0;
         } else {
            var7.postscript = var24;
            var7.generatedSwitch = false;
            var7.generatedAnIf = var4 > 0;
         }

         return var7;
      }
   }

   private static boolean suitableForCaseExpression(Alternative var0) {
      return var0.lookaheadDepth == 1 && var0.semPred == null && !var0.cache[1].containsEpsilon() && var0.cache[1].fset.degree() <= 127;
   }

   private void genElementAST(AlternativeElement var1) {
      if (this.grammar instanceof TreeWalkerGrammar && !this.grammar.buildAST) {
         if (var1.getLabel() == null) {
            String var7 = this.lt1Value;
            String var8 = "tmp" + this.astVarNumber + "_AST";
            ++this.astVarNumber;
            this.mapTreeVariable(var1, var8);
            this.println(var8 + "_in = " + var7);
         }

      } else {
         if (this.grammar.buildAST && this.syntacticPredLevel == 0) {
            boolean var2 = this.genAST && (var1.getLabel() != null || var1.getAutoGenType() != 3);
            if (var1.getAutoGenType() != 3 && var1 instanceof TokenRefElement) {
               var2 = true;
            }

            boolean var3 = this.grammar.hasSyntacticPredicate && var2;
            String var4;
            String var5;
            if (var1.getLabel() != null) {
               var4 = var1.getLabel();
               var5 = var1.getLabel();
            } else {
               var4 = this.lt1Value;
               var5 = "tmp" + this.astVarNumber;
               ++this.astVarNumber;
            }

            if (var2) {
               if (var1 instanceof GrammarAtom) {
                  GrammarAtom var6 = (GrammarAtom)var1;
                  if (var6.getASTNodeType() != null) {
                     this.genASTDeclaration(var1, var5, var6.getASTNodeType());
                  } else {
                     this.genASTDeclaration(var1, var5, this.labeledElementASTType);
                  }
               } else {
                  this.genASTDeclaration(var1, var5, this.labeledElementASTType);
               }
            }

            String var9 = var5 + "_AST";
            this.mapTreeVariable(var1, var9);
            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println(var9 + "_in = None");
            }

            if (var3) {
            }

            if (var1.getLabel() != null) {
               if (var1 instanceof GrammarAtom) {
                  this.println(var9 + " = " + this.getASTCreateString((GrammarAtom)var1, var4) + "");
               } else {
                  this.println(var9 + " = " + this.getASTCreateString(var4) + "");
               }
            }

            if (var1.getLabel() == null && var2) {
               var4 = this.lt1Value;
               if (var1 instanceof GrammarAtom) {
                  this.println(var9 + " = " + this.getASTCreateString((GrammarAtom)var1, var4) + "");
               } else {
                  this.println(var9 + " = " + this.getASTCreateString(var4) + "");
               }

               if (this.grammar instanceof TreeWalkerGrammar) {
                  this.println(var9 + "_in = " + var4 + "");
               }
            }

            if (this.genAST) {
               switch (var1.getAutoGenType()) {
                  case 1:
                     this.println("self.addASTChild(currentAST, " + var9 + ")");
                     break;
                  case 2:
                     this.println("self.makeASTRoot(currentAST, " + var9 + ")");
               }
            }

            if (var3) {
            }
         }

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
            this.genErrorHandler(var4);
         }

      }
   }

   private void genErrorHandler(ExceptionSpec var1) {
      for(int var2 = 0; var2 < var1.handlers.size(); ++var2) {
         ExceptionHandler var3 = (ExceptionHandler)var1.handlers.elementAt(var2);
         String var4 = "";
         String var5 = "";
         String var6 = var3.exceptionTypeAndName.getText();
         var6 = this.removeAssignmentFromDeclaration(var6);
         var6 = var6.trim();

         for(int var7 = var6.length() - 1; var7 >= 0; --var7) {
            if (!Character.isLetterOrDigit(var6.charAt(var7)) && var6.charAt(var7) != '_') {
               var4 = var6.substring(0, var7);
               var5 = var6.substring(var7 + 1);
               break;
            }
         }

         this.println("except " + var4 + ", " + var5 + ":");
         ++this.tabs;
         if (this.grammar.hasSyntacticPredicate) {
            this.println("if not self.inputState.guessing:");
            ++this.tabs;
         }

         ActionTransInfo var8 = new ActionTransInfo();
         this.printAction(this.processActionForSpecialSymbols(var3.action.getText(), var3.action.getLine(), this.currentRule, var8));
         if (this.grammar.hasSyntacticPredicate) {
            --this.tabs;
            this.println("else:");
            ++this.tabs;
            this.println("raise " + var5);
            --this.tabs;
         }

         --this.tabs;
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
            this.println("try: # for error handling");
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
         this.println(var2 + "_AST = None");
         this.declaredASTVariables.put(var1, var1);
      }
   }

   protected void genHeader() {
      this.println("### $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".py\"$");
   }

   protected void genLexerTest() {
      String var1 = this.grammar.getClassName();
      this.println("if __name__ == '__main__' :");
      ++this.tabs;
      this.println("import sys");
      this.println("import antlr");
      this.println("import " + var1);
      this.println("");
      this.println("### create lexer - shall read from stdin");
      this.println("try:");
      ++this.tabs;
      this.println("for token in " + var1 + ".Lexer():");
      ++this.tabs;
      this.println("print token");
      this.println("");
      --this.tabs;
      --this.tabs;
      this.println("except antlr.TokenStreamException, e:");
      ++this.tabs;
      this.println("print \"error: exception caught while lexing: \", e");
      --this.tabs;
      --this.tabs;
   }

   private void genLiteralsTest() {
      this.println("### option { testLiterals=true } ");
      this.println("_ttype = self.testLiteralsTable(_ttype)");
   }

   private void genLiteralsTestForPartialToken() {
      this.println("_ttype = self.testLiteralsTable(self.text.getString(), _begin, self.text.length()-_begin, _ttype)");
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
      String var2 = "";
      if (this.grammar instanceof TreeWalkerGrammar) {
         var2 = "_t,";
      }

      if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
         this.println("_saveIndex = self.text.length()");
      }

      this.print(var1.not ? "self.matchNot(" : "self.match(");
      this._print(var2);
      if (var1.atomText.equals("EOF")) {
         this._print("EOF_TYPE");
      } else {
         this._print(var1.atomText);
      }

      this._println(")");
      if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
         this.println("self.text.setLength(_saveIndex)");
      }

   }

   protected void genMatchUsingAtomTokenType(GrammarAtom var1) {
      String var2 = "";
      if (this.grammar instanceof TreeWalkerGrammar) {
         var2 = "_t,";
      }

      Object var3 = null;
      String var4 = var2 + this.getValueString(var1.getType(), true);
      this.println((var1.not ? "self.matchNot(" : "self.match(") + var4 + ")");
   }

   public void genNextToken() {
      boolean var1 = false;

      RuleSymbol var3;
      for(int var2 = 0; var2 < this.grammar.rules.size(); ++var2) {
         var3 = (RuleSymbol)this.grammar.rules.elementAt(var2);
         if (var3.isDefined() && var3.access.equals("public")) {
            var1 = true;
            break;
         }
      }

      if (!var1) {
         this.println("");
         this.println("def nextToken(self): ");
         ++this.tabs;
         this.println("try:");
         ++this.tabs;
         this.println("self.uponEOF()");
         --this.tabs;
         this.println("except antlr.CharStreamIOException, csioe:");
         ++this.tabs;
         this.println("raise antlr.TokenStreamIOException(csioe.io)");
         --this.tabs;
         this.println("except antlr.CharStreamException, cse:");
         ++this.tabs;
         this.println("raise antlr.TokenStreamException(str(cse))");
         --this.tabs;
         this.println("return antlr.CommonToken(type=EOF_TYPE, text=\"\")");
         --this.tabs;
      } else {
         RuleBlock var11 = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
         var3 = new RuleSymbol("mnextToken");
         var3.setDefined();
         var3.setBlock(var11);
         var3.access = "private";
         this.grammar.define(var3);
         this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var11);
         String var5 = null;
         if (((LexerGrammar)this.grammar).filterMode) {
            var5 = ((LexerGrammar)this.grammar).filterRule;
         }

         this.println("");
         this.println("def nextToken(self):");
         ++this.tabs;
         this.println("while True:");
         ++this.tabs;
         this.println("try: ### try again ..");
         ++this.tabs;
         this.println("while True:");
         ++this.tabs;
         int var6 = this.tabs;
         this.println("_token = None");
         this.println("_ttype = INVALID_TYPE");
         if (((LexerGrammar)this.grammar).filterMode) {
            this.println("self.setCommitToPath(False)");
            if (var5 != null) {
               if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(var5))) {
                  this.grammar.antlrTool.error("Filter rule " + var5 + " does not exist in this lexer");
               } else {
                  RuleSymbol var7 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(var5));
                  if (!var7.isDefined()) {
                     this.grammar.antlrTool.error("Filter rule " + var5 + " does not exist in this lexer");
                  } else if (var7.access.equals("public")) {
                     this.grammar.antlrTool.error("Filter rule " + var5 + " must be protected");
                  }
               }

               this.println("_m = self.mark()");
            }
         }

         this.println("self.resetText()");
         this.println("try: ## for char stream error handling");
         ++this.tabs;
         var6 = this.tabs;
         this.println("try: ##for lexical error handling");
         ++this.tabs;
         var6 = this.tabs;

         for(int var12 = 0; var12 < var11.getAlternatives().size(); ++var12) {
            Alternative var8 = var11.getAlternativeAt(var12);
            if (var8.cache[1].containsEpsilon()) {
               RuleRefElement var9 = (RuleRefElement)var8.head;
               String var10 = CodeGenerator.decodeLexerRuleName(var9.targetRule);
               this.antlrTool.warning("public lexical rule " + var10 + " is optional (can match \"nothing\")");
            }
         }

         String var13 = System.getProperty("line.separator");
         PythonBlockFinishingInfo var14 = this.genCommonBlock(var11, false);
         String var15 = "";
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var5 == null) {
               var15 = var15 + "self.filterdefault(self.LA(1))";
            } else {
               var15 = var15 + "self.filterdefault(self.LA(1), self.m" + var5 + ", False)";
            }
         } else {
            var15 = "self.default(self.LA(1))";
         }

         this.genBlockFinish1(var14, var15);
         this.tabs = var6;
         if (((LexerGrammar)this.grammar).filterMode && var5 != null) {
            this.println("self.commit()");
         }

         this.println("if not self._returnToken:");
         ++this.tabs;
         this.println("raise antlr.TryAgain ### found SKIP token");
         --this.tabs;
         if (((LexerGrammar)this.grammar).getTestLiterals()) {
            this.println("### option { testLiterals=true } ");
            this.println("self.testForLiteral(self._returnToken)");
         }

         this.println("### return token to caller");
         this.println("return self._returnToken");
         --this.tabs;
         this.println("### handle lexical errors ....");
         this.println("except antlr.RecognitionException, e:");
         ++this.tabs;
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var5 == null) {
               this.println("if not self.getCommitToPath():");
               ++this.tabs;
               this.println("self.consume()");
               this.println("raise antlr.TryAgain()");
               --this.tabs;
            } else {
               this.println("if not self.getCommitToPath(): ");
               ++this.tabs;
               this.println("self.rewind(_m)");
               this.println("self.resetText()");
               this.println("try:");
               ++this.tabs;
               this.println("self.m" + var5 + "(False)");
               --this.tabs;
               this.println("except antlr.RecognitionException, ee:");
               ++this.tabs;
               this.println("### horrendous failure: error in filter rule");
               this.println("self.reportError(ee)");
               this.println("self.consume()");
               --this.tabs;
               this.println("raise antlr.TryAgain()");
               --this.tabs;
            }
         }

         if (var11.getDefaultErrorHandler()) {
            this.println("self.reportError(e)");
            this.println("self.consume()");
         } else {
            this.println("raise antlr.TokenStreamRecognitionException(e)");
         }

         --this.tabs;
         --this.tabs;
         this.println("### handle char stream errors ...");
         this.println("except antlr.CharStreamException,cse:");
         ++this.tabs;
         this.println("if isinstance(cse, antlr.CharStreamIOException):");
         ++this.tabs;
         this.println("raise antlr.TokenStreamIOException(cse.io)");
         --this.tabs;
         this.println("else:");
         ++this.tabs;
         this.println("raise antlr.TokenStreamException(str(cse))");
         --this.tabs;
         --this.tabs;
         --this.tabs;
         --this.tabs;
         this.println("except antlr.TryAgain:");
         ++this.tabs;
         this.println("pass");
         --this.tabs;
         --this.tabs;
      }
   }

   public void genRule(RuleSymbol var1, boolean var2, int var3) {
      this.tabs = 1;
      if (!var1.isDefined()) {
         this.antlrTool.error("undefined rule: " + var1.getId());
      } else {
         RuleBlock var4 = var1.getBlock();
         this.currentRule = var4;
         this.currentASTResult = var1.getId();
         this.declaredASTVariables.clear();
         boolean var5 = this.genAST;
         this.genAST = this.genAST && var4.getAutoGen();
         this.saveText = var4.getAutoGen();
         this.genJavadocComment(var1);
         this.print("def " + var1.getId() + "(");
         this._print(this.commonExtraParams);
         if (this.commonExtraParams.length() != 0 && var4.argAction != null) {
            this._print(",");
         }

         if (var4.argAction != null) {
            this._println("");
            ++this.tabs;
            this.println(var4.argAction);
            --this.tabs;
            this.print("):");
         } else {
            this._print("):");
         }

         this.println("");
         ++this.tabs;
         if (var4.returnAction != null) {
            if (var4.returnAction.indexOf(61) >= 0) {
               this.println(var4.returnAction);
            } else {
               this.println(this.extractIdOfAction(var4.returnAction, var4.getLine(), var4.getColumn()) + " = None");
            }
         }

         this.println(this.commonLocalVars);
         if (this.grammar.traceRules) {
            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println("self.traceIn(\"" + var1.getId() + "\",_t)");
            } else {
               this.println("self.traceIn(\"" + var1.getId() + "\")");
            }
         }

         if (this.grammar instanceof LexerGrammar) {
            if (var1.getId().equals("mEOF")) {
               this.println("_ttype = EOF_TYPE");
            } else {
               this.println("_ttype = " + var1.getId().substring(1));
            }

            this.println("_saveIndex = 0");
         }

         if (this.grammar.debuggingOutput) {
            if (this.grammar instanceof ParserGrammar) {
               this.println("self.fireEnterRule(" + var3 + ", 0)");
            } else if (this.grammar instanceof LexerGrammar) {
               this.println("self.fireEnterRule(" + var3 + ", _ttype)");
            }
         }

         if (this.grammar.debuggingOutput || this.grammar.traceRules) {
            this.println("try: ### debugging");
            ++this.tabs;
         }

         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println(var1.getId() + "_AST_in = None");
            this.println("if _t != antlr.ASTNULL:");
            ++this.tabs;
            this.println(var1.getId() + "_AST_in = _t");
            --this.tabs;
         }

         if (this.grammar.buildAST) {
            this.println("self.returnAST = None");
            this.println("currentAST = antlr.ASTPair()");
            this.println(var1.getId() + "_AST = None");
         }

         this.genBlockPreamble(var4);
         this.genBlockInitAction(var4);
         ExceptionSpec var6 = var4.findExceptionSpec("");
         if (var6 != null || var4.getDefaultErrorHandler()) {
            this.println("try:      ## for error handling");
            ++this.tabs;
         }

         int var7 = this.tabs;
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
            PythonBlockFinishingInfo var11 = this.genCommonBlock(var4, false);
            this.genBlockFinish(var11, this.throwNoViable);
         }

         this.tabs = var7;
         if (var6 != null || var4.getDefaultErrorHandler()) {
            --this.tabs;
            this.println("");
         }

         if (var6 != null) {
            this.genErrorHandler(var6);
         } else if (var4.getDefaultErrorHandler()) {
            this.println("except " + this.exceptionThrown + ", ex:");
            ++this.tabs;
            if (this.grammar.hasSyntacticPredicate) {
               this.println("if not self.inputState.guessing:");
               ++this.tabs;
            }

            this.println("self.reportError(ex)");
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
               Lookahead var10 = this.grammar.theLLkAnalyzer.FOLLOW(1, var4.endNode);
               var9 = this.getBitsetName(this.markBitsetForGen(var10.fset));
               this.println("self.consume()");
               this.println("self.consumeUntil(" + var9 + ")");
            } else {
               this.println("if _t:");
               ++this.tabs;
               this.println("_t = _t.getNextSibling()");
               --this.tabs;
            }

            if (this.grammar.hasSyntacticPredicate) {
               --this.tabs;
               this.println("else:");
               ++this.tabs;
               this.println("raise ex");
               --this.tabs;
            }

            --this.tabs;
            this.println("");
         }

         if (this.grammar.buildAST) {
            this.println("self.returnAST = " + var1.getId() + "_AST");
         }

         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("self._retTree = _t");
         }

         if (var4.getTestLiterals()) {
            if (var1.access.equals("protected")) {
               this.genLiteralsTestForPartialToken();
            } else {
               this.genLiteralsTest();
            }
         }

         if (this.grammar instanceof LexerGrammar) {
            this.println("self.set_return_token(_createToken, _token, _ttype, _begin)");
         }

         if (var4.returnAction != null) {
            this.println("return " + this.extractIdOfAction(var4.returnAction, var4.getLine(), var4.getColumn()) + "");
         }

         if (this.grammar.debuggingOutput || this.grammar.traceRules) {
            --this.tabs;
            this.println("finally:  ### debugging");
            ++this.tabs;
            if (this.grammar.debuggingOutput) {
               if (this.grammar instanceof ParserGrammar) {
                  this.println("self.fireExitRule(" + var3 + ", 0)");
               } else if (this.grammar instanceof LexerGrammar) {
                  this.println("self.fireExitRule(" + var3 + ", _ttype)");
               }
            }

            if (this.grammar.traceRules) {
               if (this.grammar instanceof TreeWalkerGrammar) {
                  this.println("self.traceOut(\"" + var1.getId() + "\", _t)");
               } else {
                  this.println("self.traceOut(\"" + var1.getId() + "\")");
               }
            }

            --this.tabs;
         }

         --this.tabs;
         this.println("");
         this.genAST = var5;
      }
   }

   private void GenRuleInvocation(RuleRefElement var1) {
      this._print("self." + var1.targetRule + "(");
      if (this.grammar instanceof LexerGrammar) {
         if (var1.getLabel() != null) {
            this._print("True");
         } else {
            this._print("False");
         }

         if (this.commonExtraArgs.length() != 0 || var1.args != null) {
            this._print(", ");
         }
      }

      this._print(this.commonExtraArgs);
      if (this.commonExtraArgs.length() != 0 && var1.args != null) {
         this._print(", ");
      }

      RuleSymbol var2 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
      if (var1.args != null) {
         ActionTransInfo var3 = new ActionTransInfo();
         String var4 = this.processActionForSpecialSymbols(var1.args, 0, this.currentRule, var3);
         if (var3.assignToRoot || var3.refRuleRoot != null) {
            this.antlrTool.error("Arguments of rule reference '" + var1.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }

         this._print(var4);
         if (var2.block.argAction == null) {
            this.antlrTool.warning("Rule '" + var1.targetRule + "' accepts no arguments", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      } else if (var2.block.argAction != null) {
         this.antlrTool.warning("Missing parameters on reference to rule " + var1.targetRule, this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }

      this._println(")");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = self._retTree");
      }

   }

   protected void genSemPred(String var1, int var2) {
      ActionTransInfo var3 = new ActionTransInfo();
      var1 = this.processActionForSpecialSymbols(var1, var2, this.currentRule, var3);
      String var4 = this.charFormatter.escapeString(var1);
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         var1 = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + this.addSemPred(var4) + ", " + var1 + ")";
      }

      this.println("if not " + var1 + ":");
      ++this.tabs;
      this.println("raise antlr.SemanticException(\"" + var4 + "\")");
      --this.tabs;
   }

   protected void genSemPredMap() {
      Enumeration var1 = this.semPreds.elements();
      this.println("_semPredNames = [");
      ++this.tabs;

      while(var1.hasMoreElements()) {
         this.println("\"" + var1.nextElement() + "\",");
      }

      --this.tabs;
      this.println("]");
   }

   protected void genSynPred(SynPredBlock var1, String var2) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen=>(" + var1 + ")");
      }

      this.println("synPredMatched" + var1.ID + " = False");
      this.println("if " + var2 + ":");
      ++this.tabs;
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t" + var1.ID + " = _t");
      } else {
         this.println("_m" + var1.ID + " = self.mark()");
      }

      this.println("synPredMatched" + var1.ID + " = True");
      this.println("self.inputState.guessing += 1");
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         this.println("self.fireSyntacticPredicateStarted()");
      }

      ++this.syntacticPredLevel;
      this.println("try:");
      ++this.tabs;
      this.gen((AlternativeBlock)var1);
      --this.tabs;
      this.println("except " + this.exceptionThrown + ", pe:");
      ++this.tabs;
      this.println("synPredMatched" + var1.ID + " = False");
      --this.tabs;
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t" + var1.ID + "");
      } else {
         this.println("self.rewind(_m" + var1.ID + ")");
      }

      this.println("self.inputState.guessing -= 1");
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         this.println("if synPredMatched" + var1.ID + ":");
         ++this.tabs;
         this.println("self.fireSyntacticPredicateSucceeded()");
         --this.tabs;
         this.println("else:");
         ++this.tabs;
         this.println("self.fireSyntacticPredicateFailed()");
         --this.tabs;
      }

      --this.syntacticPredLevel;
      --this.tabs;
      this.println("if synPredMatched" + var1.ID + ":");
   }

   public void genTokenStrings() {
      int var1 = this.tabs;
      this.tabs = 0;
      this.println("");
      this.println("_tokenNames = [");
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
            this._print(", ");
         }

         this._println("");
      }

      --this.tabs;
      this.println("]");
      this.tabs = var1;
   }

   protected void genTokenASTNodeMap() {
      this.println("");
      this.println("def buildTokenTypeASTClassMap(self):");
      ++this.tabs;
      boolean var1 = false;
      int var2 = 0;
      Vector var3 = this.grammar.tokenManager.getVocabulary();

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         String var5 = (String)var3.elementAt(var4);
         if (var5 != null) {
            TokenSymbol var6 = this.grammar.tokenManager.getTokenSymbol(var5);
            if (var6 != null && var6.getASTNodeType() != null) {
               ++var2;
               if (!var1) {
                  this.println("self.tokenTypeToASTClassMap = {}");
                  var1 = true;
               }

               this.println("self.tokenTypeToASTClassMap[" + var6.getTokenType() + "] = " + var6.getASTNodeType());
            }
         }
      }

      if (var2 == 0) {
         this.println("self.tokenTypeToASTClassMap = None");
      }

      --this.tabs;
   }

   protected void genTokenTypes(TokenManager var1) throws IOException {
      this.tabs = 0;
      Vector var2 = var1.getVocabulary();
      this.println("SKIP                = antlr.SKIP");
      this.println("INVALID_TYPE        = antlr.INVALID_TYPE");
      this.println("EOF_TYPE            = antlr.EOF_TYPE");
      this.println("EOF                 = antlr.EOF");
      this.println("NULL_TREE_LOOKAHEAD = antlr.NULL_TREE_LOOKAHEAD");
      this.println("MIN_USER_TYPE       = antlr.MIN_USER_TYPE");

      for(int var3 = 4; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.elementAt(var3);
         if (var4 != null) {
            if (var4.startsWith("\"")) {
               StringLiteralSymbol var5 = (StringLiteralSymbol)var1.getTokenSymbol(var4);
               if (var5 == null) {
                  this.antlrTool.panic("String literal " + var4 + " not in symbol table");
               }

               if (var5.label != null) {
                  this.println(var5.label + " = " + var3);
               } else {
                  String var6 = this.mangleLiteral(var4);
                  if (var6 != null) {
                     this.println(var6 + " = " + var3);
                     var5.label = var6;
                  } else {
                     this.println("### " + var4 + " = " + var3);
                  }
               }
            } else if (!var4.startsWith("<")) {
               this.println(var4 + " = " + var3);
            }
         }
      }

      --this.tabs;
      this.exitIfError();
   }

   public String getASTCreateString(Vector var1) {
      if (var1.size() == 0) {
         return "";
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append("antlr.make(");

         for(int var3 = 0; var3 < var1.size(); ++var3) {
            var2.append(var1.elementAt(var3));
            if (var3 + 1 < var1.size()) {
               var2.append(", ");
            }
         }

         var2.append(")");
         return var2.toString();
      }
   }

   public String getASTCreateString(GrammarAtom var1, String var2) {
      return var1 != null && var1.getASTNodeType() != null ? "self.astFactory.create(" + var2 + ", " + var1.getASTNodeType() + ")" : this.getASTCreateString(var2);
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
               var8 = ", \"\"";
            }

            if (var7 != null) {
               return "self.astFactory.create(" + var1 + var8 + ", " + var7 + ")";
            }
         }

         if (this.labeledElementASTType.equals("AST")) {
            return "self.astFactory.create(" + var1 + ")";
         } else {
            return "self.astFactory.create(" + var1 + ")";
         }
      } else {
         return "self.astFactory.create(" + var1 + ")";
      }
   }

   protected String getLookaheadTestExpression(Lookahead[] var1, int var2) {
      StringBuffer var3 = new StringBuffer(100);
      boolean var4 = true;
      var3.append("(");

      for(int var5 = 1; var5 <= var2; ++var5) {
         BitSet var6 = var1[var5].fset;
         if (!var4) {
            var3.append(") and (");
         }

         var4 = false;
         if (var1[var5].containsEpsilon()) {
            var3.append("True");
         } else {
            var3.append(this.getLookaheadTestTerm(var5, var6));
         }
      }

      var3.append(")");
      String var7 = var3.toString();
      return var7;
   }

   protected String getLookaheadTestExpression(Alternative var1, int var2) {
      int var3 = var1.lookaheadDepth;
      if (var3 == Integer.MAX_VALUE) {
         var3 = this.grammar.maxk;
      }

      return var2 == 0 ? "True" : this.getLookaheadTestExpression(var1.cache, var3);
   }

   protected String getLookaheadTestTerm(int var1, BitSet var2) {
      String var3 = this.lookaheadString(var1);
      int[] var4 = var2.toArray();
      if (elementsAreRange(var4)) {
         String var9 = this.getRangeExpression(var1, var4);
         return var9;
      } else {
         int var6 = var2.degree();
         if (var6 == 0) {
            return "True";
         } else {
            int var7;
            if (var6 >= this.bitsetTestThreshold) {
               var7 = this.markBitsetForGen(var2);
               return this.getBitsetName(var7) + ".member(" + var3 + ")";
            } else {
               StringBuffer var5 = new StringBuffer();

               for(var7 = 0; var7 < var4.length; ++var7) {
                  String var8 = this.getValueString(var4[var7], true);
                  if (var7 > 0) {
                     var5.append(" or ");
                  }

                  var5.append(var3);
                  var5.append("==");
                  var5.append(var8);
               }

               String var10 = var5.toString();
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
      return "(" + this.lookaheadString(var1) + " >= " + this.getValueString(var3, true) + " and " + this.lookaheadString(var1) + " <= " + this.getValueString(var4, true) + ")";
   }

   private String getValueString(int var1, boolean var2) {
      String var3;
      if (this.grammar instanceof LexerGrammar) {
         var3 = this.charFormatter.literalChar(var1);
         if (var2) {
            var3 = "u'" + var3 + "'";
         }

         return var3;
      } else {
         TokenSymbol var4 = this.grammar.tokenManager.getTokenSymbolAt(var1);
         if (var4 == null) {
            var3 = "" + var1;
            return var3;
         } else {
            String var5 = var4.getId();
            if (!(var4 instanceof StringLiteralSymbol)) {
               return var5;
            } else {
               StringLiteralSymbol var6 = (StringLiteralSymbol)var4;
               String var7 = var6.getLabel();
               if (var7 != null) {
                  var3 = var7;
               } else {
                  var3 = this.mangleLiteral(var5);
                  if (var3 == null) {
                     var3 = String.valueOf(var1);
                  }
               }

               return var3;
            }
         }
      }
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
      return this.grammar instanceof TreeWalkerGrammar ? "_t.getType()" : "self.LA(" + var1 + ")";
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
         if (isEmpty(var1)) {
            return "";
         } else if (this.grammar == null) {
            return var1;
         } else {
            ActionLexer var5 = new ActionLexer(var1, var3, this, var4);
            var5.setLineOffset(var2);
            var5.setFilename(this.grammar.getFilename());
            var5.setTool(this.antlrTool);

            try {
               var5.mACTION(true);
               var1 = var5.getTokenObject().getText();
            } catch (RecognitionException var7) {
               var5.reportError(var7);
            } catch (TokenStreamException var8) {
               this.antlrTool.panic("Error reading action:" + var1);
            } catch (CharStreamException var9) {
               this.antlrTool.panic("Error reading action:" + var1);
            }

            return var1;
         }
      } else {
         return null;
      }
   }

   static boolean isEmpty(String var0) {
      boolean var2 = true;
      int var3 = 0;

      while(var2 && var3 < var0.length()) {
         char var1 = var0.charAt(var3);
         switch (var1) {
            default:
               var2 = false;
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               ++var3;
         }
      }

      return var2;
   }

   protected String processActionCode(String var1, int var2) {
      if (var1 != null && !isEmpty(var1)) {
         CodeLexer var3 = new CodeLexer(var1, this.grammar.getFilename(), var2, this.antlrTool);

         try {
            var3.mACTION(true);
            var1 = var3.getTokenObject().getText();
         } catch (RecognitionException var5) {
            var3.reportError(var5);
         } catch (TokenStreamException var6) {
            this.antlrTool.panic("Error reading action:" + var1);
         } catch (CharStreamException var7) {
            this.antlrTool.panic("Error reading action:" + var1);
         }

         return var1;
      } else {
         return "";
      }
   }

   protected void printActionCode(String var1, int var2) {
      var1 = this.processActionCode(var1, var2);
      this.printAction(var1);
   }

   private void setupGrammarParameters(Grammar var1) {
      Token var2;
      String var3;
      if (var1 instanceof ParserGrammar) {
         this.labeledElementASTType = "";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.labeledElementASTType = var3;
               }
            }
         }

         this.labeledElementType = "";
         this.labeledElementInit = "None";
         this.commonExtraArgs = "";
         this.commonExtraParams = "self";
         this.commonLocalVars = "";
         this.lt1Value = "self.LT(1)";
         this.exceptionThrown = "antlr.RecognitionException";
         this.throwNoViable = "raise antlr.NoViableAltException(self.LT(1), self.getFilename())";
         this.parserClassName = "Parser";
         if (var1.hasOption("className")) {
            var2 = var1.getOption("className");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.parserClassName = var3;
               }
            }
         }

      } else if (var1 instanceof LexerGrammar) {
         this.labeledElementType = "char ";
         this.labeledElementInit = "'\\0'";
         this.commonExtraArgs = "";
         this.commonExtraParams = "self, _createToken";
         this.commonLocalVars = "_ttype = 0\n        _token = None\n        _begin = self.text.length()";
         this.lt1Value = "self.LA(1)";
         this.exceptionThrown = "antlr.RecognitionException";
         this.throwNoViable = "self.raise_NoViableAlt(self.LA(1))";
         this.lexerClassName = "Lexer";
         if (var1.hasOption("className")) {
            var2 = var1.getOption("className");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.lexerClassName = var3;
               }
            }
         }

      } else if (var1 instanceof TreeWalkerGrammar) {
         this.labeledElementASTType = "";
         this.labeledElementType = "";
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
            var1.setOption("ASTLabelType", new Token(6, "<4>AST"));
         }

         this.labeledElementInit = "None";
         this.commonExtraArgs = "_t";
         this.commonExtraParams = "self, _t";
         this.commonLocalVars = "";
         this.lt1Value = "_t";
         this.exceptionThrown = "antlr.RecognitionException";
         this.throwNoViable = "raise antlr.NoViableAltException(_t)";
         this.treeWalkerClassName = "Walker";
         if (var1.hasOption("className")) {
            var2 = var1.getOption("className");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.treeWalkerClassName = var3;
               }
            }
         }

      } else {
         this.antlrTool.panic("Unknown grammar type");
      }
   }

   public void setupOutput(String var1) throws IOException {
      this.currentOutput = this.antlrTool.openOutputFile(var1 + ".py");
   }

   protected boolean isspace(char var1) {
      boolean var2 = true;
      switch (var1) {
         default:
            var2 = false;
         case '\t':
         case '\n':
         case '\r':
         case ' ':
            return var2;
      }
   }

   protected void _printAction(String var1) {
      if (var1 != null) {
         int var4 = 0;
         int var5 = var1.length();
         int var3 = 0;
         boolean var6 = true;

         char var2;
         while(var4 < var5 && var6) {
            var2 = var1.charAt(var4++);
            switch (var2) {
               case '\t':
               default:
                  var6 = false;
                  break;
               case '\n':
                  var3 = var4;
                  break;
               case '\r':
                  if (var4 <= var5 && var1.charAt(var4) == '\n') {
                     ++var4;
                  }

                  var3 = var4;
               case ' ':
            }
         }

         if (!var6) {
            --var4;
         }

         var3 = var4 - var3;
         --var5;

         while(var5 > var4 && this.isspace(var1.charAt(var5))) {
            --var5;
         }

         boolean var7 = false;

         for(int var9 = var4; var9 <= var5; ++var9) {
            var2 = var1.charAt(var9);
            switch (var2) {
               case '\t':
                  System.err.println("warning: tab characters used in Python action");
                  this.currentOutput.print("        ");
                  break;
               case '\n':
                  var7 = true;
                  break;
               case '\r':
                  var7 = true;
                  if (var9 + 1 <= var5 && var1.charAt(var9 + 1) == '\n') {
                     ++var9;
                  }
                  break;
               case ' ':
                  this.currentOutput.print(" ");
                  break;
               default:
                  this.currentOutput.print(var2);
            }

            if (var7) {
               this.currentOutput.print("\n");
               this.printTabs();
               int var8 = 0;
               var7 = false;
               ++var9;

               for(; var9 <= var5; ++var9) {
                  var2 = var1.charAt(var9);
                  if (!this.isspace(var2)) {
                     --var9;
                     break;
                  }

                  switch (var2) {
                     case '\n':
                        var7 = true;
                        break;
                     case '\r':
                        if (var9 + 1 <= var5 && var1.charAt(var9 + 1) == '\n') {
                           ++var9;
                        }

                        var7 = true;
                  }

                  if (var7) {
                     this.currentOutput.print("\n");
                     this.printTabs();
                     var8 = 0;
                     var7 = false;
                  } else {
                     if (var8 >= var3) {
                        break;
                     }

                     ++var8;
                  }
               }
            }
         }

         this.currentOutput.println();
      }
   }

   protected void od(String var1, int var2, int var3, String var4) {
      System.out.println(var4);

      for(int var6 = var2; var6 <= var3; ++var6) {
         char var5 = var1.charAt(var6);
         switch (var5) {
            case '\t':
               System.out.print(" ht ");
               break;
            case '\n':
               System.out.print(" nl ");
               break;
            case ' ':
               System.out.print(" sp ");
               break;
            default:
               System.out.print(" " + var5 + " ");
         }
      }

      System.out.println("");
   }

   protected void printAction(String var1) {
      if (var1 != null) {
         this.printTabs();
         this._printAction(var1);
      }

   }

   protected void printGrammarAction(Grammar var1) {
      this.println("### user action >>>");
      this.printAction(this.processActionForSpecialSymbols(var1.classMemberAction.getText(), var1.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
      this.println("### user action <<<");
   }

   protected void _printJavadoc(String var1) {
      int var3 = var1.length();
      byte var4 = 0;
      boolean var5 = false;
      this.currentOutput.print("\n");
      this.printTabs();
      this.currentOutput.print("###");

      for(int var6 = var4; var6 < var3; ++var6) {
         char var2 = var1.charAt(var6);
         switch (var2) {
            case '\t':
               this.currentOutput.print("\t");
               break;
            case '\n':
               var5 = true;
               break;
            case '\r':
               var5 = true;
               if (var6 + 1 <= var3 && var1.charAt(var6 + 1) == '\n') {
                  ++var6;
               }
               break;
            case ' ':
               this.currentOutput.print(" ");
               break;
            default:
               this.currentOutput.print(var2);
         }

         if (var5) {
            this.currentOutput.print("\n");
            this.printTabs();
            this.currentOutput.print("###");
            var5 = false;
         }
      }

      this.currentOutput.println();
   }

   protected void genJavadocComment(Grammar var1) {
      if (var1.comment != null) {
         this._printJavadoc(var1.comment);
      }

   }

   protected void genJavadocComment(RuleSymbol var1) {
      if (var1.comment != null) {
         this._printJavadoc(var1.comment);
      }

   }
}
