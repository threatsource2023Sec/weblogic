package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.SetTimeZoneSupport;

public class SetTimeZoneTag extends SetTimeZoneSupport {
   public void setValue(Object value) throws JspTagException {
      this.value = value;
   }
}
