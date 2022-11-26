package weblogic.j2eeclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ApplicationClientDescriptor {
   private final MyApplicationClientDescriptor appClientDescriptor;
   private final MyWlsApplicationClientDescriptor wlsAppClientDescriptor;
   private final PermissionsDescriptorLoader permissionsDescriptor;
   private static final Map wlNameChanges = new HashMap();

   public ApplicationClientDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(vjar);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(vjar, configDir, plan, moduleName);
      this.permissionsDescriptor = new PermissionsDescriptorLoader(vjar, configDir, plan, moduleName);
   }

   public ApplicationClientDescriptor(File altDD, VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(altDD);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(vjar, configDir, plan, moduleName);
      this.permissionsDescriptor = new PermissionsDescriptorLoader(vjar, configDir, plan, moduleName);
   }

   public static ApplicationClientDescriptor createWLSApplicationClientDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      return new ApplicationClientDescriptor(true, altDD, configDir, plan, moduleName);
   }

   public ApplicationClientDescriptor(GenericClassLoader gcl) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(gcl);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(gcl);
      this.permissionsDescriptor = new PermissionsDescriptorLoader((DescriptorManager)null, gcl);
   }

   public ApplicationClientDescriptor(GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(gcl);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(gcl, configDir, plan, moduleName);
      this.permissionsDescriptor = new PermissionsDescriptorLoader((DescriptorManager)null, gcl, configDir, plan, moduleName);
   }

   public ApplicationClientDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(edm, gcl);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(edm, gcl);
      this.permissionsDescriptor = new PermissionsDescriptorLoader(edm, gcl);
   }

   public ApplicationClientDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(edm, gcl);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(edm, gcl, configDir, plan, moduleName);
      this.permissionsDescriptor = new PermissionsDescriptorLoader(edm, gcl, configDir, plan, moduleName);
   }

   private ApplicationClientDescriptor(boolean createApplicationDescriptor, File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.appClientDescriptor = new MyApplicationClientDescriptor(altDD);
      this.wlsAppClientDescriptor = new MyWlsApplicationClientDescriptor(altDD, configDir, plan, moduleName);
      this.permissionsDescriptor = new PermissionsDescriptorLoader(altDD, configDir, plan, moduleName);
   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.appClientDescriptor.getDeploymentPlan();
   }

   public ApplicationClientBean getApplicationClientBean() throws IOException, XMLStreamException {
      ApplicationClientBean acb = (ApplicationClientBean)this.appClientDescriptor.loadDescriptorBean();
      if (acb == null) {
         acb = (ApplicationClientBean)(new DescriptorManager()).createDescriptorRoot(ApplicationClientBean.class).getRootBean();
      }

      return acb;
   }

   public WeblogicApplicationClientBean getWeblogicApplicationClientBean() throws IOException, XMLStreamException {
      WeblogicApplicationClientBean b = (WeblogicApplicationClientBean)this.wlsAppClientDescriptor.loadDescriptorBean();
      return b == null ? (WeblogicApplicationClientBean)(new EditableDescriptorManager()).createDescriptorRoot(WeblogicApplicationClientBean.class).getRootBean() : b;
   }

   public PermissionsBean getPermissionsBean() throws IOException, XMLStreamException {
      PermissionsBean b = (PermissionsBean)this.permissionsDescriptor.loadDescriptorBean();
      return b;
   }

   public AbstractDescriptorLoader2 getApplicationClientDescriptorLoader() {
      return this.appClientDescriptor;
   }

   public AbstractDescriptorLoader2 getWlsApplicationClientDescriptorLoader() {
      return this.wlsAppClientDescriptor;
   }

   public AbstractDescriptorLoader2 getPermissionsDescriptorLoader() {
      return this.permissionsDescriptor;
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage();
      }

      String jarPath = args[0];
      File f = new File(jarPath);
      if (f.getName().endsWith(".jar")) {
         JarFile jarJarFile = new JarFile(jarPath);
         VirtualJarFile vJarJarFile = VirtualJarFactory.createVirtualJar(jarJarFile);
         System.out.println("\n\n... getting ApplicationClientBean:");
         ApplicationClientDescriptor ac = new ApplicationClientDescriptor(vJarJarFile, (File)null, (DeploymentPlanBean)null, (String)null);
         ApplicationClientBean acb = ac.getApplicationClientBean();
         ((DescriptorBean)acb).getDescriptor().toXML(System.out);
         System.out.println("\n\n... getting WeblogicApplicationClientBean:");
         WeblogicApplicationClientBean wacb = (new ApplicationClientDescriptor(vJarJarFile, (File)null, (DeploymentPlanBean)null, (String)null)).getWeblogicApplicationClientBean();
         ((DescriptorBean)wacb).getDescriptor().toXML(System.out);
      } else if (f.getPath().endsWith("weblogic-application-client.xml")) {
         System.out.println("\n\n... getting WeblogicApplicationClientBean from: " + f);
         WeblogicApplicationClientBean wacb = (new ApplicationClientDescriptor(f, (VirtualJarFile)null, (File)null, (DeploymentPlanBean)null, (String)null)).getWeblogicApplicationClientBean();
         ((DescriptorBean)wacb).getDescriptor().toXML(System.out);
      } else if (f.getPath().endsWith("application-client.xml")) {
         System.out.println("\n\n... getting ApplicationClientBean:");
         ApplicationClientBean acb = (new ApplicationClientDescriptor(f, (VirtualJarFile)null, (File)null, (DeploymentPlanBean)null, (String)null)).getApplicationClientBean();
         ((DescriptorBean)acb).getDescriptor().toXML(System.out);
      } else {
         System.out.println("\n\n... neither application-client.xml nor weblogic-application-client.xml specified");
      }

   }

   private static void usage() {
      System.err.println("usage: java weblogic.j2eeclient.ApplicationClientDescriptor <descriptor file name>");
      System.err.println("\n\n example:\n java weblogic.j2eeclient.ApplicationClientDescriptor jar or altDD file name ");
      System.exit(0);
   }

   static {
      wlNameChanges.put("application-client", "weblogic-application-client");
      wlNameChanges.put("ejb-ref", "ejb-reference-description");
      wlNameChanges.put("resource-ref", "resource-description");
      wlNameChanges.put("resource-env-ref", "resource-env-description");
   }

   private class MyWlsApplicationClientDescriptor extends MyAbstractDescriptorLoader {
      MyWlsApplicationClientDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
         super((VirtualJarFile)vjar, configDir, plan, moduleName, "META-INF/weblogic-application-client.xml");
      }

      MyWlsApplicationClientDescriptor(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
         super((File)altDD, configDir, plan, moduleName, "META-INF/weblogic-application-client.xml");
      }

      MyWlsApplicationClientDescriptor(GenericClassLoader gcl) {
         super((GenericClassLoader)gcl, "META-INF/weblogic-application-client.xml");
      }

      MyWlsApplicationClientDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         super(edm, gcl, "META-INF/weblogic-application-client.xml");
      }

      MyWlsApplicationClientDescriptor(GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         this(gcl);
      }

      MyWlsApplicationClientDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         this(gcl);
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new VersionMunger(is, this, "weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBeanImpl$SchemaHelper2", ApplicationClientDescriptor.wlNameChanges, "http://xmlns.oracle.com/weblogic/weblogic-application-client") {
            public String getDtdNamespaceURI() {
               return "http://xmlns.oracle.com/weblogic/weblogic-application-client";
            }

            protected boolean isOldNamespaceURI(String namespaceURI) {
               return namespaceURI != null && (namespaceURI.equals("http://www.bea.com/ns/weblogic/90") || namespaceURI.equals("http://www.bea.com/ns/weblogic/10.0") || namespaceURI.equals("http://www.bea.com/ns/weblogic/10.0/persistence") || this.newNamespaceURI != null && !namespaceURI.equals(this.newNamespaceURI) && !namespaceURI.startsWith("http://www.w3.org/") && !namespaceURI.startsWith("http://xmlns.jcp.org/xml/ns/javaee"));
            }

            protected boolean isOldSchema() {
               String nameSpaceURI = this.getNamespaceURI();
               return !"http://xmlns.oracle.com/weblogic/weblogic-application-client".equals(nameSpaceURI);
            }

            protected void transformOldSchema() {
               if (this.currentEvent.getElementName().equals("weblogic-application-client")) {
                  this.transformNamespace("http://xmlns.oracle.com/weblogic/weblogic-application-client", this.currentEvent, "http://www.bea.com/ns/weblogic/90");
               }

               this.tranformedNamespace = "http://xmlns.oracle.com/weblogic/weblogic-application-client";
            }
         };
      }
   }

   private class MyApplicationClientDescriptor extends MyAbstractDescriptorLoader {
      MyApplicationClientDescriptor(VirtualJarFile vjar) {
         super((VirtualJarFile)vjar, "META-INF/application-client.xml");
      }

      MyApplicationClientDescriptor(File altDD) {
         super((File)altDD, "META-INF/application-client.xml");
      }

      MyApplicationClientDescriptor(GenericClassLoader gcl) {
         super((GenericClassLoader)gcl, "META-INF/application-client.xml");
      }

      MyApplicationClientDescriptor(DescriptorManager edm, GenericClassLoader gcl) {
         super(edm, gcl, "META-INF/application-client.xml");
      }

      protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new VersionMunger(is, this, "weblogic.j2ee.descriptor.ApplicationClientBeanImpl$SchemaHelper2") {
            private boolean inEjbLink = false;

            public String getDtdNamespaceURI() {
               return "http://xmlns.jcp.org/xml/ns/javaee";
            }

            protected boolean isOldSchema() {
               String nameSpaceURI = this.getNamespaceURI();
               if (nameSpaceURI != null) {
                  if (nameSpaceURI.indexOf("j2ee") != -1) {
                     return true;
                  }

                  if (nameSpaceURI.contains("http://java.sun.com/xml/ns/javaee")) {
                     return true;
                  }
               }

               if (this.currentEvent.getElementName().equals("application-client")) {
                  int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

                  for(int i = 0; i < count; ++i) {
                     String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
                     String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
                     if (attValue.equals("1.4") || attValue.equals("5") || attValue.equals("6") || attValue.equals("7")) {
                        return true;
                     }
                  }
               }

               return false;
            }

            protected void transformOldSchema() {
               if (this.currentEvent.getElementName().equals("application-client")) {
                  int count = this.currentEvent.getReaderEventInfo().getAttributeCount();

                  for(int i = 0; i < count; ++i) {
                     String s = this.currentEvent.getReaderEventInfo().getAttributeLocalName(i);
                     if (s != null && s.equals("version")) {
                        String attValue = this.currentEvent.getReaderEventInfo().getAttributeValue(i);
                        if (attValue.equals("1.4") || attValue.equals("5") || attValue.equals("6") || attValue.equals("7")) {
                           this.versionInfo = attValue;
                           this.currentEvent.getReaderEventInfo().setAttributeValue("8", i);
                        }
                     }
                  }

                  this.transformNamespace("http://java.sun.com/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/j2ee");
                  this.transformNamespace("http://xmlns.jcp.org/xml/ns/javaee", this.currentEvent, "http://java.sun.com/xml/ns/javaee");
               }

               this.tranformedNamespace = "http://xmlns.jcp.org/xml/ns/javaee";
            }

            public VersionMunger.Continuation onStartElement(String localName) {
               if (!this.hasDTD()) {
                  if ("application-client".equals(localName)) {
                     this.checkAndUpdateVersionAttribute();
                  }
               } else {
                  if ("application-client".equals(localName)) {
                     this.checkAndUpdateVersionAttribute();
                  }

                  if ("ejb-link".equals(localName)) {
                     this.inEjbLink = true;
                  }
               }

               return CONTINUE;
            }

            protected VersionMunger.Continuation onCharacters(String s) {
               if (!this.hasDTD()) {
                  return CONTINUE;
               } else {
                  if (this.inEjbLink) {
                     this.replaceSlashWithPeriod(this.inEjbLink);
                  }

                  return CONTINUE;
               }
            }

            public VersionMunger.Continuation onEndElement(String localName) {
               if (!this.hasDTD()) {
                  return CONTINUE;
               } else {
                  if ("ejb-link".equals(localName)) {
                     this.inEjbLink = false;
                  }

                  return CONTINUE;
               }
            }

            protected String getLatestSchemaVersion() {
               return "8";
            }

            protected boolean enableCallbacksOnSchema() {
               return true;
            }
         };
      }
   }

   private class MyAbstractDescriptorLoader extends AbstractDescriptorLoader2 {
      MyAbstractDescriptorLoader(VirtualJarFile vjar, String uri) {
         super(vjar, uri);
      }

      MyAbstractDescriptorLoader(File altDD, String uri) {
         super(altDD, uri);
      }

      MyAbstractDescriptorLoader(GenericClassLoader gcl, String uri) {
         super(gcl, uri);
      }

      MyAbstractDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl, String uri) {
         super(edm, gcl, uri);
      }

      MyAbstractDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
         super(vjar, configDir, plan, moduleName, uri);
      }

      MyAbstractDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
         super(altDD, configDir, plan, moduleName, uri);
      }

      MyAbstractDescriptorLoader(GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
         this((GenericClassLoader)gcl, uri);
      }

      MyAbstractDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
         this((GenericClassLoader)gcl, uri);
      }

      protected DescriptorBean createDescriptorBean(InputStream is) throws IOException, XMLStreamException {
         this.munger = (VersionMunger)this.createXMLStreamReader(is);
         return !this.munger.hasDTD() && !(this.munger instanceof VersionMunger) ? this.getDescriptorManager().createDescriptor(this.munger).getRootBean() : this.getDescriptorManager().createDescriptor(this.munger.getPlaybackReader(), false).getRootBean();
      }
   }
}
