package org.apache.openjpa.kernel;

import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.meta.ClassMetaData;

public interface Seq extends Closeable {
   int TYPE_DEFAULT = 0;
   int TYPE_NONTRANSACTIONAL = 1;
   int TYPE_TRANSACTIONAL = 2;
   int TYPE_CONTIGUOUS = 3;

   void setType(int var1);

   Object next(StoreContext var1, ClassMetaData var2);

   Object current(StoreContext var1, ClassMetaData var2);

   void allocate(int var1, StoreContext var2, ClassMetaData var3);

   void close();
}
