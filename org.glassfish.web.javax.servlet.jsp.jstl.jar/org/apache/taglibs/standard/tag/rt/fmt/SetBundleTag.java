package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.SetBundleSupport;

public class SetBundleTag extends SetBundleSupport {
   public void setBasename(String basename) throws JspTagException {
      this.basename = basename;
   }
}
