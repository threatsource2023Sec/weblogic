package kodo.runtime;

import kodo.jdo.ExtentImpl;
import kodo.jdo.PersistenceManagerFactoryImpl;
import org.apache.openjpa.kernel.Extent;

/** @deprecated */
public class KodoExtent extends ExtentImpl {
   private FetchConfiguration _fetch = null;

   public KodoExtent(KodoPersistenceManager pm, Extent extent) {
      super(pm, extent);
   }

   public FetchConfiguration getFetchConfiguration() {
      this.getDelegate().lock();

      FetchConfiguration var5;
      try {
         if (this._fetch == null) {
            KodoPersistenceManager kpm = (KodoPersistenceManager)this.getPersistenceManager();
            this._fetch = ((KodoPersistenceManagerFactory)kpm.getPersistenceManagerFactory()).newFetchConfiguration(kpm, this.getDelegate().getFetchConfiguration());
         }

         var5 = this._fetch;
      } finally {
         this.getDelegate().unlock();
      }

      return var5;
   }

   protected PersistenceManagerFactoryImpl getPersistenceManagerFactoryImpl() {
      return ((KodoPersistenceManagerFactory)this.getPersistenceManager().getPersistenceManagerFactory()).getDelegate();
   }
}
