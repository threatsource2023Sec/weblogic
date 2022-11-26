package com.oracle.weblogic.lifecycle.core;

import commonj.work.Work;
import commonj.work.WorkException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.FileTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Inject;
import org.glassfish.hk2.api.PostConstruct;
import org.jvnet.hk2.annotations.Service;
import weblogic.utils.annotation.ManagedServer;
import weblogic.work.j2ee.J2EEWorkManager;

@Service
@ManagedServer
public class LifecycleConfigFileWatcher implements PostConstruct {
   @Inject
   LifecycleConfigFactory configFactory;

   public void postConstruct() {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug(" In LifecycleConfigFileWatcher PostConstruct");
      }

      File configFile = null;
      Objects.requireNonNull(this.configFactory);

      try {
         configFile = this.configFactory.getConfigFile();
      } catch (IOException var7) {
         if (LifecycleUtils.isAppServer()) {
            LCMLogger.logExceptionLCMConfigFile(var7);
         }
      }

      if (configFile != null) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug(" In LifecycleConfigFileWatcher configFile=" + configFile.getAbsolutePath());
         }

         File dir = configFile.getAbsoluteFile().getParentFile();
         Path toWatch = Paths.get(dir.getPath());
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug(" In LifecycleConfigFileWatcher watching directory =" + toWatch.toString());
         }

         if (toWatch == null) {
            throw new UnsupportedOperationException(CatalogUtils.getMsgExceptionSettingUpFileWatcherLCMConfig());
         }

         try {
            WatchService myWatcher = toWatch.getFileSystem().newWatchService();
            Objects.requireNonNull(myWatcher);
            MyWatchQueueReader fileWatcher = new MyWatchQueueReader(myWatcher, configFile, this.configFactory);
            Objects.requireNonNull(fileWatcher);
            J2EEWorkManager.getDefault().schedule(fileWatcher);
            toWatch.register(myWatcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
         } catch (WorkException | IOException var6) {
            if (LifecycleUtils.isAppServer()) {
               LCMLogger.logExceptionConfigFileWatcher(var6);
            }

            throw new RuntimeException(CatalogUtils.getMsgExceptionSettingUpFileWatcherLCMConfig(), var6);
         }
      }

   }

   private static class MyWatchQueueReader implements Work {
      private WatchService myWatcher;
      private final String targetFilename;
      private final String lcConfigFileName;
      private final File configDir;
      private LifecycleConfigFactory configFactory;
      final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

      public MyWatchQueueReader(WatchService myWatcher, File configFile, LifecycleConfigFactory configFactory) {
         this.myWatcher = myWatcher;
         this.configFactory = configFactory;
         this.configDir = configFile.getParentFile();
         this.lcConfigFileName = configFile.getName();
         this.targetFilename = configFile.getName() + ".changed";
      }

      public void run() {
         try {
            for(WatchKey key = this.myWatcher.take(); key != null; key = this.myWatcher.take()) {
               Iterator var2 = key.pollEvents().iterator();

               while(var2.hasNext()) {
                  WatchEvent event = (WatchEvent)var2.next();
                  String fileName = event.context().toString();
                  if (fileName.equals(this.lcConfigFileName)) {
                     String kind = event.kind().name();
                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug("LifecycleConfigFileWatcher.run Config File Change detected fileName=" + fileName + " kind = " + kind);
                     }

                     if ("ENTRY_CREATE".equals(kind) || "ENTRY_MODIFY".equals(kind)) {
                        try {
                           this.rwLock.writeLock().lock();
                           if (LifecycleUtils.isDebugEnabled()) {
                              File file = this.configFactory.getConfigFile();
                              if (file != null) {
                                 FileTime ft = Files.getLastModifiedTime(file.toPath());
                                 long lastModified = ft.to(TimeUnit.NANOSECONDS);
                                 LifecycleUtils.debug("LifecycleConfigFileWatcher.run Reloading config " + file.getAbsolutePath() + " lastModified =" + lastModified + " size : " + file.length() + " canRead:" + file.canRead() + " exists:" + file.exists());
                              } else {
                                 LifecycleUtils.debug("LifecycleConfigFileWatcher.run configFile is null");
                              }
                           }

                           this.configFactory.reloadLifecycleConfig();
                        } catch (Exception var14) {
                           if (LifecycleUtils.isAppServer()) {
                              LCMLogger.logExceptionReloadingConfig(var14);
                           } else if (LifecycleUtils.isDebugEnabled()) {
                              LifecycleUtils.debug("Warning: Error in LifecycleConfigFileWatcher ", var14);
                           }
                        } finally {
                           this.rwLock.writeLock().unlock();
                        }
                     }
                  }
               }

               key.reset();
            }
         } catch (InterruptedException var16) {
         }

      }

      public void release() {
         try {
            if (this.myWatcher != null) {
               this.myWatcher.close();
            }
         } catch (IOException var2) {
            if (LifecycleUtils.isAppServer()) {
               LCMLogger.logExceptionReloadingConfig(var2);
            } else if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Warning: LifecycleConfigFileWatcher Close failed", var2);
            }
         }

      }

      public boolean isDaemon() {
         return true;
      }
   }
}
