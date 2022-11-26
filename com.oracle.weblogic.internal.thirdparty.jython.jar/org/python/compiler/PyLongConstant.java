package org.python.compiler;

import java.io.IOException;
import org.python.core.Py;
import org.python.core.PyLong;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyLongConstant extends Constant implements ClassConstants, Opcodes {
   final String value;

   PyLongConstant(String value) {
      this.value = value;
   }

   void get(Code c) throws IOException {
      c.ldc(this.value);
      c.invokestatic(CodegenUtils.p(Py.class), "newLong", CodegenUtils.sig(PyLong.class, String.class));
   }

   void put(Code c) throws IOException {
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean equals(Object o) {
      return o instanceof PyLongConstant ? ((PyLongConstant)o).value.equals(this.value) : false;
   }
}
