package weblogic.management.provider.internal.federatedconfig;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DirectoryLister implements Iterable {
   private File dirToScan;
   private FilenameFilter filter;

   public DirectoryLister(File dirToScan, FilenameFilter filter) {
      if (dirToScan != null && dirToScan.isDirectory()) {
         this.dirToScan = dirToScan;
         this.filter = filter;
      } else {
         throw new IllegalArgumentException("The File representing the directory to scan is not a directory.");
      }
   }

   public Iterator iterator() {
      return new DirectoryIterator(this.dirToScan.list(this.filter));
   }

   private class DirectoryIterator implements Iterator {
      private String[] theFiles;
      int curPos = 0;

      public DirectoryIterator(String[] files) {
         this.theFiles = files;
      }

      public boolean hasNext() {
         return this.theFiles.length > 0 && this.curPos < this.theFiles.length;
      }

      public File next() {
         if (this.theFiles.length > 0 && this.curPos < this.theFiles.length) {
            return new File(DirectoryLister.this.dirToScan, this.theFiles[this.curPos++]);
         } else {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
