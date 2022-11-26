package org.cryptacular.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResource implements Resource {
   private URL url;

   public URLResource(URL url) {
      if (url == null) {
         throw new IllegalArgumentException("URL cannot be null.");
      } else {
         this.url = url;
      }
   }

   public InputStream getInputStream() throws IOException {
      return this.url.openStream();
   }

   public String toString() {
      return this.url.toString();
   }
}
