package weblogic.management.utils.fileobserver;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

/** @deprecated */
@Deprecated
public class FileChangeObserverDirectory extends FileChangeObserverDirectories {
   private File dirToObserve;

   public FileChangeObserverDirectory(File dirToObserve) {
      this(dirToObserve, (FileFilter)null, (FileChangeListener[])null);
   }

   public FileChangeObserverDirectory(File dirToObserve, FileFilter fileFilter) {
      this(dirToObserve, fileFilter, (FileChangeListener[])null);
   }

   public FileChangeObserverDirectory(File dirToObserve, FileChangeListener... listeners) {
      this(dirToObserve, (FileFilter)null, listeners);
   }

   public FileChangeObserverDirectory(File dirToObserve, FileFilter fileFilter, FileChangeListener... listeners) {
      super(dirToObserve, fileFilter, listeners);
      this.dirToObserve = dirToObserve;
   }

   public File getDirToObserve() {
      return this.dirToObserve;
   }

   public FileEntry getRootFileEntry() {
      Set rootFileEntries = this.getRootFileEntries();
      return rootFileEntries.size() > 0 ? (FileEntry)rootFileEntries.iterator().next() : null;
   }
}
