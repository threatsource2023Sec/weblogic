package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.internal.WlsExtensionReader;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;
import weblogic.utils.jars.VirtualJarFile;

public class ExtensionDescriptorParser extends AbstractDescriptorLoader2 {
   private WeblogicExtensionBean wlExtBean = null;
   private boolean parsed = false;
   private final String extDescritorUri;
   private final String appName;

   public ExtensionDescriptorParser(VirtualJarFile vjf, File configDir, DeploymentPlanBean plan, String appName, String extDescriptorUri) {
      super(vjf, configDir, plan, appName, extDescriptorUri);
      this.extDescritorUri = extDescriptorUri;
      this.appName = appName;
   }

   public WeblogicExtensionBean getWlExtensionDescriptor() throws IOException, XMLStreamException {
      if (!this.parsed) {
         this.wlExtBean = (WeblogicExtensionBean)this.loadDescriptorBean();
         this.parsed = true;
      }

      return this.wlExtBean;
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new WlsExtensionReader(is, this);
   }

   public void mergeWlExtensionDescriptorsFromLibraries(VirtualJarFile[] libraryVjfs) throws IOException, XMLStreamException {
      WeblogicExtensionBean extDD = this.getWlExtensionDescriptor();
      if (libraryVjfs.length > 0) {
         AbstractDescriptorLoader2 tempLoader = this;
         if (extDD == null) {
            tempLoader = new ExtensionDescriptorParser(libraryVjfs[0], this.getConfigDir(), this.getDeploymentPlan(), this.appName, this.extDescritorUri);
            extDD = (WeblogicExtensionBean)tempLoader.loadDescriptorBean();
            libraryVjfs[0].close();
            if (libraryVjfs.length > 1) {
               VirtualJarFile[] tempArray = new VirtualJarFile[libraryVjfs.length - 1];
               System.arraycopy(libraryVjfs, 1, tempArray, 0, libraryVjfs.length - 1);
               libraryVjfs = tempArray;
            } else {
               libraryVjfs = new VirtualJarFile[0];
            }
         }

         if (libraryVjfs.length > 0) {
            extDD = (WeblogicExtensionBean)tempLoader.mergeDescriptors(libraryVjfs);
         }
      }

      this.wlExtBean = extDD;
   }
}
