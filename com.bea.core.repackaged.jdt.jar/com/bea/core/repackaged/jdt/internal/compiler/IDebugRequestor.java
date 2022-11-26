package com.bea.core.repackaged.jdt.internal.compiler;

public interface IDebugRequestor {
   void acceptDebugResult(CompilationResult var1);

   boolean isActive();

   void activate();

   void deactivate();

   void reset();
}
