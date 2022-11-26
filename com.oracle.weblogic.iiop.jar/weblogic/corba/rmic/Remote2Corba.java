package weblogic.corba.rmic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import sun.rmi.rmic.Main;
import weblogic.corba.rmi.Stub;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.rmic.Remote2JavaConstants;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerator;

public class Remote2Corba extends CodeGenerator implements Remote2JavaConstants {
   private static final boolean debug = false;
   private static final String RMIC_COMPILER_CLASS = "sun.rmi.rmic.Main";
   private boolean verbose = false;
   private Getopt2 opts;
   private String iiopDirectory;
   private boolean disableHotCodGen = false;
   private static final String IIOP_VERBOSE = "verbose";

   public Remote2Corba(Getopt2 opts) {
      super(opts);
      addGeneratorOptions(opts);
      this.opts = opts;
      if (this.verbose) {
         Debug.say("opts ok");
      }

   }

   static void addGeneratorOptions(Getopt2 opts) {
      opts.addFlag("iiop", "Generate iiop stubs from servers");
      opts.addFlag("iiopTie", "Generate CORBA skeletons, uses Sun's rmic.");
      opts.addFlag("iiopSun", "Use Sun's rmic for generating CORBA stubs.");
      opts.addOption("iiopDirectory", "directory", "Specify the directory where IIOP proxy classes will be written (overrides target directory)");
   }

   private String getIIOPDirectory() {
      return this.iiopDirectory;
   }

   protected void extractOptionValues(Getopt2 opts) {
      this.iiopDirectory = opts.getOption("iiopDirectory", (String)null);
      this.verbose = opts.hasOption("verbose");
      this.disableHotCodGen = opts.hasOption("disableHotCodeGen");
      if (Utilities.getClassLoader() != null && this.iiopDirectory != null) {
         System.err.println("WARNING: iiopDirectory not supported in combination with this classloader, ignored.");
         this.iiopDirectory = super.getRootDirectoryName();
      }

      if (null == this.iiopDirectory) {
         this.iiopDirectory = super.getRootDirectoryName();
      }

      if (null == this.iiopDirectory) {
         this.iiopDirectory = ".";
      }

   }

   public Enumeration outputs(Object[] inputs) throws Exception {
      String[] classes = (String[])((String[])inputs);
      boolean keepgenerated = this.opts.hasOption("keepgenerated");
      boolean skels = this.opts.hasOption("iiopTie");
      boolean useSunRmic = this.opts.hasOption("iiopSun") || skels;
      String dir = this.getIIOPDirectory();
      String classpath = this.opts.getOption("classpath");
      int var10;
      Class[] interfaces;
      Class[] var14;
      int var15;
      int var16;
      Class anInterface;
      if (useSunRmic) {
         if (this.opts.hasOption("descriptor")) {
            System.err.println("WARNING: -descriptor not supported in combination with -iiopTie or -iiopSun, ignored.");
         }

         List args = new ArrayList();
         args.add("-iiop");
         args.add("-always");
         if (keepgenerated) {
            args.add("-keepgenerated");
         }

         this.addIfNotNull(args, "-classpath", classpath);
         this.addIfNotNull(args, "-d", dir);
         this.addIfNotNull(args, "-bootclasspath", getBootClassPath());
         String[] argList;
         if (skels) {
            Collections.addAll(args, classes);
         } else {
            args.add("-nolocalstubs");
            argList = classes;
            var10 = classes.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String aClass = argList[var11];
               interfaces = StubGenerator.getAllInterfaces(Utilities.classForName(aClass));
               var14 = interfaces;
               var15 = interfaces.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  anInterface = var14[var16];
                  args.add(anInterface.getName());
               }
            }
         }

         argList = (String[])args.toArray(new String[args.size()]);
         if (this.verbose) {
            StringBuilder cmd = new StringBuilder("sun.rmi.rmic.Main ");
            String[] var21 = argList;
            int var23 = argList.length;

            for(int var25 = 0; var25 < var23; ++var25) {
               String anArgList = var21[var25];
               cmd.append(anArgList).append(" ");
            }

            Debug.say(cmd.toString());
         }

