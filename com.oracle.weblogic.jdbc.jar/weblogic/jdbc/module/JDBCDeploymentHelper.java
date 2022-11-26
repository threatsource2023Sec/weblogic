package weblogic.jdbc.module;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.GenericClassLoader;

public class JDBCDeploymentHelper {
   private String uri;
   private AbstractDescriptorLoader2 jdbcDescriptor;
   private String jdbcFileName;
   private final boolean debug;
   private final JDBCSystemResourceMBean jdbcSystemResource;
   private final ActiveBeanUtil activeBeanUtil;

   public JDBCDeploymentHelper(JDBCSystemResourceMBean jdbcSR) {
      this.uri = null;
      this.jdbcDescriptor = null;
      this.debug = false;
      this.activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
      this.jdbcSystemResource = jdbcSR;
   }

   public JDBCDeploymentHelper() {
      this((JDBCSystemResourceMBean)null);
   }

   public JDBCDataSourceBean createJDBCDataSourceDescriptor(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) throws ModuleException {
      try {
         this.jdbcDescriptor = createDescriptorLoader(is, dm, errorHolder, validate);
         return this.getJDBCDataSourceBean();
      } catch (Exception var6) {
         throw new ModuleException(var6);
      }
   }

   public JDBCDataSourceBean createJDBCDataSourceDescriptor(String uri) throws ModuleException {
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else {
         try {
            this.jdbcDescriptor = createDescriptorLoader(new File(uri), (File)null, (DeploymentPlanBean)null, (String)null, uri);
            return this.getJDBCDataSourceBean();
         } catch (Exception var3) {
            throw new ModuleException(var3);
         }
      }
   }

   public boolean hasDeploymentPlan(ApplicationContextInternal appCtx) {
      AppDeploymentMBean deployment = appCtx.getAppDeploymentMBean();
      if (deployment == null) {
         return false;
      } else {
         return deployment.getDeploymentPlanDescriptor() != null;
      }
   }

   public JDBCDataSourceBean createJDBCDataSourceDescriptor(ApplicationContextInternal appCtx, String uri) throws ModuleException {
      this.uri = uri;
      DeploymentPlanBean plan = null;
      File configDir = null;
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else {
         AppDeploymentMBean deployment = appCtx.getAppDeploymentMBean();
         String moduleName = null;
         String moduleUri = null;
         if (deployment != null) {
            plan = deployment.getDeploymentPlanDescriptor();
            if (deployment.getPlanDir() != null) {
               configDir = new File(deployment.getLocalPlanDir());
            }

            moduleName = this.getModuleName(deployment, uri);
            moduleUri = this.getModuleUri(deployment, uri);
         }

         File altDD = null;
         String path = this.getCanonicalPath(appCtx, uri);
         if (path != null) {
            altDD = new File(path);
         }

         SituationalConfigManager situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
         InputStream fis = appCtx.getSystemResourceMBean() == null ? null : situationalConfigManager.getSituationalConfigInputStream(appCtx.getSystemResourceMBean().getSourcePath());
         if (fis != null) {
            this.jdbcDescriptor = createDescriptorLoader(fis, moduleName, moduleUri);
         } else {
            this.jdbcDescriptor = createDescriptorLoader(altDD, configDir, plan, moduleName, moduleUri);
         }

         try {
            return this.getJDBCDataSourceBean();
         } catch (Exception var13) {
            throw new ModuleException(var13);
         }
      }
   }

   public String getModuleUri(String uri) {
      return uri != null && uri.endsWith(".xml") ? "." : uri;
   }

   public String getModuleName(String uri) {
      return uri != null ? (new File(uri)).getName() : uri;
   }

   private String getModuleUri(AppDeploymentMBean deployment, String uri) {
      return deployment.getSourcePath() != null && deployment.getSourcePath().endsWith(".xml") ? "." : uri;
   }

   private String getModuleName(AppDeploymentMBean deployment, String uri) {
      return deployment.getSourcePath() != null ? (new File(deployment.getSourcePath())).getName() : uri;
   }

   public JDBCDataSourceBean createJDBCDataSourceDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) throws IOException, XMLStreamException {
      this.jdbcDescriptor = createDescriptorLoader(edm, gcl, configDir, plan, moduleName, uri);
      return this.getJDBCDataSourceBean();
   }

   public JDBCDataSourceBean createJDBCDataSourceDescriptor(File dd, File configDir, DeploymentPlanBean plan, String moduleName, String uri) throws IOException, XMLStreamException {
      this.jdbcDescriptor = createDescriptorLoader(dd, configDir, plan, moduleName, uri);
      return this.getJDBCDataSourceBean();
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(File dd, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(dd, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JDBCDeploymentHelper.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(edm, gcl, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JDBCDeploymentHelper.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) {
      return new AbstractDescriptorLoader2(is, dm, errorHolder, validate) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JDBCDeploymentHelper.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(InputStream is, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(is, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JDBCDeploymentHelper.createVersionMunger(is, this);
         }
      };
   }

   private static VersionMunger createVersionMunger(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      String schemaHelper = "weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanImpl$SchemaHelper2";
      return new VersionMunger(is, loader, schemaHelper, "http://xmlns.oracle.com/weblogic/jdbc-data-source");
   }

   public String getCanonicalPath(ApplicationContextInternal appCtx, String fileName) throws ModuleException {
      if (appCtx.isEar()) {
         File[] files = appCtx.getEar().getModuleRoots(fileName);
         if (files.length == 0) {
            return null;
         } else {
            File f = files[0];
            return f.getAbsolutePath().replace(File.separatorChar, '/');
         }
      } else {
         return appCtx.getStagingPath();
      }
   }

   public JDBCDataSourceBean getJDBCDataSourceBean() throws IOException, XMLStreamException {
      return (JDBCDataSourceBean)PartitionProcessor.processIfClone(this.jdbcSystemResource, (JDBCDataSourceBean)this.jdbcDescriptor.loadDescriptorBean());
   }

   public static String getSystemResourceName(String name, int legacyType) {
      if (legacyType == 1) {
         return new String("CP-" + name);
      } else if (legacyType == 2) {
         return new String("MP-" + name);
      } else if (legacyType == 3) {
         return new String("DS-" + name);
      } else {
         return legacyType == 4 ? new String("TxDS-" + name) : new String("");
      }
   }

   public static void writeModuleAsXML(DescriptorBean db) {
      Descriptor desc = db.getDescriptor();

      try {
         EditableDescriptorManager edm = new EditableDescriptorManager();
         edm.writeDescriptorAsXML(desc, new BufferedOutputStream(System.out) {
            public void close() {
            }
         });
      } catch (IOException var3) {
      }

   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage();
      }

      System.out.println("\n\n... getting InterceptionBean:");
      ((DescriptorBean)(new JDBCDeploymentHelper()).createJDBCDataSourceDescriptor(args[0])).getDescriptor().toXML(System.out);
   }

   private static void usage() {
      System.err.println("usage: java weblogic.jdbc.module.JDBCDeploymentHelper <descriptor file name>");
      System.err.println("\n\n example:\n java weblogic.jdbc.module.JDBCDeploymentHelper sample.xml");
      System.exit(0);
   }
}
