package com.googlecode.cqengine.index.support;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultConcurrentSetFactory implements Factory {
   final int initialSize;

   public DefaultConcurrentSetFactory() {
      this.initialSize = 16;
   }

   public DefaultConcurrentSetFactory(int initialSize) {
      this.initialSize = initialSize;
   }

   public Set create() {
      return Collections.newSetFromMap(new ConcurrentHashMap(this.initialSize));
   }
}
