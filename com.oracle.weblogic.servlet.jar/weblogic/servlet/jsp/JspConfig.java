package weblogic.servlet.jsp;

import java.security.AccessController;
import java.util.Map;
import java.util.StringTokenizer;
import javax.el.ExpressionFactory;
import javax.el.VariableMapper;
import javax.servlet.ServletConfig;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import weblogic.jsp.internal.jsp.el.ExpressionEvaluatorImpl;
import weblogic.jsp.internal.jsp.el.VariableResolverImpl;
import weblogic.jsp.internal.jsp.el21.ELFactory;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.HTTPLogger;

public final class JspConfig {
   public static final String DEFAULT_JSP_SERVLET = getDefaultJSPServlet();
   public static final String DEFAULT_COMPILER_CLASS = "com.sun.tools.javac.Main";
   public static final String DEFAULT_PACKAGE_PREFIX = "jsp_servlet";
   public static final String DEFAULT_SUPER_CLASS = "weblogic.servlet.jsp.JspBase";
   public static final String DEFAULT_DEFAULT_FILE_NAME = "index.jsp";
   public static final int DEFAULT_PAGE_CHECK_SECONDS = 1;
   public static final boolean DEFAULT_VERBOSE = true;
   public static final boolean DEFAULT_KEEP_GENERATED = false;
   public static final boolean DEFAULT_COMPILER_SUPPORTS_ENCODING = true;
   public static final boolean DEFAULT_NO_TRY_BLOCKS = false;
   public static final boolean DEFAULT_PRECOMPILE = false;
   public static final boolean DEFAULT_PRECOMPILE_CONTINUE = false;
   public static final boolean DEFAULT_EXACT_MAPPING = true;
   public static final boolean DEFAULT_DEBUG_ENABLED = false;
   public static final boolean DEFAULT_BACKWARD_COMPATIBLE = false;
   public static final boolean DEFAULT_PRINT_NULLS = true;
   public static final boolean DEFAULT_COMPRESS_HTML_TEMPLATE = false;
   public static final boolean DEFAULT_OPTIMIZE_JAVA_EXPRESSION = false;
   public static final boolean DEFAULT_STRICT_STALE_CHECK = true;
   public static final boolean DEFAULT_STRICT_JSP_DOCUMENT_VALIDATION = false;
   public static final String BACKWARD_COMPATIBLE = "backwardCompatible";
   public static final String RTEXPRVALUE_JSP_PARAM_NAME = "rtexprvalueJspParamName";
   public static final String COMPILER = "compiler";
   public static final String COMPILE_COMMAND = "compileCommand";
   public static final String COMPILER_CLASS = "compilerClass";
   public static final String COMPILE_FLAGS = "compileFlags";
   public static final String COMPILER_SUPPORTS_ENCODING = "compilerSupportsEncoding";
   public static final String DEBUG = "debug";
   public static final String SOURCE = "source";
   public static final String TARGET = "target";
   public static final String DEFAULT_FILE_NAME = "defaultFileName";
   public static final String ENCODING = "encoding";
   public static final String EXACT_MAPPING = "exactMapping";
   public static final String JSP_PRECOMPILER = "jspPrecompiler";
   public static final String JSP_SERVLET = "jspServlet";
   public static final String KEEP_GENERATED = "keepgenerated";
   public static final String PACKAGE_PREFIX = "packagePrefix";
   public static final String PAGE_CHECK_SECONDS = "pageCheckSeconds";
   public static final String PRECOMPILE = "precompile";
   public static final String PRECOMPILE_CONTINUE = "precompileContinue";
   public static final String PRINT_NULLS = "printNulls";
   public static final String NO_TRY_BLOCKS = "noTryBlocks";
   public static final String SUPER_CLASS = "superclass";
   public static final String VERBOSE = "verbose";
   public static final String WORKING_DIR = "workingDir";
   public static final String COMPRESS_HTML_TEMPLATE = "compressHtmlTemplate";
   public static final String OPTIMIZE_JAVA_EXPRESSION = "optimizeJavaExpression";
   public static final String RESOURCE_PROVIDER_CLASS = "resourceProviderClass";
   public static final String STRICT_STALE_CHECK = "strictStaleCheck";
   public static final String STRICT_JSP_DOCUMENT_VALIDATION = "strictJspDocumentValidation";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean PRODUCTION_MODE;
   private static Boolean compilerClassExists;
   private boolean verbose;
   private boolean keepgenerated;
   private boolean compilerSupportsEncoding;
   private boolean noTryBlocks;
   private boolean precompileContinue;
   private boolean compressHtmlTemplate;
   private boolean optimizeJavaExpression;
   private long pageCheckSeconds;
   private String[] compileFlags;
   private String compileFlagsString;
   private boolean debugEnabled;
   private boolean backwardCompatible;
   private boolean isRtexprvalueJspParamName;
   private boolean printNulls;
   private boolean exactMapping;
   private boolean useByteBuffer;
   private boolean isStrictStaleCheck;
   private boolean strictJspDocumentValidation;
   public String packagePrefix;
   public String compileropt;
   public String compilerval;
   public String compilerClass;
   public String compiler;
   public String source;
   public String target;
   public String compileCommand;
   public String workingDir;
   public String superclassName;
   public String encoding;
   public String defaultFilename;
   public String jspServlet;

