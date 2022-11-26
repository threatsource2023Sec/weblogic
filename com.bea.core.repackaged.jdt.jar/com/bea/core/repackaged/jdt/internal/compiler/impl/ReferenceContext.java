package com.bea.core.repackaged.jdt.internal.compiler.impl;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;

public interface ReferenceContext {
   void abort(int var1, CategorizedProblem var2);

   CompilationResult compilationResult();

   CompilationUnitDeclaration getCompilationUnitDeclaration();

   boolean hasErrors();

   void tagAsHavingErrors();

   void tagAsHavingIgnoredMandatoryErrors(int var1);
}
