package org.apache.commons.pool;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

public final class PoolUtils {
   private static Timer MIN_IDLE_TIMER;

   public static void checkRethrow(Throwable t) {
      if (t instanceof ThreadDeath) {
         throw (ThreadDeath)t;
      } else if (t instanceof VirtualMachineError) {
         throw (VirtualMachineError)t;
      }
   }

   public static PoolableObjectFactory adapt(KeyedPoolableObjectFactory keyedFactory) throws IllegalArgumentException {
      return adapt(keyedFactory, new Object());
   }

   public static PoolableObjectFactory adapt(KeyedPoolableObjectFactory keyedFactory, Object key) throws IllegalArgumentException {
      return new PoolableObjectFactoryAdaptor(keyedFactory, key);
   }

   public static KeyedPoolableObjectFactory adapt(PoolableObjectFactory factory) throws IllegalArgumentException {
      return new KeyedPoolableObjectFactoryAdaptor(factory);
   }

   public static ObjectPool adapt(KeyedObjectPool keyedPool) throws IllegalArgumentException {
      return adapt(keyedPool, new Object());
   }

   public static ObjectPool adapt(KeyedObjectPool keyedPool, Object key) throws IllegalArgumentException {
      return new ObjectPoolAdaptor(keyedPool, key);
   }

   public static KeyedObjectPool adapt(ObjectPool pool) throws IllegalArgumentException {
      return new KeyedObjectPoolAdaptor(pool);
   }

   public static ObjectPool checkedPool(ObjectPool pool, Class type) {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else if (type == null) {
         throw new IllegalArgumentException("type must not be null.");
      } else {
         return new CheckedObjectPool(pool, type);
      }
   }

   public static KeyedObjectPool checkedPool(KeyedObjectPool keyedPool, Class type) {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (type == null) {
         throw new IllegalArgumentException("type must not be null.");
      } else {
         return new CheckedKeyedObjectPool(keyedPool, type);
      }
   }

