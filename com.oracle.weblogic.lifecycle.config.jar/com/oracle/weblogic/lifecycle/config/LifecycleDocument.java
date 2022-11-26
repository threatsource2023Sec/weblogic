package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.LifecycleException;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import weblogic.utils.FileUtils;

public class LifecycleDocument {
   Logger logger = Logger.getLogger(LifecycleDocument.class.getName());
   private final File configFile;
   private final DatabaseConfigHandler databaseConfigHandler;
   private static Map documentPool;
   private static LifecycleDocument dbLifecycleDocument;
   private boolean initialized = false;

   public static LifecycleDocument getInstance(ServiceLocator serviceLocator, URL configFileUrl) {
      if (documentPool == null) {
         Class var2 = LifecycleDocument.class;
         synchronized(LifecycleDocument.class) {
            if (documentPool == null) {
               documentPool = new TreeMap();
            }
         }
      }

      try {
         URI configFileUri = configFileUrl.toURI();
         File configFile = new File(configFileUri);
         String configFileName = configFile.getAbsolutePath();
         LifecycleDocument lifecycleDocument = (LifecycleDocument)documentPool.get(configFileName);
         if (lifecycleDocument == null) {
            synchronized(documentPool) {
               lifecycleDocument = (LifecycleDocument)documentPool.get(configFileName);
               if (lifecycleDocument == null) {
                  lifecycleDocument = new LifecycleDocument(serviceLocator, configFile);
                  documentPool.put(configFileName, lifecycleDocument);
               }
            }
         }

         return lifecycleDocument;
      } catch (URISyntaxException var9) {
         throw new RuntimeException("Can't get lifecycle-config.xml file", var9);
      }
   }

   public static LifecycleDocument getInstance(ServiceLocator serviceLocator, DatabaseConfigHandler databaseConfigHandler) {
      if (dbLifecycleDocument == null) {
         dbLifecycleDocument = new LifecycleDocument(serviceLocator, databaseConfigHandler);
      }

      return dbLifecycleDocument;
   }

