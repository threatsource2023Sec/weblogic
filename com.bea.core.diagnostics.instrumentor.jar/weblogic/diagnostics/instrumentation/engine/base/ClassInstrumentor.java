package weblogic.diagnostics.instrumentation.engine.base;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationException;
import weblogic.diagnostics.instrumentation.InstrumentationStatistics;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;

class ClassInstrumentor implements InstrumentationEngineConstants {
   private String className;
   private String auxClassName;
   private InstrumentorEngineBase engine;
   private MonitorSpecificationBase[] monitorSpecifications;
   private ClassLoader classLoader;
   private byte[] originalClassBytes;
   private byte[] modifiedClassBytes;
   private ClassAnalyzer classAnalyzer;
   private ClassInfo classInfo;
   private Map eligibleCallsitesMap;
   private Map eligibleMethodsMap;
   private Map eligibleCatchblocksMap;
   private Map callsitePointcutHandlingMaps;
   private Map methodPointcutHandlingMaps;
   private List joinPointList;
   private int joinPointId;
   private Map linenumberMap;
   private boolean hasStaticInitializer;
   private boolean hasSUID;
   private Map overflowedMethodsMap;
   private boolean modified;
   private int callJoinpointCount;
   private int execJoinpointCount;
   private int catchJoinpointCount;
   private int errorCount;
   private Map emittedEntitiesMap;
   private static long auxClassId = System.currentTimeMillis();
   private static URL codebaseURL = createCodebaseURL();
   private boolean stackmapFrameGeneration;
   private int useLocalHolder;
   private boolean sensitiveOptimize;
   private boolean hotSwapWithNoAux;
   private int auxHolderClassIndex;
   private Class supportClass;
   private Method createJoinPointMethod;
   private Method getMonitorMethod;
   private static String TARGETED_PATTERN;
   private static String TARGETED_CLASSES;
   private static final boolean targetedDebugConfigured = initializeTargetedDebug();
   private static volatile boolean targetedDebugEnabled = false;

   public boolean getStackmapFrameGeneration() {
      return this.stackmapFrameGeneration;
   }

   public void setStackmapFrameGeneration(boolean stackmapFrameGeneration) {
      this.stackmapFrameGeneration = stackmapFrameGeneration;
   }

   int getAuxHolderClassIndex() {
      return this.auxHolderClassIndex;
   }

   boolean isHotSwapWithNoAux() {
      return this.hotSwapWithNoAux;
   }

   public boolean getSensitiveOptimize() {
      return this.sensitiveOptimize;
   }

   private static URL createCodebaseURL() {
      URL url = null;
      CodeSource cs = ClassInstrumentor.class.getProtectionDomain().getCodeSource();
      if (cs != null) {
         url = cs.getLocation();
      } else {
         File lib = new File("lib");

         try {
            url = lib.toURL();
         } catch (Exception var4) {
            url = null;
         }
      }

      return url;
   }

   ClassInstrumentor(ClassLoader classLoader, InstrumentorEngineBase engine, String className, byte[] buf) {
      this(classLoader, engine, className, (MonitorSpecificationBase[])null, buf, 2, true);
   }

   ClassInstrumentor(ClassLoader classLoader, InstrumentorEngineBase engine, String className, MonitorSpecificationBase[] monitorSpecifications, byte[] buf, int useLocalHolder, boolean sensitiveOptimize) {
      this.eligibleCallsitesMap = new HashMap();
      this.eligibleMethodsMap = new HashMap();
      this.eligibleCatchblocksMap = new HashMap();
      this.callsitePointcutHandlingMaps = new HashMap();
      this.methodPointcutHandlingMaps = new HashMap();
      this.joinPointList = new ArrayList();
      this.linenumberMap = new HashMap();
      this.emittedEntitiesMap = new HashMap();
      this.stackmapFrameGeneration = true;
      this.useLocalHolder = 2;
      this.sensitiveOptimize = true;
      this.auxHolderClassIndex = -1;
      this.supportClass = null;
      this.createJoinPointMethod = null;
      if (monitorSpecifications == null) {
         monitorSpecifications = engine.getMonitorSpecifications(className);
      }

      this.classLoader = classLoader;
      this.engine = engine;
      this.className = className;
      this.monitorSpecifications = monitorSpecifications;
      this.originalClassBytes = buf;
      this.sensitiveOptimize = sensitiveOptimize;
      this.useLocalHolder = useLocalHolder;
      this.hotSwapWithNoAux = engine.getHotSwapWithNoAux();
      this.auxClassName = className;
      if (engine.isHotswapAllowed() && (!this.hotSwapWithNoAux || this.hotSwapWithNoAux && useLocalHolder != 1)) {
         this.auxClassName = generateAuxClassName(className);
      }

   }

   private static synchronized String generateAuxClassName(String className) {
      ++auxClassId;
      int index = className.lastIndexOf(47);
      if (index < 0) {
         className = "_WLDF$INST_AUX_CLASS_" + className;
      } else {
         className = className.substring(0, index + 1) + "_WLDF$INST_AUX_CLASS_" + className.substring(index + 1, className.length());
      }

      return className + "_" + auxClassId;
   }

   byte[] getBytes() throws InvalidPointcutException {
      if (this.classInfo == null) {
         this.processClass();
      }

      InstrumentationStatistics stats = this.engine.getInstrumentationStatistics();
      stats.incrementCallJoinpointCount(this.callJoinpointCount);
      stats.incrementExecutionJoinpointCount(this.execJoinpointCount);
      stats.incrementCatchJoinpointCount(this.catchJoinpointCount);
      return this.modifiedClassBytes;
   }

