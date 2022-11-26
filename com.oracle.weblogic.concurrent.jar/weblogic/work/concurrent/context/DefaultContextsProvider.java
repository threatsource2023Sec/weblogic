package weblogic.work.concurrent.context;

import java.util.Map;
import javax.transaction.Transaction;
import weblogic.kernel.AuditableThread;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.transaction.TransactionProcessor;

public abstract class DefaultContextsProvider implements ContextProvider {
   private static final long serialVersionUID = 1630351329864758942L;
   private static final ContextServiceDefaultContextsProvider contextServiceInstance = new ContextServiceDefaultContextsProvider();
   private static final NonContextServiceDefaultContextsProvider nonContextServiceInstance = new NonContextServiceDefaultContextsProvider();

   private DefaultContextsProvider() {
   }

   public static ContextServiceDefaultContextsProvider getContextServiceInstance() {
      return contextServiceInstance;
   }

   public static NonContextServiceDefaultContextsProvider getNonContextServiceInstance() {
      return nonContextServiceInstance;
   }

   public ContextHandle save(Map executionProperties) {
      return new ThreadStoragesContextHandle((Object)null, (Transaction)null, this.isUseTransOfExecutionThread(executionProperties), this.getIdentityNameProperty(executionProperties));
   }

   abstract boolean isUseTransOfExecutionThread(Map var1);

   public ContextHandle setup(ContextHandle contextHandle) {
      if (!(contextHandle instanceof ThreadStoragesContextHandle)) {
         throw new IllegalArgumentException("An instance of ThreadStoragesContextHandle is expected in default context setup, but it is " + contextHandle);
      } else {
         ThreadStoragesContextHandle handle = (ThreadStoragesContextHandle)contextHandle;
         boolean useTransOfExecutionThread = handle.isUseTransOfExecutionThread();
         Transaction trans = null;
         Object threadStorages = null;
         Thread thread = Thread.currentThread();
         if (thread instanceof AuditableThread) {
            trans = TransactionProcessor.suspendTransaction();
            threadStorages = ((AuditableThread)thread).suspendThreadStorages();
            if (useTransOfExecutionThread) {
               TransactionProcessor.resumeTransaction(trans);
               trans = null;
            }
         } else if (!useTransOfExecutionThread) {
            trans = TransactionProcessor.suspendTransaction();
         }

         return new ThreadStoragesContextHandle(threadStorages, trans, useTransOfExecutionThread, handle.getIdentityNameProp());
      }
   }

   public void reset(ContextHandle contextHandle) {
      if (!(contextHandle instanceof ThreadStoragesContextHandle)) {
         throw new IllegalArgumentException("An instance of ThreadStoragesContextHandle is expected in default context reset, but it is " + contextHandle);
      } else {
         ThreadStoragesContextHandle handle = (ThreadStoragesContextHandle)contextHandle;
         Object threadStorages = handle.getThreadStorages();
         Transaction trans = handle.getTransaction();
         boolean useTransOfExecutionThread = handle.isUseTransOfExecutionThread();
         String indentityNameProp = handle.getIdentityNameProp();
         Thread thread = Thread.currentThread();
         if (thread instanceof AuditableThread) {
            if (useTransOfExecutionThread) {
               trans = TransactionProcessor.suspendTransaction();
            } else {
               TransactionProcessor.rollbackIfExist(indentityNameProp);
            }

            ((AuditableThread)thread).restoreThreadStorages(threadStorages);
            TransactionProcessor.resumeTransaction(trans);
         } else if (!useTransOfExecutionThread) {
            TransactionProcessor.rollbackIfExist(indentityNameProp);
            TransactionProcessor.resumeTransaction(trans);
         }

      }
   }

   public String getContextType() {
      return "internal";
   }

   String getIdentityNameProperty(Map executionProperties) {
      return executionProperties != null ? (String)executionProperties.get("javax.enterprise.concurrent.IDENTITY_NAME") : null;
   }

   // $FF: synthetic method
   DefaultContextsProvider(Object x0) {
      this();
   }

   public static class NonContextServiceDefaultContextsProvider extends DefaultContextsProvider {
      private static final long serialVersionUID = -2566573551259391675L;

      private NonContextServiceDefaultContextsProvider() {
         super(null);
      }

      boolean isUseTransOfExecutionThread(Map executionProperties) {
         return false;
      }

      public int getConcurrentObjectType() {
         return 14;
      }

      // $FF: synthetic method
      NonContextServiceDefaultContextsProvider(Object x0) {
         this();
      }
   }

   public static class ContextServiceDefaultContextsProvider extends DefaultContextsProvider {
      private static final long serialVersionUID = 6710865212132755995L;

      private ContextServiceDefaultContextsProvider() {
         super(null);
      }

      boolean isUseTransOfExecutionThread(Map executionProperties) {
         return executionProperties != null && "USE_TRANSACTION_OF_EXECUTION_THREAD".equals(executionProperties.get("javax.enterprise.concurrent.TRANSACTION"));
      }

      public int getConcurrentObjectType() {
         return 1;
      }

      // $FF: synthetic method
      ContextServiceDefaultContextsProvider(Object x0) {
         this();
      }
   }

   private static class ThreadStoragesContextHandle implements ContextHandle {
      private static final long serialVersionUID = -613057579013822643L;
      private final transient Object threadStorages;
      private final transient Transaction trans;
      private final boolean useTransOfExecutionThread;
      private final String identityNameProp;

      private ThreadStoragesContextHandle(Object threadStorages, Transaction trans, boolean useTransOfExecutionThread, String identityNameProp) {
         this.threadStorages = threadStorages;
         this.trans = trans;
         this.useTransOfExecutionThread = useTransOfExecutionThread;
         this.identityNameProp = identityNameProp;
      }

      private Object getThreadStorages() {
         return this.threadStorages;
      }

      private Transaction getTransaction() {
         return this.trans;
      }

      private boolean isUseTransOfExecutionThread() {
         return this.useTransOfExecutionThread;
      }

      private String getIdentityNameProp() {
         return this.identityNameProp;
      }

      // $FF: synthetic method
      ThreadStoragesContextHandle(Object x0, Transaction x1, boolean x2, String x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
