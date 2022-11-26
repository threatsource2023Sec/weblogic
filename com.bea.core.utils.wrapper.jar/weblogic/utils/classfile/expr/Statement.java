package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;

public interface Statement {
   void code(CodeAttribute var1, Bytecodes var2);

   int getMaxStack();
}
