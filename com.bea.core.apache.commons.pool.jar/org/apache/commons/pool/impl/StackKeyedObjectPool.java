package org.apache.commons.pool.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.apache.commons.pool.BaseKeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.PoolUtils;

public class StackKeyedObjectPool extends BaseKeyedObjectPool implements KeyedObjectPool {
   protected static final int DEFAULT_MAX_SLEEPING = 8;
   protected static final int DEFAULT_INIT_SLEEPING_CAPACITY = 4;
   /** @deprecated */
   @Deprecated
   protected HashMap _pools;
   /** @deprecated */
   @Deprecated
   protected KeyedPoolableObjectFactory _factory;
   /** @deprecated */
   @Deprecated
   protected int _maxSleeping;
   /** @deprecated */
   @Deprecated
   protected int _initSleepingCapacity;
   /** @deprecated */
   @Deprecated
   protected int _totActive;
   /** @deprecated */
   @Deprecated
   protected int _totIdle;
   /** @deprecated */
   @Deprecated
   protected HashMap _activeCount;

   public StackKeyedObjectPool() {
      this((KeyedPoolableObjectFactory)null, 8, 4);
   }

   public StackKeyedObjectPool(int max) {
      this((KeyedPoolableObjectFactory)null, max, 4);
   }

   public StackKeyedObjectPool(int max, int init) {
      this((KeyedPoolableObjectFactory)null, max, init);
   }

   public StackKeyedObjectPool(KeyedPoolableObjectFactory factory) {
      this(factory, 8);
   }

   public StackKeyedObjectPool(KeyedPoolableObjectFactory factory, int max) {
      this(factory, max, 4);
   }

   public StackKeyedObjectPool(KeyedPoolableObjectFactory factory, int max, int init) {
      this._pools = null;
      this._factory = null;
      this._maxSleeping = 8;
      this._initSleepingCapacity = 4;
      this._totActive = 0;
      this._totIdle = 0;
      this._activeCount = null;
      this._factory = factory;
      this._maxSleeping = max < 0 ? 8 : max;
      this._initSleepingCapacity = init < 1 ? 4 : init;
      this._pools = new HashMap();
      this._activeCount = new HashMap();
   }

   public synchronized Object borrowObject(Object key) throws Exception {
      this.assertOpen();
      Stack stack = (Stack)this._pools.get(key);
      if (null == stack) {
         stack = new Stack();
         stack.ensureCapacity(this._initSleepingCapacity > this._maxSleeping ? this._maxSleeping : this._initSleepingCapacity);
         this._pools.put(key, stack);
      }

      Object obj = null;

      do {
         boolean newlyMade = false;
         if (!stack.empty()) {
            obj = stack.pop();
            --this._totIdle;
         } else {
            if (null == this._factory) {
               throw new NoSuchElementException("pools without a factory cannot create new objects as needed.");
            }

            obj = this._factory.makeObject(key);
            newlyMade = true;
         }

         if (null != this._factory && null != obj) {
            try {
               this._factory.activateObject(key, obj);
               if (!this._factory.validateObject(key, obj)) {
                  throw new Exception("ValidateObject failed");
               }
            } catch (Throwable var13) {
               PoolUtils.checkRethrow(var13);

               try {
                  this._factory.destroyObject(key, obj);
               } catch (Throwable var11) {
                  PoolUtils.checkRethrow(var11);
               } finally {
                  obj = null;
               }

               if (newlyMade) {
                  throw new NoSuchElementException("Could not create a validated object, cause: " + var13.getMessage());
               }
            }
         }
      } while(obj == null);

      this.incrementActiveCount(key);
      return obj;
   }

   public synchronized void returnObject(Object key, Object obj) throws Exception {
      this.decrementActiveCount(key);
      if (null != this._factory) {
         if (!this._factory.validateObject(key, obj)) {
            return;
         }

         try {
            this._factory.passivateObject(key, obj);
         } catch (Exception var9) {
            this._factory.destroyObject(key, obj);
            return;
         }
      }

      if (this.isClosed()) {
         if (null != this._factory) {
            try {
               this._factory.destroyObject(key, obj);
            } catch (Exception var7) {
            }
         }

      } else {
         Stack stack = (Stack)this._pools.get(key);
         if (null == stack) {
            stack = new Stack();
            stack.ensureCapacity(this._initSleepingCapacity > this._maxSleeping ? this._maxSleeping : this._initSleepingCapacity);
            this._pools.put(key, stack);
         }

         int stackSize = stack.size();
         if (stackSize >= this._maxSleeping) {
            Object staleObj;
            if (stackSize > 0) {
               staleObj = stack.remove(0);
               --this._totIdle;
            } else {
               staleObj = obj;
            }

            if (null != this._factory) {
               try {
                  this._factory.destroyObject(key, staleObj);
               } catch (Exception var8) {
               }
            }
         }

         stack.push(obj);
         ++this._totIdle;
      }
   }

