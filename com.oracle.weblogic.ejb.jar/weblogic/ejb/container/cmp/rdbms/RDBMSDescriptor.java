package weblogic.ejb.container.cmp.rdbms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader;
import weblogic.application.descriptor.BasicMunger;
import weblogic.application.descriptor.NamespaceURIMunger;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class RDBMSDescriptor implements weblogic.ejb.spi.RDBMSDescriptor {
   private static EditableDescriptorManager edm = new EditableDescriptorManager();
   MyWlsRDBMSDescriptor myWlsDP = null;
   private boolean writable = false;

   public RDBMSDescriptor(VirtualJarFile vjar, String fileName, String appName, String uri, DeploymentPlanBean plan, File configDir) {
      this.myWlsDP = new MyWlsRDBMSDescriptor(vjar, configDir, plan, uri, fileName);
   }

   public RDBMSDescriptor(DescriptorManager edm, String uri, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      this.myWlsDP = new MyWlsRDBMSDescriptor(edm, uri, gcl, configDir, plan, moduleName);
   }

   public RDBMSDescriptor(File altDD) {
      this.myWlsDP = new MyWlsRDBMSDescriptor(altDD);
   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.myWlsDP.getDeploymentPlan();
   }

   public DescriptorBean getDescriptorBean() throws IOException, XMLStreamException {
      try {
         return this.myWlsDP.getDeploymentPlan() != null ? this.myWlsDP.getPlanMergedDescriptorBean() : this.myWlsDP.getRootDescriptorBean();
      } catch (XMLStreamException var2) {
         throw new IOException(var2.toString());
      }
   }

   public DescriptorBean getEditableDescriptorBean() throws IOException, XMLStreamException {
      DescriptorBean var1;
      try {
         this.writable = true;
         var1 = this.getDescriptorBean();
      } catch (XMLStreamException var5) {
         throw new IOException(var5.toString());
      } finally {
         this.writable = false;
      }

      return var1;
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage();
      }

      if (args[0].lastIndexOf("create") > -1) {
         DescriptorManager dm = new DescriptorManager();
         Descriptor d = dm.createDescriptorRoot(WeblogicRdbmsJarBean.class);
         d.toXML(System.out);
         System.out.println("\n\n\n");
         d = dm.createDescriptorRoot(weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean.class);
         d.toXML(System.out);
         System.exit(0);
      }

      String jarPath = args[0];
      File f = new File(jarPath);
      if (f.getName().endsWith(".jar")) {
         JarFile jarJarFile = new JarFile(jarPath);
         VirtualJarFile vJarJarFile = VirtualJarFactory.createVirtualJar(jarJarFile);
         System.out.println("\n\n... getting RDBMSDescriptor from: " + jarJarFile);
         RDBMSDescriptor wd = new RDBMSDescriptor(vJarJarFile, (String)null, (String)null, (String)null, (DeploymentPlanBean)null, (File)null);
         wd.getDescriptorBean().getDescriptor().toXML(System.out);
      } else if (f.getPath().endsWith("weblogic-cmp-rdbms-jar.xml")) {
         System.out.println("\n\n... getting RDBMSDescriptor from: " + f.getPath());
         RDBMSDescriptor wd = new RDBMSDescriptor(f);
         wd.getDescriptorBean().getDescriptor().toXML(System.out);
      } else {
         System.out.println("\n\n... no jar or file specified");
      }

   }

   private static void usage() {
      System.err.println("usage: java weblogic.ejb.container.cmp.rdbms.RDBMSDescriptor <descriptor file name>");
      System.err.println("\n\n example:\n java weblogic.ejb.container.cmp.rdbms.RDBMSDescriptor jar or altDD file name ");
      System.exit(0);
   }

   private class MyWlsRDBMSDescriptor extends AbstractDescriptorLoader {
      private String fileName;

      MyWlsRDBMSDescriptor(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String fileName) {
         super(vjar, configDir, plan, moduleName);
         this.fileName = fileName;
      }

      MyWlsRDBMSDescriptor(DescriptorManager edm, String fileName, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
         super(edm, gcl, configDir, plan, moduleName);
         this.fileName = fileName;
      }

      MyWlsRDBMSDescriptor(File altDD) {
         super(altDD);
      }

      public Descriptor createDescriptor(InputStream is) throws IOException, XMLStreamException {
         if (!RDBMSDescriptor.this.writable) {
            return super.createDescriptor(is);
         } else {
            Descriptor var3;
            try {
               XMLStreamReader reader = this.getXMLStreamReader(is);
               var3 = RDBMSDescriptor.edm.createDescriptor(reader);
            } finally {
               try {
                  is.close();
               } catch (Exception var10) {
               }

            }

            return var3;
         }
      }

      public String getDocumentURI() {
         return this.fileName;
      }

      protected BasicMunger createXMLStreamReader(InputStream is) throws XMLStreamException {
         return new WlsCmpRdbmsReader(this.createXMLStreamReaderDelegate(is), this, super.getDeploymentPlan(), super.getModuleName(), this.getDocumentURI());
      }

      public XMLStreamReader createXMLStreamReaderDelegate(InputStream is) throws XMLStreamException {
         String[] oldNamespaceURIs = new String[]{"http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/weblogic-rdbms-jar"};
         return new NamespaceURIMunger(is, "http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", oldNamespaceURIs);
      }
   }
}
