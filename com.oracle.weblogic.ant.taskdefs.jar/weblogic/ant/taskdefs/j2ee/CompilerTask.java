package weblogic.ant.taskdefs.j2ee;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

public abstract class CompilerTask extends MatchingTask {
   private static final Class[] MAIN_SIGNATURE = new Class[]{String[].class};
   private static final Set inProcessJavacNames = new HashSet();
   protected String compiler = null;
   protected String version = null;
   protected Path compileClasspath = null;
   protected String compilerClass = null;
   protected boolean keepgenerated = false;
   protected boolean commentary = false;
   protected String destdir = null;
   protected boolean debug = false;
   protected boolean optimize = false;
   protected boolean noexit = true;
   protected boolean nowarn = false;
   protected boolean nowrite = false;
   protected boolean normi = false;
   protected boolean deprecation = false;
   protected boolean verboseJavac = false;
   protected String dispatchPolicy = null;
   protected boolean supressCompiler = false;
   protected String runtimeFlags = null;

   public void setCompiler(String compiler) {
      this.compiler = compiler;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public void setClasspath(Path classpath) {
      if (this.compileClasspath == null) {
         this.compileClasspath = classpath;
      } else {
         this.compileClasspath.append(classpath);
      }

   }

   public Path createClasspath() {
      if (this.compileClasspath == null) {
         this.compileClasspath = new Path(this.project);
      }

      return this.compileClasspath.createPath();
   }

   public void setClasspathRef(Reference r) {
      this.createClasspath().setRefid(r);
   }

   public void setKeepGenerated(boolean keepgenerated) {
      this.keepgenerated = keepgenerated;
   }

   public void setCommentary(boolean commentary) {
      this.commentary = commentary;
   }

   public void setDestDir(String destdir) {
      this.destdir = destdir;
   }

   public void setDispatchPolicy(String dispatchPolicy) {
      this.dispatchPolicy = dispatchPolicy;
   }

   public void setRuntimeFlags(String runtimeFlags) {
      this.runtimeFlags = runtimeFlags;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public void setOptimize(boolean optimize) {
      this.optimize = optimize;
   }

   public void setNoWarn(boolean nowarn) {
      this.nowarn = nowarn;
   }

   public void setNoWrite(boolean nowrite) {
      this.nowrite = nowrite;
   }

   public void setNoRmi(boolean normi) {
      this.normi = normi;
   }

   public void setDeprecation(boolean deprecation) {
      this.deprecation = deprecation;
   }

   public void setVerboseJavac(boolean verboseJavac) {
      this.verboseJavac = verboseJavac;
   }

   public void setExit() {
      this.noexit = false;
   }

   private void setCompilerFlags(Vector flags) {
      if (this.version != null) {
         flags.addElement("-source");
         flags.addElement(this.version);
         flags.addElement("-target");
         flags.addElement(this.version);
      } else {
         flags.addElement("-source");
         flags.addElement(System.getProperty("java.specification.version"));
      }

      if (!this.supressCompiler) {
         if (this.compiler == null && this.compilerClass == null) {
            String antCompiler = this.getProject().getProperty("build.compiler");
            if (antCompiler != null) {
               if ("weblogicx.ant.sj".equals(antCompiler)) {
                  this.compiler = "sj";
               } else if ("extJavac".equals(antCompiler)) {
                  this.compiler = "javac";
               } else if (inProcessJavacNames.contains(antCompiler)) {
                  this.compilerClass = "com.sun.tools.javac.Main";
               }
            }
         }

         if (this.compiler != null) {
            flags.addElement("-compiler");
            flags.addElement(this.compiler);
         } else if (this.compilerClass != null) {
            flags.addElement("-compilerClass");
            flags.addElement(this.compilerClass);
         }

      }
   }

   protected Vector getFlags() {
      Vector flags = new Vector();
      this.setCompilerFlags(flags);
      if (this.destdir != null) {
         flags.addElement("-d");
         flags.addElement(this.destdir);
      }

      if (this.dispatchPolicy != null) {
         flags.addElement("-dispatchPolicy");
         flags.addElement(this.dispatchPolicy);
      }

      if (this.runtimeFlags != null) {
         flags.addElement(this.runtimeFlags);
      }

      if (this.keepgenerated) {
         flags.addElement("-keepgenerated");
      }

      if (this.commentary) {
         flags.addElement("-commentary");
      }

      if (this.debug) {
         flags.addElement("-g");
      }

      if (this.optimize) {
         flags.addElement("-O");
      }

      if (this.nowarn) {
         flags.addElement("-nowarn");
      }

      if (this.verboseJavac) {
         flags.addElement("-verboseJavac");
      }

      if (this.nowrite) {
         flags.addElement("-nowrite");
      }

      if (this.normi) {
         flags.addElement("-normi");
      }

      if (this.deprecation) {
         flags.addElement("-deprecation");
      }

      if (!this.supressCompiler) {
         Path classpath = this.getCompileClasspath();
         flags.addElement("-classpath");
         flags.addElement(classpath.toString());
      }

      if (this.noexit) {
         flags.addElement("-noexit");
      }

      return flags;
   }

   protected String[] getArgs(Vector flags) {
      String[] args = new String[flags.size()];

      for(int i = 0; i < flags.size(); ++i) {
         args[i] = (String)flags.elementAt(i);
      }

      return args;
   }

   protected Path getCompileClasspath() {
      Path classpath = new Path(this.project);
      if (this.destdir != null) {
         File baseFile = this.project.resolveFile(this.destdir);
         classpath.addExisting(new Path(this.project, baseFile.getAbsolutePath()));
      }

      if (this.compileClasspath != null) {
         classpath.addExisting(this.compileClasspath);
      }

      classpath.addExisting(Path.systemClasspath);
      return classpath;
   }

   protected void invokeMain(String className, String[] args) {
      try {
         File classesdir = new File(this.getProject().getProperty("dest") + "/classes");
         URL[] urls = new URL[]{classesdir.toURL()};
         URLClassLoader ucl = new URLClassLoader(urls, this.getClass().getClassLoader());
         Class c = ucl.loadClass(className);
         Method m = null;
         m = c.getMethod("main", MAIN_SIGNATURE);
         Object[] arg = new Object[]{args};
         m.invoke((Object)null, arg);
      } catch (BuildException var9) {
         throw var9;
      } catch (InvocationTargetException var10) {
         Throwable th = var10.getTargetException();
         if (th == null) {
            th = var10;
         }

         throw new BuildException((Throwable)th);
      } catch (Exception var11) {
         throw new BuildException(var11);
      }
   }

   static {
      inProcessJavacNames.add("classic");
      inProcessJavacNames.add("javac1.1");
      inProcessJavacNames.add("javac1.2");
      inProcessJavacNames.add("modern");
      inProcessJavacNames.add("javac1.3");
      inProcessJavacNames.add("javac1.4");
   }
}
