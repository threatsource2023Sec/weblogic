package weblogic.utils.classloaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import weblogic.utils.io.DataIO;

public final class FileSource implements Source {
   private static final int GET_BYTES_RETRIES = 3;
   private final File file;
   private final String codeBase;

   public FileSource(String codeBase, File file) {
      this.file = file;
      this.codeBase = codeBase;
   }

   public InputStream getInputStream() throws IOException {
      return new FileInputStream(this.file);
   }

   public URL getURL() {
      return this.getURL(this.file);
   }

   private URL getURL(File f) {
      if (f.exists()) {
         try {
            return (new File(f.toString().replaceAll("#", "%23"))).toURL();
         } catch (MalformedURLException var3) {
         }
      }

      return null;
   }

   public URL getCodeSourceURL() {
      return this.getURL(new File(this.codeBase));
   }

   public byte[] getBytes() throws IOException {
      int i = 0;

      while(i < 3) {
         try {
            return this.getBytesInternal();
         } catch (IndexOutOfBoundsException var6) {
            try {
               Thread.sleep((long)((int)(Math.random() * (double)(100 ^ i))));
            } catch (InterruptedException var5) {
            }

            ++i;
         }
      }

      try {
         return this.getBytesInternal();
      } catch (IndexOutOfBoundsException var4) {
         throw new IOException("Could not read from: '" + this.file + "' because some other process was writing it");
      }
   }

   private byte[] getBytesInternal() throws IOException {
      byte[] buf = new byte[(int)this.length()];
      InputStream in = this.getInputStream();
      DataIO.readFully(in, buf);
      in.close();
      return buf;
   }

   public long lastModified() {
      return this.file.lastModified();
   }

   public long length() {
      return this.file.length();
   }

   public String toString() {
      return this.file != null ? this.file.toString() : "Unknown FileSource";
   }

   public File getFile() {
      return this.file;
   }

   public String getCodeBase() {
      return this.codeBase;
   }
}
