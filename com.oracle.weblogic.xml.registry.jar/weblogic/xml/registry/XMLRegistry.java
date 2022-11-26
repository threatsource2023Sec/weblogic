package weblogic.xml.registry;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFactoryManager;
import weblogic.j2ee.descriptor.wl.ParserFactoryBean;
import weblogic.j2ee.descriptor.wl.XmlBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.XMLEntityCacheMBean;
import weblogic.management.configuration.XMLEntitySpecRegistryEntryMBean;
import weblogic.management.configuration.XMLParserSelectRegistryEntryMBean;
import weblogic.management.configuration.XMLRegistryMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.XMLLogger;
import weblogic.xml.jaxp.XMLContext;
import weblogic.xml.util.Tools;
import weblogic.xml.util.XMLConstants;
import weblogic.xml.util.cache.entitycache.EntityCacheCumulativeStats;
import weblogic.xml.util.cache.entitycache.EntityCacheCurrentStats;
import weblogic.xml.util.cache.entitycache.Event;

public class XMLRegistry implements XMLConstants {
   private static final String defaultAppName = "BEAWeblogicDefaultApplicationContext";
   private static final double MemCheckFactor = 0.5;
   private static Hashtable registryExtent = new Hashtable();
   private static final int defaultCacheTimeoutInterval = 120;
   EntityCacheCumulativeStats sessionCacheStatsMBean = null;
   EntityCacheCumulativeStats historicalCacheStatsMBean = null;
   EntityCacheCurrentStats currentCacheStatsMBean = null;
   private static XMLRegistry defaultRegistry = null;
   private static ServerListener serverListener = null;
   private RegistryListener registryListener = null;
   private final String REGISTRY_DIR = "lib/xml/registry";
   private boolean isAppScopedRegistry = false;
   private boolean cleanupTempEntities = false;
   private AppDeploymentMBean deployableMBean = null;
   private final ApplicationAccess applicationAccess = ApplicationAccess.getApplicationAccess();
   private CacheListener cacheListener = null;
   private HashSet newEntitySpecMBeans = new HashSet();
   private HashSet newParserSelectMBeans = new HashSet();
   private static XMLRegistryMBean xmlDefaultRegistryAdminMBean = null;
   private XMLRegistryMBean xmlRegistryConfigMBean = null;
   private ConfigAbstraction.RegistryConfig config = null;
   private String privateRegistryDir;
   private String basePath = null;
   private String registryName;
   private String applicationName = null;
   private XMLRegistryDir publicRegistryDir;
   private Map psIndex = new HashMap();
   private Map esIndex = new HashMap();
   private boolean hasCustomParserEntries = false;
   private boolean hasDocumentSpecificParserEntries = false;
   private boolean hasDocumentSpecificEntityEntries = false;
   private static boolean isInitialized = false;
   private static weblogic.xml.util.cache.entitycache.EntityCache underlyingCache = null;
   private EntityCache entityCache = null;
   private static RefreshCacheLock lock = new RefreshCacheLock(10);
   private int cacheDefaultTimeoutInterval = -1;
   static XMLEntityCacheMBean cacheConfigMBean = null;
   static ServerMBean serverConfigMBean = null;
   private static final Lock lockForGetPath = new ReentrantLock();

   static void init() throws XMLRegistryException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addWLAppModuleFactory(new XMLModuleFactory());
      XMLContext.init();

      try {
         initializeDefaultRegistry();
      } catch (XMLRegistryException var2) {
         throw var2;
      } catch (Exception var3) {
      }

