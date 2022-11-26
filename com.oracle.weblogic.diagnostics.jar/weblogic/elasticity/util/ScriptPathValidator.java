package weblogic.elasticity.util;

import java.io.File;
import java.lang.annotation.Annotation;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.GlobalServiceLocator;

public class ScriptPathValidator {
   private static final DiagnosticsTextWatchTextFormatter txtFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   private static RuntimeAccess runtimeAccess = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
   private static String domainRootDir;
   private static final String domainScriptsDir;

   public static String getDomainScriptsPath() {
      return domainScriptsDir;
   }

   public static String buildScriptPath(String pathToScript) {
      if (pathToScript != null && !pathToScript.trim().isEmpty()) {
         String returnPath = pathToScript.trim();
         File scriptLocation = new File(pathToScript);
         if (scriptLocation.isAbsolute()) {
            if (!scriptLocation.getAbsolutePath().startsWith(domainScriptsDir)) {
               throw new IllegalArgumentException(txtFormatter.getInvalidScriptActionScriptPath(scriptLocation.getPath(), domainScriptsDir));
            }
         } else {
            returnPath = getDomainScriptsPath() + File.separator + pathToScript;
         }

         return returnPath;
      } else {
         throw new NullPointerException();
      }
   }

   static {
      domainRootDir = runtimeAccess.getDomain().getRootDirectory();
      domainScriptsDir = domainRootDir + File.separatorChar + "bin" + File.separatorChar + "scripts";
   }
}
