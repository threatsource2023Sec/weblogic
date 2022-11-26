package org.python.antlr;

import org.python.antlr.runtime.ANTLRFileStream;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.CommonTokenStream;

public class PythonTreeTester {
   private boolean _parseOnly;
   private Block _block;

   public PythonTreeTester() {
      this.setParseOnly(false);
      this.setBlock(PythonTreeTester.Block.MODULE);
   }

   public PythonTree parse(String[] args) throws Exception {
      PythonTree result = null;
      ErrorHandler eh = new FailFastHandler();
      CharStream input = new ANTLRFileStream(args[0]);
      PythonLexer lexer = new PythonLexer(input);
      lexer.setErrorHandler(eh);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      PythonTokenSource indentedSource = new PythonTokenSource(tokens, args[0]);
      tokens = new CommonTokenStream(indentedSource);
      PythonParser parser = new PythonParser(tokens);
      parser.setErrorHandler(eh);
      parser.setTreeAdaptor(new PythonTreeAdaptor());
      PythonTree r = null;
      switch (this._block) {
         case MODULE:
            r = parser.file_input().tree;
            break;
         case INTERACTIVE:
            r = parser.single_input().tree;
            break;
         case EXPRESSION:
            r = parser.eval_input().tree;
      }

      if (args.length > 1) {
         System.out.println(r.toStringTree());
      }

      if (!this.isParseOnly()) {
      }

      return (PythonTree)result;
   }

   public void setParseOnly(boolean parseOnly) {
      this._parseOnly = parseOnly;
   }

   public boolean isParseOnly() {
      return this._parseOnly;
   }

   public void setBlock(Block block) {
      this._block = block;
   }

   public Block getBlock() {
      return this._block;
   }

   public static enum Block {
      MODULE,
      INTERACTIVE,
      EXPRESSION;
   }
}
