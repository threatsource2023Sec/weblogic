package org.python.core;

import org.python.core.util.RelativeFile;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "traceback",
   isBaseType = false
)
public class PyTraceback extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   public PyObject tb_next;
   public PyFrame tb_frame;
   public int tb_lineno;

   public PyTraceback(PyTraceback next, PyFrame frame) {
      super(TYPE);
      this.tb_next = next;
      this.tb_frame = frame;
      this.tb_lineno = frame.f_code.getline(frame);
   }

   private String tracebackInfo() {
      if (this.tb_frame != null && this.tb_frame.f_code != null) {
         String line = null;
         if (this.tb_frame.f_code.co_filename != null) {
            line = this.getLine(this.tb_frame.f_code.co_filename, this.tb_lineno);
         }

         return String.format("  File \"%.500s\", line %d, in %.500s\n%s", this.tb_frame.f_code.co_filename, this.tb_lineno, this.tb_frame.f_code.co_name, line == null ? "" : "    " + line);
      } else {
         return String.format("  (no code object) at line %s\n", this.tb_lineno);
      }
   }

   private String getLine(String filename, int lineno) {
      RelativeFile file = new RelativeFile(filename);

      try {
         if (!file.isFile() || !file.canRead()) {
            return null;
         }
      } catch (SecurityException var11) {
         return null;
      }

      PyFile pyFile;
      try {
         pyFile = new PyFile(this.tb_frame.f_code.co_filename, "U", -1);
      } catch (PyException var9) {
         return null;
      }

      String line = null;
      int i = 0;

      try {
         for(i = 0; i < this.tb_lineno; ++i) {
            line = pyFile.readline().asString();
            if (line.equals("")) {
               break;
            }
         }
      } catch (PyException var10) {
      }

      try {
         pyFile.close();
      } catch (PyException var8) {
      }

      if (line != null && i == this.tb_lineno) {
         for(i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (c != ' ' && c != '\t' && c != '\f') {
               break;
            }
         }

         line = line.substring(i);
         if (!line.endsWith("\n")) {
            line = line + "\n";
         }
      } else {
         line = null;
      }

      return line;
   }

   public void dumpStack(StringBuilder buf) {
      buf.append(this.tracebackInfo());
      if (this.tb_next != null && this.tb_next != this) {
         ((PyTraceback)this.tb_next).dumpStack(buf);
      } else if (this.tb_next == this) {
         buf.append("circularity detected!" + this + this.tb_next);
      }

   }

   public String dumpStack() {
      StringBuilder buf = new StringBuilder();
      buf.append("Traceback (most recent call last):\n");
      this.dumpStack(buf);
      return buf.toString();
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.tb_next != null) {
         int retVal = visit.visit(this.tb_next, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.tb_frame == null ? 0 : visit.visit(this.tb_frame, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.tb_next || ob == this.tb_frame);
   }

   static {
      PyType.addBuilder(PyTraceback.class, new PyExposer());
      TYPE = PyType.fromClass(PyTraceback.class);
   }

   private static class tb_frame_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tb_frame_descriptor() {
         super("tb_frame", PyFrame.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTraceback)var1).tb_frame;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class tb_next_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tb_next_descriptor() {
         super("tb_next", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyTraceback)var1).tb_next;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class tb_lineno_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tb_lineno_descriptor() {
         super("tb_lineno", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PyTraceback)var1).tb_lineno);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[]{new tb_frame_descriptor(), new tb_next_descriptor(), new tb_lineno_descriptor()};
         super("traceback", PyTraceback.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
