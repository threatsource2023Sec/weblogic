package weblogic.management.diagnostics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.management.DomainDir;

public class ConfigurationImageSource implements ImageSource {
   private static final String CONFIG;
   private static final String RUNNING_MANAGED_SERVER;
   private volatile boolean imageCreationTimeOut = false;
   private volatile boolean imageCreationInProgress = false;

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.imageCreationInProgress = true;
      PrintWriter printout = new PrintWriter(out);
      this.writeToStream(printout);
      this.imageCreationInProgress = false;
   }

   public void timeoutImageCreation() {
      if (this.imageCreationInProgress) {
         this.imageCreationTimeOut = true;
      }

   }

   private void writeToStream(PrintWriter out) throws ImageSourceCreationException {
      this.writeToStream(out, CONFIG);
      this.writeToStream(out, RUNNING_MANAGED_SERVER);
   }

   private void writeToStream(PrintWriter out, String file) throws ImageSourceCreationException {
      out.println("Contents of the file " + file + " Follows :-");
      if (!(new File(file)).exists()) {
         out.println("File " + file + " Not found on the disk");
      } else {
         BufferedReader bin = null;

         try {
            bin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            do {
               out.println(bin.readLine());
            } while(bin.readLine() != null);

            bin.close();
            out.flush();
            if (this.imageCreationTimeOut) {
               this.imageCreationTimeOut = false;
               this.imageCreationInProgress = false;
               throw new ImageSourceCreationException("Timeout called.Complete image could not be created");
            }
         } catch (FileNotFoundException var13) {
            this.imageCreationInProgress = false;
            throw new AssertionError("File does not exist" + file);
         } catch (IOException var14) {
            throw new ImageSourceCreationException("Image creation failed" + var14);
         } finally {
            try {
               bin.close();
            } catch (IOException var12) {
               this.imageCreationInProgress = false;
               throw new ImageSourceCreationException("Image creation failed" + var12);
            }
         }

      }
   }

   static {
      CONFIG = DomainDir.getRootDir() + File.separatorChar + "config.xml";
      RUNNING_MANAGED_SERVER = DomainDir.getRootDir() + File.separatorChar + "running-managed-servers.xml";
   }
}