   public static LifecycleDocument removeInstance(ServiceLocator serviceLocator, URL configFileUrl) {
      LifecycleDocument document = null;

      try {
         if (documentPool != null) {
            URI configFileUri = configFileUrl.toURI();
            File configFile = new File(configFileUri);
            String configFileName = configFile.getAbsolutePath();
            document = (LifecycleDocument)documentPool.get(configFileName);
            if (document != null) {
               documentPool.remove(configFileName);
            }
         }

         return document;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   private LifecycleDocument(ServiceLocator serviceLocator, DatabaseConfigHandler configHandler) {
      this.configFile = null;
      this.databaseConfigHandler = configHandler;
   }

   private boolean checkIfPropagationNeeded(List changes) {
      Iterator var2 = changes.iterator();

      while(true) {
         PropertyChangeEvent pce;
         String impClass;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            pce = (PropertyChangeEvent)var2.next();
            impClass = this.getImplClass(pce.getSource());
         } while(impClass == null);

         if (!impClass.equals(Tenants.class.getName()) && !impClass.equals(Tenant.class.getName()) && !impClass.equals(Service.class.getName())) {
            if (!impClass.equals(Partition.class.getName()) && !impClass.equals(Partition.class.getName())) {
               if (impClass.equals(Environment.class.getName())) {
                  if (pce.getPropertyName().equals("partition-ref")) {
                     return true;
                  }

                  String nestedDescEnv = null;
                  if (pce.getNewValue() != null) {
                     nestedDescEnv = this.getImplClass(pce.getNewValue());
                  }

                  if (pce.getOldValue() != null) {
                     nestedDescEnv = this.getImplClass(pce.getOldValue());
                  }

                  if (nestedDescEnv != null && nestedDescEnv.equals(PartitionRef.class.getName())) {
                     return true;
                  }
               }
               continue;
            }

            return true;
         }

         return true;
      }
   }

   private String getImplClass(Object proxy) {
      if (proxy instanceof Partition) {
         return Partition.class.getName();
      } else if (proxy instanceof PartitionRef) {
         return PartitionRef.class.getName();
      } else if (!Proxy.isProxyClass(proxy.getClass())) {
         return null;
      } else {
         InvocationHandler handler = Proxy.getInvocationHandler(proxy);
         if (Descriptor.class.isAssignableFrom(handler.getClass())) {
            try {
               Method m = handler.getClass().getMethod("getImplementation");
               return (String)m.invoke(handler);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var4) {
               if (this.logger != null && this.logger.isLoggable(Level.SEVERE)) {
                  this.logger.log(Level.SEVERE, "Exception finding method impClass(Object proxy)", var4);
               }
            }
         }

         return null;
      }
   }

   private List getChangeList(List changes) {
      List partitionList = new ArrayList();
      Iterator var3 = changes.iterator();

      while(true) {
         while(true) {
            PropertyChangeEvent pce;
            String propName;
            String impClass;
            do {
               do {
                  do {
                     if (!var3.hasNext()) {
                        return partitionList;
                     }

                     pce = (PropertyChangeEvent)var3.next();
                     propName = pce.getPropertyName();
                  } while(propName == null);

                  impClass = this.getImplClass(pce.getSource());
               } while(impClass == null);
            } while(!impClass.equals(Partition.class.getName()) && !impClass.equals(PartitionRef.class.getName()));

            if (propName.equals("id")) {
               Object oldValueObject = pce.getOldValue();
               Object newValueObject = pce.getNewValue();
               if (oldValueObject != null && oldValueObject instanceof String) {
                  partitionList.add((String)oldValueObject);
               }

               if (newValueObject != null && newValueObject instanceof String) {
                  partitionList.add((String)newValueObject);
               }
            } else if (propName.equals("")) {
               String id = null;
               if (impClass.equals(Partition.class.getName())) {
                  Partition partition = (Partition)pce.getSource();
                  id = partition.getId();
               } else if (impClass.equals(PartitionRef.class.getName())) {
                  PartitionRef ref = (PartitionRef)pce.getSource();
                  id = ref.getId();
               }

               if (id != null) {
                  if (pce.getOldValue() != null && pce.getNewValue() == null) {
                     partitionList.add(id);
                  } else if (pce.getOldValue() == null && pce.getNewValue() != null) {
                     partitionList.add(id);
                  }
               }
            }
         }
      }
   }

   private void callLCConfigChangeListener(ServiceLocator serviceLocator, List changes) {
      List partitionList = this.getChangeList(changes);
      if (partitionList != null && partitionList.size() > 0) {
         Collection listeners = serviceLocator.getAllServices(LifecycleConfigChangeListener.class, new Annotation[0]);
         if (listeners != null) {
            Iterator var5 = listeners.iterator();

            while(var5.hasNext()) {
               LifecycleConfigChangeListener listener = (LifecycleConfigChangeListener)var5.next();

               try {
                  listener.configFileReloaded(partitionList);
               } catch (Exception var8) {
                  if (this.logger != null && this.logger.isLoggable(Level.SEVERE)) {
                     this.logger.log(Level.SEVERE, "Can't call the listeners", var8);
                  }
               }
            }
         }
      }

   }

   public void doTransactionCommitted(ServiceLocator serviceLocator, List changes, XmlRootHandle rootHandle) {
      try {
         if (this.configFile != null) {
            if (rootHandle == null) {
               throw new AssertionError("not implemented");
            }

            this.newSave(rootHandle);
         }

         if (this.databaseConfigHandler != null) {
            this.databaseConfigHandler.handleConfigChange(changes);
         }

         this.callLCConfigChangeListener(serviceLocator, changes);
         if (this.configFile != null) {
            boolean propagateChange = this.checkIfPropagationNeeded(changes);
            if (propagateChange) {
               PropagationManager pmgr = (PropagationManager)serviceLocator.getService(PropagationManager.class, new Annotation[0]);
               if (pmgr != null) {
                  if (pmgr.isEnabled()) {
                     pmgr.propagate(this.configFile);
                  }
               } else {
                  this.logger.log(Level.WARNING, "Unable to locate a PropagationManager to distribute changes.");
               }
            }
         }
      } catch (Exception var6) {
         if (this.logger != null && this.logger.isLoggable(Level.SEVERE)) {
            this.logger.log(Level.SEVERE, "Can't save lifecycle-config.xml", var6);
         }
      }

   }

   private LifecycleDocument(ServiceLocator serviceLocator, File configFile) {
      this.configFile = configFile;
      this.databaseConfigHandler = null;
   }

   private File getLockFile() {
      Objects.requireNonNull(this.configFile);
      return new File(this.configFile.getParent(), this.configFile.getName() + ".lok");
   }

   public FileLock getLock() throws IOException {
      File lockFile = this.getLockFile();
      if (LifecycleConfigUtil.isDebugEnabled()) {
         LifecycleConfigUtil.debug("Getting lock on " + lockFile);
      }

      FileOutputStream os = new FileOutputStream(lockFile);
      long fileTimeout = LifecycleConfigUtil.getFileLockTimeout();
      return FileUtils.getFileLock(os.getChannel(), fileTimeout);
   }

   private synchronized void newSave(XmlRootHandle rootHandle) throws Exception {
      Objects.requireNonNull(this.configFile);

      File tempFile;
      try {
         tempFile = File.createTempFile(this.configFile.getName(), "", this.configFile.getParentFile());
         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug("created temp file to save lifecycle configuration : " + tempFile);
         }
      } catch (IOException var11) {
         throw new IOException("Cannot create temporary file when saving lifecycle configuration " + this.configFile, var11);
      }

      FileOutputStream fos = new FileOutputStream(tempFile);

      try {
         rootHandle.marshal(fos);
      } catch (IOException var9) {
         if (this.logger != null && this.logger.isLoggable(Level.SEVERE)) {
            this.logger.log(Level.SEVERE, "Configuration could not be saved to temporary file", var9);
         }

         throw var9;
      } finally {
         fos.close();
      }

      this.finishUpSave(tempFile);
   }

