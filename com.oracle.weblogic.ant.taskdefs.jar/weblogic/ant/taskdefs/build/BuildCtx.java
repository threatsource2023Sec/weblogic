package weblogic.ant.taskdefs.build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Javac;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.DescriptorUpdater;
import weblogic.application.library.LibraryContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

public final class BuildCtx implements LibraryContext, DescriptorUpdater {
   private Project project;
   private Javac javacTask;
   private File srcDir;
   private File destDir;
   private ApplicationDescriptor appDesc;
   private final Collection libraryModules = new ArrayList();
   private final MultiClassFinder classFinders = new MultiClassFinder();
   private Map contextRootOverrideMap;

   public Project getProject() {
      return this.project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public Javac getJavacTask() {
      return this.javacTask;
   }

   public void setJavacTask(Javac javacTask) {
      this.javacTask = javacTask;
   }

   public File getSrcDir() {
      return this.srcDir;
   }

   public void setSrcDir(File srcDir) {
      this.srcDir = srcDir;
   }

   public File getDestDir() {
      return this.destDir;
   }

   public void setDestDir(File destDir) {
      this.destDir = destDir;
   }

   public void registerLink(File f) {
      this.registerLink((String)null, f);
   }

   public void registerLink(String uri, File f) {
      this.libraryModules.add(f);
   }

   public ApplicationBean getApplicationDD() {
      try {
         return this.appDesc.getApplicationDescriptor();
      } catch (IOException var2) {
         throw new AssertionError(var2);
      } catch (XMLStreamException var3) {
         throw new AssertionError(var3);
      }
   }

   public ApplicationDescriptor getApplicationDescriptor() {
      return this.appDesc;
   }

   public void notifyDescriptorUpdate() {
   }

   public void addClassFinder(ClassFinder in) {
      this.classFinders.addFinder(in);
   }

   public void addInstanceAppLibClassFinder(ClassFinder finder) {
   }

   public void addSharedAppLibClassFinder(ClassFinder finder) {
   }

   public ClassFinder getLibraryClassFinder() {
      return this.classFinders;
   }

   public String getRefappName() {
      return this.srcDir.getName();
   }

   public File[] getLibraryFiles() {
      return (File[])((File[])this.libraryModules.toArray(new File[this.libraryModules.size()]));
   }

   public void setApplicationDescriptor(ApplicationDescriptor appDesc) {
      this.appDesc = appDesc;
   }

   public String getRefappUri() {
      return this.srcDir == null ? null : this.srcDir.getPath();
   }

   public Map getContextRootOverrideMap() {
      return this.contextRootOverrideMap;
   }

   public void setContextRootOverrideMap(Map map) {
      this.contextRootOverrideMap = map;
   }

   public void addLibraryClassInfoFinderFirst(ClassInfoFinder classInfoFinder) {
   }
}
