package antlr;

import java.io.IOException;

class TreeWalkerGrammar extends Grammar {
   protected boolean transform = false;

   TreeWalkerGrammar(String var1, Tool var2, String var3) {
      super(var1, var2, var3);
   }

   public void generate() throws IOException {
      this.generator.gen(this);
   }

   protected String getSuperClass() {
      return "TreeParser";
   }

   public void processArguments(String[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].equals("-trace")) {
            this.traceRules = true;
            this.antlrTool.setArgOK(var2);
         } else if (var1[var2].equals("-traceTreeParser")) {
            this.traceRules = true;
            this.antlrTool.setArgOK(var2);
         }
      }

   }

   public boolean setOption(String var1, Token var2) {
      if (var1.equals("buildAST")) {
         if (var2.getText().equals("true")) {
            this.buildAST = true;
         } else if (var2.getText().equals("false")) {
            this.buildAST = false;
         } else {
            this.antlrTool.error("buildAST option must be true or false", this.getFilename(), var2.getLine(), var2.getColumn());
         }

         return true;
      } else if (var1.equals("ASTLabelType")) {
         super.setOption(var1, var2);
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
