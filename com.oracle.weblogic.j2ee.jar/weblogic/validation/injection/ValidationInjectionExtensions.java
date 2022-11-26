package weblogic.validation.injection;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.extensions.ExtensionManager;
import weblogic.j2ee.extensions.InjectionExtension;
import weblogic.validation.Jndi;

public enum ValidationInjectionExtensions implements InjectionExtension {
   VALIDATOR("javax.validation.Validator", Jndi.VALIDATOR.key),
   VALIDATOR_FACTORY("javax.validation.ValidatorFactory", Jndi.VALIDATOR_FACTORY.key);

   private String jndiName;

   private ValidationInjectionExtensions(String typeName, String aJndiName) {
      this.jndiName = aJndiName;
      ExtensionManager.instance.addInjectionExtension(typeName, this.jndiName, this);
      DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugValidation");
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Registering typeName:" + typeName + ", jndi key:" + this.jndiName);
      }

   }

   public String getName(String typeName) {
      return this.getName(typeName, (String)null);
   }

   public String getName(String typeName, String originalName) {
      DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugValidation");
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Returning jndiKey: " + originalName != null ? originalName : this.jndiName);
      }

      return originalName != null ? originalName : this.jndiName;
   }

   public static void registerExtensions() {
   }
}
