package weblogic.management.provider.internal.situationalconfig;

import java.io.File;
import java.util.ArrayList;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.utils.fileobserver.FileChangeListener;
import weblogic.management.utils.fileobserver.FileChangeObserver;
import weblogic.management.utils.situationalconfig.SituationalConfigFileProcessor;

public class DirectoryListener implements FileChangeListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private ArrayList addedFiles = new ArrayList();
   private ArrayList modifiedFiles = new ArrayList();
   private ArrayList deletedFiles = new ArrayList();
   private SituationalConfigFileProcessor situationalConfigFileProcessor;

   public DirectoryListener(SituationalConfigFileProcessor situationalConfigFileProcessor) {
      this.situationalConfigFileProcessor = situationalConfigFileProcessor;
   }

   public void onStart(FileChangeObserver fileChangeObserver) {
      this.addedFiles.clear();
      this.modifiedFiles.clear();
      this.deletedFiles.clear();
   }

   public void onDirectoryCreate(File directory) {
   }

   public void onDirectoryChange(File directory) {
   }

   public void onDirectoryDelete(File directory) {
   }

   public void onFileCreate(File file) {
      this.addedFiles.add(file);
   }

   public void onFileChange(File file) {
      this.modifiedFiles.add(file);
   }

   public void onFileDelete(File file) {
      this.deletedFiles.add(file);
   }

   public void onStop(FileChangeObserver fileChangeObserver) {
      try {
         this.situationalConfigFileProcessor.processFiles(this.addedFiles, this.modifiedFiles, this.deletedFiles);
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] Exception occurred executing onStop for the Situational Config directory listener: " + var3, var3);
         }
      }

      this.addedFiles.clear();
      this.modifiedFiles.clear();
      this.deletedFiles.clear();
   }
}
