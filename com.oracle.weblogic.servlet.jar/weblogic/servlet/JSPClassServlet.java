package weblogic.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.jsp.AddToMapException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.io.FilenameEncoder;

public class JSPClassServlet extends HttpServlet {
   protected String packagePrefix;
   protected WebAppServletContext context;
   private boolean internalApp = false;

   public synchronized void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.context = (WebAppServletContext)config.getServletContext();
      this.packagePrefix = this.context.getJSPManager().createJspConfig().getPackagePrefix();
      this.internalApp = this.context.isInternalApp();
   }

   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
      StringBuilder sb = new StringBuilder(30);
      String servletPath = (String)req.getAttribute("javax.servlet.include.servlet_path");
      String pathInfo = (String)req.getAttribute("javax.servlet.include.path_info");
      if (servletPath == null && pathInfo == null) {
         servletPath = req.getServletPath();
         pathInfo = req.getPathInfo();
      }

      if (servletPath != null) {
         sb.append(servletPath);
      }

      if (pathInfo != null) {
         sb.append(pathInfo);
      }

      int sblen = sb.length();
      if (sblen != 0 && sb.charAt(sblen - 1) != '/') {
         String uri = FilenameEncoder.resolveRelativeURIPath(sb.toString());
         String className = JSPServlet.uri2classname(this.packagePrefix, uri);
         if (this.internalApp) {
            className = className.toLowerCase();
         }

         ClassFinder classFinder = this.context.getResourceFinder("/");
         if (classFinder.getClassSource(className) == null) {
            rsp.sendError(404);
         } else {
            ServletStubImpl sstub = new ServletStubImpl(uri, className, (WebAppServletContext)this.getServletContext());
            throw new AddToMapException(uri, sstub);
         }
      } else {
         rsp.sendError(404);
      }
   }
}
