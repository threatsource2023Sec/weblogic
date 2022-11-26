package org.antlr.gunit;

public class gUnitTestInput {
   public String input;
   public boolean isFile;
   public int line;

   public gUnitTestInput(String input, boolean isFile, int line) {
      this.input = input;
      this.isFile = isFile;
      this.line = line;
   }

   public String getInputEscaped() {
      return JUnitCodeGen.escapeForJava(this.input);
   }
}
