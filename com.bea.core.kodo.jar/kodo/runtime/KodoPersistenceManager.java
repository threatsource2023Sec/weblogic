package kodo.runtime;

import java.util.Collection;
import javax.jdo.Extent;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import kodo.jdo.PersistenceManagerImpl;
import kodo.query.KodoQuery;
import org.apache.openjpa.event.TransactionListener;
import org.apache.openjpa.kernel.Broker;

/** @deprecated */
public class KodoPersistenceManager extends PersistenceManagerImpl {
   public static final int LOOKUP_CHECK = 0;
   public static final int LOOKUP_HOLLOW = 1;
   private boolean _forceValidate = false;
   private FetchConfiguration _fetch = null;
   private final KodoPersistenceManagerFactory _pmf;

   public KodoPersistenceManager(KodoPersistenceManagerFactory pmf, Broker broker) {
      super(pmf.getDelegate(), broker);
      this._pmf = pmf;
   }

   public PersistenceManagerFactory getPersistenceManagerFactory() {
      return this._pmf;
   }

   public boolean getRetainValuesInOptimistic() {
      return this.getAutoClear() != 1;
   }

   public void setRetainValuesInOptimistic(boolean retain) {
      if (retain) {
         this.setAutoClear(0);
      } else {
         this.setAutoClear(1);
      }

   }

   public int getObjectLookupMode() {
      return !this._forceValidate && this.getConfiguration().getCompatibilityInstance().getValidateFalseReturnsHollow() ? 1 : 0;
   }

   public void setObjectLookupMode(int mode) {
      this._forceValidate = mode == 0 && this.getConfiguration().getCompatibilityInstance().getValidateFalseReturnsHollow();
   }

   public FetchConfiguration getFetchConfiguration() {
      this.getDelegate().lock();

      FetchConfiguration var1;
      try {
         if (this._fetch == null) {
            this._fetch = this._pmf.newFetchConfiguration(this, this.getDelegate().getFetchConfiguration());
         }

         var1 = this._fetch;
      } finally {
         this.getDelegate().unlock();
      }

      return var1;
   }

   public void registerListener(TransactionListener tl) {
      this.addTransactionListener(tl);
   }

   public boolean removeListener(TransactionListener tl) {
      this.removeTransactionListener(tl);
      return true;
   }

   public Object getObjectById(Object oid, boolean validate) {
      return super.getObjectById(oid, validate || this._forceValidate);
   }

   public Collection getObjectsById(Collection oids, boolean validate) {
      return super.getObjectsById(oids, validate || this._forceValidate);
   }

   public Object[] getObjectsById(Object[] oids, boolean validate) {
      return super.getObjectsById(oids, validate || this._forceValidate);
   }

   public boolean getDetachOnClose() {
      return this.getDetachAllOnClose();
   }

   public void setDetachOnClose(boolean detach) {
      this.setDetachAllOnClose(detach);
   }

   public void setAttachFetchFields(boolean attachFetchFields) {
   }

   public boolean getAttachFetchFields() {
      return false;
   }

   public int getDetachFields() {
      return this.getDelegate().getAutoDetach();
   }

   public void setDetachFields(int mode) {
      this.getDelegate().setAutoDetach(mode);
   }

   public Object detach(Object pc) {
      return this.detachCopy(pc);
   }

   public Collection detachAll(Collection pcs) {
      return this.detachCopyAll(pcs);
   }

   public Object[] detachAll(Object[] pcs) {
      return this.detachCopyAll(pcs);
   }

   public Object attach(Object pc) {
      return this.attachCopy(pc, false);
   }

   public Collection attachAll(Collection pcs) {
      return this.attachCopyAll(pcs, false);
   }

   public Object[] attachAll(Object[] pcs) {
      return this.attachCopyAll(pcs, false);
   }

   public boolean isPopulateDataCache() {
      return this.getPopulateDataStoreCache();
   }

   public void setPopulateDataCache(boolean populate) {
      this.setPopulateDataStoreCache(populate);
   }

   public Extent getExtent(Class type) {
      return this.getExtent(type, true);
   }

   public Extent getExtent(Class type, boolean subclasses) {
      return new KodoExtent(this, this.getDelegate().newExtent(type, subclasses));
   }

   protected Query newQueryInternal(org.apache.openjpa.kernel.Query del) {
      return new KodoQuery(this, del);
   }
}
