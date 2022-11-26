package weblogic.persistence;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.SynchronizationType;

public final class ExtendedPersistenceContextWrapper extends PersistenceContextWrapper implements Serializable {
   private static final long serialVersionUID = -3094080938369800382L;
   private int referenceCount = 1;
   private boolean isPersistenceContextSerializable;

   public ExtendedPersistenceContextWrapper(BasePersistenceUnitInfo puInfo, SynchronizationType synchronizationType) {
      super(puInfo, synchronizationType);
      this.isPersistenceContextSerializable = puInfo.isPersistenceContextSerializable(this.entityManager.getClass());
   }

   public synchronized void incrementReferenceCount() {
      ++this.referenceCount;
   }

   public synchronized void decrementReferenceCount() {
      --this.referenceCount;
      if (this.referenceCount < 1 && this.entityManager != null) {
         this.entityManager.close();
         this.entityManager = null;
      }

   }

   public EntityManager getEntityManager() {
      if (this.referenceCount < 1) {
         throw new IllegalStateException("Internal Error: Extended Persistence Context has been closed!!!!");
      } else {
         return this.entityManager;
      }
   }

   private void writeObject(ObjectOutputStream outStream) throws IOException {
      if (!this.isPersistenceContextSerializable) {
         throw new NotSerializableException("Either the entity manager or one of its managed entity is not serializable.");
      } else {
         outStream.defaultWriteObject();
      }
   }

   private void readObject(ObjectInputStream inStream) throws IOException, ClassNotFoundException {
      inStream.defaultReadObject();
   }
}
