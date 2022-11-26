package weblogic.management.provider.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import javax.management.JMException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.ConfigurationExtensionMBean;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConfigImageSource implements ImageSource {
   RuntimeAccess runtimeAccess;
   private boolean imageCreationTimeout = false;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ConfigImageSource(RuntimeAccess ra) {
      this.runtimeAccess = ra;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         JarOutputStream jarOut = new JarOutputStream(out);
         PrintWriter img = new PrintWriter(jarOut);

         try {
            this.imageCreationTimeout = false;
            DescriptorBean domain = this.runtimeAccess.getDomain();
            Descriptor desc = domain.getDescriptor();
            writeSystemProperties(desc, jarOut);
            writeDomainDirectory(desc, jarOut);
            writeEditLockState(jarOut);
            writeDescriptorTree(desc, jarOut);
            this.writeSystemPropertiesSchema(jarOut);
            this.writeDomainDirectorySchema(jarOut);
            this.writeEditLockStateSchema(jarOut);
            jarOut.flush();
            jarOut.close();
         } catch (Exception var10) {
            Exception e = var10;
            img.println("Error dumping bean tree " + var10);
            var10.printStackTrace(img);
            img.flush();

            for(Throwable t = var10.getCause(); t != null; t = e.getCause()) {
               img.println("\nCaused by: \n");
               t.printStackTrace(img);
            }

            throw new ImageSourceCreationException(e);
         } finally {
            img.flush();
         }

      } catch (IOException var12) {
         throw new ImageSourceCreationException(var12);
      }
   }

   public void timeoutImageCreation() {
      this.imageCreationTimeout = true;
   }

   private static void writeDescriptorTree(Descriptor descriptorTree, JarOutputStream jarOut) throws IOException, JMException {
      jarOut.putNextEntry(new JarEntry("config/config.xml"));
      DescriptorManagerHelper.saveDescriptor(descriptorTree, jarOut);
      jarOut.flush();
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(descriptorTree);
      HashSet descriptorFiles = new HashSet();

      while(it != null && it.hasNext()) {
         DescriptorInfo descInfo = (DescriptorInfo)it.next();
         Descriptor desc = descInfo.getDescriptor();
         ConfigurationExtensionMBean ce = descInfo.getConfigurationExtension();
         if (!descriptorFiles.contains(ce.getDescriptorFileName())) {
            descriptorFiles.add(ce.getDescriptorFileName());
            jarOut.putNextEntry(new JarEntry("config/" + ce.getDescriptorFileName()));
            DescriptorManager descMgr2 = descInfo.getDescriptorManager();
            DescriptorBean descBean = descInfo.getDescriptorBean();
            descMgr2.writeDescriptorBeanAsXML(descBean, jarOut);
            jarOut.flush();
         }
      }

   }

   private static void writeSystemProperties(Descriptor descriptorTree, JarOutputStream jarOut) throws IOException, JMException {
      jarOut.putNextEntry(new JarEntry("system-properties.xml"));
      PrintWriter pw = new PrintWriter(jarOut);
      Properties props = System.getProperties();
      Enumeration e = props.propertyNames();
      pw.println("<!-- System Properties -->");
      pw.println("<system-properties xsi:schemaLocation=\"schema/system-properties.xsd\">");

      while(e.hasMoreElements()) {
         String key = (String)e.nextElement();
         String val = props.getProperty(key);

         for(int i = 0; i < ConfigImageSourceService.PROTECTED.length; ++i) {
            if (key.toLowerCase(Locale.US).indexOf(ConfigImageSourceService.PROTECTED[i]) >= 0) {
               val = "********";
            }
         }

         pw.println("  <property>");
         pw.println("    <key>" + key + "</key>");
         pw.println("    <val>" + val + "</val>");
         pw.println("  </property>");
      }

      pw.println("</system-properties>");
      pw.println("");
      pw.flush();
      jarOut.flush();
   }

   private static void writeDomainDirectory(Descriptor descriptorTree, JarOutputStream jarOut) throws IOException, JMException {
      jarOut.putNextEntry(new JarEntry("domain-directory.xml"));
      PrintWriter pw = new PrintWriter(jarOut);
      pw.println("<!-- Domain Directory Information -->");
      pw.println("<domain-directory xsi:schemaLocation=\"schema/domain-directory.xsd\">");
      pw.println("  <root-dir>" + DomainDir.getRootDir() + "</root-dir>");
      pw.println("  <bin-dir>" + DomainDir.getBinDir() + "</bin-dir>");
      pw.println("  <config-dir>" + DomainDir.getConfigDir() + "</config-dir>");
      pw.println("  <deployments-dir>" + DomainDir.getDeploymentsDir() + "</deployments-dir>");
      pw.println("  <diagnostic-dir>" + DomainDir.getDiagnosticsDir() + "</diagnostic-dir>");
      pw.println("  <jdbc-dir>" + DomainDir.getJDBCDir() + "</jdbc-dir>");
      pw.println("  <jms-dir>" + DomainDir.getJMSDir() + "</jms-dir>");
      pw.println("  <config-security-dir>" + DomainDir.getConfigSecurityDir() + "</config-security-dir>");
      pw.println("  <config-startup-dir>" + DomainDir.getConfigStartupDir() + "</config-startup-dir>");
      pw.println("  <lib-modules-dir>" + DomainDir.getLibModulesDir() + "</lib-modules-dir>");
      pw.println("  <pending-dir>" + DomainDir.getPendingDir() + "</pending-dir>");
      pw.println("  <security-dir>" + DomainDir.getSecurityDir() + "</security-dir>");
      pw.println("  <servers-dir>" + DomainDir.getServersDir() + "</servers-dir>");
      pw.println("  <local-server-dir>" + DomainDir.getDirForServer(ManagementService.getRuntimeAccess(kernelId).getServerName()) + "</local-server-dir>");
      pw.println("</domain-directory>");
      pw.flush();
      jarOut.flush();
   }

   private void writeDomainDirectorySchema(JarOutputStream jarOut) throws IOException {
      jarOut.putNextEntry(new JarEntry("schema/domain-directory.xsd"));
      PrintWriter pw = new PrintWriter(jarOut);
      pw.println("<?xml version=\"1.0\"?>");
      pw.println("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
      pw.println(" targetNamespace=\"http://xmlns.oracle.com/weblogic//domain-directory\"");
      pw.println(" xmlns=\"http://http://xmlns.oracle.com/weblogic//domain-directory\"");
      pw.println(" elementFormDefault=\"qualified\">");
      pw.println("   <xs:complexType>");
      pw.println("     <xs:sequence>");
      pw.println("       <xs:element name=\"root-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"bin-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"config-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"deployments-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"diagnostic-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"jdbc-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"jms-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"config-security-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"config-startup-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"config-lib-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"lib-modules-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"pending-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"security-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"servers-dir\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"local-server-dir\" type=\"xs:string\"/>");
      pw.println("     </xs:sequence>");
      pw.println("   </xs:complexType>");
      pw.println("</xs:schema>");
      pw.flush();
      jarOut.flush();
   }

   private void writeSystemPropertiesSchema(JarOutputStream jarOut) throws IOException {
      jarOut.putNextEntry(new JarEntry("schema/system-properties.xsd"));
      PrintWriter pw = new PrintWriter(jarOut);
      pw.println("<?xml version=\"1.0\"?>");
      pw.println("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
      pw.println(" targetNamespace=\"http://xmlns.oracle.com/weblogic//system-properties\"");
      pw.println(" xmlns=\"http://http://xmlns.oracle.com/weblogic//system-properties\"");
      pw.println(" elementFormDefault=\"qualified\">");
      pw.println(" <xs:element name=\"property\">");
      pw.println("     <xs:complexType>");
      pw.println("       <xs:sequence>");
      pw.println("         <xs:element name=\"key\" type=\"xs:string\"/>");
      pw.println("         <xs:element name=\"val\" type=\"xs:string\"/>");
      pw.println("        </xs:sequence>");
      pw.println("     </xs:complexType>");
      pw.println(" </xs:element>");
      pw.println("</xs:schema>");
      pw.flush();
      jarOut.flush();
   }

   private static void writeEditLockState(JarOutputStream jarOut) throws IOException, JMException {
      EditAccess editAccess = ManagementServiceRestricted.getEditAccess(kernelId);
      if (editAccess != null) {
         jarOut.putNextEntry(new JarEntry("edit-lock-state.xml"));
         PrintWriter pw = new PrintWriter(jarOut);
         pw.println("<!-- Edit Lock Information -->");
         pw.println("<edit-lock xsi:schemaLocation=\"schema/edit-lock-state.xsd\">");
         pw.println("  <editor>" + editAccess.getEditor() + "</editor>");
         pw.println("  <start-time>" + editAccess.getEditorStartTime() + "</start-time>");
         pw.println("  <expiration-time>" + editAccess.getEditorExpirationTime() + "</expiration-time>");
         pw.println("  <exclusive>" + editAccess.isEditorExclusive() + "</exclusive>");
         pw.println("</edit-lock>");
         pw.flush();
         jarOut.flush();
      }
   }

   private void writeEditLockStateSchema(JarOutputStream jarOut) throws IOException {
      jarOut.putNextEntry(new JarEntry("schema/edit-lock-state.xsd"));
      PrintWriter pw = new PrintWriter(jarOut);
      pw.println("<?xml version=\"1.0\"?>");
      pw.println("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
      pw.println(" targetNamespace=\"http://xmlns.oracle.com/weblogic//edit-lock-state\"");
      pw.println(" xmlns=\"http://http://xmlns.oracle.com/weblogic//edit-lock-state\"");
      pw.println(" elementFormDefault=\"qualified\">");
      pw.println("   <xs:complexType>");
      pw.println("     <xs:sequence>");
      pw.println("       <xs:element name=\"editor\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"start-time\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"expiration-time\" type=\"xs:string\"/>");
      pw.println("       <xs:element name=\"exclusive\" type=\"xs:boolean\"/>");
      pw.println("     </xs:sequence>");
      pw.println("   </xs:complexType>");
      pw.println("</xs:schema>");
      pw.flush();
      jarOut.flush();
   }
}
