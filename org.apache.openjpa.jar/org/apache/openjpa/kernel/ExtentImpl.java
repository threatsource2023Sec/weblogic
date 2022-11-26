package org.apache.openjpa.kernel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.iterators.FilterIterator;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProviderIterator;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;

public class ExtentImpl implements Extent {
   private static final ClassMetaData[] EMPTY_METAS = new ClassMetaData[0];
   private final Broker _broker;
   private final Class _type;
   private final boolean _subs;
   private final FetchConfiguration _fc;
   private final ReentrantLock _lock;
   private boolean _ignore = false;
   private ReferenceHashSet _openItrs = null;

   ExtentImpl(Broker broker, Class type, boolean subs, FetchConfiguration fetch) {
      this._broker = broker;
      this._type = type;
      this._subs = subs;
      if (fetch != null) {
         this._fc = fetch;
      } else {
         this._fc = (FetchConfiguration)broker.getFetchConfiguration().clone();
      }

      this._ignore = broker.getIgnoreChanges();
      if (broker.getMultithreaded()) {
         this._lock = new ReentrantLock();
      } else {
         this._lock = null;
      }

   }

   public FetchConfiguration getFetchConfiguration() {
      return this._fc;
   }

   public boolean getIgnoreChanges() {
      return this._ignore;
   }

   public void setIgnoreChanges(boolean ignoreChanges) {
      this._broker.assertOpen();
      this._ignore = ignoreChanges;
   }

   public List list() {
      List list = new ArrayList();
      Iterator itr = this.iterator();

      ArrayList var3;
      try {
         while(itr.hasNext()) {
            list.add(itr.next());
         }

         var3 = list;
      } finally {
         ImplHelper.close(itr);
      }

      return var3;
   }

   public Iterator iterator() {
      this._broker.assertNontransactionalRead();
      CloseableIterator citr = null;

      try {
         CloseableIteratorChain chain = new CloseableIteratorChain();
         boolean trans = !this._ignore && this._broker.isActive();
         if (trans) {
            chain.addIterator(new FilterNewIterator());
         }

         MetaDataRepository repos = this._broker.getConfiguration().getMetaDataRepositoryInstance();
         ClassMetaData meta = repos.getMetaData(this._type, this._broker.getClassLoader(), false);
         ClassMetaData[] metas;
         if (meta != null && (!this._subs || !meta.isManagedInterface()) && (meta.isMapped() || this._subs && meta.getMappedPCSubclassMetaDatas().length > 0)) {
            metas = new ClassMetaData[]{meta};
         } else if (!this._subs || meta != null && !meta.isManagedInterface()) {
            metas = EMPTY_METAS;
         } else {
            metas = repos.getImplementorMetaDatas(this._type, this._broker.getClassLoader(), false);
         }

         for(int i = 0; i < metas.length; ++i) {
            ResultObjectProvider rop = this._broker.getStoreManager().executeExtent(metas[i], this._subs, this._fc);
            if (rop != null) {
               chain.addIterator(new ResultObjectProviderIterator(rop));
            }
         }

         if (trans) {
            citr = new FilterDeletedIterator(chain);
         } else {
            citr = chain;
         }

         ((CloseableIterator)citr).setRemoveOnClose(this);
      } catch (OpenJPAException var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw new GeneralException(var16);
      }

      this.lock();

      try {
         if (this._openItrs == null) {
            this._openItrs = new ReferenceHashSet(2);
         }

         this._openItrs.add(citr);
      } finally {
         this.unlock();
      }

      return (Iterator)citr;
   }

   public Broker getBroker() {
      return this._broker;
   }

   public Class getElementType() {
      return this._type;
   }

   public boolean hasSubclasses() {
      return this._subs;
   }

   public void closeAll() {
      if (this._openItrs != null) {
         this.lock();

         try {
            Iterator itr = this._openItrs.iterator();

            while(itr.hasNext()) {
               CloseableIterator citr = (CloseableIterator)itr.next();
               citr.setRemoveOnClose((ExtentImpl)null);

               try {
                  citr.close();
               } catch (Exception var10) {
               }
            }

            this._openItrs.clear();
         } catch (OpenJPAException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw new GeneralException(var12);
         } finally {
            this.unlock();
         }

      }
   }

   public void lock() {
      if (this._lock != null) {
         this._lock.lock();
      }

   }

   public void unlock() {
      if (this._lock != null) {
         this._lock.unlock();
      }

   }

   private class FilterNewIterator extends FilterIterator implements Closeable, Predicate {
      public FilterNewIterator() {
         super(ExtentImpl.this._broker.getTransactionalObjects().iterator());
         this.setPredicate(this);
      }

      public void close() {
      }

      public boolean evaluate(Object o) {
         if (!ExtentImpl.this._broker.isNew(o)) {
            return false;
         } else {
            Class type = o.getClass();
            if (!ExtentImpl.this._subs && type != ExtentImpl.this._type) {
               return false;
            } else {
               return !ExtentImpl.this._subs || ExtentImpl.this._type.isAssignableFrom(type);
            }
         }
      }
   }

   private static class FilterDeletedIterator extends FilterIterator implements CloseableIterator, Predicate {
      private ExtentImpl _extent = null;
      private boolean _closed = false;

      public FilterDeletedIterator(Iterator itr) {
         super(itr);
         this.setPredicate(this);
      }

      public boolean hasNext() {
         return this._closed ? false : super.hasNext();
      }

      public Object next() {
         if (this._closed) {
            throw new NoSuchElementException();
         } else {
            return super.next();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void setRemoveOnClose(ExtentImpl extent) {
         this._extent = extent;
      }

      public void close() throws Exception {
         if (this._extent != null && this._extent._openItrs != null) {
            this._extent.lock();

            try {
               this._extent._openItrs.remove(this);
            } finally {
               this._extent.unlock();
            }
         }

         this._closed = true;
         ((Closeable)this.getIterator()).close();
      }

      public boolean evaluate(Object o) {
         return !this._extent._broker.isDeleted(o);
      }
   }

   private static class CloseableIteratorChain extends IteratorChain implements CloseableIterator {
      private ExtentImpl _extent;
      private boolean _closed;

      private CloseableIteratorChain() {
         this._extent = null;
         this._closed = false;
      }

      public boolean hasNext() {
         return this._closed ? false : super.hasNext();
      }

      public Object next() {
         if (this._closed) {
            throw new NoSuchElementException();
         } else {
            return super.next();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void setRemoveOnClose(ExtentImpl extent) {
         this._extent = extent;
      }

      public void close() throws Exception {
         if (this._extent != null && this._extent._openItrs != null) {
            this._extent.lock();

            try {
               this._extent._openItrs.remove(this);
            } finally {
               this._extent.unlock();
            }
         }

         this._closed = true;
         Iterator itr = this.getIterators().iterator();

         while(itr.hasNext()) {
            ((Closeable)itr.next()).close();
         }

      }

      // $FF: synthetic method
      CloseableIteratorChain(Object x0) {
         this();
      }
   }

   private interface CloseableIterator extends Closeable, Iterator {
      void setRemoveOnClose(ExtentImpl var1);
   }
}
