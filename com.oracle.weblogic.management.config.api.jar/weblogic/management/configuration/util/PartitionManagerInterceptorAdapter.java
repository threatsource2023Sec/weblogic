package weblogic.management.configuration.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.HK2Invocation;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;

@PartitionManagerPartitionAPI
@Setup
@Teardown
public class PartitionManagerInterceptorAdapter implements MethodInterceptor {
   public static final String PARTITION_MBEAN_KEY = "__partitionMBean";
   public static final String RESOURCE_GROUP_MBEAN_KEY = "__resourceGroupMBean";
   public static final String DEPLOYMENTS_KEY = "__deployments";
   public static final String BASIC_DEPLOYMENTS_KEY = "__basicDeployments";
   public static final String PARTITION_RUNTIME_MBEAN_KEY = "__partitionRuntimeMBean";
   public static final String PARTITION_RUNTIME_IS_AT_LEAST_BOOTED = "__partitionRuntimeAtLeastBooted";
   public static final String IS_ADMIN_SERVER = "__isAdminServer";
   public static final String SERVER_NAME = "__serverName";
   public static final String DEBUG_LOG_KEY = "__debugLog";
   public static final String DATA_HAS_BEEN_SET_KEY = "__dataHasBeenSet";
   private final Map methodMap = this.prepareMethodMap();
   private DebugLog debugLog = null;
   private boolean hasDataBeenLoaded = false;
   @Inject
   private Provider dataLoaderProvider;
   static final int SERVER_SERVICE_INTERCEPTOR_RANK = -1000;
   private DeploymentSelector DEPLOYMENTS = new DeploymentSelector("__deployments") {
      protected boolean isExpectedArray(Object o) {
         return o instanceof DeploymentMBean[];
      }

      protected DeploymentMBean[] toArray(List list) {
         return (DeploymentMBean[])list.toArray(new DeploymentMBean[list.size()]);
      }
   };
   private DeploymentSelector BASIC_DEPLOYMENTS = new DeploymentSelector("__basicDeployments") {
      protected boolean isExpectedArray(Object o) {
         return o instanceof BasicDeploymentMBean[];
      }

      protected BasicDeploymentMBean[] toArray(List list) {
         return (BasicDeploymentMBean[])list.toArray(new BasicDeploymentMBean[list.size()]);
      }
   };

   private Map prepareMethodMap() {
      Map result = new HashMap();
      Method[] var2 = this.getClass().getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         result.put(m.getName(), m);
      }

