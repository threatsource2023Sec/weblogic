package weblogic.security.principal;

import java.security.AccessController;
import java.security.PrivilegedAction;

public abstract class WLSAbstractPrincipal implements WLSPrincipal, IdentityDomainPrincipal {
   private byte[] signature = null;
   private byte[] salt = null;
   private String name = null;
   private transient volatile boolean isIddSet = false;
   private static boolean useSignature;
   private static final long serialVersionUID = -5765092415154848004L;
   private String dn = null;
   private String guid = null;
   private boolean equalsCaseInsensitive = false;
   private boolean equalsCompareDnAndGuid = false;
   protected boolean principalFactoryCreated = false;
   private String idd;

   public boolean isEqualsCaseInsensitive() {
      return this.equalsCaseInsensitive;
   }

   public boolean isEqualsCompareDnAndGuid() {
      return this.equalsCompareDnAndGuid;
   }

   public void setEqualsCaseInsensitive(boolean equalsCaseInsensitive) {
      this.equalsCaseInsensitive = equalsCaseInsensitive;
   }

   public void setEqualsCompareDnAndGuid(boolean equalsCompareDnAndGuid) {
      this.equalsCompareDnAndGuid = equalsCompareDnAndGuid;
   }

   protected WLSAbstractPrincipal() {
      this.salt = String.valueOf(System.currentTimeMillis()).getBytes();
   }

   protected WLSAbstractPrincipal(boolean createSalt) {
      if (createSalt) {
         this.salt = String.valueOf(System.currentTimeMillis()).getBytes();
      }

   }

   public String getName() {
      return this.name;
   }

   protected void setName(String name) {
      this.name = name;
   }

   public boolean equals(Object another) {
      if (another == null) {
         return false;
      } else if (this == another) {
         return true;
      } else if (!(another instanceof WLSAbstractPrincipal)) {
         return false;
      } else {
         WLSAbstractPrincipal anotherPrincipal = (WLSAbstractPrincipal)another;
         if (this.name != null && anotherPrincipal.name != null) {
            if (this.equalsCompareDnAndGuid) {
               if (this.guid != null && anotherPrincipal.guid != null) {
                  if (!this.guid.equalsIgnoreCase(anotherPrincipal.guid)) {
                     return false;
                  }
               } else if (this.dn != null && anotherPrincipal.dn != null) {
                  if (!this.dn.equalsIgnoreCase(anotherPrincipal.dn)) {
                     return false;
                  }
               } else if (this.equalsCaseInsensitive) {
                  if (!this.name.equalsIgnoreCase(anotherPrincipal.name)) {
                     return false;
                  }
               } else if (!this.name.equals(anotherPrincipal.name)) {
                  return false;
               }
            } else if (this.equalsCaseInsensitive) {
               if (!this.name.equalsIgnoreCase(anotherPrincipal.name)) {
                  return false;
               }
            } else if (!this.name.equals(anotherPrincipal.name)) {
               return false;
            }

            if (this.idd != null && !this.idd.isEmpty()) {
               if (this.equalsCaseInsensitive) {
                  if (!this.idd.equalsIgnoreCase(anotherPrincipal.idd)) {
                     return false;
                  }

                  if (!this.idd.equals(anotherPrincipal.idd)) {
                     return false;
                  }
               }
            } else if (anotherPrincipal.idd != null && !anotherPrincipal.idd.isEmpty()) {
               return false;
            }

            if (useSignature) {
               byte[] anotherSignature = anotherPrincipal.getSignature();
               if (this.signature == anotherSignature) {
                  return true;
               }

               if (this.signature == null || anotherSignature == null) {
                  return false;
               }

               if (this.signature.length != anotherSignature.length) {
                  return false;
               }

               for(int i = 0; i < this.signature.length; ++i) {
                  if (this.signature[i] != anotherSignature[i]) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return this.name == anotherPrincipal.name;
         }
      }
   }

   public String toString() {
      return this.idd != null && !this.idd.isEmpty() ? this.name + " [" + this.idd + "]" : this.name;
   }

   public int hashCode() {
      int h;
      if (this.equalsCaseInsensitive) {
         h = this.name.toLowerCase().hashCode();
         if (this.idd != null && !this.idd.isEmpty()) {
            h = h * 31 + this.idd.toLowerCase().hashCode();
         }

         return h;
      } else {
         h = this.name.hashCode();
         if (this.idd != null && !this.idd.isEmpty()) {
            h = h * 31 + this.idd.hashCode();
         }

         return h;
      }
   }

   public byte[] getSignature() {
      return this.signature;
   }

   public void setSignature(byte[] signature) {
      this.signature = signature;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public void setSalt(byte[] salt) {
      this.salt = salt;
   }

   private String getDecoratedName() {
      String decoratedName = this.name;
      if (this.idd != null && !this.idd.isEmpty()) {
         decoratedName = decoratedName + "::" + this.idd;
      }

      return decoratedName;
   }

   public byte[] getSignedData() {
      return this.equalsCaseInsensitive ? this.getDecoratedName().toLowerCase().getBytes() : this.getDecoratedName().getBytes();
   }

   protected byte[] getSignedDataCaseSensitive() {
      return this.getDecoratedName().getBytes();
   }

   public String getGuid() {
      return this.guid;
   }

   protected void setGuid(String guid) {
      this.guid = guid;
   }

   public String getDn() {
      return this.dn;
   }

   protected void setDn(String dn) {
      this.dn = dn;
   }

   public boolean isPrincipalFactoryCreated() {
      return this.principalFactoryCreated;
   }

   public String getIdentityDomain() {
      return this.idd;
   }

   public void setIdentityDomain(String identityDomain) {
      if (!this.isIddSet && this.idd == null && identityDomain != null) {
         this.isIddSet = true;
         this.idd = identityDomain;
      }

   }

   static {
      try {
         GetSignatureProperty getSignatureProperty = new GetSignatureProperty();
         useSignature = (Boolean)AccessController.doPrivileged(getSignatureProperty);
      } catch (SecurityException var1) {
         useSignature = true;
      }

   }

   private static class GetSignatureProperty implements PrivilegedAction {
      private GetSignatureProperty() {
      }

      public Object run() {
         String signatureProperty = System.getProperty("weblogic.security.principal.useSignatureInEquals", "true");
         return Boolean.valueOf(signatureProperty);
      }

      // $FF: synthetic method
      GetSignatureProperty(Object x0) {
         this();
      }
   }
}
