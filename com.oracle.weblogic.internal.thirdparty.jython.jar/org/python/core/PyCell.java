package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "cell",
   isBaseType = false
)
public class PyCell extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   public PyObject ob_ref;

   public PyCell() {
      super(TYPE);
   }

   public PyObject getCellContents() {
      if (this.ob_ref == null) {
         throw Py.ValueError("Cell is empty");
      } else {
         return this.ob_ref;
      }
   }

   public String toString() {
      return this.ob_ref == null ? String.format("<cell at %s: empty>", Py.idstr(this)) : String.format("<cell at %s: %.80s object at %s>", Py.idstr(this), this.ob_ref.getType().getName(), Py.idstr(this.ob_ref));
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.ob_ref != null ? visit.visit(this.ob_ref, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.ob_ref == ob;
   }

   static {
      PyType.addBuilder(PyCell.class, new PyExposer());
      TYPE = PyType.fromClass(PyCell.class);
   }

   private static class cell_contents_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public cell_contents_descriptor() {
         super("cell_contents", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyCell)var1).getCellContents();
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
         PyDataDescr[] var2 = new PyDataDescr[]{new cell_contents_descriptor()};
         super("cell", PyCell.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
