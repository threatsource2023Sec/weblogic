package weblogic.ant.taskdefs.build;

import java.io.File;
import org.apache.tools.ant.BuildException;

public class DDCreateTask extends BaseTask {
   private File dir;
   private File file;

   public void setdir(File dir) {
      this.dir = dir;
   }

   public File getdir() {
      return this.dir;
   }

   public void setfile(File file) {
      this.file = file;
   }

   public File getfile() {
      return this.file;
   }

   private void checkDir() throws BuildException {
      if (!this.dir.exists()) {
         throw new BuildException("dir: " + this.dir.getAbsolutePath() + " does not exist.  Ensure this directory exists, and your user has permission to read it.");
      } else if (!this.dir.isDirectory()) {
         throw new BuildException("dir: " + this.dir.getAbsolutePath() + " is not a directory.");
      }
   }

   private void checkFile() throws BuildException {
      if (!this.file.exists()) {
         throw new BuildException("file: " + this.file.getAbsolutePath() + " does not exist.  Ensure this file exists, and your user has permission to read it.");
      }
   }

   private void checkParameters() throws BuildException {
      if (this.dir == null && this.file == null) {
         throw new BuildException("You must set either the dir or file attribute");
      } else if (this.dir != null && this.file != null) {
         throw new BuildException("You cannot set both the dir and file attributes");
      } else {
         if (this.dir != null) {
            this.checkDir();
         } else {
            this.checkFile();
         }

      }
   }

   public void privateExecute() throws BuildException {
      throw new AssertionError("DDCreateTask disabled");
   }
}