   public synchronized void invalidateObject(Object key, Object obj) throws Exception {
      this.decrementActiveCount(key);
      if (null != this._factory) {
         this._factory.destroyObject(key, obj);
      }

      this.notifyAll();
   }

   public synchronized void addObject(Object key) throws Exception {
      this.assertOpen();
      if (this._factory == null) {
         throw new IllegalStateException("Cannot add objects without a factory.");
      } else {
         Object obj = this._factory.makeObject(key);

         try {
            if (!this._factory.validateObject(key, obj)) {
               return;
            }
         } catch (Exception var9) {
            try {
               this._factory.destroyObject(key, obj);
            } catch (Exception var7) {
            }

            return;
         }

         this._factory.passivateObject(key, obj);
         Stack stack = (Stack)this._pools.get(key);
         if (null == stack) {
            stack = new Stack();
            stack.ensureCapacity(this._initSleepingCapacity > this._maxSleeping ? this._maxSleeping : this._initSleepingCapacity);
            this._pools.put(key, stack);
         }

         int stackSize = stack.size();
         if (stackSize >= this._maxSleeping) {
            Object staleObj;
            if (stackSize > 0) {
               staleObj = stack.remove(0);
               --this._totIdle;
            } else {
               staleObj = obj;
            }

            try {
               this._factory.destroyObject(key, staleObj);
            } catch (Exception var8) {
               if (obj == staleObj) {
                  throw var8;
               }
            }
         } else {
            stack.push(obj);
            ++this._totIdle;
         }

      }
   }

   public synchronized int getNumIdle() {
      return this._totIdle;
   }

   public synchronized int getNumActive() {
      return this._totActive;
   }

   public synchronized int getNumActive(Object key) {
      return this.getActiveCount(key);
   }

   public synchronized int getNumIdle(Object key) {
      try {
         return ((Stack)this._pools.get(key)).size();
      } catch (Exception var3) {
         return 0;
      }
   }

   public synchronized void clear() {
      Iterator it = this._pools.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         Stack stack = (Stack)this._pools.get(key);
         this.destroyStack(key, stack);
      }

      this._totIdle = 0;
      this._pools.clear();
      this._activeCount.clear();
   }

   public synchronized void clear(Object key) {
      Stack stack = (Stack)this._pools.remove(key);
      this.destroyStack(key, stack);
   }

   private synchronized void destroyStack(Object key, Stack stack) {
      if (null != stack) {
         if (null != this._factory) {
            Iterator it = stack.iterator();

            while(it.hasNext()) {
               try {
                  this._factory.destroyObject(key, it.next());
               } catch (Exception var5) {
               }
            }
         }

         this._totIdle -= stack.size();
         this._activeCount.remove(key);
         stack.clear();
      }
   }

   public synchronized String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.getClass().getName());
      buf.append(" contains ").append(this._pools.size()).append(" distinct pools: ");
      Iterator it = this._pools.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         buf.append(" |").append(key).append("|=");
         Stack s = (Stack)this._pools.get(key);
         buf.append(s.size());
      }

      return buf.toString();
   }

   public void close() throws Exception {
      super.close();
      this.clear();
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException {
      if (0 < this.getNumActive()) {
         throw new IllegalStateException("Objects are already active");
      } else {
         this.clear();
         this._factory = factory;
      }
   }

   public synchronized KeyedPoolableObjectFactory getFactory() {
      return this._factory;
   }

   private int getActiveCount(Object key) {
      try {
         return (Integer)this._activeCount.get(key);
      } catch (NoSuchElementException var3) {
         return 0;
      } catch (NullPointerException var4) {
         return 0;
      }
   }

   private void incrementActiveCount(Object key) {
      ++this._totActive;
      Integer old = (Integer)this._activeCount.get(key);
      if (null == old) {
         this._activeCount.put(key, new Integer(1));
      } else {
         this._activeCount.put(key, new Integer(old + 1));
      }

   }

   private void decrementActiveCount(Object key) {
      --this._totActive;
      Integer active = (Integer)this._activeCount.get(key);
      if (null != active) {
         if (active <= 1) {
            this._activeCount.remove(key);
         } else {
            this._activeCount.put(key, new Integer(active - 1));
         }
      }

   }

   public Map getPools() {
      return this._pools;
   }

   public int getMaxSleeping() {
      return this._maxSleeping;
   }

   public int getInitSleepingCapacity() {
      return this._initSleepingCapacity;
   }

   public int getTotActive() {
      return this._totActive;
   }

   public int getTotIdle() {
      return this._totIdle;
   }

   public Map getActiveCount() {
      return this._activeCount;
   }
}
