package weblogic.utils.classes;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassBytes11 extends ClassBytes102 {
   public static final int BUF_SIZE = 512;
   protected ZipFile zf;

   public ClassBytes11(ZipEntry z, ZipFile zf) {
      this.source = z;
      this.zf = zf;
   }

   public boolean isFromFile() {
      return false;
   }

   public boolean isFromZip() {
      return true;
   }

   public long getLastMod() {
      return ((ZipEntry)this.source).getTime();
   }

   public long length() {
      return ((ZipEntry)this.source).getSize();
   }

   public InputStream getStream() throws IOException {
      return this.zf.getInputStream((ZipEntry)this.source);
   }
}
