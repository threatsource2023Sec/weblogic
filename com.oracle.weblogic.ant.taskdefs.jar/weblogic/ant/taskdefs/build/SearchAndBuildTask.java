package weblogic.ant.taskdefs.build;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Ant;
import org.apache.tools.ant.types.FileSet;

public class SearchAndBuildTask extends Task {
   private FileSet m_fileSet;
   private String m_target;

   public void execute() throws BuildException {
      String[] includedFiles = this.m_fileSet.getDirectoryScanner(this.project).getIncludedFiles();

      for(int i = 0; i < includedFiles.length; ++i) {
         System.out.println("###################################################");
         System.out.println("currently executing build file: " + includedFiles[i]);
         Ant ant = (Ant)this.project.createTask("ant");
         String pathToBuild = includedFiles[i].substring(0, includedFiles[i].lastIndexOf(File.separatorChar));
         pathToBuild = this.project.getBaseDir().getPath() + File.separatorChar + pathToBuild;
         File base = new File(pathToBuild);
         ant.setDir(base);
         if (this.m_target != null) {
            ant.setTarget(this.m_target);
         }

         String antFile = includedFiles[i].substring(includedFiles[i].lastIndexOf(File.separatorChar) + 1);
         System.out.println("ant file: " + antFile);
         ant.setAntfile(antFile);
         ant.setInheritAll(false);
         ant.execute();
      }

   }

   public void setTarget(String value) {
      this.m_target = value;
   }

   public void addConfiguredFileSet(FileSet fileSet) {
      this.m_fileSet = fileSet;
   }
}
