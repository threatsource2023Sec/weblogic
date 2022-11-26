package antlr;

import antlr.actions.csharp.ActionLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class CSharpCodeGenerator extends CodeGenerator {
   protected int syntacticPredLevel = 0;
   protected boolean genAST = false;
   protected boolean saveText = false;
   boolean usingCustomAST = false;
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
   private java.util.Vector astTypes;
   private static CSharpNameSpace nameSpace = null;
   int saveIndexCreateLevel;
   int blockNestingLevel;

   public CSharpCodeGenerator() {
      this.charFormatter = new CSharpCharFormatter();
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
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genAction(" + var1 + ")");
      }

      if (var1.isSemPred) {
         this.genSemPred(var1.actionText, var1.line);
      } else {
         if (this.grammar.hasSyntacticPredicate) {
            this.println("if (0==inputState.guessing)");
            this.println("{");
            ++this.tabs;
         }

         ActionTransInfo var2 = new ActionTransInfo();
         String var3 = this.processActionForSpecialSymbols(var1.actionText, var1.getLine(), this.currentRule, var2);
         if (var2.refRuleRoot != null) {
            this.println(var2.refRuleRoot + " = (" + this.labeledElementASTType + ")currentAST.root;");
         }

         this.printAction(var3);
         if (var2.assignToRoot) {
            this.println("currentAST.root = " + var2.refRuleRoot + ";");
            this.println("if ( (null != " + var2.refRuleRoot + ") && (null != " + var2.refRuleRoot + ".getFirstChild()) )");
            ++this.tabs;
            this.println("currentAST.child = " + var2.refRuleRoot + ".getFirstChild();");
            --this.tabs;
            this.println("else");
            ++this.tabs;
            this.println("currentAST.child = " + var2.refRuleRoot + ";");
            --this.tabs;
            this.println("currentAST.advanceChildToEnd();");
         }

         if (this.grammar.hasSyntacticPredicate) {
            --this.tabs;
            this.println("}");
         }
      }

   }

   public void gen(AlternativeBlock var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen(" + var1 + ")");
      }

      this.println("{");
      ++this.tabs;
      this.genBlockPreamble(var1);
      this.genBlockInitAction(var1);
      String var2 = this.currentASTResult;
      if (var1.getLabel() != null) {
         this.currentASTResult = var1.getLabel();
      }

      this.grammar.theLLkAnalyzer.deterministic(var1);
      CSharpBlockFinishingInfo var4 = this.genCommonBlock(var1, true);
      this.genBlockFinish(var4, this.throwNoViable);
      --this.tabs;
      this.println("}");
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
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      this.genMatch((GrammarAtom)var1);
      this.saveText = var2;
   }

   public void gen(CharRangeElement var1) {
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      boolean var2 = this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3);
      if (var2) {
         this.println("_saveIndex = text.Length;");
      }

      this.println("matchRange(" + OctalToUnicode(var1.beginText) + "," + OctalToUnicode(var1.endText) + ");");
      if (var2) {
         this.println("text.Length = _saveIndex;");
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

      this.genBody(var1);
   }

   public void gen(OneOrMoreBlock var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen+(" + var1 + ")");
      }

      this.println("{ // ( ... )+");
      ++this.tabs;
      ++this.blockNestingLevel;
      this.genBlockPreamble(var1);
      String var3;
      if (var1.getLabel() != null) {
         var3 = "_cnt_" + var1.getLabel();
      } else {
         var3 = "_cnt" + var1.ID;
      }

      this.println("int " + var3 + "=0;");
      String var2;
      if (var1.getLabel() != null) {
         var2 = var1.getLabel();
      } else {
         var2 = "_loop" + var1.ID;
      }

      this.println("for (;;)");
      this.println("{");
      ++this.tabs;
      ++this.blockNestingLevel;
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
            System.out.println("nongreedy (...)+ loop; exit depth is " + var1.exitLookaheadDepth);
         }

         String var8 = this.getLookaheadTestExpression(var1.exitCache, var7);
         this.println("// nongreedy exit test");
         this.println("if ((" + var3 + " >= 1) && " + var8 + ") goto " + var2 + "_breakloop;");
      }

      CSharpBlockFinishingInfo var9 = this.genCommonBlock(var1, false);
      this.genBlockFinish(var9, "if (" + var3 + " >= 1) { goto " + var2 + "_breakloop; } else { " + this.throwNoViable + "; }");
      this.println(var3 + "++;");
      --this.tabs;
      if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
         this.saveIndexCreateLevel = 0;
      }

      this.println("}");
      this._print(var2 + "_breakloop:");
      this.println(";");
      --this.tabs;
      if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
         this.saveIndexCreateLevel = 0;
      }

      this.println("}    // ( ... )+");
      this.currentASTResult = var4;
   }

   public void gen(ParserGrammar var1) throws IOException {
      if (var1.debuggingOutput) {
         this.semPreds = new Vector();
      }

      this.setGrammar(var1);
      if (!(this.grammar instanceof ParserGrammar)) {
         this.antlrTool.panic("Internal error generating parser");
      }

      this.genBody(var1);
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
               this.println(var1.getLabel() + " = _t==ASTNULL ? null : " + this.lt1Value + ";");
            }

            if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
               this.declareSaveIndexVariableIfNeeded();
               this.println("_saveIndex = text.Length;");
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
               this.declareSaveIndexVariableIfNeeded();
               this.println("text.Length = _saveIndex;");
            }

            if (this.syntacticPredLevel == 0) {
               boolean var3 = this.grammar.hasSyntacticPredicate && (this.grammar.buildAST && var1.getLabel() != null || this.genAST && var1.getAutoGenType() == 1);
               if (var3) {
                  this.println("if (0 == inputState.guessing)");
                  this.println("{");
                  ++this.tabs;
               }

               if (this.grammar.buildAST && var1.getLabel() != null) {
                  this.println(var1.getLabel() + "_AST = (" + this.labeledElementASTType + ")returnAST;");
               }

               if (this.genAST) {
                  switch (var1.getAutoGenType()) {
                     case 1:
                        if (this.usingCustomAST) {
                           this.println("astFactory.addASTChild(ref currentAST, (AST)returnAST);");
                        } else {
                           this.println("astFactory.addASTChild(ref currentAST, returnAST);");
                        }
                        break;
                     case 2:
                        this.antlrTool.error("Internal: encountered ^ after rule reference");
                  }
               }

               if (this.grammar instanceof LexerGrammar && var1.getLabel() != null) {
                  this.println(var1.getLabel() + " = returnToken_;");
               }

               if (var3) {
                  --this.tabs;
                  this.println("}");
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
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      this.genElementAST(var1);
      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      this.genMatch((GrammarAtom)var1);
      this.saveText = var2;
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling();");
      }

   }

   public void gen(TokenRangeElement var1) {
      this.genErrorTryForElement(var1);
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      this.genElementAST(var1);
      this.println("matchRange(" + OctalToUnicode(var1.beginText) + "," + OctalToUnicode(var1.endText) + ");");
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
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      this.genElementAST(var1);
      this.genMatch((GrammarAtom)var1);
      this.genErrorCatchForElement(var1);
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling();");
      }

   }

   public void gen(TreeElement var1) {
      this.println("AST __t" + var1.ID + " = _t;");
      if (var1.root.getLabel() != null) {
         this.println(var1.root.getLabel() + " = (ASTNULL == _t) ? null : (" + this.labeledElementASTType + ")_t;");
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
         this.println("if (null == _t) throw new MismatchedTokenException();");
      } else {
         this.genMatch(var1.root);
      }

      this.println("_t = _t.getFirstChild();");

      for(int var2 = 0; var2 < var1.getAlternatives().size(); ++var2) {
         Alternative var3 = var1.getAlternativeAt(var2);

         for(AlternativeElement var4 = var3.head; var4 != null; var4 = var4.next) {
            var4.generate();
         }
      }

      if (this.grammar.buildAST) {
         this.println("currentAST = __currentAST" + var1.ID + ";");
      }

      this.println("_t = __t" + var1.ID + ";");
      this.println("_t = _t.getNextSibling();");
   }

   public void gen(TreeWalkerGrammar var1) throws IOException {
      this.setGrammar(var1);
      if (!(this.grammar instanceof TreeWalkerGrammar)) {
         this.antlrTool.panic("Internal error generating tree-walker");
      }

      this.genBody(var1);
   }

   public void gen(WildcardElement var1) {
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      this.genElementAST(var1);
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("if (null == _t) throw new MismatchedTokenException();");
      } else if (this.grammar instanceof LexerGrammar) {
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.declareSaveIndexVariableIfNeeded();
            this.println("_saveIndex = text.Length;");
         }

         this.println("matchNot(EOF/*_CHAR*/);");
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.declareSaveIndexVariableIfNeeded();
            this.println("text.Length = _saveIndex;");
         }
      } else {
         this.println("matchNot(" + this.getValueString(1) + ");");
      }

      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t.getNextSibling();");
      }

   }

   public void gen(ZeroOrMoreBlock var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen*(" + var1 + ")");
      }

      this.println("{    // ( ... )*");
      ++this.tabs;
      ++this.blockNestingLevel;
      this.genBlockPreamble(var1);
      String var2;
      if (var1.getLabel() != null) {
         var2 = var1.getLabel();
      } else {
         var2 = "_loop" + var1.ID;
      }

      this.println("for (;;)");
      this.println("{");
      ++this.tabs;
      ++this.blockNestingLevel;
      this.genBlockInitAction(var1);
      String var3 = this.currentASTResult;
      if (var1.getLabel() != null) {
         this.currentASTResult = var1.getLabel();
      }

      this.grammar.theLLkAnalyzer.deterministic(var1);
      boolean var5 = false;
      int var6 = this.grammar.maxk;
      if (!var1.greedy && var1.exitLookaheadDepth <= this.grammar.maxk && var1.exitCache[var1.exitLookaheadDepth].containsEpsilon()) {
         var5 = true;
         var6 = var1.exitLookaheadDepth;
      } else if (!var1.greedy && var1.exitLookaheadDepth == Integer.MAX_VALUE) {
         var5 = true;
      }

      if (var5) {
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("nongreedy (...)* loop; exit depth is " + var1.exitLookaheadDepth);
         }

         String var7 = this.getLookaheadTestExpression(var1.exitCache, var6);
         this.println("// nongreedy exit test");
         this.println("if (" + var7 + ") goto " + var2 + "_breakloop;");
      }

      CSharpBlockFinishingInfo var8 = this.genCommonBlock(var1, false);
      this.genBlockFinish(var8, "goto " + var2 + "_breakloop;");
      --this.tabs;
      if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
         this.saveIndexCreateLevel = 0;
      }

      this.println("}");
      this._print(var2 + "_breakloop:");
      this.println(";");
      --this.tabs;
      if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
         this.saveIndexCreateLevel = 0;
      }

      this.println("}    // ( ... )*");
      this.currentASTResult = var3;
   }

   protected void genAlt(Alternative var1, AlternativeBlock var2) {
      boolean var3 = this.genAST;
      this.genAST = this.genAST && var1.getAutoGen();
      boolean var4 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGen();
      Hashtable var5 = this.treeVariableMap;
      this.treeVariableMap = new Hashtable();
      if (var1.exceptionSpec != null) {
         this.println("try        // for error handling");
         this.println("{");
         ++this.tabs;
      }

      for(AlternativeElement var6 = var1.head; !(var6 instanceof BlockEndElement); var6 = var6.next) {
         var6.generate();
      }

      if (this.genAST) {
         if (var2 instanceof RuleBlock) {
            RuleBlock var7 = (RuleBlock)var2;
            if (this.usingCustomAST) {
               this.println(var7.getRuleName() + "_AST = (" + this.labeledElementASTType + ")currentAST.root;");
            } else {
               this.println(var7.getRuleName() + "_AST = currentAST.root;");
            }
         } else if (var2.getLabel() != null) {
            this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         }
      }

      if (var1.exceptionSpec != null) {
         --this.tabs;
         this.println("}");
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
      this.println("private static long[] mk_" + this.getBitsetName(var2) + "()");
      this.println("{");
      ++this.tabs;
      int var3 = var1.lengthInLongWords();
      if (var3 < 8) {
         this.println("long[] data = { " + var1.toStringOfWords() + "};");
      } else {
         this.println("long[] data = new long[" + var3 + "];");
         long[] var4 = var1.toPackedArray();
         int var5 = 0;

         label34:
         while(true) {
            while(true) {
               if (var5 >= var4.length) {
                  break label34;
               }

               if (var5 + 1 != var4.length && var4[var5] == var4[var5 + 1]) {
                  int var6;
                  for(var6 = var5 + 1; var6 < var4.length && var4[var6] == var4[var5]; ++var6) {
                  }

                  this.println("for (int i = " + var5 + "; i<=" + (var6 - 1) + "; i++) { data[i]=" + var4[var5] + "L; }");
                  var5 = var6;
               } else {
                  this.println("data[" + var5 + "]=" + var4[var5] + "L;");
                  ++var5;
               }
            }
         }
      }

      this.println("return data;");
      --this.tabs;
      this.println("}");
      this.println("public static readonly BitSet " + this.getBitsetName(var2) + " = new BitSet(" + "mk_" + this.getBitsetName(var2) + "()" + ");");
   }

   protected String getBitsetName(int var1) {
      return "tokenSet_" + var1 + "_";
   }

   private void genBlockFinish(CSharpBlockFinishingInfo var1, String var2) {
      if (var1.needAnErrorClause && (var1.generatedAnIf || var1.generatedSwitch)) {
         if (var1.generatedAnIf) {
            this.println("else");
            this.println("{");
         } else {
            this.println("{");
         }

         ++this.tabs;
         this.println(var2);
         --this.tabs;
         this.println("}");
      }

      if (var1.postscript != null) {
         if (var1.needAnErrorClause && var1.generatedSwitch && !var1.generatedAnIf && var2 != null) {
            if (var2.indexOf("throw") != 0 && var2.indexOf("goto") != 0) {
               this.println(var1.postscript);
            } else {
               int var3 = var1.postscript.indexOf("break;") + 6;
               String var4 = var1.postscript.substring(var3);
               this.println(var4);
            }
         } else {
            this.println(var1.postscript);
         }
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
                     this.println(this.labeledElementType + " " + var4.getLabel() + " = " + this.labeledElementInit + ";");
                     if (this.grammar.buildAST) {
                        this.genASTDeclaration(var4);
                     }
                  } else {
                     if (this.grammar.buildAST) {
                        this.genASTDeclaration(var4);
                     }

                     if (this.grammar instanceof LexerGrammar) {
                        this.println("IToken " + var4.getLabel() + " = null;");
                     }

                     if (this.grammar instanceof TreeWalkerGrammar) {
                        this.println(this.labeledElementType + " " + var4.getLabel() + " = " + this.labeledElementInit + ";");
                     }
                  }
               } else {
                  this.println(this.labeledElementType + " " + var4.getLabel() + " = " + this.labeledElementInit + ";");
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

   public void genBody(LexerGrammar var1) throws IOException {
      this.setupOutput(this.grammar.getClassName());
      this.genAST = false;
      this.saveText = true;
      this.tabs = 0;
      this.genHeader();
      this.println(this.behavior.getHeaderAction(""));
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      ++this.tabs;
      this.println("// Generate header specific to lexer CSharp file");
      this.println("using System;");
      this.println("using Stream                          = System.IO.Stream;");
      this.println("using TextReader                      = System.IO.TextReader;");
      this.println("using Hashtable                       = System.Collections.Hashtable;");
      this.println("using Comparer                        = System.Collections.Comparer;");
      if (!var1.caseSensitiveLiterals) {
         this.println("using CaseInsensitiveHashCodeProvider = System.Collections.CaseInsensitiveHashCodeProvider;");
         this.println("using CaseInsensitiveComparer         = System.Collections.CaseInsensitiveComparer;");
      }

      this.println("");
      this.println("using TokenStreamException            = antlr.TokenStreamException;");
      this.println("using TokenStreamIOException          = antlr.TokenStreamIOException;");
      this.println("using TokenStreamRecognitionException = antlr.TokenStreamRecognitionException;");
      this.println("using CharStreamException             = antlr.CharStreamException;");
      this.println("using CharStreamIOException           = antlr.CharStreamIOException;");
      this.println("using ANTLRException                  = antlr.ANTLRException;");
      this.println("using CharScanner                     = antlr.CharScanner;");
      this.println("using InputBuffer                     = antlr.InputBuffer;");
      this.println("using ByteBuffer                      = antlr.ByteBuffer;");
      this.println("using CharBuffer                      = antlr.CharBuffer;");
      this.println("using Token                           = antlr.Token;");
      this.println("using IToken                          = antlr.IToken;");
      this.println("using CommonToken                     = antlr.CommonToken;");
      this.println("using SemanticException               = antlr.SemanticException;");
      this.println("using RecognitionException            = antlr.RecognitionException;");
      this.println("using NoViableAltForCharException     = antlr.NoViableAltForCharException;");
      this.println("using MismatchedCharException         = antlr.MismatchedCharException;");
      this.println("using TokenStream                     = antlr.TokenStream;");
      this.println("using LexerSharedInputState           = antlr.LexerSharedInputState;");
      this.println("using BitSet                          = antlr.collections.impl.BitSet;");
      this.println(this.grammar.preambleAction.getText());
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = "antlr." + this.grammar.getSuperClass();
      }

      if (this.grammar.comment != null) {
         this._println(this.grammar.comment);
      }

      Token var3 = (Token)this.grammar.options.get("classHeaderPrefix");
      if (var3 == null) {
         this.print("public ");
      } else {
         String var4 = StringUtils.stripFrontBack(var3.getText(), "\"", "\"");
         if (var4 == null) {
            this.print("public ");
         } else {
            this.print(var4 + " ");
         }
      }

      this.print("class " + this.grammar.getClassName() + " : " + var2);
      this.println(", TokenStream");
      Token var9 = (Token)this.grammar.options.get("classHeaderSuffix");
      if (var9 != null) {
         String var5 = StringUtils.stripFrontBack(var9.getText(), "\"", "\"");
         if (var5 != null) {
            this.print(", " + var5);
         }
      }

      this.println(" {");
      ++this.tabs;
      this.genTokenDefinitions(this.grammar.tokenManager);
      this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
      this.println("public " + this.grammar.getClassName() + "(Stream ins) : this(new ByteBuffer(ins))");
      this.println("{");
      this.println("}");
      this.println("");
      this.println("public " + this.grammar.getClassName() + "(TextReader r) : this(new CharBuffer(r))");
      this.println("{");
      this.println("}");
      this.println("");
      this.print("public " + this.grammar.getClassName() + "(InputBuffer ib)");
      if (this.grammar.debuggingOutput) {
         this.println(" : this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)))");
      } else {
         this.println(" : this(new LexerSharedInputState(ib))");
      }

      this.println("{");
      this.println("}");
      this.println("");
      this.println("public " + this.grammar.getClassName() + "(LexerSharedInputState state) : base(state)");
      this.println("{");
      ++this.tabs;
      this.println("initialize();");
      --this.tabs;
      this.println("}");
      this.println("private void initialize()");
      this.println("{");
      ++this.tabs;
      if (this.grammar.debuggingOutput) {
         this.println("ruleNames  = _ruleNames;");
         this.println("semPredNames = _semPredNames;");
         this.println("setupDebugging();");
      }

      this.println("caseSensitiveLiterals = " + var1.caseSensitiveLiterals + ";");
      this.println("setCaseSensitive(" + var1.caseSensitive + ");");
      if (var1.caseSensitiveLiterals) {
         this.println("literals = new Hashtable(100, (float) 0.4, null, Comparer.Default);");
      } else {
         this.println("literals = new Hashtable(100, (float) 0.4, CaseInsensitiveHashCodeProvider.Default, CaseInsensitiveComparer.Default);");
      }

      Enumeration var10 = this.grammar.tokenManager.getTokenSymbolKeys();

      while(var10.hasMoreElements()) {
         String var6 = (String)var10.nextElement();
         if (var6.charAt(0) == '"') {
            TokenSymbol var7 = this.grammar.tokenManager.getTokenSymbol(var6);
            if (var7 instanceof StringLiteralSymbol) {
               StringLiteralSymbol var8 = (StringLiteralSymbol)var7;
               this.println("literals.Add(" + var8.getId() + ", " + var8.getTokenType() + ");");
            }
         }
      }

      --this.tabs;
      this.println("}");
      Enumeration var11;
      if (this.grammar.debuggingOutput) {
         this.println("private static readonly string[] _ruleNames = new string[] {");
         var11 = this.grammar.rules.elements();
         boolean var12 = false;

         while(var11.hasMoreElements()) {
            GrammarSymbol var14 = (GrammarSymbol)var11.nextElement();
            if (var14 instanceof RuleSymbol) {
               this.println("  \"" + ((RuleSymbol)var14).getId() + "\",");
            }
         }

         this.println("};");
      }

      this.genNextToken();
      var11 = this.grammar.rules.elements();

      for(int var13 = 0; var11.hasMoreElements(); this.exitIfError()) {
         RuleSymbol var15 = (RuleSymbol)var11.nextElement();
         if (!var15.getId().equals("mnextToken")) {
            this.genRule(var15, false, var13++, this.grammar.tokenManager);
         }
      }

      if (this.grammar.debuggingOutput) {
         this.genSemPredMap();
      }

      this.genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
      this.println("");
      --this.tabs;
      this.println("}");
      --this.tabs;
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void genInitFactory(Grammar var1) {
      if (var1.buildAST) {
         this.println("static public void initializeASTFactory( ASTFactory factory )");
         this.println("{");
         ++this.tabs;
         this.println("factory.setMaxNodeType(" + var1.tokenManager.maxTokenType() + ");");
         Vector var2 = var1.tokenManager.getVocabulary();

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            String var4 = (String)var2.elementAt(var3);
            if (var4 != null) {
               TokenSymbol var5 = var1.tokenManager.getTokenSymbol(var4);
               if (var5 != null && var5.getASTNodeType() != null) {
                  this.println("factory.setTokenTypeASTNodeType(" + var4 + ", \"" + var5.getASTNodeType() + "\");");
               }
            }
         }

         --this.tabs;
         this.println("}");
      }

   }

   public void genBody(ParserGrammar var1) throws IOException {
      this.setupOutput(this.grammar.getClassName());
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.genHeader();
      this.println(this.behavior.getHeaderAction(""));
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      ++this.tabs;
      this.println("// Generate the header common to all output files.");
      this.println("using System;");
      this.println("");
      this.println("using TokenBuffer              = antlr.TokenBuffer;");
      this.println("using TokenStreamException     = antlr.TokenStreamException;");
      this.println("using TokenStreamIOException   = antlr.TokenStreamIOException;");
      this.println("using ANTLRException           = antlr.ANTLRException;");
      String var2 = this.grammar.getSuperClass();
      String[] var3 = this.split(var2, ".");
      this.println("using " + var3[var3.length - 1] + " = antlr." + var2 + ";");
      this.println("using Token                    = antlr.Token;");
      this.println("using IToken                   = antlr.IToken;");
      this.println("using TokenStream              = antlr.TokenStream;");
      this.println("using RecognitionException     = antlr.RecognitionException;");
      this.println("using NoViableAltException     = antlr.NoViableAltException;");
      this.println("using MismatchedTokenException = antlr.MismatchedTokenException;");
      this.println("using SemanticException        = antlr.SemanticException;");
      this.println("using ParserSharedInputState   = antlr.ParserSharedInputState;");
      this.println("using BitSet                   = antlr.collections.impl.BitSet;");
      if (this.genAST) {
         this.println("using AST                      = antlr.collections.AST;");
         this.println("using ASTPair                  = antlr.ASTPair;");
         this.println("using ASTFactory               = antlr.ASTFactory;");
         this.println("using ASTArray                 = antlr.collections.impl.ASTArray;");
      }

      this.println(this.grammar.preambleAction.getText());
      String var4 = null;
      if (this.grammar.superClass != null) {
         var4 = this.grammar.superClass;
      } else {
         var4 = "antlr." + this.grammar.getSuperClass();
      }

      if (this.grammar.comment != null) {
         this._println(this.grammar.comment);
      }

      Token var5 = (Token)this.grammar.options.get("classHeaderPrefix");
      if (var5 == null) {
         this.print("public ");
      } else {
         String var6 = StringUtils.stripFrontBack(var5.getText(), "\"", "\"");
         if (var6 == null) {
            this.print("public ");
         } else {
            this.print(var6 + " ");
         }
      }

      this.println("class " + this.grammar.getClassName() + " : " + var4);
      Token var11 = (Token)this.grammar.options.get("classHeaderSuffix");
      if (var11 != null) {
         String var7 = StringUtils.stripFrontBack(var11.getText(), "\"", "\"");
         if (var7 != null) {
            this.print("              , " + var7);
         }
      }

      this.println("{");
      ++this.tabs;
      this.genTokenDefinitions(this.grammar.tokenManager);
      GrammarSymbol var9;
      Enumeration var12;
      if (this.grammar.debuggingOutput) {
         this.println("private static readonly string[] _ruleNames = new string[] {");
         ++this.tabs;
         var12 = this.grammar.rules.elements();
         boolean var8 = false;

         while(var12.hasMoreElements()) {
            var9 = (GrammarSymbol)var12.nextElement();
            if (var9 instanceof RuleSymbol) {
               this.println("  \"" + ((RuleSymbol)var9).getId() + "\",");
            }
         }

         --this.tabs;
         this.println("};");
      }

      this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
      this.println("");
      this.println("protected void initialize()");
      this.println("{");
      ++this.tabs;
      this.println("tokenNames = tokenNames_;");
      if (this.grammar.buildAST) {
         this.println("initializeFactory();");
      }

      if (this.grammar.debuggingOutput) {
         this.println("ruleNames  = _ruleNames;");
         this.println("semPredNames = _semPredNames;");
         this.println("setupDebugging(tokenBuf);");
      }

      --this.tabs;
      this.println("}");
      this.println("");
      this.println("");
      this.println("protected " + this.grammar.getClassName() + "(TokenBuffer tokenBuf, int k) : base(tokenBuf, k)");
      this.println("{");
      ++this.tabs;
      this.println("initialize();");
      --this.tabs;
      this.println("}");
      this.println("");
      this.println("public " + this.grammar.getClassName() + "(TokenBuffer tokenBuf) : this(tokenBuf," + this.grammar.maxk + ")");
      this.println("{");
      this.println("}");
      this.println("");
      this.println("protected " + this.grammar.getClassName() + "(TokenStream lexer, int k) : base(lexer,k)");
      this.println("{");
      ++this.tabs;
      this.println("initialize();");
      --this.tabs;
      this.println("}");
      this.println("");
      this.println("public " + this.grammar.getClassName() + "(TokenStream lexer) : this(lexer," + this.grammar.maxk + ")");
      this.println("{");
      this.println("}");
      this.println("");
      this.println("public " + this.grammar.getClassName() + "(ParserSharedInputState state) : base(state," + this.grammar.maxk + ")");
      this.println("{");
      ++this.tabs;
      this.println("initialize();");
      --this.tabs;
      this.println("}");
      this.println("");
      this.astTypes = new java.util.Vector(100);
      var12 = this.grammar.rules.elements();

      for(int var13 = 0; var12.hasMoreElements(); this.exitIfError()) {
         var9 = (GrammarSymbol)var12.nextElement();
         if (var9 instanceof RuleSymbol) {
            RuleSymbol var10 = (RuleSymbol)var9;
            this.genRule(var10, var10.references.size() == 0, var13++, this.grammar.tokenManager);
         }
      }

      if (this.usingCustomAST) {
         this.println("public new " + this.labeledElementASTType + " getAST()");
         this.println("{");
         ++this.tabs;
         this.println("return (" + this.labeledElementASTType + ") returnAST;");
         --this.tabs;
         this.println("}");
         this.println("");
      }

      this.println("private void initializeFactory()");
      this.println("{");
      ++this.tabs;
      if (this.grammar.buildAST) {
         this.println("if (astFactory == null)");
         this.println("{");
         ++this.tabs;
         if (this.usingCustomAST) {
            this.println("astFactory = new ASTFactory(\"" + this.labeledElementASTType + "\");");
         } else {
            this.println("astFactory = new ASTFactory();");
         }

         --this.tabs;
         this.println("}");
         this.println("initializeASTFactory( astFactory );");
      }

      --this.tabs;
      this.println("}");
      this.genInitFactory(var1);
      this.genTokenStrings();
      this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
      if (this.grammar.debuggingOutput) {
         this.genSemPredMap();
      }

      this.println("");
      --this.tabs;
      this.println("}");
      --this.tabs;
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void genBody(TreeWalkerGrammar var1) throws IOException {
      this.setupOutput(this.grammar.getClassName());
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.genHeader();
      this.println(this.behavior.getHeaderAction(""));
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      ++this.tabs;
      this.println("// Generate header specific to the tree-parser CSharp file");
      this.println("using System;");
      this.println("");
      this.println("using " + this.grammar.getSuperClass() + " = antlr." + this.grammar.getSuperClass() + ";");
      this.println("using Token                    = antlr.Token;");
      this.println("using IToken                   = antlr.IToken;");
      this.println("using AST                      = antlr.collections.AST;");
      this.println("using RecognitionException     = antlr.RecognitionException;");
      this.println("using ANTLRException           = antlr.ANTLRException;");
      this.println("using NoViableAltException     = antlr.NoViableAltException;");
      this.println("using MismatchedTokenException = antlr.MismatchedTokenException;");
      this.println("using SemanticException        = antlr.SemanticException;");
      this.println("using BitSet                   = antlr.collections.impl.BitSet;");
      this.println("using ASTPair                  = antlr.ASTPair;");
      this.println("using ASTFactory               = antlr.ASTFactory;");
      this.println("using ASTArray                 = antlr.collections.impl.ASTArray;");
      this.println(this.grammar.preambleAction.getText());
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = "antlr." + this.grammar.getSuperClass();
      }

      this.println("");
      if (this.grammar.comment != null) {
         this._println(this.grammar.comment);
      }

      Token var3 = (Token)this.grammar.options.get("classHeaderPrefix");
      if (var3 == null) {
         this.print("public ");
      } else {
         String var4 = StringUtils.stripFrontBack(var3.getText(), "\"", "\"");
         if (var4 == null) {
            this.print("public ");
         } else {
            this.print(var4 + " ");
         }
      }

      this.println("class " + this.grammar.getClassName() + " : " + var2);
      Token var10 = (Token)this.grammar.options.get("classHeaderSuffix");
      if (var10 != null) {
         String var5 = StringUtils.stripFrontBack(var10.getText(), "\"", "\"");
         if (var5 != null) {
            this.print("              , " + var5);
         }
      }

      this.println("{");
      ++this.tabs;
      this.genTokenDefinitions(this.grammar.tokenManager);
      this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
      this.println("public " + this.grammar.getClassName() + "()");
      this.println("{");
      ++this.tabs;
      this.println("tokenNames = tokenNames_;");
      --this.tabs;
      this.println("}");
      this.println("");
      this.astTypes = new java.util.Vector();
      Enumeration var11 = this.grammar.rules.elements();
      int var6 = 0;

      for(String var7 = ""; var11.hasMoreElements(); this.exitIfError()) {
         GrammarSymbol var8 = (GrammarSymbol)var11.nextElement();
         if (var8 instanceof RuleSymbol) {
            RuleSymbol var9 = (RuleSymbol)var8;
            this.genRule(var9, var9.references.size() == 0, var6++, this.grammar.tokenManager);
         }
      }

      if (this.usingCustomAST) {
         this.println("public new " + this.labeledElementASTType + " getAST()");
         this.println("{");
         ++this.tabs;
         this.println("return (" + this.labeledElementASTType + ") returnAST;");
         --this.tabs;
         this.println("}");
         this.println("");
      }

      this.genInitFactory(this.grammar);
      this.genTokenStrings();
      this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
      --this.tabs;
      this.println("}");
      this.println("");
      --this.tabs;
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   protected void genCases(BitSet var1) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genCases(" + var1 + ")");
      }

      int[] var2 = var1.toArray();
      int var3 = this.grammar instanceof LexerGrammar ? 4 : 1;
      int var4 = 1;
      boolean var5 = true;

      for(int var6 = 0; var6 < var2.length; ++var6) {
         if (var4 == 1) {
            this.print("");
         } else {
            this._print("  ");
         }

         this._print("case " + this.getValueString(var2[var6]) + ":");
         if (var4 == var3) {
            this._println("");
            var5 = true;
            var4 = 1;
         } else {
            ++var4;
            var5 = false;
         }
      }

      if (!var5) {
         this._println("");
      }

   }

   public CSharpBlockFinishingInfo genCommonBlock(AlternativeBlock var1, boolean var2) {
      int var3 = 0;
      boolean var4 = false;
      int var5 = 0;
      CSharpBlockFinishingInfo var6 = new CSharpBlockFinishingInfo();
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genCommonBlock(" + var1 + ")");
      }

      boolean var7 = this.genAST;
      this.genAST = this.genAST && var1.getAutoGen();
      boolean var8 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGen();
      String var20;
      if (var1.not && this.analyzer.subruleCanBeInverted(var1, this.grammar instanceof LexerGrammar)) {
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("special case: ~(subrule)");
         }

         Lookahead var19 = this.analyzer.look(1, (AlternativeBlock)var1);
         if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
            this.println(var1.getLabel() + " = " + this.lt1Value + ";");
         }

         this.genElementAST(var1);
         var20 = "";
         if (this.grammar instanceof TreeWalkerGrammar) {
            if (this.usingCustomAST) {
               var20 = "(AST)_t,";
            } else {
               var20 = "_t,";
            }
         }

         this.println("match(" + var20 + this.getBitsetName(this.markBitsetForGen(var19.fset)) + ");");
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("_t = _t.getNextSibling();");
         }

         return var6;
      } else {
         if (var1.getAlternatives().size() == 1) {
            Alternative var9 = var1.getAlternativeAt(0);
            if (var9.synPred != null) {
               this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), var1.getAlternativeAt(0).synPred.getLine(), var1.getAlternativeAt(0).synPred.getColumn());
            }

            if (var2) {
               if (var9.semPred != null) {
                  this.genSemPred(var9.semPred, var1.line);
               }

               this.genAlt(var9, var1);
               return var6;
            }
         }

         int var18 = 0;

         int var10;
         for(var10 = 0; var10 < var1.getAlternatives().size(); ++var10) {
            Alternative var11 = var1.getAlternativeAt(var10);
            if (suitableForCaseExpression(var11)) {
               ++var18;
            }
         }

         int var21;
         if (var18 >= this.makeSwitchThreshold) {
            var20 = this.lookaheadString(1);
            var4 = true;
            if (this.grammar instanceof TreeWalkerGrammar) {
               this.println("if (null == _t)");
               ++this.tabs;
               this.println("_t = ASTNULL;");
               --this.tabs;
            }

            this.println("switch ( " + var20 + " )");
            this.println("{");
            ++this.blockNestingLevel;

            for(var21 = 0; var21 < var1.alternatives.size(); ++var21) {
               Alternative var12 = var1.getAlternativeAt(var21);
               if (suitableForCaseExpression(var12)) {
                  Lookahead var13 = var12.cache[1];
                  if (var13.fset.degree() == 0 && !var13.containsEpsilon()) {
                     this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), var12.head.getLine(), var12.head.getColumn());
                  } else {
                     this.genCases(var13.fset);
                     this.println("{");
                     ++this.tabs;
                     ++this.blockNestingLevel;
                     this.genAlt(var12, var1);
                     this.println("break;");
                     if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
                        this.saveIndexCreateLevel = 0;
                     }

                     --this.tabs;
                     this.println("}");
                  }
               }
            }

            this.println("default:");
            ++this.tabs;
         }

         var10 = this.grammar instanceof LexerGrammar ? this.grammar.maxk : 0;

         int var22;
         for(var21 = var10; var21 >= 0; --var21) {
            if (this.DEBUG_CODE_GENERATOR) {
               System.out.println("checking depth " + var21);
            }

            for(var22 = 0; var22 < var1.alternatives.size(); ++var22) {
               Alternative var24 = var1.getAlternativeAt(var22);
               if (this.DEBUG_CODE_GENERATOR) {
                  System.out.println("genAlt: " + var22);
               }

               if (var4 && suitableForCaseExpression(var24)) {
                  if (this.DEBUG_CODE_GENERATOR) {
                     System.out.println("ignoring alt because it was in the switch");
                  }
               } else {
                  boolean var15 = false;
                  String var14;
                  if (this.grammar instanceof LexerGrammar) {
                     int var16 = var24.lookaheadDepth;
                     if (var16 == Integer.MAX_VALUE) {
                        var16 = this.grammar.maxk;
                     }

                     while(var16 >= 1 && var24.cache[var16].containsEpsilon()) {
                        --var16;
                     }

                     if (var16 != var21) {
                        if (this.DEBUG_CODE_GENERATOR) {
                           System.out.println("ignoring alt because effectiveDepth!=altDepth;" + var16 + "!=" + var21);
                        }
                        continue;
                     }

                     var15 = this.lookaheadIsEmpty(var24, var16);
                     var14 = this.getLookaheadTestExpression(var24, var16);
                  } else {
                     var15 = this.lookaheadIsEmpty(var24, this.grammar.maxk);
                     var14 = this.getLookaheadTestExpression(var24, this.grammar.maxk);
                  }

                  if (var24.cache[1].fset.degree() > 127 && suitableForCaseExpression(var24)) {
                     if (var3 == 0) {
                        this.println("if " + var14);
                        this.println("{");
                     } else {
                        this.println("else if " + var14);
                        this.println("{");
                     }
                  } else if (var15 && var24.semPred == null && var24.synPred == null) {
                     if (var3 == 0) {
                        this.println("{");
                     } else {
                        this.println("else {");
                     }

                     var6.needAnErrorClause = false;
                  } else {
                     if (var24.semPred != null) {
                        ActionTransInfo var25 = new ActionTransInfo();
                        String var17 = this.processActionForSpecialSymbols(var24.semPred, var1.line, this.currentRule, var25);
                        if ((this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar) && this.grammar.debuggingOutput) {
                           var14 = "(" + var14 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEventArgs.PREDICTING," + this.addSemPred(this.charFormatter.escapeString(var17)) + "," + var17 + "))";
                        } else {
                           var14 = "(" + var14 + "&&(" + var17 + "))";
                        }
                     }

                     if (var3 > 0) {
                        if (var24.synPred != null) {
                           this.println("else {");
                           ++this.tabs;
                           ++this.blockNestingLevel;
                           this.genSynPred(var24.synPred, var14);
                           ++var5;
                        } else {
                           this.println("else if " + var14 + " {");
                        }
                     } else if (var24.synPred != null) {
                        this.genSynPred(var24.synPred, var14);
                     } else {
                        if (this.grammar instanceof TreeWalkerGrammar) {
                           this.println("if (_t == null)");
                           ++this.tabs;
                           this.println("_t = ASTNULL;");
                           --this.tabs;
                        }

                        this.println("if " + var14);
                        this.println("{");
                     }
                  }

                  ++this.blockNestingLevel;
                  ++var3;
                  ++this.tabs;
                  this.genAlt(var24, var1);
                  --this.tabs;
                  if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
                     this.saveIndexCreateLevel = 0;
                  }

                  this.println("}");
               }
            }
         }

         String var23 = "";

         for(var22 = 1; var22 <= var5; ++var22) {
            var23 = var23 + "}";
            if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
               this.saveIndexCreateLevel = 0;
            }
         }

         this.genAST = var7;
         this.saveText = var8;
         if (var4) {
            --this.tabs;
            var6.postscript = var23 + "break; }";
            if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
               this.saveIndexCreateLevel = 0;
            }

            var6.generatedSwitch = true;
            var6.generatedAnIf = var3 > 0;
         } else {
            var6.postscript = var23;
            var6.generatedSwitch = false;
            var6.generatedAnIf = var3 > 0;
         }

         return var6;
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
            this.println(this.labeledElementASTType + " " + var8 + "_in = " + var7 + ";");
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
               this.println(this.labeledElementASTType + " " + var9 + "_in = null;");
            }

            if (var3) {
            }

            if (var1.getLabel() != null) {
               if (var1 instanceof GrammarAtom) {
                  this.println(var9 + " = " + this.getASTCreateString((GrammarAtom)var1, var4) + ";");
               } else {
                  this.println(var9 + " = " + this.getASTCreateString(var4) + ";");
               }
            }

            if (var1.getLabel() == null && var2) {
               var4 = this.lt1Value;
               if (var1 instanceof GrammarAtom) {
                  this.println(var9 + " = " + this.getASTCreateString((GrammarAtom)var1, var4) + ";");
               } else {
                  this.println(var9 + " = " + this.getASTCreateString(var4) + ";");
               }

               if (this.grammar instanceof TreeWalkerGrammar) {
                  this.println(var9 + "_in = " + var4 + ";");
               }
            }

            if (this.genAST) {
               switch (var1.getAutoGenType()) {
                  case 1:
                     if (!this.usingCustomAST && (!(var1 instanceof GrammarAtom) || ((GrammarAtom)var1).getASTNodeType() == null)) {
                        this.println("astFactory.addASTChild(ref currentAST, " + var9 + ");");
                     } else {
                        this.println("astFactory.addASTChild(ref currentAST, (AST)" + var9 + ");");
                     }
                     break;
                  case 2:
                     if (!this.usingCustomAST && (!(var1 instanceof GrammarAtom) || ((GrammarAtom)var1).getASTNodeType() == null)) {
                        this.println("astFactory.makeASTRoot(ref currentAST, " + var9 + ");");
                     } else {
                        this.println("astFactory.makeASTRoot(ref currentAST, (AST)" + var9 + ");");
                     }
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
            this.println("}");
            this.genErrorHandler(var4);
         }

      }
   }

   private void genErrorHandler(ExceptionSpec var1) {
      for(int var2 = 0; var2 < var1.handlers.size(); ++var2) {
         ExceptionHandler var3 = (ExceptionHandler)var1.handlers.elementAt(var2);
         this.println("catch (" + var3.exceptionTypeAndName.getText() + ")");
         this.println("{");
         ++this.tabs;
         if (this.grammar.hasSyntacticPredicate) {
            this.println("if (0 == inputState.guessing)");
            this.println("{");
            ++this.tabs;
         }

         ActionTransInfo var4 = new ActionTransInfo();
         this.printAction(this.processActionForSpecialSymbols(var3.action.getText(), var3.action.getLine(), this.currentRule, var4));
         if (this.grammar.hasSyntacticPredicate) {
            --this.tabs;
            this.println("}");
            this.println("else");
            this.println("{");
            ++this.tabs;
            this.println("throw;");
            --this.tabs;
            this.println("}");
         }

         --this.tabs;
         this.println("}");
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
            this.println("try   // for error handling");
            this.println("{");
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
      this.println("// $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".cs\"$");
   }

   private void genLiteralsTest() {
      this.println("_ttype = testLiteralsTable(_ttype);");
   }

   private void genLiteralsTestForPartialToken() {
      this.println("_ttype = testLiteralsTable(text.ToString(_begin, text.Length-_begin), _ttype);");
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
         if (this.usingCustomAST) {
            var2 = "(AST)_t,";
         } else {
            var2 = "_t,";
         }
      }

      if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
         this.declareSaveIndexVariableIfNeeded();
         this.println("_saveIndex = text.Length;");
      }

      this.print(var1.not ? "matchNot(" : "match(");
      this._print(var2);
      if (var1.atomText.equals("EOF")) {
         this._print("Token.EOF_TYPE");
      } else {
         this._print(var1.atomText);
      }

      this._println(");");
      if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
         this.declareSaveIndexVariableIfNeeded();
         this.println("text.Length = _saveIndex;");
      }

   }

   protected void genMatchUsingAtomTokenType(GrammarAtom var1) {
      String var2 = "";
      if (this.grammar instanceof TreeWalkerGrammar) {
         if (this.usingCustomAST) {
            var2 = "(AST)_t,";
         } else {
            var2 = "_t,";
         }
      }

      Object var3 = null;
      String var4 = var2 + this.getValueString(var1.getType());
      this.println((var1.not ? "matchNot(" : "match(") + var4 + ");");
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
         this.println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
         this.println("{");
         ++this.tabs;
         this.println("try");
         this.println("{");
         ++this.tabs;
         this.println("uponEOF();");
         --this.tabs;
         this.println("}");
         this.println("catch(CharStreamIOException csioe)");
         this.println("{");
         ++this.tabs;
         this.println("throw new TokenStreamIOException(csioe.io);");
         --this.tabs;
         this.println("}");
         this.println("catch(CharStreamException cse)");
         this.println("{");
         ++this.tabs;
         this.println("throw new TokenStreamException(cse.Message);");
         --this.tabs;
         this.println("}");
         this.println("return new CommonToken(Token.EOF_TYPE, \"\");");
         --this.tabs;
         this.println("}");
         this.println("");
      } else {
         RuleBlock var10 = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
         var3 = new RuleSymbol("mnextToken");
         var3.setDefined();
         var3.setBlock(var10);
         var3.access = "private";
         this.grammar.define(var3);
         this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var10);
         String var5 = null;
         if (((LexerGrammar)this.grammar).filterMode) {
            var5 = ((LexerGrammar)this.grammar).filterRule;
         }

         this.println("");
         this.println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
         this.println("{");
         ++this.tabs;
         this.blockNestingLevel = 1;
         this.saveIndexCreateLevel = 0;
         this.println("IToken theRetToken = null;");
         this._println("tryAgain:");
         this.println("for (;;)");
         this.println("{");
         ++this.tabs;
         this.println("IToken _token = null;");
         this.println("int _ttype = Token.INVALID_TYPE;");
         if (((LexerGrammar)this.grammar).filterMode) {
            this.println("setCommitToPath(false);");
            if (var5 != null) {
               if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(var5))) {
                  this.grammar.antlrTool.error("Filter rule " + var5 + " does not exist in this lexer");
               } else {
                  RuleSymbol var6 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(var5));
                  if (!var6.isDefined()) {
                     this.grammar.antlrTool.error("Filter rule " + var5 + " does not exist in this lexer");
                  } else if (var6.access.equals("public")) {
                     this.grammar.antlrTool.error("Filter rule " + var5 + " must be protected");
                  }
               }

               this.println("int _m;");
               this.println("_m = mark();");
            }
         }

         this.println("resetText();");
         this.println("try     // for char stream error handling");
         this.println("{");
         ++this.tabs;
         this.println("try     // for lexical error handling");
         this.println("{");
         ++this.tabs;

         for(int var11 = 0; var11 < var10.getAlternatives().size(); ++var11) {
            Alternative var7 = var10.getAlternativeAt(var11);
            if (var7.cache[1].containsEpsilon()) {
               RuleRefElement var8 = (RuleRefElement)var7.head;
               String var9 = CodeGenerator.decodeLexerRuleName(var8.targetRule);
               this.antlrTool.warning("public lexical rule " + var9 + " is optional (can match \"nothing\")");
            }
         }

         String var12 = System.getProperty("line.separator");
         CSharpBlockFinishingInfo var13 = this.genCommonBlock(var10, false);
         String var14 = "if (cached_LA1==EOF_CHAR) { uponEOF(); returnToken_ = makeToken(Token.EOF_TYPE); }";
         var14 = var14 + var12 + "\t\t\t\t";
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var5 == null) {
               var14 = var14 + "\t\t\t\telse";
               var14 = var14 + "\t\t\t\t{";
               var14 = var14 + "\t\t\t\t\tconsume();";
               var14 = var14 + "\t\t\t\t\tgoto tryAgain;";
               var14 = var14 + "\t\t\t\t}";
            } else {
               var14 = var14 + "\t\t\t\t\telse" + var12 + "\t\t\t\t\t{" + var12 + "\t\t\t\t\tcommit();" + var12 + "\t\t\t\t\ttry {m" + var5 + "(false);}" + var12 + "\t\t\t\t\tcatch(RecognitionException e)" + var12 + "\t\t\t\t\t{" + var12 + "\t\t\t\t\t\t// catastrophic failure" + var12 + "\t\t\t\t\t\treportError(e);" + var12 + "\t\t\t\t\t\tconsume();" + var12 + "\t\t\t\t\t}" + var12 + "\t\t\t\t\tgoto tryAgain;" + var12 + "\t\t\t\t}";
            }
         } else {
            var14 = var14 + "else {" + this.throwNoViable + "}";
         }

         this.genBlockFinish(var13, var14);
         if (((LexerGrammar)this.grammar).filterMode && var5 != null) {
            this.println("commit();");
         }

         this.println("if ( null==returnToken_ ) goto tryAgain; // found SKIP token");
         this.println("_ttype = returnToken_.Type;");
         if (((LexerGrammar)this.grammar).getTestLiterals()) {
            this.genLiteralsTest();
         }

         this.println("returnToken_.Type = _ttype;");
         this.println("return returnToken_;");
         --this.tabs;
         this.println("}");
         this.println("catch (RecognitionException e) {");
         ++this.tabs;
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var5 == null) {
               this.println("if (!getCommitToPath())");
               this.println("{");
               ++this.tabs;
               this.println("consume();");
               this.println("goto tryAgain;");
               --this.tabs;
               this.println("}");
            } else {
               this.println("if (!getCommitToPath())");
               this.println("{");
               ++this.tabs;
               this.println("rewind(_m);");
               this.println("resetText();");
               this.println("try {m" + var5 + "(false);}");
               this.println("catch(RecognitionException ee) {");
               this.println("\t// horrendous failure: error in filter rule");
               this.println("\treportError(ee);");
               this.println("\tconsume();");
               this.println("}");
               --this.tabs;
               this.println("}");
               this.println("else");
            }
         }

         if (var10.getDefaultErrorHandler()) {
            this.println("{");
            ++this.tabs;
            this.println("reportError(e);");
            this.println("consume();");
            --this.tabs;
            this.println("}");
         } else {
            ++this.tabs;
            this.println("throw new TokenStreamRecognitionException(e);");
            --this.tabs;
         }

         --this.tabs;
         this.println("}");
         --this.tabs;
         this.println("}");
         this.println("catch (CharStreamException cse) {");
         this.println("\tif ( cse is CharStreamIOException ) {");
         this.println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
         this.println("\t}");
         this.println("\telse {");
         this.println("\t\tthrow new TokenStreamException(cse.Message);");
         this.println("\t}");
         this.println("}");
         --this.tabs;
         this.println("}");
         --this.tabs;
         this.println("}");
         this.println("");
      }
   }

   public void genRule(RuleSymbol var1, boolean var2, int var3, TokenManager var4) {
      this.tabs = 1;
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("genRule(" + var1.getId() + ")");
      }

      if (!var1.isDefined()) {
         this.antlrTool.error("undefined rule: " + var1.getId());
      } else {
         RuleBlock var5 = var1.getBlock();
         this.currentRule = var5;
         this.currentASTResult = var1.getId();
         this.declaredASTVariables.clear();
         boolean var6 = this.genAST;
         this.genAST = this.genAST && var5.getAutoGen();
         this.saveText = var5.getAutoGen();
         if (var1.comment != null) {
            this._println(var1.comment);
         }

         this.print(var1.access + " ");
         if (var5.returnAction != null) {
            this._print(this.extractTypeOfAction(var5.returnAction, var5.getLine(), var5.getColumn()) + " ");
         } else {
            this._print("void ");
         }

         this._print(var1.getId() + "(");
         this._print(this.commonExtraParams);
         if (this.commonExtraParams.length() != 0 && var5.argAction != null) {
            this._print(",");
         }

         if (var5.argAction != null) {
            this._println("");
            ++this.tabs;
            this.println(var5.argAction);
            --this.tabs;
            this.print(")");
         } else {
            this._print(")");
         }

         this._print(" //throws " + this.exceptionThrown);
         if (this.grammar instanceof ParserGrammar) {
            this._print(", TokenStreamException");
         } else if (this.grammar instanceof LexerGrammar) {
            this._print(", CharStreamException, TokenStreamException");
         }

         if (var5.throwsSpec != null) {
            if (this.grammar instanceof LexerGrammar) {
               this.antlrTool.error("user-defined throws spec not allowed (yet) for lexer rule " + var5.ruleName);
            } else {
               this._print(", " + var5.throwsSpec);
            }
         }

         this._println("");
         this._println("{");
         ++this.tabs;
         if (var5.returnAction != null) {
            this.println(var5.returnAction + ";");
         }

         this.println(this.commonLocalVars);
         if (this.grammar.traceRules) {
            if (this.grammar instanceof TreeWalkerGrammar) {
               if (this.usingCustomAST) {
                  this.println("traceIn(\"" + var1.getId() + "\",(AST)_t);");
               } else {
                  this.println("traceIn(\"" + var1.getId() + "\",_t);");
               }
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

            this.blockNestingLevel = 1;
            this.saveIndexCreateLevel = 0;
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
            this.println(this.labeledElementASTType + " " + var1.getId() + "_AST_in = (" + this.labeledElementASTType + ")_t;");
         }

         if (this.grammar.buildAST) {
            this.println("returnAST = null;");
            this.println("ASTPair currentAST = new ASTPair();");
            this.println(this.labeledElementASTType + " " + var1.getId() + "_AST = null;");
         }

         this.genBlockPreamble(var5);
         this.genBlockInitAction(var5);
         this.println("");
         ExceptionSpec var7 = var5.findExceptionSpec("");
         if (var7 != null || var5.getDefaultErrorHandler()) {
            this.println("try {      // for error handling");
            ++this.tabs;
         }

         String var9;
         if (var5.alternatives.size() == 1) {
            Alternative var8 = var5.getAlternativeAt(0);
            var9 = var8.semPred;
            if (var9 != null) {
               this.genSemPred(var9, this.currentRule.line);
            }

            if (var8.synPred != null) {
               this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), var8.synPred.getLine(), var8.synPred.getColumn());
            }

            this.genAlt(var8, var5);
         } else {
            this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var5);
            CSharpBlockFinishingInfo var11 = this.genCommonBlock(var5, false);
            this.genBlockFinish(var11, this.throwNoViable);
         }

         if (var7 != null || var5.getDefaultErrorHandler()) {
            --this.tabs;
            this.println("}");
         }

         if (var7 != null) {
            this.genErrorHandler(var7);
         } else if (var5.getDefaultErrorHandler()) {
            this.println("catch (" + this.exceptionThrown + " ex)");
            this.println("{");
            ++this.tabs;
            if (this.grammar.hasSyntacticPredicate) {
               this.println("if (0 == inputState.guessing)");
               this.println("{");
               ++this.tabs;
            }

            this.println("reportError(ex);");
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
               Lookahead var10 = this.grammar.theLLkAnalyzer.FOLLOW(1, var5.endNode);
               var9 = this.getBitsetName(this.markBitsetForGen(var10.fset));
               this.println("recover(ex," + var9 + ");");
            } else {
               this.println("if (null != _t)");
               this.println("{");
               ++this.tabs;
               this.println("_t = _t.getNextSibling();");
               --this.tabs;
               this.println("}");
            }

            if (this.grammar.hasSyntacticPredicate) {
               --this.tabs;
               this.println("}");
               this.println("else");
               this.println("{");
               ++this.tabs;
               this.println("throw ex;");
               --this.tabs;
               this.println("}");
            }

            --this.tabs;
            this.println("}");
         }

         if (this.grammar.buildAST) {
            this.println("returnAST = " + var1.getId() + "_AST;");
         }

         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("retTree_ = _t;");
         }

         if (var5.getTestLiterals()) {
            if (var1.access.equals("protected")) {
               this.genLiteralsTestForPartialToken();
            } else {
               this.genLiteralsTest();
            }
         }

         if (this.grammar instanceof LexerGrammar) {
            this.println("if (_createToken && (null == _token) && (_ttype != Token.SKIP))");
            this.println("{");
            ++this.tabs;
            this.println("_token = makeToken(_ttype);");
            this.println("_token.setText(text.ToString(_begin, text.Length-_begin));");
            --this.tabs;
            this.println("}");
            this.println("returnToken_ = _token;");
         }

         if (var5.returnAction != null) {
            this.println("return " + this.extractIdOfAction(var5.returnAction, var5.getLine(), var5.getColumn()) + ";");
         }

         if (this.grammar.debuggingOutput || this.grammar.traceRules) {
            --this.tabs;
            this.println("}");
            this.println("finally");
            this.println("{ // debugging");
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
      }
   }

   private void GenRuleInvocation(RuleRefElement var1) {
      this._print(var1.targetRule + "(");
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

      this._println(");");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = retTree_;");
      }

   }

   protected void genSemPred(String var1, int var2) {
      ActionTransInfo var3 = new ActionTransInfo();
      var1 = this.processActionForSpecialSymbols(var1, var2, this.currentRule, var3);
      String var4 = this.charFormatter.escapeString(var1);
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         var1 = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + this.addSemPred(var4) + "," + var1 + ")";
      }

      this.println("if (!(" + var1 + "))");
      this.println("  throw new SemanticException(\"" + var4 + "\");");
   }

   protected void genSemPredMap() {
      Enumeration var1 = this.semPreds.elements();
      this.println("private string[] _semPredNames = {");
      ++this.tabs;

      while(var1.hasMoreElements()) {
         this.println("\"" + var1.nextElement() + "\",");
      }

      --this.tabs;
      this.println("};");
   }

   protected void genSynPred(SynPredBlock var1, String var2) {
      if (this.DEBUG_CODE_GENERATOR) {
         System.out.println("gen=>(" + var1 + ")");
      }

      this.println("bool synPredMatched" + var1.ID + " = false;");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("if (_t==null) _t=ASTNULL;");
      }

      this.println("if (" + var2 + ")");
      this.println("{");
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
      this.println("catch (" + this.exceptionThrown + ")");
      this.println("{");
      ++this.tabs;
      this.println("synPredMatched" + var1.ID + " = false;");
      --this.tabs;
      this.println("}");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = __t" + var1.ID + ";");
      } else {
         this.println("rewind(_m" + var1.ID + ");");
      }

      this.println("inputState.guessing--;");
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         this.println("if (synPredMatched" + var1.ID + ")");
         this.println("  fireSyntacticPredicateSucceeded();");
         this.println("else");
         this.println("  fireSyntacticPredicateFailed();");
      }

      --this.syntacticPredLevel;
      --this.tabs;
      this.println("}");
      this.println("if ( synPredMatched" + var1.ID + " )");
      this.println("{");
   }

   public void genTokenStrings() {
      this.println("");
      this.println("public static readonly string[] tokenNames_ = new string[] {");
      ++this.tabs;
      Vector var1 = this.grammar.tokenManager.getVocabulary();

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         String var3 = (String)var1.elementAt(var2);
         if (var3 == null) {
            var3 = "<" + String.valueOf(var2) + ">";
         }

         if (!var3.startsWith("\"") && !var3.startsWith("<")) {
            TokenSymbol var4 = this.grammar.tokenManager.getTokenSymbol(var3);
            if (var4 != null && var4.getParaphrase() != null) {
               var3 = StringUtils.stripFrontBack(var4.getParaphrase(), "\"", "\"");
            }
         } else if (var3.startsWith("\"")) {
            var3 = StringUtils.stripFrontBack(var3, "\"", "\"");
         }

         this.print(this.charFormatter.literalString(var3));
         if (var2 != var1.size() - 1) {
            this._print(",");
         }

         this._println("");
      }

      --this.tabs;
      this.println("};");
   }

   protected void genTokenTypes(TokenManager var1) throws IOException {
      this.setupOutput(var1.getName() + TokenTypesFileSuffix);
      this.tabs = 0;
      this.genHeader();
      this.println(this.behavior.getHeaderAction(""));
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      ++this.tabs;
      this.println("public class " + var1.getName() + TokenTypesFileSuffix);
      this.println("{");
      ++this.tabs;
      this.genTokenDefinitions(var1);
      --this.tabs;
      this.println("}");
      --this.tabs;
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
      this.exitIfError();
   }

   protected void genTokenDefinitions(TokenManager var1) throws IOException {
      Vector var2 = var1.getVocabulary();
      this.println("public const int EOF = 1;");
      this.println("public const int NULL_TREE_LOOKAHEAD = 3;");

      for(int var3 = 4; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.elementAt(var3);
         if (var4 != null) {
            if (var4.startsWith("\"")) {
               StringLiteralSymbol var5 = (StringLiteralSymbol)var1.getTokenSymbol(var4);
               if (var5 == null) {
                  this.antlrTool.panic("String literal " + var4 + " not in symbol table");
               } else if (var5.label != null) {
                  this.println("public const int " + var5.label + " = " + var3 + ";");
               } else {
                  String var6 = this.mangleLiteral(var4);
                  if (var6 != null) {
                     this.println("public const int " + var6 + " = " + var3 + ";");
                     var5.label = var6;
                  } else {
                     this.println("// " + var4 + " = " + var3);
                  }
               }
            } else if (!var4.startsWith("<")) {
               this.println("public const int " + var4 + " = " + var3 + ";");
            }
         }
      }

      this.println("");
   }

   public String processStringForASTConstructor(String var1) {
      return this.usingCustomAST && (this.grammar instanceof TreeWalkerGrammar || this.grammar instanceof ParserGrammar) && !this.grammar.tokenManager.tokenDefined(var1) ? "(AST)" + var1 : var1;
   }

   public String getASTCreateString(Vector var1) {
      if (var1.size() == 0) {
         return "";
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append("(" + this.labeledElementASTType + ") astFactory.make(");
         var2.append(var1.elementAt(0));

         for(int var3 = 1; var3 < var1.size(); ++var3) {
            var2.append(", " + var1.elementAt(var3));
         }

         var2.append(")");
         return var2.toString();
      }
   }

   public String getASTCreateString(GrammarAtom var1, String var2) {
      String var3 = "astFactory.create(" + var2 + ")";
      if (var1 == null) {
         return this.getASTCreateString(var2);
      } else {
         if (var1.getASTNodeType() != null) {
            TokenSymbol var4 = this.grammar.tokenManager.getTokenSymbol(var1.getText());
            if (var4 != null && var4.getASTNodeType() == var1.getASTNodeType()) {
               if (var4 != null && var4.getASTNodeType() != null) {
                  var3 = "(" + var4.getASTNodeType() + ") " + var3;
               }
            } else {
               var3 = "(" + var1.getASTNodeType() + ") astFactory.create(" + var2 + ", \"" + var1.getASTNodeType() + "\")";
            }
         } else if (this.usingCustomAST) {
            var3 = "(" + this.labeledElementASTType + ") " + var3;
         }

         return var3;
      }
   }

   public String getASTCreateString(String var1) {
      if (var1 == null) {
         var1 = "";
      }

      String var2 = "astFactory.create(" + var1 + ")";
      String var3 = var1;
      String var4 = null;
      boolean var6 = false;
      int var5 = var1.indexOf(44);
      if (var5 != -1) {
         var3 = var1.substring(0, var5);
         var4 = var1.substring(var5 + 1, var1.length());
         var5 = var4.indexOf(44);
         if (var5 != -1) {
            var6 = true;
         }
      }

      TokenSymbol var7 = this.grammar.tokenManager.getTokenSymbol(var3);
      if (null != var7 && null != var7.getASTNodeType()) {
         var2 = "(" + var7.getASTNodeType() + ") " + var2;
      } else if (this.usingCustomAST) {
         var2 = "(" + this.labeledElementASTType + ") " + var2;
      }

      return var2;
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
      if (this.grammar instanceof TreeWalkerGrammar) {
         return "_t.Type";
      } else {
         if (this.grammar instanceof LexerGrammar) {
            if (var1 == 1) {
               return "cached_LA1";
            }

            if (var1 == 2) {
               return "cached_LA2";
            }
         }

         return "LA(" + var1 + ")";
      }
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
      if (var1 instanceof ParserGrammar || var1 instanceof LexerGrammar || var1 instanceof TreeWalkerGrammar) {
         if (this.antlrTool.nameSpace != null) {
            nameSpace = new CSharpNameSpace(this.antlrTool.nameSpace.getName());
         }

         if (var1.hasOption("namespace")) {
            var2 = var1.getOption("namespace");
            if (var2 != null) {
               nameSpace = new CSharpNameSpace(var2.getText());
            }
         }
      }

      String var3;
      if (var1 instanceof ParserGrammar) {
         this.labeledElementASTType = "AST";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.usingCustomAST = true;
                  this.labeledElementASTType = var3;
               }
            }
         }

         this.labeledElementType = "IToken ";
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
         this.commonExtraParams = "bool _createToken";
         this.commonLocalVars = "int _ttype; IToken _token=null; int _begin=text.Length;";
         this.lt1Value = "cached_LA1";
         this.exceptionThrown = "RecognitionException";
         this.throwNoViable = "throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());";
      } else if (var1 instanceof TreeWalkerGrammar) {
         this.labeledElementASTType = "AST";
         this.labeledElementType = "AST";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.usingCustomAST = true;
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
         if (this.usingCustomAST) {
            this.lt1Value = "(_t==ASTNULL) ? null : (" + this.labeledElementASTType + ")_t";
         } else {
            this.lt1Value = "_t";
         }

         this.exceptionThrown = "RecognitionException";
         this.throwNoViable = "throw new NoViableAltException(_t);";
      } else {
         this.antlrTool.panic("Unknown grammar type");
      }

   }

   public void setupOutput(String var1) throws IOException {
      this.currentOutput = this.antlrTool.openOutputFile(var1 + ".cs");
   }

   private static String OctalToUnicode(String var0) {
      if (4 <= var0.length() && '\'' == var0.charAt(0) && '\\' == var0.charAt(1) && '0' <= var0.charAt(2) && '7' >= var0.charAt(2) && '\'' == var0.charAt(var0.length() - 1)) {
         Integer var1 = Integer.valueOf(var0.substring(2, var0.length() - 1), 8);
         return "'\\x" + Integer.toHexString(var1) + "'";
      } else {
         return var0;
      }
   }

   public String getTokenTypesClassName() {
      TokenManager var1 = this.grammar.tokenManager;
      return new String(var1.getName() + TokenTypesFileSuffix);
   }

   private void declareSaveIndexVariableIfNeeded() {
      if (this.saveIndexCreateLevel == 0) {
         this.println("int _saveIndex = 0;");
         this.saveIndexCreateLevel = this.blockNestingLevel;
      }

   }

   public String[] split(String var1, String var2) {
      StringTokenizer var3 = new StringTokenizer(var1, var2);
      int var4 = var3.countTokens();
      String[] var5 = new String[var4];

      for(int var6 = 0; var3.hasMoreTokens(); ++var6) {
         var5[var6] = var3.nextToken();
      }

      return var5;
   }
}
