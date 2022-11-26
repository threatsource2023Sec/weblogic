package weblogic.application.compiler;

import java.util.List;

class SingleModuleMergerFactory implements Factory {
   public Boolean claim(CompilerCtx m) {
      return true;
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      return null;
   }

   public Merger create(CompilerCtx m) {
      return (Merger)(m.isReadOnlyInvocation() ? new ReadOnlySingleModuleMerger(m) : new SingleModuleMerger(m));
   }
}
