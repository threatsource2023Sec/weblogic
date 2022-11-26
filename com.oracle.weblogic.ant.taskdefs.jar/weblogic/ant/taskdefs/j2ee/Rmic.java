package weblogic.ant.taskdefs.j2ee;

import java.io.File;
import java.rmi.Remote;
import java.util.Date;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;

public class Rmic extends CompilerTask {
   private String classname;
   private boolean verify = false;
   private boolean clusterable = false;
   private boolean nontransactional = false;
   private boolean serversidestubs = false;
   private boolean propagateenvironment = false;
   private boolean nomanglednames = false;
   private boolean iiop = false;
   private boolean idl = false;
   private boolean oneway = false;
   private String iiopDirectory = null;
   private String idlDirectory = null;
   private String replicahandler = null;
   private String replicaListRefreshInterval = null;
   private String descriptor = null;
   private String dgcpolicy = null;
   private boolean replicaAware = false;
   private String loadAlgorithm;
   private String callRouter;
   private boolean methodsAreIdempotent = false;
   private boolean stickToFirstServer = false;
   private String compiler;
   private boolean verbose = false;
   private String src;

   public void setSrcDir(String srcdir) {
      this.src = srcdir;
   }

   public void setClassname(String classname) {
      this.classname = classname;
   }

   public void setVerify(boolean verify) {
      this.verify = verify;
   }

   public void setIiopDirectory(String iiopDirectory) {
      this.iiopDirectory = iiopDirectory;
   }

   public void setIdlDirectory(String idlDirectory) {
      this.idlDirectory = idlDirectory;
   }

   public void setReplicaHandler(String replicahandler) {
      this.replicahandler = replicahandler;
   }

   public void setReplicaListRefreshInterval(String replicaListRefreshInterval) {
      this.replicaListRefreshInterval = replicaListRefreshInterval;
   }

   public void setClusterable(boolean clusterable) {
      this.clusterable = clusterable;
   }

   public void setReplicaAware(boolean replicaAware) {
      this.replicaAware = replicaAware;
   }

   public void setLoadAlgorithm(String loadAlgorithm) {
      this.loadAlgorithm = loadAlgorithm;
   }

   public void setCallRouter(String callRouter) {
      this.callRouter = callRouter;
   }

   public void setMethodsAreIdempotent(boolean methodsAreIdempotent) {
      this.methodsAreIdempotent = methodsAreIdempotent;
   }

