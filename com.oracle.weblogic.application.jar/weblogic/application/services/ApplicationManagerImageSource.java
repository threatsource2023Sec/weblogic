package weblogic.application.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import weblogic.application.Deployment;
import weblogic.application.DeploymentManager;
import weblogic.application.DeploymentWrapper;
import weblogic.application.utils.XMLWriter;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;

public class ApplicationManagerImageSource implements ImageSource {
   private boolean timedOut = false;

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      XMLWriter writer = null;

      try {
         writer = new XMLWriter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")));
         writer.addElement("application-container");
         Iterator iter = DeploymentManager.getDeploymentManager().getDeployments();

         while(iter.hasNext() && !this.timedOut) {
            Deployment d = (Deployment)iter.next();
            writer.addElement("application");

            while(d instanceof DeploymentWrapper) {
               d = ((DeploymentWrapper)d).getDeployment();
            }

            writer.addElement("deployment-class", d.getClass().getName());
            d.getApplicationContext().writeDiagnosticImage(writer);
            writer.closeElement();
         }
      } catch (IOException var8) {
         throw new ImageSourceCreationException(var8);
      } finally {
         if (writer != null) {
            writer.finish();
         }

      }

   }

   public void timeoutImageCreation() {
      this.timedOut = true;
   }
}
