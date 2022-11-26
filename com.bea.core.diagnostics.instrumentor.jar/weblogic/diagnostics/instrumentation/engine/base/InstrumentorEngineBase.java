package weblogic.diagnostics.instrumentation.engine.base;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationException;
import weblogic.diagnostics.instrumentation.InstrumentationStatistics;
import weblogic.diagnostics.type.DiagnosticRuntimeException;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.utils.PropertyHelper;
import weblogic.utils.time.Timer;

public class InstrumentorEngineBase implements InstrumentationEngineConstants {
   protected static final int BUF_SIZE = 32768;
   protected static final String DEFAULT_ENGINE_NAME = "InstrumentorEngine";
   protected static final Set DUMP_CLASSES = getDumpClassses();
   private static final MyClassLoader LOADER = new MyClassLoader((ClassLoader)null);
   protected String engineName;
   protected String supportClassName;
   protected MonitorSpecificationBase[] monitorSpecifications;
   protected boolean throwableCaptured;
   private Map classInfoMap;
   private static final int CLASS_INFO_MAP_MAX_SIZE = PropertyHelper.getInteger("weblogic.diagnostics.instrumentor.ClassInfoMapMaxSize", 2000);
   private static final float CLASS_INFO_MAP_RETAIN_ON_PURGE_PERCENT = 0.95F;
   private static final int CLASS_INFO_MAP_RETAIN_GOAL_SIZE;
   private static final int MAX_LENGTH;
   private Pattern includePattern;
   private Pattern excludePattern;
   private boolean enabled;
   protected InstrumentationStatistics instrumentationStatistics;
   private Timer elapsedTimeTimer;
   protected boolean allowHotswap;
   private HashMap includePatternsForMonitors;
   private HashMap excludePatternsForMonitors;
   private int execPointcutCount;
   private int callPointcutCount;
   private int withinPointcutCount;
   private int newcallPointcutCount;
   private int constructionPointcutCount;
   private int catchPointcutCount;
   protected ClassLoader bootstrapCL;
   private boolean loadedClassLookupPossible;
   private Method findLoadedClassMtd;
   private boolean findBootStrapClassPossible;
   private static boolean offline;
   public static final String ALLOW_HOTSWAP_SETTING = "AllowHotswap";
   public static final String THROWABLE_CAPTURED_SETTING = "ThrowableCaptured";
   public static final String SUPPORT_CLASSNAME_SETTING = "SupportClassName";
   public static final String USE_LOCALHOLDER_SETTING = "UseLocalHolder";
   public static final String SENSITIVE_OPTIMIZE_SETTING = "SensitiveOptimize";
   public static final String V16_COMPUTE_FRAMES_SETTING = "V16ComputeFrames";
   public static final String HOTSWAP_NOAUX_SETTING = "HotSwapWithNoAux";
   public static final String INFO_GRACE_PERIOD_SETTING = "ClassInfoCacheGracePeriodMillis";
   public static final String SUMMARY_FILE_SETTING = "SummaryFile";
   public static final int DEFAULT_LOCAL_HOLDER_USAGE = 2;
   private int useLocalHolder;
   private boolean sensitiveOptimize;
   private static long classInfoCacheGracePeriodMillis;
   protected boolean v16ComputeFrames;
   protected String summaryFileName;
   protected PrintWriter summaryWriter;
   private boolean trackMatchedMonitors;
   protected HashSet matchedMonitorSet;
   protected boolean hotSwapWithNoAux;

   private static Set getDumpClassses() {
      String classList = PropertyHelper.getProperty("weblogic.diagnostics.instrumentation.dumpclasses", (String)null);
      Set set = null;
      if (classList != null) {
         String[] arr = classList.split(",");
         set = new HashSet();

         for(int i = 0; i < arr.length; ++i) {
            set.add(arr[i].trim());
         }
      }

      return set;
   }

   static final long getClassInfoCacheGracePeriodMillis() {
      return classInfoCacheGracePeriodMillis;
   }

   protected InstrumentorEngineBase() {
      this.throwableCaptured = false;
      this.classInfoMap = new ConcurrentHashMap();
      this.enabled = true;
      this.elapsedTimeTimer = Timer.createTimer();
      this.includePatternsForMonitors = new HashMap();
      this.excludePatternsForMonitors = new HashMap();
      this.bootstrapCL = null;
      this.loadedClassLookupPossible = false;
      this.findLoadedClassMtd = null;
      this.findBootStrapClassPossible = false;
      this.useLocalHolder = 2;
      this.sensitiveOptimize = true;
      this.v16ComputeFrames = true;
      this.summaryFileName = null;
      this.summaryWriter = null;
      this.trackMatchedMonitors = false;
      this.matchedMonitorSet = null;
      this.hotSwapWithNoAux = false;
      this.initBootstrapCL();
   }

   public InstrumentorEngineBase(MonitorSpecificationBase[] monitorSpecifications) {
      this("InstrumentorEngine", monitorSpecifications);
   }

   public InstrumentorEngineBase(String name, MonitorSpecificationBase[] monitorSpecifications) {
      this(name, monitorSpecifications, false, false);
   }

   public InstrumentorEngineBase(String name, MonitorSpecificationBase[] monitorSpecifications, boolean allowHotswap, boolean throwableCaptured) {
      this.throwableCaptured = false;
      this.classInfoMap = new ConcurrentHashMap();
      this.enabled = true;
      this.elapsedTimeTimer = Timer.createTimer();
      this.includePatternsForMonitors = new HashMap();
      this.excludePatternsForMonitors = new HashMap();
      this.bootstrapCL = null;
      this.loadedClassLookupPossible = false;
      this.findLoadedClassMtd = null;
      this.findBootStrapClassPossible = false;
      this.useLocalHolder = 2;
      this.sensitiveOptimize = true;
      this.v16ComputeFrames = true;
      this.summaryFileName = null;
      this.summaryWriter = null;
      this.trackMatchedMonitors = false;
      this.matchedMonitorSet = null;
      this.hotSwapWithNoAux = false;
      this.initBootstrapCL();
      this.monitorSpecifications = monitorSpecifications;
      this.analyzePointcuts();
      this.allowHotswap = allowHotswap;
      this.hotSwapWithNoAux = false;
      this.engineName = name;
      this.throwableCaptured = throwableCaptured;
      this.instrumentationStatistics = new InstrumentationStatistics();
      if (this.supportClassName == null) {
         this.supportClassName = "weblogic/diagnostics/instrumentation/InstrumentationSupport";
      }

      this.supportClassName = this.supportClassName.replace('.', '/');
      this.computeMonitorIncludeExcludePatterns();
   }

