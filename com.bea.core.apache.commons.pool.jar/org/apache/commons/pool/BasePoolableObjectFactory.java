package org.apache.commons.pool;

public abstract class BasePoolableObjectFactory implements PoolableObjectFactory {
   public abstract Object makeObject() throws Exception;

   public void destroyObject(Object obj) throws Exception {
   }

   public boolean validateObject(Object obj) {
      return true;
   }

   public void activateObject(Object obj) throws Exception {
   }

   public void passivateObject(Object obj) throws Exception {
   }
}
