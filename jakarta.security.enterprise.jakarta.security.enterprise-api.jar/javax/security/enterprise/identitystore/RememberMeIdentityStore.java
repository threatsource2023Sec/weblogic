package javax.security.enterprise.identitystore;

import java.util.Set;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.RememberMeCredential;

public interface RememberMeIdentityStore {
   CredentialValidationResult validate(RememberMeCredential var1);

   String generateLoginToken(CallerPrincipal var1, Set var2);

   void removeLoginToken(String var1);
}
