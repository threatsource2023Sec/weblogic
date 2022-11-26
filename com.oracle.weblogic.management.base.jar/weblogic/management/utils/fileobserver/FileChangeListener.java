package weblogic.management.utils.fileobserver;

import java.io.File;

public interface FileChangeListener {
   void onStart(FileChangeObserver var1);

   void onDirectoryCreate(File var1);

   void onDirectoryChange(File var1);

   void onDirectoryDelete(File var1);

   void onFileCreate(File var1);

   void onFileChange(File var1);

   void onFileDelete(File var1);

   void onStop(FileChangeObserver var1);
}
