package org.python.antlr;

import org.python.antlr.base.mod;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.CommonTokenStream;
import org.python.antlr.runtime.RecognitionException;

public class BaseParser {
   protected final CharStream charStream;
   /** @deprecated */
   @Deprecated
   protected final boolean partial;
   protected final String filename;
   protected final String encoding;
   protected ErrorHandler errorHandler;

   public BaseParser(CharStream stream, String filename, String encoding) {
      this(stream, filename, encoding, false);
   }

   /** @deprecated */
   @Deprecated
   public BaseParser(CharStream stream, String filename, String encoding, boolean partial) {
      this.errorHandler = new FailFastHandler();
      this.charStream = stream;
      this.filename = filename;
      this.encoding = encoding;
      this.partial = partial;
   }

   public void setAntlrErrorHandler(ErrorHandler eh) {
      this.errorHandler = eh;
   }

   protected PythonParser setupParser(boolean single) {
      PythonLexer lexer = new PythonLexer(this.charStream);
      lexer.setErrorHandler(this.errorHandler);
      lexer.single = single;
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      PythonTokenSource indentedSource = new PythonTokenSource(tokens, this.filename, single);
      tokens = new CommonTokenStream(indentedSource);
      PythonParser parser = new PythonParser(tokens, this.encoding);
      parser.setErrorHandler(this.errorHandler);
      parser.setTreeAdaptor(new PythonTreeAdaptor());
      return parser;
   }

   public mod parseExpression() {
      mod tree = null;
      PythonParser parser = this.setupParser(false);

      try {
         PythonParser.eval_input_return r = parser.eval_input();
         tree = (mod)r.tree;
      } catch (RecognitionException var4) {
      }

      return tree;
   }

   public mod parseInteractive() {
      mod tree = null;
      PythonParser parser = this.setupParser(true);

      try {
         PythonParser.single_input_return r = parser.single_input();
         tree = (mod)r.tree;
      } catch (RecognitionException var4) {
         System.err.println("FIXME: pretty sure this can't happen -- but needs to be checked");
      }

      return tree;
   }

   public mod parseModule() {
      mod tree = null;
      PythonParser parser = this.setupParser(false);

      try {
         PythonParser.file_input_return r = parser.file_input();
         tree = (mod)r.tree;
      } catch (RecognitionException var4) {
      }

      return tree;
   }
}
