package org.apache.commons.pool;

public interface KeyedPoolableObjectFactory {
   Object makeObject(Object var1) throws Exception;

   void destroyObject(Object var1, Object var2) throws Exception;

   boolean validateObject(Object var1, Object var2);

   void activateObject(Object var1, Object var2) throws Exception;

   void passivateObject(Object var1, Object var2) throws Exception;
}
