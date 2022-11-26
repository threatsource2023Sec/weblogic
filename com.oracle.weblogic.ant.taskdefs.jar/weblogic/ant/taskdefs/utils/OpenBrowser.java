package weblogic.ant.taskdefs.utils;

import java.awt.Desktop;
import java.net.URI;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class OpenBrowser extends Task {
   private String url = "";
   private String unixBrowser = "";
   private boolean failonerror = true;

   public void setUrl(String pUrl) {
      this.url = pUrl;
   }

   public void setUnixBrowser(String pBrowser) {
      this.unixBrowser = pBrowser;
   }

   public void setFailonerror(boolean failonerror) {
      this.failonerror = failonerror;
   }

   public void execute() throws BuildException {
      try {
         Desktop.getDesktop().browse(new URI(this.url));
      } catch (Exception var2) {
         if (this.failonerror) {
            throw new BuildException("Could not invoke browser since there is no GUI or default browser on the server. Please manually point your browser to \"" + this.url + "\".\n" + var2);
         }

         System.out.println("Could not invoke browser since there is no GUI or default browser on the server. Please manually point your browser to \"" + this.url + "\".\n" + var2.getMessage());
      }

   }
}
