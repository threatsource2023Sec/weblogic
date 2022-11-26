package weblogic.ant.taskdefs.utils;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;

public class FindTask extends MatchingTask {
   private File targetFile;
   private File sourceDirectory;
   private String propertyName;
   private boolean matchAll = false;

   public void setTargetFile(File targetFile) {
      this.targetFile = targetFile;
   }

   public void setSourceDirectory(File sourceDirectory) {
      this.sourceDirectory = sourceDirectory;
   }

   public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
   }

   public void setMatchAll(boolean matchAll) {
      this.matchAll = matchAll;
   }

   public void execute() throws BuildException {
      Project project = this.getProject();

      try {
         StringBuffer fileList = new StringBuffer(128);
         DirectoryScanner ds = this.getDirectoryScanner(this.sourceDirectory);
         ds.scan();
         long targetModified = this.targetFile.lastModified();
         String[] sources = ds.getIncludedFiles();

         for(int i = 0; i < sources.length; ++i) {
            File sourceFile = new File(this.sourceDirectory, sources[i]);
            project.log("Testing " + sourceFile.getAbsolutePath(), 3);
            if (this.matchAll || sourceFile.lastModified() > targetModified && !sourceFile.getCanonicalPath().equals(this.targetFile.getCanonicalPath())) {
               project.log("Matched " + sourceFile.getAbsolutePath(), 2);
               fileList.append(" " + sourceFile.getAbsolutePath());
            }
         }

         this.getProject().setProperty(this.propertyName, fileList.toString());
      } catch (Exception var9) {
         var9.printStackTrace();
         throw new BuildException("Build generation failure", var9);
      }
   }
}
