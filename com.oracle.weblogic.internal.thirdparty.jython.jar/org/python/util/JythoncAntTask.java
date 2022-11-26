package org.python.util;

import java.io.File;
import org.python.apache.tools.ant.BuildException;
import org.python.apache.tools.ant.DirectoryScanner;
import org.python.apache.tools.ant.taskdefs.Java;
import org.python.apache.tools.ant.taskdefs.MatchingTask;
import org.python.apache.tools.ant.types.Path;

public class JythoncAntTask extends MatchingTask {
   protected static final String JYTHONC_PY = "Tools/jythonc/jythonc.py";
   protected static final String JYTHON_CLASS = "org.python.util.jython";
   protected Path classpath;
   protected String packageName;
   protected File jarFile;
   protected File jythoncpy;
   protected boolean deep;
   protected boolean core;
   protected boolean all;
   protected String addpackages;
   protected File jarFileBean;
   protected String skipModule;
   protected String compiler;
   protected String compileropts;
   protected String falsenames;
   protected File jythonHome;
   protected File destDir;
   protected File srcDir;
   protected File workdir;
   protected String extraArgs;

   public JythoncAntTask() {
      this.setIncludes("**/*.py");
   }

   public Path createClasspath() {
      if (this.classpath == null) {
         this.classpath = new Path(this.getProject());
      }

      return this.classpath.createPath();
   }

   public void setClasspath(Path aClasspath) {
      this.classpath = aClasspath;
   }

   public void setPackage(String aString) {
      this.packageName = aString;
   }

   public void setJar(File aJarFile) {
      this.jarFile = aJarFile;
      this.deep = true;
   }

   public void setCore(boolean aValue) {
      this.core = aValue;
      this.deep = true;
   }

   public void setAll(boolean aValue) {
      this.all = aValue;
      this.deep = true;
   }

   public void setBean(File aJarFileBean) {
      this.jarFileBean = aJarFileBean;
   }

   public void setSkip(String aValue) {
      this.skipModule = aValue;
   }

   public void setDeep(boolean aValue) {
      this.deep = aValue;
   }

   public void setAddpackages(String aValue) {
      this.addpackages = aValue;
   }

   public void setWorkdir(File aValue) {
      if (aValue.exists()) {
         if (!aValue.isDirectory()) {
            throw new BuildException("Workdir (" + aValue + ") is not a directory");
         }
      } else {
         aValue.mkdirs();
      }

      this.workdir = aValue;
   }

   public void setCompiler(String aCompiler) {
      this.compiler = aCompiler;
   }

   public void setCompileropts(String aValue) {
      this.compileropts = aValue;
   }

   public void setFalsenames(String aValue) {
      this.falsenames = aValue;
   }

   public void setHome(File aFile) {
      this.jythonHome = aFile;
   }

   public void setSrcdir(File aFile) {
      this.srcDir = aFile;
   }

   public void setDestdir(File aFile) {
      this.destDir = aFile;
   }

   public void setJythoncpy(File aValue) {
      this.jythoncpy = aValue;
   }

   public void setArgs(String aValue) {
      this.extraArgs = aValue;
   }

   public String getCompilerOptions() {
      StringBuffer aStringBuffer = new StringBuffer();
      if (this.destDir != null) {
         aStringBuffer.append("-d \"");
         aStringBuffer.append(this.destDir);
         aStringBuffer.append("\"");
         this.createClasspath().setLocation(this.destDir);
         this.destDir.mkdirs();
      }

      if (this.compileropts != null) {
         aStringBuffer.append(this.compileropts);
      }

      return aStringBuffer.length() == 0 ? null : aStringBuffer.toString();
   }

   public File getPythonHome() {
      if (this.jythonHome == null) {
         String aPythonHome = this.getProject().getProperty("python.home");
         if (aPythonHome == null) {
            throw new BuildException("No python.home or home specified");
         }

         this.jythonHome = new File(aPythonHome);
      }

      return this.jythonHome;
   }

   public File getJythoncPY() {
      return this.jythoncpy == null ? new File(this.getPythonHome(), "Tools/jythonc/jythonc.py") : this.jythoncpy;
   }

   public void execute() {
      try {
         Java javaTask = null;
         javaTask = (Java)this.getProject().createTask("java");
         javaTask.setTaskName("jythonc");
         javaTask.setClassname("org.python.util.jython");
         javaTask.createJvmarg().setValue("-Dpython.home=" + this.getPythonHome());
         File aJythonJarFile = new File(this.getPythonHome(), "jython.jar");
         this.createClasspath().setLocation(aJythonJarFile);
         javaTask.setClasspath(this.classpath);
         javaTask.createArg().setFile(this.getJythoncPY());
         if (this.packageName != null) {
            javaTask.createArg().setValue("--package");
            javaTask.createArg().setValue(this.packageName);
         }

         if (this.jarFile != null) {
            javaTask.createArg().setValue("--jar");
            javaTask.createArg().setFile(this.jarFile);
         }

         if (this.deep) {
            javaTask.createArg().setValue("--deep");
         }

         if (this.core) {
            javaTask.createArg().setValue("--core");
         }

         if (this.all) {
            javaTask.createArg().setValue("--all");
         }

         if (this.jarFileBean != null) {
            javaTask.createArg().setValue("--bean");
            javaTask.createArg().setFile(this.jarFileBean);
         }

         if (this.addpackages != null) {
            javaTask.createArg().setValue("--addpackages ");
            javaTask.createArg().setValue(this.addpackages);
         }

         if (this.workdir != null) {
            javaTask.createArg().setValue("--workdir ");
            javaTask.createArg().setFile(this.workdir);
         }

         if (this.skipModule != null) {
            javaTask.createArg().setValue("--skip");
            javaTask.createArg().setValue(this.skipModule);
         }

         String aCompilerOpts;
         if (this.compiler == null) {
            aCompilerOpts = this.getProject().getProperty("build.compiler");
            if (aCompilerOpts != null && aCompilerOpts.equals("jikes")) {
               javaTask.createArg().setValue("--compiler");
               javaTask.createArg().setValue("jikes");
            }
         } else {
            javaTask.createArg().setValue("--compiler");
            javaTask.createArg().setValue(this.compiler);
         }

         aCompilerOpts = this.getCompilerOptions();
         if (aCompilerOpts != null) {
            javaTask.createArg().setValue("--compileropts");
            javaTask.createArg().setValue(aCompilerOpts);
         }

         if (this.falsenames != null) {
            javaTask.createArg().setValue("--falsenames");
            javaTask.createArg().setValue(this.falsenames);
         }

         if (this.extraArgs != null) {
            javaTask.createArg().setLine(this.extraArgs);
         }

         if (this.srcDir == null) {
            this.srcDir = this.getProject().resolveFile(".");
         }

         DirectoryScanner scanner = super.getDirectoryScanner(this.srcDir);
         String[] dependencies = scanner.getIncludedFiles();
         this.log("compiling " + dependencies.length + " file" + (dependencies.length == 1 ? "" : "s"));
         String baseDir = scanner.getBasedir().toString() + File.separator;

         for(int i = 0; i < dependencies.length; ++i) {
            String targetFile = dependencies[i];
            javaTask.createArg().setValue(baseDir + targetFile);
         }

         javaTask.setDir(this.srcDir);
         javaTask.setFork(true);
         if (javaTask.executeJava() != 0) {
            throw new BuildException("jythonc reported an error");
         }
      } catch (Exception var9) {
         String msg = "Exception while calling org.python.util.jython. Details: " + var9.toString();
         throw new BuildException(msg, var9);
      }
   }
}
