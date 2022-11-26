package weblogic.management.mbeanservers.internal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.mbeanservers.edit.RecordingException;
import weblogic.management.provider.EditAccess;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ConcurrentHashMap;

public class RecordingManager {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private String recordingFileName;
   private String configFileName;
   private String secretFileName;
   private boolean recording = false;
   private String lastCD = "";
   private String lastCommand = "";
   private boolean verbose = false;
   private static ConcurrentHashMap managerMap = new ConcurrentHashMap();

   RecordingManager() {
   }

   public static RecordingManager getInstance(EditAccess editAccess) {
      RecordingManager freshOne = new RecordingManager();
      RecordingManager previousOne = (RecordingManager)managerMap.putIfAbsent(new EditAccessCompoundKey(editAccess), freshOne);
      if (previousOne == null) {
         editAccess.register(RecordingManager.EditAccessEventListener.INSTANCE);
         return freshOne;
      } else {
         return previousOne;
      }
   }

   public synchronized void startRecording(String fileName, boolean append) throws RecordingException {
      HashMap options = new HashMap();
      options.put("append", append ? "true" : "false");
      this.startRecording(fileName, options);
   }

   public synchronized void startRecording(String fileName, Map options) throws RecordingException {
      if (this.isRecording()) {
         throw new RecordingException(ManagementTextTextFormatter.getInstance().getRecordingAlreadyStarted());
      } else if (StringUtils.isEmptyString(fileName)) {
         throw new RecordingException(ManagementTextTextFormatter.getInstance().getMissingRecordingFileName());
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Starting WLST script recording session. The generated scripts will be written to " + fileName);
         }

         boolean append = false;
         if ("true".equals((String)options.get("append"))) {
            append = true;
         }

         this.verbose = false;
         if ("true".equals((String)options.get("verbose"))) {
            this.verbose = true;
         }

         BufferedWriter writer = null;
         File recordingFile = new File(fileName);

         try {
            writer = new BufferedWriter(new FileWriter(recordingFile, append));
            if (this.verbose) {
               boolean isEmpty = true;
               if (recordingFile.length() > 0L) {
                  isEmpty = false;
               }

               writer.write("\n# WLST scripts recording begin time: " + (new Date()).toString() + "\n\n");
               if (isEmpty) {
                  writer.write("connect()\nedit()\n");
               }

               writer.flush();
            }
         } catch (IOException var17) {
            throw new RecordingException(ManagementTextTextFormatter.getInstance().getRecordingIOException(), var17);
         } finally {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Exception var16) {
               }
            }

         }

         this.recordingFileName = fileName;
         File parentFile = recordingFile.getParentFile();
         if (parentFile == null) {
            parentFile = new File(".");
         }

         String parentPath = parentFile.getAbsolutePath();
         parentPath = StringUtils.replaceGlobal(parentPath, File.separator, "/");
         String baseName = recordingFile.getName();
         int lastIndex = baseName.lastIndexOf(".");
         if (lastIndex != -1) {
            baseName = baseName.substring(0, lastIndex);
         }

         this.configFileName = parentPath + "/" + baseName + "Config";
         this.secretFileName = parentPath + "/" + baseName + "Secret";
         if (!append) {
            File configFile = new File(this.configFileName);
            if (configFile.exists()) {
               configFile.delete();
            }

            File secretFile = new File(this.secretFileName);
            if (secretFile.exists()) {
               secretFile.delete();
            }
         }

         this.recording = true;
      }
   }

   public synchronized void stopRecording() throws RecordingException {
      if (!this.isRecording()) {
         throw new RecordingException(ManagementTextTextFormatter.getInstance().getRecordingAlreadyStopped());
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Stopping WLST script recording session.");
         }

         BufferedWriter writer = null;

         try {
            writer = new BufferedWriter(new FileWriter(new File(this.recordingFileName), true));
            if (this.verbose) {
               writer.write("\n# WLST scripts recording end time: " + (new Date()).toString() + "\n\n");
            }

            writer.flush();
         } catch (IOException var10) {
            throw new RecordingException(ManagementTextTextFormatter.getInstance().getRecordingIOException(), var10);
         } finally {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Exception var9) {
               }
            }

         }

         this.recording = false;
      }
   }

   public synchronized void record(String str) throws RecordingException {
      if (!this.isRecording()) {
         throw new RecordingException(ManagementTextTextFormatter.getInstance().getRecordFailedDueToRecordingNotStarted(str));
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Caller adds string '" + str + "' to recording file.");
         }

         try {
            this.write(str, false, false);
         } catch (IOException var3) {
            throw new RecordingException(ManagementTextTextFormatter.getInstance().getRecordingIOException(), var3);
         }
      }
   }

   public synchronized void encrypt(String attributeName, String propName, String attributeValue) throws IOException {
      System.setProperty("weblogic.management.confirmKeyfileCreation", "true");
      UserConfigFileManager.setUsernameAndPassword(new UsernameAndPassword("", attributeValue.toCharArray()), this.configFileName, this.secretFileName, propName);
      String parameterList = "'" + attributeName + "', '" + propName + "', '" + this.configFileName + "', '" + this.secretFileName + "'";
      this.write("setEncrypted(" + parameterList + ")");
   }

   public synchronized boolean isRecording() {
      return this.recording;
   }

   public synchronized String getRecordingFileName() {
      return this.recordingFileName;
   }

   public synchronized boolean isVerbose() {
      return this.verbose;
   }

   public synchronized void write(String str) throws IOException {
      this.write(str, false, true);
   }

   public synchronized void write(String str, boolean prependNewline, boolean generated) throws IOException {
      if (!this.isRecording()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Can not write to recording file since caller has not started a recording session.");
         }

         throw new NoAccessRuntimeException("Operation can not be performed as caller has not started a recording session");
      } else if (!StringUtils.isEmptyString(str)) {
         if (generated) {
            if (str.equals(this.lastCommand)) {
               return;
            }

            if (str.equals(this.lastCD)) {
               return;
            }
         }

         BufferedWriter writer = null;

         try {
            writer = new BufferedWriter(new FileWriter(new File(this.recordingFileName), true));
            if (prependNewline) {
               writer.write("\n");
            }

            writer.write(str);
            this.lastCommand = str;
            if (str.startsWith("cd(")) {
               this.lastCD = str;
            }

            writer.write("\n");
            writer.flush();
         } finally {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Exception var11) {
               }
            }

         }

      }
   }

   private static class EditAccessEventListener implements EditAccess.EventListener {
      static final EditAccessEventListener INSTANCE = new EditAccessEventListener();

      public void onDestroy(EditAccess editAccess, DomainMBean editDomainMBean) {
         RecordingManager.managerMap.remove(new EditAccessCompoundKey(editAccess));
      }
   }

   private static class EditAccessCompoundKey {
      private final String sessionName;
      private final String partitionName;

      EditAccessCompoundKey(EditAccess editAccess) {
         this.sessionName = editAccess.getEditSessionName();
         this.partitionName = editAccess.getPartitionName();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            EditAccessCompoundKey that = (EditAccessCompoundKey)o;
            return !this.sessionName.equals(that.sessionName) ? false : this.partitionName.equals(that.partitionName);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.sessionName.hashCode();
         result = 31 * result + this.partitionName.hashCode();
         return result;
      }
   }

   private static class Maker {
      private static RecordingManager SINGLETON = new RecordingManager();
   }
}
