package org.python.modules.sre;

public class SRE_REPEAT {
   int count;
   int pidx;
   int last_ptr = -1;
   SRE_REPEAT prev;

   SRE_REPEAT(SRE_REPEAT prev) {
      this.prev = prev;
   }
}
