package weblogic.ejb.spi;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ModuleContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.ejb.container.deployer.Archive;
import weblogic.ejb.container.metadata.EjbDescriptorReaderImpl;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public final class EjbDescriptorFactory {
   private static EjbDescriptorReader reader;

   public static EjbDescriptorReader getEjbDescriptorReader() {
      if (reader == null) {
         reader = new EjbDescriptorReaderImpl();
      }

      return reader;
   }

   public static EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile vjf) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createDescriptorFromJarFile(vjf);
   }

   public static EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile vjf, File altDD) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createDescriptorFromJarFile(vjf, altDD);
   }

   public static EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jar, File altDD, File config, DeploymentPlanBean plan, String appName, String uri) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createDescriptorFromJarFile(jar, altDD, config, plan, appName, uri);
   }

   public static EjbDescriptorBean createDescriptor(ModuleContext mc) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createDescriptor(mc);
   }

   public static EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile jar, GenericClassLoader cl) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createReadOnlyDescriptorFromJarFile(jar, cl);
   }

   public static EjbDescriptorBean createReadOnlyDescriptor(ModuleContext modCtx, Archive archive, VirtualJarFile[] autoRefLibJars) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      EjbDescriptorReader descReader = getEjbDescriptorReader();
      EjbDescriptorBean desc = descReader.loadDescriptors(modCtx, archive, autoRefLibJars);
      descReader.processAnnotations(desc, archive, (ModuleRegistry)null);
      return desc;
   }

   public static EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile jar, File altDD, File config, DeploymentPlanBean plan, String appName, String uri, GenericClassLoader cl, VirtualJarFile[] autoRefLibFiles) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createReadOnlyDescriptorFromJarFile(jar, altDD, config, plan, appName, uri, cl, autoRefLibFiles);
   }

   public static EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile jar, File altDD, File config, DeploymentPlanBean plan, String appName, String uri, GenericClassLoader cl, VirtualJarFile[] autoRefLibFiles, Set commonAnnotatedClasses) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException {
      return getEjbDescriptorReader().createReadOnlyDescriptorFromJarFile(jar, altDD, config, plan, appName, uri, cl, autoRefLibFiles, commonAnnotatedClasses);
   }
}
