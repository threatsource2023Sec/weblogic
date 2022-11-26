package net.shibboleth.utilities.java.support.xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ClasspathResolver implements EntityResolver, LSResourceResolver {
   public static final String CLASSPATH_URI_SCHEME = "classpath:";
   private final Logger log = LoggerFactory.getLogger(ClasspathResolver.class);

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      InputStream resourceStream = this.resolver(publicId, systemId);
      if (resourceStream != null) {
         InputSource is = new InputSource(resourceStream);
         is.setSystemId(systemId);
         is.setPublicId(publicId);
         return is;
      } else {
         return null;
      }
   }

   public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
      return new LSInputImpl(publicId, systemId, this.resolver(publicId, systemId));
   }

   protected InputStream resolver(String publicId, String systemId) {
      String resource = null;
      InputStream resourceIns = null;
      if (systemId.startsWith("classpath:")) {
         this.log.trace("Attempting to resolve, within the classpath, the entity with the following system id: {}", systemId);
         resource = systemId.replaceFirst("classpath:", "");
         resourceIns = this.getClass().getResourceAsStream(resource);
      }

      if (resourceIns == null && publicId != null && publicId.startsWith("classpath:")) {
         this.log.trace("Attempting to resolve, within the classpath, the entity with the following public id: {}", resource);
         resource = publicId.replaceFirst("classpath:", "");
         resourceIns = this.getClass().getResourceAsStream(resource);
      }

      if (resourceIns == null) {
         this.log.trace("Entity was not resolved from classpath, public id ({}), system id({})", publicId, systemId);
         return null;
      } else {
         this.log.trace("Entity resolved from classpath");
         return resourceIns;
      }
   }

   protected class LSInputImpl implements LSInput {
      private String publicId;
      private String systemId;
      private BufferedInputStream buffInput;

      public LSInputImpl(String pubId, String sysId, InputStream input) {
         this.publicId = pubId;
         this.systemId = sysId;
         this.buffInput = new BufferedInputStream(input);
      }

      public String getBaseURI() {
         return null;
      }

      public InputStream getByteStream() {
         return this.buffInput;
      }

      public boolean getCertifiedText() {
         return false;
      }

      public Reader getCharacterStream() {
         return new InputStreamReader(this.buffInput);
      }

      public String getEncoding() {
         return null;
      }

      public String getPublicId() {
         return this.publicId;
      }

      public String getStringData() {
         synchronized(this.buffInput) {
            String var10000;
            try {
               this.buffInput.reset();
               byte[] input = new byte[this.buffInput.available()];
               this.buffInput.read(input);
               var10000 = new String(input);
            } catch (IOException var4) {
               return null;
            }

            return var10000;
         }
      }

      public String getSystemId() {
         return this.systemId;
      }

      public void setBaseURI(String uri) {
      }

      public void setByteStream(InputStream byteStream) {
      }

      public void setCertifiedText(boolean isCertifiedText) {
      }

      public void setCharacterStream(Reader characterStream) {
      }

      public void setEncoding(String encoding) {
      }

      public void setPublicId(String id) {
      }

      public void setStringData(String stringData) {
      }

      public void setSystemId(String id) {
      }
   }
}
