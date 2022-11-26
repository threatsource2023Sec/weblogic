package org.apache.commons.pool;

public abstract class BaseKeyedPoolableObjectFactory implements KeyedPoolableObjectFactory {
   public abstract Object makeObject(Object var1) throws Exception;

   public void destroyObject(Object key, Object obj) throws Exception {
   }

   public boolean validateObject(Object key, Object obj) {
      return true;
   }

   public void activateObject(Object key, Object obj) throws Exception {
   }

   public void passivateObject(Object key, Object obj) throws Exception {
   }
}
