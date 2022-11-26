package org.apache.taglibs.standard.tag.rt.core;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.core.ParamSupport;

public class ParamTag extends ParamSupport {
   public void setName(String name) throws JspTagException {
      this.name = name;
   }

   public void setValue(String value) throws JspTagException {
      this.value = value;
   }
}
