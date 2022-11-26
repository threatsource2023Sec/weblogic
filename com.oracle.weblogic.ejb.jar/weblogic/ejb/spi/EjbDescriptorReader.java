package weblogic.ejb.spi;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ModuleContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.ejb.container.deployer.Archive;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public interface EjbDescriptorReader {
   EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile var1) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile var1, File var2) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile var1, File var2, File var3, DeploymentPlanBean var4, String var5, String var6) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean createDescriptor(ModuleContext var1) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile var1, GenericClassLoader var2) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile var1, File var2, File var3, DeploymentPlanBean var4, String var5, String var6, GenericClassLoader var7, VirtualJarFile[] var8) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean createReadOnlyDescriptorFromJarFile(VirtualJarFile var1, File var2, File var3, DeploymentPlanBean var4, String var5, String var6, GenericClassLoader var7, VirtualJarFile[] var8, Set var9) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   EjbDescriptorBean loadDescriptors(ModuleContext var1, Archive var2, VirtualJarFile[] var3) throws IOException, XMLParsingException, XMLProcessingException, XMLStreamException;

   void processAnnotations(EjbDescriptorBean var1, Archive var2, ModuleRegistry var3) throws IOException;
}
