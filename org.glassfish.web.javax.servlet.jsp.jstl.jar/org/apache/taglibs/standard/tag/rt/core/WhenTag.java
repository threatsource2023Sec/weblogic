package org.apache.taglibs.standard.tag.rt.core;

import org.apache.taglibs.standard.tag.common.core.WhenTagSupport;

public class WhenTag extends WhenTagSupport {
   private boolean test;

   public WhenTag() {
      this.init();
   }

   public void release() {
      super.release();
      this.init();
   }

   protected boolean condition() {
      return this.test;
   }

   public void setTest(boolean test) {
      this.test = test;
   }

   private void init() {
      this.test = false;
   }
}
