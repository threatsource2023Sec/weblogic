package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.ParamSupport;

public class ParamTag extends ParamSupport {
   public void setValue(Object value) throws JspTagException {
      this.value = value;
      this.valueSpecified = true;
   }
}
