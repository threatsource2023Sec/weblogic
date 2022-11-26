package org.python.compiler;

import java.io.IOException;
import org.python.core.CompilerFlags;
import org.python.core.PyCode;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyBytecodeConstant extends Constant implements ClassConstants, Opcodes {
   PyBytecodeConstant(String name, String className, CompilerFlags cflags, Module module) throws Exception {
      this.module = module;
      this.name = name;
   }

   void get(Code c) throws IOException {
      c.getstatic(this.module.classfile.name, this.name, CodegenUtils.ci(PyCode.class));
   }

   void put(Code c) throws IOException {
   }
}
