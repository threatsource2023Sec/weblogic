package weblogic.application.compiler;

import java.util.List;
import weblogic.application.utils.EarUtils;

public class GenVersionFactory implements Factory {
   public Boolean claim(CompilerCtx m) {
      return m.isGenerateVersion() ? true : null;
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      return this.claim(m);
   }

   public Merger create(CompilerCtx m) {
      boolean isEar = EarUtils.isEar(m.getSourceFile());
      return new GenVersionMerger(m, isEar);
   }
}
