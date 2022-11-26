package weblogic.management.configuration;

import weblogic.management.internal.ManagementTextTextFormatter;

public class ScriptInterceptorValidator {
   private static final ManagementTextTextFormatter txtFmt = ManagementTextTextFormatter.getInstance();

   public static void validateInterceptor(ScriptInterceptorMBean config) throws IllegalArgumentException {
      if (config != null) {
         if (!config.getPreProcessor().isSet("PathToScript") && !config.getPostProcessor().isSet("PathToScript")) {
            throw new IllegalArgumentException(txtFmt.getScriptInterceptorNoScriptConfiguredText(config.getName()));
         }
      }
   }
}
