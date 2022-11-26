package weblogic.iiop.ior;

public enum RequirementType {
   NONE,
   SUPPORTED,
   REQUIRED;

   public static RequirementType toRequirementType(boolean supported, boolean required) {
      if (required) {
         return REQUIRED;
      } else {
         return supported ? SUPPORTED : NONE;
      }
   }
}
