package weblogic.application;

import java.security.AccessController;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextChangeListener;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.t3.srvr.ServerRuntime;
import weblogic.t3.srvr.T3Srvr;

public final class ComponentInvocationContextManagerImpl extends ComponentInvocationContextManager {
   private static final boolean IS_DEBUG_ENABLED = Boolean.getBoolean("weblogic.debug.DebugCIC");
   private static final String DATE_FORMAT = "MMM dd, YYYY, HH:mm:ss z";
   private static Set alreadyLoggedSet = Collections.newSetFromMap(new ConcurrentHashMap());
   private static final boolean DETECT_PUSH_POP_USAGES = Boolean.getBoolean("weblogic.debug.DetectPushPopUsages");
   private final AuditableThreadLocal LOCAL_CONTEXT_STACK = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue(true) {
      protected Object initialValue() {
         return new ArrayDeque();
      }

      protected Object childValue(Object parentValue) {
         return ((ArrayDeque)parentValue).clone();
      }
   });
   private final AuditableThreadLocal LAST_PUSHER = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue() {
      protected Object initialValue() {
         return new StackTraceElement[0];
      }
   });
   private final AuditableThreadLocal LAST_POPPER = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue() {
      protected Object initialValue() {
         return new StackTraceElement[0];
      }
   });
   private final List contextListeners = new CopyOnWriteArrayList();
   private static final ComponentInvocationContext NULL_CIC = new NullComponentInvocationContext();

   private static void detectPushPopUsages(String method) {
      if (DETECT_PUSH_POP_USAGES) {
         StackTraceElement[] sEltsArr = Thread.currentThread().getStackTrace();
         List stackElts = Arrays.asList(sEltsArr);
         if (!alreadyLoggedSet.contains(stackElts)) {
            alreadyLoggedSet.add(stackElts);
            debug("----DETECTING----\n" + formatTrace(sEltsArr) + "----END----");
         }
      }

   }

   public ComponentInvocationContext getCurrentComponentInvocationContext() {
      ComponentInvocationContext cic = null;
      Deque ctxts = (Deque)this.LOCAL_CONTEXT_STACK.get();
      if (ctxts.isEmpty()) {
         if (IS_DEBUG_ENABLED) {
            debug("CIC is not yet set, so returning null CIC at \n" + formatTrace(Thread.currentThread().getStackTrace()));
         }

         cic = NULL_CIC;
      } else {
         cic = (ComponentInvocationContext)ctxts.peek();
         if (IS_DEBUG_ENABLED) {
            debug("Using local context for partition context, Size of stack:" + ctxts.size() + ", TID:" + Thread.currentThread().getId() + ", returning CIC:" + cic);
         }
      }

      return cic;
   }

   public void pushComponentInvocationContext(ComponentInvocationContext cic) {
      if (cic == null) {
         throw new IllegalArgumentException("Cannot push null ComponentInvocationContext");
      } else {
         detectPushPopUsages("PUSH");
         this.pushComponentInvocationContext((Deque)this.LOCAL_CONTEXT_STACK.get(), cic);
      }
   }

   private void pushComponentInvocationContext(Deque ctxts, ComponentInvocationContext cic) {
      ComponentInvocationContext prevTopOfStack = ctxts.isEmpty() ? null : (ComponentInvocationContext)ctxts.peek();
      ctxts.push(cic);
      if (IS_DEBUG_ENABLED) {
         this.LAST_PUSHER.set(Thread.currentThread().getStackTrace());
         debug("Pushed [" + cic + "] on top of [" + prevTopOfStack + "]. New size is [" + ctxts.size() + "]. Pushed by [" + formatTrace((StackTraceElement[])((StackTraceElement[])this.LAST_PUSHER.get())) + "]");
      }

      this.notifyListeners(prevTopOfStack, cic, true);
   }

   public void popComponentInvocationContext() {
      Deque ctxts = (Deque)this.LOCAL_CONTEXT_STACK.get();
      if (ctxts.size() == 0) {
         throw new IllegalStateException("Cannot allow popComponentInvocationContext as there is no context to pop on stack");
      } else {
         detectPushPopUsages("POP");
         this.popComponentInvocationContext(ctxts);
      }
   }

   private void popComponentInvocationContext(Deque ctxts) {
      ComponentInvocationContext cic = (ComponentInvocationContext)ctxts.pop();
      ComponentInvocationContext currentTopOfStack = (ComponentInvocationContext)ctxts.peek();
      if (IS_DEBUG_ENABLED) {
         this.LAST_POPPER.set(Thread.currentThread().getStackTrace());
         debug("Popped [" + cic + "] making [" + currentTopOfStack + "] new top. New stack size is [" + ctxts.size() + "]. Popped by [" + formatTrace((StackTraceElement[])((StackTraceElement[])this.LAST_POPPER.get())) + "]");
      }

      this.notifyListeners(cic, currentTopOfStack, false);
   }

   public ManagedInvocationContext setCurrentComponentInvocationContext(ComponentInvocationContext cic) {
      if (cic == null) {
         throw new IllegalArgumentException("Cannot push null ComponentInvocationContext ");
      } else {
         if (IS_DEBUG_ENABLED) {
            debug("setCurrentComponentInvocationContext: pushing " + cic);
         }

         final Deque ctxs = (Deque)this.LOCAL_CONTEXT_STACK.get();
         final ComponentInvocationContext pushedCic = new ComponentInvocationContextImpl(cic);
         this.pushComponentInvocationContext(ctxs, pushedCic);
         return new ManagedInvocationContext() {
            public void close() {
               this.validate();
               ComponentInvocationContextManagerImpl.this.popComponentInvocationContext(ctxs);
            }

            private void validate() {
               Deque currentCtxs = (Deque)ComponentInvocationContextManagerImpl.this.LOCAL_CONTEXT_STACK.get();
               if (ctxs != currentCtxs) {
                  throw new IllegalStateException("close() is called from a thread that's different from the one used during setCurrentComponentInvocationContext");
               } else {
                  ComponentInvocationContext currentCic = (ComponentInvocationContext)currentCtxs.peek();
                  if (pushedCic != currentCic) {
                     throw new IllegalStateException("Context pushed was [" + pushedCic + "], but context being popped is [" + currentCic + "]");
                  }
               }
            }

            public String toString() {
               return "ManagedInvocationContext [ctxs = " + ctxs + ", token = " + pushedCic + "]";
            }
         };
      }
   }

   public void addInvocationContextChangeListener(ComponentInvocationContextChangeListener ic) {
      this.contextListeners.add(ic);
   }

   public void removeInvocationContextChangeListener(ComponentInvocationContextChangeListener ic) {
      this.contextListeners.remove(ic);
   }

   private void notifyListeners(ComponentInvocationContext oldCIC, ComponentInvocationContext newCIC, boolean isPush) {
      Iterator var4 = this.contextListeners.iterator();

      while(var4.hasNext()) {
         ComponentInvocationContextChangeListener cicl = (ComponentInvocationContextChangeListener)var4.next();

         try {
            cicl.componentInvocationContextChanged(oldCIC, newCIC, isPush);
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }

   }

   public ComponentInvocationContext createComponentInvocationContext(String appId, String moduleName, String componentName) {
      return this.createComponentInvocationContext(ApplicationVersionUtils.getPartitionName(appId), ApplicationVersionUtils.getApplicationName(appId), ApplicationVersionUtils.getVersionId(appId), moduleName, componentName);
   }

   public ComponentInvocationContext createComponentInvocationContext(String partitionName, String applicationName, String applicationVersion, String moduleName, String componentName) {
      return (ComponentInvocationContext)(applicationName != null || applicationVersion != null || moduleName != null || componentName != null || partitionName != null && !"DOMAIN".equals(partitionName) ? new ComponentInvocationContextImpl(partitionName, applicationName, applicationVersion, moduleName, componentName) : NULL_CIC);
   }

   private static void debug(String debugMsg) {
      DateFormat formatter = new SimpleDateFormat("MMM dd, YYYY, HH:mm:ss z");
      String timeStamp = formatter.format(Calendar.getInstance().getTime());
      DebugLogger.println("<" + timeStamp + "> INVCTXT (" + Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "): " + debugMsg);
   }

   private static AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   protected void checkIfKernel(Principal sub) {
      try {
         if (T3Srvr.getT3Srvr().getRunState() < 1) {
            return;
         }
      } catch (Throwable var5) {
         return;
      }

      if (sub == null) {
         String message = "Subject '<null>' is not the kernel identity";
         throw new SecurityException(message);
      } else {
         AuthenticatedSubject asub = null;

         try {
            asub = (AuthenticatedSubject)sub;
         } catch (ClassCastException var4) {
            throw new SecurityException(var4);
         }

         if (!SecurityServiceManager.isKernelIdentity(asub)) {
            String message = "Subject '" + sub.toString() + "' is not the kernel identity";
            throw new SecurityException(message);
         }
      }
   }

   private static class EditConfigTree {
      private static final AuthenticatedSubject KERNEL_ID = ComponentInvocationContextManagerImpl.getKernelId();

      static PartitionMBean lookupPartition(String pname) {
         if (ComponentInvocationContextManagerImpl.IS_DEBUG_ENABLED) {
            ComponentInvocationContextManagerImpl.debug("Looking for partition " + pname + " in edit config tree.");
         }

         try {
            EditAccess editAccess = ManagementServiceRestricted.getEditAccess(KERNEL_ID);
            PartitionMBean result = lookupPartition(pname, editAccess);
            if (result == null) {
               Map map = ManagementServiceRestricted.getNamedEditAccess(KERNEL_ID);
               Iterator var4 = map.values().iterator();

               while(var4.hasNext()) {
                  Map map2 = (Map)var4.next();
                  Iterator var6 = map2.values().iterator();

                  while(var6.hasNext()) {
                     EditAccess ea = (EditAccess)var6.next();
                     result = lookupPartition(pname, ea);
                     if (result != null) {
                        return result;
                     }
                  }
               }
            }

            return result;
         } catch (EditFailedException var8) {
            throw new RuntimeException(var8);
         }
      }

      private static PartitionMBean lookupPartition(String pname, EditAccess editAccess) throws EditFailedException {
         DomainMBean domainBean = editAccess.getDomainBeanWithoutLock();
         return domainBean.lookupPartition(pname);
      }
   }

   private static class RuntimeConfigTree {
      private static final AuthenticatedSubject KERNEL_ID = ComponentInvocationContextManagerImpl.getKernelId();

      static PartitionMBean lookupPartition(String pname) {
         if (ComponentInvocationContextManagerImpl.IS_DEBUG_ENABLED) {
            ComponentInvocationContextManagerImpl.debug("Looking for partition " + pname + " in runtime config tree.");
         }

         DomainMBean domainBean = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
         return domainBean.lookupPartition(pname);
      }
   }

   private static class DomainConfigTree {
      private static final AuthenticatedSubject KERNEL_ID = ComponentInvocationContextManagerImpl.getKernelId();

      static PartitionMBean lookupPartition(String pname) {
         if (ComponentInvocationContextManagerImpl.IS_DEBUG_ENABLED) {
            ComponentInvocationContextManagerImpl.debug("Looking for partition " + pname + " in domain config tree.");
         }

         DomainMBean domainBean = ManagementService.getDomainAccess(KERNEL_ID).getDomainRuntimeService().getDomainConfiguration();
         return domainBean.lookupPartition(pname);
      }
   }

   static final class PartitionIdNameMapper {
      static String mapPartitionNameToId(String pname) {
         if (pname == null) {
            throw new NullPointerException();
         } else if ("DOMAIN".equals(pname)) {
            return "0";
         } else {
            PartitionMBean p = ComponentInvocationContextManagerImpl.RuntimeConfigTree.lookupPartition(pname);
            if (p == null) {
               ServerRuntime serverRuntime = ServerRuntime.theOne();
               if (serverRuntime.isAdminServer()) {
                  p = ComponentInvocationContextManagerImpl.DomainConfigTree.lookupPartition(pname);
                  if (p == null) {
                     p = ComponentInvocationContextManagerImpl.EditConfigTree.lookupPartition(pname);
                  }
               }
            }

            if (p != null) {
               return p.getPartitionID();
            } else {
               throw new IllegalArgumentException("No partition called " + pname);
            }
         }
      }
   }

   static final class NullComponentInvocationContext implements ComponentInvocationContext {
      private static final String DESC = "(pId = 0, pName = DOMAIN, appId = " + null + ", appName = " + null + ", appVersion = " + null + ", mId = " + null + ", compName = " + null + ")";

      public String getPartitionId() {
         return "0";
      }

      public String getPartitionName() {
         return "DOMAIN";
      }

      public String getApplicationId() {
         return null;
      }

      public String getApplicationName() {
         return null;
      }

      public String getApplicationVersion() {
         return null;
      }

      public String getModuleName() {
         return null;
      }

      public String getComponentName() {
         return null;
      }

      public boolean isGlobalRuntime() {
         return true;
      }

      public String toString() {
         return DESC;
      }
   }
}
