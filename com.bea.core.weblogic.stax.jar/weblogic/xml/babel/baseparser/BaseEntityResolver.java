package weblogic.xml.babel.baseparser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.babel.reader.XmlReader;

public class BaseEntityResolver {
   public InputSource resolveEntity(String publicID, String systemID) throws SAXException {
      try {
         InputSource inputSource = new InputSource();
         inputSource.setPublicId(publicID);
         inputSource.setSystemId(systemID);

         try {
            InputStream stream = new FileInputStream(systemID);
            inputSource.setCharacterStream(XmlReader.createReader(stream));
            return inputSource;
         } catch (IOException var6) {
            URL url = new URL(systemID);
            InputStream stream = new BufferedInputStream(url.openStream());
            inputSource.setCharacterStream(XmlReader.createReader(stream));
            return inputSource;
         }
      } catch (Exception var7) {
         throw new SAXException(var7);
      }
   }
}
