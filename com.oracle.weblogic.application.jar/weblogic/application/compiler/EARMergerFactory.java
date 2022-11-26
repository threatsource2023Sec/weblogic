package weblogic.application.compiler;

import java.util.Iterator;
import java.util.List;
import weblogic.application.utils.EarUtils;

public class EARMergerFactory implements Factory {
   public Boolean claim(CompilerCtx m) {
      return EarUtils.isEar(m.getSourceFile()) ? true : null;
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      Iterator var3 = allClaimants.iterator();

      Factory claimant;
      do {
         if (!var3.hasNext()) {
            return this.claim(m);
         }

         claimant = (Factory)var3.next();
      } while(!(claimant instanceof GenVersionFactory));

      return null;
   }

   public Merger create(CompilerCtx m) {
      if (m.isReadOnlyInvocation()) {
         if (m.isMergeDisabled()) {
            return (Merger)(m.isBasicView() ? new EarWithoutCMReader(m) : new EarReader(m));
         } else {
            return new ReadOnlyEarMerger(m);
         }
      } else {
         return new EARMerger(m);
      }
   }
}
