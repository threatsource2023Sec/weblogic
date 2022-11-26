package org.apache.openjpa.kernel;

import java.util.Collection;
import org.apache.openjpa.lib.util.Closeable;

public interface LockManager extends Closeable, LockLevels {
   void setContext(StoreContext var1);

   int getLockLevel(OpenJPAStateManager var1);

   void lock(OpenJPAStateManager var1, int var2, int var3, Object var4);

   void lockAll(Collection var1, int var2, int var3, Object var4);

   void release(OpenJPAStateManager var1);

   void beginTransaction();

   void endTransaction();

   void close();
}
