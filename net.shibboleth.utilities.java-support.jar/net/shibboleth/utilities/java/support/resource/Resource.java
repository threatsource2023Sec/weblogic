package net.shibboleth.utilities.java.support.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import javax.annotation.Nonnull;

public interface Resource {
   boolean exists();

   boolean isReadable();

   boolean isOpen();

   URL getURL() throws IOException;

   URI getURI() throws IOException;

   File getFile() throws IOException;

   @Nonnull
   InputStream getInputStream() throws IOException;

   long contentLength() throws IOException;

   long lastModified() throws IOException;

   Resource createRelativeResource(String var1) throws IOException;

   String getFilename();

   String getDescription();
}
