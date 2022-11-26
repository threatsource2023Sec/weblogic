package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.internal.ProductionModeHelper;
import weblogic.t3.srvr.FileOwnerFixer;

public final class DescriptorHelper {
   public static final String BINDING_CONFIG = "schema/weblogic-domain-binding.jar.jar";
   private static boolean skipSetProductionMode = false;
   public static final String RECOVERY_EXTENSION = ".recovery";
   public static final String RECOVERY_NEW_EXTENSION = ".recovery_new";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");

   public static boolean saveDescriptorTree(Descriptor descriptorTree, boolean usePendingDirectory, String domainDir) throws IOException {
      return saveDescriptorTree(descriptorTree, usePendingDirectory, domainDir, (String)null);
   }

   public static boolean saveDescriptorTree(Descriptor descriptorTree, boolean usePendingDirectory, String domainDir, String encoding) throws IOException {
      return usePendingDirectory ? savePendingDescriptorTree(descriptorTree, EditDirectoryManager.getGlobalDirectoryManager(), encoding) : saveConfigDescriptorTree(descriptorTree, domainDir.endsWith("config") ? domainDir : domainDir + File.separator + "config", encoding);
   }

   public static boolean savePendingDescriptorTree(Descriptor descriptorTree, EditDirectoryManager pendingMgr, String encoding) throws IOException {
      File originalFile = null;
      File recoveryFile = null;
      boolean newRecoveryFile = false;
      OutputStream os = null;
      boolean anyFileSaved = false;

      try {
         String pendingConfig = pendingMgr.getPendingFilePath("config.xml");
         boolean externalTreeModified = false;
         Iterator it = DescriptorInfoUtils.getDescriptorInfos(descriptorTree);

         DescriptorInfo descInfo;
         Descriptor desc;
         while(it != null && it.hasNext()) {
            descInfo = (DescriptorInfo)it.next();
            desc = descInfo.getDescriptor();
            if (desc.isModified()) {
               externalTreeModified = true;
               break;
            }
         }

         if (descriptorTree.isModified() || externalTreeModified) {
            originalFile = new File(pendingConfig);
            if (originalFile.exists()) {
               recoveryFile = getRecoveryFile(originalFile);
               recoveryFile.delete();
               originalFile.renameTo(recoveryFile);
            } else {
               recoveryFile = getRecoveryFileForNewFile(originalFile);
               newRecoveryFile = true;
            }

            os = new FileOutputStream(pendingConfig);
            DescriptorManagerHelper.getDescriptorManager(true).writeDescriptorAsXML(descriptorTree, os, encoding);
            os.flush();
            os.close();
            os = null;
            originalFile = null;
            if (recoveryFile != null) {
               recoveryFile.delete();
               recoveryFile = null;
            }

            anyFileSaved = true;
         }

         it = DescriptorInfoUtils.getDescriptorInfos(descriptorTree);

         while(it != null && it.hasNext()) {
            descInfo = (DescriptorInfo)it.next();
            desc = descInfo.getDescriptor();
            if (desc.isModified() || desc == descriptorTree) {
               String pendingCustomConfig = pendingMgr.getPendingFilePath(descInfo.getConfigurationExtension().getDescriptorFileName());
               originalFile = new File(pendingCustomConfig);
               if (originalFile.exists()) {
                  recoveryFile = getRecoveryFile(originalFile);
                  recoveryFile.delete();
                  originalFile.renameTo(recoveryFile);
                  newRecoveryFile = false;
               } else {
                  recoveryFile = getRecoveryFileForNewFile(originalFile);
                  newRecoveryFile = true;
               }

               os = new FileOutputStream(pendingCustomConfig);
               descInfo.getDescriptorManager().writeDescriptorAsXML(descInfo.getDescriptorBean().getDescriptor(), os, encoding);
               os.flush();
               os.close();
               os = null;
               originalFile = null;
               if (recoveryFile != null) {
                  recoveryFile.delete();
                  recoveryFile = null;
               }

               anyFileSaved = true;
               propagateExternalConfigChangesInEditTree(descInfo);
            }
         }

         boolean var17 = anyFileSaved;
         return var17;
      } finally {
         if (os != null) {
            os.close();
            if (originalFile != null) {
               originalFile.delete();
            }
         }

         if (recoveryFile != null) {
            if (newRecoveryFile) {
               recoveryFile.delete();
            } else {
               recoveryFile.renameTo(originalFile);
            }
         }

      }
   }

