package weblogic.management.tools.migration;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;
import weblogic.descriptor.BasicDescriptorManager;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.SecurityService;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.security.Salt;
import weblogic.security.UserConfigFileManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.internal.encryption.JSafeEncryptionServiceFactory;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StringUtils;
import weblogic.utils.XXEUtils;

public final class DescriptorHelper {
   public static final String MIGRATION_XML = "config/migrate-config.xml";
   public static final String ATTRIBUTES_JSON = "-attributes.json";
   public static final String SecretKeyFileName = "archiveSecret";
   public static final String REPORT_HTML = "-ExportLogReport.html";
   public HashMap skippedResources = new HashMap();
   private static final String ATT_NAME_ENCRYPD_SUFFIX = "Encrypted";
   private static final Logger LOGGER = Logger.getLogger(DescriptorHelper.class.getName());
   private static Class JMSBeanClass;
   private static Class NamedEntityBeanClass;
   private static Class TargetableClass;
   private static Class WeightedDistributedQueueClass;
   private static Class WeightedDistributedTopicClass;
   private static Class ForeignServerBeanClass;
   private static Class JDBCDataSourceBeanClass;
   private static Class JDBCDriverParamsBeanClass;
   private static Class SAFRemoteContextClass;
   private static Class SAFLoginContextClass;
   private static Class UniformDistributedTopicClass;
   private static BeanInfoAccess beanInfoAccess = null;
   private static String Secret_Key_Filepath = null;
   private static DescriptorSecurityServiceImpl descriptorSecSvc = null;
   private static DomainMBean domainMBean = null;
   private static AuthenticatedSubject kernelId = null;
   private static String configDirPath = null;
   private static final String DEFAULT_TGTED_DEST_MSG = "The JMS Destination '%s' of type '%s' in JMS Module '%s' is default targeted. This configuration is not supported when importing this domain to a partition. Destinations should always be targeted using a subdeployment before attempting an export, and the subdeployment should in turn reference a JMS Server or a SAF Agent.";
   private static final String SUBDEPLOY_INVALID_TGT_MSG = "The JMS Destination '%s' of type '%s' with subdeployment '%s' in JMS Module '%s' is targeted to '%s' of type '%s' . This configuration is not supported when importing this domain to a partition. Subdeployments should always be targeted to a JMS Server or a SAF Agent before export, otherwise they will be left untargeted when imported to a partition and any resources that reference the subdeployment will not deploy.";
   private static final String SUBDEPLOY_UNTARGETED_MSG = "The JMS Destination '%s' with subdeployment '%s' in JMS Module '%s' is not targeted, so it will be left untargeted when imported to a partition.";
   private static final String SERVER_PARTOF_CLUSTER_MSG = "The standalone JMS destination '%s' of type '%s' with subdeployment '%s' in JMS Module '%s' is targeted to '%s' of type '%s' and '%s' is associated with a specific server in cluster '%s' via a target of '%s' of type '%s'. Partition scoped standalone destinations do not support targeting a particular Server or Migratable, so, after import, this subdeployment will be targeted to a singleton JMS Server with an effective target of a Cluster, the destination runtime will therefore be hosted on a single random server in the cluster, and the destination's host server may change after failures or restarts. If the original configuration absolutely requires that the destination have an affinity to a particular server in the cluster then the stand-alone destination should be replaced with a distributed destination: distributed destinations create a member on each server on the cluster, these members are each individually addressable as a Standalone destination, and each member automatically has an affinity to a particular server in its cluster.";
   private static final String OTHER_DEST_SERVER_PARTOF_CLUSTER_MSG = "The JMS destination '%s' of type '%s' with subdeployment '%s' in JMS Module '%s' is targeted to '%s' of type '%s' and '%s' is associated with a specific server in cluster '%s' via a target of '%s' of type '%s'. Partition scoped destinations do not support targeting a particular Server or Migratable, so, after import, this subdeployment will be targeted to a JMS Server/SAF Agent with an effective target of a Cluster, the destination runtime will therefore be hosted on all the members of the cluster.";
   private static final String REPLICATED_DIST_TOPIC_NOT_SUPPORTED_MSG = "The Uniform Distributed Topic '%s' in JMS Module '%s' is configured to use the forwarding policy 'Replicated'(the default). This configuration is not supported when importing this domain to a partition, so the forwarding policy will be changed to 'Partitioned'. Partitioned topics have very different semantics than replicated topics so additional application tuning changes and even application code changes may be required to get the desired behavior; for example, MDBs and SOA JMS Adapters will need to specify a message distribution policy of 'one copy per server' or 'one copy per application'. It is recommended to consult the documentation (pending) for details, to convert the original configuration to use partitioned topics, and to test changes prior to export.";
   private static final String JMS_SRVR_CONFLICT_DESTS_MSG = "One or more JMS Server(s) '%s' was found to host conflicting JMS destination types - %s and %s, which is not supported for export. A JMS server cannot host both %s and %s. Correct the configuration and try exporting the configuration again.";
   private static final String SUBDEPLOY_CONFLICT_DESTS_MSG = "One or more Subdeployment(s) '%s' in JMS module '%s' is being referred by conflicting JMS destination types - %s and %s, which is not supported for export. A subdeployment cannot be referred from both %s and %s. Correct the configuration and try exporting the configuration again.";
   private static final String WDD_NOT_SUPPORTED_MSG = "Weighted distributed destinations are deprecated and is not supported when importing a JMS module containing them to a partition. The bean '%s' of type '%s' in JMS Module '%s' will not be exported.";
   private static final String GETTER_METHOD_NOT_FOUND_FOR_ATTR = "Read method not found for property '%s' for MBean '%s' of type '%s'";

   public DescriptorHelper() throws Exception {
      this.init((String)null);
   }

   public DescriptorHelper(String configPath) throws Exception {
      this.init(configPath);
   }

   public String getSecretKeyFilePath() {
      return Secret_Key_Filepath;
   }

   public void setSecretKeyFileDir(String directoryPath) {
      try {
         String filename = (directoryPath == null ? System.getProperty("java.io.tmpdir") : directoryPath) + "/" + "archiveSecret";
         Secret_Key_Filepath = StringUtils.replaceGlobal(filename, File.separator, "/");
      } catch (Exception var3) {
         LOGGER.log(Level.WARNING, "setSecretKeyFileDir exception " + var3);
      }

   }

