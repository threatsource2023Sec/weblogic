package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.core.compiler.IProblem;
import weblogic.utils.compiler.JavaCompilerDiagnostic;

public class JDTDiagnosticAdapter implements JavaCompilerDiagnostic {
   IProblem problem;

   JDTDiagnosticAdapter(IProblem problem) {
      this.problem = problem;
   }

   public String getMessage() {
      return this.problem.getMessage();
   }

   public String getOriginatingFileName() {
      return new String(this.problem.getOriginatingFileName());
   }

   public int getSourceEnd() {
      return this.problem.getSourceEnd();
   }

   public int getSourceStart() {
      return this.problem.getSourceStart();
   }

   public boolean isError() {
      return this.problem.isError();
   }
}
