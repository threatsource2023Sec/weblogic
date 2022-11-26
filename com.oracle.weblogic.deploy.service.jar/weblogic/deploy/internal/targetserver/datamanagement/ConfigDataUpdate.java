package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.service.DataStream;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.TargetFileDataStream;
import weblogic.deploy.service.datatransferhandlers.DataHandlerManager;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.deploy.internal.DeploymentManagerLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtils;

public class ConfigDataUpdate extends DataUpdate {
   private static final int LOCK_WAIT_TIME_INTERVAL = Integer.parseInt(System.getProperty("weblogic.deploy.FileLockWaitTimeInterval", "100"));
   private static final String BOOTING_SERVER_LOCK = "BOOTING_SERVER_LOCK";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static File domainDir = null;
   private static final ConfigBackupRecoveryManager backupManager = ConfigBackupRecoveryManager.getInstance();
   private CopyLockInfo copyLockInfo = null;
   private boolean handleCAMConfigFilesOnly = false;
   private DataTransferHandler dataTransferHandler = null;
   private Boolean isAdminServer = null;

   public static void setDomainDir(File dd) {
      domainDir = dd;
   }

   public static File getDomainDir() {
      if (domainDir == null) {
         domainDir = new File(DomainDir.getRootDir());
      }

      return domainDir;
   }

   private boolean isAdminServer() {
      if (this.isAdminServer == null) {
         this.isAdminServer = ManagementService.getPropertyService(KERNEL_ID).isAdminServer();
      }

      return this.isAdminServer;
   }

   public ConfigDataUpdate(Data localData, DataUpdateRequestInfo reqInfo) {
      super(localData, reqInfo);
   }

   protected final void doDownload(String handlerType) throws DeploymentException {
      List requestedFiles = this.getRequestedFiles();
      List targetFiles = this.getTargetFiles();
      if (requestedFiles != null && requestedFiles.size() != 0) {
         CopyLockInfo lock = new CopyLockInfo(this.getLocalData().getLockPath(), this.getRequestId(), this.isServerBooting());
         this.setCopyLock(lock);
         if (lock != null && !lock.isUpdateNeeded()) {
            lock.release();
            if (!this.isAdminServer()) {
               if (isDebugEnabled()) {
                  debug("Not preparing '" + this + "' - since some other server acquired lock");
               }

               return;
            }

            List camRequestedFiles = new ArrayList();
            List camTargetFiles = new ArrayList();
            Iterator iter = requestedFiles.iterator();

            while(iter.hasNext()) {
               String requestedPath = (String)iter.next();
               if (requestedPath.startsWith(DomainDir.getCAMPendingDir())) {
                  camRequestedFiles.add(requestedPath);
                  camTargetFiles.add((Object)null);
                  if (isDebugEnabled()) {
                     debug("Admin server is downloading CAM file: " + requestedPath);
                  }
               }
            }

            if (camRequestedFiles.size() == 0) {
               return;
            }

            requestedFiles.clear();
            requestedFiles.addAll(camRequestedFiles);
            targetFiles = camTargetFiles;
            this.handleCAMConfigFilesOnly = true;
         }

         ConfigData configData = this.getLocalConfigData();
         DataTransferRequest request = new ConfigDataTransferRequestImpl(this.getRequestId(), requestedFiles, (List)targetFiles, configData.getLockPath());

         try {
            if (this.dataTransferHandler == null) {
               this.dataTransferHandler = DataHandlerManager.getHandler(handlerType);
            }

            MultiDataStream streams = new ReadOnlyMultiDataStream(this.dataTransferHandler.getDataAsStream(request));
            this.setDownloadedStream(streams);
            this.handleBackup(streams);
         } catch (IOException var9) {
            var9.printStackTrace();
            throw new DeploymentException("Exception occured while downloading files", var9);
         }
      }
   }

   protected final void doUpdate() throws DeploymentException {
      CopyLockInfo lock = this.getCopyLock();
      if (lock != null && !lock.isUpdateNeeded() && !this.handleCAMConfigFilesOnly) {
         if (isDebugEnabled()) {
            debug("Not committing '" + this + "' - since some other server acquired lock");
         }

      } else {
         FileLock configLock = null;

         try {
            if (!this.handleCAMConfigFilesOnly) {
               configLock = this.acquireFileLock(this.getLocalData().getLockPath());
            }

            super.doUpdate();
         } finally {
            if (!this.handleCAMConfigFilesOnly) {
               this.releaseFileLock(configLock);
            }

         }

      }
   }

   protected void doCancel() {
      this.restoreFromBackup();
   }

