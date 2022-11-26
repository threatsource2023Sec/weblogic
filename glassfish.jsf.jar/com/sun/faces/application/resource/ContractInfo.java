package com.sun.faces.application.resource;

public final class ContractInfo {
   private static final long serialVersionUID = 6585532979916457692L;
   String contract;

   public ContractInfo(String contract) {
      this.contract = contract;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ContractInfo other = (ContractInfo)obj;
         if (this.contract == null) {
            if (other.contract != null) {
               return false;
            }
         } else if (!this.contract.equals(other.contract)) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int hash = 7;
      hash = 29 * hash + (this.contract != null ? this.contract.hashCode() : 0);
      return hash;
   }

   public String toString() {
      return this.contract;
   }
}
