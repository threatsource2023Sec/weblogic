package weblogic.messaging.interception.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.InterceptionBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class InterceptionParser {
   private AbstractDescriptorLoader2 interceptionDescriptor = null;
   private String interceptionFileName;
   private final boolean debug = false;

   public InterceptionBean createInterceptionDescriptor(String uri) throws ModuleException {
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else {
         if (this.interceptionDescriptor == null) {
            this.interceptionDescriptor = createDescriptorLoader(new File(uri), (File)null, (DeploymentPlanBean)null, (String)null, (String)null);
         }

         try {
            return this.getInterceptionBean();
         } catch (Exception var3) {
            throw new ModuleException(var3.toString(), var3);
         }
      }
   }

   public InterceptionBean createInterceptionDescriptor(ApplicationContextInternal appCtx, String uri) throws ModuleException {
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else {
         AppDeploymentMBean deployment = appCtx.getAppDeploymentMBean();
         DeploymentPlanBean plan = null;
         File configDir = null;
         String moduleName = null;
         String moduleUri = null;
         if (deployment != null) {
            plan = deployment.getDeploymentPlanDescriptor();
            configDir = null;
            if (deployment.getPlanDir() != null) {
               configDir = new File(deployment.getLocalPlanDir());
            }

            moduleName = this.getModuleName(deployment, uri);
            moduleUri = this.getModuleUri(deployment, uri);
         }

         if (this.interceptionDescriptor == null) {
            this.interceptionDescriptor = createDescriptorLoader(new File(this.getCanonicalPath(appCtx, uri)), configDir, plan, moduleName, moduleUri);
         }

         try {
            return this.getInterceptionBean();
         } catch (Exception var9) {
            throw new ModuleException(var9.toString());
         }
      }
   }

   private String getModuleUri(AppDeploymentMBean deployment, String uri) {
      return deployment.getSourcePath() != null && deployment.getSourcePath().endsWith(".xml") ? "." : uri;
   }

   private String getModuleName(AppDeploymentMBean deployment, String uri) {
      return deployment.getSourcePath() != null ? (new File(deployment.getSourcePath())).getName() : uri;
   }

   public InterceptionBean createInterceptionDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) throws IOException, XMLStreamException {
      this.interceptionDescriptor = createDescriptorLoader(edm, gcl, configDir, plan, moduleName, uri);
      return this.getInterceptionBean();
   }

   public String getCanonicalPath(ApplicationContextInternal appCtx, String fileName) {
      File[] paths = appCtx.getApplicationPaths();
      fileName = paths[0] + "/" + fileName;
      return (new File(fileName)).getAbsolutePath().replace(File.separatorChar, '/');
   }

   public InterceptionBean getInterceptionBean() throws IOException, XMLStreamException {
      return (InterceptionBean)this.interceptionDescriptor.loadDescriptorBean();
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(edm, gcl, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return InterceptionParser.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(File dd, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(dd, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return InterceptionParser.createVersionMunger(is, this);
         }
      };
   }

   private static VersionMunger createVersionMunger(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      String schemaHelper = "weblogic.j2ee.descriptor.wl.InterceptionBeanImpl$SchemaHelper2";
      return new VersionMunger(is, loader, schemaHelper, "http://xmlns.oracle.com/weblogic/weblogic-interception");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage();
      }

      System.out.println("\n\n... getting InterceptionBean:");
      ((DescriptorBean)(new InterceptionParser()).createInterceptionDescriptor(args[0])).getDescriptor().toXML(System.out);
   }

   private static void usage() {
      System.err.println("usage: java weblogic.messaging.interception.module.InterceptionParser <descriptor file name>");
      System.err.println("\n\n example:\n java weblogic.messaging.interception.module.InterceptionParser sample.xml");
      System.exit(0);
   }
}
