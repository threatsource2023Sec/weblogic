package org.apache.commons.pool.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimerTask;
import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolUtils;
import org.apache.commons.pool.PoolableObjectFactory;

public class GenericObjectPool extends BaseObjectPool implements ObjectPool {
   public static final byte WHEN_EXHAUSTED_FAIL = 0;
   public static final byte WHEN_EXHAUSTED_BLOCK = 1;
   public static final byte WHEN_EXHAUSTED_GROW = 2;
   public static final int DEFAULT_MAX_IDLE = 8;
   public static final int DEFAULT_MIN_IDLE = 0;
   public static final int DEFAULT_MAX_ACTIVE = 8;
   public static final byte DEFAULT_WHEN_EXHAUSTED_ACTION = 1;
   public static final boolean DEFAULT_LIFO = true;
   public static final long DEFAULT_MAX_WAIT = -1L;
   public static final boolean DEFAULT_TEST_ON_BORROW = false;
   public static final boolean DEFAULT_TEST_ON_RETURN = false;
   public static final boolean DEFAULT_TEST_WHILE_IDLE = false;
   public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1L;
   public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;
   public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1800000L;
   public static final long DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = -1L;
   private int _maxIdle;
   private int _minIdle;
   private int _maxActive;
   private long _maxWait;
   private byte _whenExhaustedAction;
   private volatile boolean _testOnBorrow;
   private volatile boolean _testOnReturn;
   private boolean _testWhileIdle;
   private long _timeBetweenEvictionRunsMillis;
   private int _numTestsPerEvictionRun;
   private long _minEvictableIdleTimeMillis;
   private long _softMinEvictableIdleTimeMillis;
   private boolean _lifo;
   private CursorableLinkedList _pool;
   private CursorableLinkedList.Cursor _evictionCursor;
   private PoolableObjectFactory _factory;
   private int _numActive;
   private Evictor _evictor;
   private int _numInternalProcessing;
   private final LinkedList _allocationQueue;

   public GenericObjectPool() {
      this((PoolableObjectFactory)null, 8, (byte)1, -1L, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory) {
      this(factory, 8, (byte)1, -1L, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory, Config config) {
      this(factory, config.maxActive, config.whenExhaustedAction, config.maxWait, config.maxIdle, config.minIdle, config.testOnBorrow, config.testOnReturn, config.timeBetweenEvictionRunsMillis, config.numTestsPerEvictionRun, config.minEvictableIdleTimeMillis, config.testWhileIdle, config.softMinEvictableIdleTimeMillis, config.lifo);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive) {
      this(factory, maxActive, (byte)1, -1L, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, 0, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, -1L);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, long softMinEvictableIdleTimeMillis) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, softMinEvictableIdleTimeMillis, true);
   }

   public GenericObjectPool(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, long softMinEvictableIdleTimeMillis, boolean lifo) {
      this._maxIdle = 8;
      this._minIdle = 0;
      this._maxActive = 8;
      this._maxWait = -1L;
      this._whenExhaustedAction = 1;
      this._testOnBorrow = false;
      this._testOnReturn = false;
      this._testWhileIdle = false;
      this._timeBetweenEvictionRunsMillis = -1L;
      this._numTestsPerEvictionRun = 3;
      this._minEvictableIdleTimeMillis = 1800000L;
      this._softMinEvictableIdleTimeMillis = -1L;
      this._lifo = true;
      this._pool = null;
      this._evictionCursor = null;
      this._factory = null;
      this._numActive = 0;
      this._evictor = null;
      this._numInternalProcessing = 0;
      this._allocationQueue = new LinkedList();
      this._factory = factory;
      this._maxActive = maxActive;
      this._lifo = lifo;
      switch (whenExhaustedAction) {
         case 0:
         case 1:
         case 2:
            this._whenExhaustedAction = whenExhaustedAction;
            this._maxWait = maxWait;
            this._maxIdle = maxIdle;
            this._minIdle = minIdle;
            this._testOnBorrow = testOnBorrow;
            this._testOnReturn = testOnReturn;
            this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
            this._numTestsPerEvictionRun = numTestsPerEvictionRun;
            this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
            this._softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
            this._testWhileIdle = testWhileIdle;
            this._pool = new CursorableLinkedList();
            this.startEvictor(this._timeBetweenEvictionRunsMillis);
            return;
         default:
            throw new IllegalArgumentException("whenExhaustedAction " + whenExhaustedAction + " not recognized.");
      }
   }

