package weblogic.work.concurrent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.enterprise.concurrent.ContextService;
import javax.naming.Context;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.concurrent.context.ApplicationContextProcessor;
import weblogic.work.concurrent.context.ContextSetupProcessor;
import weblogic.work.concurrent.partition.PartitionContextProcessor;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.spi.ServerStatusChecker;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.concurrent.utils.LogUtils;
import weblogic.work.concurrent.utils.ManagedTaskUtils;

public class ContextServiceImpl extends AbstractConcurrentManagedObject implements ContextService {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private static long currentID;
   private final String seqID;
   private static Set startedContextServices = Collections.synchronizedSet(new HashSet());
   private final ServerStatusChecker stateObj;

   public ContextServiceImpl(ConcurrentManagedObjectBuilder builder) {
      super(builder);
      this.seqID = nextID();
      this.stateObj = new ServerStatusChecker();
      if (this.getContextSetupProcessor() == null) {
         if (this.appId != null) {
            this.setContextSetupProcessor(new ContextSetupProcessor(this.appId, this.moduleId, this.seqID, this.toString()));
         } else {
            this.setContextSetupProcessor(new PartitionContextProcessor(this.partitionName, this.parCL, this.seqID, this.toString()));
         }
      }

   }

   ContextServiceImpl(ContextServiceImpl impl, ContextProvider provider) {
      super(impl, provider);
      this.moduleId = null;
      this.seqID = impl.seqID;
      this.stateObj = impl.stateObj;
   }

   public String getSeqID() {
      return this.seqID;
   }

   private static synchronized String nextID() {
      StringBuilder builder = new StringBuilder();
      builder.append(System.currentTimeMillis());
      builder.append((long)(currentID++));
      return builder.toString();
   }

   private void rejectUnsupportedRequestOutOfCS() {
      try {
         this.rejectIfOutOfScope();
         this.rejectIfSubmittingCompNotStarted();
      } catch (RejectException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   private void rejectIfCSNotStarted() {
      if (!this.isStarted()) {
         throw new IllegalArgumentException(ConcurrencyLogger.logCSNotStartLoggable(this.toString()).getMessage());
      }
   }

   public Object createContextualProxy(Object instance, Class... interfaces) {
      return this.createContextualProxy(instance, (Map)null, (Class[])interfaces);
   }

   private void warnUserObjectCheckSkipped(Object instance, Map executionProperties) {
      if (this.warnIfUserObjectCheckSkipped) {
         LogUtils.warnSkipClassloaderCheck(this.name, this.getTaskName(instance, executionProperties));
         this.warnIfUserObjectCheckSkipped = false;
      }

   }

   private String getTaskName(Object instance, Map executionProperties) {
      if (executionProperties == null) {
         return instance.toString();
      } else {
         String taskProp = (String)executionProperties.get("javax.enterprise.concurrent.IDENTITY_NAME");
         return taskProp == null ? instance.toString() : taskProp;
      }
   }

   public Object createContextualProxy(Object instance, Map executionProperties, Class... interfaces) {
      this.rejectUnsupportedRequestOutOfCS();
      this.rejectIfCSNotStarted();
      if (instance == null) {
         throw new IllegalArgumentException(ConcurrencyLogger.logNullInstanceLoggable().getMessage());
      } else if (interfaces != null && interfaces.length != 0) {
         Class instanceClass = instance.getClass();
         Class[] var5 = interfaces;
         int var6 = interfaces.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class thisInterface = var5[var7];
            if (!thisInterface.isAssignableFrom(instanceClass)) {
               throw new IllegalArgumentException(ConcurrencyLogger.logInterfacesNotImplementedLoggable().getMessage());
            }
         }

         if (this.cmoType == 1) {
            if (ManagedTaskUtils.isCheckUserObject(executionProperties)) {
               try {
                  ConcurrentUtils.serializeByClassloader(instance, this.parCL);
               } catch (Exception var9) {
                  throw new IllegalArgumentException(var9);
               }
            } else {
               this.warnUserObjectCheckSkipped(instance, executionProperties);
            }
         }

         ContextProxyInvocationHandler handler = new ContextProxyInvocationHandler(this.seqID, this.getContextSetupProcessor(), instance, executionProperties);
         Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(), interfaces, handler);
         return proxy;
      } else {
         throw new IllegalArgumentException(ConcurrencyLogger.logNoInterfaceLoggable().getMessage());
      }
   }

