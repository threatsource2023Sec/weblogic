package com.bea.common.security.store.data;

public class PasswordCredentialId extends CredentialId {
   public PasswordCredentialId() {
   }

   public PasswordCredentialId(String binding) {
      super(binding);
   }

   public PasswordCredentialId(String domainName, String realmName, String credentialName) {
      super(domainName, realmName, credentialName);
   }

   public PasswordCredentialId(PasswordCredential obj) {
      super((Credential)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof PasswordCredentialId) ? false : super.equals(other);
      }
   }
}