   public synchronized int getMaxActive() {
      return this._maxActive;
   }

   public void setMaxActive(int maxActive) {
      synchronized(this) {
         this._maxActive = maxActive;
      }

      this.allocate();
   }

   public synchronized byte getWhenExhaustedAction() {
      return this._whenExhaustedAction;
   }

   public void setWhenExhaustedAction(byte whenExhaustedAction) {
      synchronized(this) {
         switch (whenExhaustedAction) {
            case 0:
            case 1:
            case 2:
               this._whenExhaustedAction = whenExhaustedAction;
               break;
            default:
               throw new IllegalArgumentException("whenExhaustedAction " + whenExhaustedAction + " not recognized.");
         }
      }

      this.allocate();
   }

   public synchronized long getMaxWait() {
      return this._maxWait;
   }

   public void setMaxWait(long maxWait) {
      synchronized(this) {
         this._maxWait = maxWait;
      }

      this.allocate();
   }

   public synchronized int getMaxIdle() {
      return this._maxIdle;
   }

   public void setMaxIdle(int maxIdle) {
      synchronized(this) {
         this._maxIdle = maxIdle;
      }

      this.allocate();
   }

   public void setMinIdle(int minIdle) {
      synchronized(this) {
         this._minIdle = minIdle;
      }

      this.allocate();
   }

   public synchronized int getMinIdle() {
      return this._minIdle;
   }

   public boolean getTestOnBorrow() {
      return this._testOnBorrow;
   }

   public void setTestOnBorrow(boolean testOnBorrow) {
      this._testOnBorrow = testOnBorrow;
   }

   public boolean getTestOnReturn() {
      return this._testOnReturn;
   }

   public void setTestOnReturn(boolean testOnReturn) {
      this._testOnReturn = testOnReturn;
   }

   public synchronized long getTimeBetweenEvictionRunsMillis() {
      return this._timeBetweenEvictionRunsMillis;
   }

