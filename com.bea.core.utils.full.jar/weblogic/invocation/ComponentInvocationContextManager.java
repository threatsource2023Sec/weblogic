package weblogic.invocation;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import weblogic.diagnostics.debug.DebugLogger;

public abstract class ComponentInvocationContextManager {
   private static final boolean IS_WARN_ENABLED = Boolean.getBoolean("weblogic.debug.WarnUnsecureCICAccess");
   private static final boolean IS_CONDENSE_DISABLED = Boolean.getBoolean("weblogic.debug.EnableFullCICAccessStacks");
   private static Set alreadyWarnedSet = new HashSet();

   public static ComponentInvocationContextManager getInstance() {
      return (ComponentInvocationContextManager)(IS_WARN_ENABLED ? new ReadOnlyComponentInvocationContextManager(ComponentInvocationContextManager.SingletonHolder.INSTANCE) : ComponentInvocationContextManager.SingletonHolder.INSTANCE);
   }

   public static ComponentInvocationContextManager getInstance(Principal subject) {
      try {
         ComponentInvocationContextManager.SingletonHolder.INSTANCE.checkIfKernel(subject);
         return ComponentInvocationContextManager.SingletonHolder.INSTANCE;
      } catch (SecurityException var2) {
         var2.printStackTrace();
         throw var2;
      }
   }

   public abstract ComponentInvocationContext createComponentInvocationContext(String var1, String var2, String var3);

   public abstract ComponentInvocationContext createComponentInvocationContext(String var1, String var2, String var3, String var4, String var5);

   public ComponentInvocationContext createComponentInvocationContext(String partitionName) {
      return this.createComponentInvocationContext(partitionName, (String)null, (String)null, (String)null, (String)null);
   }

   public abstract ComponentInvocationContext getCurrentComponentInvocationContext();

   /** @deprecated */
   @Deprecated
   public abstract void pushComponentInvocationContext(ComponentInvocationContext var1);

   /** @deprecated */
   @Deprecated
   public abstract void popComponentInvocationContext();

   public abstract ManagedInvocationContext setCurrentComponentInvocationContext(ComponentInvocationContext var1);

   /** @deprecated */
   @Deprecated
   public static Object runAs(ComponentInvocationContext ci, Callable action) throws ExecutionException {
      warnUnsecureCallers();
      return _runAs(ci, action);
   }

   public static Object runAs(Principal subject, ComponentInvocationContext ci, Callable action) throws ExecutionException {
      ComponentInvocationContextManager.SingletonHolder.INSTANCE.checkIfKernel(subject);
      return _runAs(ci, action);
   }

   private static Object _runAs(ComponentInvocationContext ci, Callable action) throws ExecutionException {
      ManagedInvocationContext ignored = ComponentInvocationContextManager.SingletonHolder.INSTANCE.setCurrentComponentInvocationContext(ci);
      Throwable var3 = null;

      Object var4;
      try {
         try {
            var4 = action.call();
         } catch (Exception var14) {
            throw new ExecutionException(var14);
         }
      } catch (Throwable var15) {
         var3 = var15;
         throw var15;
      } finally {
         if (ignored != null) {
            if (var3 != null) {
               try {
                  ignored.close();
               } catch (Throwable var13) {
                  var3.addSuppressed(var13);
               }
            } else {
               ignored.close();
            }
         }

      }

      return var4;
   }

   /** @deprecated */
   @Deprecated
   public static void runAs(ComponentInvocationContext ci, Runnable action) throws ExecutionException {
      warnUnsecureCallers();
      _runAs(ci, action);
   }

   public static void runAs(Principal subject, ComponentInvocationContext ci, Runnable action) throws ExecutionException {
      ComponentInvocationContextManager.SingletonHolder.INSTANCE.checkIfKernel(subject);
      _runAs(ci, action);
   }

   private static void _runAs(ComponentInvocationContext ci, Runnable action) throws ExecutionException {
      ManagedInvocationContext ignored = ComponentInvocationContextManager.SingletonHolder.INSTANCE.setCurrentComponentInvocationContext(ci);
      Throwable var3 = null;

      try {
         try {
            action.run();
         } catch (RuntimeException var13) {
            throw new ExecutionException(var13);
         }
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (ignored != null) {
            if (var3 != null) {
               try {
                  ignored.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               ignored.close();
            }
         }

      }

   }

   public abstract void addInvocationContextChangeListener(ComponentInvocationContextChangeListener var1);

   public abstract void removeInvocationContextChangeListener(ComponentInvocationContextChangeListener var1);

   protected abstract void checkIfKernel(Principal var1);

   protected static void warnUnsecureCallers() {
      if (IS_WARN_ENABLED) {
         StackTraceElement[] sEltsArr = Thread.currentThread().getStackTrace();
         List stackElts = Arrays.asList(sEltsArr);
         if (!alreadyWarnedSet.contains(stackElts)) {
            alreadyWarnedSet.add(stackElts);
            DebugLogger.println("Unsecure CIC caller: " + formatTrace(sEltsArr));
         }
      }

   }

   protected static String formatTrace(StackTraceElement[] stackTraceElements) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i <= stackTraceElements.length - 1; ++i) {
         StackTraceElement elt = stackTraceElements[i];
         if (i != 0) {
            if (!IS_CONDENSE_DISABLED && i > 10) {
               break;
            }

            sb.append(elt).append(System.lineSeparator());
         }
      }

      return sb.toString();
   }

   private static final class SingletonHolder {
      private static final ComponentInvocationContextManager INSTANCE = init();

      private static ComponentInvocationContextManager init() {
         Iterator var0 = ServiceLoader.load(ComponentInvocationContextManager.class).iterator();
         if (var0.hasNext()) {
            ComponentInvocationContextManager cim = (ComponentInvocationContextManager)var0.next();
            return cim;
         } else {
            throw new RuntimeException("META-INF/services/" + ComponentInvocationContextManager.class.getName() + " is not found in the search path of Thread Context ClassLoader");
         }
      }
   }
}
