package weblogic.ant.taskdefs;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import weblogic.Home;

public class WLPath extends Task {
   private String path = null;
   private String property = null;

   public void setPath(String path) {
      this.path = path;
   }

   public void setProperty(String property) {
      this.property = property;
   }

   public void execute() throws BuildException {
      File weblogicHome = Home.getFile();
      if (!weblogicHome.isDirectory()) {
         throw new BuildException("Invalid WebLogic Home: " + weblogicHome.getPath());
      } else {
         File weblogicPath = this.path != null ? new File(weblogicHome, this.path) : weblogicHome;
         if (this.property != null && weblogicPath.exists()) {
            this.getProject().setProperty(this.property, weblogicPath.getPath());
         }

      }
   }
}
