package weblogic.cache;

import java.util.Iterator;

public interface CacheScope {
   boolean isReadOnly();

   Iterator getAttributeNames() throws CacheException;

   void setAttribute(String var1, Object var2) throws CacheException;

   Object getAttribute(String var1) throws CacheException;

   void removeAttribute(String var1) throws CacheException;
}
