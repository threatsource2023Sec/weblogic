package weblogic;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.spi.EJBC;
import weblogic.ejb.spi.EJBCFactory;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.kernel.KernelStatus;
import weblogic.utils.AssertionError;
import weblogic.utils.BadOptionException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.FileUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.ICompilerFactory;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public class ejbc20 extends Tool {
   private final EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private static final String EJBC_WORKING_DIR = "ejbcgen";
   private String sourceJarFileName;
   private String targetJarFileName;
   private File inputJar;
   private String appId;
   private EjbDescriptorBean ejbDescriptor;
   private File outputDir;
   private boolean runFromCmdLine = true;
   protected ICompilerFactory compilerFactory = null;
   private boolean doClose = true;
   private boolean createOutputJar = true;
   private VirtualJarFile myJar = null;
   private GenericClassLoader m_classLoader;
   private Class[] bugs = new Class[]{Error.class, NullPointerException.class, IndexOutOfBoundsException.class, AssertionError.class};

   public ejbc20(String[] args) {
      super(args);
      if (KernelStatus.isServer()) {
         this.runFromCmdLine = false;
      }

   }

   public void setRunFromCmdLine(boolean value) {
      this.runFromCmdLine = value;
   }

   public void prepare() {
      this.opts.addFlag("nodeploy", "Do not unpack jar files into the target dir.");
      this.opts.setUsageArgs("<source directory or jar file> [<target directory or jar file>]");
      this.opts.addFlag("idl", "Generate idl for remote interfaces");
      this.opts.addFlag("idlOverwrite", "Always overwrite existing IDL files");
      this.opts.addFlag("idlVerbose", "Display verbose information for IDL generation");
      this.opts.addFlag("idlNoValueTypes", "Do not generate valuetypes and methods/attributes that contain them.");
      this.opts.addFlag("idlNoAbstractInterfaces", "Do not generate abstract interfaces and methods/attributes that contain them.");
      this.opts.addFlag("idlFactories", "Generate factory methods for valuetypes.");
      this.opts.addFlag("idlVisibroker", "Generate IDL somewhat compatible with Visibroker 4.5 C++.");
      this.opts.addFlag("idlOrbix", "Generate IDL somewhat compatible with Orbix 2000 2.0 C++.");
      this.opts.addOption("idlDirectory", "dir", "Specify the directory where IDL files will be created (default : target directory or jar)");
      this.opts.addFlag("iiop", "Generate CORBA stubs.");
      this.opts.addFlag("iiopSun", "Use Sun's rmic for generating CORBA stubs.");
      this.opts.markPrivate("iiopSun");
      this.opts.addFlag("iiopTie", "Generate CORBA skeletons, uses Sun's rmic.");
      this.opts.markPrivate("iiopTie");
      this.opts.addOption("iiopDirectory", "dir", "Specify the directory where IIOP stub files will be written (default : target directory or jar)");
      this.opts.addOption("idlMethodSignatures", "", "Specify the method signatures used to trigger idl code generation.");
      this.opts.addFlag("idlCompatibility", "Substitute structs for value types to generate CORBA 2.2 compatible IDL.");
      this.opts.markPrivate("idlCompatibility");
      this.opts.addOption("dispatchPolicy", "policy", "Specify the Dispatch Policy for this EJB");
      this.opts.markPrivate("dispatchPolicy");
      this.opts.addFlag("stickToFirstServer", "Enables sticky load balancing");
      this.opts.markPrivate("stickToFirstServer");
      this.opts.addFlag("nocompliance", "Do not run the EJB Compliance Checker.");
      this.opts.addFlag("forceGeneration", "Force generation of wrapper classes.  Without this flag the classes may not be regenerated if it is determined to be unnecessary.");
      this.opts.addFlag("basicClientJar", "Generate a client jar that does not contain deployment descriptors.");
      this.opts.addFlag("disableHotCodeGen", "Generate ejb stub and skel as part of ejbc. Avoid HotCodeGen to have better performance.");
      new CompilerInvoker(this.opts);
      this.opts.markPrivate("nowrite");
      this.opts.markPrivate("commentary");
      this.opts.markPrivate("nodeploy");
      this.opts.markPrivate("O");
      this.opts.markPrivate("d");
      this.opts.markPrivate("nocompliance");

      try {
         this.opts.setFlag("nowarn", true);
      } catch (BadOptionException var2) {
         throw new java.lang.AssertionError(var2);
      }
   }

   private String getSourceJarFileName(Getopt2 opts) {
      return opts.args()[0];
   }

   private void makeOutputDir(String directory) throws ToolFailureException {
      this.outputDir = new File(directory);
      if (!this.outputDir.exists()) {
         if (!this.outputDir.mkdir()) {
            throw new ToolFailureException("ERROR: Ejbc ould not create temporary working directory:" + this.outputDir.getAbsolutePath() + ".  Please ensure that this directory can be created.");
         }
      } else if (!this.outputDir.canWrite()) {
         throw new ToolFailureException("ERROR: Ejbc can not write to the temporary working directory:" + this.outputDir.getAbsolutePath() + ".  Please ensure that you have write permission for this directory.  You may also specify an alternative output directory on the weblogic.ejbc command line.");
      }

   }

   private String getTargetJarFileName(Getopt2 opts) throws ToolFailureException {
      String[] args = opts.args();
      this.targetJarFileName = opts.getOption("d");
      if (this.targetJarFileName == null) {
         if (args.length == 1) {
            this.targetJarFileName = args[0];
         } else {
            this.targetJarFileName = args[1];
         }
      }

      if (!this.targetJarFileName.endsWith(".jar") && !this.targetJarFileName.endsWith(".JAR")) {
         this.makeOutputDir(this.targetJarFileName);
         this.targetJarFileName = null;
      } else {
         this.outputDir = new File("ejbcgen");
         if (this.outputDir.exists()) {
            FileUtils.remove(new File("ejbcgen"));
         }

         this.makeOutputDir("ejbcgen");
      }

      return this.targetJarFileName;
   }

   private void validateToolInputs() throws ToolFailureException {
      if (this.opts.args().length >= 1 && this.opts.args().length <= 2) {
         this.sourceJarFileName = this.getSourceJarFileName(this.opts);
         if (this.inputJar == null) {
            this.inputJar = new File(this.sourceJarFileName);
         }

         if (!this.inputJar.exists()) {
            throw new ToolFailureException("ERROR: source file: " + this.sourceJarFileName + " could not be found.");
         } else if (!this.inputJar.isDirectory() && !this.sourceJarFileName.endsWith(".jar")) {
            throw new ToolFailureException("ERROR: You must specify a source directory or ejb-jar file ending with the suffix .jar to run weblogic.ejbc");
         } else {
            this.appId = this.inputJar.getName();
            this.targetJarFileName = this.getTargetJarFileName(this.opts);

            try {
               this.opts.setOption("d", this.outputDir.getPath());
            } catch (BadOptionException var2) {
               throw new java.lang.AssertionError(var2);
            }
         }
      } else {
         this.opts.usageError("weblogic.ejbc");
         throw new ToolFailureException("ERROR: incorrect command-line.");
      }
   }

   private File backupJar(File jarFile) throws ToolFailureException {
      File saveJar = new File(jarFile + "SAVE");
      if (saveJar.exists() && !saveJar.delete()) {
         throw new ToolFailureException("ERROR: Could not delete old backup file: " + saveJar.getAbsolutePath());
      } else {
         try {
            FileUtils.copy(jarFile, saveJar);
         } catch (IOException var4) {
            throw new ToolFailureException("ERROR: Could not create a backup file " + saveJar.getAbsolutePath());
         }

         if (!jarFile.delete()) {
            throw new ToolFailureException("ERROR: Could not delete previous jar " + jarFile.getAbsolutePath());
         } else {
            return saveJar;
         }
      }
   }

   private void createOutputJar(String targetJarFileName) throws ToolFailureException {
      File outJar = new File(targetJarFileName);
      File savedJar = null;
      if (outJar.exists()) {
         savedJar = this.backupJar(outJar);
      }

      try {
         JarFileUtils.createJarFileFromDirectory(targetJarFileName, this.outputDir);
         if (savedJar != null) {
            savedJar.delete();
         }

         FileUtils.remove(this.outputDir);
      } catch (Exception var5) {
         if (outJar.exists()) {
            outJar.delete();
         }

         if (savedJar != null && savedJar.exists()) {
            savedJar.renameTo(outJar);
            throw new ToolFailureException("ERROR: Could not create output jar, restoring previous jar.  The error was " + var5);
         } else {
            throw new ToolFailureException("ERROR: Could not create output jar.  The error was:" + var5);
         }
      }
   }

   public void setClose(boolean b) {
      this.doClose = b;
   }

   public void setClassLoader(ClassLoader classLoader) {
      this.m_classLoader = (GenericClassLoader)classLoader;
   }

   public void setJarFile(VirtualJarFile vjf) {
      this.myJar = vjf;
   }

   public void setCreateOutputJar(boolean b) {
      this.createOutputJar = b;
   }

   public void runBody() throws ToolFailureException, ErrorCollectionException {
      this.validateToolInputs();
      VirtualJarFile jf = null;
      if (this.myJar != null) {
         jf = this.myJar;
      } else {
         try {
            jf = VirtualJarFactory.createVirtualJar(this.inputJar);
         } catch (IOException var18) {
            throw new ToolFailureException("ERROR: Error processing input Jar file: " + var18);
         }
      }

      if (this.targetJarFileName != null || !this.inputJar.equals(this.outputDir)) {
         try {
            if (this.outputDir != null && jf != null) {
               this.inform(this.fmt.getExpandJar(jf.getName(), this.outputDir.getPath()));
               JarFileUtils.extract(jf, this.outputDir);
            }
         } catch (IOException var17) {
            throw new ToolFailureException("ERROR: Error expanding input Jar file: " + var17);
         }
      }

      ClasspathClassFinder2 finder = null;
      if (this.m_classLoader == null) {
         String classpath = this.outputDir.getAbsolutePath();
         if (this.opts.hasOption("classpath")) {
            StringBuffer sb = new StringBuffer();
            sb.append(classpath);
            sb.append(File.pathSeparator);
            sb.append(this.opts.getOption("classpath"));
            sb.append(File.pathSeparator);
            classpath = sb.toString();
         }

         finder = new ClasspathClassFinder2(classpath);
         ClassLoader parent = this.getClass().getClassLoader();
         this.m_classLoader = new GenericClassLoader(finder, parent);
         this.m_classLoader.setAnnotation(new Annotation(this.appId, this.appId));
      }

      if (this.ejbDescriptor == null) {
         this.inform(this.fmt.getExtractingDesc(jf.getName()));

         try {
            this.ejbDescriptor = this.getDescriptorFromJar(jf, true, this.m_classLoader);
         } catch (ErrorCollectionException var19) {
            ErrorCollectionException ece = var19;
            if (this.runFromCmdLine) {
               var19.printStackTrace();
               ece = this.formatErrorsInCollection(var19);
               ece.add(new ToolFailureException("ERROR: ejbc couldn't load descriptor from jar"));
            }

            throw ece;
         }
      }

      try {
         this.opts.setOption("classpath", this.m_classLoader.getClassPath());
      } catch (BadOptionException var16) {
         throw new java.lang.AssertionError(var16);
      }

      EJBC ejbCompiler = EJBCFactory.createEJBC(this.opts, this.outputDir);
      ejbCompiler.setCompilerFactory(this.compilerFactory);
      this.inform(this.fmt.getCompilingJar(jf.getName()));

      try {
         ejbCompiler.compileEJB(this.m_classLoader, this.ejbDescriptor, jf);
      } catch (ErrorCollectionException var20) {
         ErrorCollectionException ece = var20;
         if (this.runFromCmdLine) {
            ece = this.formatErrorsInCollection(var20);
            ece.add(new ToolFailureException("ERROR: ejbc couldn't invoke compiler"));
         }

         throw ece;
      } finally {
         if (this.doClose) {
            try {
               jf.close();
            } catch (Exception var15) {
            }
         }

         if (finder != null) {
            finder.close();
         }

      }

      if (this.targetJarFileName != null && this.createOutputJar) {
         this.inform(this.fmt.getCreatingOutputJar(this.targetJarFileName));
         this.createOutputJar(this.targetJarFileName);
      }

      if (this.runFromCmdLine) {
         System.out.println("ejbc successful.");
      } else {
         this.inform("\n" + this.fmt.getEJBCSuccess());
      }

   }

   private void persistNewDescriptors(EjbDescriptorBean ejbDesc) throws ErrorCollectionException {
      try {
         this.renameOldDescriptors();
         ejbDesc.usePersistenceDestination(this.outputDir.getAbsolutePath());
         ejbDesc.persist();
      } catch (IOException var4) {
         ErrorCollectionException errors = new ErrorCollectionException();
         errors.add(var4);
         errors = this.formatErrorsInCollection(errors);
         errors.add(new ToolFailureException("ERROR: ejbc couldn't rename decsriptors"));
         throw errors;
      }
   }

   private void renameOldDescriptors() {
      File directory = new File(this.outputDir.getAbsolutePath());
      String[] files = directory.list();

      for(int i = 0; i < files.length; ++i) {
         System.out.println("the file names are " + files[i]);
         if (files[i].equalsIgnoreCase("META-INF")) {
            File metainf = new File(this.outputDir.getAbsolutePath() + System.getProperty("file.separator") + files[i]);
            if (metainf.isDirectory()) {
               String[] xmlFiles = metainf.list();

               for(int j = 0; j < xmlFiles.length; ++j) {
                  System.out.println("the xml file names are " + xmlFiles[j]);
                  if (xmlFiles[j].equalsIgnoreCase("ejb-jar.xml") || xmlFiles[j].equalsIgnoreCase("weblogic-ejb-jar.xml") || xmlFiles[j].equalsIgnoreCase("weblogic-cmp-rdbms-jar.xml")) {
                     String absPath = metainf.getAbsolutePath() + System.getProperty("file.separator") + xmlFiles[j];
                     (new File(absPath)).renameTo(new File(absPath + ".old"));
                  }
               }
            }
         }
      }

   }

   public ErrorCollectionException formatErrorsInCollection(ErrorCollectionException ece) {
      Collection errors = ece.getExceptions();
      ErrorCollectionException result = new ErrorCollectionException(ece.getBaseMessage());
      Iterator var4 = errors.iterator();

      while(var4.hasNext()) {
         Throwable th = (Throwable)var4.next();
         String message = this.formatExceptionMessage(th);
         result.add(new ToolFailureException(message));
      }

      return result;
   }

   public String formatExceptionMessage(Throwable th) {
      String message = th.getMessage() + "\n";
      String prefix = "ERROR: Error from ejbc: ";
      StringBuffer result = new StringBuffer();
      if (th instanceof ToolFailureException) {
         result.append(message);
      } else {
         result.append(prefix);
         if (th instanceof ClassNotFoundException) {
            result.append("Unable to load a class specified in your ejb-jar.xml: " + message);
         } else if (th instanceof XMLProcessingException) {
            XMLProcessingException xpe = (XMLProcessingException)th;
            result.append("Error processing '" + xpe.getFileName() + "': " + message);
         } else {
            result.append(message);
         }

         if (this.opts.hasOption("verbose") || message == null || this.isBug(th)) {
            result.append("\n" + StackTraceUtils.throwable2StackTrace(th));
         }
      }

      return result.toString();
   }

   private EjbDescriptorBean getDescriptorFromJar(VirtualJarFile inputJarFile, boolean readOnly, GenericClassLoader cl) throws ErrorCollectionException {
      ErrorCollectionException errors;
      try {
         return readOnly ? EjbDescriptorFactory.createReadOnlyDescriptorFromJarFile(inputJarFile, (File)null, (File)null, (DeploymentPlanBean)null, (String)null, (String)null, cl, (VirtualJarFile[])null) : EjbDescriptorFactory.createDescriptorFromJarFile(inputJarFile);
      } catch (XMLProcessingException var6) {
         errors = new ErrorCollectionException();
         errors.add(new ToolFailureException("ERROR: ejbc found errors while processing the descriptor for " + this.sourceJarFileName + ": \n"));
         errors.add(var6);
         throw errors;
      } catch (XMLParsingException var7) {
         errors = new ErrorCollectionException();
         errors.add(new ToolFailureException("ERROR: ejbc found errors while parsing the descriptor for " + this.sourceJarFileName + ": \n"));
         errors.add(var7);
         throw errors;
      } catch (Exception var8) {
         errors = new ErrorCollectionException();
         errors.add(new ToolFailureException("ERROR: Error creating descriptor from jar file " + this.sourceJarFileName + ": "));
         errors.add(var8);
         throw errors;
      }
   }

   private boolean isBug(Throwable th) {
      for(int i = 0; i < this.bugs.length; ++i) {
         if (this.bugs[i].isAssignableFrom(th.getClass())) {
            return true;
         }
      }

      return false;
   }

   private void inform(String message) {
   }

   public ICompilerFactory getCompilerFactory() {
      return this.compilerFactory;
   }

   public void setCompilerFactory(ICompilerFactory compilerFactory) {
      this.compilerFactory = compilerFactory;
   }

   public static void main(String[] args) throws Exception {
      System.out.println("\nDEPRECATED: The weblogic.ejbc20 compiler is deprecated and will be removed in a future version of WebLogic Server.  Please use weblogic.ejbc instead.\n");
      (new ejbc20(args)).run();
   }
}
