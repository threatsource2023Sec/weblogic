package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblem;

public class AptProblem extends DefaultProblem {
   private static final String MARKER_ID = "com.bea.core.repackaged.jdt.apt.pluggable.core.compileProblem";
   public final ReferenceContext _referenceContext;

   public AptProblem(ReferenceContext referenceContext, char[] originatingFileName, String message, int id, String[] stringArguments, int severity, int startPosition, int endPosition, int line, int column) {
      super(originatingFileName, message, id, stringArguments, severity, startPosition, endPosition, line, column);
      this._referenceContext = referenceContext;
   }

   public int getCategoryID() {
      return 0;
   }

   public String getMarkerType() {
      return "com.bea.core.repackaged.jdt.apt.pluggable.core.compileProblem";
   }
}
