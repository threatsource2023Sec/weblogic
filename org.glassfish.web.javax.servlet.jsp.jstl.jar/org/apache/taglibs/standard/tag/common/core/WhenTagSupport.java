package org.apache.taglibs.standard.tag.common.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.resources.Resources;

public abstract class WhenTagSupport extends ConditionalTagSupport {
   public int doStartTag() throws JspException {
      Tag parent;
      if (!((parent = this.getParent()) instanceof ChooseTag)) {
         throw new JspTagException(Resources.getMessage("WHEN_OUTSIDE_CHOOSE"));
      } else if (!((ChooseTag)parent).gainPermission()) {
         return 0;
      } else if (this.condition()) {
         ((ChooseTag)parent).subtagSucceeded();
         return 1;
      } else {
         return 0;
      }
   }
}
