package antlr;

import antlr.actions.cpp.ActionLexer;
import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public class CppCodeGenerator extends CodeGenerator {
   boolean DEBUG_CPP_CODE_GENERATOR = false;
   protected int syntacticPredLevel = 0;
   protected boolean genAST = false;
   protected boolean saveText = false;
   protected boolean genHashLines = true;
   protected boolean noConstructors = false;
   protected int outputLine;
   protected String outputFile;
   boolean usingCustomAST = false;
   String labeledElementType;
   String labeledElementASTType;
   String labeledElementASTInit;
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
   private Vector astTypes;
   private static String namespaceStd = "ANTLR_USE_NAMESPACE(std)";
   private static String namespaceAntlr = "ANTLR_USE_NAMESPACE(antlr)";
   private static NameSpace nameSpace = null;
   private static final String preIncludeCpp = "pre_include_cpp";
   private static final String preIncludeHpp = "pre_include_hpp";
   private static final String postIncludeCpp = "post_include_cpp";
   private static final String postIncludeHpp = "post_include_hpp";

   public CppCodeGenerator() {
      this.charFormatter = new CppCharFormatter();
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

   protected int countLines(String var1) {
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         if (var1.charAt(var3) == '\n') {
            ++var2;
         }
      }

      return var2;
   }

   protected void _print(String var1) {
      if (var1 != null) {
         this.outputLine += this.countLines(var1);
         this.currentOutput.print(var1);
      }

   }

   protected void _printAction(String var1) {
      if (var1 != null) {
         this.outputLine += this.countLines(var1) + 1;
         super._printAction(var1);
      }

   }

   public void printAction(Token var1) {
      if (var1 != null) {
         this.genLineNo(var1.getLine());
         this.printTabs();
         this._printAction(this.processActionForSpecialSymbols(var1.getText(), var1.getLine(), (RuleBlock)null, (ActionTransInfo)null));
         this.genLineNo2();
      }

   }

   public void printHeaderAction(String var1) {
      Token var2 = (Token)this.behavior.headerActions.get(var1);
      if (var2 != null) {
         this.genLineNo(var2.getLine());
         this.println(this.processActionForSpecialSymbols(var2.getText(), var2.getLine(), (RuleBlock)null, (ActionTransInfo)null));
         this.genLineNo2();
      }

   }

   protected void _println(String var1) {
      if (var1 != null) {
         this.outputLine += this.countLines(var1) + 1;
         this.currentOutput.println(var1);
      }

   }

   protected void println(String var1) {
      if (var1 != null) {
         this.printTabs();
         this.outputLine += this.countLines(var1) + 1;
         this.currentOutput.println(var1);
      }

   }

   public void genLineNo(int var1) {
      if (var1 == 0) {
         ++var1;
      }

      if (this.genHashLines) {
         this._println("#line " + var1 + " \"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"");
      }

   }

   public void genLineNo(GrammarElement var1) {
      if (var1 != null) {
         this.genLineNo(var1.getLine());
      }

   }

   public void genLineNo(Token var1) {
      if (var1 != null) {
         this.genLineNo(var1.getLine());
      }

   }

   public void genLineNo2() {
      if (this.genHashLines) {
         this._println("#line " + (this.outputLine + 1) + " \"" + this.outputFile + "\"");
      }

   }

   private boolean charIsDigit(String var1, int var2) {
      return var2 < var1.length() && Character.isDigit(var1.charAt(var2));
   }

   private String convertJavaToCppString(String var1, boolean var2) {
      String var3 = new String();
      int var5 = 0;
      int var6 = 0;
      if (var2) {
         if (!var1.startsWith("'") || !var1.endsWith("'")) {
            this.antlrTool.error("Invalid character literal: '" + var1 + "'");
         }
      } else if (!var1.startsWith("\"") || !var1.endsWith("\"")) {
         this.antlrTool.error("Invalid character string: '" + var1 + "'");
      }

      String var4 = var1.substring(1, var1.length() - 1);
      String var7 = "";
      int var8 = 255;
      if (this.grammar instanceof LexerGrammar) {
         var8 = ((LexerGrammar)this.grammar).charVocabulary.size() - 1;
         if (var8 > 255) {
            var7 = "L";
         }
      }

      while(true) {
         while(true) {
            while(true) {
               while(var5 < var4.length()) {
                  if (var4.charAt(var5) == '\\') {
                     if (var4.length() == var5 + 1) {
                        this.antlrTool.error("Invalid escape in char literal: '" + var1 + "' looking at '" + var4.substring(var5) + "'");
                     }

                     switch (var4.charAt(var5 + 1)) {
                        case '"':
                        case '\'':
                        case '\\':
                           var6 = var4.charAt(var5 + 1);
                           var5 += 2;
                           break;
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                           if (this.charIsDigit(var4, var5 + 2)) {
                              if (this.charIsDigit(var4, var5 + 3)) {
                                 var6 = (var4.charAt(var5 + 1) - 48) * 8 * 8 + (var4.charAt(var5 + 2) - 48) * 8 + (var4.charAt(var5 + 3) - 48);
                                 var5 += 4;
                              } else {
                                 var6 = (var4.charAt(var5 + 1) - 48) * 8 + (var4.charAt(var5 + 2) - 48);
                                 var5 += 3;
                              }
                           } else {
                              var6 = var4.charAt(var5 + 1) - 48;
                              var5 += 2;
                           }
                           break;
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                           if (this.charIsDigit(var4, var5 + 2)) {
                              var6 = (var4.charAt(var5 + 1) - 48) * 8 + (var4.charAt(var5 + 2) - 48);
                              var5 += 3;
                           } else {
                              var6 = var4.charAt(var5 + 1) - 48;
                              var5 += 2;
                           }
                        default:
                           this.antlrTool.error("Unhandled escape in char literal: '" + var1 + "' looking at '" + var4.substring(var5) + "'");
                           var6 = 0;
                           break;
                        case 'a':
                           var6 = 7;
                           var5 += 2;
                           break;
                        case 'b':
                           var6 = 8;
                           var5 += 2;
                           break;
                        case 'f':
                           var6 = 12;
                           var5 += 2;
                           break;
                        case 'n':
                           var6 = 10;
                           var5 += 2;
                           break;
                        case 'r':
                           var6 = 13;
                           var5 += 2;
                           break;
                        case 't':
                           var6 = 9;
                           var5 += 2;
                           break;
                        case 'u':
                           if (var5 + 5 < var4.length()) {
                              var6 = Character.digit(var4.charAt(var5 + 2), 16) * 16 * 16 * 16 + Character.digit(var4.charAt(var5 + 3), 16) * 16 * 16 + Character.digit(var4.charAt(var5 + 4), 16) * 16 + Character.digit(var4.charAt(var5 + 5), 16);
                              var5 += 6;
                           } else {
                              this.antlrTool.error("Invalid escape in char literal: '" + var1 + "' looking at '" + var4.substring(var5) + "'");
                           }
                     }
                  } else {
                     var6 = var4.charAt(var5++);
                  }

                  if (this.grammar instanceof LexerGrammar && var6 > var8) {
                     String var9;
                     if (32 <= var6 && var6 < 127) {
                        var9 = this.charFormatter.escapeChar(var6, true);
                     } else {
                        var9 = "0x" + Integer.toString(var6, 16);
                     }

                     this.antlrTool.error("Character out of range in " + (var2 ? "char literal" : "string constant") + ": '" + var4 + "'");
                     this.antlrTool.error("Vocabulary size: " + var8 + " Character " + var9);
                  }

                  if (var2) {
                     if (var5 != var4.length()) {
                        this.antlrTool.error("Invalid char literal: '" + var1 + "'");
                     }

                     if (var8 <= 255) {
                        if (var6 <= 255 && (var6 & 128) != 0) {
                           var3 = "static_cast<unsigned char>('" + this.charFormatter.escapeChar(var6, true) + "')";
                        } else {
                           var3 = "'" + this.charFormatter.escapeChar(var6, true) + "'";
                        }
                     } else {
                        var3 = "L'" + this.charFormatter.escapeChar(var6, true) + "'";
                     }
                  } else {
                     var3 = var3 + this.charFormatter.escapeChar(var6, true);
                  }
               }

               if (!var2) {
                  var3 = var7 + "\"" + var3 + "\"";
               }

               return var3;
            }
         }
      }
   }

   public void gen() {
      try {
         Enumeration var1 = this.behavior.grammars.elements();

         while(var1.hasMoreElements()) {
            Grammar var2 = (Grammar)var1.nextElement();
            if (var2.debuggingOutput) {
               this.antlrTool.error(var2.getFilename() + ": C++ mode does not support -debug");
            }

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
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genAction(" + var1 + ")");
      }

      if (var1.isSemPred) {
         this.genSemPred(var1.actionText, var1.line);
      } else {
         if (this.grammar.hasSyntacticPredicate) {
            this.println("if ( inputState->guessing==0 ) {");
            ++this.tabs;
         }

         ActionTransInfo var2 = new ActionTransInfo();
         String var3 = this.processActionForSpecialSymbols(var1.actionText, var1.getLine(), this.currentRule, var2);
         if (var2.refRuleRoot != null) {
            this.println(var2.refRuleRoot + " = " + this.labeledElementASTType + "(currentAST.root);");
         }

         this.genLineNo((GrammarElement)var1);
         this.printAction(var3);
         this.genLineNo2();
         if (var2.assignToRoot) {
            this.println("currentAST.root = " + var2.refRuleRoot + ";");
            this.println("if ( " + var2.refRuleRoot + "!=" + this.labeledElementASTInit + " &&");
            ++this.tabs;
            this.println(var2.refRuleRoot + "->getFirstChild() != " + this.labeledElementASTInit + " )");
            this.println("  currentAST.child = " + var2.refRuleRoot + "->getFirstChild();");
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
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("gen(" + var1 + ")");
      }

      this.println("{");
      this.genBlockPreamble(var1);
      this.genBlockInitAction(var1);
      String var2 = this.currentASTResult;
      if (var1.getLabel() != null) {
         this.currentASTResult = var1.getLabel();
      }

      this.grammar.theLLkAnalyzer.deterministic(var1);
      CppBlockFinishingInfo var4 = this.genCommonBlock(var1, true);
      this.genBlockFinish(var4, this.throwNoViable);
      this.println("}");
      this.currentASTResult = var2;
   }

   public void gen(BlockEndElement var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genRuleEnd(" + var1 + ")");
      }

   }

   public void gen(CharLiteralElement var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genChar(" + var1 + ")");
      }

      if (!(this.grammar instanceof LexerGrammar)) {
         this.antlrTool.error("cannot ref character literals in grammar: " + var1);
      }

      if (var1.getLabel() != null) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      boolean var2 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGenType() == 1;
      if (!this.saveText || var1.getAutoGenType() == 3) {
         this.println("_saveIndex = text.length();");
      }

      this.print(var1.not ? "matchNot(" : "match(");
      this._print(this.convertJavaToCppString(var1.atomText, true));
      this._println(" /* charlit */ );");
      if (!this.saveText || var1.getAutoGenType() == 3) {
         this.println("text.erase(_saveIndex);");
      }

      this.saveText = var2;
   }

   public void gen(CharRangeElement var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genCharRangeElement(" + var1.beginText + ".." + var1.endText + ")");
      }

      if (!(this.grammar instanceof LexerGrammar)) {
         this.antlrTool.error("cannot ref character range in grammar: " + var1);
      }

      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      boolean var2 = this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3);
      if (var2) {
         this.println("_saveIndex=text.length();");
      }

      this.println("matchRange(" + this.convertJavaToCppString(var1.beginText, true) + "," + this.convertJavaToCppString(var1.endText, true) + ");");
      if (var2) {
         this.println("text.erase(_saveIndex);");
      }

   }

   public void gen(LexerGrammar var1) throws IOException {
      if (var1.debuggingOutput) {
         this.semPreds = new Vector();
      }

      if (var1.charVocabulary.size() > 256) {
         this.antlrTool.warning(var1.getFilename() + ": Vocabularies of this size still experimental in C++ mode (vocabulary size now: " + var1.charVocabulary.size() + ")");
      }

      this.setGrammar(var1);
      if (!(this.grammar instanceof LexerGrammar)) {
         this.antlrTool.panic("Internal error generating lexer");
      }

      this.genBody(var1);
      this.genInclude(var1);
   }

   public void gen(OneOrMoreBlock var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("gen+(" + var1 + ")");
      }

      this.println("{ // ( ... )+");
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

      this.println("for (;;) {");
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
         if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println("nongreedy (...)+ loop; exit depth is " + var1.exitLookaheadDepth);
         }

         String var8 = this.getLookaheadTestExpression(var1.exitCache, var7);
         this.println("// nongreedy exit test");
         this.println("if ( " + var3 + ">=1 && " + var8 + ") goto " + var2 + ";");
      }

      CppBlockFinishingInfo var9 = this.genCommonBlock(var1, false);
      this.genBlockFinish(var9, "if ( " + var3 + ">=1 ) { goto " + var2 + "; } else {" + this.throwNoViable + "}");
      this.println(var3 + "++;");
      --this.tabs;
      this.println("}");
      this.println(var2 + ":;");
      this.println("}  // ( ... )+");
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
      this.genInclude(var1);
   }

   public void gen(RuleRefElement var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genRR(" + var1 + ")");
      }

      RuleSymbol var2 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
      if (var2 != null && var2.isDefined()) {
         if (!(var2 instanceof RuleSymbol)) {
            this.antlrTool.error("'" + var1.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            this.genErrorTryForElement(var1);
            if (this.grammar instanceof TreeWalkerGrammar && var1.getLabel() != null && this.syntacticPredLevel == 0) {
               this.println(var1.getLabel() + " = (_t == ASTNULL) ? " + this.labeledElementASTInit + " : " + this.lt1Value + ";");
            }

            if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
               this.println("_saveIndex = text.length();");
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
               this.println("text.erase(_saveIndex);");
            }

            if (this.syntacticPredLevel == 0) {
               boolean var3 = this.grammar.hasSyntacticPredicate && (this.grammar.buildAST && var1.getLabel() != null || this.genAST && var1.getAutoGenType() == 1);
               if (var3) {
                  this.println("if (inputState->guessing==0) {");
                  ++this.tabs;
               }

               if (this.grammar.buildAST && var1.getLabel() != null) {
                  this.println(var1.getLabel() + "_AST = returnAST;");
               }

               if (this.genAST) {
                  switch (var1.getAutoGenType()) {
                     case 1:
                        if (this.usingCustomAST) {
                           this.println("astFactory->addASTChild(currentAST, " + namespaceAntlr + "RefAST(returnAST));");
                        } else {
                           this.println("astFactory->addASTChild( currentAST, returnAST );");
                        }
                        break;
                     case 2:
                        this.antlrTool.error("Internal: encountered ^ after rule reference");
                  }
               }

               if (this.grammar instanceof LexerGrammar && var1.getLabel() != null) {
                  this.println(var1.getLabel() + "=_returnToken;");
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
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
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
         this.println("_t = _t->getNextSibling();");
      }

   }

   public void gen(TokenRangeElement var1) {
      this.genErrorTryForElement(var1);
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      this.genElementAST(var1);
      this.println("matchRange(" + var1.beginText + "," + var1.endText + ");");
      this.genErrorCatchForElement(var1);
   }

   public void gen(TokenRefElement var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
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
         this.println("_t = _t->getNextSibling();");
      }

   }

   public void gen(TreeElement var1) {
      this.println(this.labeledElementType + " __t" + var1.ID + " = _t;");
      if (var1.root.getLabel() != null) {
         this.println(var1.root.getLabel() + " = (_t == " + this.labeledElementType + "(ASTNULL)) ? " + this.labeledElementASTInit + " : _t;");
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
         this.println(namespaceAntlr + "ASTPair __currentAST" + var1.ID + " = currentAST;");
         this.println("currentAST.root = currentAST.child;");
         this.println("currentAST.child = " + this.labeledElementASTInit + ";");
      }

      if (var1.root instanceof WildcardElement) {
         this.println("if ( _t == ASTNULL ) throw " + namespaceAntlr + "MismatchedTokenException();");
      } else {
         this.genMatch(var1.root);
      }

      this.println("_t = _t->getFirstChild();");

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
      this.println("_t = _t->getNextSibling();");
   }

   public void gen(TreeWalkerGrammar var1) throws IOException {
      this.setGrammar(var1);
      if (!(this.grammar instanceof TreeWalkerGrammar)) {
         this.antlrTool.panic("Internal error generating tree-walker");
      }

      this.genBody(var1);
      this.genInclude(var1);
   }

   public void gen(WildcardElement var1) {
      if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
         this.println(var1.getLabel() + " = " + this.lt1Value + ";");
      }

      this.genElementAST(var1);
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("if ( _t == " + this.labeledElementASTInit + " ) throw " + namespaceAntlr + "MismatchedTokenException();");
      } else if (this.grammar instanceof LexerGrammar) {
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("_saveIndex = text.length();");
         }

         this.println("matchNot(EOF/*_CHAR*/);");
         if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
            this.println("text.erase(_saveIndex);");
         }
      } else {
         this.println("matchNot(" + this.getValueString(1) + ");");
      }

      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _t->getNextSibling();");
      }

   }

   public void gen(ZeroOrMoreBlock var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("gen*(" + var1 + ")");
      }

      this.println("{ // ( ... )*");
      this.genBlockPreamble(var1);
      String var2;
      if (var1.getLabel() != null) {
         var2 = var1.getLabel();
      } else {
         var2 = "_loop" + var1.ID;
      }

      this.println("for (;;) {");
      ++this.tabs;
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
         if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
            System.out.println("nongreedy (...)* loop; exit depth is " + var1.exitLookaheadDepth);
         }

         String var7 = this.getLookaheadTestExpression(var1.exitCache, var6);
         this.println("// nongreedy exit test");
         this.println("if (" + var7 + ") goto " + var2 + ";");
      }

      CppBlockFinishingInfo var8 = this.genCommonBlock(var1, false);
      this.genBlockFinish(var8, "goto " + var2 + ";");
      --this.tabs;
      this.println("}");
      this.println(var2 + ":;");
      this.println("} // ( ... )*");
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
         this.println("try {      // for error handling");
         ++this.tabs;
      }

      for(AlternativeElement var6 = var1.head; !(var6 instanceof BlockEndElement); var6 = var6.next) {
         var6.generate();
      }

      if (this.genAST) {
         if (var2 instanceof RuleBlock) {
            RuleBlock var7 = (RuleBlock)var2;
            if (this.usingCustomAST) {
               this.println(var7.getRuleName() + "_AST = " + this.labeledElementASTType + "(currentAST.root);");
            } else {
               this.println(var7.getRuleName() + "_AST = currentAST.root;");
            }
         } else if (var2.getLabel() != null) {
            this.antlrTool.warning("Labeled subrules are not implemented", this.grammar.getFilename(), var2.getLine(), var2.getColumn());
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

   protected void genBitsets(Vector var1, int var2, String var3) {
      TokenManager var4 = this.grammar.tokenManager;
      this.println("");

      for(int var5 = 0; var5 < var1.size(); ++var5) {
         BitSet var6 = (BitSet)var1.elementAt(var5);
         var6.growToInclude(var2);
         this.println("const unsigned long " + var3 + this.getBitsetName(var5) + "_data_" + "[] = { " + var6.toStringOfHalfWords() + " };");
         String var7 = "// ";

         for(int var8 = 0; var8 < var4.getVocabulary().size(); ++var8) {
            if (var6.member(var8)) {
               if (this.grammar instanceof LexerGrammar) {
                  if (32 <= var8 && var8 < 127 && var8 != 92) {
                     var7 = var7 + this.charFormatter.escapeChar(var8, true) + " ";
                  } else {
                     var7 = var7 + "0x" + Integer.toString(var8, 16) + " ";
                  }
               } else {
                  var7 = var7 + var4.getTokenStringAt(var8) + " ";
               }

               if (var7.length() > 70) {
                  this.println(var7);
                  var7 = "// ";
               }
            }
         }

         if (var7 != "// ") {
            this.println(var7);
         }

         this.println("const " + namespaceAntlr + "BitSet " + var3 + this.getBitsetName(var5) + "(" + this.getBitsetName(var5) + "_data_," + var6.size() / 32 + ");");
      }

   }

   protected void genBitsetsHeader(Vector var1, int var2) {
      this.println("");

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         BitSet var4 = (BitSet)var1.elementAt(var3);
         var4.growToInclude(var2);
         this.println("static const unsigned long " + this.getBitsetName(var3) + "_data_" + "[];");
         this.println("static const " + namespaceAntlr + "BitSet " + this.getBitsetName(var3) + ";");
      }

   }

   private void genBlockFinish(CppBlockFinishingInfo var1, String var2) {
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

   }

   protected void genBlockInitAction(AlternativeBlock var1) {
      if (var1.initAction != null) {
         this.genLineNo((GrammarElement)var1);
         this.printAction(this.processActionForSpecialSymbols(var1.initAction, var1.line, this.currentRule, (ActionTransInfo)null));
         this.genLineNo2();
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
                        this.println(namespaceAntlr + "RefToken " + var4.getLabel() + ";");
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
                        this.genASTDeclaration(var4, "Ref" + var5.getASTNodeType());
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
      this.outputFile = this.grammar.getClassName() + ".cpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.genAST = false;
      this.saveText = true;
      this.tabs = 0;
      this.genHeader(this.outputFile);
      this.printHeaderAction("pre_include_cpp");
      this.println("#include \"" + this.grammar.getClassName() + ".hpp\"");
      this.println("#include <antlr/CharBuffer.hpp>");
      this.println("#include <antlr/TokenStreamException.hpp>");
      this.println("#include <antlr/TokenStreamIOException.hpp>");
      this.println("#include <antlr/TokenStreamRecognitionException.hpp>");
      this.println("#include <antlr/CharStreamException.hpp>");
      this.println("#include <antlr/CharStreamIOException.hpp>");
      this.println("#include <antlr/NoViableAltForCharException.hpp>");
      if (this.grammar.debuggingOutput) {
         this.println("#include <antlr/DebuggingInputBuffer.hpp>");
      }

      this.println("");
      this.printHeaderAction("post_include_cpp");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.printAction(this.grammar.preambleAction);
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = this.grammar.getSuperClass();
         if (var2.lastIndexOf(46) != -1) {
            var2 = var2.substring(var2.lastIndexOf(46) + 1);
         }

         var2 = namespaceAntlr + var2;
      }

      if (this.noConstructors) {
         this.println("#if 0");
         this.println("// constructor creation turned of with 'noConstructor' option");
      }

      this.println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(" + namespaceStd + "istream& in)");
      ++this.tabs;
      if (this.grammar.debuggingOutput) {
         this.println(": " + var2 + "(new " + namespaceAntlr + "DebuggingInputBuffer(new " + namespaceAntlr + "CharBuffer(in))," + var1.caseSensitive + ")");
      } else {
         this.println(": " + var2 + "(new " + namespaceAntlr + "CharBuffer(in)," + var1.caseSensitive + ")");
      }

      --this.tabs;
      this.println("{");
      ++this.tabs;
      if (this.grammar.debuggingOutput) {
         this.println("setRuleNames(_ruleNames);");
         this.println("setSemPredNames(_semPredNames);");
         this.println("setupDebugging();");
      }

      this.println("initLiterals();");
      --this.tabs;
      this.println("}");
      this.println("");
      this.println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(" + namespaceAntlr + "InputBuffer& ib)");
      ++this.tabs;
      if (this.grammar.debuggingOutput) {
         this.println(": " + var2 + "(new " + namespaceAntlr + "DebuggingInputBuffer(ib)," + var1.caseSensitive + ")");
      } else {
         this.println(": " + var2 + "(ib," + var1.caseSensitive + ")");
      }

      --this.tabs;
      this.println("{");
      ++this.tabs;
      if (this.grammar.debuggingOutput) {
         this.println("setRuleNames(_ruleNames);");
         this.println("setSemPredNames(_semPredNames);");
         this.println("setupDebugging();");
      }

      this.println("initLiterals();");
      --this.tabs;
      this.println("}");
      this.println("");
      this.println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(const " + namespaceAntlr + "LexerSharedInputState& state)");
      ++this.tabs;
      this.println(": " + var2 + "(state," + var1.caseSensitive + ")");
      --this.tabs;
      this.println("{");
      ++this.tabs;
      if (this.grammar.debuggingOutput) {
         this.println("setRuleNames(_ruleNames);");
         this.println("setSemPredNames(_semPredNames);");
         this.println("setupDebugging();");
      }

      this.println("initLiterals();");
      --this.tabs;
      this.println("}");
      this.println("");
      if (this.noConstructors) {
         this.println("// constructor creation turned of with 'noConstructor' option");
         this.println("#endif");
      }

      this.println("void " + this.grammar.getClassName() + "::initLiterals()");
      this.println("{");
      ++this.tabs;
      Enumeration var3 = this.grammar.tokenManager.getTokenSymbolKeys();

      while(var3.hasMoreElements()) {
         String var4 = (String)var3.nextElement();
         if (var4.charAt(0) == '"') {
            TokenSymbol var5 = this.grammar.tokenManager.getTokenSymbol(var4);
            if (var5 instanceof StringLiteralSymbol) {
               StringLiteralSymbol var6 = (StringLiteralSymbol)var5;
               this.println("literals[" + var6.getId() + "] = " + var6.getTokenType() + ";");
            }
         }
      }

      --this.tabs;
      this.println("}");
      Enumeration var7;
      if (this.grammar.debuggingOutput) {
         this.println("const char* " + this.grammar.getClassName() + "::_ruleNames[] = {");
         ++this.tabs;
         var7 = this.grammar.rules.elements();
         boolean var8 = false;

         while(var7.hasMoreElements()) {
            GrammarSymbol var10 = (GrammarSymbol)var7.nextElement();
            if (var10 instanceof RuleSymbol) {
               this.println("\"" + ((RuleSymbol)var10).getId() + "\",");
            }
         }

         this.println("0");
         --this.tabs;
         this.println("};");
      }

      this.genNextToken();
      var7 = this.grammar.rules.elements();

      for(int var9 = 0; var7.hasMoreElements(); this.exitIfError()) {
         RuleSymbol var11 = (RuleSymbol)var7.nextElement();
         if (!var11.getId().equals("mnextToken")) {
            this.genRule(var11, false, var9++, this.grammar.getClassName() + "::");
         }
      }

      if (this.grammar.debuggingOutput) {
         this.genSemPredMap(this.grammar.getClassName() + "::");
      }

      this.genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size(), this.grammar.getClassName() + "::");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void genInitFactory(Grammar var1) {
      String var2 = "factory ";
      if (!var1.buildAST) {
         var2 = "";
      }

      this.println("void " + var1.getClassName() + "::initializeASTFactory( " + namespaceAntlr + "ASTFactory& " + var2 + ")");
      this.println("{");
      ++this.tabs;
      if (var1.buildAST) {
         TokenManager var3 = this.grammar.tokenManager;
         Enumeration var4 = var3.getTokenSymbolKeys();

         while(var4.hasMoreElements()) {
            String var5 = (String)var4.nextElement();
            TokenSymbol var6 = var3.getTokenSymbol(var5);
            if (var6.getASTNodeType() != null) {
               this.astTypes.ensureCapacity(var6.getTokenType());
               String var7 = (String)this.astTypes.elementAt(var6.getTokenType());
               if (var7 == null) {
                  this.astTypes.setElementAt(var6.getASTNodeType(), var6.getTokenType());
               } else if (!var6.getASTNodeType().equals(var7)) {
                  this.antlrTool.warning((String)("Token " + var5 + " taking most specific AST type"), this.grammar.getFilename(), 1, 1);
                  this.antlrTool.warning((String)("  using " + var7 + " ignoring " + var6.getASTNodeType()), this.grammar.getFilename(), 1, 1);
               }
            }
         }

         for(int var8 = 0; var8 < this.astTypes.size(); ++var8) {
            String var9 = (String)this.astTypes.elementAt(var8);
            if (var9 != null) {
               this.println("factory.registerFactory(" + var8 + ", \"" + var9 + "\", " + var9 + "::factory);");
            }
         }

         this.println("factory.setMaxNodeType(" + this.grammar.tokenManager.maxTokenType() + ");");
      }

      --this.tabs;
      this.println("}");
   }

   public void genBody(ParserGrammar var1) throws IOException {
      this.outputFile = this.grammar.getClassName() + ".cpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.genHeader(this.outputFile);
      this.printHeaderAction("pre_include_cpp");
      this.println("#include \"" + this.grammar.getClassName() + ".hpp\"");
      this.println("#include <antlr/NoViableAltException.hpp>");
      this.println("#include <antlr/SemanticException.hpp>");
      this.println("#include <antlr/ASTFactory.hpp>");
      this.printHeaderAction("post_include_cpp");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.printAction(this.grammar.preambleAction);
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = this.grammar.getSuperClass();
         if (var2.lastIndexOf(46) != -1) {
            var2 = var2.substring(var2.lastIndexOf(46) + 1);
         }

         var2 = namespaceAntlr + var2;
      }

      Enumeration var3;
      GrammarSymbol var5;
      if (this.grammar.debuggingOutput) {
         this.println("const char* " + this.grammar.getClassName() + "::_ruleNames[] = {");
         ++this.tabs;
         var3 = this.grammar.rules.elements();
         boolean var4 = false;

         while(var3.hasMoreElements()) {
            var5 = (GrammarSymbol)var3.nextElement();
            if (var5 instanceof RuleSymbol) {
               this.println("\"" + ((RuleSymbol)var5).getId() + "\",");
            }
         }

         this.println("0");
         --this.tabs;
         this.println("};");
      }

      if (this.noConstructors) {
         this.println("#if 0");
         this.println("// constructor creation turned of with 'noConstructor' option");
      }

      this.print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
      this.println("(" + namespaceAntlr + "TokenBuffer& tokenBuf, int k)");
      this.println(": " + var2 + "(tokenBuf,k)");
      this.println("{");
      this.println("}");
      this.println("");
      this.print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
      this.println("(" + namespaceAntlr + "TokenBuffer& tokenBuf)");
      this.println(": " + var2 + "(tokenBuf," + this.grammar.maxk + ")");
      this.println("{");
      this.println("}");
      this.println("");
      this.print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
      this.println("(" + namespaceAntlr + "TokenStream& lexer, int k)");
      this.println(": " + var2 + "(lexer,k)");
      this.println("{");
      this.println("}");
      this.println("");
      this.print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
      this.println("(" + namespaceAntlr + "TokenStream& lexer)");
      this.println(": " + var2 + "(lexer," + this.grammar.maxk + ")");
      this.println("{");
      this.println("}");
      this.println("");
      this.print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
      this.println("(const " + namespaceAntlr + "ParserSharedInputState& state)");
      this.println(": " + var2 + "(state," + this.grammar.maxk + ")");
      this.println("{");
      this.println("}");
      this.println("");
      if (this.noConstructors) {
         this.println("// constructor creation turned of with 'noConstructor' option");
         this.println("#endif");
      }

      this.astTypes = new Vector();
      var3 = this.grammar.rules.elements();

      for(int var7 = 0; var3.hasMoreElements(); this.exitIfError()) {
         var5 = (GrammarSymbol)var3.nextElement();
         if (var5 instanceof RuleSymbol) {
            RuleSymbol var6 = (RuleSymbol)var5;
            this.genRule(var6, var6.references.size() == 0, var7++, this.grammar.getClassName() + "::");
         }
      }

      this.genInitFactory(var1);
      this.genTokenStrings(this.grammar.getClassName() + "::");
      this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), this.grammar.getClassName() + "::");
      if (this.grammar.debuggingOutput) {
         this.genSemPredMap(this.grammar.getClassName() + "::");
      }

      this.println("");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void genBody(TreeWalkerGrammar var1) throws IOException {
      this.outputFile = this.grammar.getClassName() + ".cpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.genHeader(this.outputFile);
      this.printHeaderAction("pre_include_cpp");
      this.println("#include \"" + this.grammar.getClassName() + ".hpp\"");
      this.println("#include <antlr/Token.hpp>");
      this.println("#include <antlr/AST.hpp>");
      this.println("#include <antlr/NoViableAltException.hpp>");
      this.println("#include <antlr/MismatchedTokenException.hpp>");
      this.println("#include <antlr/SemanticException.hpp>");
      this.println("#include <antlr/BitSet.hpp>");
      this.printHeaderAction("post_include_cpp");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.printAction(this.grammar.preambleAction);
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
      } else {
         var2 = this.grammar.getSuperClass();
         if (var2.lastIndexOf(46) != -1) {
            var2 = var2.substring(var2.lastIndexOf(46) + 1);
         }

         (new StringBuffer()).append(namespaceAntlr).append(var2).toString();
      }

      if (this.noConstructors) {
         this.println("#if 0");
         this.println("// constructor creation turned of with 'noConstructor' option");
      }

      this.println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "()");
      this.println("\t: " + namespaceAntlr + "TreeParser() {");
      ++this.tabs;
      --this.tabs;
      this.println("}");
      if (this.noConstructors) {
         this.println("// constructor creation turned of with 'noConstructor' option");
         this.println("#endif");
      }

      this.println("");
      this.astTypes = new Vector();
      Enumeration var3 = this.grammar.rules.elements();
      int var4 = 0;

      for(String var5 = ""; var3.hasMoreElements(); this.exitIfError()) {
         GrammarSymbol var6 = (GrammarSymbol)var3.nextElement();
         if (var6 instanceof RuleSymbol) {
            RuleSymbol var7 = (RuleSymbol)var6;
            this.genRule(var7, var7.references.size() == 0, var4++, this.grammar.getClassName() + "::");
         }
      }

      this.genInitFactory(this.grammar);
      this.genTokenStrings(this.grammar.getClassName() + "::");
      this.genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), this.grammar.getClassName() + "::");
      this.println("");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   protected void genCases(BitSet var1) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genCases(" + var1 + ")");
      }

      int[] var2 = var1.toArray();
      byte var3 = 1;
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

   public CppBlockFinishingInfo genCommonBlock(AlternativeBlock var1, boolean var2) {
      int var3 = 0;
      boolean var4 = false;
      int var5 = 0;
      CppBlockFinishingInfo var6 = new CppBlockFinishingInfo();
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genCommonBlk(" + var1 + ")");
      }

      boolean var7 = this.genAST;
      this.genAST = this.genAST && var1.getAutoGen();
      boolean var8 = this.saveText;
      this.saveText = this.saveText && var1.getAutoGen();
      String var20;
      if (var1.not && this.analyzer.subruleCanBeInverted(var1, this.grammar instanceof LexerGrammar)) {
         Lookahead var19 = this.analyzer.look(1, (AlternativeBlock)var1);
         if (var1.getLabel() != null && this.syntacticPredLevel == 0) {
            this.println(var1.getLabel() + " = " + this.lt1Value + ";");
         }

         this.genElementAST(var1);
         var20 = "";
         if (this.grammar instanceof TreeWalkerGrammar) {
            if (this.usingCustomAST) {
               var20 = namespaceAntlr + "RefAST" + "(_t),";
            } else {
               var20 = "_t,";
            }
         }

         this.println("match(" + var20 + this.getBitsetName(this.markBitsetForGen(var19.fset)) + ");");
         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println("_t = _t->getNextSibling();");
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
               this.println("if (_t == " + this.labeledElementASTInit + " )");
               ++this.tabs;
               this.println("_t = ASTNULL;");
               --this.tabs;
            }

            this.println("switch ( " + var20 + ") {");

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
                     this.genAlt(var12, var1);
                     this.println("break;");
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
            if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
               System.out.println("checking depth " + var21);
            }

            for(var22 = 0; var22 < var1.alternatives.size(); ++var22) {
               Alternative var24 = var1.getAlternativeAt(var22);
               if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
                  System.out.println("genAlt: " + var22);
               }

               if (var4 && suitableForCaseExpression(var24)) {
                  if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
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
                        if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
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
                        if (this.grammar instanceof TreeWalkerGrammar) {
                           this.println("if (_t == " + this.labeledElementASTInit + " )");
                           ++this.tabs;
                           this.println("_t = ASTNULL;");
                           --this.tabs;
                        }

                        this.println("if " + var14 + " {");
                     } else {
                        this.println("else if " + var14 + " {");
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
                        if (!this.grammar.debuggingOutput || !(this.grammar instanceof ParserGrammar) && !(this.grammar instanceof LexerGrammar)) {
                           var14 = "(" + var14 + "&&(" + var17 + "))";
                        } else {
                           var14 = "(" + var14 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING," + this.addSemPred(this.charFormatter.escapeString(var17)) + "," + var17 + "))";
                        }
                     }

                     if (var3 > 0) {
                        if (var24.synPred != null) {
                           this.println("else {");
                           ++this.tabs;
                           this.genSynPred(var24.synPred, var14);
                           ++var5;
                        } else {
                           this.println("else if " + var14 + " {");
                        }
                     } else if (var24.synPred != null) {
                        this.genSynPred(var24.synPred, var14);
                     } else {
                        if (this.grammar instanceof TreeWalkerGrammar) {
                           this.println("if (_t == " + this.labeledElementASTInit + " )");
                           ++this.tabs;
                           this.println("_t = ASTNULL;");
                           --this.tabs;
                        }

                        this.println("if " + var14 + " {");
                     }
                  }

                  ++var3;
                  ++this.tabs;
                  this.genAlt(var24, var1);
                  --this.tabs;
                  this.println("}");
               }
            }
         }

         String var23 = "";

         for(var22 = 1; var22 <= var5; ++var22) {
            --this.tabs;
            var23 = var23 + "}";
         }

         this.genAST = var7;
         this.saveText = var8;
         if (var4) {
            --this.tabs;
            var6.postscript = var23 + "}";
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
                     this.genASTDeclaration(var1, var5, "Ref" + var6.getASTNodeType());
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
               this.println(this.labeledElementASTType + " " + var9 + "_in = " + this.labeledElementASTInit + ";");
            }

            if (var3) {
               this.println("if ( inputState->guessing == 0 ) {");
               ++this.tabs;
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
                        this.println("astFactory->addASTChild(currentAST, " + var9 + ");");
                     } else {
                        this.println("astFactory->addASTChild(currentAST, " + namespaceAntlr + "RefAST(" + var9 + "));");
                     }
                     break;
                  case 2:
                     if (!this.usingCustomAST && (!(var1 instanceof GrammarAtom) || ((GrammarAtom)var1).getASTNodeType() == null)) {
                        this.println("astFactory->makeASTRoot(currentAST, " + var9 + ");");
                     } else {
                        this.println("astFactory->makeASTRoot(currentAST, " + namespaceAntlr + "RefAST(" + var9 + "));");
                     }
               }
            }

            if (var3) {
               --this.tabs;
               this.println("}");
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
         this.println("catch (" + var3.exceptionTypeAndName.getText() + ") {");
         ++this.tabs;
         if (this.grammar.hasSyntacticPredicate) {
            this.println("if (inputState->guessing==0) {");
            ++this.tabs;
         }

         ActionTransInfo var4 = new ActionTransInfo();
         this.genLineNo(var3.action);
         this.printAction(this.processActionForSpecialSymbols(var3.action.getText(), var3.action.getLine(), this.currentRule, var4));
         this.genLineNo2();
         if (this.grammar.hasSyntacticPredicate) {
            --this.tabs;
            this.println("} else {");
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
            this.println("try { // for error handling");
            ++this.tabs;
         }

      }
   }

   protected void genHeader(String var1) {
      StringBuffer var10001 = (new StringBuffer()).append("/* $ANTLR ");
      Tool var10002 = this.antlrTool;
      this.println(var10001.append(Tool.version).append(": ").append("\"").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append("\"").append(" -> ").append("\"").append(var1).append("\"$ */").toString());
   }

   public void genInclude(LexerGrammar var1) throws IOException {
      this.outputFile = this.grammar.getClassName() + ".hpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.genAST = false;
      this.saveText = true;
      this.tabs = 0;
      this.println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
      this.println("#define INC_" + this.grammar.getClassName() + "_hpp_");
      this.println("");
      this.printHeaderAction("pre_include_hpp");
      this.println("#include <antlr/config.hpp>");
      this.genHeader(this.outputFile);
      this.println("#include <antlr/CommonToken.hpp>");
      this.println("#include <antlr/InputBuffer.hpp>");
      this.println("#include <antlr/BitSet.hpp>");
      this.println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
         this.println("\n// Include correct superclass header with a header statement for example:");
         this.println("// header \"post_include_hpp\" {");
         this.println("// #include \"" + var2 + ".hpp\"");
         this.println("// }");
         this.println("// Or....");
         this.println("// header {");
         this.println("// #include \"" + var2 + ".hpp\"");
         this.println("// }\n");
      } else {
         var2 = this.grammar.getSuperClass();
         if (var2.lastIndexOf(46) != -1) {
            var2 = var2.substring(var2.lastIndexOf(46) + 1);
         }

         this.println("#include <antlr/" + var2 + ".hpp>");
         var2 = namespaceAntlr + var2;
      }

      this.printHeaderAction("post_include_hpp");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.printHeaderAction("");
      if (this.grammar.comment != null) {
         this._println(this.grammar.comment);
      }

      this.print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + var2);
      this.println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
      Token var3 = (Token)this.grammar.options.get("classHeaderSuffix");
      if (var3 != null) {
         String var4 = StringUtils.stripFrontBack(var3.getText(), "\"", "\"");
         if (var4 != null) {
            this.print(", " + var4);
         }
      }

      this.println("{");
      if (this.grammar.classMemberAction != null) {
         this.genLineNo(this.grammar.classMemberAction);
         this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
         this.genLineNo2();
      }

      this.tabs = 0;
      this.println("private:");
      this.tabs = 1;
      this.println("void initLiterals();");
      this.tabs = 0;
      this.println("public:");
      this.tabs = 1;
      this.println("bool getCaseSensitiveLiterals() const");
      this.println("{");
      ++this.tabs;
      this.println("return " + var1.caseSensitiveLiterals + ";");
      --this.tabs;
      this.println("}");
      this.tabs = 0;
      this.println("public:");
      this.tabs = 1;
      if (this.noConstructors) {
         this.tabs = 0;
         this.println("#if 0");
         this.println("// constructor creation turned of with 'noConstructor' option");
         this.tabs = 1;
      }

      this.println(this.grammar.getClassName() + "(" + namespaceStd + "istream& in);");
      this.println(this.grammar.getClassName() + "(" + namespaceAntlr + "InputBuffer& ib);");
      this.println(this.grammar.getClassName() + "(const " + namespaceAntlr + "LexerSharedInputState& state);");
      if (this.noConstructors) {
         this.tabs = 0;
         this.println("// constructor creation turned of with 'noConstructor' option");
         this.println("#endif");
         this.tabs = 1;
      }

      this.println(namespaceAntlr + "RefToken nextToken();");

      for(Enumeration var6 = this.grammar.rules.elements(); var6.hasMoreElements(); this.exitIfError()) {
         RuleSymbol var5 = (RuleSymbol)var6.nextElement();
         if (!var5.getId().equals("mnextToken")) {
            this.genRuleHeader(var5, false);
         }
      }

      this.tabs = 0;
      this.println("private:");
      this.tabs = 1;
      if (this.grammar.debuggingOutput) {
         this.println("static const char* _ruleNames[];");
      }

      if (this.grammar.debuggingOutput) {
         this.println("static const char* _semPredNames[];");
      }

      this.genBitsetsHeader(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
      this.tabs = 0;
      this.println("};");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void genInclude(ParserGrammar var1) throws IOException {
      this.outputFile = this.grammar.getClassName() + ".hpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
      this.println("#define INC_" + this.grammar.getClassName() + "_hpp_");
      this.println("");
      this.printHeaderAction("pre_include_hpp");
      this.println("#include <antlr/config.hpp>");
      this.genHeader(this.outputFile);
      this.println("#include <antlr/TokenStream.hpp>");
      this.println("#include <antlr/TokenBuffer.hpp>");
      this.println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
         this.println("\n// Include correct superclass header with a header statement for example:");
         this.println("// header \"post_include_hpp\" {");
         this.println("// #include \"" + var2 + ".hpp\"");
         this.println("// }");
         this.println("// Or....");
         this.println("// header {");
         this.println("// #include \"" + var2 + ".hpp\"");
         this.println("// }\n");
      } else {
         var2 = this.grammar.getSuperClass();
         if (var2.lastIndexOf(46) != -1) {
            var2 = var2.substring(var2.lastIndexOf(46) + 1);
         }

         this.println("#include <antlr/" + var2 + ".hpp>");
         var2 = namespaceAntlr + var2;
      }

      this.println("");
      this.printHeaderAction("post_include_hpp");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.printHeaderAction("");
      if (this.grammar.comment != null) {
         this._println(this.grammar.comment);
      }

      this.print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + var2);
      this.println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
      Token var3 = (Token)this.grammar.options.get("classHeaderSuffix");
      if (var3 != null) {
         String var4 = StringUtils.stripFrontBack(var3.getText(), "\"", "\"");
         if (var4 != null) {
            this.print(", " + var4);
         }
      }

      this.println("{");
      if (this.grammar.debuggingOutput) {
         this.println("public: static const char* _ruleNames[];");
      }

      if (this.grammar.classMemberAction != null) {
         this.genLineNo(this.grammar.classMemberAction.getLine());
         this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
         this.genLineNo2();
      }

      this.println("public:");
      this.tabs = 1;
      this.println("void initializeASTFactory( " + namespaceAntlr + "ASTFactory& factory );");
      this.tabs = 0;
      if (this.noConstructors) {
         this.println("#if 0");
         this.println("// constructor creation turned of with 'noConstructor' option");
      }

      this.println("protected:");
      this.tabs = 1;
      this.println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenBuffer& tokenBuf, int k);");
      this.tabs = 0;
      this.println("public:");
      this.tabs = 1;
      this.println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenBuffer& tokenBuf);");
      this.tabs = 0;
      this.println("protected:");
      this.tabs = 1;
      this.println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenStream& lexer, int k);");
      this.tabs = 0;
      this.println("public:");
      this.tabs = 1;
      this.println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenStream& lexer);");
      this.println(this.grammar.getClassName() + "(const " + namespaceAntlr + "ParserSharedInputState& state);");
      if (this.noConstructors) {
         this.tabs = 0;
         this.println("// constructor creation turned of with 'noConstructor' option");
         this.println("#endif");
         this.tabs = 1;
      }

      this.println("int getNumTokens() const");
      this.println("{");
      ++this.tabs;
      this.println("return " + this.grammar.getClassName() + "::NUM_TOKENS;");
      --this.tabs;
      this.println("}");
      this.println("const char* getTokenName( int type ) const");
      this.println("{");
      ++this.tabs;
      this.println("if( type > getNumTokens() ) return 0;");
      this.println("return " + this.grammar.getClassName() + "::tokenNames[type];");
      --this.tabs;
      this.println("}");
      this.println("const char* const* getTokenNames() const");
      this.println("{");
      ++this.tabs;
      this.println("return " + this.grammar.getClassName() + "::tokenNames;");
      --this.tabs;
      this.println("}");

      for(Enumeration var7 = this.grammar.rules.elements(); var7.hasMoreElements(); this.exitIfError()) {
         GrammarSymbol var5 = (GrammarSymbol)var7.nextElement();
         if (var5 instanceof RuleSymbol) {
            RuleSymbol var6 = (RuleSymbol)var5;
            this.genRuleHeader(var6, var6.references.size() == 0);
         }
      }

      this.tabs = 0;
      this.println("public:");
      this.tabs = 1;
      this.println(namespaceAntlr + "RefAST getAST()");
      this.println("{");
      if (this.usingCustomAST) {
         ++this.tabs;
         this.println("return " + namespaceAntlr + "RefAST(returnAST);");
         --this.tabs;
      } else {
         ++this.tabs;
         this.println("return returnAST;");
         --this.tabs;
      }

      this.println("}");
      this.println("");
      this.tabs = 0;
      this.println("protected:");
      this.tabs = 1;
      this.println(this.labeledElementASTType + " returnAST;");
      this.tabs = 0;
      this.println("private:");
      this.tabs = 1;
      this.println("static const char* tokenNames[];");
      this._println("#ifndef NO_STATIC_CONSTS");
      this.println("static const int NUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size() + ";");
      this._println("#else");
      this.println("enum {");
      this.println("\tNUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size());
      this.println("};");
      this._println("#endif");
      this.genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
      if (this.grammar.debuggingOutput) {
         this.println("static const char* _semPredNames[];");
      }

      this.tabs = 0;
      this.println("};");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void genInclude(TreeWalkerGrammar var1) throws IOException {
      this.outputFile = this.grammar.getClassName() + ".hpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.genAST = this.grammar.buildAST;
      this.tabs = 0;
      this.println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
      this.println("#define INC_" + this.grammar.getClassName() + "_hpp_");
      this.println("");
      this.printHeaderAction("pre_include_hpp");
      this.println("#include <antlr/config.hpp>");
      this.println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
      this.genHeader(this.outputFile);
      String var2 = null;
      if (this.grammar.superClass != null) {
         var2 = this.grammar.superClass;
         this.println("\n// Include correct superclass header with a header statement for example:");
         this.println("// header \"post_include_hpp\" {");
         this.println("// #include \"" + var2 + ".hpp\"");
         this.println("// }");
         this.println("// Or....");
         this.println("// header {");
         this.println("// #include \"" + var2 + ".hpp\"");
         this.println("// }\n");
      } else {
         var2 = this.grammar.getSuperClass();
         if (var2.lastIndexOf(46) != -1) {
            var2 = var2.substring(var2.lastIndexOf(46) + 1);
         }

         this.println("#include <antlr/" + var2 + ".hpp>");
         var2 = namespaceAntlr + var2;
      }

      this.println("");
      this.printHeaderAction("post_include_hpp");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.printHeaderAction("");
      if (this.grammar.comment != null) {
         this._println(this.grammar.comment);
      }

      this.print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + var2);
      this.println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
      Token var3 = (Token)this.grammar.options.get("classHeaderSuffix");
      if (var3 != null) {
         String var4 = StringUtils.stripFrontBack(var3.getText(), "\"", "\"");
         if (var4 != null) {
            this.print(", " + var4);
         }
      }

      this.println("{");
      if (this.grammar.classMemberAction != null) {
         this.genLineNo(this.grammar.classMemberAction.getLine());
         this.print(this.processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, (ActionTransInfo)null));
         this.genLineNo2();
      }

      this.tabs = 0;
      this.println("public:");
      if (this.noConstructors) {
         this.println("#if 0");
         this.println("// constructor creation turned of with 'noConstructor' option");
      }

      this.tabs = 1;
      this.println(this.grammar.getClassName() + "();");
      if (this.noConstructors) {
         this.tabs = 0;
         this.println("#endif");
         this.tabs = 1;
      }

      this.println("static void initializeASTFactory( " + namespaceAntlr + "ASTFactory& factory );");
      this.println("int getNumTokens() const");
      this.println("{");
      ++this.tabs;
      this.println("return " + this.grammar.getClassName() + "::NUM_TOKENS;");
      --this.tabs;
      this.println("}");
      this.println("const char* getTokenName( int type ) const");
      this.println("{");
      ++this.tabs;
      this.println("if( type > getNumTokens() ) return 0;");
      this.println("return " + this.grammar.getClassName() + "::tokenNames[type];");
      --this.tabs;
      this.println("}");
      this.println("const char* const* getTokenNames() const");
      this.println("{");
      ++this.tabs;
      this.println("return " + this.grammar.getClassName() + "::tokenNames;");
      --this.tabs;
      this.println("}");
      Enumeration var8 = this.grammar.rules.elements();

      for(String var5 = ""; var8.hasMoreElements(); this.exitIfError()) {
         GrammarSymbol var6 = (GrammarSymbol)var8.nextElement();
         if (var6 instanceof RuleSymbol) {
            RuleSymbol var7 = (RuleSymbol)var6;
            this.genRuleHeader(var7, var7.references.size() == 0);
         }
      }

      this.tabs = 0;
      this.println("public:");
      this.tabs = 1;
      this.println(namespaceAntlr + "RefAST getAST()");
      this.println("{");
      if (this.usingCustomAST) {
         ++this.tabs;
         this.println("return " + namespaceAntlr + "RefAST(returnAST);");
         --this.tabs;
      } else {
         ++this.tabs;
         this.println("return returnAST;");
         --this.tabs;
      }

      this.println("}");
      this.println("");
      this.tabs = 0;
      this.println("protected:");
      this.tabs = 1;
      this.println(this.labeledElementASTType + " returnAST;");
      this.println(this.labeledElementASTType + " _retTree;");
      this.tabs = 0;
      this.println("private:");
      this.tabs = 1;
      this.println("static const char* tokenNames[];");
      this._println("#ifndef NO_STATIC_CONSTS");
      this.println("static const int NUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size() + ";");
      this._println("#else");
      this.println("enum {");
      this.println("\tNUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size());
      this.println("};");
      this._println("#endif");
      this.genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
      this.tabs = 0;
      this.println("};");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
      this.currentOutput.close();
      this.currentOutput = null;
   }

   protected void genASTDeclaration(AlternativeElement var1) {
      this.genASTDeclaration(var1, this.labeledElementASTType);
   }

   protected void genASTDeclaration(AlternativeElement var1, String var2) {
      this.genASTDeclaration(var1, var1.getLabel(), var2);
   }

   protected void genASTDeclaration(AlternativeElement var1, String var2, String var3) {
      if (!this.declaredASTVariables.contains(var1)) {
         String var4 = this.labeledElementASTInit;
         if (var1 instanceof GrammarAtom && ((GrammarAtom)var1).getASTNodeType() != null) {
            var4 = "Ref" + ((GrammarAtom)var1).getASTNodeType() + "(" + this.labeledElementASTInit + ")";
         }

         this.println(var3 + " " + var2 + "_AST = " + var4 + ";");
         this.declaredASTVariables.put(var1, var1);
      }
   }

   private void genLiteralsTest() {
      this.println("_ttype = testLiteralsTable(_ttype);");
   }

   private void genLiteralsTestForPartialToken() {
      this.println("_ttype = testLiteralsTable(text.substr(_begin, text.length()-_begin),_ttype);");
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
         this.antlrTool.error("cannot ref character literals in grammar: " + var1);
      } else if (var1 instanceof TokenRefElement) {
         this.genMatchUsingAtomTokenType(var1);
      } else if (var1 instanceof WildcardElement) {
         this.gen((WildcardElement)var1);
      }

   }

   protected void genMatchUsingAtomText(GrammarAtom var1) {
      String var2 = "";
      if (this.grammar instanceof TreeWalkerGrammar) {
         if (this.usingCustomAST) {
            var2 = namespaceAntlr + "RefAST" + "(_t),";
         } else {
            var2 = "_t,";
         }
      }

      if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
         this.println("_saveIndex = text.length();");
      }

      this.print(var1.not ? "matchNot(" : "match(");
      this._print(var2);
      if (var1.atomText.equals("EOF")) {
         this._print(namespaceAntlr + "Token::EOF_TYPE");
      } else if (this.grammar instanceof LexerGrammar) {
         String var3 = this.convertJavaToCppString(var1.atomText, false);
         this._print(var3);
      } else {
         this._print(var1.atomText);
      }

      this._println(");");
      if (this.grammar instanceof LexerGrammar && (!this.saveText || var1.getAutoGenType() == 3)) {
         this.println("text.erase(_saveIndex);");
      }

   }

   protected void genMatchUsingAtomTokenType(GrammarAtom var1) {
      String var2 = "";
      if (this.grammar instanceof TreeWalkerGrammar) {
         if (this.usingCustomAST) {
            var2 = namespaceAntlr + "RefAST" + "(_t),";
         } else {
            var2 = "_t,";
         }
      }

      String var3 = var2 + this.getValueString(var1.getType());
      this.println((var1.not ? "matchNot(" : "match(") + var3 + ");");
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
         this.println(namespaceAntlr + "RefToken " + this.grammar.getClassName() + "::nextToken() { return " + namespaceAntlr + "RefToken(new " + namespaceAntlr + "CommonToken(" + namespaceAntlr + "Token::EOF_TYPE, \"\")); }");
         this.println("");
      } else {
         RuleBlock var9 = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
         var3 = new RuleSymbol("mnextToken");
         var3.setDefined();
         var3.setBlock(var9);
         var3.access = "private";
         this.grammar.define(var3);
         this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var9);
         String var5 = null;
         if (((LexerGrammar)this.grammar).filterMode) {
            var5 = ((LexerGrammar)this.grammar).filterRule;
         }

         this.println("");
         this.println(namespaceAntlr + "RefToken " + this.grammar.getClassName() + "::nextToken()");
         this.println("{");
         ++this.tabs;
         this.println(namespaceAntlr + "RefToken theRetToken;");
         this.println("for (;;) {");
         ++this.tabs;
         this.println(namespaceAntlr + "RefToken theRetToken;");
         this.println("int _ttype = " + namespaceAntlr + "Token::INVALID_TYPE;");
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
         this.println("try {   // for lexical and char stream error handling");
         ++this.tabs;

         for(int var10 = 0; var10 < var9.getAlternatives().size(); ++var10) {
            Alternative var7 = var9.getAlternativeAt(var10);
            if (var7.cache[1].containsEpsilon()) {
               this.antlrTool.warning("found optional path in nextToken()");
            }
         }

         String var11 = System.getProperty("line.separator");
         CppBlockFinishingInfo var12 = this.genCommonBlock(var9, false);
         String var8 = "if (LA(1)==EOF_CHAR)" + var11 + "\t\t\t\t{" + var11 + "\t\t\t\t\tuponEOF();" + var11 + "\t\t\t\t\t_returnToken = makeToken(" + namespaceAntlr + "Token::EOF_TYPE);" + var11 + "\t\t\t\t}";
         var8 = var8 + var11 + "\t\t\t\t";
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var5 == null) {
               var8 = var8 + "else {consume(); goto tryAgain;}";
            } else {
               var8 = var8 + "else {" + var11 + "\t\t\t\t\tcommit();" + var11 + "\t\t\t\t\ttry {m" + var5 + "(false);}" + var11 + "\t\t\t\t\tcatch(" + namespaceAntlr + "RecognitionException& e) {" + var11 + "\t\t\t\t\t\t// catastrophic failure" + var11 + "\t\t\t\t\t\treportError(e);" + var11 + "\t\t\t\t\t\tconsume();" + var11 + "\t\t\t\t\t}" + var11 + "\t\t\t\t\tgoto tryAgain;" + var11 + "\t\t\t\t}";
            }
         } else {
            var8 = var8 + "else {" + this.throwNoViable + "}";
         }

         this.genBlockFinish(var12, var8);
         if (((LexerGrammar)this.grammar).filterMode && var5 != null) {
            this.println("commit();");
         }

         this.println("if ( !_returnToken )" + var11 + "\t\t\t\tgoto tryAgain; // found SKIP token" + var11);
         this.println("_ttype = _returnToken->getType();");
         if (((LexerGrammar)this.grammar).getTestLiterals()) {
            this.genLiteralsTest();
         }

         this.println("_returnToken->setType(_ttype);");
         this.println("return _returnToken;");
         --this.tabs;
         this.println("}");
         this.println("catch (" + namespaceAntlr + "RecognitionException& e) {");
         ++this.tabs;
         if (((LexerGrammar)this.grammar).filterMode) {
            if (var5 == null) {
               this.println("if ( !getCommitToPath() ) {");
               ++this.tabs;
               this.println("consume();");
               this.println("goto tryAgain;");
               --this.tabs;
               this.println("}");
            } else {
               this.println("if ( !getCommitToPath() ) {");
               ++this.tabs;
               this.println("rewind(_m);");
               this.println("resetText();");
               this.println("try {m" + var5 + "(false);}");
               this.println("catch(" + namespaceAntlr + "RecognitionException& ee) {");
               this.println("\t// horrendous failure: error in filter rule");
               this.println("\treportError(ee);");
               this.println("\tconsume();");
               this.println("}");
               --this.tabs;
               this.println("}");
               this.println("else");
            }
         }

         if (var9.getDefaultErrorHandler()) {
            this.println("{");
            ++this.tabs;
            this.println("reportError(e);");
            this.println("consume();");
            --this.tabs;
            this.println("}");
         } else {
            ++this.tabs;
            this.println("throw " + namespaceAntlr + "TokenStreamRecognitionException(e);");
            --this.tabs;
         }

         --this.tabs;
         this.println("}");
         this.println("catch (" + namespaceAntlr + "CharStreamIOException& csie) {");
         this.println("\tthrow " + namespaceAntlr + "TokenStreamIOException(csie.io);");
         this.println("}");
         this.println("catch (" + namespaceAntlr + "CharStreamException& cse) {");
         this.println("\tthrow " + namespaceAntlr + "TokenStreamException(cse.getMessage());");
         this.println("}");
         this._println("tryAgain:;");
         --this.tabs;
         this.println("}");
         --this.tabs;
         this.println("}");
         this.println("");
      }
   }

   public void genRule(RuleSymbol var1, boolean var2, int var3, String var4) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
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

         if (var5.returnAction != null) {
            this._print(this.extractTypeOfAction(var5.returnAction, var5.getLine(), var5.getColumn()) + " ");
         } else {
            this._print("void ");
         }

         this._print(var4 + var1.getId() + "(");
         this._print(this.commonExtraParams);
         if (this.commonExtraParams.length() != 0 && var5.argAction != null) {
            this._print(",");
         }

         String var9;
         if (var5.argAction != null) {
            this._println("");
            ++this.tabs;
            String var7 = var5.argAction;
            String var8 = "";
            var9 = "";
            int var10 = var7.indexOf(61);
            if (var10 != -1) {
               int var11 = 0;

               while(var11 != -1 && var10 != -1) {
                  var8 = var8 + var9 + var7.substring(0, var10).trim();
                  var9 = ", ";
                  var11 = var7.indexOf(44, var10);
                  if (var11 != -1) {
                     var7 = var7.substring(var11 + 1).trim();
                     var10 = var7.indexOf(61);
                     if (var10 == -1) {
                        var8 = var8 + var9 + var7;
                     }
                  }
               }
            } else {
               var8 = var7;
            }

            this.println(var8);
            --this.tabs;
            this.print(") ");
         } else {
            this._print(") ");
         }

         this._println("{");
         ++this.tabs;
         if (this.grammar.traceRules) {
            if (this.grammar instanceof TreeWalkerGrammar) {
               if (this.usingCustomAST) {
                  this.println("Tracer traceInOut(this,\"" + var1.getId() + "\"," + namespaceAntlr + "RefAST" + "(_t));");
               } else {
                  this.println("Tracer traceInOut(this,\"" + var1.getId() + "\",_t);");
               }
            } else {
               this.println("Tracer traceInOut(this, \"" + var1.getId() + "\");");
            }
         }

         if (var5.returnAction != null) {
            this.genLineNo((GrammarElement)var5);
            this.println(var5.returnAction + ";");
            this.genLineNo2();
         }

         if (!this.commonLocalVars.equals("")) {
            this.println(this.commonLocalVars);
         }

         if (this.grammar instanceof LexerGrammar) {
            if (var1.getId().equals("mEOF")) {
               this.println("_ttype = " + namespaceAntlr + "Token::EOF_TYPE;");
            } else {
               this.println("_ttype = " + var1.getId().substring(1) + ";");
            }

            this.println(namespaceStd + "string::size_type _saveIndex;");
         }

         if (this.grammar.debuggingOutput) {
            if (this.grammar instanceof ParserGrammar) {
               this.println("fireEnterRule(" + var3 + ",0);");
            } else if (this.grammar instanceof LexerGrammar) {
               this.println("fireEnterRule(" + var3 + ",_ttype);");
            }
         }

         if (this.grammar instanceof TreeWalkerGrammar) {
            this.println(this.labeledElementASTType + " " + var1.getId() + "_AST_in = (_t == " + this.labeledElementASTType + "(ASTNULL)) ? " + this.labeledElementASTInit + " : _t;");
         }

         if (this.grammar.buildAST) {
            this.println("returnAST = " + this.labeledElementASTInit + ";");
            this.println(namespaceAntlr + "ASTPair currentAST;");
            this.println(this.labeledElementASTType + " " + var1.getId() + "_AST = " + this.labeledElementASTInit + ";");
         }

         this.genBlockPreamble(var5);
         this.genBlockInitAction(var5);
         this.println("");
         ExceptionSpec var12 = var5.findExceptionSpec("");
         if (var12 != null || var5.getDefaultErrorHandler()) {
            this.println("try {      // for error handling");
            ++this.tabs;
         }

         if (var5.alternatives.size() == 1) {
            Alternative var13 = var5.getAlternativeAt(0);
            var9 = var13.semPred;
            if (var9 != null) {
               this.genSemPred(var9, this.currentRule.line);
            }

            if (var13.synPred != null) {
               this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), var13.synPred.getLine(), var13.synPred.getColumn());
            }

            this.genAlt(var13, var5);
         } else {
            this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var5);
            CppBlockFinishingInfo var14 = this.genCommonBlock(var5, false);
            this.genBlockFinish(var14, this.throwNoViable);
         }

         if (var12 != null || var5.getDefaultErrorHandler()) {
            --this.tabs;
            this.println("}");
         }

         if (var12 != null) {
            this.genErrorHandler(var12);
         } else if (var5.getDefaultErrorHandler()) {
            this.println("catch (" + this.exceptionThrown + "& ex) {");
            ++this.tabs;
            if (this.grammar.hasSyntacticPredicate) {
               this.println("if( inputState->guessing == 0 ) {");
               ++this.tabs;
            }

            this.println("reportError(ex);");
            if (!(this.grammar instanceof TreeWalkerGrammar)) {
               Lookahead var15 = this.grammar.theLLkAnalyzer.FOLLOW(1, var5.endNode);
               var9 = this.getBitsetName(this.markBitsetForGen(var15.fset));
               this.println("recover(ex," + var9 + ");");
            } else {
               this.println("if ( _t != " + this.labeledElementASTInit + " )");
               ++this.tabs;
               this.println("_t = _t->getNextSibling();");
               --this.tabs;
            }

            if (this.grammar.hasSyntacticPredicate) {
               --this.tabs;
               this.println("} else {");
               ++this.tabs;
               this.println("throw;");
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
            this.println("_retTree = _t;");
         }

         if (var5.getTestLiterals()) {
            if (var1.access.equals("protected")) {
               this.genLiteralsTestForPartialToken();
            } else {
               this.genLiteralsTest();
            }
         }

         if (this.grammar instanceof LexerGrammar) {
            this.println("if ( _createToken && _token==" + namespaceAntlr + "nullToken && _ttype!=" + namespaceAntlr + "Token::SKIP ) {");
            this.println("   _token = makeToken(_ttype);");
            this.println("   _token->setText(text.substr(_begin, text.length()-_begin));");
            this.println("}");
            this.println("_returnToken = _token;");
            this.println("_saveIndex=0;");
         }

         if (var5.returnAction != null) {
            this.println("return " + this.extractIdOfAction(var5.returnAction, var5.getLine(), var5.getColumn()) + ";");
         }

         --this.tabs;
         this.println("}");
         this.println("");
         this.genAST = var6;
      }
   }

   public void genRuleHeader(RuleSymbol var1, boolean var2) {
      this.tabs = 1;
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("genRuleHeader(" + var1.getId() + ")");
      }

      if (!var1.isDefined()) {
         this.antlrTool.error("undefined rule: " + var1.getId());
      } else {
         RuleBlock var3 = var1.getBlock();
         this.currentRule = var3;
         this.currentASTResult = var1.getId();
         boolean var4 = this.genAST;
         this.genAST = this.genAST && var3.getAutoGen();
         this.saveText = var3.getAutoGen();
         this.print(var1.access + ": ");
         if (var3.returnAction != null) {
            this._print(this.extractTypeOfAction(var3.returnAction, var3.getLine(), var3.getColumn()) + " ");
         } else {
            this._print("void ");
         }

         this._print(var1.getId() + "(");
         this._print(this.commonExtraParams);
         if (this.commonExtraParams.length() != 0 && var3.argAction != null) {
            this._print(",");
         }

         if (var3.argAction != null) {
            this._println("");
            ++this.tabs;
            this.println(var3.argAction);
            --this.tabs;
            this.print(")");
         } else {
            this._print(")");
         }

         this._println(";");
         --this.tabs;
         this.genAST = var4;
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
         String var4 = this.processActionForSpecialSymbols(var1.args, var1.line, this.currentRule, var3);
         if (var3.assignToRoot || var3.refRuleRoot != null) {
            this.antlrTool.error("Arguments of rule reference '" + var1.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName() + " on line " + var1.getLine());
         }

         this._print(var4);
         if (var2.block.argAction == null) {
            this.antlrTool.warning("Rule '" + var1.targetRule + "' accepts no arguments", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         }
      }

      this._println(");");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = _retTree;");
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
      ++this.tabs;
      this.println("throw " + namespaceAntlr + "SemanticException(\"" + var4 + "\");");
      --this.tabs;
   }

   protected void genSemPredMap(String var1) {
      Enumeration var2 = this.semPreds.elements();
      this.println("const char* " + var1 + "_semPredNames[] = {");
      ++this.tabs;

      while(var2.hasMoreElements()) {
         this.println("\"" + var2.nextElement() + "\",");
      }

      this.println("0");
      --this.tabs;
      this.println("};");
   }

   protected void genSynPred(SynPredBlock var1, String var2) {
      if (this.DEBUG_CODE_GENERATOR || this.DEBUG_CPP_CODE_GENERATOR) {
         System.out.println("gen=>(" + var1 + ")");
      }

      this.println("bool synPredMatched" + var1.ID + " = false;");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("if (_t == " + this.labeledElementASTInit + " )");
         ++this.tabs;
         this.println("_t = ASTNULL;");
         --this.tabs;
      }

      this.println("if (" + var2 + ") {");
      ++this.tabs;
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println(this.labeledElementType + " __t" + var1.ID + " = _t;");
      } else {
         this.println("int _m" + var1.ID + " = mark();");
      }

      this.println("synPredMatched" + var1.ID + " = true;");
      this.println("inputState->guessing++;");
      if (this.grammar.debuggingOutput && (this.grammar instanceof ParserGrammar || this.grammar instanceof LexerGrammar)) {
         this.println("fireSyntacticPredicateStarted();");
      }

      ++this.syntacticPredLevel;
      this.println("try {");
      ++this.tabs;
      this.gen((AlternativeBlock)var1);
      --this.tabs;
      this.println("}");
      this.println("catch (" + this.exceptionThrown + "& pe) {");
      ++this.tabs;
      this.println("synPredMatched" + var1.ID + " = false;");
      --this.tabs;
      this.println("}");
      if (this.grammar instanceof TreeWalkerGrammar) {
         this.println("_t = __t" + var1.ID + ";");
      } else {
         this.println("rewind(_m" + var1.ID + ");");
      }

      this.println("inputState->guessing--;");
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
   }

   public void genTokenStrings(String var1) {
      this.println("const char* " + var1 + "tokenNames[] = {");
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
         this._println(",");
      }

      this.println("0");
      --this.tabs;
      this.println("};");
   }

   protected void genTokenTypes(TokenManager var1) throws IOException {
      this.outputFile = var1.getName() + TokenTypesFileSuffix + ".hpp";
      this.outputLine = 1;
      this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
      this.tabs = 0;
      this.println("#ifndef INC_" + var1.getName() + TokenTypesFileSuffix + "_hpp_");
      this.println("#define INC_" + var1.getName() + TokenTypesFileSuffix + "_hpp_");
      this.println("");
      if (nameSpace != null) {
         nameSpace.emitDeclarations(this.currentOutput);
      }

      this.genHeader(this.outputFile);
      this.println("");
      this.println("#ifndef CUSTOM_API");
      this.println("# define CUSTOM_API");
      this.println("#endif");
      this.println("");
      this.println("#ifdef __cplusplus");
      this.println("struct CUSTOM_API " + var1.getName() + TokenTypesFileSuffix + " {");
      this.println("#endif");
      ++this.tabs;
      this.println("enum {");
      ++this.tabs;
      Vector var2 = var1.getVocabulary();
      this.println("EOF_ = 1,");

      for(int var3 = 4; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.elementAt(var3);
         if (var4 != null) {
            if (var4.startsWith("\"")) {
               StringLiteralSymbol var5 = (StringLiteralSymbol)var1.getTokenSymbol(var4);
               if (var5 == null) {
                  this.antlrTool.panic("String literal " + var4 + " not in symbol table");
               } else if (var5.label != null) {
                  this.println(var5.label + " = " + var3 + ",");
               } else {
                  String var6 = this.mangleLiteral(var4);
                  if (var6 != null) {
                     this.println(var6 + " = " + var3 + ",");
                     var5.label = var6;
                  } else {
                     this.println("// " + var4 + " = " + var3);
                  }
               }
            } else if (!var4.startsWith("<")) {
               this.println(var4 + " = " + var3 + ",");
            }
         }
      }

      this.println("NULL_TREE_LOOKAHEAD = 3");
      --this.tabs;
      this.println("};");
      --this.tabs;
      this.println("#ifdef __cplusplus");
      this.println("};");
      this.println("#endif");
      if (nameSpace != null) {
         nameSpace.emitClosures(this.currentOutput);
      }

      this.println("#endif /*INC_" + var1.getName() + TokenTypesFileSuffix + "_hpp_*/");
      this.currentOutput.close();
      this.currentOutput = null;
      this.exitIfError();
   }

   public String processStringForASTConstructor(String var1) {
      return this.usingCustomAST && (this.grammar instanceof TreeWalkerGrammar || this.grammar instanceof ParserGrammar) && !this.grammar.tokenManager.tokenDefined(var1) ? namespaceAntlr + "RefAST(" + var1 + ")" : var1;
   }

   public String getASTCreateString(Vector var1) {
      if (var1.size() == 0) {
         return "";
      } else {
         StringBuffer var2 = new StringBuffer();
         var2.append(this.labeledElementASTType + "(astFactory->make((new " + namespaceAntlr + "ASTArray(" + var1.size() + "))");

         for(int var3 = 0; var3 < var1.size(); ++var3) {
            var2.append("->add(" + var1.elementAt(var3) + ")");
         }

         var2.append("))");
         return var2.toString();
      }
   }

   public String getASTCreateString(GrammarAtom var1, String var2) {
      if (var1 != null && var1.getASTNodeType() != null) {
         this.astTypes.ensureCapacity(var1.getType());
         String var4 = (String)this.astTypes.elementAt(var1.getType());
         if (var4 == null) {
            this.astTypes.setElementAt(var1.getASTNodeType(), var1.getType());
         } else if (!var1.getASTNodeType().equals(var4)) {
            this.antlrTool.warning("Attempt to redefine AST type for " + var1.getText(), this.grammar.getFilename(), var1.getLine(), var1.getColumn());
            this.antlrTool.warning(" from \"" + var4 + "\" to \"" + var1.getASTNodeType() + "\" sticking to \"" + var4 + "\"", this.grammar.getFilename(), var1.getLine(), var1.getColumn());
         } else {
            this.astTypes.setElementAt(var1.getASTNodeType(), var1.getType());
         }

         return "astFactory->create(" + var2 + ")";
      } else {
         boolean var3 = false;
         if (var2.indexOf(44) != -1) {
            var3 = this.grammar.tokenManager.tokenDefined(var2.substring(0, var2.indexOf(44)));
         }

         return this.usingCustomAST && this.grammar instanceof TreeWalkerGrammar && !this.grammar.tokenManager.tokenDefined(var2) && !var3 ? "astFactory->create(" + namespaceAntlr + "RefAST(" + var2 + "))" : "astFactory->create(" + var2 + ")";
      }
   }

   public String getASTCreateString(String var1) {
      return this.usingCustomAST ? this.labeledElementASTType + "(astFactory->create(" + namespaceAntlr + "RefAST(" + var1 + ")))" : "astFactory->create(" + var1 + ")";
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

      return var2 == 0 ? "true" : "(" + this.getLookaheadTestExpression(var1.cache, var3) + ")";
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
                     var5.append(" || ");
                  }

                  var5.append(var3);
                  var5.append(" == ");
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
         } else if (var4.equals("EOF")) {
            var2 = namespaceAntlr + "Token::EOF_TYPE";
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
      return this.grammar instanceof TreeWalkerGrammar ? "_t->getType()" : "LA(" + var1 + ")";
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
            }

            if (var1.length() > 3 && var1.lastIndexOf("_in") == var1.length() - 3) {
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

   private String fixNameSpaceOption(String var1) {
      var1 = StringUtils.stripFrontBack(var1, "\"", "\"");
      if (var1.length() > 2 && !var1.substring(var1.length() - 2, var1.length()).equals("::")) {
         var1 = var1 + "::";
      }

      return var1;
   }

   private void setupGrammarParameters(Grammar var1) {
      Token var2;
      String var3;
      if (var1 instanceof ParserGrammar || var1 instanceof LexerGrammar || var1 instanceof TreeWalkerGrammar) {
         if (this.antlrTool.nameSpace != null) {
            nameSpace = this.antlrTool.nameSpace;
         }

         if (this.antlrTool.namespaceStd != null) {
            namespaceStd = this.fixNameSpaceOption(this.antlrTool.namespaceStd);
         }

         if (this.antlrTool.namespaceAntlr != null) {
            namespaceAntlr = this.fixNameSpaceOption(this.antlrTool.namespaceAntlr);
         }

         this.genHashLines = this.antlrTool.genHashLines;
         if (var1.hasOption("namespace")) {
            var2 = var1.getOption("namespace");
            if (var2 != null) {
               nameSpace = new NameSpace(var2.getText());
            }
         }

         if (var1.hasOption("namespaceAntlr")) {
            var2 = var1.getOption("namespaceAntlr");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  if (var3.length() > 2 && !var3.substring(var3.length() - 2, var3.length()).equals("::")) {
                     var3 = var3 + "::";
                  }

                  namespaceAntlr = var3;
               }
            }
         }

         if (var1.hasOption("namespaceStd")) {
            var2 = var1.getOption("namespaceStd");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  if (var3.length() > 2 && !var3.substring(var3.length() - 2, var3.length()).equals("::")) {
                     var3 = var3 + "::";
                  }

                  namespaceStd = var3;
               }
            }
         }

         if (var1.hasOption("genHashLines")) {
            var2 = var1.getOption("genHashLines");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               this.genHashLines = var3.equals("true");
            }
         }

         this.noConstructors = this.antlrTool.noConstructors;
         if (var1.hasOption("noConstructors")) {
            var2 = var1.getOption("noConstructors");
            if (var2 != null && !var2.getText().equals("true") && !var2.getText().equals("false")) {
               this.antlrTool.error("noConstructors option must be true or false", this.antlrTool.getGrammarFile(), var2.getLine(), var2.getColumn());
            }

            this.noConstructors = var2.getText().equals("true");
         }
      }

      if (var1 instanceof ParserGrammar) {
         this.labeledElementASTType = namespaceAntlr + "RefAST";
         this.labeledElementASTInit = namespaceAntlr + "nullAST";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.usingCustomAST = true;
                  this.labeledElementASTType = var3;
                  this.labeledElementASTInit = var3 + "(" + namespaceAntlr + "nullAST)";
               }
            }
         }

         this.labeledElementType = namespaceAntlr + "RefToken ";
         this.labeledElementInit = namespaceAntlr + "nullToken";
         this.commonExtraArgs = "";
         this.commonExtraParams = "";
         this.commonLocalVars = "";
         this.lt1Value = "LT(1)";
         this.exceptionThrown = namespaceAntlr + "RecognitionException";
         this.throwNoViable = "throw " + namespaceAntlr + "NoViableAltException(LT(1), getFilename());";
      } else if (var1 instanceof LexerGrammar) {
         this.labeledElementType = "char ";
         this.labeledElementInit = "'\\0'";
         this.commonExtraArgs = "";
         this.commonExtraParams = "bool _createToken";
         this.commonLocalVars = "int _ttype; " + namespaceAntlr + "RefToken _token; " + namespaceStd + "string::size_type _begin = text.length();";
         this.lt1Value = "LA(1)";
         this.exceptionThrown = namespaceAntlr + "RecognitionException";
         this.throwNoViable = "throw " + namespaceAntlr + "NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());";
      } else if (var1 instanceof TreeWalkerGrammar) {
         this.labeledElementInit = namespaceAntlr + "nullAST";
         this.labeledElementASTInit = namespaceAntlr + "nullAST";
         this.labeledElementASTType = namespaceAntlr + "RefAST";
         this.labeledElementType = namespaceAntlr + "RefAST";
         this.commonExtraParams = namespaceAntlr + "RefAST _t";
         this.throwNoViable = "throw " + namespaceAntlr + "NoViableAltException(_t);";
         this.lt1Value = "_t";
         if (var1.hasOption("ASTLabelType")) {
            var2 = var1.getOption("ASTLabelType");
            if (var2 != null) {
               var3 = StringUtils.stripFrontBack(var2.getText(), "\"", "\"");
               if (var3 != null) {
                  this.usingCustomAST = true;
                  this.labeledElementASTType = var3;
                  this.labeledElementType = var3;
                  this.labeledElementInit = var3 + "(" + namespaceAntlr + "nullAST)";
                  this.labeledElementASTInit = this.labeledElementInit;
                  this.commonExtraParams = var3 + " _t";
                  this.throwNoViable = "throw " + namespaceAntlr + "NoViableAltException(" + namespaceAntlr + "RefAST(_t));";
                  this.lt1Value = "_t";
               }
            }
         }

         if (!var1.hasOption("ASTLabelType")) {
            var1.setOption("ASTLabelType", new Token(6, namespaceAntlr + "RefAST"));
         }

         this.commonExtraArgs = "_t";
         this.commonLocalVars = "";
         this.exceptionThrown = namespaceAntlr + "RecognitionException";
      } else {
         this.antlrTool.panic("Unknown grammar type");
      }

   }
}
