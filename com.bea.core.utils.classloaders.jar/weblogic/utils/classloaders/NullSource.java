package weblogic.utils.classloaders;

import java.io.InputStream;
import java.net.URL;
import weblogic.utils.io.NullInputStream;

public class NullSource implements Source {
   public InputStream getInputStream() {
      return new NullInputStream();
   }

   public URL getURL() {
      return null;
   }

   public URL getCodeSourceURL() {
      return null;
   }

   public byte[] getBytes() {
      return new byte[0];
   }

   public long lastModified() {
      return -1L;
   }

   public long length() {
      return 0L;
   }
}
