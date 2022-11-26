package org.python.core;

import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "listiterator",
   base = PyIterator.class,
   isBaseType = false
)
public class PyListIterator extends PyIterator {
   public static final PyType TYPE;
   private PyList list;
   private boolean stopped = false;
   private int index = 0;

   public PyListIterator(PyList list) {
      this.list = list;
   }

   public PyObject __iternext__() {
      synchronized(this.list) {
         if (this.stopped) {
            return null;
         } else if (this.index >= this.list.size()) {
            this.stopped = true;
            return null;
         } else {
            return this.list.pyget(this.index++);
         }
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retValue = super.traverse(visit, arg);
      if (retValue != 0) {
         return retValue;
      } else {
         return this.list == null ? 0 : visit.visit(this.list, arg);
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.list || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(PyListIterator.class, new PyExposer());
      TYPE = PyType.fromClass(PyListIterator.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("listiterator", PyListIterator.class, PyIterator.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
