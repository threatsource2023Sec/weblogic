package weblogic.utils.classloaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface Source {
   InputStream getInputStream() throws IOException;

   URL getURL();

   URL getCodeSourceURL();

   byte[] getBytes() throws IOException;

   long lastModified();

   long length();
}
