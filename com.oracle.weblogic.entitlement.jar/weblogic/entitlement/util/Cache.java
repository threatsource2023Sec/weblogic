package weblogic.entitlement.util;

import java.util.Iterator;
import java.util.Map;

public interface Cache {
   int getMaximumSize();

   void setMaximumSize(int var1);

   int size();

   Object put(Object var1, Object var2);

   Object get(Object var1);

   Object lookup(Object var1);

   void putOff(Object var1);

   boolean containsKey(Object var1);

   boolean containsValue(Object var1);

   Map.Entry remove();

   Object remove(Object var1);

   void clear();

   Iterator iterator();
}
