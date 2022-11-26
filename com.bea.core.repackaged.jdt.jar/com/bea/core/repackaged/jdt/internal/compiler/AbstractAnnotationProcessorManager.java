package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.io.PrintWriter;

public abstract class AbstractAnnotationProcessorManager {
   public abstract void configure(Object var1, String[] var2);

   public abstract void configureFromPlatform(Compiler var1, Object var2, Object var3, boolean var4);

   public abstract void setOut(PrintWriter var1);

   public abstract void setErr(PrintWriter var1);

   public abstract ICompilationUnit[] getNewUnits();

   public abstract ReferenceBinding[] getNewClassFiles();

   public abstract ICompilationUnit[] getDeletedUnits();

   public abstract void reset();

   public abstract void processAnnotations(CompilationUnitDeclaration[] var1, ReferenceBinding[] var2, boolean var3);

   public abstract void setProcessors(Object[] var1);
}
