package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.config.AuditListener;
import com.oracle.weblogic.lifecycle.config.DatabaseConfigHandler;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.LifecycleConfigUtil;
import com.oracle.weblogic.lifecycle.config.LifecycleDocument;
import com.oracle.weblogic.lifecycle.config.VetoableLifecycleConfigChangeListener;
import com.oracle.weblogic.lifecycle.config.customizers.AuditableCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.EditSessionCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.EditSessionsCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.EnvironmentCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.EnvironmentsCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.LifecycleConfigDefaulter;
import com.oracle.weblogic.lifecycle.config.customizers.PDBCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PartitionCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PartitionRefCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PluginCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PluginsCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.PropertyBagBeanCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.ResourcesCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.RuntimeCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.RuntimesCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.ServiceCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.TenantCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.TenantsCustomizer;
import com.oracle.weblogic.lifecycle.config.customizers.UncommittedEnvironmentCustomizer;
import com.oracle.weblogic.lifecycle.config.validators.ReferenceValidator;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.channels.FileLock;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.configuration.api.ConfigurationUtilities;
import org.glassfish.hk2.configuration.hub.api.BeanDatabase;
import org.glassfish.hk2.configuration.hub.api.BeanDatabaseUpdateListener;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;
import org.glassfish.hk2.xml.api.XmlServiceUtilities;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

@Service
public class LifecycleConfigFactory implements PostConstruct {
   private static final AuditListener AUDIT_LISTENER = new AuditListener();
   public static final String CONFIG_FILE_NAME = "lifecycle-config.xml";
   private static final String NAME = "LifecycleConfig";
   private ServiceLocator configLocator;
   private Object kernel_id = null;
   private static final String DB = "database";
   private PersistenceType persistenceType;
   private static Boolean isServer = false;
   private XmlRootHandle lifecycleConfigRootHandle;
   DatabaseConfigHandler databaseConfigHandler;
   @Inject
   private ServiceLocator systemServiceLocator;

   public LifecycleConfigFactory() {
      this.persistenceType = LifecycleConfigFactory.PersistenceType.XML;
      this.databaseConfigHandler = null;
   }

   public LifecycleConfig getLifecycleConfig() {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecycleConfigFactory.getLifecycleConfig ");
      }

      LifecycleConfig lifecycleConfig = (LifecycleConfig)this.configLocator.getService(LifecycleConfig.class, new Annotation[0]);
      if (this.persistenceType.equals(LifecycleConfigFactory.PersistenceType.DATABASE)) {
         if (this.databaseConfigHandler == null) {
            if (this.isServer()) {
               throw new RuntimeException(LCMMessageTextFormatter.getInstance().databaseHandlerUnavailable());
            }

            throw new RuntimeException("Database Config Handler is not initialized");
         }

         try {
            if (!this.databaseConfigHandler.isDataUptodate()) {
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("Lifecycle configuration data is out of sync. Reloading lifecycle configuration.");
               }

               this.clearConfigBeans();
               LifecycleDocument lifecycleDocument = LifecycleDocument.getInstance(this.configLocator, this.databaseConfigHandler);
               XmlService xmlService = (XmlService)this.configLocator.getService(XmlService.class, new Annotation[0]);
               this.lifecycleConfigRootHandle = xmlService.unmarshal(this.databaseConfigHandler.load(), LifecycleConfig.class, true, true);
               this.initializeRoot(this.lifecycleConfigRootHandle, this.configLocator, lifecycleDocument, (String)null);
            }
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

      return lifecycleConfig;
   }

   public void postConstruct() {
      this.getPersistenceType();
      this.loadLifecycleConfig(false);
   }

   public synchronized void reloadLifecycleConfig() {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecycleConfigFactory.reloadLifecycleConfig ");
      }

