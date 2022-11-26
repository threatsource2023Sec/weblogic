package weblogic.net.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;

/** @deprecated */
@Deprecated
public final class SOAPHttpURLConnection extends HttpURLConnection {
   public SOAPHttpURLConnection(URL theURL) {
      this(theURL, (Proxy)null);
   }

   public SOAPHttpURLConnection(URL theURL, Proxy theProxy) {
      super(theURL, theProxy);
   }

   public InputStream getInputStream() throws IOException {
      try {
         return super.getInputStream();
      } catch (FileNotFoundException var2) {
         if (this.getResponseCode() == 500) {
            return this.http.getInputStream();
         } else {
            throw var2;
         }
      }
   }
}
