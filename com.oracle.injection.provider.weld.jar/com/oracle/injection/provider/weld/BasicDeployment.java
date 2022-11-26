package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import com.oracle.injection.InjectionArchiveType;
import com.oracle.injection.spi.ContainerIntegrationService;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.CDI11Deployment;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.configuration.spi.ExternalConfiguration;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.metadata.FileMetadata;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.security.spi.SecurityServices;
import org.jboss.weld.transaction.spi.TransactionServices;

class BasicDeployment implements CDI11Deployment {
   static final Logger logger = Logger.getLogger(BasicDeployment.class.getName());
   private static final String META_INF_SERVICES_EXTENSION = "/META-INF/services/javax.enterprise.inject.spi.Extension";
   private final Bootstrap m_weldBootstrap;
   private final Collection m_injectionArchives;
   private final ContainerIntegrationService m_integrationService;
   private final ServiceRegistry m_serviceRegistry;
   private final List m_beanDeploymentArchives = new ArrayList();
   private final Map m_mapOfBeanDeploymentArchives = new HashMap();
   private Map extensionBDAMap = new HashMap();
   private List rarRootBdas = new ArrayList();
   private List ejbRootBdas = new ArrayList();
   private List warRootBdas = new ArrayList();
   private List libJarRootBdas = new ArrayList();

   public BasicDeployment(Bootstrap weldBootstrap, Collection injectionArchives, ContainerIntegrationService integrationService) {
      this.m_weldBootstrap = weldBootstrap;
      this.m_injectionArchives = injectionArchives;
      this.m_integrationService = integrationService;
      this.m_serviceRegistry = new SimpleServiceRegistry();
      this.initialize();
      this.registerWeldServices();
   }

   public Collection getBeanDeploymentArchives() {
      return this.m_beanDeploymentArchives;
   }

   public ServiceRegistry getServices() {
      return this.m_serviceRegistry;
   }

   public Iterable getExtensions() {
      ExtensionComparator comparator = new ExtensionComparator();
      TreeSet sortedExtensions = new TreeSet(comparator);
      Iterator var3 = this.m_injectionArchives.iterator();

      while(var3.hasNext()) {
         InjectionArchive injectionArchive = (InjectionArchive)var3.next();
         ClassLoader curClassLoader = Thread.currentThread().getContextClassLoader();

         try {
            Thread.currentThread().setContextClassLoader(injectionArchive.getClassLoader());
            Iterable iterableOfExtensions = this.m_weldBootstrap.loadExtensions(injectionArchive.getClassLoader());
            Iterator var7 = iterableOfExtensions.iterator();

            while(var7.hasNext()) {
               Metadata extensionMetadata = (Metadata)var7.next();
               if (extensionMetadata instanceof FileMetadata) {
                  FileMetadata fileMetadata = (FileMetadata)extensionMetadata;
                  if (!this.extensionAlreadyInSet(sortedExtensions, fileMetadata)) {
                     sortedExtensions.add(extensionMetadata);
                  }
               }
            }
         } finally {
            Thread.currentThread().setContextClassLoader(curClassLoader);
         }
      }

      this.buildExtensionBdas(sortedExtensions);
      return sortedExtensions;
   }

   private void buildExtensionBdas(TreeSet sortedExtensions) {
      Iterator var2 = sortedExtensions.iterator();

      while(var2.hasNext()) {
         Metadata extensionMetadata = (Metadata)var2.next();
         FileMetadata extension = (FileMetadata)extensionMetadata;
         Class extensionClass = extension.getValue().getClass();
         URI codeSourceUri = this.getBeanClassCodeSourceUri(extensionClass);
         BeanDeploymentArchive extensionBDA = (BeanDeploymentArchive)this.extensionBDAMap.get(codeSourceUri);
         if (extensionBDA == null) {
            this.createOneExtensionBda(this.getExtensionFileName(extension), extension.getValue().getClass(), codeSourceUri);
         }
      }

   }

