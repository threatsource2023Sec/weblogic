package weblogic.cache;

import java.io.Serializable;
import java.util.Map;

public interface CacheEntry extends Map.Entry, Serializable {
   void touch();

   long getExpirationTime();

   long getVersion();

   void setVersion(long var1);

   long getCreationTime();

   long getLastAccessTime();

   long getLastUpdateTime();

   boolean isDiscarded();

   void discard();
}
