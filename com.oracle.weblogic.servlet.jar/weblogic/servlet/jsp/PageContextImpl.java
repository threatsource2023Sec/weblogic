package weblogic.servlet.jsp;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Stack;
import javax.el.ELContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;
import weblogic.servlet.internal.ChunkOutputWrapper;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.enumerations.IteratorEnumerator;
import weblogic.utils.io.FilenameEncoder;

public final class PageContextImpl extends PageContext {
   private HttpServletRequest rq;
   private HttpServletResponse rp;
   protected Servlet servlet;
   protected ServletConfig config;
   protected ServletContext context;
   protected JspFactory factory;
   protected ELContext elContext;
   protected JspApplicationContextImpl jaCtxImpl;
   protected boolean needsSession;
   protected String errorPageURL;
   protected boolean autoFlush;
   protected int bufferSize;
   protected transient HashMap attributes;
   protected transient JspWriter out;
   private Stack writers = new Stack();

   public void initialize(Servlet svlet, ServletRequest req, ServletResponse rsp, String errorURL, boolean needsSession, int bufsize, boolean autoflush) throws IllegalStateException, IllegalArgumentException {
      this.servlet = svlet;
      this.config = svlet.getServletConfig();
      this.context = svlet.getServletConfig().getServletContext();
      this.rq = (HttpServletRequest)req;
      this.rp = (HttpServletResponse)rsp;
      this.errorPageURL = errorURL;
      this.needsSession = needsSession;
      this.bufferSize = bufsize;
      this.autoFlush = autoflush;
      this.attributes = new HashMap();
      rsp.setBufferSize(bufsize);
      this.setAttribute("javax.servlet.jsp.jspOut", this.out);
      this.setAttribute("javax.servlet.jsp.jspRequest", this.rq);
      this.setAttribute("javax.servlet.jsp.jspResponse", this.rp);
      this.setAttribute("javax.servlet.jsp.jspPage", this.servlet);
      this.setAttribute("javax.servlet.jsp.jspConfig", this.config);
      this.setAttribute("javax.servlet.jsp.jspPageContext", this);
      this.setAttribute("javax.servlet.jsp.jspApplication", this.context);
      this.setAttribute("weblogic.servlet.jsp", "true", 2);
   }

   protected PageContextImpl(JspFactory factory, Servlet servlet, ServletRequest rq, ServletResponse rp, String errorPageUrl, boolean needsSession, int bufferSize, boolean autoFlush) throws IOException, IllegalStateException, IllegalArgumentException {
      this.out = new JspWriterImpl(rp, bufferSize, autoFlush);
      this.initialize(servlet, rq, rp, errorPageUrl, needsSession, bufferSize, autoFlush);
   }

   private RequestDispatcher getRD(String url) throws ServletException {
      if (url == null) {
         throw new ServletException("requested URL string is null");
      } else {
         if (!url.startsWith("/")) {
            String orig = (String)this.rq.getAttribute("javax.servlet.include.request_uri");
            if (orig == null) {
               orig = ServletRequestImpl.getResolvedURI(this.rq);
            }

            int ind = orig.lastIndexOf(47);
            if (ind == -1) {
               url = '/' + url;
            } else if (ind == orig.length() - 1) {
               url = orig + url;
            } else {
               url = orig.substring(0, ind + 1) + url;
            }

            if (!"/".equals(this.context.getContextPath())) {
               url = url.substring(this.context.getContextPath().length());
            }

            ind = url.indexOf(63);
            String queryString = null;
            if (ind != -1) {
               queryString = url.substring(ind);
               url = url.substring(0, ind);
            }

            url = FilenameEncoder.resolveRelativeURIPath(url);
            if (queryString != null) {
               url = url + queryString;
            }
         }

         RequestDispatcher rd = this.servlet.getServletConfig().getServletContext().getRequestDispatcher(url);
         if (rd == null) {
            throw new ServletException("no request dispatcher available for '" + url + "'");
         } else {
            return rd;
         }
      }
   }

   public void forward(String url) throws IOException, ServletException {
      try {
         this.out.clear();
      } catch (IOException var3) {
         throw new IllegalStateException(var3);
      }

      this.getRD(url).forward(this.rq, this.rp);
   }

   public void include(String url) throws IOException, ServletException {
      this.getRD(url).include(this.rq, this.rp);
   }

