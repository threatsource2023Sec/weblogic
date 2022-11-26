package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ExceptionLabel;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public abstract class SubRoutineStatement extends Statement {
   ExceptionLabel anyExceptionLabel;

   public static void reenterAllExceptionHandlers(SubRoutineStatement[] subroutines, int max, CodeStream codeStream) {
      if (subroutines != null) {
         if (max < 0) {
            max = subroutines.length;
         }

         for(int i = 0; i < max; ++i) {
            SubRoutineStatement sub = subroutines[i];
            sub.enterAnyExceptionHandler(codeStream);
            sub.enterDeclaredExceptionHandlers(codeStream);
         }

      }
   }

   public ExceptionLabel enterAnyExceptionHandler(CodeStream codeStream) {
      if (this.anyExceptionLabel == null) {
         this.anyExceptionLabel = new ExceptionLabel(codeStream, (TypeBinding)null);
      }

      this.anyExceptionLabel.placeStart();
      return this.anyExceptionLabel;
   }

   public void enterDeclaredExceptionHandlers(CodeStream codeStream) {
   }

   public void exitAnyExceptionHandler() {
      if (this.anyExceptionLabel != null) {
         this.anyExceptionLabel.placeEnd();
      }

   }

   public void exitDeclaredExceptionHandlers(CodeStream codeStream) {
   }

   public abstract boolean generateSubRoutineInvocation(BlockScope var1, CodeStream var2, Object var3, int var4, LocalVariableBinding var5);

   public abstract boolean isSubRoutineEscaping();

   public void placeAllAnyExceptionHandler() {
      this.anyExceptionLabel.place();
   }
}
