package weblogic.security.service;

public final class DeploymentValidationResult {
   private int value;
   private String name = null;
   public static final DeploymentValidationResult EXISTS = new DeploymentValidationResult(1);
   public static final DeploymentValidationResult NOT_EXISTS = new DeploymentValidationResult(2);
   public static final DeploymentValidationResult UNKNOWN = new DeploymentValidationResult(3);

   private DeploymentValidationResult(int value) {
      this.value = value;
      if (value == 1) {
         this.name = "Exists";
      } else if (value == 2) {
         this.name = "Not Exists";
      } else if (value == 3) {
         this.name = "Unknown";
      }

   }

   public String toString() {
      return this.name;
   }
}