   public JspConfig(Map args) {
      String s = null;
      s = this.get(args, "verbose");
      if (s == null) {
         this.verbose = true;
      } else {
         this.verbose = "true".equalsIgnoreCase(s);
      }

      this.compileFlagsString = this.get(args, "compileFlags");
      this.compileFlags = parseFlags(this.compileFlagsString);
      s = this.get(args, "keepgenerated");
      this.keepgenerated = "true".equalsIgnoreCase(s);
      s = this.get(args, "useByteBuffer");
      this.useByteBuffer = "true".equalsIgnoreCase(s);
      s = this.get(args, "strictStaleCheck");
      this.isStrictStaleCheck = "true".equalsIgnoreCase(s);
      s = this.get(args, "strictJspDocumentValidation");
      this.strictJspDocumentValidation = "true".equalsIgnoreCase(s);
      s = this.get(args, "precompileContinue");
      this.precompileContinue = "true".equalsIgnoreCase(s);
      s = this.get(args, "compilerSupportsEncoding");
      if (s == null) {
         this.compilerSupportsEncoding = true;
      } else {
         this.compilerSupportsEncoding = "true".equalsIgnoreCase(s);
      }

      s = this.get(args, "noTryBlocks");
      this.noTryBlocks = "true".equalsIgnoreCase(s);
      this.pageCheckSeconds = PRODUCTION_MODE ? -1L : 1L;
      s = this.get(args, "pageCheckSeconds");
      if (s != null) {
         try {
            this.pageCheckSeconds = Long.parseLong(s);
         } catch (NumberFormatException var4) {
         }
      }

      if ((this.workingDir = this.get(args, "workingDir")) == null) {
         throw new IllegalArgumentException("JSP 'workingDir' must be specified");
      } else {
         s = this.get(args, "debug");
         this.debugEnabled = "true".equalsIgnoreCase(s);
         s = this.get(args, "compressHtmlTemplate");
         this.compressHtmlTemplate = "true".equalsIgnoreCase(s);
         s = this.get(args, "optimizeJavaExpression");
         this.optimizeJavaExpression = "true".equalsIgnoreCase(s);
         s = this.get(args, "compiler");
         if (s != null) {
            this.compiler = s;
         }

         s = this.get(args, "source");
         if (s != null) {
            this.source = s;
         }

         s = this.get(args, "target");
         if (s != null) {
            this.target = s;
         }

         String compilerclass = this.get(args, "compilerclass");
         if (compilerclass == null) {
            this.compileropt = "-compiler";
            this.compilerval = this.compileCommand = this.get(args, "compileCommand");
         } else {
            this.compileropt = "-compilerclass";
            this.compilerval = this.compilerClass = compilerclass;
         }

         this.superclassName = this.get(args, "superclass");
         this.encoding = this.get(args, "encoding");
         if ((this.defaultFilename = this.get(args, "defaultFilename")) == null) {
            this.defaultFilename = "index.jsp";
         }

         if ((this.packagePrefix = this.get(args, "packagePrefix")) == null) {
            this.packagePrefix = "jsp_servlet";
         }

         s = this.get(args, "backwardCompatible");
         this.backwardCompatible = "true".equalsIgnoreCase(s);
         s = this.get(args, "rtexprvalueJspParamName");
         this.isRtexprvalueJspParamName = "true".equalsIgnoreCase(s);
         this.printNulls = !"false".equalsIgnoreCase(this.get(args, "printNulls"));
         this.exactMapping = !"false".equalsIgnoreCase(this.get(args, "exactMapping"));
         this.jspServlet = this.get(args, "jspServlet");
      }
   }

