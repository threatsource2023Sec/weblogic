package org.apache.openjpa.persistence;

import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.kernel.DelegatingExtent;

public class ExtentImpl implements Extent {
   private final EntityManagerImpl _em;
   private final DelegatingExtent _extent;
   private FetchPlan _fetch = null;

   public ExtentImpl(EntityManagerImpl em, org.apache.openjpa.kernel.Extent extent) {
      this._em = em;
      this._extent = new DelegatingExtent(extent, PersistenceExceptions.getRollbackTranslator(em));
   }

   public org.apache.openjpa.kernel.Extent getDelegate() {
      return this._extent.getDelegate();
   }

   public Class getElementClass() {
      return this._extent.getElementType();
   }

   public boolean hasSubclasses() {
      return this._extent.hasSubclasses();
   }

   public OpenJPAEntityManager getEntityManager() {
      return this._em;
   }

   public FetchPlan getFetchPlan() {
      this._em.assertNotCloseInvoked();
      this._extent.lock();

      FetchPlan var1;
      try {
         if (this._fetch == null) {
            this._fetch = ((EntityManagerFactoryImpl)this._em.getEntityManagerFactory()).toFetchPlan(this._extent.getBroker(), this._extent.getFetchConfiguration());
         }

         var1 = this._fetch;
      } finally {
         this._extent.unlock();
      }

      return var1;
   }

   public boolean getIgnoreChanges() {
      return this._extent.getIgnoreChanges();
   }

   public void setIgnoreChanges(boolean ignoreChanges) {
      this._em.assertNotCloseInvoked();
      this._extent.setIgnoreChanges(ignoreChanges);
   }

   public List list() {
      this._em.assertNotCloseInvoked();
      return this._extent.list();
   }

   public Iterator iterator() {
      this._em.assertNotCloseInvoked();
      return this._extent.iterator();
   }

   public void closeAll() {
      this._extent.closeAll();
   }

   public int hashCode() {
      return this._extent.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof ExtentImpl) ? false : this._extent.equals(((ExtentImpl)other)._extent);
      }
   }
}
