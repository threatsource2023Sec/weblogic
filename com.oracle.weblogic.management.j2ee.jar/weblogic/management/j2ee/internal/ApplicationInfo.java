package weblogic.management.j2ee.internal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.enterprise.deploy.model.DeployableObject;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.spi.WebLogicDConfigBeanRoot;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.Debug;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

class ApplicationInfo {
   public static final int J2EE_APP = 1;
   public static final int J2EE_WEB_MODULE = 2;
   public static final int J2EE_EJB_MODULE = 3;
   public static final int J2EE_CONNECTOR_MODULE = 4;
   public static final int J2EE_APP_CLIENT_MODULE = 5;
   private static final String APP_DD_URI = "META-INF/application.xml";
   private static final String WEB_DD_URI = "WEB-INF/web.xml";
   private static final String EJB_DD_URI = "META-INF/ejb-jar.xml";
   private static final String RAR_DD_URI = "META-INF/ra.xml";
   private static final String WLS_APP_DD_URI = "META-INF/weblogic-application.xml";
   private static final String WLS_WEB_DD_URI = "WEB-INF/weblogic.xml";
   private static final String WLS_EJB_DD_URI = "META-INF/weblogic-ejb-jar.xml";
   private static final String WLS_RAR_DD_URI = "META-INF/weblogic-ra.xml";
   private final ObjectName name;
   private final int type;
   private final MBeanServerConnection domainConnection;
   private final MBeanServerConnection editConnection;
   private final DescriptorManager descriptorManager;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJ2EEManagement");
   private String appSource = null;
   private String split_build_dir = null;

   public ApplicationInfo(ObjectName name, int type) {
      this.name = name;
      this.type = type;
      this.domainConnection = MBeanServerConnectionProvider.getDomainMBeanServerConnection();
      this.editConnection = MBeanServerConnectionProvider.getEditMBeanServerConnection();
      this.descriptorManager = new DescriptorManager();
   }

   public String getDescriptor() {
      switch (this.type) {
         case 1:
            return this.getApplicationDD();
         case 2:
            return this.getWebModuleDD();
         case 3:
            return this.getEJBModuleDD();
         case 4:
            return this.getConnectorModuleDD();
         default:
            throw new AssertionError("In valid request");
      }
   }

   public String getWebLogicDescriptor() {
      switch (this.type) {
         case 1:
            return this.getWebLogicApplicationDD();
         case 2:
            return this.getWebLogicWebModuleDD();
         case 3:
            return this.getWebLogicEJBModuleDD();
         case 4:
            return this.getWebLogicConnectorModuleDD();
         default:
            throw new AssertionError("In valid request");
      }
   }

   public boolean isEar() {
      return this.isEar(this.getApplicationSource());
   }

   public boolean isParentEar() {
      ObjectName parentName = this.getParentObjectName();
      return this.isEar(this.getApplicationSource(parentName));
   }

