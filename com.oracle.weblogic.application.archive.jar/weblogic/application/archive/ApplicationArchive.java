package weblogic.application.archive;

import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;

public interface ApplicationArchive {
   String getName();

   ApplicationArchive getApplicationArchive(String var1) throws IOException;

   Iterable list(String var1);

   Iterable list(String var1, FileFilter var2);

   Iterable find(String var1, FileFilter var2);

   ApplicationArchiveEntry getEntry(String var1);

   InputStream getInputStream(ApplicationArchiveEntry var1) throws IOException;

   Manifest getManifest() throws IOException;

   void close() throws IOException;
}
