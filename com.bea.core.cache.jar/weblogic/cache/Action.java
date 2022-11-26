package weblogic.cache;

import java.io.Serializable;

public interface Action extends Serializable {
   void setTarget(CacheMap var1);

   Object run();

   void close();
}
