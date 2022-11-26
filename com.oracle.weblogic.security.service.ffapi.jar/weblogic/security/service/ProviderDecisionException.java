package weblogic.security.service;

/** @deprecated */
@Deprecated
public class ProviderDecisionException extends SecurityServiceException {
   public ProviderDecisionException() {
   }

   public ProviderDecisionException(String msg) {
      super(msg);
   }

   public ProviderDecisionException(Throwable nested) {
      super(nested);
   }

   public ProviderDecisionException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
