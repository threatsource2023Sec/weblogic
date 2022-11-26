package org.python.core;

public class PyIndentationError extends PyException {
   int lineno;
   int column;
   String text;
   String filename;

   public PyIndentationError(String s, int line, int column, String text, String filename) {
      super(Py.IndentationError);
      PyObject[] tmp = new PyObject[]{new PyString(filename), new PyInteger(line), new PyInteger(column), new PyString(text)};
      this.value = new PyTuple(new PyObject[]{new PyString(s), new PyTuple(tmp)});
      this.lineno = line;
      this.column = column;
      this.text = text;
      this.filename = filename;
   }
}
