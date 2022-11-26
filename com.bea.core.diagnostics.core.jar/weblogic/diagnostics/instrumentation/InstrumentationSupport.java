package weblogic.diagnostics.instrumentation;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class InstrumentationSupport extends InstrumentationSupportBase {
   private static final String SUPPORT_CLASSNAME = "weblogic.diagnostics.instrumentation.rtsupport.InstrumentationSupportImpl";
   public static final String SENSITIVE_VALUE = "*****";
   private static InstrumentationSupportInterface support = getInstrumentationSupport();
   private static final GatheredArgument[] EMPTY_GATHERED_ARGS = new GatheredArgument[0];
   private static final String[] EMPTY_STRINGS = new String[0];
   private static final DiagnosticAction[] EMPTY_ACTIONS = new DiagnosticAction[0];

   private static InstrumentationSupportInterface getInstrumentationSupport() {
      InstrumentationSupportInterface retVal = getInstrumentationSupportOverride();
      if (retVal == null) {
         try {
            retVal = (InstrumentationSupportInterface)Class.forName("weblogic.diagnostics.instrumentation.rtsupport.InstrumentationSupportImpl").newInstance();
         } catch (Throwable var2) {
         }
      }

      return retVal;
   }

   private static InstrumentationSupportInterface getInstrumentationSupportOverride() {
      Properties props = new Properties();
      InputStream iostream = null;

      InstrumentationSupportInterface retVal;
      try {
         iostream = InstrumentationSupport.class.getClassLoader().getResourceAsStream("weblogic-diagnostics.properties");
         props.load(iostream);
         String overrideName = props.getProperty("weblogic.diagnostics.instrumentation.rt.SupportOverrideClassName");
         if (overrideName != null) {
            retVal = null;
            retVal = (InstrumentationSupportInterface)Class.forName(overrideName).newInstance();
            InstrumentationSupportInterface var4 = retVal;
            return var4;
         }

         retVal = null;
      } catch (Throwable var15) {
         return null;
      } finally {
         if (iostream != null) {
            try {
               iostream.close();
            } catch (Throwable var14) {
            }
         }

      }

      return retVal;
   }

   public static DiagnosticMonitor getMonitor(Class clz, String type) {
      return (DiagnosticMonitor)(support != null ? support.getMonitor(clz, type) : new PlaceholderDelegatingMonitor(type));
   }

   public static JoinPoint createJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, Map pointcutHandlingInfoMap, boolean isStatic) {
      return createJoinPoint((Class)joinpointClass, source, className, methodName, methodDesc, lineNum, (String)null, (String)null, (String)null, pointcutHandlingInfoMap, isStatic);
   }

   public static JoinPoint createJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
      return (JoinPoint)(support != null ? support.createJoinPoint(joinpointClass, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodDesc, pointcutHandlingInfoMap, isStatic) : new PlaceholderJoinPoint(joinpointClass, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodDesc, pointcutHandlingInfoMap, isStatic));
   }

   public static DiagnosticMonitor getMonitor(ClassLoader loader, String className, String type) {
      return (DiagnosticMonitor)(support != null ? support.getMonitor(loader, className, type) : new PlaceholderDelegatingMonitor(type));
   }

   public static JoinPoint createJoinPoint(ClassLoader loader, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
      return (JoinPoint)(support != null ? support.createJoinPoint(loader, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodDesc, pointcutHandlingInfoMap, isStatic) : new PlaceholderJoinPoint(loader, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodDesc, pointcutHandlingInfoMap, isStatic));
   }

   public static void createDynamicJoinPoint(LocalHolder holder) {
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      if (support != null) {
         monHolder.djp = support.createDynamicJoinPoint((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.captureArgs ? holder.args : null, holder.ret);
      } else {
         monHolder.djp = new PlaceholderDynamicJoinPoint((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp));
      }

   }

   public static DynamicJoinPoint createDynamicJoinPoint(JoinPoint jp, Object[] args, Object retVal) {
      return (DynamicJoinPoint)(support != null ? support.createDynamicJoinPoint(jp, args, retVal) : new PlaceholderDynamicJoinPoint(jp));
   }

   public static void process(LocalHolder holder) {
      if (support != null) {
         MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
         support.process((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor, monHolder.actions);
      }

   }

   public static void process(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions) {
      if (support != null) {
         support.process(jp, mon, actions);
      }

   }

   public static void process(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, Throwable th) {
      if (support != null) {
         support.process(jp, mon, actions);
      }

   }

   public static void preProcess(LocalHolder holder) {
      applyActionStates(holder);
      if (support != null) {
         MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
         support.preProcess((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor, monHolder.actions, monHolder.states);
      }

   }

   public static void preProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states) {
      if (support != null) {
         support.preProcess(jp, mon, actions, states);
      }

   }

   public static void postProcess(LocalHolder holder) {
      if (support != null) {
         MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
         support.postProcess((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor, monHolder.actions, monHolder.states);
      }

   }

   public static void postProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states) {
      if (support != null) {
         support.postProcess(jp, mon, actions, states);
      }

   }

   public static void postProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states, Throwable th) {
      if (support != null) {
         support.postProcess(jp, mon, actions, states);
      }

   }

   public static void catchThrowable(Throwable th) {
   }

   public static PointcutHandlingInfo createPointcutHandlingInfo(ValueHandlingInfo classValueHandlingInfo, ValueHandlingInfo returnValueHandlingInfo, ValueHandlingInfo[] argumentValueHandlingInfo) {
      return (PointcutHandlingInfo)(support != null ? support.createPointcutHandlingInfo(classValueHandlingInfo, returnValueHandlingInfo, argumentValueHandlingInfo) : new PlaceholderPointcutHandlingInfo(classValueHandlingInfo, returnValueHandlingInfo, argumentValueHandlingInfo));
   }

   public static ValueHandlingInfo createValueHandlingInfo(String name, String rendererClassName, boolean sensitive, boolean gathered) {
      return (ValueHandlingInfo)(support != null ? support.createValueHandlingInfo(name, rendererClassName, sensitive, gathered) : new PlaceholderValueHandlingInfo(name, rendererClassName, sensitive, gathered));
   }

   public static Map makeMap(String[] names, PointcutHandlingInfo[] infos) {
      return support != null ? support.makeMap(names, infos) : placeholderMakeMap(names, infos);
   }

   private static Map placeholderMakeMap(String[] names, PointcutHandlingInfo[] infos) {
      if (names != null && infos != null && (names.length != 0 || infos.length != 0)) {
         if (names.length != infos.length) {
            return null;
         } else {
            Map theMap = new HashMap();

            for(int i = 0; i < names.length; ++i) {
               if (names[i] == null) {
                  return null;
               }

               theMap.put(names[i], infos[i]);
            }

            return theMap;
         }
      } else {
         return null;
      }
   }

   public static Object[] toSensitive(Object[] inArr) {
      return inArr == null ? null : toSensitive(inArr.length);
   }

   public static Object[] toSensitive(int length) {
      return InstrumentationSupport.SensitiveArrayHandler.toSensitive(length);
   }

   public static boolean isSensitiveArray(Object[] testArray) {
      return InstrumentationSupport.SensitiveArrayHandler.isHandledSensitiveArray(testArray);
   }

   private static class SensitiveArrayHandler {
      private static final Object[] SENSITIVE_ARRAY_1 = new Object[]{"*****"};
      private static final Object[] SENSITIVE_ARRAY_2 = new Object[]{"*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_3 = new Object[]{"*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_4 = new Object[]{"*****", "*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_5 = new Object[]{"*****", "*****", "*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_6 = new Object[]{"*****", "*****", "*****", "*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_7 = new Object[]{"*****", "*****", "*****", "*****", "*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_8 = new Object[]{"*****", "*****", "*****", "*****", "*****", "*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_9 = new Object[]{"*****", "*****", "*****", "*****", "*****", "*****", "*****", "*****", "*****"};
      private static final Object[] SENSITIVE_ARRAY_10 = new Object[]{"*****", "*****", "*****", "*****", "*****", "*****", "*****", "*****", "*****", "*****"};
      private static final Object[][] SENSITIVE_ARRAYS;
      private static HashMap extendedArrays;

      public static Object[] toSensitive(int length) {
         if (length == 0) {
            return null;
         } else {
            return length > SENSITIVE_ARRAYS.length ? getExtendedArray(length) : SENSITIVE_ARRAYS[length - 1];
         }
      }

      public static boolean isHandledSensitiveArray(Object[] testArray) {
         if (testArray != null && testArray.length != 0) {
            if (testArray.length > SENSITIVE_ARRAYS.length) {
               synchronized(extendedArrays) {
                  Object[] val = (Object[])extendedArrays.get(testArray.length);
                  if (val == null) {
                     return false;
                  } else {
                     return val == testArray;
                  }
               }
            } else {
               return testArray == SENSITIVE_ARRAYS[testArray.length - 1];
            }
         } else {
            return false;
         }
      }

      private static Object[] getExtendedArray(int length) {
         Integer key = length;
         Object[] retVal = (Object[])extendedArrays.get(key);
         if (retVal != null) {
            return retVal;
         } else {
            synchronized(extendedArrays) {
               retVal = (Object[])extendedArrays.get(key);
               if (retVal != null) {
                  return retVal;
               } else {
                  retVal = new Object[length];

                  for(int i = 0; i < length; ++i) {
                     retVal[i] = "*****";
                  }

                  extendedArrays.put(key, retVal);
                  return retVal;
               }
            }
         }
      }

      static {
         SENSITIVE_ARRAYS = new Object[][]{SENSITIVE_ARRAY_1, SENSITIVE_ARRAY_2, SENSITIVE_ARRAY_3, SENSITIVE_ARRAY_4, SENSITIVE_ARRAY_5, SENSITIVE_ARRAY_6, SENSITIVE_ARRAY_7, SENSITIVE_ARRAY_8, SENSITIVE_ARRAY_9, SENSITIVE_ARRAY_10};
         extendedArrays = new HashMap();
      }
   }

   private static class PlaceholderValueHandlingInfo implements ValueHandlingInfo {
      private String name;
      private String rendererClassName;
      private boolean sensitive;
      private boolean gathered;

      public PlaceholderValueHandlingInfo(String name, String rendererClassName, boolean sensitive, boolean gathered) {
         this.name = name;
         this.rendererClassName = rendererClassName;
         this.sensitive = sensitive;
         this.gathered = gathered;
      }

      public String getName() {
         return this.name;
      }

      public String getRendererClassName() {
         return this.rendererClassName;
      }

      public boolean isGathered() {
         return this.gathered;
      }

      public boolean isSensitive() {
         return this.sensitive;
      }
   }

   private static class PlaceholderPointcutHandlingInfo implements PointcutHandlingInfo {
      private ValueHandlingInfo classValueHandlingInfo;
      private ValueHandlingInfo returnValueHandlingInfo;
      private ValueHandlingInfo[] argumentValueHandlingInfo;

      public PlaceholderPointcutHandlingInfo(ValueHandlingInfo classValueHandlingInfo, ValueHandlingInfo returnValueHandlingInfo, ValueHandlingInfo[] argumentValueHandlingInfo) {
         this.classValueHandlingInfo = classValueHandlingInfo;
         this.returnValueHandlingInfo = returnValueHandlingInfo;
         this.argumentValueHandlingInfo = argumentValueHandlingInfo;
      }

      public ValueHandlingInfo[] getArgumentValueHandlingInfo() {
         return this.argumentValueHandlingInfo;
      }

      public ValueHandlingInfo getClassValueHandlingInfo() {
         return this.classValueHandlingInfo;
      }

      public ValueHandlingInfo getReturnValueHandlingInfo() {
         return this.returnValueHandlingInfo;
      }
   }

   private static class PlaceholderDynamicJoinPoint extends PlaceholderJoinPoint implements DynamicJoinPoint {
      private JoinPoint jp;
      private static Object[] placeHolderArgs = new Object[0];

      PlaceholderDynamicJoinPoint(JoinPoint jp) {
         this.jp = jp;
      }

      public Object[] getArguments() {
         return placeHolderArgs;
      }

      public JoinPoint getDelegate() {
         return this.jp;
      }

      public Object getReturnValue() {
         return "";
      }

      public boolean isReturnGathered() {
         return false;
      }

      public GatheredArgument[] getGatheredArguments() {
         return InstrumentationSupport.EMPTY_GATHERED_ARGS;
      }
   }

   private static class PlaceholderJoinPoint implements JoinPoint {
      private static final String UNKNOWN = "Unknown";
      private WeakReference loaderRef = null;
      private Class joinpointClass = null;
      private String source = "Unknown";
      private String className = "Unknown";
      private String methodName = "Unknown";
      private String methodDesc = "Unknown";
      private String callerClassName = "Unknown";
      private String callerMethodName = "Unknown";
      private String callerMethodDesc = "Unknown";
      private int lineNum = 0;
      private Map pointcutHandlingInfoMap = null;
      private boolean isStatic = true;

      public PlaceholderJoinPoint() {
      }

      public PlaceholderJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
         this.joinpointClass = joinpointClass;
         this.source = source;
         this.className = className;
         this.methodName = methodName;
         this.methodDesc = methodDesc;
         this.lineNum = lineNum;
         this.callerClassName = callerClassName;
         this.callerMethodName = callerMethodName;
         this.callerMethodDesc = callerMethodDesc;
         this.pointcutHandlingInfoMap = pointcutHandlingInfoMap;
         this.isStatic = isStatic;
      }

      public PlaceholderJoinPoint(ClassLoader loader, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map pointcutHandlingInfoMap, boolean isStatic) {
         if (loader != null) {
            this.loaderRef = new WeakReference(loader);
         }

         this.source = source;
         this.className = className;
         this.methodName = methodName;
         this.methodDesc = methodDesc;
         this.lineNum = lineNum;
         this.callerClassName = callerClassName;
         this.callerMethodName = callerMethodName;
         this.callerMethodDesc = callerMethodDesc;
         this.pointcutHandlingInfoMap = pointcutHandlingInfoMap;
         this.isStatic = isStatic;
      }

      public Class getJoinpointClass() {
         if (this.joinpointClass != null) {
            return this.joinpointClass;
         } else if (this.className == null) {
            return null;
         } else {
            try {
               ClassLoader loader = this.loaderRef == null ? null : (ClassLoader)this.loaderRef.get();
               if (this.loaderRef != null && loader == null) {
                  return null;
               }

               this.joinpointClass = Class.forName(this.className, true, loader);
            } catch (Throwable var2) {
            }

            return this.joinpointClass;
         }
      }

      public String getClassName() {
         return this.className;
      }

      public int getLineNumber() {
         return this.lineNum;
      }

      public String getMethodDescriptor() {
         return this.methodDesc;
      }

      public String getMethodName() {
         return this.methodName;
      }

      public String getModuleName() {
         return "Unknown";
      }

      public String getSourceFile() {
         return this.source;
      }

      public boolean isReturnGathered(String monitorType) {
         return false;
      }

      public GatheredArgument[] getGatheredArguments(String monitorType) {
         return InstrumentationSupport.EMPTY_GATHERED_ARGS;
      }

      public Map getPointcutHandlingInfoMap() {
         return this.pointcutHandlingInfoMap;
      }

      public boolean isStatic() {
         return this.isStatic;
      }

      public String getCallerClassName() {
         return this.callerClassName;
      }

      public String getCallerMethodName() {
         return this.callerMethodName;
      }

      public String getCallerMethodDescriptor() {
         return this.callerMethodDesc;
      }
   }

   private static class PlaceholderDelegatingMonitor implements DelegatingMonitor {
      private String type;

      PlaceholderDelegatingMonitor(String type) {
         this.type = type;
      }

      public String getAttribute(String attributeName) {
         return null;
      }

      public String[] getAttributeNames() {
         return InstrumentationSupport.EMPTY_STRINGS;
      }

      public String getDescription() {
         return null;
      }

      public String getName() {
         return this.type;
      }

      public String getType() {
         return this.type;
      }

      public boolean isArgumentsCaptureNeeded() {
         return false;
      }

      public boolean isComponentScopeAllowed() {
         return true;
      }

      public boolean isEnabled() {
         return false;
      }

      public boolean isEnabledAndNotDyeFiltered() {
         return false;
      }

      public boolean isServerScopeAllowed() {
         return true;
      }

      public void setAttribute(String attributeName, String attributeValue) {
      }

      public void setDescription(String description) {
      }

      public void setEnabled(boolean enable) {
      }

      public void setName(String name) {
      }

      public long getDyeMask() {
         return 0L;
      }

      public boolean isDyeFilteringEnabled() {
         return false;
      }

      public void setDyeFilteringEnabled(boolean enable) {
      }

      public void setDyeMask(long dye_mask) {
      }

      public void addAction(DiagnosticAction action) {
      }

      public DiagnosticAction[] getActions() {
         return InstrumentationSupport.EMPTY_ACTIONS;
      }

      public String[] getCompatibleActionTypes() {
         return InstrumentationSupport.EMPTY_STRINGS;
      }

      public void removeAction(DiagnosticAction action) {
      }

      public String[] getIncludes() {
         return InstrumentationSupport.EMPTY_STRINGS;
      }

      public void setIncludes(String[] includes) {
      }

      public String[] getExcludes() {
         return InstrumentationSupport.EMPTY_STRINGS;
      }

      public void setExcludes(String[] excludes) {
      }

      public boolean isServerManaged() {
         return false;
      }

      public void setServerManaged(boolean serverManaged) {
      }

      public String getDiagnosticVolume() {
         return "Off";
      }

      public void setDiagnosticVolume(String diagnosticVolume) {
      }

      public String getEventClassName() {
         return null;
      }

      public Class getEventClass() {
         return null;
      }

      public void setEventClassName(String eventClassName) {
      }
   }

   public interface InstrumentationSupportInterface {
      DiagnosticMonitor getMonitor(Class var1, String var2);

      DiagnosticMonitor getMonitor(ClassLoader var1, String var2, String var3);

      void process(JoinPoint var1, DiagnosticMonitor var2, DiagnosticAction[] var3);

      void preProcess(JoinPoint var1, DiagnosticMonitor var2, DiagnosticAction[] var3, DiagnosticActionState[] var4);

      void postProcess(JoinPoint var1, DiagnosticMonitor var2, DiagnosticAction[] var3, DiagnosticActionState[] var4);

      JoinPoint createJoinPoint(Class var1, String var2, String var3, String var4, String var5, int var6, String var7, String var8, String var9, Map var10, boolean var11);

      JoinPoint createJoinPoint(ClassLoader var1, String var2, String var3, String var4, String var5, int var6, String var7, String var8, String var9, Map var10, boolean var11);

      DynamicJoinPoint createDynamicJoinPoint(JoinPoint var1, Object[] var2, Object var3);

      PointcutHandlingInfo createPointcutHandlingInfo(ValueHandlingInfo var1, ValueHandlingInfo var2, ValueHandlingInfo[] var3);

      ValueHandlingInfo createValueHandlingInfo(String var1, String var2, boolean var3, boolean var4);

      Map makeMap(String[] var1, PointcutHandlingInfo[] var2);
   }
}
