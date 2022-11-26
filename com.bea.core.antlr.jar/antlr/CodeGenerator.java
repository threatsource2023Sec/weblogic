package antlr;

import antlr.collections.impl.BitSet;
import antlr.collections.impl.Vector;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class CodeGenerator {
   protected Tool antlrTool;
   protected int tabs = 0;
   protected transient PrintWriter currentOutput;
   protected Grammar grammar = null;
   protected Vector bitsetsUsed;
   protected DefineGrammarSymbols behavior;
   protected LLkGrammarAnalyzer analyzer;
   protected CharFormatter charFormatter;
   protected boolean DEBUG_CODE_GENERATOR = false;
   protected static final int DEFAULT_MAKE_SWITCH_THRESHOLD = 2;
   protected static final int DEFAULT_BITSET_TEST_THRESHOLD = 4;
   protected static final int BITSET_OPTIMIZE_INIT_THRESHOLD = 8;
   protected int makeSwitchThreshold = 2;
   protected int bitsetTestThreshold = 4;
   private static boolean OLD_ACTION_TRANSLATOR = true;
   public static String TokenTypesFileSuffix = "TokenTypes";
   public static String TokenTypesFileExt = ".txt";

   protected void _print(String var1) {
      if (var1 != null) {
         this.currentOutput.print(var1);
      }

   }

   protected void _printAction(String var1) {
      if (var1 != null) {
         int var2;
         for(var2 = 0; var2 < var1.length() && Character.isSpaceChar(var1.charAt(var2)); ++var2) {
         }

         int var3;
         for(var3 = var1.length() - 1; var3 > var2 && Character.isSpaceChar(var1.charAt(var3)); --var3) {
         }

         boolean var4 = false;
         int var5 = var2;

         while(true) {
            boolean var6;
            do {
               if (var5 > var3) {
                  this.currentOutput.println();
                  return;
               }

               char var7 = var1.charAt(var5);
               ++var5;
               var6 = false;
               switch (var7) {
                  case '\n':
                     var6 = true;
                     break;
                  case '\r':
                     if (var5 <= var3 && var1.charAt(var5) == '\n') {
                        ++var5;
                     }

                     var6 = true;
                     break;
                  default:
                     this.currentOutput.print(var7);
               }
            } while(!var6);

            this.currentOutput.println();
            this.printTabs();

            while(var5 <= var3 && Character.isSpaceChar(var1.charAt(var5))) {
               ++var5;
            }

            var6 = false;
         }
      }
   }

   protected void _println(String var1) {
      if (var1 != null) {
         this.currentOutput.println(var1);
      }

   }

   public static boolean elementsAreRange(int[] var0) {
      if (var0.length == 0) {
         return false;
      } else {
         int var1 = var0[0];
         int var2 = var0[var0.length - 1];
         if (var0.length <= 2) {
            return false;
         } else if (var2 - var1 + 1 > var0.length) {
            return false;
         } else {
            int var3 = var1 + 1;

            for(int var4 = 1; var4 < var0.length - 1; ++var4) {
               if (var3 != var0[var4]) {
                  return false;
               }

               ++var3;
            }

            return true;
         }
      }
   }

   protected String extractIdOfAction(Token var1) {
      return this.extractIdOfAction(var1.getText(), var1.getLine(), var1.getColumn());
   }

   protected String extractIdOfAction(String var1, int var2, int var3) {
      var1 = this.removeAssignmentFromDeclaration(var1);

      for(int var4 = var1.length() - 2; var4 >= 0; --var4) {
         if (!Character.isLetterOrDigit(var1.charAt(var4)) && var1.charAt(var4) != '_') {
            return var1.substring(var4 + 1);
         }
      }

      this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), var2, var3);
      return "";
   }

   protected String extractTypeOfAction(Token var1) {
      return this.extractTypeOfAction(var1.getText(), var1.getLine(), var1.getColumn());
   }

   protected String extractTypeOfAction(String var1, int var2, int var3) {
      var1 = this.removeAssignmentFromDeclaration(var1);

      for(int var4 = var1.length() - 2; var4 >= 0; --var4) {
         if (!Character.isLetterOrDigit(var1.charAt(var4)) && var1.charAt(var4) != '_') {
            return var1.substring(0, var4 + 1);
         }
      }

      this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), var2, var3);
      return "";
   }

   public abstract void gen();

   public abstract void gen(ActionElement var1);

   public abstract void gen(AlternativeBlock var1);

   public abstract void gen(BlockEndElement var1);

   public abstract void gen(CharLiteralElement var1);

   public abstract void gen(CharRangeElement var1);

   public abstract void gen(LexerGrammar var1) throws IOException;

   public abstract void gen(OneOrMoreBlock var1);

   public abstract void gen(ParserGrammar var1) throws IOException;

   public abstract void gen(RuleRefElement var1);

   public abstract void gen(StringLiteralElement var1);

   public abstract void gen(TokenRangeElement var1);

   public abstract void gen(TokenRefElement var1);

   public abstract void gen(TreeElement var1);

   public abstract void gen(TreeWalkerGrammar var1) throws IOException;

   public abstract void gen(WildcardElement var1);

   public abstract void gen(ZeroOrMoreBlock var1);

   protected void genTokenInterchange(TokenManager var1) throws IOException {
      String var2 = var1.getName() + TokenTypesFileSuffix + TokenTypesFileExt;
      this.currentOutput = this.antlrTool.openOutputFile(var2);
      StringBuffer var10001 = (new StringBuffer()).append("// $ANTLR ");
      Tool var10002 = this.antlrTool;
      this.println(var10001.append(Tool.version).append(": ").append(this.antlrTool.fileMinusPath(this.antlrTool.grammarFile)).append(" -> ").append(var2).append("$").toString());
      this.tabs = 0;
      this.println(var1.getName() + "    // output token vocab name");
      Vector var3 = var1.getVocabulary();

      for(int var4 = 4; var4 < var3.size(); ++var4) {
         String var5 = (String)var3.elementAt(var4);
         if (this.DEBUG_CODE_GENERATOR) {
            System.out.println("gen persistence file entry for: " + var5);
         }

         if (var5 != null && !var5.startsWith("<")) {
            if (var5.startsWith("\"")) {
               StringLiteralSymbol var6 = (StringLiteralSymbol)var1.getTokenSymbol(var5);
               if (var6 != null && var6.label != null) {
                  this.print(var6.label + "=");
               }

               this.println(var5 + "=" + var4);
            } else {
               this.print(var5);
               TokenSymbol var7 = var1.getTokenSymbol(var5);
               if (var7 == null) {
                  this.antlrTool.warning("undefined token symbol: " + var5);
               } else if (var7.getParaphrase() != null) {
                  this.print("(" + var7.getParaphrase() + ")");
               }

               this.println("=" + var4);
            }
         }
      }

      this.currentOutput.close();
      this.currentOutput = null;
   }

   public String processStringForASTConstructor(String var1) {
      return var1;
   }

   public abstract String getASTCreateString(Vector var1);

   public abstract String getASTCreateString(GrammarAtom var1, String var2);

   protected String getBitsetName(int var1) {
      return "_tokenSet_" + var1;
   }

   public static String encodeLexerRuleName(String var0) {
      return "m" + var0;
   }

   public static String decodeLexerRuleName(String var0) {
      return var0 == null ? null : var0.substring(1, var0.length());
   }

   public abstract String mapTreeId(String var1, ActionTransInfo var2);

   protected int markBitsetForGen(BitSet var1) {
      for(int var2 = 0; var2 < this.bitsetsUsed.size(); ++var2) {
         BitSet var3 = (BitSet)this.bitsetsUsed.elementAt(var2);
         if (var1.equals(var3)) {
            return var2;
         }
      }

      this.bitsetsUsed.appendElement(var1.clone());
      return this.bitsetsUsed.size() - 1;
   }

   protected void print(String var1) {
      if (var1 != null) {
         this.printTabs();
         this.currentOutput.print(var1);
      }

   }

   protected void printAction(String var1) {
      if (var1 != null) {
         this.printTabs();
         this._printAction(var1);
      }

   }

   protected void println(String var1) {
      if (var1 != null) {
         this.printTabs();
         this.currentOutput.println(var1);
      }

   }

   protected void printTabs() {
      for(int var1 = 1; var1 <= this.tabs; ++var1) {
         this.currentOutput.print("\t");
      }

   }

   protected abstract String processActionForSpecialSymbols(String var1, int var2, RuleBlock var3, ActionTransInfo var4);

   public String getFOLLOWBitSet(String var1, int var2) {
      GrammarSymbol var3 = this.grammar.getSymbol(var1);
      if (!(var3 instanceof RuleSymbol)) {
         return null;
      } else {
         RuleBlock var4 = ((RuleSymbol)var3).getBlock();
         Lookahead var5 = this.grammar.theLLkAnalyzer.FOLLOW(var2, var4.endNode);
         String var6 = this.getBitsetName(this.markBitsetForGen(var5.fset));
         return var6;
      }
   }

   public String getFIRSTBitSet(String var1, int var2) {
      GrammarSymbol var3 = this.grammar.getSymbol(var1);
      if (!(var3 instanceof RuleSymbol)) {
         return null;
      } else {
         RuleBlock var4 = ((RuleSymbol)var3).getBlock();
         Lookahead var5 = this.grammar.theLLkAnalyzer.look(var2, var4);
         String var6 = this.getBitsetName(this.markBitsetForGen(var5.fset));
         return var6;
      }
   }

   protected String removeAssignmentFromDeclaration(String var1) {
      if (var1.indexOf(61) >= 0) {
         var1 = var1.substring(0, var1.indexOf(61)).trim();
      }

      return var1;
   }

   private void reset() {
      this.tabs = 0;
      this.bitsetsUsed = new Vector();
      this.currentOutput = null;
      this.grammar = null;
      this.DEBUG_CODE_GENERATOR = false;
      this.makeSwitchThreshold = 2;
      this.bitsetTestThreshold = 4;
   }

   public static String reverseLexerRuleName(String var0) {
      return var0.substring(1, var0.length());
   }

   public void setAnalyzer(LLkGrammarAnalyzer var1) {
      this.analyzer = var1;
   }

   public void setBehavior(DefineGrammarSymbols var1) {
      this.behavior = var1;
   }

   protected void setGrammar(Grammar var1) {
      this.reset();
      this.grammar = var1;
      Token var3;
      if (this.grammar.hasOption("codeGenMakeSwitchThreshold")) {
         try {
            this.makeSwitchThreshold = this.grammar.getIntegerOption("codeGenMakeSwitchThreshold");
         } catch (NumberFormatException var5) {
            var3 = this.grammar.getOption("codeGenMakeSwitchThreshold");
            this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", this.grammar.getClassName(), var3.getLine(), var3.getColumn());
         }
      }

      if (this.grammar.hasOption("codeGenBitsetTestThreshold")) {
         try {
            this.bitsetTestThreshold = this.grammar.getIntegerOption("codeGenBitsetTestThreshold");
         } catch (NumberFormatException var4) {
            var3 = this.grammar.getOption("codeGenBitsetTestThreshold");
            this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", this.grammar.getClassName(), var3.getLine(), var3.getColumn());
         }
      }

      if (this.grammar.hasOption("codeGenDebug")) {
         Token var2 = this.grammar.getOption("codeGenDebug");
         if (var2.getText().equals("true")) {
            this.DEBUG_CODE_GENERATOR = true;
         } else if (var2.getText().equals("false")) {
            this.DEBUG_CODE_GENERATOR = false;
         } else {
            this.antlrTool.error("option 'codeGenDebug' must be true or false", this.grammar.getClassName(), var2.getLine(), var2.getColumn());
         }
      }

   }

   public void setTool(Tool var1) {
      this.antlrTool = var1;
   }
}
