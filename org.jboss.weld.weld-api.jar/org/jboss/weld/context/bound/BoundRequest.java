package org.jboss.weld.context.bound;

import java.util.Map;

public interface BoundRequest {
   Map getRequestMap();

   Map getSessionMap(boolean var1);
}
