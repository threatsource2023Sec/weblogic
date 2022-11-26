package weblogic.store;

public interface TestStoreException {
   String DEBUG_STORE_PROP = "weblogic.store.qa.StoreTest";

   PersistentStoreException getTestException();
}