   public static boolean checkCompilerClass() {
      if (compilerClassExists != null) {
         return compilerClassExists == Boolean.TRUE;
      } else {
         try {
            Class c = Class.forName("com.sun.tools.javac.Main");
            if (c != null) {
               compilerClassExists = Boolean.TRUE;
            } else {
               compilerClassExists = Boolean.FALSE;
            }
         } catch (Throwable var1) {
            HTTPLogger.logUnableToLoadDefaultCompilerClass("com.sun.tools.javac.Main", var1);
            compilerClassExists = Boolean.FALSE;
         }

         return compilerClassExists == Boolean.TRUE;
      }
   }

   private static String getDefaultJSPServlet() {
      return "weblogic.servlet.JSPServlet";
   }

   private String get(Map args, String name) {
      return this.trim((String)args.get(name));
   }

   private String get(ServletConfig config, String name) {
      return this.trim(config.getInitParameter(name));
   }

   private String trim(String ret) {
      if (ret == null) {
         return null;
      } else {
         ret = ret.trim();
         return ret.length() == 0 ? null : ret;
      }
   }

   public static String[] parseFlags(String flagsStr) {
      if (flagsStr == null) {
         return new String[0];
      } else {
         StringTokenizer st = new StringTokenizer(flagsStr, " \n\t\r", false);
         String[] ret = new String[st.countTokens()];

         for(int i = 0; st.hasMoreTokens(); ret[i++] = st.nextToken()) {
         }

         return ret;
      }
   }

