package weblogic.management.utils.fileobserver;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FileChangeObserverDirectories extends FileChangeObserverBase {
   private FileFilter fileFilter;
   private HashSet rootFileEntries;

   public FileChangeObserverDirectories(Set dirsToObserve) {
      this((Set)dirsToObserve, (FileFilter)null, (FileChangeListener[])null);
   }

   public FileChangeObserverDirectories(Set dirsToObserve, FileFilter fileFilter) {
      this((Set)dirsToObserve, fileFilter, (FileChangeListener[])null);
   }

   public FileChangeObserverDirectories(Set dirsToObserve, FileChangeListener... listeners) {
      this((Set)dirsToObserve, (FileFilter)null, listeners);
   }

   public FileChangeObserverDirectories(File dirToObserve, FileFilter fileFilter, FileChangeListener... listeners) {
      super(listeners);
      this.rootFileEntries = new HashSet();
      HashSet dirs = new HashSet();
      if (dirToObserve != null) {
         dirs.add(dirToObserve);
      }

      this.init(dirs, fileFilter);
   }

   public FileChangeObserverDirectories(Set dirsToObserve, FileFilter fileFilter, FileChangeListener... listeners) {
      super(listeners);
      this.rootFileEntries = new HashSet();
      this.init(dirsToObserve, fileFilter);
   }

   private void init(Set dirsToObserve, FileFilter fileFilter) {
      if (dirsToObserve != null && dirsToObserve.size() != 0) {
         Iterator var3 = dirsToObserve.iterator();

         while(var3.hasNext()) {
            File oneDir = (File)var3.next();
            if (oneDir.exists() && !oneDir.isDirectory()) {
               String dirName;
               try {
                  dirName = oneDir.getCanonicalPath();
               } catch (IOException var7) {
                  dirName = oneDir.getName();
               }

               throw new IllegalStateException(dirName + " must be a directory.");
            }

            FileEntry rootFileEntry = new FileEntry(oneDir);
            rootFileEntry.refresh(rootFileEntry.getFile());
            FileEntry[] children = this.getFilesForDirectory(rootFileEntry.getFile(), rootFileEntry);
            rootFileEntry.setChildren(children);
            this.rootFileEntries.add(rootFileEntry);
         }

         this.fileFilter = fileFilter;
      } else {
         throw new IllegalStateException("There must be a directory to observe.");
      }
   }

   private FileEntry[] getFilesForDirectory(File file, FileEntry entry) {
      File[] files = this.getFilesForDirectory(file);
      FileEntry[] children = new FileEntry[files.length];

      for(int i = 0; i < files.length; ++i) {
         children[i] = this.createFileEntry(entry, files[i]);
      }

      return children;
   }

   private FileEntry createFileEntry(FileEntry parent, File file) {
      FileEntry entry = parent.newChildInstance(file);
      entry.refresh(file);
      return entry;
   }

   private File[] getFilesForDirectory(File file) {
      File[] children = null;
      if (file.isDirectory() && file.exists()) {
         if (this.fileFilter == null) {
            children = file.listFiles();
         } else {
            children = file.listFiles(this.fileFilter);
         }
      }

      if (children == null) {
         children = new File[0];
      }

      return children;
   }

   protected void observeFileSystemChanges() {
      FileEntry rootFileEntry;
      for(Iterator iterator = this.rootFileEntries.iterator(); iterator.hasNext(); this.observeFileSystemChanges(rootFileEntry)) {
         rootFileEntry = (FileEntry)iterator.next();
         if (!rootFileEntry.isExists()) {
            rootFileEntry.refresh(rootFileEntry.getFile());
         }
      }

   }

   private synchronized void observeFileSystemChanges(FileEntry parent) {
      Map previousFileEntries = parent.getChildren();
      Map currentFileEntries = new HashMap();
      File[] curFiles = this.getFilesForDirectory(parent.getFile());
      File[] var5 = curFiles;
      int var6 = curFiles.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         File oneFile = var5[var7];
         FileEntry prevEntry = (FileEntry)previousFileEntries.remove(oneFile);
         if (prevEntry != null) {
            this.doMatch(prevEntry, oneFile);
            currentFileEntries.put(prevEntry.getFile(), prevEntry);
         } else {
            FileEntry newEntry = this.createFileEntry(parent, oneFile);
            this.doCreate(newEntry);
            currentFileEntries.put(newEntry.getFile(), newEntry);
         }
      }

      Iterator var11 = previousFileEntries.values().iterator();

      while(var11.hasNext()) {
         FileEntry entry = (FileEntry)var11.next();
         this.doDelete(entry);
      }

      parent.setChildren((Map)currentFileEntries);
   }

   public FileFilter getFileFilter() {
      return this.fileFilter;
   }

   Set getRootFileEntries() {
      HashSet set = new HashSet();
      set.addAll(this.rootFileEntries);
      return set;
   }
}
