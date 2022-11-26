package com.bea.common.security.store.data;

public class UserPasswordCredentialId extends CredentialId {
   public UserPasswordCredentialId() {
   }

   public UserPasswordCredentialId(String binding) {
      super(binding);
   }

   public UserPasswordCredentialId(String domainName, String realmName, String credentialName) {
      super(domainName, realmName, credentialName);
   }

   public UserPasswordCredentialId(UserPasswordCredential obj) {
      super((Credential)obj);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof UserPasswordCredentialId) ? false : super.equals(other);
      }
   }
}
