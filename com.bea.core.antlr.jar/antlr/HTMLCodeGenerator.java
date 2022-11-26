package antlr;

import antlr.collections.impl.Vector;
import java.io.IOException;
import java.util.Enumeration;

public class HTMLCodeGenerator extends CodeGenerator {
   protected int syntacticPredLevel = 0;
   protected boolean doingLexRules = false;
   protected boolean firstElementInAlt;
   protected AlternativeElement prevAltElem = null;

   public HTMLCodeGenerator() {
      this.charFormatter = new JavaCharFormatter();
   }

   static String HTMLEncode(String var0) {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      for(int var3 = var0.length(); var2 < var3; ++var2) {
         char var4 = var0.charAt(var2);
         if (var4 == '&') {
            var1.append("&amp;");
         } else if (var4 == '"') {
            var1.append("&quot;");
         } else if (var4 == '\'') {
            var1.append("&#039;");
         } else if (var4 == '<') {
            var1.append("&lt;");
         } else if (var4 == '>') {
            var1.append("&gt;");
         } else {
            var1.append(var4);
         }
      }

      return var1.toString();
   }

   public void gen() {
      try {
         Enumeration var1 = this.behavior.grammars.elements();

         while(var1.hasMoreElements()) {
            Grammar var2 = (Grammar)var1.nextElement();
            var2.setCodeGenerator(this);
            var2.generate();
            if (this.antlrTool.hasError()) {
               this.antlrTool.fatalError("Exiting due to errors.");
            }
         }
      } catch (IOException var3) {
         this.antlrTool.reportException(var3, (String)null);
      }

   }

   public void gen(ActionElement var1) {
   }

   public void gen(AlternativeBlock var1) {
      this.genGenericBlock(var1, "");
   }

   public void gen(BlockEndElement var1) {
   }

   public void gen(CharLiteralElement var1) {
      if (var1.not) {
         this._print("~");
      }

      this._print(HTMLEncode(var1.atomText) + " ");
   }

   public void gen(CharRangeElement var1) {
      this.print(var1.beginText + ".." + var1.endText + " ");
   }

