package org.apache.commons.pool.impl;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolUtils;
import org.apache.commons.pool.PoolableObjectFactory;

public class SoftReferenceObjectPool extends BaseObjectPool implements ObjectPool {
   private final List _pool;
   private PoolableObjectFactory _factory = null;
   private final ReferenceQueue refQueue = new ReferenceQueue();
   private int _numActive = 0;

   /** @deprecated */
   @Deprecated
   public SoftReferenceObjectPool() {
      this._pool = new ArrayList();
      this._factory = null;
   }

   public SoftReferenceObjectPool(PoolableObjectFactory factory) {
      this._pool = new ArrayList();
      this._factory = factory;
   }

   /** @deprecated */
   @Deprecated
   public SoftReferenceObjectPool(PoolableObjectFactory factory, int initSize) throws Exception, IllegalArgumentException {
      if (factory == null) {
         throw new IllegalArgumentException("factory required to prefill the pool.");
      } else {
         this._pool = new ArrayList(initSize);
         this._factory = factory;
         PoolUtils.prefill(this, initSize);
      }
   }

   public synchronized Object borrowObject() throws Exception {
      this.assertOpen();
      Object obj = null;
      boolean newlyCreated = false;

      while(null == obj) {
         if (this._pool.isEmpty()) {
            if (null == this._factory) {
               throw new NoSuchElementException();
            }

            newlyCreated = true;
            obj = this._factory.makeObject();
         } else {
            SoftReference ref = (SoftReference)this._pool.remove(this._pool.size() - 1);
            obj = ref.get();
            ref.clear();
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
      if (this._factory != null) {
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
         this._pool.add(new SoftReference(obj, this.refQueue));
      }

      this.notifyAll();
      if (shouldDestroy && this._factory != null) {
         try {
            this._factory.destroyObject(obj);
         } catch (Exception var5) {
         }
      }

   }

   public synchronized void invalidateObject(Object obj) throws Exception {
      --this._numActive;
      if (this._factory != null) {
         this._factory.destroyObject(obj);
      }

      this.notifyAll();
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
            this._pool.add(new SoftReference(obj, this.refQueue));
            this.notifyAll();
         }

         if (shouldDestroy) {
            try {
               this._factory.destroyObject(obj);
            } catch (Exception var5) {
            }
         }

      }
   }

   public synchronized int getNumIdle() {
      this.pruneClearedReferences();
      return this._pool.size();
   }

   public synchronized int getNumActive() {
      return this._numActive;
   }

   public synchronized void clear() {
      if (null != this._factory) {
         Iterator iter = this._pool.iterator();

         while(iter.hasNext()) {
            try {
               Object obj = ((SoftReference)iter.next()).get();
               if (null != obj) {
                  this._factory.destroyObject(obj);
               }
            } catch (Exception var3) {
            }
         }
      }

      this._pool.clear();
      this.pruneClearedReferences();
   }

   public void close() throws Exception {
      super.close();
      this.clear();
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

   private void pruneClearedReferences() {
      Reference ref;
      while((ref = this.refQueue.poll()) != null) {
         try {
            this._pool.remove(ref);
         } catch (UnsupportedOperationException var3) {
         }
      }

   }

   public synchronized PoolableObjectFactory getFactory() {
      return this._factory;
   }
}
