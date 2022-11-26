package org.apache.commons.pool;

public abstract class BaseObjectPool implements ObjectPool {
   private volatile boolean closed = false;

   public abstract Object borrowObject() throws Exception;

   public abstract void returnObject(Object var1) throws Exception;

   public abstract void invalidateObject(Object var1) throws Exception;

   public int getNumIdle() throws UnsupportedOperationException {
      return -1;
   }

   public int getNumActive() throws UnsupportedOperationException {
      return -1;
   }

   public void clear() throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public void addObject() throws Exception, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public void close() throws Exception {
      this.closed = true;
   }

   /** @deprecated */
   @Deprecated
   public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public final boolean isClosed() {
      return this.closed;
   }

   protected final void assertOpen() throws IllegalStateException {
      if (this.isClosed()) {
         throw new IllegalStateException("Pool not open");
      }
   }
}
