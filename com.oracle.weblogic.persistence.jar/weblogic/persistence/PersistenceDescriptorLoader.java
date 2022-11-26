package weblogic.persistence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.PersistenceBean;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.persistence.spi.JPAIntegrationProviderFactory;
import weblogic.utils.jars.VirtualJarFile;

public class PersistenceDescriptorLoader extends AbstractDescriptorLoader2 {
   private static final boolean IS_DEBUG_ENABLED = Boolean.getBoolean("weblogic.deployment.PersistenceDescriptor");
   private final URL resourceURL;

   public PersistenceDescriptorLoader(URL resourceURL, File configDir, DeploymentPlanBean plan, String moduleName, String qualifiedURI) {
      super((VirtualJarFile)null, configDir, plan, moduleName, qualifiedURI);
      this.resourceURL = resourceURL;
   }

   public PersistenceDescriptorLoader(VirtualJarFile vjf, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      super(vjf, configDir, plan, moduleName, uri);
      this.resourceURL = null;
   }

   public InputStream getInputStream() throws IOException {
      return this.resourceURL != null ? this.resourceURL.openStream() : super.getInputStream();
   }

   protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
      if (!this.isPersistenceConfigurationDescriptor()) {
         return new PersistenceReader(is, this);
      } else {
         throw new XMLStreamException("Can't parse non persistence.xml descriptors");
      }
   }

   public DescriptorBean loadDescriptorBean() throws IOException, XMLStreamException {
      DescriptorBean db = super.loadDescriptorBean();
      if (db != null && !this.isPersistenceConfigurationDescriptor()) {
         PersistenceBean pb = (PersistenceBean)db;
         PersistenceUnitBean pub = pb.lookupPersistenceUnit("__ORACLE_WLS_INTERNAL_DUMMY_PERSISTENCE_UNIT");
         if (pub != null) {
            pb.destroyPersistenceUnit(pub);
         }

         PersistenceReader pr = (PersistenceReader)this.getMunger();
         pb.setOriginalVersion(pr.getOriginalVersion());
         return db;
      } else {
         return db;
      }
   }

   protected boolean isPersistenceConfigurationDescriptor() {
      return this.getDocumentURI() != null && this.getDocumentURI().endsWith("META-INF/persistence-configuration.xml");
   }

   public static void debug(String msg, Exception e) {
      if (IS_DEBUG_ENABLED) {
         System.out.println(msg);
         if (e != null) {
            e.printStackTrace();
         }
      }

   }

   public static void debug(String msg) {
      debug(msg, (Exception)null);
   }

   public static URI getRelativeURI(URI rootURI, URI resourceURI) {
      URI relativeURI = rootURI.relativize(resourceURI);
      debug("Relativizing " + resourceURI + " to " + rootURI);
      if (relativeURI.equals(resourceURI)) {
         debug("No initial match, schemes are " + resourceURI.getScheme() + " and " + rootURI.getScheme());
         if ((resourceURI.getScheme().equals("jar") || resourceURI.getScheme().equals("zip")) && rootURI.getScheme().equals("file")) {
            debug("Schema mismatch detected");
            String resourceURIAsString = resourceURI.toString();
            String descriptorURI = resourceURIAsString.substring(resourceURIAsString.indexOf("!") + 1);
            debug("Descriptor URI is " + descriptorURI);
            String jarURIAsString = null;
            if (resourceURI.getScheme().equals("zip")) {
               jarURIAsString = "file:/" + resourceURIAsString.substring(4, resourceURIAsString.indexOf("!"));
            } else {
               jarURIAsString = resourceURIAsString.substring(4, resourceURIAsString.indexOf("!"));
            }

            debug("Jar URI is " + jarURIAsString);

            try {
               URI jarURI = new URI(jarURIAsString);
               URI relativeJarURI = rootURI.relativize(jarURI);
               debug("Relative jar URI is " + relativeJarURI.toString());
               return new URI(relativeJarURI + "!" + descriptorURI);
            } catch (URISyntaxException var8) {
               debug("Unable to relativize URI", var8);
               return relativeURI;
            }
         }
      }

      return relativeURI;
   }

   public static URI getRelativeURI(File[] rootFiles, URI resourceURI) {
      if (rootFiles != null) {
         File[] var2 = rootFiles;
         int var3 = rootFiles.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File rootFile = var2[var4];
            URI relativeURI = getRelativeURI(rootFile.toURI(), resourceURI);
            if (!relativeURI.equals(resourceURI)) {
               return relativeURI;
            }
         }
      }

      return resourceURI;
   }

   public static URI getResourceURI(URL resourceURL) throws IOException {
      String file = resourceURL.getFile();
      if ("jar".equals(resourceURL.getProtocol())) {
         file = file.substring(5);
      }

      return (new File(file)).getCanonicalFile().toURI();
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         System.out.println("Usage: java weblogic.deployment.PersistenceDescriptorLoader [descriptor-uri] [plan path] [module] [root-uri]");
      } else {
         DeploymentPlanBean plan = null;
         if (args.length > 1) {
            DeploymentPlanDescriptorLoader dpd = new DeploymentPlanDescriptorLoader(new File(args[1]));
            plan = dpd.getDeploymentPlanBean();
         }

         PersistenceDescriptorLoader loader = JPAIntegrationProviderFactory.getDefaultJPAIntegrationProvider().getDescriptorLoader((VirtualJarFile)null, (new File(args[0])).toURL(), (File)null, plan, args[2], args[3]);
         DescriptorBean rootBean = loader.loadDescriptorBean();
         DescriptorUtils.writeAsXML(rootBean);
      }

   }
}
