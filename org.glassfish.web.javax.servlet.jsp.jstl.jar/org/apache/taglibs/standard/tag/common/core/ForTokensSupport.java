package org.apache.taglibs.standard.tag.common.core;

import java.util.StringTokenizer;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTagSupport;
import org.apache.taglibs.standard.resources.Resources;

public abstract class ForTokensSupport extends LoopTagSupport {
   protected Object items;
   protected String delims;
   protected StringTokenizer st;

   protected void prepare() throws JspTagException {
      if (this.items instanceof ValueExpression) {
         this.deferredExpression = (ValueExpression)this.items;
         this.items = this.deferredExpression.getValue(this.pageContext.getELContext());
      }

      if (!(this.items instanceof String)) {
         throw new JspTagException(Resources.getMessage("FORTOKENS_BAD_ITEMS"));
      } else {
         this.st = new StringTokenizer((String)this.items, this.delims);
      }
   }

   protected boolean hasNext() throws JspTagException {
      return this.st.hasMoreElements();
   }

   protected Object next() throws JspTagException {
      return this.st.nextElement();
   }

   protected String getDelims() {
      return this.delims;
   }

   public void release() {
      super.release();
      this.items = this.delims = null;
      this.st = null;
      this.deferredExpression = null;
   }
}
