package weblogic.ant.taskdefs.path;

import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

public class PathModTask extends Task {
   private String m_oldPathPrefix;
   private String m_newPathPrefix;
   private String m_propertyName;
   private String m_inputPath;

   public void setOldPathPrefix(String path) {
      this.m_oldPathPrefix = path;
      if (System.getProperty("os.name").indexOf("Windows") > -1) {
         this.m_oldPathPrefix = this.m_oldPathPrefix.replace('/', File.separatorChar);
      }

   }

   public void setNewPathPrefix(String path) {
      this.m_newPathPrefix = path;
      if (System.getProperty("os.name").indexOf("Windows") > -1) {
         this.m_newPathPrefix = this.m_newPathPrefix.replace('/', File.separatorChar);
      }

   }

   public void setPropertyName(String name) {
      this.m_propertyName = name;
   }

   public void setInputPath(String path) {
      this.m_inputPath = path;
   }

   public void execute() throws BuildException {
      Project project = this.getProject();
      String result = "";
      System.out.println("input source path - " + this.m_inputPath);
      boolean match = this.m_inputPath.regionMatches(true, 0, this.m_oldPathPrefix, 0, this.m_oldPathPrefix.length());
      if (match) {
         String temp = this.m_inputPath.substring(this.m_oldPathPrefix.length());
         result = this.m_newPathPrefix + temp;
         result = result.replace(File.separatorChar, '/');
         project.setUserProperty(this.m_propertyName, result);
         System.out.println("output build path - " + result);
      } else {
         throw new BuildException("Unable to match old path prefix: " + this.m_oldPathPrefix + " to input path: " + this.m_inputPath);
      }
   }
}
