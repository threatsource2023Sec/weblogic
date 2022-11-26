package org.apache.commons.pool;

public interface PoolableObjectFactory {
   Object makeObject() throws Exception;

   void destroyObject(Object var1) throws Exception;

   boolean validateObject(Object var1);

   void activateObject(Object var1) throws Exception;

   void passivateObject(Object var1) throws Exception;
}
