package weblogic.utils.classloaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DelegateSource implements Source {
   private final Source source;

   public DelegateSource(Source s) {
      this.source = s;
   }

   public Source getSource() {
      return this.source;
   }

   public InputStream getInputStream() throws IOException {
      return this.source.getInputStream();
   }

   public URL getURL() {
      return this.source.getURL();
   }

   public URL getCodeSourceURL() {
      return this.source.getCodeSourceURL();
   }

   public byte[] getBytes() throws IOException {
      return this.source.getBytes();
   }

   public long lastModified() {
      return this.source.lastModified();
   }

   public long length() {
      return this.source.length();
   }
}
