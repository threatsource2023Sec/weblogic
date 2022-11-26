package org.apache.velocity.util.introspection;

import java.util.Iterator;

public interface Uberspect {
   void init() throws Exception;

   Iterator getIterator(Object var1, Info var2) throws Exception;

   VelMethod getMethod(Object var1, String var2, Object[] var3, Info var4) throws Exception;

   VelPropertyGet getPropertyGet(Object var1, String var2, Info var3) throws Exception;

   VelPropertySet getPropertySet(Object var1, String var2, Object var3, Info var4) throws Exception;
}
