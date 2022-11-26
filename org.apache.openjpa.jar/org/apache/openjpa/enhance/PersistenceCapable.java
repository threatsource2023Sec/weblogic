package org.apache.openjpa.enhance;

public interface PersistenceCapable {
   byte READ_WRITE_OK = 0;
   byte LOAD_REQUIRED = 1;
   byte READ_OK = -1;
   byte CHECK_READ = 1;
   byte MEDIATE_READ = 2;
   byte CHECK_WRITE = 4;
   byte MEDIATE_WRITE = 8;
   byte SERIALIZABLE = 16;
   Object DESERIALIZED = new Object();

   int pcGetEnhancementContractVersion();

   Object pcGetGenericContext();

   StateManager pcGetStateManager();

   void pcReplaceStateManager(StateManager var1);

   void pcProvideField(int var1);

   void pcProvideFields(int[] var1);

   void pcReplaceField(int var1);

   void pcReplaceFields(int[] var1);

   void pcCopyFields(Object var1, int[] var2);

   void pcDirty(String var1);

   Object pcFetchObjectId();

   Object pcGetVersion();

   boolean pcIsDirty();

   boolean pcIsTransactional();

   boolean pcIsPersistent();

   boolean pcIsNew();

   boolean pcIsDeleted();

   Boolean pcIsDetached();

   PersistenceCapable pcNewInstance(StateManager var1, boolean var2);

   PersistenceCapable pcNewInstance(StateManager var1, Object var2, boolean var3);

   Object pcNewObjectIdInstance();

   Object pcNewObjectIdInstance(Object var1);

   void pcCopyKeyFieldsToObjectId(Object var1);

   void pcCopyKeyFieldsToObjectId(FieldSupplier var1, Object var2);

   void pcCopyKeyFieldsFromObjectId(FieldConsumer var1, Object var2);

   Object pcGetDetachedState();

   void pcSetDetachedState(Object var1);
}
