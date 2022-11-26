package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.RequestEncodingSupport;

public class RequestEncodingTag extends RequestEncodingSupport {
   public void setValue(String value) throws JspTagException {
      this.value = value;
   }
}
