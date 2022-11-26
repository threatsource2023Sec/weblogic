package org.glassfish.hk2.xml.jaxb.internal;

import java.util.Map;
import org.glassfish.hk2.xml.internal.ModelImpl;

public interface NamespaceBeanLikeMap {
   Object getValue(String var1, String var2);

   void setValue(String var1, String var2, Object var3);

   boolean isSet(String var1, String var2);

   void backup();

   void restoreBackup(boolean var1);

   Map getBeanLikeMap(Map var1);

   Map getQNameMap();

   void shallowCopy(NamespaceBeanLikeMap var1, ModelImpl var2, boolean var3);

   Map getNamespaceBeanLikeMap();
}
