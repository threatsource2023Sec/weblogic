package org.apache.taglibs.standard.lang.jstl.test;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

public class PageContextImpl extends PageContext {
   Map mPage = Collections.synchronizedMap(new HashMap());
   Map mRequest = Collections.synchronizedMap(new HashMap());
   Map mSession = Collections.synchronizedMap(new HashMap());
   Map mApp = Collections.synchronizedMap(new HashMap());

   public void initialize(Servlet servlet, ServletRequest request, ServletResponse response, String errorPageURL, boolean needSession, int bufferSize, boolean autoFlush) {
   }

   public void release() {
   }

   public void setAttribute(String name, Object attribute) {
      this.mPage.put(name, attribute);
   }

   public void setAttribute(String name, Object attribute, int scope) {
      switch (scope) {
         case 1:
            this.mPage.put(name, attribute);
            break;
         case 2:
            this.mRequest.put(name, attribute);
            break;
         case 3:
            this.mSession.put(name, attribute);
            break;
         case 4:
            this.mApp.put(name, attribute);
            break;
         default:
            throw new IllegalArgumentException("Bad scope " + scope);
      }

   }

   public Object getAttribute(String name) {
      return this.mPage.get(name);
   }

   public Object getAttribute(String name, int scope) {
      switch (scope) {
         case 1:
            return this.mPage.get(name);
         case 2:
            return this.mRequest.get(name);
         case 3:
            return this.mSession.get(name);
         case 4:
            return this.mApp.get(name);
         default:
            throw new IllegalArgumentException("Bad scope " + scope);
      }
   }

   public Object findAttribute(String name) {
      if (this.mPage.containsKey(name)) {
         return this.mPage.get(name);
      } else if (this.mRequest.containsKey(name)) {
         return this.mRequest.get(name);
      } else if (this.mSession.containsKey(name)) {
         return this.mSession.get(name);
      } else {
         return this.mApp.containsKey(name) ? this.mApp.get(name) : null;
      }
   }

   public void removeAttribute(String name) {
      if (this.mPage.containsKey(name)) {
         this.mPage.remove(name);
      } else if (this.mRequest.containsKey(name)) {
         this.mRequest.remove(name);
      } else if (this.mSession.containsKey(name)) {
         this.mSession.remove(name);
      } else if (this.mApp.containsKey(name)) {
         this.mApp.remove(name);
      }

   }

   public void removeAttribute(String name, int scope) {
      switch (scope) {
         case 1:
            this.mPage.remove(name);
            break;
         case 2:
            this.mRequest.remove(name);
            break;
         case 3:
            this.mSession.remove(name);
            break;
         case 4:
            this.mApp.remove(name);
            break;
         default:
            throw new IllegalArgumentException("Bad scope " + scope);
      }

   }

   public int getAttributesScope(String name) {
      if (this.mPage.containsKey(name)) {
         return 1;
      } else if (this.mRequest.containsKey(name)) {
         return 2;
      } else if (this.mSession.containsKey(name)) {
         return 3;
      } else {
         return this.mApp.containsKey(name) ? 4 : 0;
      }
   }

   public Enumeration getAttributeNamesInScope(int scope) {
      return null;
   }

   public JspWriter getOut() {
      return null;
   }

   public HttpSession getSession() {
      return null;
   }

   public Object getPage() {
      return null;
   }

   public ServletRequest getRequest() {
      return null;
   }

   public ServletResponse getResponse() {
      return null;
   }

   public Exception getException() {
      return null;
   }

   public ServletConfig getServletConfig() {
      return null;
   }

   public ServletContext getServletContext() {
      return null;
   }

   public void forward(String path) {
   }

   public void include(String path) {
   }

   public void handlePageException(Exception exc) {
   }

   public void handlePageException(Throwable exc) {
   }

   public void include(String relativeUrlPath, boolean flush) {
   }

   public ExpressionEvaluator getExpressionEvaluator() {
      return null;
   }

   public VariableResolver getVariableResolver() {
      return null;
   }

   public ELContext getELContext() {
      return null;
   }
}
