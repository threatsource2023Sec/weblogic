package org.python.core;

import java.io.OutputStream;

class FixedFileWrapper extends StdoutWrapper {
   private PyObject file;

   public FixedFileWrapper(PyObject file) {
      this.name = "fixed file";
      this.file = file;
      if (file.getJavaProxy() != null) {
         Object tojava = file.__tojava__(OutputStream.class);
         if (tojava != null && tojava != Py.NoConversion) {
            this.file = new PyFile((OutputStream)tojava);
         }
      }

   }

   protected PyObject myFile() {
      return this.file;
   }
}
