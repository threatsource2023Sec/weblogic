package weblogic.diagnostics.instrumentation.rtsupport;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;
import weblogic.diagnostics.instrumentation.DynamicJoinPointImpl;
import weblogic.diagnostics.instrumentation.InstrumentationSupportBase;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.JoinPointImpl;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.MonitorLocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfoImpl;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfoImpl;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.spring.monitoring.actions.AbstractApplicationContextRefreshAction;
import weblogic.spring.monitoring.actions.AbstractBeanFactoryCreateBeanAction;
import weblogic.spring.monitoring.actions.AbstractBeanFactoryGetBeanAction;
import weblogic.spring.monitoring.actions.AbstractBeanFactoryRegisterScopeAction;
import weblogic.spring.monitoring.actions.AbstractPlatformTransactionManagerCommitAction;
import weblogic.spring.monitoring.actions.AbstractPlatformTransactionManagerResumeAction;
import weblogic.spring.monitoring.actions.AbstractPlatformTransactionManagerRollbackAction;
import weblogic.spring.monitoring.actions.AbstractPlatformTransactionManagerSuspendAction;
import weblogic.spring.monitoring.actions.ApplicationContextObtainFreshBeanFactoryAction;
import weblogic.spring.monitoring.actions.DefaultListableBeanFactoryGetBeanNamesForTypeAction;
import weblogic.spring.monitoring.actions.DefaultListableBeanFactoryGetBeansOfTypeAction;

public class SpringInstSupportImpl extends InstrumentationSupportBase {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");
   private static boolean legacyUnknownSpringMessageIssued = false;
   private static boolean legacySpringMessageIssued = false;
   private static final ValueHandlingInfo LEGACY_SPRING_INFO = new ValueHandlingInfoImpl((String)null, (String)null, false, false);
   private static Map monitorMap = new HashMap();

   public static synchronized DiagnosticMonitor getMonitor(Class clz, String type) {
      return getMonitor(clz.getClassLoader(), clz.getName(), type);
   }

