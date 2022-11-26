package org.python.modules.jffi;

import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "jffi.ByReference",
   base = PyObject.class
)
public final class ByReference extends PyObject implements Pointer {
   public static final PyType TYPE;
   private final DirectMemory memory;

   ByReference(CType componentType, DirectMemory memory) {
      super(TYPE);
      this.memory = memory;
   }

   public final DirectMemory getMemory() {
      return this.memory;
   }

   public boolean __nonzero__() {
      return !this.getMemory().isNull();
   }

   static {
      PyType.addBuilder(ByReference.class, new PyExposer());
      TYPE = PyType.fromClass(ByReference.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("jffi.ByReference", ByReference.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
