package kodo.query;

import java.util.Collection;
import kodo.jdo.PersistenceManagerFactoryImpl;
import kodo.jdo.QueryImpl;
import kodo.runtime.FetchConfiguration;
import kodo.runtime.KodoPersistenceManager;
import kodo.runtime.KodoPersistenceManagerFactory;
import org.apache.openjpa.kernel.Query;

/** @deprecated */
public class KodoQuery extends QueryImpl {
   public static final int QUERY_FLUSH_TRUE = 0;
   public static final int QUERY_FLUSH_FALSE = 1;
   public static final int QUERY_FLUSH_WITH_CONNECTION = 2;
   private FetchConfiguration _fetch = null;

   public KodoQuery(KodoPersistenceManager pm, Query query) {
      super(pm, query);
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

   public Collection getFilterListeners() {
      return this.getDelegate().getFilterListeners();
   }

   public Collection getAggregateListeners() {
      return this.getDelegate().getAggregateListeners();
   }

   public long getStartIndex() {
      return this.getStartRange();
   }

   public long getEndIndex() {
      return this.getEndRange();
   }
}
