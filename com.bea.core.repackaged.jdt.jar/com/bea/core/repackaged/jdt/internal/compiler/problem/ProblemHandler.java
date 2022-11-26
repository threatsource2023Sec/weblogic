package com.bea.core.repackaged.jdt.internal.compiler.problem;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.IErrorHandlingPolicy;
import com.bea.core.repackaged.jdt.internal.compiler.IProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class ProblemHandler {
   public static final String[] NoArgument;
   public IErrorHandlingPolicy policy;
   public final IProblemFactory problemFactory;
   public final CompilerOptions options;
   private IErrorHandlingPolicy rootPolicy;
   protected boolean suppressTagging = false;

   static {
      NoArgument = CharOperation.NO_STRINGS;
   }

   public ProblemHandler(IErrorHandlingPolicy policy, CompilerOptions options, IProblemFactory problemFactory) {
      this.policy = policy;
      this.problemFactory = problemFactory;
      this.options = options;
   }

   public int computeSeverity(int problemId) {
      return 1;
   }

   public CategorizedProblem createProblem(char[] fileName, int problemId, String[] problemArguments, String[] messageArguments, int severity, int problemStartPosition, int problemEndPosition, int lineNumber, int columnNumber) {
      return this.problemFactory.createProblem(fileName, problemId, problemArguments, messageArguments, severity, problemStartPosition, problemEndPosition, lineNumber, columnNumber);
   }

   public CategorizedProblem createProblem(char[] fileName, int problemId, String[] problemArguments, int elaborationId, String[] messageArguments, int severity, int problemStartPosition, int problemEndPosition, int lineNumber, int columnNumber) {
      return this.problemFactory.createProblem(fileName, problemId, problemArguments, elaborationId, messageArguments, severity, problemStartPosition, problemEndPosition, lineNumber, columnNumber);
   }

   public void handle(int problemId, String[] problemArguments, int elaborationId, String[] messageArguments, int severity, int problemStartPosition, int problemEndPosition, ReferenceContext referenceContext, CompilationResult unitResult) {
      if (severity != 256) {
         boolean mandatory = (severity & 33) == 1;
         CategorizedProblem problem;
         if ((severity & 512) == 0 && this.policy.ignoreAllErrors()) {
            if (referenceContext == null) {
               if ((severity & 1) != 0) {
                  problem = this.createProblem((char[])null, problemId, problemArguments, elaborationId, messageArguments, severity, 0, 0, 0, 0);
                  throw new AbortCompilation((CompilationResult)null, problem);
               }
            } else {
               if (mandatory) {
                  referenceContext.tagAsHavingIgnoredMandatoryErrors(problemId);
               }

            }
         } else {
            if ((severity & 32) != 0 && problemId != 536871362 && !this.options.ignoreSourceFolderWarningOption) {
               ICompilationUnit cu = unitResult.getCompilationUnit();

               try {
                  if (cu != null && cu.ignoreOptionalProblems()) {
                     return;
                  }
               } catch (AbstractMethodError var16) {
               }
            }

            if (referenceContext == null) {
               if ((severity & 1) != 0) {
                  problem = this.createProblem((char[])null, problemId, problemArguments, elaborationId, messageArguments, severity, 0, 0, 0, 0);
                  throw new AbortCompilation((CompilationResult)null, problem);
               }
            } else {
               int[] lineEnds;
               int lineNumber = problemStartPosition >= 0 ? Util.getLineNumber(problemStartPosition, lineEnds = unitResult.getLineSeparatorPositions(), 0, lineEnds.length - 1) : 0;
               int columnNumber = problemStartPosition >= 0 ? Util.searchColumnNumber(unitResult.getLineSeparatorPositions(), lineNumber, problemStartPosition) : 0;
               CategorizedProblem problem = this.createProblem(unitResult.getFileName(), problemId, problemArguments, elaborationId, messageArguments, severity, problemStartPosition, problemEndPosition, lineNumber, columnNumber);
               if (problem != null) {
                  switch (severity & 1) {
                     case 0:
                        this.record(problem, unitResult, referenceContext, false);
                        break;
                     case 1:
                        this.record(problem, unitResult, referenceContext, mandatory);
                        if ((severity & 128) != 0) {
                           if (!referenceContext.hasErrors() && !mandatory && this.options.suppressOptionalErrors) {
                              CompilationUnitDeclaration unitDecl = referenceContext.getCompilationUnitDeclaration();
                              if (unitDecl != null && unitDecl.isSuppressed(problem)) {
                                 return;
                              }
                           }

                           if (!this.suppressTagging || this.options.treatOptionalErrorAsFatal) {
                              referenceContext.tagAsHavingErrors();
                           }

                           int var10000 = this.policy.stopOnFirstError() ? 2 : severity & 30;
                           int abortLevel = var10000;
                           if (var10000 != 0) {
                              referenceContext.abort(abortLevel, problem);
                           }
                        }
                  }

               }
            }
         }
      }
   }

   public void handle(int problemId, String[] problemArguments, String[] messageArguments, int problemStartPosition, int problemEndPosition, ReferenceContext referenceContext, CompilationResult unitResult) {
      this.handle(problemId, problemArguments, 0, messageArguments, this.computeSeverity(problemId), problemStartPosition, problemEndPosition, referenceContext, unitResult);
   }

   public void record(CategorizedProblem problem, CompilationResult unitResult, ReferenceContext referenceContext, boolean mandatoryError) {
      unitResult.record(problem, referenceContext, mandatoryError);
   }

   public IErrorHandlingPolicy switchErrorHandlingPolicy(IErrorHandlingPolicy newPolicy) {
      if (this.rootPolicy == null) {
         this.rootPolicy = this.policy;
      }

      IErrorHandlingPolicy presentPolicy = this.policy;
      this.policy = newPolicy;
      return presentPolicy;
   }

   public IErrorHandlingPolicy suspendTempErrorHandlingPolicy() {
      IErrorHandlingPolicy presentPolicy = this.policy;
      if (this.rootPolicy != null) {
         this.policy = this.rootPolicy;
      }

      return presentPolicy;
   }

   public void resumeTempErrorHandlingPolicy(IErrorHandlingPolicy previousPolicy) {
      this.policy = previousPolicy;
   }
}
