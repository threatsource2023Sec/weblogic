package weblogic.ant.taskdefs.management;

import com.bea.staxb.buildtime.Java2SchemaTask;
import com.bea.staxb.buildtime.internal.mbean.MBeanJava2SchemaTask;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import weblogic.descriptor.beangen.beangen;
import weblogic.utils.FileUtils;

public final class DescriptorGen extends Task {
   private static final String BEA_JAVA2SCHEMA_NAME = "weblogic.ant.taskdefs.management.DescriptorGen.java2schema";
   private static final String BEAN_SUFFIX = "Impl";
   private static final String BEAN_BASE_CLASS = "weblogic.descriptor.internal.AbstractDescriptorBean";
   private static final String BEAN_PATTERN = "**/*Bean.java";
   private static final String IMPL_PATTERN = "**/*BeanImpl.java";
   private File srcdir;
   private File destdir;
   private File bindingJarFile;
   private String targetNamespace;
   private Path classpath;

   public DescriptorGen() {
      this.classpath = Path.systemClasspath;
   }

   public void setClasspath(Path classpath) {
      this.classpath.append(classpath);
   }

   public Path getClasspath() {
      return this.classpath;
   }

   public Path createClasspath() {
      return this.classpath.createPath();
   }

   public void setSrcdir(File srcdir) {
      this.srcdir = srcdir;
   }

   public File getSrcdir() {
      return this.srcdir;
   }

   public void setDestdir(File destdir) {
      this.destdir = destdir;
   }

   public File getDestdir() {
      return this.destdir;
   }

   public void setBindingJarFile(File bindingJarFile) {
      this.bindingJarFile = bindingJarFile;
   }

   public File getBindingJarFile() {
      return this.bindingJarFile;
   }

   public void setTargetNamespace(String namespace) {
      this.targetNamespace = namespace;
   }

   public String getTargetNamespace() {
      return this.targetNamespace;
   }

   private void validateOpts() throws BuildException {
      if (this.srcdir == null) {
         throw new BuildException("srcdir was not set");
      } else if (!this.srcdir.exists()) {
         throw new BuildException("srcdir " + this.srcdir.getPath() + " does not exist or could not be read.");
      } else if (!this.srcdir.isDirectory()) {
         throw new BuildException("srcdir " + this.srcdir.getPath() + " is not a directory.");
      } else if (this.destdir == null) {
         throw new BuildException("destdir not set");
      } else {
         if (this.destdir.exists()) {
            if (!this.destdir.isDirectory()) {
               throw new BuildException("destdir " + this.destdir.getPath() + " is not a directory.");
            }

            this.destdir.mkdirs();
         }

         if (this.bindingJarFile == null) {
            throw new BuildException("bindingJarFile not set");
         }
      }
   }

   public void execute() throws BuildException {
      this.validateOpts();
      this.classpath = new Path(this.project);
      this.classpath.add(Path.systemClasspath);
      this.classpath.add(new Path(this.project, this.destdir.getAbsolutePath()));
      this.beangen();
      this.compileGeneratedCode();

      try {
         this.java2schema();
      } catch (IOException var2) {
         throw new BuildException(var2);
      }
   }

   private String find(File dir, String pattern) {
      FileSet fs = new FileSet();
      fs.setDir(dir);
      fs.createInclude().setName(pattern);
      DirectoryScanner ds = fs.getDirectoryScanner(this.project);
      String[] files = ds.getIncludedFiles();
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < files.length; ++i) {
         sb.append(" " + files[i]);
      }

      return sb.toString();
   }

   private Commandline.Argument buildArgList(Java javaTask) {
      StringBuffer sb = new StringBuffer();
      sb.append("-d ").append(this.destdir.getAbsolutePath());
      sb.append(" -sourcedir ").append(this.srcdir.getAbsolutePath());
      sb.append(" -nolocalvalidation");
      sb.append(" -suffix ").append("Impl");
      sb.append(" -baseclass ").append("weblogic.descriptor.internal.AbstractDescriptorBean");
      if (this.getTargetNamespace() != null) {
         sb.append(" -targetNamespace ").append(this.getTargetNamespace());
      }

      sb.append(this.find(this.srcdir, "**/*Bean.java"));
      String commandLine = sb.toString();
      System.out.println("\n Using beangen command-line of " + commandLine);
      Commandline.Argument arg = javaTask.createArg();
      arg.setLine(commandLine);
      return arg;
   }

   private void beangen() throws BuildException {
      Java javaTask = (Java)this.project.createTask("java");
      javaTask.setClassname(beangen.class.getName());
      javaTask.setFork(true);
      javaTask.setFailonerror(true);
      this.buildArgList(javaTask);
      javaTask.setClasspath(this.classpath);
      javaTask.execute();
   }

   private void compileGeneratedCode() throws BuildException {
      Javac javacTask = (Javac)this.project.createTask("javac");
      javacTask.setSrcdir(new Path(this.project, this.destdir.getAbsolutePath()));
      javacTask.setDestdir(this.destdir);
      javacTask.setClasspath(this.classpath);
      javacTask.execute();
   }

   private Java2SchemaTask findOrCreateSchemaTask() {
      Hashtable taskDefs = this.project.getTaskDefinitions();
      if (!taskDefs.containsKey("weblogic.ant.taskdefs.management.DescriptorGen.java2schema")) {
         this.project.addTaskDefinition("weblogic.ant.taskdefs.management.DescriptorGen.java2schema", MBeanJava2SchemaTask.class);
      }

      Java2SchemaTask j = (Java2SchemaTask)this.project.createTask("weblogic.ant.taskdefs.management.DescriptorGen.java2schema");
      j.setTaskName("java2Schema");
      return j;
   }

   private void java2schema() throws BuildException, IOException {
      File tmpDir = FileUtils.createTempDir("dgen");
      tmpDir.mkdirs();
      System.out.println("java2schema using tempdir " + tmpDir);
      Java2SchemaTask js = this.findOrCreateSchemaTask();
      js.setSrcdir(new Path(this.project, this.destdir.getAbsolutePath()));
      js.setIncludes("**/*BeanImpl.java");
      js.setDestDir(tmpDir);
      js.setClasspath(this.classpath);
      js.execute();
      Jar jar = (Jar)this.project.createTask("jar");
      jar.setBasedir(tmpDir);
      jar.setDestFile(this.bindingJarFile);
      jar.execute();
      FileUtils.remove(tmpDir);
   }
}