         this.runOrbRmicCompiler(argList);
      } else {
         String[] var18 = classes;
         int var19 = classes.length;

         String aClass;
         for(var10 = 0; var10 < var19; ++var10) {
            aClass = var18[var10];
            if (this.verbose) {
               Debug.say("weblogic.rmic -iiop -d " + this.getIIOPDirectory() + " " + aClass);
            }

            Class remote = Utilities.classForName(aClass);
            if (!remote.isInterface()) {
               this.generateStub(remote);
            }

            interfaces = StubGenerator.getAllInterfaces(remote);
            var14 = interfaces;
            var15 = interfaces.length;

            for(var16 = 0; var16 < var15; ++var16) {
               anInterface = var14[var16];
               this.generateStub(anInterface);
            }
         }

         if (this.disableHotCodGen) {
            var18 = classes;
            var19 = classes.length;

            for(var10 = 0; var10 < var19; ++var10) {
               aClass = var18[var10];
               this.generateIIOPStub(aClass);
            }
         }
      }

      return (new Hashtable()).elements();
   }

   void runOrbRmicCompiler(String[] argList) throws Exception {
      Main main = new Main(System.out, "rmic");
      boolean exitVal = main.compile(argList);
      if (!exitVal) {
         throw new Exception("Remote2Corba.compile failed during invocation of " + main.getClass().getName());
      }
   }

   private void addIfNotNull(List args, String flag, String value) {
      if (value != null) {
         args.add(flag);
         args.add(value);
      }
   }

   private void generateStub(Class remote) throws Exception {
      if (this.verbose) {
         Debug.say("weblogic.rmic -iiop -d " + this.getIIOPDirectory() + remote.getName());
      }

      StubGenerator stubGenerator = new StubGenerator(remote);
      String fileName = stubGenerator.getClassName().replace('.', File.separatorChar) + ".class";
      ensureDirectoryExists(this.getIIOPDirectory(), fileName);
      FileOutputStream fos = new FileOutputStream(this.getIIOPDirectory() + File.separatorChar + fileName);
      stubGenerator.write(fos);
      fos.close();
   }

   private static void ensureDirectoryExists(String outputDir, String fileName) {
      String fileDir = "";
      int i = fileName.lastIndexOf(File.separatorChar);
      if (i != -1) {
         fileDir = fileName.substring(0, i);
      }

      String dir = outputDir + File.separatorChar + fileDir;
      File f = new File(dir);
      f.mkdirs();
   }

   public void generateIIOPStubs() {
   }

   private void generateIIOPStub(String className) throws ClassNotFoundException {
      FileOutputStream stubOut = null;

      try {
         ClassLoader cl = Utilities.getClassLoader();
         Class c = null;

         try {
            c = Class.forName(className);
         } catch (ClassNotFoundException var20) {
            if (cl != null) {
               c = cl.loadClass(className);
            }
         }

         RuntimeDescriptor rtd = DescriptorHelper.getDescriptor(c);
         weblogic.rmi.internal.StubGenerator stub = new weblogic.rmi.internal.StubGenerator(rtd, className + "_IIOP_WLStub", Stub.class.getName());
         String fileName = stub.getClassName().replace('.', File.separatorChar);
         File f = new File(super.getRootDirectoryName());
         f.mkdir();
         f = new File(super.getRootDirectoryName() + File.separatorChar + fileName + ".class");
         String path = f.getParent();
         if (path != null) {
            File dir = new File(path);
            if (!dir.exists()) {
               dir.mkdirs();
            }
         }

         stubOut = new FileOutputStream(f);
         stub.write(stubOut);
      } catch (Exception var21) {
         var21.printStackTrace();
         System.exit(1);
      } finally {
         try {
            if (stubOut != null) {
               stubOut.close();
            }
         } catch (IOException var19) {
         }

      }

   }

   static String getBootClassPath() {
      return (String)Optional.of(getOmgApiClassPath()).filter((s) -> {
         return s.contains("omgapi");
      }).orElse((Object)null);
   }

   private static String getOmgApiClassPath() {
      URL resource = Remote2Corba.class.getClassLoader().getResource("org/omg/CORBA/Object.class");
      return resource == null ? "" : toJarFilePath(resource.getPath());
   }

   private static String toJarFilePath(String resource) {
      String withoutFile = resource.split(":")[1];
      return withoutFile.split("!")[0];
   }
}
