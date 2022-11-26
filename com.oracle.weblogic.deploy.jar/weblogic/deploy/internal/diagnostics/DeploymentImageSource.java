package weblogic.deploy.internal.diagnostics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import weblogic.application.utils.XMLWriter;
import weblogic.deploy.service.internal.adminserver.AdminDeploymentServiceImageProvider;
import weblogic.deploy.service.internal.targetserver.TargetDeploymentServiceImageProvider;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.management.deploy.DeploymentTaskImageProvider;
import weblogic.management.deploy.internal.AppRuntimeStateImageProvider;

public class DeploymentImageSource implements ImageSource {
   private boolean timedOut = false;
   private ImageProvider[] ips = null;

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      XMLWriter writer = null;

      try {
         writer = new XMLWriter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")));
         writer.addElement("deployment");
         this.ips = DeploymentImageSource.IPFactory.getProviders();

         for(int i = 0; i < this.ips.length && !this.timedOut; ++i) {
            this.ips[i].writeDiagnosticImage(writer);
         }
      } catch (IOException var7) {
         throw new ImageSourceCreationException(var7);
      } finally {
         if (writer != null) {
            writer.finish();
         }

      }

   }

   public void timeoutImageCreation() {
      this.timedOut = true;

      for(int i = 0; i < this.ips.length && !this.timedOut; ++i) {
         this.ips[i].timeoutImageCreation();
      }

   }

   private static class IPFactory {
      public static ImageProvider[] getProviders() {
         return new ImageProvider[]{new AppRuntimeStateImageProvider(), new DeploymentTaskImageProvider(), new AdminDeploymentServiceImageProvider(), new TargetDeploymentServiceImageProvider()};
      }
   }
}
