package weblogic.ant.taskdefs.pointbase;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Java;

public class PointBase extends Java implements Runnable {
   private static final String POINTBASE_MAIN_CLASS = "com.pointbase.net.netServer";
   private static final int DEFAULT_PORT = 9092;
   private boolean win = false;
   private int displayLevel = -1;
   private String database = null;
   private int port = 9092;
   private File logFile = null;
   private File pointbaseini = null;
   private boolean noconsole = true;
   private long timeout = 0L;
   private String home = null;
   private boolean execFailed = false;

   public void setWin(boolean win) {
      this.win = win;
   }

   public void setDisplayLevel(int displayLevel) {
      this.displayLevel = displayLevel;
   }

   public void setDatabase(String database) {
      this.database = database;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public void setFile(File logFile) {
      this.logFile = logFile;
   }

   public void setPointBaseIni(File pointbaseini) {
      this.pointbaseini = pointbaseini;
   }

   public void setNoConsole(boolean noconsole) {
      this.noconsole = noconsole;
   }

   public void setTimeout(long timeout) {
      this.timeout = timeout * 1000L;
   }

   public void setHome(String home) {
      this.home = home;
   }

   public void execute() throws BuildException {
      (new Thread(this, "Execute-PointBase")).start();
   }

   private void executePointBase() {
      this.setFork(true);
      this.setClassname("com.pointbase.net.netServer");
      this.setProperty("database.home", this.home);
      if (this.win) {
         this.createArg().setValue("/win");
      }

      if (this.displayLevel >= 0) {
         this.createArg().setValue("/d:" + this.displayLevel);
      }

      if (this.database != null) {
         this.createArg().setValue("/database:" + this.database);
      }

      this.createArg().setValue("/port:" + this.port);
      if (this.logFile != null) {
         this.createArg().setValue("/file=" + this.logFile.toString());
      }

      if (this.pointbaseini != null) {
         this.createArg().setValue("/pointbase.ini=" + this.pointbaseini.toString());
      }

      if (this.noconsole) {
         this.createArg().setValue("/noconsole");
      }

      if (this.executeJava() != 0) {
         this.execFailed = true;
      }

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
      this.executePointBase();
   }
}
