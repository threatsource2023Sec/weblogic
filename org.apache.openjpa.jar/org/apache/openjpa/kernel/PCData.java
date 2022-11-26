package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.BitSet;

public interface PCData extends Serializable {
   Object getId();

   Class getType();

   Object getImplData();

   void setImplData(Object var1);

   Object getVersion();

   void setVersion(Object var1);

   void load(OpenJPAStateManager var1, FetchConfiguration var2, Object var3);

   void load(OpenJPAStateManager var1, BitSet var2, FetchConfiguration var3, Object var4);

   void store(OpenJPAStateManager var1);

   void store(OpenJPAStateManager var1, BitSet var2);

   Object getData(int var1);

   boolean isLoaded(int var1);
}
