package org.python.compiler;

import java.io.IOException;
import org.python.objectweb.asm.Opcodes;

abstract class Constant implements Opcodes {
   Module module;
   static int access = 24;
   String name;

   abstract void get(Code var1) throws IOException;

   abstract void put(Code var1) throws IOException;
}
