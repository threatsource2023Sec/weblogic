package org.jboss.weld.context.bound;

import java.util.Map;

public class MutableBoundRequest implements BoundRequest {
   private final Map requestMap;
   private final Map sessionMap;

   public MutableBoundRequest(Map requestMap, Map sessionMap) {
      this.requestMap = requestMap;
      this.sessionMap = sessionMap;
   }

   public Map getRequestMap() {
      return this.requestMap;
   }

   public Map getSessionMap(boolean create) {
      return this.sessionMap;
   }
}
