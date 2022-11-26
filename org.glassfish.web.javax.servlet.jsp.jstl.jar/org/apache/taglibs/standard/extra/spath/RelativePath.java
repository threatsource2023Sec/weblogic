package org.apache.taglibs.standard.extra.spath;

import java.util.List;
import java.util.Vector;

public class RelativePath extends Path {
   private RelativePath next;
   private Step step;

   public RelativePath(Step step, RelativePath next) {
      if (step == null) {
         throw new IllegalArgumentException("non-null step required");
      } else {
         this.step = step;
         this.next = next;
      }
   }

   public List getSteps() {
      Object l;
      if (this.next != null) {
         l = this.next.getSteps();
      } else {
         l = new Vector();
      }

      ((List)l).add(0, this.step);
      return (List)l;
   }
}