   String getClassName() {
      return this.className;
   }

   String getAuxClassName() {
      return this.auxClassName;
   }

   void generateAuxClass() {
      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("ClassInstrumentor.generateAuxClass: generating aux class: " + this.auxClassName);
      }

      GenericClassLoader gcl = this.getScopeRootClassloader((GenericClassLoader)this.classLoader);
      ClassWriter auxClassVisitor = new ClassLoaderAwareClassWriter(1, gcl, this.className, this.engine);
      auxClassVisitor.visit(48, 49, this.auxClassName, (String)null, "java/lang/Object", (String[])null);
      MethodVisitor auxCodeVisitor = auxClassVisitor.visitMethod(0, "<init>", "()V", (String)null, (String[])null);
      auxCodeVisitor.visitVarInsn(25, 0);
      auxCodeVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", "()V");
      auxCodeVisitor.visitInsn(177);
      auxCodeVisitor.visitMaxs(1, 1);
      auxCodeVisitor = auxClassVisitor.visitMethod(8, "<clinit>", "()V", (String)null, (String[])null);
      this.emitInitializerCode(auxClassVisitor, auxCodeVisitor);
      auxCodeVisitor.visitInsn(177);
      auxCodeVisitor.visitMaxs(0, 0);
      auxClassVisitor.visitEnd();
      byte[] auxClassBytes = auxClassVisitor.toByteArray();

