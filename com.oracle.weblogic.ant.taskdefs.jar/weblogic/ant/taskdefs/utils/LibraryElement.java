package weblogic.ant.taskdefs.utils;

import java.io.File;
import org.apache.tools.ant.BuildException;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;

public class LibraryElement {
   private File dir = null;
   private File location = null;
   private String name = null;
   private String specVersion = null;
   private String implVersion = null;

   public void setDir(File f) {
      this.dir = f;
   }

   public File getDir() {
      return this.dir;
   }

   public void setFile(File f) {
      this.location = f;
   }

   public void setName(String s) {
      this.name = s;
   }

   public void setSpecificationVersion(String s) {
      this.specVersion = s;
   }

   public void setImplementationVersion(String s) {
      this.implVersion = s;
   }

   public File getFile() {
      return this.location;
   }

   public String getName() {
      return this.name;
   }

   public String getSpecificationVersion() {
      return this.specVersion;
   }

   public String getImplementationVersion() {
      return this.implVersion;
   }

   public LibraryData getLibraryData() {
      LibraryData rtn = null;

      try {
         rtn = LibraryLoggingUtils.initLibraryData(this.getName(), this.getSpecificationVersion(), this.getImplementationVersion(), this.getFile());
         return rtn;
      } catch (LoggableLibraryProcessingException var3) {
         throw new BuildException(var3.getLoggable().getMessage());
      }
   }
}
