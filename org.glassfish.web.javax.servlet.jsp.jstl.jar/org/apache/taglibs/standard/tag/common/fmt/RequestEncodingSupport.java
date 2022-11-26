package org.apache.taglibs.standard.tag.common.fmt;

import java.io.UnsupportedEncodingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class RequestEncodingSupport extends TagSupport {
   static final String REQUEST_CHAR_SET = "javax.servlet.jsp.jstl.fmt.request.charset";
   private static final String DEFAULT_ENCODING = "ISO-8859-1";
   protected String value;
   protected String charEncoding;

   public RequestEncodingSupport() {
      this.init();
   }

   private void init() {
      this.value = null;
   }

   public int doEndTag() throws JspException {
      this.charEncoding = this.value;
      if (this.charEncoding == null && this.pageContext.getRequest().getCharacterEncoding() == null) {
         this.charEncoding = (String)this.pageContext.getAttribute("javax.servlet.jsp.jstl.fmt.request.charset", 3);
         if (this.charEncoding == null) {
            this.charEncoding = "ISO-8859-1";
         }
      }

      if (this.charEncoding != null) {
         try {
            this.pageContext.getRequest().setCharacterEncoding(this.charEncoding);
         } catch (UnsupportedEncodingException var2) {
            throw new JspTagException(var2.toString(), var2);
         }
      }

      return 6;
   }

   public void release() {
      this.init();
   }
}
