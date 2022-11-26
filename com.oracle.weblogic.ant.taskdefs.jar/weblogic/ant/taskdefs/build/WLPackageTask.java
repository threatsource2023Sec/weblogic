package weblogic.ant.taskdefs.build;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;
import weblogic.application.SplitDirectoryConstants;

public final class WLPackageTask extends BaseTask implements SplitDirectoryConstants {
   private File toFile;
   private File toDir;
   private File srcDir;
   private File destDir;
   private List filesets = new ArrayList();

   public void setTofile(File toFile) {
      this.toFile = toFile;
   }

   public void setTodir(File toDir) {
      this.toDir = toDir;
   }

   public void setSrcdir(File srcDir) {
      this.srcDir = srcDir;
   }

   public void setDestdir(File destDir) {
      this.destDir = destDir;
   }

   public void addFileset(FileSet set) {
      this.filesets.add(set);
   }

   private void validateDir(String paramName, File dir) throws BuildException {
      if (dir == null) {
         throw new BuildException("Parameter " + paramName + " must be set.");
      } else if (!dir.exists()) {
         throw new BuildException(paramName + ": " + dir.getAbsolutePath() + " does not exist.");
      } else if (!dir.isDirectory()) {
         throw new BuildException(paramName + ": " + dir.getAbsolutePath() + " exists, but it is not a directory.");
      }
   }

   private void validateParameters() throws BuildException {
      if (this.toDir == null && this.toFile == null) {
         throw new BuildException("Either toFile or toDir must be set");
      } else if (this.toDir != null && this.toFile != null) {
         throw new BuildException("Either toFile or toDir cannot both be set");
      } else {
         if (this.toDir != null) {
            if (this.toDir.exists()) {
               if (!this.toDir.isDirectory()) {
                  throw new BuildException("toDir: " + this.toDir.getAbsolutePath() + " exists, but is not a directory");
               }
            } else if (!this.toDir.mkdirs()) {
               throw new BuildException("toDir: " + this.toDir.getAbsolutePath() + " does not exist, and we were unable to create it.");
            }
         }

         this.validateDir("srcdir", this.srcDir);
         this.validateDir("destdir", this.destDir);
      }
   }

   private FileSet[] buildFileSet() {
      if (this.filesets.size() > 0) {
         return (FileSet[])((FileSet[])this.filesets.toArray(new FileSet[this.filesets.size()]));
      } else {
         FileSet[] files = new FileSet[]{new FileSet(), null};
         files[0].setDir(this.srcDir);
         files[0].setExcludes("**/*.java build.xml .beabuild.txt");
         files[1] = new FileSet();
         files[1].setDir(this.destDir);
         files[1].setExcludes("**/*.java .beabuild.txt");
         return files;
      }
   }

   private void copy(FileSet[] files, File toDir) {
      Copy copyTask = (Copy)this.project.createTask("copy");
      copyTask.setTodir(toDir);
      copyTask.setIncludeEmptyDirs(false);
      copyTask.setPreserveLastModified(true);

      for(int i = 0; i < files.length; ++i) {
         copyTask.addFileset(files[i]);
      }

      copyTask.execute();
   }

   private void jar(File jarFile, FileSet[] files) {
      Jar jarTask = (Jar)this.project.createTask("jar");
      jarTask.setDestFile(jarFile);
      File manifest = new File(this.srcDir, "META-INF/MANIFEST.MF");
      if (manifest.exists()) {
         jarTask.setManifest(manifest);
      }

      for(int i = 0; i < files.length; ++i) {
         jarTask.addFileset(files[i]);
      }

      jarTask.execute();
   }

   public void privateExecute() throws BuildException {
      this.validateParameters();
      FileSet[] files = this.buildFileSet();
      if (this.toFile != null) {
         this.jar(this.toFile, files);
      } else {
         this.copy(files, this.toDir);
      }

   }
}
