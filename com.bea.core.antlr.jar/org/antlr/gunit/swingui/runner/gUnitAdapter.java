package org.antlr.gunit.swingui.runner;

import java.io.IOException;
import org.antlr.gunit.GrammarInfo;
import org.antlr.gunit.gUnitExecutor;
import org.antlr.gunit.gUnitLexer;
import org.antlr.gunit.gUnitParser;
import org.antlr.gunit.swingui.model.TestSuite;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

public class gUnitAdapter {
   private ParserLoader loader;
   private TestSuite testSuite;

   public gUnitAdapter(TestSuite suite) throws IOException, ClassNotFoundException {
      int i = true;
      this.loader = new ParserLoader(suite.getGrammarName(), suite.getTestSuiteFile().getParent());
      this.testSuite = suite;
   }

   public void run() {
      if (this.testSuite == null) {
         throw new IllegalArgumentException("Null testsuite.");
      } else {
         try {
            CharStream input = new ANTLRFileStream(this.testSuite.getTestSuiteFile().getCanonicalPath());
            gUnitLexer lexer = new gUnitLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            GrammarInfo grammarInfo = new GrammarInfo();
            gUnitParser parser = new gUnitParser(tokens, grammarInfo);
            parser.gUnitDef();
            gUnitExecutor executer = new NotifiedTestExecuter(grammarInfo, this.loader, this.testSuite.getTestSuiteFile().getParent(), this.testSuite);
            executer.execTest();
         } catch (Exception var7) {
            var7.printStackTrace();
         }

      }
   }
}
