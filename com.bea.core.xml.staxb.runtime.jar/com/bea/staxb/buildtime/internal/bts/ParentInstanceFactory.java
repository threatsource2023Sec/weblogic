package com.bea.staxb.buildtime.internal.bts;

import java.util.Collections;
import java.util.Map;
import weblogic.utils.collections.LRUCacheHashMap;

public class ParentInstanceFactory extends JavaInstanceFactory {
   private MethodName createObjectMethod;
   private static final long serialVersionUID = 1L;
   private static final Map cache = Collections.synchronizedMap(new LRUCacheHashMap(10));

   public static ParentInstanceFactory forMethodName(MethodName factoryMethod) {
      ParentInstanceFactory retFactory = (ParentInstanceFactory)cache.get(factoryMethod);
      if (retFactory == null) {
         retFactory = new ParentInstanceFactory(factoryMethod);
         cache.put(factoryMethod, retFactory);
      }

      return retFactory;
   }

   public ParentInstanceFactory() {
   }

   public ParentInstanceFactory(MethodName factoryMethod) {
      this.setCreateObjectMethod(factoryMethod);
   }

   public MethodName getCreateObjectMethod() {
      return this.createObjectMethod;
   }

   public void setCreateObjectMethod(MethodName createObjectMethod) {
      this.createObjectMethod = createObjectMethod;
   }
}
