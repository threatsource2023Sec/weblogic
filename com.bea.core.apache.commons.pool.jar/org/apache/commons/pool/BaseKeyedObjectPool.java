package org.apache.commons.pool;

public abstract class BaseKeyedObjectPool implements KeyedObjectPool {
   private volatile boolean closed = false;

   public abstract Object borrowObject(Object var1) throws Exception;

   public abstract void returnObject(Object var1, Object var2) throws Exception;

   public abstract void invalidateObject(Object var1, Object var2) throws Exception;

   public void addObject(Object key) throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public int getNumIdle(Object key) throws UnsupportedOperationException {
      return -1;
   }

   public int getNumActive(Object key) throws UnsupportedOperationException {
      return -1;
   }

   public int getNumIdle() throws UnsupportedOperationException {
      return -1;
   }

   public int getNumActive() throws UnsupportedOperationException {
      return -1;
   }

   public void clear() throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public void clear(Object key) throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public void close() throws Exception {
      this.closed = true;
   }

   /** @deprecated */
   @Deprecated
   public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   protected final boolean isClosed() {
      return this.closed;
   }

   protected final void assertOpen() throws IllegalStateException {
      if (this.isClosed()) {
         throw new IllegalStateException("Pool not open");
      }
   }
}
