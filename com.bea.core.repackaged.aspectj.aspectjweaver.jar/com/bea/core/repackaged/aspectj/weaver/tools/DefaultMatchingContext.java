package com.bea.core.repackaged.aspectj.weaver.tools;

import java.util.HashMap;
import java.util.Map;

public class DefaultMatchingContext implements MatchingContext {
   private Map contextMap = new HashMap();

   public boolean hasContextBinding(String contextParameterName) {
      return this.contextMap.containsKey(contextParameterName);
   }

   public Object getBinding(String contextParameterName) {
      return this.contextMap.get(contextParameterName);
   }

   public void addContextBinding(String name, Object value) {
      this.contextMap.put(name, value);
   }

   public void removeContextBinding(String name) {
      this.contextMap.remove(name);
   }
}
