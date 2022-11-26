package weblogic.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.CachingDescriptorLoader2;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.jars.VirtualJarFile;

public class PermissionsDescriptorLoader extends CachingDescriptorLoader2 {
   private File altDD;
   private Source source;
   private boolean sourceSpecified;

   public PermissionsDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI, Source source) {
      super((File)altDD, configDir, plan, moduleName, documentURI, (File)null);
      this.altDD = altDD;
      this.source = source;
      this.sourceSpecified = true;
   }

   public PermissionsDescriptorLoader(DescriptorManager dm, String documentURI, Source source) {
      super(dm, (GenericClassLoader)null, documentURI, (File)null);
      this.source = source;
      this.sourceSpecified = true;
   }

   public PermissionsDescriptorLoader(File altDD) {
      this((File)altDD, (File)null, (DeploymentPlanBean)null, (String)null);
   }

   public PermissionsDescriptorLoader(VirtualJarFile vjar) {
      this((VirtualJarFile)vjar, (File)null, (DeploymentPlanBean)null, (String)null);
   }

   public PermissionsDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl) {
      this((DescriptorManager)edm, (GenericClassLoader)gcl, (File)null, (DeploymentPlanBean)null, (String)null);
   }

   public PermissionsDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      this(altDD, configDir, plan, moduleName, (File)null);
   }

   public PermissionsDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
      super(altDD, configDir, plan, moduleName, "META-INF/permissions.xml", descriptorCacheDir);
   }

   public PermissionsDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentUri) {
      super((File)altDD, configDir, plan, moduleName, "META-INF/permissions.xml", (File)null);
   }

   public PermissionsDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
      this(vjar, configDir, plan, moduleName, (File)null);
   }

   public PermissionsDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, File descriptorCacheDir) {
      super(vjar, configDir, plan, moduleName, "META-INF/permissions.xml", descriptorCacheDir);
   }

   public PermissionsDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String descriptorUri) {
      super((VirtualJarFile)vjar, configDir, plan, moduleName, "META-INF/permissions.xml", (File)null);
   }

   public PermissionsDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      super(edm, gcl, configDir, plan, moduleName, "META-INF/permissions.xml", (File)null);
   }

   public InputStream getInputStream() throws IOException {
      return this.sourceSpecified ? this.source.getInputStream() : super.getInputStream();
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      return new PermissionsReader(is, this);
   }

   public void setValidate(boolean validate) {
      super.setValidateSchema(validate);
   }
}