   public static TimerTask checkMinIdle(ObjectPool pool, int minIdle, long period) throws IllegalArgumentException {
      if (pool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (minIdle < 0) {
         throw new IllegalArgumentException("minIdle must be non-negative.");
      } else {
         TimerTask task = new ObjectPoolMinIdleTimerTask(pool, minIdle);
         getMinIdleTimer().schedule(task, 0L, period);
         return task;
      }
   }

   public static TimerTask checkMinIdle(KeyedObjectPool keyedPool, Object key, int minIdle, long period) throws IllegalArgumentException {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (key == null) {
         throw new IllegalArgumentException("key must not be null.");
      } else if (minIdle < 0) {
         throw new IllegalArgumentException("minIdle must be non-negative.");
      } else {
         TimerTask task = new KeyedObjectPoolMinIdleTimerTask(keyedPool, key, minIdle);
         getMinIdleTimer().schedule(task, 0L, period);
         return task;
      }
   }

   public static Map checkMinIdle(KeyedObjectPool keyedPool, Collection keys, int minIdle, long period) throws IllegalArgumentException {
      if (keys == null) {
         throw new IllegalArgumentException("keys must not be null.");
      } else {
         Map tasks = new HashMap(keys.size());
         Iterator iter = keys.iterator();

         while(iter.hasNext()) {
            Object key = iter.next();
            TimerTask task = checkMinIdle(keyedPool, key, minIdle, period);
            tasks.put(key, task);
         }

         return tasks;
      }
   }

   public static void prefill(ObjectPool pool, int count) throws Exception, IllegalArgumentException {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else {
         for(int i = 0; i < count; ++i) {
            pool.addObject();
         }

      }
   }

   public static void prefill(KeyedObjectPool keyedPool, Object key, int count) throws Exception, IllegalArgumentException {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (key == null) {
         throw new IllegalArgumentException("key must not be null.");
      } else {
         for(int i = 0; i < count; ++i) {
            keyedPool.addObject(key);
         }

      }
   }

   public static void prefill(KeyedObjectPool keyedPool, Collection keys, int count) throws Exception, IllegalArgumentException {
      if (keys == null) {
         throw new IllegalArgumentException("keys must not be null.");
      } else {
         Iterator iter = keys.iterator();

         while(iter.hasNext()) {
            prefill(keyedPool, iter.next(), count);
         }

      }
   }

   public static ObjectPool synchronizedPool(ObjectPool pool) {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else {
         return new SynchronizedObjectPool(pool);
      }
   }

   public static KeyedObjectPool synchronizedPool(KeyedObjectPool keyedPool) {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else {
         return new SynchronizedKeyedObjectPool(keyedPool);
      }
   }

   public static PoolableObjectFactory synchronizedPoolableFactory(PoolableObjectFactory factory) {
      return new SynchronizedPoolableObjectFactory(factory);
   }

   public static KeyedPoolableObjectFactory synchronizedPoolableFactory(KeyedPoolableObjectFactory keyedFactory) {
      return new SynchronizedKeyedPoolableObjectFactory(keyedFactory);
   }

   public static ObjectPool erodingPool(ObjectPool pool) {
      return erodingPool(pool, 1.0F);
   }

   public static ObjectPool erodingPool(ObjectPool pool, float factor) {
      if (pool == null) {
         throw new IllegalArgumentException("pool must not be null.");
      } else if (factor <= 0.0F) {
         throw new IllegalArgumentException("factor must be positive.");
      } else {
         return new ErodingObjectPool(pool, factor);
      }
   }

   public static KeyedObjectPool erodingPool(KeyedObjectPool keyedPool) {
      return erodingPool(keyedPool, 1.0F);
   }

   public static KeyedObjectPool erodingPool(KeyedObjectPool keyedPool, float factor) {
      return erodingPool(keyedPool, factor, false);
   }

   public static KeyedObjectPool erodingPool(KeyedObjectPool keyedPool, float factor, boolean perKey) {
      if (keyedPool == null) {
         throw new IllegalArgumentException("keyedPool must not be null.");
      } else if (factor <= 0.0F) {
         throw new IllegalArgumentException("factor must be positive.");
      } else {
         return (KeyedObjectPool)(perKey ? new ErodingPerKeyKeyedObjectPool(keyedPool, factor) : new ErodingKeyedObjectPool(keyedPool, factor));
      }
   }

   private static synchronized Timer getMinIdleTimer() {
      if (MIN_IDLE_TIMER == null) {
         MIN_IDLE_TIMER = new Timer(true);
      }

      return MIN_IDLE_TIMER;
   }

   private static class ErodingPerKeyKeyedObjectPool extends ErodingKeyedObjectPool {
      private final float factor;
      private final Map factors = Collections.synchronizedMap(new HashMap());

      public ErodingPerKeyKeyedObjectPool(KeyedObjectPool keyedPool, float factor) {
         super(keyedPool, (ErodingFactor)null);
         this.factor = factor;
      }

      protected int numIdle(Object key) {
         return this.getKeyedPool().getNumIdle(key);
      }

      protected ErodingFactor getErodingFactor(Object key) {
         ErodingFactor factor = (ErodingFactor)this.factors.get(key);
         if (factor == null) {
            factor = new ErodingFactor(this.factor);
            this.factors.put(key, factor);
         }

         return factor;
      }

      public String toString() {
         return "ErodingPerKeyKeyedObjectPool{factor=" + this.factor + ", keyedPool=" + this.getKeyedPool() + '}';
      }
   }

   private static class ErodingKeyedObjectPool implements KeyedObjectPool {
      private final KeyedObjectPool keyedPool;
      private final ErodingFactor erodingFactor;

      public ErodingKeyedObjectPool(KeyedObjectPool keyedPool, float factor) {
         this(keyedPool, new ErodingFactor(factor));
      }

      protected ErodingKeyedObjectPool(KeyedObjectPool keyedPool, ErodingFactor erodingFactor) {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.erodingFactor = erodingFactor;
         }
      }

      public Object borrowObject(Object key) throws Exception, NoSuchElementException, IllegalStateException {
         return this.keyedPool.borrowObject(key);
      }

      public void returnObject(Object key, Object obj) throws Exception {
         boolean discard = false;
         long now = System.currentTimeMillis();
         ErodingFactor factor = this.getErodingFactor(key);
         synchronized(this.keyedPool) {
            if (factor.getNextShrink() < now) {
               int numIdle = this.numIdle(key);
               if (numIdle > 0) {
                  discard = true;
               }

               factor.update(now, numIdle);
            }
         }

         try {
            if (discard) {
               this.keyedPool.invalidateObject(key, obj);
            } else {
               this.keyedPool.returnObject(key, obj);
            }
         } catch (Exception var10) {
         }

      }

      protected int numIdle(Object key) {
         return this.getKeyedPool().getNumIdle();
      }

      protected ErodingFactor getErodingFactor(Object key) {
         return this.erodingFactor;
      }

      public void invalidateObject(Object key, Object obj) {
         try {
            this.keyedPool.invalidateObject(key, obj);
         } catch (Exception var4) {
         }

      }

      public void addObject(Object key) throws Exception, IllegalStateException, UnsupportedOperationException {
         this.keyedPool.addObject(key);
      }

      public int getNumIdle() throws UnsupportedOperationException {
         return this.keyedPool.getNumIdle();
      }

      public int getNumIdle(Object key) throws UnsupportedOperationException {
         return this.keyedPool.getNumIdle(key);
      }

      public int getNumActive() throws UnsupportedOperationException {
         return this.keyedPool.getNumActive();
      }

      public int getNumActive(Object key) throws UnsupportedOperationException {
         return this.keyedPool.getNumActive(key);
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.keyedPool.clear();
      }

      public void clear(Object key) throws Exception, UnsupportedOperationException {
         this.keyedPool.clear(key);
      }

      public void close() {
         try {
            this.keyedPool.close();
         } catch (Exception var2) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         this.keyedPool.setFactory(factory);
      }

      protected KeyedObjectPool getKeyedPool() {
         return this.keyedPool;
      }

      public String toString() {
         return "ErodingKeyedObjectPool{erodingFactor=" + this.erodingFactor + ", keyedPool=" + this.keyedPool + '}';
      }
   }