   private String getApplicationLevelDD(String ddType) {
      InputStream in = null;
      VirtualJarFile file = null;
      SessionHelper helper = null;
      DeployableObject deployableObject = null;
      WebLogicDDBeanRoot dRoot = null;
      WebLogicDConfigBeanRoot configRoot = null;
      AbstractDescriptorBean descriptor = null;

      String var11;
      try {
         String source = this.getApplicationSource();
         file = VirtualJarFactory.createVirtualJar(new File(source));
         ZipEntry entry = file.getEntry(ddType);
         if (entry == null) {
            helper = SessionHelper.getInstance(SessionHelper.getDisconnectedDeploymentManager());
            helper.initializeConfiguration(new File(source), (File)null);
            deployableObject = helper.getDeployableObject();
            dRoot = (WebLogicDDBeanRoot)deployableObject.getDDBeanRoot();
            if ("META-INF/application.xml".equals(ddType)) {
               descriptor = (AbstractDescriptorBean)dRoot.getDescriptorBean();
            } else {
               configRoot = (WebLogicDConfigBeanRoot)helper.getConfiguration().getDConfigBeanRoot(dRoot);
               descriptor = (AbstractDescriptorBean)configRoot.getDescriptorBean();
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            this.descriptorManager.writeDescriptorAsXML(descriptor.getDescriptor(), stream);
            String var12 = stream.toString();
            return var12;
         }

         in = file.getInputStream(entry);
         var11 = this.makeDescriptor(in);
      } catch (Exception var22) {
         throw new AssertionError(" Unable to read Application Deployment Descriptor: " + ddType);
      } finally {
         try {
            if (in != null) {
               in.close();
            }

            if (file != null) {
               file.close();
            }
         } catch (IOException var21) {
         }

      }

      return var11;
   }

   private String getApplicationDD() {
      return this.getApplicationLevelDD("META-INF/application.xml");
   }

   private String getWebLogicApplicationDD() {
      return this.getApplicationLevelDD("META-INF/weblogic-application.xml");
   }

   private String getWebModuleDD() {
      try {
         ObjectName parent = this.getParentObjectName();
         String source = this.getApplicationSource(parent);
         String uri = (String)this.domainConnection.getAttribute(this.name, "ModuleURI");
         source = source.replace("/\\", "/");
         return this.getModuleDescriptor(source, uri, "WEB-INF/web.xml");
      } catch (Throwable var4) {
         throw new AssertionError("Unable to get information about Web Module");
      }
   }

   private String getWebLogicWebModuleDD() {
      return this.getModuleDD("WEB-INF/weblogic.xml");
   }

   private String getEJBModuleLevelDD(String ddType) {
      try {
         ObjectName parent = this.getParentObjectName();
         String source = this.getApplicationSource(parent);
         String uri = (String)this.domainConnection.getAttribute(this.name, "ModuleId");
         String mdd = this.getModuleDescriptor(source, uri, ddType);
         if (mdd == null) {
            mdd = this.getModuleDescriptor(this.appSource, uri, ddType);
         }

         if (mdd == null) {
            mdd = "";
         }

         return mdd;
      } catch (Throwable var6) {
         throw new AssertionError("Unabled get information about the Module: " + ddType);
      }
   }

   private String getEJBModuleDD() {
      return this.getEJBModuleLevelDD("META-INF/ejb-jar.xml");
   }

   private String getWebLogicEJBModuleDD() {
      return this.getEJBModuleLevelDD("META-INF/weblogic-ejb-jar.xml");
   }

   private String getModuleDD(String ddType) {
      try {
         ObjectName parent = this.getParentObjectName();
         String source = this.getApplicationSource(parent);
         String uri = (String)this.domainConnection.getAttribute(this.name, "ModuleId");
         return this.getModuleDescriptor(source, uri, ddType);
      } catch (Throwable var5) {
         throw new AssertionError("Unabled get information about the Module: " + ddType);
      }
   }

   private String getConnectorModuleDD() {
      return this.getModuleDD("META-INF/ra.xml");
   }

   private String getWebLogicConnectorModuleDD() {
      return this.getModuleDD("META-INF/weblogic-ra.xml");
   }

   private String getApplicationSource(ObjectName appRuntime) {
      if (this.type == 3 && this.split_build_dir != null) {
         return this.split_build_dir;
      } else if (this.appSource != null) {
         return this.appSource;
      } else {
         String appName = null;

         try {
            appName = (String)this.domainConnection.getAttribute(appRuntime, "ApplicationName");
            String sObjectName = appRuntime.getDomain() + ":Name=" + appName + ",Type=AppDeployment";

            try {
               this.appSource = (String)this.editConnection.getAttribute(new ObjectName(sObjectName), "SourcePath");
            } catch (InstanceNotFoundException var5) {
               this.appSource = InternalAppDataCacheService.getSourceLocation(appName);
            }

            this.appSource = this.getCorrectPathIfSplitDir(this.appSource);
            return this.appSource;
         } catch (Throwable var6) {
            throw new AssertionError("Failed to get information about the application" + var6);
         }
      }
   }

   private String getCorrectPathIfSplitDir(String path) {
      InputStream in = null;
      File beaBuildPath = new File(path, ".beabuild.txt");
      if (!beaBuildPath.exists()) {
         return path;
      } else {
         String var6;
         try {
            String correctSource;
            try {
               in = new FileInputStream(beaBuildPath);
               Properties prop = new Properties();
               prop.load(in);
               correctSource = prop.getProperty("bea.srcdir");
               if (correctSource == null) {
                  return path;
               }

               this.split_build_dir = path;
               var6 = correctSource;
            } catch (Throwable var17) {
               correctSource = path;
               return correctSource;
            }
         } finally {
            try {
               if (in != null) {
                  in.close();
               }
            } catch (IOException var16) {
            }

         }

         return var6;
      }
   }

   private String getApplicationSource() {
      return this.getApplicationSource(this.name);
   }

   private boolean isEar(String appSource) {
      if (appSource == null) {
         return false;
      } else if (appSource.endsWith("ear")) {
         return true;
      } else {
         VirtualJarFile file = null;

         boolean var4;
         try {
            file = VirtualJarFactory.createVirtualJar(new File(appSource));
            if (file.getEntry("META-INF/application.xml") != null) {
               boolean var3 = true;
               return var3;
            }

            return false;
         } catch (IOException var15) {
            var4 = false;
         } finally {
            try {
               if (file != null) {
                  file.close();
               }
            } catch (IOException var14) {
            }

         }

         return var4;
      }
   }

   private ObjectName getParentObjectName() {
      try {
         return (ObjectName)this.domainConnection.getAttribute(this.name, "Parent");
      } catch (InstanceNotFoundException var2) {
         throw new Error("Failed to access information about parent ObjectName", var2);
      } catch (ReflectionException var3) {
         throw new Error("Failed to access information about parent ObjectName", var3);
      } catch (IOException var4) {
         throw new Error("Failed to access information about parent ObjectName", var4);
      } catch (MBeanException var5) {
         throw new Error("Failed to access information about parent ObjectName", var5);
      } catch (AttributeNotFoundException var6) {
         throw new Error("Failed to access information about parent ObjectName", var6);
      }
   }

   private String getModuleDescriptor(String source, String uri, String ddType) {
      if (debug.isDebugEnabled()) {
         Debug.say("Application source: " + source + "  With uri: " + uri + "  and deployment descriptor location: " + ddType);
      }

      ZipEntry entry = null;
      InputStream in = null;
      VirtualJarFile file = null;

      ZipEntry descriptor;
      try {
         file = VirtualJarFactory.createVirtualJar(new File(source));
         Iterator it;
         if (debug.isDebugEnabled()) {
            for(it = file.entries(); it.hasNext(); descriptor = (ZipEntry)it.next()) {
            }
         }

         entry = file.getEntry(ddType);
         String exploded_uri;
         if (entry != null) {
            in = file.getInputStream(entry);
            exploded_uri = this.makeDescriptor(in);
            return exploded_uri;
         }

         entry = file.getEntry(uri);
         if (entry == null) {
            if ("WEB-INF/web.xml".equals(ddType) || "META-INF/ejb-jar.xml".equals(ddType) || "META-INF/ra.xml".equals(ddType)) {
               try {
                  SessionHelper helper = SessionHelper.getInstance(SessionHelper.getDisconnectedDeploymentManager());
                  helper.initializeConfiguration(new File(source), (File)null);
                  descriptor = null;
                  DeployableObject deployableObject = helper.getDeployableObject();
                  WebLogicDDBeanRoot dRoot = (WebLogicDDBeanRoot)deployableObject.getDDBeanRoot();
                  WebLogicDConfigBeanRoot configRoot = (WebLogicDConfigBeanRoot)helper.getConfiguration().getDConfigBeanRoot(dRoot);
                  AbstractDescriptorBean descriptor = (AbstractDescriptorBean)configRoot.getDescriptorBean();
                  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                  this.descriptorManager.writeDescriptorAsXML(descriptor.getDescriptor(), stream);
                  String var13 = stream.toString();
                  return var13;
               } catch (Exception var28) {
               }
            }

            it = null;
            return it;
         }

         exploded_uri = uri + "/" + ddType;
         descriptor = file.getEntry(exploded_uri);
         String var9;
         if (descriptor == null) {
            in = file.getInputStream(entry);
            var9 = this.makeDescriptor(in, ddType);
            return var9;
         }

         in = file.getInputStream(descriptor);
         var9 = this.makeDescriptor(in);
         return var9;
      } catch (IOException var29) {
         descriptor = null;
      } finally {
         try {
            if (in != null) {
               in.close();
            }

            if (file != null) {
               file.close();
            }
         } catch (IOException var27) {
         }

      }

      return descriptor;
   }

   private String makeDescriptor(InputStream in) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));

      try {
         String descriptor;
         String descriptorLine;
         for(descriptor = new String(); (descriptorLine = reader.readLine()) != null; descriptor = descriptor + descriptorLine + System.getProperty("line.separator")) {
         }

         reader.close();
         String var5 = descriptor;
         return var5;
      } catch (IOException var14) {
         throw new AssertionError(" Unable to read Application Deployment Descriptor");
      } finally {
         try {
            if (reader != null) {
               reader.close();
            }
         } catch (IOException var13) {
         }

      }
   }

   private String makeDescriptor(InputStream input, String uri) {
      ByteArrayOutputStream out = null;
      ZipInputStream zis = null;
      String descriptor = new String();

      String var7;
      try {
         zis = new ZipInputStream(input);

         for(ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
            if (ze.getName().equals(uri)) {
               out = new ByteArrayOutputStream();
               byte[] buf = new byte[1024];

               int r;
               while((r = zis.read(buf)) > 0) {
                  out.write(buf, 0, r);
               }

               out.flush();
               descriptor = out.toString();
               return descriptor;
            }
         }

         return descriptor;
      } catch (IOException var17) {
         var7 = descriptor;
      } finally {
         try {
            if (out != null) {
               out.close();
            }

            if (zis != null) {
               zis.close();
            }
         } catch (IOException var16) {
         }

      }

      return var7;
   }
}
