package antlr;

import antlr.collections.impl.Vector;
import java.io.IOException;
import java.util.Enumeration;

public class DiagnosticCodeGenerator extends CodeGenerator {
   protected int syntacticPredLevel = 0;
   protected boolean doingLexRules = false;

   public DiagnosticCodeGenerator() {
      this.charFormatter = new JavaCharFormatter();
   }

   public void gen() {
      try {
         Enumeration var1 = this.behavior.grammars.elements();

         while(var1.hasMoreElements()) {
            Grammar var2 = (Grammar)var1.nextElement();
            var2.setGrammarAnalyzer(this.analyzer);
            var2.setCodeGenerator(this);
            this.analyzer.setGrammar(var2);
            var2.generate();
            if (this.antlrTool.hasError()) {
               this.antlrTool.panic("Exiting due to errors.");
            }
         }

         Enumeration var5 = this.behavior.tokenManagers.elements();

         while(var5.hasMoreElements()) {
            TokenManager var3 = (TokenManager)var5.nextElement();
            if (!var3.isReadOnly()) {
               this.genTokenTypes(var3);
            }
         }
      } catch (IOException var4) {
         this.antlrTool.reportException(var4, (String)null);
      }

   }

   public void gen(ActionElement var1) {
      if (!var1.isSemPred) {
         this.print("ACTION: ");
         this._printAction(var1.actionText);
      }

   }

   public void gen(AlternativeBlock var1) {
      this.println("Start of alternative block.");
      ++this.tabs;
      this.genBlockPreamble(var1);
      boolean var2 = this.grammar.theLLkAnalyzer.deterministic(var1);
      if (!var2) {
         this.println("Warning: This alternative block is non-deterministic");
      }

      this.genCommonBlock(var1);
      --this.tabs;
   }

   public void gen(BlockEndElement var1) {
   }

   public void gen(CharLiteralElement var1) {
      this.print("Match character ");
      if (var1.not) {
         this._print("NOT ");
      }

      this._print(var1.atomText);
      if (var1.label != null) {
         this._print(", label=" + var1.label);
      }

      this._println("");
   }

   public void gen(CharRangeElement var1) {
      this.print("Match character range: " + var1.beginText + ".." + var1.endText);
      if (var1.label != null) {
         this._print(", label = " + var1.label);
      }

      this._println("");
   }

