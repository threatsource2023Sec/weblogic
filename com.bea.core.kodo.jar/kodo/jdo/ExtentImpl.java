package kodo.jdo;

import java.util.Iterator;
import java.util.List;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;
import org.apache.openjpa.kernel.DelegatingExtent;
import org.apache.openjpa.kernel.Extent;

public class ExtentImpl implements KodoExtent {
   private final PersistenceManagerImpl _pm;
   private final DelegatingExtent _extent;
   private FetchPlan _plan = null;

   public ExtentImpl(PersistenceManagerImpl pm, Extent extent) {
      this._pm = pm;
      this._extent = new DelegatingExtent(extent, JDOExceptions.TRANSLATOR);
   }

   public Extent getDelegate() {
      return this._extent.getDelegate();
   }

   public Class getCandidateClass() {
      return this._extent.getElementType();
   }

   public boolean hasSubclasses() {
      return this._extent.hasSubclasses();
   }

   public PersistenceManager getPersistenceManager() {
      return this._pm;
   }

   public FetchPlan getFetchPlan() {
      this._extent.lock();

      FetchPlan var1;
      try {
         if (this._plan == null) {
            this._plan = this.getPersistenceManagerFactoryImpl().toFetchPlan(this._extent.getBroker(), this._extent.getFetchConfiguration());
         }

         var1 = this._plan;
      } finally {
         this._extent.unlock();
      }

      return var1;
   }

   protected PersistenceManagerFactoryImpl getPersistenceManagerFactoryImpl() {
      return (PersistenceManagerFactoryImpl)this._pm.getPersistenceManagerFactory();
   }

   public boolean getIgnoreCache() {
      return this._extent.getIgnoreChanges();
   }

   public void setIgnoreCache(boolean ignoreCache) {
      this._extent.setIgnoreChanges(ignoreCache);
   }

   public List list() {
      return this._extent.list();
   }

   public Iterator iterator() {
      return this._extent.iterator();
   }

   public void close(Iterator itr) {
      KodoJDOHelper.close(itr);
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
