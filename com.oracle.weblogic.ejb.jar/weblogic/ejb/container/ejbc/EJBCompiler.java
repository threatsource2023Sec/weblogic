package weblogic.ejb.container.ejbc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import javax.ejb.EJBHome;
import weblogic.rmic;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.ModuleRegistry;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceCheckerFactory;
import weblogic.ejb.container.deployer.DeploymentInfoImpl;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.ejbc.bytecodegen.GeneratorFactory;
import weblogic.ejb.container.ejbc.javac.JavacCompiler;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CMPCompiler;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.SingletonSessionBeanInfo;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.spi.EJBC;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.SessionBeanInfo;
import weblogic.ejb.spi.VersionHelper;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.ICompilerFactory;
import weblogic.utils.compiler.jdt.JDTJavaCompilerFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class EJBCompiler implements EJBC {
   private static final DebugLogger debugLogger;
   private static final String JMS_RES_TYPE_ID = "javax.jms.";
   private static final String JDBC_RES_TYPE_ID = "javax.sql.";
   private static final String DISPATCH_POLICY = "dispatchPolicy";
   private static final String STICK_TO_FIRST_SERVER = "stickToFirstServer";
   private final Ejb2Rmi ejb2rmi;
   private final Getopt2 opts;
   private final File classesRootDir;
   private final File moduleRootDir;
   private VersionHelper vHelper;
   private final boolean forceGeneration;
   private final boolean basicClientJar;
   private DeploymentInfo activeDeploymentInfo;
   private ClassLoader classLoader;
   private final boolean runComplianceChecker;
   private ICompilerFactory compilerFactory;
   private static final int BUFFER_SIZE = 8192;

   public EJBCompiler(Getopt2 opts, File moduleRootDir) {
      this.ejb2rmi = new Ejb2Rmi(opts);
      this.opts = opts;
      this.classesRootDir = new File(this.ejb2rmi.rootDirectory());
      this.moduleRootDir = moduleRootDir;
      this.runComplianceChecker = !opts.hasOption("nocompliance");
      this.forceGeneration = opts.hasOption("forceGeneration");
      this.basicClientJar = opts.hasOption("basicClientJar");
   }

   private void setupEJB(GenericClassLoader cl, EjbDescriptorBean desc, VirtualJarFile jf, VersionHelper helper, ModuleRegistry mr, boolean isCDIEnabled) throws ErrorCollectionException {
      this.classLoader = cl;
      if (helper != null) {
         this.activeDeploymentInfo = ((VersionHelperImpl)helper).getDeploymentInfo();
         this.vHelper = helper;
      } else {
         try {
            this.activeDeploymentInfo = getStandAloneDeploymentInfo(cl, jf, desc, isCDIEnabled);
            if (mr != null) {
               mr.put(weblogic.ejb.spi.DeploymentInfo.class.getName(), this.activeDeploymentInfo);
            }

            Iterator var7 = this.activeDeploymentInfo.getEntityBeanInfos().iterator();

            while(var7.hasNext()) {
               EntityBeanInfo ebi = (EntityBeanInfo)var7.next();
               if (!ebi.getIsBeanManagedPersistence()) {
                  ebi.getCMPInfo().setup(new File(jf.getName()), this.opts, jf);
               }
            }

            var7 = this.activeDeploymentInfo.getSessionBeanInfos().iterator();

            while(var7.hasNext()) {
               SessionBeanInfo bi = (SessionBeanInfo)var7.next();
               if (mr != null && bi.isSingleton()) {
                  ((SingletonSessionBeanInfo)bi).registerSingletonDependencyResolver(mr);
               }
            }

            this.vHelper = new VersionHelperImpl(this.activeDeploymentInfo, this.opts);
         } catch (ErrorCollectionException var9) {
            throw var9;
         } catch (Exception var10) {
            throw new ErrorCollectionException(var10);
         }
      }

      this.addIIOPOptionsToOpts();
   }

   private List getSourceFilePaths(Set outputs) {
      List filePaths = new ArrayList(outputs.size());
      Iterator var3 = outputs.iterator();

      while(var3.hasNext()) {
         EjbCodeGenerator.Output o = (EjbCodeGenerator.Output)var3.next();
         filePaths.add(o.getAbsoluteFilePath());
      }

      return filePaths;
   }

   private Map getSourceContent(Set outputs) {
      Map map = new HashMap(outputs.size());
      Iterator var3 = outputs.iterator();

      while(var3.hasNext()) {
         EjbCodeGenerator.Output o = (EjbCodeGenerator.Output)var3.next();
         String pkg = o.getPackage();
         String name = o.getOutputFile().substring(0, o.getOutputFile().length() - 5);
         String fqName = pkg != null && !"".equals(pkg) ? pkg + "." + name : name;
         map.put(fqName, o.getOutputContent());
      }

      return map;
   }

   private void doCompile(Collection beans) throws ErrorCollectionException {
      if (this.runComplianceChecker) {
         ComplianceCheckerFactory.getComplianceChecker().checkDeploymentInfo(this.activeDeploymentInfo);
         if (debugLogger.isDebugEnabled()) {
            debug("Compliance Checker said bean was compliant");
         }
      }

      Set outputs = new HashSet();
      InterceptorsBean iceptors = this.activeDeploymentInfo.getEjbDescriptorBean().getEjbJarBean().getInterceptors();
      if (iceptors != null) {
         Set hasGeneratedInterceptor = new HashSet();
         InterceptorBean[] var5 = iceptors.getInterceptors();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            InterceptorBean ib = var5[var7];

            try {
               Class interceptorClass = this.classLoader.loadClass(ib.getInterceptorClass());
               if (!Serializable.class.isAssignableFrom(interceptorClass) && !hasGeneratedInterceptor.contains(interceptorClass)) {
                  this.generate(ib);
                  hasGeneratedInterceptor.add(interceptorClass);
               }
            } catch (Exception var22) {
               throw new ErrorCollectionException(var22);
            }
         }
      }

      List cmpCompilers = new ArrayList();
      Iterator it = beans.iterator();

      while(it.hasNext()) {
         BeanInfo bi = (BeanInfo)it.next();
         if (debugLogger.isDebugEnabled()) {
            debug("Generating Bean Sources");
         }

         try {
            outputs.addAll(this.generate(bi));
         } catch (Exception var19) {
            throw new ErrorCollectionException(var19);
         }

         if (bi instanceof EntityBeanInfo) {
            EntityBeanInfo ebi = (EntityBeanInfo)bi;
            if (!ebi.getIsBeanManagedPersistence()) {
               try {
                  if (debugLogger.isDebugEnabled()) {
                     debug("Generating Persistence Sources");
                  }

                  CMPCompiler cmp = new EJBCMPCompiler(this.classesRootDir, this.opts, this.compilerFactory);
                  outputs.addAll(cmp.generatePersistenceSources(ebi));
                  cmpCompilers.add(cmp);
               } catch (Exception var18) {
                  throw new ErrorCollectionException(var18);
               }
            }
         }
      }

      if (!outputs.isEmpty()) {
         if (debugLogger.isDebugEnabled()) {
            debug("Compiling EJB sources");
         }

         String output = null;
         boolean isServer = KernelStatus.isServer();
         boolean errorOccurred = false;

         try {
            GenericClassLoader gcl = (GenericClassLoader)Thread.currentThread().getContextClassLoader();
            if (this.ejb2rmi.isJDTBased()) {
               CompilerForJDT compiler = new CompilerForJDT(this.ejb2rmi.rootDirectory(), gcl.getClassPath());
               compiler.compile(this.getSourceContent(outputs));
               output = compiler.getCompilerErrors();
            } else {
               JavacCompiler compiler = new JavacCompiler(this.ejb2rmi.rootDirectory(), gcl.getClassPath());
               compiler.compile(this.getSourceContent(outputs));
               output = compiler.getCompilerErrors();
            }
         } catch (IOException var17) {
            errorOccurred = true;
            throw new ErrorCollectionException(var17);
         } finally {
            if (output != null && output.trim().length() > 0) {
               if (errorOccurred) {
                  EJBLogger.logJavaCompilerErrorOutput(output);
               } else {
                  EJBLogger.logJavaCompilerOutput(output);
               }
            }

         }
      }

      try {
         it = cmpCompilers.iterator();

         while(it.hasNext()) {
            CMPCompiler cmpCompiler = (CMPCompiler)it.next();
            cmpCompiler.postCompilation();
         }
      } catch (Exception var21) {
         throw new ErrorCollectionException(var21);
      }

      this.vHelper.writeChecksum(this.moduleRootDir);
   }

   private void doRmic(Collection beans) throws ErrorCollectionException {
      Iterator var2 = beans.iterator();

      while(var2.hasNext()) {
         BeanInfo bi = (BeanInfo)var2.next();
         if (bi.isClientDriven()) {
            ClientDrivenBeanInfo cdbi = (ClientDrivenBeanInfo)bi;
            Map ifaceToImpls = this.getRemoteIfaceToImplMapping(cdbi);
            if (null != ifaceToImpls) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Got Remote Classes: " + ifaceToImpls.values());
               }

               if (!ifaceToImpls.isEmpty()) {
                  this.runRmic(ifaceToImpls, cdbi);
               }
            }
         }
      }

   }

   public void compileEJB(GenericClassLoader cl, EjbDescriptorBean desc, VirtualJarFile jf, ModuleValidationInfo mvi, ModuleRegistry mr, boolean isCDIEnabled) throws ErrorCollectionException {
      this.compileEJB(cl, desc, jf, mvi, (VersionHelper)null, (Collection)null, mr, isCDIEnabled);
   }

   public void compileEJB(GenericClassLoader cl, EjbDescriptorBean desc, VirtualJarFile jf) throws ErrorCollectionException {
      try {
         this.compileEJB(cl, desc, jf, (ModuleValidationInfo)null, (VersionHelper)null, (Collection)null, (ModuleRegistry)null, false);
      } finally {
         JDTJavaCompilerFactory.getInstance().resetCache(cl);
      }

   }

   public void compileEJB(GenericClassLoader cl, VirtualJarFile jf, VersionHelper helper, Collection needsRecompile) throws ErrorCollectionException {
      this.compileEJB(cl, (EjbDescriptorBean)null, jf, (ModuleValidationInfo)null, helper, needsRecompile, (ModuleRegistry)null, false);
   }

   private void compileEJB(GenericClassLoader cl, EjbDescriptorBean desc, VirtualJarFile jf, ModuleValidationInfo mvi, VersionHelper helper, Collection needsRecompile, ModuleRegistry mr, boolean isCDIEnabled) throws ErrorCollectionException {
      ClassLoader clSave = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(cl);
         this.setupEJB(cl, desc, jf, helper, mr, isCDIEnabled);
         if (mvi != null) {
            populateMVI(mvi, this.activeDeploymentInfo);
         }

         if (this.forceGeneration) {
            if (debugLogger.isDebugEnabled()) {
               debug("Recompiling because of forceGeneration flag");
            }

            needsRecompile = this.activeDeploymentInfo.getBeanInfos();
         } else if (needsRecompile == null) {
            needsRecompile = this.vHelper.needsRecompile(jf, false);
         }

         Collection needRmic;
         if (this.allBeansNeedRmic()) {
            needRmic = this.activeDeploymentInfo.getBeanInfos();
         } else {
            needRmic = needsRecompile;
         }

         if (!needsRecompile.isEmpty()) {
            this.doCompile(needsRecompile);
            if (debugLogger.isDebugEnabled()) {
               debug("Recompilation completed");
            }
         } else if (debugLogger.isDebugEnabled()) {
            debug("Recompilation determined unnecessary");
         }

         if (!needRmic.isEmpty()) {
            this.doRmic(needRmic);
            if (debugLogger.isDebugEnabled()) {
               debug("Rmic completed");
            }
         } else if (debugLogger.isDebugEnabled()) {
            debug("Rmic determined unnecessary");
         }

         if (!KernelStatus.isServer()) {
            String clientJarName = this.activeDeploymentInfo.getClientJarFileName();

            try {
               if (clientJarName != null && !"".equals(clientJarName)) {
                  this.createClientJar(clientJarName, this.activeDeploymentInfo.getBeanInfos());
               }
            } catch (IOException var16) {
               throw new ErrorCollectionException(var16);
            }
         }
      } finally {
         if (clSave != null) {
            Thread.currentThread().setContextClassLoader(clSave);
         }

      }

   }

   private boolean allBeansNeedRmic() {
      return this.opts.hasOption("idl") || this.opts.hasOption("idlOverwrite") || this.opts.hasOption("idlNoValueTypes") || this.opts.hasOption("idlFactories") || this.opts.hasOption("idlVisibroker") || this.opts.hasOption("idlDirectory") || this.opts.hasOption("idlMethodSignatures") || this.opts.hasOption("iiop") || this.opts.hasOption("iiopDirectory");
   }

   private Collection getStubClasses(Collection allStubFiles) {
      List returnList = new ArrayList();
      Iterator var3 = allStubFiles.iterator();

      while(var3.hasNext()) {
         String eachStubName = (String)var3.next();
         eachStubName = eachStubName.substring(this.classesRootDir.getAbsolutePath().length() + 1, eachStubName.length());
         eachStubName = ClassUtils.fileNameToClass(eachStubName);

         try {
            returnList.add(this.classLoader.loadClass(eachStubName));
         } catch (ClassNotFoundException var6) {
         }
      }

      return returnList;
   }

   private void createClientJar(String cjName, Collection beans) throws IOException {
      Collection extCls = this.getStubClasses(this.getAllIIOPStubs(this.classesRootDir));
      if (this.opts.hasOption("disableHotCodeGen")) {
         extCls.addAll(this.getStubClasses(this.getRMIStubClasses(this.classesRootDir)));
      }

      ClientJarMaker cjMaker = new ClientJarMaker(this.classLoader);
      String[] cjFiles = cjMaker.createClientJar(beans, extCls);
      if (debugLogger.isDebugEnabled()) {
         debug("Client jar files: " + Arrays.toString(cjFiles));
      }

      if (cjFiles.length > 0) {
         this.makeJar(cjName, cjFiles);
         EJBLogger.logClientJarCreated(cjName);
      }

   }

   private List getRMIStubClasses(File dir) {
      String match = ServerHelper.getWlsStubVersion() + ".class";
      List results = new ArrayList();
      String[] var4 = dir.list();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String file = var4[var6];
         File f = new File(dir.getAbsolutePath() + File.separator + file);
         if (f.isDirectory()) {
            results.addAll(this.getRMIStubClasses(f));
         } else if (f.getAbsolutePath().endsWith(match)) {
            results.add(f.getAbsolutePath());
         }
      }

      return results;
   }

   private List getRuntimeDescriptors(File dir) {
      String match = "ImplRTD.xml";
      List results = new ArrayList();
      String[] var4 = dir.list();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String file = var4[var6];
         File f = new File(dir.getAbsolutePath() + File.separator + file);
         if (f.isDirectory()) {
            results.addAll(this.getRuntimeDescriptors(f));
         } else if (f.getAbsolutePath().endsWith(match)) {
            String fullPath = f.getAbsolutePath();
            String rtdFile = fullPath.substring(this.classesRootDir.getAbsolutePath().length() + 1, fullPath.length());
            rtdFile = rtdFile.replace('\\', '/');
            results.add(rtdFile);
         }
      }

      return results;
   }

   private List getAllIIOPStubs(File dir) {
      String match = "_Stub.class";
      List results = new ArrayList();
      String[] var4 = dir.list();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String file = var4[var6];
         File f = new File(dir.getAbsolutePath() + File.separator + file);
         if (f.isDirectory()) {
            results.addAll(this.getAllIIOPStubs(f));
         } else if (f.getAbsolutePath().endsWith(match)) {
            results.add(f.getAbsolutePath());
         }
      }

      return results;
   }

   private File makeClientJarOutputDirectoryIfNecessary() throws IOException {
      String clientJarOutputDir = this.opts.getOption("clientJarOutputDir");
      if (clientJarOutputDir != null && clientJarOutputDir.length() != 0) {
         File dir = new File(clientJarOutputDir);
         if (dir.exists()) {
            if (!dir.isDirectory()) {
               throw new IOException("ERROR: the clientJarOutputDir [" + dir.getAbsolutePath() + "] must be a directory.");
            }
         } else {
            dir.mkdir();
         }

         return dir;
      } else {
         return null;
      }
   }

   private void makeJar(String jarName, String[] files) throws IOException {
      File clientJarOutputDir = this.makeClientJarOutputDirectoryIfNecessary();
      FileOutputStream fos = null;
      JarOutputStream jos = null;

      try {
         String jarNameForCreate = jarName;
         if (clientJarOutputDir != null) {
            jarNameForCreate = clientJarOutputDir.getCanonicalPath() + File.separator + jarName;
         }

         fos = new FileOutputStream(jarNameForCreate);
         jos = new JarOutputStream(fos);
         String[] var7 = files;
         int var8 = files.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String file = var7[var9];
            InputStream is = null;

            try {
               String resourceName = file.replace('.', '/') + ".class";
               is = this.classLoader.getResourceAsStream(resourceName);
               jos.putNextEntry(new ZipEntry(resourceName));
               this.copyBytes(is, jos);
               jos.closeEntry();
            } finally {
               if (is != null) {
                  is.close();
               }

            }
         }

         if (!this.basicClientJar) {
            InputStream is = this.classLoader.getResourceAsStream("META-INF/ejb-jar.xml");
            if (is != null) {
               jos.putNextEntry(new ZipEntry("META-INF/ejb-jar.xml"));
               this.copyBytes(is, jos);
               jos.closeEntry();
            }

            is = this.classLoader.getResourceAsStream("META-INF/weblogic-ejb-jar.xml");
            if (is != null) {
               jos.putNextEntry(new ZipEntry("META-INF/weblogic-ejb-jar.xml"));
               this.copyBytes(is, jos);
               jos.closeEntry();
            }

            Iterator var22 = this.getRuntimeDescriptors(this.classesRootDir).iterator();

            while(var22.hasNext()) {
               String runtimeDescriptor = (String)var22.next();
               is = this.classLoader.getResourceAsStream(runtimeDescriptor);
               if (is != null) {
                  jos.putNextEntry(new ZipEntry(runtimeDescriptor));
                  this.copyBytes(is, jos);
                  jos.closeEntry();
               }
            }
         }
      } finally {
         if (jos != null) {
            jos.close();
         }

         if (fos != null) {
            fos.close();
         }

      }

   }

   private void copyBytes(InputStream is, OutputStream os) throws IOException {
      byte[] buffer = new byte[8192];
      int bytesRead = false;

      int bytesRead;
      while((bytesRead = is.read(buffer, 0, 8192)) != -1) {
         os.write(buffer, 0, bytesRead);
      }

   }

   private void generate(InterceptorBean ib) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug("Generating code for interceptor " + ib.getInterceptorClass());
      }

      NamingConvention nc = new NamingConvention(ib.getInterceptorClass());
      GeneratorFactory.generate(ib, nc, this.classesRootDir.getAbsolutePath(), (GenericClassLoader)this.classLoader);
   }

   private List generate(BeanInfo bi) throws Exception {
      List outputs = new ArrayList();
      if (bi instanceof MessageDrivenBeanInfo) {
         if (debugLogger.isDebugEnabled()) {
            debug("Generating code for ejb " + bi.getEJBName());
         }

         MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)bi;
         NamingConvention nc = new NamingConvention(mdbi.getBeanClassName(), mdbi.getEJBName());
         GeneratorFactory.generate(mdbi, nc, this.classesRootDir.getAbsolutePath());
      } else {
         if (!(bi instanceof ClientDrivenBeanInfo)) {
            throw new IllegalArgumentException("Uknnown type of BeanInfo:" + bi);
         }

         if (debugLogger.isDebugEnabled()) {
            debug("Generating code for ejb " + bi.getEJBName());
         }

         if (((ClientDrivenBeanInfo)bi).isSessionBean()) {
            NamingConvention nc = new NamingConvention(bi.getBeanClassName(), bi.getEJBName());
            GeneratorFactory.generate((weblogic.ejb.container.interfaces.SessionBeanInfo)bi, nc, this.classesRootDir.getAbsolutePath());
         } else {
            List fileNames = this.ejb2rmi.generate(bi);
            if (debugLogger.isDebugEnabled()) {
               debug("Generated the following sources for this EJB: " + fileNames);
            }

            outputs.addAll(this.ejb2rmi.getGeneratedOutputs());
         }
      }

      return outputs;
   }

   private Map getRemoteIfaceToImplMapping(ClientDrivenBeanInfo cdbi) {
      if (!cdbi.hasRemoteClientView()) {
         return null;
      } else {
         Map classes = new HashMap();
         NamingConvention nc = new NamingConvention(cdbi.getBeanClassName(), cdbi.getEJBName());
         if (cdbi.hasDeclaredRemoteHome()) {
            classes.put(cdbi.getHomeInterfaceClass(), nc.getHomeClassName());
            classes.put(cdbi.getRemoteInterfaceClass(), nc.getEJBObjectClassName());
         }

         if (cdbi.isSessionBean()) {
            Iterator var4 = ((weblogic.ejb.container.interfaces.SessionBeanInfo)cdbi).getBusinessRemotes().iterator();

            while(var4.hasNext()) {
               Class iface = (Class)var4.next();
               classes.put(iface, nc.getRemoteBusinessImplClassName(iface));
            }
         }

         return classes;
      }
   }

   private Set runRmic(Map map, ClientDrivenBeanInfo cdbi) throws ErrorCollectionException {
      try {
         Set rmiSources = new HashSet();
         ClassLoader cl = cdbi.getClassLoader();
         Iterator var5 = map.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry e = (Map.Entry)var5.next();
            RMICOptions rmicOptions = this.buildRmicOptions((Class)e.getKey(), cdbi);
            String[] commandArray = this.getRmicCommandOptions(rmicOptions, (String)e.getValue());
            Collection mds = rmicOptions.getRmicMethodDescriptors();
            String[] var10 = rmic.main_nocompile(commandArray, cl, mds);
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String s = var10[var12];
               rmiSources.add(s);
            }
         }

         return rmiSources;
      } catch (Exception var14) {
         throw new ErrorCollectionException(var14);
      }
   }

   private boolean isHomeClass(Class c) {
      return EJBHome.class.isAssignableFrom(c);
   }

   private RMICOptions buildRmicOptions(Class iface, ClientDrivenBeanInfo cdbi) {
      RMICOptions rmicOptions = new RMICOptions(cdbi);
      rmicOptions.setIIOPSecurityOptions();
      if (this.isHomeClass(iface)) {
         rmicOptions.setHomeOptions();
      } else {
         rmicOptions.setEOOptions(iface);
      }

      return rmicOptions;
   }

   private String[] getRmicCommandOptions(RMICOptions rmicOptions, String sourceFile) {
      List newOptionsList = rmicOptions.asList();
      boolean hasDispatchPolicy = newOptionsList.contains("-dispatchPolicy");
      boolean hasStickToFirstServer = newOptionsList.contains("-stickToFirstServer");
      Getopt2 newOpts = (Getopt2)this.opts.clone();
      if (hasDispatchPolicy && newOpts.hasOption("dispatchPolicy")) {
         newOpts.removeOption("dispatchPolicy");
      }

      if (hasStickToFirstServer && newOpts.hasOption("stickToFirstServer")) {
         newOpts.removeOption("stickToFirstServer");
      }

      String[] oldOptions = newOpts.asCommandArray();

      for(int j = 0; j < oldOptions.length; ++j) {
         if (oldOptions[j].equals("-output")) {
            ++j;
         } else if (oldOptions[j].equals("-maxfiles")) {
            ++j;
         } else if (oldOptions[j].equals("-plan")) {
            ++j;
         } else if (oldOptions[j].equals("-clientJarOutputDir")) {
            ++j;
         } else if (!oldOptions[j].equals("-nodeploy") && !oldOptions[j].equals("-nocompliance") && !oldOptions[j].equals("-lineNumbers") && !oldOptions[j].equals("-forceGeneration") && !oldOptions[j].equals("-basicClientJar") && !oldOptions[j].equals("-quiet") && !oldOptions[j].equals("-convertDDs") && !oldOptions[j].equals("-writeInferredDescriptors")) {
            newOptionsList.add(oldOptions[j]);
         }
      }

      newOptionsList.add(sourceFile);
      return (String[])newOptionsList.toArray(new String[newOptionsList.size()]);
   }

   private void addIIOPOptionsToOpts() {
      this.opts.addOption("integrity", "integrity", "IIOP Transport integrity");
      this.opts.addOption("confidentiality", "confidentiality", "IIOP Transport confidentiality");
      this.opts.addOption("clientCertAuthentication", "clientCertAuthentication", "IIOP Transport clientCertAuthentication");
      this.opts.addOption("clientAuthentication", "clientAuthentication", "clientAuthentication");
      this.opts.addOption("identityAssertion", "identityAssertion", "identityAssertion");
   }

   private static void debug(String s) {
      debugLogger.debug("[EJBCompiler] " + s);
   }

   public void populateValidationInfo(GenericClassLoader cl, EjbDescriptorBean desc, VirtualJarFile jf, boolean isCDIEnabled, ModuleValidationInfo mvi) throws ErrorCollectionException {
      DeploymentInfo deploymentInfo = null;

      try {
         deploymentInfo = getStandAloneDeploymentInfo(cl, jf, desc, isCDIEnabled);
      } catch (Exception var8) {
         throw new ErrorCollectionException(var8);
      }

      if (mvi != null) {
         populateMVI(mvi, deploymentInfo);
      }

   }

   private static void populateMVI(ModuleValidationInfo mvi, DeploymentInfo di) {
      Iterator var2 = di.getBeanInfos().iterator();

      label113:
      while(var2.hasNext()) {
         BeanInfo bi = (BeanInfo)var2.next();
         mvi.addEJBValidationInfo(bi.getEJBName(), bi);
         Iterator var4 = bi.getAllEJBReferences().iterator();

         while(var4.hasNext()) {
            EjbRefBean eref = (EjbRefBean)var4.next();
            if (eref.getEjbLink() != null) {
               mvi.addEJBRef(bi.getEJBName(), eref.getEjbRefName(), false, eref.getRemote(), eref.getHome(), eref.getEjbRefType(), eref.getEjbLink(), false);
            }
         }

         var4 = bi.getAllEJBLocalReferences().iterator();

         while(var4.hasNext()) {
            EjbLocalRefBean eref = (EjbLocalRefBean)var4.next();
            if (eref.getEjbLink() != null) {
               mvi.addEJBRef(bi.getEJBName(), eref.getEjbRefName(), true, eref.getLocal(), eref.getLocalHome(), eref.getEjbRefType(), eref.getEjbLink(), true);
            }
         }

         if (bi instanceof EntityBeanInfo) {
            String cacheName = ((EntityBeanInfo)bi).getCacheName();
            if (cacheName != null) {
               mvi.addAppScopedCacheReference(bi.getEJBName(), cacheName);
            }
         }

         var4 = bi.getAllWlResourceReferences().iterator();

         while(true) {
            String resRefType;
            Iterator var7;
            ResourceDescriptionBean wlResRef;
            do {
               if (!var4.hasNext()) {
                  var4 = bi.getAllWlResourceEnvReferences().iterator();

                  while(true) {
                     ResourceEnvDescriptionBean wlResEnvRef;
                     do {
                        if (!var4.hasNext()) {
                           continue label113;
                        }

                        wlResEnvRef = (ResourceEnvDescriptionBean)var4.next();
                     } while(wlResEnvRef.getResourceLink() == null);

                     resRefType = null;
                     var7 = bi.getAllResourceEnvReferences().iterator();

                     while(var7.hasNext()) {
                        ResourceEnvRefBean resEnvRef = (ResourceEnvRefBean)var7.next();
                        if (resEnvRef.getResourceEnvRefName().equals(wlResEnvRef.getResourceEnvRefName())) {
                           resRefType = resEnvRef.getResourceEnvRefType();
                           break;
                        }
                     }

                     if (resRefType != null && resRefType.startsWith("javax.jms.")) {
                        mvi.addJMSLinkRefs(bi.getEJBName(), "EJB", wlResEnvRef.getResourceEnvRefName(), wlResEnvRef.getResourceLink(), resRefType, false);
                     }

                     if (resRefType != null && resRefType.startsWith("javax.sql.")) {
                        mvi.addJDBCLinkRefs(bi.getEJBName(), "EJB", wlResEnvRef.getResourceEnvRefName(), wlResEnvRef.getResourceLink(), resRefType, false);
                     }
                  }
               }

               wlResRef = (ResourceDescriptionBean)var4.next();
            } while(wlResRef.getResourceLink() == null);

            resRefType = null;
            var7 = bi.getAllResourceReferences().iterator();

            while(var7.hasNext()) {
               ResourceRefBean resRef = (ResourceRefBean)var7.next();
               if (resRef.getResRefName().equals(wlResRef.getResRefName())) {
                  resRefType = resRef.getResType();
                  break;
               }
            }

            if (resRefType != null && resRefType.startsWith("javax.jms.")) {
               mvi.addJMSLinkRefs(bi.getEJBName(), "EJB", wlResRef.getResRefName(), wlResRef.getResourceLink(), resRefType, false);
            }

            if (resRefType != null && resRefType.startsWith("javax.sql.")) {
               mvi.addJDBCLinkRefs(bi.getEJBName(), "EJB", wlResRef.getResRefName(), wlResRef.getResourceLink(), resRefType, false);
            }
         }
      }

   }

   private static DeploymentInfo getStandAloneDeploymentInfo(GenericClassLoader cl, VirtualJarFile jf, EjbDescriptorBean desc, boolean isCDIEnabled) throws Exception {
      return new DeploymentInfoImpl(desc, cl, "", "", "", jf, (ApplicationContextInternal)null, isCDIEnabled);
   }

   public void setCompilerFactory(ICompilerFactory compilerFactory) {
      this.compilerFactory = compilerFactory;
      this.ejb2rmi.setCompilerFactory(compilerFactory);
   }

   static {
      debugLogger = EJBDebugService.compilationLogger;
   }
}
