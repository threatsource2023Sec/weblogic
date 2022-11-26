package weblogic.connector.configuration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader;
import weblogic.application.descriptor.BasicMunger;
import weblogic.application.descriptor.NamespaceURIMunger;
import weblogic.application.descriptor.PredicateMatcher;
import weblogic.connector.common.Debug;
import weblogic.connector.common.JCAConnectionFactoryRegistry;
import weblogic.connector.deploy.DeployerUtil;
import weblogic.connector.deploy.RarArchive;
import weblogic.connector.exception.RAConfigurationException;
import weblogic.connector.exception.WLRAConfigurationException;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.ConnectorDescriptorConstants;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class ConnectorDescriptor implements ConnectorDescriptorConstants {
   private MyConnectorDescriptor connectorDescriptor;
   private MyWlsConnectorDescriptor wlsConnectorDescriptor;
   private ConnectorBean connectorBean;
   private RarArchive rar;
   private boolean insideEar;
   private AdditionalAnnotatedClassesProvider aacProvider;
   private ConnectorAPContext apContext;

   public ConnectorAPContext getAnnotationProcessingContext() {
      return this.apContext;
   }

   public static ConnectorDescriptor buildDescriptor(File altDD, File altWlsDD, RarArchive rar, File configDir, DeploymentPlanBean plan, String moduleName, ClassLoader gcl, boolean insideEar, AdditionalAnnotatedClassesProvider provider) throws RAConfigurationException {
      ConnectorDescriptor descriptor = new ConnectorDescriptor(altDD, altWlsDD, rar, configDir, plan, moduleName, insideEar, provider);
      descriptor.loadRaDD();
      if (rar != null) {
         descriptor.processAnnotation(gcl);
      } else if (Debug.isDeploymentEnabled()) {
         Debug.deployment("Skip annotation processing for adapter since there is no vjar specified");
      }

      return descriptor;
   }

   public static ConnectorDescriptor buildDescriptor(DescriptorManager edm, GenericClassLoader gcl, boolean createExtensionBean) throws RAConfigurationException {
      ConnectorDescriptor descriptor = new ConnectorDescriptor(edm, gcl, createExtensionBean);
      descriptor.loadRaDD();
      return descriptor;
   }

   public ConnectorDescriptor(File altDD, File altWlsDD, RarArchive rar, File configDir, DeploymentPlanBean plan, String moduleName, boolean insideEar, AdditionalAnnotatedClassesProvider provider) {
      this.apContext = ConnectorAPContext.NullContext;
      VirtualJarFile vjar = rar == null ? null : rar.getVirtualJarFile();
      if (altDD != null) {
         this.connectorDescriptor = new MyConnectorDescriptor(altDD);
      } else {
         this.connectorDescriptor = new MyConnectorDescriptor(vjar);
      }

      if (altWlsDD != null) {
         this.wlsConnectorDescriptor = new MyWlsConnectorDescriptor(altWlsDD, configDir, plan, moduleName);
      } else {
         this.wlsConnectorDescriptor = new MyWlsConnectorDescriptor(vjar, configDir, plan, moduleName);
      }

      this.rar = rar;
      this.insideEar = insideEar;
      this.aacProvider = provider;
   }

   public ConnectorDescriptor(DescriptorManager edm, GenericClassLoader gcl, boolean createExtensionBean) {
      this.apContext = ConnectorAPContext.NullContext;
      this.connectorDescriptor = new MyConnectorDescriptor(edm, gcl);
      this.wlsConnectorDescriptor = new MyWlsConnectorDescriptor(edm, gcl, createExtensionBean);
   }

   /** @deprecated */
   @Deprecated
   public ConnectorDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.apContext = ConnectorAPContext.NullContext;
      if (altDD != null && !altDD.getName().endsWith("weblogic-ra.xml")) {
         this.connectorDescriptor = new MyConnectorDescriptor(altDD);
      } else {
         this.wlsConnectorDescriptor = new MyWlsConnectorDescriptor(altDD, configDir, plan, moduleName);
      }

   }

   public ConnectorBean getConnectorBean() {
      return this.connectorBean;
   }

   public WeblogicConnectorBean getWeblogicConnectorBean() throws WLRAConfigurationException {
      try {
         if (this.wlsConnectorDescriptor == null) {
            return null;
         } else if (this.wlsConnectorDescriptor.getDeploymentPlan() != null) {
            if (this.getConnectorBean() != null) {
               this.wlsConnectorDescriptor.version = this.getConnectorBean().getVersion();
            } else {
               this.wlsConnectorDescriptor.version = "1.6";
            }

            return (WeblogicConnectorBean)this.wlsConnectorDescriptor.getPlanMergedDescriptorBean();
         } else {
            return (WeblogicConnectorBean)this.wlsConnectorDescriptor.getRootDescriptorBean();
         }
      } catch (IOException var2) {
         throw new WLRAConfigurationException(var2);
      } catch (XMLStreamException var3) {
         throw new WLRAConfigurationException(var3);
      }
   }

   private void loadRaDD() throws RAConfigurationException {
      try {
         this.connectorBean = this.connectorDescriptor == null ? null : (ConnectorBean)this.connectorDescriptor.getRootDescriptorBean();
      } catch (IOException var2) {
         throw new RAConfigurationException(var2);
      } catch (XMLStreamException var3) {
         throw new RAConfigurationException(var3);
      }
   }

   private void processAnnotation(ClassLoader parent) throws RAConfigurationException {
      GenericClassLoader cl = null;
      ArrayList finders = null;
      boolean var17 = false;

      try {
         var17 = true;
         ConnectorBean artificialBean;
         if (this.connectorBean == null) {
            artificialBean = (ConnectorBean)(new DescriptorManager()).createDescriptorRoot(ConnectorBean.class).getRootBean();
            artificialBean.setVersion("1.6");
         } else {
            artificialBean = this.connectorBean;
         }

         VJAnnotationScanner scanner = new VJAnnotationScanner(artificialBean);
         if (scanner.needToProcessAnnotation()) {
            cl = new GenericClassLoader(parent);
            if (Debug.isDeploymentEnabled()) {
               Debug.deployment("created temp classloader for annotation processing:" + cl);
            }

            finders = new ArrayList();
            DeployerUtil.updateClassFinder(cl, this.rar, finders);
            scanner.process(this.rar, cl);
            if (this.insideEar) {
               if (Debug.isDeploymentEnabled()) {
                  Debug.deployment("start to process annotationed classes in EAR for adapter " + this.rar);
               }

               Set classSet = this.aacProvider.getAnnotatedClasses();
               scanner.process(classSet);
            }

            scanner.processConfigProperty(cl);
            this.apContext = scanner.getResult();
            this.connectorBean = artificialBean;
            if (Debug.isDeploymentEnabled()) {
               Debug.deployment("End processing annotations for RAR: " + this.rar + "; Errors:" + this.apContext.getErrors() + "; Warnings:" + this.apContext.getWarnings());
            }
         } else if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Do not need to process annotations for RAR: " + this.rar);
         }

         if (this.connectorBean != null) {
            JCAConnectionFactoryRegistry.getInstance().registerConnectionFactory(this.connectorBean);
            var17 = false;
         } else {
            var17 = false;
         }
      } catch (Throwable var22) {
         throw new RAConfigurationException(var22);
      } finally {
         if (var17) {
            if (finders != null) {
               Iterator var8 = finders.iterator();

               while(var8.hasNext()) {
                  ClassFinder finder = (ClassFinder)var8.next();

                  try {
                     finder.close();
                  } catch (Throwable var19) {
                  }
               }
            }

            try {
               if (cl != null) {
                  if (Debug.isDeploymentEnabled()) {
                     Debug.deployment("close temp classloader for annotation processing:" + cl);
                  }

                  cl.close();
               }
            } catch (Throwable var18) {
            }

         }
      }

      if (finders != null) {
         Iterator var24 = finders.iterator();

         while(var24.hasNext()) {
            ClassFinder finder = (ClassFinder)var24.next();

            try {
               finder.close();
            } catch (Throwable var21) {
            }
         }
      }

      try {
         if (cl != null) {
            if (Debug.isDeploymentEnabled()) {
               Debug.deployment("close temp classloader for annotation processing:" + cl);
            }

            cl.close();
         }
      } catch (Throwable var20) {
      }

   }

   public AbstractDescriptorLoader getWlsRaDescriptorLoader() {
      return this.wlsConnectorDescriptor;
   }

   public AbstractDescriptorLoader getRaDescriptorLoader() {
      return this.connectorDescriptor;
   }

   public String dumpConnectorBeanToXml() throws IOException {
      return dumpDDBeanToXml(this.getConnectorBean());
   }

   public String dumpWeblogicConnectorBeanToXml() throws IOException, WLRAConfigurationException {
      return dumpDDBeanToXml(this.getWeblogicConnectorBean());
   }

   public static String dumpDDBeanToXml(Object bean) throws IOException {
      if (bean != null) {
         ByteArrayOutputStream cbeanXML = new ByteArrayOutputStream();
         ((DescriptorBean)bean).getDescriptor().toXML(cbeanXML);
         return cbeanXML.toString();
      } else {
         return "<null>";
      }
   }

   private class MyWlsConnectorDescriptor extends AbstractDescriptorLoader {
      String version = "1.6";
      PredicateMatcher predicateMatcher;
      private boolean createExtensionBean;

      MyWlsConnectorDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(vjar, configDir, plan, moduleName);
         this.createExtensionBean = true;
      }

      MyWlsConnectorDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(altDD, configDir, plan, moduleName);
         this.createExtensionBean = true;
      }

      MyWlsConnectorDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(edm, gcl, configDir, plan, moduleName);
         this.createExtensionBean = true;
      }

      MyWlsConnectorDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         super(edm, gcl);
         this.createExtensionBean = true;
      }

      MyWlsConnectorDescriptor(DescriptorManager edm, GenericClassLoader gcl, boolean createExtensionBean) {
         super(edm, gcl);
         this.createExtensionBean = createExtensionBean;
      }

      public String getDocumentURI() {
         return "META-INF/weblogic-ra.xml";
      }

      protected BasicMunger createXMLStreamReader(InputStream is) throws XMLStreamException {
         ConnectorBean cb = null;
         cb = ConnectorDescriptor.this.getConnectorBean();
         return new WlsRAReader(cb, this.createXMLStreamReaderDelegate(is), this, this.getDeploymentPlan(), this.getModuleName(), this.getDocumentURI(), this.createExtensionBean);
      }

      public XMLStreamReader createXMLStreamReaderDelegate(InputStream is) throws XMLStreamException {
         String[] oldNamespaceURIs = new String[]{"http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/weblogic-connector"};
         return new NamespaceURIMunger(is, "http://xmlns.oracle.com/weblogic/weblogic-connector", oldNamespaceURIs);
      }

      protected PredicateMatcher getPredicateMatcher() {
         if (this.predicateMatcher == null) {
            this.predicateMatcher = new JCAPredicateMatcher(this.version);
         }

         return this.predicateMatcher;
      }
   }

   public static class MyConnectorDescriptor extends AbstractDescriptorLoader {
      public MyConnectorDescriptor(File altDD) {
         super(altDD);
      }

      public MyConnectorDescriptor(VirtualJarFile vjar) {
         super(vjar);
      }

      MyConnectorDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         super(edm, gcl);
      }

      public String getDocumentURI() {
         return "META-INF/ra.xml";
      }

      protected BasicMunger createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new RAReader(this.createXMLStreamReaderDelegate(is), this);
      }
   }
}
