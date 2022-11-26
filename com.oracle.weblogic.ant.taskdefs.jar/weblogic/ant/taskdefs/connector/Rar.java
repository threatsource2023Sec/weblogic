package weblogic.ant.taskdefs.connector;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.ZipFileSet;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.zip.ZipOutputStream;

public class Rar extends Jar {
   private File deploymentDescriptor;
   private File weblogicDeploymentDescriptor;
   private boolean descriptorAdded;
   private static final FileUtils fu = FileUtils.newFileUtils();

   public Rar() {
      this.archiveType = "rar";
      this.emptyBehavior = "create";
   }

   /** @deprecated */
   @Deprecated
   public void setWarfile(File warFile) {
      this.setDestFile(warFile);
   }

   public void setRaxml(File descr) {
      this.deploymentDescriptor = descr;
      if (!this.deploymentDescriptor.exists()) {
         throw new BuildException("Deployment descriptor: " + this.deploymentDescriptor + " does not exist.");
      } else {
         ZipFileSet fs = new ZipFileSet();
         fs.setFile(this.deploymentDescriptor);
         fs.setFullpath("META-INF/ra.xml");
         super.addFileset(fs);
      }
   }

   public void setWeblogicRaxml(File descr) {
      this.weblogicDeploymentDescriptor = descr;
      if (!this.weblogicDeploymentDescriptor.exists()) {
         throw new BuildException("Weblogic Deployment descriptor: " + this.weblogicDeploymentDescriptor + " does not exist.");
      } else {
         ZipFileSet fs = new ZipFileSet();
         fs.setFile(this.weblogicDeploymentDescriptor);
         fs.setFullpath("META-INF/weblogic-ra.xml");
         super.addFileset(fs);
      }
   }

   public void addLib(ZipFileSet fs) {
      super.addFileset(fs);
   }

   public void addClasses(ZipFileSet fs) {
      super.addFileset(fs);
   }

   protected void initZipOutputStream(ZipOutputStream zOut) throws IOException, BuildException {
      if (this.deploymentDescriptor == null && !this.isInUpdateMode()) {
         throw new BuildException("raxml attribute is required", this.getLocation());
      } else {
         super.initZipOutputStream(zOut);
      }
   }

   protected void zipFile(File file, ZipOutputStream zOut, String vPath, int mode) throws IOException {
      if (vPath.equalsIgnoreCase("META-INF/ra.xml")) {
         if (this.deploymentDescriptor != null && fu.fileNameEquals(this.deploymentDescriptor, file) && !this.descriptorAdded) {
            super.zipFile(file, zOut, vPath, mode);
            this.descriptorAdded = true;
         } else {
            this.log("Warning: selected " + this.archiveType + " files include a META-INF/ra.xml which will be ignored (please use raxmll attribute to " + this.archiveType + " task)", 1);
         }
      } else {
         super.zipFile(file, zOut, vPath, mode);
      }

   }

   protected void cleanUp() {
      this.descriptorAdded = false;
      super.cleanUp();
   }
}
