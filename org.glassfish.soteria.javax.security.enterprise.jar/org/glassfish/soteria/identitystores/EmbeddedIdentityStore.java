package org.glassfish.soteria.identitystores;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStorePermission;
import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

public class EmbeddedIdentityStore implements IdentityStore {
   private final EmbeddedIdentityStoreDefinition embeddedIdentityStoreDefinition;
   private final Map callerToCredentials;
   private final Set validationType;

   public EmbeddedIdentityStore(EmbeddedIdentityStoreDefinition embeddedIdentityStoreDefinition) {
      this.embeddedIdentityStoreDefinition = embeddedIdentityStoreDefinition;
      this.callerToCredentials = (Map)Arrays.stream(embeddedIdentityStoreDefinition.value()).collect(Collectors.toMap((e) -> {
         return e.callerName();
      }, (e) -> {
         return e;
      }));
      this.validationType = Collections.unmodifiableSet(new HashSet(Arrays.asList(embeddedIdentityStoreDefinition.useFor())));
   }

   public CredentialValidationResult validate(Credential credential) {
      return credential instanceof UsernamePasswordCredential ? this.validate((UsernamePasswordCredential)credential) : CredentialValidationResult.NOT_VALIDATED_RESULT;
   }

   public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
      Credentials credentials = (Credentials)this.callerToCredentials.get(usernamePasswordCredential.getCaller());
      return credentials != null && usernamePasswordCredential.getPassword().compareTo(credentials.password()) ? new CredentialValidationResult(new CallerPrincipal(credentials.callerName()), new HashSet(Arrays.asList(credentials.groups()))) : CredentialValidationResult.INVALID_RESULT;
   }

   public Set getCallerGroups(CredentialValidationResult validationResult) {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null) {
         securityManager.checkPermission(new IdentityStorePermission("getGroups"));
      }

      Credentials credentials = (Credentials)this.callerToCredentials.get(validationResult.getCallerPrincipal().getName());
      return (Set)(credentials != null ? new HashSet(Arrays.asList(credentials.groups())) : Collections.emptySet());
   }

   public int priority() {
      return this.embeddedIdentityStoreDefinition.priority();
   }

   public Set validationTypes() {
      return this.validationType;
   }
}
