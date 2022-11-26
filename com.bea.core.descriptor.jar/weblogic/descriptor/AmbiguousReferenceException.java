package weblogic.descriptor;

public class AmbiguousReferenceException extends IllegalArgumentException {
   private final String reference;
   private final String referer;

   public AmbiguousReferenceException(String reference, String referer, DescriptorBean[] candidates) {
      super(computeMessage(reference, referer, candidates));
      this.reference = reference;
      this.referer = referer;
   }

   public String getReference() {
      return this.reference;
   }

   public String getReferer() {
      return this.referer;
   }

   private static String computeMessage(String reference, String referer, DescriptorBean[] candidates) {
      String message = "Reference to " + reference + " by " + referer + " is ambigous;\nThe following beans share this name:";

      for(int i = 0; i < candidates.length; ++i) {
         message = message + "\n- " + candidates[i];
      }

      return message;
   }
}
