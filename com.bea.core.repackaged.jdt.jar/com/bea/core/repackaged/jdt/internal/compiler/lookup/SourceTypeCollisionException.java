package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;

public class SourceTypeCollisionException extends RuntimeException {
   private static final long serialVersionUID = 4798247636899127380L;
   public boolean isLastRound = false;
   public ICompilationUnit[] newAnnotationProcessorUnits;
}
