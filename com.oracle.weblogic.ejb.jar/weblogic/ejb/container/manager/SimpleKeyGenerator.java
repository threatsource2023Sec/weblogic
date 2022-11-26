package weblogic.ejb.container.manager;

import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb20.manager.SimpleKey;

public final class SimpleKeyGenerator implements KeyGenerator {
   private long base_id;
   private long count;

   public void setup(BeanInfo info) {
      long h = (long)System.identityHashCode(this);
      this.base_id = h << 32;
   }

   public Object nextKey() {
      long myCount;
      synchronized(this) {
         myCount = (long)(this.count++);
      }

      return new SimpleKey(this.base_id ^ myCount);
   }
}
