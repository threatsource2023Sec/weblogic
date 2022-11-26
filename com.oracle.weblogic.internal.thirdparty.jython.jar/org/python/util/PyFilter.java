package org.python.util;

import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.python.core.PyException;

public class PyFilter implements Filter {
   public static final String FILTER_PATH_PARAM = "__filter__";
   private PythonInterpreter interp;
   private FilterConfig config;
   private File source;
   private Filter cached;
   private long loadedMtime;

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      request.setAttribute("pyfilter", this);
      this.getFilter().doFilter(request, response, chain);
   }

   public void init(FilterConfig config) throws ServletException {
      if (config.getServletContext().getAttribute("__jython_initialized__") == null) {
         throw new ServletException("Jython has not been initialized.  Add org.python.util.PyServletInitializer as a listener to your web.xml.");
      } else {
         this.config = config;
         String filterPath = config.getInitParameter("__filter__");
         if (filterPath == null) {
            throw new ServletException("Missing required param '__filter__'");
         } else {
            this.source = new File(this.getRealPath(config.getServletContext(), filterPath));
            if (!this.source.exists()) {
               throw new ServletException(this.source.getAbsolutePath() + " does not exist.");
            } else {
               this.interp = PyServlet.createInterpreter(config.getServletContext());
            }
         }
      }
   }

   private String getRealPath(ServletContext context, String appPath) {
      String realPath = context.getRealPath(appPath);
      return realPath.replaceAll("\\\\", "/");
   }

   private Filter getFilter() throws ServletException, IOException {
      return this.cached != null && this.source.lastModified() <= this.loadedMtime ? this.cached : this.loadFilter();
   }

   private Filter loadFilter() throws ServletException, IOException {
      this.loadedMtime = this.source.lastModified();
      this.cached = (Filter)PyServlet.createInstance(this.interp, this.source, Filter.class);

      try {
         this.cached.init(this.config);
      } catch (PyException var2) {
         throw new ServletException(var2);
      }

      return this.cached;
   }

   public void destroy() {
      if (this.cached != null) {
         this.cached.destroy();
      }

      if (this.interp != null) {
         this.interp.cleanup();
      }

   }
}
