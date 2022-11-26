package com.bea.common.ldap;

import java.util.BitSet;
import java.util.Map;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ClassMetaData;

public interface DataLoader {
   void loadData(ObjectData var1, FetchConfiguration var2, OpenJPAStateManager var3);

   void loadData(ObjectData var1, BitSet var2, FetchConfiguration var3, OpenJPAStateManager var4);

   String[] createFetchList(BitSet var1, FetchConfiguration var2);

   String createSearchBase(ClassMetaData var1, Map var2, Object[] var3);
}
