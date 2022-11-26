package com.oracle.wls.shaded.org.apache.bcel.verifier.structurals;

import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import java.util.ArrayList;

public interface InstructionContext {
   int getTag();

   void setTag(int var1);

   boolean execute(Frame var1, ArrayList var2, InstConstraintVisitor var3, ExecutionVisitor var4);

   Frame getOutFrame(ArrayList var1);

   InstructionHandle getInstruction();

   InstructionContext[] getSuccessors();

   ExceptionHandler[] getExceptionHandlers();
}
