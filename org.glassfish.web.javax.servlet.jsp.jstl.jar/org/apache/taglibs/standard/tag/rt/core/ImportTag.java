package org.apache.taglibs.standard.tag.rt.core;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.core.ImportSupport;

public class ImportTag extends ImportSupport {
   public void setUrl(String url) throws JspTagException {
      this.url = url;
   }

   public void setContext(String context) throws JspTagException {
      this.context = context;
   }

   public void setCharEncoding(String charEncoding) throws JspTagException {
      this.charEncoding = charEncoding;
   }
}
