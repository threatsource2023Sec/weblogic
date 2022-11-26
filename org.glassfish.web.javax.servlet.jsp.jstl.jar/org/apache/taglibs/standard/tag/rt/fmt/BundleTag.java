package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;

public class BundleTag extends BundleSupport {
   public void setBasename(String basename) throws JspTagException {
      this.basename = basename;
   }

   public void setPrefix(String prefix) throws JspTagException {
      this.prefix = prefix;
   }
}
