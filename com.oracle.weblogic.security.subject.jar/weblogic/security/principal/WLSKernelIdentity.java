package weblogic.security.principal;

import weblogic.security.WLSPrincipals;

public final class WLSKernelIdentity implements WLSPrincipal {
   private static final String WLS_KERNEL = "WLS Kernel";
   private String value = null;
   private static final long serialVersionUID = -2611876037958603489L;

   public WLSKernelIdentity(String value) {
      this.value = value;
   }

   public String getName() {
      return WLSPrincipals.getKernelUsername();
   }

   public boolean equals(Object another) {
      if (another == null) {
         return false;
      } else if (this == another) {
         return true;
      } else if (!(another instanceof WLSKernelIdentity)) {
         return false;
      } else {
         WLSKernelIdentity anotherPrincipal = (WLSKernelIdentity)another;
         return this.value.equals(anotherPrincipal.getName());
      }
   }

   public String toString() {
      return "WLS Kernel";
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public byte[] getSignature() {
      return null;
   }

   public void setSignature(byte[] signature) {
   }

   public byte[] getSalt() {
      return null;
   }

   public byte[] getSignedData() {
      return null;
   }

   public String getDn() {
      return null;
   }

   public String getGuid() {
      return null;
   }
}
