package weblogic.xml.saaj.mime4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface TempFile {
   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream() throws IOException;

   String getAbsolutePath();

   void delete();

   boolean isInMemory();

   long length();
}
