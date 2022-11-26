package weblogic.work.concurrent.spi;

import java.io.Serializable;
import java.util.Map;

public interface ContextProvider extends Serializable {
   String CONTEXT_INFO_INTERNAL = "internal";
   String CONTEXT_INFO_CLASSLOADER = "classloader";
   String CONTEXT_INFO_JNDI = "jndi";
   String CONTEXT_INFO_SECURITY = "security";
   String CONTEXT_INFO_WORKAREA = "workarea";
   int AllConcurrentObjects = -1;
   int ContextService = 1;
   int ManagedThreadFactory = 2;
   int ManagedExecutorService = 4;
   int ManagedScheduledExecutorService = 8;

   String getContextType();

   int getConcurrentObjectType();

   ContextHandle save(Map var1);

   ContextHandle setup(ContextHandle var1);

   void reset(ContextHandle var1);
}
