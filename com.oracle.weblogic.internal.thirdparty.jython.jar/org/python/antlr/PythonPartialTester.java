package org.python.antlr;

import org.python.antlr.runtime.ANTLRFileStream;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.CommonTokenStream;

public class PythonPartialTester {
   public void parse(String[] args) throws Exception {
      try {
         PythonTree result = null;
         CharStream input = new ANTLRFileStream(args[0]);
         PythonPartialLexer lexer = new PythonPartialLexer(input);
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         PythonTokenSource indentedSource = new PythonTokenSource(tokens, "<test>");
         tokens = new CommonTokenStream(indentedSource);
         PythonPartialParser parser = new PythonPartialParser(tokens);
         parser.single_input();
         System.out.println("SUCCEED");
      } catch (ParseException var8) {
         System.out.println("FAIL:" + var8);
      }

   }

   public static void main(String[] args) throws Exception {
      PythonPartialTester p = new PythonPartialTester();
      p.parse(args);
   }
}