   private static void propagateExternalConfigChangesInEditTree(DescriptorInfo descInfo) {
      if (descInfo.getConfigurationExtension() instanceof SystemResourceMBean) {
         try {
            Descriptor templateDescriptor = ((SystemResourceMBean)descInfo.getConfigurationExtension()).getResource().getDescriptor();
            Field delegateSourcesField = descInfo.getConfigurationExtension().getClass().getDeclaredField("_DelegateSources");
            delegateSourcesField.setAccessible(true);
            Collection delegateSources = (Collection)delegateSourcesField.get(descInfo.getConfigurationExtension());
            Iterator var4 = delegateSources.iterator();

            while(var4.hasNext()) {
               SystemResourceMBean source = (SystemResourceMBean)var4.next();
               Descriptor descriptor = source.getResource().getDescriptor();
               descriptor.prepareUpdate(templateDescriptor, false);
               descriptor.activateUpdate();
            }
         } catch (NoSuchFieldException | IllegalAccessException var7) {
         } catch (DescriptorUpdateRejectedException | DescriptorUpdateFailedException var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Propagation of changes in RGT system resources to delegate sources was not successful", var8);
            }
         }
      }

   }

   public static boolean saveConfigDescriptorTree(Descriptor descriptorTree, String domainDir, String encoding) throws IOException {
      OutputStream os = null;

      try {
         os = getFileOutputStream(domainDir, "config.xml");
         DescriptorManagerHelper.getDescriptorManager(true).writeDescriptorAsXML(descriptorTree, os, encoding);
         os.flush();
         os.close();
         os = null;

         for(Iterator it = DescriptorInfoUtils.getDescriptorInfos(descriptorTree); it != null && it.hasNext(); os = null) {
            DescriptorInfo descInfo = (DescriptorInfo)it.next();
            os = getFileOutputStream(domainDir, descInfo.getConfigurationExtension().getDescriptorFileName());
            descInfo.getDescriptorManager().writeDescriptorAsXML(descInfo.getDescriptorBean().getDescriptor(), os, encoding);
            os.flush();
            os.close();
         }

         boolean var9 = true;
         return var9;
      } finally {
         if (os != null) {
            os.close();
         }

      }
   }

   private static OutputStream getFileOutputStream(String domainDir, String file) throws IOException {
      String filePath = null;
      if (domainDir != null) {
         filePath = domainDir + File.separator + file;
      } else {
         filePath = DomainDir.getPathRelativeConfigDir(file);
      }

      File outputFile = new File(filePath);
      File parent = outputFile.getParentFile();
      if (!parent.exists()) {
         parent.mkdirs();
      }

      FileOwnerFixer.addPathJDK6(outputFile);
      return new FileOutputStream(outputFile);
   }

   private static File getRecoveryFile(File file) throws IOException {
      String recoveryFileName = file.getPath() + ".recovery";
      return new File(recoveryFileName);
   }

   private static File getRecoveryFileForNewFile(File file) throws IOException {
      String recoveryFileName = file.getPath() + ".recovery_new";
      File newFile = new File(recoveryFileName);
      File parent = newFile.getParentFile();
      if (!parent.exists()) {
         parent.mkdirs();
      }

      FileOwnerFixer.addPathJDK6(newFile);
      newFile.createNewFile();
      return newFile;
   }

   public static void setDescriptorTreeProductionMode(Descriptor descriptorTree, boolean productionMode) {
      if (!skipSetProductionMode) {
         descriptorTree.setProductionMode(productionMode);
         Iterator it = DescriptorInfoUtils.getDescriptorInfos(descriptorTree);

         while(it != null && it.hasNext()) {
            DescriptorInfo descInfo = (DescriptorInfo)it.next();
            Descriptor desc = descInfo.getDescriptor();
            desc.setProductionMode(productionMode);
         }

      }
   }

   public static void setDescriptorManagerProductionModeIfNeeded(Descriptor descriptorTree, boolean productionMode) {
      if (productionMode && !ProductionModeHelper.isProductionModePropertySet()) {
         DescriptorBean root = descriptorTree.getRootBean();
         if (root instanceof DomainMBean && !descriptorTree.isEditable()) {
            DescriptorManagerHelper.getDescriptorManager(true).setProductionMode(true);
            DescriptorManagerHelper.getDescriptorManager(false).setProductionMode(true);
         }
      }

   }

   public static void setDescriptorTreeSecureMode(Descriptor descriptorTree, boolean secureMode) {
      descriptorTree.setSecureMode(secureMode);
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(descriptorTree);

      while(it != null && it.hasNext()) {
         DescriptorInfo descInfo = (DescriptorInfo)it.next();
         Descriptor desc = descInfo.getDescriptor();
         desc.setSecureMode(secureMode);
      }

   }

   public static boolean recoverPendingDirectory(File[] pendingFiles, EditDirectoryManager directoryMgr) {
      int numDeleted = 0;
      File[] var3 = pendingFiles;
      int var4 = pendingFiles.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File pendingFile = var3[var5];
         String path = pendingFile.getPath();
         File origFile;
         if (path.endsWith(".recovery")) {
            origFile = new File(path.substring(0, path.lastIndexOf(".recovery")));
            origFile.delete();
            pendingFile.renameTo(origFile);
         } else if (path.endsWith(".recovery_new")) {
            origFile = new File(path.substring(0, path.lastIndexOf(".recovery_new")));
            origFile.delete();
            pendingFile.delete();
            ++numDeleted;
         }
      }

      return numDeleted == 0 || !directoryMgr.getAllPendingFiles().isEmpty();
   }

   public static void setSkipSetProductionMode(boolean skip) {
      skipSetProductionMode = skip;
   }
}