   private static class ErodingObjectPool implements ObjectPool {
      private final ObjectPool pool;
      private final ErodingFactor factor;

      public ErodingObjectPool(ObjectPool pool, float factor) {
         this.pool = pool;
         this.factor = new ErodingFactor(factor);
      }

      public Object borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
         return this.pool.borrowObject();
      }

      public void returnObject(Object obj) {
         boolean discard = false;
         long now = System.currentTimeMillis();
         synchronized(this.pool) {
            if (this.factor.getNextShrink() < now) {
               int numIdle = this.pool.getNumIdle();
               if (numIdle > 0) {
                  discard = true;
               }

               this.factor.update(now, numIdle);
            }
         }

         try {
            if (discard) {
               this.pool.invalidateObject(obj);
            } else {
               this.pool.returnObject(obj);
            }
         } catch (Exception var8) {
         }

      }

      public void invalidateObject(Object obj) {
         try {
            this.pool.invalidateObject(obj);
         } catch (Exception var3) {
         }

      }

      public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
         this.pool.addObject();
      }

      public int getNumIdle() throws UnsupportedOperationException {
         return this.pool.getNumIdle();
      }

      public int getNumActive() throws UnsupportedOperationException {
         return this.pool.getNumActive();
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.pool.clear();
      }

      public void close() {
         try {
            this.pool.close();
         } catch (Exception var2) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         this.pool.setFactory(factory);
      }

      public String toString() {
         return "ErodingObjectPool{factor=" + this.factor + ", pool=" + this.pool + '}';
      }
   }

   private static class ErodingFactor {
      private final float factor;
      private transient volatile long nextShrink;
      private transient volatile int idleHighWaterMark;

      public ErodingFactor(float factor) {
         this.factor = factor;
         this.nextShrink = System.currentTimeMillis() + (long)(900000.0F * factor);
         this.idleHighWaterMark = 1;
      }

      public void update(int numIdle) {
         this.update(System.currentTimeMillis(), numIdle);
      }

      public void update(long now, int numIdle) {
         int idle = Math.max(0, numIdle);
         this.idleHighWaterMark = Math.max(idle, this.idleHighWaterMark);
         float maxInterval = 15.0F;
         float minutes = 15.0F + -14.0F / (float)this.idleHighWaterMark * (float)idle;
         this.nextShrink = now + (long)(minutes * 60000.0F * this.factor);
      }

      public long getNextShrink() {
         return this.nextShrink;
      }

      public String toString() {
         return "ErodingFactor{factor=" + this.factor + ", idleHighWaterMark=" + this.idleHighWaterMark + '}';
      }
   }

   private static class SynchronizedKeyedPoolableObjectFactory implements KeyedPoolableObjectFactory {
      private final Object lock;
      private final KeyedPoolableObjectFactory keyedFactory;

      SynchronizedKeyedPoolableObjectFactory(KeyedPoolableObjectFactory keyedFactory) throws IllegalArgumentException {
         if (keyedFactory == null) {
            throw new IllegalArgumentException("keyedFactory must not be null.");
         } else {
            this.keyedFactory = keyedFactory;
            this.lock = new Object();
         }
      }

      public Object makeObject(Object key) throws Exception {
         synchronized(this.lock) {
            return this.keyedFactory.makeObject(key);
         }
      }

      public void destroyObject(Object key, Object obj) throws Exception {
         synchronized(this.lock) {
            this.keyedFactory.destroyObject(key, obj);
         }
      }

      public boolean validateObject(Object key, Object obj) {
         synchronized(this.lock) {
            return this.keyedFactory.validateObject(key, obj);
         }
      }

      public void activateObject(Object key, Object obj) throws Exception {
         synchronized(this.lock) {
            this.keyedFactory.activateObject(key, obj);
         }
      }

      public void passivateObject(Object key, Object obj) throws Exception {
         synchronized(this.lock) {
            this.keyedFactory.passivateObject(key, obj);
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("SynchronizedKeyedPoolableObjectFactory");
         sb.append("{keyedFactory=").append(this.keyedFactory);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class SynchronizedPoolableObjectFactory implements PoolableObjectFactory {
      private final Object lock;
      private final PoolableObjectFactory factory;

      SynchronizedPoolableObjectFactory(PoolableObjectFactory factory) throws IllegalArgumentException {
         if (factory == null) {
            throw new IllegalArgumentException("factory must not be null.");
         } else {
            this.factory = factory;
            this.lock = new Object();
         }
      }

      public Object makeObject() throws Exception {
         synchronized(this.lock) {
            return this.factory.makeObject();
         }
      }

      public void destroyObject(Object obj) throws Exception {
         synchronized(this.lock) {
            this.factory.destroyObject(obj);
         }
      }

      public boolean validateObject(Object obj) {
         synchronized(this.lock) {
            return this.factory.validateObject(obj);
         }
      }

      public void activateObject(Object obj) throws Exception {
         synchronized(this.lock) {
            this.factory.activateObject(obj);
         }
      }

      public void passivateObject(Object obj) throws Exception {
         synchronized(this.lock) {
            this.factory.passivateObject(obj);
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("SynchronizedPoolableObjectFactory");
         sb.append("{factory=").append(this.factory);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class SynchronizedKeyedObjectPool implements KeyedObjectPool {
      private final Object lock;
      private final KeyedObjectPool keyedPool;

      SynchronizedKeyedObjectPool(KeyedObjectPool keyedPool) throws IllegalArgumentException {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.lock = new Object();
         }
      }

      public Object borrowObject(Object key) throws Exception, NoSuchElementException, IllegalStateException {
         synchronized(this.lock) {
            return this.keyedPool.borrowObject(key);
         }
      }

      public void returnObject(Object key, Object obj) {
         synchronized(this.lock) {
            try {
               this.keyedPool.returnObject(key, obj);
            } catch (Exception var6) {
            }

         }
      }

      public void invalidateObject(Object key, Object obj) {
         synchronized(this.lock) {
            try {
               this.keyedPool.invalidateObject(key, obj);
            } catch (Exception var6) {
            }

         }
      }

      public void addObject(Object key) throws Exception, IllegalStateException, UnsupportedOperationException {
         synchronized(this.lock) {
            this.keyedPool.addObject(key);
         }
      }

      public int getNumIdle(Object key) throws UnsupportedOperationException {
         synchronized(this.lock) {
            return this.keyedPool.getNumIdle(key);
         }
      }

      public int getNumActive(Object key) throws UnsupportedOperationException {
         synchronized(this.lock) {
            return this.keyedPool.getNumActive(key);
         }
      }

      public int getNumIdle() throws UnsupportedOperationException {
         synchronized(this.lock) {
            return this.keyedPool.getNumIdle();
         }
      }

      public int getNumActive() throws UnsupportedOperationException {
         synchronized(this.lock) {
            return this.keyedPool.getNumActive();
         }
      }

      public void clear() throws Exception, UnsupportedOperationException {
         synchronized(this.lock) {
            this.keyedPool.clear();
         }
      }

      public void clear(Object key) throws Exception, UnsupportedOperationException {
         synchronized(this.lock) {
            this.keyedPool.clear(key);
         }
      }

      public void close() {
         try {
            synchronized(this.lock) {
               this.keyedPool.close();
            }
         } catch (Exception var4) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         synchronized(this.lock) {
            this.keyedPool.setFactory(factory);
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("SynchronizedKeyedObjectPool");
         sb.append("{keyedPool=").append(this.keyedPool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class SynchronizedObjectPool implements ObjectPool {
      private final Object lock;
      private final ObjectPool pool;

      SynchronizedObjectPool(ObjectPool pool) throws IllegalArgumentException {
         if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
         } else {
            this.pool = pool;
            this.lock = new Object();
         }
      }

      public Object borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
         synchronized(this.lock) {
            return this.pool.borrowObject();
         }
      }

      public void returnObject(Object obj) {
         synchronized(this.lock) {
            try {
               this.pool.returnObject(obj);
            } catch (Exception var5) {
            }

         }
      }

      public void invalidateObject(Object obj) {
         synchronized(this.lock) {
            try {
               this.pool.invalidateObject(obj);
            } catch (Exception var5) {
            }

         }
      }

      public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
         synchronized(this.lock) {
            this.pool.addObject();
         }
      }

      public int getNumIdle() throws UnsupportedOperationException {
         synchronized(this.lock) {
            return this.pool.getNumIdle();
         }
      }

      public int getNumActive() throws UnsupportedOperationException {
         synchronized(this.lock) {
            return this.pool.getNumActive();
         }
      }

      public void clear() throws Exception, UnsupportedOperationException {
         synchronized(this.lock) {
            this.pool.clear();
         }
      }

      public void close() {
         try {
            synchronized(this.lock) {
               this.pool.close();
            }
         } catch (Exception var4) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         synchronized(this.lock) {
            this.pool.setFactory(factory);
         }
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("SynchronizedObjectPool");
         sb.append("{pool=").append(this.pool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class KeyedObjectPoolMinIdleTimerTask extends TimerTask {
      private final int minIdle;
      private final Object key;
      private final KeyedObjectPool keyedPool;

      KeyedObjectPoolMinIdleTimerTask(KeyedObjectPool keyedPool, Object key, int minIdle) throws IllegalArgumentException {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.key = key;
            this.minIdle = minIdle;
         }
      }

      public void run() {
         boolean success = false;

         try {
            if (this.keyedPool.getNumIdle(this.key) < this.minIdle) {
               this.keyedPool.addObject(this.key);
            }

            success = true;
         } catch (Exception var6) {
            this.cancel();
         } finally {
            if (!success) {
               this.cancel();
            }

         }

      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("KeyedObjectPoolMinIdleTimerTask");
         sb.append("{minIdle=").append(this.minIdle);
         sb.append(", key=").append(this.key);
         sb.append(", keyedPool=").append(this.keyedPool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class ObjectPoolMinIdleTimerTask extends TimerTask {
      private final int minIdle;
      private final ObjectPool pool;

      ObjectPoolMinIdleTimerTask(ObjectPool pool, int minIdle) throws IllegalArgumentException {
         if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
         } else {
            this.pool = pool;
            this.minIdle = minIdle;
         }
      }

      public void run() {
         boolean success = false;

         try {
            if (this.pool.getNumIdle() < this.minIdle) {
               this.pool.addObject();
            }

            success = true;
         } catch (Exception var6) {
            this.cancel();
         } finally {
            if (!success) {
               this.cancel();
            }

         }

      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("ObjectPoolMinIdleTimerTask");
         sb.append("{minIdle=").append(this.minIdle);
         sb.append(", pool=").append(this.pool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class CheckedKeyedObjectPool implements KeyedObjectPool {
      private final Class type;
      private final KeyedObjectPool keyedPool;

      CheckedKeyedObjectPool(KeyedObjectPool keyedPool, Class type) {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else if (type == null) {
            throw new IllegalArgumentException("type must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.type = type;
         }
      }

      public Object borrowObject(Object key) throws Exception, NoSuchElementException, IllegalStateException {
         Object obj = this.keyedPool.borrowObject(key);
         if (this.type.isInstance(obj)) {
            return obj;
         } else {
            throw new ClassCastException("Borrowed object for key: " + key + " is not of type: " + this.type.getName() + " was: " + obj);
         }
      }

      public void returnObject(Object key, Object obj) {
         if (this.type.isInstance(obj)) {
            try {
               this.keyedPool.returnObject(key, obj);
            } catch (Exception var4) {
            }

         } else {
            throw new ClassCastException("Returned object for key: " + key + " is not of type: " + this.type.getName() + " was: " + obj);
         }
      }

      public void invalidateObject(Object key, Object obj) {
         if (this.type.isInstance(obj)) {
            try {
               this.keyedPool.invalidateObject(key, obj);
            } catch (Exception var4) {
            }

         } else {
            throw new ClassCastException("Invalidated object for key: " + key + " is not of type: " + this.type.getName() + " was: " + obj);
         }
      }

      public void addObject(Object key) throws Exception, IllegalStateException, UnsupportedOperationException {
         this.keyedPool.addObject(key);
      }

      public int getNumIdle(Object key) throws UnsupportedOperationException {
         return this.keyedPool.getNumIdle(key);
      }

      public int getNumActive(Object key) throws UnsupportedOperationException {
         return this.keyedPool.getNumActive(key);
      }

      public int getNumIdle() throws UnsupportedOperationException {
         return this.keyedPool.getNumIdle();
      }

      public int getNumActive() throws UnsupportedOperationException {
         return this.keyedPool.getNumActive();
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.keyedPool.clear();
      }

      public void clear(Object key) throws Exception, UnsupportedOperationException {
         this.keyedPool.clear(key);
      }

      public void close() {
         try {
            this.keyedPool.close();
         } catch (Exception var2) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         this.keyedPool.setFactory(factory);
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("CheckedKeyedObjectPool");
         sb.append("{type=").append(this.type);
         sb.append(", keyedPool=").append(this.keyedPool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class CheckedObjectPool implements ObjectPool {
      private final Class type;
      private final ObjectPool pool;

      CheckedObjectPool(ObjectPool pool, Class type) {
         if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
         } else if (type == null) {
            throw new IllegalArgumentException("type must not be null.");
         } else {
            this.pool = pool;
            this.type = type;
         }
      }

      public Object borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
         Object obj = this.pool.borrowObject();
         if (this.type.isInstance(obj)) {
            return obj;
         } else {
            throw new ClassCastException("Borrowed object is not of type: " + this.type.getName() + " was: " + obj);
         }
      }

      public void returnObject(Object obj) {
         if (this.type.isInstance(obj)) {
            try {
               this.pool.returnObject(obj);
            } catch (Exception var3) {
            }

         } else {
            throw new ClassCastException("Returned object is not of type: " + this.type.getName() + " was: " + obj);
         }
      }

      public void invalidateObject(Object obj) {
         if (this.type.isInstance(obj)) {
            try {
               this.pool.invalidateObject(obj);
            } catch (Exception var3) {
            }

         } else {
            throw new ClassCastException("Invalidated object is not of type: " + this.type.getName() + " was: " + obj);
         }
      }

      public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
         this.pool.addObject();
      }

      public int getNumIdle() throws UnsupportedOperationException {
         return this.pool.getNumIdle();
      }

      public int getNumActive() throws UnsupportedOperationException {
         return this.pool.getNumActive();
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.pool.clear();
      }

      public void close() {
         try {
            this.pool.close();
         } catch (Exception var2) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         this.pool.setFactory(factory);
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("CheckedObjectPool");
         sb.append("{type=").append(this.type);
         sb.append(", pool=").append(this.pool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class KeyedObjectPoolAdaptor implements KeyedObjectPool {
      private final ObjectPool pool;

      KeyedObjectPoolAdaptor(ObjectPool pool) throws IllegalArgumentException {
         if (pool == null) {
            throw new IllegalArgumentException("pool must not be null.");
         } else {
            this.pool = pool;
         }
      }

      public Object borrowObject(Object key) throws Exception, NoSuchElementException, IllegalStateException {
         return this.pool.borrowObject();
      }

      public void returnObject(Object key, Object obj) {
         try {
            this.pool.returnObject(obj);
         } catch (Exception var4) {
         }

      }

      public void invalidateObject(Object key, Object obj) {
         try {
            this.pool.invalidateObject(obj);
         } catch (Exception var4) {
         }

      }

      public void addObject(Object key) throws Exception, IllegalStateException {
         this.pool.addObject();
      }

      public int getNumIdle(Object key) throws UnsupportedOperationException {
         return this.pool.getNumIdle();
      }

      public int getNumActive(Object key) throws UnsupportedOperationException {
         return this.pool.getNumActive();
      }

      public int getNumIdle() throws UnsupportedOperationException {
         return this.pool.getNumIdle();
      }

      public int getNumActive() throws UnsupportedOperationException {
         return this.pool.getNumActive();
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.pool.clear();
      }

      public void clear(Object key) throws Exception, UnsupportedOperationException {
         this.pool.clear();
      }

      public void close() {
         try {
            this.pool.close();
         } catch (Exception var2) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(KeyedPoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         this.pool.setFactory(PoolUtils.adapt(factory));
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("KeyedObjectPoolAdaptor");
         sb.append("{pool=").append(this.pool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class ObjectPoolAdaptor implements ObjectPool {
      private final Object key;
      private final KeyedObjectPool keyedPool;

      ObjectPoolAdaptor(KeyedObjectPool keyedPool, Object key) throws IllegalArgumentException {
         if (keyedPool == null) {
            throw new IllegalArgumentException("keyedPool must not be null.");
         } else if (key == null) {
            throw new IllegalArgumentException("key must not be null.");
         } else {
            this.keyedPool = keyedPool;
            this.key = key;
         }
      }

      public Object borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
         return this.keyedPool.borrowObject(this.key);
      }

      public void returnObject(Object obj) {
         try {
            this.keyedPool.returnObject(this.key, obj);
         } catch (Exception var3) {
         }

      }

      public void invalidateObject(Object obj) {
         try {
            this.keyedPool.invalidateObject(this.key, obj);
         } catch (Exception var3) {
         }

      }

      public void addObject() throws Exception, IllegalStateException {
         this.keyedPool.addObject(this.key);
      }

      public int getNumIdle() throws UnsupportedOperationException {
         return this.keyedPool.getNumIdle(this.key);
      }

      public int getNumActive() throws UnsupportedOperationException {
         return this.keyedPool.getNumActive(this.key);
      }

      public void clear() throws Exception, UnsupportedOperationException {
         this.keyedPool.clear();
      }

      public void close() {
         try {
            this.keyedPool.close();
         } catch (Exception var2) {
         }

      }

      /** @deprecated */
      @Deprecated
      public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
         this.keyedPool.setFactory(PoolUtils.adapt(factory));
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("ObjectPoolAdaptor");
         sb.append("{key=").append(this.key);
         sb.append(", keyedPool=").append(this.keyedPool);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class KeyedPoolableObjectFactoryAdaptor implements KeyedPoolableObjectFactory {
      private final PoolableObjectFactory factory;

      KeyedPoolableObjectFactoryAdaptor(PoolableObjectFactory factory) throws IllegalArgumentException {
         if (factory == null) {
            throw new IllegalArgumentException("factory must not be null.");
         } else {
            this.factory = factory;
         }
      }

      public Object makeObject(Object key) throws Exception {
         return this.factory.makeObject();
      }

      public void destroyObject(Object key, Object obj) throws Exception {
         this.factory.destroyObject(obj);
      }

      public boolean validateObject(Object key, Object obj) {
         return this.factory.validateObject(obj);
      }

      public void activateObject(Object key, Object obj) throws Exception {
         this.factory.activateObject(obj);
      }

      public void passivateObject(Object key, Object obj) throws Exception {
         this.factory.passivateObject(obj);
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("KeyedPoolableObjectFactoryAdaptor");
         sb.append("{factory=").append(this.factory);
         sb.append('}');
         return sb.toString();
      }
   }

   private static class PoolableObjectFactoryAdaptor implements PoolableObjectFactory {
      private final Object key;
      private final KeyedPoolableObjectFactory keyedFactory;

      PoolableObjectFactoryAdaptor(KeyedPoolableObjectFactory keyedFactory, Object key) throws IllegalArgumentException {
         if (keyedFactory == null) {
            throw new IllegalArgumentException("keyedFactory must not be null.");
         } else if (key == null) {
            throw new IllegalArgumentException("key must not be null.");
         } else {
            this.keyedFactory = keyedFactory;
            this.key = key;
         }
      }

      public Object makeObject() throws Exception {
         return this.keyedFactory.makeObject(this.key);
      }

      public void destroyObject(Object obj) throws Exception {
         this.keyedFactory.destroyObject(this.key, obj);
      }

      public boolean validateObject(Object obj) {
         return this.keyedFactory.validateObject(this.key, obj);
      }

      public void activateObject(Object obj) throws Exception {
         this.keyedFactory.activateObject(this.key, obj);
      }

      public void passivateObject(Object obj) throws Exception {
         this.keyedFactory.passivateObject(this.key, obj);
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("PoolableObjectFactoryAdaptor");
         sb.append("{key=").append(this.key);
         sb.append(", keyedFactory=").append(this.keyedFactory);
         sb.append('}');
         return sb.toString();
      }
   }
}
