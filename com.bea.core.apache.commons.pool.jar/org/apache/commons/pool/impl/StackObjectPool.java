package org.apache.commons.pool.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolUtils;
import org.apache.commons.pool.PoolableObjectFactory;

public class StackObjectPool extends BaseObjectPool implements ObjectPool {
   protected static final int DEFAULT_MAX_SLEEPING = 8;
   protected static final int DEFAULT_INIT_SLEEPING_CAPACITY = 4;
   /** @deprecated */
   @Deprecated
   protected Stack _pool;
   /** @deprecated */
   @Deprecated
   protected PoolableObjectFactory _factory;
   /** @deprecated */
   @Deprecated
   protected int _maxSleeping;
   /** @deprecated */
   @Deprecated
   protected int _numActive;

   /** @deprecated */
   @Deprecated
   public StackObjectPool() {
      this((PoolableObjectFactory)null, 8, 4);
   }

   /** @deprecated */
   @Deprecated
   public StackObjectPool(int maxIdle) {
      this((PoolableObjectFactory)null, maxIdle, 4);
   }

   /** @deprecated */
   @Deprecated
   public StackObjectPool(int maxIdle, int initIdleCapacity) {
      this((PoolableObjectFactory)null, maxIdle, initIdleCapacity);
   }

   public StackObjectPool(PoolableObjectFactory factory) {
      this(factory, 8, 4);
   }

   public StackObjectPool(PoolableObjectFactory factory, int maxIdle) {
      this(factory, maxIdle, 4);
   }

   public StackObjectPool(PoolableObjectFactory factory, int maxIdle, int initIdleCapacity) {
      this._pool = null;
      this._factory = null;
      this._maxSleeping = 8;
      this._numActive = 0;
      this._factory = factory;
      this._maxSleeping = maxIdle < 0 ? 8 : maxIdle;
      int initcapacity = initIdleCapacity < 1 ? 4 : initIdleCapacity;
      this._pool = new Stack();
      this._pool.ensureCapacity(initcapacity > this._maxSleeping ? this._maxSleeping : initcapacity);
   }

   public synchronized Object borrowObject() throws Exception {
      this.assertOpen();
      Object obj = null;
      boolean newlyCreated = false;

      while(null == obj) {
         if (!this._pool.empty()) {
            obj = this._pool.pop();
         } else {
            if (null == this._factory) {
               throw new NoSuchElementException();
            }

            obj = this._factory.makeObject();
            newlyCreated = true;
            if (obj == null) {
               throw new NoSuchElementException("PoolableObjectFactory.makeObject() returned null.");
            }
         }

         if (null != this._factory && null != obj) {
            try {
               this._factory.activateObject(obj);
               if (!this._factory.validateObject(obj)) {
                  throw new Exception("ValidateObject failed");
               }
            } catch (Throwable var11) {
               PoolUtils.checkRethrow(var11);

               try {
                  this._factory.destroyObject(obj);
               } catch (Throwable var9) {
                  PoolUtils.checkRethrow(var9);
               } finally {
                  obj = null;
               }

               if (newlyCreated) {
                  throw new NoSuchElementException("Could not create a validated object, cause: " + var11.getMessage());
               }
            }
         }
      }

      ++this._numActive;
      return obj;
   }

   public synchronized void returnObject(Object obj) throws Exception {
      boolean success = !this.isClosed();
      if (null != this._factory) {
         if (!this._factory.validateObject(obj)) {
            success = false;
         } else {
            try {
               this._factory.passivateObject(obj);
            } catch (Exception var6) {
               success = false;
            }
         }
      }

      boolean shouldDestroy = !success;
      --this._numActive;
      if (success) {
         Object toBeDestroyed = null;
         if (this._pool.size() >= this._maxSleeping) {
            shouldDestroy = true;
            toBeDestroyed = this._pool.remove(0);
         }

         this._pool.push(obj);
         obj = toBeDestroyed;
      }

      this.notifyAll();
      if (shouldDestroy) {
         try {
            this._factory.destroyObject(obj);
         } catch (Exception var5) {
         }
      }

   }

   public synchronized void invalidateObject(Object obj) throws Exception {
      --this._numActive;
      if (null != this._factory) {
         this._factory.destroyObject(obj);
      }

      this.notifyAll();
   }

   public synchronized int getNumIdle() {
      return this._pool.size();
   }

   public synchronized int getNumActive() {
      return this._numActive;
   }

   public synchronized void clear() {
      if (null != this._factory) {
         Iterator it = this._pool.iterator();

         while(it.hasNext()) {
            try {
               this._factory.destroyObject(it.next());
            } catch (Exception var3) {
            }
         }
      }

      this._pool.clear();
   }

   public void close() throws Exception {
      super.close();
      this.clear();
   }

   public synchronized void addObject() throws Exception {
      this.assertOpen();
      if (this._factory == null) {
         throw new IllegalStateException("Cannot add objects without a factory.");
      } else {
         Object obj = this._factory.makeObject();
         boolean success = true;
         if (!this._factory.validateObject(obj)) {
            success = false;
         } else {
            this._factory.passivateObject(obj);
         }

         boolean shouldDestroy = !success;
         if (success) {
            Object toBeDestroyed = null;
            if (this._pool.size() >= this._maxSleeping) {
               shouldDestroy = true;
               toBeDestroyed = this._pool.remove(0);
            }

            this._pool.push(obj);
            obj = toBeDestroyed;
         }

         this.notifyAll();
         if (shouldDestroy) {
            try {
               this._factory.destroyObject(obj);
            } catch (Exception var5) {
            }
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setFactory(PoolableObjectFactory factory) throws IllegalStateException {
      this.assertOpen();
      if (0 < this.getNumActive()) {
         throw new IllegalStateException("Objects are already active");
      } else {
         this.clear();
         this._factory = factory;
      }
   }

   public synchronized PoolableObjectFactory getFactory() {
      return this._factory;
   }

   public int getMaxSleeping() {
      return this._maxSleeping;
   }
}