   public synchronized void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
      this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
      this.startEvictor(this._timeBetweenEvictionRunsMillis);
   }

   public synchronized int getNumTestsPerEvictionRun() {
      return this._numTestsPerEvictionRun;
   }

   public synchronized void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
      this._numTestsPerEvictionRun = numTestsPerEvictionRun;
   }

   public synchronized long getMinEvictableIdleTimeMillis() {
      return this._minEvictableIdleTimeMillis;
   }

   public synchronized void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
      this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
   }

   public synchronized long getSoftMinEvictableIdleTimeMillis() {
      return this._softMinEvictableIdleTimeMillis;
   }

   public synchronized void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis) {
      this._softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
   }

   public synchronized boolean getTestWhileIdle() {
      return this._testWhileIdle;
   }

   public synchronized void setTestWhileIdle(boolean testWhileIdle) {
      this._testWhileIdle = testWhileIdle;
   }

   public synchronized boolean getLifo() {
      return this._lifo;
   }

   public synchronized void setLifo(boolean lifo) {
      this._lifo = lifo;
   }

   public void setConfig(Config conf) {
      synchronized(this) {
         this.setMaxIdle(conf.maxIdle);
         this.setMinIdle(conf.minIdle);
         this.setMaxActive(conf.maxActive);
         this.setMaxWait(conf.maxWait);
         this.setWhenExhaustedAction(conf.whenExhaustedAction);
         this.setTestOnBorrow(conf.testOnBorrow);
         this.setTestOnReturn(conf.testOnReturn);
         this.setTestWhileIdle(conf.testWhileIdle);
         this.setNumTestsPerEvictionRun(conf.numTestsPerEvictionRun);
         this.setMinEvictableIdleTimeMillis(conf.minEvictableIdleTimeMillis);
         this.setTimeBetweenEvictionRunsMillis(conf.timeBetweenEvictionRunsMillis);
         this.setSoftMinEvictableIdleTimeMillis(conf.softMinEvictableIdleTimeMillis);
         this.setLifo(conf.lifo);
      }

      this.allocate();
   }

   public Object borrowObject() throws Exception {
      long starttime = System.currentTimeMillis();
      Latch latch = new Latch();
      byte whenExhaustedAction;
      long maxWait;
      synchronized(this) {
         whenExhaustedAction = this._whenExhaustedAction;
         maxWait = this._maxWait;
         this._allocationQueue.add(latch);
      }

      this.allocate();

      while(true) {
         synchronized(this) {
            this.assertOpen();
         }

         if (latch.getPair() == null && !latch.mayCreate()) {
            switch (whenExhaustedAction) {
               case 0:
                  synchronized(this) {
                     if (latch.getPair() == null && !latch.mayCreate()) {
                        this._allocationQueue.remove(latch);
                        throw new NoSuchElementException("Pool exhausted");
                     }
                     break;
                  }
               case 1:
                  try {
                     synchronized(latch) {
                        if (latch.getPair() != null || latch.mayCreate()) {
                           break;
                        }

                        if (maxWait <= 0L) {
                           latch.wait();
                        } else {
                           long elapsed = System.currentTimeMillis() - starttime;
                           long waitTime = maxWait - elapsed;
                           if (waitTime > 0L) {
                              latch.wait(waitTime);
                           }
                        }
                     }

                     if (this.isClosed()) {
                        throw new IllegalStateException("Pool closed");
                     }
                  } catch (InterruptedException var51) {
                     boolean doAllocate = false;
                     synchronized(this) {
                        if (latch.getPair() == null && !latch.mayCreate()) {
                           this._allocationQueue.remove(latch);
                        } else if (latch.getPair() == null && latch.mayCreate()) {
                           --this._numInternalProcessing;
                           doAllocate = true;
                        } else {
                           --this._numInternalProcessing;
                           ++this._numActive;
                           this.returnObject(latch.getPair().getValue());
                        }
                     }

                     if (doAllocate) {
                        this.allocate();
                     }

                     Thread.currentThread().interrupt();
                     throw var51;
                  }

                  if (maxWait <= 0L || System.currentTimeMillis() - starttime < maxWait) {
                     continue;
                  }

                  synchronized(this) {
                     if (latch.getPair() != null || latch.mayCreate()) {
                        break;
                     }

                     this._allocationQueue.remove(latch);
                  }

                  throw new NoSuchElementException("Timeout waiting for idle object");
               case 2:
                  synchronized(this) {
                     if (latch.getPair() == null && !latch.mayCreate()) {
                        this._allocationQueue.remove(latch);
                        ++this._numInternalProcessing;
                     }
                     break;
                  }
               default:
                  throw new IllegalArgumentException("WhenExhaustedAction property " + whenExhaustedAction + " not recognized.");
            }
         }

         boolean newlyCreated = false;
         if (null == latch.getPair()) {
            boolean var36 = false;

            try {
               var36 = true;
               Object obj = this._factory.makeObject();
               latch.setPair(new GenericKeyedObjectPool.ObjectTimestampPair(obj));
               newlyCreated = true;
               var36 = false;
            } finally {
               if (var36) {
                  if (!newlyCreated) {
                     synchronized(this) {
                        --this._numInternalProcessing;
                     }

                     this.allocate();
                  }

               }
            }

            if (!newlyCreated) {
               synchronized(this) {
                  --this._numInternalProcessing;
               }

               this.allocate();
            }
         }

         try {
            this._factory.activateObject(latch.getPair().value);
            if (this._testOnBorrow && !this._factory.validateObject(latch.getPair().value)) {
               throw new Exception("ValidateObject failed");
            }

            synchronized(this) {
               --this._numInternalProcessing;
               ++this._numActive;
            }

            return latch.getPair().value;
         } catch (Throwable var47) {
            PoolUtils.checkRethrow(var47);

            try {
               this._factory.destroyObject(latch.getPair().value);
            } catch (Throwable var42) {
               PoolUtils.checkRethrow(var42);
            }

            synchronized(this) {
               --this._numInternalProcessing;
               if (!newlyCreated) {
                  latch.reset();
                  this._allocationQueue.add(0, latch);
               }
            }

            this.allocate();
            if (newlyCreated) {
               throw new NoSuchElementException("Could not create a validated object, cause: " + var47.getMessage());
            }
         }
      }
   }

   private synchronized void allocate() {
      if (!this.isClosed()) {
         Latch latch;
         while(!this._pool.isEmpty() && !this._allocationQueue.isEmpty()) {
            latch = (Latch)this._allocationQueue.removeFirst();
            latch.setPair((GenericKeyedObjectPool.ObjectTimestampPair)this._pool.removeFirst());
            ++this._numInternalProcessing;
            synchronized(latch) {
               latch.notify();
            }
         }

         while(!this._allocationQueue.isEmpty() && (this._maxActive < 0 || this._numActive + this._numInternalProcessing < this._maxActive)) {
            latch = (Latch)this._allocationQueue.removeFirst();
            latch.setMayCreate(true);
            ++this._numInternalProcessing;
            synchronized(latch) {
               latch.notify();
            }
         }

      }
   }

   public void invalidateObject(Object obj) throws Exception {
      boolean var10 = false;

      try {
         var10 = true;
         if (this._factory != null) {
            this._factory.destroyObject(obj);
            var10 = false;
         } else {
            var10 = false;
         }
      } finally {
         if (var10) {
            synchronized(this) {
               --this._numActive;
            }

            this.allocate();
         }
      }

      synchronized(this) {
         --this._numActive;
      }

      this.allocate();
   }

   public void clear() {
      List toDestroy = new ArrayList();
      synchronized(this) {
         toDestroy.addAll(this._pool);
         this._numInternalProcessing += this._pool._size;
         this._pool.clear();
      }

      this.destroy(toDestroy, this._factory);
   }

   private void destroy(Collection c, PoolableObjectFactory factory) {
      Iterator it = c.iterator();

      while(it.hasNext()) {
         boolean var15 = false;

         label109: {
            try {
               var15 = true;
               factory.destroyObject(((GenericKeyedObjectPool.ObjectTimestampPair)it.next()).value);
               var15 = false;
               break label109;
            } catch (Exception var19) {
               var15 = false;
            } finally {
               if (var15) {
                  synchronized(this) {
                     --this._numInternalProcessing;
                  }

                  this.allocate();
               }
            }

            synchronized(this) {
               --this._numInternalProcessing;
            }

            this.allocate();
            continue;
         }

         synchronized(this) {
            --this._numInternalProcessing;
         }

         this.allocate();
      }

   }

   public synchronized int getNumActive() {
      return this._numActive;
   }

   public synchronized int getNumIdle() {
      return this._pool.size();
   }

   public void returnObject(Object obj) throws Exception {
      try {
         this.addObjectToPool(obj, true);
      } catch (Exception var7) {
         if (this._factory != null) {
            try {
               this._factory.destroyObject(obj);
            } catch (Exception var6) {
            }

            synchronized(this) {
               --this._numActive;
            }

            this.allocate();
         }
      }

   }

   private void addObjectToPool(Object obj, boolean decrementNumActive) throws Exception {
      boolean success = true;
      if (this._testOnReturn && !this._factory.validateObject(obj)) {
         success = false;
      } else {
         this._factory.passivateObject(obj);
      }

      boolean shouldDestroy = !success;
      boolean doAllocate = false;
      synchronized(this) {
         if (this.isClosed()) {
            shouldDestroy = true;
         } else if (this._maxIdle >= 0 && this._pool.size() >= this._maxIdle) {
            shouldDestroy = true;
         } else if (success) {
            if (this._lifo) {
               this._pool.addFirst(new GenericKeyedObjectPool.ObjectTimestampPair(obj));
            } else {
               this._pool.addLast(new GenericKeyedObjectPool.ObjectTimestampPair(obj));
            }

            if (decrementNumActive) {
               --this._numActive;
            }

            doAllocate = true;
         }
      }

      if (doAllocate) {
         this.allocate();
      }

      if (shouldDestroy) {
         try {
            this._factory.destroyObject(obj);
         } catch (Exception var10) {
         }

         if (decrementNumActive) {
            synchronized(this) {
               --this._numActive;
            }

            this.allocate();
         }
      }

   }

   public void close() throws Exception {
      super.close();
      synchronized(this) {
         this.clear();
         this.startEvictor(-1L);

         while(this._allocationQueue.size() > 0) {
            Latch l = (Latch)this._allocationQueue.removeFirst();
            synchronized(l) {
               l.notify();
            }
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public void setFactory(PoolableObjectFactory factory) throws IllegalStateException {
      List toDestroy = new ArrayList();
      PoolableObjectFactory oldFactory = this._factory;
      synchronized(this) {
         this.assertOpen();
         if (0 < this.getNumActive()) {
            throw new IllegalStateException("Objects are already active");
         }

         toDestroy.addAll(this._pool);
         this._numInternalProcessing += this._pool._size;
         this._pool.clear();
         this._factory = factory;
      }

      this.destroy(toDestroy, oldFactory);
   }

   public void evict() throws Exception {
      this.assertOpen();
      synchronized(this) {
         if (this._pool.isEmpty()) {
            return;
         }

         if (null == this._evictionCursor) {
            this._evictionCursor = this._pool.cursor(this._lifo ? this._pool.size() : 0);
         }
      }

      int i = 0;

      for(int m = this.getNumTests(); i < m; ++i) {
         GenericKeyedObjectPool.ObjectTimestampPair pair;
         synchronized(this) {
            if (this._lifo && !this._evictionCursor.hasPrevious() || !this._lifo && !this._evictionCursor.hasNext()) {
               this._evictionCursor.close();
               this._evictionCursor = this._pool.cursor(this._lifo ? this._pool.size() : 0);
            }

            pair = this._lifo ? (GenericKeyedObjectPool.ObjectTimestampPair)this._evictionCursor.previous() : (GenericKeyedObjectPool.ObjectTimestampPair)this._evictionCursor.next();
            this._evictionCursor.remove();
            ++this._numInternalProcessing;
         }

         boolean removeObject = false;
         long idleTimeMilis = System.currentTimeMillis() - pair.tstamp;
         if (this.getMinEvictableIdleTimeMillis() > 0L && idleTimeMilis > this.getMinEvictableIdleTimeMillis()) {
            removeObject = true;
         } else if (this.getSoftMinEvictableIdleTimeMillis() > 0L && idleTimeMilis > this.getSoftMinEvictableIdleTimeMillis() && this.getNumIdle() + 1 > this.getMinIdle()) {
            removeObject = true;
         }

         if (this.getTestWhileIdle() && !removeObject) {
            boolean active = false;

            try {
               this._factory.activateObject(pair.value);
               active = true;
            } catch (Exception var12) {
               removeObject = true;
            }

            if (active) {
               if (!this._factory.validateObject(pair.value)) {
                  removeObject = true;
               } else {
                  try {
                     this._factory.passivateObject(pair.value);
                  } catch (Exception var11) {
                     removeObject = true;
                  }
               }
            }
         }

         if (removeObject) {
            try {
               this._factory.destroyObject(pair.value);
            } catch (Exception var10) {
            }
         }

         synchronized(this) {
            if (!removeObject) {
               this._evictionCursor.add(pair);
               if (this._lifo) {
                  this._evictionCursor.previous();
               }
            }

            --this._numInternalProcessing;
         }
      }

      this.allocate();
   }

   private void ensureMinIdle() throws Exception {
      int objectDeficit = this.calculateDeficit(false);

      for(int j = 0; j < objectDeficit && this.calculateDeficit(true) > 0; ++j) {
         boolean var11 = false;

         try {
            var11 = true;
            this.addObject();
            var11 = false;
         } finally {
            if (var11) {
               synchronized(this) {
                  --this._numInternalProcessing;
               }

               this.allocate();
            }
         }

         synchronized(this) {
            --this._numInternalProcessing;
         }

         this.allocate();
      }

   }

   private synchronized int calculateDeficit(boolean incrementInternal) {
      int objectDeficit = this.getMinIdle() - this.getNumIdle();
      if (this._maxActive > 0) {
         int growLimit = Math.max(0, this.getMaxActive() - this.getNumActive() - this.getNumIdle() - this._numInternalProcessing);
         objectDeficit = Math.min(objectDeficit, growLimit);
      }

      if (incrementInternal && objectDeficit > 0) {
         ++this._numInternalProcessing;
      }

      return objectDeficit;
   }

   public void addObject() throws Exception {
      this.assertOpen();
      if (this._factory == null) {
         throw new IllegalStateException("Cannot add objects without a factory.");
      } else {
         Object obj = this._factory.makeObject();

         try {
            this.assertOpen();
            this.addObjectToPool(obj, false);
         } catch (IllegalStateException var5) {
            try {
               this._factory.destroyObject(obj);
            } catch (Exception var4) {
            }

            throw var5;
         }
      }
   }

   protected synchronized void startEvictor(long delay) {
      if (null != this._evictor) {
         EvictionTimer.cancel(this._evictor);
         this._evictor = null;
      }

      if (delay > 0L) {
         this._evictor = new Evictor();
         EvictionTimer.schedule(this._evictor, delay, delay);
      }

   }

   synchronized String debugInfo() {
      StringBuffer buf = new StringBuffer();
      buf.append("Active: ").append(this.getNumActive()).append("\n");
      buf.append("Idle: ").append(this.getNumIdle()).append("\n");
      buf.append("Idle Objects:\n");
      Iterator it = this._pool.iterator();
      long time = System.currentTimeMillis();

      while(it.hasNext()) {
         GenericKeyedObjectPool.ObjectTimestampPair pair = (GenericKeyedObjectPool.ObjectTimestampPair)it.next();
         buf.append("\t").append(pair.value).append("\t").append(time - pair.tstamp).append("\n");
      }

      return buf.toString();
   }

   private int getNumTests() {
      return this._numTestsPerEvictionRun >= 0 ? Math.min(this._numTestsPerEvictionRun, this._pool.size()) : (int)Math.ceil((double)this._pool.size() / Math.abs((double)this._numTestsPerEvictionRun));
   }

   private static final class Latch {
      private GenericKeyedObjectPool.ObjectTimestampPair _pair;
      private boolean _mayCreate;

      private Latch() {
         this._mayCreate = false;
      }

      private synchronized GenericKeyedObjectPool.ObjectTimestampPair getPair() {
         return this._pair;
      }

      private synchronized void setPair(GenericKeyedObjectPool.ObjectTimestampPair pair) {
         this._pair = pair;
      }

      private synchronized boolean mayCreate() {
         return this._mayCreate;
      }

      private synchronized void setMayCreate(boolean mayCreate) {
         this._mayCreate = mayCreate;
      }

      private synchronized void reset() {
         this._pair = null;
         this._mayCreate = false;
      }

      // $FF: synthetic method
      Latch(Object x0) {
         this();
      }
   }

   public static class Config {
      public int maxIdle = 8;
      public int minIdle = 0;
      public int maxActive = 8;
      public long maxWait = -1L;
      public byte whenExhaustedAction = 1;
      public boolean testOnBorrow = false;
      public boolean testOnReturn = false;
      public boolean testWhileIdle = false;
      public long timeBetweenEvictionRunsMillis = -1L;
      public int numTestsPerEvictionRun = 3;
      public long minEvictableIdleTimeMillis = 1800000L;
      public long softMinEvictableIdleTimeMillis = -1L;
      public boolean lifo = true;
   }

   private class Evictor extends TimerTask {
      private Evictor() {
      }

      public void run() {
         try {
            GenericObjectPool.this.evict();
         } catch (Exception var3) {
         } catch (OutOfMemoryError var4) {
            var4.printStackTrace(System.err);
         }

         try {
            GenericObjectPool.this.ensureMinIdle();
         } catch (Exception var2) {
         }

      }

      // $FF: synthetic method
      Evictor(Object x1) {
         this();
      }
   }
}
