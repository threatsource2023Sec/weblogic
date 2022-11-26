package weblogic.ant.taskdefs.j2ee;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import weblogic.ant.taskdefs.utils.AntLibraryUtils;
import weblogic.ant.taskdefs.utils.LibraryElement;
import weblogic.utils.Executable;

public class Appc extends CompilerTask {
   private String source = null;
   private String output = null;
   private String iiopDirectory = null;
   private String idlDirectory = null;
   private String idlMethodSignatures = null;
   private String classpath = null;
   private String libdir = null;
   private String plan = null;
   private String clientJarOutputDir = null;
   private boolean forceGeneration = false;
   private boolean lineNumbers = false;
   private boolean basicClientJar = false;
   private boolean continueCompilation = false;
   private boolean idl = false;
   private boolean idlOverwrite = false;
   private boolean idlVerbose = false;
   private boolean idlNoValueTypes = false;
   private boolean idlNoAbstractInterfaces = false;
   private boolean idlFactories = false;
   private boolean idlVisibroker = false;
   private boolean idlOrbix = false;
   private boolean iiop = false;
   private boolean verbose = false;
   private boolean enableHotCodeGen = false;
   private Collection libraries = new ArrayList();

   public void setClasspath(String classpath) {
      this.classpath = classpath;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public void setOutput(String output) {
      this.output = output;
   }

   public void setForceGeneration(boolean forceGeneration) {
      this.forceGeneration = forceGeneration;
   }

   public void setLineNumbers(boolean lineNumbers) {
      this.lineNumbers = lineNumbers;
   }

   public void setBasicClientJar(boolean basicClientJar) {
      this.basicClientJar = basicClientJar;
   }

   public void setContinueCompilation(boolean continueCompilation) {
      this.continueCompilation = continueCompilation;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setEnableHotCodeGen(boolean enableHotCodeGen) {
      this.enableHotCodeGen = enableHotCodeGen;
   }

   public void setIiopDirectory(String iiopDirectory) {
      this.iiopDirectory = iiopDirectory;
   }

   public void setIdlDirectory(String idlDirectory) {
      this.idlDirectory = idlDirectory;
   }

   public void setIdl(boolean idl) {
      this.idl = idl;
   }

   public void setIdlOverwrite(boolean idlOverwrite) {
      this.idlOverwrite = idlOverwrite;
   }

   public void setIdlVerbose(boolean idlVerbose) {
      this.idlVerbose = idlVerbose;
   }

   public void setIdlNoValueTypes(boolean idlNoValueTypes) {
      this.idlNoValueTypes = idlNoValueTypes;
   }

   public void setIdlNoAbstractInterfaces(boolean idlNoAbstractInterfaces) {
      this.idlNoAbstractInterfaces = idlNoAbstractInterfaces;
   }

   public void setIdlFactories(boolean idlFactories) {
      this.idlFactories = idlFactories;
   }

   public void setIdlVisibroker(boolean idlVisibroker) {
      this.idlVisibroker = idlVisibroker;
   }

   public void setIdlOrbix(boolean idlOrbix) {
      this.idlOrbix = idlOrbix;
   }

   public void setIiop(boolean iiop) {
      this.iiop = iiop;
   }

   public void setIdlMethodSignatures(String idlMethodSignatures) {
      this.idlMethodSignatures = idlMethodSignatures;
   }

   public void setlibraryDir(String libdir) {
      this.libdir = libdir;
   }

   public void setPlan(String plan) {
      this.plan = plan;
   }

   public void setClientJarOutputDir(String clientJarOutputDir) {
      this.clientJarOutputDir = clientJarOutputDir;
   }

   public void addConfiguredLibrary(LibraryElement library) {
      if (library.getFile() == null) {
         throw new BuildException("Location of Library must be set");
      } else {
         this.libraries.add(library);
      }
   }

   public void execute() {
      Thread th = Thread.currentThread();
      ClassLoader cl = th.getContextClassLoader();
      ClassLoader antLoader = this.getClass().getClassLoader();

      try {
         if (cl != antLoader) {
            th.setContextClassLoader(antLoader);
         }

         this.privateExecute();
      } finally {
         th.setContextClassLoader(cl);
      }

   }

   private void privateExecute() throws BuildException {
      File sourceFile = this.project.resolveFile(this.source);
      File outputFile = null;
      if (this.output != null) {
         outputFile = this.project.resolveFile(this.output);
      }

      if (sourceFile == null) {
         throw new BuildException("Source must be specified");
      } else if (!sourceFile.exists()) {
         throw new BuildException("Source not found: " + sourceFile);
      } else if (outputFile != null && outputFile.isFile() && sourceFile.isFile() && outputFile.lastModified() > sourceFile.lastModified()) {
         this.log(outputFile + " is up to date", 3);
      } else {
         boolean spawnAppc = !this.canFindJavelinClasses();
         if (spawnAppc) {
            this.setExit();
         }

         Vector flags = super.getFlags();
         if (this.forceGeneration) {
            flags.addElement("-forceGeneration");
         }

         if (this.lineNumbers) {
            flags.addElement("-lineNumbers");
         }

         if (this.basicClientJar) {
            flags.addElement("-basicClientJar");
         }

         if (this.continueCompilation) {
            flags.addElement("-k");
         }

         if (this.verbose) {
            flags.addElement("-verbose");
         }

         if (this.idl) {
            flags.addElement("-idl");
         }

         if (this.idlOverwrite) {
            flags.addElement("-idlOverwrite");
         }

         if (this.idlVerbose) {
            flags.addElement("-idlVerbose");
         }

         if (this.idlNoValueTypes) {
            flags.addElement("-idlNoValueTypes");
         }

         if (this.idlNoAbstractInterfaces) {
            flags.addElement("-idlNoAbstractInterfaces");
         }

         if (this.idlFactories) {
            flags.addElement("-idlFactories");
         }

         if (this.idlVisibroker) {
            flags.addElement("-idlVisibroker");
         }

         if (this.idlOrbix) {
            flags.addElement("-idlOrbix");
         }

         if (this.iiop) {
            flags.addElement("-iiop");
         }

         if (this.enableHotCodeGen) {
            flags.addElement("-enableHotCodeGen");
         }

         if (this.iiopDirectory != null) {
            flags.addElement("-iiopDirectory");
            flags.addElement(this.iiopDirectory);
         }

         if (this.idlDirectory != null) {
            flags.addElement("-idlDirectory");
            flags.addElement(this.idlDirectory);
         }

         if (this.idlMethodSignatures != null) {
            flags.addElement("-idlMethodSignatures");
            flags.addElement(this.idlMethodSignatures);
         }

         if (outputFile != null) {
            flags.addElement("-output");
            flags.addElement(outputFile.getAbsolutePath());
         }

         if (this.plan != null) {
            flags.addElement("-plan");
            flags.addElement(this.project.resolveFile(this.plan).getAbsolutePath());
         }

         if (this.clientJarOutputDir != null) {
            flags.addElement("-clientJarOutputDir");
            flags.addElement(this.project.resolveFile(this.clientJarOutputDir).getAbsolutePath());
         }

         this.addLibraryFlags(flags);
         flags.addElement(sourceFile.getAbsolutePath());
         String[] args = this.getArgs(flags);
         if (spawnAppc) {
            this.execAppc(args);
         } else {
            this.invokeMain("weblogic.appc", args);
         }

      }
   }

   private boolean canFindJavelinClasses() {
      try {
         Class invoker = this.getClass().getClassLoader().loadClass("weblogic.servlet.jsp.JspcInvoker");
         Method m = invoker.getMethod("canFindJavelinClasses");
         Object ret = m.invoke((Object)null);
         return (Boolean)ret;
      } catch (Exception var4) {
         var4.printStackTrace();
         return false;
      }
   }

   private void execAppc(String[] args) throws BuildException {
      Executable exec = new Executable(System.out, System.err);
      List list = new ArrayList();
      long maxMem = Runtime.getRuntime().maxMemory();
      if (maxMem == Long.MAX_VALUE) {
         maxMem = Runtime.getRuntime().totalMemory();
      }

      list.add("java");
      list.add("-mx" + maxMem);
      list.add("weblogic.appc");

      for(int i = 0; i < args.length; ++i) {
         list.add(args[i]);
      }

      this.log("[APPC_TASK] exec'ing appc", 3);
      boolean result = exec.exec((String[])((String[])list.toArray(new String[list.size()])));
      if (!result) {
         throw new BuildException("Appc failed, see error messages above");
      }
   }

   private void addLibraryFlags(Vector flags) {
      File f = null;
      if (this.libdir != null) {
         f = this.project.resolveFile(this.libdir);
      }

      List l = AntLibraryUtils.getLibraryFlags(f, this.libraries);
      Iterator iter = l.iterator();

      while(iter.hasNext()) {
         flags.addElement(iter.next());
      }

   }
}
