package weblogic.diagnostics.accessor;

import java.io.IOException;
import java.io.InputStream;
import weblogic.management.scripting.plugin.WLSTPlugin;

public class AccessorPlugin extends WLSTPlugin {
   public static void initialize() {
      InputStream inStr = null;

      try {
         inStr = AccessorPlugin.class.getResourceAsStream("export.py");
         interpreter.execfile(inStr);
      } finally {
         if (inStr != null) {
            try {
               inStr.close();
            } catch (IOException var7) {
            }
         }

      }

   }
}
