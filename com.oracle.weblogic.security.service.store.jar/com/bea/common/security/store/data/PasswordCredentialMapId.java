package com.bea.common.security.store.data;

public class PasswordCredentialMapId extends CredentialMapId {
   public PasswordCredentialMapId() {
   }

   public PasswordCredentialMapId(String binding) {
      super(binding);
   }

   public PasswordCredentialMapId(String domainName, String realmName, String cn) {
      super(domainName, realmName, cn);
   }

   public PasswordCredentialMapId(PasswordCredentialMap obj) {
      super((CredentialMap)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof PasswordCredentialMapId) ? false : super.equals(other);
      }
   }
}
