package javax.jdo.spi;

import javax.jdo.PersistenceManager;

public interface PersistenceCapable {
   byte READ_WRITE_OK = 0;
   byte LOAD_REQUIRED = 1;
   byte READ_OK = -1;
   byte CHECK_READ = 1;
   byte MEDIATE_READ = 2;
   byte CHECK_WRITE = 4;
   byte MEDIATE_WRITE = 8;
   byte SERIALIZABLE = 16;

   PersistenceManager jdoGetPersistenceManager();

   void jdoReplaceStateManager(StateManager var1) throws SecurityException;

   void jdoProvideField(int var1);

   void jdoProvideFields(int[] var1);

   void jdoReplaceField(int var1);

   void jdoReplaceFields(int[] var1);

   void jdoReplaceFlags();

   void jdoCopyFields(Object var1, int[] var2);

   void jdoMakeDirty(String var1);

   Object jdoGetObjectId();

   Object jdoGetTransactionalObjectId();

   Object jdoGetVersion();

   boolean jdoIsDirty();

   boolean jdoIsTransactional();

   boolean jdoIsPersistent();

   boolean jdoIsNew();

   boolean jdoIsDeleted();

   boolean jdoIsDetached();

   PersistenceCapable jdoNewInstance(StateManager var1);

   PersistenceCapable jdoNewInstance(StateManager var1, Object var2);

   Object jdoNewObjectIdInstance();

   Object jdoNewObjectIdInstance(Object var1);

   void jdoCopyKeyFieldsToObjectId(Object var1);

   void jdoCopyKeyFieldsToObjectId(ObjectIdFieldSupplier var1, Object var2);

   void jdoCopyKeyFieldsFromObjectId(ObjectIdFieldConsumer var1, Object var2);

   public interface ObjectIdFieldConsumer {
      void storeBooleanField(int var1, boolean var2);

      void storeCharField(int var1, char var2);

      void storeByteField(int var1, byte var2);

      void storeShortField(int var1, short var2);

      void storeIntField(int var1, int var2);

      void storeLongField(int var1, long var2);

      void storeFloatField(int var1, float var2);

      void storeDoubleField(int var1, double var2);

      void storeStringField(int var1, String var2);

      void storeObjectField(int var1, Object var2);
   }

   public interface ObjectIdFieldSupplier {
      boolean fetchBooleanField(int var1);

      char fetchCharField(int var1);

      byte fetchByteField(int var1);

      short fetchShortField(int var1);

      int fetchIntField(int var1);

      long fetchLongField(int var1);

      float fetchFloatField(int var1);

      double fetchDoubleField(int var1);

      String fetchStringField(int var1);

      Object fetchObjectField(int var1);
   }

   public interface ObjectIdFieldManager extends ObjectIdFieldConsumer, ObjectIdFieldSupplier {
   }
}
