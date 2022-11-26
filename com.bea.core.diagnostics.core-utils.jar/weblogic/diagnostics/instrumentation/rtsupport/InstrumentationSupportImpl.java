package weblogic.diagnostics.instrumentation.rtsupport;

import java.util.Map;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DelegatingMonitorControl;
import weblogic.diagnostics.instrumentation.DiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitorControl;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;
import weblogic.diagnostics.instrumentation.DynamicJoinPointImpl;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationManager;
import weblogic.diagnostics.instrumentation.InstrumentationScope;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.JoinPointImpl;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfoImpl;
import weblogic.diagnostics.instrumentation.StatelessDiagnosticAction;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfoImpl;
import weblogic.diagnostics.instrumentation.support.DyeInjectionMonitorSupport;
import weblogic.kernel.Kernel;
import weblogic.utils.PropertyHelper;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;

public final class InstrumentationSupportImpl implements InstrumentationSupport.InstrumentationSupportInterface {
   private static final boolean noSupport1 = PropertyHelper.getBoolean("weblogic.diagnostics.internal.noSupport1");
   private static final boolean noSupport2 = PropertyHelper.getBoolean("weblogic.diagnostics.internal.noSupport2");

   public InstrumentationSupportImpl() throws Exception {
      if (!Kernel.isServer()) {
         throw new Exception("Not running in server, this will cause InstrumentationSupport to be used instead");
      }
   }

   public DiagnosticMonitor getMonitor(Class clz, String type) {
      return this.getMonitor(clz.getClassLoader(), clz.getName(), type);
   }

   public DiagnosticMonitor getMonitor(ClassLoader loader, String className, String type) {
      if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_ACTIONS.debug("InstrumentationSupport.getMonitor: " + type + " for class " + className);
         StringBuffer buf = new StringBuffer();
         DisplayClassLoaderAnnotations(loader, buf);
         InstrumentationDebug.DEBUG_ACTIONS.debug("InstrumentationSupport.getMonitor: classloaders: " + buf.toString());
      }

      InstrumentationManager manager = InstrumentationManager.getInstrumentationManager();
      DiagnosticMonitorControl monitor = manager.getServerManagedMonitorControl(type);
      if (monitor != null) {
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Returning monitor of type " + type + " monitor=" + monitor);
         }

