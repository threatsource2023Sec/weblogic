package org.apache.taglibs.standard.tag.common.xml;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.tag.common.core.OutSupport;

public abstract class ExprSupport extends TagSupport {
   private String select;
   protected boolean escapeXml;

   public ExprSupport() {
      this.init();
   }

   private void init() {
      this.select = null;
      this.escapeXml = true;
   }

   public int doStartTag() throws JspException {
      try {
         XPathUtil xu = new XPathUtil(this.pageContext);
         String result = xu.valueOf(XPathUtil.getContext(this), this.select);
         OutSupport.out(this.pageContext, this.escapeXml, result);
         return 0;
      } catch (IOException var3) {
         throw new JspTagException(var3.toString(), var3);
      }
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setSelect(String select) {
      this.select = select;
   }
}
