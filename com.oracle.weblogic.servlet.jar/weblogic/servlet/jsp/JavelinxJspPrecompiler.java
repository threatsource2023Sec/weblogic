package weblogic.servlet.jsp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.JspPropertyGroupBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.JSPServlet;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.enumerations.ResourceEnumerator;

public class JavelinxJspPrecompiler implements JSPPrecompiler {
   private WebAppServletContext servletContext;
   private ResourceEnumerator resourceEnumerator;
   private File workingDir = null;
   private JspConfig config;
   private StubCompilerContext compilerContext;

   public void compile(WebAppServletContext sc, File docroot) throws Exception {
      this.init(sc, docroot, sc.getJSPManager().createJspConfig());
      this.compileAll();
   }

   private void init(WebAppServletContext servletContext, File f, JspConfig jspConfig) {
      this.servletContext = servletContext;
      this.compilerContext = new StubCompilerContext(servletContext);
      String s = jspConfig.getWorkingDir();
      WebAppBean wb = servletContext.getWebAppModule().getWebAppBean();
      JspConfigBean[] configBean = wb.getJspConfigs();
      HashMap extnMatches = new HashMap();
      extnMatches.put("*.jsp", "*.jsp");
      extnMatches.put("*.jspx", "*.jspx");

      for(int i = 0; i < configBean.length; ++i) {
         JspPropertyGroupBean[] grpBean = configBean[i].getJspPropertyGroups();

         for(int j = 0; j < grpBean.length; ++j) {
            String[] patterns = grpBean[j].getUrlPatterns();

            for(int k = 0; k < patterns.length; ++k) {
               if (extnMatches.get(patterns[k]) == null) {
                  extnMatches.put(patterns[k], patterns[k]);
               }
            }
         }
      }

      if (s != null) {
         this.workingDir = new File(s.replace('/', File.separatorChar));
      } else {
         this.workingDir = servletContext.getRootTempDir();
      }

      String[] ignore = new String[]{"*.html", "/classes/*"};
      String[] match = (String[])extnMatches.keySet().toArray(new String[extnMatches.size()]);
      this.resourceEnumerator = ResourceEnumerator.makeInstance(f, ignore, match);
      servletContext.addClassPath(this.workingDir.getAbsolutePath());
      this.config = jspConfig;
   }

   private void compileAll() throws Exception {
      String pkgPrefix = this.config.getPackagePrefix();
      if (pkgPrefix == null) {
         pkgPrefix = "jsp_servlet";
      }

      List toCompile = new ArrayList();

      try {
         String uri = null;

         while((uri = this.resourceEnumerator.getNextURI()) != null) {
            if (!uri.startsWith("/")) {
               uri = "/" + uri;
            }

            if (this.needsCompilation(uri, pkgPrefix)) {
               toCompile.add(uri);
            } else {
               HTTPLogger.logJSPClassUptodate(this.servletContext.getAppDisplayName(), uri);
            }
         }
      } finally {
         this.resourceEnumerator.close();
      }

      byte maxFiles = 50;
      int size = toCompile.size();
      JspFileLock lock = new JspFileLock(new File(this.compilerContext.getJspConfig().getWorkingDir()));
      lock.lock();

      try {
         int i = 0;

         for(int j = maxFiles; i < size; i += j) {
            int incr = Math.min(j, size - i);
            int to = i + incr;
            List sublist = toCompile.subList(i, to);
            JspCLLManager.compileJsps(sublist, this.compilerContext);
         }
      } finally {
         lock.release();
      }

   }

   private boolean needsCompilation(String uri, String pkgPrefix) {
      Class clazz = null;

      try {
         String className = JSPServlet.uri2classname(pkgPrefix, uri);
         ClassLoader cl = this.makeLoader(className);
         clazz = cl.loadClass(className);
      } catch (ClassNotFoundException var6) {
         HTTPLogger.logPrecompilingJspNoClass(this.servletContext.getAppDisplayName(), uri);
         return true;
      } catch (Throwable var7) {
      }

      if (clazz != null && JspStub.isJSPClassStale(clazz, this.servletContext)) {
         HTTPLogger.logPrecompilingStaleJsp(this.servletContext.getAppDisplayName(), uri);
         return true;
      } else {
         return false;
      }
   }

   private ClassLoader makeLoader(String className) {
      GenericClassLoader parent = (GenericClassLoader)this.servletContext.getServletClassLoader();
      ClassFinder parentFinder = parent.getClassFinder();
      GenericClassLoader child = new JspClassLoader(parentFinder, parent, className);
      child.addClassFinderFirst(new ClasspathClassFinder2(this.config.getWorkingDir()));
      return child;
   }

   static class StubCompilerContext implements JspCLLManager.IJSPCompilerContext {
      private WebAppServletContext context;

      StubCompilerContext(WebAppServletContext context) {
         this.context = context;
      }

      public ClassLoader getClassLoader() {
         return this.context.getTagFileHelper().getCompileTimeTagFileClassLoader();
      }

      public Source getSource(String URI) {
         return this.context.getResourceAsSource(URI);
      }

      public String getClasspath() {
         return this.context.getFullClasspath();
      }

      public JspConfig getJspConfig() {
         return this.context.getJSPManager().createJspConfig();
      }

      public String[] getSourcePaths() {
         String cp = this.context.getResourceFinder("/").getClassPath();
         return StringUtils.splitCompletely(cp, File.pathSeparator);
      }

      public WebAppBean getWebAppBean() {
         return this.context.getWebAppModule().getWebAppBean();
      }

      public WeblogicWebAppBean getWlWebAppBean() {
         return this.context.getWebAppModule().getWlWebAppBean();
      }

      public String getName() {
         return this.context.getName();
      }

      public ServletWorkContext getServletContext() {
         return this.context;
      }

      public String getContextPath() {
         return this.context.getContextPath();
      }

      public void say(String jspURI) {
      }

      public void sayError(String jspURI, String errorString) {
         HTTPLogger.logJSPPrecompileErrors(this.context.getAppDisplayName(), jspURI, errorString);
      }

      public Set getAdditionalExtensions() {
         return null;
      }

      public String[] getSplitDirectoryJars() {
         return this.context.getWarInstance().getSplitDirectoryJars();
      }
   }
}
