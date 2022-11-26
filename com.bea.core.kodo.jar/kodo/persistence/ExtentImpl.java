package kodo.persistence;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/** @deprecated */
class ExtentImpl implements Extent {
   private final org.apache.openjpa.persistence.ExtentImpl _delegate;

   public ExtentImpl(org.apache.openjpa.persistence.ExtentImpl del) {
      this._delegate = del;
   }

   public org.apache.openjpa.kernel.Extent getDelegate() {
      return this._delegate.getDelegate();
   }

   public Class getElementClass() {
      return this._delegate.getElementClass();
   }

   public boolean hasSubclasses() {
      return this._delegate.hasSubclasses();
   }

   public KodoEntityManager getEntityManager() {
      return KodoPersistence.cast((EntityManager)this._delegate.getEntityManager());
   }

   public FetchPlanImpl getFetchPlan() {
      return new FetchPlanImpl((org.apache.openjpa.persistence.FetchPlanImpl)this._delegate.getFetchPlan());
   }

   public boolean getIgnoreChanges() {
      return this._delegate.getIgnoreChanges();
   }

   public void setIgnoreChanges(boolean ignoreChanges) {
      this._delegate.setIgnoreChanges(ignoreChanges);
   }

   public List list() {
      return this._delegate.list();
   }

   public Iterator iterator() {
      return this._delegate.iterator();
   }

   public void closeAll() {
      this._delegate.closeAll();
   }

   public int hashCode() {
      return this._delegate.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof ExtentImpl) ? false : this._delegate.equals(((ExtentImpl)other)._delegate);
      }
   }
}
