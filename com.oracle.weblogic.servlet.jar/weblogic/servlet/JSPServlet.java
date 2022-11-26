package weblogic.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.jsp.internal.jsp.utils.JavaTransformUtils;
import weblogic.servlet.internal.ServletStubFactory;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.jsp.AddToMapException;
import weblogic.servlet.jsp.JspConfig;
import weblogic.servlet.jsp.JspFactoryImpl;
import weblogic.servlet.jsp.JspStub;
import weblogic.utils.classloaders.Source;
import weblogic.utils.io.FilenameEncoder;

public class JSPServlet extends HttpServlet {
   public static final String DEFAULT_PACKAGE_PREFIX = "jsp_servlet";
   public static final String DEFAULT_PAGE_NAME = "index.jsp";
   protected JspConfig jspConfig;
   protected WebAppServletContext ourContext;
   private static boolean isCaseSensitive;

   public synchronized void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.ourContext = (WebAppServletContext)this.getServletContext();
      this.jspConfig = this.ourContext.getJSPManager().createJspConfig();
      this.jspConfig.setValuesFromInitArgs(config);
      boolean verbose = this.jspConfig.isVerbose();
      if (verbose) {
         this.log("param verbose initialized to: " + verbose);
         this.log("param packagePrefix initialized to: " + this.jspConfig.getPackagePrefix());
         this.log("param compilerclass initialized to: " + this.jspConfig.getCompilerClass());
         this.log("param compileCommand initialized to: " + this.jspConfig.getCompileCommand());
         this.log("param compilerval initialized to: " + this.jspConfig.getCompilerval());
         this.log("param pageCheckSeconds initialized to: " + this.jspConfig.getPageCheckSecs());
         this.log("param encoding initialized to: " + this.jspConfig.getEncoding());
         this.log("param superclass initialized to " + this.jspConfig.getSuperClassName());
      }

      if (this.jspConfig.getCompilerval() == null) {
         throw new UnavailableException("Couldn't find init param: compileCommand");
      } else {
         String workingDir = this.jspConfig.getWorkingDir();
         if (workingDir == null) {
            throw new UnavailableException("Couldn't find init param: workingDir");
         } else {
            File wd = new File(workingDir.replace('/', File.separatorChar));
            if (!wd.exists()) {
               if (!wd.mkdirs()) {
                  throw new UnavailableException("Working directory: " + workingDir + " was not found and could not be created.");
               }

               this.log("Making new working directory: " + workingDir);
            }

            try {
               workingDir = wd.getCanonicalPath();
            } catch (IOException var6) {
               throw new UnavailableException("Couldn't resolve canonical path for: " + workingDir);
            }

            if (verbose) {
               this.log("param workingDir initialized to: " + workingDir);
            }

            this.ourContext.addClassPath(workingDir);
            if (verbose) {
               this.log("initialization complete");
            }

         }
      }
   }

   public void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
      ServletStubImpl sstub = null;
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
         if (!this.jspConfig.isExactMapping() && servletPath != null) {
            String lowered = pathInfo.toLowerCase();
            int pos = false;
            int pos;
            if ((pos = lowered.indexOf(".jsp/")) != -1) {
               pathInfo = pathInfo.substring(0, pos + 4);
               sb.append(pathInfo);
            } else if ((pos = lowered.indexOf(".jspx/")) != -1) {
               pathInfo = pathInfo.substring(0, pos + 5);
               sb.append(pathInfo);
            } else {
               sb.append(pathInfo);
            }
         } else {
            sb.append(pathInfo);
         }
      }

      int sblen = sb.length();
      if (sblen == 0) {
         sb.append('/');
         sb.append(this.jspConfig.getDefaultFilename());
      } else if (sb.charAt(sblen - 1) == '/') {
         sb.append(this.jspConfig.getDefaultFilename());
      }

      String uri = FilenameEncoder.resolveRelativeURIPath(sb.toString());
      if (this.ourContext.getJSPManager().getResourceProviderClass() == null) {
         Source source = this.ourContext.getResourceAsSource(uri);
         if (source == null) {
            rsp.sendError(404);
            return;
         }

         if (source.length() == 0L) {
            rsp.setContentLength(0);
            return;
         }
      }

      WebAppServletContext ctx = (WebAppServletContext)this.getServletContext();
      sstub = ctx.getServletStub(uri);
      if (sstub != null && sstub instanceof JspStub && sstub.getClassName() != null && !sstub.getClassName().equals(this.getClass().getName())) {
         throw new AddToMapException(uri, sstub);
      } else {
         sstub = ServletStubFactory.getInstance(this.ourContext, uri, this.jspConfig);
         throw new AddToMapException(uri, sstub);
      }
   }

   public static String uri2classname(String pkgPrefix, String uri) {
      try {
         return !uri.endsWith(".tag") && !uri.endsWith(".tagx") ? JavaTransformUtils.getFullClassName(uri, false, pkgPrefix) : JavaTransformUtils.computeTagClassName(uri, pkgPrefix);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new RuntimeException(var3);
         }
      }
   }

   static {
      JspFactoryImpl.init();
      isCaseSensitive = false;

      try {
         String cs = System.getProperty("weblogic.jsp.windows.caseSensitive");
         String csa = System.getProperty("weblogic.jsp.caseSensitive");
         if ("true".equalsIgnoreCase(cs) || "true".equalsIgnoreCase(csa)) {
            isCaseSensitive = true;
         }
      } catch (Throwable var2) {
      }

   }
}
