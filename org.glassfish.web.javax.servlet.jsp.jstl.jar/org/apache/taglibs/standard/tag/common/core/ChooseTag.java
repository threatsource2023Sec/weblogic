package org.apache.taglibs.standard.tag.common.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.resources.Resources;

public class ChooseTag extends TagSupport {
   private boolean subtagGateClosed;

   public ChooseTag() {
      this.init();
   }

   public void release() {
      super.release();
      this.init();
   }

   public synchronized boolean gainPermission() {
      return !this.subtagGateClosed;
   }

   public synchronized void subtagSucceeded() {
      if (this.subtagGateClosed) {
         throw new IllegalStateException(Resources.getMessage("CHOOSE_EXCLUSIVITY"));
      } else {
         this.subtagGateClosed = true;
      }
   }

   public int doStartTag() throws JspException {
      this.subtagGateClosed = false;
      return 1;
   }

   private void init() {
      this.subtagGateClosed = false;
   }
}