   private ExtensionBeanDeploymentArchive createOneExtensionBda(String extensionJarName, Class extensionClass, URI codeSourceUri) {
      String extensionId = extensionJarName + "_" + (this.m_beanDeploymentArchives.size() + 1);
      ExtensionBeanDeploymentArchive extensionBeanDeploymentArchive = new ExtensionBeanDeploymentArchive(extensionId, extensionClass, this.m_integrationService, extensionJarName);
      Iterator var6 = this.m_beanDeploymentArchives.iterator();

      while(var6.hasNext()) {
         BeanDeploymentArchive bda = (BeanDeploymentArchive)var6.next();
         bda.getBeanDeploymentArchives().add(extensionBeanDeploymentArchive);
      }

      this.m_beanDeploymentArchives.add(extensionBeanDeploymentArchive);
      if (codeSourceUri != null) {
         this.extensionBDAMap.put(codeSourceUri, extensionBeanDeploymentArchive);
      }

      return extensionBeanDeploymentArchive;
   }

   private String getExtensionFileName(FileMetadata extension) {
      int pathNdx = extension.getFile().getPath().indexOf("/META-INF/services/javax.enterprise.inject.spi.Extension");
      if (pathNdx == -1) {
         return extension.getFile().getPath();
      } else {
         String fileName = extension.getFile().getPath().substring(0, pathNdx);
         int lastSlashPos = fileName.lastIndexOf("/");
         return fileName.substring(lastSlashPos + 1);
      }
   }

   private boolean extensionAlreadyInSet(Set existingExtensions, FileMetadata fileMetadataToCheck) {
      URL fileUrl = fileMetadataToCheck.getFile();
      int lineNumber = fileMetadataToCheck.getLineNumber();
      Iterator var5 = existingExtensions.iterator();

      while(var5.hasNext()) {
         Metadata oneExistingExtension = (Metadata)var5.next();
         FileMetadata oneExistingFileExtension = (FileMetadata)oneExistingExtension;

         try {
            if (oneExistingFileExtension.getFile().toURI().equals(fileUrl.toURI()) && oneExistingFileExtension.getLineNumber() == lineNumber) {
               return true;
            }
         } catch (URISyntaxException var9) {
            logger.log(Level.WARNING, "Exception getting uri for extension: " + var9);
         }
      }

      return false;
   }

   private BeanDeploymentArchive getBdaFromNonRootBdas(Class beanClass) {
      String beanClassName = beanClass.getName();
      Collection bdas = this.m_mapOfBeanDeploymentArchives.values();
      Iterator var4 = bdas.iterator();

      while(var4.hasNext()) {
         BeanDeploymentArchive oneBda = (BeanDeploymentArchive)var4.next();
         if (oneBda.getBeanClasses().contains(beanClassName)) {
            ResourceLoader resourceLoader = (ResourceLoader)oneBda.getServices().get(ResourceLoader.class);
            if (resourceLoader != null && resourceLoader instanceof BasicResourceLoader) {
               BasicResourceLoader basicResourceLoader = (BasicResourceLoader)resourceLoader;
               if (beanClass.getClassLoader().equals(basicResourceLoader.getInjectionArchiveClassLoader())) {
                  return oneBda;
               }
            }
         }
      }

      return null;
   }

   public BeanDeploymentArchive loadBeanDeploymentArchive(Class beanClass) {
      BeanDeploymentArchive nonRootBda = this.getBdaFromNonRootBdas(beanClass);
      if (nonRootBda != null) {
         return nonRootBda;
      } else {
         URI codeSourceUri = this.getBeanClassCodeSourceUri(beanClass);
         BeanDeploymentArchive extensionBDA = (BeanDeploymentArchive)this.extensionBDAMap.get(codeSourceUri);
         if (extensionBDA != null) {
            return extensionBDA;
         } else if (codeSourceUri == null) {
            return this.createOneExtensionBda(beanClass.getName(), beanClass, (URI)null);
         } else {
            int ndx = codeSourceUri.getPath().lastIndexOf("/");
            String jarName = codeSourceUri.getPath().substring(ndx + 1);
            return this.createOneExtensionBda(jarName, beanClass, codeSourceUri);
         }
      }
   }

