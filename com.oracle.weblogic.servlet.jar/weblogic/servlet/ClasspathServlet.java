package weblogic.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.AppClassLoaderManager;
import weblogic.common.internal.VersionInfo;
import weblogic.core.base.api.ClassLoaderService;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.iiop.Utils;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.kernel.AuditableThread;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.StubGenerator;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.application.WarDetector;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ByteArraySource;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.io.FilenameEncoder;

public final class ClasspathServlet extends HttpServlet {
   private static final String SERVE_MANIFEST = "weblogic.classpathservlet.servemanifest";
   public static final String URI = "/classes";
   private static final DebugCategory debugging;
   private static final GenericClassLoader AUG_GCL;
   private static final String WLS_STUB_VERSION;
   private final AppClassLoaderManager appClassLoaderManager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
   private static final String SYSTEM_INFO_RESOURCE_NAME = "/weblogic/SystemInfo";
   private static final Source systemInfoSource;
   private static final String DESCRIPTOR_SOURCE_MARKER = "!";
   private static final boolean serveManifest;
   private final ClassFinder finder = new ClasspathClassFinder2();
   private ServletContext context;
   private String defaultFilename;
   private boolean inited = false;
   private final ArrayList restrictedFiles = new ArrayList();
   private final ArrayList allowedPackagesInSecureMode = new ArrayList();
   private boolean secureMode = false;
   private boolean strictCheck;
   private String restrictedDirectory;
   static final long serialVersionUID = -7374555030090527655L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.ClasspathServlet");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Request_Action_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   private static final boolean initServeManifest() {
      return Boolean.getBoolean("weblogic.classpathservlet.servemanifest");
   }

   public synchronized void init(ServletConfig config) throws ServletException {
      if (!this.inited) {
         super.init(config);
         this.context = config.getServletContext();
         this.defaultFilename = config.getInitParameter("defaultFilename");
         this.secureMode = WebServerRegistry.getInstance().getManagementProvider().getServerMBean().isClasspathServletSecureModeEnabled();
         this.fillAllowedPackageListInSecureMode();
         this.fillRestrictedFileList();
         this.inited = true;
      }
   }

   private void fillRestrictedFileList() {
      this.restrictedFiles.add(".bat");
      this.restrictedFiles.add(".cmd");
      this.restrictedFiles.add(".crt");
      this.restrictedFiles.add(".der");
      this.restrictedFiles.add(".ini");
      this.restrictedFiles.add(".jks");
      this.restrictedFiles.add(".log");
      this.restrictedFiles.add(".p12");
      this.restrictedFiles.add(".pem");
      this.restrictedFiles.add(".sh");
      this.restrictedFiles.add("boot.properties");
      this.restrictedFiles.add("cacerts");
      this.restrictedFiles.add("config.xml");
      this.restrictedFiles.add("filerealm.properties");
      this.restrictedFiles.add("license.bea");
      this.restrictedFiles.add("SerializedSystemIni.dat");
      this.restrictedFiles.add("weblogiclicense");
      this.restrictedFiles.add("weblogic.properties");

      try {
         if (Boolean.getBoolean("weblogic.servlet.ClasspathServlet.disableStrictCheck")) {
            this.strictCheck = false;
         } else {
            this.strictCheck = true;
         }
      } catch (SecurityException var2) {
         this.strictCheck = true;
      }

      this.restrictedDirectory = "config/mydomain";
   }

   private void fillAllowedPackageListInSecureMode() {
      if (this.secureMode) {
         this.allowedPackagesInSecureMode.add("/weblogic/");
         this.allowedPackagesInSecureMode.add("/javax/");
         this.allowedPackagesInSecureMode.add("/com/oracle/core/registryhelper/");
      }
   }

   private static ClassLoaderService getClassLoaderService() {
      ClassLoaderService cls = (ClassLoaderService)GlobalServiceLocator.getServiceLocator().getService(ClassLoaderService.class, "Application", new Annotation[0]);
      if (cls == null) {
         throw new RuntimeException("Implementation of weblogic.common.internal.ClassLoaderService with name of Application not found on classpath");
      } else {
         return cls;
      }
   }

   private static String parseResourceString(String resName) {
      String resourceName = FilenameEncoder.resolveRelativeURIPath(HttpParsing.unescape(resName).toLowerCase(Locale.ENGLISH).trim());
      if (resourceName.endsWith("/")) {
         resourceName = resourceName.substring(0, resourceName.length() - 1);
      }

      return resourceName;
   }

   private boolean isRestrictedResource(String resName) {
      String resourceName = parseResourceString(resName);
      if (resourceName.length() == 0) {
         return false;
      } else if (this.isSystemClasspathManifest(resourceName, serveManifest)) {
         return true;
      } else {
         Iterator itr;
         String fileName;
         if (this.secureMode) {
            if (!resourceName.endsWith(".class")) {
               return true;
            } else {
               itr = this.allowedPackagesInSecureMode.iterator();

               do {
                  if (!itr.hasNext()) {
                     if (isGeneratableClass(resName)) {
                        return false;
                     }

                     if (isGeneratedEJBClass(resName)) {
                        return false;
                     }

                     return true;
                  }

                  fileName = (String)itr.next();
               } while(!resourceName.startsWith(fileName));

               return false;
            }
         } else if (!resourceName.endsWith(".class") && !WarDetector.instance.suffixed(resourceName) && !resourceName.endsWith(".jar") && !resourceName.endsWith(".ear") && !resourceName.endsWith(".rar") && !resourceName.endsWith(".dtd") && !resourceName.endsWith("rtd.xml") && !resourceName.endsWith(".mf")) {
            if (this.strictCheck) {
               return true;
            } else {
               itr = this.restrictedFiles.iterator();

               do {
                  if (!itr.hasNext()) {
                     if (resourceName.indexOf(this.restrictedDirectory) != -1) {
                        if (debugging.isEnabled()) {
                           logDebug(this.restrictedDirectory + " is restricted. It cannot be served by ClasspathServlet.");
                        }

                        return true;
                     }

                     return false;
                  }

                  fileName = (String)itr.next();
               } while(!resourceName.endsWith(fileName.toLowerCase(Locale.ENGLISH)));

               if (debugging.isEnabled()) {
                  logDebug(fileName + " is restricted. It cannot be served by ClasspathServlet.");
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean isSystemClasspathManifest(String resourceName, boolean serveManifest) {
      return resourceName.toLowerCase(Locale.ENGLISH).equals("/meta-inf/manifest.mf") && !serveManifest;
   }

   public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
      this.doGet(req, res);
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[3];
            Object[] var10000 = var6.args;
            var10000[0] = this;
            var10000[1] = req;
            var10000[2] = res;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      label149: {
         label150: {
            label151: {
               try {
                  if (debugging.isEnabled()) {
                     logDebug("incoming request: " + req);
                     logDebug("path info:        " + req.getPathInfo());
                     logDebug("path trans:       " + req.getPathTranslated());
                     logDebug("parsed request:   " + parseResourceString(req.getPathInfo()));
                     logDebug("ctx path :        " + req.getContextPath());
                     logDebug("req uri :         " + req.getRequestURI());
                     logDebug("servlet path :    " + req.getServletPath());
                  }

                  Source resource = this.findResource(req);
                  if (resource == null) {
                     if (debugging.isEnabled()) {
                        logDebug("Couldn't find resource for URI: " + req.getRequestURI() + " sending 404");
                     }

                     res.sendError(404);
                     break label149;
                  }

                  long length = resource.length();
                  if (length == 0L) {
                     res.setContentLength(0);
                     break label150;
                  }

                  if (!isModified(req, res, resource)) {
                     res.setStatus(304);
                     break label151;
                  }

                  res.setContentType(this.context.getMimeType(resource.getURL().toString()));
                  res.setContentLength((int)length);
                  sendResource(resource, res);
               } catch (Throwable var8) {
                  if (var6 != null) {
                     var6.th = var8;
                     InstrumentationSupport.postProcess(var6);
                  }

                  throw var8;
               }

               if (var6 != null) {
                  InstrumentationSupport.postProcess(var6);
               }

               return;
            }

            if (var6 != null) {
               InstrumentationSupport.postProcess(var6);
            }

            return;
         }

         if (var6 != null) {
            InstrumentationSupport.postProcess(var6);
         }

         return;
      }

      if (var6 != null) {
         InstrumentationSupport.postProcess(var6);
      }

   }

   private static boolean isModified(HttpServletRequest req, HttpServletResponse res, Source src) throws IOException {
      if (req.getAttribute("javax.servlet.include.request_uri") != null) {
         return true;
      } else {
         long lastModified = src.lastModified();
         lastModified -= lastModified % 1000L;
         if (lastModified == 0L) {
            if (debugging.isEnabled()) {
               logDebug("Couldn't find resource for URI: " + req.getRequestURI() + " - sending 404");
            }

            res.sendError(404);
            return false;
         } else {
            long ifModifiedSince = -1L;

            try {
               ifModifiedSince = req.getDateHeader("If-Modified-Since");
            } catch (IllegalArgumentException var8) {
            }

            if (ifModifiedSince >= lastModified) {
               if (debugging.isEnabled()) {
                  logDebug("Resource: " + src.getURL() + " last modified: " + lastModified + " cache last modified: " + ifModifiedSince + " sending 302");
               }

               res.setStatus(304);
               return false;
            } else {
               res.setDateHeader("Last-Modified", lastModified);
               return true;
            }
         }
      }
   }

   private Source findResource(HttpServletRequest req) throws IOException {
      String resourceName = req.getPathInfo();
      if (resourceName == null) {
         return null;
      } else if (resourceName.toLowerCase(Locale.ENGLISH).indexOf("weblogiclicense") != -1) {
         return null;
      } else if ("/weblogic/SystemInfo".equals(resourceName)) {
         return systemInfoSource;
      } else if (this.isRestrictedResource(resourceName)) {
         return null;
      } else if (resourceName.endsWith("MANIFEST.MF") && serveManifest) {
         ByteArraySource baSource = new ByteArraySource(VersionInfo.theOne().getVersionInfoForNetworkClassLoader(), new URL(req.getRequestURL().toString()));
         return baSource;
      } else {
         int start = 0;
         if (resourceName.charAt(0) == '/') {
            start = 1;
         }

         int slashIndex = resourceName.indexOf("/", start);
         int seperatorIndex = resourceName.indexOf(64);
         String name;
         if (seperatorIndex != -1 && seperatorIndex < slashIndex) {
            name = resourceName.substring(start, seperatorIndex);
            String componentName = resourceName.substring(seperatorIndex + 1, slashIndex);
            String name = resourceName.substring(slashIndex + 1);
            if (debugging.isEnabled()) {
               logDebug("appName: " + name + ", componentName: " + componentName);
            }

            if (name.indexOf("!") > -1 && name.endsWith("RTD.xml")) {
               Source s = getRuntimeDescriptorSource(name);
               if (s != null) {
                  return s;
               }
            }

            return this.findSourceFromApplicationContainer(name, componentName, name);
         } else {
            name = resourceName.substring(start);
            Source s = null;
            if (name.indexOf("!") > -1 && name.endsWith("RTD.xml")) {
               s = getRuntimeDescriptorSource(name);
               if (s != null) {
                  return s;
               }
            }

            s = AUG_GCL.getClassFinder().getSource(name);
            if (s != null) {
               return s;
            } else {
               if (isGeneratableClass(name)) {
                  Source generatedClassSource;
                  if (JDBCWrapperFactory.isJDBCRMIWrapperClass(name)) {
                     generatedClassSource = getJDBCRMIWrapperClassSource(name);
                     if (generatedClassSource != null) {
                        return generatedClassSource;
                     }
                  } else {
                     generatedClassSource = this.getGeneratedClassSource(name, (String)null);
                     if (generatedClassSource != null) {
                        return generatedClassSource;
                     }
                  }
               }

               return this.sourceOrDefault(this.finder.getSource(name));
            }
         }
      }
   }

   private static boolean isGeneratableClass(String name) {
      return name.endsWith("_WLStub.class") || name.endsWith("_WLSkel.class") || Utils.isStubName(name) || isWrapperInterface(name);
   }

   private static boolean isGeneratedEJBClass(String name) {
      return name.endsWith("RIntf.class") && name.indexOf(95) != -1;
   }

   private static boolean isWrapperInterface(String name) {
      return name.endsWith("_RemoteInterface.class");
   }

   private Source findSourceFromApplicationContainer(String appName, String componentName, String name) throws IOException {
      GenericClassLoader gcl = this.appClassLoaderManager.findLoader(new weblogic.utils.classloaders.Annotation(appName));
      Source defaultSource;
      if (gcl != null) {
         defaultSource = gcl.getClassFinder().getSource(name);
         if (defaultSource != null) {
            return defaultSource;
         }
      }

      gcl = this.appClassLoaderManager.findLoader(new weblogic.utils.classloaders.Annotation(appName, componentName));
      if (gcl != null) {
         defaultSource = gcl.getClassFinder().getSource(name);
         if (defaultSource != null) {
            return defaultSource;
         }
      }

      defaultSource = this.sourceOrDefault(this.finder.getSource(name));
      if (defaultSource != null) {
         return defaultSource;
      } else if (isGeneratableClass(name)) {
         if (debugging.isEnabled()) {
            logDebug("\n \n This is a Generatable class. name :" + name + "\n \n");
         }

         if (isWrapperInterface(name)) {
            return AUG_GCL.getClassFinder().getSource(name);
         } else {
            return JDBCWrapperFactory.isJDBCRMIWrapperClass(name) ? getJDBCRMIWrapperClassSource(name) : this.getGeneratedClassSource(name, appName);
         }
      } else {
         return null;
      }
   }

   private Source getGeneratedClassSource(String inputName, String annotation) throws IOException {
      Source generatedClassSource = null;
      String name = inputName.replace('/', '.');
      int skelIndex = name.indexOf("_WLSkel.class");
      if (skelIndex != -1) {
         name = name.substring(0, skelIndex);
         Class implClass = loadClass(name, annotation);
         if (implClass != null && !implClass.isInterface()) {
            generatedClassSource = getSkelClassSource(implClass, inputName);
            return generatedClassSource;
         } else {
            return null;
         }
      } else {
         int stubIndex = name.indexOf(WLS_STUB_VERSION);
         if (stubIndex != -1) {
            name = name.substring(0, stubIndex);
            Class implClass = loadClass(name, annotation);
            if (implClass != null && !implClass.isInterface()) {
               generatedClassSource = getStubClassSource(implClass, inputName);
               return generatedClassSource;
            } else {
               return null;
            }
         } else if (Utils.isStubName(inputName)) {
            ClassLoader cclsave = Thread.currentThread().getContextClassLoader();

            try {
               Thread.currentThread().setContextClassLoader(this.getDefaultContextClassLoader());
               Class intfClass = getClassLoaderService().loadClass(Utils.getClassNameFromStubName(name), annotation);
               generatedClassSource = getIIOPStubClassSource(intfClass, inputName);
            } catch (ClassNotFoundException var12) {
               if (debugging.isEnabled()) {
                  logDebug("Unexpected error in ClasspathServlet: " + var12);
               }
            } finally {
               Thread.currentThread().setContextClassLoader(cclsave);
            }

            return generatedClassSource;
         } else {
            return generatedClassSource;
         }
      }
   }

   private ClassLoader getDefaultContextClassLoader() {
      return Thread.currentThread() instanceof AuditableThread ? ((AuditableThread)Thread.currentThread()).getDefaultContextClassLoader() : null;
   }

   private static Source getSkelClassSource(Class cls, String skelName) throws IOException {
      ServerHelper.getRuntimeDescriptor(cls);
      return AUG_GCL.getClassFinder().getSource(skelName);
   }

   private static Source getJDBCRMIWrapperClassSource(String wrapperName) {
      if (debugging.isEnabled()) {
         logDebug("wrapperName: " + wrapperName);
      }

      JDBCWrapperFactory.generateWrapperClass(wrapperName, false);
      Source src = AUG_GCL.getClassFinder().getSource(wrapperName);
      if (debugging.isEnabled()) {
         logDebug("src: " + src);
      }

      return src;
   }

   private static Source getStubClassSource(Class cls, String stubName) throws IOException {
      RuntimeDescriptor rtd = DescriptorHelper.getDescriptor(cls);
      if (debugging.isEnabled()) {
         logDebug("cls: " + cls);
         logDebug("stubName: " + stubName);
         logDebug("rtd: " + rtd);
      }

      (new StubGenerator(rtd, (String)null)).generateClass(AUG_GCL);
      Source src = AUG_GCL.getClassFinder().getSource(stubName);
      if (debugging.isEnabled()) {
         logDebug("src: " + src);
      }

      return src;
   }

   private static Source getIIOPStubClassSource(Class cls, String stubName) {
      if (debugging.isEnabled()) {
         logDebug("cls :" + cls);
         logDebug("stubName :" + stubName);
      }

      ClassLoader cl = cls.getClassLoader();
      if (!(cl instanceof GenericClassLoader)) {
         cl = AUG_GCL;
      }

      weblogic.corba.rmic.StubGenerator.createStubs(cls, (ClassLoader)cl);
      Source src = ((GenericClassLoader)cl).getClassFinder().getSource(stubName);
      if (debugging.isEnabled()) {
         logDebug("src: " + src);
      }

      return src;
   }

   private static Source getRuntimeDescriptorSource(String resourceName) {
      int start = 0;
      int descIndex = resourceName.indexOf("!", start);
      if (descIndex != -1) {
         int oid = Integer.parseInt(resourceName.substring(start, descIndex));

         try {
            return ServerHelper.getRuntimeDescriptorSource(oid);
         } catch (IOException var5) {
         }
      }

      return null;
   }

   private Source sourceOrDefault(Source source) {
      return source == null && this.defaultFilename != null ? this.finder.getSource(this.defaultFilename) : source;
   }

   private static void sendResource(Source resource, HttpServletResponse res) throws IOException {
      ServletOutputStream out = res.getOutputStream();
      InputStream is = resource.getInputStream();

      try {
         ((ServletOutputStreamImpl)out).writeStream(is);
      } finally {
         if (is != null) {
            is.close();
         }

      }

   }

   private static Source createSystemInfoSource() {
      String exportStr = "ssl_strength=domestic\n";

      try {
         URL url = new URL("file", "", ".txt");
         return new ByteArraySource(exportStr.getBytes("ASCII"), url);
      } catch (MalformedURLException var2) {
         throw new AssertionError(var2);
      } catch (UnsupportedEncodingException var3) {
         throw new AssertionError(var3);
      }
   }

   private static void logDebug(String msg) {
      Debug.say(msg);
   }

   private static Class loadClass(String className, String annotation) {
      try {
         return getClassLoaderService().loadClass(className, annotation);
      } catch (ClassNotFoundException var4) {
         try {
            return AUG_GCL.loadClass(className);
         } catch (ClassNotFoundException var3) {
            if (debugging.isEnabled()) {
               logDebug("Unexpected error in ClasspathServlet: " + var3);
            }

            return null;
         }
      }
   }

   static {
      _WLDF$INST_FLD_Servlet_Request_Action_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Request_Action_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ClasspathServlet.java", "weblogic.servlet.ClasspathServlet", "doGet", "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V", 282, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Request_Action_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Request_Action_Around_Medium};
      debugging = Debug.getCategory("weblogic.ClasspathServlet");
      AUG_GCL = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
      WLS_STUB_VERSION = RMIEnvironment.getEnvironment().getStubVersion();
      systemInfoSource = createSystemInfoSource();
      serveManifest = initServeManifest();
   }
}
