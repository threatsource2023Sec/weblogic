package org.python.core;

public class PySyntaxError extends PyException {
   int lineno;
   int column;
   String text;
   String filename;

   public PySyntaxError(String s, int line, int column, String text, String filename) {
      super(Py.SyntaxError);
      if (text == null) {
         text = "";
      }

      PyObject[] tmp = new PyObject[]{Py.fileSystemEncode(filename), new PyInteger(line), new PyInteger(column), new PyString(text)};
      this.value = new PyTuple(new PyObject[]{new PyString(s), new PyTuple(tmp)});
      this.lineno = line;
      this.column = column;
      this.text = text;
      this.filename = filename;
   }
}
