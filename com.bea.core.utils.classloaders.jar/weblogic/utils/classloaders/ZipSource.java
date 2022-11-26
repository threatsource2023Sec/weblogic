package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import weblogic.utils.io.DataIO;
import weblogic.utils.zip.Handler;

public class ZipSource implements Source {
   private final ZipFile zf;
   private final ZipEntry ze;
   private boolean isFailureLogged = false;

   public ZipSource(ZipFile zf, ZipEntry ze) {
      this.zf = zf;
      this.ze = ze;
   }

   public InputStream getInputStream() throws IOException {
      return this.zf.getInputStream(this.ze);
   }

   public URL getURL() {
      String zipFileName = this.zf.getName().replace(File.separatorChar, '/').replaceAll("#", "%23");
      String zipurl = zipFileName + '!' + '/' + this.ze.getName();

      try {
         return new URL("zip", "", zipurl);
      } catch (MalformedURLException var6) {
         try {
            return new URL("jar", "", (new File(zipurl)).toURL().toString());
         } catch (MalformedURLException var5) {
            return null;
         }
      }
   }

   public URL getCodeSourceURL() {
      try {
         File zip = new File(this.zf.getName());
         return zip.toURL();
      } catch (MalformedURLException var2) {
         return null;
      }
   }

   public void getBytes(byte[] b) throws IOException {
      try {
         InputStream in = this.getInputStream();
         DataIO.readFully(in, b, 0, (int)this.length());
         in.close();
      } catch (IOException var3) {
         if (this.isFailureLogged) {
            throw var3;
         } else {
            this.isFailureLogged = true;
            ClassFinderUtils.checkArchive((new File(this.zf.getName())).getCanonicalPath(), var3);
            throw var3;
         }
      }
   }

   public byte[] getBytes() throws IOException {
      try {
         byte[] buf = new byte[(int)this.length()];
         InputStream in = this.getInputStream();
         DataIO.readFully(in, buf);
         in.close();
         return buf;
      } catch (IOException var3) {
         if (this.isFailureLogged) {
            throw var3;
         } else {
            this.isFailureLogged = true;
            ClassFinderUtils.checkArchive((new File(this.zf.getName())).getCanonicalPath(), var3);
            throw var3;
         }
      }
   }

   public long lastModified() {
      return this.ze.getTime();
   }

   public long length() {
      return this.ze.getSize();
   }

   public String toString() {
      return this.zf != null ? this.zf.getName() : "ZIP file " + this.getURL();
   }

   public ZipEntry getEntry() {
      return this.ze;
   }

   public ZipFile getFile() {
      return this.zf;
   }

   static {
      try {
         Handler.init();
      } catch (Throwable var1) {
         var1.printStackTrace();
      }

   }
}