      return result;
   }

   public Object invoke(MethodInvocation methodInvocation) throws Throwable {
      this.initDebugLog(methodInvocation);
      Method m = methodInvocation.getMethod();
      Object[] args = methodInvocation.getArguments();
      Method typedMethod = (Method)this.methodMap.get(m.getName());
      if (typedMethod == null) {
         return methodInvocation.proceed();
      } else {
         CallWrapper cw = new CallWrapper(this.getClass().getName(), methodInvocation.getMethod().getName());
         switch (methodInvocation.getMethod().getName()) {
            case "bootPartition":
               this.doBootPartition(methodInvocation, cw);
               break;
            case "startPartitionInAdmin":
            case "startPartition":
               this.doAStartPartition(methodInvocation, cw);
               break;
            case "shutdownPartition":
            case "forceShutdownPartition":
               this.doAShutdownPartition(methodInvocation, cw);
               break;
            case "haltPartition":
               this.doHaltPartition(methodInvocation, cw);
         }

         if (this.debugLog.isEnabled()) {
            this.debugLog.write(cw.toString());
         }

         switch (cw.disp) {
            case REDIRECTED:
               return cw.result;
            case TO_BE_SKIPPED:
               return methodInvocation.proceed();
            case TO_BE_RUN:
            case UNUSED:
               Object[] argsWithMethodInvocation = new Object[args.length + 1];
               nextSlot = 0;
               argsWithMethodInvocation[nextSlot++] = methodInvocation;

               for(int i = 0; i < args.length; ++i) {
                  argsWithMethodInvocation[nextSlot++] = args[i];
               }

               return typedMethod.invoke(this, argsWithMethodInvocation);
            default:
               throw new IllegalStateException("Unexpected call wrapper disposition: " + cw.disp.name());
         }
      }
   }

   public void bootPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (this.debugLog.isEnabled()) {
         this.debugLog.write("Redirecting bootPartition to startPartition for partition " + partitionName);
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
   }

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      methodInvocation.proceed();
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      methodInvocation.proceed();
   }

   public void haltPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (this.debugLog.isEnabled()) {
         this.debugLog.write("Redirecting haltPartition to forceShutdownInPartition for partition " + partitionName);
      }

      this.forceShutdownPartition(methodInvocation, partitionName);
   }

   public DeploymentMBean[] getDeployments(MethodInvocation methodInvocation, Class... types) {
      return (DeploymentMBean[])this.DEPLOYMENTS.select(methodInvocation, types);
   }

   public BasicDeploymentMBean[] getBasicDeployments(MethodInvocation methodInvocation, Class... types) {
      return (BasicDeploymentMBean[])this.BASIC_DEPLOYMENTS.select(methodInvocation, types);
   }

   public PartitionMBean getPartition(MethodInvocation methodInvocation) {
      return (PartitionMBean)this.getUserDataValue(methodInvocation, "__partitionMBean", PartitionMBean.class);
   }

   public ResourceGroupMBean getResourceGroup(MethodInvocation methodInvocation) {
      return (ResourceGroupMBean)this.getUserDataValue(methodInvocation, "__resourceGroupMBean", ResourceGroupMBean.class);
   }

   public boolean isAdminServer(MethodInvocation methodInvocation) {
      Boolean result = (Boolean)this.getUserDataValue(methodInvocation, "__isAdminServer", Boolean.class);
      return result != null && result;
   }

   public String getServerName(MethodInvocation methodInvocation) {
      return (String)this.getUserDataValue(methodInvocation, "__serverName", String.class);
   }

   private boolean isPartitionAtLeastBooted(MethodInvocation methodInvocation) {
      Boolean result = (Boolean)this.getUserDataValue(methodInvocation, "__partitionRuntimeAtLeastBooted", Boolean.class);
      return result != null && result;
   }

   private boolean isPartitionHalted(MethodInvocation methodInvocation) {
      return !this.isPartitionAtLeastBooted(methodInvocation);
   }

   private boolean isAnyAdminRGTargeted(MethodInvocation methodInvocation) {
      PartitionMBean partition = this.getPartition(methodInvocation);
      return partition != null && partition.findAdminResourceGroupsTargeted(this.getServerName(methodInvocation)).length > 0;
   }

   private void doBootPartition(MethodInvocation methodInvocation, CallWrapper cw) throws Throwable {
      if (this.shouldDoBoot(methodInvocation)) {
         cw.markToRun();
      } else {
         cw.markToSkip();
      }

   }

   private void doAStartPartition(MethodInvocation methodInvocation, CallWrapper cw) throws Throwable {
      if (this.shouldDoStart(methodInvocation)) {
         cw.markToRun();
      } else {
         cw.markToSkip();
      }

   }

   private void doAShutdownPartition(MethodInvocation methodInvocation, CallWrapper cw) throws Throwable {
      if (this.shouldDoShutdown(methodInvocation)) {
         cw.markToRun();
      } else {
         cw.markToSkip();
      }

   }

   private void doHaltPartition(MethodInvocation methodInvocation, CallWrapper cw) throws Throwable {
      if (this.shouldDoHalt(methodInvocation)) {
         cw.markToRun();
      } else {
         cw.markToSkip();
      }

   }

   protected boolean shouldDoBoot(MethodInvocation methodInvocation) {
      return this.isAdminServer(methodInvocation) || this.isAnyAdminRGTargeted(methodInvocation);
   }

   protected boolean shouldDoStart(MethodInvocation methodInvocation) {
      return this.isPartitionHalted(methodInvocation);
   }

   protected boolean shouldDoShutdown(MethodInvocation methodInvocation) {
      return !this.isAdminServer(methodInvocation) && !this.isAnyAdminRGTargeted(methodInvocation);
   }

   protected boolean shouldDoHalt(MethodInvocation methodInvocation) {
      return !this.isPartitionHalted(methodInvocation);
   }

   private String firstArgAsString(MethodInvocation methodInvocation) {
      Object[] args = methodInvocation.getArguments();
      if (args != null && args.length != 0 && args[0] instanceof String) {
         return (String)args[0];
      } else {
         throw new IllegalArgumentException("Expected first argument of type String");
      }
   }

   private Object getUserDataValue(MethodInvocation methodInvocation, String userDataKey, Class tClass) {
      Object o = this.convertInvocation(methodInvocation).getUserData(userDataKey);
      if (o == null) {
         return null;
      } else if (!tClass.isAssignableFrom(o.getClass())) {
         throw new IllegalArgumentException("Expected " + tClass.getName() + " but found " + o.getClass().getName());
      } else {
         return o;
      }
   }

   private void initDebugLog(MethodInvocation methodInvocation) {
      this.debugLog = (DebugLog)this.getUserDataValue(methodInvocation, "__debugLog", DebugLog.class);
      if (this.debugLog == null) {
         this.debugLog = new DebugLog() {
            public boolean isEnabled() {
               return false;
            }

            public void write(String message) {
            }
         };
      }

   }

   public Object getPartitionRuntime(MethodInvocation methodInvocation) {
      return this.getUserDataValue(methodInvocation, "__partitionRuntimeMBean", Object.class);
   }

   private HK2Invocation convertInvocation(MethodInvocation methodInvocation) {
      if (!(methodInvocation instanceof HK2Invocation)) {
         throw new IllegalArgumentException("Expected method invocation of type HK2Invocation");
      } else {
         if (!this.hasDataBeenLoaded) {
            try {
               if (this.dataLoaderProvider != null && methodInvocation.getMethod().getDeclaringClass().getName().endsWith("PartitionManagerService")) {
                  PartitionManagerInterceptorDataLoader dataLoader = (PartitionManagerInterceptorDataLoader)this.dataLoaderProvider.get();
                  if (dataLoader != null) {
                     dataLoader.loadUserData(methodInvocation);
                     this.hasDataBeenLoaded = true;
                  }
               }
            } catch (Exception var3) {
               throw new RuntimeException(var3);
            }
         }

         return (HK2Invocation)methodInvocation;
      }
   }

   private String extractPartitionName(Object[] args, Method m) {
      if (args.length == 0) {
         throw new IllegalArgumentException("Intercepted method should pass arguments but did not:" + m.getName());
      } else if (!(args[0] instanceof String)) {
         throw new IllegalArgumentException("Intercepted method should pass partition name (String) as first argument but did not: " + m.getName() + ", " + args[0].getClass().getName());
      } else {
         return (String)args[0];
      }
   }

   private abstract class DeploymentSelector {
      private String key;

      private DeploymentSelector(String key) {
         this.key = key;
      }

      protected abstract boolean isExpectedArray(Object var1);

      protected abstract ConfigurationMBean[] toArray(List var1);

      protected ConfigurationMBean[] select(MethodInvocation methodInvocation, Class... types) {
         HK2Invocation mi = PartitionManagerInterceptorAdapter.this.convertInvocation(methodInvocation);
         Object o = mi.getUserData(this.key);
         if (o == null) {
            throw new IllegalStateException(this.key + "  not present in user data");
         } else if (!this.isExpectedArray(o)) {
            throw new IllegalArgumentException("Internal data " + this.key + " not expected to be of type " + o.getClass().getName());
         } else {
            ConfigurationMBean[] deps = (ConfigurationMBean[])((ConfigurationMBean[])o);
            if (types != null && types.length != 0) {
               List result = new ArrayList();
               ConfigurationMBean[] var7 = deps;
               int var8 = deps.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  ConfigurationMBean dep = var7[var9];
                  Class[] var11 = types;
                  int var12 = types.length;

                  for(int var13 = 0; var13 < var12; ++var13) {
                     Class type = var11[var13];
                     if (type.isAssignableFrom(dep.getClass())) {
                        result.add(dep);
                        break;
                     }
                  }
               }

               return this.toArray(result);
            } else {
               return deps;
            }
         }
      }

      protected void store(HK2Invocation mi, ConfigurationMBean[] deps) {
         mi.setUserData(this.key, deps);
      }

      // $FF: synthetic method
      DeploymentSelector(String x1, Object x2) {
         this(x1);
      }
   }

   public interface DebugLog {
      boolean isEnabled();

      void write(String var1);
   }

   private static class CallWrapper {
      Disposition disp;
      Object result;
      final String methodName;
      final String className;
      String redirectedMethodName;

      CallWrapper(String className, String methodName) {
         this.disp = PartitionManagerInterceptorAdapter.CallWrapper.Disposition.UNUSED;
         this.result = null;
         this.className = className;
         this.methodName = methodName;
      }

      void markRedirected(String methodName) {
         this.redirectedMethodName = methodName;
         this.disp = PartitionManagerInterceptorAdapter.CallWrapper.Disposition.REDIRECTED;
      }

      void markToSkip() {
         this.disp = PartitionManagerInterceptorAdapter.CallWrapper.Disposition.TO_BE_SKIPPED;
      }

      void markToRun() {
         this.disp = PartitionManagerInterceptorAdapter.CallWrapper.Disposition.TO_BE_RUN;
      }

      public String toString() {
         return "CallWrapper: " + this.className + "." + this.methodName + ", disposition = " + this.disp.name() + (this.disp == PartitionManagerInterceptorAdapter.CallWrapper.Disposition.REDIRECTED ? " to " + this.redirectedMethodName : "");
      }

      static enum Disposition {
         UNUSED,
         REDIRECTED,
         TO_BE_SKIPPED,
         TO_BE_RUN;
      }
   }
}
