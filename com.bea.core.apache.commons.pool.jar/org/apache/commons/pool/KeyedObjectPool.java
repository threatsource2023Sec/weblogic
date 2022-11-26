package org.apache.commons.pool;

import java.util.NoSuchElementException;

public interface KeyedObjectPool {
   Object borrowObject(Object var1) throws Exception, NoSuchElementException, IllegalStateException;

   void returnObject(Object var1, Object var2) throws Exception;

   void invalidateObject(Object var1, Object var2) throws Exception;

   void addObject(Object var1) throws Exception, IllegalStateException, UnsupportedOperationException;

   int getNumIdle(Object var1) throws UnsupportedOperationException;

   int getNumActive(Object var1) throws UnsupportedOperationException;

   int getNumIdle() throws UnsupportedOperationException;

   int getNumActive() throws UnsupportedOperationException;

   void clear() throws Exception, UnsupportedOperationException;

   void clear(Object var1) throws Exception, UnsupportedOperationException;

   void close() throws Exception;

   /** @deprecated */
   @Deprecated
   void setFactory(KeyedPoolableObjectFactory var1) throws IllegalStateException, UnsupportedOperationException;
}
