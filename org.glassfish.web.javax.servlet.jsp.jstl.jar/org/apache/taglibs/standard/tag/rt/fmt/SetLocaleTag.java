package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;

public class SetLocaleTag extends SetLocaleSupport {
   public void setValue(Object value) throws JspTagException {
      this.value = value;
   }

   public void setVariant(String variant) throws JspTagException {
      this.variant = variant;
   }
}
