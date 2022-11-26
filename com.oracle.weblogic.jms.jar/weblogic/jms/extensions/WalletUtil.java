package weblogic.jms.extensions;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import oracle.security.pki.OracleSecretStore;
import oracle.security.pki.OracleSecretStoreException;
import oracle.security.pki.OracleWallet;

public class WalletUtil {
   private static final String DEFAULT_WALLET = "cwallet.sso";
   private static final String CREATE = "create";
   private static final String ADD = "add";
   private static final String REPLACE = "replace";
   private static final String REMOVE = "remove";
   private static final String DUMP = "dump";
   private static final String HELP = "help";
   private static final String EOL = System.getProperty("line.separator");
   private static final String HELP_DESC;
   private OracleWallet wallet;
   private OracleSecretStore store;

   public static void main(String[] args) {
      (new WalletUtil()).doWork(args);
   }

   private void doWork(String[] args) {
      String cmd = null;

      try {
         if (args.length == 0) {
            this.usage(1);
         }

         cmd = args[0];
         if (cmd.equals("create")) {
            this.create(args);
         } else if (cmd.equals("add")) {
            this.addOrReplace(args, "Add");
         } else if (cmd.equals("replace")) {
            this.addOrReplace(args, "Replace");
         } else if (cmd.equals("remove")) {
            this.remove(args);
         } else if (cmd.equals("dump")) {
            this.dump(args);
         } else if (cmd.equals("help")) {
            if (args.length == 1) {
               this.usage(0);
            } else {
               this.usage(1);
            }
         } else {
            this.usage(1);
         }
      } catch (Throwable var4) {
         System.err.println("Error: '" + cmd + "' may have failed. Unexpected exception.");
         var4.printStackTrace(System.err);
         System.exit(1);
      }

   }

   private void create(String[] args) throws IOException, OracleSecretStoreException {
      int length = args.length;
      String dir = null;
      if (length == 1 || length > 2) {
         this.usage(1);
      }

      dir = args[1];
      dir = this.checkDirForCreate(dir);
      this.wallet = new OracleWallet();
      this.wallet.createSSO();
      this.wallet.saveAs(dir);
      System.out.println("Info: Created wallet under directory '" + dir + "'.");
   }

   private void addOrReplace(String[] args, String cmd) throws IOException, OracleSecretStoreException {
      int length = args.length;
      String dir = null;
      String alias = null;
      String value = null;
      if (length > 4) {
         this.usage(1);
      }

      if (length < 3) {
         this.usage("Error: " + cmd + " failed. Both alias and value are required.");
      }

      alias = args[1];
      value = args[2];
      if (length == 4) {
         dir = args[3];
      }

      dir = this.checkDir(dir, cmd, alias);
      this.openWallet(dir);
      if (this.store.containsAlias(alias) && cmd.equalsIgnoreCase("add")) {
         this.exit("Error: Add failed. Alias '" + alias + "' already exists.");
      }

      this.store.setSecret(alias, value.toCharArray());
      this.saveWallet(dir);
      if (cmd.equalsIgnoreCase("add")) {
         System.out.println("Info: Added alias '" + alias + "'.");
      } else {
         System.out.println("Info: Replaced alias '" + alias + "'.");
      }

   }

   private void remove(String[] args) throws IOException, OracleSecretStoreException {
      int length = args.length;
      String dir = null;
      String alias = null;
      if (length > 3) {
         this.usage(1);
      }

      if (length < 2) {
         this.usage("Error: Remove failed. An alias is required.");
      }

      alias = args[1];
      if (length == 3) {
         dir = args[2];
      }

      dir = this.checkDir(dir, "Remove", alias);
      this.openWallet(dir);
      if (!this.store.containsAlias(alias)) {
         System.out.println("Info: Alias '" + alias + "' not found.");
      } else {
         this.store.deleteSecret(alias);
         this.saveWallet(dir);
         System.out.println("Info: Removed alias '" + alias + "'.");
      }

   }

   private void dump(String[] args) throws IOException, OracleSecretStoreException {
      int length = args.length;
      String dir = null;
      if (length > 2) {
         this.usage(1);
      }

      if (length == 2) {
         dir = args[1];
      }

      dir = this.checkDir(dir, "Dump", (String)null);
      this.openWallet(dir);
      Enumeration list = this.store.internalAliases();
      System.out.println("Info: Aliases found in wallet under '" + dir + "'.");

      int count;
      for(count = 0; list.hasMoreElements(); ++count) {
         String alias = (String)list.nextElement();
         System.out.println(alias);
      }

      if (count == 1) {
         System.out.println("Info: " + count + " alias found.");
      } else {
         System.out.println("Info: " + count + " aliases found.");
      }

   }

   private String checkDirForCreate(String dir) {
      if (dir == null) {
         dir = ".";
      }

      if (!(new File(dir)).exists()) {
         this.exit("Error: Create failed. The directory for wallet location '" + dir + "' does not exist.");
      }

      if ((new File(dir + File.separator + "cwallet.sso")).exists()) {
         this.exit("Error: Create failed. Wallet under  '" + dir + "' already exists.");
      }

      return dir;
   }

   private String checkDir(String dir, String cmd, String alias) {
      if (dir == null) {
         dir = ".";
      }

      if (!(new File(dir + File.separator + "cwallet.sso")).exists()) {
         if (!cmd.equalsIgnoreCase("dump")) {
            this.exit("Error: " + cmd + " failed. Could not " + cmd.toLowerCase() + " alias '" + alias + "'.  Wallet under '" + dir + "' not found.");
         } else {
            this.exit("Error: Dump failed.  Wallet file under '" + dir + "' not found.");
         }
      }

      return dir;
   }

   private void openWallet(String file) throws IOException, OracleSecretStoreException {
      this.wallet = new OracleWallet();
      this.wallet.open(file, (char[])null);
      this.store = this.wallet.getSecretStore();
   }

   private void saveWallet(String file) throws IOException, OracleSecretStoreException {
      this.wallet.setSecretStore(this.store);
      this.wallet.saveAs(file);
   }

   private void exit(String error) {
      System.err.println(error);
      System.exit(1);
   }

   private void usage(String error) {
      System.err.println(error);
      this.usage(1);
   }

   private void usage(int exitCode) {
      if (exitCode == 0) {
         System.out.println(HELP_DESC);
      } else {
         System.err.println(HELP_DESC);
      }

      System.exit(exitCode);
   }

   static {
      HELP_DESC = "Usage:" + EOL + EOL + "create <dir>                    : Create wallet under given directory." + EOL + "add <alias> <value> [dir]       : Add value using the alias." + EOL + "replace <alias> <value> [dir]   : Replace value of the alias." + EOL + "remove <alias> [dir]            : Remove an alias." + EOL + "dump [dir]                      : List all aliases in the wallet." + EOL + "help                            : This help.";
   }
}