      this.loadLifecycleConfig(true);
   }

   private static void setupCustomizers(ServiceLocator locator) {
      if (locator.getService(LifecycleConfigDefaulter.class, new Annotation[0]) == null) {
         XmlServiceUtilities.enableDomXmlService(locator);
         ConfigurationUtilities.enableConfigurationSystem(locator);
         ServiceLocatorUtilities.addClasses(locator, true, new Class[]{LifecycleConfigDefaulter.class, RuntimesCustomizer.class, RuntimeCustomizer.class, EnvironmentsCustomizer.class, EnvironmentCustomizer.class, PartitionCustomizer.class, AuditableCustomizer.class, PartitionRefCustomizer.class, TenantsCustomizer.class, TenantCustomizer.class, ServiceCustomizer.class, PDBCustomizer.class, ResourcesCustomizer.class, PluginsCustomizer.class, PluginCustomizer.class, EditSessionsCustomizer.class, EditSessionCustomizer.class, UncommittedEnvironmentCustomizer.class, PropertyBagBeanCustomizer.class});
      }
   }

   private boolean getConfigLocator() {
      this.configLocator = ServiceLocatorFactory.getInstance().find("LifecycleConfig");
      boolean retVal = false;
      if (this.configLocator == null) {
         this.configLocator = ServiceLocatorFactory.getInstance().create("LifecycleConfig", this.systemServiceLocator);
         ReferenceValidator.setLocator(this.configLocator);
         retVal = true;
      }

      setupCustomizers(this.configLocator);
      return retVal;
   }

   private static void printConstraintViolations(String location, ConstraintViolationException cve) {
      StringBuffer sb = new StringBuffer("\n");
      int lcv = 1;

      for(Iterator var4 = cve.getConstraintViolations().iterator(); var4.hasNext(); ++lcv) {
         ConstraintViolation violation = (ConstraintViolation)var4.next();
         sb.append(lcv + ". " + violation.getMessage() + "," + violation.getInvalidValue() + "," + violation.getPropertyPath() + "\n");
      }

      LCMLogger.logConfigFileValidationException(location, sb.toString());
   }

   private static RuntimeException unwrapAndPrintConstraintViolations(String location, MultiException me) {
      ConstraintViolationException foundConstraintViolation = null;
      Iterator var3 = me.getErrors().iterator();

      while(var3.hasNext()) {
         Throwable th = (Throwable)var3.next();

         for(Throwable cause = th; cause != null; cause = cause.getCause()) {
            if (cause instanceof ConstraintViolationException) {
               foundConstraintViolation = (ConstraintViolationException)cause;
               break;
            }
         }

         if (foundConstraintViolation != null) {
            break;
         }
      }

      if (foundConstraintViolation == null) {
         return me;
      } else {
         printConstraintViolations(location, foundConstraintViolation);
         return foundConstraintViolation;
      }
   }

   private void initializeRoot(XmlRootHandle root, ServiceLocator locator, LifecycleDocument document, String location) {
      document.setInitialized();
      TransactionCommitHandler tch = (TransactionCommitHandler)locator.getService(TransactionCommitHandler.class, new Annotation[0]);
      if (tch == null) {
         tch = new TransactionCommitHandler(locator, document, root);
         ServiceLocatorUtilities.addOneConstant(locator, tch);
      } else {
         tch.setDocumentAndHandle(document, root);
      }

      root.addChangeListener(new VetoableChangeListener[]{new VetoableLifecycleConfigChangeListener(locator)});
      root.addChangeListener(new VetoableChangeListener[]{AUDIT_LISTENER});
      root.addChangeListener(new VetoableChangeListener[]{tch});

      try {
         root.startValidating();
      } catch (MultiException var7) {
         throw unwrapAndPrintConstraintViolations(location, var7);
      } catch (ConstraintViolationException var8) {
         printConstraintViolations(location, var8);
         throw var8;
      }
   }

   private void clearConfigBeans() {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecycleConfigFactory.clearConfigBeans ");
      }

      Filter filter = BuilderHelper.createContractFilter(XmlHk2ConfigurationBean.class.getName());
      ServiceLocatorUtilities.removeFilter(this.configLocator, filter, true);
   }

   private synchronized void loadLifecycleConfig(boolean forceLoad) {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecycleConfigFactory.loadLifecycleConfig forceLoad=" + forceLoad);
      }

      try {
         boolean doLoad = false;
         switch (this.persistenceType) {
            case XML:
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("LifecycleConfigFactory.loadLifecycleConfig persistenceType= XML");
               }

               File lifecycleConfigFile = this.getConfigFile();
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("LifecyleConfigFactory.loadLifecycleConfig lifecycleConfigFile is " + lifecycleConfigFile);
               }

               URL url = null;
               if (lifecycleConfigFile != null) {
                  url = lifecycleConfigFile.toURI().toURL();
               }

               if (url == null) {
                  if (this.isServer()) {
                     throw new LifecycleException(LCMMessageTextFormatter.getInstance().lcmConfigFileNotFound());
                  }

                  throw new LifecycleException("Lifecycle config file location could not be determined");
               }

               doLoad = this.getConfigLocator();
               LifecycleDocument lifecycleDocument = LifecycleDocument.getInstance(this.configLocator, url);
               if (!lifecycleDocument.isInitialized()) {
                  doLoad = true;
               }

               if (!doLoad && !forceLoad) {
                  break;
               }

               for(long remainingTime = LifecycleConfigUtil.getFileLockTimeout(); lifecycleConfigFile.length() == 0L && remainingTime > 0L; remainingTime -= 500L) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleConfigFactory: waiting, length is zero remaining time = " + remainingTime + " " + lifecycleDocument);
                  }

                  Thread.sleep(500L);
               }

               FileLock lock = null;

               try {
                  lock = lifecycleDocument.getLock();
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleConfigFactory: GotLock " + lifecycleDocument);
                  }

                  if (lock == null || !lifecycleConfigFile.exists() || lifecycleConfigFile.length() <= 0L) {
                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug("LifecycleConfigFactory: configuration not loaded : " + lifecycleDocument);
                     }

                     if (LifecycleUtils.isAppServer()) {
                        throw new LifecycleException(LCMMessageTextFormatter.getInstance().cannotLoadConfigFile(lifecycleConfigFile.getAbsolutePath(), lifecycleConfigFile.length()));
                     }

                     throw new LifecycleException("Cannot load lifecycle configuration file " + lifecycleConfigFile + " size : " + lifecycleConfigFile.length() + " Ensure that the file exists and can be read.");
                  }

                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleConfigFactory: loading configuration : " + lifecycleDocument.toString());
                  }

                  this.clearConfigBeans();
                  XmlService xmlService = (XmlService)this.configLocator.getService(XmlService.class, new Annotation[0]);
                  this.lifecycleConfigRootHandle = xmlService.unmarshal(url.toURI(), LifecycleConfig.class);
                  this.initializeRoot(this.lifecycleConfigRootHandle, this.configLocator, lifecycleDocument, url.toExternalForm());
               } catch (IOException var20) {
                  throw new LifecycleException(var20);
               } finally {
                  if (lock != null) {
                     try {
                        lock.release();
                        lock.channel().close();
                        lock.close();
                     } catch (IOException var18) {
                        if (LifecycleUtils.isDebugEnabled()) {
                           LifecycleUtils.debug(var18.getMessage(), var18);
                        }
                     }
                  }

               }

               LifecycleConfig lc = (LifecycleConfig)this.configLocator.getService(LifecycleConfig.class, new Annotation[0]);
               if ("database".equals(lc.getPersistenceType())) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("Switching to database persistence for lifecycle config");
                  }

                  this.persistenceType = LifecycleConfigFactory.PersistenceType.DATABASE;
                  this.databaseConfigHandler = new DatabaseConfigHandler(lc);
                  lifecycleDocument = LifecycleDocument.getInstance(this.configLocator, this.databaseConfigHandler);
                  this.clearConfigBeans();
                  XmlService xmlService = (XmlService)this.configLocator.getService(XmlService.class, new Annotation[0]);
                  this.lifecycleConfigRootHandle = xmlService.unmarshal(this.databaseConfigHandler.load(), LifecycleConfig.class, true, true);
                  this.initializeRoot(this.lifecycleConfigRootHandle, this.configLocator, lifecycleDocument, (String)null);
               }
               break;
            case DATABASE:
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("LifecycleConfigFactory.loadLifecycleConfig persistence type = DATABASE");
               }

               doLoad = this.getConfigLocator();
               if (this.databaseConfigHandler == null) {
                  this.databaseConfigHandler = new DatabaseConfigHandler(this.getDataSource());
               }

               LifecycleDocument lifecycleDocument = LifecycleDocument.getInstance(this.configLocator, this.databaseConfigHandler);
               if (!lifecycleDocument.isInitialized()) {
                  this.clearConfigBeans();
                  doLoad = true;
               }

               if (doLoad || forceLoad) {
                  XmlService xmlService = (XmlService)this.configLocator.getService(XmlService.class, new Annotation[0]);
                  XmlRootHandle root = xmlService.unmarshal(this.databaseConfigHandler.load(), LifecycleConfig.class, true, true);
                  this.initializeRoot(root, this.configLocator, lifecycleDocument, (String)null);
               }
               break;
            default:
               if (this.isServer()) {
                  throw new RuntimeException(LCMMessageTextFormatter.getInstance().invalidPersistenceType());
               }

               throw new RuntimeException("Invalid persistence type specified");
         }

         Collection listeners = this.systemServiceLocator.getAllServices(LifecycleConfigListener.class, new Annotation[0]);
         if (listeners != null) {
            Iterator var28 = listeners.iterator();

            while(var28.hasNext()) {
               LifecycleConfigListener listener = (LifecycleConfigListener)var28.next();

               try {
                  listener.configFileLoaded((LifecycleConfig)this.configLocator.getService(LifecycleConfig.class, new Annotation[0]));
               } catch (Exception var19) {
                  String message = "LifecycleConfigFactory.loadLifecycleConfig lifecycle config listener implementation has thrown exception";
                  if (this.isServer()) {
                     LCMLogger.logException(message, var19);
                  } else {
                     LifecycleUtils.debug(message, var19);
                  }
               }
            }
         }

      } catch (Exception var22) {
         String message = "LifecycleConfigFactory.loadLifecycleConfig: Error loading lifecycle configuration : " + var22.getMessage();
         if (this.isServer()) {
            LCMLogger.logException(message, var22);
            throw new RuntimeException(LCMMessageTextFormatter.getInstance().errorLoadingLCMConfig(var22.getMessage()), var22);
         } else {
            LifecycleUtils.debug(message);
            throw new RuntimeException(message, var22);
         }
      }
   }

   private void getPersistenceType() {
      String persistenceTypeFromConfig;
      try {
         if (ManagementService.isRuntimeAccessInitialized()) {
            this.kernel_id = AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            DomainMBean mbean = ManagementService.getRuntimeAccess((AuthenticatedSubject)this.kernel_id).getDomain();
            persistenceTypeFromConfig = mbean.getLifecycleManagerConfig().getPersistenceType();
            if ("database".equals(persistenceTypeFromConfig)) {
               this.persistenceType = LifecycleConfigFactory.PersistenceType.DATABASE;
            }
         }
      } catch (NoClassDefFoundError var3) {
         persistenceTypeFromConfig = "Error getting persistence type" + var3.getMessage();
         if (this.isServer()) {
            LifecycleUtils.debug(persistenceTypeFromConfig);
         }
      }

   }

   private String getDataSource() throws Exception {
      if (ManagementService.isRuntimeAccessInitialized()) {
         DomainMBean mbean = ManagementService.getRuntimeAccess((AuthenticatedSubject)this.kernel_id).getDomain();
         return mbean.getLifecycleManagerConfig().getDataSourceName();
      } else {
         return null;
      }
   }

   private boolean isServer() {
      return isServer;
   }

   File getConfigFile() throws IOException {
      String CONFIG_FILE_TEMPLATE_NAME = "lifecycle-config-template.xml";
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile: getConfigFile");
      }

      File configFile;
      File configFile;
      try {
         Boolean isServer = this.isServer();
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile: isServer=" + isServer);
         }

         if (isServer) {
            configFile = new File(DomainDir.getConfigDir());
            configFile = new File(configFile, "lifecycle-config.xml");
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile: configFile=" + configFile.getAbsolutePath());
            }

            if (!configFile.exists()) {
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:File Not Found.  Creating new file from lifecycle-config-template.xml");
               }

               InputStream is = LifecycleConfigFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE_TEMPLATE_NAME);
               FileUtils.writeToFile(is, configFile);
            }

            return configFile;
         }
      } catch (SecurityException | NoClassDefFoundError | IllegalArgumentException var7) {
         if (this.isServer()) {
            LifecycleUtils.debug("LCF:Caught exception trying to find lifeycle-config.xml under domain config directory. This is fine for SE case or unit tests.", var7);
         }
      }

      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile::Running in SE or unit tests");
      }

      String seConfigDir = System.getProperty("lifecycle.configDir");
      if (seConfigDir != null && !seConfigDir.isEmpty()) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:SE Configuration directory pointed by lifecycle.configDir=" + seConfigDir);
         }

         configFile = new File(seConfigDir, "lifecycle-config.xml");
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:Bootstapping SE Configuration from lifecycle.configDir ");
         }

         if (configFile.exists()) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile::SE Configuration File Found in location pointed by lifecycle.configDir");
            }

            return configFile;
         }

         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:SE Configuration File Not Found in location pointed by lifecycle.configDir");
         }
      }

      String seConfigFile = System.getProperty("lifecycle.configFile");
      if (seConfigFile != null && !seConfigFile.isEmpty()) {
         configFile = new File(seConfigFile);
         if (configFile.exists()) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:Bootstapping Configuration from lifecycle.configFile=" + seConfigFile);
            }

            return configFile;
         }

         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:Configuration File Not Found in location pointed by lifecycle.configFile=" + seConfigFile);
         }
      }

      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile::Bootstapping SE Configuration from current working directory");
      }

      String workingDir = System.getProperty("user.dir");
      File configFile = new File(workingDir, "lifecycle-config.xml");
      if (configFile.exists()) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:Config File Found in current working directory:= " + workingDir);
         }
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecyleConfigFactory.getConfigFile:Config File Not Found.  Creating new file from lifecycle-config-template.xml");
         }

         InputStream is = LifecycleConfigFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE_TEMPLATE_NAME);
         FileUtils.writeToFile(is, configFile);
      }

      return configFile;
   }

   public String toString() {
      return "LifecycleConfigFactory(" + System.identityHashCode(this) + ")";
   }

   static {
      try {
         Class myClass = Class.forName("weblogic.kernel.KernelStatus");
         Method myMethod = myClass.getMethod("isServer");
         Object returnObject = myMethod.invoke((Object)null);
         isServer = (Boolean)returnObject;
      } catch (Exception var3) {
      }

   }

   @Contract
   private static class TransactionCommitHandler implements BeanDatabaseUpdateListener, VetoableChangeListener {
      private LifecycleDocument lifecycleDocument;
      private final ServiceLocator configLocator;
      private XmlRootHandle handle;
      private List events;

      private TransactionCommitHandler(ServiceLocator configLocator, LifecycleDocument lifecycleDocument, XmlRootHandle handle) {
         this.configLocator = configLocator;
         this.lifecycleDocument = lifecycleDocument;
         this.handle = handle;
      }

      private synchronized void setDocumentAndHandle(LifecycleDocument document, XmlRootHandle handle) {
         this.lifecycleDocument = document;
         this.handle = handle;
         this.events = null;
      }

      public synchronized void commitDatabaseChange(BeanDatabase arg0, BeanDatabase arg1, Object arg2, List changes) {
         List localEvents = this.events;
         this.events = null;
         if (this.lifecycleDocument != null && this.configLocator != null && localEvents != null) {
            this.lifecycleDocument.doTransactionCommitted(this.configLocator, localEvents, this.handle);
         }
      }

      public void prepareDatabaseChange(BeanDatabase arg0, BeanDatabase arg1, Object arg2, List changes) {
      }

      public synchronized void rollbackDatabaseChange(BeanDatabase arg0, BeanDatabase arg1, Object arg2, List arg3) {
         this.events = null;
      }

      public synchronized void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
         if (this.events == null) {
            this.events = new LinkedList();
         }

         this.events.add(evt);
      }

      // $FF: synthetic method
      TransactionCommitHandler(ServiceLocator x0, LifecycleDocument x1, XmlRootHandle x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static enum PersistenceType {
      XML,
      DATABASE;
   }
}
