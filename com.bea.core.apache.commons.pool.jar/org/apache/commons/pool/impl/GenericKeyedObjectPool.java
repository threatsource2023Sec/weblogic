package org.apache.commons.pool.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimerTask;
import java.util.TreeMap;
import org.apache.commons.pool.BaseKeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.PoolUtils;

public class GenericKeyedObjectPool extends BaseKeyedObjectPool implements KeyedObjectPool {
   public static final byte WHEN_EXHAUSTED_FAIL = 0;
   public static final byte WHEN_EXHAUSTED_BLOCK = 1;
   public static final byte WHEN_EXHAUSTED_GROW = 2;
   public static final int DEFAULT_MAX_IDLE = 8;
   public static final int DEFAULT_MAX_ACTIVE = 8;
   public static final int DEFAULT_MAX_TOTAL = -1;
   public static final byte DEFAULT_WHEN_EXHAUSTED_ACTION = 1;
   public static final long DEFAULT_MAX_WAIT = -1L;
   public static final boolean DEFAULT_TEST_ON_BORROW = false;
   public static final boolean DEFAULT_TEST_ON_RETURN = false;
   public static final boolean DEFAULT_TEST_WHILE_IDLE = false;
   public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1L;
   public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = 3;
   public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1800000L;
   public static final int DEFAULT_MIN_IDLE = 0;
   public static final boolean DEFAULT_LIFO = true;
   private int _maxIdle;
   private volatile int _minIdle;
   private int _maxActive;
   private int _maxTotal;
   private long _maxWait;
   private byte _whenExhaustedAction;
   private volatile boolean _testOnBorrow;
   private volatile boolean _testOnReturn;
   private boolean _testWhileIdle;
   private long _timeBetweenEvictionRunsMillis;
   private int _numTestsPerEvictionRun;
   private long _minEvictableIdleTimeMillis;
   private Map _poolMap;
   private int _totalActive;
   private int _totalIdle;
   private int _totalInternalProcessing;
   private KeyedPoolableObjectFactory _factory;
   private Evictor _evictor;
   private CursorableLinkedList _poolList;
   private CursorableLinkedList.Cursor _evictionCursor;
   private CursorableLinkedList.Cursor _evictionKeyCursor;
   private boolean _lifo;
   private LinkedList _allocationQueue;