   public Object createContextualProxy(Object instance, Class intf) {
      return this.createContextualProxy(instance, (Map)null, (Class)intf);
   }

   public Object createContextualProxy(Object instance, Map executionProperties, Class intf) {
      this.rejectUnsupportedRequestOutOfCS();
      this.rejectIfCSNotStarted();
      if (instance == null) {
         throw new IllegalArgumentException(ConcurrencyLogger.logNullInstanceLoggable().getMessage());
      } else if (intf == null) {
         throw new IllegalArgumentException(ConcurrencyLogger.logNoInterfaceLoggable().getMessage());
      } else {
         if (this.cmoType == 1) {
            if (ManagedTaskUtils.isCheckUserObject(executionProperties)) {
               try {
                  ConcurrentUtils.serializeByClassloader(instance, this.parCL);
               } catch (Exception var6) {
                  throw new IllegalArgumentException(var6);
               }
            } else {
               this.warnUserObjectCheckSkipped(instance, executionProperties);
            }
         }

         ContextProxyInvocationHandler handler = new ContextProxyInvocationHandler(this.seqID, this.getContextSetupProcessor(), instance, executionProperties);
         Object proxy = Proxy.newProxyInstance(instance.getClass().getClassLoader(), new Class[]{intf}, handler);
         return proxy;
      }
   }

   public Map getExecutionProperties(Object contextObject) {
      try {
         this.rejectIfOutOfScope();
      } catch (RejectException var3) {
         throw new IllegalArgumentException(var3);
      }

      ContextProxyInvocationHandler handler = this.verifyHandler(contextObject);
      return handler.getExecutionProperties();
   }

   private ContextProxyInvocationHandler verifyHandler(Object contextObject) {
      InvocationHandler handler = Proxy.getInvocationHandler(contextObject);
      if (handler instanceof ContextProxyInvocationHandler) {
         ContextProxyInvocationHandler cpih = (ContextProxyInvocationHandler)handler;
         if (!this.seqID.equals(cpih.getSeqID())) {
            throw new IllegalArgumentException(ConcurrencyLogger.logDifferentCSLoggable().getMessage());
         } else {
            return cpih;
         }
      } else {
         throw new IllegalArgumentException(ConcurrencyLogger.logInvalidProxyLoggable().getMessage());
      }
   }

   public boolean start() {
      boolean success = this.stateObj.start();
      if (success) {
         startedContextServices.add(this.seqID);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this + " started, flag=" + success + ", isStarted=" + isStarted(this.seqID));
      }

      return success;
   }

   public boolean stop() {
      boolean success = this.stateObj.stop();
      if (success) {
         startedContextServices.remove(this.seqID);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this + " stopped, flag=" + success + ", isStarted=" + isStarted(this.seqID));
      }

      return success;
   }

   public boolean isStarted() {
      return this.stateObj.isStarted();
   }

   public static boolean isStarted(String seqID) {
      return startedContextServices.contains(seqID);
   }

   public String getJSR236Class() {
      return ContextService.class.getName();
   }

   AbstractConcurrentManagedObject.ConcurrentOpaqueReference createApplicationDelegator(ClassLoader classLoader, Context jndiContext) {
      return new AbstractConcurrentManagedObject.ConcurrentOpaqueReference(new ContextServiceImpl(this, new ApplicationContextProcessor(this.getAppId(), classLoader, jndiContext, this.seqID, this.toString())));
   }

   protected String getExtraToString() {
      return ",seqID=" + this.seqID;
   }
}
