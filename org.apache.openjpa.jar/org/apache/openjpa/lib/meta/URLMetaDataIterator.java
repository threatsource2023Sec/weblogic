package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class URLMetaDataIterator implements MetaDataIterator {
   private final URL _url;
   private boolean _iterated = false;

   public URLMetaDataIterator(URL url) {
      this._url = url;
   }

   public boolean hasNext() {
      return this._url != null && !this._iterated;
   }

   public Object next() throws IOException {
      if (!this.hasNext()) {
         throw new IllegalStateException();
      } else {
         this._iterated = true;
         return this._url;
      }
   }

   public InputStream getInputStream() throws IOException {
      if (!this._iterated) {
         throw new IllegalStateException();
      } else if (this._url == null) {
         return null;
      } else {
         try {
            return (InputStream)AccessController.doPrivileged(J2DoPrivHelper.openStreamAction(this._url));
         } catch (PrivilegedActionException var2) {
            throw (IOException)var2.getException();
         }
      }
   }

   public File getFile() {
      if (!this._iterated) {
         throw new IllegalStateException();
      } else if (this._url == null) {
         return null;
      } else {
         File file = new File(URLDecoder.decode(this._url.getPath()));
         return (Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file)) ? file : null;
      }
   }

   public void close() {
   }
}
