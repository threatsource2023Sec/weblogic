package weblogic.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.management.ManagementException;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.WebAppServletContext;

public final class ServletServlet extends HttpServlet {
   private static final boolean DEBUG = false;
   private Map servletStubs;
   private WebAppServletContext context;
   private static final String[] SERVLET_BLACK_LIST = new String[]{"kodo.remote.PersistenceServerServlet"};

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.context = (WebAppServletContext)config.getServletContext();
      this.servletStubs = new HashMap();
   }

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String pathInfo = (String)req.getAttribute("javax.servlet.include.path_info");
      if (pathInfo == null) {
         pathInfo = req.getPathInfo();
      }

      ServletStubImpl servlet = this.resolveServlet(pathInfo);
      if (servlet != null) {
         servlet.execute(req, res);
      } else {
         res.sendError(404);
      }

   }

   private void print(String msg) {
   }

   private ServletStubImpl resolveServlet(String uri) {
      ServletStubImpl sstub = null;
      uri = uri.substring(1);
      String classname = uri.replace('/', '.');
      String[] var4 = SERVLET_BLACK_LIST;
      int idx = var4.length;

      for(int var6 = 0; var6 < idx; ++var6) {
         String nameInBlackList = var4[var6];
         if (nameInBlackList.equalsIgnoreCase(classname)) {
            return null;
         }
      }

      while(true) {
         try {
            this.context.getServletClassLoader().loadClass(classname);
            break;
         } catch (ClassNotFoundException var8) {
            idx = classname.lastIndexOf(".");
            if (idx == -1) {
               return null;
            }

            classname = classname.substring(0, idx);
         }
      }

      String sName = classname.replace('.', '/');
      sstub = (ServletStubImpl)this.servletStubs.get(sName);
      if (sstub == null) {
         sstub = this.registerServlet(sName, classname);
      }

      return sstub;
   }

   private ServletStubImpl registerServlet(String name, String className) {
      ServletStubImpl sstub = null;

      try {
         sstub = new ServletStubImpl(name, className, this.context);
         sstub.initRuntime();
      } catch (ManagementException var8) {
         HTTPLogger.logErrorCreatingServletStub((String)null, name, className, (Object)null, var8);
         return null;
      }

      synchronized(this.servletStubs) {
         Map newStubs = (Map)((HashMap)this.servletStubs).clone();
         newStubs.put(name, sstub);
         this.servletStubs = newStubs;
         return sstub;
      }
   }

   public void destroy() {
      Collection stubs = this.servletStubs.values();
      if (stubs != null && !stubs.isEmpty()) {
         Iterator stubsIterator = stubs.iterator();

         while(stubsIterator.hasNext()) {
            ServletStubImpl stubImpl = (ServletStubImpl)stubsIterator.next();
            stubImpl.destroyRuntime();
         }

         this.servletStubs.clear();
         this.servletStubs = null;
         this.context = null;
      }
   }
}
