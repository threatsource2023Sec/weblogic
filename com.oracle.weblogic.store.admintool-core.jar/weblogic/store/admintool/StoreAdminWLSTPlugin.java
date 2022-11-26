package weblogic.store.admintool;

import java.io.InputStream;
import weblogic.kernel.Kernel;
import weblogic.management.scripting.plugin.WLSTPlugin;

public class StoreAdminWLSTPlugin extends WLSTPlugin {
   static final String PYTHON_COMMAND_FILE = "storeadminWLST.py";
   static final StoreAdmin sa = new StoreAdmin(new String[0], false);

   public static void initialize() {
      Kernel.ensureInitialized();
      InputStream i = StoreAdminIF.class.getResourceAsStream("storeadminWLST.py");

      assert i != null : "Could not load python command file for StoreAdmin";

      interpreter.execfile(i);
   }

   public static String[] getStoreNames() {
      return CommandImpls.getStoreNames(sa);
   }

   public static String[] getConnectionNames(String storeName) {
      return CommandImpls.getConnectionNames(sa, storeName);
   }

   public static boolean runCommand(String command) {
      return sa.execute(command);
   }

   public static String getCommand(CommandDefs.CommandType ct) {
      return ct == null ? "" : ct.toString();
   }

   public static String getParam(CommandDefs.CommandParam cp, String val) {
      if (cp == null) {
         return "";
      } else {
         if (cp.optCount() > 0) {
            if (val == null || val.trim().equals("")) {
               return "";
            }

            val = val.trim();
         }

         return cp.asString(val);
      }
   }
}
