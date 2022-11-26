package org.python.antlr;

import org.python.antlr.base.expr;
import org.python.antlr.base.mod;
import org.python.antlr.base.slice;
import org.python.antlr.base.stmt;
import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.BitSet;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.Lexer;
import org.python.antlr.runtime.RecognitionException;

interface ErrorHandler {
   void reportError(BaseRecognizer var1, RecognitionException var2);

   void recover(BaseRecognizer var1, IntStream var2, RecognitionException var3);

   void recover(Lexer var1, RecognitionException var2);

   boolean mismatch(BaseRecognizer var1, IntStream var2, int var3, BitSet var4) throws RecognitionException;

   Object recoverFromMismatchedToken(BaseRecognizer var1, IntStream var2, int var3, BitSet var4) throws RecognitionException;

   expr errorExpr(PythonTree var1);

   mod errorMod(PythonTree var1);

   slice errorSlice(PythonTree var1);

   stmt errorStmt(PythonTree var1);

   void error(String var1, PythonTree var2);
}
