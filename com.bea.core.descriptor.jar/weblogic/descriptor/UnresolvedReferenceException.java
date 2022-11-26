package weblogic.descriptor;

public class UnresolvedReferenceException extends IllegalArgumentException {
   private final String reference;
   private final String referer;

   public UnresolvedReferenceException(String reference, String referer) {
      super("Unresolved reference to " + reference + " by " + referer);
      this.reference = reference;
      this.referer = referer;
   }

   public String getReference() {
      return this.reference;
   }

   public String getReferer() {
      return this.referer;
   }
}