   public void setValuesFromInitArgs(ServletConfig config) {
      String str = this.get(config, "defaultFilename");
      if (str != null) {
         this.defaultFilename = str;
      }

      str = this.get(config, "verbose");
      if (str != null) {
         this.verbose = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "noTryBlocks");
      if (str != null) {
         this.noTryBlocks = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "debug");
      if (str != null) {
         this.debugEnabled = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "keepgenerated");
      if (str != null) {
         this.keepgenerated = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "backwardCompatible");
      if (str != null) {
         this.backwardCompatible = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "rtexprvalueJspParamName");
      if (str != null) {
         this.isRtexprvalueJspParamName = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "printNulls");
      if (str != null) {
         this.printNulls = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "exactMapping");
      if (str != null) {
         this.exactMapping = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "compilerSupportsEncoding");
      if (str != null) {
         this.compilerSupportsEncoding = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "precompileContinue");
      if (str != null) {
         this.precompileContinue = "true".equalsIgnoreCase(str);
      }

      str = this.get(config, "compileFlags");
      if (str != null) {
         this.compileFlagsString = str;
         this.compileFlags = parseFlags(str);
      }

      str = this.get(config, "pageCheckSeconds");
      if (str != null) {
         try {
            this.pageCheckSeconds = Long.parseLong(str);
         } catch (NumberFormatException var4) {
         }
      }

      str = this.get(config, "packagePrefix");
      if (str != null) {
         this.packagePrefix = str;
      }

      str = this.get(config, "compilerclass");
      if (str != null) {
         this.compileropt = "-compilerclass";
         this.compilerClass = this.compilerval = str;
      } else {
         str = this.get(config, "compileCommand");
         if (str != null) {
            this.compileropt = "-compiler";
            this.compilerval = this.compileCommand = str;
         }
      }

      str = this.get(config, "workingDir");
      if (str != null) {
         this.workingDir = str;
      }

      str = this.get(config, "superclass");
      if (str != null) {
         this.superclassName = str;
      }

      str = this.get(config, "encoding");
      if (str != null) {
         this.encoding = str;
      }

      str = this.get(config, "jspServlet");
      if (str != null) {
         this.jspServlet = str;
      }

   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public String getPackagePrefix() {
      return this.packagePrefix;
   }

   public String getCompiler() {
      return this.compiler;
   }

   public String getCompileropt() {
      return this.compileropt;
   }

   public String getCompilerClass() {
      return this.compilerClass;
   }

   public String getWorkingDir() {
      return this.workingDir;
   }

   public long getPageCheckSecs() {
      return this.pageCheckSeconds;
   }

   public String getSuperClassName() {
      return this.superclassName;
   }

   public boolean isKeepGenerated() {
      return this.keepgenerated;
   }

   public boolean useByteBuffer() {
      return this.useByteBuffer;
   }

   public boolean isPrecompileContinue() {
      return this.precompileContinue;
   }

   public boolean isCompilerSupportsEncoding() {
      return this.compilerSupportsEncoding;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public String getDefaultFilename() {
      return this.defaultFilename;
   }

   public String[] getCompileFlags() {
      return this.compileFlags;
   }

   public String getCompileFlagsString() {
      return this.compileFlagsString;
   }

   public boolean isNoTryBlocks() {
      return this.noTryBlocks;
   }

   public boolean isDebugEnabled() {
      return this.debugEnabled;
   }

   public boolean isBackwardCompatible() {
      return this.backwardCompatible;
   }

   public boolean isRtexprvalueJspParamName() {
      return this.isRtexprvalueJspParamName;
   }

   public boolean isPrintNulls() {
      return this.printNulls;
   }

   public boolean isExactMapping() {
      return this.exactMapping;
   }

   public String getJspServlet() {
      return this.jspServlet;
   }

   public boolean isCompressHtmlTemplate() {
      return this.compressHtmlTemplate;
   }

   public boolean isOptimizeJavaExpression() {
      return this.optimizeJavaExpression;
   }

   public boolean isStrictStaleCheck() {
      return this.isStrictStaleCheck;
   }

   public boolean isStrictJspDocumentValidation() {
      return this.strictJspDocumentValidation;
   }

   public String getCompilerval() {
      return this.compilerval == null ? this.getCompileCommand() : this.compilerval;
   }

   public String getCompileCommand() {
      if (this.compileCommand != null && this.compileCommand.length() > 0) {
         return this.compileCommand;
      } else {
         String systemDefault = null;
         if (Kernel.isServer()) {
            ServerMBean svr = ManagementService.getRuntimeAccess(kernelId).getServer();
            if (svr != null) {
               systemDefault = svr.getJavaCompiler();
            }
         }

         if (systemDefault == null || systemDefault.length() == 0) {
            systemDefault = "javac";
         }

         return systemDefault;
      }
   }

   public void setCompileCommand(String compiler) {
      this.compileropt = "-compiler";
      this.compilerval = this.compileCommand = compiler;
   }

   public void setVerbose(boolean b) {
      this.verbose = b;
   }

   public void setKeepGenerated(boolean b) {
      this.keepgenerated = b;
   }

   public void setUseByteBuffer(boolean b) {
      this.useByteBuffer = b;
   }

   public void setDebugEnabled(boolean b) {
      this.debugEnabled = b;
   }

   public void setCompressHtmlTemplate(boolean b) {
      this.compressHtmlTemplate = b;
   }

   public void setOptimizeJavaExpression(boolean b) {
      this.optimizeJavaExpression = b;
   }

   public void setPageCheckSecs(long secs) {
      this.pageCheckSeconds = secs;
   }

   public void setStrictJspDocumentValidation(boolean b) {
      this.strictJspDocumentValidation = b;
   }

   public void setWorkingDir(String workingDir) {
      if (workingDir == null) {
         throw new IllegalArgumentException("JSP 'workingDir' cannot be null");
      } else {
         this.workingDir = workingDir;
      }
   }

   public void setCompilerClass(String compCl) {
      this.compileropt = "-compilerclass";
      this.compilerval = this.compilerClass = compCl;
   }

   public void setPrecompileContinue(boolean flag) {
      this.precompileContinue = flag;
   }

   public void setNoTryBlocks(boolean flag) {
      this.noTryBlocks = flag;
   }

   public void setCompilerSupportsEncoding(boolean flag) {
      this.compilerSupportsEncoding = flag;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setCompileFlagsString(String compileFlagsString) {
      this.compileFlagsString = compileFlagsString;
      this.compileFlags = parseFlags(compileFlagsString);
   }

   public void setExactMapping(boolean flag) {
      this.exactMapping = flag;
   }

   public static boolean isJspServletValid(String ctx, String className, ClassLoader cl) throws DeploymentException {
      if (className == null) {
         return false;
      } else if (className.equals(DEFAULT_JSP_SERVLET)) {
         return true;
      } else if (cl == null) {
         return true;
      } else {
         try {
            Class clazz = cl.loadClass(className);
            Class base = cl.loadClass("javax.servlet.http.HttpServlet");
            boolean isAssignable = base.isAssignableFrom(clazz);
            if (!isAssignable) {
               Loggable l = HTTPLogger.logInvalidJspServletLoggable(ctx, className);
               throw new DeploymentException(l.getMessage());
            } else {
               return isAssignable;
            }
         } catch (Exception var7) {
            Loggable l = HTTPLogger.logUnableToLoadJspServletClassLoggable(ctx, className, var7);
            l.log();
            throw new DeploymentException(l.getMessage(), var7);
         }
      }
   }

   public String toString() {
      String _compileFlags = this.getCompileFlagsString();
      if (_compileFlags == null) {
         _compileFlags = "";
      }

      return "[JspConfig: verbose=" + this.isVerbose() + ",packagePrefix=" + this.getPackagePrefix() + "," + this.compileropt + "=" + this.compilerval + ",compileFlags=" + _compileFlags + ",compier=" + this.compiler + ",source=" + this.source + ",target=" + this.target + ",workingDir=" + this.getWorkingDir() + ",pageCheckSeconds=" + this.getPageCheckSecs() + ",superclass=" + this.getSuperClassName() + ",keepgenerated=" + this.isKeepGenerated() + ",precompileContinue=" + this.isPrecompileContinue() + ",compilerSupportsEncoding=" + this.isCompilerSupportsEncoding() + ",encoding=" + this.getEncoding() + ",defaultfilename=" + this.getDefaultFilename() + ",compilerclass=" + this.getCompilerClass() + ",noTryBlocks=" + this.isNoTryBlocks() + ",debugEnabled=" + this.isDebugEnabled() + ",printNulls=" + this.isPrintNulls() + ",jspServlet=" + this.getJspServlet() + "]";
   }

   public static ExpressionEvaluator getExpressionEvaluator() {
      return new ExpressionEvaluatorImpl();
   }

   public static VariableResolver getVariableResolver(PageContextImpl pc) {
      return new VariableResolverImpl(pc);
   }

   public static String getJspPrecompilerClass() {
      return "weblogic.servlet.jsp.JavelinxJspPrecompiler";
   }

   public static ExpressionFactory getExpressionFactory(boolean el22Compatible) {
      return ELFactory.getInstance().createRuntimeExpressionFactory(el22Compatible);
   }

   public static VariableMapper getVariableMapper() {
      return ELFactory.getInstance().createVariableMapper();
   }

   static {
      PRODUCTION_MODE = !Kernel.isServer() || ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled();
   }
}
