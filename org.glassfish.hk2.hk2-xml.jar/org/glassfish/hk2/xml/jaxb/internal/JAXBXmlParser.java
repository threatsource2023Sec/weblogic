package org.glassfish.hk2.xml.jaxb.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.spi.Model;
import org.glassfish.hk2.xml.spi.PreGenerationRequirement;
import org.glassfish.hk2.xml.spi.XmlServiceParser;

@Singleton
@Named("JAXBXmlParsingService")
@Visibility(DescriptorVisibility.LOCAL)
public class JAXBXmlParser implements XmlServiceParser {
   public Object parseRoot(Model rootModel, URI location, Unmarshaller.Listener listener, Map options) throws Exception {
      Class clazz = rootModel.getProxyAsClass();
      JAXBContext context = JAXBContext.newInstance(new Class[]{clazz});
      Unmarshaller unmarshaller = context.createUnmarshaller();
      unmarshaller.setListener(listener);
      Object root = unmarshaller.unmarshal(location.toURL());
      return root;
   }

   public Object parseRoot(Model rootModel, InputStream input, Unmarshaller.Listener listener, Map options) throws Exception {
      Class clazz = rootModel.getProxyAsClass();
      JAXBContext context = JAXBContext.newInstance(new Class[]{clazz});
      Unmarshaller unmarshaller = context.createUnmarshaller();
      unmarshaller.setListener(listener);
      Object root = unmarshaller.unmarshal(input);
      return root;
   }

   public PreGenerationRequirement getPreGenerationRequirement() {
      return PreGenerationRequirement.MUST_PREGENERATE;
   }

   public void marshal(OutputStream outputStream, XmlRootHandle rootHandle, Map options) throws IOException {
      Object root = rootHandle.getRoot();
      if (root != null) {
         XmlHk2ConfigurationBean xmlBean = (XmlHk2ConfigurationBean)root;
         Model model = xmlBean._getModel();
         Class clazz = model.getProxyAsClass();

         try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{clazz});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.marshal(root, outputStream);
         } catch (RuntimeException var10) {
            throw new IOException(var10);
         } catch (Exception var11) {
            throw new IOException(var11);
         }
      }
   }
}
