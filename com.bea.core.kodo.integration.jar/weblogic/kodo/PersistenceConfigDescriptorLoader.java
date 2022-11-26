package weblogic.kodo;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.VersionMunger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.persistence.PersistenceDescriptorLoader;
import weblogic.utils.jars.VirtualJarFile;

public class PersistenceConfigDescriptorLoader extends PersistenceDescriptorLoader {
   public PersistenceConfigDescriptorLoader(VirtualJarFile vjf, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      super(vjf, configDir, plan, moduleName, uri);
   }

   public PersistenceConfigDescriptorLoader(URL resourceURL, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      super(resourceURL, configDir, plan, moduleName, uri);
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      if (this.isPersistenceConfigurationDescriptor()) {
         String schemaHelper = "kodo.jdbc.conf.descriptor.PersistenceConfigurationBeanImpl$SchemaHelper2";
         return new VersionMunger(is, this, schemaHelper, "http://xmlns.oracle.com/weblogic/persistence-configuration");
      } else {
         return super.createXMLStreamReader(is);
      }
   }
}
