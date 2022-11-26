package org.python.modules.posix;

import java.util.Locale;
import org.python.core.PySystemState;

enum OS {
   NT("Windows", new String[][]{{"cmd.exe", "/c"}, {"command.com", "/c"}}),
   IBMi("OS/400", new String[][]{{"/QOpenSys/usr/bin/sh", "-c"}}),
   POSIX(new String[][]{{"/bin/sh", "-c"}});

   private final String[][] shellCommands;
   private final String pattern;

   private OS(String pattern, String[]... shellCommands) {
      this.shellCommands = shellCommands;
      this.pattern = pattern != null ? pattern : this.name();
   }

   private OS(String[]... shellCommands) {
      this((String)null, shellCommands);
   }

   String getModuleName() {
      return this.name().toLowerCase(Locale.ROOT);
   }

   String[][] getShellCommands() {
      return this.shellCommands;
   }

   static OS getOS() {
      String osName = PySystemState.registry.getProperty("python.os");
      if (osName == null) {
         osName = System.getProperty("os.name");
      }

      OS[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         OS os = var1[var3];
         if (osName.startsWith(os.pattern)) {
            return os;
         }
      }

      return POSIX;
   }
}