   public GenericKeyedObjectPool() {
      this((KeyedPoolableObjectFactory)null, 8, (byte)1, -1L, 8, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory) {
      this(factory, 8, (byte)1, -1L, 8, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, Config config) {
      this(factory, config.maxActive, config.whenExhaustedAction, config.maxWait, config.maxIdle, config.maxTotal, config.minIdle, config.testOnBorrow, config.testOnReturn, config.timeBetweenEvictionRunsMillis, config.numTestsPerEvictionRun, config.minEvictableIdleTimeMillis, config.testWhileIdle, config.lifo);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive) {
      this(factory, maxActive, (byte)1, -1L, 8, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, -1, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, 0, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, true);
   }

   public GenericKeyedObjectPool(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, boolean lifo) {
      this._maxIdle = 8;
      this._minIdle = 0;
      this._maxActive = 8;
      this._maxTotal = -1;
      this._maxWait = -1L;
      this._whenExhaustedAction = 1;
      this._testOnBorrow = false;
      this._testOnReturn = false;
      this._testWhileIdle = false;
      this._timeBetweenEvictionRunsMillis = -1L;
      this._numTestsPerEvictionRun = 3;
      this._minEvictableIdleTimeMillis = 1800000L;
      this._poolMap = null;
      this._totalActive = 0;
      this._totalIdle = 0;
      this._totalInternalProcessing = 0;
      this._factory = null;
      this._evictor = null;
      this._poolList = null;
      this._evictionCursor = null;
      this._evictionKeyCursor = null;
      this._lifo = true;
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
            this._maxTotal = maxTotal;
            this._minIdle = minIdle;
            this._testOnBorrow = testOnBorrow;
            this._testOnReturn = testOnReturn;
            this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
            this._numTestsPerEvictionRun = numTestsPerEvictionRun;
            this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
            this._testWhileIdle = testWhileIdle;
            this._poolMap = new HashMap();
            this._poolList = new CursorableLinkedList();
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

   public synchronized int getMaxTotal() {
      return this._maxTotal;
   }

   public void setMaxTotal(int maxTotal) {
      synchronized(this) {
         this._maxTotal = maxTotal;
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

   public void setMinIdle(int poolSize) {
      this._minIdle = poolSize;
   }

   public int getMinIdle() {
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

   public synchronized boolean getTestWhileIdle() {
      return this._testWhileIdle;
   }

   public synchronized void setTestWhileIdle(boolean testWhileIdle) {
      this._testWhileIdle = testWhileIdle;
   }

   public synchronized void setConfig(Config conf) {
      this.setMaxIdle(conf.maxIdle);
      this.setMaxActive(conf.maxActive);
      this.setMaxTotal(conf.maxTotal);
      this.setMinIdle(conf.minIdle);
      this.setMaxWait(conf.maxWait);
      this.setWhenExhaustedAction(conf.whenExhaustedAction);
      this.setTestOnBorrow(conf.testOnBorrow);
      this.setTestOnReturn(conf.testOnReturn);
      this.setTestWhileIdle(conf.testWhileIdle);
      this.setNumTestsPerEvictionRun(conf.numTestsPerEvictionRun);
      this.setMinEvictableIdleTimeMillis(conf.minEvictableIdleTimeMillis);
      this.setTimeBetweenEvictionRunsMillis(conf.timeBetweenEvictionRunsMillis);
   }

   public synchronized boolean getLifo() {
      return this._lifo;
   }

   public synchronized void setLifo(boolean lifo) {
      this._lifo = lifo;
   }

   public Object borrowObject(Object key) throws Exception {
      long starttime = System.currentTimeMillis();
      Latch latch = new Latch(key);
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

         if (null == latch.getPair() && !latch.mayCreate()) {
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
                  } catch (InterruptedException var52) {
                     boolean doAllocate = false;
                     synchronized(this) {
                        if (latch.getPair() == null && !latch.mayCreate()) {
                           this._allocationQueue.remove(latch);
                        } else if (latch.getPair() == null && latch.mayCreate()) {
                           latch.getPool().decrementInternalProcessingCount();
                           doAllocate = true;
                        } else {
                           latch.getPool().decrementInternalProcessingCount();
                           latch.getPool().incrementActiveCount();
                           this.returnObject(latch.getkey(), latch.getPair().getValue());
                        }
                     }

                     if (doAllocate) {
                        this.allocate();
                     }

                     Thread.currentThread().interrupt();
                     throw var52;
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
                        latch.getPool().incrementInternalProcessingCount();
                     }
                     break;
                  }
               default:
                  throw new IllegalArgumentException("whenExhaustedAction " + whenExhaustedAction + " not recognized.");
            }
         }

         boolean newlyCreated = false;
         if (null == latch.getPair()) {
            boolean var37 = false;

            try {
               var37 = true;
               Object obj = this._factory.makeObject(key);
               latch.setPair(new ObjectTimestampPair(obj));
               newlyCreated = true;
               var37 = false;
            } finally {
               if (var37) {
                  if (!newlyCreated) {
                     synchronized(this) {
                        latch.getPool().decrementInternalProcessingCount();
                     }

                     this.allocate();
                  }

               }
            }

            if (!newlyCreated) {
               synchronized(this) {
                  latch.getPool().decrementInternalProcessingCount();
               }

               this.allocate();
            }
         }

         try {
            this._factory.activateObject(key, latch.getPair().value);
            if (this._testOnBorrow && !this._factory.validateObject(key, latch.getPair().value)) {
               throw new Exception("ValidateObject failed");
            }

            synchronized(this) {
               latch.getPool().decrementInternalProcessingCount();
               latch.getPool().incrementActiveCount();
            }

            return latch.getPair().value;
         } catch (Throwable var48) {
            PoolUtils.checkRethrow(var48);

            try {
               this._factory.destroyObject(key, latch.getPair().value);
            } catch (Throwable var43) {
               PoolUtils.checkRethrow(var43);
            }

            synchronized(this) {
               latch.getPool().decrementInternalProcessingCount();
               if (!newlyCreated) {
                  latch.reset();
                  this._allocationQueue.add(0, latch);
               }
            }

            this.allocate();
            if (newlyCreated) {
               throw new NoSuchElementException("Could not create a validated object, cause: " + var48.getMessage());
            }
         }
      }
   }

   private void allocate() {
      boolean clearOldest = false;
      synchronized(this) {
         if (this.isClosed()) {
            return;
         }

         Iterator allocationQueueIter = this._allocationQueue.iterator();

         label80:
         while(true) {
            while(true) {
               if (!allocationQueueIter.hasNext()) {
                  break label80;
               }

               Latch latch = (Latch)allocationQueueIter.next();
               ObjectQueue pool = (ObjectQueue)this._poolMap.get(latch.getkey());
               if (null == pool) {
                  pool = new ObjectQueue();
                  this._poolMap.put(latch.getkey(), pool);
                  this._poolList.add(latch.getkey());
               }

               latch.setPool(pool);
               if (!pool.queue.isEmpty()) {
                  allocationQueueIter.remove();
                  latch.setPair((ObjectTimestampPair)pool.queue.removeFirst());
                  pool.incrementInternalProcessingCount();
                  --this._totalIdle;
                  synchronized(latch) {
                     latch.notify();
                  }
               } else {
                  if (this._maxTotal > 0 && this._totalActive + this._totalIdle + this._totalInternalProcessing >= this._maxTotal) {
                     clearOldest = true;
                     break label80;
                  }

                  if (this._maxActive >= 0 && pool.activeCount + pool.internalProcessingCount >= this._maxActive || this._maxTotal >= 0 && this._totalActive + this._totalIdle + this._totalInternalProcessing >= this._maxTotal) {
                     if (this._maxActive < 0) {
                        break label80;
                     }
                  } else {
                     allocationQueueIter.remove();
                     latch.setMayCreate(true);
                     pool.incrementInternalProcessingCount();
                     synchronized(latch) {
                        latch.notify();
                     }
                  }
               }
            }
         }
      }

      if (clearOldest) {
         this.clearOldest();
      }

   }

   public void clear() {
      Map toDestroy = new HashMap();
      synchronized(this) {
         Iterator it = this._poolMap.keySet().iterator();

         while(true) {
            if (!it.hasNext()) {
               break;
            }

            Object key = it.next();
            ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
            List objects = new ArrayList();
            objects.addAll(pool.queue);
            toDestroy.put(key, objects);
            it.remove();
            this._poolList.remove(key);
            this._totalIdle -= pool.queue.size();
            this._totalInternalProcessing += pool.queue.size();
            pool.queue.clear();
         }
      }

      this.destroy(toDestroy, this._factory);
   }

   public void clearOldest() {
      Map toDestroy = new HashMap();
      Map map = new TreeMap();
      synchronized(this) {
         Iterator keyiter = this._poolMap.keySet().iterator();

         while(keyiter.hasNext()) {
            Object key = keyiter.next();
            List list = ((ObjectQueue)this._poolMap.get(key)).queue;
            Iterator it = list.iterator();

            while(it.hasNext()) {
               map.put(it.next(), key);
            }
         }

         Set setPairKeys = map.entrySet();
         int itemsToRemove = (int)((double)map.size() * 0.15) + 1;
         Iterator iter = setPairKeys.iterator();

         while(true) {
            if (!iter.hasNext() || itemsToRemove <= 0) {
               break;
            }

            Map.Entry entry = (Map.Entry)iter.next();
            Object key = entry.getValue();
            ObjectTimestampPair pairTimeStamp = (ObjectTimestampPair)entry.getKey();
            ObjectQueue objectQueue = (ObjectQueue)this._poolMap.get(key);
            List list = objectQueue.queue;
            list.remove(pairTimeStamp);
            if (toDestroy.containsKey(key)) {
               ((List)toDestroy.get(key)).add(pairTimeStamp);
            } else {
               List listForKey = new ArrayList();
               listForKey.add(pairTimeStamp);
               toDestroy.put(key, listForKey);
            }

            objectQueue.incrementInternalProcessingCount();
            --this._totalIdle;
            --itemsToRemove;
         }
      }

      this.destroy(toDestroy, this._factory);
   }

   public void clear(Object key) {
      Map toDestroy = new HashMap();
      synchronized(this) {
         ObjectQueue pool = (ObjectQueue)this._poolMap.remove(key);
         if (pool == null) {
            return;
         }

         this._poolList.remove(key);
         List objects = new ArrayList();
         objects.addAll(pool.queue);
         toDestroy.put(key, objects);
         this._totalIdle -= pool.queue.size();
         this._totalInternalProcessing += pool.queue.size();
         pool.queue.clear();
      }

      this.destroy(toDestroy, this._factory);
   }

   private void destroy(Map m, KeyedPoolableObjectFactory factory) {
      Iterator entries = m.entrySet().iterator();

      while(entries.hasNext()) {
         Map.Entry entry = (Map.Entry)entries.next();
         Object key = entry.getKey();
         List c = (List)entry.getValue();
         Iterator it = c.iterator();

         while(it.hasNext()) {
            try {
               factory.destroyObject(key, ((ObjectTimestampPair)it.next()).value);
            } catch (Exception var20) {
            } finally {
               synchronized(this) {
                  ObjectQueue objectQueue = (ObjectQueue)this._poolMap.get(key);
                  if (objectQueue != null) {
                     objectQueue.decrementInternalProcessingCount();
                     if (objectQueue.internalProcessingCount == 0 && objectQueue.activeCount == 0 && objectQueue.queue.isEmpty()) {
                        this._poolMap.remove(key);
                        this._poolList.remove(key);
                     }
                  } else {
                     --this._totalInternalProcessing;
                  }
               }

               this.allocate();
            }
         }
      }

   }

   public synchronized int getNumActive() {
      return this._totalActive;
   }

   public synchronized int getNumIdle() {
      return this._totalIdle;
   }

   public synchronized int getNumActive(Object key) {
      ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
      return pool != null ? pool.activeCount : 0;
   }

   public synchronized int getNumIdle(Object key) {
      ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
      return pool != null ? pool.queue.size() : 0;
   }

   public void returnObject(Object key, Object obj) throws Exception {
      try {
         this.addObjectToPool(key, obj, true);
      } catch (Exception var9) {
         if (this._factory != null) {
            try {
               this._factory.destroyObject(key, obj);
            } catch (Exception var7) {
            }

            ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
            if (pool != null) {
               synchronized(this) {
                  pool.decrementActiveCount();
                  if (pool.queue.isEmpty() && pool.activeCount == 0 && pool.internalProcessingCount == 0) {
                     this._poolMap.remove(key);
                     this._poolList.remove(key);
                  }
               }

               this.allocate();
            }
         }
      }

   }

   private void addObjectToPool(Object key, Object obj, boolean decrementNumActive) throws Exception {
      boolean success = true;
      if (this._testOnReturn && !this._factory.validateObject(key, obj)) {
         success = false;
      } else {
         this._factory.passivateObject(key, obj);
      }

      boolean shouldDestroy = !success;
      boolean doAllocate = false;
      ObjectQueue pool;
      synchronized(this) {
         pool = (ObjectQueue)this._poolMap.get(key);
         if (null == pool) {
            pool = new ObjectQueue();
            this._poolMap.put(key, pool);
            this._poolList.add(key);
         }

         if (this.isClosed()) {
            shouldDestroy = true;
         } else if (this._maxIdle >= 0 && pool.queue.size() >= this._maxIdle) {
            shouldDestroy = true;
         } else if (success) {
            if (this._lifo) {
               pool.queue.addFirst(new ObjectTimestampPair(obj));
            } else {
               pool.queue.addLast(new ObjectTimestampPair(obj));
            }

            ++this._totalIdle;
            if (decrementNumActive) {
               pool.decrementActiveCount();
            }

            doAllocate = true;
         }
      }

      if (doAllocate) {
         this.allocate();
      }

      if (shouldDestroy) {
         try {
            this._factory.destroyObject(key, obj);
         } catch (Exception var11) {
         }

         if (decrementNumActive) {
            synchronized(this) {
               pool.decrementActiveCount();
               if (pool.queue.isEmpty() && pool.activeCount == 0 && pool.internalProcessingCount == 0) {
                  this._poolMap.remove(key);
                  this._poolList.remove(key);
               }
            }

            this.allocate();
         }
      }

   }

   public void invalidateObject(Object key, Object obj) throws Exception {
      try {
         this._factory.destroyObject(key, obj);
      } finally {
         synchronized(this) {
            ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
            if (null == pool) {
               pool = new ObjectQueue();
               this._poolMap.put(key, pool);
               this._poolList.add(key);
            }

            pool.decrementActiveCount();
         }

         this.allocate();
      }

   }

   public void addObject(Object key) throws Exception {
      this.assertOpen();
      if (this._factory == null) {
         throw new IllegalStateException("Cannot add objects without a factory.");
      } else {
         Object obj = this._factory.makeObject(key);

         try {
            this.assertOpen();
            this.addObjectToPool(key, obj, false);
         } catch (IllegalStateException var6) {
            try {
               this._factory.destroyObject(key, obj);
            } catch (Exception var5) {
            }

            throw var6;
         }
      }
   }

   public synchronized void preparePool(Object key, boolean populateImmediately) {
      ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
      if (null == pool) {
         pool = new ObjectQueue();
         this._poolMap.put(key, pool);
         this._poolList.add(key);
      }

      if (populateImmediately) {
         try {
            this.ensureMinIdle(key);
         } catch (Exception var5) {
         }
      }

   }

   public void close() throws Exception {
      super.close();
      synchronized(this) {
         this.clear();
         if (null != this._evictionCursor) {
            this._evictionCursor.close();
            this._evictionCursor = null;
         }

         if (null != this._evictionKeyCursor) {
            this._evictionKeyCursor.close();
            this._evictionKeyCursor = null;
         }

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
   public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException {
      Map toDestroy = new HashMap();
      KeyedPoolableObjectFactory oldFactory = this._factory;
      synchronized(this) {
         this.assertOpen();
         if (0 < this.getNumActive()) {
            throw new IllegalStateException("Objects are already active");
         }

         Iterator it = this._poolMap.keySet().iterator();

         while(true) {
            if (!it.hasNext()) {
               this._factory = factory;
               break;
            }

            Object key = it.next();
            ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
            if (pool != null) {
               List objects = new ArrayList();
               objects.addAll(pool.queue);
               toDestroy.put(key, objects);
               it.remove();
               this._poolList.remove(key);
               this._totalIdle -= pool.queue.size();
               this._totalInternalProcessing += pool.queue.size();
               pool.queue.clear();
            }
         }
      }

      this.destroy(toDestroy, oldFactory);
   }

   public void evict() throws Exception {
      Object key = null;
      boolean testWhileIdle;
      long minEvictableIdleTimeMillis;
      synchronized(this) {
         testWhileIdle = this._testWhileIdle;
         minEvictableIdleTimeMillis = this._minEvictableIdleTimeMillis;
         if (this._evictionKeyCursor != null && this._evictionKeyCursor._lastReturned != null) {
            key = this._evictionKeyCursor._lastReturned.value();
         }
      }

      int i = 0;

      for(int m = this.getNumTests(); i < m; ++i) {
         ObjectTimestampPair pair;
         synchronized(this) {
            if (this._poolMap == null || this._poolMap.size() == 0) {
               continue;
            }

            if (null == this._evictionKeyCursor) {
               this.resetEvictionKeyCursor();
               key = null;
            }

            if (null == this._evictionCursor) {
               if (this._evictionKeyCursor.hasNext()) {
                  key = this._evictionKeyCursor.next();
                  this.resetEvictionObjectCursor(key);
               } else {
                  this.resetEvictionKeyCursor();
                  if (this._evictionKeyCursor != null && this._evictionKeyCursor.hasNext()) {
                     key = this._evictionKeyCursor.next();
                     this.resetEvictionObjectCursor(key);
                  }
               }
            }

            if (this._evictionCursor == null) {
               continue;
            }

            if ((this._lifo && !this._evictionCursor.hasPrevious() || !this._lifo && !this._evictionCursor.hasNext()) && this._evictionKeyCursor != null) {
               if (this._evictionKeyCursor.hasNext()) {
                  key = this._evictionKeyCursor.next();
                  this.resetEvictionObjectCursor(key);
               } else {
                  this.resetEvictionKeyCursor();
                  if (this._evictionKeyCursor != null && this._evictionKeyCursor.hasNext()) {
                     key = this._evictionKeyCursor.next();
                     this.resetEvictionObjectCursor(key);
                  }
               }
            }

            if (this._lifo && !this._evictionCursor.hasPrevious() || !this._lifo && !this._evictionCursor.hasNext()) {
               continue;
            }

            pair = this._lifo ? (ObjectTimestampPair)this._evictionCursor.previous() : (ObjectTimestampPair)this._evictionCursor.next();
            this._evictionCursor.remove();
            ObjectQueue objectQueue = (ObjectQueue)this._poolMap.get(key);
            objectQueue.incrementInternalProcessingCount();
            --this._totalIdle;
         }

         boolean removeObject = false;
         if (minEvictableIdleTimeMillis > 0L && System.currentTimeMillis() - pair.tstamp > minEvictableIdleTimeMillis) {
            removeObject = true;
         }

         if (testWhileIdle && !removeObject) {
            boolean active = false;

            try {
               this._factory.activateObject(key, pair.value);
               active = true;
            } catch (Exception var15) {
               removeObject = true;
            }

            if (active) {
               if (!this._factory.validateObject(key, pair.value)) {
                  removeObject = true;
               } else {
                  try {
                     this._factory.passivateObject(key, pair.value);
                  } catch (Exception var14) {
                     removeObject = true;
                  }
               }
            }
         }

         if (removeObject) {
            try {
               this._factory.destroyObject(key, pair.value);
            } catch (Exception var13) {
            }
         }

         synchronized(this) {
            ObjectQueue objectQueue = (ObjectQueue)this._poolMap.get(key);
            objectQueue.decrementInternalProcessingCount();
            if (removeObject) {
               if (objectQueue.queue.isEmpty() && objectQueue.activeCount == 0 && objectQueue.internalProcessingCount == 0) {
                  this._poolMap.remove(key);
                  this._poolList.remove(key);
               }
            } else {
               this._evictionCursor.add(pair);
               ++this._totalIdle;
               if (this._lifo) {
                  this._evictionCursor.previous();
               }
            }
         }
      }

      this.allocate();
   }

   private void resetEvictionKeyCursor() {
      if (this._evictionKeyCursor != null) {
         this._evictionKeyCursor.close();
      }

      this._evictionKeyCursor = this._poolList.cursor();
      if (null != this._evictionCursor) {
         this._evictionCursor.close();
         this._evictionCursor = null;
      }

   }

   private void resetEvictionObjectCursor(Object key) {
      if (this._evictionCursor != null) {
         this._evictionCursor.close();
      }

      if (this._poolMap != null) {
         ObjectQueue pool = (ObjectQueue)this._poolMap.get(key);
         if (pool != null) {
            CursorableLinkedList queue = pool.queue;
            this._evictionCursor = queue.cursor(this._lifo ? queue.size() : 0);
         }

      }
   }

   private void ensureMinIdle() throws Exception {
      if (this._minIdle > 0) {
         Object[] keysCopy;
         synchronized(this) {
            keysCopy = this._poolMap.keySet().toArray();
         }

         for(int i = 0; i < keysCopy.length; ++i) {
            this.ensureMinIdle(keysCopy[i]);
         }
      }

   }

   private void ensureMinIdle(Object key) throws Exception {
      ObjectQueue pool;
      synchronized(this) {
         pool = (ObjectQueue)this._poolMap.get(key);
      }

      if (pool != null) {
         int objectDeficit = this.calculateDeficit(pool, false);

         for(int i = 0; i < objectDeficit && this.calculateDeficit(pool, true) > 0; ++i) {
            try {
               this.addObject(key);
            } finally {
               synchronized(this) {
                  pool.decrementInternalProcessingCount();
               }

               this.allocate();
            }
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
      Iterator it = this._poolMap.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         buf.append("\t").append(key).append(" ").append(this._poolMap.get(key)).append("\n");
      }

      return buf.toString();
   }

   private synchronized int getNumTests() {
      return this._numTestsPerEvictionRun >= 0 ? Math.min(this._numTestsPerEvictionRun, this._totalIdle) : (int)Math.ceil((double)this._totalIdle / Math.abs((double)this._numTestsPerEvictionRun));
   }

   private synchronized int calculateDeficit(ObjectQueue pool, boolean incrementInternal) {
      int objectDefecit = false;
      int objectDefecit = this.getMinIdle() - pool.queue.size();
      int growLimit;
      if (this.getMaxActive() > 0) {
         growLimit = Math.max(0, this.getMaxActive() - pool.activeCount - pool.queue.size() - pool.internalProcessingCount);
         objectDefecit = Math.min(objectDefecit, growLimit);
      }

      if (this.getMaxTotal() > 0) {
         growLimit = Math.max(0, this.getMaxTotal() - this.getNumActive() - this.getNumIdle() - this._totalInternalProcessing);
         objectDefecit = Math.min(objectDefecit, growLimit);
      }

      if (incrementInternal && objectDefecit > 0) {
         pool.incrementInternalProcessingCount();
      }

      return objectDefecit;
   }

   private final class Latch {
      private final Object _key;
      private ObjectQueue _pool;
      private ObjectTimestampPair _pair;
      private boolean _mayCreate;

      private Latch(Object key) {
         this._mayCreate = false;
         this._key = key;
      }

      private synchronized Object getkey() {
         return this._key;
      }

      private synchronized ObjectQueue getPool() {
         return this._pool;
      }

      private synchronized void setPool(ObjectQueue pool) {
         this._pool = pool;
      }

      private synchronized ObjectTimestampPair getPair() {
         return this._pair;
      }

      private synchronized void setPair(ObjectTimestampPair pair) {
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
      Latch(Object x1, Object x2) {
         this(x1);
      }
   }

   public static class Config {
      public int maxIdle = 8;
      public int maxActive = 8;
      public int maxTotal = -1;
      public int minIdle = 0;
      public long maxWait = -1L;
      public byte whenExhaustedAction = 1;
      public boolean testOnBorrow = false;
      public boolean testOnReturn = false;
      public boolean testWhileIdle = false;
      public long timeBetweenEvictionRunsMillis = -1L;
      public int numTestsPerEvictionRun = 3;
      public long minEvictableIdleTimeMillis = 1800000L;
      public boolean lifo = true;
   }

   private class Evictor extends TimerTask {
      private Evictor() {
      }

      public void run() {
         try {
            GenericKeyedObjectPool.this.evict();
         } catch (Exception var3) {
         } catch (OutOfMemoryError var4) {
            var4.printStackTrace(System.err);
         }

         try {
            GenericKeyedObjectPool.this.ensureMinIdle();
         } catch (Exception var2) {
         }

      }

      // $FF: synthetic method
      Evictor(Object x1) {
         this();
      }
   }

   static class ObjectTimestampPair implements Comparable {
      /** @deprecated */
      @Deprecated
      Object value;
      /** @deprecated */
      @Deprecated
      long tstamp;

      ObjectTimestampPair(Object val) {
         this(val, System.currentTimeMillis());
      }

      ObjectTimestampPair(Object val, long time) {
         this.value = val;
         this.tstamp = time;
      }

      public String toString() {
         return this.value + ";" + this.tstamp;
      }

      public int compareTo(Object obj) {
         return this.compareTo((ObjectTimestampPair)obj);
      }

      public int compareTo(ObjectTimestampPair other) {
         long tstampdiff = this.tstamp - other.tstamp;
         return tstampdiff == 0L ? System.identityHashCode(this) - System.identityHashCode(other) : (int)Math.min(Math.max(tstampdiff, -2147483648L), 2147483647L);
      }

      public Object getValue() {
         return this.value;
      }

      public long getTstamp() {
         return this.tstamp;
      }
   }

   private class ObjectQueue {
      private int activeCount;
      private final CursorableLinkedList queue;
      private int internalProcessingCount;

      private ObjectQueue() {
         this.activeCount = 0;
         this.queue = new CursorableLinkedList();
         this.internalProcessingCount = 0;
      }

      void incrementActiveCount() {
         synchronized(GenericKeyedObjectPool.this) {
            GenericKeyedObjectPool.this._totalActive++;
         }

         ++this.activeCount;
      }

      void decrementActiveCount() {
         synchronized(GenericKeyedObjectPool.this) {
            GenericKeyedObjectPool.this._totalActive--;
         }

         if (this.activeCount > 0) {
            --this.activeCount;
         }

      }

      void incrementInternalProcessingCount() {
         synchronized(GenericKeyedObjectPool.this) {
            GenericKeyedObjectPool.this._totalInternalProcessing++;
         }

         ++this.internalProcessingCount;
      }

      void decrementInternalProcessingCount() {
         synchronized(GenericKeyedObjectPool.this) {
            GenericKeyedObjectPool.this._totalInternalProcessing--;
         }

         --this.internalProcessingCount;
      }

      // $FF: synthetic method
      ObjectQueue(Object x1) {
         this();
      }
   }
}