   public void setStickToFirstServer(boolean stickToFirstServer) {
      this.stickToFirstServer = stickToFirstServer;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setCompiler(String compiler) {
      this.compiler = compiler;
   }

   public void setTransactional(boolean transactional) {
      this.nontransactional = !transactional;
   }

   public void setNonTransactional(boolean nontransactional) {
      this.nontransactional = nontransactional;
   }

   public void setServerSideStubs(boolean serversidestubs) {
      this.serversidestubs = serversidestubs;
   }

   public void setPropagateEnvironment(boolean propagateenvironment) {
      this.propagateenvironment = propagateenvironment;
   }

   public void setNoMangledNames(boolean nomanglednames) {
      this.nomanglednames = nomanglednames;
   }

   public void setOneway(boolean oneway) {
      this.oneway = oneway;
   }

   public void setDescriptor(String descriptor) {
      if (this.src == null) {
         this.descriptor = descriptor;
      } else {
         this.descriptor = this.src + "/" + descriptor;
      }

   }

   public void setDgcPolicy(String dgcpolicy) {
      this.dgcpolicy = dgcpolicy;
   }

   public void setIiop(boolean iiop) {
      this.iiop = iiop;
   }

   public void setIdl(boolean idl) {
      this.idl = idl;
   }

   public void execute() throws BuildException {
      if (this.verify) {
         this.log("Verify has been turned on.", 2);
      }

      Vector flags = super.getFlags();
      if (this.clusterable) {
         flags.addElement("-clusterable");
      }

      if (this.nontransactional) {
         flags.addElement("-nontransactional");
      }

      if (this.serversidestubs) {
         flags.addElement("-serverSideStubs");
      }

      if (this.propagateenvironment) {
         flags.addElement("-propagateEnvironment");
      }

      if (this.nomanglednames) {
         flags.addElement("-nomanglednames");
      }

      if (this.iiop) {
         flags.addElement("-iiop");
      }

      if (this.idl) {
         flags.addElement("-idl");
      }

      if (this.replicaAware) {
         flags.addElement("-replicaAware");
      }

      if (this.methodsAreIdempotent) {
         flags.addElement("-methodsAreIdempotent");
      }

      if (this.stickToFirstServer) {
         flags.addElement("-stickToFirstServer");
      }

      if (this.verbose) {
         flags.addElement("-verbose");
      }

      if (this.oneway) {
         flags.addElement("-oneway");
      }

      if (this.callRouter != null) {
         flags.addElement("-callRouter");
         flags.addElement(this.callRouter);
      }

      if (this.loadAlgorithm != null) {
         flags.addElement("-loadAlgorithm");
         flags.addElement(this.loadAlgorithm);
      }

      if (this.compiler != null) {
         flags.addElement("-compiler");
         flags.addElement(this.compiler);
      }

      if (this.iiopDirectory != null) {
         flags.addElement("-iiopDirectory");
         flags.addElement(this.iiopDirectory);
      }

      if (this.idlDirectory != null) {
         flags.addElement("-idlDirectory");
         flags.addElement(this.idlDirectory);
      }

      if (this.replicahandler != null) {
         flags.addElement("-replicaHandler");
         flags.addElement(this.replicahandler);
      }

      if (this.descriptor != null) {
         flags.addElement("-descriptor");
         flags.addElement(this.descriptor);
      }

      if (this.dgcpolicy != null) {
         flags.addElement("-dgcPolicy");
         flags.addElement(this.dgcpolicy);
      }

      int numFlags = flags.size();
      File baseDir = this.project.resolveFile(this.destdir);
      if (this.classname == null) {
         DirectoryScanner ds = this.getDirectoryScanner(baseDir);
         String[] files = ds.getIncludedFiles();
         this.scanDir(baseDir, files, this.verify, flags);
      } else if (this.shouldCompile(new File(baseDir, this.classname.replace('.', File.separatorChar) + ".class"))) {
         flags.addElement(this.classname);
      }

      if (flags.size() > numFlags) {
         String[] args = this.getArgs(flags);
         this.log("Compiling " + (args.length - numFlags) + " classes to " + this.destdir, 2);
         if (this.verbose) {
            System.out.print("weblogic.rmic(");

            for(int i = 0; i < args.length; ++i) {
               System.out.print(args[i] + " ");
            }

            System.out.println(")");
         }

         this.invokeMain("weblogic.rmic", args);
      }

   }

   protected void scanDir(File destdir, String[] files, boolean shouldVerify, Vector compileList) {
      for(int i = 0; i < files.length; ++i) {
         File baseFile = new File(destdir, files[i]);
         if (files[i].endsWith(".class") && this.shouldCompile(baseFile)) {
            String classname = files[i].replace(File.separatorChar, '.');
            classname = classname.substring(0, classname.indexOf(".class"));
            boolean shouldAdd = true;
            if (shouldVerify) {
               try {
                  Class testClass = Class.forName(classname);
                  if (testClass.isInterface() || !this.isValidRmiRemote(testClass)) {
                     shouldAdd = false;
                  }
               } catch (ClassNotFoundException var10) {
                  this.log("Unable to verify class " + classname + ". It could not be found.", 1);
               } catch (NoClassDefFoundError var11) {
                  this.log("Unable to verify class " + classname + ". It is not defined.", 1);
               }
            }

            if (shouldAdd) {
               this.log("Adding: " + classname + " to compile list", 3);
               compileList.addElement(classname);
            }
         }
      }

   }

   private boolean isValidRmiRemote(Class testClass) {
      Class rmiRemote = Remote.class;
      if (rmiRemote.equals(testClass)) {
         return true;
      } else {
         Class[] interfaces = testClass.getInterfaces();
         if (interfaces != null) {
            for(int i = 0; i < interfaces.length; ++i) {
               if (rmiRemote.equals(interfaces[i])) {
                  return true;
               }

               if (this.isValidRmiRemote(interfaces[i])) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean shouldCompile(File classFile) {
      long now = (new Date()).getTime();
      File descFile = new File(classFile.getAbsolutePath().substring(0, classFile.getAbsolutePath().indexOf(".class")) + "RTD.xml");
      if (!descFile.exists()) {
         return true;
      } else if (classFile.exists()) {
         if (classFile.lastModified() > now) {
            this.log("Warning: file modified in the future: " + classFile, 1);
         }

         if (classFile.lastModified() > descFile.lastModified()) {
            return true;
         } else {
            if (this.descriptor != null) {
               File descriptorFile = new File(this.descriptor);
               if (descriptorFile.exists() && descriptorFile.lastModified() > descFile.lastModified()) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }
}
