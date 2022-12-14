package com.oracle.wls.shaded.org.apache.bcel.verifier.structurals;

import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.ObjectType;

public class ExceptionHandler {
   private ObjectType catchtype;
   private InstructionHandle handlerpc;

   ExceptionHandler(ObjectType catch_type, InstructionHandle handler_pc) {
      this.catchtype = catch_type;
      this.handlerpc = handler_pc;
   }

   public ObjectType getExceptionType() {
      return this.catchtype;
   }

   public InstructionHandle getHandlerStart() {
      return this.handlerpc;
   }
}
