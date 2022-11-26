package javax.security.enterprise.identitystore;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.security.enterprise.credential.Credential;

public interface IdentityStore {
   Set DEFAULT_VALIDATION_TYPES = EnumSet.of(IdentityStore.ValidationType.VALIDATE, IdentityStore.ValidationType.PROVIDE_GROUPS);

   default CredentialValidationResult validate(Credential credential) {
      try {
         return (CredentialValidationResult)CredentialValidationResult.class.cast(MethodHandles.lookup().bind(this, "validate", MethodType.methodType(CredentialValidationResult.class, credential.getClass())).invoke(credential));
      } catch (NoSuchMethodException var3) {
         return CredentialValidationResult.NOT_VALIDATED_RESULT;
      } catch (Throwable var4) {
         throw new IllegalStateException(var4);
      }
   }

   default Set getCallerGroups(CredentialValidationResult validationResult) {
      return Collections.emptySet();
   }

   default int priority() {
      return 100;
   }

   default Set validationTypes() {
      return DEFAULT_VALIDATION_TYPES;
   }

   public static enum ValidationType {
      VALIDATE,
      PROVIDE_GROUPS;
   }
}
