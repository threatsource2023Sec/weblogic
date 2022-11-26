package org.python.antlr;

import java.util.List;
import org.python.antlr.ast.Name;
import org.python.antlr.base.mod;
import org.python.antlr.runtime.ANTLRFileStream;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.Token;

public class AnalyzingParser extends BaseParser {
   public AnalyzingParser(CharStream stream, String filename, String encoding) {
      super(stream, filename, encoding);
      this.errorHandler = new RecordingErrorHandler();
   }

   public List getRecognitionErrors() {
      return ((RecordingErrorHandler)this.errorHandler).errs;
   }

   protected PythonParser setupParser(boolean single) {
      PythonParser parser = super.setupParser(single);
      parser.setTreeAdaptor(new AnalyzerTreeAdaptor());
      return parser;
   }

   public static void main(String[] args) {
      CharStream in = null;

      try {
         in = new ANTLRFileStream(args[0]);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      AnalyzingParser p = new AnalyzingParser(in, args[0], "ascii");
      mod ast = p.parseModule();
      if (ast != null) {
         System.out.println("parse result: \n" + ast.toStringTree());
      } else {
         System.out.println("failure: \n" + p.getRecognitionErrors());
      }

   }

   public static class AnalyzerTreeAdaptor extends PythonTreeAdaptor {
      public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
         if (!(t instanceof Name) || startToken == null || stopToken == null || startToken.getType() != 43 || stopToken.getType() != 44) {
            super.setTokenBoundaries(t, startToken, stopToken);
         }

      }
   }
}
