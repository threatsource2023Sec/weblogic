package weblogic.persistence;

import javax.persistence.EntityManager;
import javax.persistence.SynchronizationType;

public class PersistenceContextWrapper {
   private String persistenceUnitName;
   private SynchronizationType synchronizationType;
   protected EntityManager entityManager;

   public PersistenceContextWrapper() {
   }

   public PersistenceContextWrapper(BasePersistenceUnitInfo puInfo, SynchronizationType synchronizationType) {
      this.persistenceUnitName = puInfo.getPersistenceUnitId();
      if (synchronizationType == SynchronizationType.UNSYNCHRONIZED) {
         this.entityManager = puInfo.getUnwrappedEntityManagerFactory().createEntityManager(synchronizationType);
      } else {
         this.entityManager = puInfo.getUnwrappedEntityManagerFactory().createEntityManager();
      }

      this.synchronizationType = synchronizationType;
   }

   public String getPersistenceUnitName() {
      return this.persistenceUnitName;
   }

   public SynchronizationType getSynchronizationType() {
      return this.synchronizationType;
   }

   public EntityManager getEntityManager() {
      return this.entityManager;
   }
}