   public InstrumentorEngineBase(String name, MonitorSpecificationBase[] monitorSpecifications, boolean allowHotswap) {
      this(name, monitorSpecifications, allowHotswap, false);
   }

   public InstrumentorEngineBase(String name, MonitorSpecificationBase[] monitorSpecifications, boolean allowHotswap, String supportClassName) {
      this(name, monitorSpecifications, allowHotswap, false);
      if (supportClassName != null) {
         supportClassName = supportClassName.replace('.', '/');
         this.supportClassName = supportClassName;
      }

   }

   public InstrumentorEngineBase(String name, MonitorSpecificationBase[] monitorSpecifications, boolean allowHotswap, String supportClassName, boolean throwableCaptured) {
      this(name, monitorSpecifications, allowHotswap, throwableCaptured);
      if (supportClassName != null) {
         supportClassName = supportClassName.replace('.', '/');
         this.supportClassName = supportClassName;
      }

   }

   public InstrumentorEngineBase(String name, MonitorSpecificationBase[] monitorSpecifications, Properties settings) {
      this.throwableCaptured = false;
      this.classInfoMap = new ConcurrentHashMap();
      this.enabled = true;
      this.elapsedTimeTimer = Timer.createTimer();
      this.includePatternsForMonitors = new HashMap();
      this.excludePatternsForMonitors = new HashMap();
      this.bootstrapCL = null;
      this.loadedClassLookupPossible = false;
      this.findLoadedClassMtd = null;
      this.findBootStrapClassPossible = false;
      this.useLocalHolder = 2;
      this.sensitiveOptimize = true;
      this.v16ComputeFrames = true;
      this.summaryFileName = null;
      this.summaryWriter = null;
      this.trackMatchedMonitors = false;
      this.matchedMonitorSet = null;
      this.hotSwapWithNoAux = false;
      this.initBootstrapCL();
      this.engineName = name;
      this.monitorSpecifications = monitorSpecifications;
      this.analyzePointcuts();
      if (settings == null) {
         this.allowHotswap = false;
         this.throwableCaptured = false;
         this.supportClassName = "weblogic/diagnostics/instrumentation/InstrumentationSupport";
         this.useLocalHolder = 2;
         this.sensitiveOptimize = true;
         this.v16ComputeFrames = true;
         this.hotSwapWithNoAux = false;
         classInfoCacheGracePeriodMillis = 0L;
         this.summaryFileName = null;
      } else {
         this.allowHotswap = Boolean.parseBoolean(settings.getProperty("AllowHotswap", "false"));
         this.throwableCaptured = Boolean.parseBoolean(settings.getProperty("ThrowableCaptured", "false"));
         this.supportClassName = settings.getProperty("SupportClassName", "weblogic/diagnostics/instrumentation/InstrumentationSupport");
         this.useLocalHolder = parseLocalHolderUsage(settings.getProperty("UseLocalHolder"));
         this.sensitiveOptimize = Boolean.parseBoolean(settings.getProperty("SensitiveOptimize", "true"));
         this.v16ComputeFrames = Boolean.parseBoolean(settings.getProperty("V16ComputeFrames", "true"));
         this.hotSwapWithNoAux = Boolean.parseBoolean(settings.getProperty("HotSwapWithNoAux", "false"));
         classInfoCacheGracePeriodMillis = Long.parseLong(settings.getProperty("ClassInfoCacheGracePeriodMillis", "0"));
         this.summaryFileName = settings.getProperty("SummaryFile");
         if (this.allowHotswap) {
            if (this.hotSwapWithNoAux && this.useLocalHolder == 0) {
               this.hotSwapWithNoAux = false;
               if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("Hotswap AUX classes must be generated if not using LocalHolder style, HotSwapWithNoAux setting is ignored for all classes");
               }
            }
         } else {
            this.hotSwapWithNoAux = false;
         }
      }

      if (this.summaryFileName != null) {
         try {
            this.summaryWriter = new PrintWriter(new BufferedWriter(new FileWriter(this.summaryFileName, true)));
         } catch (IOException var5) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Problem creating summary file " + this.summaryFileName, var5);
            }

