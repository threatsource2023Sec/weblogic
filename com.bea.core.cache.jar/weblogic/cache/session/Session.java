package weblogic.cache.session;

import weblogic.cache.CacheMap;

public interface Session extends CacheMap {
   void flush();

   void cancel();

   boolean isClosed();

   void close();
}
