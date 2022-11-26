package com.xhaus.modjy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.PyType;
import org.python.util.PythonInterpreter;

public class ModjyJServlet extends HttpServlet {
   protected static final String MODJY_PYTHON_CLASSNAME = "modjy_servlet";
   protected static final String LIB_PYTHON = "/WEB-INF/lib-python";
   protected static final String PTH_FILE_EXTENSION = ".pth";
   protected static final String LOAD_SITE_PACKAGES_PARAM = "load_site_packages";
   protected static final String PYTHON_HOME_PARAM = "python.home";
   protected PythonInterpreter interp;
   protected HttpServlet modjyServlet;

   protected Properties readConfiguration() {
      Properties props = new Properties();
      ServletContext context = this.getServletContext();
      Enumeration e = context.getInitParameterNames();

      String pythonHomeString;
      while(e.hasMoreElements()) {
         pythonHomeString = (String)e.nextElement();
         props.put(pythonHomeString, context.getInitParameter(pythonHomeString));
      }

      e = this.getInitParameterNames();

      while(e.hasMoreElements()) {
         pythonHomeString = (String)e.nextElement();
         props.put(pythonHomeString, this.getInitParameter(pythonHomeString));
      }

      pythonHomeString = props.getProperty("python.home");
      if (pythonHomeString != null) {
         File pythonHome = new File(pythonHomeString);
         if (!pythonHome.isAbsolute()) {
            pythonHomeString = context.getRealPath(pythonHomeString);
         }

         props.setProperty("python.home", pythonHomeString);
      }

      return props;
   }

   public void init() throws ServletException {
      try {
         Properties props = this.readConfiguration();
         this.checkSitePackages(props);
         PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
         PySystemState systemState = new PySystemState();
         this.interp = new PythonInterpreter((PyObject)null, systemState);
         this.setupEnvironment(this.interp, props, systemState);

         try {
            this.interp.exec("from modjy.modjy import modjy_servlet");
         } catch (PyException var5) {
            throw new ServletException("Unable to import 'modjy_servlet': maybe you need to set the 'python.home' parameter?", var5);
         }

         PyObject pyServlet = ((PyType)this.interp.get("modjy_servlet")).__call__();
         Object temp = pyServlet.__tojava__(HttpServlet.class);
         if (temp == Py.NoConversion) {
            throw new ServletException("Corrupted modjy file: cannot find definition of 'modjy_servlet' class");
         } else {
            this.modjyServlet = (HttpServlet)temp;
            this.modjyServlet.init(this);
         }
      } catch (PyException var6) {
         throw new ServletException("Exception creating modjy servlet: " + var6.toString(), var6);
      }
   }

   public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      this.modjyServlet.service(req, resp);
   }

   public void destroy() {
      this.interp.cleanup();
   }

   protected void setupEnvironment(PythonInterpreter interp, Properties props, PySystemState systemState) throws PyException {
      this.processPythonLib(interp, systemState);
   }

   protected void checkSitePackages(Properties props) throws PyException {
      boolean loadSitePackages = true;
      String loadSitePackagesParam = props.getProperty("load_site_packages");
      if (loadSitePackagesParam != null && loadSitePackagesParam.trim().compareTo("0") == 0) {
         loadSitePackages = false;
      }

      Options.importSite = loadSitePackages;
   }

   protected void processPythonLib(PythonInterpreter interp, PySystemState systemState) {
      String pythonLibPath = this.getServletContext().getRealPath("/WEB-INF/lib-python");
      if (pythonLibPath != null) {
         File pythonLib = new File(pythonLibPath);
         if (pythonLib.exists()) {
            systemState.path.append(new PyString(pythonLibPath));
            String[] libPythonContents = pythonLib.list();
            String[] var6 = libPythonContents;
            int var7 = libPythonContents.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String libPythonContent = var6[var8];
               if (libPythonContent.endsWith(".pth")) {
                  this.processPthFile(interp, systemState, pythonLibPath, libPythonContent);
               }
            }

         }
      }
   }

   protected void processPthFile(PythonInterpreter interp, PySystemState systemState, String pythonLibPath, String pthFilename) {
      try {
         LineNumberReader lineReader = new LineNumberReader(new FileReader(new File(pythonLibPath, pthFilename)));

         String line;
         while((line = lineReader.readLine()) != null) {
            line = line.trim();
            if (line.length() != 0 && !line.startsWith("#")) {
               if (line.startsWith("import")) {
                  interp.exec(line);
               } else {
                  File archiveFile = new File(pythonLibPath, line);
                  String archiveRealpath = archiveFile.getAbsolutePath();
                  systemState.path.append(new PyString(archiveRealpath));
               }
            }
         }
      } catch (IOException var9) {
         System.err.println("IOException: " + var9.toString());
      }

   }
}
