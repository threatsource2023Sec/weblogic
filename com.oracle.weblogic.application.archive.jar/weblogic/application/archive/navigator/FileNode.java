package weblogic.application.archive.navigator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileNode extends Node {
   protected File file;

   protected FileNode(Node parent, File file) {
      super(parent, file.getName());
      this.file = file;
   }

   public long getSize() {
      return this.file.length();
   }

   public long getTime() {
      return this.file.lastModified();
   }

   public boolean isDirectory() {
      return this.file.isDirectory();
   }

   public InputStream getInputStream() throws IOException {
      return new FileInputStream(this.file);
   }

   public File asFile() {
      return this.file;
   }
}
