package org.apache.commons.pool;

import java.util.NoSuchElementException;

public interface ObjectPool {
   Object borrowObject() throws Exception, NoSuchElementException, IllegalStateException;

   void returnObject(Object var1) throws Exception;

   void invalidateObject(Object var1) throws Exception;

   void addObject() throws Exception, IllegalStateException, UnsupportedOperationException;

   int getNumIdle() throws UnsupportedOperationException;

   int getNumActive() throws UnsupportedOperationException;

   void clear() throws Exception, UnsupportedOperationException;

   void close() throws Exception;

   /** @deprecated */
   @Deprecated
   void setFactory(PoolableObjectFactory var1) throws IllegalStateException, UnsupportedOperationException;
}
