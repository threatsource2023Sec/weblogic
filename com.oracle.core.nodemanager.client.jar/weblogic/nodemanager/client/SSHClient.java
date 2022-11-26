package weblogic.nodemanager.client;

class SSHClient extends ShellClient {
   public static final String SSH_SHELL_COMMAND = "ssh -o PasswordAuthentication=no %H wlscontrol.sh -d %D -r %R -s %S %C";
   public static final int LISTEN_PORT = 22;

   SSHClient() {
      super(System.getProperty("weblogic.nodemanager.ShellCommand", "ssh -o PasswordAuthentication=no %H wlscontrol.sh -d %D -r %R -s %S %C"));
      this.host = "localhost";
      this.port = 22;
   }
}
