package org.apache.openjpa.kernel;

import java.util.BitSet;
import java.util.Collection;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

public interface StoreManager extends Closeable {
   int VERSION_LATER = 1;
   int VERSION_EARLIER = 2;
   int VERSION_SAME = 3;
   int VERSION_DIFFERENT = 4;
   int FORCE_LOAD_NONE = 0;
   int FORCE_LOAD_DFG = 1;
   int FORCE_LOAD_REFRESH = 3;
   int FORCE_LOAD_ALL = 2;

   void setContext(StoreContext var1);

   void beginOptimistic();

   void rollbackOptimistic();

   void begin();

   void commit();

   void rollback();

   boolean exists(OpenJPAStateManager var1, Object var2);

   boolean syncVersion(OpenJPAStateManager var1, Object var2);

   boolean initialize(OpenJPAStateManager var1, PCState var2, FetchConfiguration var3, Object var4);

   boolean load(OpenJPAStateManager var1, BitSet var2, FetchConfiguration var3, int var4, Object var5);

   Collection loadAll(Collection var1, PCState var2, int var3, FetchConfiguration var4, Object var5);

   void beforeStateChange(OpenJPAStateManager var1, PCState var2, PCState var3);

   Collection flush(Collection var1);

   boolean assignObjectId(OpenJPAStateManager var1, boolean var2);

   boolean assignField(OpenJPAStateManager var1, int var2, boolean var3);

   Class getManagedType(Object var1);

   Class getDataStoreIdType(ClassMetaData var1);

   Object copyDataStoreId(Object var1, ClassMetaData var2);

   Object newDataStoreId(Object var1, ClassMetaData var2);

   Object getClientConnection();

   void retainConnection();

   void releaseConnection();

   boolean cancelAll();

   ResultObjectProvider executeExtent(ClassMetaData var1, boolean var2, FetchConfiguration var3);

   StoreQuery newQuery(String var1);

   FetchConfiguration newFetchConfiguration();

   int compareVersion(OpenJPAStateManager var1, Object var2, Object var3);

   Seq getDataStoreIdSequence(ClassMetaData var1);

   Seq getValueSequence(FieldMetaData var1);

   void close();
}