   public void gen(LexerGrammar var1) throws IOException {
      this.setGrammar(var1);
      this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
      this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
      this.tabs = 0;
      this.doingLexRules = true;
      this.genHeader();
      this.println("");
      this.println("*** Lexer Preamble Action.");
      this.println("This action will appear before the declaration of your lexer class:");
      ++this.tabs;
      this.println(this.grammar.preambleAction.getText());
      --this.tabs;
      this.println("*** End of Lexer Preamble Action");
      this.println("");
      this.println("*** Your lexer class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
      this.println("");
      this.println("*** User-defined lexer  class members:");
      this.println("These are the member declarations that you defined for your class:");
      ++this.tabs;
      this.printAction(this.grammar.classMemberAction.getText());
      --this.tabs;
      this.println("*** End of user-defined lexer class members");
      this.println("");
      this.println("*** String literals used in the parser");
      this.println("The following string literals were used in the parser.");
      this.println("An actual code generator would arrange to place these literals");
      this.println("into a table in the generated lexer, so that actions in the");
      this.println("generated lexer could match token text against the literals.");
      this.println("String literals used in the lexer are not listed here, as they");
      this.println("are incorporated into the mainstream lexer processing.");
      ++this.tabs;
      Enumeration var2 = this.grammar.getSymbols();

      while(var2.hasMoreElements()) {
         GrammarSymbol var3 = (GrammarSymbol)var2.nextElement();
         if (var3 instanceof StringLiteralSymbol) {
            StringLiteralSymbol var4 = (StringLiteralSymbol)var3;
            this.println(var4.getId() + " = " + var4.getTokenType());
         }
      }

      --this.tabs;
      this.println("*** End of string literals used by the parser");
      this.genNextToken();
      this.println("");
      this.println("*** User-defined Lexer rules:");
      ++this.tabs;
      var2 = this.grammar.rules.elements();

      while(var2.hasMoreElements()) {
         RuleSymbol var5 = (RuleSymbol)var2.nextElement();
         if (!var5.id.equals("mnextToken")) {
            this.genRule(var5);
         }
      }

      --this.tabs;
      this.println("");
      this.println("*** End User-defined Lexer rules:");
      this.currentOutput.close();
      this.currentOutput = null;
      this.doingLexRules = false;
   }

   public void gen(OneOrMoreBlock var1) {
      this.println("Start ONE-OR-MORE (...)+ block:");
      ++this.tabs;
      this.genBlockPreamble(var1);
      boolean var2 = this.grammar.theLLkAnalyzer.deterministic(var1);
      if (!var2) {
         this.println("Warning: This one-or-more block is non-deterministic");
      }

      this.genCommonBlock(var1);
      --this.tabs;
      this.println("End ONE-OR-MORE block.");
   }

   public void gen(ParserGrammar var1) throws IOException {
      this.setGrammar(var1);
      this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
      this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
      this.tabs = 0;
      this.genHeader();
      this.println("");
      this.println("*** Parser Preamble Action.");
      this.println("This action will appear before the declaration of your parser class:");
      ++this.tabs;
      this.println(this.grammar.preambleAction.getText());
      --this.tabs;
      this.println("*** End of Parser Preamble Action");
      this.println("");
      this.println("*** Your parser class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
      this.println("");
      this.println("*** User-defined parser class members:");
      this.println("These are the member declarations that you defined for your class:");
      ++this.tabs;
      this.printAction(this.grammar.classMemberAction.getText());
      --this.tabs;
      this.println("*** End of user-defined parser class members");
      this.println("");
      this.println("*** Parser rules:");
      ++this.tabs;
      Enumeration var2 = this.grammar.rules.elements();

      while(var2.hasMoreElements()) {
         this.println("");
         GrammarSymbol var3 = (GrammarSymbol)var2.nextElement();
         if (var3 instanceof RuleSymbol) {
            this.genRule((RuleSymbol)var3);
         }
      }

      --this.tabs;
      this.println("");
      this.println("*** End of parser rules");
      this.println("");
      this.println("*** End of parser");
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void gen(RuleRefElement var1) {
      RuleSymbol var2 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
      this.print("Rule Reference: " + var1.targetRule);
      if (var1.idAssign != null) {
         this._print(", assigned to '" + var1.idAssign + "'");
      }

      if (var1.args != null) {
         this._print(", arguments = " + var1.args);
      }

      this._println("");
      if (var2 != null && var2.isDefined()) {
         if (!(var2 instanceof RuleSymbol)) {
            this.println("Rule '" + var1.targetRule + "' is referenced, but that is not a grammar rule.");
         } else {
            if (var1.idAssign != null) {
               if (var2.block.returnAction == null) {
                  this.println("Error: You assigned from Rule '" + var1.targetRule + "', but that rule has no return type.");
               }
            } else if (!(this.grammar instanceof LexerGrammar) && this.syntacticPredLevel == 0 && var2.block.returnAction != null) {
               this.println("Warning: Rule '" + var1.targetRule + "' returns a value");
            }

            if (var1.args != null && var2.block.argAction == null) {
               this.println("Error: Rule '" + var1.targetRule + "' accepts no arguments.");
            }

         }
      } else {
         this.println("Rule '" + var1.targetRule + "' is referenced, but that rule is not defined.");
         this.println("\tPerhaps the rule is misspelled, or you forgot to define it.");
      }
   }

   public void gen(StringLiteralElement var1) {
      this.print("Match string literal ");
      this._print(var1.atomText);
      if (var1.label != null) {
         this._print(", label=" + var1.label);
      }

      this._println("");
   }

   public void gen(TokenRangeElement var1) {
      this.print("Match token range: " + var1.beginText + ".." + var1.endText);
      if (var1.label != null) {
         this._print(", label = " + var1.label);
      }

      this._println("");
   }

   public void gen(TokenRefElement var1) {
      this.print("Match token ");
      if (var1.not) {
         this._print("NOT ");
      }

      this._print(var1.atomText);
      if (var1.label != null) {
         this._print(", label=" + var1.label);
      }

      this._println("");
   }

   public void gen(TreeElement var1) {
      this.print("Tree reference: " + var1);
   }

   public void gen(TreeWalkerGrammar var1) throws IOException {
      this.setGrammar(var1);
      this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + TokenTypesFileExt);
      this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + TokenTypesFileExt);
      this.tabs = 0;
      this.genHeader();
      this.println("");
      this.println("*** Tree-walker Preamble Action.");
      this.println("This action will appear before the declaration of your tree-walker class:");
      ++this.tabs;
      this.println(this.grammar.preambleAction.getText());
      --this.tabs;
      this.println("*** End of tree-walker Preamble Action");
      this.println("");
      this.println("*** Your tree-walker class is called '" + this.grammar.getClassName() + "' and is a subclass of '" + this.grammar.getSuperClass() + "'.");
      this.println("");
      this.println("*** User-defined tree-walker class members:");
      this.println("These are the member declarations that you defined for your class:");
      ++this.tabs;
      this.printAction(this.grammar.classMemberAction.getText());
      --this.tabs;
      this.println("*** End of user-defined tree-walker class members");
      this.println("");
      this.println("*** tree-walker rules:");
      ++this.tabs;
      Enumeration var2 = this.grammar.rules.elements();

      while(var2.hasMoreElements()) {
         this.println("");
         GrammarSymbol var3 = (GrammarSymbol)var2.nextElement();
         if (var3 instanceof RuleSymbol) {
            this.genRule((RuleSymbol)var3);
         }
      }

      --this.tabs;
      this.println("");
      this.println("*** End of tree-walker rules");
      this.println("");
      this.println("*** End of tree-walker");
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void gen(WildcardElement var1) {
      this.print("Match wildcard");
      if (var1.getLabel() != null) {
         this._print(", label = " + var1.getLabel());
      }

      this._println("");
   }

   public void gen(ZeroOrMoreBlock var1) {
      this.println("Start ZERO-OR-MORE (...)+ block:");
      ++this.tabs;
      this.genBlockPreamble(var1);
      boolean var2 = this.grammar.theLLkAnalyzer.deterministic(var1);
      if (!var2) {
         this.println("Warning: This zero-or-more block is non-deterministic");
      }

      this.genCommonBlock(var1);
      --this.tabs;
      this.println("End ZERO-OR-MORE block.");
   }

   protected void genAlt(Alternative var1) {
      for(AlternativeElement var2 = var1.head; !(var2 instanceof BlockEndElement); var2 = var2.next) {
         var2.generate();
      }

      if (var1.getTreeSpecifier() != null) {
         this.println("AST will be built as: " + var1.getTreeSpecifier().getText());
      }

   }

   protected void genBlockPreamble(AlternativeBlock var1) {
      if (var1.initAction != null) {
         this.printAction("Init action: " + var1.initAction);
      }

   }

   public void genCommonBlock(AlternativeBlock var1) {
      boolean var2 = var1.alternatives.size() == 1;
      this.println("Start of an alternative block.");
      ++this.tabs;
      this.println("The lookahead set for this block is:");
      ++this.tabs;
      this.genLookaheadSetForBlock(var1);
      --this.tabs;
      if (var2) {
         this.println("This block has a single alternative");
         if (var1.getAlternativeAt(0).synPred != null) {
            this.println("Warning: you specified a syntactic predicate for this alternative,");
            this.println("and it is the only alternative of a block and will be ignored.");
         }
      } else {
         this.println("This block has multiple alternatives:");
         ++this.tabs;
      }

      for(int var3 = 0; var3 < var1.alternatives.size(); ++var3) {
         Alternative var4 = var1.getAlternativeAt(var3);
         AlternativeElement var5 = var4.head;
         this.println("");
         if (var3 != 0) {
            this.print("Otherwise, ");
         } else {
            this.print("");
         }

         this._println("Alternate(" + (var3 + 1) + ") will be taken IF:");
         this.println("The lookahead set: ");
         ++this.tabs;
         this.genLookaheadSetForAlt(var4);
         --this.tabs;
         if (var4.semPred == null && var4.synPred == null) {
            this.println("is matched.");
         } else {
            this.print("is matched, AND ");
         }

         if (var4.semPred != null) {
            this._println("the semantic predicate:");
            ++this.tabs;
            this.println(var4.semPred);
            if (var4.synPred != null) {
               this.print("is true, AND ");
            } else {
               this.println("is true.");
            }
         }

         if (var4.synPred != null) {
            this._println("the syntactic predicate:");
            ++this.tabs;
            this.genSynPred(var4.synPred);
            --this.tabs;
            this.println("is matched.");
         }

         this.genAlt(var4);
      }

      this.println("");
      this.println("OTHERWISE, a NoViableAlt exception will be thrown");
      this.println("");
      if (!var2) {
         --this.tabs;
         this.println("End of alternatives");
      }

      --this.tabs;
      this.println("End of alternative block.");
   }

   public void genFollowSetForRuleBlock(RuleBlock var1) {
      Lookahead var2 = this.grammar.theLLkAnalyzer.FOLLOW(1, var1.endNode);
      this.printSet(this.grammar.maxk, 1, var2);
   }

   protected void genHeader() {
      this.println("ANTLR-generated file resulting from grammar " + this.antlrTool.grammarFile);
      this.println("Diagnostic output");
      this.println("");
      this.println("Terence Parr, MageLang Institute");
      this.println("with John Lilley, Empathy Software");
      StringBuffer var10001 = (new StringBuffer()).append("ANTLR Version ");
      Tool var10002 = this.antlrTool;
      this.println(var10001.append(Tool.version).append("; 1989-2005").toString());
      this.println("");
      this.println("*** Header Action.");
      this.println("This action will appear at the top of all generated files.");
      ++this.tabs;
      this.printAction(this.behavior.getHeaderAction(""));
      --this.tabs;
      this.println("*** End of Header Action");
      this.println("");
   }

   protected void genLookaheadSetForAlt(Alternative var1) {
      if (this.doingLexRules && var1.cache[1].containsEpsilon()) {
         this.println("MATCHES ALL");
      } else {
         int var2 = var1.lookaheadDepth;
         if (var2 == Integer.MAX_VALUE) {
            var2 = this.grammar.maxk;
         }

         for(int var3 = 1; var3 <= var2; ++var3) {
            Lookahead var4 = var1.cache[var3];
            this.printSet(var2, var3, var4);
         }

      }
   }

   public void genLookaheadSetForBlock(AlternativeBlock var1) {
      int var2 = 0;

      int var3;
      for(var3 = 0; var3 < var1.alternatives.size(); ++var3) {
         Alternative var4 = var1.getAlternativeAt(var3);
         if (var4.lookaheadDepth == Integer.MAX_VALUE) {
            var2 = this.grammar.maxk;
            break;
         }

         if (var2 < var4.lookaheadDepth) {
            var2 = var4.lookaheadDepth;
         }
      }

      for(var3 = 1; var3 <= var2; ++var3) {
         Lookahead var5 = this.grammar.theLLkAnalyzer.look(var3, var1);
         this.printSet(var2, var3, var5);
      }

   }

   public void genNextToken() {
      this.println("");
      this.println("*** Lexer nextToken rule:");
      this.println("The lexer nextToken rule is synthesized from all of the user-defined");
      this.println("lexer rules.  It logically consists of one big alternative block with");
      this.println("each user-defined rule being an alternative.");
      this.println("");
      RuleBlock var1 = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
      RuleSymbol var2 = new RuleSymbol("mnextToken");
      var2.setDefined();
      var2.setBlock(var1);
      var2.access = "private";
      this.grammar.define(var2);
      if (!this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var1)) {
         this.println("The grammar analyzer has determined that the synthesized");
         this.println("nextToken rule is non-deterministic (i.e., it has ambiguities)");
         this.println("This means that there is some overlap of the character");
         this.println("lookahead for two or more of your lexer rules.");
      }

      this.genCommonBlock(var1);
      this.println("*** End of nextToken lexer rule.");
   }

   public void genRule(RuleSymbol var1) {
      this.println("");
      String var2 = this.doingLexRules ? "Lexer" : "Parser";
      this.println("*** " + var2 + " Rule: " + var1.getId());
      if (!var1.isDefined()) {
         this.println("This rule is undefined.");
         this.println("This means that the rule was referenced somewhere in the grammar,");
         this.println("but a definition for the rule was not encountered.");
         this.println("It is also possible that syntax errors during the parse of");
         this.println("your grammar file prevented correct processing of the rule.");
         this.println("*** End " + var2 + " Rule: " + var1.getId());
      } else {
         ++this.tabs;
         if (var1.access.length() != 0) {
            this.println("Access: " + var1.access);
         }

         RuleBlock var3 = var1.getBlock();
         if (var3.returnAction != null) {
            this.println("Return value(s): " + var3.returnAction);
            if (this.doingLexRules) {
               this.println("Error: you specified return value(s) for a lexical rule.");
               this.println("\tLexical rules have an implicit return type of 'int'.");
            }
         } else if (this.doingLexRules) {
            this.println("Return value: lexical rule returns an implicit token type");
         } else {
            this.println("Return value: none");
         }

         if (var3.argAction != null) {
            this.println("Arguments: " + var3.argAction);
         }

         this.genBlockPreamble(var3);
         boolean var4 = this.grammar.theLLkAnalyzer.deterministic((AlternativeBlock)var3);
         if (!var4) {
            this.println("Error: This rule is non-deterministic");
         }

         this.genCommonBlock(var3);
         ExceptionSpec var5 = var3.findExceptionSpec("");
         if (var5 != null) {
            this.println("You specified error-handler(s) for this rule:");
            ++this.tabs;

            for(int var6 = 0; var6 < var5.handlers.size(); ++var6) {
               if (var6 != 0) {
                  this.println("");
               }

               ExceptionHandler var7 = (ExceptionHandler)var5.handlers.elementAt(var6);
               this.println("Error-handler(" + (var6 + 1) + ") catches [" + var7.exceptionTypeAndName.getText() + "] and executes:");
               this.printAction(var7.action.getText());
            }

            --this.tabs;
            this.println("End error-handlers.");
         } else if (!this.doingLexRules) {
            this.println("Default error-handling will be generated, which catches all");
            this.println("parser exceptions and consumes tokens until the follow-set is seen.");
         }

         if (!this.doingLexRules) {
            this.println("The follow set for this rule is:");
            ++this.tabs;
            this.genFollowSetForRuleBlock(var3);
            --this.tabs;
         }

         --this.tabs;
         this.println("*** End " + var2 + " Rule: " + var1.getId());
      }
   }

   protected void genSynPred(SynPredBlock var1) {
      ++this.syntacticPredLevel;
      this.gen((AlternativeBlock)var1);
      --this.syntacticPredLevel;
   }

   protected void genTokenTypes(TokenManager var1) throws IOException {
      this.antlrTool.reportProgress("Generating " + var1.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
      this.currentOutput = this.antlrTool.openOutputFile(var1.getName() + TokenTypesFileSuffix + TokenTypesFileExt);
      this.tabs = 0;
      this.genHeader();
      this.println("");
      this.println("*** Tokens used by the parser");
      this.println("This is a list of the token numeric values and the corresponding");
      this.println("token identifiers.  Some tokens are literals, and because of that");
      this.println("they have no identifiers.  Literals are double-quoted.");
      ++this.tabs;
      Vector var2 = var1.getVocabulary();

      for(int var3 = 4; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.elementAt(var3);
         if (var4 != null) {
            this.println(var4 + " = " + var3);
         }
      }

      --this.tabs;
      this.println("*** End of tokens used by the parser");
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public String getASTCreateString(Vector var1) {
      return "***Create an AST from a vector here***" + System.getProperty("line.separator");
   }

   public String getASTCreateString(GrammarAtom var1, String var2) {
      return "[" + var2 + "]";
   }

   protected String processActionForSpecialSymbols(String var1, int var2, RuleBlock var3, ActionTransInfo var4) {
      return var1;
   }

   public String mapTreeId(String var1, ActionTransInfo var2) {
      return var1;
   }

   public void printSet(int var1, int var2, Lookahead var3) {
      byte var4 = 5;
      int[] var5 = var3.fset.toArray();
      if (var1 != 1) {
         this.print("k==" + var2 + ": {");
      } else {
         this.print("{ ");
      }

      if (var5.length > var4) {
         this._println("");
         ++this.tabs;
         this.print("");
      }

      int var6 = 0;

      for(int var7 = 0; var7 < var5.length; ++var7) {
         ++var6;
         if (var6 > var4) {
            this._println("");
            this.print("");
            var6 = 0;
         }

         if (this.doingLexRules) {
            this._print(this.charFormatter.literalChar(var5[var7]));
         } else {
            this._print((String)this.grammar.tokenManager.getVocabulary().elementAt(var5[var7]));
         }

         if (var7 != var5.length - 1) {
            this._print(", ");
         }
      }

      if (var5.length > var4) {
         this._println("");
         --this.tabs;
         this.print("");
      }

      this._println(" }");
   }
}
