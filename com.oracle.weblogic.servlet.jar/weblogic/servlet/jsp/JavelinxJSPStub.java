package weblogic.servlet.jsp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.descriptor.DescriptorBean;
import weblogic.html.EntityEscape;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.jsp.compiler.DiagnosticList;
import weblogic.jsp.compiler.IApplication;
import weblogic.jsp.compiler.ICPL;
import weblogic.jsp.compiler.IJavelin;
import weblogic.jsp.compiler.IJavelinFile;
import weblogic.jsp.compiler.Diagnostic.Severity;
import weblogic.jsp.compiler.client.ClientUtils;
import weblogic.jsp.compiler.jsp.IJspCompilerOptions;
import weblogic.jsp.internal.html.HtmlLanguageX;
import weblogic.jsp.internal.jsp.JspLanguageX;
import weblogic.jsp.internal.jsp.options.JspCompilerOptionsX;
import weblogic.jsp.internal.jsp.utils.JavaTransformUtils;
import weblogic.jsp.languages.java.IJavaLanguage;
import weblogic.jsp.wlw.filesystem.IFile;
import weblogic.jsp.wlw.util.filesystem.FS;
import weblogic.servlet.internal.RequestCallback;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.Source;

public class JavelinxJSPStub extends JspStub {
   private static final boolean verboseload = Boolean.getBoolean("weblogic.jsp.javelin.verboseload");

   public JavelinxJSPStub(String name, String className, WebAppServletContext sci, JspConfig jsps) {
      super(name, className, sci, jsps);
   }

   protected URI getFileToCompileURI(String uri) throws Exception {
      Source source = this.getContext().getResourceAsSource(uri);
      if (source == null) {
         throw new JspFileNotFoundException("no resource '" + uri + "' in servlet context root '" + this.getContext().getDocroot() + "'");
      } else {
         return getAbsoluteJspURI(source, uri);
      }
   }

   protected URI[] prepareDocRoots(String uri) throws IOException {
      List docRoots = new ArrayList();
      String[] sourcePaths = this.getSourcePaths(uri);
      if (sourcePaths != null && sourcePaths.length > 0) {
         IFile[] paths = new IFile[sourcePaths.length];

         for(int i = 0; i < sourcePaths.length; ++i) {
            File srcPath = (new File(sourcePaths[i])).getCanonicalFile();
            paths[i] = FS.getIFile(srcPath);
            if (paths[i] != null) {
               docRoots.add(paths[i].getURI());
            }
         }
      } else {
         String sourcePath = this.getContext().getDocroot();
         IFile path = FS.getIFile(new File(sourcePath));
         if (path != null) {
            docRoots.add(path.getURI());
         }
      }

      return (URI[])docRoots.toArray(new URI[docRoots.size()]);
   }

   protected String[] getSourcePaths(String uri) {
      Source source = this.getContext().getResourceAsSource(uri);
      if (source != null) {
         String protocol = source.getURL().getProtocol();
         if ("file".equals(protocol) || "zip".equals(protocol)) {
            String sPaths = this.getContext().getResourceFinder(uri).getClassPath();
            return StringUtils.splitCompletely(sPaths, File.pathSeparator);
         }
      }

      return null;
   }

