package javax.faces.webapp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class FacetTag extends TagSupport {
   private String name = null;

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void release() {
      super.release();
      this.name = null;
   }

   public int doStartTag() throws JspException {
      return 1;
   }
}
