package weblogic.cache.lld;

import java.io.Serializable;
import java.util.Map;

public interface CacheEntry extends Map.Entry, Serializable {
   void touch();

   void discard();

   long expiration();

   long getCreationTime();

   long getLastAccessTime();

   long getLastUpdateTime();

   boolean isDiscarded();
}
