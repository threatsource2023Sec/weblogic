package weblogic.diagnostics.snmp.cmdline;

import java.util.HashSet;
import java.util.Set;
import monfox.toolkit.snmp.Main;
import monfox.toolkit.snmp.SnmpFramework;
import weblogic.diagnostics.snmp.i18n.SNMPManagerTextFormatter;

public class Manager {
   private static final SNMPManagerTextFormatter TF = new SNMPManagerTextFormatter();
   private static final String[] COMMANDS = new String[]{"SnmpGet", "SnmpGetAll", "SnmpGetNext", "SnmpGetBulk", "SnmpGetBulk", "SnmpWalk", "SnmpBulkWalk", "SnmpTrapMonitor", "SnmpTrap", "SnmpTrapLogger", "SnmpInform"};
   private static Set validCommands = new HashSet();

   public static void main(String[] args) {
      try {
         if (args == null || args.length == 0 || !isValidCommand(args[0])) {
            printUsage();
            System.exit(0);
         }

         SnmpFramework.isUTF8ToStringSupported(true);
         SnmpFramework.getCharacterSetSupport().setDefaultCharSet("UTF-8");
         Main.main(args);
      } catch (Exception var2) {
         var2.printStackTrace();
         System.out.println("  ERROR: " + var2);
      }

   }

   private static boolean isValidCommand(String cmd) {
      if (validCommands.isEmpty()) {
         for(int i = 0; i < COMMANDS.length; ++i) {
            validCommands.add(COMMANDS[i]);
         }
      }

      return validCommands.contains(cmd);
   }

   private static void printUsage() {
      String text = TF.getUsageText();
      System.out.println(text);
   }
}