   public void include(String url, boolean flush) throws IOException, ServletException {
      if (flush && !(this.out instanceof BodyContent)) {
         this.out.flush();
      }

      this.include(url);
   }

   public JspWriter _createOut(int buffsize, boolean autoflush) {
      return this.out;
   }

   public void setAttribute(String name, Object val) {
      if (name == null) {
         throw new NullPointerException("null name");
      } else {
         this.attributes.put(name, val);
      }
   }

   public Object getAttribute(String name) {
      if (name == null) {
         throw new NullPointerException("null name");
      } else {
         return this.attributes.get(name);
      }
   }

   public void removeAttribute(String name) {
      if (name == null) {
         throw new NullPointerException("removeAttribute called with null name");
      } else {
         this.attributes.remove(name);
         this.removeAttribute(name, 2);
         HttpSession session = this.getSession();
         if (session != null) {
            this.removeAttribute(name, 3);
         }

         this.removeAttribute(name, 4);
      }
   }

   public JspWriter getOut() {
      return this.out;
   }

   public HttpSession getSession() {
      if (this.needsSession) {
         this.needsSession = false;
         return this.rq.getSession(true);
      } else {
         return this.rq.getSession(false);
      }
   }

   private HttpSession getSessionForSessionScope() {
      HttpSession session = this.getSession();
      if (session == null) {
         throw new IllegalStateException("Either the page doesn't participate in a session, or the session is not valid any longer");
      } else {
         return session;
      }
   }

   public Object getPage() {
      return this.servlet;
   }

   public ServletRequest getRequest() {
      return this.rq;
   }

   public ServletResponse getResponse() {
      return this.rp;
   }

   public Exception getException() {
      return (Exception)this.rq.getAttribute("javax.servlet.jsp.jspException");
   }

   public ServletConfig getServletConfig() {
      return this.servlet.getServletConfig();
   }

   public ServletContext getServletContext() {
      return this.getServletConfig().getServletContext();
   }

   public void release() {
      this.rq = null;
      this.rp = null;
      this.context = null;
      this.errorPageURL = null;
      this.attributes.clear();
      this.out = null;
      this.writers = null;
   }

   public void setAttribute(String name, Object val, int scope) {
      switch (scope) {
         case 1:
            this.setAttribute(name, val);
            break;
         case 2:
            this.rq.setAttribute(name, val);
            break;
         case 3:
            HttpSession session = this.getSessionForSessionScope();
            session.setAttribute(name, val);
            break;
         case 4:
            this.context.setAttribute(name, val);
            break;
         default:
            throw new IllegalArgumentException("illegal scope: " + scope);
      }

   }

   public Object getAttribute(String name, int scope) {
      if (name == null) {
         throw new NullPointerException("null name");
      } else {
         switch (scope) {
            case 1:
               return this.getAttribute(name);
            case 2:
               return this.rq.getAttribute(name);
            case 3:
               HttpSession session = this.getSessionForSessionScope();
               return session.getAttribute(name);
            case 4:
               return this.context.getAttribute(name);
            default:
               throw new IllegalArgumentException("illegal scope: " + scope);
         }
      }
   }

   public Object findAttribute(String name) {
      if (name == null) {
         throw new NullPointerException("findAttribute called with a null value");
      } else {
         Object ret = null;
         ret = this.getAttribute(name, 1);
         if (ret == null) {
            ret = this.getAttribute(name, 2);
            if (ret == null) {
               HttpSession session = this.getSession();
               if (session != null) {
                  ret = this.getAttribute(name, 3);
               }

               if (ret == null) {
                  ret = this.getAttribute(name, 4);
               }
            }
         }

         return ret;
      }
   }

   public void removeAttribute(String name, int scope) {
      if (name == null) {
         throw new NullPointerException("name is null");
      } else {
         switch (scope) {
            case 1:
               this.attributes.remove(name);
               break;
            case 2:
               this.rq.removeAttribute(name);
               break;
            case 3:
               HttpSession session = this.getSessionForSessionScope();
               session.removeAttribute(name);
               break;
            case 4:
               this.context.removeAttribute(name);
               break;
            default:
               throw new IllegalArgumentException("illegal scope: " + scope);
         }

      }
   }

