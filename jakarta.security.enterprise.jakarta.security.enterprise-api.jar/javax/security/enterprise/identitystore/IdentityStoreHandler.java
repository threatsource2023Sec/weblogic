package javax.security.enterprise.identitystore;

import javax.security.enterprise.credential.Credential;

public interface IdentityStoreHandler {
   CredentialValidationResult validate(Credential var1);
}
