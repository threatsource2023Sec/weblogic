package weblogic.persistence;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.SynchronizationType;
import javax.transaction.Transaction;
import weblogic.application.naming.PersistenceUnitRegistry;

public final class ExtendedEntityManagerProxyImpl extends BasePersistenceContextProxyImpl {
   ExtendedEntityManagerProxyImpl(String appName, String moduleName, String unitName, PersistenceUnitRegistry reg) {
      super(appName, moduleName, unitName, reg);

      try {
         Class cls = EntityManager.class;
         this.setTransactionAccessMethod(cls.getMethod("getTransaction", (Class[])null));
         this.setCloseMethod(cls.getMethod("close", (Class[])null));
         Set meths = new HashSet();
         meths.add(cls.getMethod("flush", (Class[])null));
         meths.add(cls.getMethod("lock", Object.class, LockModeType.class));
         meths.add(cls.getMethod("joinTransaction", (Class[])null));
         meths.add(cls.getMethod("lock", Object.class, LockModeType.class, Map.class));
         meths.add(cls.getMethod("getLockMode", Object.class));
         this.setTransactionalMethods(meths);
      } catch (NoSuchMethodException var7) {
         throw new AssertionError("Could not get expected method: " + var7);
      }
   }

   protected String getPersistenceContextStyleName() {
      return "EntityManager";
   }

   protected Object getPersistenceContext(Transaction tx) {
      String unitName = this.getUnitName();
      if (tx != null) {
         Object o = this.txRegistry.getResource(unitName);
         if (o != null) {
            return ((ExtendedPersistenceContextWrapper)o).getEntityManager();
         }
      }

      ExtendedPersistenceContextWrapper epcWrapper = ExtendedPersistenceContextManager.getExtendedPersistenceContext(unitName);
      if (epcWrapper == null) {
         throw new IllegalStateException("Extended Persistence Contexts can only be invoked from within the context of the stateful session bean that declares the Extended Persistence Context.");
      } else {
         EntityManager em = epcWrapper.getEntityManager();
         if (tx != null) {
            this.txRegistry.putResource(unitName, epcWrapper);
            if (epcWrapper.getSynchronizationType() == SynchronizationType.SYNCHRONIZED) {
               em.joinTransaction();
            }
         }

         return em;
      }
   }

   protected Object newPersistenceContext(BasePersistenceUnitInfo unit) {
      throw new IllegalStateException("Extended Persistence Contexts should only be created when a Stateful Session Bean is created.");
   }

   protected void perhapsClose(Object pc, Method m) {
   }

   protected void perhapsClean(Object pc, Method m) {
   }
}
