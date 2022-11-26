package weblogic.deployment.configuration;

public interface ValidationResult {
   boolean isDeploymentValid();

   Throwable getException();
}