   protected void doClose(boolean success) {
      if (success) {
         this.saveBackupToPrev();
      }

      CopyLockInfo lock = this.getCopyLock();
      if (lock != null) {
         lock.release();
         if (lock.isUpdateNeeded()) {
            lock.removeFile();
         }
      }

      this.setCopyLock((CopyLockInfo)null);
   }

   public final void releaseLock() {
      CopyLockInfo lock = this.getCopyLock();
      if (lock != null) {
         lock.release();
      }

   }

   protected final File getFileFor(String targetPath) {
      if (targetPath == null) {
         throw new NullPointerException("targetPath null");
      } else {
         File file = null;
         MultiDataStream streams = this.getDownloadedStream();
         if (streams != null) {
            Iterator allStreams = streams.getDataStreams();

            while(allStreams.hasNext()) {
               DataStream eachStream = (DataStream)allStreams.next();
               if (eachStream.getName().equals(targetPath)) {
                  if (!(eachStream instanceof FileDataStream)) {
                     break;
                  }

                  file = ((FileDataStream)eachStream).getFile();
               }
            }
         }

         if (file == null) {
            file = new File(this.getLocalData().getRootLocation(), targetPath);
         }

         return file;
      }
   }

   protected final void deleteFile(String targetURI) {
      if (targetURI != null) {
         File f = new File(this.getLocalData().getRootLocation(), targetURI);
         CopyLockInfo lock = this.getCopyLock();
         if (lock != null && !lock.isUpdateNeeded()) {
            if (!ManagementService.getPropertyService(KERNEL_ID).isAdminServer() || !DomainDir.isFileRelativeToCAMConfigDir(f)) {
               if (isDebugEnabled()) {
                  debug("Not deleting file '" + f.getAbsolutePath() + "' for '" + this + "' - since copy lock acquired by some other server");
               }

               return;
            }

            this.handleCAMConfigFilesOnly = true;
            if (isDebugEnabled()) {
               debug("Admin server will only delete CAM file: " + f.getAbsolutePath());
            }
         }

         if (isDebugEnabled()) {
            debug("Deleting file " + f.getAbsolutePath());
         }

         FileLock configLock = null;
         boolean deleted = false;

         try {
            if (!this.handleCAMConfigFilesOnly) {
               configLock = this.acquireFileLock(this.getLocalData().getLockPath());
            }

            deleted = f.delete();
         } finally {
            if (!this.handleCAMConfigFilesOnly) {
               this.releaseFileLock(configLock);
            }

         }

         if (!deleted & f.exists()) {
            f.deleteOnExit();
            DeploymentManagerLogger.logDeleteFileFailed(targetURI);
         } else if (isDebugEnabled()) {
            debug("Deleted file " + f.getAbsolutePath());
         }

      }
   }

   private ConfigData getLocalConfigData() {
      return (ConfigData)this.getLocalData();
   }

   private final void saveBackupToPrev() {
      CopyLockInfo lock = this.getCopyLock();
      if (lock != null && !lock.isUpdateNeeded()) {
         if (isDebugEnabled()) {
            debug("Not saving config_bak dir to config_prev dir for '" + this + "' - since copy lock acquired by some other server");
         }

      } else {
         try {
            if (isDebugEnabled()) {
               debug("Saving config_bak dir to config_prev dir.");
            }

            backupManager.saveConfigBakDirToConfigPrevDir();
            if (isDebugEnabled()) {
               debug("Saving config_bak dir to config_prev dir - completed.");
            }
         } catch (Throwable var3) {
            var3.printStackTrace();
         }

      }
   }

   private final void restoreFromBackup() {
      CopyLockInfo lock = this.getCopyLock();
      if (lock != null && !lock.isUpdateNeeded()) {
         if (isDebugEnabled()) {
            debug("Not restoring from config_bak dir for '" + this + "' - since copy lock acquired by some other server");
         }

      } else {
         synchronized(this) {
            try {
               if (isDebugEnabled()) {
                  debug("Restoring from config_bak dir.");
               }

               backupManager.restoreFromBackup();
               if (isDebugEnabled()) {
                  debug("Restoring from config_bak dir. - completed");
               }
            } catch (Throwable var5) {
               var5.printStackTrace();
            }

         }
      }
   }

   public static File copyDataStreamToTempFile(DataStream stream, String targetPath) throws IOException {
      if (stream instanceof FileDataStream) {
         File streamFile = ((FileDataStream)stream).getFile();
         if (streamFile.getName().startsWith("wl_comp")) {
            return streamFile;
         } else {
            File tmpFile = copyStreamToTempFile(stream.getInputStream(), targetPath);
            FileUtils.setPosixFilePermissions(tmpFile, FileUtils.getPosixFilePermissions(streamFile));
            return tmpFile;
         }
      } else {
         return copyStreamToTempFile(stream.getInputStream(), targetPath);
      }
   }