   private static void loadClasses() throws ClassNotFoundException {
      JDBCDataSourceBeanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBean");
      JDBCDriverParamsBeanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean");
      JMSBeanClass = Class.forName("weblogic.j2ee.descriptor.wl.JMSBean");
      ForeignServerBeanClass = Class.forName("weblogic.j2ee.descriptor.wl.ForeignServerBean");
      NamedEntityBeanClass = Class.forName("weblogic.j2ee.descriptor.wl.NamedEntityBean");
      TargetableClass = Class.forName("weblogic.j2ee.descriptor.wl.TargetableBean");
      WeightedDistributedQueueClass = Class.forName("weblogic.j2ee.descriptor.wl.DistributedQueueBean");
      WeightedDistributedTopicClass = Class.forName("weblogic.j2ee.descriptor.wl.DistributedTopicBean");
      SAFRemoteContextClass = Class.forName("weblogic.j2ee.descriptor.wl.SAFRemoteContextBean");
      SAFLoginContextClass = Class.forName("weblogic.j2ee.descriptor.wl.SAFLoginContextBean");
      UniformDistributedTopicClass = Class.forName("weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean");
   }

   private void init(String configPath) throws Exception {
      loadClasses();
      if (configPath == null) {
         try {
            kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         } catch (Exception var3) {
            kernelId = null;
         }

         beanInfoAccess = ManagementService.getBeanInfoAccess();
         domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      } else {
         configDirPath = configPath;
         System.setProperty("weblogic.RootDirectory", configPath.substring(0, configPath.length() - 7));
         beanInfoAccess = ManagementServiceClient.getBeanInfoAccess();
         domainMBean = ManagementServiceClient.getClientAccess().getDomain(configPath + "/config.xml", false);
      }

      LOGGER.log(Level.FINE, "DescriptorHelper kernelId=" + kernelId + " configPath=" + configPath);
   }

   public String exportDescriptor(String beanPath, File descriptorFile, String userKeyFile, boolean isSystemResorApp) throws Exception {
      String descpContent = new String(Files.readAllBytes(Paths.get(descriptorFile.toURI())));
      return this.exportDescriptor(beanPath, descpContent, userKeyFile, isSystemResorApp);
   }

   public String exportDescriptor(String beanPath, String descpContent, String userKeyFile, boolean isSystemResorApp) throws Exception {
      DescriptorBean bean = null;
      LOGGER.log(Level.FINE, "exportDescriptor beanPath " + beanPath);
      LOGGER.log(Level.FINE, "exportDescriptor DomainBean " + domainMBean);
      String[] beansp = beanPath.split("/");
      int i = 0;
      if (beansp.length == 3) {
         i = 1;
      } else if (beansp.length == 4) {
         i = 2;
      }

      if (i > 0 && beansp[i].startsWith("JDBCSystemResource")) {
         Object bn = domainMBean.lookupJDBCSystemResource(beansp[i + 1]);
         LOGGER.log(Level.FINE, "JDBC SR bean " + bn);
         bean = (DescriptorBean)bn;
      } else if (i > 0 && beansp[i].startsWith("JMSSystemResource")) {
         Object bn = domainMBean.lookupJMSSystemResource(beansp[i + 1]);
         LOGGER.log(Level.FINE, "JMS SR bean " + bn);
         bean = (DescriptorBean)bn;
      }

      return this.exportDescriptor(bean, descpContent, userKeyFile, isSystemResorApp);
   }

   public String exportDescriptor(DescriptorBean bean, String descpContent, String userKeyFile, boolean isSystemResorApp) throws Exception {
      ByteArrayOutputStream boAs = new ByteArrayOutputStream();
      String content = null;

      try {
         String userKey = userKeyFile != null && userKeyFile.length() > 0 ? new String(Files.readAllBytes(Paths.get(userKeyFile))) : null;
         BasicDescriptorManager dm = new BasicDescriptorManager(DescriptorHelper.class.getClassLoader(), true, this.getSecurityService(userKey));
         dm.addInitialNamespace("sec", "http://xmlns.oracle.com/weblogic/security");
         dm.addInitialNamespace("wls", "http://xmlns.oracle.com/weblogic/security/wls");
         XMLInputFactory factory = XXEUtils.createXMLInputFactoryInstance();
         XMLStreamReader xmlReader = null;

         try {
            xmlReader = factory.createXMLStreamReader(new ByteArrayInputStream(descpContent.getBytes()), (String)null);
         } catch (Exception var18) {
            xmlReader = factory.createXMLStreamReader(new ByteArrayInputStream(descpContent.getBytes()), "UTF-8");
         }

         Descriptor descriptor = dm.createDescriptor(xmlReader, false);
         DescriptorBean rootBean = descriptor.getRootBean();
         if (bean instanceof JMSSystemResourceMBean) {
            this.validateSubDeployments(rootBean, (JMSSystemResourceMBean)bean);
            this.filterOutUnsupportedBeans(rootBean, ((JMSSystemResourceMBean)bean).getName());
         }

         Set scannedObjs = new HashSet();
         scannedObjs.clear();
         this.walkBeanTree(bean, rootBean, isSystemResorApp, scannedObjs);
         dm.writeDescriptorAsXML(descriptor, boAs);
         content = new String(boAs.toByteArray());
      } catch (Exception var19) {
         LOGGER.log(Level.WARNING, "exportDescriptor exception " + var19);
         var19.printStackTrace();
         throw var19;
      } finally {
         boAs.close();
      }

      return content;
   }

   private DescriptorSecurityServiceImpl getSecurityService(String userkey) throws IOException {
      if (descriptorSecSvc == null) {
         descriptorSecSvc = new DescriptorSecurityServiceImpl();
         descriptorSecSvc.createEncryptionService(userkey);
      }

      return descriptorSecSvc;
   }

   private boolean isEncrypted(PropertyDescriptor pd) {
      Boolean obj = (Boolean)pd.getValue("encrypted");
      return obj != null ? obj : false;
   }

