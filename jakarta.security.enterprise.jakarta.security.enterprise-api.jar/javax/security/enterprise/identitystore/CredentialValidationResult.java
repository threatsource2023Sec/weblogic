package javax.security.enterprise.identitystore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.enterprise.CallerPrincipal;

public class CredentialValidationResult {
   public static final CredentialValidationResult INVALID_RESULT;
   public static final CredentialValidationResult NOT_VALIDATED_RESULT;
   private final Status status;
   private final String storeId;
   private final String callerDn;
   private final String callerUniqueId;
   private final CallerPrincipal callerPrincipal;
   private final Set groups;

   private CredentialValidationResult(Status status) {
      this(status, (String)null, (CallerPrincipal)null, (String)null, (String)null, (Set)null);
   }

   public CredentialValidationResult(String callerName) {
      this((CallerPrincipal)(new CallerPrincipal(callerName)), (Set)null);
   }

   public CredentialValidationResult(CallerPrincipal callerPrincipal) {
      this((CallerPrincipal)callerPrincipal, (Set)null);
   }

   public CredentialValidationResult(String callerName, Set groups) {
      this(new CallerPrincipal(callerName), groups);
   }

   public CredentialValidationResult(CallerPrincipal callerPrincipal, Set groups) {
      this((String)null, (CallerPrincipal)callerPrincipal, (String)null, (String)null, groups);
   }

   public CredentialValidationResult(String storeId, String callerName, String callerDn, String callerUniqueId, Set groups) {
      this(storeId, new CallerPrincipal(callerName), callerDn, callerUniqueId, groups);
   }

   public CredentialValidationResult(String storeId, CallerPrincipal callerPrincipal, String callerDn, String callerUniqueId, Set groups) {
      this(CredentialValidationResult.Status.VALID, storeId, callerPrincipal, callerDn, callerUniqueId, groups);
   }

   private CredentialValidationResult(Status status, String storeId, CallerPrincipal callerPrincipal, String callerDn, String callerUniqueId, Set groups) {
      if (status == CredentialValidationResult.Status.VALID || storeId == null && callerPrincipal == null && callerDn == null && callerUniqueId == null && groups == null) {
         if (status == CredentialValidationResult.Status.VALID && (callerPrincipal == null || callerPrincipal.getName().trim().isEmpty())) {
            throw new IllegalArgumentException("Null or empty CallerPrincipal");
         } else {
            this.status = status;
            this.storeId = storeId;
            this.callerPrincipal = callerPrincipal;
            this.callerDn = callerDn;
            this.callerUniqueId = callerUniqueId;
            this.groups = groups != null ? Collections.unmodifiableSet(new HashSet(groups)) : Collections.emptySet();
         }
      } else {
         throw new IllegalArgumentException("Bad status");
      }
   }

   public Status getStatus() {
      return this.status;
   }

   public String getIdentityStoreId() {
      return this.storeId;
   }

   public CallerPrincipal getCallerPrincipal() {
      return this.callerPrincipal;
   }

   public String getCallerUniqueId() {
      return this.callerUniqueId;
   }

   public String getCallerDn() {
      return this.callerDn;
   }

   public Set getCallerGroups() {
      return this.groups;
   }

   static {
      INVALID_RESULT = new CredentialValidationResult(CredentialValidationResult.Status.INVALID);
      NOT_VALIDATED_RESULT = new CredentialValidationResult(CredentialValidationResult.Status.NOT_VALIDATED);
   }

   public static enum Status {
      NOT_VALIDATED,
      INVALID,
      VALID;
   }
}
