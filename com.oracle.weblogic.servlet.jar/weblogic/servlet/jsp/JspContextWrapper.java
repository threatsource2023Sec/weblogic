package weblogic.servlet.jsp;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;

public class JspContextWrapper extends PageContext implements VariableResolver {
   private PageContext _invokingContext;
   private Map _pageAttributes;
   private List _nestedVars;
   private List _atBeginVars;
   private List _atEndVars;
   private Map _aliases;
   private Map _originalNestedVars;
   private ELContext _elContext;

   public JspContextWrapper(JspContext jspContext, List nestedVars, List atBeginVars, List atEndVars, Map aliases) {
      this._invokingContext = (PageContext)jspContext;
      this._nestedVars = nestedVars;
      this._atBeginVars = atBeginVars;
      this._atEndVars = atEndVars;
      this._pageAttributes = new HashMap(16);
      this._aliases = aliases;
      if (nestedVars != null) {
         this._originalNestedVars = new HashMap(nestedVars.size());
      }

      this.syncBeginTagFile();
   }

   public void initialize(Servlet servlet, ServletRequest request, ServletResponse response, String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush) throws IOException, IllegalStateException, IllegalArgumentException {
   }

   public Object getAttribute(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         return this._pageAttributes.get(name);
      }
   }

   public Object getAttribute(String name, int scope) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         return scope == 1 ? this._pageAttributes.get(name) : this._invokingContext.getAttribute(name, scope);
      }
   }

   public void setAttribute(String name, Object value) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         if (value != null) {
            this._pageAttributes.put(name, value);
         } else {
            this.removeAttribute(name, 1);
         }

      }
   }

   public void setAttribute(String name, Object value, int scope) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         if (scope == 1) {
            if (value != null) {
               this._pageAttributes.put(name, value);
            } else {
               this.removeAttribute(name, 1);
            }
         } else {
            this._invokingContext.setAttribute(name, value, scope);
         }

      }
   }

   public Object findAttribute(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         Object o = this._pageAttributes.get(name);
         if (o == null) {
            o = this._invokingContext.getAttribute(name, 2);
            if (o == null) {
               if (this.getSession() != null) {
                  o = this._invokingContext.getAttribute(name, 3);
               }

               if (o == null) {
                  o = this._invokingContext.getAttribute(name, 4);
               }
            }
         }

         return o;
      }
   }

   public void removeAttribute(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         this._pageAttributes.remove(name);
         this._invokingContext.removeAttribute(name, 2);
         if (this.getSession() != null) {
            this._invokingContext.removeAttribute(name, 3);
         }

         this._invokingContext.removeAttribute(name, 4);
      }
   }

   public void removeAttribute(String name, int scope) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         if (scope == 1) {
            this._pageAttributes.remove(name);
         } else {
            this._invokingContext.removeAttribute(name, scope);
         }

      }
   }

   public int getAttributesScope(String name) {
      if (name == null) {
         throw new NullPointerException();
      } else {
         return this._pageAttributes.get(name) != null ? 1 : this._invokingContext.getAttributesScope(name);
      }
   }

   public Enumeration getAttributeNamesInScope(int scope) {
      return scope == 1 ? Collections.enumeration(this._pageAttributes.keySet()) : this._invokingContext.getAttributeNamesInScope(scope);
   }

   public void release() {
      this._invokingContext.release();
   }

   public JspWriter getOut() {
      return this._invokingContext.getOut();
   }

   public HttpSession getSession() {
      return this._invokingContext.getSession();
   }

   public Object getPage() {
      return this._invokingContext.getPage();
   }

   public ServletRequest getRequest() {
      return this._invokingContext.getRequest();
   }

   public ServletResponse getResponse() {
      return this._invokingContext.getResponse();
   }

   public Exception getException() {
      return this._invokingContext.getException();
   }

   public ServletConfig getServletConfig() {
      return this._invokingContext.getServletConfig();
   }

   public ServletContext getServletContext() {
      return this._invokingContext.getServletContext();
   }

   public void forward(String relativeUrlPath) throws ServletException, IOException {
      this._invokingContext.forward(relativeUrlPath);
   }

   public void include(String relativeUrlPath) throws ServletException, IOException {
      this._invokingContext.include(relativeUrlPath);
   }

   public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException {
      this._invokingContext.include(relativeUrlPath, flush);
   }

   public VariableResolver getVariableResolver() {
      return this;
   }

   public ELContext getELContext() {
      if (this._elContext == null) {
         PageContext pc;
         for(pc = this._invokingContext; pc instanceof JspContextWrapper; pc = ((JspContextWrapper)pc)._invokingContext) {
         }

         PageContextImpl pci = (PageContextImpl)pc;
         this._elContext = pci.getJspApplicationContextImpl().createELContext(this);
      }

      return this._elContext;
   }

   public BodyContent pushBody() {
      return this._invokingContext.pushBody();
   }

   public JspWriter pushBody(Writer writer) {
      return this._invokingContext.pushBody(writer);
   }

   public JspWriter popBody() {
      return this._invokingContext.popBody();
   }

   public ExpressionEvaluator getExpressionEvaluator() {
      return this._invokingContext.getExpressionEvaluator();
   }

   public void handlePageException(Exception ex) throws IOException, ServletException {
      this.handlePageException((Throwable)ex);
   }

   public void handlePageException(Throwable t) throws IOException, ServletException {
      this._invokingContext.handlePageException(t);
   }

   public Object resolveVariable(String pName) throws ELException {
      Object result = null;
      ELContext ctx = this.getELContext();

      try {
         result = ctx.getELResolver().getValue(ctx, (Object)null, pName);
         return result;
      } catch (javax.el.ELException var5) {
         throw new ELException();
      }
   }

   public void syncBeginTagFile() {
      this.saveNestedVariables();
   }

   public void syncBeforeInvoke() {
      this.copyTagToPageScope(0);
      this.copyTagToPageScope(1);
   }

   public void syncEndTagFile() {
      this.copyTagToPageScope(1);
      this.copyTagToPageScope(2);
      this.restoreNestedVariables();
   }

   private void copyTagToPageScope(int scope) {
      Iterator iter = null;
      switch (scope) {
         case 0:
            if (this._nestedVars != null) {
               iter = this._nestedVars.iterator();
            }
            break;
         case 1:
            if (this._atBeginVars != null) {
               iter = this._atBeginVars.iterator();
            }
            break;
         case 2:
            if (this._atEndVars != null) {
               iter = this._atEndVars.iterator();
            }
      }

      while(iter != null && iter.hasNext()) {
         String varName = (String)iter.next();
         Object obj = this.getAttribute(varName);
         varName = this.findAlias(varName);
         if (obj != null) {
            this._invokingContext.setAttribute(varName, obj);
         } else {
            this._invokingContext.removeAttribute(varName, 1);
         }
      }

   }

   private void saveNestedVariables() {
      if (this._nestedVars != null) {
         Iterator iter = this._nestedVars.iterator();

         while(iter.hasNext()) {
            String varName = (String)iter.next();
            varName = this.findAlias(varName);
            Object obj = this._invokingContext.getAttribute(varName);
            if (obj != null) {
               this._originalNestedVars.put(varName, obj);
            }
         }
      }

   }

   private void restoreNestedVariables() {
      if (this._nestedVars != null) {
         Iterator iter = this._nestedVars.iterator();

         while(iter.hasNext()) {
            String varName = (String)iter.next();
            varName = this.findAlias(varName);
            Object obj = this._originalNestedVars.get(varName);
            if (obj != null) {
               this._invokingContext.setAttribute(varName, obj);
            } else {
               this._invokingContext.removeAttribute(varName, 1);
            }
         }
      }

   }

   private String findAlias(String varName) {
      if (this._aliases == null) {
         return varName;
      } else {
         String alias = (String)this._aliases.get(varName);
         return alias == null ? varName : alias;
      }
   }
}
