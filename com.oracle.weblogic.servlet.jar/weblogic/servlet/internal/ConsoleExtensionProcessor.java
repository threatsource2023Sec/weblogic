package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.kernel.KernelStatus;

public class ConsoleExtensionProcessor extends AbstractWarExtensionProcessor {
   private static final String CONSOLE_EXTENSION_EXTRACT_ROOT = "console-ext";
   private static final String CONSOLE = "console";
   private static final String CONSOLEHELP = "consolehelp";
   private static final String CONSOLEHELPWAR = "consolehelp.war";
   private War war;

   public ConsoleExtensionProcessor(War war) throws IOException {
      this.war = war;
   }

   protected List getExtensionJarFiles() {
      ConsoleExtensionManager manager = ConsoleExtensionManager.getInstance();
      ConsoleExtensionManager.ExtensionDef[] extDefs = manager.findExtensions();
      String uri = this.getWarUri();

      List var4;
      try {
         if (extDefs != null && extDefs.length != 0) {
            List extFiles = new ArrayList();
            ConsoleExtensionManager.ExtensionDef[] var5 = extDefs;
            int var6 = extDefs.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ConsoleExtensionManager.ExtensionDef extDef = var5[var7];
               if (manager.shouldIncludeExtension(uri, extDef)) {
                  extFiles.add(extDef.getFile());
               }
            }

            ArrayList var13 = extFiles;
            return var13;
         }

         var4 = Collections.EMPTY_LIST;
      } finally {
         manager.release();
      }

      return var4;
   }

   protected File getWarExtractRoot() {
      return this.war.getExtractDir();
   }

   protected String getExtensionExtractRoot() {
      return "console-ext";
   }

   protected String getWarUri() {
      return this.war.getURI();
   }

   public boolean isSupport() {
      String uri = this.war.getURI();
      return support(uri);
   }

   public static boolean support(String uri) {
      if (!KernelStatus.isServer()) {
         return false;
      } else {
         return uri.equals("console") || uri.equals("consolehelp") || uri.equals("consolehelp.war");
      }
   }

   public static String getConsoleAppName() {
      return "console";
   }
}
