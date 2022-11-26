package weblogic.application.compiler;

import weblogic.application.compiler.flow.CompilerFlow;
import weblogic.utils.compiler.ToolFailureException;

class BaseCompiler implements Compiler {
   private final CompilerFlow[] flows;

   BaseCompiler(CompilerFlow[] flows) {
      this.flows = flows;
   }

   public void compile() throws ToolFailureException {
      (new FlowDriver()).run(this.flows);
   }
}
