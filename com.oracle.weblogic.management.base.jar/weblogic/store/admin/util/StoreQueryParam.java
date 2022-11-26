package weblogic.store.admin.util;

import weblogic.management.configuration.PersistentStoreMBean;

public final class StoreQueryParam implements Cloneable {
   private String mDeploymentName;
   private String mServerInstanceName;
   private PersistentStoreMBean mStoreBean;

   public StoreQueryParam(String deploymentName, String serverInstanceName, PersistentStoreMBean storeBean) {
      this.mDeploymentName = deploymentName;
      this.mServerInstanceName = serverInstanceName;
      this.mStoreBean = storeBean;
   }

   public String getDeploymentName() {
      return this.mDeploymentName;
   }

   public String getServerInstanceName() {
      return this.mServerInstanceName;
   }

   public PersistentStoreMBean getStoreBean() {
      return this.mStoreBean;
   }

   public StoreQueryParam clone() {
      return new StoreQueryParam(this.mDeploymentName, this.mServerInstanceName, this.mStoreBean);
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.mDeploymentName == null ? 0 : this.mDeploymentName.hashCode());
      result = 31 * result + (this.mServerInstanceName == null ? 0 : this.mServerInstanceName.hashCode());
      result = 31 * result + (this.mStoreBean == null ? 0 : this.mStoreBean.hashCode());
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
         StoreQueryParam other = (StoreQueryParam)obj;
         if (this.mDeploymentName == null) {
            if (other.mDeploymentName != null) {
               return false;
            }
         } else if (!this.mDeploymentName.equals(other.mDeploymentName)) {
            return false;
         }

         if (this.mServerInstanceName == null) {
            if (other.mServerInstanceName != null) {
               return false;
            }
         } else if (!this.mServerInstanceName.equals(other.mServerInstanceName)) {
            return false;
         }

         if (this.mStoreBean == null) {
            if (other.mStoreBean != null) {
               return false;
            }
         } else if (!this.mStoreBean.equals(other.mStoreBean)) {
            return false;
         }

         return true;
      }
   }
}
