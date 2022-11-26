package weblogic.kodo;

import java.lang.reflect.Method;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import kodo.jdo.KodoJDOHelper;
import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactory;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.persistence.BasePersistenceContextProxyImpl;
import weblogic.persistence.BasePersistenceUnitInfo;

public final class TransactionalPersistenceManagerProxyImpl extends BasePersistenceContextProxyImpl {
   public TransactionalPersistenceManagerProxyImpl(String appName, String moduleName, String unitName, PersistenceUnitRegistry reg) {
      super(appName, moduleName, unitName, reg);

      try {
         Class cls = PersistenceManager.class;
         this.setTransactionAccessMethod(cls.getMethod("currentTransaction", (Class[])null));
         this.setCloseMethod(cls.getMethod("close", (Class[])null));
      } catch (NoSuchMethodException var6) {
         throw new AssertionError("Could not get expected method: " + var6);
      }
   }

   protected String getPersistenceContextStyleName() {
      return "PersistenceManager";
   }

   protected Object newPersistenceContext(BasePersistenceUnitInfo unit) {
      EntityManager em = unit.getUnwrappedEntityManagerFactory().createEntityManager();
      return toPersistenceManager(em);
   }

   protected void perhapsClose(Object pc, Method m) {
      ((PersistenceManager)pc).close();
   }

   protected void perhapsClean(Object pc, Method m) {
      this.perhapsClose(pc, m);
   }

   protected static PersistenceManager toPersistenceManager(EntityManager em) {
      if (!(em instanceof OpenJPAEntityManager)) {
         try {
            Class jpaFacadeHelper = Class.forName(JPAFacadeHelper.class.getName(), true, em.getClass().getClassLoader());
            Method toBroker = jpaFacadeHelper.getMethod("toBroker", EntityManager.class);
            Class kodoJDOHelper = Class.forName(KodoJDOHelper.class.getName(), true, em.getClass().getClassLoader());
            Class brokerClass = toBroker.getReturnType();
            Method toPersistenceManager = kodoJDOHelper.getMethod("toPersistenceManager", brokerClass);
            Object broker = toBroker.invoke((Object)null, em);
            return (PersistenceManager)toPersistenceManager.invoke((Object)null, broker);
         } catch (Exception var7) {
            if (var7 instanceof RuntimeException) {
               throw (RuntimeException)var7;
            } else {
               throw new RuntimeException("Error converting EntityManager to Broker", var7);
            }
         }
      } else {
         return KodoJDOHelper.toPersistenceManager(JPAFacadeHelper.toBroker(em));
      }
   }

   protected static PersistenceManagerFactory toPersistenceManagerFactory(EntityManagerFactory emf) {
      if (!(emf instanceof OpenJPAEntityManagerFactory)) {
         try {
            Class jpaFacadeHelper = Class.forName(JPAFacadeHelper.class.getName(), true, emf.getClass().getClassLoader());
            Method toBrokerFactory = jpaFacadeHelper.getMethod("toBrokerFactory", EntityManagerFactory.class);
            Class kodoJDOHelper = Class.forName(KodoJDOHelper.class.getName(), true, emf.getClass().getClassLoader());
            Class brokerFactoryClass = toBrokerFactory.getReturnType();
            Method toPersistenceManagerFactory = kodoJDOHelper.getMethod("toPersistenceManagerFactory", brokerFactoryClass);
            Object brokerFactory = toBrokerFactory.invoke((Object)null, emf);
            return (PersistenceManagerFactory)toPersistenceManagerFactory.invoke((Object)null, brokerFactory);
         } catch (Exception var7) {
            if (var7 instanceof RuntimeException) {
               throw (RuntimeException)var7;
            } else {
               throw new RuntimeException("Error converting EntityManagerFactory to BrokerFactory", var7);
            }
         }
      } else {
         return KodoJDOHelper.toPersistenceManagerFactory(JPAFacadeHelper.toBrokerFactory(emf));
      }
   }
}
