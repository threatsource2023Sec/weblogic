package weblogic.ejb.tools;

import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.CDIUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.shared.ModuleType;
import javax.interceptor.Interceptor;
import javax.xml.stream.XMLStreamException;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Extensible;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.ParentModule;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.utils.ContextUtils;
import weblogic.application.utils.PersistenceUtils;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorHelper;
import weblogic.ejb.container.cmp.rdbms.Deployer;
import weblogic.ejb.container.cmp.rdbms.RDBMSDescriptor;
import weblogic.ejb.container.cmp11.rdbms.RDBMSDeploymentInfo;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.deployer.EjbJarArchive;
import weblogic.ejb.container.deployer.ModuleExtensionContextImpl;
import weblogic.ejb.container.metadata.EJBDescriptorBeanUtils;
import weblogic.ejb.container.metadata.EjbAnnotationProcessor;
import weblogic.ejb.spi.EJBC;
import weblogic.ejb.spi.EJBCFactory;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.j2ee.wsee.compiler.WSEEModuleHelper;
import weblogic.j2ee.wsee.deploy.WSEEDescriptor;
import weblogic.j2ee.wsee.policy.deployment.WsPolicyDescriptor;
import weblogic.kernel.KernelStatus;
import weblogic.logging.Loggable;
import weblogic.persistence.PersistenceUnitViewer;
import weblogic.utils.BadOptionException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.Getopt2;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.compiler.jdt.JDTJavaCompilerFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public class EJBModule implements ToolsModule, Extensible, ParentModule {
   private static final String WSEE_EJB_URI_81 = "META-INF/web-services.xml";
   private GenericClassLoader moduleClassLoader;
   private ModuleContext state;
   private EjbJarArchive ejbJarArchive;
   private ModuleExtensionContext extCtx;
   private final String altDD;
   private final String moduleUri;
   private ToolsContext ctx;
   private EjbDescriptorBean desc;
   private WSEEModuleHelper wseeHelper;
   private Map descriptors;

   EJBModule(String uri, String altDD) {
      this.moduleUri = uri;
      this.altDD = altDD;
   }

   public String getAltDD() {
      return this.altDD;
   }

   public String getURI() {
      return this.moduleUri;
   }

   public ClassFinder init(ModuleContext mc, ToolsContext toolsCtx, GenericClassLoader parentCL) throws ToolFailureException {
      this.state = mc;
      this.ctx = toolsCtx;
      MultiClassFinder moduleClassFinder = new MultiClassFinder();
      moduleClassFinder.addFinder(new ClasspathClassFinder2(this.state.getOutputDir().getPath()));
      if (ContextUtils.isSplitDir(toolsCtx)) {
         File[] var5 = toolsCtx.getEar().getModuleRoots(this.getURI());
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File mr = var5[var7];
            moduleClassFinder.addFinder(new ClasspathClassFinder2(mr.getAbsolutePath()));
         }
      }

      if (parentCL != null) {
         try {
            PersistenceUtils.addRootPersistenceJars(toolsCtx.getAppClassLoader(), toolsCtx.getApplicationId(), toolsCtx.getApplicationDD());
            PersistenceUnitViewer perViewer = new PersistenceUnitViewer.ResourceViewer(toolsCtx.getAppClassLoader(), toolsCtx.getApplicationId(), toolsCtx.getConfigDir(), toolsCtx.getPlanBean());
            perViewer.loadDescriptors();
         } catch (IOException var9) {
            throw new ToolFailureException("Unable to load persistence descriptors", var9);
         }

         parentCL.addClassFinder(moduleClassFinder);
         this.moduleClassLoader = new GenericClassLoader(parentCL);
      } else {
         this.moduleClassLoader = new GenericClassLoader(moduleClassFinder);
      }

      this.moduleClassLoader.setAnnotation(new Annotation(toolsCtx.getApplicationId(), this.getURI()));
      this.ejbJarArchive = new EjbJarArchive(mc, (ApplicationContextInternal)null, moduleClassFinder);
      this.extCtx = new ModuleExtensionContextImpl(mc, moduleClassFinder, this.ejbJarArchive);
      return moduleClassFinder;
   }

   public boolean needsClassLoader() {
      return false;
   }

   public Map compile(GenericClassLoader cl) throws ToolFailureException {
      if (this.ctx.isVerbose() && !KernelStatus.isServer()) {
         System.setProperty("weblogic.debug.DebugEjbCompilation", "true");
      }

      try {
         this.compileEJB(cl, this.state, this.ctx.getOpts());
         if (this.ctx.getModules().length == 1) {
            JDTJavaCompilerFactory.getInstance().resetCache(cl);
         }
      } catch (ErrorCollectionException var5) {
         Loggable l = J2EELogger.logAppcErrorsEncounteredCompilingModuleLoggable(this.getURI(), var5.toString());
         throw new ToolFailureException(l.getMessage(), var5);
      }

      if (this.ctx.isWriteInferredDescriptors()) {
         try {
            this.desc = EjbDescriptorFactory.createDescriptor(this.state);
         } catch (XMLProcessingException | IOException | XMLStreamException | XMLParsingException var4) {
            throw new ToolFailureException("Error parsing EJB descriptor", var4);
         }

         if (this.desc.getEjbJarBean() == null || !this.desc.getEjbJarBean().isMetadataComplete()) {
            this.backupDescriptors(this.desc);
            this.processAnnotations(this.desc);
            this.write();
         }
      }

      return Collections.emptyMap();
   }

   private EjbDescriptorBean parseDescriptors(ModuleContext mc, String uri, GenericClassLoader cl) throws ErrorCollectionException {
      try {
         return EjbDescriptorFactory.createReadOnlyDescriptorFromJarFile(mc.getVirtualJarFile(), mc.getAltDDFile(), mc.getConfigDir(), mc.getPlan(), (String)null, uri, cl, (VirtualJarFile[])null, this.getApplicationAnnotatedClassesFromContext());
      } catch (XMLProcessingException | IOException | XMLStreamException | AnnotationProcessingException | XMLParsingException var5) {
         throw new ErrorCollectionException(var5);
      }
   }

   public void cleanup() {
      if (this.moduleClassLoader != null) {
         this.moduleClassLoader.close();
      }

      if (this.ejbJarArchive != null) {
         this.ejbJarArchive.reset();
      }

   }

   public Map merge() throws ToolFailureException {
      this.descriptors = new HashMap();

      try {
         this.desc = EjbDescriptorFactory.createDescriptor(this.state);
         this.wseeHelper = new WSEEModuleHelper(this.ctx, this.getModuleExtensionContext(), this.state.getVirtualJarFile(), this.getURI(), false);
         if (this.ctx.isWriteInferredDescriptors() && (this.desc.getEjbJarBean() == null || !this.desc.getEjbJarBean().isMetadataComplete())) {
            this.backupDescriptors(this.desc);
         }

         this.processAnnotations(this.desc);
         if (this.desc.getEjbJarBean() != null) {
            this.descriptors.put("META-INF/ejb-jar.xml", (DescriptorBean)this.desc.getEjbJarBean());
         }

         if (this.desc.getWeblogicEjbJarBean() != null && (!this.desc.isWeblogicEjbJarSynthetic() || this.ctx.isBeanScaffoldingEnabled())) {
            if (this.desc.getEjbJarBean() != null && this.ctx.isBeanScaffoldingEnabled()) {
               this.completeWeblogicEjbJar(this.desc);
            }

            this.descriptors.put("META-INF/weblogic-ejb-jar.xml", (DescriptorBean)this.desc.getWeblogicEjbJarBean());
         }

         PersistenceUnitViewer perViewer = new PersistenceUnitViewer.EntryViewer(this.state.getVirtualJarFile(), this.getURI(), this.ctx.getConfigDir(), this.ctx.getPlanBean());
         perViewer.loadDescriptors();
         Iterator uris = perViewer.getDescriptorURIs();

         while(uris.hasNext()) {
            String uri = (String)uris.next();
            this.descriptors.put(uri, perViewer.getDescriptor(uri).getRootBean());
         }

         if (this.wseeHelper.getWsBean() != null) {
            this.descriptors.put("META-INF/webservices.xml", (DescriptorBean)this.wseeHelper.getWsBean());
         }

         if (this.wseeHelper.getWlWsBean() != null) {
            this.descriptors.put("META-INF/weblogic-webservices.xml", (DescriptorBean)this.wseeHelper.getWlWsBean());
         }

         try {
            if (this.state.getVirtualJarFile().getEntry("META-INF/web-services.xml") != null) {
               WSEEDescriptor wseeDes81 = new WSEEDescriptor(new File(this.state.getOutputDir(), "META-INF/web-services.xml"), (File)null, (DeploymentPlanBean)null, (String)null);
               this.descriptors.put("META-INF/web-services.xml", (DescriptorBean)wseeDes81.getWebservicesBean());
            }
         } catch (Exception var14) {
         }

         WsPolicyDescriptor wspdes = new WsPolicyDescriptor(this.state.getVirtualJarFile(), this.ctx.getConfigDir(), this.ctx.getPlanBean(), this.getURI());

         try {
            if (wspdes.getWebservicesPolicyBean() != null) {
               this.descriptors.put("META-INF/weblogic-webservices-policy.xml", (DescriptorBean)wspdes.getWebservicesPolicyBean());
            }
         } catch (Exception var13) {
         }

         Map urizeMap = new HashMap();
         Map uriNameMap = new HashMap();
         Set cmpEJBNames = EJBDescriptorBeanUtils.getCMPEJBNames(this.desc);
         Iterator var6 = cmpEJBNames.iterator();

         String uri;
         PersistenceBean wlRdbmsJarBean;
         ZipEntry ze;
         while(var6.hasNext()) {
            String cmpEJBName = (String)var6.next();
            uri = null;
            wlRdbmsJarBean = EJBDescriptorBeanUtils.getPersistenceBean(cmpEJBName, this.desc);
            if (wlRdbmsJarBean != null && wlRdbmsJarBean.isPersistenceUseSet()) {
               uri = wlRdbmsJarBean.getPersistenceUse().getTypeStorage();
            }

            if (uri != null) {
               ze = this.state.getVirtualJarFile().getEntry(uri);
               if (ze != null) {
                  urizeMap.put(uri, ze);
                  uriNameMap.put(uri, cmpEJBName);
               } else {
                  File dd = new File(new File(this.ctx.getConfigDir(), this.getURI()), uri);
                  if (dd.exists()) {
                     DescriptorBean bean = this.parseWLSCMPDescriptor(dd);
                     if (bean != null) {
                        this.descriptors.put(uri, bean);
                     }
                  }
               }
            }
         }

         var6 = urizeMap.entrySet().iterator();

         while(true) {
            if (!var6.hasNext()) {
               DescriptorBean diagDD = WLDFDescriptorHelper.getDiagnosticDescriptor(this.getURI(), this.getModuleType().toString(), this.state.getVirtualJarFile(), this.ctx.getPlanBean(), this.ctx.getConfigDir(), this.ctx.getEar() == null);
               if (diagDD != null) {
                  this.descriptors.put("META-INF/weblogic-diagnostics.xml", diagDD);
               }
               break;
            }

            Map.Entry entry = (Map.Entry)var6.next();
            uri = (String)entry.getKey();
            wlRdbmsJarBean = null;
            ze = (ZipEntry)entry.getValue();
            DescriptorBean wlRdbmsJarBean;
            if (this.isSchemaBasedDD(ze)) {
               RDBMSDescriptor rdbmsDes = new RDBMSDescriptor(this.state.getVirtualJarFile(), uri, (String)null, this.getURI(), this.ctx.getPlanBean(), this.ctx.getConfigDir());
               wlRdbmsJarBean = rdbmsDes.getDescriptorBean();
            } else {
               wlRdbmsJarBean = this.xpiParseWLSCMPDescriptor(ze, uri, (String)uriNameMap.get(uri), this.desc);
            }

            if (wlRdbmsJarBean != null) {
               this.descriptors.put(uri, wlRdbmsJarBean);
            }
         }
      } catch (Exception var15) {
         throw new ToolFailureException("Unable to parse EJB descriptor", var15);
      }

      return this.descriptors;
   }

   private void completeWeblogicEjbJar(EjbDescriptorBean desc) {
      EnterpriseBeansBean enterpriseBeansBean = desc.getEjbJarBean().getEnterpriseBeans();
      WeblogicEjbJarBean wlEjbJarBean = desc.getWeblogicEjbJarBean();
      Map wlBeanMap = new HashMap();
      WeblogicEnterpriseBeanBean[] var5 = wlEjbJarBean.getWeblogicEnterpriseBeans();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         WeblogicEnterpriseBeanBean webb = var5[var7];
         wlBeanMap.put(webb.getEjbName(), webb);
      }

      SessionBeanBean[] var10 = enterpriseBeansBean.getSessions();
      var6 = var10.length;

      WeblogicEnterpriseBeanBean webb;
      for(var7 = 0; var7 < var6; ++var7) {
         SessionBeanBean sbb = var10[var7];
         webb = (WeblogicEnterpriseBeanBean)wlBeanMap.get(sbb.getEjbName());
         if (webb == null) {
            webb = wlEjbJarBean.createWeblogicEnterpriseBean();
            webb.setEjbName(sbb.getEjbName());
         }
      }

      EntityBeanBean[] var11 = enterpriseBeansBean.getEntities();
      var6 = var11.length;

      for(var7 = 0; var7 < var6; ++var7) {
         EntityBeanBean entityBeanBean = var11[var7];
         webb = (WeblogicEnterpriseBeanBean)wlBeanMap.get(entityBeanBean.getEjbName());
         if (webb == null) {
            webb = wlEjbJarBean.createWeblogicEnterpriseBean();
            webb.setEjbName(entityBeanBean.getEjbName());
         }
      }

      MessageDrivenBeanBean[] var12 = enterpriseBeansBean.getMessageDrivens();
      var6 = var12.length;

      for(var7 = 0; var7 < var6; ++var7) {
         MessageDrivenBeanBean mdbb = var12[var7];
         webb = (WeblogicEnterpriseBeanBean)wlBeanMap.get(mdbb.getEjbName());
         if (webb == null) {
            webb = wlEjbJarBean.createWeblogicEnterpriseBean();
            webb.setEjbName(mdbb.getEjbName());
         }
      }

   }

   private DescriptorBean parseWLSCMPDescriptor(File dd) throws Exception {
      BufferedInputStream stream = null;

      DescriptorBean var4;
      try {
         stream = new BufferedInputStream(new FileInputStream(dd));
         EditableDescriptorManager edm = new EditableDescriptorManager();
         var4 = edm.createDescriptor(stream).getRootBean();
      } finally {
         this.closeAndIgnore(stream);
      }

      return var4;
   }

   private DescriptorBean xpiParseWLSCMPDescriptor(ZipEntry ze, String uri, String ejbName, EjbDescriptorBean ejbDescBean) throws Exception {
      BufferedInputStream stream = null;

      DescriptorBean var9;
      try {
         stream = new BufferedInputStream(this.state.getVirtualJarFile().getInputStream(ze));
         DescriptorBean var6 = (DescriptorBean)Deployer.parseXMLFile(stream, uri, ejbDescBean, (Map)null);
         return var6;
      } catch (RDBMSException var13) {
         weblogic.ejb.container.cmp11.rdbms.Deployer deployer = new weblogic.ejb.container.cmp11.rdbms.Deployer();
         RDBMSDeploymentInfo rdbmsDI = deployer.parseXMLFile(this.state.getVirtualJarFile(), uri, ejbName, new ProcessorFactory(), ejbDescBean);
         var9 = (DescriptorBean)rdbmsDI.getWeblogicRdbmsJarBean();
      } finally {
         this.closeAndIgnore(stream);
      }

      return var9;
   }

   private boolean isSchemaBasedDD(ZipEntry ze) throws IOException {
      BufferedInputStream stream = null;

      boolean var3;
      try {
         stream = new BufferedInputStream(this.state.getVirtualJarFile().getInputStream(ze));
         var3 = Deployer.isSchemaBasedDD(stream);
      } finally {
         this.closeAndIgnore(stream);
      }

      return var3;
   }

   private void closeAndIgnore(Closeable closeable) {
      try {
         if (closeable != null) {
            closeable.close();
         }
      } catch (IOException var3) {
      }

   }

   private boolean wseeAnnotationsEnabled(EjbJarBean ejbBean) {
      String schemaVersion = ((DescriptorBean)ejbBean).getDescriptor().getOriginalVersionInfo();
      return !"DTD".equalsIgnoreCase(schemaVersion) && (double)Float.parseFloat(schemaVersion) >= 3.0;
   }

   public void populateValidationInfo(GenericClassLoader cl) throws ToolFailureException {
      EJBC ejbCompiler = EJBCFactory.createEJBC(this.ctx.getOpts(), (File)null);

      try {
         ModuleValidationInfo mvi = this.state.getValidationInfo();
         String uri = mvi == null ? null : mvi.getURI();
         EjbDescriptorBean ejbDesc = this.parseDescriptors(this.state, uri, cl);
         boolean isCDIEnabled = CDIUtils.isModuleCdiEnabled(this.state, this.getModuleExtensionContext(), (ApplicationContextInternal)null);
         ejbCompiler.populateValidationInfo(cl, ejbDesc, this.state.getVirtualJarFile(), isCDIEnabled, this.state.getValidationInfo());
      } catch (ErrorCollectionException | InjectionException var7) {
         throw new ToolFailureException(J2EELogger.logAppcUnableToContinueProcessingFileLoggable(this.ctx.getSourceFile().getAbsolutePath(), var7.toString()).getMessage(), var7);
      }
   }

   public void write() throws ToolFailureException {
      if (this.ctx.isWriteInferredDescriptors()) {
         this.desc.getEjbJarBean().setMetadataComplete(true);
      }

      if (this.descriptors != null) {
         Iterator var1 = this.descriptors.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            if ((DescriptorBean)entry.getValue() != null) {
               AppcUtils.writeDescriptor(this.state.getOutputDir(), (String)entry.getKey(), (DescriptorBean)entry.getValue());
            }
         }
      } else {
         AppcUtils.writeDescriptor(this.state.getOutputDir(), "META-INF/ejb-jar.xml", (DescriptorBean)this.desc.getEjbJarBean());
         AppcUtils.writeDescriptor(this.state.getOutputDir(), "META-INF/weblogic-ejb-jar.xml", (DescriptorBean)this.desc.getWeblogicEjbJarBean());
      }

   }

   public ModuleType getModuleType() {
      return ModuleType.EJB;
   }

   private void backupDescriptors(EjbDescriptorBean ejbDescBean) throws ToolFailureException {
      if (this.state.getVirtualJarFile().getEntry("META-INF/weblogic-ejb-jar.xml") != null) {
         AppcUtils.writeDescriptor(this.state.getOutputDir(), "META-INF/weblogic-ejb-jar.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)ejbDescBean.getWeblogicEjbJarBean());
      }

      if (this.state.getVirtualJarFile().getEntry("META-INF/ejb-jar.xml") != null) {
         AppcUtils.writeDescriptor(this.state.getOutputDir(), "META-INF/ejb-jar.xml" + ContextUtils.ORIGINAL_DESCRIPTOR_SUFFIX, (DescriptorBean)ejbDescBean.getEjbJarBean());
      }

   }

   private void processAnnotations(EjbDescriptorBean ejbDescBean) throws ToolFailureException {
      if (!this.ctx.isBasicView() && (this.ctx.isReadOnlyInvocation() || this.ctx.isWriteInferredDescriptors())) {
         if (ejbDescBean.getEjbJarBean() == null || ejbDescBean.verSupportsAnnotatedEjbs() && !ejbDescBean.getEjbJarBean().isMetadataComplete()) {
            try {
               ejbDescBean.setEjb30(true);
               EjbAnnotationProcessor eap = new EjbAnnotationProcessor(this.moduleClassLoader, ejbDescBean);
               Set classes = this.ejbJarArchive.getAnnotatedClasses((Class[])DDConstants.TOP_LEVEL_ANNOS.toArray(new Class[DDConstants.TOP_LEVEL_ANNOS.size()]));
               eap.processAnnotations(classes, this.getApplicationAnnotatedClassesFromContext());
               eap.processWLSAnnotations();
               EjbJarBean ejbJarBean = ejbDescBean.getEjbJarBean();
               if (this.wseeAnnotationsEnabled(ejbJarBean) && ejbJarBean.getEnterpriseBeans() != null) {
                  SessionBeanBean[] sessionBeanBeans = ejbJarBean.getEnterpriseBeans().getSessions();
                  String[][] ejbLinks = new String[sessionBeanBeans.length][2];

                  for(int i = 0; i < sessionBeanBeans.length; ++i) {
                     ejbLinks[i][0] = sessionBeanBeans[i].getEjbName();
                     ejbLinks[i][1] = sessionBeanBeans[i].getEjbClass();
                  }

                  if (this.wseeHelper == null) {
                     this.wseeHelper = new WSEEModuleHelper(this.ctx, this.getModuleExtensionContext(), this.state.getVirtualJarFile(), this.getURI(), false);
                  }

                  this.wseeHelper.processAnnotations(this.state.getVirtualJarFile(), this.moduleClassLoader, ejbLinks, sessionBeanBeans);
               }
            } catch (NoClassDefFoundError | ErrorCollectionException | AnnotationProcessingException | ClassNotFoundException var8) {
               var8.printStackTrace();
               if (this.ctx.verifyLibraryReferences()) {
                  throw new ToolFailureException("Unable to process annotations for module " + this.getURI(), var8);
               }
            }

         }
      }
   }

   private Set getApplicationAnnotatedClassesFromContext() throws AnnotationProcessingException {
      return this.ctx.getEar() == null ? null : this.ctx.getAnnotatedClasses(new Class[]{Interceptor.class});
   }

   private void compileEJB(GenericClassLoader cl, ModuleContext mc, Getopt2 opts) throws ErrorCollectionException {
      Getopt2 ejbOpts = (Getopt2)opts.clone();

      try {
         ejbOpts.setOption("d", mc.getOutputDir().getPath());
         if (ejbOpts.containsOption("k")) {
            ejbOpts.removeOption("k");
         }

         if (ejbOpts.containsOption("manifest")) {
            ejbOpts.removeOption("manifest");
         }

         if (ejbOpts.containsOption("ignorePlanValidation")) {
            ejbOpts.removeOption("ignorePlanValidation");
         }
      } catch (BadOptionException var11) {
         throw new AssertionError(var11);
      }

      ModuleValidationInfo mvi = mc.getValidationInfo();
      String uri = mvi == null ? this.getUriFromPlan(mc.getPlan()) : mvi.getURI();
      EjbDescriptorBean ejbDesc = this.parseDescriptors(mc, uri, cl);
      boolean isCDIEnabled = false;

      try {
         isCDIEnabled = CDIUtils.isModuleCdiEnabled(mc, this.getModuleExtensionContext(), (ApplicationContextInternal)null);
      } catch (InjectionException var10) {
         throw new ErrorCollectionException(var10);
      }

      EJBC ejbCompiler = EJBCFactory.createEJBC(ejbOpts, mc.getOutputDir());
      ejbCompiler.compileEJB(cl, ejbDesc, mc.getVirtualJarFile(), mvi, mc.getRegistry(), isCDIEnabled);
   }

   private String getUriFromPlan(DeploymentPlanBean plan) {
      if (plan == null) {
         return null;
      } else {
         ModuleOverrideBean[] var2 = plan.getModuleOverrides();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ModuleOverrideBean mov = var2[var4];
            if (plan.rootModule(mov.getModuleName())) {
               return mov.getModuleName();
            }
         }

         return null;
      }
   }

   public String getType() {
      return ModuleType.EJB.toString();
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      return this.extCtx;
   }

   public Descriptor getStandardDescriptor() {
      return this.desc != null && this.desc.getEjbJarBean() != null ? ((DescriptorBean)this.desc.getEjbJarBean()).getDescriptor() : null;
   }

   public String toString() {
      return this.getURI();
   }

   public String getStandardDescriptorURI() {
      return "META-INF/ejb-jar.xml";
   }

   public String[] getApplicationNameXPath() {
      return new String[]{"ejb-jar", "module-name"};
   }

   public boolean isDeployableObject() {
      return true;
   }

   public void enhanceDeploymentView(EditableDeployableObject deployableObject) {
   }

   public String getWLExtensionDirectory() {
      return null;
   }
}
