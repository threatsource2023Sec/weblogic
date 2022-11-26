package weblogic.diagnostics.descriptor.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class WLDFDescriptorLoader extends AbstractDescriptorLoader2 {
   public WLDFDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      super(vjar, configDir, plan, moduleName, documentURI);
   }

   public WLDFDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      super(altDD, configDir, plan, moduleName, documentURI);
   }

   public WLDFDescriptorLoader(InputStream is, DescriptorManager dm, List errorHolder) {
      super(is, dm, errorHolder, true);
   }

   public WLDFDescriptorLoader(InputStream descriptorIs) {
      super(descriptorIs);
   }

   public WLDFDescriptorLoader(DescriptorManager dm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String name, String uri) {
      super(dm, gcl, configDir, plan, name, uri);
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new WLDFVersionMunger(is, this);
   }
}
