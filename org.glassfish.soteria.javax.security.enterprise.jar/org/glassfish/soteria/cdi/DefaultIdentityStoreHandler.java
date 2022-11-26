package org.glassfish.soteria.cdi;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.security.enterprise.identitystore.IdentityStore.ValidationType;

public class DefaultIdentityStoreHandler implements IdentityStoreHandler {
   private List authenticationIdentityStores;
   private List authorizationIdentityStores;

   public void init() {
      List identityStores = CdiUtils.getBeanReferencesByType(IdentityStore.class, false);
      this.authenticationIdentityStores = (List)identityStores.stream().filter((i) -> {
         return i.validationTypes().contains(ValidationType.VALIDATE);
      }).sorted(Comparator.comparing(IdentityStore::priority)).collect(Collectors.toList());
      this.authorizationIdentityStores = (List)identityStores.stream().filter((i) -> {
         return i.validationTypes().contains(ValidationType.PROVIDE_GROUPS) && !i.validationTypes().contains(ValidationType.VALIDATE);
      }).sorted(Comparator.comparing(IdentityStore::priority)).collect(Collectors.toList());
   }

   public CredentialValidationResult validate(Credential credential) {
      final CredentialValidationResult validationResult = null;
      IdentityStore identityStore = null;
      boolean isGotAnInvalidResult = false;
      Iterator var5 = this.authenticationIdentityStores.iterator();

      while(var5.hasNext()) {
         IdentityStore authenticationIdentityStore = (IdentityStore)var5.next();
         validationResult = authenticationIdentityStore.validate(credential);
         if (validationResult.getStatus() == Status.VALID) {
            identityStore = authenticationIdentityStore;
            break;
         }

         if (validationResult.getStatus() == Status.INVALID) {
            isGotAnInvalidResult = true;
         }
      }

      if (validationResult != null && validationResult.getStatus() == Status.VALID) {
         final Set groups = new HashSet();
         if (identityStore.validationTypes().contains(ValidationType.PROVIDE_GROUPS)) {
            groups.addAll(validationResult.getCallerGroups());
         }

         AccessController.doPrivileged(new PrivilegedAction() {
            public Void run() {
               Iterator var1 = DefaultIdentityStoreHandler.this.authorizationIdentityStores.iterator();

               while(var1.hasNext()) {
                  IdentityStore authorizationIdentityStore = (IdentityStore)var1.next();
                  groups.addAll(authorizationIdentityStore.getCallerGroups(validationResult));
               }

               return null;
            }
         });
         return new CredentialValidationResult(validationResult.getIdentityStoreId(), validationResult.getCallerPrincipal(), validationResult.getCallerDn(), validationResult.getCallerUniqueId(), groups);
      } else {
         return isGotAnInvalidResult ? CredentialValidationResult.INVALID_RESULT : CredentialValidationResult.NOT_VALIDATED_RESULT;
      }
   }
}
