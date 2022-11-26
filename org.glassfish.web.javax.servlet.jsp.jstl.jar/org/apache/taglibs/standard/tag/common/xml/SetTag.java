package org.apache.taglibs.standard.tag.common.xml;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.taglibs.standard.tag.common.core.Util;

public class SetTag extends TagSupport {
   private String select;
   private String var;
   private int scope;

   public SetTag() {
      this.init();
   }

   private void init() {
      this.var = null;
      this.select = null;
      this.scope = 1;
   }

   public int doStartTag() throws JspException {
      XPathUtil xu = new XPathUtil(this.pageContext);
      List result = xu.selectNodes(XPathUtil.getContext(this), this.select);
      Object ret = result;
      if (result.size() == 1) {
         Object o = result.get(0);
         if (o instanceof String || o instanceof Boolean || o instanceof Number) {
            ret = o;
         }
      }

      this.pageContext.setAttribute(this.var, ret, this.scope);
      return 0;
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

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }
}
