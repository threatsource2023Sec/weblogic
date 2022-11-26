package org.python.antlr;

import org.python.antlr.base.expr;
import org.python.antlr.base.mod;
import org.python.antlr.base.slice;
import org.python.antlr.base.stmt;
import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.BitSet;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.Lexer;
import org.python.antlr.runtime.MismatchedTokenException;
import org.python.antlr.runtime.RecognitionException;

public class FailFastHandler implements ErrorHandler {
   public void reportError(BaseRecognizer br, RecognitionException re) {
      throw new ParseException(this.message(br, re), re);
   }

   public void recover(Lexer lex, RecognitionException re) {
      throw new ParseException(this.message(lex, re), re);
   }

   public void recover(BaseRecognizer br, IntStream input, RecognitionException re) {
      throw new ParseException(this.message(br, re), re);
   }

   public boolean mismatch(BaseRecognizer br, IntStream input, int ttype, BitSet follow) throws RecognitionException {
      throw new MismatchedTokenException(ttype, input);
   }

   public Object recoverFromMismatchedToken(BaseRecognizer br, IntStream input, int ttype, BitSet follow) throws RecognitionException {
      throw new MismatchedTokenException(ttype, input);
   }

   public expr errorExpr(PythonTree t) {
      throw new ParseException("Bad Expr Node", t);
   }

   public mod errorMod(PythonTree t) {
      throw new ParseException("Bad Mod Node", t);
   }

   public slice errorSlice(PythonTree t) {
      throw new ParseException("Bad Slice Node", t);
   }

   public stmt errorStmt(PythonTree t) {
      throw new ParseException("Bad Stmt Node", t);
   }

   public void error(String message, PythonTree t) {
      throw new ParseException(message, t);
   }

   private String message(BaseRecognizer br, RecognitionException re) {
      return br.getErrorMessage(re, br.getTokenNames());
   }
}
