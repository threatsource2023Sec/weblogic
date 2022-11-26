package org.glassfish.hk2.xml.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.spi.Model;
import org.glassfish.hk2.xml.spi.PreGenerationRequirement;
import org.glassfish.hk2.xml.spi.XmlServiceParser;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
@Named("StreamXmlParsingService")
@Rank(1)
public class DomXmlParser implements XmlServiceParser {
   private final XMLInputFactory xif = XMLInputFactory.newInstance();
   @Inject
   @Named("StreamXmlParsingService")
   private Provider xmlService;

   public Object parseRoot(Model rootModel, URI location, Unmarshaller.Listener listener, Map options) throws Exception {
      InputStream urlStream = location.toURL().openStream();

      Object var6;
      try {
         var6 = this.parseRoot(rootModel, urlStream, listener, options);
      } finally {
         urlStream.close();
      }

      return var6;
   }

   public Object parseRoot(Model rootModel, InputStream input, Unmarshaller.Listener listener, Map options) throws Exception {
      XMLStreamReader xmlStreamReader = this.xif.createXMLStreamReader(input);

      Object var6;
      try {
         var6 = XmlStreamImpl.parseRoot((XmlServiceImpl)this.xmlService.get(), rootModel, xmlStreamReader, listener);
      } finally {
         xmlStreamReader.close();
      }

      return var6;
   }

   public PreGenerationRequirement getPreGenerationRequirement() {
      return PreGenerationRequirement.LAZY_PREGENERATION;
   }

   public void marshal(OutputStream outputStream, XmlRootHandle root, Map options) throws IOException {
      XmlStreamImpl.marshall(outputStream, root);
   }
}
