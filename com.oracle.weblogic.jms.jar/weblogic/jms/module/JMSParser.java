package weblogic.jms.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;
import weblogic.application.descriptor.AbstractDescriptorLoader2;
import weblogic.application.descriptor.VersionMunger;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.jms.common.JMSDebug;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class JMSParser {
   public static JMSBean createJMSDescriptor(String uri, String planURI) throws ModuleException {
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else if (planURI == null) {
         try {
            return createJMSDescriptor((ApplicationContextInternal)null, (BasicDeploymentMBean)null, uri);
         } catch (Exception var7) {
            throw new ModuleException("Could not create the JMS descriptor", var7);
         }
      } else {
         File moduleFile = new File(uri);
         File planFile = new File(planURI);
         DeploymentPlanDescriptorLoader dpd = new DeploymentPlanDescriptorLoader(planFile);

         DeploymentPlanBean planBean;
         try {
            planBean = dpd.getDeploymentPlanBean();
         } catch (IOException var10) {
            throw new ModuleException(var10.getMessage(), var10);
         } catch (XMLStreamException var11) {
            throw new ModuleException(var11.getMessage(), var11);
         }

         try {
            return createJMSDescriptor(moduleFile, moduleFile.getParentFile(), planBean, uri, uri);
         } catch (IOException var8) {
            throw new ModuleException(var8.getMessage(), var8);
         } catch (XMLStreamException var9) {
            throw new ModuleException(var9.getMessage(), var9);
         }
      }
   }

   public static JMSBean createJMSDescriptor(String uri) throws ModuleException {
      return createJMSDescriptor(uri, (String)null);
   }

   static JMSBean createJMSDescriptor(ApplicationContextInternal context, BasicDeploymentMBean basic, String uri) throws ModuleException {
      DeploymentPlanBean plan = null;
      String appName = null;
      AbstractDescriptorLoader2 jmsDescriptor = null;
      File configDir = null;
      if (uri == null) {
         throw new ModuleException("Null URI specified");
      } else {
         if (basic != null) {
            if (basic instanceof AppDeploymentMBean) {
               if (context.isEar()) {
                  File[] files = context.getEar().getModuleRoots(uri);
                  if (files.length <= 0) {
                     throw new ModuleException("Could not find the JMS module file \"" + uri + "\" in application \"" + context.getApplicationId() + "\"");
                  }
               }

               AppDeploymentMBean deployment = (AppDeploymentMBean)basic;
               plan = deployment.getDeploymentPlanDescriptor();
               if (deployment.getPlanDir() != null) {
                  configDir = new File(deployment.getLocalPlanDir());
               }

               appName = deployment.getName();
               if (jmsDescriptor == null) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("Creating jms descriptor from deployment at " + getCanonicalPath(context, uri));
                  }

                  String moduleName = getModuleName(deployment, uri);
                  String moduleUri = getModuleUri(deployment, uri);
                  jmsDescriptor = createDescriptorLoader(new File(getCanonicalPath(context, uri)), configDir, plan, moduleName, moduleUri);
               }
            } else {
               SystemResourceMBean syr = (SystemResourceMBean)basic;
               if (syr == null) {
                  throw new ModuleException("Application was neither a system resource nor a deployment");
               }

               appName = syr.getName();
               if (jmsDescriptor == null) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("Creating jms descriptor from system resource at " + getCanonicalPath(context, uri));
                  }

                  SituationalConfigManager situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
                  InputStream fis = situationalConfigManager.getSituationalConfigInputStream(context.getSystemResourceMBean().getSourcePath());
                  if (fis != null) {
                     jmsDescriptor = createDescriptorLoader(fis, appName, uri);
                  } else {
                     jmsDescriptor = createDescriptorLoader((File)(new File(getCanonicalPath(context, uri))), (File)null, (DeploymentPlanBean)null, appName, uri);
                  }
               }
            }
         } else if (jmsDescriptor == null) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Creating jms descriptor offline for " + uri);
            }

            jmsDescriptor = createDescriptorLoader((File)(new File(uri)), (File)null, (DeploymentPlanBean)null, (String)null, uri);
         }

         try {
            return getJMSBean(jmsDescriptor);
         } catch (Exception var10) {
            throw new ModuleException("Could not create the JMS descriptor", var10);
         }
      }
   }

   public AbstractDescriptorLoader2 getJmsDescriptorLoader(File dd, String uri) throws IOException, XMLStreamException {
      return createDescriptorLoader((File)dd, (File)null, (DeploymentPlanBean)null, (String)null, uri);
   }

   public AbstractDescriptorLoader2 getJmsDescriptorLoader(File dd, File configDir, DeploymentPlanBean plan, String moduleName, String uri) throws IOException, XMLStreamException {
      return createDescriptorLoader(dd, configDir, plan, moduleName, uri);
   }

   private static String getModuleUri(AppDeploymentMBean deployment, String uri) {
      return deployment.getSourcePath() != null && deployment.getSourcePath().endsWith(".xml") ? "." : uri;
   }

   private static String getModuleName(AppDeploymentMBean deployment, String uri) {
      return deployment.getSourcePath() != null ? (new File(deployment.getSourcePath())).getName() : uri;
   }

   public static String getModuleUri(String uri) {
      return uri != null && uri.endsWith(".xml") ? "." : uri;
   }

   public static String getModuleName(String uri) {
      return uri != null ? (new File(uri)).getName() : uri;
   }

   private static JMSBean createJMSDescriptor(VirtualJarFile vjar, String appName, String moduleName, String uri) throws ModuleException {
      File configDir = null;
      DeploymentPlanBean plan = null;
      ApplicationContextInternal aci = null;
      AbstractDescriptorLoader2 jmsDescriptor = null;
      if (appName != null) {
         aci = ApplicationAccess.getApplicationAccess().getApplicationContext(appName);
      }

      if (aci != null) {
         AppDeploymentMBean dmb = aci.getAppDeploymentMBean();
         plan = dmb.getDeploymentPlanDescriptor();
         if (dmb.getPlanDir() != null) {
            configDir = new File(dmb.getLocalPlanDir());
         }
      }

      jmsDescriptor = createDescriptorLoader(vjar, configDir, plan, moduleName, uri);

      try {
         return getJMSBean(jmsDescriptor);
      } catch (Exception var9) {
         throw new ModuleException("Could not create the JMS descriptor", var9);
      }
   }

   public static JMSBean createJMSDescriptor(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) throws ModuleException {
      AbstractDescriptorLoader2 jmsDescriptor = createDescriptorLoader(is, dm, errorHolder, validate);

      try {
         return getJMSBean(jmsDescriptor);
      } catch (Exception var6) {
         throw new ModuleException("Could not create the JMS descriptor", var6);
      }
   }

   public static JMSBean createJMSDescriptor(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) throws IOException, XMLStreamException {
      AbstractDescriptorLoader2 jmsDescriptor = createDescriptorLoader(edm, gcl, configDir, plan, moduleName, uri);
      return getJMSBean(jmsDescriptor);
   }

   private static JMSBean createJMSDescriptor(File dd, File configDir, DeploymentPlanBean plan, String moduleName, String uri) throws IOException, XMLStreamException {
      AbstractDescriptorLoader2 jmsDescriptor = createDescriptorLoader(dd, configDir, plan, moduleName, uri);
      return getJMSBean(jmsDescriptor);
   }

   private static String getCanonicalPath(ApplicationContextInternal appCtx, String fileName) {
      if (appCtx.isEar()) {
         File f = appCtx.getEar().getModuleRoots(fileName)[0];
         return f.getAbsolutePath().replace(File.separatorChar, '/');
      } else {
         return appCtx.getStagingPath();
      }
   }

   private static JMSBean getJMSBean(AbstractDescriptorLoader2 jmsDescriptor) throws IOException, XMLStreamException {
      return (JMSBean)jmsDescriptor.loadDescriptorBean();
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(DescriptorManager edm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(edm, gcl, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JMSParser.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(File dd, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(dd, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JMSParser.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(vjar, configDir, plan, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JMSParser.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(InputStream is, DescriptorManager dm, List errorHolder, boolean validate) {
      return new AbstractDescriptorLoader2(is, dm, errorHolder, validate) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JMSParser.createVersionMunger(is, this);
         }
      };
   }

   private static AbstractDescriptorLoader2 createDescriptorLoader(InputStream is, String moduleName, String uri) {
      return new AbstractDescriptorLoader2(is, moduleName, uri) {
         protected XMLStreamReader createXMLStreamReader(InputStream is) throws XMLStreamException {
            return JMSParser.createVersionMunger(is, this);
         }
      };
   }

   private static VersionMunger createVersionMunger(InputStream is, AbstractDescriptorLoader2 loader) throws XMLStreamException {
      String schemaHelper = "weblogic.j2ee.descriptor.wl.JMSBeanImpl$SchemaHelper2";
      return new VersionMunger(is, loader, schemaHelper, "http://xmlns.oracle.com/weblogic/weblogic-jms");
   }

   public static void main(String[] args) throws Exception {
      if (args.length < 1) {
         usage();
      }

      String filePath = args[0];
      String uri = null;
      if (args.length == 2) {
         uri = args[1];
      }

      String appName = null;
      String moduleName = null;
      File f = null;
      JMSBean rootBean = null;

      try {
         f = new File(filePath);
         if (f.getName().endsWith(".ear")) {
            JarFile jarJarFile = new JarFile(filePath);
            VirtualJarFile vJarJarFile = VirtualJarFactory.createVirtualJar(jarJarFile);
            if (uri == null) {
               Iterator entries = vJarJarFile.entries();

               while(entries.hasNext()) {
                  ZipEntry ze = (ZipEntry)entries.next();
                  if (ze.getName().endsWith("-jms.xml")) {
                     uri = ze.getName();
                     System.out.println("\n\n... getting JMSBean from EAR for uri " + uri + ":\n\n");

                     try {
                        rootBean = createJMSDescriptor(vJarJarFile, (String)appName, (String)moduleName, uri);
                        if (rootBean != null) {
                           DescriptorUtils.writeAsXML((DescriptorBean)rootBean);
                        }
                     } catch (Throwable var15) {
                        logValidationError(var15);
                     }
                  }
               }
            } else {
               System.out.println("\n\n... getting JMSBean from EAR for uri " + uri + ":\n\n");

               try {
                  rootBean = createJMSDescriptor(vJarJarFile, (String)appName, (String)moduleName, uri);
                  if (rootBean != null) {
                     DescriptorUtils.writeAsXML((DescriptorBean)rootBean);
                  }
               } catch (Throwable var14) {
                  logValidationError(var14);
               }
            }
         } else if (f.getPath().endsWith("-jms.xml")) {
            if (args.length == 1) {
               System.out.println("\n\n... getting JMSBean from JMSMD:\n\n");

               try {
                  rootBean = createJMSDescriptor(args[0]);
                  if (rootBean != null) {
                     DescriptorUtils.writeAsXML((DescriptorBean)rootBean);
                  }
               } catch (Throwable var13) {
                  logValidationError(var13);
               }
            } else if (args[1].endsWith("plan.xml")) {
               System.out.println("\n\n... plan:");

               try {
                  rootBean = createJMSDescriptor(args[0], args[1]);
                  if (rootBean != null) {
                     DescriptorUtils.writeAsXML((DescriptorBean)rootBean);
                  }
               } catch (Throwable var12) {
                  logValidationError(var12);
               }
            }
         } else {
            usage();
         }
      } catch (Throwable var16) {
         logValidationError(var16);
      }

   }

   private static void logValidationError(Throwable t) {
      System.out.println(t.toString());
      t.printStackTrace();
   }

   private static void usage() {
      System.err.println("usage: java weblogic.jms.module.JMSParser <JMS Module descriptor | J2EE Application Archive>");
      System.err.println("\n\n example:\n java weblogic.jms.module.JMSParser my-jms.xml");
      System.err.println("\n\n example:\n java weblogic.jms.module.JMSParser myapp.ear <Any JMS module URI inside this EAR>");
      System.exit(0);
   }
}
