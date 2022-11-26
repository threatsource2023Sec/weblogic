package org.python.antlr;

import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.Token;
import org.python.core.Py;
import org.python.core.PyObject;

public class ParseException extends RuntimeException {
   public transient IntStream input;
   public int index;
   public Token token;
   public Object node;
   public int c;
   public int line;
   public int charPositionInLine;
   public boolean approximateLineInfo;
   private PyObject type;

   public ParseException() {
      this.type = Py.SyntaxError;
   }

   public ParseException(String message, int lin, int charPos) {
      super(message);
      this.type = Py.SyntaxError;
      this.line = lin;
      this.charPositionInLine = charPos;
   }

   public ParseException(String message) {
      this(message, 0, 0);
   }

   public ParseException(String message, PythonTree n) {
      this(message, n.getLine(), n.getCharPositionInLine());
      this.node = n;
      this.token = n.getToken();
   }

   public ParseException(String message, RecognitionException r) {
      super(message);
      this.type = Py.SyntaxError;
      this.input = r.input;
      this.index = r.index;
      this.token = r.token;
      this.node = r.node;
      this.c = r.c;
      this.line = r.line;
      this.charPositionInLine = r.charPositionInLine;
      this.approximateLineInfo = r.approximateLineInfo;
   }

   public void setType(PyObject t) {
      this.type = t;
   }

   public PyObject getType() {
      return this.type;
   }
}
