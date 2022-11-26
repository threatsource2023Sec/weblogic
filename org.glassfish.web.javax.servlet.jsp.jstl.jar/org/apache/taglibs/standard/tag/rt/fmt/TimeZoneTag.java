package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.TimeZoneSupport;

public class TimeZoneTag extends TimeZoneSupport {
   public void setValue(Object value) throws JspTagException {
      this.value = value;
   }
}
