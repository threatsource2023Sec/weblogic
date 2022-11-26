package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.ObjectFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.Scope;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SimpleThreadScope implements Scope {
   private static final Log logger = LogFactory.getLog(SimpleThreadScope.class);
   private final ThreadLocal threadScope = new NamedThreadLocal("SimpleThreadScope") {
      protected Map initialValue() {
         return new HashMap();
      }
   };

   public Object get(String name, ObjectFactory objectFactory) {
      Map scope = (Map)this.threadScope.get();
      Object scopedObject = scope.get(name);
      if (scopedObject == null) {
         scopedObject = objectFactory.getObject();
         scope.put(name, scopedObject);
      }

      return scopedObject;
   }

   @Nullable
   public Object remove(String name) {
      Map scope = (Map)this.threadScope.get();
      return scope.remove(name);
   }

   public void registerDestructionCallback(String name, Runnable callback) {
      logger.warn("SimpleThreadScope does not support destruction callbacks. Consider using RequestScope in a web environment.");
   }

   @Nullable
   public Object resolveContextualObject(String key) {
      return null;
   }

   public String getConversationId() {
      return Thread.currentThread().getName();
   }
}
