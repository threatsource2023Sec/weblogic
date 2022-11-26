package weblogic.rmi.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.utils.PartitionManagerPartitionStateAPI;

@Service
@Interceptor
@ContractsProvided({MethodInterceptor.class, PartitionStateInterceptor.class})
@PartitionManagerPartitionStateAPI
public class PartitionStateInterceptor implements MethodInterceptor {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("PartitionStateInterceptor");
   private static final Object sync = new Object();
   private static final Set listeners = Collections.newSetFromMap(new ConcurrentHashMap());
   private static final ConcurrentHashMap stateMap = new ConcurrentHashMap();
   private static MBeanServer server;
   private static final Map interceptMethods = new HashMap();
   private static final InterceptorClass interceptorInstance = new InterceptorClass();

   public static void addListener(PartitionStateChangeListener listener) {
      listeners.add(listener);
   }

   public static void removeListener(PartitionStateChangeListener listener) {
      listeners.remove(listener);
   }

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      if (KernelStatus.DEBUG && debugLogger.isDebugEnabled()) {
         debugLogger.debug("Invoke " + methodInvocation.getMethod() + " params=" + Arrays.toString(methodInvocation.getArguments()));
      }

      Object result = methodInvocation.proceed();
      Method m = (Method)interceptMethods.get(methodInvocation.getMethod().getName());
      if (m != null) {
         synchronized(sync) {
            m.invoke(interceptorInstance, methodInvocation.getArguments()[0]);
         }
      } else if (KernelStatus.DEBUG && debugLogger.isDebugEnabled()) {
         debugLogger.debug("Unknown partition life cycle method: " + methodInvocation.getMethod());
      }

      return result;
   }

   public static Object getSync() {
      return sync;
   }

   public static State getPartitionState(String partitionName) {
      State state = (State)stateMap.get(partitionName);
      if (state == null) {
         synchronized(sync) {
            state = (State)stateMap.get(partitionName);
            if (state == null) {
               state = doesExist(partitionName) ? PartitionStateInterceptor.State.SHUTDOWN : null;
               if (state != null) {
                  stateMap.putIfAbsent(partitionName, state);
               }
            }
         }
      }

      return state;
   }

   public static boolean isPartitionRunning(String partitionName) {
      return PartitionStateInterceptor.State.RUNNING.equals(getPartitionState(partitionName));
   }

   public static boolean isPartitionSuspended(String partitionName) {
      return PartitionStateInterceptor.State.SUSPEND.equals(getPartitionState(partitionName));
   }

   public static boolean isPartitionShutdown(String partitionName) {
      return PartitionStateInterceptor.State.SHUTDOWN.equals(getPartitionState(partitionName));
   }

   public static boolean isPartitionExist(String partitionName) {
      return getPartitionState(partitionName) != null;
   }

   private static boolean doesExist(String partitionName) {
      try {
         if (server == null) {
            InitialContext ctx = new InitialContext();
            server = (MBeanServer)ctx.lookup("java:comp/jmx/runtime");
         }

         ObjectName runtimeService = new ObjectName("com.bea:Name=" + partitionName + ",Type=Partition");
         return server.getObjectInstance(runtimeService) != null;
      } catch (MalformedObjectNameException var2) {
         debugLogger.debug("Unexpected exception: " + var2.getClass() + " " + var2.getMessage());
      } catch (NamingException var3) {
         debugLogger.debug("Unexpected exception: " + var3.getClass() + " " + var3.getMessage());
      } catch (InstanceNotFoundException var4) {
      }

      return false;
   }

   private static void notifyListeners(String partitionName, State oldState, State newState) {
      Iterator var3 = listeners.iterator();

      while(var3.hasNext()) {
         PartitionStateChangeListener listener = (PartitionStateChangeListener)var3.next();
         listener.onChanged(partitionName, oldState, newState);
      }

   }

   static {
      Method[] var0 = InterceptorClass.class.getMethods();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Method m = var0[var2];
         interceptMethods.put(m.getName(), m);
      }

   }

   public static enum State {
      SHUTDOWN,
      SUSPEND,
      RUNNING;

      public static State fromPartitionMBeanState(String state) {
         switch (state) {
            case "STARTING":
            case "RUNNING":
            case "RESUMING":
               return RUNNING;
            case "SHUTDOWN":
            case "SHUTTING_DOWN":
            case "FORCE_SHUTTING_DOWN":
               return SHUTDOWN;
            case "STARTING_IN_ADMIN":
            case "ADMIN":
            case "SUSPENDING":
            case "FORCE_SUSPENDING":
               return SUSPEND;
            default:
               throw new IllegalStateException("unknown state: " + state);
         }
      }
   }

   public interface PartitionStateChangeListener {
      void onChanged(String var1, State var2, State var3);
   }

   private static class InterceptorClass {
      private InterceptorClass() {
      }

      public void startPartition(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### startPartition interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.RUNNING);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.RUNNING);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.RUNNING);
      }

      public void resumePartition(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### resumePartition interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.RUNNING);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.RUNNING);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.RUNNING);
      }

      public void startPartitionInAdmin(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### startPartitionInAdmin interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.SUSPEND);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.SUSPEND);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.SUSPEND);
      }

      public void suspendPartition(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### suspendPartition interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.SUSPEND);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.SUSPEND);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.SUSPEND);
      }

      public void forceSuspendPartition(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### forceSuspendPartition interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.SUSPEND);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.SUSPEND);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.SUSPEND);
      }

      public void shutdownPartition(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### shutdownPartition interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.SHUTDOWN);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.SHUTDOWN);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.SHUTDOWN);
      }

      public void forceShutdownPartition(String partitionName) {
         if (KernelStatus.DEBUG && PartitionStateInterceptor.debugLogger.isDebugEnabled()) {
            PartitionStateInterceptor.debugLogger.debug("########### forceShutdownPartition interceptor. " + partitionName + " -> " + PartitionStateInterceptor.State.SHUTDOWN);
         }

         State oldState = (State)PartitionStateInterceptor.stateMap.put(partitionName, PartitionStateInterceptor.State.SHUTDOWN);
         PartitionStateInterceptor.notifyListeners(partitionName, oldState, PartitionStateInterceptor.State.SHUTDOWN);
      }

      // $FF: synthetic method
      InterceptorClass(Object x0) {
         this();
      }
   }
}
