package org.apache.taglibs.standard.extra.spath;

import java.util.List;

public class AbsolutePath extends Path {
   private boolean all;
   private RelativePath base;

   public AbsolutePath(RelativePath base) {
      if (base == null) {
         throw new IllegalArgumentException("non-null base required");
      } else {
         this.base = base;
      }
   }

   public List getSteps() {
      return this.base.getSteps();
   }
}
