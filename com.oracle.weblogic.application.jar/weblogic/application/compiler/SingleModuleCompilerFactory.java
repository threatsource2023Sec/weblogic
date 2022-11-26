package weblogic.application.compiler;

import java.util.List;

public class SingleModuleCompilerFactory implements Factory {
   public Boolean claim(CompilerCtx m) {
      return true;
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      return null;
   }

   public Compiler create(CompilerCtx m) {
      return new SingleModuleCompiler(m);
   }
}
