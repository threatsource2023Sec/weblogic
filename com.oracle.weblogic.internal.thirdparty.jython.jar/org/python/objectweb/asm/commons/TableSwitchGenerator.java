package org.python.objectweb.asm.commons;

import org.python.objectweb.asm.Label;

public interface TableSwitchGenerator {
   void generateCase(int var1, Label var2);

   void generateDefault();
}
