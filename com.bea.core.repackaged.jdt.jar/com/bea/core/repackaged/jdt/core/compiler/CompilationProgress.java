package com.bea.core.repackaged.jdt.core.compiler;

public abstract class CompilationProgress {
   public abstract void begin(int var1);

   public abstract void done();

   public abstract boolean isCanceled();

   public abstract void setTaskName(String var1);

   public abstract void worked(int var1, int var2);
}
