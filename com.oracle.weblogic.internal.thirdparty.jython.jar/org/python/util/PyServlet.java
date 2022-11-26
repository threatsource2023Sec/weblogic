package org.python.util;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;

public class PyServlet extends HttpServlet {
   public static final String SKIP_INIT_NAME = "skip_jython_initialization";
   protected static final String INIT_ATTR = "__jython_initialized__";
   private static final Pattern FIND_NAME = Pattern.compile("([^/]+)\\.py$");
   private PythonInterpreter interp;
   private Map cache = Generic.map();

   public void init() {
      Properties props = new Properties();
      Enumeration e = this.getInitParameterNames();

      while(e.hasMoreElements()) {
         String name = (String)e.nextElement();
         props.put(name, this.getInitParameter(name));
      }

      boolean initialize = this.getServletConfig().getInitParameter("skip_jython_initialization") != null;
      if (this.getServletContext().getAttribute("__jython_initialized__") != null) {
         if (initialize) {
            System.err.println("Jython has already been initialized in this context, not initializing for " + this.getServletName() + ".  Add " + "skip_jython_initialization" + " to as an init param to this servlet's configuration to indicate this " + "is expected.");
         }
      } else if (initialize) {
         init(props, this.getServletContext());
      }

      this.reset();
   }

   protected static void init(Properties props, ServletContext context) {
      String rootPath = getRootPath(context);
      context.setAttribute("__jython_initialized__", true);
      Properties baseProps = PySystemState.getBaseProperties();
      Enumeration e = context.getInitParameterNames();

      while(e.hasMoreElements()) {
         String name = (String)e.nextElement();
         props.put(name, context.getInitParameter(name));
      }

      if (props.getProperty("python.home") == null && baseProps.getProperty("python.home") == null) {
         props.put("python.home", rootPath + "WEB-INF" + File.separator + "lib");
      }

      PySystemState.initialize(baseProps, props, new String[0]);
      PySystemState.add_package("javax.servlet");
      PySystemState.add_package("javax.servlet.http");
      PySystemState.add_package("javax.servlet.jsp");
      PySystemState.add_package("javax.servlet.jsp.tagext");
      PySystemState.add_classdir(rootPath + "WEB-INF" + File.separator + "classes");
      PySystemState.add_extdir(rootPath + "WEB-INF" + File.separator + "lib", true);
   }

   protected static PythonInterpreter createInterpreter(ServletContext servletContext) {
      String rootPath = getRootPath(servletContext);
      PySystemState sys = new PySystemState();
      PythonInterpreter interp = new PythonInterpreter(Py.newStringMap(), sys);
      sys.path.append(new PyString(rootPath));
      String modulesDir = rootPath + "WEB-INF" + File.separator + "jython";
      sys.path.append(new PyString(modulesDir));
      return interp;
   }

   protected static String getRootPath(ServletContext context) {
      String rootPath = context.getRealPath("/");
      if (!rootPath.endsWith(File.separator)) {
         rootPath = rootPath + File.separator;
      }

      return rootPath;
   }

   public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
      req.setAttribute("pyservlet", this);
      String spath = (String)req.getAttribute("javax.servlet.include.servlet_path");
      if (spath == null) {
         spath = ((HttpServletRequest)req).getServletPath();
         if (spath == null || spath.length() == 0) {
            spath = ((HttpServletRequest)req).getPathInfo();
         }
      }

      String rpath = this.getServletContext().getRealPath(spath);
      this.getServlet(rpath).service(req, res);
   }

   public void destroy() {
      super.destroy();
      this.destroyCache();
   }

   public void reset() {
      this.destroyCache();
      this.interp = createInterpreter(this.getServletContext());
   }

   private synchronized HttpServlet getServlet(String path) throws ServletException, IOException {
      CacheEntry entry = (CacheEntry)this.cache.get(path);
      return entry != null && (new File(path)).lastModified() <= entry.date ? entry.servlet : this.loadServlet(path);
   }

   private HttpServlet loadServlet(String path) throws ServletException, IOException {
      File file = new File(path);
      HttpServlet servlet = (HttpServlet)createInstance(this.interp, file, HttpServlet.class);

      try {
         servlet.init(this.getServletConfig());
      } catch (PyException var5) {
         throw new ServletException(var5);
      }

      this.cache.put(path, new CacheEntry(servlet, file.lastModified()));
      return servlet;
   }

   protected static Object createInstance(PythonInterpreter interp, File file, Class type) throws ServletException {
      Matcher m = FIND_NAME.matcher(file.getName());
      if (!m.find()) {
         throw new ServletException("I can't guess the name of the class from " + file.getAbsolutePath());
      } else {
         String name = m.group(1);

         try {
            interp.set("__file__", (Object)file.getAbsolutePath());
            interp.execfile(file.getAbsolutePath());
            PyObject cls = interp.get(name);
            if (cls == null) {
               throw new ServletException("No callable (class or function) named " + name + " in " + file.getAbsolutePath());
            } else {
               PyObject pyServlet = cls.__call__();
               Object o = pyServlet.__tojava__(type);
               if (o == Py.NoConversion) {
                  throw new ServletException("The value from " + name + " must extend " + type.getSimpleName());
               } else {
                  return o;
               }
            }
         } catch (PyException var9) {
            throw new ServletException(var9);
         }
      }
   }

   private void destroyCache() {
      Iterator var1 = this.cache.values().iterator();

      while(var1.hasNext()) {
         CacheEntry entry = (CacheEntry)var1.next();
         entry.servlet.destroy();
      }

      this.cache.clear();
   }

   private static class CacheEntry {
      public long date;
      public HttpServlet servlet;

      CacheEntry(HttpServlet servlet, long date) {
         this.servlet = servlet;
         this.date = date;
      }
   }
}