      isInitialized = true;
   }

   private XMLRegistry(XMLRegistryMBean registryConfigMBean, String applicationName) throws XMLRegistryException {
      this.xmlRegistryConfigMBean = registryConfigMBean;
      this.privateRegistryDir = "lib/xml/registry";
      this.applicationName = applicationName;
      this.loadPrivateRegistry();
      this.installRegistryInstance(applicationName);
   }

   private XMLRegistry(XmlBean xmlDD, String appName, String basePath) throws XMLRegistryException {
      this.basePath = basePath;

      try {
         this.applicationName = appName;
         this.config = ConfigAbstraction.getRegistryConfig(xmlDD, this.applicationName);
         this.registryName = "_._" + this.config.getName();
         this.setUpEntityCache();
      } catch (Exception var5) {
         throw new XMLRegistryException(var5);
      }

      this.installRegistryInstance(appName);
   }

   static ServerMBean getServerConfigMBean() {
      if (serverConfigMBean == null) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         serverConfigMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      }

      return serverConfigMBean;
   }

   private XMLRegistryMBean getRegistryConfigMBean() {
      if (this.xmlRegistryConfigMBean == null) {
         this.xmlRegistryConfigMBean = getServerConfigMBean().getXMLRegistry();
      }

      return this.xmlRegistryConfigMBean;
   }

   private static XMLRegistryMBean getRegistryConfigMBeanStatic() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      return ManagementService.getRuntimeAccess(kernelId).getServer().getXMLRegistry();
   }

   XMLRegistryMBean getXMLRegistryMBean(String name) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess config = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = config.getDomain();
      ServerMBean[] server = domain.getServers();
      if (server == null) {
         return null;
      } else {
         for(int i = 0; i < server.length; ++i) {
            XMLRegistryMBean registry = server[i].getXMLRegistry();
            if (registry != null && registry.getName().equals(name)) {
               return registry;
            }
         }

         return null;
      }
   }

   static XMLEntityCacheMBean getCacheConfigMBean() {
      if (cacheConfigMBean == null) {
         try {
            cacheConfigMBean = getServerConfigMBean().getXMLEntityCache();
         } catch (Exception var1) {
         }
      }

      return cacheConfigMBean;
   }

   private static void initializeDefaultRegistry() throws XMLRegistryException {
      Class var0 = XMLRegistry.class;
      synchronized(XMLRegistry.class) {
         XMLRegistryMBean regBean = getRegistryConfigMBeanStatic();
         defaultRegistry = new XMLRegistry(regBean, "BEAWeblogicDefaultApplicationContext");
         defaultRegistry.loadFromMBean(getRegistryConfigMBeanStatic());
         if (serverListener == null) {
            serverListener = new ServerListener();
            serverConfigMBean.addPropertyChangeListener(serverListener);
         }

      }
   }

   private AppDeploymentMBean getAppDeploymentMBean() {
      return this.deployableMBean;
   }

   static void initializeAppScopedXMLRegistry(XmlBean xmlDD, AppDeploymentMBean deployableMBean, String basePath) throws XMLRegistryException {
      String appName = deployableMBean.getName();
      XMLRegistry appScopedXMLRegistry = new XMLRegistry(xmlDD, appName, basePath);
      appScopedXMLRegistry.isAppScopedRegistry = true;
      appScopedXMLRegistry.cleanupTempEntities = true;
      appScopedXMLRegistry.deployableMBean = deployableMBean;
      appScopedXMLRegistry.loadPublicEntries();
      ParserFactoryBean parserFactoryBean = xmlDD.getParserFactory();
      appScopedXMLRegistry.basePath = basePath;
   }

   static void cleanUpAppScopedXMLRegistry(String appName) throws XMLRegistryException {
      XMLRegistry xmlRegistry = getXMLRegistry(appName);
      xmlRegistry.cleanupTempEntities = true;

      XMLEntitySpecRegistryEntry ent;
      for(Iterator ei = xmlRegistry.esIndex.values().iterator(); ei.hasNext(); ent.setListener((PropertyChangeListener)null)) {
         ent = (XMLEntitySpecRegistryEntry)ei.next();
         if (xmlRegistry.getCache() != null) {
            xmlRegistry.getCache().remove(ent.getPublicId(), ent.getSystemId());
         }

         PropertyChangeListener l = ent.getListener();
         if (l != null) {
            ConfigAbstraction.EntryConfig bean = ent.getMBean();
            if (bean != null) {
               bean.removePropertyChangeListener(l);
            }
         }
      }

      registryExtent.remove(appName);
      xmlRegistry.config = null;
   }

   void cleanUpCache(String[] changedFiles) throws XMLRegistryException {
      for(int i = 0; i < changedFiles.length; ++i) {
         if (changedFiles[i].startsWith("lib/xml/registry")) {
            if (changedFiles[i].equals("lib/xml/registry")) {
               this.cleanUpCache();
               break;
            }

            Iterator ei = this.esIndex.values().iterator();

            while(ei.hasNext()) {
               XMLEntitySpecRegistryEntry ent = (XMLEntitySpecRegistryEntry)ei.next();
               if (ent.getEntityURI() != null) {
                  String entityURI = changedFiles[i].substring("lib/xml/registry".length() + 1);
                  if (entityURI.equals(ent.getEntityURI())) {
                     this.getCache().remove(ent.getPublicId(), ent.getSystemId());
                  }
               }
            }
         }
      }

   }

   private void cleanUpCache() throws XMLRegistryException {
      Iterator ei = this.esIndex.values().iterator();

      while(ei.hasNext()) {
         XMLEntitySpecRegistryEntry ent = (XMLEntitySpecRegistryEntry)ei.next();
         this.getCache().remove(ent.getPublicId(), ent.getSystemId());
      }

   }

   private static void reinitializeDefaultRegistry() throws XMLRegistryException {
      Class var0 = XMLRegistry.class;
      synchronized(XMLRegistry.class) {
         defaultRegistry.cleanupRegistry();
         initializeDefaultRegistry();
      }

      defaultRegistry.config.removePropertyChangeListener(defaultRegistry.registryListener);
      defaultRegistry.registryListener = null;
   }

   private void cleanupRegistry() {
      defaultRegistry = null;
      xmlDefaultRegistryAdminMBean = null;
      if (this.config != null) {
         this.config.removePropertyChangeListener(this.registryListener);
      }

      this.registryListener = null;

      XMLParserSelectRegistryEntry ent;
      for(Iterator pi = this.psIndex.values().iterator(); pi.hasNext(); ent.setListener((PropertyChangeListener)null)) {
         ent = (XMLParserSelectRegistryEntry)pi.next();
         PropertyChangeListener l = ent.getListener();
         if (l != null) {
            ConfigAbstraction.EntryConfig bean = ent.getMBean();
            if (bean != null) {
               bean.removePropertyChangeListener(l);
            }
         }
      }

      XMLEntitySpecRegistryEntry ent;
      for(Iterator ei = this.esIndex.values().iterator(); ei.hasNext(); ent.setListener((PropertyChangeListener)null)) {
         ent = (XMLEntitySpecRegistryEntry)ei.next();
         PropertyChangeListener l = ent.getListener();
         if (l != null) {
            ConfigAbstraction.EntryConfig bean = ent.getMBean();
            if (bean != null) {
               bean.removePropertyChangeListener(l);
            }
         }
      }

   }

   public String getName() {
      return this.registryName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public RefreshCacheLock getLock() {
      return lock;
   }

   public EntityCache getCache() throws XMLRegistryException {
      return this.entityCache;
   }

   private String getCacheBeanName() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      return "XMLCacheMBean_" + ManagementService.getRuntimeAccess(kernelId).getServerName();
   }

   XMLEntityCacheMBean getEntityCacheMBean() {
      if (cacheConfigMBean == null) {
         cacheConfigMBean = getServerConfigMBean().getXMLEntityCache();
      }

      return cacheConfigMBean;
   }

   protected weblogic.xml.util.cache.entitycache.EntityCache getUnderlyingCache() throws XMLRegistryException {
      if (underlyingCache == null) {
         try {
            XMLEntityCacheMBean cacheMBean = this.getEntityCacheMBean();
            if (cacheMBean == null) {
               return null;
            }

            String cachePath = cacheMBean.getCacheLocation();
            String cacheName = "XML-Entity-Cache";
            int memSize = cacheMBean.getCacheMemorySize();
            int diskSize = cacheMBean.getCacheDiskSize();
            weblogic.xml.util.cache.entitycache.EntityCache.CacheSpec cacheSpec = new weblogic.xml.util.cache.entitycache.EntityCache.CacheSpec();
            cacheSpec.name = cacheName;
            cacheSpec.path = cachePath;
            cacheSpec.memSize = (long)(memSize * 1000);
            cacheSpec.diskSize = (long)(diskSize * 1000000);
            cacheSpec.cacheListener = new CacheLogListener();
            underlyingCache = weblogic.xml.util.cache.entitycache.EntityCache.getCache(cacheSpec, cacheConfigMBean);
            if (underlyingCache != null) {
               Runtime rt = Runtime.getRuntime();

               try {
                  long freeMemory = rt.freeMemory();
                  if ((double)memSize > (double)freeMemory * 0.5) {
                     if ((long)memSize > freeMemory) {
                        XMLLogger.logCacheMemoryWarningExceeds((long)memSize, freeMemory);
                     } else {
                        XMLLogger.logCacheMemoryWarningClose((long)memSize, freeMemory);
                     }
                  }
               } catch (Exception var10) {
               }
            }
         } catch (Exception var11) {
            throw new XMLRegistryException(var11);
         }
      }

      return underlyingCache;
   }

   private void loadFromMBean(XMLRegistryMBean configMbean) throws XMLRegistryException {
      if (configMbean != null) {
         this.config = ConfigAbstraction.getRegistryConfig(configMbean);
         this.registryName = configMbean.getName();
         if (this.registryName == null || this.registryName.length() == 0) {
            String msg = "Registry does not have a name";
            throw new XMLRegistryException(msg);
         }

         this.publicRegistryDir = new XMLRegistryDir(this.registryName);
         this.setUpEntityCache();
         this.loadPublicRegistry();
         this.registryListener = new RegistryListener(this);
         this.config.addPropertyChangeListener(this.registryListener);
      }

   }

   private void installRegistryInstance(String applicationName) {
      registryExtent.put(applicationName, this);
   }

   public static XMLRegistry getXMLRegistry(String applicationName) {
      return (XMLRegistry)registryExtent.get(applicationName);
   }

   private void setUpEntityCache() throws XMLRegistryException {
      weblogic.xml.util.cache.entitycache.EntityCache underlyingCache = this.getUnderlyingCache();
      if (underlyingCache != null) {
         this.entityCache = new EntityCache(this, underlyingCache);
         XMLEntityCacheMBean cacheMBean = this.getEntityCacheMBean();
         if (cacheMBean != null) {
            this.cacheListener = new CacheListener(this);
            cacheMBean.addPropertyChangeListener(this.cacheListener);
         }
      }

   }

   public static XMLRegistry[] getXMLRegistryPath() throws XMLRegistryException {
      XMLRegistry[] regs = null;
      String appName = null;
      if (isInitialized) {
         appName = ApplicationAccess.getApplicationAccess().getCurrentApplicationName();
      }

      if (appName == null) {
         appName = "BEAWeblogicDefaultApplicationContext";
      }

      XMLRegistry reg = null;
      reg = (XMLRegistry)registryExtent.get(appName);
      lockForGetPath.lock();

      XMLRegistry[] var3;
      try {
         if (reg != null || defaultRegistry != null) {
            if (reg != null && reg != defaultRegistry) {
               regs = new XMLRegistry[]{reg, defaultRegistry};
               return regs;
            }

            regs = new XMLRegistry[]{defaultRegistry};
            return regs;
         }

         var3 = new XMLRegistry[0];
      } finally {
         lockForGetPath.unlock();
      }

      return var3;
   }

   void setCacheDefaultTimeoutInterval(int newValue) {
      this.cacheDefaultTimeoutInterval = newValue;
   }

   public int getCacheTimeoutInterval(String publicID, String systemID) throws XMLRegistryException {
      XMLEntitySpecRegistryEntry ent = this.lookupEntitySpecEntry(publicID, systemID);
      return this.getCacheTimeoutInterval(ent);
   }

   int getCacheTimeoutInterval(XMLEntitySpecRegistryEntry ent) throws XMLRegistryException {
      int timeoutInterval = ent.getCacheTimeoutInterval();
      if (timeoutInterval < 0) {
         if (this.cacheDefaultTimeoutInterval == -1) {
            XMLEntityCacheMBean cacheMBean = this.getEntityCacheMBean();
            if (cacheMBean != null) {
               try {
                  this.cacheDefaultTimeoutInterval = cacheMBean.getCacheTimeoutInterval();
               } catch (ClassCastException var5) {
               }
            }

            if (this.cacheDefaultTimeoutInterval == -1) {
               this.cacheDefaultTimeoutInterval = 120;
            }
         }

         timeoutInterval = this.cacheDefaultTimeoutInterval;
      }

      return timeoutInterval;
   }

   public ResolvedEntity getEntity(String publicId, String systemId) throws XMLRegistryException {
      ResolvedEntity resolvedEntity = new ResolvedEntity();
      Tools.getEntityDescriptor(publicId, systemId, (String)null);
      XMLEntitySpecRegistryEntry entry = this.lookupEntitySpecEntry(publicId, systemId);
      InputSource ins = null;
      if (entry != null) {
         String uri = entry.getEntityURI();
         if (uri != null) {
            InputStream strm = this.retrieveEntity(uri);
            if (strm != null) {
               ins = new InputSource(strm);
               ins.setSystemId(systemId);
               ins.setPublicId(publicId);
               resolvedEntity.inputSource = ins;
               resolvedEntity.entry = entry;
               String whenToCache = entry.getWhenToCache();
               String appName = this.applicationAccess.getCurrentApplicationName();
               if (whenToCache == null || whenToCache.equals("defer-to-registry-setting")) {
                  whenToCache = this.config.getWhenToCache();
               }

               if (whenToCache != null && whenToCache.equals("cache-never")) {
                  resolvedEntity.isSubjectToCaching = false;
               }

               resolvedEntity.isLocal = strm instanceof FileInputStream || this.publicRegistryDir != null && this.publicRegistryDir.isLocal();
            }
         }
      }

      return resolvedEntity;
   }

   public Parser getParser(String publicId, String systemId, String root) throws XMLRegistryException {
      XMLParserSelectRegistryEntry entry = this.lookupParserSelectEntry(publicId, systemId, root);
      Parser parser = null;
      if (entry != null) {
         String parserClassName = entry.getParserClassName();
         if (parserClassName != null) {
            try {
               Object parserOrReader = Class.forName(parserClassName).newInstance();
               if (parserOrReader instanceof XMLReader) {
                  XMLReader reader = (XMLReader)parserOrReader;
                  parser = new XMLReaderAdapter(reader);
               } else {
                  parser = (Parser)parserOrReader;
               }
            } catch (ClassNotFoundException var9) {
               throw new XMLRegistryException("ClassNotFoundException. Class " + var9.getMessage() + " cannot be located.", var9);
            } catch (ClassCastException var10) {
               throw new XMLRegistryException("ClassCastException. Class " + var10.getMessage() + " is not of type org.xml.sax.Parser.", var10);
            } catch (IllegalAccessException var11) {
               throw new XMLRegistryException("IllegalAccessException. Class " + var11.getMessage() + " is not accessible.", var11);
            } catch (InstantiationException var12) {
               throw new XMLRegistryException("InstantiationException. " + var12.getMessage() + " Class instantiation fails. This Class represents an abstract class, an interface, an array class, a primitive type, or void, or instantiation fails for some other reason", var12);
            } catch (SecurityException var13) {
               throw new XMLRegistryException("SecurityException. There is no permission to create a new instance " + var13.getMessage(), var13);
            } catch (Exception var14) {
               throw new XMLRegistryException(var14);
            }
         }
      }

      return (Parser)parser;
   }

   public SAXParserFactory getSAXParserFactory(String publicId, String systemId, String root) throws XMLRegistryException {
      XMLParserSelectRegistryEntry entry = this.lookupParserSelectEntry(publicId, systemId, root);
      SAXParserFactory factory = null;
      if (entry != null) {
         String factoryClassName = entry.getSAXParserFactory();
         if (factoryClassName != null) {
            String appfactoryName = null;
            String appName = this.applicationAccess.getCurrentApplicationName();
            if (appName != null && getXMLRegistry(appName) != null) {
               appfactoryName = getXMLRegistry(appName).config.getSAXParserFactory();
            }

            factory = (SAXParserFactory)this.getFactory(factoryClassName, "javax.xml.parsers.SAXParserFactory", appfactoryName, false);
         }
      }

      return factory;
   }

   public SAXParserFactory getSAXParserFactory() throws XMLRegistryException {
      SAXParserFactory factory = null;
      if (this.havePublicData()) {
         factory = (SAXParserFactory)this.getFactory(this.config.getSAXParserFactory(), "javax.xml.parsers.SAXParserFactory", (String)null, true);
      }

      return factory;
   }

   public TransformerFactory getTransformerFactory(String publicId, String systemId, String root) throws XMLRegistryException {
      XMLParserSelectRegistryEntry entry = this.lookupParserSelectEntry(publicId, systemId, root);
      TransformerFactory factory = null;
      if (entry != null) {
         String factoryClassName = entry.getTransformerFactory();
         if (factoryClassName != null) {
            String appfactoryName = null;
            String appName = this.applicationAccess.getCurrentApplicationName();
            if (appName != null && getXMLRegistry(appName) != null) {
               appfactoryName = getXMLRegistry(appName).config.getTransformerFactory();
            }

            factory = (TransformerFactory)this.getFactory(factoryClassName, "javax.xml.transform.TransformerFactory", appfactoryName, false);
         }
      }

      return factory;
   }

   public TransformerFactory getTransformerFactory() throws XMLRegistryException {
      TransformerFactory factory = null;
      if (this.havePublicData()) {
         String factoryClassName = this.config.getTransformerFactory();
         factory = (TransformerFactory)this.getFactory(this.config.getTransformerFactory(), "javax.xml.transform.TransformerFactory", (String)null, true);
      }

      return factory;
   }

   public DocumentBuilderFactory getDocumentBuilderFactory(String publicId, String systemId, String root) throws XMLRegistryException {
      XMLParserSelectRegistryEntry entry = this.lookupParserSelectEntry(publicId, systemId, root);
      DocumentBuilderFactory factory = null;
      if (entry != null) {
         String factoryClassName = entry.getDocumentBuilderFactory();
         if (factoryClassName != null) {
            String appfactoryName = null;
            String appName = this.applicationAccess.getCurrentApplicationName();
            if (appName != null && getXMLRegistry(appName) != null) {
               appfactoryName = getXMLRegistry(appName).config.getDocumentBuilderFactory();
            }

            factory = (DocumentBuilderFactory)this.getFactory(factoryClassName, "javax.xml.parsers.DocumentBuilderFactory", appfactoryName, false);
         }
      }

      return factory;
   }

   public DocumentBuilderFactory getDocumentBuilderFactory() throws XMLRegistryException {
      DocumentBuilderFactory factory = null;
      if (this.havePublicData()) {
         factory = (DocumentBuilderFactory)this.getFactory(this.config.getDocumentBuilderFactory(), "javax.xml.parsers.DocumentBuilderFactory", (String)null, true);
      }

      return factory;
   }

   public XPathFactory getXPathFactory() throws XMLRegistryException {
      XPathFactory factory = null;
      if (this.havePublicData()) {
         factory = (XPathFactory)this.getFactory(this.config.getXPathFactory(), "javax.xml.xpath.XPathFactor", (String)null, true);
      }

      return factory;
   }

   public SchemaFactory getSchemaFactory() throws XMLRegistryException {
      SchemaFactory factory = null;
      if (this.havePublicData()) {
         factory = (SchemaFactory)this.getFactory(this.config.getSchemaFactory(), "javax.xml.validation.SchemaFactory", (String)null, true);
      }

      return factory;
   }

   public XMLInputFactory getXMLInputFactory() throws XMLRegistryException {
      XMLInputFactory factory = null;
      if (this.havePublicData()) {
         factory = (XMLInputFactory)this.getFactory(this.config.getXMLInputFactory(), "javax.xml.stream.XMLInputFactory", (String)null, true);
      }

      return factory;
   }

   public XMLOutputFactory getXMLOutputFactory() throws XMLRegistryException {
      XMLOutputFactory factory = null;
      if (this.havePublicData()) {
         factory = (XMLOutputFactory)this.getFactory(this.config.getXMLOutputFactory(), "javax.xml.stream.XMLOutputFactory", (String)null, true);
      }

      return factory;
   }

   public XMLEventFactory getXMLEventFactory() throws XMLRegistryException {
      XMLEventFactory factory = null;
      if (this.havePublicData()) {
         factory = (XMLEventFactory)this.getFactory(this.config.getXMLEventFactory(), "javax.xml.stream.XMLEventFactory", (String)null, true);
      }

      return factory;
   }

   public boolean hasDocumentSpecificParserEntries() {
      return this.hasDocumentSpecificParserEntries;
   }

   public boolean hasDocumentSpecificEntityEntries() {
      return this.hasDocumentSpecificEntityEntries;
   }

   public boolean hasCustomParserEntries() {
      return this.hasCustomParserEntries;
   }

   public boolean hasHandleEntityInvalidationSetSupport() {
      return !this.isAppScopedRegistry;
   }

   public String getHandleEntityInvalidation(String publicId, String systemId) throws XMLRegistryException {
      if (!this.hasHandleEntityInvalidationSetSupport()) {
         return null;
      } else {
         String invalidation = null;
         XMLEntitySpecRegistryEntry specEntry = this.lookupEntitySpecEntry(publicId, systemId);
         if (specEntry != null) {
            invalidation = specEntry.getHandleEntityInvalidation();
            if (invalidation != null && "defer-to-registry-setting".equals(invalidation)) {
               invalidation = this.config.getHandleEntityInvalidation();
            }
         }

         return invalidation;
      }
   }

   public String getHandleEntityInvalidation() throws XMLRegistryException {
      if (!this.hasHandleEntityInvalidationSetSupport()) {
         return null;
      } else {
         return this.havePublicData() ? this.config.getHandleEntityInvalidation() : null;
      }
   }

   String getExtendedLibraryPath(String entityURI) {
      return this.privateRegistryDir + File.separatorChar + entityURI;
   }

   String getApplicationExtendedLibraryPath(String entityURI) {
      return this.privateRegistryDir + "/" + entityURI;
   }

   private boolean isURL(String entityURI) {
      String candidate = entityURI.trim().toLowerCase(Locale.ENGLISH);
      return candidate.startsWith("http://") || candidate.startsWith("file://") || candidate.startsWith("jdbc:") || candidate.startsWith("ftp://");
   }

   private InputStream retrieveEntity(String entityURI) throws XMLRegistryException {
      if (this.isURL(entityURI)) {
         return this.retrieveEntityFromURL(entityURI);
      } else if (this.basePath != null) {
         String basePathLC = this.basePath.toLowerCase(Locale.ENGLISH);
         return this.retrieveEntityFromApplication(entityURI);
      } else {
         InputStream entityReader = null;
         if ((entityReader = this.retrieveEntityFromLocalDirectory(entityURI)) == null) {
            entityReader = this.retrieveEntityFromAdminServer(entityURI);
         }

         return entityReader;
      }
   }

   private InputStream retrieveEntityFromURL(String entityURL) throws XMLRegistryException {
      InputStream entityReader = null;

      try {
         URL url = new URL(entityURL);
         URLConnection connection = url.openConnection();
         entityReader = connection.getInputStream();
         return entityReader;
      } catch (IOException var5) {
         String explanation = "Can't read provided URL: " + entityURL;
         throw new XMLRegistryRemoteAccessException(explanation, var5);
      }
   }

   private InputStream retrieveEntityFromLocalDirectory(String entityURI) throws XMLRegistryException {
      InputStream entityReader = null;
      File entityFile = new File(entityURI);
      if (!entityFile.isAbsolute()) {
         entityFile = new File(this.getExtendedLibraryPath(entityURI));
      }

      if (entityFile.exists()) {
         try {
            entityReader = new FileInputStream(entityFile);
         } catch (FileNotFoundException var5) {
         }
      }

      return entityReader;
   }

   private InputStream retrieveEntityFromAdminServer(String entityURI) throws XMLRegistryException {
      InputStream entityReader = null;
      if (this.havePublicData()) {
         entityReader = this.publicRegistryDir.getEntity(entityURI);
      }

      return entityReader;
   }

   private InputStream retrieveEntityFromZip(String entityURI) throws XMLRegistryException {
      InputStream entityReader = null;
      ZipFile zip = null;
      this.basePath = "d:/weblogic/src_131sj/config/mydomain/mydeployments/examples/examples.ear";

      try {
         zip = new ZipFile(this.basePath);
      } catch (IOException var7) {
         return null;
      }

      ZipEntry entry = zip.getEntry(entityURI);
      if (entry != null) {
         try {
            entityReader = zip.getInputStream(entry);
         } catch (IOException var6) {
            throw new XMLRegistryException("Can't read zip entry: " + entityURI + " in zip: " + this.basePath, var6);
         }
      }

      return entityReader;
   }

   private InputStream retrieveEntityFromApplication(String entityURI) throws XMLRegistryException {
      File tempDTD = null;
      InputStream entityReader = null;
      VirtualJarFile jarFile = null;

      ApplicationContextInternal strm;
      try {
         ApplicationAccess access = ApplicationAccess.getApplicationAccess();
         strm = access.getApplicationContext(this.getAppDeploymentMBean().getName());
         if (strm == null) {
            return null;
         }

         jarFile = strm.getApplicationFileManager().getVirtualJarFile();
      } catch (IOException var14) {
         return null;
      }

      ZipEntry entry = jarFile.getEntry("lib/xml/registry/" + entityURI);

      try {
         if (!(new File(this.basePath)).isDirectory()) {
            strm = null;
            File parentDir = (new File(this.basePath)).getParentFile();
            if (parentDir.isDirectory()) {
               tempDTD = new File(parentDir, entityURI);
               if (this.cleanupTempEntities) {
                  if (tempDTD.getParentFile() != null && !tempDTD.getParentFile().exists()) {
                     tempDTD.getParentFile().mkdirs();
                     tempDTD.getParentFile().deleteOnExit();
                  }

                  tempDTD.createNewFile();
                  tempDTD.deleteOnExit();
                  BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tempDTD));
                  InputStream in = jarFile.getInputStream(entry);
                  byte[] buf = new byte[4096];
                  int read = false;

                  int read;
                  while((read = in.read(buf, 0, 4096)) != -1) {
                     out.write(buf, 0, read);
                  }

                  out.flush();
                  in.close();
                  out.close();
                  if (!this.isAppScopedRegistry) {
                     this.cleanupTempEntities = false;
                  }
               }
            }
         }
      } catch (IOException var13) {
         throw new XMLRegistryException("Can't read zip entry: " + entityURI + " in zip: " + this.basePath, var13);
      }

      if (entry != null) {
         strm = null;

         try {
            if (!(new File(this.basePath)).isDirectory()) {
               entityReader = this.retrieveEntityFromLocalDirectory(tempDTD.getAbsolutePath());
            } else {
               entityReader = jarFile.getInputStream(entry);
            }
         } catch (IOException var12) {
            throw new XMLRegistryException("Can't read zip entry: " + entityURI + " in zip: " + this.basePath, var12);
         }
      }

      try {
         jarFile.close();
         return entityReader;
      } catch (IOException var11) {
         return null;
      }
   }

   private void loadPrivateRegistry() throws XMLRegistryException {
      this.psIndex = new HashMap();
      this.esIndex = new HashMap();
      this.initializePrivateEntries();
   }

   private void loadPublicRegistry() throws XMLRegistryException {
      if (this.havePublicData()) {
         this.hasCustomParserEntries = false;
         this.hasDocumentSpecificParserEntries = false;
         this.hasDocumentSpecificEntityEntries = false;
         this.loadPublicEntries();
      }

      this.preLoadCache();
   }

   void preLoadCache() {
      if (this.havePublicData()) {
         RegistryEntityResolver resolver = null;
         Iterator ei = this.esIndex.values().iterator();

         while(ei.hasNext()) {
            InputSource entity = null;
            XMLEntitySpecRegistryEntry ent = (XMLEntitySpecRegistryEntry)ei.next();
            String descr = Tools.getEntityDescriptor(ent.getPublicId(), ent.getSystemId());
            String whenToCache = ent.getWhenToCache();
            if (whenToCache == null || whenToCache.equals("defer-to-registry-setting")) {
               whenToCache = this.config.getWhenToCache();
            }

            if (whenToCache != null && whenToCache.equals("cache-at-initialization")) {
               if (resolver == null) {
                  try {
                     resolver = new RegistryEntityResolver(new XMLRegistry[]{this});
                  } catch (Exception var9) {
                  }
               }

               try {
                  resolver.resolveEntity(ent.getPublicId(), ent.getSystemId());
               } catch (Exception var8) {
               }
            }
         }

      }
   }

   private XMLParserSelectRegistryEntry lookupParserSelectEntry(String publicId, String systemId, String rootTag) throws XMLRegistryException {
      if (publicId == null && systemId == null && rootTag == null) {
         throw new XMLRegistryException("Invalid parameters: at least one of publicId, systemId, rootTag must be non-null");
      } else {
         Iterator ei = this.psIndex.values().iterator();

         XMLParserSelectRegistryEntry ent;
         do {
            if (!ei.hasNext()) {
               ei = this.newParserSelectMBeans.iterator();

               XMLParserSelectRegistryEntryMBean ent;
               do {
                  if (!ei.hasNext()) {
                     return null;
                  }

                  ent = (XMLParserSelectRegistryEntryMBean)ei.next();
               } while(!this.matchesParserSelectMBean(ent, publicId, systemId, (String)null));

               XMLParserSelectRegistryEntry newEntry = this.loadParserSelectEntry(ConfigAbstraction.getParserSelectEntryConfig(ent), true);
               this.newParserSelectMBeans.remove(ent);
               return newEntry;
            }

            ent = (XMLParserSelectRegistryEntry)ei.next();
         } while(!this.matches(ent, publicId, systemId, rootTag));

         return ent;
      }
   }

   private XMLEntitySpecRegistryEntry lookupEntitySpecEntry(String publicId, String systemId) throws XMLRegistryException {
      if (publicId == null && systemId == null) {
         throw new XMLRegistryException("Invalid parameters: at least one of publicId or systemId must be non-null");
      } else {
         Iterator ei = this.esIndex.values().iterator();

         XMLEntitySpecRegistryEntry ent;
         do {
            if (!ei.hasNext()) {
               ei = this.newEntitySpecMBeans.iterator();

               XMLEntitySpecRegistryEntryMBean ent;
               do {
                  if (!ei.hasNext()) {
                     return null;
                  }

                  ent = (XMLEntitySpecRegistryEntryMBean)ei.next();
               } while(!this.matchesEntitySpecMBean(ent, publicId, systemId, (String)null));

               XMLEntitySpecRegistryEntry newEntry = this.loadEntitySpecEntry(ConfigAbstraction.getEntitySpecEntryConfig(ent), true);
               this.newEntitySpecMBeans.remove(ent);
               return newEntry;
            }

            ent = (XMLEntitySpecRegistryEntry)ei.next();
         } while(!this.matches(ent, publicId, systemId, (String)null));

         return ent;
      }
   }

   private boolean matches(XMLAbstractRegistryEntry ent, String publicId, String systemId, String rootTag) {
      if (publicId != null && publicId.equals(ent.getPublicId())) {
         return true;
      } else if (systemId != null && systemId.equals(ent.getSystemId())) {
         return true;
      } else {
         return rootTag != null && rootTag.equals(ent.getRootElementTag());
      }
   }

   private boolean matchesParserSelectMBean(XMLParserSelectRegistryEntryMBean ent, String publicId, String systemId, String rootTag) {
      if (publicId != null && publicId.equals(ent.getPublicId())) {
         return true;
      } else {
         return systemId != null && systemId.equals(ent.getSystemId());
      }
   }

   private boolean matchesEntitySpecMBean(XMLEntitySpecRegistryEntryMBean ent, String publicId, String systemId, String rootTag) {
      if (publicId != null && publicId.equals(ent.getPublicId())) {
         return true;
      } else {
         return systemId != null && systemId.equals(ent.getSystemId());
      }
   }

   private void loadPublicEntries() throws XMLRegistryException {
      if (this.havePublicData()) {
         Enumeration esEntries;
         if (!this.isAppScopedRegistry) {
            esEntries = this.config.getParserSelectRegistryEntries();

            while(esEntries != null && esEntries.hasMoreElements()) {
               this.loadParserSelectEntry((ConfigAbstraction.ParserSelectEntryConfig)esEntries.nextElement(), true);
            }
         }

         esEntries = this.config.getEntitySpecRegistryEntries();

         while(esEntries.hasMoreElements()) {
            this.loadEntitySpecEntry((ConfigAbstraction.EntitySpecEntryConfig)esEntries.nextElement(), true);
         }

      }
   }

   private synchronized XMLParserSelectRegistryEntry loadParserSelectEntry(ConfigAbstraction.ParserSelectEntryConfig bean, boolean addListener) {
      XMLParserSelectRegistryEntry ent = this.readParserSelectEntry(bean);
      if (this.hasCustomParser(ent)) {
         this.hasCustomParserEntries = true;
      }

      if (this.hasDocumentSpecificParserEntry(ent)) {
         this.hasDocumentSpecificParserEntries = true;
      }

      DocumentType dt = this.getDocumentType(ent);
      if (addListener) {
         ent.setListener(new ParserSelectEntryListener(bean, ent));
         bean.addPropertyChangeListener(ent.getListener());
      }

      this.psIndex.put(dt, ent);
      return ent;
   }

   private synchronized XMLEntitySpecRegistryEntry loadEntitySpecEntry(ConfigAbstraction.EntitySpecEntryConfig bean, boolean addListener) {
      XMLEntitySpecRegistryEntry ent = this.readEntitySpecEntry(bean);
      if (this.hasDocumentSpecificEntityEntry(ent)) {
         this.hasDocumentSpecificEntityEntries = true;
      }

      if (addListener) {
         ent.setListener(new EntitySpecEntryListener(bean, ent));
         bean.addPropertyChangeListener(ent.getListener());
      }

      DocumentType dt = this.getDocumentType(ent);
      this.esIndex.put(dt, ent);
      return ent;
   }

   private boolean hasCustomParser(XMLParserSelectRegistryEntry ent) {
      return ent.getParserClassName() != null;
   }

   private boolean hasDocumentSpecificParserEntry(XMLParserSelectRegistryEntry ent) {
      return ent.getSAXParserFactory() != null || ent.getDocumentBuilderFactory() != null || ent.getTransformerFactory() != null || ent.getParserClassName() != null;
   }

   private boolean hasDocumentSpecificEntityEntry(XMLEntitySpecRegistryEntry ent) {
      return ent.getEntityURI() != null || ent.getWhenToCache() != null || ent.getHandleEntityInvalidation() != null;
   }

   private XMLParserSelectRegistryEntry readParserSelectEntry(ConfigAbstraction.ParserSelectEntryConfig mbean) {
      String publicId = mbean.getPublicId();
      String systemId = mbean.getSystemId();
      String rootTag = mbean.getRootElementTag();
      XMLParserSelectRegistryEntry newEnt = new XMLParserSelectRegistryEntry(publicId, systemId, rootTag, mbean);
      newEnt.setDocumentBuilderFactory(mbean.getDocumentBuilderFactory());
      newEnt.setSAXParserFactory(mbean.getSAXParserFactory());
      newEnt.setTransformerFactory(mbean.getTransformerFactory());
      newEnt.setParserClassName(mbean.getParserClassName());
      return newEnt;
   }

   private XMLEntitySpecRegistryEntry readEntitySpecEntry(ConfigAbstraction.EntitySpecEntryConfig mbean) {
      String publicId = mbean.getPublicId();
      String systemId = mbean.getSystemId();
      XMLEntitySpecRegistryEntry newEnt = new XMLEntitySpecRegistryEntry(publicId, systemId, mbean);
      newEnt.setEntityURI(mbean.getEntityURI());
      newEnt.setWhenToCache(mbean.getWhenToCache());
      int timeout = mbean.getCacheTimeoutInterval();
      newEnt.setCacheTimeoutInterval(timeout);
      String invalidation = mbean.getHandleEntityInvalidation();
      newEnt.setHandleEntityInvalidation(invalidation);
      return newEnt;
   }

   private synchronized XMLEntitySpecRegistryEntry updateEntitySpecEntry(ConfigAbstraction.EntitySpecEntryConfig mbean, XMLEntitySpecRegistryEntry ent) {
      ent.setEntityURI(mbean.getEntityURI());
      ent.setWhenToCache(mbean.getWhenToCache());
      ent.setCacheTimeoutInterval(mbean.getCacheTimeoutInterval());
      ent.setHandleEntityInvalidation(mbean.getHandleEntityInvalidation());
      return ent;
   }

   private synchronized XMLParserSelectRegistryEntry updateParserSelectEntry(ConfigAbstraction.ParserSelectEntryConfig mbean, XMLParserSelectRegistryEntry ent) {
      ent.setDocumentBuilderFactory(mbean.getDocumentBuilderFactory());
      ent.setSAXParserFactory(mbean.getSAXParserFactory());
      ent.setTransformerFactory(mbean.getTransformerFactory());
      ent.setParserClassName(mbean.getParserClassName());
      return ent;
   }

   private void initializePrivateEntries() {
      this.addPrivateResolverEntry("-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 1.1//EN", "http://java.sun.com/j2ee/dtds/ejb-jar_1_1.dtd", "ejb11-jar.dtd");
      this.addPrivateResolverEntry("-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN", "http://java.sun.com/j2ee/dtds/ejb-jar_2_0.dtd", "ejb20-jar.dtd");
      this.addPrivateResolverEntry("-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB//EN", "http://www.bea.com/servers/wls510/dtd/weblogic-ejb-jar.dtd", "weblogic510-ejb-jar.dtd");
      this.addPrivateResolverEntry("-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB//EN", "http://www.bea.com/servers/wls600/dtd/weblogic-ejb-jar.dtd", "weblogic600-ejb-jar.dtd");
      this.addPrivateResolverEntry("-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB RDBMS Persistence//EN", "http://www.bea.com/servers/wls510/dtd/weblogic-rdbms-persistence.dtd", "weblogic-rdbms-persistence.dtd");
      this.addPrivateResolverEntry("-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN", "http://www.bea.com/servers/wls600/dtd/weblogic-rdbms20-persistence-600.dtd", "weblogic-rdbms20-persistence-600.dtd");
   }

   private void addPrivateResolverEntry(String publicId, String systemId, String localDTDPath) {
      DocumentType key = new DocumentType(publicId, systemId, (String)null);
      if (!this.esIndex.containsKey(key)) {
         XMLEntitySpecRegistryEntry newEnt = new XMLEntitySpecRegistryEntry(publicId, systemId, (ConfigAbstraction.EntryConfig)null);
         newEnt.setEntityURI(localDTDPath);
         newEnt.setPrivate(true);
         newEnt.setCacheTimeoutInterval(600);
         this.esIndex.put(key, newEnt);
      }

   }

   private DocumentType getDocumentType(XMLAbstractRegistryEntry entry) {
      return new DocumentType(entry.getPublicId(), entry.getSystemId(), entry.getRootElementTag());
   }

   public boolean havePublicData() {
      return this.config != null;
   }

   void removeEntry(XMLEntitySpecRegistryEntry entry) {
      try {
         this.getCache().remove(entry.getPublicId(), entry.getSystemId());
      } catch (Exception var3) {
      }

      DocumentType dt = this.getDocumentType(entry);
      this.esIndex.remove(dt);
   }

   void removeEntry(XMLParserSelectRegistryEntry entry) {
      DocumentType dt = this.getDocumentType(entry);
      this.psIndex.remove(dt);
   }

   private Object getFactory(String factoryClassName, String defaultFactory, String appfactoryName, boolean useApp) throws XMLRegistryException {
      Object ret = null;
      boolean setTCCL = false;
      String value = null;
      Thread thisThread = null;
      ClassLoader tccl = null;
      if (factoryClassName == null) {
         return null;
      } else {
         try {
            if (appfactoryName != null) {
               if (!appfactoryName.equals(defaultFactory)) {
                  Thread t = Thread.currentThread();
                  ClassLoader cl = t.getContextClassLoader();
                  Object var41 = Class.forName(appfactoryName, true, cl).newInstance();
                  return var41;
               }

               factoryClassName = appfactoryName;
            }

            Class cls;
            if (factoryClassName.equals(defaultFactory)) {
               try {
                  value = System.getProperty(defaultFactory);
                  if (value != null) {
                     System.clearProperty(defaultFactory);
                  }
               } catch (Exception var28) {
               }

               thisThread = Thread.currentThread();
               tccl = thisThread.getContextClassLoader();
               setTCCL = true;
               thisThread.setContextClassLoader(platformClassLoader());
               cls = Class.forName(defaultFactory);
               if (factoryClassName.indexOf("SchemaFactory") != -1) {
                  Class[] c = new Class[]{String.class};
                  Method m = cls.getMethod("newInstance", c);
                  ret = m.invoke(cls, "http://www.w3.org/2001/XMLSchema");
               } else {
                  Method m = cls.getMethod("newInstance", (Class[])null);
                  ret = m.invoke(cls);
               }

               return ret;
            } else {
               cls = null;
               if (useApp && this.isAppScopedRegistry && this.applicationAccess.getCurrentApplicationName() != null) {
                  Thread t = Thread.currentThread();
                  ClassLoader cl = t.getContextClassLoader();
                  ret = Class.forName(factoryClassName, true, cl).newInstance();
               } else {
                  ret = Class.forName(factoryClassName).newInstance();
               }

               return ret;
            }
         } catch (ClassNotFoundException var29) {
            throw new XMLRegistryException("ClassNotFoundException. Class " + var29.getMessage() + " cannot be located.", var29);
         } catch (ClassCastException var30) {
            throw new XMLRegistryException("ClassCastException. Class " + var30.getMessage() + " is not of type " + defaultFactory + ".", var30);
         } catch (IllegalAccessException var31) {
            throw new XMLRegistryException("IllegalAccessException. Class " + var31.getMessage() + " is not accessible.", var31);
         } catch (InstantiationException var32) {
            throw new XMLRegistryException("InstantiationException. " + var32.getMessage() + " Class instantiation fails. This Class represents an abstract class, an interface, an array class, a primitive type, or void, or instantiation fails for some other reason", var32);
         } catch (SecurityException var33) {
            throw new XMLRegistryException("SecurityException. There is no permission to create a new instance " + var33.getMessage(), var33);
         } catch (Exception var34) {
            throw new XMLRegistryException(var34);
         } finally {
            if (setTCCL) {
               thisThread.setContextClassLoader(tccl);
            }

            if (value != null) {
               try {
                  System.setProperty(factoryClassName, value);
               } catch (Exception var27) {
               }
            }

         }
      }
   }

   private static ClassLoader platformClassLoader() throws Exception {
      ClassLoader parent;
      for(ClassLoader loader = ClassLoader.getSystemClassLoader(); loader != null; loader = parent) {
         parent = loader.getParent();
         if (parent == null) {
            return loader;
         }
      }

      throw new Exception("Unable to determine platform/extension class loader");
   }

   static class ServerListener implements PropertyChangeListener {
      public void propertyChange(PropertyChangeEvent notification) {
         String attributeName = notification.getPropertyName();
         if (attributeName.equalsIgnoreCase("xmlregistry")) {
            try {
               XMLRegistry.reinitializeDefaultRegistry();
            } catch (XMLRegistryException var4) {
            }
         }

      }
   }

   class CacheListener implements PropertyChangeListener {
      XMLRegistry registry = null;

      CacheListener(XMLRegistry registry) {
         this.registry = registry;
      }

      public void propertyChange(PropertyChangeEvent note) {
         String attributeName = note.getPropertyName();
         Object value = note.getNewValue();
         if ("CacheMemorySize".equals(attributeName) && value != null) {
            try {
               this.registry.getCache().setMemorySize((Integer)value * 1000);
            } catch (Exception var7) {
            }
         } else if ("CacheDiskSize".equals(attributeName) && value != null) {
            try {
               this.registry.getCache().setDiskSize((Integer)value * 1000000);
            } catch (Exception var6) {
            }
         } else if ("CacheTimeoutInterval".equals(attributeName)) {
            try {
               if (value != null) {
                  this.registry.setCacheDefaultTimeoutInterval((Integer)value * 1000);
               } else {
                  this.registry.setCacheDefaultTimeoutInterval(-1);
               }
            } catch (Exception var5) {
            }
         }

      }
   }

   class RegistryListener implements PropertyChangeListener {
      XMLRegistry registry = null;

      RegistryListener(XMLRegistry registry) {
         this.registry = registry;
      }

      public void propertyChange(PropertyChangeEvent note) {
         String attributeName = note.getPropertyName();
         if (note.getOldValue() == null && note.getNewValue() != null) {
            this.addNewMBean(attributeName, (WebLogicMBean)note.getNewValue());
         } else if (note.getOldValue() != null && note.getNewValue() == null) {
            this.removeOldMBean(attributeName, (WebLogicMBean)note.getOldValue());
         } else if (note.getNewValue() != null && (attributeName.equalsIgnoreCase("ParserSelectRegistryEntries") || attributeName.equalsIgnoreCase("EntitySpecRegistryEntries"))) {
            WebLogicMBean mbean = this.findDeletedMBean(attributeName, (Object[])((Object[])note.getOldValue()), (Object[])((Object[])note.getNewValue()));
            this.removeOldMBean(attributeName, mbean);
         }

      }

      WebLogicMBean findDeletedMBean(String attributeName, Object[] oldSet, Object[] newSet) {
         try {
            Object oldOne = null;
            Object newOne = null;
            int i = 0;

            while(i < oldSet.length) {
               oldOne = oldSet[i];
               int j = false;
               int jx = 0;

               while(true) {
                  if (jx < newSet.length) {
                     newOne = newSet[jx];
                     if (!oldOne.equals(newOne)) {
                        ++jx;
                        continue;
                     }
                  }

                  if (jx == newSet.length) {
                     return (WebLogicMBean)oldOne;
                  }

                  ++i;
                  break;
               }
            }
         } catch (Exception var8) {
         }

         return null;
      }

      void addNewMBean(String attributeName, WebLogicMBean mbean) {
         if ("ParserSelectRegistryEntries".equals(attributeName)) {
            XMLParserSelectRegistryEntryMBean newEntryMBean = (XMLParserSelectRegistryEntryMBean)mbean;
            if (newEntryMBean.getPublicId() == null && newEntryMBean.getSystemId() == null && newEntryMBean.getRootElementTag() == null) {
               XMLRegistry.this.newParserSelectMBeans.add(newEntryMBean);
               this.registry.hasDocumentSpecificParserEntries = true;
            } else {
               XMLParserSelectRegistryEntry var4 = this.registry.loadParserSelectEntry(ConfigAbstraction.getParserSelectEntryConfig(newEntryMBean), true);
            }
         } else if ("EntitySpecRegistryEntries".equals(attributeName)) {
            XMLEntitySpecRegistryEntryMBean newEntryMBeanx = (XMLEntitySpecRegistryEntryMBean)mbean;
            if (newEntryMBeanx.getPublicId() == null && newEntryMBeanx.getSystemId() == null) {
               XMLRegistry.this.newEntitySpecMBeans.add(newEntryMBeanx);
               this.registry.hasDocumentSpecificEntityEntries = true;
            } else {
               XMLEntitySpecRegistryEntry var6 = this.registry.loadEntitySpecEntry(ConfigAbstraction.getEntitySpecEntryConfig(newEntryMBeanx), true);
            }
         }

      }

      void removeOldMBean(String attributeName, WebLogicMBean mbean) {
         try {
            if ("ParserSelectRegistryEntries".equals(attributeName)) {
               XMLParserSelectRegistryEntryMBean deadEntryMBeanx = (XMLParserSelectRegistryEntryMBean)mbean;
               XMLParserSelectRegistryEntry deadEntryx = this.registry.readParserSelectEntry(ConfigAbstraction.getParserSelectEntryConfig(deadEntryMBeanx));
               this.registry.removeEntry(deadEntryx);
            } else if ("EntitySpecRegistryEntries".equals(attributeName)) {
               XMLEntitySpecRegistryEntryMBean deadEntryMBean = (XMLEntitySpecRegistryEntryMBean)mbean;
               XMLEntitySpecRegistryEntry deadEntry = this.registry.readEntitySpecEntry(ConfigAbstraction.getEntitySpecEntryConfig(deadEntryMBean));
               this.registry.removeEntry(deadEntry);
            }
         } catch (Exception var5) {
         }

      }
   }

   class EntitySpecEntryListener implements PropertyChangeListener {
      ConfigAbstraction.EntitySpecEntryConfig mbean = null;
      XMLEntitySpecRegistryEntry entry = null;

      EntitySpecEntryListener(ConfigAbstraction.EntitySpecEntryConfig mbean, XMLEntitySpecRegistryEntry entry) {
         this.mbean = mbean;
         this.entry = entry;
      }

      public void propertyChange(PropertyChangeEvent note) {
         String attributeName = note.getPropertyName();
         if (!attributeName.equalsIgnoreCase("parent")) {
            Object oldValue = note.getOldValue();
            Object newValue = note.getNewValue();
            if (oldValue == null) {
               oldValue = "";
            }

            if (newValue == null) {
               newValue = "";
            }

            if (!oldValue.equals(newValue)) {
               if (!attributeName.equalsIgnoreCase("publicid") && !attributeName.equalsIgnoreCase("systemid")) {
                  if (this.entry != null) {
                     XMLRegistry.this.updateEntitySpecEntry(this.mbean, this.entry);

                     try {
                        XMLRegistry.this.getCache().putrify(this.mbean.getPublicId(), this.mbean.getSystemId());
                     } catch (Exception var6) {
                     }
                  }
               } else {
                  if (this.entry != null) {
                     XMLRegistry.this.removeEntry(this.entry);
                  }

                  this.entry = XMLRegistry.this.loadEntitySpecEntry(this.mbean, false);
               }
            }
         }

      }
   }

   class ParserSelectEntryListener implements PropertyChangeListener {
      ConfigAbstraction.ParserSelectEntryConfig mbean = null;
      XMLParserSelectRegistryEntry entry = null;

      ParserSelectEntryListener(ConfigAbstraction.ParserSelectEntryConfig mbean, XMLParserSelectRegistryEntry entry) {
         this.mbean = mbean;
         this.entry = entry;
      }

      public void propertyChange(PropertyChangeEvent note) {
         String attributeName = note.getPropertyName();
         if (!attributeName.equalsIgnoreCase("parent")) {
            Object oldValue = (String)note.getOldValue();
            Object newValue = (String)note.getNewValue();
            if (oldValue == null) {
               oldValue = "";
            }

            if (newValue == null) {
               newValue = "";
            }

            if (!oldValue.equals(newValue)) {
               if (!attributeName.equalsIgnoreCase("publicid") && !attributeName.equalsIgnoreCase("systemid") && !attributeName.equalsIgnoreCase("rootelementtag")) {
                  if (this.entry != null) {
                     XMLRegistry.this.updateParserSelectEntry(this.mbean, this.entry);
                  }
               } else {
                  if (this.entry != null) {
                     XMLRegistry.this.removeEntry(this.entry);
                  }

                  this.entry = XMLRegistry.this.loadParserSelectEntry(this.mbean, false);
               }
            }
         }

      }
   }

   public class ResolvedEntity {
      XMLEntitySpecRegistryEntry entry = null;
      InputSource inputSource = null;
      boolean isLocal = false;
      boolean isSubjectToCaching = true;

      public InputSource inputSource() {
         return this.inputSource;
      }

      public boolean isSubjectToCaching() {
         return this.isSubjectToCaching;
      }

      public boolean isLocal() {
         return this.isLocal;
      }

      public XMLEntitySpecRegistryEntry getEntry() {
         return this.entry;
      }
   }

   class CacheLogListener implements weblogic.xml.util.cache.entitycache.CacheListener {
      public void notify(Event.CacheUtilityEvent event) {
         try {
            if (event instanceof Event.MemoryPurgeEvent) {
               Event.MemoryPurgeEvent exx = (Event.MemoryPurgeEvent)event;
               XMLLogger.logCacheMemoryPurge(exx.cacheEntries.size(), exx.combinedMemorySize, exx.currentMemorySize);
            } else if (event instanceof Event.DiskPurgeEvent) {
               Event.DiskPurgeEvent exxxxx = (Event.DiskPurgeEvent)event;
               XMLLogger.logCacheDiskPurge(exxxxx.cacheEntries.size(), exxxxx.combinedDiskSize, exxxxx.currentDiskSize);
            } else if (event instanceof Event.EntryDiskRejectionEvent) {
               Event.EntryDiskRejectionEvent exxxxxx = (Event.EntryDiskRejectionEvent)event;
               XMLLogger.logCacheDiskRejection(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(exxxxxx.cacheEntry.getCacheKey()), exxxxxx.diskSize);
            } else if (event instanceof Event.EntryRejectionEvent) {
               Event.EntryRejectionEvent exxxxxxx = (Event.EntryRejectionEvent)event;
               XMLLogger.logCacheRejection(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(exxxxxxx.cacheEntry.getCacheKey()), exxxxxxx.memorySize);
            } else if (event instanceof Event.EntryAddEvent) {
               Event.EntryAddEvent exxxxxxxx = (Event.EntryAddEvent)event;
               XMLLogger.logCacheEntryAdd(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(exxxxxxxx.cacheEntry.getCacheKey()), exxxxxxxx.memorySize, exxxxxxxx.cacheEntry.isPersistent() ? "Persistent" : "Transient", exxxxxxxx.currentMemorySize);
            } else if (event instanceof Event.EntryDeleteEvent) {
               Event.EntryDeleteEvent exxxxxxxxx = (Event.EntryDeleteEvent)event;
               XMLLogger.logCacheEntryDelete(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(exxxxxxxxx.cacheEntry.getCacheKey()), exxxxxxxxx.memorySize, exxxxxxxxx.diskSize, exxxxxxxxx.currentMemorySize, exxxxxxxxx.currentDiskSize);
            } else if (event instanceof Event.EntryPersistEvent) {
               Event.EntryPersistEvent exxxxxxxxxx = (Event.EntryPersistEvent)event;
               XMLLogger.logCacheEntryPersist(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(exxxxxxxxxx.cacheEntry.getCacheKey()), exxxxxxxxxx.diskSize, exxxxxxxxxx.currentDiskSize);
            } else if (event instanceof Event.EntryLoadEvent) {
               Event.EntryLoadEvent exxxxxxxxxxx = (Event.EntryLoadEvent)event;
               XMLLogger.logCacheEntryLoad(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(exxxxxxxxxxx.cacheEntry.getCacheKey()), exxxxxxxxxxx.memorySize, exxxxxxxxxxx.currentMemorySize);
            } else if (event instanceof Event.StatCheckpointEvent) {
               Event.StatCheckpointEvent exxxxxxxxxxxx = (Event.StatCheckpointEvent)event;
               XMLLogger.logCacheStatisticsCheckpoint();
            } else if (event instanceof Event.CacheCreationEvent) {
               Event.CacheCreationEvent exxxxxxxxxxxxx = (Event.CacheCreationEvent)event;
               XMLLogger.logCacheCreation(exxxxxxxxxxxxx.currentMemorySize, exxxxxxxxxxxxx.currentDiskSize);
            } else if (event instanceof Event.CacheLoadEvent) {
               Event.CacheLoadEvent exxxxxxxxxxxxxx = (Event.CacheLoadEvent)event;
               XMLLogger.logCacheLoad(exxxxxxxxxxxxxx.currentMemorySize, exxxxxxxxxxxxxx.currentDiskSize);
            } else if (event instanceof Event.CacheCloseEvent) {
               Event.CacheCloseEvent exxxxxxxxxxxxxxx = (Event.CacheCloseEvent)event;
               XMLLogger.logCacheClose(exxxxxxxxxxxxxxx.currentDiskSize);
            } else if (event instanceof Event.CacheCorruptionEvent) {
               Event.CacheCorruptionEvent exxxxxxxxxxxxxxxx = (Event.CacheCorruptionEvent)event;
               XMLLogger.logCacheCorrupted(exxxxxxxxxxxxxxxx.path);
            } else if (event instanceof Event.EntryCorruptionEvent) {
               Event.EntryCorruptionEvent exxxxxxxxxxxxxxxxx = (Event.EntryCorruptionEvent)event;
               XMLLogger.logCacheEntryCorrupted(XMLRegistry.this.getName(), exxxxxxxxxxxxxxxxx.path, XMLRegistry.this.getCache().getDescription(exxxxxxxxxxxxxxxxx.key));
            } else if (event instanceof Event.StatisticsCorruptionEvent) {
               Event.StatisticsCorruptionEvent exxxxxxxxxxxxxxxxxx = (Event.StatisticsCorruptionEvent)event;
               XMLLogger.logCacheStatisticsCorrupted(exxxxxxxxxxxxxxxxxx.path);
            } else if (event instanceof Event.FileAccessErrorForEntryEvent) {
               Event.FileAccessErrorForEntryEvent exxxxxxxxxxxxxxxxxxx = (Event.FileAccessErrorForEntryEvent)event;
               if (exxxxxxxxxxxxxxxxxxx.onWrite) {
                  XMLLogger.logCacheEntrySaveError(XMLRegistry.this.getName(), exxxxxxxxxxxxxxxxxxx.path, XMLRegistry.this.getCache().getDescription(exxxxxxxxxxxxxxxxxxx.cacheEntry.getCacheKey()));
               } else {
                  XMLLogger.logCacheEntryReadError(XMLRegistry.this.getName(), exxxxxxxxxxxxxxxxxxx.path, XMLRegistry.this.getCache().getDescription(exxxxxxxxxxxxxxxxxxx.cacheEntry.getCacheKey()));
               }
            } else if (event instanceof Event.FileAccessErrorForCacheEvent) {
               Event.FileAccessErrorForCacheEvent exxxxxxxxxxxxxxxxxxxx = (Event.FileAccessErrorForCacheEvent)event;
               if (exxxxxxxxxxxxxxxxxxxx.onWrite) {
                  XMLLogger.logCacheSaveError(exxxxxxxxxxxxxxxxxxxx.path);
               } else {
                  XMLLogger.logCacheReadError(exxxxxxxxxxxxxxxxxxxx.path);
               }
            } else if (event instanceof Event.FileAccessErrorForStatisticsEvent) {
               Event.FileAccessErrorForStatisticsEvent exxxxxxxxxxxxxxxxxxxxx = (Event.FileAccessErrorForStatisticsEvent)event;
               if (exxxxxxxxxxxxxxxxxxxxx.onWrite) {
                  XMLLogger.logCacheStatisticsSaveError(exxxxxxxxxxxxxxxxxxxxx.path);
               } else {
                  XMLLogger.logCacheStatisticsReadError(exxxxxxxxxxxxxxxxxxxxx.path);
               }
            } else if (event instanceof Event.OutOfMemoryLoadingEntryEvent) {
               Event.OutOfMemoryLoadingEntryEvent e = (Event.OutOfMemoryLoadingEntryEvent)event;
               XMLLogger.logCacheOutOfMemoryOnEntryLoad(XMLRegistry.this.getName(), XMLRegistry.this.getCache().getDescription(e.key), e.path);
            } else if (event instanceof Event.OutOfMemoryLoadingCacheEvent) {
               Event.OutOfMemoryLoadingCacheEvent ex = (Event.OutOfMemoryLoadingCacheEvent)event;
               XMLLogger.logCacheOutOfMemoryOnLoad(ex.path);
            } else if (event instanceof Event.OutOfMemoryLoadingStatisticsEvent) {
               Event.OutOfMemoryLoadingStatisticsEvent exxx = (Event.OutOfMemoryLoadingStatisticsEvent)event;
               XMLLogger.logCacheOutOfMemoryOnStatisticsLoad(exxx.path);
            } else if (event instanceof Event.CacheFailureEvent) {
               Event.CacheFailureEvent exxxx = (Event.CacheFailureEvent)event;
               String cache = exxxx.path;
               if (exxxx.cache != null) {
                  cache = exxxx.cache.getName();
               }

               XMLLogger.logCacheUnexpectedProblem(exxxx.message);
            }
         } catch (Exception var4) {
         }

      }

      public void cacheUpdateOccured(Object key, String applicationName) {
      }
   }
}
