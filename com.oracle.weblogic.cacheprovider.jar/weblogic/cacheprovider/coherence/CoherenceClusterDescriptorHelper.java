package weblogic.cacheprovider.coherence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.ModuleException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.descriptor.DescriptorManager;

public class CoherenceClusterDescriptorHelper {
   public static WeblogicCoherenceBean createCoherenceDescriptor(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) throws ModuleException {
      AbstractDescriptorLoader2 coherenceDescriptor = createDescriptorLoader(is, dm, errorHolder, validate);

      try {
         return getCoherenceBean(coherenceDescriptor);
      } catch (Exception var6) {
         throw new ModuleException("Could not create the Coherence Cluster descriptor", var6);
      }
   }

   public static WeblogicCoherenceBean createCoherenceDescriptor(String uri) throws ModuleException {
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else {
         FileInputStream document = null;

         WeblogicCoherenceBean var3;
         try {
            document = new FileInputStream(uri);
            AbstractDescriptorLoader2 coherenceDescriptor = createDescriptorLoader(document, (DescriptorManager)null, (List)null, true);
            var3 = getCoherenceBean(coherenceDescriptor);
         } catch (Exception var12) {
            throw new ModuleException(var12);
         } finally {
            try {
               if (document != null) {
                  document.close();
               }
            } catch (Exception var11) {
            }

         }

         return var3;
      }
   }

   private static WeblogicCoherenceBean getCoherenceBean(AbstractDescriptorLoader2 coherenceDescriptor) throws IOException, XMLStreamException {
      return (WeblogicCoherenceBean)coherenceDescriptor.loadDescriptorBean();
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) {
      return new AbstractDescriptorLoader2(is, dm, errorHolder, validate) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return CoherenceClusterDescriptorHelper.createVersionMunger(is, this);
         }
      };
   }

   private static VersionMunger createVersionMunger(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      String schemaHelper = "weblogic.coherence.descriptor.wl.WeblogicCoherenceBeanImpl$SchemaHelper2";
      return new VersionMunger(is, loader, schemaHelper, "http://xmlns.oracle.com/weblogic/weblogic-coherence");
   }
}
