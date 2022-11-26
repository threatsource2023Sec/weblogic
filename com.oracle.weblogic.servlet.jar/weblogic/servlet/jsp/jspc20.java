package weblogic.servlet.jsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.version;
import weblogic.descriptor.DescriptorException;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.JSPServlet;
import weblogic.servlet.internal.CharsetMap;
import weblogic.servlet.internal.JSPManager;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.utils.BadOptionException;
import weblogic.utils.FileUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.enumerations.ResourceEnumerator;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class jspc20 extends Tool implements StaleChecker, JspcInvoker.IJspc {
   private static final String WEBLOGIC_EXTENSION_DIRS = "weblogic.ext.dirs";
   private static final boolean debug = false;
   private boolean verbose;
   private static final int DEFAULT_MAX_FILES = 2000;
   private static String DEFAULT_TARGET_DIR;
   private boolean needToCloseResourceFinder = false;
   private boolean needToCloseClassFinder = false;
   private ClassFinder resourceFinder;
   private ClassFinder classFinder;
   private GenericClassLoader classLoader;
   private File outputDir;
   private WebAppBean webBean;
   private WeblogicWebAppBean wlWebBean;
   private String[] splitDirectoryJars;
   private String[] args;
   private boolean isStrictStaleCheck;
   private static final boolean useUniqueJSPCL;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 6480502750414915117L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.jsp.jspc20");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public jspc20(String[] args) {
      super(args);
      this.opts.setUsageArgs("<jsp files>...");
      this.opts.addOption("webapp", "dir", "Directory to be considered as the document root for resolving relative files.");
      this.opts.addAlias("docroot", "webapp");
      this.opts.addFlag("verboseJspc", "whether JSP Compiler runs in verbose mode (default is false)");
      this.opts.addAdvancedOption("compileFlags", "flags", "Use this to specify non-standard options to the java compiler (those other than -g -nowarn, etc).  E.g, -compileFlags \"+E -otherOpt +P\"");
      this.opts.addAdvancedFlag("verboseJavac", "whether to invoke java compiler with -verbose (default false, use -verbose also)");
      this.opts.addAdvancedFlag("linenumbers", "add jsp line numbers to generated class files to aid debugging");
      this.opts.addAlias("debug", "linenumbers");
      this.opts.addAdvancedFlag("k", "continue compiling files, even when some fail");
      this.opts.addAdvancedFlag("noTryBlocks", "disable use of try/finally blocks for BodyTag extensions. Improves code for deeply-nested tags.");
      this.opts.addAdvancedFlag("noImplicitTlds", "disable search and registration of implicit Tlds. ");
      this.opts.addAdvancedFlag("noPrintNulls", "show \"null\" in jsp expressions as \"\" ");
      this.opts.addAlias("docRoot", "webapp");
      this.opts.addAdvancedFlag("backwardcompatible", "Backward compatibility option.");
      this.opts.addAdvancedOption("charsetMap", "charsetMapString", "specify mapping of IANA or unofficial charset names used in JSP contentType directives to java charset names.  E.g., '-charsetMap x-sjis=Shift_JIS,x-big5=Big5'  The most common mappings are built-in to jspc.  Use this option only if a desired charset mapping isn't recognized.");
      this.opts.addAdvancedOption("maxfiles", "int", "Maximum number of generated java files to be compiled at one time.");
      this.opts.addAdvancedFlag("skipJavac", "Skip compilation of generated servlet code.");
      this.opts.addAdvancedFlag("forceGeneration", "Force generation of JSP classes. Without this flag, the classes may not be generated if it is determined to be unnecessary.");
      this.opts.addAdvancedFlag("compressHtmlTemplate", "Remove additional white spaces in html template. Without this flag, html template will be output as is.");
      this.opts.addAdvancedFlag("optimizeJavaExpression", "Optimize string concatenation in Java expression. Without this flag, Java expression will be output as is.");
      this.opts.addAdvancedFlag("useByteBuffer", "Generate source codes of jsp files to use NIO ByteBuffer for static contents.");
      this.opts.addAdvancedFlag("strictJspDocumentValidation", "Validate JSP document's format strictly with XML Parser.");
      this.opts.addFlag("moreVerbose", "yet some more verbose");
      this.opts.markPrivate("moreVerbose");
      this.opts.addFlag("compileAll", "compile all .jsp files below the directory specified by -webapp");
      this.opts.markPrivate("compileAll");
      this.opts.addFlag("depend", "only compile files that are out of date (source .jsp file(s) newer than generated .class file)");
      this.opts.markPrivate("depend");
      new CompilerInvoker(this.opts);
      this.opts.addAdvancedOption("encoding", "options", "Valid args are \"default\" to use the default character encoding of JDK, or named character encoding, like \"8859_1\". If the -encoding flag is not present,  an array of bytes is used.");
      this.opts.addAdvancedOption("compilerSupportsEncoding", "options", "Set to \"true\" if the java compiler supports the -encoding flag, or \"false\" if it does not. ");
      this.opts.addAdvancedOption("package", "packageName", "The package into which the .jsp files should be placed");
      this.opts.addAdvancedOption("superclass", "superclass", "The class name of the superclass which this servlet should extend.");
      this.opts.markPrivate("nowrite");
      this.opts.markPrivate("nowarn");
      this.opts.markPrivate("verbose");
      this.opts.markPrivate("deprecation");
      this.opts.markPrivate("commentary");
      this.opts.markPrivate("O");
      this.opts.markPrivate("J");
      this.opts.markPrivate("normi");
      this.opts.markPrivate("g");
      this.opts.markPrivate("compiler");
      this.opts.markPrivate("compilerclass");
      this.opts.markPrivate("compilerSupportsEncoding");
      this.opts.markPrivate("verboseJavac");
      this.opts.markPrivate("compileFlags");
      this.opts.markPrivate("skipJavac");
      this.opts.markPrivate("source");
      this.opts.markPrivate("linenumbers");
      this.opts.markPrivate("noImplicitTlds");
      this.opts.markPrivate("noTryBlocks");
      this.opts.addOption("jsps", "jsps", "Comma-separated list of jsp files, specifies jsps that need to be compiled. All jsps of the app will be compiled if the option is not passed into.");
      this.opts.markPrivate("jsps");
      this.opts.addFlag("compileAllTagFiles", "Compile all JSP tag files");
      this.opts.markPrivate("compileAllTagFiles");
      this.opts.markPrivate("useByteBuffer");
      this.args = args;
   }

   public void prepare() {
      this.setRequireExtraArgs(false);
   }

   public void runJspc(GenericClassLoader classloader, ClassFinder resourceFinder, VirtualJarFile vjf) throws Exception {
      this.prepare();
      this.opts.grok(this.args);
      this.resourceFinder = resourceFinder;
      this.init(classloader, vjf);
      if (!$assertionsDisabled && !vjf.isDirectory()) {
         throw new AssertionError();
      } else {
         try {
            boolean compileAll = true;
            String[] jsps = new String[0];
            if (this.opts.hasOption("jsps") && !"*".equals(this.opts.getOption("jsps")) && !"\"*\"".equals(this.opts.getOption("jsps")) && !"'*'".equals(this.opts.getOption("jsps"))) {
               jsps = this.opts.getOption("jsps").split(",");
               compileAll = false;
            }

            this.runBodyInternal(vjf, compileAll, jsps);
         } finally {
            try {
               if (vjf != null) {
                  vjf.close();
               }

               this.closeFinders();
            } catch (IOException var11) {
            }

         }

      }
   }

   private void closeFinders() {
      if (this.needToCloseResourceFinder && this.resourceFinder != null) {
         this.resourceFinder.close();
      }

      if (this.needToCloseClassFinder && this.classFinder != null) {
         this.classFinder.close();
      }

   }

   public void runBody() throws Exception {
      this.validateToolInput(this.opts);
      long t = 0L;
      boolean moreVerbose = this.opts.getBooleanOption("moreVerbose");
      if (moreVerbose) {
         t = System.currentTimeMillis();
      }

      String docRootStr = this.opts.getOption("webapp", ".").replace('/', File.separatorChar);
      File docRoot = new File(docRootStr);
      VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(docRoot);
      boolean isExtracted = !vjf.isDirectory();
      boolean jarIt = isExtracted && this.opts.getOption("d") == null;
      vjf = this.getVirtualJarFile(vjf);
      this.init((GenericClassLoader)null, vjf);
      this.initDescriptors(vjf);

      try {
         this.runBodyInternal(vjf, false, this.opts.args());
      } finally {
         try {
            if (vjf != null) {
               vjf.close();
            }

            this.closeFinders();
         } catch (IOException var15) {
         }

         if (moreVerbose) {
            say("Total time elapsed : " + (System.currentTimeMillis() - t));
         }

      }

      if (isExtracted) {
         String tmp = this.opts.getOption("webapp");
         File tmpDir = new File(tmp);
         if (jarIt) {
            say(" Creating jar file " + docRootStr + " w/ compiled jsps");
            JarFileUtils.createJarFileFromDirectory(docRootStr, tmpDir);
         }

         FileUtils.remove(tmpDir);
      }

   }

   public void setWebBean(WebAppBean bean) {
      this.webBean = bean;
   }

   public void setWlWebBean(WeblogicWebAppBean bean) {
      this.wlWebBean = bean;
   }

   public void setSplitDirectoryJars(String[] splitDirectoryJars) {
      this.splitDirectoryJars = splitDirectoryJars;
   }

   private void runBodyInternal(VirtualJarFile vjf, boolean compileAllJsps, String[] filez) throws Exception {
      JspConfig config = this.mergeOptions(this.opts);
      Set patternSet = null;
      if (this.webBean != null) {
         patternSet = JSPManager.getJspConfigPatterns(this.webBean.getJspConfigs());
      }

      this.verbose = this.opts.getBooleanOption("verboseJspc", false);
      this.isStrictStaleCheck = config.isStrictStaleCheck();
      boolean keepgenerated = this.opts.getBooleanOption("keepgenerated", false);
      boolean dashK = this.opts.getBooleanOption("k", false);
      boolean compileAll = compileAllJsps;
      if (!compileAllJsps) {
         compileAll = this.opts.getOption("webapp") != null && filez.length == 0 || this.opts.getBooleanOption("compileAll");
      }

      boolean compileAllTagFiles = this.opts.getBooleanOption("compileAllTagFiles", false);
      File[] roots = vjf.getRootFiles();
      if (!$assertionsDisabled && roots == null) {
         throw new AssertionError();
      } else {
         List jspfiles = null;
         String uri;
         if (compileAll) {
            if (this.verbose) {
               String startString = this.opts.getBooleanOption("compileAll") ? "-compileAll specified, " : " -webapp specified, ";
               say(startString + "searching " + this.opts.getOption("webapp", ".") + " for JSPs");
            }

            List list = new ArrayList();
            if (patternSet == null) {
               patternSet = new HashSet();
            }

            if (compileAllTagFiles) {
               ((Set)patternSet).add("/WEB-INF/tags/*.tag");
               ((Set)patternSet).add("/WEB-INF/tags/*.tagx");
            }

            ((Set)patternSet).add("*.jsp");
            ((Set)patternSet).add("*.jspx");
            String[] match = (String[])((String[])((Set)patternSet).toArray(new String[0]));

            for(int i = 0; i < roots.length; ++i) {
               if (!roots[i].exists()) {
                  if (this.verbose) {
                     say("Ignoring non-existent web-root " + roots[i].getAbsolutePath());
                  }
               } else {
                  ResourceEnumerator re = ResourceEnumerator.makeInstance(roots[i], new String[0], match);
                  uri = null;

                  while((uri = re.getNextURI()) != null) {
                     list.add(uri);
                  }

                  re.close();
               }
            }

            jspfiles = list;
         } else if (filez != null && filez.length > 0) {
            jspfiles = new ArrayList(filez.length);

            for(int i = 0; i < filez.length; ++i) {
               File f = new File(filez[i].replace('/', File.separatorChar));
               jspfiles.add(determineURI(roots[0], f, filez[i]));
            }
         }

         if (jspfiles != null && jspfiles.size() != 0) {
            boolean forceGeneration = this.opts.getBooleanOption("forceGeneration", false);
            boolean depend = this.opts.getBooleanOption("depend", false);
            ClassFinder cf = this.classLoader.getClassFinder();
            GenericClassLoader jspClassLoader = this.classLoader;
            if (!forceGeneration || depend) {
               uri = this.opts.getOption("package", "jsp_servlet");
               Iterator i = jspfiles.iterator();

               while(i.hasNext()) {
                  String uri = (String)i.next();
                  if (useUniqueJSPCL) {
                     String cName = JSPServlet.uri2classname(uri, uri);
                     jspClassLoader = JspcInvoker.getJspClassLoader(this.classLoader, cf, cName);
                  }

                  if (!this.classIsStale(uri, uri, jspClassLoader)) {
                     i.remove();
                     if (this.verbose) {
                        say("skipping " + uri + ", it is up to date.");
                     }
                  }
               }
            }

            JspCompilerContext compilationContext = new JspCompilerContext("[jspc]");
            compilationContext.setVerbose(this.verbose);
            compilationContext.setClassLoader(jspClassLoader);
            compilationContext.setResourceFinder(this.resourceFinder);
            compilationContext.setJspConfig(config);
            String resourceCP = this.resourceFinder.getClassPath();
            String[] resourcePaths = resourceCP.split(File.pathSeparator);
            int size;
            if (resourcePaths != null) {
               List l = new ArrayList(resourcePaths.length);

               for(size = 0; size < resourcePaths.length; ++size) {
                  File f = new File(resourcePaths[size]);
                  if (f.exists()) {
                     l.add(resourcePaths[size]);
                  }
               }

               compilationContext.setSourcePaths((String[])((String[])l.toArray(new String[0])));
            }

            compilationContext.setWebAppBean(this.webBean);
            compilationContext.setWlWebAppBean(this.wlWebBean);
            compilationContext.setSplitDirectoryJars(this.splitDirectoryJars);
            int maxFiles = this.opts.getIntegerOption("maxfiles", 2000);
            size = jspfiles.size();
            if (size < maxFiles) {
               maxFiles = size;
            }

            boolean hasErrors = false;
            int i = 0;

            for(int j = maxFiles; i < size; i += j) {
               int incr = Math.min(j, size - i);
               int to = i + incr;
               List sublist = jspfiles.subList(i, to);
               boolean success = false;

               try {
                  success = JspCLLManager.compileJsps(sublist, compilationContext);
               } catch (CompilationException var30) {
                  if (!dashK) {
                     throw new ToolFailureException("jspc failed with errors :" + var30);
                  }
               } catch (Exception var31) {
                  var31.printStackTrace();
                  throw new ToolFailureException("Unexpected exception while compiling jsps :" + var31);
               }

               hasErrors |= !success;
            }

            if (!dashK && hasErrors) {
               throw new ToolFailureException("[jspc] compiler failed with errors");
            }
         } else {
            say("No jsp files found, nothing to do");
         }
      }
   }

   private void init(GenericClassLoader classloader, VirtualJarFile vjf) {
      if (this.resourceFinder == null) {
         this.needToCloseResourceFinder = true;
         this.resourceFinder = buildResourceFinder(vjf);
      }

      this.classLoader = classloader;
      this.outputDir = new File(this.opts.getOption("d", "."));
      this.outputDir.mkdirs();
      if (this.classLoader == null) {
         this.needToCloseClassFinder = true;
         this.classFinder = buildClassFinder(this.opts, this.outputDir, vjf);
         this.classLoader = new GenericClassLoader(this.classFinder);
      } else {
         String extCP = buildExtClassPath();
         StringBuilder cpath = new StringBuilder();
         if (extCP != null) {
            cpath.append(extCP);
            cpath.append(File.pathSeparator);
         }

         cpath.append(this.outputDir.getAbsolutePath());
         this.classLoader.addClassFinder(new ClasspathClassFinder2(cpath.toString()));
      }

   }

   private void initDescriptors(VirtualJarFile vjf) throws ToolFailureException {
      boolean loadWebXml = vjf.getEntry("WEB-INF/web.xml") != null;
      if (!loadWebXml) {
         say("warning: expected file /WEB-INF/web.xml  not found, tag libraries cannot be resolved.");
      }

      boolean loadWebLogicXml = vjf.getEntry("WEB-INF/weblogic.xml") != null;
      WebAppDescriptor wad = new WebAppDescriptor(vjf);
      if (loadWebXml && this.webBean == null) {
         try {
            this.webBean = wad.getWebAppBean();
         } catch (XMLStreamException var9) {
         } catch (DescriptorException var10) {
            String message = null;
            if (var10.getCause() != null) {
               message = var10.getCause().getMessage();
            }

            say("Warning: Error occured while parsing WEB-INF/web.xml, tag libraries will not be resolved " + (message == null ? "" : message));
         } catch (IOException var11) {
         }
      }

      if (loadWebLogicXml && this.wlWebBean == null) {
         try {
            this.wlWebBean = wad.getWeblogicWebAppBean();
         } catch (XMLStreamException var7) {
            throw new ToolFailureException("Error occured while parsing /WEB-INF/weblogic.xml : " + var7);
         } catch (IOException var8) {
         }
      }

   }

   public boolean isResourceStale(String resource, long sinceWhen, String releaseBuildVersion, String var5) {
      LocalHolder var10;
      if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var10.argsCapture) {
            var10.args = new Object[5];
            Object[] var10000 = var10.args;
            var10000[0] = this;
            var10000[1] = resource;
            var10000[2] = InstrumentationSupport.convertToObject(sinceWhen);
            var10000[3] = releaseBuildVersion;
            var10000[4] = var5;
         }

         InstrumentationSupport.createDynamicJoinPoint(var10);
         InstrumentationSupport.preProcess(var10);
         var10.resetPostBegin();
      }

      boolean var13;
      label175: {
         label176: {
            label177: {
               try {
                  boolean noVersionCheck = Boolean.getBoolean("weblogic.jspc.skipVersionCheck");
                  if (!noVersionCheck && !version.getReleaseBuildVersion().equals(releaseBuildVersion)) {
                     if (this.verbose) {
                        say("Resource '" + resource + "' is being considered new, its server version '" + releaseBuildVersion + "' does not match the current server version '" + version.getReleaseBuildVersion() + "'");
                     }

                     var13 = true;
                     break label175;
                  }

                  if (noVersionCheck && this.verbose) {
                     say("found System property 'weblogic.jspc.skipVersionCheck=true', skipping server version check ");
                  }

                  Source s = this.resourceFinder.getSource(resource);
                  if (s == null) {
                     var13 = true;
                     break label176;
                  }

                  long builtTime = s.lastModified();
                  if (!this.isStrictStaleCheck) {
                     say("using none strick stale check");
                     var13 = sinceWhen != builtTime + 2000L;
                     break label177;
                  }

                  var13 = sinceWhen < builtTime;
               } catch (Throwable var12) {
                  if (var10 != null) {
                     var10.th = var12;
                     var10.ret = InstrumentationSupport.convertToObject(false);
                     InstrumentationSupport.createDynamicJoinPoint(var10);
                     InstrumentationSupport.postProcess(var10);
                  }

                  throw var12;
               }

               if (var10 != null) {
                  var10.ret = InstrumentationSupport.convertToObject(var13);
                  InstrumentationSupport.createDynamicJoinPoint(var10);
                  InstrumentationSupport.postProcess(var10);
               }

               return var13;
            }

            if (var10 != null) {
               var10.ret = InstrumentationSupport.convertToObject(var13);
               InstrumentationSupport.createDynamicJoinPoint(var10);
               InstrumentationSupport.postProcess(var10);
            }

            return var13;
         }

         if (var10 != null) {
            var10.ret = InstrumentationSupport.convertToObject(var13);
            InstrumentationSupport.createDynamicJoinPoint(var10);
            InstrumentationSupport.postProcess(var10);
         }

         return var13;
      }

      if (var10 != null) {
         var10.ret = InstrumentationSupport.convertToObject(var13);
         InstrumentationSupport.createDynamicJoinPoint(var10);
         InstrumentationSupport.postProcess(var10);
      }

      return var13;
   }

   private VirtualJarFile getVirtualJarFile(VirtualJarFile vjf) throws IOException {
      if (vjf.isDirectory()) {
         return vjf;
      } else {
         File tmpDir = JspcInvoker.getJspcTempDir();
         JarFileUtils.extract(vjf, tmpDir);

         try {
            this.opts.setOption("webapp", tmpDir.getAbsolutePath());
         } catch (BadOptionException var6) {
            if (!$assertionsDisabled) {
               throw new AssertionError();
            }
         }

         File target = new File(tmpDir, DEFAULT_TARGET_DIR);
         if (this.opts.getOption("d") == null) {
            try {
               this.opts.setOption("d", target.getAbsolutePath());
            } catch (BadOptionException var5) {
               if (!$assertionsDisabled) {
                  throw new AssertionError();
               }
            }
         }

         vjf.close();
         return VirtualJarFactory.createVirtualJar(tmpDir);
      }
   }

   private JspConfig mergeOptions(Getopt2 opts) {
      Map descriptorMap = null;
      if (this.wlWebBean == null) {
         descriptorMap = JspcInvoker.makeDefaultDescriptorMap();
      } else {
         descriptorMap = JspcInvoker.makeDescriptorMap(this.wlWebBean, (CharsetMap)null);
      }

      descriptorMap.put("workingDir", this.outputDir.getAbsolutePath());
      descriptorMap.put("compiler", opts.getOption("compiler"));
      descriptorMap.put("source", opts.getOption("source"));
      descriptorMap.put("target", opts.getOption("target"));
      Iterator i = descriptorMap.keySet().iterator();

      while(i.hasNext()) {
         String actualName = (String)i.next();
         String name = this.getToolOptionName(actualName);
         String optionValue = null;
         if (name != null && (optionValue = opts.getOption(name)) != null) {
            if ("printNulls".equals(actualName)) {
               optionValue = "false".equals(optionValue) ? "true" : "false";
            } else {
               say("Overriding " + (this.wlWebBean == null ? " default " : "") + "descriptor option '" + actualName + "'" + (name.equals(actualName) ? "" : " (alias '" + name + "')") + " with value specified on command-line '" + optionValue + "' ");
            }

            descriptorMap.put(actualName, optionValue);
         }
      }

      descriptorMap.put("useByteBuffer", Boolean.toString(opts.getBooleanOption("useByteBuffer")));
      return new JspConfig(descriptorMap);
   }

   private boolean classIsStale(String pkg, String uri, ClassLoader cl) {
      String cname = JSPServlet.uri2classname(pkg, uri);

      try {
         Class c = cl.loadClass(cname);
         return JspStub.isJSPClassStale(c, this);
      } catch (ThreadDeath var6) {
         throw var6;
      } catch (NoClassDefFoundError var7) {
         if (this.verbose) {
            say("WARN: precompiled servlet class " + cname + "'s dependent class " + var7.getMessage().replace('/', '.') + " cannot be found!, so jsp check stale fails and " + uri + " will be recompiled!");
         }
      } catch (Throwable var8) {
      }

      return true;
   }

   private static String buildExtClassPath() {
      return System.getProperty("weblogic.ext.dirs");
   }

   private static ClassFinder buildClassFinder(Getopt2 opts, File outputDir, VirtualJarFile vjf) {
      String classpath = opts.getOption("classpath", (String)null);
      String cpath = System.getProperty("java.class.path");
      if (classpath != null) {
         cpath = cpath + File.pathSeparator + classpath;
      }

      String extCP = buildExtClassPath();
      if (extCP != null) {
         cpath = extCP + File.pathSeparator + cpath;
      }

      String webappCP = JspcInvoker.determineWebAppClasspath(vjf, new HashMap());
      if (webappCP != null) {
         cpath = webappCP + File.pathSeparator + cpath;
      }

      if (outputDir != null) {
         cpath = outputDir.getAbsolutePath() + File.pathSeparator + cpath;
      }

      return new ClasspathClassFinder2(cpath);
   }

   private static String determineURI(File docroot, File f, String uri) throws FileNotFoundException {
      String drs = docroot.getAbsolutePath();
      int drlen = drs.length();
      if (drs.endsWith(".")) {
         --drlen;
         drs = drs.substring(0, drlen);
      }

      if (drs.endsWith(File.separator)) {
         --drlen;
         drs = drs.substring(0, drlen);
      }

      String fs = f.getAbsolutePath();
      if (fs.startsWith(drs.substring(1), 1) && fs.substring(0, 1).equalsIgnoreCase(drs.substring(0, 1))) {
         String jspUri = fs.substring(drlen).replace(File.separatorChar, '/');
         return jspUri;
      } else {
         File jsp = new File(drs, uri.replace('/', File.separatorChar));
         if (!jsp.exists()) {
            throw new FileNotFoundException("ERROR: Source file '" + uri + "' can not be found in the docroot '" + drs + "'. Put the source in the docroot or specify the correct docroot with jspc option \"-webapp\".");
         } else {
            return uri.replace(File.separatorChar, '/');
         }
      }
   }

   private static ClassFinder buildResourceFinder(VirtualJarFile vjf) {
      if (vjf == null) {
         return null;
      } else {
         String resourcePath = "";
         File[] roots = vjf.getRootFiles();
         if (roots == null) {
            return null;
         } else {
            for(int i = 0; i < roots.length; ++i) {
               resourcePath = roots[i].getAbsolutePath() + File.pathSeparator + resourcePath;
            }

            return new ClasspathClassFinder2(resourcePath);
         }
      }
   }

   protected String getToolOptionName(String name) {
      if (name.equals("printNulls")) {
         return "noPrintNulls";
      } else if (name.equals("precompileContinue")) {
         return "k";
      } else if (name.equals("compileCommand")) {
         return "compiler";
      } else if (name.equals("packagePrefix")) {
         return "package";
      } else if (name.equals("debug")) {
         return "linenumbers";
      } else {
         return !name.equals("encoding") && !name.equals("compilerclass") && !name.equals("compileFlags") && !name.equals("keepgenerated") && !name.equals("compilerSupportsEncoding") && !name.equals("superclass") && !name.equals("compressHtmlTemplate") && !name.equals("optimizeJavaExpression") && !name.equals("noTryBlocks") ? null : name;
      }
   }

   private void validateToolInput(Getopt2 opts) throws ToolFailureException {
      boolean compileAll = opts.getBooleanOption("compileAll");
      boolean webapp = opts.getOption("webapp") != null;
      String pkgName = opts.getOption("package", "jsp_servlet");
      if (!compileAll && !webapp && opts.args().length == 0) {
         if (opts.hasOptions()) {
            opts.usageError("weblogic.jspc");
            throw new ToolFailureException("Only options were given, additional arguments are required ");
         } else {
            opts.usageError("weblogic.jspc");
            throw new ToolFailureException("No arguments were given, there is nothing to do.");
         }
      } else if ("".equals(pkgName.trim())) {
         throw new IllegalArgumentException("Bad -package option value, please specify a valid value for this option");
      }
   }

   private static void say(String s) {
      System.out.println("[jspc] " + s);
   }

   private static void sayError(String s) {
      System.err.println("Error: " + s);
   }

   static void p(String out) {
      System.out.println("[jspc20]" + out);
   }

   public static void main(String[] args) throws Exception {
      (new jspc20(args)).run();
   }

   static {
      _WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Stale_Resource_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "jspc20.java", "weblogic.servlet.jsp.jspc20", "isResourceStale", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;)Z", 562, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Stale_Resource_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("res", (String)null, false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium};
      $assertionsDisabled = !jspc20.class.desiredAssertionStatus();
      DEFAULT_TARGET_DIR = File.separator + "WEB-INF" + File.separator + "classes";
      useUniqueJSPCL = Boolean.getBoolean("weblogic.jspc.useUniqueJspClassLoader");
   }
}
