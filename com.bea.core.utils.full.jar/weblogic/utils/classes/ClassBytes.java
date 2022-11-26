package weblogic.utils.classes;

import java.io.IOException;
import java.io.InputStream;

public interface ClassBytes {
   boolean isFromFile();

   boolean isFromZip();

   long getLastMod();

   long length();

   InputStream getStream() throws IOException;

   byte[] getBytes() throws IOException;
}
