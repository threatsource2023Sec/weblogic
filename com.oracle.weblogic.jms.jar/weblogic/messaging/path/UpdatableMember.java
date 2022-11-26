package weblogic.messaging.path;

import weblogic.common.CompletionRequest;
import weblogic.store.PersistentStoreTransaction;

public interface UpdatableMember extends Member {
   void update(Key var1, UpdatableMember var2, CompletionRequest var3);

   boolean updateException(Throwable var1, Key var2, UpdatableMember var3, PersistentStoreTransaction var4, CompletionRequest var5);
}
