package weblogic.cache.session;

import java.util.Map;

public interface Workspace {
   boolean isEmpty();

   Object get(Object var1);

   boolean wasNull();

   boolean wasRemoved();

   Map getAdds();

   Map getUpdates();

   void put(Object var1, Object var2);

   void put(Object var1, Object var2, Object var3);

   Map getRemoves();

   void remove(Object var1, Object var2);

   boolean isCleared();

   void clear();

   void reset();
}
