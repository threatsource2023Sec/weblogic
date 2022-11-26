package weblogic.ejb.container.metadata;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.shared.ModuleType;
import javax.interceptor.Interceptor;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.InputSource;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.naming.ModuleRegistry;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.WeblogicJarChecker;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.dd.xml.DDLoader;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.deployer.Archive;
import weblogic.ejb.container.deployer.EjbJarArchive;
import weblogic.ejb.spi.EJBJarUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorReader;
import weblogic.j2ee.descriptor.ActivationConfigBean;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.SecurityRoleRefBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JndiBindingBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.DelegateFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;
import weblogic.utils.classloaders.ZipSource;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.ProcessorFactoryException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;
import weblogic.xml.stax.XMLStreamInputFactory;

public final class EjbDescriptorReaderImpl implements EjbDescriptorReader {
   private static final DebugLogger DEBUG_LOGGER;
   private static final String[] VALID_WEBLOGIC_EJB_JAR_PUBLIC_IDS;
   private static final int BUF_SIZE = 16384;
   private static final int MAX_DTD_ELEMENTS = 10;
   private static final XMLInputFactory XML_INPUT_FACTORY;

   public EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jar) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return this.createDescriptorFromJarFile(jar, (File)null);
   }

   public EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jar, File altDD) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return this.createDescriptorFromJarFile(jar, altDD, (String)null, (String)null);
   }

   public EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jar, File altDD, String appName, String uri) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return this.createDescriptorFromJarFile(jar, altDD, (File)null, (DeploymentPlanBean)null, appName, uri);
   }

   public EjbDescriptorBean createDescriptor(ModuleContext mc) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      EjbDescriptorBean desc = this.createEjbDescriptorBean(mc.getApplicationId(), mc.getURI(), mc.getPlan(), mc.getConfigDir(), false);
      String descRoot = this.getDescriptorRoot(mc.getType());
      String descUri = descRoot + "ejb-jar.xml";
      this.processEjbJarXML(desc, mc.getAltDDFile(), descUri, this.getDescriptorSource(mc.getVirtualJarFile(), descUri), (VirtualJarFile[])null);
      descUri = descRoot + "weblogic-ejb-jar.xml";
      this.processWeblogicEjbJarXML(desc, descUri, this.getDescriptorSource(mc.getVirtualJarFile(), descUri));
      this.translateJndiNames(desc, false);
      return desc;
   }

   public EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jar, File altDD, File config, DeploymentPlanBean plan, String appName, String uri) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      EjbDescriptorBean desc = this.createEjbDescriptorBean(appName, uri, plan, config, false);
      String descRoot = this.getDescriptorRoot(ModuleType.EJB.toString());
      String descUri = descRoot + "ejb-jar.xml";
      this.processEjbJarXML(desc, altDD, descUri, this.getDescriptorSource(jar, descUri), (VirtualJarFile[])null);
      descUri = descRoot + "weblogic-ejb-jar.xml";
      this.processWeblogicEjbJarXML(desc, descUri, this.getDescriptorSource(jar, descUri));
      this.translateJndiNames(desc, false);
      return desc;
   }

   private void translateJndiNames(EjbDescriptorBean ed, boolean isReadOnly) {
      if (ed.getEjbJarBean() != null) {
         EnterpriseBeansBean ebb = ed.getEjbJarBean().getEnterpriseBeans();
         if (ebb != null) {
            WeblogicEnterpriseBeanBean[] var4 = ed.getWeblogicEjbJarBean().getWeblogicEnterpriseBeans();
            int var5 = var4.length;

            label47:
            for(int var6 = 0; var6 < var5; ++var6) {
               WeblogicEnterpriseBeanBean wlBean = var4[var6];
               if (wlBean.lookupJndiBinding("_WL_HOME") != null || wlBean.lookupJndiBinding("_WL_LOCALHOME") != null) {
                  SessionBeanBean[] var8 = ebb.getSessions();
                  int var9 = var8.length;

                  int var10;
                  for(var10 = 0; var10 < var9; ++var10) {
                     SessionBeanBean session = var8[var10];
                     if (session.getEjbName().equals(wlBean.getEjbName())) {
                        this.translateJndiName(session.getHome(), session.getLocalHome(), wlBean, isReadOnly);
                        continue label47;
                     }
                  }

                  EntityBeanBean[] var13 = ebb.getEntities();
                  var9 = var13.length;

                  for(var10 = 0; var10 < var9; ++var10) {
                     EntityBeanBean entity = var13[var10];
                     if (entity.getEjbName().equals(wlBean.getEjbName())) {
                        this.translateJndiName(entity.getHome(), entity.getLocalHome(), wlBean, isReadOnly);
                        break;
                     }
                  }
               }
            }

         }
      }
   }

   private void translateJndiName(String homeName, String localHomeName, WeblogicEnterpriseBeanBean wlBean, boolean isReadOnly) {
      if (wlBean != null) {
         JndiBindingBean jndiBinding = wlBean.lookupJndiBinding("_WL_HOME");
         if (jndiBinding != null) {
            if (homeName != null) {
               jndiBinding.setClassName(homeName);
               if (wlBean.getJNDIName() != null) {
                  jndiBinding.setJndiName(wlBean.getJNDIName());
               }
            } else if (isReadOnly) {
               wlBean.destroyJndiBinding(jndiBinding);
            } else {
               EJBLogger.logNotFoundHomeForJndiName(jndiBinding.getJndiName(), "remote", wlBean.getEjbName());
            }
         }

         jndiBinding = wlBean.lookupJndiBinding("_WL_LOCALHOME");
         if (jndiBinding != null) {
            if (localHomeName != null) {
               jndiBinding.setClassName(localHomeName);
               if (wlBean.getLocalJNDIName() != null) {
                  jndiBinding.setJndiName(wlBean.getLocalJNDIName());
               }
            } else if (isReadOnly) {
               wlBean.destroyJndiBinding(jndiBinding);
            } else {
               EJBLogger.logNotFoundHomeForJndiName(jndiBinding.getJndiName(), "local", wlBean.getEjbName());
            }
         }

      }
   }

   public EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile jar, GenericClassLoader cl) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return this.createReadOnlyDescriptorFromJarFile(jar, (File)null, (File)null, (DeploymentPlanBean)null, (String)null, (String)null, cl, (VirtualJarFile[])null);
   }

   public EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile jar, File altDD, File config, DeploymentPlanBean plan, String appName, String uri, GenericClassLoader cl, VirtualJarFile[] autoRefLibFiles) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return this.createReadOnlyDescriptorFromJarFile(jar, altDD, config, plan, appName, uri, cl, autoRefLibFiles, (Set)null);
   }

   public EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile jar, File altDD, File config, DeploymentPlanBean plan, String appName, String uri, GenericClassLoader cl, VirtualJarFile[] autoRefLibFiles, Set commonAnnotatedClasses) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      GenericClassLoader classLoader = null;
      EjbDescriptorBean desc = this.createEjbDescriptorBean(appName, uri, plan, config, true);
      String descRoot = this.getDescriptorRoot(ModuleType.EJB.toString());
      String descUri = descRoot + "ejb-jar.xml";
      this.processEjbJarXML(desc, altDD, descUri, this.getDescriptorSource(jar, descUri), autoRefLibFiles);
      ClassLoader origCL = Thread.currentThread().getContextClassLoader();

      EjbDescriptorBean var32;
      try {
         classLoader = this.createAnnotationProcessorClassLoader(cl);
         Thread.currentThread().setContextClassLoader(classLoader);
         EjbJarBean ejbJar = desc.getEjbJarBean();
         EjbAnnotationProcessor ap = null;
         if (ejbJar == null || desc.verSupportsAnnotatedEjbs() && !ejbJar.isMetadataComplete()) {
            ap = new EjbAnnotationProcessor(classLoader, desc);

            try {
               Set classes = EJBJarUtils.getIdentityAnnotatedClasses(jar, classLoader);
               desc.setEjb30(true);
               ap.processAnnotations(classes, commonAnnotatedClasses);
            } catch (AnnotationProcessingException | ErrorCollectionException var27) {
               IOException ioe = new IOException("Error processing annotations. ");
               ioe.initCause(var27);
               throw ioe;
            } catch (Exception var28) {
               throw new IOException(": " + var28);
            }
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            this.logDescriptor("With annotation metadata merged in, effective ejb-jar.xml of " + jar.getName() + " is : ", (DescriptorBean)desc.getEjbJarBean());
         }

         descUri = descRoot + "weblogic-ejb-jar.xml";
         this.processWeblogicEjbJarXML(desc, descUri, this.getDescriptorSource(jar, descUri));
         WeblogicEjbJarBean wlsEjbJar = desc.getWeblogicEjbJarBean();
         if (wlsEjbJar == null || desc.verSupportsAnnotatedEjbs() && !desc.getEjbJarBean().isMetadataComplete()) {
            if (ap == null) {
               ap = new EjbAnnotationProcessor(classLoader, desc);
            }

            try {
               ap.processWLSAnnotations();
            } catch (Exception var26) {
               IOException ioe = new IOException("Error processing annotations. " + var26);
               ioe.initCause(var26);
               throw ioe;
            }
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            this.logDescriptor("With annotation metadata merged in, effective weblogic-ejb-jar.xml of " + jar.getName() + " is : ", (DescriptorBean)desc.getWeblogicEjbJarBean());
         }

         WeblogicJarChecker.validateEnterpriseBeansMinimalConfiguration(desc.getEjbJarBean(), desc.getWeblogicEjbJarBean(), uri);
         this.completeEjbJar(desc);
         this.completeWeblogicEjbJar(desc);
         this.translateJndiNames(desc, true);
         var32 = desc;
      } catch (ComplianceException var29) {
         throw new IOException(var29.getMessage());
      } finally {
         Thread.currentThread().setContextClassLoader(origCL);
         if (classLoader != null) {
            classLoader.close();
         }

      }

      return var32;
   }

   public EjbDescriptorBean loadDescriptors(ModuleContext mc, Archive archive, VirtualJarFile[] autoRefs) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      EjbDescriptorBean desc = this.createEjbDescriptorBean(mc.getApplicationId(), mc.getURI(), mc.getPlan(), mc.getConfigDir(), true);
      String descUri = archive.getStandardDescriptorRoot() + "ejb-jar.xml";
      this.processEjbJarXML(desc, mc.getAltDDFile(), descUri, archive.getSource(descUri), autoRefs);
      descUri = archive.getStandardDescriptorRoot() + "weblogic-ejb-jar.xml";
      this.processWeblogicEjbJarXML(desc, descUri, archive.getSource(descUri));
      return desc;
   }

   public void processAnnotations(EjbDescriptorBean desc, Archive archive, ModuleRegistry mr) throws IOException {
      ClassLoader origCL = Thread.currentThread().getContextClassLoader();
      ClassLoader classLoader = archive.getTemporaryClassLoader();

      try {
         EjbJarBean ejbJar = desc.getEjbJarBean();
         EjbAnnotationProcessor ap = null;
         if (ejbJar == null || desc.verSupportsAnnotatedEjbs() && !ejbJar.isMetadataComplete()) {
            try {
               Thread.currentThread().setContextClassLoader(classLoader);
               ap = new EjbAnnotationProcessor(classLoader, desc);
               ap.setProcessIcptrBindings(!archive.isCdiEnabled());
               desc.setEjb30(true);
               Set appIceptorClasses = null;
               if (archive instanceof EjbJarArchive) {
                  ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(((EjbJarArchive)archive).getModuleContext().getApplicationId());
                  if (appCtx != null && appCtx.isEar()) {
                     appIceptorClasses = appCtx.getAnnotatedClasses(new Class[]{Interceptor.class});
                  }
               }

               if (mr != null) {
                  ap.beginRecording();
               }

               ap.processAnnotations(archive.getAnnotatedClasses((Class[])DDConstants.TOP_LEVEL_ANNOS.toArray(new Class[0])), appIceptorClasses);
               if (mr != null) {
                  mr.addAnnotationProcessedClasses(ap.endRecording());
               }
            } catch (AnnotationProcessingException | ErrorCollectionException var18) {
               IOException ioe = new IOException("Error processing annotations: ");
               ioe.initCause(var18);
               throw ioe;
            } catch (Exception var19) {
               throw new IOException(var19);
            }
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            this.logDescriptor("With annotation metadata merged in, effective ejb-jar.xml of " + desc.getUri() + " is : ", (DescriptorBean)desc.getEjbJarBean());
         }

         WeblogicEjbJarBean wlsEjbJar = desc.getWeblogicEjbJarBean();
         if (wlsEjbJar == null || desc.verSupportsAnnotatedEjbs() && !desc.getEjbJarBean().isMetadataComplete()) {
            if (ap == null) {
               ap = new EjbAnnotationProcessor(classLoader, desc);
            }

            try {
               ap.processWLSAnnotations();
            } catch (Exception var17) {
               IOException ioe = new IOException("Error processing annotations: " + var17);
               ioe.initCause(var17);
               throw ioe;
            }
         }

         if (DEBUG_LOGGER.isDebugEnabled()) {
            this.logDescriptor("With annotation metadata merged in, effective weblogic-ejb-jar.xml of " + desc.getUri() + " is : ", (DescriptorBean)desc.getWeblogicEjbJarBean());
         }

         WeblogicJarChecker.validateEnterpriseBeansMinimalConfiguration(desc.getEjbJarBean(), desc.getWeblogicEjbJarBean(), desc.getUri());
         this.completeEjbJar(desc);
         this.completeWeblogicEjbJar(desc);
         this.translateJndiNames(desc, true);
      } catch (ComplianceException var20) {
         throw new IOException(var20.getMessage());
      } finally {
         Thread.currentThread().setContextClassLoader(origCL);
      }

   }

   private EjbDescriptorBean createEjbDescriptorBean(String appName, String uri, DeploymentPlanBean plan, File config, boolean readOnly) {
      EjbDescriptorBean result = new EjbDescriptorBean(readOnly);
      result.setAppName(appName);
      result.setUri(uri);
      result.setDeploymentPlan(plan);
      result.setConfigDirectory(config);
      return result;
   }

   private void processEjbJarXML(EjbDescriptorBean desc, File altDD, String documentURI, Source descSource, VirtualJarFile[] autoRefLibJars) throws IOException, XMLStreamException {
      if (altDD != null || descSource != null || autoRefLibJars != null) {
         EjbJarLoader loader = new EjbJarLoader(altDD, desc.getConfigDirectory(), desc.getDeploymentPlan(), desc.getUri(), documentURI, descSource);
         if (autoRefLibJars != null && autoRefLibJars.length > 0) {
            this.mergeAutoRefLibMetadata(loader, autoRefLibJars);
         }

         if (desc.isReadOnly()) {
            desc.setEjbJarBean((EjbJarBean)loader.loadDescriptorBean());
         } else {
            desc.setEjbJarBean((EjbJarBean)loader.loadEditableDescriptorBean());
         }

      }
   }

   private String getDescriptorRoot(String moduleType) {
      if (ModuleType.WAR.toString().equals(moduleType)) {
         return "WEB-INF/";
      } else if (ModuleType.EJB.toString().equals(moduleType)) {
         return "META-INF/";
      } else {
         throw new AssertionError("Unexpected module type : " + moduleType);
      }
   }

   private Source getDescriptorSource(VirtualJarFile vjar, String documentURI) throws IOException {
      if (vjar.isDirectory()) {
         URL url = vjar.getResource(documentURI);
         if (url != null) {
            return new URLSource(url);
         }
      } else {
         ZipEntry ze = vjar.getEntry(documentURI);
         if (ze != null) {
            return new ZipSource(vjar.getJarFile(), ze);
         }
      }

      return null;
   }

   private void completeEjbJar(EjbDescriptorBean desc) {
      if (desc.isEjb30()) {
         EjbJarBean ejbJarBean = desc.getEjbJarBean();
         SessionBeanBean[] var3 = ejbJarBean.getEnterpriseBeans().getSessions();
         int var4 = var3.length;

         int var5;
         String beanClassName;
         for(var5 = 0; var5 < var4; ++var5) {
            SessionBeanBean session = var3[var5];
            beanClassName = session.getEjbClass();
            this.addAroundInvokeDefaults(session.getAroundInvokes(), beanClassName);
            this.addLifecycleCallbackDefaults(session.getPostActivates(), beanClassName);
            this.addLifecycleCallbackDefaults(session.getPrePassivates(), beanClassName);
            this.addLifecycleCallbackDefaults(session.getPostConstructs(), beanClassName);
            this.addLifecycleCallbackDefaults(session.getPreDestroys(), beanClassName);
         }

         MessageDrivenBeanBean[] var14 = ejbJarBean.getEnterpriseBeans().getMessageDrivens();
         var4 = var14.length;

         for(var5 = 0; var5 < var4; ++var5) {
            MessageDrivenBeanBean mdb = var14[var5];
            beanClassName = mdb.getEjbClass();
            this.addAroundInvokeDefaults(mdb.getAroundInvokes(), beanClassName);
            this.addLifecycleCallbackDefaults(mdb.getPostConstructs(), beanClassName);
            this.addLifecycleCallbackDefaults(mdb.getPreDestroys(), beanClassName);
         }

         InterceptorsBean interceptorsBean = ejbJarBean.getInterceptors();
         if (interceptorsBean != null) {
            InterceptorBean[] var16 = interceptorsBean.getInterceptors();
            var5 = var16.length;

            for(int var19 = 0; var19 < var5; ++var19) {
               InterceptorBean ib = var16[var19];
               String iClassName = ib.getInterceptorClass();
               this.addAroundInvokeDefaults(ib.getAroundInvokes(), iClassName);
               this.addAroundTimeoutDefaults(ib.getAroundTimeouts(), iClassName);
               this.addLifecycleCallbackDefaults(ib.getPostActivates(), iClassName);
               this.addLifecycleCallbackDefaults(ib.getPrePassivates(), iClassName);
               this.addLifecycleCallbackDefaults(ib.getPostConstructs(), iClassName);
               this.addLifecycleCallbackDefaults(ib.getPreDestroys(), iClassName);
            }
         }

         Set declaredRoles = new HashSet();
         AssemblyDescriptorBean ad = ejbJarBean.getAssemblyDescriptor();
         if (ad != null) {
            SecurityRoleBean[] var21 = ad.getSecurityRoles();
            int var24 = var21.length;

            int var25;
            for(var25 = 0; var25 < var24; ++var25) {
               SecurityRoleBean sr = var21[var25];
               declaredRoles.add(sr.getRoleName());
            }

            SessionBeanBean[] var23 = ejbJarBean.getEnterpriseBeans().getSessions();
            var24 = var23.length;

            for(var25 = 0; var25 < var24; ++var25) {
               SessionBeanBean session = var23[var25];
               SecurityRoleRefBean[] var10 = session.getSecurityRoleRefs();
               int var11 = var10.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  SecurityRoleRefBean srr = var10[var12];
                  if (srr.getRoleLink() == null && declaredRoles.contains(srr.getRoleName())) {
                     srr.setRoleLink(srr.getRoleName());
                  }
               }
            }

         }
      }
   }

   private void addLifecycleCallbackDefaults(LifecycleCallbackBean[] lcbs, String beanClassName) {
      LifecycleCallbackBean[] var3 = lcbs;
      int var4 = lcbs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LifecycleCallbackBean lcb = var3[var5];
         if (lcb.getLifecycleCallbackClass() == null) {
            lcb.setLifecycleCallbackClass(beanClassName);
         }
      }

   }

   private void addAroundInvokeDefaults(AroundInvokeBean[] ais, String beanClassName) {
      AroundInvokeBean[] var3 = ais;
      int var4 = ais.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AroundInvokeBean ai = var3[var5];
         if (ai.getClassName() == null) {
            ai.setClassName(beanClassName);
         }
      }

   }

   private void addAroundTimeoutDefaults(AroundTimeoutBean[] ats, String beanClassName) {
      AroundTimeoutBean[] var3 = ats;
      int var4 = ats.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         AroundTimeoutBean at = var3[var5];
         if (at.getClassName() == null) {
            at.setClassName(beanClassName);
         }
      }

   }

   private GenericClassLoader createAnnotationProcessorClassLoader(GenericClassLoader cl) {
      ClassFinder finder = new NonClosingClassFinder(cl.getClassFinder());
      return new GenericClassLoader(finder, cl.getParent());
   }

   private void processWeblogicEjbJarXML(EjbDescriptorBean desc, String documentURI, Source descSource) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      boolean isDTDBased = false;
      InputStream in = null;

      try {
         if (descSource != null) {
            in = descSource.getInputStream();
            if (!(in instanceof BufferedInputStream)) {
               in = new BufferedInputStream((InputStream)in);
            }

            isDTDBased = this.isDTDBased((InputStream)in);
         }

         if (isDTDBased) {
            this.processDTDBasedWLDD(desc.getDeploymentPlan(), desc.getUri(), (InputStream)in, descSource.getURL().toString(), desc, documentURI);
         } else {
            WeblogicEjbJarLoader loader = new WeblogicEjbJarLoader((File)null, desc.getConfigDirectory(), desc.getDeploymentPlan(), desc.getUri(), documentURI, descSource);
            if (desc.isReadOnly()) {
               desc.setWeblogicEjbJarBean((WeblogicEjbJarBean)loader.loadDescriptorBean());
            } else {
               desc.setWeblogicEjbJarBean((WeblogicEjbJarBean)loader.loadEditableDescriptorBean());
            }

            if (desc.getWeblogicEjbJarBean() == null) {
               desc.createWeblogicEjbJarBean();
               desc.markWeblogicEjbJarSynthetic();
            }
         }
      } finally {
         if (in != null) {
            ((InputStream)in).close();
         }

      }

   }

   private boolean isDTDBased(InputStream in) throws IOException, XMLStreamException {
      InputStream is = new NoCloseInputStream(in);
      is.mark(16384);
      XMLStreamReader r = XML_INPUT_FACTORY.createXMLStreamReader(is);

      try {
         for(int i = 0; i < 10 && r.hasNext(); ++i) {
            boolean var5;
            if (r.isStartElement()) {
               var5 = false;
               return var5;
            }

            if (r.next() == 11) {
               var5 = true;
               return var5;
            }
         }

         boolean var9 = false;
         return var9;
      } finally {
         r.close();
         is.reset();
      }
   }

   public EjbDescriptorBean parseEjbDescriptorsForConverter(DescriptorManager dm, VirtualJarFile vjar, ModuleType modType) throws IOException, XMLStreamException {
      String descRoot = this.getDescriptorRoot(modType.toString());
      String ejbDescUri = descRoot + "ejb-jar.xml";
      Source ejbDescSource = this.getDescriptorSource(vjar, ejbDescUri);
      if (ejbDescSource == null) {
         return null;
      } else {
         EjbJarLoader ejbLoader = new EjbJarLoader(dm, ejbDescUri, ejbDescSource);
         EjbJarBean std = (EjbJarBean)ejbLoader.loadEditableDescriptorBean();
         EjbDescriptorBean ejbDescriptor = new EjbDescriptorBean(false);
         ejbDescriptor.setEjbJarBean(std);
         String wlDescUri = descRoot + "weblogic-ejb-jar.xml";
         Source wlDescSource = this.getDescriptorSource(vjar, wlDescUri);
         if (wlDescSource != null) {
            InputStream in = wlDescSource.getInputStream();
            if (!((InputStream)in).markSupported()) {
               in = new BufferedInputStream((InputStream)in, 16384);
            }

            WeblogicEjbJarBean wl = null;

            try {
               if (this.isDTDBased((InputStream)in)) {
                  EjbDescriptorBean e = new EjbDescriptorBean(false);
                  e.setEjbJarBean(std);
                  this.processDTDBasedWLDD((DeploymentPlanBean)null, (String)null, (InputStream)in, (String)null, e, wlDescUri);
                  wl = e.getWeblogicEjbJarBean();
               } else {
                  WeblogicEjbJarLoader wlLoader = new WeblogicEjbJarLoader(dm, wlDescUri, wlDescSource);
                  wl = (WeblogicEjbJarBean)wlLoader.loadEditableDescriptorBean();
               }
            } catch (XMLProcessingException | XMLParsingException var15) {
               throw new XMLStreamException(var15.getMessage(), var15);
            }

            ejbDescriptor.setWeblogicEjbJarBean(wl);
         }

         return ejbDescriptor;
      }
   }

   private void processDTDBasedWLDD(DeploymentPlanBean plan, String moduleName, InputStream xml, String sysId, EjbDescriptorBean desc, String documentURI) throws IOException, XMLParsingException, XMLProcessingException {
      if (plan != null) {
         ModuleOverrideBean mob = plan.findModuleOverride(moduleName);
         if (mob != null) {
            ModuleDescriptorBean md = plan.findModuleDescriptor(moduleName, documentURI);
            if (md != null && md.getVariableAssignments() != null && md.getVariableAssignments().length > 0) {
               Loggable l = EJBLogger.logNoPlanOverridesWithDTDDescriptorsLoggable(moduleName);
               throw new XMLProcessingException(l.getMessageText());
            }
         }
      }

      ProcessorFactory f = new ProcessorFactory();
      String encoding = DDUtils.getXMLEncoding((InputStream)xml, documentURI);
      if (!((InputStream)xml).markSupported()) {
         xml = new BufferedInputStream((InputStream)xml);
      }

      ((InputStream)xml).mark(1048576);
      DDLoader loader = null;

      try {
         loader = (DDLoader)f.getProcessor((InputStream)xml, VALID_WEBLOGIC_EJB_JAR_PUBLIC_IDS);
      } catch (ProcessorFactoryException var13) {
         throw new XMLProcessingException(var13, documentURI);
      }

      ((InputStream)xml).reset();
      loader.setEJBDescriptor(desc);
      loader.setEncoding(encoding);
      loader.setValidate(f.isValidating());

      try {
         InputSource is = new InputSource((InputStream)xml);
         is.setSystemId(sysId);
         loader.process(is);
      } catch (XMLParsingException var11) {
         var11.setFileName(documentURI);
         throw var11;
      } catch (XMLProcessingException var12) {
         var12.setFileName(documentURI);
         throw var12;
      }
   }

   private void completeWeblogicEjbJar(EjbDescriptorBean ed) {
      EnterpriseBeansBean enterpriseBeansBean = ed.getEjbJarBean().getEnterpriseBeans();
      WeblogicEjbJarBean wlEjbJarBean = ed.getWeblogicEjbJarBean();
      Map wlBeanMap = new HashMap();
      WeblogicEnterpriseBeanBean[] var5 = wlEjbJarBean.getWeblogicEnterpriseBeans();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         WeblogicEnterpriseBeanBean webb = var5[var7];
         wlBeanMap.put(webb.getEjbName(), webb);
      }

      SessionBeanBean[] var11 = enterpriseBeansBean.getSessions();
      var6 = var11.length;

      WeblogicEnterpriseBeanBean webb;
      for(var7 = 0; var7 < var6; ++var7) {
         SessionBeanBean sbb = var11[var7];
         webb = (WeblogicEnterpriseBeanBean)wlBeanMap.get(sbb.getEjbName());
         if (webb == null) {
            webb = wlEjbJarBean.createWeblogicEnterpriseBean();
            webb.setEjbName(sbb.getEjbName());
         }
      }

      EntityBeanBean[] var12 = enterpriseBeansBean.getEntities();
      var6 = var12.length;

      for(var7 = 0; var7 < var6; ++var7) {
         EntityBeanBean entityBeanBean = var12[var7];
         webb = (WeblogicEnterpriseBeanBean)wlBeanMap.get(entityBeanBean.getEjbName());
         if (webb == null) {
            webb = wlEjbJarBean.createWeblogicEnterpriseBean();
            webb.setEjbName(entityBeanBean.getEjbName());
         }
      }

      MessageDrivenBeanBean[] var13 = enterpriseBeansBean.getMessageDrivens();
      var6 = var13.length;

      for(var7 = 0; var7 < var6; ++var7) {
         MessageDrivenBeanBean mdbb = var13[var7];
         webb = (WeblogicEnterpriseBeanBean)wlBeanMap.get(mdbb.getEjbName());
         if (webb == null) {
            webb = wlEjbJarBean.createWeblogicEnterpriseBean();
            webb.setEjbName(mdbb.getEjbName());
         }

         ActivationConfigBean acb = mdbb.getActivationConfig();
         if (acb == null) {
            acb = mdbb.createActivationConfig();
         }
      }

   }

   private void mergeAutoRefLibMetadata(AbstractDescriptorLoader2 adl, VirtualJarFile[] libs) throws IOException, XMLStreamException {
      if (libs != null && libs.length > 0) {
         VirtualJarFile[] var3 = libs;
         int var4 = libs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            VirtualJarFile lib = var3[var5];
            this.validateAutoRefMetadata(lib);
         }

         adl.loadDescriptorBean();
         adl.mergeDescriptors(libs);
         adl.loadDescriptorBean();
      }

   }

   private void validateAutoRefMetadata(VirtualJarFile vjf) throws IOException {
      try {
         EjbDescriptorBean ejbDesc = EJBDescriptorBeanUtils.createDescriptorFromJarFile(vjf, true);
         EjbJarBean ejbJarBean = ejbDesc.getEjbJarBean();
         if (ejbJarBean.getEnterpriseBeans() != null) {
            throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should not configure enterprise-beans");
         } else {
            AssemblyDescriptorBean ad = ejbJarBean.getAssemblyDescriptor();
            if (ad == null) {
               throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should configure assembly-descriptor");
            } else if (ad.getApplicationExceptions() != null && ad.getApplicationExceptions().length > 0) {
               throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should not configure application-exception");
            } else if (ad.getMessageDestinations() != null && ad.getMessageDestinations().length > 0) {
               throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should not configure message-destination");
            } else if (ad.getMethodPermissions() != null && ad.getMethodPermissions().length > 0) {
               throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should not configure method-permission");
            } else if (ad.getSecurityRoles() != null && ad.getSecurityRoles().length > 0) {
               throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should not configure security-role");
            } else if (ad.getInterceptorBindings() != null && ad.getInterceptorBindings().length != 0) {
               if (ejbJarBean.getInterceptors() == null) {
                  throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should configure interceptors");
               } else if (ejbJarBean.getInterceptors().getInterceptors() == null || ejbJarBean.getInterceptors().getInterceptors().length == 0) {
                  throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should configure interceptor");
               }
            } else {
               throw new IllegalArgumentException("ejb-jar.xml in auto ref lib should configure interceptor-binding");
            }
         }
      } catch (Exception var5) {
         IOException ioe = new IOException("Nested exception is: ");
         ioe.initCause(var5);
         throw ioe;
      }
   }

   private void logDescriptor(String s, DescriptorBean bean) {
      OutputStream out = new ByteArrayOutputStream();

      try {
         bean.getDescriptor().toXML(out);
         DEBUG_LOGGER.debug("[EjbDescriptorReaderImpl] " + s + out.toString());
      } catch (IOException var5) {
      }

   }

   static {
      DEBUG_LOGGER = EJBDebugService.metadataLogger;
      VALID_WEBLOGIC_EJB_JAR_PUBLIC_IDS = new String[]{"-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB//EN", "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB//EN", "-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB//EN", "-//BEA Systems, Inc.//DTD WebLogic 8.1.0 EJB//EN"};
      ClassLoader origCL = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(EjbDescriptorReaderImpl.class.getClassLoader());
         XML_INPUT_FACTORY = XMLStreamInputFactory.newInstance();
      } finally {
         Thread.currentThread().setContextClassLoader(origCL);
      }

   }

   private static class NonClosingClassFinder extends DelegateFinder {
      public NonClosingClassFinder(ClassFinder finder) {
         super(finder);
      }

      public void close() {
      }
   }

   private static class NoCloseInputStream extends InputStream {
      private final InputStream is;

      NoCloseInputStream(InputStream is) {
         this.is = is;
      }

      public int read() throws IOException {
         return this.is.read();
      }

      public synchronized void mark(int size) {
         this.is.mark(size);
      }

      public boolean markSupported() {
         return this.is.markSupported();
      }

      public synchronized void reset() throws IOException {
         this.is.reset();
      }

      public void close() {
      }
   }
}
