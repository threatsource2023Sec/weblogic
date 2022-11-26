package weblogic.cache;

import java.util.Iterator;

public interface CacheScope {
   boolean isReadOnly();

   Iterator getAttributeNames();

   void setAttribute(String var1, Object var2);

   Object getAttribute(String var1);

   void removeAttribute(String var1);
}