   public static synchronized DiagnosticMonitor getMonitor(ClassLoader loader, String className, String type) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SpringInstSupportImpl.getMonitor: clz=" + className + " type=" + type);
      }

      SpringDelegatingMonitor mon = (SpringDelegatingMonitor)monitorMap.get(type);
      if (mon == null) {
         mon = new SpringDelegatingMonitor(type);
         Object act;
         if ("Spring_Around_Internal_Application_Context_Obtain_Fresh_Bean_Factory".equals(type)) {
            act = new ApplicationContextObtainFreshBeanFactoryAction();
         } else if ("Spring_Around_Internal_Abstract_Application_Context_Refresh".equals(type)) {
            act = new AbstractApplicationContextRefreshAction();
         } else if ("Spring_Around_Internal_Abstract_Bean_Factory_Create_Bean".equals(type)) {
            act = new AbstractBeanFactoryCreateBeanAction();
         } else if ("Spring_Around_Internal_Abstract_Bean_Factory_Get_Bean".equals(type)) {
            act = new AbstractBeanFactoryGetBeanAction();
         } else if ("Spring_Around_Internal_Abstract_Bean_Factory_Register_Scope".equals(type)) {
            act = new AbstractBeanFactoryRegisterScopeAction();
         } else if ("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Commit".equals(type)) {
            act = new AbstractPlatformTransactionManagerCommitAction();
         } else if ("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Resume".equals(type)) {
            act = new AbstractPlatformTransactionManagerResumeAction();
         } else if ("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Rollback".equals(type)) {
            act = new AbstractPlatformTransactionManagerRollbackAction();
         } else if ("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Suspend".equals(type)) {
            act = new AbstractPlatformTransactionManagerSuspendAction();
         } else if ("Spring_Around_Internal_Default_Listable_Bean_Factory_Get_Bean_Names_For_Type".equals(type)) {
            act = new DefaultListableBeanFactoryGetBeanNamesForTypeAction();
         } else {
            if (!"Spring_Around_Internal_Default_Listable_Bean_Factory_Get_Beans_Of_Type".equals(type)) {
               return null;
            }

            act = new DefaultListableBeanFactoryGetBeansOfTypeAction();
         }

         ((AroundDiagnosticAction)act).setDiagnosticMonitor(mon);
         DiagnosticAction[] actions = new DiagnosticAction[]{(DiagnosticAction)act};
         mon.setActions(actions);
         monitorMap.put(type, mon);
      }

      return mon;
   }

   public static JoinPoint createJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum) {
      Map pointcutHandlingInfoMap = null;
      boolean isStatic = false;
      if (methodName != null) {
         if (methodName.equals("obtainFreshBeanFactory")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Application_Context_Obtain_Fresh_Bean_Factory", true, true, 0);
         } else if (methodName.equals("refresh")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Application_Context_Refresh", false, true, 0);
         } else if (methodName.equals("createBean")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Bean_Factory_Create_Bean", false, true, 0);
         } else if (methodName.equals("getBean")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Bean_Factory_Get_Bean", false, true, 0);
         } else if (methodName.equals("registerScope")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Bean_Factory_Register_Scope", false, true, 2);
         } else if (methodName.equals("commit")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Commit", false, true, 0);
         } else if (methodName.equals("resume")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Resume", false, true, 0);
         } else if (methodName.equals("rollback")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Rollback", false, true, 0);
         } else if (methodName.equals("suspend")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Suspend", false, true, 0);
         } else if (methodName.equals("getBeanNamesForType")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Default_Listable_Bean_Factory_Get_Bean_Names_For_Type", false, true, 0);
         } else if (methodName.equals("getBeansOfType")) {
            pointcutHandlingInfoMap = createSpringLegacyMap("Spring_Around_Internal_Default_Listable_Bean_Factory_Get_Beans_Of_Type", false, true, 0);
         } else if (!legacyUnknownSpringMessageIssued) {
            DiagnosticsLogger.logLegacySpringInstrumentationUnknownMethod(methodName);
            legacyUnknownSpringMessageIssued = true;
         }
      }

      if (pointcutHandlingInfoMap != null && !legacySpringMessageIssued) {
         DiagnosticsLogger.logLegacySpringInstrumentationCalled();
         legacySpringMessageIssued = true;
      }

      return new JoinPointImpl(joinpointClass, source, className, methodName, methodDesc, lineNum, (String)null, (String)null, (String)null, pointcutHandlingInfoMap, isStatic);
   }

   private static Map createSpringLegacyMap(String monitorName, boolean markReturn, boolean markClass, int markNumArgs) {
      ValueHandlingInfo returnInfo = markReturn ? LEGACY_SPRING_INFO : null;
      ValueHandlingInfo classInfo = markClass ? LEGACY_SPRING_INFO : null;
      ValueHandlingInfo[] argInfos = null;
      if (markNumArgs > 0) {
         argInfos = new ValueHandlingInfo[markNumArgs];

         for(int i = 0; i < markNumArgs; ++i) {
            argInfos[i] = LEGACY_SPRING_INFO;
         }
      }

      PointcutHandlingInfo pcInfo = new PointcutHandlingInfoImpl(classInfo, returnInfo, argInfos);
      Map legacyMap = new HashMap();
      legacyMap.put(monitorName, pcInfo);
      return legacyMap;
   }

   public static JoinPoint createJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, Map infoMap, boolean isStatic) {
      return createJoinPoint((Class)joinpointClass, source, className, methodName, methodDesc, lineNum, (String)null, (String)null, (String)null, infoMap, isStatic);
   }

   public static JoinPoint createJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map infoMap, boolean isStatic) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.createJoinPoint");
      }

      return new JoinPointImpl(joinpointClass, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodName, infoMap, isStatic);
   }

   public static JoinPoint createJoinPoint(ClassLoader loader, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map infoMap, boolean isStatic) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.createJoinPoint");
      }

      return new JoinPointImpl(loader, source, className, methodName, methodDesc, lineNum, callerClassName, callerMethodName, callerMethodName, infoMap, isStatic);
   }

   public static DynamicJoinPoint createDynamicJoinPoint(JoinPoint jp, Object[] args, Object retVal) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.createDynamicJoinPoint");
      }

      return new DynamicJoinPointImpl(jp, args, retVal);
   }

   public static void createDynamicJoinPoint(LocalHolder holder) {
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      monHolder.djp = createDynamicJoinPoint((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.captureArgs ? holder.args : null, holder.ret);
   }

   public static PointcutHandlingInfo createPointcutHandlingInfo(ValueHandlingInfo classValueHandlingInfo, ValueHandlingInfo returnValueHandlingInfo, ValueHandlingInfo[] argumentValueHandlingInfo) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.createPointcutHandlingInfo");
      }

      return new PointcutHandlingInfoImpl(classValueHandlingInfo, returnValueHandlingInfo, argumentValueHandlingInfo);
   }

   public static ValueHandlingInfo createValueHandlingInfo(String name, String rendererClassName, boolean sensitive, boolean gathered) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.createValueHandlingInfo");
      }

      return new ValueHandlingInfoImpl(name, rendererClassName, sensitive, gathered);
   }

   public static Map makeMap(String[] names, PointcutHandlingInfo[] infos) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.makeMap");
      }

      return PointcutHandlingInfoImpl.makeMap(names, infos);
   }

   public static void preProcess(LocalHolder holder) {
      applyActionStates(holder);
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      preProcess((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor, monHolder.actions, monHolder.states);
   }

   public static void preProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.preProcess: mon=" + mon.getName());
      }

      DynamicJoinPointImpl dynImp = null;
      if (jp instanceof DynamicJoinPointImpl) {
         dynImp = (DynamicJoinPointImpl)jp;
         dynImp.setMonitorType(mon.getType());
      }

      int size = actions != null ? actions.length : 0;

      for(int i = 0; i < size; ++i) {
         try {
            AroundDiagnosticAction act = (AroundDiagnosticAction)actions[i];
            act.preProcess(jp, states[i]);
         } catch (Throwable var8) {
            UnexpectedExceptionHandler.handle("Unexpected exception in preProcess,  executing action " + i, var8);
         }
      }

      if (dynImp != null) {
         dynImp.setMonitorType((String)null);
      }

   }

   public static void postProcess(LocalHolder holder) {
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      postProcess((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor, monHolder.actions, monHolder.states);
   }

   public static void postProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states, Throwable th) {
      postProcess(jp, mon, actions, states);
   }

   public static void postProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.postProcess: mon=" + mon.getName());
      }

      DynamicJoinPointImpl dynImp = null;
      if (jp instanceof DynamicJoinPointImpl) {
         dynImp = (DynamicJoinPointImpl)jp;
         dynImp.setMonitorType(mon.getType());
      }

      int size = actions != null ? actions.length : 0;

      for(int i = 0; i < size; ++i) {
         try {
            AroundDiagnosticAction act = (AroundDiagnosticAction)actions[i];
            act.postProcess(jp, states[i]);
         } catch (Throwable var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unexpected exception in postProcess,  executing action " + i, var8);
            }
         }
      }

      if (dynImp != null) {
         dynImp.setMonitorType((String)null);
      }

   }

   public static void process(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, Throwable th) {
      process(jp, mon, actions);
   }

   public static void process(LocalHolder holder) {
      MonitorLocalHolder monHolder = holder.monitorHolder[holder.monitorIndex];
      process((JoinPoint)(monHolder.djp == null ? holder.jp : monHolder.djp), monHolder.monitor, monHolder.actions);
   }

   public static void process(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Executing SpringInstSupportImpl.process: mon=" + mon.getName());
      }

      DynamicJoinPointImpl dynImp = null;
      if (jp instanceof DynamicJoinPointImpl) {
         dynImp = (DynamicJoinPointImpl)jp;
         dynImp.setMonitorType(mon.getType());
      }

      int size = actions != null ? actions.length : 0;

      for(int i = 0; i < size; ++i) {
         try {
            StatelessDiagnosticAction act = (StatelessDiagnosticAction)actions[i];
            act.process(jp);
         } catch (Throwable var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unexpected exception in process,  executing action " + i, var7);
            }
         }
      }

      if (dynImp != null) {
         dynImp.setMonitorType((String)null);
      }

   }

   private static class SpringDelegatingMonitor implements DelegatingMonitor {
      private String type;
      private DiagnosticAction[] actions;

      SpringDelegatingMonitor(String type) {
         this.type = type;
      }

      public String getAttribute(String attributeName) {
         return null;
      }

      public String[] getAttributeNames() {
         return null;
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
         return true;
      }

      public boolean isComponentScopeAllowed() {
         return true;
      }

      public boolean isEnabled() {
         return true;
      }

      public boolean isEnabledAndNotDyeFiltered() {
         return true;
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

      public void setActions(DiagnosticAction[] actions) {
         this.actions = actions;
      }

      public DiagnosticAction[] getActions() {
         return this.actions;
      }

      public String[] getCompatibleActionTypes() {
         return null;
      }

      public void removeAction(DiagnosticAction action) {
      }

      public String[] getIncludes() {
         return null;
      }

      public void setIncludes(String[] includes) {
      }

      public String[] getExcludes() {
         return null;
      }

      public void setExcludes(String[] excludes) {
      }

      public String getDiagnosticVolume() {
         return "Off";
      }

      public boolean isServerManaged() {
         return false;
      }

      public void setDiagnosticVolume(String diagnosticVolume) {
      }

      public void setServerManaged(boolean serverManaged) {
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
}
