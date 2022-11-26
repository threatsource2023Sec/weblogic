package org.apache.taglibs.standard.tag.common.core;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.taglibs.standard.resources.Resources;

public abstract class UrlSupport extends BodyTagSupport implements ParamParent {
   protected String value;
   protected String context;
   private String var;
   private int scope;
   private ParamSupport.ParamManager params;

   public UrlSupport() {
      this.init();
   }

   private void init() {
      this.value = this.var = null;
      this.params = null;
      this.context = null;
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
      String baseUrl = resolveUrl(this.value, this.context, this.pageContext);
      String result = this.params.aggregateParams(baseUrl);
      if (!ImportSupport.isAbsoluteUrl(result)) {
         HttpServletResponse response = (HttpServletResponse)this.pageContext.getResponse();
         result = response.encodeURL(result);
      }

      if (this.var != null) {
         this.pageContext.setAttribute(this.var, result, this.scope);
      } else {
         try {
            this.pageContext.getOut().print(result);
         } catch (IOException var4) {
            throw new JspTagException(var4.toString(), var4);
         }
      }

      return 6;
   }

   public void release() {
      this.init();
   }

   public static String resolveUrl(String url, String context, PageContext pageContext) throws JspException {
      if (ImportSupport.isAbsoluteUrl(url)) {
         return url;
      } else {
         HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
         if (context == null) {
            return url.startsWith("/") ? request.getContextPath() + url : url;
         } else if (context.startsWith("/") && url.startsWith("/")) {
            return context.equals("/") ? url : context + url;
         } else {
            throw new JspTagException(Resources.getMessage("IMPORT_BAD_RELATIVE"));
         }
      }
   }
}
