package org.mozilla.javascript;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebugReader;
import org.mozilla.javascript.debug.DebuggableEngine;
import org.mozilla.javascript.debug.Debugger;

public class Context {
   public static final String languageVersionProperty = "language version";
   public static final String errorReporterProperty = "error reporter";
   public static final int VERSION_UNKNOWN = -1;
   public static final int VERSION_DEFAULT = 0;
   public static final int VERSION_1_0 = 100;
   public static final int VERSION_1_1 = 110;
   public static final int VERSION_1_2 = 120;
   public static final int VERSION_1_3 = 130;
   public static final int VERSION_1_4 = 140;
   public static final int VERSION_1_5 = 150;
   public static final int FEATURE_NON_ECMA_GET_YEAR = 1;
   static final String defaultResource = "org.mozilla.javascript.resources.Messages";
   static final boolean printTrees = false;
   private static Class codegenClass;
   private static ClassNameHelper nameHelper;
   private static boolean requireSecurityDomain;
   private static boolean resourceMissing;
   static final String securityResourceName = "org.mozilla.javascript.resources.Security";
   static final boolean useJSObject = false;
   NativeCall currentActivation;
   Hashtable iterating;
   Object interpreterSecurityDomain;
   int version;
   int errorCount;
   static boolean isCachingEnabled;
   private SecuritySupport securitySupport;
   private ErrorReporter errorReporter;
   private Thread currentThread;
   private static Hashtable threadContexts;
   private RegExpProxy regExpProxy;
   private Locale locale;
   private boolean generatingDebug;
   private boolean generatingDebugChanged;
   private boolean generatingSource = true;
   private boolean compileFunctionsWithDynamicScopeFlag;
   private int optimizationLevel;
   WrapHandler wrapHandler;
   Debugger debugger;
   DebuggableEngine debuggableEngine;
   boolean inLineStepMode;
   Stack frameStack;
   private int enterCount;
   private Object[] listeners;
   private Hashtable hashtable;
   private Hashtable activationNames;
   private static final Object staticDataLock;
   private static Object[] contextListeners;
   int interpreterLine;
   String interpreterSourceFile;
   int instructionCount;
   int instructionThreshold;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Interpreter;

   static {
      try {
         codegenClass = Class.forName("org.mozilla.javascript.optimizer.Codegen");
         Class var0 = Class.forName("org.mozilla.javascript.optimizer.OptClassNameHelper");
         nameHelper = (ClassNameHelper)var0.newInstance();
      } catch (ClassNotFoundException var4) {
         codegenClass = null;
      } catch (IllegalAccessException var5) {
         codegenClass = null;
      } catch (InstantiationException var6) {
         codegenClass = null;
      }

      requireSecurityDomain = true;
      resourceMissing = false;

      try {
         ResourceBundle var7 = ResourceBundle.getBundle("org.mozilla.javascript.resources.Security");
         String var1 = var7.getString("security.requireSecurityDomain");
         requireSecurityDomain = var1.equals("true");
      } catch (MissingResourceException var2) {
         requireSecurityDomain = true;
         resourceMissing = true;
      } catch (SecurityException var3) {
         requireSecurityDomain = true;
      }

      isCachingEnabled = true;
      threadContexts = new Hashtable(11);
      staticDataLock = new Object();
   }

   public Context() {
      this.init();
   }

   public Context(SecuritySupport var1) {
      this.securitySupport = var1;
      this.init();
   }

   public void addActivationName(String var1) {
      if (this.activationNames == null) {
         this.activationNames = new Hashtable(5);
      }

      this.activationNames.put(var1, var1);
   }

   public static void addContextListener(ContextListener var0) {
      Object var1 = staticDataLock;
      synchronized(var1){}

      try {
         contextListeners = ListenerArray.add(contextListeners, var0);
      } catch (Throwable var3) {
         throw var3;
      }

   }

   public void addPropertyChangeListener(PropertyChangeListener var1) {
      synchronized(this){}

      try {
         this.listeners = ListenerArray.add(this.listeners, var1);
      } catch (Throwable var4) {
         throw var4;
      }

   }

