package weblogic.persistence;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.PersistenceUnitParent;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.utils.PersistenceUtils;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.PersistenceBean;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.persistence.spi.BeanInfo;
import weblogic.persistence.spi.JPAIntegrationProvider;
import weblogic.persistence.spi.JPAIntegrationProviderFactory;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public abstract class AbstractPersistenceUnitRegistry implements PersistenceUnitRegistry {
   protected static final DebugLogger DEBUG;
   protected Map persistenceUnits;
   protected ApplicationContextInternal appCtx;
   private final Map descriptorMap;
   private File[] rootFiles;
   private File[] canonicalizedRootFiles;
   private final GenericClassLoader scopeClassLoader;
   private final String scopeName;
   private final File configDir;
   private final DeploymentPlanBean planBean;
   private final JPAIntegrationProvider defaultJPAIntegrationProvider;
   private final JPAIntegrationProvider kodoJPAIntegrationProvider;

   public AbstractPersistenceUnitRegistry(GenericClassLoader scopeClassLoader, String scopeName, File configDir, DeploymentPlanBean planBean) {
      this(scopeClassLoader, scopeName, configDir, planBean, (ApplicationContextInternal)null);
   }

   public AbstractPersistenceUnitRegistry(GenericClassLoader scopeClassLoader, String scopeName, File configDir, DeploymentPlanBean planBean, ApplicationContextInternal appCtx) {
      this.persistenceUnits = new HashMap();
      this.descriptorMap = new HashMap();
      this.rootFiles = null;
      this.canonicalizedRootFiles = null;
      this.appCtx = appCtx;
      this.scopeClassLoader = scopeClassLoader;
      this.scopeName = scopeName;
      this.configDir = configDir;
      this.planBean = planBean;
      this.defaultJPAIntegrationProvider = JPAIntegrationProviderFactory.getDefaultJPAIntegrationProvider();
      this.kodoJPAIntegrationProvider = JPAIntegrationProviderFactory.getKodoJPAIntegrationProvider();
   }

   public void setParentRuntimeMBean(PersistenceUnitParent bean, RuntimeMBean parentMBean) throws EnvironmentException {
      Iterator var3 = this.persistenceUnits.values().iterator();

      while(var3.hasNext()) {
         BasePersistenceUnitInfo pu = (BasePersistenceUnitInfo)var3.next();
         pu.setParentRuntimeMBean(bean, parentMBean);
      }

   }

   public String getQualifiedName(URL rootURL) {
      return this.getScopeName();
   }

   protected String getScopeName() {
      return this.scopeName;
   }

   public void loadPersistenceDescriptors(boolean process) throws EnvironmentException, IllegalAccessException, InstantiationException, ClassNotFoundException, NamingException, IOException, XMLStreamException {
      Map units = this.loadPersistenceDescriptor("META-INF/persistence.xml", process, this.getAppropriateProvider());
      Map configs = this.loadPersistenceDescriptor("META-INF/persistence-configuration.xml", process, this.getAppropriateProvider());
      if (process) {
         this.storeDescriptors(units, configs);
      }

   }

   private JPAIntegrationProvider getAppropriateProvider() {
      if (this.defaultJPAIntegrationProvider == this.kodoJPAIntegrationProvider) {
         return this.defaultJPAIntegrationProvider;
      } else if (this.descriptorMap.isEmpty()) {
         return this.defaultJPAIntegrationProvider;
      } else {
         Iterator var1 = this.descriptorMap.values().iterator();

         while(true) {
            DescriptorBean dBean;
            do {
               if (!var1.hasNext()) {
                  return this.defaultJPAIntegrationProvider;
               }

               Descriptor descriptor = (Descriptor)var1.next();
               dBean = descriptor.getRootBean();
            } while(!(dBean instanceof PersistenceBean));

            PersistenceBean pBean = (PersistenceBean)dBean;
            PersistenceUnitBean[] pus = pBean.getPersistenceUnits();
            PersistenceUnitBean[] var6 = pus;
            int var7 = pus.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               PersistenceUnitBean puBean = var6[var8];
               if (JPAIntegrationProviderFactory.isKodoProviderClass(puBean.getProvider())) {
                  return this.kodoJPAIntegrationProvider;
               }
            }
         }
      }
   }

   private File[] getRootFiles(boolean canonicalize) throws IOException {
      File[] splitDirSourceFiles;
      if (canonicalize) {
         if (this.canonicalizedRootFiles == null) {
            this.canonicalizedRootFiles = PersistenceUtils.getApplicationRoots(this.scopeClassLoader, this.scopeName, canonicalize);
            if (this.isSplitDirectoryDevelopment()) {
               splitDirSourceFiles = this.getSplitDirSourceRoots(this.scopeClassLoader, this.scopeName, canonicalize);
               if (splitDirSourceFiles != null) {
                  this.canonicalizedRootFiles = PersistenceUtils.concatRootFiles(this.canonicalizedRootFiles, splitDirSourceFiles);
               }
            }
         }

         return this.canonicalizedRootFiles;
      } else {
         if (this.rootFiles == null) {
            this.rootFiles = PersistenceUtils.getApplicationRoots(this.scopeClassLoader, this.scopeName, canonicalize);
            if (this.isSplitDirectoryDevelopment()) {
               splitDirSourceFiles = this.getSplitDirSourceRoots(this.scopeClassLoader, this.scopeName, canonicalize);
               if (splitDirSourceFiles != null) {
                  this.rootFiles = PersistenceUtils.concatRootFiles(this.rootFiles, splitDirSourceFiles);
               }
            }
         }

         return this.rootFiles;
      }
   }

   private Map loadPersistenceDescriptor(String descriptorURI, boolean process, JPAIntegrationProvider provider) throws EnvironmentException {
      Map parsed = null;

      Enumeration resourceURLs;
      try {
         resourceURLs = this.scopeClassLoader.getResources(descriptorURI);
      } catch (IOException var15) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("scope:" + this.scopeName + " unable to find " + descriptorURI);
         }

         return null;
      }

      Set processedURIs = new HashSet();

      while(resourceURLs.hasMoreElements()) {
         URL resourceURL = (URL)resourceURLs.nextElement();
         URI relativeURI = null;
         URI resourceURI = null;

         try {
            resourceURI = PersistenceDescriptorLoader.getResourceURI(resourceURL);
            if (processedURIs.contains(resourceURI)) {
               continue;
            }

            processedURIs.add(resourceURI);
            File[] roots = this.getRootFiles(true);
            relativeURI = PersistenceDescriptorLoader.getRelativeURI(roots, resourceURI);
         } catch (IOException var18) {
            throw new EnvironmentException("Error scanning module " + this.getScopeName() + " for persistence descriptors: " + StackTraceUtils.throwable2StackTrace(var18));
         }

         if (!relativeURI.equals(resourceURI)) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("loading persistence descriptor at: " + resourceURL + " " + relativeURI);
            }

            PersistenceDescriptorLoader loader = provider.getDescriptorLoader((VirtualJarFile)null, resourceURL, this.configDir, this.planBean, this.scopeName, relativeURI.toString());
            DescriptorBean rootBean = null;

            try {
               if (loader != null) {
                  rootBean = loader.loadDescriptorBean();
               }
            } catch (Exception var14) {
               throw new EnvironmentException("Error loading the persistence descriptor " + relativeURI + " from the module " + this.getScopeName() + ".  See the following stack trace for nested errors: " + StackTraceUtils.throwable2StackTrace(var14));
            }

            if (rootBean != null) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug(DescriptorUtils.toString(rootBean));
               }

               this.descriptorMap.put(relativeURI.toString(), rootBean.getDescriptor());
               if (process) {
                  try {
                     URL rootUrl = this.toRootURL(resourceURL);
                     URL jarParentUrl = this.toJarParentURL(rootUrl);
                     parsed = this.processDescriptor(rootBean, rootUrl, jarParentUrl, parsed);
                  } catch (MalformedURLException var16) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("Unable to process resourceURL " + resourceURL, var16);
                     }
                  } catch (URISyntaxException var17) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("Unable to process resourceURL " + resourceURL, var17);
                     }
                  }
               }
            }
         } else if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Unable to find relative root for " + resourceURI);
         }
      }

      return parsed;
   }

   public void loadPersistenceDescriptor(VirtualJarFile vjf, boolean process, File rootFile) throws EnvironmentException {
      DescriptorBean persistenceBean = this.loadPersistenceDescriptor(vjf, "META-INF/persistence.xml");
      DescriptorBean configBean = null;
      if (persistenceBean != null) {
         configBean = this.loadPersistenceDescriptor(vjf, "META-INF/persistence-configuration.xml");
      }

      if (process) {
         URL rootUrl = null;
         URL jarParentUrl = null;

         try {
            rootUrl = rootFile.toURL();
            jarParentUrl = rootFile.getAbsoluteFile().getParentFile().toURL();
         } catch (MalformedURLException var10) {
            throw new EnvironmentException("Error computing URL: " + var10);
         }

         Map units = null;
         Map configs = null;
         if (persistenceBean != null) {
            units = this.processDescriptor(persistenceBean, rootUrl, jarParentUrl, (Map)null);
         }

         if (configBean != null) {
            configs = this.processDescriptor(configBean, rootUrl, jarParentUrl, (Map)null);
         }

         this.storeDescriptors(units, configs);
      }

   }

   private DescriptorBean loadPersistenceDescriptor(VirtualJarFile vjf, String uri) throws EnvironmentException {
      try {
         PersistenceDescriptorLoader perloader = this.getAppropriateProvider().getDescriptorLoader(vjf, (URL)null, this.configDir, this.planBean, this.scopeName, uri);
         if (perloader == null) {
            return null;
         } else {
            DescriptorBean persistenceBean = perloader.loadDescriptorBean();
            if (persistenceBean != null) {
               this.descriptorMap.put(uri, persistenceBean.getDescriptor());
            }

            return persistenceBean;
         }
      } catch (Exception var5) {
         throw new EnvironmentException("Error loading the persistence descriptor " + uri + " from the module " + this.getScopeName() + ".  See the following stack trace for nested errors: " + StackTraceUtils.throwable2StackTrace(var5));
      }
   }

   private Map processDescriptor(DescriptorBean rootBean, URL rootUrl, URL jarParentUrl, Map parsed) throws EnvironmentException {
      if (parsed == null) {
         parsed = new HashMap();
      }

      if (rootBean instanceof PersistenceBean) {
         PersistenceBean units = (PersistenceBean)rootBean;
         PersistenceUnitBean[] puUnits = units.getPersistenceUnits();
         PersistenceUnitBean[] var9 = puUnits;
         int var10 = puUnits.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            PersistenceUnitBean puUnit = var9[var11];
            String name = puUnit.getName();
            this.assertNoDuplicate((Map)parsed, name, rootUrl);
            BeanInfo info = new BeanInfo(puUnit, rootUrl, jarParentUrl, rootBean);
            ((Map)parsed).put(name, info);
         }
      } else {
         Map beanInfos = this.getAppropriateProvider().getPersistenceUnitConfigsAsBeanInfo(rootBean, rootUrl, jarParentUrl);
         Iterator var14 = beanInfos.keySet().iterator();

         while(var14.hasNext()) {
            String biName = (String)var14.next();
            this.assertNoDuplicate((Map)parsed, biName, rootUrl);
         }

         ((Map)parsed).putAll(beanInfos);
      }

      return (Map)parsed;
   }

   private void assertNoDuplicate(Map map, String name, URL rootUrl) throws EnvironmentException {
      BeanInfo info = (BeanInfo)map.get(name);
      if (info != null) {
         throw new EnvironmentException("duplicate persistence units with name " + name + " in scope " + this.scopeName + ". First PU location: " + info.rootUrl + ". Second PU location: " + rootUrl);
      }
   }

   protected void storeDescriptors(Map units, Map configs) throws EnvironmentException {
      if (units != null) {
         Iterator it = units.values().iterator();

         while(it.hasNext()) {
            BeanInfo info = (BeanInfo)it.next();
            PersistenceUnitBean unit = (PersistenceUnitBean)info.bean;
            String originalVersion = ((PersistenceBean)info.rootBean).getOriginalVersion();
            BeanInfo cfgInfo = configs == null ? null : (BeanInfo)configs.get(unit.getName());
            Object unitConfig = cfgInfo == null ? null : cfgInfo.bean;

            try {
               BasePersistenceUnitInfo bpui = JPAIntegrationProviderFactory.getProvider(unit).createPersistenceUnitInfo(unit, unitConfig, this.scopeClassLoader, this.getQualifiedName(info.rootUrl), info.rootUrl, info.jarParentUrl, originalVersion, this.appCtx);
               this.putPersistenceUnit(bpui);
            } catch (EnvironmentException var11) {
               EnvironmentException e = new EnvironmentException("Error processing persistence unit " + unit.getName() + " of module " + this.scopeName + ": " + var11.getMessage());
               e.setStackTrace(var11.getStackTrace());
               throw e;
            }
         }

      }
   }

   protected void putPersistenceUnit(BasePersistenceUnitInfo unit) throws EnvironmentException {
      String puName = unit.getPersistenceUnitName();
      BasePersistenceUnitInfo other = (BasePersistenceUnitInfo)this.persistenceUnits.put(puName, unit);
      if (other != null) {
         throw new EnvironmentException("duplicate persistence units with name " + puName + " in scope " + this.scopeName + ". First PU location: " + other.getPersistenceUnitRootUrl() + ". Second PU location: " + unit.getPersistenceUnitRootUrl());
      }
   }

   public void close() {
      Iterator var1 = this.persistenceUnits.values().iterator();

      while(var1.hasNext()) {
         BasePersistenceUnitInfo pui = (BasePersistenceUnitInfo)var1.next();

         try {
            pui.close();
         } catch (PersistenceException var4) {
            var4.printStackTrace();
         }
      }

   }

   public Collection getPersistenceUnitNames() {
      Set names = new HashSet();
      names.addAll(this.persistenceUnits.keySet());
      return names;
   }

   public Iterator getDescriptorURIs() {
      return this.descriptorMap.keySet().iterator();
   }

   public Descriptor getDescriptor(String uri) {
      return (Descriptor)this.descriptorMap.get(uri);
   }

   protected URL toRootURL(URL url) throws MalformedURLException, URISyntaxException {
      if ("file".equals(url.getProtocol())) {
         return new URL(url, "..");
      } else if (!"jar".equals(url.getProtocol()) && !"zip".equals(url.getProtocol())) {
         throw new IllegalArgumentException("Unsupported URL format: " + url);
      } else {
         String urlName = url.toString();
         if ("zip".equals(url.getProtocol())) {
            if (urlName.startsWith("zip:/")) {
               urlName = urlName.replaceFirst("^zip:", "file:");
            } else {
               urlName = urlName.replaceFirst("^zip:", "file:/");
            }
         } else {
            urlName = urlName.replaceFirst("^jar:", "");
         }

         urlName = urlName.substring(0, urlName.indexOf("!/"));
         URL root = new URL(urlName);
         return root;
      }
   }

   private URL toJarParentURL(URL rootUrl) throws MalformedURLException {
      return new URL(rootUrl, ".");
   }

   private boolean isSplitDirectoryDevelopment() {
      return this.appCtx != null && this.appCtx.getSplitDirectoryInfo() != null;
   }

   protected File[] getSplitDirSourceRoots(GenericClassLoader loader, String applicationName, boolean canonicalize) throws IOException {
      return null;
   }

   static {
      DEBUG = JPAIntegrationProviderFactory.DEBUG;
   }
}
