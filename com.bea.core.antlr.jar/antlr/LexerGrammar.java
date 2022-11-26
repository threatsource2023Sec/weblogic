package antlr;

import antlr.collections.impl.BitSet;
import java.io.IOException;

class LexerGrammar extends Grammar {
   protected BitSet charVocabulary;
   protected boolean testLiterals = true;
   protected boolean caseSensitiveLiterals = true;
   protected boolean caseSensitive = true;
   protected boolean filterMode = false;
   protected String filterRule = null;

   LexerGrammar(String var1, Tool var2, String var3) {
      super(var1, var2, var3);
      BitSet var4 = new BitSet();

      for(int var5 = 0; var5 <= 127; ++var5) {
         var4.add(var5);
      }

      this.setCharVocabulary(var4);
      this.defaultErrorHandler = false;
   }

   public void generate() throws IOException {
      this.generator.gen(this);
   }

   public String getSuperClass() {
      return this.debuggingOutput ? "debug.DebuggingCharScanner" : "CharScanner";
   }

   public boolean getTestLiterals() {
      return this.testLiterals;
   }

   public void processArguments(String[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].equals("-trace")) {
            this.traceRules = true;
            this.antlrTool.setArgOK(var2);
         } else if (var1[var2].equals("-traceLexer")) {
            this.traceRules = true;
            this.antlrTool.setArgOK(var2);
         } else if (var1[var2].equals("-debug")) {
            this.debuggingOutput = true;
            this.antlrTool.setArgOK(var2);
         }
      }

   }

   public void setCharVocabulary(BitSet var1) {
      this.charVocabulary = var1;
   }

   public boolean setOption(String var1, Token var2) {
      String var3 = var2.getText();
      if (var1.equals("buildAST")) {
         this.antlrTool.warning("buildAST option is not valid for lexer", this.getFilename(), var2.getLine(), var2.getColumn());
         return true;
      } else if (var1.equals("testLiterals")) {
         if (var3.equals("true")) {
            this.testLiterals = true;
         } else if (var3.equals("false")) {
            this.testLiterals = false;
         } else {
            this.antlrTool.warning("testLiterals option must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else if (var1.equals("interactive")) {
         if (var3.equals("true")) {
            this.interactive = true;
         } else if (var3.equals("false")) {
            this.interactive = false;
         } else {
            this.antlrTool.error("interactive option must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else if (var1.equals("caseSensitive")) {
         if (var3.equals("true")) {
            this.caseSensitive = true;
         } else if (var3.equals("false")) {
            this.caseSensitive = false;
         } else {
            this.antlrTool.warning("caseSensitive option must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else if (var1.equals("caseSensitiveLiterals")) {
         if (var3.equals("true")) {
            this.caseSensitiveLiterals = true;
         } else if (var3.equals("false")) {
            this.caseSensitiveLiterals = false;
         } else {
            this.antlrTool.warning("caseSensitiveLiterals option must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else if (var1.equals("filter")) {
         if (var3.equals("true")) {
            this.filterMode = true;
         } else if (var3.equals("false")) {
            this.filterMode = false;
         } else if (var2.getType() == 24) {
            this.filterMode = true;
            this.filterRule = var3;
         } else {
            this.antlrTool.warning("filter option must be true, false, or a lexer rule name", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else if (var1.equals("longestPossible")) {
         this.antlrTool.warning("longestPossible option has been deprecated; ignoring it...", this.getFilename(), var2.getLine(), var2.getColumn());
         return true;
      } else if (var1.equals("className")) {
         super.setOption(var1, var2);
         return true;
      } else if (super.setOption(var1, var2)) {
         return true;
      } else {
         this.antlrTool.error("Invalid option: " + var1, this.getFilename(), var2.getLine(), var2.getColumn());
         return false;
      }
   }
}
