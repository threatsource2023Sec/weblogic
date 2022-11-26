package weblogic.utils.classes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassBytes102 implements ClassBytes {
   public static final int BUF_SIZE = 512;
   protected Object source;
   protected File classpathEntry;

   public Object getSource() {
      return this.source;
   }

   protected ClassBytes102() {
   }

   public ClassBytes102(File f, File classpathEntry) {
      this.source = f;
      this.classpathEntry = classpathEntry;
   }

   public boolean isFromFile() {
      return true;
   }

   public boolean isFromZip() {
      return false;
   }

   public long getLastMod() {
      return ((File)this.source).lastModified();
   }

   public long length() {
      return ((File)this.source).length();
   }

   public InputStream getStream() throws IOException {
      return new FileInputStream((File)this.source);
   }

   public byte[] getBytes() throws IOException {
      InputStream in = this.getStream();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buf = new byte[512];

      while(true) {
         int len = in.read(buf);
         if (len == -1) {
            in.close();
            return baos.toByteArray();
         }

         baos.write(buf, 0, len);
      }
   }
}