   public void gen(LexerGrammar var1) throws IOException {
      this.setGrammar(var1);
      this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
      this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
      this.tabs = 0;
      this.doingLexRules = true;
      this.genHeader();
      this.println("");
      if (this.grammar.comment != null) {
         this._println(HTMLEncode(this.grammar.comment));
      }

      this.println("Definition of lexer " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
      this.genNextToken();
      Enumeration var2 = this.grammar.rules.elements();

      while(var2.hasMoreElements()) {
         RuleSymbol var3 = (RuleSymbol)var2.nextElement();
         if (!var3.id.equals("mnextToken")) {
            this.genRule(var3);
         }
      }

      this.currentOutput.close();
      this.currentOutput = null;
      this.doingLexRules = false;
   }

   public void gen(OneOrMoreBlock var1) {
      this.genGenericBlock(var1, "+");
   }

   public void gen(ParserGrammar var1) throws IOException {
      this.setGrammar(var1);
      this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
      this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
      this.tabs = 0;
      this.genHeader();
      this.println("");
      if (this.grammar.comment != null) {
         this._println(HTMLEncode(this.grammar.comment));
      }

      this.println("Definition of parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
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
      this.genTail();
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void gen(RuleRefElement var1) {
      RuleSymbol var2 = (RuleSymbol)this.grammar.getSymbol(var1.targetRule);
      this._print("<a href=\"" + this.grammar.getClassName() + ".html#" + var1.targetRule + "\">");
      this._print(var1.targetRule);
      this._print("</a>");
      this._print(" ");
   }

   public void gen(StringLiteralElement var1) {
      if (var1.not) {
         this._print("~");
      }

      this._print(HTMLEncode(var1.atomText));
      this._print(" ");
   }

   public void gen(TokenRangeElement var1) {
      this.print(var1.beginText + ".." + var1.endText + " ");
   }

   public void gen(TokenRefElement var1) {
      if (var1.not) {
         this._print("~");
      }

      this._print(var1.atomText);
      this._print(" ");
   }

   public void gen(TreeElement var1) {
      this.print(var1 + " ");
   }

   public void gen(TreeWalkerGrammar var1) throws IOException {
      this.setGrammar(var1);
      this.antlrTool.reportProgress("Generating " + this.grammar.getClassName() + ".html");
      this.currentOutput = this.antlrTool.openOutputFile(this.grammar.getClassName() + ".html");
      this.tabs = 0;
      this.genHeader();
      this.println("");
      this.println("");
      if (this.grammar.comment != null) {
         this._println(HTMLEncode(this.grammar.comment));
      }

      this.println("Definition of tree parser " + this.grammar.getClassName() + ", which is a subclass of " + this.grammar.getSuperClass() + ".");
      this.println("");
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
      this.currentOutput.close();
      this.currentOutput = null;
   }

   public void gen(WildcardElement var1) {
      this._print(". ");
   }

   public void gen(ZeroOrMoreBlock var1) {
      this.genGenericBlock(var1, "*");
   }

   protected void genAlt(Alternative var1) {
      if (var1.getTreeSpecifier() != null) {
         this._print(var1.getTreeSpecifier().getText());
      }

      this.prevAltElem = null;

      for(AlternativeElement var2 = var1.head; !(var2 instanceof BlockEndElement); var2 = var2.next) {
         var2.generate();
         this.firstElementInAlt = false;
         this.prevAltElem = var2;
      }

   }

   public void genCommonBlock(AlternativeBlock var1) {
      for(int var2 = 0; var2 < var1.alternatives.size(); ++var2) {
         Alternative var3 = var1.getAlternativeAt(var2);
         AlternativeElement var4 = var3.head;
         if (var2 > 0 && var1.alternatives.size() > 1) {
            this._println("");
            this.print("|\t");
         }

         boolean var5 = this.firstElementInAlt;
         this.firstElementInAlt = true;
         ++this.tabs;
         this.genAlt(var3);
         --this.tabs;
         this.firstElementInAlt = var5;
      }

   }

   public void genFollowSetForRuleBlock(RuleBlock var1) {
      Lookahead var2 = this.grammar.theLLkAnalyzer.FOLLOW(1, var1.endNode);
      this.printSet(this.grammar.maxk, 1, var2);
   }

   protected void genGenericBlock(AlternativeBlock var1, String var2) {
      if (var1.alternatives.size() > 1) {
         if (!this.firstElementInAlt) {
            if (this.prevAltElem != null && this.prevAltElem instanceof AlternativeBlock && ((AlternativeBlock)this.prevAltElem).alternatives.size() != 1) {
               this._print("(\t");
            } else {
               this._println("");
               this.print("(\t");
            }
         } else {
            this._print("(\t");
         }
      } else {
         this._print("( ");
      }

      this.genCommonBlock(var1);
      if (var1.alternatives.size() > 1) {
         this._println("");
         this.print(")" + var2 + " ");
         if (!(var1.next instanceof BlockEndElement)) {
            this._println("");
            this.print("");
         }
      } else {
         this._print(")" + var2 + " ");
      }

   }

   protected void genHeader() {
      this.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
      this.println("<HTML>");
      this.println("<HEAD>");
      this.println("<TITLE>Grammar " + this.antlrTool.grammarFile + "</TITLE>");
      this.println("</HEAD>");
      this.println("<BODY>");
      this.println("<table summary=\"\" border=\"1\" cellpadding=\"5\">");
      this.println("<tr>");
      this.println("<td>");
      this.println("<font size=\"+2\">Grammar " + this.grammar.getClassName() + "</font><br>");
      this.println("<a href=\"http://www.ANTLR.org\">ANTLR</a>-generated HTML file from " + this.antlrTool.grammarFile);
      this.println("<p>");
      this.println("Terence Parr, <a href=\"http://www.magelang.com\">MageLang Institute</a>");
      StringBuffer var10001 = (new StringBuffer()).append("<br>ANTLR Version ");
      Tool var10002 = this.antlrTool;
      this.println(var10001.append(Tool.version).append("; 1989-2005").toString());
      this.println("</td>");
      this.println("</tr>");
      this.println("</table>");
      this.println("<PRE>");
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
      this.println("/** Lexer nextToken rule:");
      this.println(" *  The lexer nextToken rule is synthesized from all of the user-defined");
      this.println(" *  lexer rules.  It logically consists of one big alternative block with");
      this.println(" *  each user-defined rule being an alternative.");
      this.println(" */");
      RuleBlock var1 = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
      RuleSymbol var2 = new RuleSymbol("mnextToken");
      var2.setDefined();
      var2.setBlock(var1);
      var2.access = "private";
      this.grammar.define(var2);
      this.genCommonBlock(var1);
   }

   public void genRule(RuleSymbol var1) {
      if (var1 != null && var1.isDefined()) {
         this.println("");
         if (var1.comment != null) {
            this._println(HTMLEncode(var1.comment));
         }

         if (var1.access.length() != 0 && !var1.access.equals("public")) {
            this._print(var1.access + " ");
         }

         this._print("<a name=\"" + var1.getId() + "\">");
         this._print(var1.getId());
         this._print("</a>");
         RuleBlock var2 = var1.getBlock();
         this._println("");
         ++this.tabs;
         this.print(":\t");
         this.genCommonBlock(var2);
         this._println("");
         this.println(";");
         --this.tabs;
      }
   }

   protected void genSynPred(SynPredBlock var1) {
      ++this.syntacticPredLevel;
      this.genGenericBlock(var1, " =>");
      --this.syntacticPredLevel;
   }

   public void genTail() {
      this.println("</PRE>");
      this.println("</BODY>");
      this.println("</HTML>");
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
      return null;
   }

   public String getASTCreateString(GrammarAtom var1, String var2) {
      return null;
   }

   public String mapTreeId(String var1, ActionTransInfo var2) {
      return var1;
   }

   protected String processActionForSpecialSymbols(String var1, int var2, RuleBlock var3, ActionTransInfo var4) {
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
