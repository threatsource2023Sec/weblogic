package weblogic.store.admin.util;

import weblogic.management.configuration.PersistentStoreMBean;

public final class StoreQueryResult {
   private StoreQueryParam mQueryParam;
   private StoreQueryStatus mStoreExists;
   private Throwable mThrowable;

   public StoreQueryResult(StoreQueryParam queryParam, StoreQueryStatus itExists, Throwable throwable) {
      this.mQueryParam = queryParam.clone();
      this.mStoreExists = itExists;
      this.mThrowable = throwable;
   }

   public StoreQueryParam getQueryParameters() {
      return this.mQueryParam;
   }

   public StoreQueryStatus getQueryStatus() {
      return this.mStoreExists;
   }

   public Throwable getThrowable() {
      return this.mThrowable;
   }

   public String getDeploymentName() {
      return this.mQueryParam.getDeploymentName();
   }

   public String getServerInstanceName() {
      return this.mQueryParam.getServerInstanceName();
   }

   public PersistentStoreMBean getStoreBean() {
      return this.mQueryParam.getStoreBean();
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.mQueryParam == null ? 0 : this.mQueryParam.hashCode());
      result = 31 * result + (this.mStoreExists == null ? 0 : this.mStoreExists.hashCode());
      result = 31 * result + (this.mThrowable == null ? 0 : this.mThrowable.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         StoreQueryResult other = (StoreQueryResult)obj;
         if (this.mQueryParam == null) {
            if (other.mQueryParam != null) {
               return false;
            }
         } else if (!this.mQueryParam.equals(other.mQueryParam)) {
            return false;
         }

         if (this.mStoreExists != other.mStoreExists) {
            return false;
         } else {
            if (this.mThrowable == null) {
               if (other.mThrowable != null) {
                  return false;
               }
            } else if (!this.mThrowable.equals(other.mThrowable)) {
               return false;
            }

            return true;
         }
      }
   }
}
