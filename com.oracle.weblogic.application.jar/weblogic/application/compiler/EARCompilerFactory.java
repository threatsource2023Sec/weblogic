package weblogic.application.compiler;

import java.util.List;
import weblogic.application.utils.EarUtils;

public final class EARCompilerFactory implements Factory {
   public Boolean claim(CompilerCtx m) {
      return EarUtils.isEar(m.getSourceFile()) ? true : null;
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      return this.claim(m);
   }

   public Compiler create(CompilerCtx m) {
      return new EARCompiler(m);
   }
}