   public int getAttributesScope(String name) {
      if (name == null) {
         throw new NullPointerException("null name");
      } else if (this.getAttribute(name, 1) != null) {
         return 1;
      } else if (this.getAttribute(name, 2) != null) {
         return 2;
      } else if (this.getAttribute(name, 3) != null) {
         return 3;
      } else {
         return this.getAttribute(name, 4) != null ? 4 : 0;
      }
   }

   public Enumeration getAttributeNamesInScope(int scope) {
      switch (scope) {
         case 1:
            return new IteratorEnumerator(this.attributes.keySet().iterator());
         case 2:
            return this.rq.getAttributeNames();
         case 3:
            HttpSession session = this.getSessionForSessionScope();
            return session.getAttributeNames();
         case 4:
            return this.context.getAttributeNames();
         default:
            throw new IllegalArgumentException("illegal scope: " + scope);
      }
   }

   public void handlePageException(Throwable ee) throws ServletException, IOException {
      if (ee == null) {
         throw new NullPointerException("null Throwable");
      } else {
         Throwable e = ee;
         if (ee instanceof ServletException) {
            e = WebAppServletContext.getRootCause((ServletException)ee);
         }

         String reqUri = this.rq.getRequestURI();
         this.rq.setAttribute("javax.servlet.jsp.jspException", e);
         this.rq.setAttribute("javax.servlet.error.request_uri", reqUri);
         if (this.errorPageURL != null) {
            ((WebAppServletContext)this.context).getErrorManager().setErrorAttributes(this.rq, reqUri, e);
            if (this.rp.isCommitted()) {
               this.include(this.errorPageURL);
            } else {
               this.forward(this.errorPageURL);
            }

         } else {
            if (ee instanceof javax.servlet.jsp.JspException) {
               Throwable rootCause = ((javax.servlet.jsp.JspException)ee).getRootCause();
               if (rootCause != null) {
                  ee = rootCause;
               }
            }

            if (ee instanceof Error) {
               throw (Error)ee;
            } else if (ee instanceof RuntimeException) {
               throw (RuntimeException)ee;
            } else if (ee instanceof ServletException) {
               throw (ServletException)ee;
            } else if (ee instanceof IOException) {
               throw (IOException)ee;
            } else {
               throw new ServletException(ee);
            }
         }
      }
   }

   public void handlePageException(Exception e) throws ServletException, IOException {
      this.handlePageException((Throwable)e);
   }

   public JspWriter pushBody(Writer w) {
      this.writers.push(this.out);
      this.out = new BodyContentImpl(this.out, this, w);
      this.rp = new NestedBodyResponse(this.rp, (BodyContentImpl)this.out);
      this.setAttribute("javax.servlet.jsp.jspOut", this.out);
      ChunkOutputWrapper co = ((BodyContentImpl)this.out).co;
      this.rq.setAttribute("weblogic.servlet.BodyTagOutput", co);
      return (BodyContent)this.out;
   }

   public BodyContent pushBody() {
      return (BodyContent)this.pushBody((Writer)null);
   }

   public JspWriter popBody() {
      if (this.writers.isEmpty()) {
         return this.out;
      } else {
         this.out = (JspWriter)this.writers.pop();
         if (this.rp instanceof NestedBodyResponse) {
            this.rp = (HttpServletResponse)((NestedBodyResponse)this.rp).getResponse();
         }

         if (this.out instanceof BodyContentImpl) {
            ChunkOutputWrapper co = ((BodyContentImpl)this.out).co;
            this.rq.setAttribute("weblogic.servlet.BodyTagOutput", co);
         } else {
            this.rq.removeAttribute("weblogic.servlet.BodyTagOutput");
         }

         this.setAttribute("javax.servlet.jsp.jspOut", this.out);
         return this.out;
      }
   }

   public ExpressionEvaluator getExpressionEvaluator() {
      return JspConfig.getExpressionEvaluator();
   }

   public VariableResolver getVariableResolver() {
      return JspConfig.getVariableResolver(this);
   }

   public ELContext getELContext() {
      if (this.elContext == null) {
         this.elContext = this.getJspApplicationContextImpl().createELContext(this);
      }

      return this.elContext;
   }

   public JspApplicationContextImpl getJspApplicationContextImpl() {
      if (this.jaCtxImpl == null) {
         JspFactoryImpl.init();
         this.jaCtxImpl = (JspApplicationContextImpl)JspFactoryImpl.getDefaultFactory().getJspApplicationContext(this.context);
      }

      return this.jaCtxImpl;
   }
}