   private static File copyStreamToTempFile(InputStream in, String targetPath) throws IOException {
      File targetFile = new File(getDomainDir(), targetPath);
      File tempFile = null;
      String prefix = targetFile.getName();
      if (prefix.length() < 3) {
         prefix = prefix + "aaa".substring(prefix.length());
      }

      try {
         tempFile = File.createTempFile(prefix, "new");
      } catch (IOException var13) {
         tempFile = File.createTempFile(prefix, "new", getDomainDir());
      }

      tempFile.deleteOnExit();

      try {
         FileUtils.writeToFile(in, tempFile);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var12) {
            }
         }

      }

      return tempFile;
   }

   private void setCopyLock(CopyLockInfo copyLockInfo) {
      this.copyLockInfo = copyLockInfo;
   }

   private CopyLockInfo getCopyLock() {
      return this.copyLockInfo;
   }

   private String getCopyLockFileName(long id) {
      File tmpDir = new File(DomainDir.getTempDir());
      if (!tmpDir.exists()) {
         tmpDir.mkdirs();
      }

      if (isDebugEnabled()) {
         debug(" Is Server Booting.... : " + this.isServerBooting());
      }

      String fileToBeLocked = this.isServerBooting() ? "BOOTING_SERVER_LOCK" : Long.toString(id);
      String lockFilePath = DomainDir.getPathRelativeTempDir(fileToBeLocked + ".lok");
      return lockFilePath;
   }

   private boolean isServerBooting() {
      return !ManagementService.isRuntimeAccessInitialized();
   }

   private FileLock acquireFileLock(String lockFileName) {
      if (lockFileName == null) {
         return null;
      } else {
         FileLock fileLock = null;
         String lockFilePath = this.getLocalData().getRootLocation() + File.separator + lockFileName;
         fileLock = this.acquireFileLock(new File(lockFilePath));
         return fileLock;
      }
   }

   private FileLock acquireFileLock(File lockFile) {
      FileLock lock = getFileLock(lockFile);
      if (isDebugEnabled()) {
         debug(" Acquired FileLock on '" + lockFile.getAbsolutePath() + "' -- " + System.currentTimeMillis());
      }

      return lock;
   }

   public static FileLock getFileLock(File lockFile) {
      try {
         FileOutputStream os = new FileOutputStream(lockFile);
         FileChannel lockChannel = os.getChannel();
         if (isDebugEnabled()) {
            debug("Trying to get lock for File: " + lockFile.getAbsolutePath() + " with channel : " + lockChannel);
         }

         return getFileLock(lockChannel, 300000L);
      } catch (FileNotFoundException var3) {
         return null;
      }
   }

   private static FileLock getFileLock(FileChannel c, long timeoutInMillis) {
      long currentTime = System.currentTimeMillis();
      long timeoutTime = currentTime + timeoutInMillis;
      FileLock fileLock = null;
      String reason = "";

      while(fileLock == null && currentTime <= timeoutTime) {
         if (isDebugEnabled()) {
            debug("Trying to get lock channel: '" + c + "' currentTime: " + currentTime + ", timeoutTime: " + timeoutTime);
         }

         try {
            fileLock = c.tryLock();
         } catch (Throwable var14) {
            reason = var14.toString();
            if (isDebugEnabled()) {
               debug("Experienced Exception while c.tryLock() and it is ignored :: " + StackTraceUtils.throwable2StackTrace(var14));
            }
         }

         if (fileLock == null) {
            long remainingTime = timeoutTime - currentTime;

            try {
               long sleepTime = (long)LOCK_WAIT_TIME_INTERVAL;
               if (isDebugEnabled()) {
                  debug("Sleeping for '" + sleepTime + "' ...");
               }

               Thread.sleep(remainingTime > sleepTime ? sleepTime : remainingTime);
            } catch (InterruptedException var13) {
            }

            currentTime = System.currentTimeMillis();
         }
      }

      if (fileLock == null) {
         if (isDebugEnabled()) {
            debug("Could not get lock on channel: '" + c + "'.");
         }

         DeploymentManagerLogger.logCouldNotGetFileLock(reason);
      } else if (isDebugEnabled()) {
         debug("Got lock on channel: '" + c + "' : " + fileLock);
      }

      return fileLock;
   }

   private void releaseFileLock(FileLock lockedFile) {
      if (lockedFile != null) {
         try {
            if (isDebugEnabled()) {
               debug(" Releasing FileLock : " + lockedFile + " -- " + System.currentTimeMillis());
            }

            lockedFile.release();
            lockedFile.channel().close();
         } catch (IOException var3) {
         }
      }

   }

   private final void handleBackup(MultiDataStream streams) throws IOException {
      CopyLockInfo lock = this.getCopyLock();
      if (lock != null && !lock.isUpdateNeeded()) {
         if (isDebugEnabled()) {
            debug("Not taking backup for update '" + this + "' - since copy lock acquired by some other server");
         }

      } else {
         Iterator targetPaths = getAllTargetPaths(streams).iterator();
         if (targetPaths.hasNext()) {
            synchronized(this) {
               while(targetPaths.hasNext()) {
                  String targetPath = (String)targetPaths.next();
                  File destFile = new File(this.getLocalData().getRootLocation(), targetPath);

                  try {
                     if (isDebugEnabled()) {
                        debug("Saving file '" + destFile.getAbsolutePath() + "' to config_bak");
                     }

                     backupManager.handleBackup(destFile, targetPath);
                     if (isDebugEnabled()) {
                        debug("Saved file '" + destFile.getAbsolutePath() + "' to config_bak");
                     }
                  } catch (IOException var9) {
                     backupManager.deleteConfigBakFile(targetPath);
                     this.restoreFromBackup();
                     throw var9;
                  }
               }

            }
         }
      }
   }

   private static List getAllTargetPaths(MultiDataStream streams) {
      List results = new ArrayList();
      if (streams != null) {
         Iterator allStreams = streams.getDataStreams();

         while(allStreams.hasNext()) {
            DataStream eachStream = (DataStream)allStreams.next();
            results.add(eachStream.getName());
         }
      }

      return results;
   }

   private class DelegatingFileDataStream extends DelegatingDataStream implements FileDataStream {
      private File referringFile = null;

      DelegatingFileDataStream(DataStream inputStream) throws IOException {
         super(inputStream);
         this.referringFile = ConfigDataUpdate.copyDataStreamToTempFile(inputStream, this.getName());
      }

      public File getFile() {
         if (this.referringFile != null) {
            return this.referringFile;
         } else {
            return this.delegate instanceof FileDataStream ? ((FileDataStream)this.delegate).getFile() : null;
         }
      }

      public int getLength() throws IOException {
         File file = this.getFile();
         return file != null ? (int)file.length() : 0;
      }

      public InputStream getInputStream() throws IOException {
         return (InputStream)(this.referringFile != null ? new FileInputStream(this.referringFile) : super.getInputStream());
      }

      public void close() {
         if (this.referringFile != null) {
            if (DataUpdate.isDebugEnabled()) {
               DataUpdate.debug("Removing temporary file : " + this.referringFile.getAbsolutePath());
            }

            this.referringFile.delete();
         } else {
            super.close();
         }

      }
   }

   private class DelegatingDataStream implements DataStream {
      protected final DataStream delegate;

      DelegatingDataStream(DataStream inputStream) {
         this.delegate = inputStream;
      }

      public String getName() {
         return this.delegate.getName();
      }

      public boolean isZip() {
         return this.delegate.isZip();
      }

      public InputStream getInputStream() throws IOException {
         return this.delegate.getInputStream();
      }

      public void close() {
         this.delegate.close();
      }
   }

   private class ReadOnlyMultiDataStream implements MultiDataStream {
      List allStreams = new ArrayList();

      ReadOnlyMultiDataStream(MultiDataStream inputStreams) throws IOException {
         FileLock configLock = null;

         try {
            if (ConfigDataUpdate.this.isAdminServer() && !ConfigDataUpdate.this.handleCAMConfigFilesOnly) {
               configLock = ConfigDataUpdate.this.acquireFileLock(ConfigDataUpdate.this.getLocalData().getLockPath());
            }

            Iterator allInputStreams = inputStreams.getDataStreams();

            while(allInputStreams.hasNext()) {
               DataStream eachStream = (DataStream)allInputStreams.next();
               String streamName = eachStream.getName();
               final String targetName = this.getTargetName(streamName, eachStream);
               this.allStreams.add(new DelegatingFileDataStream((FileDataStream)eachStream) {
                  public String getName() {
                     return targetName;
                  }
               });
            }
         } finally {
            if (!ConfigDataUpdate.this.handleCAMConfigFilesOnly) {
               ConfigDataUpdate.this.releaseFileLock(configLock);
            }

         }

      }

      private String getTargetName(String streamName, DataStream eachStream) {
         String target = null;
         if (eachStream instanceof TargetFileDataStream) {
            TargetFileDataStream tfds = (TargetFileDataStream)eachStream;
            target = "".equals(tfds.getTarget()) ? null : tfds.getTarget();
         }

         if (target == null) {
            int pendingIndex = !streamName.startsWith("pending") && !streamName.startsWith("partitions") && !streamName.startsWith("edit") ? -1 : streamName.lastIndexOf("pending");
            target = pendingIndex != -1 ? "config" + streamName.substring(pendingIndex + 7, streamName.length()) : streamName;
         }

         return target;
      }

      public int getSize() {
         return this.allStreams.size();
      }

      public Iterator getDataStreams() {
         return this.allStreams.iterator();
      }

      public Iterator getInputStreams() throws IOException {
         List inputStreams = new ArrayList();
         Iterator iter = this.getDataStreams();

         while(iter.hasNext()) {
            inputStreams.add(((DataStream)iter.next()).getInputStream());
         }

         return inputStreams.iterator();
      }

      public void close() {
         Iterator streams = this.getDataStreams();

         while(streams.hasNext()) {
            DataStream stream = (DataStream)streams.next();
            stream.close();
         }

      }

      public void addDataStream(DataStream stream) {
         throw new UnsupportedOperationException("[ReadOnly].addDataStream(DataStream) unsupported");
      }

      public void addFileDataStream(String name, File file, boolean isZip) {
         throw new UnsupportedOperationException("[ReadOnly].addFileDataStream(String, File, boolean) unsupported");
      }

      public void addFileDataStream(String name, File file, String targetFile, boolean isZip) {
         throw new UnsupportedOperationException("[ReadOnly].addFileDataStream(String, File, File, boolean) unsupported");
      }

      public void addFileDataStream(String location, boolean isZip) {
         throw new UnsupportedOperationException("[ReadOnly].addFileDataStream(String, boolean) unsupported");
      }

      public void removeDataStream(DataStream stream) {
         throw new UnsupportedOperationException("[ReadOnly].removeDataStream(DataStream) unsupported");
      }
   }

   final class CopyLockInfo {
      boolean updateIsNeeded = false;
      FileLock copyLock = null;
      File targetFile = null;
      boolean deleteFile = false;

      protected CopyLockInfo(String lockPath, long id, boolean deleteFile) {
         this.copyLock = this.acquireCopyLock(lockPath, id);
         this.deleteFile = deleteFile;
         if (DataUpdate.isDebugEnabled()) {
            DataUpdate.debug("Got lock on '" + this.targetFile.getAbsolutePath() + "' == " + System.currentTimeMillis());
         }

      }

      protected FileLock getLock() {
         return this.copyLock;
      }

      protected boolean isUpdateNeeded() {
         return this.updateIsNeeded;
      }

      protected void release() {
         if (this.copyLock != null) {
            ConfigDataUpdate.this.releaseFileLock(this.copyLock);
            this.copyLock = null;
            if (DataUpdate.isDebugEnabled()) {
               DataUpdate.debug(" Released lock on '" + this.targetFile.getAbsolutePath() + "' == " + System.currentTimeMillis());
            }

         }
      }

      protected void removeFile() {
         if (this.targetFile.exists()) {
            if (this.deleteFile) {
               this.targetFile.delete();
            } else {
               this.targetFile.deleteOnExit();
            }

            if (DataUpdate.isDebugEnabled()) {
               DataUpdate.debug(" Removed file '" + this.targetFile.getAbsolutePath() + "'");
            }
         }

      }

      private FileLock acquireCopyLock(String lockFilePath, long id) {
         FileLock configFileLock = ConfigDataUpdate.this.acquireFileLock(lockFilePath);

         FileLock var6;
         try {
            String copyLockPath = ConfigDataUpdate.this.getCopyLockFileName(id);
            this.targetFile = new File(copyLockPath);
            this.targetFile.deleteOnExit();
            if (this.targetFile.exists()) {
               ConfigDataUpdate.this.releaseFileLock(configFileLock);
               configFileLock = null;
               this.updateIsNeeded = false;
            } else {
               this.updateIsNeeded = true;
            }

            if (DataUpdate.isDebugEnabled()) {
               DataUpdate.debug("Is update required : " + this.updateIsNeeded);
            }

            var6 = ConfigDataUpdate.this.acquireFileLock(this.targetFile);
         } finally {
            ConfigDataUpdate.this.releaseFileLock(configFileLock);
         }

         return var6;
      }
   }
}
