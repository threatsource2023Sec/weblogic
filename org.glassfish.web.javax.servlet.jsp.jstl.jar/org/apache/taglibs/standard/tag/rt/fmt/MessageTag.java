package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import org.apache.taglibs.standard.tag.common.fmt.MessageSupport;

public class MessageTag extends MessageSupport {
   public void setKey(String key) throws JspTagException {
      this.keyAttrValue = key;
      this.keySpecified = true;
   }

   public void setBundle(LocalizationContext locCtxt) throws JspTagException {
      this.bundleAttrValue = locCtxt;
      this.bundleSpecified = true;
   }
}
