package weblogic.xml.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.transform.Source;
import org.xml.sax.InputSource;
import weblogic.xml.babel.reader.XmlReader;

public final class InputFormats {
   public static Reader resolveSystemID(String systemID) throws IOException {
      try {
         InputStream stream = new FileInputStream(systemID);
         return XmlReader.createReader(stream);
      } catch (IOException var3) {
         URL url = new URL(systemID);
         InputStream stream = new BufferedInputStream(url.openStream());
         return XmlReader.createReader(stream);
      }
   }

   public static Reader resolveInputSource(InputSource source) throws IOException {
      if (source.getCharacterStream() != null) {
         return source.getCharacterStream();
      } else if (source.getByteStream() != null) {
         return XmlReader.createReader(source.getByteStream());
      } else if (source.getSystemId() != null) {
         return resolveSystemID(source.getSystemId());
      } else {
         throw new IOException("Unable to resolve input source.");
      }
   }

   public static Reader resolveSource(Source source) throws IOException {
      return resolveSystemID(source.getSystemId());
   }
}
