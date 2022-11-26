package org.apache.taglibs.standard.tag.common.xml;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.resources.Resources;

public abstract class ParamSupport extends BodyTagSupport {
   protected String name;
   protected Object value;

   public ParamSupport() {
      this.init();
   }

   private void init() {
      this.name = null;
      this.value = null;
   }

   public int doEndTag() throws JspException {
      Tag t = findAncestorWithClass(this, TransformSupport.class);
      if (t == null) {
         throw new JspTagException(Resources.getMessage("PARAM_OUTSIDE_TRANSFORM"));
      } else {
         TransformSupport parent = (TransformSupport)t;
         Object value = this.value;
         if (value == null) {
            if (this.bodyContent != null && this.bodyContent.getString() != null) {
               value = this.bodyContent.getString().trim();
            } else {
               value = "";
            }
         }

         parent.addParameter(this.name, value);
         return 6;
      }
   }

   public void release() {
      this.init();
   }
}
