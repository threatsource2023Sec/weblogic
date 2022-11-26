package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.DataStream;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.management.DeploymentException;
import weblogic.t3.srvr.FileOwnerFixer;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.jars.JarFileUtils;

public abstract class DataUpdate {
   public static final int STATE_UNINITIALIZED = -1;
   public static final int STATE_INITIALIZED = 0;
   public static final int STATE_PREPARED = 1;
   public static final int STATE_COMMITTED = 2;
   private int state = -1;
   private List requestedFiles = null;
   private List targetFiles = null;
   private MultiDataStream downloadedStream = null;
   private long requestId = 0L;
   private Data localData = null;

   protected DataUpdate(Data localData, DataUpdateRequestInfo requestInfo) {
      this.init(localData, requestInfo);
   }

   public final void download(String handlerType) throws DeploymentException {
      int currentState = this.getState();
      if (currentState != 0) {
         throw new IllegalStateException("Invalid state transition - download on : " + this);
      } else {
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.download() - preparing data update on : " + this);
         }

         this.doDownload(handlerType);
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.download() - prepared data update on : " + this);
         }

         this.setState(1);
      }
   }

   public final void update() throws DeploymentException {
      int currentState = this.getState();
      if (currentState != 1) {
         throw new IllegalStateException("Invalid state transition - commit on " + this);
      } else {
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.commit() - committing data update on : " + this);
         }

         this.doUpdate();
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.commit() - committed data update on : " + this);
         }

         this.setState(2);
      }
   }

   public final void cancel() {
      int currentState = this.getState();
      if (currentState >= 1) {
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.cancel() - cancelling data update on : " + this);
         }

         this.doCancel();
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.cancel() - cancelling data update on : " + this);
         }

      }
   }

   public final void close(boolean success) {
      if (isDebugEnabled()) {
         debug(" +++ DataUpdate.close() - closing data update on : " + this);
      }

      this.doClose(success);
      if (isDebugEnabled()) {
         debug(" +++ DataUpdate.close() - closed data update on : " + this);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toString()).append("(localData=");
      sb.append(this.localData).append(", state=").append(this.getStateAsString());
      sb.append(")");
      return sb.toString();
   }

   protected final MultiDataStream getDownloadedStream() {
      if (this.getState() < 1) {
         throw new IllegalStateException("Cannot get downloaded stream since update is not prepared");
      } else {
         return this.downloadedStream;
      }
   }

   protected final List getRequestedFiles() {
      if (this.getState() < 0) {
         throw new IllegalStateException("Cannot get requested files since update is not prepared");
      } else {
         return this.requestedFiles;
      }
   }

   protected final List getTargetFiles() {
      if (this.getState() < 0) {
         throw new IllegalStateException("Cannot get target files since update is not prepared");
      } else {
         return this.targetFiles;
      }
   }

   protected final long getRequestId() {
      if (this.getState() < 0) {
         throw new IllegalStateException("Cannot get requestId since update is not prepared");
      } else {
         return this.requestId;
      }
   }

   protected final Data getLocalData() {
      if (this.getState() < 0) {
         throw new IllegalStateException("Cannot get local data since update is not prepared");
      } else {
         return this.localData;
      }
   }

   protected final void setLocalData(Data data) {
      this.localData = data;
   }

   protected final void setRequestId(long id) {
      this.requestId = id;
   }

   protected final void setRequestedFiles(List files) {
      this.requestedFiles = new ArrayList();
      this.requestedFiles.addAll(files);
   }

   protected final void setTargetFiles(List files) {
      this.targetFiles = new ArrayList(files);
   }

   protected final void setDownloadedStream(MultiDataStream stream) {
      this.downloadedStream = stream;
   }

   protected void updateLocalData(DataStream stream) throws IOException {
      try {
         String streamName = stream.getName();
         File destFile = this.selectDestFile(streamName);
         this.copyOrExtractTo(stream, destFile);
      } finally {
         stream.close();
      }

   }

   protected String selectDataLocation() {
      return this.getLocalData().getLocation();
   }

   protected File selectDestFile(String streamName) {
      return new File(this.selectDataLocation(), this.selectDestFileName(streamName));
   }

   protected String selectDestFileName(String streamName) {
      return streamName;
   }

   protected final void copyOrExtractTo(DataStream stream, File destFile) throws IOException {
      boolean needsExtract = stream.isZip();
      if (needsExtract) {
         needsExtract = !destFile.exists() || destFile.isDirectory();
      }

      if (isDebugEnabled()) {
         debug("copyOrExtractTo() : needsExtract = " + needsExtract);
      }

      FileOwnerFixer.addPathJDK6(destFile);
      if (stream instanceof FileDataStream) {
         File fromFile = ((FileDataStream)stream).getFile();
         if (needsExtract) {
            if (isDebugEnabled()) {
               debug("copyOrExtractTo() : extracting '" + fromFile.getAbsolutePath() + "' - '" + destFile.getAbsolutePath() + "'");
            }

            this.extract(fromFile, destFile);
         } else {
            if (isDebugEnabled()) {
               debug("copyOrExtractTo() : copying '" + fromFile.getAbsolutePath() + "' - '" + destFile.getAbsolutePath() + "'");
            }

            this.copy(stream.getInputStream(), destFile);
            FileUtils.setPosixFilePermissions(destFile, FileUtils.getPosixFilePermissions(fromFile));
         }
      } else {
         InputStream in = stream.getInputStream();
         if (needsExtract) {
            this.extract(in, destFile);
         } else {
            this.copy(in, destFile);
         }
      }

   }

   protected final void extract(File fromFile, File toFile) throws IOException {
      try {
         JarFileUtils.extract(fromFile, toFile);
      } catch (IOException var4) {
         debug(" Exception occured while copying to '" + toFile.getAbsolutePath() + " :: " + StackTraceUtils.throwable2StackTrace(var4));
         throw var4;
      }
   }

   protected final void extract(InputStream in, File toFile) throws IOException {
      try {
         JarFileUtils.extract(in, toFile);
      } catch (IOException var11) {
         debug(" Exception occured while copying to '" + toFile.getAbsolutePath() + " :: " + StackTraceUtils.throwable2StackTrace(var11));
         throw var11;
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var10) {
            }
         }

      }

   }

   protected final void copy(File fromFile, File toFile) throws IOException {
      try {
         FileUtils.copyPreservePermissions(fromFile, toFile);
      } catch (IOException var4) {
         debug(" Exception occured while copying to '" + toFile.getAbsolutePath() + " :: " + StackTraceUtils.throwable2StackTrace(var4));
         throw var4;
      }
   }

   protected final void copy(InputStream in, File toFile) throws IOException {
      try {
         if (isDebugEnabled()) {
            debug("copy() : toFile: " + toFile.getAbsolutePath());
         }

         File parent = toFile.getParentFile();
         if (isDebugEnabled()) {
            debug("copy() : parent: " + toFile.getAbsolutePath());
         }

         boolean isParentExists = parent.exists();
         if (!isParentExists) {
            isParentExists = parent.mkdirs();
         }

         if (!isParentExists) {
            throw new IOException("Could not create parent directory : " + parent.getAbsolutePath());
         }

         FileUtils.writeToFile(in, toFile);
         if (isDebugEnabled()) {
            debug("copy() : wrote to : " + toFile);
         }
      } catch (IOException var12) {
         debug(" Exception occured while copying to '" + toFile.getAbsolutePath() + " :: " + StackTraceUtils.throwable2StackTrace(var12));
         throw var12;
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var11) {
            }
         }

      }

   }

   protected void doInit(Data localData, DataUpdateRequestInfo reqInfo) {
      this.setLocalData(localData);
      this.setRequestedFiles(reqInfo.getDeltaFiles());
      this.setTargetFiles(reqInfo.getTargetFiles());
      this.setRequestId(reqInfo.getRequestId());
   }

   protected abstract void doDownload(String var1) throws DeploymentException;

   protected void doUpdate() throws DeploymentException {
      MultiDataStream streams = null;

      try {
         streams = this.getDownloadedStream();
         if (streams == null) {
            return;
         }

         this.backup();
         Iterator allStreams = streams.getDataStreams();

         while(allStreams.hasNext()) {
            DataStream stream = (DataStream)allStreams.next();
            this.updateLocalData(stream);
         }
      } catch (IOException var7) {
         var7.printStackTrace();
         this.restore();
         throw new DeploymentException("Exception occured while copying files", var7);
      } finally {
         if (streams != null) {
            streams.close();
         }

      }

   }

   protected abstract void doCancel();

   protected abstract void doClose(boolean var1);

   protected abstract void deleteFile(String var1);

   protected abstract File getFileFor(String var1);

   protected static void debug(String msg) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug(msg);
      }

   }

   protected static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   protected void backup() {
   }

   protected void restore() {
   }

   protected void deleteBackup() {
   }

   private final void init(Data localData, DataUpdateRequestInfo reqInfo) {
      int currentState = this.getState();
      if (currentState != -1) {
         throw new IllegalStateException("Invalid state transition - init on : " + this);
      } else {
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.init() - initializing data update with Data=" + localData + ", files=" + reqInfo.getDeltaFiles() + ", requestId=" + reqInfo.getRequestId() + " : " + this);
         }

         this.doInit(localData, reqInfo);
         if (isDebugEnabled()) {
            debug(" +++ DataUpdate.init() - initialized data update on : " + this);
         }

         this.setState(0);
      }
   }

   private final void setState(int newState) {
      boolean invalidStateTransition = false;
      switch (newState) {
         case -1:
            break;
         case 0:
            if (this.state != -1) {
               invalidStateTransition = true;
            }
            break;
         case 1:
            if (this.state != 0) {
               invalidStateTransition = true;
            }
            break;
         case 2:
            if (this.state != 1) {
               invalidStateTransition = true;
            }
            break;
         default:
            throw new IllegalStateException("Invalid state '" + newState + "'");
      }

      if (invalidStateTransition) {
         throw new IllegalStateException("Invalid state transition from " + this.state + " --> " + newState);
      } else {
         this.state = newState;
      }
   }

   private final int getState() {
      return this.state;
   }

   private final String getStateAsString() {
      return this.getStateAsString(this.state);
   }

   private final String getStateAsString(int theState) {
      switch (theState) {
         case -1:
            return "STATE_UNINITIALIZED";
         case 0:
            return "STATE_INITIALIZED";
         case 1:
            return "STATE_PREPARED";
         case 2:
            return "STATE_COMMITTED";
         default:
            throw new IllegalStateException("Invalid state");
      }
   }
}
