package weblogic.application.archive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSigner;
import java.security.cert.Certificate;

public interface ApplicationArchiveEntry {
   String getName();

   boolean isDirectory();

   long getSize();

   long getTime();

   Certificate[] getCertificates();

   CodeSigner[] getCodeSigners();

   InputStream getInputStream() throws IOException;

   URL getURL() throws IOException;
}
