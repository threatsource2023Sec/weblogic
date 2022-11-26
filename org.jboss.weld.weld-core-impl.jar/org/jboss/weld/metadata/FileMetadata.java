package org.jboss.weld.metadata;

import java.net.URL;
import org.jboss.weld.bootstrap.spi.Metadata;

public class FileMetadata implements Metadata {
   private final Object value;
   private final URL file;
   private final int lineNumber;

   public FileMetadata(Object value, URL file, int lineNumber) {
      this.value = value;
      this.file = file;
      this.lineNumber = lineNumber;
   }

   public String getLocation() {
      return this.value != null ? this.file.toString() + "@" + this.lineNumber + "[" + this.value + "]" : this.file.toString() + "@" + this.lineNumber;
   }

   public Object getValue() {
      return this.value;
   }

   public URL getFile() {
      return this.file;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public String toString() {
      return this.getLocation();
   }
}