      try {
         Class auxClz = gcl.defineCodeGenClass(this.auxClassName.replace('/', '.'), auxClassBytes, codebaseURL);
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("generateAuxClass: auxClz=" + auxClz);
         }
      } catch (Throwable var6) {
         if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("generateAuxClass unexpected failure: ", var6);
         }

         this.reportError();
      }

   }

   private GenericClassLoader getScopeRootClassloader(GenericClassLoader gcl) {
      while(true) {
         if (gcl != null) {
            Annotation anno = gcl.getAnnotation();
            if (anno != null && anno.getApplicationName() != null) {
               return gcl;
            }

            ClassLoader parent = gcl.getParent();
            if (parent != null && parent instanceof GenericClassLoader) {
               gcl = (GenericClassLoader)parent;
               continue;
            }

            return gcl;
         }

         return gcl;
      }
   }

   static boolean isTargetedDebugEnabled() {
      return targetedDebugEnabled;
   }

   boolean wouldClassBeInstrumented() throws InvalidPointcutException, InstrumentationException {
      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("ClassInstrumentor.wouldClassBeInstrumented: " + this.className);
      }

      this.updateTargetedDebug();
      this.classAnalyzer = new ClassAnalyzer(this.originalClassBytes);
      this.classInfo = this.classAnalyzer.getClassInfo();
      if (!this.classInfo.isValid()) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("ClassInstrumentor.wouldClassBeInstrumented: " + this.className + ", invalid ClassInfo: " + this.classInfo.toString());
         }

         throw new InstrumentationException("Invalid ClassInfo for " + this.className + ", unable to use it for analysis");
      } else {
         this.classInfo.markStartBeingInstrumented();
         this.engine.putClassInfo(this.className, this.classLoader, this.classInfo, (URL[])null);
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED isEligible callsites for: " + this.className);
         }

         this.eligibleCallsitesMap = this.classAnalyzer.getEligibleCallsites(this, this.monitorSpecifications);
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED isEligible methods for: " + this.className);
         }

         this.eligibleMethodsMap = this.classAnalyzer.getEligibleMethods(this, this.monitorSpecifications);
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED isEligible exceptions for: " + this.className);
         }

         this.eligibleCatchblocksMap = this.classAnalyzer.getEligibleExceptions(this, this.monitorSpecifications);
         this.classInfo.setSourceFileName(this.classAnalyzer.getSourceFileName());
         if (this.eligibleCallsitesMap.size() == 0 && this.eligibleMethodsMap.size() == 0 && this.eligibleCatchblocksMap.size() == 0) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               if (targetedDebugEnabled) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED no eligible matches for: " + this.className);
               }

               InstrumentationDebug.DEBUG_LOGGER.debug(Thread.currentThread().getId() + "ClassInstrumentor.wouldClassBeInstrumented: completed for " + this.className);
            }

            this.markEndBeingInstrumented();
            return false;
         } else {
            if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED Eligible matches found for: " + this.className);
            }

            this.markEndBeingInstrumented();
            return true;
         }
      }
   }

   private void processClass() throws InvalidPointcutException {
      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("ClassInstrumentor.processClass: " + this.className);
      }

      this.updateTargetedDebug();
      this.classAnalyzer = new ClassAnalyzer(this.originalClassBytes);
      this.classInfo = this.classAnalyzer.getClassInfo();
      this.classInfo.markStartBeingInstrumented();
      if (this.classInfo.isValid()) {
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED Putting classInfo for: " + this.className + ", cl: " + (this.classLoader == null ? "boostrap classloader" : this.classLoader.getClass().getName() + "@" + this.classLoader.hashCode()) + " classInfo: " + this.classInfo.toString());
         }

         this.engine.putClassInfo(this.className, this.classLoader, this.classInfo, (URL[])null);
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED isEligible callsites for: " + this.className);
         }

         this.eligibleCallsitesMap = this.classAnalyzer.getEligibleCallsites(this, this.monitorSpecifications);
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED isEligible methods for: " + this.className);
         }

         this.eligibleMethodsMap = this.classAnalyzer.getEligibleMethods(this, this.monitorSpecifications);
         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED isEligible exceptions for: " + this.className);
         }

         this.eligibleCatchblocksMap = this.classAnalyzer.getEligibleExceptions(this, this.monitorSpecifications);
         this.classInfo.setSourceFileName(this.classAnalyzer.getSourceFileName());
         if (this.eligibleCallsitesMap.size() == 0 && this.eligibleMethodsMap.size() == 0 && this.eligibleCatchblocksMap.size() == 0) {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               if (targetedDebugEnabled) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED no eligible matches for: " + this.className);
               }

               InstrumentationDebug.DEBUG_LOGGER.debug(Thread.currentThread().getId() + "ClassInstrumentor.processClass: completed for " + this.className);
            }

            return;
         }

         if (targetedDebugEnabled && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED Eligible matches found for: " + this.className);
         }

         this.doInstrument((Map)null);
      } else {
         DiagnosticsLogger.logInvalidClassBytes(this.className);
      }

      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("ClassInstrumentor.processClass: completed for " + this.className);
      }

   }

   void markEndBeingInstrumented() {
      if (this.classInfo != null) {
         this.classInfo.markEndBeingInstrumented();
      }
   }

   String getSourceFileName() {
      return this.classInfo != null ? this.classInfo.getSourceFileName() : null;
   }

   private void doInstrument(Map overflowedMethods) {
      this.joinPointId = this.classAnalyzer.getFirstAvailableJoinpointIndex();
      this.overflowedMethodsMap = overflowedMethods;
      ClassReader clReader = new ClassReader(this.originalClassBytes);
      switch (this.useLocalHolder) {
         case 0:
            this.setStackmapFrameGeneration(false);
         case 1:
            break;
         case 2:
            if (this.classAnalyzer.getMajorVersion() < 50) {
               this.setStackmapFrameGeneration(false);
            }
            break;
         case 3:
            if (this.classAnalyzer.getMajorVersion() <= 50) {
               this.setStackmapFrameGeneration(false);
            }
            break;
         default:
            if (this.classAnalyzer.getMajorVersion() < 50) {
               this.setStackmapFrameGeneration(false);
            }
      }

      if (this.hotSwapWithNoAux) {
         if (this.stackmapFrameGeneration) {
            this.auxClassName = this.className;
            this.auxHolderClassIndex = LocalHolder.addAUXHolder(this.classLoader, this.className);
         } else {
            if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_LOGGER.debug("Hotswap AUX classes must be generated if not using LocalHolder style, aux class will be generated for " + this.className + " since it is not using LocalHolder style (due to class version rules)");
            }

            this.hotSwapWithNoAux = false;
         }
      }

      int minCheck = this.engine.getV16ComputeFrames() ? 49 : 50;
      ClassWriter clWriter = new ClassLoaderAwareClassWriter(this.stackmapFrameGeneration ? (this.classAnalyzer.getMajorVersion() > minCheck ? 2 : 1) : 1, this.classLoader, this.className, this.engine);
      InstrumentingClassVisitor instrumentingVisitor = new InstrumentingClassVisitor(this, clWriter);
      clReader.accept(instrumentingVisitor, 0);
      if (this.getErrorCount() == 0 && this.isModified()) {
         ClassVisitor finalizingVisitor = new FinalizingClassVisitor(this, clWriter);
         clReader.accept(finalizingVisitor, 0);
         this.modifiedClassBytes = clWriter.toByteArray();
      }

      if (this.getErrorCount() == 0 && this.modifiedClassBytes != null && overflowedMethods == null) {
         ClassAnalyzer analyzer = new ClassAnalyzer(this.modifiedClassBytes);
         Map overflowedMap = analyzer.findOverflowedMethods();
         if (analyzer.getCantInstrumentClass()) {
            DiagnosticsLogger.logInstrumentationFailure(this.className);
            this.modifiedClassBytes = null;
            this.execJoinpointCount = 0;
            this.callJoinpointCount = 0;
            this.catchJoinpointCount = 0;
         } else if (overflowedMap != null && overflowedMap.size() > 0) {
            this.modified = false;
            this.modifiedClassBytes = null;
            this.execJoinpointCount = 0;
            this.callJoinpointCount = 0;
            this.catchJoinpointCount = 0;
            this.joinPointList.clear();
            this.doInstrument(overflowedMap);
         }
      }

      if (this.getErrorCount() > 0) {
         DiagnosticsLogger.logInstrumentationFailure(this.className);
         this.modifiedClassBytes = null;
      }

   }

   InstrumentorEngineBase getInstrumentorEngine() {
      return this.engine;
   }

   String getInstrumentationSupportClassName() {
      return this.engine.getInstrumentationSupportClassName();
   }

   int getErrorCount() {
      return this.errorCount;
   }

   void reportError() {
      ++this.errorCount;
   }

   ClassInfo getClassInfo(String className) {
      return this.engine.getClassInfo(className, this.classLoader);
   }

   ClassInfo getClassInfo() {
      return this.classInfo;
   }

   MonitorSpecificationBase[] getMonitorsForMethod(String methodName, String methodDesc) {
      String key = methodName + ":" + methodDesc;
      return (MonitorSpecificationBase[])((MonitorSpecificationBase[])this.eligibleMethodsMap.get(key));
   }

   boolean hasEligibleMonitorsForMethods() {
      return this.eligibleMethodsMap.size() > 0;
   }

   private int computeMethodAccessOfCalledMethod(int opcode, String clzName, String methodName, String methodDesc) {
      int acc = 0;
      boolean isSame = clzName.equals(this.className);
      MethodInfo mInfo;
      switch (opcode) {
         case 182:
         case 185:
            if (this.classAnalyzer.getMajorVersion() >= 55) {
               mInfo = this.classAnalyzer.getMethodInfo(methodName, methodDesc);
               if (mInfo != null && (mInfo.getMethodAccess() & 2) != 0) {
                  acc = 2;
               }
            }
            break;
         case 183:
            if (isSame) {
               acc = 2;
            }
            break;
         case 184:
            acc = 8;
            if (isSame) {
               mInfo = this.classAnalyzer.getMethodInfo(methodName, methodDesc);
               if (mInfo != null) {
                  acc |= mInfo.getMethodAccess();
               }
            }
      }

      return acc;
   }

   MonitorSpecificationBase[] getMonitorsForCallsite(String className, String methodName, String methodDesc, String enclosingClassName, String enclosingMethodName, String enclosingMethodDesc, int opcode) {
      String extendedKey = className + ":" + methodName + ":" + methodDesc + ":" + enclosingClassName + ":" + enclosingMethodName + ":" + enclosingMethodDesc;
      if (this.eligibleCallsitesMap.containsKey(extendedKey)) {
         return (MonitorSpecificationBase[])((MonitorSpecificationBase[])this.eligibleCallsitesMap.get(extendedKey));
      } else {
         MonitorSpecificationBase[] mons = this.getMonitorsForCallsite_inner(className, methodName, methodDesc, enclosingClassName, enclosingMethodName, enclosingMethodDesc, opcode);
         this.eligibleCallsitesMap.put(extendedKey, mons);
         return mons;
      }
   }

   private MonitorSpecificationBase[] getMonitorsForCallsite_inner(String className, String methodName, String methodDesc, String enclosingClassName, String enclosingMethodName, String enclosingMethodDesc, int opcode) {
      String key = className + ":" + methodName + ":" + methodDesc;
      MonitorSpecificationBase[] mons = (MonitorSpecificationBase[])((MonitorSpecificationBase[])this.eligibleCallsitesMap.get(key));
      ArrayList tmpList = null;
      if (mons == null) {
         return null;
      } else {
         MethodInfo methodInfo = new MethodInfo();
         methodInfo.setClassName(enclosingClassName);
         methodInfo.setMethodName(enclosingMethodName);
         methodInfo.setMethodDesc(enclosingMethodDesc);
         int acc = this.computeMethodAccessOfCalledMethod(opcode, className, methodName, methodDesc);
         methodInfo.setMethodAccess(acc);
         MonitorSpecificationBase[] var13 = mons;
         int var14 = mons.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            MonitorSpecificationBase mon = var13[var15];

            try {
               if (mon.isEligibleCallsite(this, "L" + className + ";", methodName, methodDesc, methodInfo)) {
                  if (InstrumentationDebug.DEBUG_WEAVING_MATCHES.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_WEAVING_MATCHES.debug("Matched monitor " + mon.getType() + " with call: " + className + "::" + methodName + methodDesc + " in " + methodInfo.getClassName() + "::" + methodInfo.getMethodName() + methodInfo.getMethodDesc());
                  }

                  if (tmpList == null) {
                     tmpList = new ArrayList();
                  }

                  tmpList.add(mon);
               }
            } catch (InvalidPointcutException var18) {
               if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_LOGGER.debug("getMonitorsForCallsite_inner unexpected failure: ", var18);
               }
            }
         }

         return tmpList == null ? null : (MonitorSpecificationBase[])((MonitorSpecificationBase[])tmpList.toArray(new MonitorSpecificationBase[tmpList.size()]));
      }
   }

   boolean isEligibleCatchBlockInMethod(String methodName, String methodDesc, String exceptionClassname) {
      String key = methodName + ":" + methodDesc + ":" + exceptionClassname;
      return this.eligibleCatchblocksMap.containsKey(key);
   }

   Map getPointcutHandlingInfoMapForCallsite(String className, String methodName, String methodDesc) {
      String key = className + ":" + methodName + ":" + methodDesc;
      return (Map)this.callsitePointcutHandlingMaps.get(key);
   }

   Map getPointcutHandlingInfoMapForMethod(String methodName, String methodDesc) {
      String key = methodName + ":" + methodDesc;
      return (Map)this.methodPointcutHandlingMaps.get(key);
   }

   void addPointcutHandlngInfoForCallsite(String className, String methodName, String methodDesc, MonitorSpecificationBase monSpec, PointcutHandlingInfo info) {
      if (monSpec != null) {
         String key = null;
         if (className != null) {
            int startIndex = 0;
            int endIndex = className.length();
            if (className.startsWith("L")) {
               startIndex = 1;
            }

            if (className.endsWith(";")) {
               endIndex = className.length() - 1;
            }

            if (startIndex == 0 && endIndex == className.length()) {
               key = className + ":" + methodName + ":" + methodDesc;
            } else {
               key = className.substring(startIndex, endIndex) + ":" + methodName + ":" + methodDesc;
            }
         }

         Map infoMap = (Map)this.callsitePointcutHandlingMaps.get(key);
         if (infoMap == null) {
            infoMap = new HashMap();
            this.callsitePointcutHandlingMaps.put(key, infoMap);
         }

         ((Map)infoMap).put(monSpec.getType(), info);
      }
   }

   void addPointcutHandlngInfoForMethod(String methodName, String methodDesc, MonitorSpecificationBase monSpec, PointcutHandlingInfo info) {
      if (monSpec != null) {
         String key = methodName + ":" + methodDesc;
         Map infoMap = (Map)this.methodPointcutHandlingMaps.get(key);
         if (infoMap == null) {
            infoMap = new HashMap();
            this.methodPointcutHandlingMaps.put(key, infoMap);
         }

         ((Map)infoMap).put(monSpec.getType(), info);
      }
   }

   boolean hasEligibleMonitorsForCallsites() {
      return this.eligibleCallsitesMap.size() > 0;
   }

   boolean isModified() {
      return this.modified;
   }

   void setModified(boolean modified) {
      this.modified = modified;
   }

   void setStaticInitializerMark(boolean hasit) {
      this.hasStaticInitializer = hasit;
   }

   void setSUIDMark(boolean hasit) {
      this.hasSUID = hasit;
   }

   String registerJoinPoint(JoinPointInfo jpInfo) {
      String name = "_WLDF$INST_JPFLD_" + this.joinPointId;
      ++this.joinPointId;
      jpInfo.setName(name);
      this.joinPointList.add(jpInfo);
      return name;
   }

   void registerLabel(Label label, int lineNumber) {
      this.linenumberMap.put(label, lineNumber);
   }

   Integer getLineNumberForLabel(Label label) {
      return (Integer)this.linenumberMap.get(label);
   }

   private void emitJoinPointInitializers(ClassVisitor classVisitor, MethodVisitor codeVisitor) {
      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("Emitting " + this.joinPointList.size() + " JoinPoint fields");
      }

      int access = 24;
      if (this.engine.isHotswapAllowed()) {
         ++access;
      }

      String jpClassNameInternal = "L" + JoinPoint.class.getName().replace('.', '/') + ";";
      String creatorDesc = WLDF_CREATE_JOINPOINT_DESC;
      Integer unknownLineNumber = 0;
      String supportClassName = this.getInstrumentationSupportClassName();
      boolean printSummary = this.engine.printSummary();
      Iterator it = this.joinPointList.iterator();

      while(it.hasNext()) {
         JoinPointInfo jpInfo = (JoinPointInfo)it.next();
         String jpName = jpInfo.getName();
         JoinPoint jp = jpInfo.getJoinPoint();
         Label jpLabel = jpInfo.getLabel();
         Integer lineNumber = jpLabel != null ? (Integer)this.linenumberMap.get(jpLabel) : null;
         if (lineNumber == null) {
            lineNumber = unknownLineNumber;
         }

         classVisitor.visitField(access, jpName, jpClassNameInternal, (String)null, (Object)null);
         if (printSummary) {
            this.engine.summaryPrintln("   JoinPoint: " + jp.getClassName().replace('/', '.') + "." + jp.getMethodName() + jp.getMethodDescriptor());
         }

         codeVisitor.visitFieldInsn(178, this.auxClassName, "_WLDF$INST_FLD_class", "Ljava/lang/Class;");
         codeVisitor.visitLdcInsn(jp.getSourceFile());
         codeVisitor.visitLdcInsn(jp.getClassName().replace('/', '.'));
         codeVisitor.visitLdcInsn(jp.getMethodName());
         codeVisitor.visitLdcInsn(jp.getMethodDescriptor());
         codeVisitor.visitLdcInsn(lineNumber);
         codeVisitor.visitLdcInsn(jp.getCallerClassName().replace('/', '.'));
         codeVisitor.visitLdcInsn(jp.getCallerMethodName());
         codeVisitor.visitLdcInsn(jp.getCallerMethodDescriptor());
         this.emitPointcutHandlingInfoMap(codeVisitor, supportClassName, jp.getPointcutHandlingInfoMap());
         codeVisitor.visitLdcInsn(jp.isStatic());
         codeVisitor.visitMethodInsn(184, supportClassName, "createJoinPoint", creatorDesc);
         codeVisitor.visitFieldInsn(179, this.auxClassName, jpName, jpClassNameInternal);
         if (this.getStackmapFrameGeneration()) {
            this.emitJoinPointMonitorArray(classVisitor, codeVisitor, jpName, jpInfo.getMonitors());
         }
      }

   }

   private void emitJoinPointMonitorArray(ClassVisitor classVisitor, MethodVisitor codeVisitor, String jpName, MonitorSpecificationBase[] monitors) {
      if (monitors == null) {
         throw new RuntimeException("JoinPoint is missing the monitor array which is required for LocalHolder");
      } else {
         String jpMonsFieldName = CodeUtils.getJPMonitorsFieldName(jpName);
         int access = 24;
         if (this.engine.isHotswapAllowed()) {
            ++access;
         }

         classVisitor.visitField(access, jpMonsFieldName, "[L" + WLDF_DIAGMON_CLASSNAME + ";", (String)null, (Object)null);
         codeVisitor.visitIntInsn(16, monitors.length);
         codeVisitor.visitTypeInsn(189, WLDF_DIAGMON_CLASSNAME);
         int index = 0;
         MonitorSpecificationBase[] var8 = monitors;
         int var9 = monitors.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            MonitorSpecificationBase monitor = var8[var10];
            codeVisitor.visitInsn(89);
            codeVisitor.visitIntInsn(16, index);
            codeVisitor.visitFieldInsn(178, this.auxClassName, "_WLDF$INST_FLD_" + monitor.getType().replace('/', '$').replace('.', '$'), "L" + (monitor.isStandardMonitor() ? "weblogic/diagnostics/instrumentation/StandardMonitor" : "weblogic/diagnostics/instrumentation/DelegatingMonitor") + ";");
            codeVisitor.visitInsn(83);
            ++index;
         }

         codeVisitor.visitFieldInsn(179, this.auxClassName, jpMonsFieldName, "[L" + WLDF_DIAGMON_CLASSNAME + ";");
      }
   }

   private void emitPointcutHandlingInfoMap(MethodVisitor codeVisitor, String supportClassName, Map infoMap) {
      boolean debug = InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled();
      if (infoMap != null && !infoMap.isEmpty()) {
         int size = infoMap.size();
         String[] keys = new String[size];
         keys = (String[])infoMap.keySet().toArray(keys);
         PointcutHandlingInfo[] values = new PointcutHandlingInfo[size];
         boolean allNullValues = true;

         int i;
         for(i = 0; i < size; ++i) {
            if (keys[i] == null) {
               if (debug) {
                  InstrumentationDebug.DEBUG_WEAVING.debug("emitPointcutHandlingInfoMap(): ERROR: Null Key found! INVALID MAP, emit null");
               }

               codeVisitor.visitInsn(1);
               this.reportError();
               return;
            }

            values[i] = (PointcutHandlingInfo)infoMap.get(keys[i]);
            if (allNullValues && values[i] != null) {
               allNullValues = false;
            }
         }

         if (allNullValues) {
            if (debug) {
               InstrumentationDebug.DEBUG_WEAVING.debug("emitPointcutHandlingInfoMap(): All monitors have null infos, no need for a Map, emit null");
            }

            codeVisitor.visitInsn(1);
         } else {
            if (debug) {
               InstrumentationDebug.DEBUG_WEAVING.debug("emitPointcutHandlingInfoMap(): emitting Map initialization call to InstrumentationSupport.makeMap, size = " + infoMap.size());
            }

            codeVisitor.visitIntInsn(16, size);
            codeVisitor.visitTypeInsn(189, "java/lang/String");

            for(i = 0; i < size; ++i) {
               codeVisitor.visitInsn(89);
               codeVisitor.visitIntInsn(16, i);
               codeVisitor.visitLdcInsn(keys[i]);
               codeVisitor.visitInsn(83);
            }

            codeVisitor.visitIntInsn(16, size);
            codeVisitor.visitTypeInsn(189, "weblogic/diagnostics/instrumentation/PointcutHandlingInfo");

            for(i = 0; i < size; ++i) {
               codeVisitor.visitInsn(89);
               codeVisitor.visitIntInsn(16, i);
               this.emitPointcutHandlingInfo(codeVisitor, supportClassName, (PointcutHandlingInfo)infoMap.get(keys[i]));
               codeVisitor.visitInsn(83);
            }

            codeVisitor.visitMethodInsn(184, supportClassName, "makeMap", WLDF_INSTRUMENTATIONSUPPORT_MAKEMAP_DESC);
         }
      } else {
         if (debug) {
            InstrumentationDebug.DEBUG_WEAVING.debug("emitPointcutHandlingInfoMap(): emit null");
         }

         codeVisitor.visitInsn(1);
      }
   }

   LocalHolder.JoinPointAuxInfo generateJoinPointAuxInfo(String jpName) {
      if (jpName != null && !this.joinPointList.isEmpty()) {
         LocalHolder.JoinPointAuxInfo retVal = new LocalHolder.JoinPointAuxInfo();
         Iterator var3 = this.joinPointList.iterator();

         while(var3.hasNext()) {
            JoinPointInfo jpInfo = (JoinPointInfo)var3.next();
            if (jpName.equals(jpInfo.getName())) {
               MonitorSpecificationBase[] mons = jpInfo.getMonitors();
               DiagnosticMonitor[] diagMons = new DiagnosticMonitor[mons.length];

               for(int i = 0; i < mons.length; ++i) {
                  diagMons[i] = this.invokeSupportClassGetMonitor(mons[i].getType());
               }

               retVal.setJoinPoint(this.invokeSupportClassCreateJoinPoint(jpInfo));
               retVal.setJPMons(diagMons);
               break;
            }
         }

         return retVal;
      } else {
         return null;
      }
   }

   private void initializeSupportClassRuntime() {
      try {
         this.supportClass = Class.forName(this.getInstrumentationSupportClassName().replace('/', '.'));
         this.createJoinPointMethod = this.supportClass.getDeclaredMethod("createJoinPoint", ClassLoader.class, String.class, String.class, String.class, String.class, Integer.TYPE, String.class, String.class, String.class, Map.class, Boolean.TYPE);
         this.getMonitorMethod = this.supportClass.getDeclaredMethod("getMonitor", ClassLoader.class, String.class, String.class);
      } catch (Throwable var2) {
         throw new RuntimeException("Failure accessing runtime support class or method reflectively to process HotSwap no-AUX mode", var2);
      }
   }

   private JoinPoint invokeSupportClassCreateJoinPoint(JoinPointInfo jpInfo) {
      if (this.createJoinPointMethod == null) {
         this.initializeSupportClassRuntime();
      }

      JoinPoint infoJP = jpInfo.getJoinPoint();

      try {
         return (JoinPoint)this.createJoinPointMethod.invoke((Object)null, this.classLoader, infoJP.getSourceFile(), infoJP.getClassName(), infoJP.getMethodName(), infoJP.getMethodDescriptor(), infoJP.getLineNumber(), infoJP.getCallerClassName(), infoJP.getCallerMethodName(), infoJP.getCallerMethodDescriptor(), infoJP.getPointcutHandlingInfoMap(), infoJP.isStatic());
      } catch (Throwable var4) {
         throw new RuntimeException("Failed to invoke createJoinPoint", var4);
      }
   }

   private DiagnosticMonitor invokeSupportClassGetMonitor(String monType) {
      if (this.getMonitorMethod == null) {
         this.initializeSupportClassRuntime();
      }

      try {
         return (DiagnosticMonitor)this.getMonitorMethod.invoke((Object)null, this.classLoader, this.className, monType);
      } catch (Throwable var3) {
         throw new RuntimeException("Failed to invoke getMonitor", var3);
      }
   }

   private void emitPointcutHandlingInfo(MethodVisitor codeVisitor, String supportClassName, PointcutHandlingInfo info) {
      boolean debug = InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled();
      if (info == null) {
         if (debug) {
            InstrumentationDebug.DEBUG_WEAVING.debug("emitPointcutHandlingInfo() PointcutHandlingInfo == null, so emitting NULL");
         }

         codeVisitor.visitInsn(1);
      } else {
         if (debug) {
            InstrumentationDebug.DEBUG_WEAVING.debug("emitPointcutHandlingInfo(). Emitting InstrumentationSupport.createPointcutHandlingInfo:");
            InstrumentationDebug.DEBUG_WEAVING.debug("        class:     " + info.getClassValueHandlingInfo());
            InstrumentationDebug.DEBUG_WEAVING.debug("        return:    " + info.getReturnValueHandlingInfo());
         }

         this.emitValueHandlingInfo(codeVisitor, supportClassName, info.getClassValueHandlingInfo());
         this.emitValueHandlingInfo(codeVisitor, supportClassName, info.getReturnValueHandlingInfo());
         ValueHandlingInfo[] argInfos = info.getArgumentValueHandlingInfo();
         if (argInfos != null && argInfos.length != 0) {
            if (debug) {
               InstrumentationDebug.DEBUG_WEAVING.debug("        Args (" + argInfos.length + ")");
            }

            codeVisitor.visitIntInsn(16, argInfos.length);
            codeVisitor.visitTypeInsn(189, "weblogic/diagnostics/instrumentation/ValueHandlingInfo");
            int i = 0;
            ValueHandlingInfo[] var7 = argInfos;
            int var8 = argInfos.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               ValueHandlingInfo argInfo = var7[var9];
               if (debug) {
                  InstrumentationDebug.DEBUG_WEAVING.debug("        Args[]:=" + argInfo);
               }

               codeVisitor.visitInsn(89);
               codeVisitor.visitIntInsn(16, i++);
               this.emitValueHandlingInfo(codeVisitor, supportClassName, argInfo);
               codeVisitor.visitInsn(83);
            }
         } else {
            if (debug) {
               InstrumentationDebug.DEBUG_WEAVING.debug("        No args ");
            }

            codeVisitor.visitInsn(1);
         }

         codeVisitor.visitMethodInsn(184, supportClassName, "createPointcutHandlingInfo", WLDF_POINTCUTHANDLINGINFO_CREATE_DESC);
      }
   }

   private void emitValueHandlingInfo(MethodVisitor codeVisitor, String supportClassName, ValueHandlingInfo info) {
      boolean debug = InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled();
      if (info == null) {
         if (debug) {
            InstrumentationDebug.DEBUG_WEAVING.debug("emitValueHandlingInfo() ValueHandlingInfo == null, so emitting NULL");
         }

         codeVisitor.visitInsn(1);
      } else {
         if (debug) {
            InstrumentationDebug.DEBUG_WEAVING.debug("emitValueHandlingInfo(). Emitting InstrumentationSupport.createValueHandlingInfo:");
            InstrumentationDebug.DEBUG_WEAVING.debug("        name:              " + info.getName());
            InstrumentationDebug.DEBUG_WEAVING.debug("        RendererClassName: " + info.getRendererClassName());
            InstrumentationDebug.DEBUG_WEAVING.debug("        sensitive:         " + info.isSensitive());
            InstrumentationDebug.DEBUG_WEAVING.debug("        gathered:          " + info.isGathered());
         }

         this.emitStringArgOrNull(codeVisitor, info.getName());
         this.emitStringArgOrNull(codeVisitor, info.getRendererClassName());
         codeVisitor.visitInsn(info.isSensitive() ? 4 : 3);
         codeVisitor.visitInsn(info.isGathered() ? 4 : 3);
         codeVisitor.visitMethodInsn(184, supportClassName, "createValueHandlingInfo", WLDF_VALUEHANDLINGINFO_CREATE_DESC);
      }
   }

   private void emitStringArgOrNull(MethodVisitor codeVisitor, String theString) {
      if (theString == null) {
         codeVisitor.visitInsn(1);
      } else {
         codeVisitor.visitLdcInsn(theString);
      }

   }

   private void emitSupportInitializer(ClassVisitor classVisitor, MethodVisitor codeVisitor) {
      if (!this.classAnalyzer.hasField("_WLDF$INST_FLD_class")) {
         classVisitor.visitField(4104, "_WLDF$INST_FLD_class", "Ljava/lang/Class;", (String)null, (Object)null);
      }

      codeVisitor.visitLdcInsn(this.auxClassName.replace('/', '.'));
      codeVisitor.visitMethodInsn(184, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
      codeVisitor.visitFieldInsn(179, this.auxClassName, "_WLDF$INST_FLD_class", "Ljava/lang/Class;");
   }

   private void emitMonitorInitializers(ClassVisitor classVisitor, MethodVisitor codeVisitor, Map monitors, Map processedMonitors) {
      String supportClassName = this.getInstrumentationSupportClassName();
      String fieldPrefix = "_WLDF$INST_FLD_";
      int access = 24;
      if (this.engine.isHotswapAllowed()) {
         ++access;
      }

      String getMonDesc = "(Ljava/lang/Class;Ljava/lang/String;)L" + DiagnosticMonitor.class.getName().replace('.', '/') + ";";
      boolean printSummary = this.engine.printSummary();
      boolean trackMatchedMonitors = this.engine.getTrackMatchedMonitors();
      Iterator it = monitors.values().iterator();

      while(it.hasNext()) {
         MonitorSpecificationBase[] mSpecs = (MonitorSpecificationBase[])((MonitorSpecificationBase[])it.next());
         int size = mSpecs != null ? mSpecs.length : 0;

         for(int i = 0; i < size; ++i) {
            MonitorSpecificationBase mon = mSpecs[i];
            String monType = mon.getType();
            if (processedMonitors.get(monType) == null) {
               processedMonitors.put(monType, monType);
               String monFieldName = fieldPrefix + mon.getType();
               monFieldName = monFieldName.replace('/', '$').replace('.', '$');
               String monClassType = mon.isStandardMonitor() ? "weblogic/diagnostics/instrumentation/StandardMonitor" : "weblogic/diagnostics/instrumentation/DelegatingMonitor";
               if (trackMatchedMonitors) {
                  this.engine.addMatchedMonitor(mon.getType());
               }

               if (printSummary) {
                  StringBuffer sb = new StringBuffer();
                  sb.append("   Monitor: ");
                  sb.append(mon.getType());
                  sb.append(", (");
                  sb.append(this.locationToString(mon.getLocation()));
                  sb.append(")");
                  if (mon.isServerManaged()) {
                     sb.append(", volume: ");
                     sb.append(mon.getDiagnosticVolume());
                     String event = mon.getEventClassName();
                     if (event != null) {
                        sb.append(", event: ");
                        sb.append(event);
                     }
                  }

                  this.engine.summaryPrintln(sb.toString());
               }

               classVisitor.visitField(access, monFieldName, "L" + monClassType + ";", (String)null, (Object)null);
               codeVisitor.visitFieldInsn(178, this.auxClassName, "_WLDF$INST_FLD_class", "Ljava/lang/Class;");
               codeVisitor.visitLdcInsn(monType);
               codeVisitor.visitMethodInsn(184, supportClassName, "getMonitor", getMonDesc);
               codeVisitor.visitTypeInsn(192, monClassType);
               codeVisitor.visitFieldInsn(179, this.auxClassName, monFieldName, "L" + monClassType + ";");
            }
         }
      }

   }

   void emitInitializerCode(ClassVisitor classVisitor, MethodVisitor codeVisitor) {
      if (!this.classAnalyzer.hasField("_WLDF$INST_VERSION")) {
         classVisitor.visitField(24, "_WLDF$INST_VERSION", "Ljava/lang/String;", (String)null, "9.0.0");
      }

      this.emitSupportInitializer(classVisitor, codeVisitor);
      HashMap processedMonitors = new HashMap();
      boolean printSummary = this.engine.printSummary();
      if (printSummary) {
         this.engine.summaryPrintln("++++\nInstrumented Class: " + this.className);
         this.engine.summaryPrintln("   SupportClassName: " + this.getInstrumentationSupportClassName());
      }

      this.emitMonitorInitializers(classVisitor, codeVisitor, this.eligibleMethodsMap, processedMonitors);
      this.emitMonitorInitializers(classVisitor, codeVisitor, this.eligibleCallsitesMap, processedMonitors);
      this.emitJoinPointInitializers(classVisitor, codeVisitor);
      if (printSummary) {
         this.engine.summaryPrintln("----");
      }

   }

   boolean hasEmittedEntity(String entityName) {
      return this.emittedEntitiesMap.get(entityName) != null;
   }

   void registerEmittedEnity(String enityName) {
      this.emittedEntitiesMap.put(enityName, enityName);
   }

   int getMaxLocalsForMethod(String name, String desc) {
      return this.classAnalyzer.getMaxLocalsForMethod(name, desc);
   }

   boolean isOverflowedMethod(String methodName, String methodDesc) {
      if (this.overflowedMethodsMap == null) {
         return false;
      } else {
         String key = methodName + "/" + methodDesc;
         return this.overflowedMethodsMap.get(key) != null;
      }
   }

   void incrementExecutionJoinpointCount(int incr) {
      this.execJoinpointCount += incr;
   }

   void incrementCallJoinpointCount(int incr) {
      this.callJoinpointCount += incr;
   }

   void incrementCatchJoinpointCount(int incr) {
      this.catchJoinpointCount += incr;
   }

   String locationToString(int location) {
      switch (location) {
         case 1:
            return "before";
         case 2:
            return "after";
         case 3:
            return "around";
         default:
            return "invalid location";
      }
   }

   boolean usesFrames() {
      return this.classAnalyzer != null ? this.classAnalyzer.usesFrames() : false;
   }

   private void updateTargetedDebug() {
      if (targetedDebugConfigured && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
         try {
            String normalizedClassName = this.className == null ? "" : this.className.replace('.', '/');
            if (TARGETED_CLASSES != null && TARGETED_CLASSES.contains(normalizedClassName) || TARGETED_PATTERN != null && normalizedClassName.matches(TARGETED_PATTERN)) {
               InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED debug enabled for " + this.className);
               targetedDebugEnabled = true;
               return;
            }
         } catch (Throwable var2) {
         }

         InstrumentationDebug.DEBUG_LOGGER.debug("TARGETED debug disabled for " + this.className);
         targetedDebugEnabled = false;
      } else {
         targetedDebugEnabled = false;
      }
   }

   private static boolean initializeTargetedDebug() {
      try {
         TARGETED_PATTERN = System.getProperty("weblogic.diagnostics.instrumentation.targetpattern");
         TARGETED_CLASSES = System.getProperty("weblogic.diagnostics.instrumentation.targetclasses");
      } catch (Throwable var1) {
      }

      boolean result = TARGETED_PATTERN != null || TARGETED_CLASSES != null;
      if (result && InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_LOGGER.debug("Targeted debug configured: " + result + ", " + TARGETED_PATTERN + ", " + TARGETED_CLASSES);
      }

      return result;
   }
}
