package javax.enterprise.concurrent;

import java.util.Map;

public interface ContextService {
   Object createContextualProxy(Object var1, Class var2);

   Object createContextualProxy(Object var1, Class... var2);

   Object createContextualProxy(Object var1, Map var2, Class var3);

   Object createContextualProxy(Object var1, Map var2, Class... var3);

   Map getExecutionProperties(Object var1);
}
