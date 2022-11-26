package weblogic.servlet.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.DescriptorCache;
import weblogic.servlet.utils.ResourceLocation;
import weblogic.xml.stax.XMLStreamInputFactory;

abstract class WebAppIOHelper implements DescriptorCache.IOHelper {
   protected final ResourceLocation resLocation;

   WebAppIOHelper(ResourceLocation resLocation) {
      this.resLocation = resLocation;
   }

   public InputStream openInputStream() throws IOException {
      return this.resLocation.getInputStream();
   }

   public Object readCachedBean(File destFile) throws IOException {
      ObjectInputStream ois = null;

      Object var3;
      try {
         ois = new ObjectInputStream(new FileInputStream(destFile));
         var3 = ois.readObject();
      } catch (ClassNotFoundException var12) {
         throw (IOException)(new IOException(var12.getMessage())).initCause(var12);
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (IOException var11) {
            }
         }

      }

      return var3;
   }

   public final Object parseXML(InputStream in) throws IOException, XMLStreamException {
      XMLInputFactory factory = XMLStreamInputFactory.newInstance();
      XMLStreamReader parser = factory.createXMLStreamReader(in);
      return this.parseXMLInternal(parser);
   }

   protected abstract Object parseXMLInternal(XMLStreamReader var1) throws IOException, XMLStreamException;

   public boolean useCaching() {
      return true;
   }
}