   private URI getBeanClassCodeSourceUri(final Class beanClass) {
      ProtectionDomain protectionDomain = (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return beanClass.getProtectionDomain();
         }
      });
      CodeSource cs = protectionDomain.getCodeSource();

      try {
         if (cs != null) {
            return cs.getLocation().toURI();
         }
      } catch (URISyntaxException var5) {
         if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Exception getting uri for beanClass " + beanClass.getName() + ".  Exception: " + var5);
         }
      }

      return null;
   }

   BeanDeploymentArchive getBeanDeploymentArchive(String archiveName) {
      return (BeanDeploymentArchive)this.m_mapOfBeanDeploymentArchives.get(archiveName);
   }

   private boolean initialize() {
      EEModuleDescriptorFactory eeModuleDescriptorFactory = new EEModuleDescriptorFactory();
      Iterator var3 = this.m_injectionArchives.iterator();

      while(true) {
         BasicBeanDeploymentArchive moduleBda;
         EEModuleDescriptor eeModuleDescriptor;
         RootBeanDeploymentArchive rootBda;
         Collection embeddedArchives;
         do {
            if (!var3.hasNext()) {
               this.setupAccessibleBeanArchives();
               return true;
            }

            InjectionArchive moduleInjectionArchive = (InjectionArchive)var3.next();
            eeModuleDescriptor = eeModuleDescriptorFactory.createEEModuleDescriptor(moduleInjectionArchive);
            moduleBda = this.createBeanDeploymentArchive(moduleInjectionArchive, eeModuleDescriptor);
            rootBda = this.createRootBda(moduleInjectionArchive, moduleBda, eeModuleDescriptor);
            embeddedArchives = moduleInjectionArchive.getEmbeddedArchives();
         } while(embeddedArchives == null);

         ArrayList embeddedBeanDeploymentArchiveList = new ArrayList();
         Iterator var9 = embeddedArchives.iterator();

         while(var9.hasNext()) {
            InjectionArchive embeddedArchive = (InjectionArchive)var9.next();
            if (embeddedArchive != null) {
               BasicBeanDeploymentArchive embeddedBeanArchive = this.createBeanDeploymentArchive(embeddedArchive, eeModuleDescriptor);
               moduleBda.addAccessibleBeanArchive(embeddedBeanArchive);
               rootBda.addAccessibleBeanArchive(embeddedBeanArchive);
               embeddedBeanArchive.addAccessibleBeanArchive(moduleBda);
               embeddedBeanArchive.addAccessibleBeanArchive(rootBda);
               this.makeEmbeddedArchivesVisibleToEachOther(embeddedBeanArchive, embeddedBeanDeploymentArchiveList);
               embeddedBeanDeploymentArchiveList.add(embeddedBeanArchive);
               this.m_mapOfBeanDeploymentArchives.put(embeddedArchive.getArchiveName(), embeddedBeanArchive);
            }
         }
      }
   }

   private RootBeanDeploymentArchive createRootBda(InjectionArchive moduleInjectionArchive, BasicBeanDeploymentArchive moduleBda, EEModuleDescriptor eeModuleDescriptor) {
      RootBeanDeploymentArchive rootBda = new RootBeanDeploymentArchive(moduleInjectionArchive, moduleBda, this.m_integrationService, eeModuleDescriptor);
      if (isWarArchive(moduleInjectionArchive)) {
         this.warRootBdas.add(rootBda);
      } else if (isEJBArchive(moduleInjectionArchive)) {
         this.ejbRootBdas.add(rootBda);
      } else if (isRarArchive(moduleInjectionArchive)) {
         this.rarRootBdas.add(rootBda);
      } else if (isEarLibraryJarArchive(moduleInjectionArchive)) {
         this.libJarRootBdas.add(rootBda);
      }

      moduleBda.addAccessibleBeanArchive(rootBda);
      rootBda.addAccessibleBeanArchive(moduleBda);
      this.m_beanDeploymentArchives.add(rootBda);
      return rootBda;
   }

   private void makeEmbeddedArchivesVisibleToEachOther(BasicBeanDeploymentArchive embeddedBeanArchive, ArrayList embeddedBeanDeploymentArchives) {
      Iterator var3 = embeddedBeanDeploymentArchives.iterator();

      while(var3.hasNext()) {
         BasicBeanDeploymentArchive existingEmbeddedBeanArchive = (BasicBeanDeploymentArchive)var3.next();
         embeddedBeanArchive.addAccessibleBeanArchive(existingEmbeddedBeanArchive);
         existingEmbeddedBeanArchive.addAccessibleBeanArchive(embeddedBeanArchive);
      }

   }

   private void registerWeldServices() {
      WeldConfigurationPropertiesFactory weldConfigurationPropertiesFactory = new WeldConfigurationPropertiesFactory();
      this.m_serviceRegistry.add(TransactionServices.class, new WeldTransactionServicesAdapter(this.m_integrationService));
      this.m_serviceRegistry.add(EjbServices.class, new WeldEjbServicesAdapter(this.m_integrationService));
      this.m_serviceRegistry.add(SecurityServices.class, new WeldSecurityServicesAdapter(this.m_integrationService));
      this.m_serviceRegistry.add(ExternalConfiguration.class, weldConfigurationPropertiesFactory.createExternalConfiguration());
   }

   private void setupAccessibleBeanArchives() {
      HashMap allRarEmbeddedBdas = new HashMap();
      Iterator var2;
      RootBeanDeploymentArchive libJarRootBda;
      if (this.rarRootBdas != null) {
         var2 = this.rarRootBdas.iterator();

         while(var2.hasNext()) {
            libJarRootBda = (RootBeanDeploymentArchive)var2.next();
            ArrayList embeddedBdas = new ArrayList();
            BasicBeanDeploymentArchive moduleBda = libJarRootBda.getModuleBda();
            embeddedBdas.addAll(moduleBda.getBeanDeploymentArchives());
            embeddedBdas.remove(libJarRootBda);
            allRarEmbeddedBdas.put(libJarRootBda, embeddedBdas);
         }
      }

      Iterator var9;
      BeanDeploymentArchive oneRarEmbeddedBda;
      BasicBeanDeploymentArchive libJarModuleBda;
      ArrayList warEmbeddedBdas;
      if (this.warRootBdas != null) {
         for(var2 = this.warRootBdas.iterator(); var2.hasNext(); this.addRarBdasAsAccessible(libJarRootBda, warEmbeddedBdas, allRarEmbeddedBdas)) {
            libJarRootBda = (RootBeanDeploymentArchive)var2.next();
            libJarModuleBda = libJarRootBda.getModuleBda();
            warEmbeddedBdas = new ArrayList();
            warEmbeddedBdas.addAll(libJarModuleBda.getBeanDeploymentArchives());
            warEmbeddedBdas.remove(libJarRootBda);
            Iterator var6;
            RootBeanDeploymentArchive libJarRootBda;
            BasicBeanDeploymentArchive libJarModuleBda;
            if (this.ejbRootBdas != null) {
               var6 = this.ejbRootBdas.iterator();

               while(var6.hasNext()) {
                  libJarRootBda = (RootBeanDeploymentArchive)var6.next();
                  libJarModuleBda = libJarRootBda.getModuleBda();
                  libJarRootBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarRootBda.addAccessibleBeanArchive(libJarModuleBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarModuleBda);
                  var9 = warEmbeddedBdas.iterator();

                  while(var9.hasNext()) {
                     oneRarEmbeddedBda = (BeanDeploymentArchive)var9.next();
                     oneRarEmbeddedBda.getBeanDeploymentArchives().add(libJarRootBda);
                     oneRarEmbeddedBda.getBeanDeploymentArchives().add(libJarModuleBda);
                  }
               }
            }

            if (this.libJarRootBdas != null) {
               var6 = this.libJarRootBdas.iterator();

               while(var6.hasNext()) {
                  libJarRootBda = (RootBeanDeploymentArchive)var6.next();
                  libJarModuleBda = libJarRootBda.getModuleBda();
                  libJarRootBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarRootBda.addAccessibleBeanArchive(libJarModuleBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarModuleBda);
                  var9 = warEmbeddedBdas.iterator();

                  while(var9.hasNext()) {
                     oneRarEmbeddedBda = (BeanDeploymentArchive)var9.next();
                     oneRarEmbeddedBda.getBeanDeploymentArchives().add(libJarRootBda);
                     oneRarEmbeddedBda.getBeanDeploymentArchives().add(libJarModuleBda);
                  }
               }
            }
         }
      }

      Iterator var13;
      RootBeanDeploymentArchive libJarRootBda;
      BasicBeanDeploymentArchive libJarModuleBda;
      if (this.ejbRootBdas != null) {
         for(var2 = this.ejbRootBdas.iterator(); var2.hasNext(); this.addRarBdasAsAccessible(libJarRootBda, (Collection)null, allRarEmbeddedBdas)) {
            libJarRootBda = (RootBeanDeploymentArchive)var2.next();
            libJarModuleBda = libJarRootBda.getModuleBda();
            var13 = this.ejbRootBdas.iterator();

            while(var13.hasNext()) {
               libJarRootBda = (RootBeanDeploymentArchive)var13.next();
               libJarModuleBda = libJarRootBda.getModuleBda();
               if (!libJarModuleBda.getId().equals(libJarModuleBda.getId())) {
                  libJarRootBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarRootBda.addAccessibleBeanArchive(libJarModuleBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarModuleBda);
               }
            }

            if (this.libJarRootBdas != null) {
               var13 = this.libJarRootBdas.iterator();

               while(var13.hasNext()) {
                  libJarRootBda = (RootBeanDeploymentArchive)var13.next();
                  libJarModuleBda = libJarRootBda.getModuleBda();
                  libJarRootBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarRootBda.addAccessibleBeanArchive(libJarModuleBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarModuleBda);
               }
            }
         }
      }

      if (this.rarRootBdas != null) {
         var2 = this.rarRootBdas.iterator();

         label91:
         while(true) {
            do {
               if (!var2.hasNext()) {
                  break label91;
               }

               libJarRootBda = (RootBeanDeploymentArchive)var2.next();
               libJarModuleBda = libJarRootBda.getModuleBda();
            } while(this.libJarRootBdas == null);

            var13 = this.libJarRootBdas.iterator();

            while(var13.hasNext()) {
               libJarRootBda = (RootBeanDeploymentArchive)var13.next();
               libJarModuleBda = libJarRootBda.getModuleBda();
               libJarRootBda.addAccessibleBeanArchive(libJarRootBda);
               libJarRootBda.addAccessibleBeanArchive(libJarModuleBda);
               libJarModuleBda.addAccessibleBeanArchive(libJarRootBda);
               libJarModuleBda.addAccessibleBeanArchive(libJarModuleBda);
               List rarEmbeddedBdas = (List)allRarEmbeddedBdas.get(libJarRootBda);
               var9 = rarEmbeddedBdas.iterator();

               while(var9.hasNext()) {
                  oneRarEmbeddedBda = (BeanDeploymentArchive)var9.next();
                  oneRarEmbeddedBda.getBeanDeploymentArchives().add(libJarRootBda);
                  oneRarEmbeddedBda.getBeanDeploymentArchives().add(libJarModuleBda);
               }
            }
         }
      }

      if (this.libJarRootBdas != null) {
         var2 = this.libJarRootBdas.iterator();

         while(var2.hasNext()) {
            libJarRootBda = (RootBeanDeploymentArchive)var2.next();
            libJarModuleBda = libJarRootBda.getModuleBda();
            var13 = this.libJarRootBdas.iterator();

            while(var13.hasNext()) {
               libJarRootBda = (RootBeanDeploymentArchive)var13.next();
               libJarModuleBda = libJarRootBda.getModuleBda();
               if (!libJarModuleBda.getId().equals(libJarModuleBda.getId())) {
                  libJarRootBda.addAccessibleBeanArchive(libJarRootBda);
                  libJarRootBda.addAccessibleBeanArchive(libJarModuleBda);
                  libJarModuleBda.addAccessibleBeanArchive(libJarModuleBda);
               }
            }
         }
      }

   }

   private void addRarBdasAsAccessible(RootBeanDeploymentArchive otherRootBda, Collection otherEmbeddedBdas, HashMap allRarEmbeddedBdas) {
      if (this.rarRootBdas != null) {
         BasicBeanDeploymentArchive otherModuleBda = otherRootBda.getModuleBda();
         Iterator var5 = this.rarRootBdas.iterator();

         while(true) {
            RootBeanDeploymentArchive rarRootBda;
            BasicBeanDeploymentArchive rarModuleBda;
            List rarEmbeddedBdas;
            Iterator var9;
            BeanDeploymentArchive oneBda;
            do {
               if (!var5.hasNext()) {
                  return;
               }

               rarRootBda = (RootBeanDeploymentArchive)var5.next();
               rarModuleBda = rarRootBda.getModuleBda();
               otherRootBda.addAccessibleBeanArchive(rarRootBda);
               otherRootBda.addAccessibleBeanArchive(rarModuleBda);
               otherModuleBda.addAccessibleBeanArchive(rarRootBda);
               otherModuleBda.addAccessibleBeanArchive(rarModuleBda);
               rarEmbeddedBdas = (List)allRarEmbeddedBdas.get(rarRootBda);
               var9 = rarEmbeddedBdas.iterator();

               while(var9.hasNext()) {
                  oneBda = (BeanDeploymentArchive)var9.next();
                  otherRootBda.getBeanDeploymentArchives().add(oneBda);
                  otherModuleBda.getBeanDeploymentArchives().add(oneBda);
               }
            } while(otherEmbeddedBdas == null);

            var9 = otherEmbeddedBdas.iterator();

            while(var9.hasNext()) {
               oneBda = (BeanDeploymentArchive)var9.next();
               oneBda.getBeanDeploymentArchives().add(rarRootBda);
               oneBda.getBeanDeploymentArchives().add(rarModuleBda);
               Iterator var11 = rarEmbeddedBdas.iterator();

               while(var11.hasNext()) {
                  BeanDeploymentArchive oneRarEmbeddedBda = (BeanDeploymentArchive)var11.next();
                  oneBda.getBeanDeploymentArchives().add(oneRarEmbeddedBda);
               }
            }
         }
      }
   }

   private BasicBeanDeploymentArchive createBeanDeploymentArchive(InjectionArchive injectionArchive, EEModuleDescriptor eeModuleDescriptor) {
      BasicBeanDeploymentArchive bda = new BasicBeanDeploymentArchive(this.m_weldBootstrap, injectionArchive, this.m_integrationService, eeModuleDescriptor);
      this.m_beanDeploymentArchives.add(bda);
      this.m_mapOfBeanDeploymentArchives.put(injectionArchive.getArchiveName(), bda);
      return bda;
   }

   private static boolean isEJBArchive(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveType() == InjectionArchiveType.EJB_JAR;
   }

   private static boolean isWarArchive(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveType() == InjectionArchiveType.WAR;
   }

   private static boolean isRarArchive(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveType() == InjectionArchiveType.RAR;
   }

   private static boolean isEarLibraryJarArchive(InjectionArchive injectionArchive) {
      return injectionArchive.getArchiveType() == InjectionArchiveType.JAR;
   }

   public BeanDeploymentArchive getBeanDeploymentArchive(Class beanClass) {
      if (beanClass == null) {
         return null;
      } else {
         BeanDeploymentArchive nonRootBda = this.getBdaFromNonRootBdas(beanClass);
         if (nonRootBda != null) {
            return nonRootBda;
         } else {
            ClassLoader classLoader = beanClass.getClassLoader();
            RootBeanDeploymentArchive rootBda = this.findRootBda(classLoader, this.ejbRootBdas);
            if (rootBda == null) {
               rootBda = this.findRootBda(classLoader, this.warRootBdas);
               if (rootBda == null) {
                  rootBda = this.findRootBda(classLoader, this.libJarRootBdas);
                  if (rootBda == null) {
                     rootBda = this.findRootBda(classLoader, this.rarRootBdas);
                  }
               }
            }

            return rootBda;
         }
      }
   }

   private RootBeanDeploymentArchive findRootBda(ClassLoader classLoader, List rootBdas) {
      if (rootBdas != null && classLoader != null) {
         Iterator var3 = rootBdas.iterator();

         RootBeanDeploymentArchive oneRootBda;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            oneRootBda = (RootBeanDeploymentArchive)var3.next();
         } while(!classLoader.equals(oneRootBda.getModuleClassLoaderForBDA()));

         return oneRootBda;
      } else {
         return null;
      }
   }

   private class ExtensionComparator implements Comparator {
      private ExtensionComparator() {
      }

      public int compare(Metadata metadata_1, Metadata metadata_2) {
         if (metadata_1 instanceof FileMetadata && metadata_2 instanceof FileMetadata) {
            FileMetadata fileMetadata_1 = (FileMetadata)metadata_1;
            FileMetadata fileMetadata_2 = (FileMetadata)metadata_2;
            if (fileMetadata_1.getFile().toString().equals(fileMetadata_2.getFile().toString())) {
               return fileMetadata_1.getValue().getClass().getName().compareTo(fileMetadata_2.getValue().getClass().getName());
            }
         }

         return metadata_1.toString().compareTo(metadata_2.toString());
      }

      // $FF: synthetic method
      ExtensionComparator(Object x1) {
         this();
      }
   }
}