   private void finishUpSave(File tempFile) throws Exception {
      Path configFilePath = FileSystems.getDefault().getPath(this.configFile.getParent(), this.configFile.getName());
      File backup = new File(this.configFile.getParent(), this.configFile.getName() + ".bak");
      Path backupConfigFilePath = FileSystems.getDefault().getPath(backup.getParent(), backup.getName());
      Path tempFilePath = FileSystems.getDefault().getPath(tempFile.getParent(), tempFile.getName());
      FileLock lock = null;

      try {
         lock = this.getLock();
         if (lock == null) {
            throw new LifecycleException("Could not lock file : " + this.getLockFile());
         }

         if (this.configFile.exists()) {
            if (LifecycleConfigUtil.isDebugEnabled()) {
               LifecycleConfigUtil.debug("Backup config file : Renaming " + configFilePath + " to " + backupConfigFilePath);
            }

            Files.move(configFilePath, backupConfigFilePath, StandardCopyOption.REPLACE_EXISTING);
         }

         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug("Renaming " + tempFile.getAbsolutePath() + " to " + configFilePath);
         }

         Files.move(tempFilePath, configFilePath, StandardCopyOption.REPLACE_EXISTING);
         if (LifecycleConfigUtil.isDebugEnabled()) {
            LifecycleConfigUtil.debug("Renamed " + tempFile.getAbsolutePath() + " to " + configFilePath);
         }
      } catch (Exception var15) {
         if (this.logger != null && this.logger.isLoggable(Level.SEVERE)) {
            this.logger.log(Level.SEVERE, "Exception while saving the configuration, changes not persisted", var15);
         }

         throw var15;
      } finally {
         if (lock != null) {
            try {
               lock.release();
               lock.channel().close();
               lock.close();
               if (LifecycleConfigUtil.isDebugEnabled()) {
                  LifecycleConfigUtil.debug("File lock released ");
               }
            } catch (IOException var14) {
            }
         }

      }

   }

   public synchronized void setInitialized() {
      this.initialized = true;
   }

   public synchronized boolean isInitialized() {
      return this.initialized;
   }

   public String toString() {
      return this.configFile == null ? "null" : this.configFile + " exists : " + this.configFile.exists() + " size : " + this.configFile.length() + " canwrite: " + this.configFile.canWrite() + " canread: " + this.configFile.canRead() + " canexec: " + this.configFile.canExecute() + " lastmodified : " + this.configFile.lastModified() + " isFile: " + this.configFile.isFile();
   }
}