            this.summaryWriter = null;
         }
      }

      this.supportClassName = this.supportClassName.replace('.', '/');
      this.instrumentationStatistics = new InstrumentationStatistics();
   }

   private void initBootstrapCL() {
      try {
         this.bootstrapCL = new BootstrapDelegator();
      } catch (Throwable var7) {
         if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("Unable to init the bootstrap delegator, returning the systemCL. Classes resolving differently between the system and bootstrap classloader on JVMs where the bootstrap classloader is represented with null may have issues being instrumented", var7);
         }

         try {
            this.bootstrapCL = ClassLoader.getSystemClassLoader();
         } catch (Throwable var6) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Unable to fallback to the systemCL for the bootstrap delegator, returning the engines CL. Classes resolving differently between the engines CL and bootstrap classloader on JVMs where the bootstrap classloader is represented with null may have issues being instrumented", var6);
            }

            try {
               this.bootstrapCL = this.getClass().getClassLoader();
            } catch (Throwable var4) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Unable to fallback to the engines CL for the bootstrap delegator, classes loaded by the bootstrap classloader on JVMs where the bootstrap classloader is represented with null may have issues being instrumented", var4);
            }
         }
      }

      if (System.getProperty("java.version").startsWith("1.")) {
         try {
            this.findLoadedClassMtd = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            this.findLoadedClassMtd.setAccessible(true);
            this.loadedClassLookupPossible = true;
         } catch (Throwable var5) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Unable to call findLoadedClass, ClassInfo construction may not be possible for some classes (ie: generated ones)", var5);
            }
         }
      } else {
         this.findBootStrapClassPossible = true;
      }

   }

   Class findBootstrapClassOrNull(String name) {
      try {
         return LOADER.findClass(name);
      } catch (ClassNotFoundException var3) {
         return null;
      }
   }

   private Class findLoadedClass(ClassLoader loader, String className) {
      if (className == null) {
         return null;
      } else {
         String binaryClassName = className.replace('/', '.');

         try {
            Class fooClz = null;
            if (this.loadedClassLookupPossible) {
               fooClz = (Class)this.findLoadedClassMtd.invoke(loader, binaryClassName);
            }

            if (fooClz == null && this.findBootStrapClassPossible) {
               fooClz = this.findBootstrapClassOrNull(binaryClassName);
            }

            return fooClz;
         } catch (Throwable var5) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("findLoadedClass failed", var5);
            }

            return null;
         }
      }
   }

   private void analyzePointcuts() {
      PointcutExpressionVisitor visitor = new PointcutExpressionVisitor() {
         public void visit(PointcutExpression expr) {
            if (expr instanceof PointcutSpecification) {
               PointcutSpecification pSpec = (PointcutSpecification)expr;
               switch (pSpec.getType()) {
                  case 0:
                     InstrumentorEngineBase.this.execPointcutCount++;
                     break;
                  case 1:
                     InstrumentorEngineBase.this.callPointcutCount++;
                     break;
                  case 2:
                     InstrumentorEngineBase.this.withinPointcutCount++;
                     break;
                  case 3:
                     InstrumentorEngineBase.this.constructionPointcutCount++;
                     break;
                  case 4:
                     InstrumentorEngineBase.this.newcallPointcutCount++;
                     break;
                  case 5:
                     InstrumentorEngineBase.this.catchPointcutCount++;
               }
            }

         }
      };
      if (this.monitorSpecifications != null) {
         MonitorSpecificationBase[] var2 = this.monitorSpecifications;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MonitorSpecificationBase mon = var2[var4];
            PointcutExpression pExpr = mon.getPointcutExpression();
            if (pExpr != null) {
               pExpr.accept(visitor);
            }
         }
      }

   }

   int getExecPointcutCount() {
      return this.execPointcutCount;
   }

   int getCallPointcutCount() {
      return this.callPointcutCount;
   }

   int getWithinPointcutCount() {
      return this.withinPointcutCount;
   }

   int getNewcallPointcutCount() {
      return this.newcallPointcutCount;
   }

   int getCatchPointcutCount() {
      return this.catchPointcutCount;
   }

   int getConstructionPointcutCount() {
      return this.constructionPointcutCount;
   }

   public boolean isHotswapAllowed() {
      return this.allowHotswap;
   }

   public boolean getHotSwapWithNoAux() {
      return this.hotSwapWithNoAux;
   }

   public boolean getV16ComputeFrames() {
      return this.v16ComputeFrames;
   }

   public boolean isThrowableCaptured() {
      return this.throwableCaptured;
   }

   public static boolean isOffline() {
      return offline;
   }

   public static void setOffline(boolean val) {
      offline = val;
   }

   public void setIncludePatterns(String[] includes) throws PatternSyntaxException {
      this.includePattern = this.createCompositPattern(includes);
   }

   public void setExcludePatterns(String[] excludes) throws PatternSyntaxException {
      this.excludePattern = this.createCompositPattern(excludes);
   }

   public void setDiagnosticMonitors(List monitors) {
      this.includePatternsForMonitors = new HashMap();
      this.excludePatternsForMonitors = new HashMap();
      if (monitors != null) {
         Iterator it = monitors.iterator();

         while(it.hasNext()) {
            DiagnosticMonitor mon = (DiagnosticMonitor)it.next();
            String monType = mon.getType();
            this.computeMonitorIncludeExcludePatterns(monType, mon.getIncludes(), mon.getExcludes());
         }

      }
   }

   private void computeMonitorIncludeExcludePatterns() {
      this.includePatternsForMonitors = new HashMap();
      this.excludePatternsForMonitors = new HashMap();
      int size = this.monitorSpecifications != null ? this.monitorSpecifications.length : 0;
      if (size > 0) {
         MonitorSpecificationBase[] var2 = this.monitorSpecifications;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MonitorSpecificationBase mon = var2[var4];
            this.computeMonitorIncludeExcludePatterns(mon.getType(), mon.getInclusionPatterns(), mon.getExclusionPatterns());
         }
      }

   }

   private void computeMonitorIncludeExcludePatterns(String monType, String[] inc, String[] exc) {
      Pattern pat;
      try {
         pat = this.createCompositPattern(inc);
         if (pat != null) {
            this.includePatternsForMonitors.put(monType, pat);
         }
      } catch (Exception var6) {
         DiagnosticsLogger.logInvalidInclusionPatternInMonitorError(this.engineName, monType);
      }

      try {
         pat = this.createCompositPattern(exc);
         if (pat != null) {
            this.excludePatternsForMonitors.put(monType, pat);
         }
      } catch (Exception var5) {
         DiagnosticsLogger.logInvalidExclusionPatternInMonitorError(this.engineName, monType);
      }

   }

   private Pattern createCompositPattern(String[] patterns) throws PatternSyntaxException {
      Pattern pattern = null;
      String patString = null;
      int size = patterns != null ? patterns.length : 0;
      boolean first = true;

      for(int i = 0; i < size; ++i) {
         String pat = patterns[i].trim();
         if (pat.length() > 0) {
            String ele = this.toRegexPattern(pat);
            if (first) {
               patString = ele;
            } else {
               patString = patString + "|" + ele;
            }

            first = false;
         }
      }

      if (patString != null) {
         try {
            pattern = Pattern.compile(patString);
         } catch (PatternSyntaxException var9) {
            this.enabled = false;
            throw var9;
         }
      }

      return pattern;
   }

   private String toRegexPattern(String pat) {
      if (!pat.equals("*") && !pat.equals("**")) {
         pat = pat.replaceAll("\\.", "/");
         pat = pat.replaceAll("\\$", "\\\\\\$");
         if (!pat.startsWith("*")) {
            pat = "^" + pat;
         }

         if (!pat.endsWith("*")) {
            pat = pat + "$";
         }

         pat = pat.replaceAll("\\*", "(.*)");
         return pat;
      } else {
         return "(.*)";
      }
   }

   private boolean isEligibleClass(String className) {
      return !this.enabled ? false : this.isEligibleClass(className, this.includePattern, this.excludePattern);
   }

   private boolean isEligibleClass(String className, Pattern inPattern, Pattern exPattern) {
      if (exPattern != null && exPattern.matcher(className).matches()) {
         return false;
      } else {
         return inPattern != null ? inPattern.matcher(className).matches() : true;
      }
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   MonitorSpecificationBase[] getMonitorSpecifications(String className) {
      List list = new ArrayList();
      int size = this.monitorSpecifications != null ? this.monitorSpecifications.length : 0;

      for(int i = 0; i < size; ++i) {
         MonitorSpecificationBase monSpec = this.monitorSpecifications[i];
         String monType = monSpec.getType();
         Pattern inPattern = (Pattern)this.includePatternsForMonitors.get(monType);
         Pattern exPattern = (Pattern)this.excludePatternsForMonitors.get(monType);
         if (this.isEligibleClass(className, inPattern, exPattern)) {
            list.add(monSpec);
         }
      }

      MonitorSpecificationBase[] specs = new MonitorSpecificationBase[list.size()];
      specs = (MonitorSpecificationBase[])((MonitorSpecificationBase[])list.toArray(specs));
      return specs;
   }

   String getInstrumentationSupportClassName() {
      return this.supportClassName;
   }

   public InstrumentationStatistics getInstrumentationStatistics() {
      return this.instrumentationStatistics;
   }

   public boolean wouldClassBeInstrumented(Class clazz) throws InstrumentationException {
      return clazz == null ? false : this.wouldClassBeInstrumented(clazz.getClassLoader(), clazz.getName());
   }

   public boolean wouldClassBeInstrumented(ClassLoader classLoader, String className) throws InstrumentationException {
      if (classLoader == null && this.bootstrapCL != null) {
         classLoader = this.bootstrapCL;
      }

      if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_LOGGER.debug("wouldClassBeInstrumented: className=" + className);
      }

      className = className.replace('.', '/');
      if (!this.isEligibleClass(className)) {
         if (InstrumentationDebug.DEBUG_RESULT.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_RESULT.debug("wouldClassBeInstrumented: returns false, class not eligible: className=" + className);
         }

         return false;
      } else {
         boolean retVal = false;
         URL[] urlHolder = new URL[1];
         byte[] buf = null;

         byte[] buf;
         try {
            this.findClassInfo(className, classLoader, urlHolder);
            buf = this.findClassBytes(classLoader, className, urlHolder[0]);
         } catch (Throwable var8) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Unexpected failure finding class info or byte [] for " + className, var8);
            }

            throw new InstrumentationException("Unexpected failure attempting to analyze" + className, var8);
         }

         if (buf == null) {
            throw new InstrumentationException("Unable to retrieve byte [] for " + className + " for analysis");
         } else {
            try {
               MonitorSpecificationBase[] mSpecs = this.getMonitorSpecifications(className);
               if (mSpecs.length > 0) {
                  ClassInstrumentor classInstrumentor = new ClassInstrumentor(classLoader, this, className, mSpecs, buf, this.useLocalHolder, this.sensitiveOptimize);
                  retVal = classInstrumentor.wouldClassBeInstrumented();
               }
            } catch (InstrumentationException var9) {
               throw var9;
            } catch (Throwable var10) {
               if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("Could not analyze class " + className, var10);
               }

               throw new InstrumentationException("Unexpected failure attempting to analyze" + className, var10);
            }

            if (InstrumentationDebug.DEBUG_RESULT.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_RESULT.debug("wouldClassBeInstrument: returns " + retVal + " for className=" + className);
            }

            return retVal;
         }
      }
   }

   private byte[] findClassBytes(ClassLoader cl, String className, URL url) {
      ClassLoader clRes = cl != null ? cl : (this.bootstrapCL == null ? null : this.bootstrapCL);
      InputStream in = null;

      try {
         if (url != null) {
            in = url.openStream();
            if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CLASSINFO.debug("findClassBytes(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + "), look for bytes at url: " + url + ", returned " + (in == null ? "no input stream" : "input stream"));
            }
         } else if (clRes != null) {
            in = clRes.getResourceAsStream(className + ".class");
            if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CLASSINFO.debug("findClassBytes(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + "), getResourceAsStream returned " + (in == null ? "no input stream" : "input stream"));
            }
         }

         if (in != null) {
            byte[] buf = this.readFromStream(in);
            if (!ClassAnalyzer.isMagicOk(buf)) {
               if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("findClassBytes(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ") failed to retrieve byte []");
               }

               Object var22 = null;
               return (byte[])var22;
            }

            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("findClassBytes(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ") retrieved bytes supplied by classloader, appears valid");
            }

            byte[] var7 = buf;
            return var7;
         }

         if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("findClassBytes(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ") no bytes found, classloader hierarchy starting with parent:");

            for(ClassLoader currCl = cl == null ? null : cl.getParent(); currCl != null; currCl = currCl.getParent()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("    " + currCl.getClass().getName() + "@" + currCl.hashCode());
            }
         }
      } catch (IOException var19) {
         UnexpectedExceptionHandler.handle("Unexpected exception reading class bytes for " + className, var19);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var18) {
               UnexpectedExceptionHandler.handle("Unexpected exception closing stream", var18);
            }
         }

      }

      return null;
   }

   public byte[] instrumentClass(ClassLoader classLoader, String className, byte[] buf) {
      if (classLoader == null && this.bootstrapCL != null) {
         classLoader = this.bootstrapCL;
      }

      long t0 = this.elapsedTimeTimer.timestamp();
      byte[] retVal = null;
      if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_LOGGER.debug("instrumentClass: className=" + className);
      }

      boolean dumpClass = DUMP_CLASSES != null && DUMP_CLASSES.contains(className);
      className = className.replace('.', '/');
      if (this.isEligibleClass(className)) {
         try {
            if (dumpClass) {
               dumpBytes(className + "_IN", buf);
            }

            MonitorSpecificationBase[] mSpecs = this.getMonitorSpecifications(className);
            if (mSpecs.length > 0) {
               ClassInstrumentor classInstrumentor = new ClassInstrumentor(classLoader, this, className, mSpecs, buf, this.useLocalHolder, this.sensitiveOptimize);
               retVal = classInstrumentor.getBytes();
               if (InstrumentationDebug.DEBUG_RESULT.isDebugEnabled()) {
                  if (retVal == null) {
                     InstrumentationDebug.DEBUG_RESULT.debug("instrumentClass: class not instrumented: className=" + className);
                  } else {
                     InstrumentationDebug.DEBUG_RESULT.debug("instrumentClass: class instrumented: className=" + className);
                  }
               }

               if (dumpClass && retVal != null) {
                  dumpBytes(className + "_OUT", retVal);
               }

               classInstrumentor.markEndBeingInstrumented();
            } else if (InstrumentationDebug.DEBUG_RESULT.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_RESULT.debug("instrumentClass: class no monitors matched not instrumented: className=" + className);
            }
         } catch (Throwable var10) {
            this.instrumentationStatistics.incrementClassweaveAbortCount(1);
            DiagnosticsLogger.logCouldNotInstrumentClass(className, var10.getMessage());
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Could not instrument class " + className, var10);
            }
         }
      } else if (InstrumentationDebug.DEBUG_RESULT.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_RESULT.debug("instrumentClass: class not eligible: className=" + className);
      }

      long t1 = this.elapsedTimeTimer.timestamp();
      this.instrumentationStatistics.incrementWeavingTime(t1 - t0, retVal != null);
      return retVal;
   }

   static void dumpBytes(String name, byte[] bytes) {
      FileOutputStream os = null;

      try {
         String fname = name.replace('/', '_') + ".class";
         os = new FileOutputStream(fname);
         os.write(bytes);
      } catch (Exception var12) {
         if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("failed dumping class " + name, var12);
         }
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (Exception var11) {
            }
         }

      }

   }

   boolean isParentClassloader(ClassLoader parent, ClassLoader child) {
      if (parent != null) {
         for(ClassLoader cl = child; cl != null; cl = cl.getParent()) {
            if (cl == parent) {
               return true;
            }
         }
      }

      return false;
   }

   private ClassInfo findClassInfo(String className, ClassLoader cl, URL[] urlHolder) {
      ClassInfoEntry entry = (ClassInfoEntry)this.classInfoMap.get(className);
      ClassInfo[] infos = entry != null ? entry.infos : null;
      int infoCount = infos != null ? infos.length : 0;

      for(int i = 0; i < infoCount; ++i) {
         ClassInfo info = infos[i];
         if (info != null && this.isParentClassloader(info.getClassLoader(), cl)) {
            if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
               ClassLoader infoCl = info.getClassLoader();
               InstrumentationDebug.DEBUG_CLASSINFO.debug("Returning cached ClassInfo for: className=" + className + " cl=" + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ", infoCl=" + (infoCl == null ? "boostrap classloader" : infoCl.getClass().getName() + "@" + infoCl.hashCode()));
            }

            if (info.isValid()) {
               return info;
            }

            if (info.getClassLoader() == cl) {
               return info;
            }
         }
      }

      ClassLoader clRes = cl != null ? cl : (this.bootstrapCL == null ? null : this.bootstrapCL);
      if (infoCount > 0) {
         URL url = null;
         if (urlHolder != null) {
            url = urlHolder[0];
            if (url == null && clRes != null) {
               url = clRes.getResource(className + ".class");
               urlHolder[0] = url;
               if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: getResource for " + className + " with cl: " + clRes.getClass().getName() + "@" + clRes.hashCode() + " returned url: " + url);
               }
            }
         }

         if (url != null) {
            String urlSource = url.toString();

            for(int i = 0; i < infoCount; ++i) {
               ClassInfo info = infos[i];
               if (info != null && urlSource.equals(info.getURLSource())) {
                  try {
                     info = (ClassInfo)info.clone();
                     info.setClassLoader(cl);
                     if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: clone classInfo for " + className + " with cl set to: " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()));
                     }

                     this.enterClassInfo(className, cl, info, url);
                  } catch (CloneNotSupportedException var13) {
                     if (ClassInstrumentor.isTargetedDebugEnabled() && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED: CloneNotSupportedException for " + className + " setting cl for into to be " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()));
                     }

                     info.setClassLoader(cl);
                  }

                  if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CLASSINFO.debug("Returning shared/cloned ClassInfo for: className=" + className + " cl=" + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()));
                  }

                  return info;
               }
            }
         }
      }

      if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CLASSINFO.debug("Unable to find cached ClassInfo for className=" + className + " cl=" + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()));
      }

      return null;
   }

   private void enterClassInfo(String className, ClassLoader cl, ClassInfo info, URL url) {
      ClassInfoEntry entry = (ClassInfoEntry)this.classInfoMap.get(className);
      if (entry == null || !entry.cachingDisabled) {
         ClassInfo[] infos = entry != null ? entry.infos : null;
         int infoCount = infos != null ? infos.length : 0;
         if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CLASSINFO.debug("Caching ClassInfo for className=" + className + " cl=" + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()));
         }

         info.setClassLoader(cl);
         if (url != null) {
            info.setURLSource(url.toString());
         }

         List infoList = new ArrayList();

         for(int i = 0; i < infoCount; ++i) {
            if (infos[i] != null && infos[i].getClassLoader() != null && (!this.isParentClassloader(cl, infos[i].getClassLoader()) || !info.isValid() && infos[i].isValid())) {
               infoList.add(infos[i]);
               if (infoList.size() >= MAX_LENGTH) {
                  ClassInfo removed = (ClassInfo)infoList.remove(0);
                  if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled() && removed != null) {
                     InstrumentationDebug.DEBUG_CLASSINFO.debug("InfoList would exceed max size, ClassInfo removed: " + removed);
                  }
               }
            }
         }

         info.setEngine(this);
         infoList.add(info);
         ClassInfo[] newInfos = new ClassInfo[infoList.size()];
         newInfos = (ClassInfo[])((ClassInfo[])infoList.toArray(newInfos));
         if (entry == null) {
            if (this.purgeClassInfoEntries() < CLASS_INFO_MAP_MAX_SIZE) {
               ClassInfoEntry newEntry = new ClassInfoEntry(newInfos);
               this.classInfoMap.put(className, newEntry);
            } else if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CLASSINFO.debug("Cache limit exceeded, not caching ClassInfo for className=" + className + " cl=" + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()));
            }
         } else {
            entry.setInfos(newInfos);
         }

      }
   }

   ClassInfo getClassInfo(String className, ClassLoader cl) {
      ClassInfo info = null;
      URL[] urlHolder = new URL[1];
      info = this.findClassInfo(className, cl, urlHolder);
      if (info != null) {
         if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled() && !info.isValid()) {
            InstrumentationDebug.DEBUG_CLASSINFO.debug("getClassInfo(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + "), findClassInfo returned invalid ClassInfo: " + info.toString());
         }

         return info;
      } else {
         URL url = urlHolder[0];
         byte[] buf = this.findClassBytes(cl, className, url);

         try {
            ClassAnalyzer loadedClass;
            if (buf != null) {
               loadedClass = new ClassAnalyzer(buf);
               info = loadedClass.getClassInfo();
               if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("getClassInfo(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ") formed from bytes supplied by classloader = " + info.toString());
               }
            }

            if (buf == null || info == null || !info.isValid()) {
               loadedClass = null;
               Class loadedClass = this.findLoadedClass(cl, className);
               info = new ClassInfo(loadedClass);
               if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("getClassInfo(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ") fallback to loaded class found " + loadedClass + " and formed info = " + info.toString());
               }

               if (!info.isValid()) {
                  DiagnosticsLogger.logClassInfoUnavailable(className);
                  throw new DiagnosticRuntimeException(DiagnosticsLogger.logClassInfoUnavailableLoggable(className).getMessage());
               }
            }
         } catch (IllegalArgumentException var9) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("getClassInfo failed unexpectedly: " + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + ", buf: " + (buf == null ? "null" : buf.length));
               if (buf != null) {
                  String dumpName = className.replace('.', '/') + "_DBG";
                  InstrumentationDebug.DEBUG_LOGGER.debug("dumping class buf to " + dumpName + ".class");
                  dumpBytes(dumpName, buf);
               }
            }

            throw var9;
         }

         if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled()) {
            if (info == null) {
               InstrumentationDebug.DEBUG_CLASSINFO.debug("getClassInfo(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + "), resulted in a null ClassInfo");
            } else if (!info.isValid()) {
               InstrumentationDebug.DEBUG_CLASSINFO.debug("getClassInfo(" + className + ", " + (cl == null ? "boostrap classloader" : cl.getClass().getName() + "@" + cl.hashCode()) + "), resulted in invalid ClassInfo: " + info.toString());
            }
         }

         return this.isCachingDisabled(className) ? info : this.putClassInfo(className, cl, info, urlHolder);
      }
   }

   private int purgeClassInfoEntries() {
      int size = this.classInfoMap.size();
      if (size >= CLASS_INFO_MAP_MAX_SIZE && size != CLASS_INFO_MAP_RETAIN_GOAL_SIZE) {
         Iterator iter = this.classInfoMap.entrySet().iterator();
         int index = 0;
         int purged = 0;
         int savedEntries = 0;
         int purgeAmount = size - CLASS_INFO_MAP_RETAIN_GOAL_SIZE;
         int purgeMod = size / purgeAmount;
         boolean removeEveryNthEntry = true;

         while(true) {
            Map.Entry entry;
            ClassInfoEntry cEntry;
            boolean saveEntry;
            ClassInfo[] infos;
            long timestamp;
            int i;
            ClassInfo info;
            do {
               label113:
               do {
                  while(iter.hasNext()) {
                     ++index;
                     entry = (Map.Entry)iter.next();
                     cEntry = (ClassInfoEntry)entry.getValue();
                     if (cEntry != null && cEntry.infos != null && (cEntry.infos.length != 1 || cEntry.infos[0] != null && cEntry.infos[0].getClassLoader() != null)) {
                        continue label113;
                     }

                     if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled() && entry != null) {
                        InstrumentationDebug.DEBUG_CLASSINFO.debug("Dead entry removed: " + (String)entry.getKey());
                     }

                     iter.remove();
                     ++purged;
                     if (purged > purgeAmount) {
                        removeEveryNthEntry = false;
                     }
                  }

                  if (purged < purgeAmount && savedEntries > 0) {
                     iter = this.classInfoMap.entrySet().iterator();

                     while(true) {
                        do {
                           do {
                              if (!iter.hasNext()) {
                                 return this.classInfoMap.size();
                              }

                              entry = (Map.Entry)iter.next();
                              cEntry = (ClassInfoEntry)entry.getValue();
                              saveEntry = false;
                           } while(cEntry == null);
                        } while(cEntry.infos == null);

                        infos = cEntry.infos;
                        timestamp = System.currentTimeMillis();

                        for(i = 0; i < infos.length; ++i) {
                           info = infos[i];
                           if (info != null && info.getClassLoader() != null && info.isUnSafeToPurge(timestamp, true)) {
                              saveEntry = true;
                              break;
                           }
                        }

                        if (!saveEntry) {
                           if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled() && entry != null) {
                              InstrumentationDebug.DEBUG_CLASSINFO.debug("Fallback entry removed: " + (String)entry.getKey());
                           }

                           iter.remove();
                           ++purged;
                           if (purged > purgeAmount) {
                              return this.classInfoMap.size();
                           }
                        }
                     }
                  }

                  return this.classInfoMap.size();
               } while(!removeEveryNthEntry);
            } while(index % purgeMod != 0);

            saveEntry = false;
            infos = cEntry.infos;
            timestamp = System.currentTimeMillis();

            for(i = 0; i < infos.length; ++i) {
               info = infos[i];
               if (info != null && info.getClassLoader() != null && info.isUnSafeToPurge(timestamp, true)) {
                  saveEntry = true;
                  ++savedEntries;
                  break;
               }
            }

            if (!saveEntry) {
               if (InstrumentationDebug.DEBUG_CLASSINFO.isDebugEnabled() && entry != null) {
                  InstrumentationDebug.DEBUG_CLASSINFO.debug("Random entry removed: " + (String)entry.getKey());
               }

               iter.remove();
               ++purged;
               if (purged > purgeAmount) {
                  removeEveryNthEntry = false;
               }
            }
         }
      } else {
         return size;
      }
   }

   private boolean isCachingDisabled(String className) {
      ClassInfoEntry entry = (ClassInfoEntry)this.classInfoMap.get(className);
      return entry == null ? false : entry.cachingDisabled;
   }

   public boolean isAssignableFrom(String clazz1, String clazz2, ClassLoader loader1, ClassLoader loader2, ClassLoader instLoader) {
      try {
         this.classInfoMap.clear();
         ClassInfo ci1 = this.getClassInfo(clazz1.replace('.', '/'), loader1);
         ClassInfo ci2 = this.getClassInfo(clazz2.replace('.', '/'), loader2);
         return ci1.isAssignableFrom(ci2, instLoader);
      } catch (DiagnosticRuntimeException var8) {
         return false;
      }
   }

   ClassInfo putClassInfo(String className, ClassLoader cl, ClassInfo info, URL[] urlHolder) {
      ClassInfo existingInfo = this.findClassInfo(className, cl, urlHolder);
      if (existingInfo != null) {
         return existingInfo;
      } else {
         URL url = urlHolder != null ? urlHolder[0] : null;
         this.enterClassInfo(className, cl, info, url);
         return info;
      }
   }

   byte[] readFromStream(InputStream in) throws IOException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] buf = new byte['耀'];
      int nread = false;

      int nread;
      while((nread = in.read(buf)) >= 0) {
         bos.write(buf, 0, nread);
      }

      bos.flush();
      buf = bos.toByteArray();
      bos.close();
      return buf;
   }

   private void instrumentClass(ClassLoader classLoader, File inFile, File outputDir) {
      InputStream in = null;

      try {
         long size = inFile.length();
         byte[] buf = new byte[(int)size];
         in = new FileInputStream(inFile);
         in.read(buf);
         ClassAnalyzer analyzer = new ClassAnalyzer(buf);
         ClassInfo info = analyzer.getClassInfo();
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug(info.toString());
         }

         if (info.isValid()) {
            String className = info.getClassName();
            buf = this.instrumentClass(classLoader, className, buf);
            if (buf != null) {
               this.writeOutputFile(className, buf, outputDir);
            }
         } else {
            DiagnosticsLogger.logInvalidClassFile(inFile.toString());
         }
      } catch (IOException var19) {
         DiagnosticsLogger.logCouldNotInstrument(inFile.toString());
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var18) {
               UnexpectedExceptionHandler.handle("Unexcpected exception closing input file" + inFile, var18);
            }
         }

      }

   }

   private void writeOutputFile(String className, byte[] buf, File outputRoot) throws IOException {
      OutputStream out = null;
      File outFile = this.getOutputFilePath(outputRoot, className);

      try {
         if (buf != null) {
            out = new FileOutputStream(outFile);
            out.write(buf);
         }
      } finally {
         if (out != null) {
            try {
               out.close();
            } catch (IOException var12) {
               UnexpectedExceptionHandler.handle("Unexcpected exception closing output file" + outFile, var12);
            }
         }

      }

   }

   private File getOutputFilePath(File outputRoot, String className) throws IOException {
      File outputDir = outputRoot;
      String[] packageNames = className.split("/");
      int cnt = packageNames != null ? packageNames.length : 0;

      for(int i = 0; i < cnt - 1; ++i) {
         outputDir = new File(outputDir, packageNames[i]);
         if (!outputDir.exists() && !outputDir.mkdir()) {
            throw new IOException("Failed to create directory: " + outputDir);
         }
      }

      return new File(outputDir, packageNames[cnt - 1] + ".class");
   }

   private void instrumentDirectory(ClassLoader classLoader, File inputDir, File outputDir) {
      String[] list = inputDir.list();
      int cnt = list != null ? list.length : 0;

      for(int i = 0; i < cnt; ++i) {
         String fname = list[i];
         if (!fname.startsWith(".")) {
            File inFile = new File(inputDir, fname);
            if (inFile.isDirectory()) {
               this.instrumentDirectory(classLoader, inFile, outputDir);
            } else if (fname.endsWith(".class")) {
               this.instrumentClass(classLoader, inFile, outputDir);
            }
         }
      }

   }

   private void instrumentClass(ClassLoader classLoader, ZipFile zipFile, ZipEntry zipEntry, File outputDir) throws IOException {
      InputStream in = null;
      String name = zipEntry.getName();
      if (name.endsWith(".class")) {
         try {
            in = zipFile.getInputStream(zipEntry);
            byte[] buf = this.readFromStream(in);
            String className = name.substring(0, name.length() - 6);
            buf = this.instrumentClass(classLoader, className, buf);
            if (buf != null) {
               this.writeOutputFile(className, buf, outputDir);
            }
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var14) {
                  UnexpectedExceptionHandler.handle("Unexpected exception while closing zip entry", var14);
               }
            }

         }

      }
   }

   private void instrumentClassArchive(ClassLoader classLoader, File inFile, File outputDir) {
      String fname = inFile.getName().toLowerCase();
      if (fname.endsWith(".zip") || fname.endsWith(".jar")) {
         ZipFile zipFile = null;

         try {
            zipFile = new ZipFile(inFile);
            Enumeration en = zipFile.entries();

            while(en.hasMoreElements()) {
               ZipEntry zipEntry = (ZipEntry)en.nextElement();
               this.instrumentClass(classLoader, zipFile, zipEntry, outputDir);
            }
         } catch (IOException var16) {
            DiagnosticsLogger.logCouldNotInstrument(inFile.toString());
         } finally {
            if (zipFile != null) {
               try {
                  zipFile.close();
               } catch (IOException var15) {
                  UnexpectedExceptionHandler.handle("Unexpected exception while closing archive", var15);
               }
            }

         }
      }

   }

   public void doInstrument(ClassLoader classLoader, File inFile, File outputDir) {
      if (!inFile.exists()) {
         DiagnosticsLogger.logMissingInputFile(inFile.toString());
      }

      if (inFile.isDirectory()) {
         this.instrumentDirectory(classLoader, inFile, outputDir);
      } else {
         String fname = inFile.getName();
         if (fname.endsWith(".class")) {
            this.instrumentClass(classLoader, inFile, outputDir);
         } else {
            this.instrumentClassArchive(classLoader, inFile, outputDir);
         }
      }

   }

   private static int parseLocalHolderUsage(String usage) {
      if (usage == null) {
         return 2;
      } else if ("V16+".equalsIgnoreCase(usage)) {
         return 2;
      } else if ("ALWAYS".equalsIgnoreCase(usage)) {
         return 1;
      } else if ("V17+".equalsIgnoreCase(usage)) {
         return 3;
      } else {
         return "NEVER".equalsIgnoreCase(usage) ? 0 : 2;
      }
   }

   protected boolean printSummary() {
      return this.summaryWriter != null;
   }

   protected void summaryPrintln(String str) {
      this.summaryWriter.println(str);
   }

   public void closeSummary() {
      if (this.summaryWriter != null) {
         this.summaryWriter.flush();
         this.summaryWriter.close();
         this.summaryWriter = null;
      }

   }

   public synchronized ClassInfoCacheStatistics getClassInfoCacheStats(boolean purgeFirst) {
      if (purgeFirst) {
         this.purgeClassInfoEntries();
      }

      ClassInfoCacheStatistics cacheStats = new ClassInfoCacheStatistics();
      cacheStats.cacheSize = this.classInfoMap.size();
      Iterator iter = this.classInfoMap.entrySet().iterator();
      int index = 0;

      while(true) {
         while(iter.hasNext()) {
            ++index;
            Map.Entry entry = (Map.Entry)iter.next();
            ClassInfoEntry cEntry = (ClassInfoEntry)entry.getValue();
            if (cEntry == null) {
               cacheStats.nullEntries++;
            } else {
               if (cEntry.cachingDisabled) {
                  cacheStats.cachingDisabledEntries++;
               }

               if (cEntry.infos == null) {
                  cacheStats.nullEntryInfos++;
               } else {
                  if (cEntry.infos.length > cacheStats.maxEntryLength) {
                     cacheStats.maxEntryLength = cEntry.infos.length;
                  }

                  for(int i = 0; i < cEntry.infos.length; ++i) {
                     if (cEntry.infos[i] == null) {
                        cacheStats.emptyEntryInfos++;
                     } else {
                        int state = cEntry.infos[i].getInstrumentationState();
                        if (state == 1) {
                           cacheStats.infosBeingInstrumented++;
                        }

                        if (state == 2) {
                           cacheStats.infosInGracePeriod++;
                        }
                     }
                  }
               }
            }
         }

         return cacheStats;
      }
   }

   private void resetMatchedMonitors() {
      if (this.trackMatchedMonitors) {
         if (this.matchedMonitorSet != null) {
            this.matchedMonitorSet.clear();
         }
      } else {
         this.matchedMonitorSet = null;
      }

   }

   public boolean getTrackMatchedMonitors() {
      return this.trackMatchedMonitors;
   }

   public void setTrackMatchedMonitors(boolean enabled) {
      this.trackMatchedMonitors = enabled;
      this.resetMatchedMonitors();
   }

   public Set getMatchedMonitorSet() {
      return this.matchedMonitorSet;
   }

   protected void addMatchedMonitor(String monitorName) {
      if (this.trackMatchedMonitors) {
         if (this.matchedMonitorSet == null) {
            this.matchedMonitorSet = new HashSet();
         }

         this.matchedMonitorSet.add(monitorName);
      }
   }

   static {
      CLASS_INFO_MAP_RETAIN_GOAL_SIZE = Math.round((float)CLASS_INFO_MAP_MAX_SIZE * 0.95F);
      MAX_LENGTH = PropertyHelper.getInteger("weblogic.diagnostics.instrumentor.ClassInfoEntryLimit", 20);
      classInfoCacheGracePeriodMillis = 0L;
   }

   private static class BootstrapDelegator extends ClassLoader {
      BootstrapDelegator() {
         super((ClassLoader)null);
      }
   }

   public static class ClassInfoCacheStatistics {
      private int cacheEntriesInUse = 0;
      private int cacheSize = 0;
      private int cachingDisabledEntries = 0;
      private int infosBeingInstrumented = 0;
      private int infosInGracePeriod = 0;
      private int maxEntryLength = 0;
      private int nullEntries = 0;
      private int nullEntryInfos = 0;
      private int emptyEntryInfos = 0;

      public int getCacheMaxSizeLimit() {
         return InstrumentorEngineBase.CLASS_INFO_MAP_MAX_SIZE;
      }

      public int getCacheRetainGoalSize() {
         return InstrumentorEngineBase.CLASS_INFO_MAP_RETAIN_GOAL_SIZE;
      }

      public int getEntryLengthMaxLimit() {
         return InstrumentorEngineBase.MAX_LENGTH;
      }

      public int getCacheEntriesInUse() {
         return this.cacheEntriesInUse;
      }

      public int getCacheSize() {
         return this.cacheSize;
      }

      public int getInfosBeingInstrumented() {
         return this.infosBeingInstrumented;
      }

      public int getInfosInGracePeriod() {
         return this.infosInGracePeriod;
      }

      public int getMaxEntryLength() {
         return this.maxEntryLength;
      }

      public int getNullEntries() {
         return this.nullEntries;
      }

      public int getNullEntryInfos() {
         return this.nullEntryInfos;
      }

      public int getEmptyEntryInfos() {
         return this.emptyEntryInfos;
      }

      public int getCachingDisabledEntries() {
         return this.cachingDisabledEntries;
      }
   }

   private static class ClassInfoEntry {
      public ClassInfo[] infos;
      public boolean cachingDisabled = false;

      private ClassInfoEntry() {
      }

      public ClassInfoEntry(ClassInfo[] newInfos) {
         this.setInfos(newInfos);
      }

      public void setInfos(ClassInfo[] newInfos) {
         if (newInfos.length > InstrumentorEngineBase.MAX_LENGTH) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               String className = null;
               ClassInfo[] var3 = newInfos;
               int var4 = newInfos.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  ClassInfo info = var3[var5];
                  if (info != null) {
                     className = info.getClassName();
                     break;
                  }
               }

               InstrumentationDebug.DEBUG_LOGGER.debug("ClassInfo caching disabled for " + className);
            }

            this.cachingDisabled = true;
         }

         this.infos = newInfos;
      }
   }

   public static class LocalHolderUsage {
      public static final int NEVER = 0;
      public static final int ALWAYS = 1;
      public static final int V16 = 2;
      public static final int V17 = 3;
   }

   static class MyClassLoader extends ClassLoader {
      MyClassLoader(ClassLoader parent) {
         super(parent);
      }

      protected Class findClass(String name) throws ClassNotFoundException {
         return super.findClass(name);
      }

      protected Class findClass(String moduleName, String name) {
         try {
            return super.findClass(name);
         } catch (Exception var4) {
            return null;
         }
      }
   }
}
