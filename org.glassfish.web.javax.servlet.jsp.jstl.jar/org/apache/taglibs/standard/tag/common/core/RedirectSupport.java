package org.apache.taglibs.standard.tag.common.core;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public abstract class RedirectSupport extends BodyTagSupport implements ParamParent {
   protected String url;
   protected String context;
   private String var;
   private int scope;
   private ParamSupport.ParamManager params;

   public RedirectSupport() {
      this.init();
   }

   private void init() {
      this.url = this.var = null;
      this.params = null;
      this.scope = 1;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public void addParameter(String name, String value) {
      this.params.addParameter(name, value);
   }

   public int doStartTag() throws JspException {
      this.params = new ParamSupport.ParamManager();
      return 2;
   }

   public int doEndTag() throws JspException {
      String baseUrl = UrlSupport.resolveUrl(this.url, this.context, this.pageContext);
      String result = this.params.aggregateParams(baseUrl);
      HttpServletResponse response = (HttpServletResponse)this.pageContext.getResponse();
      if (!ImportSupport.isAbsoluteUrl(result)) {
         result = response.encodeRedirectURL(result);
      }

      try {
         response.sendRedirect(result);
         return 5;
      } catch (IOException var5) {
         throw new JspTagException(var5.toString(), var5);
      }
   }

   public void release() {
      this.init();
   }
}
