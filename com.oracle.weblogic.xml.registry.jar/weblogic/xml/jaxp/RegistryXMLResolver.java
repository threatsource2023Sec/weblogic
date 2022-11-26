package weblogic.xml.jaxp;

import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.InputSource;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;

public class RegistryXMLResolver implements XMLResolver {
   private RegistryEntityResolver mEntityResolver = new RegistryEntityResolver();

   public RegistryXMLResolver() throws XMLRegistryException {
   }

   public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
      if (this.mEntityResolver != null) {
         try {
            URL url = new URL(baseURI);
            String ref = (new URL(url, systemID)).toExternalForm();
            InputSource isrc = this.mEntityResolver.resolveEntity(publicID, ref);
            if (isrc != null) {
               InputStream in = isrc.getByteStream();
               if (in != null) {
                  return in;
               }

               Reader r = isrc.getCharacterStream();
               if (r != null) {
                  return r;
               }
            }

            return null;
         } catch (Exception var10) {
            throw new XMLStreamException(var10.getMessage(), var10);
         }
      } else {
         return null;
      }
   }
}
