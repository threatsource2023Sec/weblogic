package org.apache.taglibs.standard.tag.common.xml;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.core.WhenTagSupport;

public class WhenTag extends WhenTagSupport {
   private String select;

   public WhenTag() {
      this.init();
   }

   public void release() {
      super.release();
      this.init();
   }

   protected boolean condition() throws JspTagException {
      XPathUtil xu = new XPathUtil(this.pageContext);
      return xu.booleanValueOf(XPathUtil.getContext(this), this.select);
   }

   public void setSelect(String select) {
      this.select = select;
   }

   private void init() {
      this.select = null;
   }
}
