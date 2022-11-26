package weblogic.coherence.container.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.ModuleException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.coherence.app.descriptor.CoherenceAppDescriptorLoader;
import weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean;
import weblogic.coherence.app.descriptor.wl.WeblogicCohAppBean;
import weblogic.descriptor.DescriptorManager;

public class WLSCoherenceAppDescriptorLoader extends CoherenceAppDescriptorLoader {
   public static final String WEBLOGIC_COH_APP_NAMESPACE_URI = "http://xmlns.oracle.com/weblogic/weblogic-coh-app";

   public CoherenceApplicationBean createCoherenceAppDescriptor(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) throws ModuleException {
      AbstractDescriptorLoader2 coherenceDescriptor = this.createDescriptorLoader(is, dm, errorHolder, validate);

      try {
         return this.getCoherenceBean(coherenceDescriptor);
      } catch (Exception var7) {
         throw new ModuleException("Could not create the Coherence Application descriptor", var7);
      }
   }

   public CoherenceApplicationBean createCoherenceAppDescriptor(URL url) throws ModuleException {
      if (url == null) {
         throw new ModuleException("Null URI specified");
      } else {
         InputStream document = null;

         CoherenceApplicationBean var4;
         try {
            document = url.openStream();
            AbstractDescriptorLoader2 loader = this.createDescriptorLoader(document, (DescriptorManager)null, (List)null, true);
            var4 = this.getCoherenceBean(loader);
         } catch (Exception var13) {
            throw new ModuleException(var13);
         } finally {
            try {
               if (document != null) {
                  document.close();
               }
            } catch (Exception var12) {
            }

         }

         return var4;
      }
   }

   public WeblogicCohAppBean createWeblogicCohAppBean(URL url) throws ModuleException {
      if (url == null) {
         throw new ModuleException("Null URI specified");
      } else {
         InputStream document = null;

         WeblogicCohAppBean var4;
         try {
            document = url.openStream();
            AbstractDescriptorLoader2 loader = this.createCohAppDescriptorLoader(document, (DescriptorManager)null, (List)null, true);
            var4 = (WeblogicCohAppBean)loader.loadDescriptorBean();
         } catch (Exception var13) {
            throw new ModuleException(var13);
         } finally {
            try {
               if (document != null) {
                  document.close();
               }
            } catch (Exception var12) {
            }

         }

         return var4;
      }
   }

   private CoherenceApplicationBean getCoherenceBean(AbstractDescriptorLoader2 loader) throws IOException, XMLStreamException {
      return (CoherenceApplicationBean)loader.loadDescriptorBean();
   }

   private AbstractDescriptorLoader2 createDescriptorLoader(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) {
      return new AbstractDescriptorLoader2(is, dm, errorHolder, validate) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return WLSCoherenceAppDescriptorLoader.this.createVersionMunger(is, this);
         }
      };
   }

   private VersionMunger createVersionMunger(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      String schemaHelper = "weblogic.coherence.app.descriptor.wl.CoherenceApplicationBeanImpl$SchemaHelper2";
      return new VersionMunger(is, loader, schemaHelper, "http://xmlns.oracle.com/coherence/coherence-application");
   }

   private AbstractDescriptorLoader2 createCohAppDescriptorLoader(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) {
      return new AbstractDescriptorLoader2(is, dm, errorHolder, validate) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return WLSCoherenceAppDescriptorLoader.this.createCohAppVersionMunger(is, this);
         }
      };
   }

   private VersionMunger createCohAppVersionMunger(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      String schemaHelper = "weblogic.coherence.app.descriptor.wl.WeblogicCohAppBeanImpl$SchemaHelper2";
      return new VersionMunger(is, loader, schemaHelper, "http://xmlns.oracle.com/weblogic/weblogic-coh-app");
   }
}
