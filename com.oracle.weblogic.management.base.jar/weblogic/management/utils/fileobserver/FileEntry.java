package weblogic.management.utils.fileobserver;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FileEntry implements Serializable {
   private final FileEntry parent;
   private Map children;
   private final File file;
   private boolean exists;
   private boolean directory;
   private long lastModified;
   private long length;

   public FileEntry(File file) {
      this((FileEntry)null, file);
   }

   public FileEntry(FileEntry parent, File file) {
      this.children = new HashMap();
      if (file == null) {
         throw new IllegalArgumentException("file cannot be null.");
      } else {
         this.file = file;
         this.parent = parent;
         this.refresh(file);
      }
   }

   public boolean refresh(File file) {
      boolean origExists = this.exists;
      long origLastModified = this.lastModified;
      boolean origDirectory = this.directory;
      long origLength = this.length;
      this.exists = file.exists();
      this.directory = this.exists && file.isDirectory();
      this.lastModified = this.exists ? file.lastModified() : 0L;
      this.length = this.exists && !this.directory ? file.length() : 0L;
      return this.exists != origExists || this.lastModified != origLastModified || this.directory != origDirectory || this.length != origLength;
   }

   public FileEntry newChildInstance(File file) {
      return new FileEntry(this, file);
   }

   public FileEntry getParent() {
      return this.parent;
   }

   public int getLevel() {
      return this.parent == null ? 0 : this.parent.getLevel() + 1;
   }

   public Map getChildren() {
      return this.children;
   }

   public void setChildren(FileEntry[] children) {
      HashMap temp = new HashMap();
      if (children != null) {
         FileEntry[] var3 = children;
         int var4 = children.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            FileEntry entry = var3[var5];
            temp.put(entry.getFile(), entry);
         }
      }

      this.setChildren((Map)temp);
   }

   public void setChildren(Map children) {
      this.children = children;
   }

   public File getFile() {
      return this.file;
   }

   public boolean isExists() {
      return this.exists;
   }

   public boolean isDirectory() {
      return this.directory;
   }
}
