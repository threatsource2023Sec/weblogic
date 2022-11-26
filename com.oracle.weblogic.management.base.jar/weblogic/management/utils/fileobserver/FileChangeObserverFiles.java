package weblogic.management.utils.fileobserver;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileChangeObserverFiles extends FileChangeObserverBase {
   private CopyOnWriteArrayList fileEntries;

   public FileChangeObserverFiles(File... filesToObserve) {
      this(new FileChangeListener[0], filesToObserve);
   }

   public FileChangeObserverFiles(FileChangeListener listener, File... filesToObserve) {
      this(new FileChangeListener[]{listener}, filesToObserve);
   }

   public FileChangeObserverFiles(FileChangeListener[] listeners, File... filesToObserve) {
      super(listeners);
      if (filesToObserve != null && filesToObserve.length != 0) {
         this.fileEntries = new CopyOnWriteArrayList();
         File[] var3 = filesToObserve;
         int var4 = filesToObserve.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File oneFile = var3[var5];
            this.addFile(oneFile);
         }

      } else {
         throw new IllegalStateException("There must be at least 1 file to observe.");
      }
   }

   public synchronized void addFile(File file) {
      Iterator iterator = this.fileEntries.iterator();
      boolean found = false;

      while(!found && iterator.hasNext()) {
         if (((FileEntry)iterator.next()).getFile().equals(file)) {
            found = true;
         }
      }

      if (!found) {
         FileEntry fileEntry = new FileEntry(file);
         fileEntry.refresh(fileEntry.getFile());
         this.fileEntries.add(fileEntry);
      }

   }

   public synchronized void removeFile(File file) {
      FileEntry foundEntry = null;
      Iterator iterator = this.fileEntries.iterator();

      while(foundEntry == null && iterator.hasNext()) {
         foundEntry = (FileEntry)iterator.next();
         if (!foundEntry.getFile().equals(file)) {
            foundEntry = null;
         }
      }

      if (foundEntry != null) {
         this.fileEntries.remove(foundEntry);
      }

   }

   protected void observeFileSystemChanges() {
      Iterator var1 = this.fileEntries.iterator();

      while(var1.hasNext()) {
         FileEntry oneEntry = (FileEntry)var1.next();
         this.observeFileSystemChanges(oneEntry);
      }

   }

   private void observeFileSystemChanges(FileEntry fileEntry) {
      if (fileEntry.isExists()) {
         if (!fileEntry.getFile().exists()) {
            this.doDelete(fileEntry);
            fileEntry.refresh(fileEntry.getFile());
         } else if (fileEntry.refresh(fileEntry.getFile())) {
            this.doChange(fileEntry, fileEntry.getFile());
         }
      } else if (fileEntry.refresh(fileEntry.getFile())) {
         this.doCreate(fileEntry);
      }

   }

   public List getFilesToObserve() {
      return this.fileEntries;
   }
}
