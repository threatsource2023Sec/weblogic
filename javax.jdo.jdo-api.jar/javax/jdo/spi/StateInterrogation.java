package javax.jdo.spi;

import javax.jdo.PersistenceManager;

public interface StateInterrogation {
   Boolean isPersistent(Object var1);

   Boolean isTransactional(Object var1);

   Boolean isDirty(Object var1);

   Boolean isNew(Object var1);

   Boolean isDeleted(Object var1);

   Boolean isDetached(Object var1);

   PersistenceManager getPersistenceManager(Object var1);

   Object getObjectId(Object var1);

   Object getTransactionalObjectId(Object var1);

   Object getVersion(Object var1);

   boolean makeDirty(Object var1, String var2);
}
