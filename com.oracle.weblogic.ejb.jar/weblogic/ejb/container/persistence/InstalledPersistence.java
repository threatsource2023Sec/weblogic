package weblogic.ejb.container.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.Home;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.Deployer;
import weblogic.ejb.container.cmp.rdbms.RDBMSDeployment;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.cmp.rdbms.codegen.RDBMSCodeGenerator;
import weblogic.ejb.container.cmp11.rdbms.PersistenceManagerImpl;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.ProcessorFactoryException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public class InstalledPersistence {
   private static final boolean verbose = System.getProperty("weblogic.ejb.container.persistence.verbose") != null;
   private static String PERSISTENCE_INSTALL_FILE = "persistence.install";
   private String installationLocation = null;
   private Set installedTypes = null;
   private boolean initialized = false;

   public synchronized Set getInstalledTypes() throws PersistenceException {
      if (!this.initialized) {
         this.initialize();
      }

      return this.installedTypes;
   }

   public synchronized PersistenceType getInstalledType(String identifier, String version) throws PersistenceException {
      if (!this.initialized) {
         this.initialize();
      }

      Iterator var3 = this.installedTypes.iterator();

      PersistenceType type;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         type = (PersistenceType)var3.next();
      } while(!type.getIdentifier().equals(identifier) || !type.getVersion().equals(version));

      return type;
   }

   private synchronized void initialize() throws PersistenceException {
      this.setInstallationLocation();
      File installFile = new File(this.installationLocation, PERSISTENCE_INSTALL_FILE);
      Loggable l;
      if (!installFile.exists()) {
         l = EJBLogger.logInstalledPersistFileNotExistLoggable(installFile.getAbsolutePath());
         throw new PersistenceException(l.getMessageText());
      } else if (!installFile.canRead()) {
         l = EJBLogger.logInstalledPersistFileNotReadableLoggable(installFile.getAbsolutePath());
         throw new PersistenceException(l.getMessageText());
      } else {
         BufferedReader reader = null;

         try {
            try {
               reader = new BufferedReader(new FileReader(installFile));
            } catch (FileNotFoundException var46) {
               Loggable l = EJBLogger.logInstalledPersistFileCouldNotOpenLoggable(installFile.getAbsolutePath());
               throw new PersistenceException(l.getMessageText());
            }

            try {
               if (verbose) {
                  if (!reader.ready()) {
                     Debug.say("Found no CMP descriptors in '" + installFile.getAbsolutePath() + "'.");
                  } else {
                     Debug.say("Loading CMP Installation from '" + installFile + "'.");
                  }
               }

               Set resourceNames = new HashSet();

               while(reader.ready()) {
                  String resourceName = reader.readLine();
                  if (verbose) {
                     Debug.say("found resource name: " + resourceName);
                  }

                  if (resourceName != null && !resourceName.trim().equals("")) {
                     resourceNames.add(resourceName);
                  }
               }

               resourceNames.remove("WebLogic_CMP_RDBMS.xml");
               this.installedTypes = new HashSet();
               this.addWebLogicCMPRDBMS(this.installedTypes);
               if (!resourceNames.isEmpty()) {
                  ProcessorFactory procFac = new ProcessorFactory();
                  Iterator namesIter = resourceNames.iterator();

                  while(namesIter.hasNext()) {
                     String resourceName = (String)namesIter.next();
                     InputStream xmlStream = null;
                     xmlStream = this.getClass().getResourceAsStream(resourceName);
                     if (xmlStream == null) {
                        xmlStream = this.getClass().getResourceAsStream('/' + resourceName);
                     }

                     if (verbose) {
                        if (xmlStream == null) {
                           Debug.say("xmlStream for resource '" + resourceName + "' is null.");
                        } else {
                           Debug.say("xmlStream for resource '" + resourceName + "' is not null.");
                        }
                     }

                     if (xmlStream == null) {
                        Loggable l = EJBLogger.logInstalledPersistErrorLoadingResourceLoggable(resourceName);
                        throw new PersistenceException(l.getMessageText());
                     }

                     BufferedInputStream xmlInputStream = null;

                     try {
                        Loggable l;
                        try {
                           xmlInputStream = new BufferedInputStream(xmlStream);
                           PersistenceVendorProcessor processor = (PersistenceVendorProcessor)procFac.getProcessor(xmlInputStream, true, PersistenceUtils.validPersistencePublicIds);
                           if (processor == null) {
                              l = EJBLogger.logInstalledPersistNoXMLProcessorLoggable(resourceName);
                              throw new PersistenceException(l.getMessageText());
                           }

                           processor.process((InputStream)xmlInputStream);
                           this.installedTypes.addAll(processor.getInstalledTypes());
                        } catch (XMLParsingException var42) {
                           throw new PersistenceException(var42.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(var42));
                        } catch (XMLProcessingException var43) {
                           throw new PersistenceException(var43.getMessage());
                        } catch (ProcessorFactoryException var44) {
                           l = EJBLogger.logInstalledPersistNoXMLProcessorLoggable(resourceName);
                           throw new PersistenceException(l.getMessageText());
                        }
                     } finally {
                        try {
                           if (xmlInputStream != null) {
                              xmlInputStream.close();
                           }
                        } catch (IOException var41) {
                        }

                     }
                  }
               }
            } catch (IOException var47) {
               throw new PersistenceException(StackTraceUtilsClient.throwable2StackTrace(var47));
            } catch (PersistenceException var48) {
               throw var48;
            } catch (Exception var49) {
               throw new AssertionError("Error while reading CMP Installation file '" + installFile.getAbsolutePath() + "': " + StackTraceUtilsClient.throwable2StackTrace(var49));
            }
         } finally {
            if (reader != null) {
               try {
                  reader.close();
               } catch (IOException var40) {
               }
            }

         }

         if (verbose) {
            Debug.say("Installed persistence types:");
            Iterator iter = this.installedTypes.iterator();

            while(iter.hasNext()) {
               Debug.say(iter.next().toString());
            }
         }

         this.initialized = true;
      }
   }

   private void addWebLogicCMPRDBMS(Set installedTypes) {
      PersistenceVendor pv = new PersistenceVendor();
      pv.setName(",2013, Oracle and/or its affiliates. All rights reserved.");
      PersistenceType cmp2x60 = new PersistenceType();
      cmp2x60.setCodeGeneratorClassName(RDBMSCodeGenerator.class.getName());
      cmp2x60.setCmpDeployerClassName(Deployer.class.getName());
      cmp2x60.setJarDeploymentClassName(RDBMSDeployment.class.getName());
      cmp2x60.setIdentifier("WebLogic_CMP_RDBMS");
      cmp2x60.setPersistenceManagerClassName(RDBMSPersistenceManager.class.getName());
      cmp2x60.setVendor(pv);
      cmp2x60.setVersion("6.0");
      cmp2x60.setCmpVersion("2.x");
      cmp2x60.setWeblogicVersion("7");
      installedTypes.add(cmp2x60);
      PersistenceType cmp2x70 = new PersistenceType();
      cmp2x70.setCodeGeneratorClassName(RDBMSCodeGenerator.class.getName());
      cmp2x70.setCmpDeployerClassName(Deployer.class.getName());
      cmp2x70.setJarDeploymentClassName(RDBMSDeployment.class.getName());
      cmp2x70.setIdentifier("WebLogic_CMP_RDBMS");
      cmp2x70.setPersistenceManagerClassName(RDBMSPersistenceManager.class.getName());
      cmp2x70.setVendor(pv);
      cmp2x70.setVersion("7.0");
      cmp2x70.setCmpVersion("2.x");
      cmp2x70.setWeblogicVersion("7");
      installedTypes.add(cmp2x70);
      PersistenceType cmp1x510 = new PersistenceType();
      cmp1x510.setCodeGeneratorClassName(weblogic.ejb.container.cmp11.rdbms.codegen.RDBMSCodeGenerator.class.getName());
      cmp1x510.setCmpDeployerClassName(weblogic.ejb.container.cmp11.rdbms.Deployer.class.getName());
      cmp1x510.setJarDeploymentClassName(weblogic.ejb.container.cmp11.rdbms.RDBMSDeployment.class.getName());
      cmp1x510.setIdentifier("WebLogic_CMP_RDBMS");
      cmp1x510.setPersistenceManagerClassName(PersistenceManagerImpl.class.getName());
      cmp1x510.setVendor(pv);
      cmp1x510.setVersion("5.1.0");
      cmp1x510.setCmpVersion("1.x");
      cmp1x510.setWeblogicVersion("7");
      installedTypes.add(cmp1x510);
   }

   private synchronized void setInstallationLocation() {
      String weblogicHome = Home.getPath();
      if (null == weblogicHome) {
         if (verbose) {
            Debug.say("weblogic.Home not found. There must be a problem with the classpath. Unable to find any Persistence Types.");
         }

         throw new AssertionError("weblogic.home not found.  There must be a problem with the classpath.");
      } else {
         this.installationLocation = weblogicHome + File.separator + "lib" + File.separator + "persistence";
         File f = new File(this.installationLocation);
         if (!f.exists()) {
            String platformInstallationLocation = weblogicHome + File.separator + "server" + File.separator + "lib" + File.separator + "persistence";
            f = new File(platformInstallationLocation);
            if (f.exists()) {
               this.installationLocation = platformInstallationLocation;
            }
         }

         if (verbose) {
            Debug.say("Set installation location to " + this.installationLocation);
         }

      }
   }

   public static void main(String[] argv) {
      InstalledPersistence ip = new InstalledPersistence();
      System.out.println("Thank you for invoking this tool. It initialized successfully.");

      try {
         Iterator iter = ip.getInstalledTypes().iterator();

         while(iter.hasNext()) {
            PersistenceType type = (PersistenceType)iter.next();
            System.out.println("\n---------------------------------------------");
            System.out.println("Found PersistenceType:");
            System.out.println("" + type);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }
}
