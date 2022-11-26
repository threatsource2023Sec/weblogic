package org.python.apache.xerces.impl.xs.identity;

import org.python.apache.xerces.xs.ShortList;

public interface ValueStore {
   void addValue(Field var1, boolean var2, Object var3, short var4, ShortList var5);

   void reportError(String var1, Object[] var2);
}
