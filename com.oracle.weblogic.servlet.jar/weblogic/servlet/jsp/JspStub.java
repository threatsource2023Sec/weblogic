package weblogic.servlet.jsp;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import oracle.jsp.provider.JspResourceProvider;
import weblogic.servlet.internal.RequestCallback;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.StubLifecycleHelper;
import weblogic.servlet.internal.WarSource;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public abstract class JspStub extends ServletStubImpl {
   public static final String DEFAULT_PACKAGE_PREFIX = "jsp_servlet";
   protected JspConfig jsps;
   protected JspResourceProvider resourceProvider;
   private String hardcodedFilePath = null;
   private static final boolean alwaysCheckDisk = initAlwaysCheckDiskProp();
   private long lastStaleCheck;
   private URL sourceUrl = null;
   private GenericClassLoader jspClassLoader;

   public JspStub(String name, String className, WebAppServletContext sci, JspConfig jsps) {
      super(name, className, sci);
      this.jsps = jsps;
      this.hardcodedFilePath = name;
      this.resourceProvider = sci.getJspResourceProvider();
   }

   protected boolean isPreliminary() {
      return false;
   }

   protected String getDefaultContentType() {
      return "text/html";
   }

   protected boolean isServletStale(Servlet tmpServlet) {
      StaleIndicator jspx = (StaleIndicator)tmpServlet;

      try {
         return jspx._isStale();
      } catch (Throwable var4) {
         return true;
      }
   }

   public String getFilePath() {
      return this.hardcodedFilePath;
   }

   public void setFilePath(String fp) {
      this.hardcodedFilePath = fp;
   }

   protected synchronized ClassLoader getNewClassLoader() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            GenericClassLoader parent = JspStub.this.getContext().getTagFileHelper().getTagFileClassLoader();
            ClassFinder parentFinder = parent.getClassFinder();
            JspStub.this.jspClassLoader = new JspClassLoader(parentFinder, parent, JspStub.this.className);
            JspStub.this.jspClassLoader.addClassFinderFirst(new ClasspathClassFinder2(JspStub.this.jsps.getWorkingDir()));
            return null;
         }
      });
      return this.jspClassLoader;
   }

   protected ClassLoader getClassLoader() {
      return (ClassLoader)(this.jspClassLoader == null ? this.getNewClassLoader() : this.jspClassLoader);
   }

   public void reloadJSPOnUpdate(RequestCallback rc) throws IOException, ServletException {
      synchronized(this) {
         this.getLifecycleHelper().destroy();
         this.prepareServlet(rc, false);
      }
   }

   protected void checkForReload(RequestCallback rc) throws ServletException, UnavailableException, IOException {
      if (this.lastStaleCheck > 0L && this.getContext().getTagFileHelper().getCLCreationTime() > this.lastStaleCheck) {
         this.lastStaleCheck = 0L;
      }

      long reloadInterval = this.jsps.getPageCheckSecs() * 1000L;
      if (reloadInterval >= 0L && this.lastStaleCheck + reloadInterval <= System.currentTimeMillis()) {
         synchronized(this) {
            if (this.lastStaleCheck + reloadInterval <= System.currentTimeMillis()) {
               boolean needReload = false;
               Source source = this.getSourceForRequestURI(rc);
               URI sourceURI = source == null ? null : getURIFromURL(source.getURL());
               if (this.resourceProvider == null && (source == null || this.isJspFromArchivedLib(source) && !sourceURI.equals(getURIFromURL(this.sourceUrl)))) {
                  needReload = true;
               }

               if (needReload || this.isStale() || this.isClassLoaderStale()) {
                  synchronized(this) {
                     this.getLifecycleHelper().destroy();
                     this.prepareServlet(rc, alwaysCheckDisk);
                  }
               }

               this.lastStaleCheck = System.currentTimeMillis();
            }
         }
      }

      super.checkForReload(rc);
   }

   private static URI getURIFromURL(URL url) {
      if (url == null) {
         return null;
      } else {
         try {
            return url.toURI();
         } catch (URISyntaxException var2) {
            return null;
         }
      }
   }

   protected final boolean isStale() {
      StubLifecycleHelper f = this.getLifecycleHelper();
      if (f == null) {
         return false;
      } else {
         boolean result;
         try {
            Servlet tempServlet = f.getServlet();
            boolean b = this.isServletStale(tempServlet);
            f.returnServlet(tempServlet);
            result = b;
         } catch (ServletException var5) {
            result = true;
         }

         return result;
      }
   }

   private boolean isClassLoaderStale() {
      if (this.jspClassLoader == null) {
         return false;
      } else {
         boolean ret = this.jspClassLoader.getParent() != this.getContext().getTagFileHelper().getTagFileClassLoader();
         return ret;
      }
   }

   protected Source getSourceForRequestURI(RequestCallback rc) throws JspFileNotFoundException {
      if (this.getContext() != null) {
         String uri = this.getRequestURI(rc);
         if (uri != null) {
            return this.getContext().getResourceAsSource(uri);
         }
      }

      return null;
   }

   protected String getRequestURI(RequestCallback rc) {
      return this.hardcodedFilePath != null ? this.hardcodedFilePath : rc.getIncludeURI();
   }

   protected void prepareServlet(RequestCallback rc) throws ServletException, UnavailableException, IOException {
      this.prepareServlet(rc, true);
      if (this.lastStaleCheck == 0L) {
         this.lastStaleCheck = System.currentTimeMillis();
      }

   }

   private void prepareServlet(RequestCallback rc, boolean checkDisk) throws ServletException, UnavailableException, IOException {
      Source source = this.getSourceForRequestURI(rc);
      String uri = this.getRequestURI(rc);
      if (source == null && this.resourceProvider == null && this.getContext() != null && uri != null) {
         throw new JspFileNotFoundException("Requested JSP source file '" + uri + "' no longer exists");
      } else {
         ClassLoader loader = this.getNewClassLoader();
         boolean needToCompile = true;
         if (this.getLifecycleHelper() == null || checkDisk || this.isJspFromArchivedLib(source)) {
            try {
               Class cl = loader.loadClass(this.getClassName());
               if (!isJSPClassStale(cl, this.getContext())) {
                  needToCompile = false;
               }
            } catch (ThreadDeath var25) {
               throw var25;
            } catch (Throwable var26) {
            }
         }

         if (source != null) {
            URL currSourceUrl = source.getURL();
            URI currSourceUri = getURIFromURL(currSourceUrl);
            URI sourceUri = this.sourceUrl == null ? null : getURIFromURL(this.sourceUrl);
            if (this.isJspFromArchivedLib(source) && !needToCompile && sourceUri != null && !sourceUri.equals(currSourceUri)) {
               needToCompile = true;
            }

            this.sourceUrl = currSourceUrl;
         }

         if (needToCompile) {
            Thread thread = Thread.currentThread();
            ClassLoader cl = thread.getContextClassLoader();

            try {
               thread.setContextClassLoader(loader);
               this.compilePage(rc);
            } catch (ServletException var19) {
               throw var19;
            } catch (CompilationException var20) {
               throw var20;
            } catch (RuntimeException var21) {
               throw var21;
            } catch (IOException var22) {
               throw var22;
            } catch (Exception var23) {
               throw new ServletException("JSP compilation of " + uri + " failed: " + var23.toString(), var23);
            } finally {
               thread.setContextClassLoader(cl);
            }
         }

         if (this.jsps.getPageCheckSecs() >= 0L) {
            this.getContext().getTagFileHelper().reloadIfNecessary(this.jsps.getPageCheckSecs());
         }

         if (needToCompile) {
            this.getNewClassLoader();
         }

         super.prepareServlet(rc);
      }
   }

   static void p(String s) {
      System.err.println("[JspStub]: " + s);
   }

   private static boolean initAlwaysCheckDiskProp() {
      return Boolean.getBoolean("weblogic.jsp.alwaysCheckDisk");
   }

   public static boolean isJSPClassStale(Class jspclass, StaleChecker sc) {
      try {
         Class[] scclass = new Class[]{StaleChecker.class};
         Method m = jspclass.getMethod("_staticIsStale", scclass);
         if (m == null) {
            return true;
         } else {
            int mods = m.getModifiers();
            if (Modifier.isStatic(mods) && Modifier.isPublic(mods)) {
               Class[] paramtypes = m.getParameterTypes();
               if (paramtypes != null && paramtypes.length == 1 && paramtypes[0] == StaleChecker.class) {
                  if (m.getReturnType() != Boolean.TYPE) {
                     return true;
                  } else {
                     Object[] args = new Object[]{sc};
                     Object ret = m.invoke((Object)null, args);
                     return !(ret instanceof Boolean) ? true : (Boolean)ret;
                  }
               } else {
                  return true;
               }
            } else {
               return true;
            }
         }
      } catch (Exception var8) {
         return true;
      }
   }

   private boolean isJspFromArchivedLib(Source source) {
      if (source instanceof WarSource) {
         WarSource libSrc = (WarSource)source;
         if (libSrc.isFromArchive() && libSrc.isFromLibrary()) {
            return true;
         }
      }

      return false;
   }

   protected abstract void compilePage(RequestCallback var1) throws Exception;
}
