package weblogic.ant.taskdefs.management.commo;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;

public class WLMBeanMaker extends Task {
   private static final Class[] MAIN_SIGNATURE = new Class[]{String[].class};
   private String compiler;
   private String source;
   private String target;
   private String files;
   private String mdfFile;
   private String mdfDir;
   private String mjfJar;
   private String mbeantypesDir;
   private boolean noCompile;
   private Path classpath;
   private boolean createStubs = true;
   private boolean doBeanGen = false;
   private boolean doCheckDescription = false;
   private boolean preserveStubs;
   private boolean internalWLSBuild;
   private String targetNamespace = null;
   private String schemaLocation = null;
   private boolean compileDebug = false;
   private boolean verbose = false;
   private String jvmArgs = null;

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public void setCompiler(String compiler) {
      this.compiler = compiler;
   }

   public void setCreateStubs(boolean createStubs) {
      this.createStubs = createStubs;
   }

   public void setDoBeanGen(boolean doBeangen) {
      this.doBeanGen = doBeangen;
   }

   public void setDoCheckDescription(boolean doCheckDescription) {
      this.doCheckDescription = doCheckDescription;
   }

   public void setPreserveStubs(boolean preserveStubs) {
      this.preserveStubs = preserveStubs;
   }

   public void setFiles(String files) {
      this.files = files;
   }

   public void setMDFFile(String mdfFile) {
      this.mdfFile = mdfFile;
   }

   public void setMDFDir(String mdfDir) {
      this.mdfDir = mdfDir;
   }

   public void setNoCompile(boolean noCompile) {
      this.noCompile = noCompile;
   }

   public void setMJFJar(String mjfJar) {
      this.mjfJar = mjfJar;
   }

   public void setMBeanTypesDir(String mbeantypesDir) {
      this.mbeantypesDir = mbeantypesDir;
   }

   public void setTargetNameSpace(String targetNamespace) {
      this.targetNamespace = targetNamespace;
   }

   public void setSchemaLocation(String schemaLocation) {
      this.schemaLocation = schemaLocation;
   }

   public void setJvmArgs(String jvmArgs) {
      this.jvmArgs = jvmArgs;
   }

   public void setClasspath(Path p) {
      this.createClasspath().append(p);
   }

   public Path createClasspath() {
      if (this.classpath == null) {
         this.classpath = new Path(this.project);
      }

      return this.classpath;
   }

   public void setInternalWLSBuild(boolean internalWLSBuild) {
      this.internalWLSBuild = internalWLSBuild;
   }

   public void setClasspathRef(Reference r) {
      this.createClasspath().setRefid(r);
   }

   public void setCompileDebug(boolean compileDebug) {
      this.compileDebug = compileDebug;
   }

   public void execute() throws BuildException {
      List flags = new ArrayList();
      if (this.verbose) {
         flags.add("-verbose");
      }

      if (this.compiler != null) {
         flags.add("-compiler");
         flags.add(this.compiler);
      }

      if (this.source != null) {
         flags.add("-source");
         flags.add(this.source);
      }

      if (this.target != null) {
         flags.add("-target");
         flags.add(this.target);
      }

      if (this.createStubs) {
         flags.add("-createStubs");
      }

      if (this.doBeanGen) {
         flags.add("-doBeanGen");
      }

      if (this.doCheckDescription) {
         flags.add("-doCheckDescription");
      }

      if (this.preserveStubs) {
         flags.add("-preserveStubs");
      }

      if (this.files != null) {
         flags.add("-files");
         flags.add(this.files);
      }

      if (this.mdfFile != null) {
         flags.add("-MDF");
         flags.add(this.mdfFile);
      }

      if (this.mdfDir != null) {
         flags.add("-MDFDIR");
         flags.add(this.mdfDir);
      }

      if (this.noCompile) {
         flags.add("-noCompile");
      }

      if (this.mjfJar != null) {
         flags.add("-MJF");
         flags.add(this.mjfJar);
      }

      if (this.mbeantypesDir != null) {
         flags.add("-mbeantypesDir");
         flags.add(this.mbeantypesDir);
      }

      if (this.compileDebug) {
         flags.add("-g");
      }

      if (this.targetNamespace != null) {
         flags.add("-targetNameSpace");
         flags.add(this.targetNamespace);
      }

      if (this.schemaLocation != null) {
         flags.add("-schemaLocation");
         flags.add(this.schemaLocation);
      }

      if (this.jvmArgs != null) {
         flags.add("-jvmArgs");
         flags.add(this.jvmArgs);
      }

      if (this.internalWLSBuild) {
         flags.add("-internalWLSBuild");
      }

      GenericClassLoader cl;
      if (this.classpath == null) {
         this.createClasspath().setPath(System.getProperty("java.class.path"));
         this.classpath.createPathElement().setLocation(new File(this.files));
         ClassFinder f = new ClasspathClassFinder2(this.files);
         cl = new GenericClassLoader(f, this.getClass().getClassLoader());
      } else {
         String classPathString = this.classpath.toString().trim() + File.pathSeparatorChar + this.files;
         ClassFinder f = new ClasspathClassFinder2(classPathString);
         cl = GenericClassLoader.getRootClassLoader(f);
      }

      flags.add("-classpath");
      flags.add(this.classpath.toString().trim());
      String[] args = this.getArgs(flags);
      ClassLoader save = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(cl);
         Class c = cl.loadClass("weblogic.management.commo.WebLogicMBeanMaker");
         Method m = null;
         m = c.getMethod("main", MAIN_SIGNATURE);
         Object[] arg = new Object[]{args};
         System.out.print("weblogic.management.commo.WebLogicMBeanMaker ");

         for(int i = 0; i < args.length; ++i) {
            System.out.print(args[i]);
            System.out.print(" ");
         }

         System.out.println("");
         m.invoke((Object)null, arg);
      } catch (Exception var12) {
         var12.printStackTrace();
         throw new BuildException(var12.toString());
      } finally {
         Thread.currentThread().setContextClassLoader(save);
      }
   }

   protected String[] getArgs(List flags) {
      List args = new ArrayList(flags);
      return (String[])((String[])args.toArray(new String[args.size()]));
   }
}
