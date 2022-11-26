package weblogic.ant.taskdefs.derby;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Java;

public class Derby extends Java {
   private static final String DERBY_MAIN_CLASS = "org.apache.derby.drda.NetworkServerControl";
   private static final int DEFAULT_PORT = 1527;
   private String host = null;
   private Integer port = null;
   private String systemHome = null;
   private String operation = "ping";
   private boolean execFailed = false;

   public void setHost(String host) {
      this.host = host;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public void setOperation(String op) {
      this.operation = op;
   }

   public void setSystemHome(String home) {
      this.systemHome = home;
   }

   public void execute() throws BuildException {
      this.executeDerby();
   }

   private void executeDerby() {
      this.setFork(true);
      this.setSpawn(true);
      this.setClassname("org.apache.derby.drda.NetworkServerControl");
      this.setProperty("derby.system.home", this.systemHome);
      if (this.port != null) {
         this.setProperty("derby.drda.portNumber", this.port);
      }

      if (this.host != null) {
         this.setProperty("derby.drda.host", this.host);
      }

      if (this.operation != null) {
         this.createArg().setValue(this.operation);
      }

      this.createArg().setValue("-noSecurityManager");
      System.out.println("invoking: " + this.getCommandLine());
      if (this.executeJava() != 0) {
         System.out.println("failed: ");
         this.execFailed = true;
      }

      System.out.println("finished");
   }

   private void setProperty(String property, Object value) {
      if (value != null) {
         this.createJvmarg().setValue("-D" + property + "=" + value.toString());
      }

   }

   private void setProperty(String property, boolean bool) {
      if (bool) {
         this.createJvmarg().setValue("-D" + property);
      }

   }

   public void run() {
      this.executeDerby();
   }
}
