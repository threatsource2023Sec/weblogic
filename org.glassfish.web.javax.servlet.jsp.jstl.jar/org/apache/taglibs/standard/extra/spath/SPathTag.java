package org.apache.taglibs.standard.extra.spath;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class SPathTag extends TagSupport {
   private String select;
   private String var;

   public SPathTag() {
      this.init();
   }

   private void init() {
      this.select = this.var = null;
   }

   public int doStartTag() throws JspException {
      try {
         SPathFilter s = new SPathFilter((new SPathParser(this.select)).expression());
         this.pageContext.setAttribute(this.var, s);
         return 0;
      } catch (ParseException var2) {
         throw new JspTagException(var2.toString(), var2);
      }
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setSelect(String select) {
      this.select = select;
   }

   public void setVar(String var) {
      this.var = var;
   }
}
