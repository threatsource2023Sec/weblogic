package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class NameMatchCacheOperationSource implements CacheOperationSource, Serializable {
   protected static final Log logger = LogFactory.getLog(NameMatchCacheOperationSource.class);
   private Map nameMap = new LinkedHashMap();

   public void setNameMap(Map nameMap) {
      nameMap.forEach(this::addCacheMethod);
   }

   public void addCacheMethod(String methodName, Collection ops) {
      if (logger.isDebugEnabled()) {
         logger.debug("Adding method [" + methodName + "] with cache operations [" + ops + "]");
      }

      this.nameMap.put(methodName, ops);
   }

   @Nullable
   public Collection getCacheOperations(Method method, @Nullable Class targetClass) {
      String methodName = method.getName();
      Collection ops = (Collection)this.nameMap.get(methodName);
      if (ops == null) {
         String bestNameMatch = null;
         Iterator var6 = this.nameMap.keySet().iterator();

         while(true) {
            String mappedName;
            do {
               do {
                  if (!var6.hasNext()) {
                     return ops;
                  }

                  mappedName = (String)var6.next();
               } while(!this.isMatch(methodName, mappedName));
            } while(bestNameMatch != null && bestNameMatch.length() > mappedName.length());

            ops = (Collection)this.nameMap.get(mappedName);
            bestNameMatch = mappedName;
         }
      } else {
         return ops;
      }
   }

   protected boolean isMatch(String methodName, String mappedName) {
      return PatternMatchUtils.simpleMatch(mappedName, methodName);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NameMatchCacheOperationSource)) {
         return false;
      } else {
         NameMatchCacheOperationSource otherTas = (NameMatchCacheOperationSource)other;
         return ObjectUtils.nullSafeEquals(this.nameMap, otherTas.nameMap);
      }
   }

   public int hashCode() {
      return NameMatchCacheOperationSource.class.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.nameMap;
   }
}