         return (DiagnosticMonitor)monitor;
      } else {
         InstrumentationScope scope = manager.getAssociatedScope(loader);
         if (scope == null) {
            String appName = null;

            for(ClassLoader cl = loader; cl != null && appName == null; cl = cl.getParent()) {
               appName = getInstrumentationScopeName(cl);
            }

            if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_ACTIONS.debug("InstrumentationSupport.getMonitor: appName=" + appName);
            }

            if (appName == null) {
               appName = "_WL_INTERNAL_SERVER_SCOPE";
            }

            if (appName != null) {
               scope = manager.findInstrumentationScope(appName);
               if (scope != null) {
                  manager.associateClassloaderWithScope(loader, scope);
               }
            }
         }

         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("InstrumentationSupport.getMonitor: scope=" + (scope != null ? scope.getName() : "null"));
         }

         if (scope != null) {
            monitor = scope.findDiagnosticMonitorControl(type);
         }

         if (monitor == null) {
            monitor = manager.getServerMonitor(type);
         }

         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("InstrumentationSupport.getMonitor: monitor=" + monitor);
         }

         if (monitor == null) {
            if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_ACTIONS.debug("Returning orphan monitor of type " + type);
            }

            monitor = new DelegatingMonitorControl(type, type);
         }

         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Returning monitor of type " + type + " monitor=" + monitor);
         }

         return (DiagnosticMonitor)monitor;
      }
   }

   public static void DisplayClassLoaderAnnotations(ClassLoader cl, StringBuffer buf) {
      for(int level = 0; cl != null; cl = cl.getParent()) {
         buf.append("\n    " + level + ": " + cl.getClass().getName() + "@" + cl.hashCode());
         if (cl instanceof GenericClassLoader) {
            GenericClassLoader gcl = (GenericClassLoader)cl;
            Annotation anno = gcl.getAnnotation();
            buf.append(" annotation=");
            buf.append(anno != null ? anno.toString() : "null");
         }

         ++level;
      }

   }

   public static String getInstrumentationScopeName(ClassLoader classLoader) {
      String appName;
      for(appName = null; classLoader != null && appName == null; classLoader = classLoader.getParent()) {
         if (classLoader instanceof GenericClassLoader) {
            GenericClassLoader gcl = (GenericClassLoader)classLoader;
            Annotation anno = gcl.getAnnotation();
            if (anno != null) {
               appName = anno.getApplicationName();
            }
         }
      }

      return appName;
   }

   public static boolean dyeMatches(DiagnosticMonitor monitor) {
      Correlation context = CorrelationFactory.findOrCreateCorrelation();
      return dyeMatches(monitor, context);
   }

   public static boolean dyeMatches(DiagnosticMonitor monitor, Correlation context) {
      if (context == null) {
         return true;
      } else if (monitor.isDyeFilteringEnabled()) {
         DiagnosticMonitorControl mc = (DiagnosticMonitorControl)monitor;
         long[] dyeMasks = mc.getDyeMasks();
         long[] var4 = dyeMasks;
         int var5 = dyeMasks.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            long dyeMask = var4[var6];
            if ((dyeMask & context.getDyeVector()) == dyeMask) {
               return true;
            }
         }

         return false;
      } else if (DyeInjectionMonitorSupport.isThrottlingEnabled()) {
         return (context.getDyeVector() & 4294967296L) != 0L;
      } else {
         return true;
      }
   }

   public void process(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions) {
      if (mon instanceof DiagnosticMonitorControl) {
         DiagnosticMonitorControl monitor = (DiagnosticMonitorControl)mon;
         int size = actions != null ? actions.length : 0;
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Executing process() for " + monitor.getType() + " with " + size + " actions");
         }

         DynamicJoinPointImpl dynImp = null;
         if (jp instanceof DynamicJoinPointImpl) {
            dynImp = (DynamicJoinPointImpl)jp;
            dynImp.setMonitorType(mon.getType());
         }

         for(int i = 0; i < size; ++i) {
            try {
               StatelessDiagnosticAction act = (StatelessDiagnosticAction)actions[i];
               act.process(jp);
            } catch (Throwable var9) {
               if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_ACTIONS.debug("Unexpected exception in process,  executing action " + i, var9);
               }
            }
         }

         if (dynImp != null) {
            dynImp.setMonitorType((String)null);
         }

      }
   }

   public void preProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states) {
      if (mon instanceof DiagnosticMonitorControl) {
         DiagnosticMonitorControl monitor = (DiagnosticMonitorControl)mon;
         int size = actions != null ? actions.length : 0;
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Executing preProcess() for " + monitor.getType() + " with " + size + " actions");
         }

         DynamicJoinPointImpl dynImp = null;
         if (jp instanceof DynamicJoinPointImpl) {
            dynImp = (DynamicJoinPointImpl)jp;
            dynImp.setMonitorType(mon.getType());
         }

         for(int i = 0; i < size; ++i) {
            try {
               AroundDiagnosticAction act = (AroundDiagnosticAction)actions[i];
               act.preProcess(jp, states[i]);
            } catch (Throwable var10) {
               if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_ACTIONS.debug("Unexpected exception in preProcess,  executing action " + i, var10);
               }
            }
         }

         if (dynImp != null) {
            dynImp.setMonitorType((String)null);
         }

      }
   }

   public void postProcess(JoinPoint jp, DiagnosticMonitor mon, DiagnosticAction[] actions, DiagnosticActionState[] states) {
      if (mon instanceof DiagnosticMonitorControl) {
         DiagnosticMonitorControl monitor = (DiagnosticMonitorControl)mon;
         int size = actions != null ? actions.length : 0;
         if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_ACTIONS.debug("Executing postProcess() for " + monitor.getType() + " with " + size + " actions");
         }

         DynamicJoinPointImpl dynImp = null;
         if (jp instanceof DynamicJoinPointImpl) {
            dynImp = (DynamicJoinPointImpl)jp;
            dynImp.setMonitorType(mon.getType());
         }

         for(int i = 0; i < size; ++i) {
            try {
               AroundDiagnosticAction act = (AroundDiagnosticAction)actions[i];
               act.postProcess(jp, states[i]);
            } catch (Throwable var10) {
               if (InstrumentationDebug.DEBUG_ACTIONS.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_ACTIONS.debug("Unexpected exception in postProcess,  executing action " + i, var10);
               }
            }
         }

         if (dynImp != null) {
            dynImp.setMonitorType((String)null);
         }

      }
   }

   public DynamicJoinPoint createDynamicJoinPoint(JoinPoint jp, Object[] args, Object retVal) {
      if (jp instanceof DynamicJoinPointImpl) {
         DynamicJoinPointImpl djp = (DynamicJoinPointImpl)jp;
         if (args != null) {
            djp.setArguments(args);
         }

         djp.setReturnValue(retVal);
         return djp;
      } else {
         return new DynamicJoinPointImpl(jp, args, retVal);
      }
   }

   public JoinPoint createJoinPoint(Class joinpointClass, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map infoMap, boolean isStatic) {
      return new JoinPointImpl(joinpointClass, source, className, methodName, methodDesc, lineNum, callerClassName, callerClassName, callerMethodDesc, infoMap, isStatic);
   }

   public JoinPoint createJoinPoint(ClassLoader loader, String source, String className, String methodName, String methodDesc, int lineNum, String callerClassName, String callerMethodName, String callerMethodDesc, Map infoMap, boolean isStatic) {
      return new JoinPointImpl(loader, source, className, methodName, methodDesc, lineNum, callerClassName, callerClassName, callerMethodDesc, infoMap, isStatic);
   }

   public PointcutHandlingInfo createPointcutHandlingInfo(ValueHandlingInfo classValueHandlingInfo, ValueHandlingInfo returnValueHandlingInfo, ValueHandlingInfo[] argumentValueHandlingInfo) {
      return new PointcutHandlingInfoImpl(classValueHandlingInfo, returnValueHandlingInfo, argumentValueHandlingInfo);
   }

   public ValueHandlingInfo createValueHandlingInfo(String name, String rendererClassName, boolean sensitive, boolean gathered) {
      return new ValueHandlingInfoImpl(name, rendererClassName, sensitive, gathered);
   }

   public Map makeMap(String[] names, PointcutHandlingInfo[] infos) {
      return PointcutHandlingInfoImpl.makeMap(names, infos);
   }
}