   private void walkBeanTree(Object bean, Object toSetObj, boolean isSystemResorApp, Set scannedObjs) throws SecurityException, Exception {
      LOGGER.log(Level.FINE, "walkBeanTree begin: " + bean + " , toSet: " + toSetObj);
      if (!scannedObjs.contains(bean)) {
         scannedObjs.add(bean);
         PropertyDescriptor[] var5 = beanInfoAccess.getBeanInfoForInstance(bean, false, (String)null).getPropertyDescriptors();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor propDesc = var5[var7];
            String pps = propDesc.toString();
            LOGGER.log(Level.FINE, "  propDesc: " + pps);
            if (propDesc.getName() != "Parent" && propDesc.getName() != "Name" && propDesc.getName() != "ObjectName" && pps.indexOf("transient=true") <= 0 && propDesc.getName() != "Targets") {
               Method getMethod = propDesc.getReadMethod();
               if (getMethod != null) {
                  Object child = getMethod.invoke(bean);
                  if (child != null) {
                     LOGGER.log(Level.FINE, "walkBeanTree prop child " + child);
                     if (child.getClass().isArray()) {
                        for(int i = 0; i < Array.getLength(child); ++i) {
                           Object childBean = Array.get(child, i);
                           if (!(childBean instanceof DescriptorBean)) {
                              if (this.isEncrypted(propDesc)) {
                                 this.setEncryptedAttributes(propDesc, child, bean, toSetObj);
                              }
                              break;
                           }

                           if (childBean instanceof SystemResourceMBean && !isSystemResorApp) {
                              break;
                           }

                           this.walkBeanTree(childBean, toSetObj, isSystemResorApp, scannedObjs);
                        }
                     } else if (child instanceof DescriptorBean) {
                        if (child instanceof SystemResourceMBean && !isSystemResorApp) {
                           LOGGER.log(Level.FINE, "walkBeanTree continue ... " + child);
                        } else {
                           this.walkBeanTree(child, toSetObj, isSystemResorApp, scannedObjs);
                        }
                     } else if (this.isEncrypted(propDesc)) {
                        this.setEncryptedAttributes(propDesc, child, bean, toSetObj);
                     } else if (configDirPath != null && "DescriptorFileName".equals(propDesc.getName())) {
                        String dfileName = configDirPath + "/" + child;
                        Object childBean = DescriptorManagerHelper.loadDescriptor(dfileName, true, false, (List)null).getRootBean();
                        LOGGER.log(Level.FINE, "walkBeanTree to DescriptorFileName=" + dfileName + " bean=" + childBean);
                        this.walkBeanTree(childBean, toSetObj, isSystemResorApp, scannedObjs);
                     } else {
                        LOGGER.log(Level.FINE, "walkBeanTree continue .... " + child);
                     }
                  }
               }
            }
         }
      }

   }

   private void setEncryptedAttributes(PropertyDescriptor propDesc, Object child, Object bean, Object toSetObj) throws Exception, SecurityException {
      LOGGER.log(Level.FINE, "setEncryptedAttributes " + propDesc + " , child: " + child + " , bean: " + bean + " , toSet: " + toSetObj);
      String attrName = propDesc.getName();
      Object theChild = child;
      Method getPE;
      if (attrName.endsWith("Encrypted")) {
         attrName = StringUtils.replaceGlobal(attrName, "Encrypted", "");
         getPE = bean.getClass().getMethod("get" + attrName);
         Object[] p1 = null;
         theChild = getPE.invoke(bean, (Object[])p1);
      } else {
         attrName = attrName + "Encrypted";
         getPE = bean.getClass().getMethod("get" + attrName);
         if (getPE != null) {
            LOGGER.log(Level.FINE, "setEncryptedAttributes return due to encrypted value with the same name as propDesc.getName");
            return;
         }
      }

      this.setAttributeVal(theChild.toString().toCharArray(), propDesc, bean, toSetObj);
   }

   private void setAttributeVal(char[] passwordValue, PropertyDescriptor pDesc, Object bean, Object toSetObj) throws Exception {
      LOGGER.log(Level.FINE, "setAttributeVal " + pDesc + " , bean: " + bean + " , toSet: " + toSetObj);
      AbstractDescriptorBean toSetBean = null;

      try {
         Method m = AbstractDescriptorBean.class.getDeclaredMethod("findByQualifiedName", AbstractDescriptorBean.class);
         toSetBean = ((AbstractDescriptorBean)toSetObj).findByQualifiedName((AbstractDescriptorBean)bean);
      } catch (Exception var11) {
         toSetBean = this.findJDBCDriverParamsBean(toSetObj, bean);
         if (toSetBean == null) {
            toSetBean = this.findJMSForeignServerBean(toSetObj, bean);
         }

         if (toSetBean == null) {
            toSetBean = this.findSAFLoginContextBean(toSetObj, bean);
         }
      }

      if (toSetBean != null) {
         String attrName = pDesc.getName();
         String methodName = null;
         Method writeMethod = null;
         if (attrName.endsWith("Encrypted")) {
            attrName = StringUtils.replaceGlobal(attrName, "Encrypted", "");
            Class[] clzz = new Class[]{String.class};
            Class beanClass = bean.getClass();
            methodName = beanClass.getMethod("set" + attrName, clzz).getName();
            writeMethod = toSetBean.getClass().getMethod(methodName, clzz);
            writeMethod.invoke(toSetBean, new String(passwordValue));
         } else {
            methodName = pDesc.getWriteMethod().getName();
            writeMethod = toSetBean.getClass().getMethod(methodName, pDesc.getWriteMethod().getParameterTypes());
            writeMethod.invoke(toSetBean, (new String(passwordValue)).getBytes());
         }

      } else {
         if (toSetObj.getClass().getName().contains("JMSBean") && bean.getClass().getName().contains("JDBC")) {
            LOGGER.log(Level.FINE, "No need to setAttributeVal " + pDesc + " for bean: " + bean + " on toSet: " + toSetObj);
         } else {
            LOGGER.log(Level.WARNING, "Could not setAttributeVal " + pDesc + " for bean: " + bean + " on toSet: " + toSetObj);
         }

      }
   }

   public String getStringFromInputStream(InputStream ins) throws IOException {
      if (ins != null) {
         Writer writer = new StringWriter();
         char[] buffer = new char[1024];

         try {
            Reader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));

            int n;
            while((n = reader.read(buffer)) != -1) {
               writer.write(buffer, 0, n);
            }
         } finally {
            ins.close();
         }

         return writer.toString();
      } else {
         return "";
      }
   }

   private JSONObject getJSONObjectFromAttributesJson(String elementXmlType, String elementName, Object jsonObject) throws JSONException {
      JSONObject returnObj = null;
      if (jsonObject instanceof JSONObject) {
         JSONObject jo = (JSONObject)jsonObject;
         Iterator keys = jo.keys();

         while(true) {
            while(true) {
               while(keys.hasNext()) {
                  String key = (String)keys.next();
                  if (key.compareTo(elementXmlType) == 0) {
                     Object elementObj = jo.opt(key);
                     if (elementObj instanceof JSONObject) {
                        String elementValue = ((JSONObject)elementObj).optString("name");
                        if (elementValue.compareTo(elementName) == 0) {
                           returnObj = (JSONObject)elementObj;
                           return returnObj;
                        }
                     } else if (elementObj instanceof JSONArray) {
                        for(int i = 0; i < ((JSONArray)elementObj).length(); ++i) {
                           JSONObject arrayElementObj = ((JSONArray)elementObj).optJSONObject(i);
                           if (elementName.compareTo(arrayElementObj.optString("name")) == 0) {
                              returnObj = arrayElementObj;
                              break;
                           }
                        }
                     }
                  } else {
                     returnObj = this.getJSONObjectFromAttributesJson(elementXmlType, elementName, jo.get(key));
                  }
               }

               return returnObj;
            }
         }
      } else if (jsonObject instanceof JSONArray) {
         for(int i = 0; i < ((JSONArray)jsonObject).length(); ++i) {
            returnObj = this.getJSONObjectFromAttributesJson(elementXmlType, elementName, ((JSONArray)jsonObject).opt(i));
         }
      }

      return returnObj;
   }

   private void setAppAttributesFromJson(Object appJsonObject, AppDeploymentMBean app) throws JSONException, ManagementException {
      int i;
      if (appJsonObject instanceof JSONObject) {
         JSONObject appObj = (JSONObject)appJsonObject;
         if (appObj.get("name").toString().compareTo(app.getName()) != 0) {
            return;
         }

         i = ((AbstractDescriptorBean)app)._getPropertyIndex("SourcePath");
         String srcPathXmlName = ((AbstractDescriptorBean)app)._getSchemaHelper2().getElementName(i);
         Object jsonSrcPath = appObj.opt(srcPathXmlName);
         if (jsonSrcPath != null) {
            app.setSourcePath(jsonSrcPath.toString());
         }

         i = ((AbstractDescriptorBean)app)._getPropertyIndex("StagingMode");
         String stagingModeXmlName = ((AbstractDescriptorBean)app)._getSchemaHelper2().getElementName(i);
         Object jsonStagingMode = appObj.opt(stagingModeXmlName);
         if (jsonStagingMode != null && jsonStagingMode.getClass().isAssignableFrom(String.class)) {
            app.setStagingMode(jsonStagingMode.toString());
         }
      } else if (appJsonObject instanceof JSONArray) {
         JSONArray appsArray = (JSONArray)appJsonObject;

         for(i = 0; i < appsArray.length(); ++i) {
            this.setAppAttributesFromJson(appsArray.get(i), app);
         }
      }

   }

   private AbstractDescriptorBean findJDBCDriverParamsBean(Object base, Object bean) {
      try {
         if (JDBCDataSourceBeanClass.isInstance(base) && JDBCDriverParamsBeanClass.isInstance(bean)) {
            Object jdp = JDBCDataSourceBeanClass.getDeclaredMethod("getJDBCDriverParams").invoke(base);
            return (AbstractDescriptorBean)jdp;
         }
      } catch (Exception var4) {
         LOGGER.log(Level.FINE, "findJDBCDriverParamsBean exception " + var4);
      }

      LOGGER.log(Level.FINE, "findJDBCDriverParamsBean return null for " + base);
      return null;
   }

   private AbstractDescriptorBean findJMSForeignServerBean(Object base, Object bean) {
      try {
         if (JMSBeanClass.isInstance(base) && ForeignServerBeanClass.isInstance(bean)) {
            Object name = NamedEntityBeanClass.getDeclaredMethod("getName").invoke(bean);
            Object fsb = JMSBeanClass.getDeclaredMethod("lookupForeignServer", String.class).invoke(base, name);
            return (AbstractDescriptorBean)fsb;
         }
      } catch (Exception var5) {
         LOGGER.log(Level.FINE, "findJMSForeignServerBean exception " + var5);
      }

      LOGGER.log(Level.FINE, "findJMSForeignServerBean return null for " + base + " , " + bean);
      return null;
   }

   private AbstractDescriptorBean findSAFLoginContextBean(Object base, Object bean) {
      try {
         if (JMSBeanClass.isInstance(base) && SAFLoginContextClass.isInstance(bean)) {
            DescriptorBean parent = ((AbstractDescriptorBean)bean).getParentBean();
            Object safRemoteCtxName = parent.getClass().getMethod("getName").invoke(parent);
            Object safRemoteContext = JMSBeanClass.getDeclaredMethod("lookupSAFRemoteContext", String.class).invoke(base, safRemoteCtxName);
            Object aSAFLoginCtx = SAFRemoteContextClass.getDeclaredMethod("getSAFLoginContext").invoke(safRemoteContext);
            return (AbstractDescriptorBean)aSAFLoginCtx;
         }
      } catch (Exception var7) {
         LOGGER.log(Level.FINE, "findSAFLoginContextBean exception " + var7);
      }

      LOGGER.log(Level.FINE, "findSAFLoginContextBean return null for " + base + " , " + bean);
      return null;
   }

   public File formatJson(File jsonFile, String expArchPath, String domainName, String migrateRootDir) {
      File outputFile = new File(migrateRootDir + "/" + domainName + "-attributes.json");

      try {
         BufferedWriter writer = Files.newBufferedWriter(outputFile.toPath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
         Throwable var7 = null;

         try {
            String unformattedJsonContent = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject jsonObject = new JSONObject(new JSONTokener(unformattedJsonContent));
            writer.write(jsonObject.toString(4));
         } catch (Throwable var18) {
            var7 = var18;
            throw var18;
         } finally {
            if (writer != null) {
               if (var7 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var17) {
                     var7.addSuppressed(var17);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (Exception var20) {
         LOGGER.log(Level.FINE, "exception in JSON pretty formatting ", var20);
         var20.printStackTrace();
      }

      return outputFile;
   }

   private void filterOutUnsupportedBeans(Object bean, String jmsModuleName) {
      try {
         Object wdQueues = JMSBeanClass.getDeclaredMethod("getDistributedQueues").invoke(bean);
         Object wdTopics = JMSBeanClass.getDeclaredMethod("getDistributedTopics").invoke(bean);
         ArrayList wDDList = new ArrayList();
         Object[] var6 = (Object[])((Object[])wdQueues);
         int var7 = var6.length;

         int var8;
         Object safIDs;
         Object name;
         for(var8 = 0; var8 < var7; ++var8) {
            safIDs = var6[var8];
            name = NamedEntityBeanClass.getDeclaredMethod("getName").invoke(safIDs);
            wDDList.add(name.toString());
            LOGGER.log(Level.WARNING, String.format("Weighted distributed destinations are deprecated and is not supported when importing a JMS module containing them to a partition. The bean '%s' of type '%s' in JMS Module '%s' will not be exported.", name, "Weighted Distributed Queue", jmsModuleName));
            JMSBeanClass.getDeclaredMethod("destroyDistributedQueue", WeightedDistributedQueueClass).invoke(bean, safIDs);
         }

         var6 = (Object[])((Object[])wdTopics);
         var7 = var6.length;

         for(var8 = 0; var8 < var7; ++var8) {
            safIDs = var6[var8];
            name = NamedEntityBeanClass.getDeclaredMethod("getName").invoke(safIDs);
            wDDList.add(name.toString());
            LOGGER.log(Level.WARNING, String.format("Weighted distributed destinations are deprecated and is not supported when importing a JMS module containing them to a partition. The bean '%s' of type '%s' in JMS Module '%s' will not be exported.", name, "Weighted Distributed Topic", jmsModuleName));
            JMSBeanClass.getDeclaredMethod("destroyDistributedTopic", WeightedDistributedTopicClass).invoke(bean, safIDs);
         }

         if (!wDDList.isEmpty()) {
            this.skippedResources.put("WDD", wDDList);
         }

         Object udqueues = JMSBeanClass.getDeclaredMethod("getUniformDistributedQueues").invoke(bean);
         Object udtopics = JMSBeanClass.getDeclaredMethod("getUniformDistributedTopics").invoke(bean);
         ArrayList uDDList = new ArrayList();
         this.handleDefaultTargetedDestinations(udqueues, uDDList, jmsModuleName);
         this.handleDefaultTargetedDestinations(udtopics, uDDList, jmsModuleName);
         if (!uDDList.isEmpty()) {
            this.skippedResources.put("UDD", uDDList);
         }

         safIDs = JMSBeanClass.getDeclaredMethod("getSAFImportedDestinations").invoke(bean);
         ArrayList safIDList = new ArrayList();
         this.handleDefaultTargetedDestinations(safIDs, safIDList, jmsModuleName);
         if (!safIDList.isEmpty()) {
            this.skippedResources.put("SAFID", safIDList);
         }
      } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var11) {
         var11.printStackTrace();
      }

   }

   private void validateSubDeployments(Object jmsBean, JMSSystemResourceMBean jmsSystemResourceBean) {
      WebLogicMBean parent = jmsSystemResourceBean.getParent();
      if (!(parent instanceof DomainMBean)) {
         throw new AssertionError("Parent of a JMSSystemResource must be an instance of  Domain but was '" + parent.getClass() + "'.");
      } else {
         JMSServerMBean[] jmsServers = ((DomainMBean)parent).getJMSServers();
         JMSSystemResourceMBean[] jmsSystemResources = ((DomainMBean)parent).getJMSSystemResources();

         try {
            if (!JMSBeanClass.isInstance(jmsBean)) {
               throw new IllegalArgumentException("Expecting a JMSBean but got a bean of type '" + jmsBean.getClass());
            }

            Set jmsServersHostingSingletons = new HashSet();
            Set jmsServersHostingDistributed = new HashSet();
            Set jmsServersHostingForeignSrvrs = new HashSet();
            JMSSystemResourceMBean[] var9 = jmsSystemResources;
            int var10 = jmsSystemResources.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               JMSSystemResourceMBean aJmsSystemResource = var9[var11];
               String jmsModuleName = aJmsSystemResource.getName();
               HashMap subDeployMap = new HashMap();
               SubDeploymentMBean[] var15 = aJmsSystemResource.getSubDeployments();
               int var16 = var15.length;

               for(int var17 = 0; var17 < var16; ++var17) {
                  SubDeploymentMBean aSubDeployment = var15[var17];
                  subDeployMap.put(aSubDeployment.getName(), aSubDeployment);
               }

               Object cfactories = JMSBeanClass.getDeclaredMethod("getConnectionFactories").invoke(jmsBean);
               this.validateSubDeploymentsTargetedToAClusterMember(cfactories, subDeployMap, jmsModuleName);
               this.validateSubDeploymentTargets(cfactories, subDeployMap, jmsModuleName);
               Set singletonSubDeploys = new HashSet();
               Object queues = JMSBeanClass.getDeclaredMethod("getQueues").invoke(jmsBean);
               Object topics = JMSBeanClass.getDeclaredMethod("getTopics").invoke(jmsBean);
               singletonSubDeploys.addAll(this.getSubDeploymentNames(queues));
               singletonSubDeploys.addAll(this.getSubDeploymentNames(topics));
               jmsServersHostingSingletons.addAll(this.getSubDeploymentJMSServers(jmsServers, subDeployMap, singletonSubDeploys));
               this.validateSubDeploymentsTargetedToAClusterMember(queues, subDeployMap, jmsModuleName);
               this.validateSubDeploymentsTargetedToAClusterMember(topics, subDeployMap, jmsModuleName);
               Set distributedSubDeploys = new HashSet();
               Object udqueues = JMSBeanClass.getDeclaredMethod("getUniformDistributedQueues").invoke(jmsBean);
               Object udtopics = JMSBeanClass.getDeclaredMethod("getUniformDistributedTopics").invoke(jmsBean);
               distributedSubDeploys.addAll(this.getSubDeploymentNames(udqueues));
               distributedSubDeploys.addAll(this.getSubDeploymentNames(udtopics));
               jmsServersHostingDistributed.addAll(this.getSubDeploymentJMSServers(jmsServers, subDeployMap, distributedSubDeploys));
               this.validateSubDeploymentTargets(udqueues, subDeployMap, jmsModuleName);
               this.validateSubDeploymentTargets(udtopics, subDeployMap, jmsModuleName);
               this.validateSubDeploymentsTargetedToAClusterMember(udqueues, subDeployMap, jmsModuleName);
               this.validateSubDeploymentsTargetedToAClusterMember(udtopics, subDeployMap, jmsModuleName);
               this.validateForwardingPolicy((Object[])((Object[])udtopics), jmsModuleName);
               Object foreignServers = JMSBeanClass.getDeclaredMethod("getForeignServers").invoke(jmsBean);
               Set foreignSrvrSubDeploys = this.getSubDeploymentNames(foreignServers);
               jmsServersHostingForeignSrvrs.addAll(this.getSubDeploymentJMSServers(jmsServers, subDeployMap, foreignSrvrSubDeploys));
               this.validateSubDeploymentTargets(foreignServers, subDeployMap, jmsModuleName);
               this.validateSubDeploymentsTargetedToAClusterMember(foreignServers, subDeployMap, jmsModuleName);
               Object safIDs = JMSBeanClass.getDeclaredMethod("getSAFImportedDestinations").invoke(jmsBean);
               this.validateSubDeploymentTargets(safIDs, subDeployMap, jmsModuleName);
               this.validateSubDeploymentsTargetedToAClusterMember(safIDs, subDeployMap, jmsModuleName);
               Set conflictingSubdeployNames = this.checkConflictingSubDeploymentNames(singletonSubDeploys, distributedSubDeploys);
               if (!conflictingSubdeployNames.isEmpty()) {
                  throw new IllegalArgumentException(String.format("One or more Subdeployment(s) '%s' in JMS module '%s' is being referred by conflicting JMS destination types - %s and %s, which is not supported for export. A subdeployment cannot be referred from both %s and %s. Correct the configuration and try exporting the configuration again.", conflictingSubdeployNames, jmsModuleName, "standalone destination", "distributed destination", "standalone destination", "distributed destination"));
               }

               conflictingSubdeployNames = this.checkConflictingSubDeploymentNames(singletonSubDeploys, foreignSrvrSubDeploys);
               if (!conflictingSubdeployNames.isEmpty()) {
                  throw new IllegalArgumentException(String.format("One or more Subdeployment(s) '%s' in JMS module '%s' is being referred by conflicting JMS destination types - %s and %s, which is not supported for export. A subdeployment cannot be referred from both %s and %s. Correct the configuration and try exporting the configuration again.", conflictingSubdeployNames, jmsModuleName, "standalone destination", "foreign server", "standalone destination", "foreign server"));
               }
            }

            Set conflictingSubdeployTargets = this.checkConflictingSubDeploymentTargets(jmsServersHostingSingletons, jmsServersHostingDistributed);
            if (!conflictingSubdeployTargets.isEmpty()) {
               throw new IllegalArgumentException(String.format("One or more JMS Server(s) '%s' was found to host conflicting JMS destination types - %s and %s, which is not supported for export. A JMS server cannot host both %s and %s. Correct the configuration and try exporting the configuration again.", this.getJMSServerNames(conflictingSubdeployTargets), "standalone destination", "distributed destination", "standalone destination", "distributed destination"));
            }

            conflictingSubdeployTargets = this.checkConflictingSubDeploymentTargets(jmsServersHostingSingletons, jmsServersHostingForeignSrvrs);
            if (!conflictingSubdeployTargets.isEmpty()) {
               throw new IllegalArgumentException(String.format("One or more JMS Server(s) '%s' was found to host conflicting JMS destination types - %s and %s, which is not supported for export. A JMS server cannot host both %s and %s. Correct the configuration and try exporting the configuration again.", this.getJMSServerNames(conflictingSubdeployTargets), "standalone destination", "foreign server", "standalone destination", "foreign server"));
            }
         } catch (IllegalArgumentException var26) {
            var26.printStackTrace();
            throw var26;
         } catch (Exception var27) {
            var27.printStackTrace();
         }

      }
   }

   private String getJMSServerNames(Set jmsServerMBeans) {
      if (jmsServerMBeans != null && !jmsServerMBeans.isEmpty()) {
         List jmsServerNames = new ArrayList();
         Iterator var3 = jmsServerMBeans.iterator();

         while(var3.hasNext()) {
            JMSServerMBean aJMSServer = (JMSServerMBean)var3.next();
            jmsServerNames.add(aJMSServer.getName());
         }

         return jmsServerNames.toString();
      } else {
         return "[]";
      }
   }

   private Set getSubDeploymentJMSServers(JMSServerMBean[] avlblJMSServers, HashMap subdeployMap, Set subdeployNames) {
      Set jmsServers = new HashSet();
      Iterator var5 = subdeployNames.iterator();

      while(var5.hasNext()) {
         String aSubdeploymentName = (String)var5.next();
         SubDeploymentMBean subdeployMBean = (SubDeploymentMBean)subdeployMap.get(aSubdeploymentName);
         if (subdeployMBean != null) {
            jmsServers.addAll(this.filterJMSServersUsedByASubDeployment(avlblJMSServers, subdeployMBean));
         }
      }

      return jmsServers;
   }

   private Set filterJMSServersUsedByASubDeployment(JMSServerMBean[] avlblJMSServers, SubDeploymentMBean subdeployMBean) {
      Set jmsServersPerSubdeploy = new HashSet();
      TargetMBean[] var4 = subdeployMBean.getTargets();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetMBean aTargetMBean = var4[var6];
         jmsServersPerSubdeploy.addAll(this.filterJMSServersForTarget(avlblJMSServers, aTargetMBean));
      }

      return jmsServersPerSubdeploy;
   }

   private Set filterJMSServersForTarget(JMSServerMBean[] avlblJMSServers, TargetMBean targetMBean) {
      Set jmsServersPerTarget = new HashSet();
      if (targetMBean instanceof JMSServerMBean) {
         jmsServersPerTarget.add((JMSServerMBean)targetMBean);
         return jmsServersPerTarget;
      } else {
         JMSServerMBean[] var4 = avlblJMSServers;
         int var5 = avlblJMSServers.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            JMSServerMBean jmsServer = var4[var6];
            List jmsServerTargets = Arrays.asList(jmsServer.getTargets());
            if (jmsServerTargets.contains(targetMBean)) {
               jmsServersPerTarget.add(jmsServer);
            }
         }

         List subTargets = new ArrayList();
         if (targetMBean instanceof ClusterMBean) {
            subTargets.addAll(Arrays.asList(((ClusterMBean)targetMBean).getServers()));
         } else {
            if (!(targetMBean instanceof MigratableTargetMBean)) {
               return jmsServersPerTarget;
            }

            subTargets.addAll(Arrays.asList(((MigratableTargetMBean)targetMBean).getAllCandidateServers()));
            subTargets.add(((MigratableTargetMBean)targetMBean).getCluster());
         }

         Iterator var13 = subTargets.iterator();

         while(var13.hasNext()) {
            TargetMBean aSubTarget = (TargetMBean)var13.next();
            JMSServerMBean[] var15 = avlblJMSServers;
            int var16 = avlblJMSServers.length;

            for(int var9 = 0; var9 < var16; ++var9) {
               JMSServerMBean jmsServer = var15[var9];
               List jmsServerTargets = Arrays.asList(jmsServer.getTargets());
               if (jmsServerTargets.contains(aSubTarget)) {
                  jmsServersPerTarget.add(jmsServer);
               }
            }
         }

         return jmsServersPerTarget;
      }
   }

   private Set getSubDeploymentNames(Object targetables) throws Exception {
      Set subdeployNames = new HashSet();
      Object[] var3 = (Object[])((Object[])targetables);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object targetable = var3[var5];
         Object subdeployName = TargetableClass.getDeclaredMethod("getSubDeploymentName").invoke(targetable);
         if (subdeployName != null) {
            subdeployNames.add((String)subdeployName);
         }
      }

      return subdeployNames;
   }

   private Set checkConflictingSubDeploymentTargets(Set singletonTargets, Set distributedTargets) {
      Set conflictJmsServers = new HashSet(singletonTargets);
      conflictJmsServers.retainAll(distributedTargets);
      return conflictJmsServers;
   }

   private Set checkConflictingSubDeploymentNames(Set singletonSubdeploys, Set distributedSubdeploys) {
      Set conflictSubdeployNames = new HashSet(singletonSubdeploys);
      conflictSubdeployNames.retainAll(distributedSubdeploys);
      return conflictSubdeployNames;
   }

   private void handleDefaultTargetedDestinations(Object jmsDestinations, List skippedResources, String jmsModuleName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Object[] var4 = (Object[])((Object[])jmsDestinations);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object aJMSDestination = var4[var6];
         Object defaultTargetingFlag = TargetableClass.getDeclaredMethod("isDefaultTargetingEnabled").invoke(aJMSDestination);
         if (Boolean.TRUE.equals(defaultTargetingFlag)) {
            Object name = NamedEntityBeanClass.getDeclaredMethod("getName").invoke(aJMSDestination);
            String destType = aJMSDestination.getClass().getSimpleName().replace("BeanImpl", "");
            LOGGER.log(Level.WARNING, String.format("The JMS Destination '%s' of type '%s' in JMS Module '%s' is default targeted. This configuration is not supported when importing this domain to a partition. Destinations should always be targeted using a subdeployment before attempting an export, and the subdeployment should in turn reference a JMS Server or a SAF Agent.", name, destType, jmsModuleName));
            TargetableClass.getDeclaredMethod("setDefaultTargetingEnabled", Boolean.TYPE).invoke(aJMSDestination, false);
            skippedResources.add(name.toString());
         }
      }

   }

   private void validateSubDeploymentTargets(Object destinations, Map subDeployMap, String jmsModuleName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Object[] var4 = (Object[])((Object[])destinations);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object aJMSDestination = var4[var6];
         Object subdeployName = TargetableClass.getDeclaredMethod("getSubDeploymentName").invoke(aJMSDestination);
         Object name = NamedEntityBeanClass.getDeclaredMethod("getName").invoke(aJMSDestination);
         String destType = aJMSDestination.getClass().getSimpleName().replace("BeanImpl", "");
         if (subdeployName != null) {
            SubDeploymentMBean subdeployment = (SubDeploymentMBean)subDeployMap.get(subdeployName);
            if (subdeployment != null) {
               TargetMBean[] subdeploymentTgts = subdeployment.getTargets();
               if (subdeploymentTgts == null || subdeploymentTgts.length == 0) {
                  LOGGER.log(Level.WARNING, String.format("The JMS Destination '%s' with subdeployment '%s' in JMS Module '%s' is not targeted, so it will be left untargeted when imported to a partition.", name, subdeployment.getName(), jmsModuleName));
               }

               TargetMBean[] var13 = subdeploymentTgts;
               int var14 = subdeploymentTgts.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  TargetMBean target = var13[var15];
                  if (target instanceof ClusterMBean || target instanceof MigratableTargetMBean || target instanceof ServerMBean) {
                     LOGGER.log(Level.WARNING, String.format("The JMS Destination '%s' of type '%s' with subdeployment '%s' in JMS Module '%s' is targeted to '%s' of type '%s' . This configuration is not supported when importing this domain to a partition. Subdeployments should always be targeted to a JMS Server or a SAF Agent before export, otherwise they will be left untargeted when imported to a partition and any resources that reference the subdeployment will not deploy.", name, destType, subdeployment.getName(), jmsModuleName, target.getName(), target.getType()));
                  }
               }
            }
         }
      }

   }

   private void validateSubDeploymentsTargetedToAClusterMember(Object destinations, Map subDeployMap, String jmsModuleName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Object[] var4 = (Object[])((Object[])destinations);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object aJMSDestination = var4[var6];
         Object subdeployName = TargetableClass.getDeclaredMethod("getSubDeploymentName").invoke(aJMSDestination);
         if (subdeployName != null) {
            Object name = NamedEntityBeanClass.getDeclaredMethod("getName").invoke(aJMSDestination);
            String destType = aJMSDestination.getClass().getSimpleName().replace("BeanImpl", "");
            SubDeploymentMBean subdeployment = (SubDeploymentMBean)subDeployMap.get(subdeployName);
            if (subdeployment != null) {
               TargetMBean[] var12 = subdeployment.getTargets();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  TargetMBean target = var12[var14];
                  if (target instanceof JMSServerMBean) {
                     TargetMBean[] var16 = ((JMSServerMBean)target).getTargets();
                     int var17 = var16.length;

                     for(int var18 = 0; var18 < var17; ++var18) {
                        TargetMBean aJMSServerTarget = var16[var18];
                        ClusterMBean cluster = null;
                        if (aJMSServerTarget instanceof ServerMBean) {
                           cluster = ((ServerMBean)aJMSServerTarget).getCluster();
                        } else if (aJMSServerTarget instanceof MigratableTargetMBean) {
                           cluster = ((MigratableTargetMBean)aJMSServerTarget).getCluster();
                        }

                        if (cluster != null) {
                           String errorMsg = String.format("The JMS destination '%s' of type '%s' with subdeployment '%s' in JMS Module '%s' is targeted to '%s' of type '%s' and '%s' is associated with a specific server in cluster '%s' via a target of '%s' of type '%s'. Partition scoped destinations do not support targeting a particular Server or Migratable, so, after import, this subdeployment will be targeted to a JMS Server/SAF Agent with an effective target of a Cluster, the destination runtime will therefore be hosted on all the members of the cluster.", name, destType, subdeployment.getName(), jmsModuleName, target.getName(), target.getType(), target.getName(), cluster.getName(), aJMSServerTarget.getName(), aJMSServerTarget.getType());
                           if (destType.equalsIgnoreCase("Queue") || destType.equalsIgnoreCase("Topic")) {
                              errorMsg = String.format("The standalone JMS destination '%s' of type '%s' with subdeployment '%s' in JMS Module '%s' is targeted to '%s' of type '%s' and '%s' is associated with a specific server in cluster '%s' via a target of '%s' of type '%s'. Partition scoped standalone destinations do not support targeting a particular Server or Migratable, so, after import, this subdeployment will be targeted to a singleton JMS Server with an effective target of a Cluster, the destination runtime will therefore be hosted on a single random server in the cluster, and the destination's host server may change after failures or restarts. If the original configuration absolutely requires that the destination have an affinity to a particular server in the cluster then the stand-alone destination should be replaced with a distributed destination: distributed destinations create a member on each server on the cluster, these members are each individually addressable as a Standalone destination, and each member automatically has an affinity to a particular server in its cluster.", name, destType, subdeployment.getName(), jmsModuleName, target.getName(), target.getType(), target.getName(), cluster.getName(), aJMSServerTarget.getName(), aJMSServerTarget.getType());
                           }

                           LOGGER.log(Level.WARNING, errorMsg);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private void validateForwardingPolicy(Object[] udtopics, String jmsModuleName) {
      Object[] var3 = udtopics;
      int var4 = udtopics.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object aUDTopic = var3[var5];
         if (UniformDistributedTopicClass.isInstance(aUDTopic)) {
            try {
               Object forwardingPolicy = UniformDistributedTopicClass.getDeclaredMethod("getForwardingPolicy").invoke(aUDTopic);
               Object name = NamedEntityBeanClass.getMethod("getName").invoke(aUDTopic);
               if (forwardingPolicy != null && "Replicated".equalsIgnoreCase(forwardingPolicy.toString())) {
                  LOGGER.log(Level.WARNING, String.format("The Uniform Distributed Topic '%s' in JMS Module '%s' is configured to use the forwarding policy 'Replicated'(the default). This configuration is not supported when importing this domain to a partition, so the forwarding policy will be changed to 'Partitioned'. Partitioned topics have very different semantics than replicated topics so additional application tuning changes and even application code changes may be required to get the desired behavior; for example, MDBs and SOA JMS Adapters will need to specify a message distribution policy of 'one copy per server' or 'one copy per application'. It is recommended to consult the documentation (pending) for details, to convert the original configuration to use partitioned topics, and to test changes prior to export.", name, jmsModuleName));
               }
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var9) {
               LOGGER.log(Level.FINE, var9.getMessage());
            }
         }
      }

   }

   public String getEncryptedAttribute(String mbeanType, String mbeanName, String propertyName, String userKeyFile) throws Exception {
      Object bean = null;
      if (mbeanType.startsWith("JMSBridgeDestination")) {
         bean = domainMBean.lookupJMSBridgeDestination(mbeanName);
      } else {
         Method lookupMethod = domainMBean.getClass().getMethod("lookup" + mbeanType, String.class);
         if (lookupMethod != null) {
            bean = lookupMethod.invoke(domainMBean, mbeanName);
         }
      }

      Object thePassword = "";
      String userKey = userKeyFile != null && userKeyFile.length() > 0 ? new String(Files.readAllBytes(Paths.get(userKeyFile))) : null;
      Method getPasswordMethod;
      if (propertyName.endsWith("Encrypted")) {
         propertyName = StringUtils.replaceGlobal(propertyName, "Encrypted", "");
         getPasswordMethod = bean.getClass().getMethod("get" + propertyName);
         if (getPasswordMethod == null) {
            throw new IllegalArgumentException(String.format("Read method not found for property '%s' for MBean '%s' of type '%s'", propertyName, mbeanName, mbeanType));
         }

         Object clearPassword = getPasswordMethod.invoke(bean);
         if (clearPassword != null) {
            thePassword = new String(this.getSecurityService(userKey).encrypt(clearPassword.toString()));
         }
      } else {
         propertyName = propertyName + "Encrypted";
         getPasswordMethod = bean.getClass().getMethod("get" + propertyName);
         if (getPasswordMethod == null) {
            throw new IllegalArgumentException(String.format("Read method not found for property '%s' for MBean '%s' of type '%s'", propertyName, mbeanName, mbeanType));
         }

         byte[] bytes = (byte[])((byte[])getPasswordMethod.invoke(bean));
         if (bytes != null && bytes.length > 0) {
            EncryptionService service = SerializedSystemIni.getEncryptionService(domainMBean.getRootDirectory());
            byte[] clearPassword = (new ClearOrEncryptedService(service)).decryptBytes(bytes);
            thePassword = new String(this.getSecurityService(userKey).encrypt(new String(clearPassword)));
         }
      }

      return thePassword.toString();
   }

   public class DescriptorSecurityServiceImpl implements SecurityService {
      private ClearOrEncryptedService encryptionService;
      String userKey;

      private DescriptorSecurityServiceImpl() {
         this.encryptionService = null;
         this.userKey = null;
         System.setProperty("weblogic.management.confirmKeyfileCreation", "true");
      }

      public boolean isEncrypted(byte[] bEncrypted) throws DescriptorException {
         return this.encryptionService != null ? this.encryptionService.isEncryptedBytes(bEncrypted) : false;
      }

      public byte[] encrypt(String sValue) throws DescriptorException {
         if (this.encryptionService != null) {
            return this.encryptionService.encrypt(sValue).getBytes();
         } else {
            return sValue != null ? sValue.getBytes() : null;
         }
      }

      public String decrypt(byte[] bEncrypted) throws DescriptorException {
         if (this.encryptionService != null) {
            return new String(this.encryptionService.decryptBytes(bEncrypted));
         } else {
            return bEncrypted != null ? new String(bEncrypted) : null;
         }
      }

      public void createEncryptionService(String userkey) throws IOException {
         this.userKey = userkey;

         try {
            Method getEnCS = UserConfigFileManager.class.getMethod("getEncryptedService", String.class, String.class);
            this.encryptionService = UserConfigFileManager.getEncryptedService(DescriptorHelper.Secret_Key_Filepath, userkey);
            if (DescriptorHelper.LOGGER.isLoggable(Level.FINE)) {
               DescriptorHelper.LOGGER.log(Level.FINE, "createEncryptionService getEncryptedService=" + this.encryptionService);
            }
         } catch (Exception var12) {
            if (DescriptorHelper.LOGGER.isLoggable(Level.FINE)) {
               DescriptorHelper.LOGGER.log(Level.FINE, "createEncryptionService getEncryptedService exception " + var12);
            }

            try {
               File keyFile = new File(DescriptorHelper.Secret_Key_Filepath);
               if (keyFile.exists()) {
                  keyFile.delete();
               }

               keyFile.createNewFile();
               FileOutputStream outFile = new FileOutputStream(keyFile);
               String myvalue = userkey != null ? userkey : "0xfe593a5c23b88c112b3c674e33ea4c7901e26a7c";
               JSafeEncryptionServiceFactory factory = new JSafeEncryptionServiceFactory();
               byte[] salt = Salt.getRandomBytes(4);
               byte[] encryptedKey = factory.createEncryptedSecretKey(salt, myvalue);
               byte[] encryptedAESKey = factory.createAESEncryptedSecretKey(salt, myvalue);
               outFile.write(salt.length);
               outFile.write(salt);
               outFile.write(2);
               outFile.write(encryptedKey.length);
               outFile.write(encryptedKey);
               outFile.write(encryptedAESKey.length);
               outFile.write(encryptedAESKey);
               outFile.flush();
               outFile.close();
               EncryptionService es = factory.getEncryptionService(salt, myvalue, encryptedKey, encryptedAESKey);
               this.encryptionService = new ClearOrEncryptedService(es);
               if (DescriptorHelper.LOGGER.isLoggable(Level.FINE)) {
                  DescriptorHelper.LOGGER.log(Level.FINE, "createEncryptionService ClearOrEncryptedService=" + this.encryptionService);
               }
            } catch (Exception var11) {
               if (DescriptorHelper.LOGGER.isLoggable(Level.INFO)) {
                  DescriptorHelper.LOGGER.log(Level.INFO, "createEncryptionService ClearOrEncryptedService exception " + var11);
               }
            }
         }

      }

      // $FF: synthetic method
      DescriptorSecurityServiceImpl(Object x1) {
         this();
      }
   }
}