   protected void compilePage(final RequestCallback rc) throws Exception {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               JavelinxJSPStub.this.compilePage0(rc);
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         throw var3.getException();
      }
   }

   private void compilePage0(RequestCallback rc) throws Exception {
      String uri = this.getRequestURI(rc);
      URI fileToCompileURI = this.getFileToCompileURI(uri);
      URI[] docRoots = this.prepareDocRoots(uri);
      WebAppBean descriptorBean = this.getContext().getWebAppModule().getWebAppBean();
      String version = ((DescriptorBean)descriptorBean).getDescriptor().getOriginalVersionInfo();
      IJavelin javelin = this.prepareJavelin(version);
      JspCLLManager.JspClientContext clientContext = new JspCLLManager.JspClientContext();
      IApplication app = javelin.createApplication("JSPStubx", clientContext);
      ICPL cll = app.createCPL("CPL");

      try {
         IJspCompilerOptions opts = JspCompilerOptionsX.createFeature(cll);
         if (this.getContext().getConfigManager().useDefaultEncoding() && this.jsps.getEncoding() == null) {
            this.jsps.setEncoding(this.getContext().getConfigManager().getDefaultEncoding());
         }

         JspCLLManager.setUpCompilerOptions(opts, this.jsps);
         JspCLLManager.makeConfigFeature(descriptorBean, cll, this.getContext().getJSPManager().getJspExtensions());
         this.prepareCPLClasspath(cll);
         cll.setClassLoader(this.getContext().getTagFileHelper().getCompileTimeTagFileClassLoader());
         JspCLLManager.setWebAppProjectFeature(cll, this.getContext().getContextPath(), docRoots, version, JspCLLManager.getVirtualRootsInfo(this.getContext().getWebAppModule().getWlWebAppBean()), this.resourceProvider, this.getContext().getWarInstance().getSplitDirectoryJars());
         Set filesToBuild = new HashSet();
         IFile ifileToCompile = FS.getIFile(fileToCompileURI);
         filesToBuild.add(ifileToCompile);
         cll.addSourceFiles(filesToBuild);
         Set filesToBuild = cll.getSourceFiles();
         Set builtFiles = new HashSet();
         Map props = this.prepareBuildOptions();
         IFile workingdir = FS.getIFile(new File(this.jsps.getWorkingDir()));

         while(filesToBuild.size() > 0) {
            builtFiles.addAll(filesToBuild);
            ClientUtils.get(app).build(cll, filesToBuild, workingdir, props);
            filesToBuild.addAll(cll.getSourceFiles());
            filesToBuild.removeAll(builtFiles);
         }

         ClientUtils.get(app).buildPrototypes(cll, workingdir, props);
         this.reportCompilationErrorIfNeccessary(cll, clientContext, rc, uri, builtFiles);
      } finally {
         ClientUtils.get(app).closeAndWait();
      }
   }

   private void reportCompilationErrorIfNeccessary(ICPL cll, JspCLLManager.JspClientContext clientContext, RequestCallback rc, String uri, Set builtFiles) throws IOException {
      StringBuilder exStr = this.getInternalErrors(clientContext);
      StringBuffer plainTextMsg = null;
      StringBuffer htmlMsg = null;
      Map filesWithDiags = cll.getFilesWithDiagnostics();
      if (exStr != null || filesWithDiags != null && filesWithDiags.size() > 0) {
         plainTextMsg = new StringBuffer();
         htmlMsg = new StringBuffer();
         boolean foundError = false;
         if (exStr != null) {
            this.appendErrorMessageHeader(htmlMsg, uri);
            plainTextMsg.append(exStr);
            foundError = true;
         }

         if (filesWithDiags != null && filesWithDiags.size() > 0) {
            Iterator iter = filesWithDiags.entrySet().iterator();

            while(iter.hasNext()) {
               Map.Entry entry = (Map.Entry)iter.next();
               IJavelinFile file = (IJavelinFile)entry.getKey();
               if (!file.isBinary() && entry.getValue() != null && builtFiles.contains(file)) {
                  DiagnosticList diagnostics = (DiagnosticList)entry.getValue();
                  List errors = diagnostics.get(true, Severity.ERROR);
                  int numErrors = errors.size();
                  if (numErrors > 0) {
                     if (!foundError) {
                        foundError = true;
                        this.appendErrorMessageHeader(htmlMsg, uri);
                     }

                     StringWriter errs = new StringWriter();
                     PrintWriter outWriter = new PrintWriter(errs);
                     diagnostics.print(true, JavaTransformUtils.isDiagnosticWithAbsolutePath ? file.getIFile().getDisplayPath() : file.getName(), outWriter, true, Severity.ERROR);
                     plainTextMsg.append(errs.toString());
                  }
               }
            }
         }

         if (foundError) {
            String msg = plainTextMsg.toString();
            EntityEscape.escapeString(msg, htmlMsg);
            htmlMsg.append("</pre>\n</body></html>\n");
            String html = htmlMsg.toString();
            this.getContext().log(msg);
            CompilationException compileFailure = new CompilationException("Failed to compile JSP " + uri, uri, msg, html, (Throwable)null);
            if (this.jsps.isVerbose() && !this.isErrorPageDefined(500) && !this.isErrorPageDefined(compileFailure)) {
               rc.reportJSPCompilationFailure(msg, html);
            }

            this.destroy();
            throw compileFailure;
         }
      }

   }

   private Map prepareBuildOptions() {
      Map props = new HashMap();
      if (this.jsps.isKeepGenerated()) {
         props.put("-keepGenerated", "true");
         props.put("-s", (new File(this.jsps.getWorkingDir())).getAbsolutePath());
      }

      return props;
   }

   private StringBuilder getInternalErrors(JspCLLManager.JspClientContext clientContext) {
      StringBuilder exStr = null;
      if (clientContext.hasException()) {
         exStr = new StringBuilder("Exception occurred while processing '");
         exStr.append(clientContext.getPath());
         exStr.append("'");
         Throwable ex = clientContext.getException();
         if (ex != null) {
            exStr.append(StackTraceUtils.throwable2StackTrace(ex));
         }
      }

      return exStr;
   }

   private void prepareCPLClasspath(ICPL cll) {
      List roots = new ArrayList();
      this.addPaths(roots, this.getContext().getFullClasspath());
      cll.initializeBinaryPaths(roots);
   }

   private IJavelin prepareJavelin(String version) {
      IJavelin javelin = this.createJaveLin(verboseload, false);
      javelin.addLanguage(new JspLanguageX(javelin));
      javelin.addLanguage(new HtmlLanguageX(javelin));
      float webVersion = 0.0F;
      if (version != null) {
         try {
            webVersion = Float.parseFloat(version);
         } catch (NumberFormatException var5) {
         }
      }

      IJavaLanguage javaLanguage = (IJavaLanguage)javelin.getLanguage("java");
      if (this.jsps.isDebugEnabled()) {
         javaLanguage.setEmitDebug(7);
      }

      if ((double)webVersion < 2.4 && javaLanguage != null) {
         javaLanguage.setJSPBackCompat(true);
      }

      return javelin;
   }

   protected IJavelin createJaveLin(boolean verboseload, boolean autoload) {
      return ClientUtils.createCommandLineJavelin(verboseload, false, false);
   }

   private void appendErrorMessageHeader(StringBuffer messageBuffer, String uri) {
      messageBuffer.append("<html>\n<head>\n<title>Weblogic JSP compilation error</title>\n</head>\n<body>\n");
      messageBuffer.append("<b>Compilation of JSP File '");
      messageBuffer.append(uri);
      messageBuffer.append("' <font color=#FF0000>failed</font>:</b><HR><pre>\n");
   }

   private boolean isErrorPageDefined(int errorCode) {
      return this.getContext().getErrorManager().getErrorLocation(String.valueOf(errorCode)) != null;
   }

   private boolean isErrorPageDefined(Throwable e) {
      return this.getContext().getErrorManager().getExceptionLocation(e) != null;
   }

   private void addPaths(List roots, String path) {
      String[] parts = StringUtils.splitCompletely(path, File.pathSeparator);

      for(int i = 0; i < parts.length; ++i) {
         File file = new File(parts[i]);
         if (file.exists()) {
            roots.add(FS.getIFile(file));
         }
      }

   }

   static URI getAbsoluteJspURI(Source source, String uri) throws CompilationException {
      URL jspURL = source.getURL();
      String protocol = jspURL.getProtocol();
      if ("file".equals(protocol)) {
         return (new File(jspURL.getPath())).toURI();
      } else {
         String url;
         if ("zip".equals(protocol)) {
            url = jspURL.toString();
            int delim = url.indexOf("!/");
            String ssp;
            if (delim == -1) {
               ssp = "JSP source=" + url + " can't be loaded";
               throw new CompilationException(ssp, uri, "", "", (Throwable)null);
            } else {
               ssp = url.substring("zip:".length());
               if (ssp.charAt(0) != '/') {
                  ssp = "/" + ssp;
               }

               try {
                  return new URI("war:file:" + ssp);
               } catch (URISyntaxException var9) {
                  String errMsg = "JSP source URI syntax error: URI: " + ssp + ", error: " + var9.getMessage();
                  throw new CompilationException(errMsg, uri, "", "", (Throwable)null);
               }
            }
         } else {
            url = "JSP source in an unsupported form: URL=" + source.getCodeSourceURL() + " Source class=" + source.getClass().getName();
            throw new CompilationException(url, uri, "", "", (Throwable)null);
         }
      }
   }

   static {
      FS.initializeForJSP();
   }
}
