package weblogic.ant.taskdefs.j2ee;

import java.io.File;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;

public class Ejbc extends CompilerTask {
   protected String version;
   protected String source;
   protected String target;
   protected String iiopDirectory;
   protected String idlMethodSignatures;
   protected boolean idlOverwrite;
   protected boolean idlVerbose;
   protected boolean idlNoValueTypes;
   protected boolean iiop;
   protected boolean idl;
   protected boolean disableHotCodeGen;

   public void setDisableHotCodeGen(boolean b) {
      this.disableHotCodeGen = b;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public void setIiopDirectory(String iiopDirectory) {
      this.iiopDirectory = iiopDirectory;
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

   public void setIiop(boolean iiop) {
      this.iiop = iiop;
   }

   public void setIdlMethodSignatures(String idlMethodSignatures) {
      this.idlMethodSignatures = idlMethodSignatures;
   }

   protected Vector getFlags() {
      Vector flags = super.getFlags();
      if (this.iiop) {
         flags.addElement("-iiop");
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

      if (this.debug || this.disableHotCodeGen) {
         flags.addElement("-disableHotCodeGen");
      }

      if (this.iiopDirectory != null) {
         flags.addElement("-iiopDirectory");
         flags.addElement(this.iiopDirectory);
      }

      if (this.idlMethodSignatures != null) {
         flags.addElement("-idlMethodSignatures");
         flags.addElement(this.idlMethodSignatures);
      }

      if (this.compiler != null) {
         if (this.compiler.equals("javac")) {
            flags.addElement("-compilerclass");
            flags.addElement("com.sun.tools.javac.Main");
         } else {
            flags.addElement("-compiler");
            flags.addElement(this.compiler);
         }
      }

      if (this.version != null) {
         flags.addElement("-source");
         flags.addElement(this.version);
         flags.addElement("-target");
         flags.addElement(this.version);
      }

      if (!flags.contains("-classpath")) {
         Path classpath = this.getCompileClasspath();
         flags.addElement("-classpath");
         flags.addElement(classpath.toString());
      }

      return flags;
   }

   public void execute() throws BuildException {
      Thread thread = Thread.currentThread();
      ClassLoader origClassLoader = thread.getContextClassLoader();
      thread.setContextClassLoader(this.getClass().getClassLoader());
      if (System.getProperty("weblogic.home") == null) {
         String wlhome = this.getProject().getProperty("wl.home");
         if (wlhome != null) {
            System.setProperty("weblogic.home", wlhome);
         }
      }

      try {
         this.supressCompiler = true;
         File sourceFile = this.project.resolveFile(this.source);
         File targetFile = this.project.resolveFile(this.target);
         if (sourceFile == null) {
            throw new BuildException("Source must be specified");
         }

         if (!sourceFile.exists()) {
            throw new BuildException("Source not found: " + sourceFile);
         }

         if (this.target == null) {
            throw new BuildException("Target must be specified");
         }

         if (!sourceFile.exists() || !targetFile.exists() || !targetFile.isFile() || targetFile.lastModified() <= sourceFile.lastModified()) {
            Vector flags = this.getFlags();
            flags.addElement(sourceFile.getAbsolutePath());
            flags.addElement(targetFile.getAbsolutePath());
            String[] args = this.getArgs(flags);

            try {
               this.invokeMain("weblogic.ejbc", args);
               return;
            } catch (BuildException var11) {
               this.log(var11.getMessage(), 0);
               throw var11;
            }
         }

         this.log(targetFile + " is up to date", 3);
      } finally {
         thread.setContextClassLoader(origClassLoader);
      }

   }
}
