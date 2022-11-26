package weblogic.ejb.container.metadata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFile;

public final class EjbJarLoader extends AbstractDescriptorLoader2 {
   private final EditableDescriptorManager edm = new EditableDescriptorManager();
   private boolean isWritable = false;
   private File altDD;
   private Source source;
   private boolean sourceSpecified;

   public EjbJarLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String uri, String documentURI) {
      super(vjar, configDir, plan, uri, documentURI);
   }

   public EjbJarLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI, Source source) {
      super(altDD, configDir, plan, moduleName, documentURI);
      this.altDD = altDD;
      this.source = source;
      this.sourceSpecified = true;
   }

   public EjbJarLoader(DescriptorManager dm, String documentURI, Source source) {
      super(dm, (GenericClassLoader)null, documentURI);
      this.source = source;
      this.sourceSpecified = true;
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new EjbJarReader(is, this);
   }

   protected DescriptorManager getDescriptorManager() {
      return (DescriptorManager)(this.isWritable ? this.edm : super.getDescriptorManager());
   }

   public DescriptorBean loadEditableDescriptorBean() throws IOException, XMLStreamException {
      this.isWritable = true;
      DescriptorBean db = this.loadDescriptorBean();
      this.isWritable = false;
      return db;
   }

   public String getAbsolutePath() {
      return this.altDD == null && this.source != null ? this.source.getURL().toString() : super.getAbsolutePath();
   }

   public InputStream getInputStream() throws IOException {
      if (this.altDD == null && this.sourceSpecified) {
         return this.source != null ? this.source.getInputStream() : null;
      } else {
         return super.getInputStream();
      }
   }
}
