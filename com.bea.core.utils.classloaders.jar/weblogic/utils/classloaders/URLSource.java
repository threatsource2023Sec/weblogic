package weblogic.utils.classloaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.StreamUtils;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public class URLSource implements Source {
   private final URLConnection conn;

   public URLSource(URL url) throws IOException {
      this.conn = url.openConnection();
   }

   public InputStream getInputStream() throws IOException {
      return this.conn.getInputStream();
   }

   public URL getURL() {
      return this.conn.getURL();
   }

   public URL getCodeSourceURL() {
      return this.getURL();
   }

   public byte[] getBytes() throws IOException {
      InputStream in = this.getInputStream();

      byte[] var4;
      try {
         int len = (int)this.length();
         if (len == -1) {
            UnsyncByteArrayOutputStream out = new UnsyncByteArrayOutputStream();
            StreamUtils.writeTo(in, out);
            var4 = out.toByteArray();
            return var4;
         }

         byte[] res = new byte[len];
         DataIO.readFully(in, res);
         var4 = res;
      } finally {
         in.close();
      }

      return var4;
   }

   public long lastModified() {
      return this.conn.getLastModified();
   }

   public long length() {
      return (long)this.conn.getContentLength();
   }
}