   public static final void checkSecurityDomainRequired() {
      if (requireSecurityDomain) {
         String var0 = "Required security context not found";
         if (resourceMissing) {
            var0 = var0 + ". Didn't find properties file at org.mozilla.javascript.resources.Security";
         }

         throw new SecurityException(var0);
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   private Object compile(Scriptable var1, Reader var2, String var3, int var4, Object var5, boolean var6) throws IOException {
      if (this.debugger != null && var2 != null) {
         var2 = new DebugReader((Reader)var2);
      }

      TokenStream var7 = new TokenStream((Reader)var2, var1, var3, var4);
      return this.compile(var1, var7, var5, (Reader)var2, var6);
   }

   private Object compile(Scriptable var1, TokenStream var2, Object var3, Reader var4, boolean var5) throws IOException {
      Interpreter var6 = this.optimizationLevel == -1 ? new Interpreter() : this.getCompiler();
      this.errorCount = 0;
      IRFactory var7 = var6.createIRFactory(var2, nameHelper, var1);
      Parser var8 = new Parser(var7);
      Node var9 = (Node)var8.parse(var2);
      if (var9 == null) {
         return null;
      } else {
         var9 = var6.transform(var9, var2, var1);
         if (var5) {
            Node var10 = var9.getFirstChild();
            if (var10 == null) {
               return null;
            }

            var9 = (Node)var10.getProp(5);
            if (var9 == null) {
               return null;
            }
         }

         if (var4 instanceof DebugReader) {
            DebugReader var11 = (DebugReader)var4;
            var9.putProp(31, var11.getSaved());
         }

         Object var12 = var6.compile(this, var1, var9, var3, this.securitySupport, nameHelper);
         return this.errorCount == 0 ? var12 : null;
      }
   }

   public Function compileFunction(Scriptable var1, String var2, String var3, int var4, Object var5) {
      StringReader var6 = new StringReader(var2);

      try {
         return (Function)this.compile(var1, var6, var3, var4, var5, true);
      } catch (IOException var7) {
         throw new RuntimeException();
      }
   }

   public Script compileReader(Scriptable var1, Reader var2, String var3, int var4, Object var5) throws IOException {
      return (Script)this.compile(var1, var2, var3, var4, var5, false);
   }

   public String decompileFunction(Function var1, int var2) {
      return var1 instanceof BaseFunction ? ((BaseFunction)var1).decompile(this, var2, false) : "function " + var1.getClassName() + "() {\n\t[native code]\n}\n";
   }

   public String decompileFunctionBody(Function var1, int var2) {
      return var1 instanceof BaseFunction ? ((BaseFunction)var1).decompile(this, var2, true) : "[native code]\n";
   }

   public String decompileScript(Script var1, Scriptable var2, int var3) {
      NativeScript var4 = (NativeScript)var1;
      var4.initScript(var2);
      return var4.decompile(this, var3, false);
   }

   public static Context enter() {
      return enter((Context)null);
   }

   public static Context enter(Context var0) {
      Thread var1 = Thread.currentThread();
      Context var2 = (Context)threadContexts.get(var1);
      if (var2 != null) {
         synchronized(var2){}

         try {
            ++var2.enterCount;
         } catch (Throwable var9) {
            throw var9;
         }
      } else if (var0 != null) {
         synchronized(var0){}

         try {
            if (var0.currentThread == null) {
               var0.currentThread = var1;
               threadContexts.put(var1, var0);
               ++var0.enterCount;
            }
         } catch (Throwable var10) {
            throw var10;
         }

         var2 = var0;
      } else {
         var2 = new Context();
         var2.currentThread = var1;
         threadContexts.put(var1, var2);
         var2.enterCount = 1;
      }

      Object[] var3 = contextListeners;
      if (var3 != null) {
         int var4 = var3.length;

         while(var4-- != 0) {
            ((ContextListener)var3[var4]).contextEntered(var2);
         }
      }

      return var2;
   }

   public Object evaluateReader(Scriptable var1, Reader var2, String var3, int var4, Object var5) throws IOException, JavaScriptException {
      Script var6 = this.compileReader(var1, var2, var3, var4, var5);
      return var6 != null ? var6.exec(this, var1) : null;
   }

   public Object evaluateString(Scriptable var1, String var2, String var3, int var4, Object var5) throws JavaScriptException {
      try {
         StringReader var6 = new StringReader(var2);
         return this.evaluateReader(var1, var6, var3, var4, var5);
      } catch (IOException var7) {
         throw new RuntimeException();
      }
   }

   public static void exit() {
      Context var0 = getCurrentContext();
      boolean var1 = false;
      if (var0 != null) {
         synchronized(var0){}

         try {
            if (--var0.enterCount == 0) {
               threadContexts.remove(var0.currentThread);
               var0.currentThread = null;
               var1 = true;
            }
         } catch (Throwable var6) {
            throw var6;
         }

         Object[] var2 = contextListeners;
         if (var2 != null) {
            int var3 = var2.length;

            while(var3-- != 0) {
               ContextListener var4 = (ContextListener)var2[var3];
               var4.contextExited(var0);
               if (var1) {
                  var4.contextReleased(var0);
               }
            }
         }
      }

   }

   void firePropertyChange(String var1, Object var2, Object var3) {
      Object[] var4 = this.listeners;
      if (var4 != null) {
         this.firePropertyChangeImpl(var4, var1, var2, var3);
      }

   }

   private void firePropertyChangeImpl(Object[] var1, String var2, Object var3, Object var4) {
      int var5 = var1.length;

      while(var5-- != 0) {
         Object var6 = var1[var5];
         if (var6 instanceof PropertyChangeListener) {
            PropertyChangeListener var7 = (PropertyChangeListener)var6;
            var7.propertyChange(new PropertyChangeEvent(this, var2, var3, var4));
         }
      }

   }

   public ClassOutput getClassOutput() {
      return nameHelper == null ? null : nameHelper.getClassOutput();
   }

   private Interpreter getCompiler() {
      if (codegenClass != null) {
         try {
            return (Interpreter)codegenClass.newInstance();
         } catch (SecurityException var1) {
         } catch (IllegalArgumentException var2) {
         } catch (InstantiationException var3) {
         } catch (IllegalAccessException var4) {
         }
      }

      return new Interpreter();
   }

   static Context getContext() {
      Thread var0 = Thread.currentThread();
      Context var1 = (Context)threadContexts.get(var0);
      if (var1 == null) {
         throw new RuntimeException("No Context associated with current Thread");
      } else {
         return var1;
      }
   }

   public static Context getCurrentContext() {
      Thread var0 = Thread.currentThread();
      return (Context)threadContexts.get(var0);
   }

   public DebuggableEngine getDebuggableEngine() {
      if (this.debuggableEngine == null) {
         this.debuggableEngine = new DebuggableEngineImpl(this);
      }

      return this.debuggableEngine;
   }

   public Object[] getElements(Scriptable var1) {
      double var2 = NativeArray.getLengthProperty(var1);
      if (var2 != var2) {
         return null;
      } else {
         int var4 = (int)var2;
         Object[] var5 = new Object[var4];

         for(int var6 = 0; var6 < var4; ++var6) {
            Object var7 = var1.get(var6, var1);
            var5[var6] = var7 == Scriptable.NOT_FOUND ? Undefined.instance : var7;
         }

         return var5;
      }
   }

   public ErrorReporter getErrorReporter() {
      if (this.errorReporter == null) {
         this.errorReporter = new DefaultErrorReporter();
      }

      return this.errorReporter;
   }

   public String getImplementationVersion() {
      return "Rhino 1.5 release 2 2001 07 27";
   }

   public int getInstructionObserverThreshold() {
      return this.instructionThreshold;
   }

   public Object getInterpreterSecurityDomain() {
      return this.interpreterSecurityDomain;
   }

   public int getLanguageVersion() {
      return this.version;
   }

   public Locale getLocale() {
      if (this.locale == null) {
         this.locale = Locale.getDefault();
      }

      return this.locale;
   }

   static String getMessage(String var0, Object[] var1) {
      Context var2 = getCurrentContext();
      Locale var3 = var2 != null ? var2.getLocale() : Locale.getDefault();
      ResourceBundle var4 = ResourceBundle.getBundle("org.mozilla.javascript.resources.Messages", var3);

      String var5;
      try {
         var5 = var4.getString(var0);
      } catch (MissingResourceException var7) {
         throw new RuntimeException("no message resource found for message property " + var0);
      }

      MessageFormat var6 = new MessageFormat(var5);
      return var6.format(var1);
   }

   static String getMessage0(String var0) {
      return getMessage(var0, (Object[])null);
   }

   static String getMessage1(String var0, Object var1) {
      Object[] var2 = new Object[]{var1};
      return getMessage(var0, var2);
   }

   static String getMessage2(String var0, Object var1, Object var2) {
      Object[] var3 = new Object[]{var1, var2};
      return getMessage(var0, var3);
   }

   static String getMessage3(String var0, Object var1, Object var2, Object var3) {
      Object[] var4 = new Object[]{var1, var2, var3};
      return getMessage(var0, var4);
   }

   public int getOptimizationLevel() {
      return this.optimizationLevel;
   }

   RegExpProxy getRegExpProxy() {
      if (this.regExpProxy == null) {
         try {
            Class var1 = Class.forName("org.mozilla.javascript.regexp.RegExpImpl");
            this.regExpProxy = (RegExpProxy)var1.newInstance();
            return this.regExpProxy;
         } catch (ClassNotFoundException var2) {
         } catch (InstantiationException var3) {
         } catch (IllegalAccessException var4) {
         }
      }

      return this.regExpProxy;
   }

   Object getSecurityDomainForStackDepth(int var1) {
      Object var2 = null;
      if (this.securitySupport != null) {
         Class[] var3 = this.securitySupport.getClassContext();
         if (var3 != null) {
            int var4;
            if (var1 != -1) {
               var4 = var1 + 1;
               var2 = this.getSecurityDomainFromClass(var3[var4]);
            } else {
               for(var4 = 1; var4 < var3.length; ++var4) {
                  var2 = this.getSecurityDomainFromClass(var3[var4]);
                  if (var2 != null) {
                     break;
                  }
               }
            }
         }
      }

      if (var2 != null) {
         return var2;
      } else {
         if (requireSecurityDomain) {
            checkSecurityDomainRequired();
         }

         return null;
      }
   }

   Object getSecurityDomainFromClass(Class var1) {
      return var1 == (class$org$mozilla$javascript$Interpreter != null ? class$org$mozilla$javascript$Interpreter : (class$org$mozilla$javascript$Interpreter = class$("org.mozilla.javascript.Interpreter"))) ? this.interpreterSecurityDomain : this.securitySupport.getSecurityDomain(var1);
   }

   SecuritySupport getSecuritySupport() {
      return this.securitySupport;
   }

   static String getSourcePositionFromStack(int[] var0) {
      Context var1 = getCurrentContext();
      if (var1 == null) {
         return null;
      } else if (var1.interpreterLine > 0 && var1.interpreterSourceFile != null) {
         var0[0] = var1.interpreterLine;
         return var1.interpreterSourceFile;
      } else {
         CharArrayWriter var2 = new CharArrayWriter();
         RuntimeException var3 = new RuntimeException();
         var3.printStackTrace(new PrintWriter(var2));
         String var4 = var2.toString();
         int var5 = -1;
         int var6 = -1;
         int var7 = -1;

         for(int var8 = 0; var8 < var4.length(); ++var8) {
            char var9 = var4.charAt(var8);
            if (var9 == ':') {
               var7 = var8;
            } else if (var9 == '(') {
               var5 = var8;
            } else if (var9 == ')') {
               var6 = var8;
            } else if (var9 == '\n' && var5 != -1 && var6 != -1 && var7 != -1 && var5 < var7 && var7 < var6) {
               String var10 = var4.substring(var5 + 1, var7);
               if (var10.endsWith(".js")) {
                  String var11 = var4.substring(var7 + 1, var6);

                  try {
                     var0[0] = Integer.parseInt(var11);
                     return var10;
                  } catch (NumberFormatException var12) {
                  }
               }

               var7 = -1;
               var6 = -1;
               var5 = -1;
            }
         }

         return null;
      }
   }

   public String getTargetClassFileName() {
      return nameHelper == null ? null : nameHelper.getTargetClassFileName();
   }

   public String getTargetPackage() {
      return nameHelper == null ? null : nameHelper.getTargetPackage();
   }

   public Object getThreadLocal(Object var1) {
      return this.hashtable == null ? null : this.hashtable.get(var1);
   }

   public static Object getUndefinedValue() {
      return Undefined.instance;
   }

   public WrapHandler getWrapHandler() {
      return this.wrapHandler;
   }

   public boolean hasCompileFunctionsWithDynamicScope() {
      return this.compileFunctionsWithDynamicScopeFlag;
   }

   public boolean hasFeature(int var1) {
      if (var1 != 1) {
         throw new RuntimeException("Bad feature index: " + var1);
      } else {
         return this.version == 100 || this.version == 110 || this.version == 120;
      }
   }

   private void init() {
      this.setLanguageVersion(0);
      this.optimizationLevel = codegenClass != null ? 0 : -1;
      Object[] var1 = contextListeners;
      if (var1 != null) {
         int var2 = var1.length;

         while(var2-- != 0) {
            ((ContextListener)var1[var2]).contextCreated(this);
         }
      }

   }

   public Scriptable initStandardObjects(ScriptableObject var1) {
      return this.initStandardObjects(var1, false);
   }

   public ScriptableObject initStandardObjects(ScriptableObject var1, boolean var2) {
      if (var1 == null) {
         var1 = new NativeObject();
      }

      BaseFunction.init(this, (Scriptable)var1, var2);
      NativeObject.init(this, (Scriptable)var1, var2);
      Scriptable var3 = ScriptableObject.getObjectPrototype((Scriptable)var1);
      Scriptable var4 = ScriptableObject.getFunctionPrototype((Scriptable)var1);
      var4.setPrototype(var3);
      if (((ScriptableObject)var1).getPrototype() == null) {
         ((ScriptableObject)var1).setPrototype(var3);
      }

      NativeError.init(this, (Scriptable)var1, var2);
      NativeGlobal.init(this, (Scriptable)var1, var2);
      NativeArray.init(this, (Scriptable)var1, var2);
      NativeString.init(this, (Scriptable)var1, var2);
      NativeBoolean.init(this, (Scriptable)var1, var2);
      NativeNumber.init(this, (Scriptable)var1, var2);
      NativeDate.init(this, (Scriptable)var1, var2);
      NativeMath.init(this, (Scriptable)var1, var2);
      NativeWith.init(this, (Scriptable)var1, var2);
      NativeCall.init(this, (Scriptable)var1, var2);
      NativeScript.init(this, (Scriptable)var1, var2);
      new LazilyLoadedCtor((ScriptableObject)var1, "RegExp", "org.mozilla.javascript.regexp.NativeRegExp", var2);
      new LazilyLoadedCtor((ScriptableObject)var1, "Packages", "org.mozilla.javascript.NativeJavaPackage", var2);
      new LazilyLoadedCtor((ScriptableObject)var1, "java", "org.mozilla.javascript.NativeJavaPackage", var2);
      new LazilyLoadedCtor((ScriptableObject)var1, "getClass", "org.mozilla.javascript.NativeJavaPackage", var2);
      String var5 = "org.mozilla.javascript.JavaAdapter";
      String var6 = "JavaAdapter";

      try {
         var5 = System.getProperty(var5, var5);
         var6 = System.getProperty("org.mozilla.javascript.JavaAdapterClassName", var6);
      } catch (SecurityException var7) {
      }

      new LazilyLoadedCtor((ScriptableObject)var1, var6, var5, var2);
      return (ScriptableObject)var1;
   }

   public boolean isActivationNeeded(String var1) {
      if ("arguments".equals(var1)) {
         return true;
      } else {
         return this.activationNames != null && this.activationNames.containsKey(var1);
      }
   }

   public boolean isGeneratingDebug() {
      return this.generatingDebug;
   }

   public boolean isGeneratingDebugChanged() {
      return this.generatingDebugChanged;
   }

   public boolean isGeneratingSource() {
      return this.generatingSource;
   }

   public boolean isInterpreterClass(Class var1) {
      return var1 == (class$org$mozilla$javascript$Interpreter != null ? class$org$mozilla$javascript$Interpreter : (class$org$mozilla$javascript$Interpreter = class$("org.mozilla.javascript.Interpreter")));
   }

   public static boolean isSecurityDomainRequired() {
      return requireSecurityDomain;
   }

   final boolean isVersionECMA1() {
      return this.version == 0 || this.version >= 130;
   }

   public Scriptable newArray(Scriptable var1, int var2) {
      NativeArray var3 = new NativeArray((long)var2);
      this.newArrayHelper(var1, var3);
      return var3;
   }

   public Scriptable newArray(Scriptable var1, Object[] var2) {
      NativeArray var3 = new NativeArray(var2);
      this.newArrayHelper(var1, var3);
      return var3;
   }

   private void newArrayHelper(Scriptable var1, Scriptable var2) {
      var2.setParentScope(var1);
      Object var3 = ScriptRuntime.getTopLevelProp(var1, "Array");
      if (var3 != null && var3 instanceof Scriptable) {
         Scriptable var4 = (Scriptable)var3;
         var2.setPrototype((Scriptable)var4.get("prototype", var4));
      }

   }

   public Scriptable newObject(Scriptable var1) throws PropertyException, NotAFunctionException, JavaScriptException {
      return this.newObject(var1, "Object", (Object[])null);
   }

   public Scriptable newObject(Scriptable var1, String var2) throws PropertyException, NotAFunctionException, JavaScriptException {
      return this.newObject(var1, var2, (Object[])null);
   }

   public Scriptable newObject(Scriptable var1, String var2, Object[] var3) throws PropertyException, NotAFunctionException, JavaScriptException {
      Object var4 = ScriptRuntime.getTopLevelProp(var1, var2);
      String var6;
      if (var4 == Scriptable.NOT_FOUND) {
         var6 = getMessage1("msg.ctor.not.found", var2);
         throw new PropertyException(var6);
      } else if (!(var4 instanceof Function)) {
         var6 = getMessage1("msg.not.ctor", var2);
         throw new NotAFunctionException(var6);
      } else {
         Function var5 = (Function)var4;
         return var5.construct(this, var5.getParentScope(), var3 == null ? ScriptRuntime.emptyArgs : var3);
      }
   }

   protected void observeInstructionCount(int var1) {
   }

   void popFrame() {
      this.frameStack.pop();
   }

   void pushFrame(DebugFrame var1) {
      if (this.frameStack == null) {
         this.frameStack = new Stack();
      }

      this.frameStack.push(var1);
   }

   public void putThreadLocal(Object var1, Object var2) {
      if (this.hashtable == null) {
         this.hashtable = new Hashtable();
      }

      this.hashtable.put(var1, var2);
   }

   public void removeActivationName(String var1) {
      if (this.activationNames != null) {
         this.activationNames.remove(var1);
      }

   }

   public static void removeContextListener(ContextListener var0) {
      Object var1 = staticDataLock;
      synchronized(var1){}

      try {
         contextListeners = ListenerArray.remove(contextListeners, var0);
      } catch (Throwable var3) {
         throw var3;
      }

   }

   public void removePropertyChangeListener(PropertyChangeListener var1) {
      synchronized(this){}

      try {
         this.listeners = ListenerArray.remove(this.listeners, var1);
      } catch (Throwable var4) {
         throw var4;
      }

   }

   public void removeThreadLocal(Object var1) {
      if (this.hashtable != null) {
         this.hashtable.remove(var1);
      }
   }

   public static void reportError(String var0) {
      int[] var1 = new int[1];
      String var2 = getSourcePositionFromStack(var1);
      reportError(var0, var2, var1[0], (String)null, 0);
   }

   public static void reportError(String var0, String var1, int var2, String var3, int var4) {
      Context var5 = getCurrentContext();
      if (var5 != null) {
         ++var5.errorCount;
         var5.getErrorReporter().error(var0, var1, var2, var3, var4);
      } else {
         throw new EvaluatorException(var0);
      }
   }

   public static EvaluatorException reportRuntimeError(String var0) {
      int[] var1 = new int[1];
      String var2 = getSourcePositionFromStack(var1);
      return reportRuntimeError(var0, var2, var1[0], (String)null, 0);
   }

   public static EvaluatorException reportRuntimeError(String var0, String var1, int var2, String var3, int var4) {
      Context var5 = getCurrentContext();
      if (var5 != null) {
         ++var5.errorCount;
         return var5.getErrorReporter().runtimeError(var0, var1, var2, var3, var4);
      } else {
         throw new EvaluatorException(var0);
      }
   }

   static EvaluatorException reportRuntimeError0(String var0) {
      return reportRuntimeError(getMessage0(var0));
   }

   static EvaluatorException reportRuntimeError1(String var0, Object var1) {
      return reportRuntimeError(getMessage1(var0, var1));
   }

   static EvaluatorException reportRuntimeError2(String var0, Object var1, Object var2) {
      return reportRuntimeError(getMessage2(var0, var1, var2));
   }

   static EvaluatorException reportRuntimeError3(String var0, Object var1, Object var2, Object var3) {
      return reportRuntimeError(getMessage3(var0, var1, var2, var3));
   }

   public static void reportWarning(String var0) {
      int[] var1 = new int[1];
      String var2 = getSourcePositionFromStack(var1);
      reportWarning(var0, var2, var1[0], (String)null, 0);
   }

   public static void reportWarning(String var0, String var1, int var2, String var3, int var4) {
      Context var5 = getContext();
      var5.getErrorReporter().warning(var0, var1, var2, var3, var4);
   }

   public static void setCachingEnabled(boolean var0) {
      if (isCachingEnabled && !var0) {
         JavaMembers.classTable = new Hashtable();
         nameHelper.reset();
      }

      isCachingEnabled = var0;
      FunctionObject.setCachingEnabled(var0);
   }

   public void setClassOutput(ClassOutput var1) {
      if (nameHelper != null) {
         nameHelper.setClassOutput(var1);
      }

   }

   public void setCompileFunctionsWithDynamicScope(boolean var1) {
      this.compileFunctionsWithDynamicScopeFlag = var1;
   }

   public ErrorReporter setErrorReporter(ErrorReporter var1) {
      ErrorReporter var2 = this.errorReporter;
      Object[] var3 = this.listeners;
      if (var3 != null && this.errorReporter != var1) {
         this.firePropertyChangeImpl(var3, "error reporter", this.errorReporter, var1);
      }

      this.errorReporter = var1;
      return var2;
   }

   public void setGeneratingDebug(boolean var1) {
      this.generatingDebugChanged = true;
      if (var1) {
         this.setOptimizationLevel(0);
      }

      this.generatingDebug = var1;
   }

   public void setGeneratingSource(boolean var1) {
      this.generatingSource = var1;
   }

   public void setInstructionObserverThreshold(int var1) {
      this.instructionThreshold = var1;
   }

   public void setLanguageVersion(int var1) {
      Object[] var2 = this.listeners;
      if (var2 != null && var1 != this.version) {
         this.firePropertyChangeImpl(var2, "language version", new Integer(this.version), new Integer(var1));
      }

      this.version = var1;
   }

   public Locale setLocale(Locale var1) {
      Locale var2 = this.locale;
      this.locale = var1;
      return var2;
   }

   public void setOptimizationLevel(int var1) {
      if (var1 < 0) {
         var1 = -1;
      } else if (var1 > 9) {
         var1 = 9;
      }

      if (codegenClass == null) {
         var1 = -1;
      }

      this.optimizationLevel = var1;
   }

   public synchronized void setSecuritySupport(SecuritySupport var1) {
      if (this.securitySupport != null) {
         throw new SecurityException("Cannot overwrite existing SecuritySupport object");
      } else {
         this.securitySupport = var1;
      }
   }

   public void setTargetClassFileName(String var1) {
      if (nameHelper != null) {
         nameHelper.setTargetClassFileName(var1);
      }

   }

   public void setTargetExtends(Class var1) {
      if (nameHelper != null) {
         nameHelper.setTargetExtends(var1);
      }

   }

   public void setTargetImplements(Class[] var1) {
      if (nameHelper != null) {
         nameHelper.setTargetImplements(var1);
      }

   }

   public void setTargetPackage(String var1) {
      if (nameHelper != null) {
         nameHelper.setTargetPackage(var1);
      }

   }

   public void setWrapHandler(WrapHandler var1) {
      this.wrapHandler = var1;
   }

   public synchronized boolean stringIsCompilableUnit(String var1) {
      StringReader var2 = new StringReader(var1);
      TokenStream var3 = new TokenStream(var2, (Scriptable)null, (String)null, 1);
      ErrorReporter var4 = this.setErrorReporter(new DefaultErrorReporter());
      boolean var5 = false;

      try {
         IRFactory var8 = new IRFactory(var3, (Scriptable)null);
         Parser var9 = new Parser(var8);
         var9.parse(var3);
      } catch (IOException var13) {
         var5 = true;
      } catch (EvaluatorException var14) {
         var5 = true;
      } finally {
         this.setErrorReporter(var4);
      }

      return !var5 || !var3.eof();
   }

   public static boolean toBoolean(Object var0) {
      return ScriptRuntime.toBoolean(var0);
   }

   public static double toNumber(Object var0) {
      return ScriptRuntime.toNumber(var0);
   }

   public static Scriptable toObject(Object var0, Scriptable var1) {
      return ScriptRuntime.toObject(var1, var0, (Class)null);
   }

   public static Scriptable toObject(Object var0, Scriptable var1, Class var2) {
      return var0 == null && var2 != null ? null : ScriptRuntime.toObject(var1, var0, var2);
   }

   public static String toString(Object var0) {
      return ScriptRuntime.toString(var0);
   }
}
