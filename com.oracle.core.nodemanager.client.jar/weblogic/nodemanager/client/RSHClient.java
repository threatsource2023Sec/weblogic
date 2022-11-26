package weblogic.nodemanager.client;

class RSHClient extends ShellClient {
   private static final String RSH_PROGRAM;
   public static final String RSH_SHELL_COMMAND;
   public static final int LISTEN_PORT = 514;

   RSHClient() {
      super(System.getProperty("weblogic.nodemanager.ShellCommand", RSH_SHELL_COMMAND));
      this.host = "localhost";
      this.port = 514;
   }

   static {
      if (System.getProperty("os.name").equalsIgnoreCase("HP-UX")) {
         RSH_PROGRAM = "remsh";
      } else {
         RSH_PROGRAM = "rsh";
      }

      RSH_SHELL_COMMAND = RSH_PROGRAM + " %H " + "wlscontrol.sh -d %D -r %R -s %S %C";
   }
}
