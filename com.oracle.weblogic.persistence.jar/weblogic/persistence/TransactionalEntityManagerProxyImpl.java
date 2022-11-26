package weblogic.persistence;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.SynchronizationType;
import javax.persistence.TransactionRequiredException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.j2ee.J2EELogger;
import weblogic.transaction.internal.InterpositionTier;
import weblogic.transaction.internal.ServerTransactionManagerImpl;

public final class TransactionalEntityManagerProxyImpl extends BasePersistenceContextProxyImpl {
   private final SynchronizationType synchronizationType;
   private final Set queryMethods = new HashSet();
   private final Method delegateMethod;
   private final Set cleanPostInvocationMethods = new HashSet();

   TransactionalEntityManagerProxyImpl(String appName, String moduleName, String unitName, PersistenceUnitRegistry reg, SynchronizationType syncType) {
      super(appName, moduleName, unitName, reg);
      this.synchronizationType = syncType;

      try {
         Class cls = EntityManager.class;
         this.setTransactionAccessMethod(cls.getMethod("getTransaction", (Class[])null));
         this.setCloseMethod(cls.getMethod("close", (Class[])null));
         this.delegateMethod = cls.getMethod("getDelegate", (Class[])null);
         Set txMethods = new HashSet();
         txMethods.add(cls.getMethod("refresh", Object.class));
         txMethods.add(cls.getMethod("remove", Object.class));
         txMethods.add(cls.getMethod("merge", Object.class));
         txMethods.add(cls.getMethod("persist", Object.class));
         txMethods.add(cls.getMethod("flush", (Class[])null));
         txMethods.add(cls.getMethod("lock", Object.class, LockModeType.class));
         txMethods.add(cls.getMethod("joinTransaction", (Class[])null));
         txMethods.add(cls.getMethod("refresh", Object.class, LockModeType.class));
         txMethods.add(cls.getMethod("refresh", Object.class, LockModeType.class, Map.class));
         txMethods.add(cls.getMethod("refresh", Object.class, Map.class));
         txMethods.add(cls.getMethod("lock", Object.class, LockModeType.class, Map.class));
         txMethods.add(cls.getMethod("getLockMode", Object.class));
         this.setTransactionalMethods(txMethods);
         Method[] var8 = cls.getMethods();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Method m = var8[var10];
            if (m.getName().startsWith("create") && m.getName().endsWith("Query")) {
               this.queryMethods.add(m);
            }
         }

         this.cleanPostInvocationMethods.add(cls.getMethod("find", Class.class, Object.class));
         this.cleanPostInvocationMethods.add(cls.getMethod("find", Class.class, Object.class, LockModeType.class));
         this.cleanPostInvocationMethods.add(cls.getMethod("find", Class.class, Object.class, LockModeType.class, Map.class));
         this.cleanPostInvocationMethods.add(cls.getMethod("find", Class.class, Object.class, Map.class));
      } catch (NoSuchMethodException var12) {
         throw new AssertionError("Could not get expected method: " + var12);
      }
   }

   public SynchronizationType getSynchronizationType() {
      return this.synchronizationType;
   }

   protected Object getPersistenceContext(Transaction tx) {
      String qualifiedPUName = this.getUnitName();
      EntityManager cicScopedEM;
      if (tx != null) {
         if (!CICSCOPED_EM_DISABLED) {
            cicScopedEM = CICScopedEMProvider.getInstance().getEMForCurrentCIC(qualifiedPUName);
            if (cicScopedEM != null) {
               CICScopedEMProvider.getInstance().invalidateEMForCurrentCIC(qualifiedPUName);
            }
         }

         Object o = this.txRegistry.getResource(qualifiedPUName);
         if (o != null) {
            PersistenceContextWrapper pcWrapper = (PersistenceContextWrapper)o;
            if (pcWrapper.getSynchronizationType() == SynchronizationType.UNSYNCHRONIZED && this.synchronizationType == SynchronizationType.SYNCHRONIZED) {
               throw new IllegalStateException("Detected an UNSYNCHRONIZED Persistence Context being propagated to SYNCHRONIZED Persistence Context.");
            }

            return pcWrapper.getEntityManager();
         }
      } else if (!CICSCOPED_EM_DISABLED) {
         cicScopedEM = CICScopedEMProvider.getInstance().getEMForCurrentCIC(qualifiedPUName);
         if (cicScopedEM != null) {
            return cicScopedEM;
         }
      }

      PersistenceContextWrapper pcWrapper = (PersistenceContextWrapper)this.newPersistenceContext(this.persistenceUnit);
      if (tx != null) {
         this.txRegistry.putResource(qualifiedPUName, pcWrapper);
         ((ServerTransactionManagerImpl)this.txRegistry).registerInterposedSynchronization(new BasePersistenceContextProxyImpl.PersistenceContextCloser(pcWrapper.getEntityManager()), InterpositionTier.WLS_INTERNAL_SYNCHRONIZATION);
      } else if (!CICSCOPED_EM_DISABLED) {
         CICScopedEMProvider.getInstance().setEMForCurrentCIC(qualifiedPUName, pcWrapper.getEntityManager());
      }

      return pcWrapper.getEntityManager();
   }

   protected String getPersistenceContextStyleName() {
      return "EntityManager";
   }

   protected Object newPersistenceContext(BasePersistenceUnitInfo unit) {
      return new PersistenceContextWrapper(unit, this.synchronizationType);
   }

   protected Object invoke(Object proxy, Method method, Object[] args, Transaction tx) throws Throwable {
      if (CICSCOPED_EM_DISABLED && tx == null && this.queryMethods.contains(method)) {
         this.throwExIfProcedureQuery(method);
         return new QueryProxyImpl(this, method, args);
      } else {
         return super.invoke(proxy, method, args, tx);
      }
   }

   protected void checkTransactionStatus(Object pc, Transaction tx) {
      EntityManager em = (EntityManager)pc;
      if (!em.isOpen()) {
         try {
            if (tx != null && tx.getStatus() == 4) {
               throw new IllegalStateException("The transaction associated with this transaction-scoped persistence context has been rolled back and as a result, the EntityManager has been closed. No further operations are allowed in this transaction context. Please see the server log for the cause of the rollback.");
            }
         } catch (SystemException var5) {
         }
      }

   }

   protected void perhapsClose(Object pc, Method m) {
      if (!this.delegateMethod.equals(m)) {
         EntityManager em = (EntityManager)pc;
         if (em.isOpen()) {
            em.close();
         }

      }
   }

   protected void perhapsClean(Object pc, Method m) {
      if (this.cleanPostInvocationMethods.contains(m)) {
         EntityManager em = (EntityManager)pc;
         if (em.isOpen()) {
            em.clear();
         }
      }

   }

   private void throwExIfProcedureQuery(Method method) {
      if (method.getName().contains("StoredProcedure")) {
         throw new TransactionRequiredException(J2EELogger.getTransRequiredForProcedureQueryMsg());
      }
   }
}
