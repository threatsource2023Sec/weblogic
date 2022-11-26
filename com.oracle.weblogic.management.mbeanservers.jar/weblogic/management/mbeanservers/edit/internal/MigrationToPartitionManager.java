package weblogic.management.mbeanservers.edit.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.management.InvalidAttributeValueException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFileManager;
import weblogic.descriptor.BasicDescriptorManager;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.SecurityService;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFRemoteContextBean;
import weblogic.j2ee.descriptor.wl.TargetableBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicDeploymentMBean;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.ForeignConnectionFactoryOverrideMBean;
import weblogic.management.configuration.ForeignDestinationOverrideMBean;
import weblogic.management.configuration.ForeignServerOverrideMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JDBCSystemResourceOverrideMBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.JMSSystemResourceOverrideMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.provider.internal.ConfigReader;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.management.provider.internal.ImportExportHelper;
import weblogic.management.provider.internal.ImportExportHelper.ImpExpSecurityServiceImpl;
import weblogic.management.security.RealmMBean;
import weblogic.management.tools.migration.DescriptorHelper;
import weblogic.utils.FileUtils;
import weblogic.utils.XXEUtils;
import weblogic.utils.io.StreamUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

@Service
public final class MigrationToPartitionManager {
   private static final String SecretKeyFileName = "archiveSecret";
   private static final String JMS_SERVER = "JMSServer";
   private static final String SAF_AGENT = "SAFAgent";
   private static final String MIGRATABLE_TARGET = "MigratableTarget";
   private static final String MIGRATION_BASIS_CONSENSUS = "consensus";
   private static final String MIGRATION_BASIS_DATABASE = "database";
   private static final String WL_CLUSTER = "Cluster";
   private static final String WL_SERVER = "Server";
   private static final String EXISTING_VALUE = "__EXISTING_VALUE__";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private static final Comparator targetComparator = new Comparator() {
      public int compare(TargetMBean o1, TargetMBean o2) {
         return o1 != null && o2 != null ? Collator.getInstance().compare(o1.getName(), o2.getName()) : 0;
      }
   };
   private static final DomainToPartitionLogger domainToPartitionLogger = new DomainToPartitionLogger();
   Map jmsServerTgtMap;
   Map safAgentTgtMap;
   Set jmsServersHostingUDDs;
   Set jmsServersHostingFSs;
   Set singletonJMSServers;

   public PartitionMBean migrateToPartition(String archiveFileName, String partitionName, Boolean createRGT, String keyFile, ConfigurationManagerMBean manager) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Migration of archive " + archiveFileName + " to partition " + partitionName + " begins ...");
      }

      DomainToPartitionLogger var10002;
      if (archiveFileName == null) {
         var10002 = domainToPartitionLogger;
         throw new RuntimeException(DomainToPartitionLogger.logErrorArchiveNullLoggable(archiveFileName).getMessage());
      } else if (partitionName == null) {
         var10002 = domainToPartitionLogger;
         throw new RuntimeException(DomainToPartitionLogger.logNullPartitionNameLoggable().getMessage());
      } else {
         ZipFile partitionArchive = new ZipFile(archiveFileName);
         String archiveFileNamePath = (new File(archiveFileName)).getParent();
         File secretKeyFile = null;
         String tmpDirForArchive = null;
         String uploadDirectoryName = null;
         String newPartitionDir = null;
         PartitionMBean partitionMBean = null;

         DomainToPartitionLogger var10000;
         try {
            DomainMBean toImportDomain = manager.startEdit(-1, -1);
            DomainMBean domain = (DomainMBean)((Descriptor)toImportDomain.getDescriptor().clone()).getRootBean();
            String currentConfigxml = DescriptorUtils.toString(toImportDomain);
            String backConfigFile = toImportDomain.getRootDirectory() + "/config/config-backup-mgt.xml";
            FileUtils.writeToFile(new ByteArrayInputStream(currentConfigxml.getBytes()), new File(backConfigFile));
            (new StringBuilder()).append(toImportDomain.getRootDirectory()).append("/partitions/").append(partitionName).toString();
            Enumeration zes = partitionArchive.entries();
            String newConfig = null;
            String jsonAttributesString = null;
            if (!zes.hasMoreElements()) {
               var10002 = domainToPartitionLogger;
               throw new RuntimeException(DomainToPartitionLogger.logEmptyDomainArchiveLoggable(archiveFileName).getMessage());
            }

            tmpDirForArchive = Files.createTempDirectory("migrate-" + partitionName).toString();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Temporary archive directory " + tmpDirForArchive);
            }

            label2815:
            while(true) {
               InputStream ins;
               while(true) {
                  ZipEntry ze;
                  if (!zes.hasMoreElements()) {
                     ze = null;
                     int vn1 = newConfig.indexOf("export-domain-version:") + 22;
                     int vn2 = newConfig.indexOf(":export-domain-version");
                     if (vn1 > 22 && vn2 > vn1) {
                        String arcvDomainVer = newConfig.substring(vn1, vn2);
                        if (!domain.getDomainVersion().equals(arcvDomainVer) && debugLogger.isDebugEnabled()) {
                           debugLogger.debug("MigrationToPartitionManager:migrateToPartition : The archive domain version " + arcvDomainVer);
                        }
                     }

                     String configChangeFile = newConfig.replace("${DOMAIN_NAME}", domain.getName());
                     configChangeFile = configChangeFile.replace("${DOMAIN_VERSION}", domain.getDomainVersion());
                     if (jsonAttributesString != null && !jsonAttributesString.isEmpty()) {
                        jsonAttributesString = jsonAttributesString.replace("${PARTITION_NAME}", partitionName);
                        jsonAttributesString = jsonAttributesString.replace("${DOMAIN_VALUE}", "__EXISTING_VALUE__");
                        String wlHome = System.getenv("WL_HOME");
                        wlHome = wlHome.replace("\\", "\\/");
                        if (wlHome == null) {
                           wlHome = BootStrap.getWebLogicHome();
                        }

                        jsonAttributesString = jsonAttributesString.replace("${WL_HOME}", wlHome);
                     }

                     InputStream is = new ByteArrayInputStream(configChangeFile.getBytes());
                     EditableDescriptorManager descriptoManager = (EditableDescriptorManager)DescriptorManagerHelper.getDescriptorManager(true);
                     ArrayList errs = new ArrayList();
                     Descriptor descriptorGraph = descriptoManager.createDescriptor(new ConfigReader(is), errs, false);
                     is.close();
                     MigrationReportHandler htmlImport = new MigrationReportHandler(toImportDomain.getRootDirectory(), partitionName);
                     DomainMBean domainMBeanFromDescriptor = (DomainMBean)descriptorGraph.getRootBean();
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Domain " + domain + ", descriptor " + domainMBeanFromDescriptor);
                     }

                     String sysResoureSubDir = "";
                     JSONObject readJsonRoot = new JSONObject(jsonAttributesString);
                     Object resourceGroupEntryInJSON = readJsonRoot.opt("resource-group");
                     if (resourceGroupEntryInJSON == null) {
                        var10002 = domainToPartitionLogger;
                        throw new RuntimeException(DomainToPartitionLogger.logIncompatibleDomainArchiveLoggable(archiveFileName).getMessage());
                     }

                     if (!(resourceGroupEntryInJSON instanceof JSONArray)) {
                        var10002 = domainToPartitionLogger;
                        throw new Exception(DomainToPartitionLogger.logElementNotAnArrayLoggable("resource-group").getMessage());
                     }

                     partitionMBean = domain.createPartition(partitionName);
                     Object jsonObject = readJsonRoot.opt("partition");
                     JSONObject partitionJsonObject = jsonObject instanceof JSONObject ? (JSONObject)jsonObject : null;
                     SecurityConfigurationMBean secConfMBean = domain.getSecurityConfiguration();
                     if (secConfMBean != null) {
                        RealmMBean realmMBean = secConfMBean.getDefaultRealm();
                        String realmName = null;
                        if (partitionJsonObject != null) {
                           realmName = partitionJsonObject.optString("realm");
                           if (realmName != null && !"__EXISTING_VALUE__".equals(realmName)) {
                              realmMBean = secConfMBean.lookupRealm(realmName);
                           }
                        }

                        if (realmMBean == null) {
                           var10002 = domainToPartitionLogger;
                           throw new DescriptorException(DomainToPartitionLogger.logNoRealmAvailableLoggable(realmName).getMessage());
                        }

                        partitionMBean.setRealm(realmMBean);
                     }

                     ServerMBean[] var123 = domainMBeanFromDescriptor.getServers();
                     int var125 = var123.length;

                     for(int var38 = 0; var38 < var125; ++var38) {
                        ServerMBean svrmb = var123[var38];
                        String svrName = svrmb.getName();
                        ServerMBean dsvr = domain.lookupServer(svrName);
                        if (dsvr == null) {
                        }
                     }

                     Map vtMap = new HashMap();
                     Map vtClusterMap = new HashMap();
                     jsonObject = readJsonRoot.opt("virtual-target");
                     JSONArray vtJsonArray = null;
                     if (jsonObject != null) {
                        if (!(jsonObject instanceof JSONArray)) {
                           var10002 = domainToPartitionLogger;
                           throw new RuntimeException(DomainToPartitionLogger.logElementNotAnArrayLoggable("virtual-target").getMessage());
                        }

                        vtJsonArray = (JSONArray)jsonObject;
                     }

                     String resourceGroupName;
                     String vtname;
                     int var156;
                     if (vtJsonArray != null) {
                        for(int i = 0; i < vtJsonArray.length(); ++i) {
                           JSONObject vtJsonObject = vtJsonArray.optJSONObject(i);
                           VirtualTargetMBean virtualTargetMBean = null;
                           resourceGroupName = vtJsonObject.optString("name");
                           String targetName = vtJsonObject.optString("target");
                           VirtualTargetMBean[] var44 = domain.getVirtualTargets();
                           int var45 = var44.length;

                           int var46;
                           for(var46 = 0; var46 < var45; ++var46) {
                              VirtualTargetMBean evtg = var44[var46];
                              if (resourceGroupName.equals(evtg.getName())) {
                                 virtualTargetMBean = evtg;
                                 break;
                              }
                           }

                           if (virtualTargetMBean != null) {
                              boolean wrongTarget = true;
                              TargetMBean[] var147 = virtualTargetMBean.getTargets();
                              var46 = var147.length;

                              for(var156 = 0; var156 < var46; ++var156) {
                                 TargetMBean etb = var147[var156];
                                 if (etb.getName().equals(targetName)) {
                                    wrongTarget = false;
                                    break;
                                 }
                              }

                              if (wrongTarget) {
                                 var10002 = domainToPartitionLogger;
                                 throw new RuntimeException(DomainToPartitionLogger.logVTNotForTargetLoggable(virtualTargetMBean.getName(), targetName).getMessage());
                              }
                           } else {
                              ServerMBean dsvr = domain.lookupServer(targetName);
                              if (dsvr != null && dsvr.getCluster() != null) {
                                 var10002 = domainToPartitionLogger;
                                 throw new RuntimeException(DomainToPartitionLogger.logVTTargetMemberOfClusterLoggable(resourceGroupName, dsvr.getName(), dsvr.getCluster().getName()).getMessage());
                              }

                              virtualTargetMBean = domain.createVirtualTarget(resourceGroupName);
                              vtname = vtJsonObject.optString("uri-prefix");
                              virtualTargetMBean.setUriPrefix(vtname != null ? vtname : "/" + partitionName);
                              String hostNamesJsonArray = vtJsonObject.optString("host-names").trim();
                              List hostNameList = new ArrayList();
                              if (!"".equals(hostNamesJsonArray)) {
                                 StringTokenizer hostNameListTokenizer = new StringTokenizer(hostNamesJsonArray, ",");

                                 while(hostNameListTokenizer != null && hostNameListTokenizer.hasMoreTokens()) {
                                    hostNameList.add(hostNameListTokenizer.nextToken().trim());
                                 }
                              }

                              if (dsvr != null) {
                                 String lstnAddr = dsvr.getListenAddress();
                                 if (lstnAddr == null || lstnAddr.trim().length() == 0) {
                                    lstnAddr = InetAddress.getLocalHost().getCanonicalHostName();
                                 }

                                 if (hostNameList.contains("__EXISTING_VALUE__")) {
                                    hostNameList.set(hostNameList.indexOf("__EXISTING_VALUE__"), lstnAddr);
                                 }

                                 if (hostNameList.isEmpty()) {
                                    hostNameList.add(lstnAddr);
                                 }

                                 virtualTargetMBean.setHostNames((String[])hostNameList.toArray(new String[hostNameList.size()]));
                                 virtualTargetMBean.addTarget(dsvr);
                                 htmlImport.addVirtualTarget_ToReport(virtualTargetMBean.getName(), dsvr.getName(), "Weblogic Server");
                              } else {
                                 ClusterMBean clusb = domain.lookupCluster(targetName);
                                 if (clusb == null) {
                                    var10002 = domainToPartitionLogger;
                                    throw new RuntimeException(DomainToPartitionLogger.logNonExistingVTTargetLoggable(targetName, resourceGroupName).getMessage());
                                 }

                                 String lstnAddr = InetAddress.getLocalHost().getCanonicalHostName();
                                 if (hostNameList.contains("__EXISTING_VALUE__")) {
                                    hostNameList.set(hostNameList.indexOf("__EXISTING_VALUE__"), lstnAddr);
                                 }

                                 if (hostNameList.isEmpty()) {
                                    hostNameList.add(lstnAddr);
                                 }

                                 virtualTargetMBean.setHostNames((String[])hostNameList.toArray(new String[hostNameList.size()]));
                                 virtualTargetMBean.addTarget(clusb);
                                 vtClusterMap.put(resourceGroupName, clusb);
                                 htmlImport.addVirtualTarget_ToReport(virtualTargetMBean.getName(), clusb.getName(), "Weblogic Cluster");
                              }

                              vtMap.put(targetName, virtualTargetMBean);
                              if (debugLogger.isDebugEnabled()) {
                                 debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created virtual target " + virtualTargetMBean.getName());
                              }
                           }
                        }
                     }

                     List availableVTs = new ArrayList();
                     TargetMBean[] var50;
                     int i;
                     int var52;
                     TargetMBean etb;
                     JSONArray vtArray;
                     int var166;
                     if (partitionJsonObject != null) {
                        jsonObject = partitionJsonObject.opt("available-target");
                        if (jsonObject instanceof JSONArray) {
                           vtArray = (JSONArray)jsonObject;

                           for(int i = 0; i < vtArray.length(); ++i) {
                              JSONObject arrayElementObj = vtArray.optJSONObject(i);
                              Object vteo = arrayElementObj.opt("virtual-target");
                              if (vteo instanceof JSONObject) {
                                 String vtname = ((JSONObject)vteo).optString("name");
                                 VirtualTargetMBean virtualTargetMBean = null;
                                 VirtualTargetMBean[] var153 = domain.getVirtualTargets();
                                 var156 = var153.length;

                                 label2892:
                                 for(var166 = 0; var166 < var156; ++var166) {
                                    VirtualTargetMBean evtg = var153[var166];
                                    if (vtname.equals(evtg.getName())) {
                                       virtualTargetMBean = evtg;
                                       var50 = evtg.getTargets();
                                       i = var50.length;
                                       var52 = 0;

                                       while(true) {
                                          if (var52 >= i) {
                                             break label2892;
                                          }

                                          etb = var50[var52];
                                          if (etb instanceof ServerMBean) {
                                             ClusterMBean clmb = ((ServerMBean)etb).getCluster();
                                             if (clmb != null) {
                                                var10002 = domainToPartitionLogger;
                                                throw new RuntimeException(DomainToPartitionLogger.logTargetNotForAvailableTargetsLoggable(vtname, etb.getName(), clmb.getName()).getMessage());
                                             }
                                          }

                                          ++var52;
                                       }
                                    }
                                 }

                                 if (virtualTargetMBean == null) {
                                    var10002 = domainToPartitionLogger;
                                    throw new RuntimeException(DomainToPartitionLogger.logVTForAvailableTargetNotFoundLoggable(vtname).getMessage());
                                 }

                                 partitionMBean.addAvailableTarget(virtualTargetMBean);
                                 availableVTs.add(vtname);
                                 if (debugLogger.isDebugEnabled()) {
                                    debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Added virtual target " + virtualTargetMBean.getName() + " as an available target for the domain partition " + partitionName);
                                 }
                              }
                           }
                        }
                     }

                     ClusterMBean clusterMB;
                     if (partitionJsonObject != null) {
                        jsonObject = partitionJsonObject.opt("default-target");
                        if (jsonObject instanceof JSONArray) {
                           vtArray = (JSONArray)jsonObject;
                           boolean vtargetAdded = false;

                           for(int i = 0; i < vtArray.length(); ++i) {
                              JSONObject arrayElementObj = vtArray.optJSONObject(i);
                              Object vteo = arrayElementObj.opt("virtual-target");
                              if (vteo instanceof JSONObject) {
                                 vtname = ((JSONObject)vteo).optString("name");
                                 VirtualTargetMBean virtualTargetMBean = null;
                                 VirtualTargetMBean[] var160 = domain.getVirtualTargets();
                                 var166 = var160.length;

                                 label2919:
                                 for(int var163 = 0; var163 < var166; ++var163) {
                                    VirtualTargetMBean evtg = var160[var163];
                                    if (vtname.equals(evtg.getName())) {
                                       virtualTargetMBean = evtg;
                                       TargetMBean[] var172 = evtg.getTargets();
                                       var52 = var172.length;
                                       int var175 = 0;

                                       while(true) {
                                          if (var175 >= var52) {
                                             break label2919;
                                          }

                                          TargetMBean etb = var172[var175];
                                          if (etb instanceof ServerMBean) {
                                             clusterMB = ((ServerMBean)etb).getCluster();
                                             if (clusterMB != null) {
                                                var10002 = domainToPartitionLogger;
                                                throw new RuntimeException(DomainToPartitionLogger.logVTNotAddedAsDefaultTargetLoggable(vtname, etb.getName(), clusterMB.getName()).getMessage());
                                             }
                                          }

                                          ++var175;
                                       }
                                    }
                                 }

                                 if (virtualTargetMBean == null) {
                                    var10002 = domainToPartitionLogger;
                                    throw new RuntimeException(DomainToPartitionLogger.logVTNotForDefaultTargetLoggable(vtname).getMessage());
                                 }

                                 if (!vtargetAdded) {
                                    partitionMBean.addDefaultTarget(virtualTargetMBean);
                                    vtargetAdded = true;
                                    htmlImport.addefaultTarget_ToReport(virtualTargetMBean.getName());
                                    if (debugLogger.isDebugEnabled()) {
                                       debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Added virtual target " + virtualTargetMBean.getName() + " as a default target for the domain partition " + partitionName);
                                    }
                                 } else {
                                    var10000 = domainToPartitionLogger;
                                    DomainToPartitionLogger.logExistingDefaultTarget(partitionName, virtualTargetMBean.getName());
                                 }
                              }
                           }
                        }
                     }

                     Object jsonResourceGroups = readJsonRoot.opt("resource-group");
                     if (jsonResourceGroups != null && jsonResourceGroups instanceof JSONArray) {
                        JSONArray resourceGroupsArray = (JSONArray)jsonResourceGroups;
                        resourceGroupName = "";
                        Set createdBridgeDestinations = new HashSet();

                        label3362:
                        for(int rg = 0; rg < resourceGroupsArray.length(); ++rg) {
                           JSONObject jsonRGObject = resourceGroupsArray.optJSONObject(rg);
                           resourceGroupName = jsonRGObject.optString("name");
                           List rgTargetList = new ArrayList();
                           boolean isHAValidationComplete = false;
                           ResourceGroupMBean rgMBean = partitionMBean.createResourceGroup(resourceGroupName);
                           htmlImport.addResourceGroup_ToReport(rgMBean);
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Create a Resource Group with the name " + resourceGroupName);
                           }

                           Object rgTarget = jsonRGObject.opt("target");
                           Object jdbcSystemResourceObject;
                           if (rgTarget instanceof JSONArray) {
                              JSONArray rgTargetArray = (JSONArray)rgTarget;

                              for(i = 0; i < rgTargetArray.length(); ++i) {
                                 JSONObject rgTargetElementObj = rgTargetArray.optJSONObject(i);
                                 jdbcSystemResourceObject = rgTargetElementObj.opt("virtual-target");
                                 if (jdbcSystemResourceObject instanceof JSONObject) {
                                    String vtname = ((JSONObject)jdbcSystemResourceObject).optString("name");
                                    if (!availableVTs.contains(vtname)) {
                                       var10002 = domainToPartitionLogger;
                                       throw new RuntimeException(DomainToPartitionLogger.logVTNotAvailableTargetsLoggable(vtname, partitionName).getMessage());
                                    }

                                    VirtualTargetMBean vtMBean = domain.lookupVirtualTarget(vtname);
                                    if (vtMBean == null) {
                                       var10002 = domainToPartitionLogger;
                                       throw new RuntimeException(DomainToPartitionLogger.logVTNotAvailableForRGLoggable(vtname, resourceGroupName).getMessage());
                                    }

                                    rgMBean.addTarget(vtMBean);
                                 }
                              }
                           }

                           try {
                              var50 = rgMBean.findEffectiveTargets();
                              i = var50.length;

                              for(var52 = 0; var52 < i; ++var52) {
                                 etb = var50[var52];
                                 rgTargetList.add(etb.getName());
                              }
                           } catch (Exception var112) {
                           }

                           ResourceGroupTemplateMBean rgtMBean = null;
                           if (createRGT == null) {
                              createRGT = Boolean.FALSE;
                           }

                           String sysResourePendingSubDir;
                           if (createRGT) {
                              sysResourePendingSubDir = partitionName + "-" + resourceGroupName + "-RGTemplate";
                              Object readJsonObject = jsonRGObject.opt("resource-group-template");
                              if (readJsonObject instanceof JSONObject) {
                                 String nameValue = ((JSONObject)readJsonObject).optString("name");
                                 if (nameValue != null) {
                                    sysResourePendingSubDir = nameValue;
                                 }
                              }

                              if (domain.lookupResourceGroupTemplate(sysResourePendingSubDir) != null) {
                                 var10002 = domainToPartitionLogger;
                                 throw new RuntimeException(DomainToPartitionLogger.logConflictingResourceGroupTemplateLoggable(sysResourePendingSubDir).getMessage());
                              }

                              rgtMBean = domain.createResourceGroupTemplate(sysResourePendingSubDir);
                              if (debugLogger.isDebugEnabled()) {
                                 debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created a Resource Group Template with the name " + sysResourePendingSubDir);
                              }

                              sysResoureSubDir = "resource-group-templates/" + sysResourePendingSubDir;
                           } else {
                              sysResoureSubDir = "partitions/" + partitionName;
                           }

                           sysResourePendingSubDir = DomainDir.getPendingDir() + "/" + sysResoureSubDir;
                           if (rgtMBean != null) {
                              rgMBean.setResourceGroupTemplate((ResourceGroupTemplateMBean)rgtMBean);
                           } else {
                              rgtMBean = rgMBean;
                           }

                           String rgInfoStr = "Resource Group[name=" + ((ResourceGroupTemplateMBean)rgtMBean).getName() + "] /";
                           if (createRGT) {
                              rgInfoStr = "Resource GroupTemplate[name=" + ((ResourceGroupTemplateMBean)rgtMBean).getName() + "] /";
                           }

                           ImportExportHelper.setOperationType("Import");
                           if (secretKeyFile != null) {
                              ImportExportHelper.setCONFIG_TO_SCRIPT_SECRET_FILE(secretKeyFile.getCanonicalPath());
                           }

                           jdbcSystemResourceObject = jsonRGObject.opt("jdbc-system-resource");
                           Object readJStoreObject;
                           JSONObject clusterMB;
                           JDBCSystemResourceMBean jdbcSystemResourceMBean;
                           JDBCSystemResourceMBean nsr;
                           String descriptorFileName;
                           ZipEntry descpfZipEntry;
                           Path sysResourceTmpPath;
                           Path toCreate;
                           String jsonExcludeImport;
                           String safAgentJSONObj;
                           DescriptorBean dbean;
                           JSONObject jdbcOVJsonObj;
                           Object subdepArrayJSONObj;
                           int j;
                           JSONObject arrayElementOverrideObj;
                           String urls;
                           String tgtDescriptorFileName;
                           Object nsr;
                           Object sMbean;
                           if (jdbcSystemResourceObject instanceof JSONArray) {
                              for(int i = 0; i < ((JSONArray)jdbcSystemResourceObject).length(); ++i) {
                                 clusterMB = null;
                                 readJStoreObject = null;
                                 String jsonExcludeImport = null;
                                 clusterMB = ((JSONArray)jdbcSystemResourceObject).optJSONObject(i);
                                 jdbcSystemResourceMBean = domainMBeanFromDescriptor.lookupJDBCSystemResource(clusterMB.optString("name"));
                                 if (jdbcSystemResourceMBean != null) {
                                    jsonExcludeImport = clusterMB.optString("exclude-from-import");
                                    if ("true".equals(jsonExcludeImport)) {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logResourceExcludedFromImport("JDBC System Resource", jdbcSystemResourceMBean.getName());
                                       htmlImport.addImportSkipInJson_ToReport(jdbcSystemResourceMBean.getType(), jdbcSystemResourceMBean.getName());
                                    } else {
                                       if (debugLogger.isDebugEnabled()) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Creating JDBC System Resource " + jdbcSystemResourceMBean.getName());
                                       }

                                       nsr = null;
                                       descriptorFileName = jdbcSystemResourceMBean.getDescriptorFileName();
                                       descpfZipEntry = descriptorFileName != null ? ImportExportHelper.getZipEntry("config/" + descriptorFileName, partitionArchive) : null;
                                       if (descpfZipEntry != null) {
                                          sysResourceTmpPath = Paths.get(tmpDirForArchive, "config/" + descriptorFileName);
                                          toCreate = Paths.get(sysResourePendingSubDir, ImportExportHelper.removeRootFromPath(descriptorFileName));
                                          if (debugLogger.isDebugEnabled()) {
                                             debugLogger.debug("MigrationToPartitionManager:migrateToPartition : JDBC System Resource descriptor " + toCreate);
                                          }

                                          jsonExcludeImport = new String(Files.readAllBytes(sysResourceTmpPath));
                                          InputStream bis = new ByteArrayInputStream(jsonExcludeImport.getBytes());
                                          Descriptor dp = descriptoManager.createDescriptor(new ConfigReader(bis), errs, false);
                                          bis.close();
                                          dbean = dp.getRootBean();
                                          ImportExportHelper.handleEncryptedAttributes(dbean, jsonExcludeImport, true, keyFile);
                                          safAgentJSONObj = DescriptorUtils.toString(dbean);
                                          FileUtils.writeToFile(new ByteArrayInputStream(safAgentJSONObj.getBytes()), toCreate.toFile());
                                          if (debugLogger.isDebugEnabled()) {
                                             debugLogger.debug("MigrationToPartitionManager:migrateToPartition : JDBC Data Source Bean " + dbean);
                                          }

                                          nsr = ((ResourceGroupTemplateMBean)rgtMBean).createJDBCSystemResource(jdbcSystemResourceMBean.getName(), sysResoureSubDir + "/" + descriptorFileName);
                                          htmlImport.addImportSucess_ToReport(jdbcSystemResourceMBean.getType(), jdbcSystemResourceMBean.getName(), rgInfoStr + "JdbcSystemResource[name=" + jdbcSystemResourceMBean.getName() + "]");
                                          jdbcOVJsonObj = null;
                                          if (partitionJsonObject != null) {
                                             subdepArrayJSONObj = partitionJsonObject.opt("jdbc-system-resource-override");
                                             if (subdepArrayJSONObj instanceof JSONArray) {
                                                for(j = 0; j < ((JSONArray)subdepArrayJSONObj).length(); ++j) {
                                                   arrayElementOverrideObj = ((JSONArray)subdepArrayJSONObj).optJSONObject(j);
                                                   if (jdbcSystemResourceMBean.getName().equals(arrayElementOverrideObj.optString("name"))) {
                                                      jdbcOVJsonObj = arrayElementOverrideObj;
                                                      break;
                                                   }
                                                }
                                             }
                                          }

                                          if (jdbcOVJsonObj != null) {
                                             JDBCSystemResourceOverrideMBean jdbcOVB = null;

                                             try {
                                                Class c = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBean");
                                                nsr = c.getDeclaredMethod("getJDBCDriverParams").invoke(dbean);
                                                if (nsr != null) {
                                                   urls = null;
                                                   if (jdbcOVJsonObj != null) {
                                                      urls = jdbcOVJsonObj.optString("url");
                                                   }

                                                   if (urls != null && !"__EXISTING_VALUE__".equals(urls)) {
                                                      if (jdbcOVB == null) {
                                                         jdbcOVB = partitionMBean.createJDBCSystemResourceOverride(jdbcSystemResourceMBean.getName());
                                                      }

                                                      jdbcOVB.setURL(urls);
                                                   }

                                                   tgtDescriptorFileName = null;
                                                   if (jdbcOVJsonObj != null) {
                                                      tgtDescriptorFileName = jdbcOVJsonObj.optString("password");
                                                   }

                                                   if (tgtDescriptorFileName != null && !"__EXISTING_VALUE__".equals(tgtDescriptorFileName)) {
                                                      if (jdbcOVB == null) {
                                                         jdbcOVB = partitionMBean.createJDBCSystemResourceOverride(jdbcSystemResourceMBean.getName());
                                                      }

                                                      jdbcOVB.setPassword(tgtDescriptorFileName);
                                                   }

                                                   String user = null;
                                                   if (jdbcOVJsonObj != null) {
                                                      user = jdbcOVJsonObj.optString("user");
                                                   }

                                                   if (user != null && !"__EXISTING_VALUE__".equals(user)) {
                                                      if (jdbcOVB == null) {
                                                         jdbcOVB = partitionMBean.createJDBCSystemResourceOverride(jdbcSystemResourceMBean.getName());
                                                      }

                                                      jdbcOVB.setUser(user);
                                                   }
                                                }

                                                if (jdbcOVB != null) {
                                                   sMbean = c.getDeclaredMethod("getName").invoke(dbean);
                                                   if (sMbean != null) {
                                                      jdbcOVB.setDataSourceName(sMbean.toString());
                                                   }

                                                   if (debugLogger.isDebugEnabled()) {
                                                      debugLogger.debug("MigrationToPartitionManager:migrateToPartition : JDBC Override MBean " + jdbcOVB + " created.");
                                                   }
                                                }
                                             } catch (Exception var113) {
                                                if (debugLogger.isDebugEnabled()) {
                                                   debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Exception while creating the JDBC Override MBean - " + var113);
                                                }
                                             }
                                          }
                                       }

                                       if (debugLogger.isDebugEnabled()) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Descriptor of the newly created JDBC System Resource " + nsr.getDescriptorFileName());
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : JDBC System Resource " + DescriptorUtils.toString(nsr.getResource()));
                                       }
                                    }
                                 }
                              }
                           }

                           Map clustFSMap = new HashMap();
                           Object readPStoreObject = jsonRGObject.opt("file-store");
                           TargetMBean[] var207;
                           int var211;
                           int i;
                           TargetMBean targt;
                           if (readPStoreObject instanceof JSONArray) {
                              for(int i = 0; i < ((JSONArray)readPStoreObject).length(); ++i) {
                                 ClusterMBean clusterMB = null;
                                 FileStoreMBean nfileStoreMB = null;
                                 jdbcSystemResourceMBean = null;
                                 nsr = null;
                                 JSONObject arrayElementObj = ((JSONArray)readPStoreObject).optJSONObject(i);
                                 FileStoreMBean fstore = domainMBeanFromDescriptor.lookupFileStore(arrayElementObj.optString("name"));
                                 if (fstore != null) {
                                    String jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                    if ("true".equals(jsonExcludeImport)) {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logResourceExcludedFromImport("File Store", fstore.getName());
                                       htmlImport.addImportSkipInJson_ToReport(fstore.getType(), fstore.getName());
                                    } else {
                                       ClusterMBean targetClusterMBean = null;
                                       var207 = fstore.getTargets();
                                       var211 = var207.length;

                                       for(i = 0; i < var211; ++i) {
                                          targt = var207[i];
                                          fstore.removeTarget(targt);
                                          if (targt instanceof MigratableTargetMBean) {
                                             clusterMB = ((MigratableTargetMBean)targt).getCluster();
                                          }

                                          if (targt instanceof ClusterMBean) {
                                             targetClusterMBean = (ClusterMBean)targt;
                                          }
                                       }

                                       if (clusterMB != null) {
                                          if (clustFSMap.get(clusterMB.getName()) == null) {
                                             PersistentStoreMBean psMBean = ((ResourceGroupTemplateMBean)rgtMBean).createFileStore(fstore.getName());
                                             htmlImport.addImportSucess_ToReport(psMBean.getType(), psMBean.getName(), rgInfoStr + "File Store[name=" + psMBean.getName() + "]");
                                             nfileStoreMB = (FileStoreMBean)psMBean;
                                             nfileStoreMB.setDirectory(nfileStoreMB.getName());
                                             nfileStoreMB.setDistributionPolicy("Distributed");
                                             nfileStoreMB.setMigrationPolicy("Always");
                                             if (!isHAValidationComplete) {
                                                validateHAPolicies(psMBean, rgTargetList, vtClusterMap);
                                                isHAValidationComplete = true;
                                             }

                                             nfileStoreMB.setRestartInPlace(true);
                                             clustFSMap.put(clusterMB.getName(), psMBean);
                                          }
                                       } else {
                                          nfileStoreMB = (FileStoreMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(fstore.getType(), fstore);
                                          htmlImport.addImportSucess_ToReport(fstore.getType(), fstore.getName(), rgInfoStr + "File Store[name=" + fstore.getName() + "]");
                                          nfileStoreMB.setDistributionPolicy("Distributed");
                                          nfileStoreMB.setMigrationPolicy("Always");
                                          nfileStoreMB.setRestartInPlace(true);
                                          if (targetClusterMBean != null && !isHAValidationComplete) {
                                             validateHAPolicies(nfileStoreMB, rgTargetList, vtClusterMap);
                                             isHAValidationComplete = true;
                                          }
                                       }

                                       if (debugLogger.isDebugEnabled() && nfileStoreMB != null) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created a File Store with the name " + nfileStoreMB.getName() + ", with distribution policy " + nfileStoreMB.getDistributionPolicy() + " and migration policy " + nfileStoreMB.getMigrationPolicy());
                                       }
                                    }
                                 }
                              }
                           }

                           readJStoreObject = jsonRGObject.opt("jdbc-store");
                           String jmsServerName;
                           if (readJStoreObject instanceof JSONArray) {
                              for(int i = 0; i < ((JSONArray)readJStoreObject).length(); ++i) {
                                 clusterMB = null;
                                 jdbcSystemResourceMBean = null;
                                 nsr = null;
                                 descriptorFileName = null;
                                 JSONObject arrayElementObj = ((JSONArray)readJStoreObject).optJSONObject(i);
                                 JDBCStoreMBean jstore = domainMBeanFromDescriptor.lookupJDBCStore(arrayElementObj.optString("name"));
                                 if (jstore != null) {
                                    descriptorFileName = arrayElementObj.optString("exclude-from-import");
                                    if ("true".equals(descriptorFileName)) {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logResourceExcludedFromImport("JDBC Store", jstore.getName());
                                       htmlImport.addImportSkipInJson_ToReport(jstore.getType(), jstore.getName());
                                    } else {
                                       var207 = jstore.getTargets();
                                       var211 = var207.length;

                                       for(i = 0; i < var211; ++i) {
                                          targt = var207[i];
                                          jstore.removeTarget(targt);
                                          if ((targt instanceof MigratableTargetMBean || targt instanceof ClusterMBean) && !isHAValidationComplete) {
                                             validateHAPolicies(jstore, rgTargetList, vtClusterMap);
                                             isHAValidationComplete = true;
                                          }
                                       }

                                       JDBCSystemResourceMBean odsbean = jstore.getDataSource();
                                       if (odsbean != null) {
                                          JDBCSystemResourceMBean ndsbean = ((ResourceGroupTemplateMBean)rgtMBean).lookupJDBCSystemResource(odsbean.getName());
                                          if (ndsbean == null) {
                                             var10000 = domainToPartitionLogger;
                                             DomainToPartitionLogger.logErrorCreatingJDBCStore(jstore.getName(), odsbean.getName());
                                             htmlImport.addImportSkipInJson_ToReport(jstore.getName(), jstore.getType());
                                          } else {
                                             JDBCStoreMBean njStoreMB = ((ResourceGroupTemplateMBean)rgtMBean).createJDBCStore(jstore.getName());
                                             htmlImport.addImportSucess_ToReport(jstore.getType(), jstore.getName(), rgInfoStr + "Jdbc Store[name=" + jstore.getName() + "]");
                                             jmsServerName = arrayElementObj.optString("prefix-name", (String)null);
                                             String jdbcStorePrefixValue;
                                             if (jmsServerName != null && !"__EXISTING_VALUE__".equals(jmsServerName)) {
                                                jdbcStorePrefixValue = jmsServerName;
                                             } else {
                                                jdbcStorePrefixValue = jstore.getPrefixName();
                                             }

                                             if (jdbcStorePrefixValue != null) {
                                                njStoreMB.setPrefixName(jdbcStorePrefixValue);
                                             }

                                             njStoreMB.setDataSource(ndsbean);
                                          }
                                       } else {
                                          var10000 = domainToPartitionLogger;
                                          DomainToPartitionLogger.logJDBCStoreWithJDBCSystemResource(jstore.getName());
                                          htmlImport.addImportSkipInJson_ToReport(jstore.getName(), jstore.getType());
                                       }
                                    }
                                 }
                              }
                           }

                           Map clustMsgBgsMap = new HashMap();
                           Object readBridgeObject = jsonRGObject.opt("messaging-bridge");
                           Object pathServiceObject;
                           HashMap clusterPathServiceMap;
                           int i;
                           int i;
                           int var242;
                           if (readBridgeObject instanceof JSONArray) {
                              for(int i = 0; i < ((JSONArray)readBridgeObject).length(); ++i) {
                                 ClusterMBean clusterMB = null;
                                 descriptorFileName = null;
                                 descpfZipEntry = null;
                                 sysResourceTmpPath = null;
                                 JSONObject arrayElementObj = ((JSONArray)readBridgeObject).optJSONObject(i);
                                 MessagingBridgeMBean msgBrdg = domainMBeanFromDescriptor.lookupMessagingBridge(arrayElementObj.optString("name"));
                                 if (msgBrdg != null) {
                                    pathServiceObject = arrayElementObj.opt("jms-bridge-destination");
                                    clusterPathServiceMap = new HashMap();
                                    JMSBridgeDestinationMBean newTargetBridgeDestination;
                                    String bridgeDestName;
                                    String bridgeDestName;
                                    if (pathServiceObject instanceof JSONArray) {
                                       for(i = 0; i < ((JSONArray)pathServiceObject).length(); ++i) {
                                          JSONObject jsonBrdgDestn = ((JSONArray)pathServiceObject).optJSONObject(i);
                                          newTargetBridgeDestination = domainMBeanFromDescriptor.lookupJMSBridgeDestination(jsonBrdgDestn.optString("name"));
                                          if (newTargetBridgeDestination != null) {
                                             String connectionUrl = jsonBrdgDestn.optString("connection-url");
                                             bridgeDestName = jsonBrdgDestn.optString("user-name");
                                             bridgeDestName = jsonBrdgDestn.optString("user-password");
                                             if (connectionUrl != null && !"__EXISTING_VALUE__".equals(connectionUrl)) {
                                                newTargetBridgeDestination.setConnectionURL(connectionUrl);
                                             }

                                             if (bridgeDestName != null && !"__EXISTING_VALUE__".equals(bridgeDestName)) {
                                                newTargetBridgeDestination.setUserName(bridgeDestName);
                                             }

                                             if (bridgeDestName != null && !"__EXISTING_VALUE__".equals(bridgeDestName)) {
                                                byte[] passwordEncrypted = ImpExpSecurityServiceImpl.getInstance().encrypt(bridgeDestName);
                                                newTargetBridgeDestination.setUserPasswordEncrypted(passwordEncrypted);
                                             }

                                             clusterPathServiceMap.put(newTargetBridgeDestination.getName(), newTargetBridgeDestination);
                                          }
                                       }
                                    }

                                    String jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                    if ("true".equals(jsonExcludeImport)) {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logResourceExcludedFromImport("Messaging Bridge", msgBrdg.getName());
                                       htmlImport.addImportSkipInJson_ToReport(msgBrdg.getType(), msgBrdg.getName());
                                    } else {
                                       TargetMBean[] var234 = msgBrdg.getTargets();
                                       i = var234.length;

                                       for(var242 = 0; var242 < i; ++var242) {
                                          TargetMBean targt = var234[var242];
                                          msgBrdg.removeTarget(targt);
                                          if (targt instanceof MigratableTargetMBean) {
                                             clusterMB = ((MigratableTargetMBean)targt).getCluster();
                                          }

                                          if (targt instanceof ClusterMBean && !isHAValidationComplete) {
                                             validateHAPolicies(msgBrdg, rgTargetList, vtClusterMap);
                                             isHAValidationComplete = true;
                                          }
                                       }

                                       if (clusterMB != null && clustMsgBgsMap.get(clusterMB.getName()) != null && !isHAValidationComplete) {
                                          validateHAPolicies(msgBrdg, rgTargetList, vtClusterMap);
                                          isHAValidationComplete = true;
                                       }

                                       MessagingBridgeMBean nMsgBrdgMB = ((ResourceGroupTemplateMBean)rgtMBean).createMessagingBridge(msgBrdg.getName());
                                       JMSBridgeDestinationMBean oldSourceBridgeDestination = (JMSBridgeDestinationMBean)msgBrdg.getSourceDestination();
                                       JMSBridgeDestinationMBean newSourceBridgeDestination;
                                       if (oldSourceBridgeDestination != null) {
                                          newSourceBridgeDestination = ((ResourceGroupTemplateMBean)rgtMBean).lookupJMSBridgeDestination(oldSourceBridgeDestination.getName());
                                          if (newSourceBridgeDestination == null) {
                                             newTargetBridgeDestination = (JMSBridgeDestinationMBean)clusterPathServiceMap.get(oldSourceBridgeDestination.getName());
                                             if (newTargetBridgeDestination == null) {
                                                newTargetBridgeDestination = oldSourceBridgeDestination;
                                             }

                                             newSourceBridgeDestination = (JMSBridgeDestinationMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(newTargetBridgeDestination.getType(), newTargetBridgeDestination);
                                             byte[] encryptedPassword = newTargetBridgeDestination.getUserPasswordEncrypted();
                                             if (encryptedPassword != null) {
                                                bridgeDestName = ImpExpSecurityServiceImpl.getInstance().decrypt(encryptedPassword);
                                                newSourceBridgeDestination.setUserPassword(bridgeDestName);
                                             }

                                             bridgeDestName = newTargetBridgeDestination.getName();
                                             if (createdBridgeDestinations.contains(bridgeDestName)) {
                                                bridgeDestName = bridgeDestName + "_" + msgBrdg.getName();
                                             }

                                             newSourceBridgeDestination.setName(bridgeDestName);
                                             createdBridgeDestinations.add(bridgeDestName);
                                             nMsgBrdgMB.setSourceDestination(newSourceBridgeDestination);
                                             htmlImport.addImportSucess_ToReport(newSourceBridgeDestination.getType(), bridgeDestName, rgInfoStr + newSourceBridgeDestination.getType() + "[name=" + bridgeDestName + "]");
                                          }
                                       }

                                       newSourceBridgeDestination = (JMSBridgeDestinationMBean)msgBrdg.getTargetDestination();
                                       if (newSourceBridgeDestination != null) {
                                          newTargetBridgeDestination = ((ResourceGroupTemplateMBean)rgtMBean).lookupJMSBridgeDestination(newSourceBridgeDestination.getName());
                                          if (newTargetBridgeDestination == null) {
                                             JMSBridgeDestinationMBean overriddenTargetBridgeDestination = (JMSBridgeDestinationMBean)clusterPathServiceMap.get(newSourceBridgeDestination.getName());
                                             if (overriddenTargetBridgeDestination == null) {
                                                overriddenTargetBridgeDestination = newSourceBridgeDestination;
                                             }

                                             newTargetBridgeDestination = (JMSBridgeDestinationMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(overriddenTargetBridgeDestination.getType(), overriddenTargetBridgeDestination);
                                             byte[] encryptedPassword = overriddenTargetBridgeDestination.getUserPasswordEncrypted();
                                             if (encryptedPassword != null) {
                                                bridgeDestName = ImpExpSecurityServiceImpl.getInstance().decrypt(encryptedPassword);
                                                newTargetBridgeDestination.setUserPassword(bridgeDestName);
                                             }

                                             bridgeDestName = overriddenTargetBridgeDestination.getName();
                                             if (createdBridgeDestinations.contains(bridgeDestName)) {
                                                bridgeDestName = bridgeDestName + "_" + msgBrdg.getName();
                                             }

                                             newTargetBridgeDestination.setName(bridgeDestName);
                                             createdBridgeDestinations.add(bridgeDestName);
                                             nMsgBrdgMB.setTargetDestination(newTargetBridgeDestination);
                                             htmlImport.addImportSucess_ToReport(newTargetBridgeDestination.getType(), bridgeDestName, rgInfoStr + newTargetBridgeDestination.getType() + "[name=" + bridgeDestName + "]");
                                          }
                                       }

                                       nMsgBrdgMB.setStarted(msgBrdg.isStarted());
                                       nMsgBrdgMB.setAsyncEnabled(msgBrdg.isAsyncEnabled());
                                       nMsgBrdgMB.setBatchInterval(msgBrdg.getBatchInterval());
                                       nMsgBrdgMB.setBatchSize(msgBrdg.getBatchSize());
                                       nMsgBrdgMB.setDurabilityEnabled(msgBrdg.isDurabilityEnabled());
                                       nMsgBrdgMB.setIdleTimeMaximum(msgBrdg.getIdleTimeMaximum());
                                       if (msgBrdg.getNotes() != null && !"".equals(msgBrdg.getNotes())) {
                                          nMsgBrdgMB.setNotes(msgBrdg.getNotes());
                                       }

                                       nMsgBrdgMB.setPreserveMsgProperty(msgBrdg.getPreserveMsgProperty());
                                       nMsgBrdgMB.setQOSDegradationAllowed(msgBrdg.isQOSDegradationAllowed());
                                       if (msgBrdg.getQualityOfService() != null && !"".equals(msgBrdg.getQualityOfService())) {
                                          nMsgBrdgMB.setQualityOfService(msgBrdg.getQualityOfService());
                                       }

                                       nMsgBrdgMB.setReconnectDelayIncrease(msgBrdg.getReconnectDelayIncrease());
                                       nMsgBrdgMB.setReconnectDelayMaximum(msgBrdg.getReconnectDelayMaximum());
                                       nMsgBrdgMB.setReconnectDelayMinimum(msgBrdg.getReconnectDelayMinimum());
                                       if (msgBrdg.getSelector() != null && !"".equals(msgBrdg.getSelector())) {
                                          nMsgBrdgMB.setSelector(msgBrdg.getSelector());
                                       }

                                       nMsgBrdgMB.setTransactionTimeout(msgBrdg.getTransactionTimeout());
                                       htmlImport.addImportSucess_ToReport(msgBrdg.getType(), msgBrdg.getName(), rgInfoStr + msgBrdg.getType() + "[name=" + msgBrdg.getName() + "]");
                                       if (clusterMB != null) {
                                          clustMsgBgsMap.put(clusterMB.getName(), nMsgBrdgMB);
                                       }

                                       if (debugLogger.isDebugEnabled() && nMsgBrdgMB != null) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created a Messaging Bridge with the name " + nMsgBrdgMB.getName());
                                       }
                                    }
                                 }
                              }
                           }

                           this.jmsServerTgtMap = new HashMap();
                           this.safAgentTgtMap = new HashMap();
                           Object jmsSvrsObject = jsonRGObject.opt("jms-server");
                           Map clustJMSSvrMap = new HashMap();
                           int var272;
                           if (jmsSvrsObject instanceof JSONArray) {
                              for(int i = 0; i < ((JSONArray)jmsSvrsObject).length(); ++i) {
                                 ClusterMBean clusterMB = null;
                                 JMSServerMBean njmssvbean = null;
                                 toCreate = null;
                                 jsonExcludeImport = null;
                                 JSONObject arrayElementObj = ((JSONArray)jmsSvrsObject).optJSONObject(i);
                                 JMSServerMBean jmssvr = domainMBeanFromDescriptor.lookupJMSServer(arrayElementObj.optString("name"));
                                 if (jmssvr != null) {
                                    jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                    if ("true".equals(jsonExcludeImport)) {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logResourceExcludedFromImport("JMS Server", jmssvr.getName());
                                       htmlImport.addImportSkipInJson_ToReport(jmssvr.getType(), jmssvr.getName());
                                    } else {
                                       List targets = new ArrayList();
                                       TargetMBean[] var240 = jmssvr.getTargets();
                                       var242 = var240.length;

                                       for(var272 = 0; var272 < var242; ++var272) {
                                          TargetMBean targt = var240[var272];
                                          targets.add(targt);
                                          jmssvr.removeTarget(targt);
                                          if (targt instanceof MigratableTargetMBean) {
                                             clusterMB = ((MigratableTargetMBean)targt).getCluster();
                                          } else if (targt instanceof ServerMBean) {
                                             clusterMB = ((ServerMBean)targt).getCluster();
                                          }
                                       }

                                       this.jmsServerTgtMap.put(jmssvr.getName(), targets);
                                       if (clusterMB == null || clustJMSSvrMap.get(clusterMB.getName()) == null && clustFSMap.get(clusterMB.getName()) != null) {
                                          njmssvbean = (JMSServerMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(jmssvr.getType(), jmssvr);
                                          htmlImport.addImportSucess_ToReport(jmssvr.getType(), jmssvr.getName(), rgInfoStr + jmssvr.getType() + "[name=" + jmssvr.getName() + "]");
                                          Object psMbean;
                                          if (clusterMB != null) {
                                             psMbean = null;
                                             PersistentStoreMBean associatedStore = jmssvr.getPersistentStore();
                                             if (associatedStore != null) {
                                                if (associatedStore instanceof FileStoreMBean) {
                                                   associatedStore = ((ResourceGroupTemplateMBean)rgtMBean).lookupFileStore(((PersistentStoreMBean)associatedStore).getName());
                                                } else if (associatedStore instanceof JDBCStoreMBean) {
                                                   associatedStore = ((ResourceGroupTemplateMBean)rgtMBean).lookupJDBCStore(((PersistentStoreMBean)associatedStore).getName());
                                                }

                                                if (associatedStore != null) {
                                                   psMbean = associatedStore;
                                                }
                                             }

                                             if (psMbean == null) {
                                                psMbean = (PersistentStoreMBean)clustFSMap.get(clusterMB.getName());
                                             }

                                             if (psMbean != null) {
                                                njmssvbean.setPersistentStore((PersistentStoreMBean)psMbean);
                                                ((PersistentStoreMBean)psMbean).setDistributionPolicy("Distributed");
                                             }

                                             clustJMSSvrMap.put(clusterMB.getName(), njmssvbean);
                                          } else {
                                             psMbean = njmssvbean.getPersistentStore();
                                             PersistentStoreMBean opsbean = jmssvr.getPersistentStore();
                                             boolean created = false;
                                             if (opsbean instanceof JDBCStoreMBean) {
                                                JDBCStoreMBean njdbcsMB = ((ResourceGroupTemplateMBean)rgtMBean).lookupJDBCStore(opsbean.getName());
                                                if (njdbcsMB != null) {
                                                   njmssvbean.setPersistentStore(njdbcsMB);
                                                   if (debugLogger.isDebugEnabled()) {
                                                      debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Set JDBCStore " + njdbcsMB + " to " + njmssvbean);
                                                   }

                                                   psMbean = njdbcsMB;
                                                } else {
                                                   var10000 = domainToPartitionLogger;
                                                   DomainToPartitionLogger.logInvalidJDBCStoreForJMSServer(opsbean.getName(), njmssvbean.getName());
                                                }
                                             } else if (opsbean instanceof FileStoreMBean) {
                                                FileStoreMBean nfilesMB = ((ResourceGroupTemplateMBean)rgtMBean).lookupFileStore(opsbean.getName());
                                                if (nfilesMB != null) {
                                                   njmssvbean.setPersistentStore(nfilesMB);
                                                   if (debugLogger.isDebugEnabled()) {
                                                      debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Set FileStore " + nfilesMB + " to " + njmssvbean);
                                                   }

                                                   psMbean = nfilesMB;
                                                } else if (debugLogger.isDebugEnabled()) {
                                                   debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Cannot find/set FileStore " + opsbean + " to " + njmssvbean);
                                                }
                                             } else if (psMbean == null) {
                                                psMbean = ((ResourceGroupTemplateMBean)rgtMBean).createFileStore(njmssvbean.getName() + "FileStore");
                                                htmlImport.addImportSucess_ToReport(((PersistentStoreMBean)psMbean).getType(), "(Does Not Exist)", rgInfoStr + ((PersistentStoreMBean)psMbean).getType() + "[name=" + ((PersistentStoreMBean)psMbean).getName() + "]");
                                                ((FileStoreMBean)psMbean).setDirectory(((PersistentStoreMBean)psMbean).getName());
                                                ((PersistentStoreMBean)psMbean).setRestartInPlace(true);
                                                njmssvbean.setPersistentStore((PersistentStoreMBean)psMbean);
                                                created = true;
                                             }

                                             if (psMbean != null) {
                                                ((PersistentStoreMBean)psMbean).setDistributionPolicy("Distributed");
                                                ((PersistentStoreMBean)psMbean).setMigrationPolicy("Always");
                                                if (debugLogger.isDebugEnabled()) {
                                                   debugLogger.debug("MigrationToPartitionManager:migrateToPartition : " + (created ? "created" : "set") + " FileStore: " + psMbean + " with distribution policy " + ((PersistentStoreMBean)psMbean).getDistributionPolicy() + " and migration policy :" + ((PersistentStoreMBean)psMbean).getMigrationPolicy());
                                                }
                                             }
                                          }
                                       }

                                       if (debugLogger.isDebugEnabled() && njmssvbean != null) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created JMS Server " + njmssvbean.getName());
                                       }
                                    }
                                 }
                              }
                           }

                           Map clusterSAFMap = new HashMap();
                           Map safAgentStoreNames = new HashMap();
                           Object safAgentObject = jsonRGObject.opt("saf-agent");
                           JSONObject arrayElementObj;
                           Object safRemoteCtxJSONObj;
                           if (safAgentObject instanceof JSONArray) {
                              for(int i = 0; i < ((JSONArray)safAgentObject).length(); ++i) {
                                 jsonExcludeImport = null;
                                 safAgentJSONObj = null;
                                 arrayElementObj = ((JSONArray)safAgentObject).optJSONObject(i);
                                 SAFAgentMBean safagent = domainMBeanFromDescriptor.lookupSAFAgent(arrayElementObj.optString("name"));
                                 if (safagent != null) {
                                    jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                    if ("true".equals(jsonExcludeImport)) {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logResourceExcludedFromImport("SAF Agent", safagent.getName());
                                       htmlImport.addImportSkipInJson_ToReport(safagent.getType(), safagent.getName());
                                    } else {
                                       ClusterMBean clusterMB = null;
                                       List targets = new ArrayList();
                                       TargetMBean[] var283 = safagent.getTargets();
                                       j = var283.length;

                                       for(int var259 = 0; var259 < j; ++var259) {
                                          TargetMBean targt = var283[var259];
                                          targets.add(targt);
                                          safagent.removeTarget(targt);
                                          if (targt instanceof MigratableTargetMBean) {
                                             clusterMB = ((MigratableTargetMBean)targt).getCluster();
                                          } else if (targt instanceof ServerMBean) {
                                             clusterMB = ((ServerMBean)targt).getCluster();
                                          }
                                       }

                                       this.safAgentTgtMap.put(safagent.getName(), targets);
                                       subdepArrayJSONObj = null;
                                       if (clusterMB != null) {
                                          if (clusterSAFMap.containsKey(clusterMB.getName())) {
                                             if (debugLogger.isDebugEnabled()) {
                                                debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Skipped creating SAF Agent " + safagent.getName());
                                             }

                                             htmlImport.addImportSkipInJson_ToReport(safagent.getType(), safagent.getName());
                                             continue;
                                          }

                                          safRemoteCtxJSONObj = safagent.getStore();
                                          if (safRemoteCtxJSONObj != null) {
                                             if (safRemoteCtxJSONObj instanceof FileStoreMBean) {
                                                safRemoteCtxJSONObj = ((ResourceGroupTemplateMBean)rgtMBean).lookupFileStore(((PersistentStoreMBean)safRemoteCtxJSONObj).getName());
                                             } else if (safRemoteCtxJSONObj instanceof JDBCStoreMBean) {
                                                safRemoteCtxJSONObj = ((ResourceGroupTemplateMBean)rgtMBean).lookupJDBCStore(((PersistentStoreMBean)safRemoteCtxJSONObj).getName());
                                             }

                                             if (safRemoteCtxJSONObj != null) {
                                                subdepArrayJSONObj = safRemoteCtxJSONObj;
                                             } else if (safagent.getStore() != null) {
                                                subdepArrayJSONObj = (PersistentStoreMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(safagent.getStore().getType(), safagent.getStore());
                                             }
                                          }

                                          safagent.setStore((PersistentStoreMBean)null);
                                       }

                                       SAFAgentMBean nsagAgMBean = (SAFAgentMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(safagent.getType(), safagent);
                                       htmlImport.addImportSucess_ToReport(safagent.getType(), safagent.getName(), rgInfoStr + safagent.getType() + "[name=" + safagent.getName() + "]");
                                       if (clusterMB != null && !clusterSAFMap.containsKey(clusterMB.getName())) {
                                          clusterSAFMap.put(clusterMB.getName(), nsagAgMBean);
                                          nsagAgMBean.setStore((PersistentStoreMBean)subdepArrayJSONObj);
                                       }

                                       nsr = nsagAgMBean.getStore();
                                       if (nsr == null || ((PersistentStoreMBean)nsr).getDistributionPolicy() != "Distributed") {
                                          nsr = ((ResourceGroupTemplateMBean)rgtMBean).createFileStore(nsagAgMBean.getName() + "FileStore");
                                          htmlImport.addImportSucess_ToReport(((PersistentStoreMBean)nsr).getType(), "-", rgInfoStr + ((PersistentStoreMBean)nsr).getType() + "[name=" + ((PersistentStoreMBean)nsr).getName() + "]");
                                          ((FileStoreMBean)nsr).setDirectory(((PersistentStoreMBean)nsr).getName());
                                          nsagAgMBean.setStore((PersistentStoreMBean)nsr);
                                          if (debugLogger.isDebugEnabled()) {
                                             debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created file store " + ((PersistentStoreMBean)nsr).getName());
                                          }
                                       }

                                       ((PersistentStoreMBean)nsr).setDistributionPolicy("Distributed");
                                       ((PersistentStoreMBean)nsr).setMigrationPolicy("Always");
                                       safAgentStoreNames.put(nsagAgMBean.getStore().getName(), nsagAgMBean.getName());
                                    }
                                 }
                              }
                           }

                           this.singletonJMSServers = new HashSet();
                           Set processedJMSSysRes = new HashSet();
                           Object readJMSSRObject = jsonRGObject.opt("jms-system-resource");
                           JMSSystemResourceMBean sr;
                           String jsonExcludeImport;
                           if (readJMSSRObject instanceof JSONArray) {
                              for(i = 0; i < ((JSONArray)readJMSSRObject).length(); ++i) {
                                 arrayElementObj = ((JSONArray)readJMSSRObject).optJSONObject(i);
                                 sr = domainMBeanFromDescriptor.lookupJMSSystemResource(arrayElementObj.optString("name"));
                                 if (sr != null && !(sr instanceof JDBCSystemResourceMBean)) {
                                    dbean = null;
                                    jdbcOVJsonObj = null;
                                    subdepArrayJSONObj = null;
                                    jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                    subdepArrayJSONObj = arrayElementObj.opt("sub-deployment");
                                    safRemoteCtxJSONObj = arrayElementObj.opt("saf-remote-context");
                                    arrayElementOverrideObj = null;
                                    urls = sr.getDescriptorFileName();
                                    tgtDescriptorFileName = sr.getDescriptorFileName();
                                    ZipEntry descpfZipEntry = urls != null ? ImportExportHelper.getZipEntry("config/" + urls, partitionArchive) : null;
                                    if (descpfZipEntry == null && urls != null) {
                                       String jmsStrippedDescFileName = urls.replaceAll("^jms/", "");
                                       descpfZipEntry = ImportExportHelper.getZipEntry("config/" + jmsStrippedDescFileName, partitionArchive);
                                       if (descpfZipEntry != null) {
                                          urls = jmsStrippedDescFileName;
                                       }
                                    }

                                    if (descpfZipEntry != null) {
                                       Path sysResourceTmpPath = Paths.get(tmpDirForArchive, "config/" + urls);
                                       Path toCreate = Paths.get(sysResourePendingSubDir, ImportExportHelper.removeRootFromPath(tgtDescriptorFileName));
                                       if (debugLogger.isDebugEnabled()) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Descriptor for the JMS System Resource " + toCreate);
                                       }

                                       String systemResourceDesc = new String(Files.readAllBytes(sysResourceTmpPath));
                                       if (sr instanceof JMSSystemResourceMBean) {
                                          if ("true".equals(jsonExcludeImport)) {
                                             var10000 = domainToPartitionLogger;
                                             DomainToPartitionLogger.logResourceExcludedFromImport("JMS System Resource", sr.getName());
                                             htmlImport.addImportSkipInJson_ToReport(sr.getType(), sr.getName());
                                             continue;
                                          }

                                          InputStream bis = new ByteArrayInputStream(systemResourceDesc.getBytes());
                                          Descriptor dp = descriptoManager.createDescriptor(new ConfigReader(bis), errs, false);
                                          bis.close();
                                          DescriptorBean dbean = dp.getRootBean();
                                          ImportExportHelper.handleEncryptedAttributes(dbean, systemResourceDesc, true, keyFile);
                                          JMSBean jmsBean = (JMSBean)dbean;
                                          UniformDistributedTopicBean[] var85 = jmsBean.getUniformDistributedTopics();
                                          int j = var85.length;

                                          int var87;
                                          for(var87 = 0; var87 < j; ++var87) {
                                             UniformDistributedTopicBean udTopicBean = var85[var87];
                                             if (JMSConstants.FORWARDING_POLICY_REPLICATED.equals(udTopicBean.getForwardingPolicy())) {
                                                var10000 = domainToPartitionLogger;
                                                DomainToPartitionLogger.logConversionToPartitionedUDTopic(udTopicBean.getName(), sr.getName());
                                                udTopicBean.setForwardingPolicy(JMSConstants.FORWARDING_POLICY_PARTITIONED);
                                             }
                                          }

                                          HashMap safRemoteCtxMap = new HashMap();
                                          SAFRemoteContextBean[] var301 = jmsBean.getSAFRemoteContexts();
                                          var87 = var301.length;

                                          int k;
                                          for(k = 0; k < var87; ++k) {
                                             SAFRemoteContextBean safRCBean = var301[k];
                                             safRemoteCtxMap.put(safRCBean.getName(), safRCBean);
                                          }

                                          String subdepJSONObj;
                                          String jsonSubdepExcludeImport;
                                          if (!safRemoteCtxMap.isEmpty() && safRemoteCtxJSONObj instanceof JSONArray) {
                                             for(j = 0; j < ((JSONArray)safRemoteCtxJSONObj).length(); ++j) {
                                                JSONObject arrayElementSafRCObj = ((JSONArray)safRemoteCtxJSONObj).optJSONObject(j);
                                                if (arrayElementSafRCObj != null) {
                                                   String safRCName = arrayElementSafRCObj.optString("name");
                                                   String safRCLoginURL = arrayElementSafRCObj.optString("loginURL");
                                                   subdepJSONObj = arrayElementSafRCObj.optString("username");
                                                   jsonSubdepExcludeImport = arrayElementSafRCObj.optString("password");
                                                   SAFRemoteContextBean safRemoteCtxBean = (SAFRemoteContextBean)safRemoteCtxMap.get(safRCName);
                                                   if (safRemoteCtxBean != null && !"__EXISTING_VALUE__".equals(safRCLoginURL)) {
                                                      safRemoteCtxBean.getSAFLoginContext().setLoginURL(safRCLoginURL);
                                                   }

                                                   if (safRemoteCtxBean != null && !"__EXISTING_VALUE__".equals(subdepJSONObj)) {
                                                      safRemoteCtxBean.getSAFLoginContext().setUsername(subdepJSONObj);
                                                   }

                                                   if (safRemoteCtxBean != null && !"__EXISTING_VALUE__".equals(jsonSubdepExcludeImport)) {
                                                      safRemoteCtxBean.getSAFLoginContext().setPassword(jsonSubdepExcludeImport);
                                                   }
                                                }
                                             }
                                          }

                                          String beanDescriptorWithNewPwd = DescriptorUtils.toString(dbean);
                                          FileUtils.writeToFile(new ByteArrayInputStream(beanDescriptorWithNewPwd.getBytes()), toCreate.toFile());
                                          if (debugLogger.isDebugEnabled()) {
                                             debugLogger.debug("MigrationToPartitionManager:migrateToPartition : JMS System Resource " + dbean);
                                          }

                                          QueueBean[] var302 = jmsBean.getQueues();
                                          var87 = var302.length;

                                          for(k = 0; k < var87; ++k) {
                                             QueueBean queueBean = var302[k];
                                             this.singletonJMSServers.addAll(this.getSingletonJMSServers(queueBean, (JMSSystemResourceMBean)sr, domainMBeanFromDescriptor));
                                          }

                                          TopicBean[] var303 = jmsBean.getTopics();
                                          var87 = var303.length;

                                          for(k = 0; k < var87; ++k) {
                                             TopicBean topicBean = var303[k];
                                             this.singletonJMSServers.addAll(this.getSingletonJMSServers(topicBean, (JMSSystemResourceMBean)sr, domainMBeanFromDescriptor));
                                          }

                                          nsr = ((ResourceGroupTemplateMBean)rgtMBean).createJMSSystemResource(sr.getName(), sysResoureSubDir + "/" + tgtDescriptorFileName);
                                          htmlImport.addImportSucess_ToReport(sr.getType(), sr.getName(), rgInfoStr + sr.getType() + "[name=" + sr.getName() + "]");
                                          SubDeploymentMBean[] var305 = sr.getSubDeployments();
                                          var87 = var305.length;

                                          for(k = 0; k < var87; ++k) {
                                             SubDeploymentMBean subd = var305[k];
                                             subdepJSONObj = null;
                                             jsonSubdepExcludeImport = null;
                                             if (subdepArrayJSONObj instanceof JSONArray) {
                                                for(int j = 0; j < ((JSONArray)subdepArrayJSONObj).length(); ++j) {
                                                   JSONObject arrayElementSubDObj = ((JSONArray)subdepArrayJSONObj).optJSONObject(j);
                                                   if (arrayElementSubDObj != null && subd.getName().equals(arrayElementSubDObj.optString("name"))) {
                                                      jsonSubdepExcludeImport = arrayElementSubDObj.optString("exclude-from-import");
                                                      break;
                                                   }
                                                }
                                             }

                                             if ("true".equals(jsonSubdepExcludeImport)) {
                                                var10000 = domainToPartitionLogger;
                                                DomainToPartitionLogger.logResourceExcludedFromImport("Sub Deployment", subd.getName());
                                                htmlImport.addImportSkipInJson_ToReport(subd.getType(), subd.getName());
                                             }
                                          }

                                          processedJMSSysRes.add((JMSSystemResourceMBean)sr);
                                          JSONObject jmsRSOVJsonObj = null;
                                          if (partitionJsonObject != null) {
                                             Object jdobj = partitionJsonObject.opt("jms-system-resource-override");
                                             if (jdobj instanceof JSONArray) {
                                                for(k = 0; k < ((JSONArray)jdobj).length(); ++k) {
                                                   JSONObject arrayElementOverrideObj = ((JSONArray)jdobj).optJSONObject(k);
                                                   if (sr.getName().equals(arrayElementOverrideObj.optString("name"))) {
                                                      jmsRSOVJsonObj = arrayElementOverrideObj;
                                                      break;
                                                   }
                                                }
                                             }
                                          }

                                          if (jmsRSOVJsonObj != null) {
                                             boolean hasOverride = false;
                                             JMSSystemResourceOverrideMBean jmsSROVB = partitionMBean.createJMSSystemResourceOverride(sr.getName());

                                             try {
                                                Object jfsvrobj = jmsRSOVJsonObj.opt("foreign-server-override");
                                                if (jfsvrobj instanceof JSONArray) {
                                                   for(int fso = 0; fso < ((JSONArray)jfsvrobj).length(); ++fso) {
                                                      JSONObject jfsObj = ((JSONArray)jfsvrobj).optJSONObject(fso);
                                                      String foreignSvrName = jfsObj.optString("name");
                                                      ForeignServerOverrideMBean frSvrOVMB = null;
                                                      Object jfdtsobj = jfsObj.opt("foreign-destination-override");
                                                      if (jfdtsobj instanceof JSONArray) {
                                                         for(int di = 0; di < ((JSONArray)jfdtsobj).length(); ++di) {
                                                            JSONObject jfdObj = ((JSONArray)jfdtsobj).optJSONObject(di);
                                                            String foreignDestName = jfdObj.optString("name");
                                                            String remoteJndiName = jfdObj.optString("remote-jndi-name");
                                                            if (remoteJndiName != null && !"__EXISTING_VALUE__".equals(remoteJndiName)) {
                                                               hasOverride = true;
                                                               if (frSvrOVMB == null) {
                                                                  frSvrOVMB = jmsSROVB.createForeignServer(foreignSvrName);
                                                               }

                                                               ForeignDestinationOverrideMBean frDstOVMB = frSvrOVMB.createForeignDestination(foreignDestName);
                                                               frDstOVMB.setRemoteJNDIName(remoteJndiName);
                                                            }
                                                         }
                                                      }

                                                      Object jfcfsobj = jfsObj.opt("foreign-connection-factory-override");
                                                      if (jfcfsobj instanceof JSONArray) {
                                                         for(int di = 0; di < ((JSONArray)jfcfsobj).length(); ++di) {
                                                            JSONObject jfdObj = ((JSONArray)jfcfsobj).optJSONObject(di);
                                                            ForeignConnectionFactoryOverrideMBean frCFOVMB = null;
                                                            String foreignConFacName = jfdObj.optString("name");
                                                            String remoteJndiName = jfdObj.optString("remote-jndi-name");
                                                            String userName = jfdObj.optString("username");
                                                            String password = jfdObj.optString("password");
                                                            if (remoteJndiName != null && !"__EXISTING_VALUE__".equals(remoteJndiName)) {
                                                               hasOverride = true;
                                                               if (frSvrOVMB == null) {
                                                                  frSvrOVMB = jmsSROVB.createForeignServer(foreignSvrName);
                                                               }

                                                               if (frCFOVMB == null) {
                                                                  frCFOVMB = frSvrOVMB.createForeignConnectionFactory(foreignConFacName);
                                                               }

                                                               frCFOVMB.setRemoteJNDIName(remoteJndiName);
                                                            }

                                                            if (userName != null && !"__EXISTING_VALUE__".equals(userName)) {
                                                               hasOverride = true;
                                                               if (frSvrOVMB == null) {
                                                                  frSvrOVMB = jmsSROVB.createForeignServer(foreignSvrName);
                                                               }

                                                               if (frCFOVMB == null) {
                                                                  frCFOVMB = frSvrOVMB.createForeignConnectionFactory(foreignConFacName);
                                                               }

                                                               frCFOVMB.setUsername(userName);
                                                            }

                                                            if (password != null && !"__EXISTING_VALUE__".equals(password)) {
                                                               hasOverride = true;
                                                               if (frSvrOVMB == null) {
                                                                  frSvrOVMB = jmsSROVB.createForeignServer(foreignSvrName);
                                                               }

                                                               if (frCFOVMB == null) {
                                                                  frCFOVMB = frSvrOVMB.createForeignConnectionFactory(foreignConFacName);
                                                               }

                                                               frCFOVMB.setPassword(password);
                                                            }
                                                         }
                                                      }
                                                   }
                                                }

                                                if (!hasOverride) {
                                                   partitionMBean.destroyJMSSystemResourceOverride(jmsSROVB);
                                                }
                                             } catch (Exception var114) {
                                                if (debugLogger.isDebugEnabled()) {
                                                   debugLogger.debug("MigrationToPartitionManager:migrateToPartition : An exception occurred during the processing of JMS Override MBean - " + var114);
                                                }

                                                partitionMBean.destroyJMSSystemResourceOverride(jmsSROVB);
                                             }
                                          }
                                       } else {
                                          FileUtils.writeToFile(new ByteArrayInputStream(systemResourceDesc.getBytes()), toCreate.toFile());
                                          TargetMBean[] var293 = sr.getTargets();
                                          int var296 = var293.length;

                                          for(int var298 = 0; var298 < var296; ++var298) {
                                             TargetMBean targt = var293[var298];
                                             sr.removeTarget(targt);
                                          }

                                          nsr = (SystemResourceMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(sr.getType(), sr);
                                          ((SystemResourceMBean)nsr).setDescriptorFileName(sysResoureSubDir + "/" + tgtDescriptorFileName);
                                       }

                                       if (debugLogger.isDebugEnabled()) {
                                          debugLogger.debug("MigrationToPartitionManager:migrateToPartition : JMS System Resource " + DescriptorUtils.toString(((SystemResourceMBean)nsr).getResource()));
                                       }
                                    }
                                 }
                              }
                           }

                           this.jmsServersHostingUDDs = new HashSet();
                           this.jmsServersHostingFSs = new HashSet();
                           this.importAppLibBinaries(domain, (ConfigurationMBean)rgtMBean, (ResourceGroupTemplateMBean)rgtMBean, domainMBeanFromDescriptor, partitionArchive, jsonRGObject, tmpDirForArchive, htmlImport);
                           ImportExportHelper.getUploadDirectoryName(domain, (ConfigurationMBean)rgtMBean);
                           Iterator var232 = this.singletonJMSServers.iterator();

                           while(var232.hasNext()) {
                              jmsServerName = (String)var232.next();
                              JMSServerMBean jmsServerMBean = ((ResourceGroupTemplateMBean)rgtMBean).lookupJMSServer(jmsServerName);
                              if (jmsServerMBean != null) {
                                 if (this.jmsServersHostingUDDs.contains(jmsServerName)) {
                                    var10002 = domainToPartitionLogger;
                                    throw new RuntimeException(DomainToPartitionLogger.logJMSServerHostingUDDInvalidLoggable(jmsServerName).getMessage());
                                 }

                                 if (this.jmsServersHostingFSs.contains(jmsServerName)) {
                                    var10002 = domainToPartitionLogger;
                                    throw new RuntimeException(DomainToPartitionLogger.logJMSServerHostingFSInvalidLoggable(jmsServerName).getMessage());
                                 }

                                 if (safAgentStoreNames.containsKey(jmsServerMBean.getPersistentStore().getName())) {
                                    var10002 = domainToPartitionLogger;
                                    throw new RuntimeException(DomainToPartitionLogger.logConflictingStoreLoggable(jmsServerName, jmsServerMBean.getPersistentStore().getName(), (String)safAgentStoreNames.get(jmsServerMBean.getPersistentStore().getName())).getMessage());
                                 }

                                 if (debugLogger.isDebugEnabled()) {
                                    debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Setting the distribution policy to 'Singleton' for the persistence store " + jmsServerMBean.getPersistentStore().getName() + ", as the JMS Server " + jmsServerName + " which references this store is hosting one or more standalone destinations");
                                 }

                                 jmsServerMBean.getPersistentStore().setDistributionPolicy("Singleton");
                                 if ("Off".equals(jmsServerMBean.getPersistentStore().getMigrationPolicy())) {
                                    jmsServerMBean.getPersistentStore().setMigrationPolicy("Always");
                                 }

                                 if (debugLogger.isDebugEnabled()) {
                                    debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Set the distribution policy to 'Singleton' for the persistence store " + jmsServerMBean.getPersistentStore().getName());
                                 }
                              }
                           }

                           var232 = processedJMSSysRes.iterator();

                           JMSSystemResourceMBean jmssr;
                           JMSBean jmsBean;
                           while(var232.hasNext()) {
                              jmssr = (JMSSystemResourceMBean)var232.next();
                              sr = ((ResourceGroupTemplateMBean)rgtMBean).lookupJMSSystemResource(jmssr.getName());
                              if (sr != null) {
                                 jmsBean = sr.getJMSResource();
                                 if (jmsBean != null) {
                                    this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getQueues(), jmssr, (ResourceGroupTemplateMBean)rgtMBean);
                                    this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getTopics(), jmssr, (ResourceGroupTemplateMBean)rgtMBean);
                                 }
                              }
                           }

                           var232 = processedJMSSysRes.iterator();

                           while(true) {
                              int x;
                              TargetMBean[] var276;
                              TargetMBean subTgt;
                              int var282;
                              do {
                                 do {
                                    if (!var232.hasNext()) {
                                       pathServiceObject = jsonRGObject.opt("path-service");
                                       clusterPathServiceMap = new HashMap();
                                       if (pathServiceObject instanceof JSONArray) {
                                          for(i = 0; i < ((JSONArray)pathServiceObject).length(); ++i) {
                                             dbean = null;
                                             jdbcOVJsonObj = null;
                                             JSONObject arrayElementObj = ((JSONArray)pathServiceObject).optJSONObject(i);
                                             PathServiceMBean psMbean = domainMBeanFromDescriptor.lookupPathService(arrayElementObj.optString("name"));
                                             if (psMbean != null) {
                                                String jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                                if ("true".equals(jsonExcludeImport)) {
                                                   var10000 = domainToPartitionLogger;
                                                   DomainToPartitionLogger.logResourceExcludedFromImport("Path Service", psMbean.getName());
                                                   htmlImport.addImportSkipInJson_ToReport(psMbean.getType(), psMbean.getName());
                                                } else {
                                                   ClusterMBean clusterMB = null;
                                                   var276 = psMbean.getTargets();
                                                   x = var276.length;

                                                   for(var282 = 0; var282 < x; ++var282) {
                                                      subTgt = var276[var282];
                                                      psMbean.removeTarget(subTgt);
                                                      if (subTgt instanceof MigratableTargetMBean) {
                                                         clusterMB = ((MigratableTargetMBean)subTgt).getCluster();
                                                      } else if (subTgt instanceof ServerMBean) {
                                                         clusterMB = ((ServerMBean)subTgt).getCluster();
                                                      }
                                                   }

                                                   sMbean = null;
                                                   Object associatedStore;
                                                   if (clusterMB != null) {
                                                      if (clusterPathServiceMap.containsKey(clusterMB.getName())) {
                                                         if (debugLogger.isDebugEnabled()) {
                                                            debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Skipped creating Path Service " + psMbean.getName());
                                                         }

                                                         htmlImport.addImportSkipInJson_ToReport(psMbean.getType(), psMbean.getName());
                                                         continue;
                                                      }

                                                      associatedStore = psMbean.getPersistentStore();
                                                      if (associatedStore != null) {
                                                         if (associatedStore instanceof FileStoreMBean) {
                                                            associatedStore = ((ResourceGroupTemplateMBean)rgtMBean).lookupFileStore(((PersistentStoreMBean)associatedStore).getName());
                                                         } else if (associatedStore instanceof JDBCStoreMBean) {
                                                            associatedStore = ((ResourceGroupTemplateMBean)rgtMBean).lookupJDBCStore(((PersistentStoreMBean)associatedStore).getName());
                                                         }

                                                         if (associatedStore != null) {
                                                            sMbean = associatedStore;
                                                         } else if (psMbean.getPersistentStore() != null) {
                                                            sMbean = (PersistentStoreMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(psMbean.getPersistentStore().getType(), psMbean.getPersistentStore());
                                                         }
                                                      }

                                                      psMbean.setPersistentStore((PersistentStoreMBean)null);
                                                   }

                                                   if (!isHAValidationComplete) {
                                                      for(x = 0; x < rgTargetList.size(); ++x) {
                                                         if (vtClusterMap.containsKey(rgTargetList.get(x))) {
                                                            validateMigrationBasis((DynamicDeploymentMBean)null, (ClusterMBean)vtClusterMap.get(rgTargetList.get(x)), true);
                                                         }
                                                      }

                                                      isHAValidationComplete = true;
                                                   }

                                                   psMbean = (PathServiceMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(psMbean.getType(), psMbean);
                                                   htmlImport.addImportSucess_ToReport(psMbean.getType(), psMbean.getName(), rgInfoStr + psMbean.getType() + "[name=" + psMbean.getName() + "]");
                                                   if (clusterMB != null && !clusterPathServiceMap.containsKey(clusterMB.getName())) {
                                                      clusterPathServiceMap.put(clusterMB.getName(), psMbean);
                                                      psMbean.setPersistentStore((PersistentStoreMBean)sMbean);
                                                   }

                                                   associatedStore = psMbean.getPersistentStore();
                                                   if (associatedStore != null) {
                                                      var10000 = domainToPartitionLogger;
                                                      DomainToPartitionLogger.logRequiredPoliciesForPathService(((PersistentStoreMBean)associatedStore).getName(), psMbean.getName());
                                                      ((PersistentStoreMBean)associatedStore).setDistributionPolicy("Singleton");
                                                      ((PersistentStoreMBean)associatedStore).setMigrationPolicy("Always");
                                                   } else {
                                                      var10000 = domainToPartitionLogger;
                                                      DomainToPartitionLogger.logNewStoreForPathService(psMbean.getName(), psMbean.getName() + "-FileStore ", ((ResourceGroupTemplateMBean)rgtMBean).getName());
                                                      associatedStore = ((ResourceGroupTemplateMBean)rgtMBean).createFileStore(psMbean.getName() + "-FileStore");
                                                      htmlImport.addImportSucess_ToReport(((PersistentStoreMBean)associatedStore).getType(), "-", rgInfoStr + psMbean.getType() + "[name=" + ((PersistentStoreMBean)associatedStore).getName() + "]");
                                                      ((PersistentStoreMBean)associatedStore).setDistributionPolicy("Singleton");
                                                      ((PersistentStoreMBean)associatedStore).setMigrationPolicy("Always");
                                                   }

                                                   psMbean.setPersistentStore((PersistentStoreMBean)associatedStore);
                                                }
                                             }
                                          }
                                       }

                                       Object mailSessionObject = jsonRGObject.opt("mail-session");
                                       if (mailSessionObject instanceof JSONArray) {
                                          for(i = 0; i < ((JSONArray)mailSessionObject).length(); ++i) {
                                             jdbcOVJsonObj = null;
                                             subdepArrayJSONObj = null;
                                             JSONObject arrayElementObj = ((JSONArray)mailSessionObject).optJSONObject(i);
                                             MailSessionMBean mailSessMB = domainMBeanFromDescriptor.lookupMailSession(arrayElementObj.optString("name"));
                                             if (mailSessMB != null) {
                                                jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
                                                if ("true".equals(jsonExcludeImport)) {
                                                   var10000 = domainToPartitionLogger;
                                                   DomainToPartitionLogger.logResourceExcludedFromImport("Mail Session", mailSessMB.getName());
                                                   htmlImport.addImportSkipInJson_ToReport(mailSessMB.getType(), mailSessMB.getName());
                                                } else {
                                                   MailSessionMBean nMailSeBean = (MailSessionMBean)((ResourceGroupTemplateMBean)rgtMBean).createChildCopy(mailSessMB.getType(), mailSessMB);
                                                   htmlImport.addImportSucess_ToReport(mailSessMB.getType(), mailSessMB.getName(), rgInfoStr + mailSessMB.getType() + "[name=" + mailSessMB.getName() + "]");
                                                   if (debugLogger.isDebugEnabled() && nMailSeBean != null) {
                                                      debugLogger.debug("MigrationToPartitionManager:migrateToPartition : Created Mail Session " + nMailSeBean.getName());
                                                   }
                                                }
                                             }
                                          }
                                       }

                                       if (createRGT) {
                                          toImportDomain.createChildCopy(((ResourceGroupTemplateMBean)rgtMBean).getType(), (DescriptorBean)rgtMBean);
                                          htmlImport.addRgt_ToReport(((ResourceGroupTemplateMBean)rgtMBean).getName());
                                       }
                                       continue label3362;
                                    }

                                    jmssr = (JMSSystemResourceMBean)var232.next();
                                    sr = ((ResourceGroupTemplateMBean)rgtMBean).lookupJMSSystemResource(jmssr.getName());
                                 } while(sr == null);

                                 jmsBean = sr.getJMSResource();
                              } while(jmsBean == null);

                              this.updateSubdeployments(jmsBean, jmssr, (ResourceGroupTemplateMBean)rgtMBean);
                              SubDeploymentMBean[] var273 = jmssr.getSubDeployments();
                              var272 = var273.length;

                              SubDeploymentMBean subd;
                              for(j = 0; j < var272; ++j) {
                                 subd = var273[j];
                                 var276 = subd.getTargets();
                                 x = var276.length;

                                 for(var282 = 0; var282 < x; ++var282) {
                                    subTgt = var276[var282];
                                    if (!(subTgt instanceof MigratableTargetMBean) && !(subTgt instanceof ServerMBean) && !(subTgt instanceof ClusterMBean)) {
                                       if (subTgt instanceof JMSServerMBean) {
                                          TargetMBean[] var285 = ((JMSServerMBean)subTgt).getTargets();
                                          int var288 = var285.length;

                                          for(int var292 = 0; var292 < var288; ++var292) {
                                             TargetMBean aJMSServerTarget = var285[var292];
                                             ClusterMBean cluster = null;
                                             if (aJMSServerTarget instanceof ServerMBean) {
                                                cluster = ((ServerMBean)aJMSServerTarget).getCluster();
                                             } else if (aJMSServerTarget instanceof MigratableTargetMBean) {
                                                cluster = ((MigratableTargetMBean)aJMSServerTarget).getCluster();
                                             }

                                             if (cluster != null) {
                                                var10000 = domainToPartitionLogger;
                                                DomainToPartitionLogger.logServerTargetedSubDeployment(subd.getName(), jmssr.getName(), subTgt.getName(), subTgt.getType(), cluster.getName(), aJMSServerTarget.getName(), aJMSServerTarget.getType());
                                             }
                                          }
                                       }
                                    } else {
                                       var10000 = domainToPartitionLogger;
                                       DomainToPartitionLogger.logInvalidSubDeploymentTarget(subd.getName(), jmssr.getName(), subTgt.getName(), subTgt.getType());
                                       subd.removeTarget(subTgt);
                                    }
                                 }
                              }

                              var273 = jmssr.getSubDeployments();
                              var272 = var273.length;

                              for(j = 0; j < var272; ++j) {
                                 subd = var273[j];
                                 if (subd.getTargets() == null || subd.getTargets().length == 0) {
                                    var10000 = domainToPartitionLogger;
                                    DomainToPartitionLogger.logSubDeploymentWithoutTarget(subd.getName(), jmssr.getName());
                                 }

                                 if (subd.getTargets() == null || subd.getTargets().length <= 1) {
                                    sr.createChildCopy(subd.getType(), subd);
                                    htmlImport.addImportSucess_ToReport(subd.getType(), subd.getName(), rgInfoStr + subd.getType() + "[name=" + subd.getName() + "]");
                                 }
                              }
                           }
                        }
                     }

                     Iterator var143 = vtMap.values().iterator();

                     while(var143.hasNext()) {
                        VirtualTargetMBean vtb = (VirtualTargetMBean)var143.next();
                        toImportDomain.createChildCopy(vtb.getType(), vtb);
                     }

                     toImportDomain.createChildCopy(partitionMBean.getType(), partitionMBean);
                     this.activate(manager);

                     try {
                        htmlImport.dumpHtmlFile();
                     } catch (Exception var111) {
                        var10000 = domainToPartitionLogger;
                        DomainToPartitionLogger.logFailedToCreateImportReport();
                     }
                     break label2815;
                  }

                  ze = (ZipEntry)zes.nextElement();
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("MigrationToPartitionManager:migrateToPartition : zip entry " + ze);
                  }

                  ins = partitionArchive.getInputStream(ze);
                  if (ze.getName().equals("config/migrate-config.xml")) {
                     newConfig = ImportExportHelper.getStringFromInputStream(ins);
                     break;
                  }

                  if (ze.getName().endsWith("-attributes.json")) {
                     jsonAttributesString = ImportExportHelper.getStringFromInputStream(ins);
                     File changeAttributeFile = new File(archiveFileNamePath + File.separator + ze.getName());
                     if (changeAttributeFile.exists()) {
                        jsonAttributesString = new String(Files.readAllBytes(changeAttributeFile.toPath()));
                     }
                     break;
                  }

                  if (ze.getName().contains("archiveSecret")) {
                     secretKeyFile = File.createTempFile("archiveSecret", "");
                     Files.copy(ins, secretKeyFile.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                     break;
                  }

                  if (!ze.getName().startsWith("META-INF/")) {
                     Path toCreate = Paths.get(tmpDirForArchive, ImportExportHelper.removeRootFromPath(ze.getName()));
                     FileUtils.writeToFile(ins, toCreate.toFile());
                     break;
                  }
               }

               ins.close();
            }
         } catch (BeanAlreadyExistsException var115) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(var115.getLocalizedMessage());
               var115.printStackTrace();
            }

            this.undoUnactivatedChanges(manager);
            this.cleanUpOnFailure((String)uploadDirectoryName, (String)newPartitionDir);
            throw var115;
         } catch (Exception var116) {
            this.undoUnactivatedChanges(manager);
            this.cleanUpOnFailure((String)uploadDirectoryName, (String)newPartitionDir);
            throw var116;
         } finally {
            partitionArchive.close();
            if (secretKeyFile != null && secretKeyFile.exists()) {
               secretKeyFile.delete();
            }

            if (tmpDirForArchive != null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("MigrationToPartitionManager:migrateToPartition : removed/cleanup tmp archive directory : " + tmpDirForArchive);
               }

               FileUtils.remove(new File(tmpDirForArchive));
            }

         }

         var10000 = domainToPartitionLogger;
         DomainToPartitionLogger.logImportCompleted();
         return partitionMBean;
      }
   }

   private boolean activate(ConfigurationManagerMBean manager) throws Exception {
      try {
         manager.save();
         ActivationTaskMBean actTsk = manager.activate(-1L);
         actTsk.waitForTaskCompletion();
         if (actTsk.getError() != null) {
            DomainToPartitionLogger var10000 = domainToPartitionLogger;
            DomainToPartitionLogger.logActivationFailed(actTsk.getError().getMessage());
            this.undoUnactivatedChanges(manager);
            throw actTsk.getError();
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("MigrationToPartitionManager:activate : Activation of importPartition completed, and the state is " + actTsk.getState());
            }

            return actTsk.getState() == 4;
         }
      } catch (Exception var3) {
         this.undoUnactivatedChanges(manager);
         throw var3;
      }
   }

   private void undoUnactivatedChanges(ConfigurationManagerMBean manager) throws Exception {
      try {
         manager.undoUnactivatedChanges();
      } catch (Exception var6) {
         DomainToPartitionLogger var10000 = domainToPartitionLogger;
         DomainToPartitionLogger.logUnableToUndoUnActivatedChanges(var6.getMessage());
      } finally {
         manager.cancelEdit();
      }

   }

   public void cleanUpOnFailure(String uploadDirectoryName, String newPartitionDir) {
      if (uploadDirectoryName != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("MigrationToPartitionManager:cleanUpOnFailure : removed the application upload directory: " + uploadDirectoryName);
         }

         FileUtils.remove(new File(uploadDirectoryName));
      }

      if (newPartitionDir != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("MigrationToPartitionManager:cleanUpOnFailure : removed the application upload directory: " + newPartitionDir);
         }

         FileUtils.remove(new File(newPartitionDir));
      }

   }

   private void fillSubDeployTargetsMap(Map subDeployTargets, Map moduleSubDeployments, String moduleNameFromJSON, JSONArray arrayElementObjSubd) {
      if (arrayElementObjSubd != null) {
         for(int k = 0; k < arrayElementObjSubd.length(); ++k) {
            JSONObject subdArrayElementObj = arrayElementObjSubd.optJSONObject(k);
            String subdNameFromJSON = subdArrayElementObj.optString("name");
            ((List)moduleSubDeployments.get(moduleNameFromJSON)).add(subdNameFromJSON);
            StringTokenizer subDepTargetsList = null;
            String subDepTargets = subdArrayElementObj.optString("targets").trim();
            subDeployTargets.put(subdNameFromJSON, new ArrayList());
            if (subDepTargets != null) {
               subDepTargetsList = new StringTokenizer(subDepTargets, ",");

               while(subDepTargetsList != null && subDepTargetsList.hasMoreTokens()) {
                  ((List)subDeployTargets.get(subdNameFromJSON)).add(subDepTargetsList.nextToken().trim());
               }
            }
         }
      }

   }

   private void handleOverriddenSubDepTargetAppScope(DomainMBean domainMBeanFromDescriptor, SubDeploymentMBean subDeploymentMBean, List subDeploymentsStandalone, List subDeploymentsUDDs, List subDeploymentsFSs, List jsonTargetList) throws Exception {
      String subDeploymentName = subDeploymentMBean.getName();
      TargetMBean[] var8 = subDeploymentMBean.getTargets();
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         TargetMBean innerTarget = var8[var10];
         subDeploymentMBean.removeTarget(innerTarget);
      }

      for(int m = 0; m < jsonTargetList.size(); ++m) {
         TargetMBean jsontarget = domainMBeanFromDescriptor.lookupJMSServer((String)jsonTargetList.get(m));
         if (jsontarget == null) {
            jsontarget = domainMBeanFromDescriptor.lookupSAFAgent((String)jsonTargetList.get(m));
         }

         if (jsontarget != null) {
            subDeploymentMBean.addTarget((TargetMBean)jsontarget);
            String jsonTargetName = ((TargetMBean)jsontarget).getName();
            if (subDeploymentsStandalone.contains(subDeploymentName)) {
               this.singletonJMSServers.add(jsonTargetName);
            }

            if (subDeploymentsUDDs.contains(subDeploymentName)) {
               this.jmsServersHostingUDDs.add(jsonTargetName);
            }

            if (subDeploymentsFSs.contains(subDeploymentName)) {
               this.jmsServersHostingFSs.add(jsonTargetName);
            }
         } else if (!((String)jsonTargetList.get(m)).isEmpty()) {
            DomainToPartitionLogger var10002 = domainToPartitionLogger;
            throw new RuntimeException(DomainToPartitionLogger.logOverridenSubdepTargetInvalidLoggable((String)jsonTargetList.get(m)).getMessage());
         }
      }

   }

   private void handleExistingSubDepTargetAppScope(SubDeploymentMBean subDeploymentMBean, List subDeploymentsStandalone, List subDeploymentsUDDs, List subDeploymentsFSs, List jsonTargetList) throws Exception {
      String subDeploymentName = subDeploymentMBean.getName();
      TargetMBean[] var7 = subDeploymentMBean.getTargets();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         TargetMBean innerTarget = var7[var9];
         String innerTargetName = innerTarget.getName();
         if (subDeploymentsStandalone.contains(subDeploymentName)) {
            this.singletonJMSServers.add(innerTargetName);
         }

         if (subDeploymentsUDDs.contains(subDeploymentName)) {
            this.jmsServersHostingUDDs.add(innerTargetName);
         }

         if (subDeploymentsFSs.contains(subDeploymentName)) {
            this.jmsServersHostingFSs.add(innerTargetName);
         }

         if (!(innerTarget instanceof JMSServerMBean) && !(innerTarget instanceof SAFAgentMBean)) {
            subDeploymentMBean.removeTarget(innerTarget);
         }
      }

   }

   private void importAppLibBinaries(DomainMBean domain, ConfigurationMBean parent, ResourceGroupTemplateMBean rgtMBean, DomainMBean domainMBeanFromDescriptor, ZipFile partitionArchive, JSONObject readJsonRoot, String tmpDirForArchive, MigrationReportHandler htmlImport) throws Exception {
      Object readLibraryDeploymentObject = readJsonRoot.opt("library");
      if (readLibraryDeploymentObject instanceof JSONArray) {
         this.processDeployment(domain, parent, rgtMBean, domainMBeanFromDescriptor, readLibraryDeploymentObject, partitionArchive, tmpDirForArchive, true, htmlImport);
      }

      Object readAppDeploymentObject = readJsonRoot.opt("app-deployment");
      if (readAppDeploymentObject instanceof JSONArray) {
         this.processDeployment(domain, parent, rgtMBean, domainMBeanFromDescriptor, readAppDeploymentObject, partitionArchive, tmpDirForArchive, false, htmlImport);
      }

   }

   private void processDeployment(DomainMBean domain, ConfigurationMBean parent, ResourceGroupTemplateMBean rgtMBean, DomainMBean domainMBeanFromDescriptor, Object readAppDeploymentObject, ZipFile partitionArchive, String tmpDirForArchive, boolean isLibrary, MigrationReportHandler htmlImport) throws Exception {
      for(int i = 0; i < ((JSONArray)readAppDeploymentObject).length(); ++i) {
         JSONObject appDepJSONObj = null;
         String jsonSrcPath = null;
         String jsonPlanPath = null;
         String jsonExcludeImport = null;
         String jsonRequireExactVersion = null;
         JSONObject arrayElementObj = ((JSONArray)readAppDeploymentObject).optJSONObject(i);
         AppDeploymentMBean appDeploymentMBean = null;
         String appNameFromJSON = arrayElementObj.optString("name");
         if (isLibrary) {
            appDeploymentMBean = domainMBeanFromDescriptor.lookupLibrary(appNameFromJSON);
         } else {
            appDeploymentMBean = domainMBeanFromDescriptor.lookupAppDeployment(appNameFromJSON);
         }

         if (appDeploymentMBean == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("MigrationToPartitionManager:processDeployment : appDeploymentMBean is null for name " + appNameFromJSON);
            }
         } else if (((AbstractDescriptorBean)appDeploymentMBean)._isTransient()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("MigrationToPartitionManager:processDeployment : appDeploymentMBean is transient " + appNameFromJSON);
            }
         } else {
            jsonExcludeImport = arrayElementObj.optString("exclude-from-import");
            jsonSrcPath = arrayElementObj.optString("source-path");
            jsonPlanPath = arrayElementObj.optString("plan-path");
            jsonRequireExactVersion = arrayElementObj.optString("require-exact-version");
            String rgInfoStr = "Resource Group[name=" + parent.getName() + "] /";
            if (parent instanceof ResourceGroupTemplateMBean) {
               rgInfoStr = "Resource GroupTemplate[name=" + parent.getName() + "] /";
            }

            String appName = ((AppDeploymentMBean)appDeploymentMBean).getName();
            DomainToPartitionLogger var10000;
            if ("true".equals(jsonExcludeImport)) {
               if (isLibrary) {
                  var10000 = domainToPartitionLogger;
                  DomainToPartitionLogger.logResourceExcludedFromImport("Library", appName);
               } else {
                  var10000 = domainToPartitionLogger;
                  DomainToPartitionLogger.logResourceExcludedFromImport("Application Deployment ", appName);
               }

               htmlImport.addImportSkipInJson_ToReport(((AppDeploymentMBean)appDeploymentMBean).getType(), ((AppDeploymentMBean)appDeploymentMBean).getName());
               return;
            }

            TargetMBean[] var21 = ((AppDeploymentMBean)appDeploymentMBean).getTargets();
            int var22 = var21.length;

            for(int var23 = 0; var23 < var22; ++var23) {
               TargetMBean targt = var21[var23];
               ((AppDeploymentMBean)appDeploymentMBean).removeTarget(targt);
            }

            String appSrcPath = ((AppDeploymentMBean)appDeploymentMBean).getSourcePath();
            AppDeploymentMBean napp = null;
            String appPlanDir;
            JSONObject localPlanPath;
            String stagingMode;
            ZipEntry ze;
            File fromFile;
            String localPlanPath;
            int tlidx;
            if (isLibrary) {
               LibraryMBean nlib;
               if (jsonSrcPath != null && jsonSrcPath.length() > 0 && Files.exists(Paths.get(jsonSrcPath), new LinkOption[0]) && "false".equals(jsonRequireExactVersion)) {
                  nlib = null;
                  Attributes attrs = getManifestAttributes(new File(jsonSrcPath));
                  stagingMode = this.getLibraryId(attrs);
                  LibraryMBean nlib = rgtMBean.createLibrary(stagingMode, jsonSrcPath);
                  napp = nlib;
                  var10000 = domainToPartitionLogger;
                  DomainToPartitionLogger.logUsedTargetLibraryAvailableLoggable(stagingMode, jsonSrcPath);
               } else {
                  nlib = (LibraryMBean)parent.createChildCopy(((AppDeploymentMBean)appDeploymentMBean).getType(), (DescriptorBean)appDeploymentMBean);
                  napp = nlib;
               }

               htmlImport.addImportSucess_ToReport(((AppDeploymentMBean)napp).getType(), ((AppDeploymentMBean)napp).getName(), rgInfoStr + ((AppDeploymentMBean)napp).getName() + "[name=" + ((AppDeploymentMBean)napp).getName() + "]");
            } else {
               JSONArray arrayElementObjSubd = (JSONArray)arrayElementObj.opt("jms-modules");
               boolean isJMSModule = false;
               ArrayList jmsBeans = new ArrayList();
               HashMap moduleSubDeployments = new HashMap();
               HashMap subDeployTargets = new HashMap();
               if (appSrcPath != null && appSrcPath.length() > 0) {
                  appPlanDir = "deployment/" + appName;
                  if (appSrcPath.endsWith("-jms.xml")) {
                     arrayElementObjSubd = (JSONArray)arrayElementObj.opt("sub-module-targets");
                     isJMSModule = true;
                     String jmsModuleAsString = this.readFile(new File(tmpDirForArchive + "/" + appPlanDir + "/" + appSrcPath));
                     jmsBeans.add(this.getJMSBeanFromString(jmsModuleAsString));
                     localPlanPath = arrayElementObj.optString("name");
                     moduleSubDeployments.put(localPlanPath, new ArrayList());
                     this.fillSubDeployTargetsMap(subDeployTargets, moduleSubDeployments, localPlanPath, arrayElementObjSubd);
                  } else if (arrayElementObjSubd != null) {
                     String jmsModuleAsString;
                     for(int j = 0; j < arrayElementObjSubd.length(); ++j) {
                        localPlanPath = arrayElementObjSubd.optJSONObject(j);
                        jmsModuleAsString = localPlanPath.optString("name");
                        moduleSubDeployments.put(jmsModuleAsString, new ArrayList());
                        JSONArray arrayElementObjInnerSubd = (JSONArray)localPlanPath.opt("sub-module-targets");
                        this.fillSubDeployTargetsMap(subDeployTargets, moduleSubDeployments, jmsModuleAsString, arrayElementObjInnerSubd);
                     }

                     ze = ImportExportHelper.getZipEntry(appPlanDir + "/" + appSrcPath, partitionArchive);
                     fromFile = Paths.get(tmpDirForArchive, ze.getName()).toFile();
                     jmsModuleAsString = null;
                     new BasicDescriptorManager(DescriptorHelper.class.getClassLoader(), true, (SecurityService)null);
                     if (!isJMSModule && fromFile.isFile()) {
                        VirtualJarFile vjf = null;

                        try {
                           ApplicationFileManager afm = ApplicationFileManager.newInstance(fromFile);
                           vjf = afm.getVirtualJarFile();
                           Iterator entries = vjf.entries();
                           int noOfJMSModules = 0;

                           while(entries.hasNext()) {
                              JarEntry o = (JarEntry)entries.next();
                              if (o.getName().endsWith("-jms.xml")) {
                                 isJMSModule = true;
                                 InputStream is = vjf.getInputStream(o);
                                 File tempJMSFile = File.createTempFile("temp-jms.xml", "");
                                 StreamUtils.writeTo(is, new FileOutputStream(tempJMSFile.getCanonicalPath()));
                                 jmsModuleAsString = this.readFile(new File(tempJMSFile.getCanonicalPath()));
                                 jmsBeans.add(this.getJMSBeanFromString(jmsModuleAsString));
                                 ++noOfJMSModules;
                              }
                           }
                        } catch (IOException var50) {
                        } finally {
                           if (vjf != null) {
                              try {
                                 vjf.close();
                              } catch (IOException var48) {
                              }
                           }

                        }
                     }
                  }
               }

               List subDeploymentsStandalone = new ArrayList();
               List subDeploymentsUDDs = new ArrayList();
               List subDeploymentsFSs = new ArrayList();
               if (!jmsBeans.isEmpty()) {
                  Iterator var77 = jmsBeans.iterator();

                  label916:
                  while(true) {
                     if (!var77.hasNext()) {
                        SubDeploymentMBean[] var80;
                        int var81;
                        SubDeploymentMBean subDeploymentMBean;
                        String subDeploymentName;
                        if (appSrcPath.endsWith("-jms.xml")) {
                           var80 = ((AppDeploymentMBean)appDeploymentMBean).getSubDeployments();
                           var81 = var80.length;
                           tlidx = 0;

                           while(true) {
                              if (tlidx >= var81) {
                                 break label916;
                              }

                              subDeploymentMBean = var80[tlidx];
                              subDeploymentName = subDeploymentMBean.getName();
                              ArrayList jsonTargetList = (ArrayList)subDeployTargets.get(subDeploymentName);
                              if (jsonTargetList != null && jsonTargetList.size() == 1 && "__EXISTING_VALUE__".equals(jsonTargetList.get(0))) {
                                 this.handleExistingSubDepTargetAppScope(subDeploymentMBean, subDeploymentsStandalone, subDeploymentsUDDs, subDeploymentsFSs, jsonTargetList);
                              } else if (jsonTargetList != null) {
                                 this.handleOverriddenSubDepTargetAppScope(domainMBeanFromDescriptor, subDeploymentMBean, subDeploymentsStandalone, subDeploymentsUDDs, subDeploymentsFSs, jsonTargetList);
                              }

                              ++tlidx;
                           }
                        }

                        var80 = ((AppDeploymentMBean)appDeploymentMBean).getSubDeployments();
                        var81 = var80.length;
                        tlidx = 0;

                        while(true) {
                           if (tlidx >= var81) {
                              break label916;
                           }

                           subDeploymentMBean = var80[tlidx];
                           subDeploymentName = subDeploymentMBean.getName();
                           SubDeploymentMBean[] var100 = subDeploymentMBean.getSubDeployments();
                           int var103 = var100.length;

                           for(int var101 = 0; var101 < var103; ++var101) {
                              SubDeploymentMBean innerSubDeployment = var100[var101];
                              String sName = innerSubDeployment.getName();
                              ArrayList jsonTargetList = (ArrayList)subDeployTargets.get(sName);
                              if (jsonTargetList != null && jsonTargetList.size() == 1 && "__EXISTING_VALUE__".equals(jsonTargetList.get(0))) {
                                 this.handleExistingSubDepTargetAppScope(innerSubDeployment, subDeploymentsStandalone, subDeploymentsUDDs, subDeploymentsFSs, jsonTargetList);
                              } else if (jsonTargetList != null) {
                                 this.handleOverriddenSubDepTargetAppScope(domainMBeanFromDescriptor, innerSubDeployment, subDeploymentsStandalone, subDeploymentsUDDs, subDeploymentsFSs, jsonTargetList);
                              }
                           }

                           ++tlidx;
                        }
                     }

                     JMSBean jmsBean = (JMSBean)var77.next();
                     QueueBean[] var84 = jmsBean.getQueues();
                     int var85 = var84.length;

                     int var89;
                     String subDepName;
                     for(var89 = 0; var89 < var85; ++var89) {
                        QueueBean queueBean = var84[var89];
                        subDepName = queueBean.getSubDeploymentName();
                        if (subDepName != null && !"".equals(subDepName)) {
                           subDeploymentsStandalone.add(subDepName);
                        }
                     }

                     TopicBean[] var86 = jmsBean.getTopics();
                     var85 = var86.length;

                     for(var89 = 0; var89 < var85; ++var89) {
                        TopicBean topicBean = var86[var89];
                        subDepName = topicBean.getSubDeploymentName();
                        if (!topicBean.isDefaultTargetingEnabled() && subDepName != null && !"".equals(subDepName)) {
                           subDeploymentsStandalone.add(subDepName);
                        }
                     }

                     UniformDistributedQueueBean[] var87 = jmsBean.getUniformDistributedQueues();
                     var85 = var87.length;

                     for(var89 = 0; var89 < var85; ++var89) {
                        UniformDistributedQueueBean udQueueBean = var87[var89];
                        subDepName = udQueueBean.getSubDeploymentName();
                        if (!udQueueBean.isDefaultTargetingEnabled() && subDepName != null && !"".equals(subDepName)) {
                           subDeploymentsUDDs.add(subDepName);
                        }
                     }

                     UniformDistributedTopicBean[] var88 = jmsBean.getUniformDistributedTopics();
                     var85 = var88.length;

                     for(var89 = 0; var89 < var85; ++var89) {
                        UniformDistributedTopicBean udTopicBean = var88[var89];
                        subDepName = udTopicBean.getSubDeploymentName();
                        if (!udTopicBean.isDefaultTargetingEnabled() && subDepName != null && !"".equals(subDepName)) {
                           subDeploymentsUDDs.add(subDepName);
                        }
                     }

                     ForeignServerBean[] var90 = jmsBean.getForeignServers();
                     var85 = var90.length;

                     for(var89 = 0; var89 < var85; ++var89) {
                        ForeignServerBean fsBean = var90[var89];
                        subDepName = fsBean.getSubDeploymentName();
                        if (!fsBean.isDefaultTargetingEnabled() && subDepName != null && !"".equals(subDepName)) {
                           subDeploymentsFSs.add(subDepName);
                        }
                     }

                     SAFImportedDestinationsBean[] var91 = jmsBean.getSAFImportedDestinations();
                     var85 = var91.length;

                     for(var89 = 0; var89 < var85; ++var89) {
                        SAFImportedDestinationsBean var106 = var91[var89];
                     }

                     this.updateSubdeployments(jmsBean, (BasicDeploymentMBean)appDeploymentMBean, rgtMBean);
                     this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getQueues(), (BasicDeploymentMBean)appDeploymentMBean, rgtMBean);
                     this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getTopics(), (BasicDeploymentMBean)appDeploymentMBean, rgtMBean);
                  }
               }

               napp = (AppDeploymentMBean)parent.createChildCopy(((AppDeploymentMBean)appDeploymentMBean).getType(), (DescriptorBean)appDeploymentMBean);
               htmlImport.addImportSucess_ToReport(((AppDeploymentMBean)appDeploymentMBean).getType(), ((AppDeploymentMBean)appDeploymentMBean).getName(), rgInfoStr + ((AppDeploymentMBean)appDeploymentMBean).getName() + "[name=" + ((AppDeploymentMBean)appDeploymentMBean).getName() + "]");
            }

            String zipAppDir = (isLibrary ? "library/" : "deployment/") + appName;
            stagingMode = ((AppDeploymentMBean)appDeploymentMBean).getStagingMode();
            if (appSrcPath != null && appSrcPath.length() > 0) {
               ZipEntry appZipEntry = ImportExportHelper.getZipEntry(zipAppDir + "/" + appSrcPath, partitionArchive);
               DomainToPartitionLogger var10002;
               if (appZipEntry != null) {
                  File toFile = ImportExportHelper.getAppFileToWrite(domain, parent, appSrcPath);
                  if (toFile.exists()) {
                     var10002 = domainToPartitionLogger;
                     throw new ManagementException(DomainToPartitionLogger.logDestinationAppDirExistsLoggable(((AppDeploymentMBean)appDeploymentMBean).getName(), toFile.getCanonicalPath()).getMessage());
                  }

                  File fromFile = Paths.get(tmpDirForArchive, appZipEntry.getName()).toFile();
                  if (!fromFile.exists()) {
                     var10002 = domainToPartitionLogger;
                     throw new ManagementException(DomainToPartitionLogger.logAppDeploymentPLanNotThereLoggable(appZipEntry.getName()).getMessage());
                  }

                  FileUtils.copyPreservePermissions(fromFile, toFile);
                  if (isLibrary && jsonSrcPath != null && jsonSrcPath.length() > 0) {
                     if (Files.exists(Paths.get(jsonSrcPath), new LinkOption[0]) && "false".equals(jsonRequireExactVersion)) {
                        toFile.delete();
                     } else {
                        ((AppDeploymentMBean)napp).setSourcePath(toFile.getCanonicalPath());
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("MigrationToPartitionManager:processDeployment : The library " + ((AppDeploymentMBean)appDeploymentMBean).getName() + " skipped as the JSON source-path does not exist at " + jsonSrcPath);
                        }
                     }
                  } else {
                     ((AppDeploymentMBean)napp).setSourcePath(toFile.getCanonicalPath());
                  }

                  if (!isLibrary || stagingMode != null) {
                     ((AppDeploymentMBean)napp).setStagingMode("stage");
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("MigrationToPartitionManager:processDeployment : bits included srcPath" + ((AppDeploymentMBean)napp).getSourcePath());
                  }
               } else {
                  Enumeration zes = partitionArchive.entries();
                  boolean foundAppEntry = false;
                  appPlanDir = null;

                  while(zes.hasMoreElements()) {
                     ze = (ZipEntry)zes.nextElement();
                     if (ze.getName().startsWith(zipAppDir + "/" + appSrcPath)) {
                        foundAppEntry = true;
                        localPlanPath = ze.getName().substring((zipAppDir + "/").length());
                        File toFile = ImportExportHelper.getAppFileToWrite(domain, parent, localPlanPath);
                        if (toFile.exists()) {
                           var10002 = domainToPartitionLogger;
                           throw new ManagementException(DomainToPartitionLogger.logDestinationAppDirExistsLoggable(((AppDeploymentMBean)appDeploymentMBean).getName(), toFile.getCanonicalPath()).getMessage());
                        }

                        File fromFile = Paths.get(tmpDirForArchive, ze.getName()).toFile();
                        if (!fromFile.exists()) {
                           var10002 = domainToPartitionLogger;
                           throw new ManagementException(DomainToPartitionLogger.logAppNotThereLoggable(ze.getName()).getMessage());
                        }

                        FileUtils.copyPreservePermissions(fromFile, toFile);
                        if (appPlanDir == null) {
                           appPlanDir = toFile.getCanonicalPath();
                           tlidx = appPlanDir.indexOf("/" + appSrcPath);
                           if (tlidx < 0) {
                              tlidx = appPlanDir.indexOf(appSrcPath);
                           }

                           if (tlidx > 0) {
                              appPlanDir = appPlanDir.substring(0, tlidx) + "/" + appSrcPath;
                           }
                        }
                     }
                  }

                  if (appPlanDir != null) {
                     ((AppDeploymentMBean)napp).setSourcePath(appPlanDir);
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("MigrationToPartitionManager:processDeployment : no bits/dir in archive.");
                     }

                     boolean useJsonSrc = false;
                     localPlanPath = "";
                     if (jsonSrcPath != null && jsonSrcPath.length() > 0) {
                        if (Files.exists(Paths.get(jsonSrcPath), new LinkOption[0])) {
                           ((AppDeploymentMBean)napp).setSourcePath(jsonSrcPath);
                           useJsonSrc = true;
                        } else if (debugLogger.isDebugEnabled()) {
                           localPlanPath = " JSON source-path: " + jsonSrcPath + " does not exist!";
                           debugLogger.debug("MigrationToPartitionManager:processDeployment : " + localPlanPath);
                        }
                     }

                     if (!useJsonSrc) {
                        if (!Files.exists(Paths.get(appSrcPath), new LinkOption[0])) {
                           localPlanPath = "The " + ((AppDeploymentMBean)appDeploymentMBean).getName() + localPlanPath + " AppSource " + appSrcPath + " does not exists! Stage: " + stagingMode;
                           throw new ManagementException(localPlanPath);
                        }

                        ((AppDeploymentMBean)napp).setSourcePath(appSrcPath);
                     }
                  }

                  if (!isLibrary || stagingMode != null) {
                     if (!foundAppEntry) {
                        ((AppDeploymentMBean)napp).setStagingMode(stagingMode == "stage" ? "external_stage" : stagingMode);
                     } else {
                        ((AppDeploymentMBean)napp).setStagingMode("stage");
                     }
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("MigrationToPartitionManager:processDeployment : srcPath " + ((AppDeploymentMBean)napp).getSourcePath());
                  }
               }

               String appPlanPath = ((AppDeploymentMBean)appDeploymentMBean).getPlanPath();
               ZipEntry planZipEntry = appPlanPath != null ? ImportExportHelper.getZipEntry(zipAppDir + "/" + appPlanPath, partitionArchive) : null;
               File fromFile;
               if (jsonPlanPath != null && jsonPlanPath.length() > 0 && Files.exists(Paths.get(jsonPlanPath), new LinkOption[0])) {
                  ((AppDeploymentMBean)napp).setPlanPath(jsonPlanPath);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("MigrationToPartitionManager:processDeployment : planPath from JSON:  " + jsonPlanPath + " , " + ((AppDeploymentMBean)napp).getPlanPath());
                  }
               } else if (planZipEntry != null) {
                  File toFile = ImportExportHelper.getAppFileToWrite(domain, parent, appPlanPath);
                  if (toFile.exists()) {
                     var10002 = domainToPartitionLogger;
                     throw new ManagementException(DomainToPartitionLogger.logDestinationAppPlanExistsLoggable(((AppDeploymentMBean)appDeploymentMBean).getName(), toFile.getCanonicalPath()).getMessage());
                  }

                  fromFile = Paths.get(tmpDirForArchive, planZipEntry.getName()).toFile();
                  FileUtils.copyPreservePermissions(fromFile, toFile);
                  ((AppDeploymentMBean)napp).setPlanPath(toFile.getCanonicalPath());
                  localPlanPath = null;

                  try {
                     localPlanPath = ((AppDeploymentMBean)napp).getLocalPlanPath();
                     if (localPlanPath != null) {
                        FileUtils.copyPreservePermissions(fromFile, new File(localPlanPath));
                     }
                  } catch (Exception var49) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("MigrationToPartitionManager:processDeployment : failed to copy over local-plan-path to: " + localPlanPath);
                     }
                  }
               }

               appPlanDir = ((AppDeploymentMBean)appDeploymentMBean).getPlanDir();
               planZipEntry = appPlanDir != null ? ImportExportHelper.getZipEntry(zipAppDir + "/" + appPlanDir, partitionArchive) : null;
               if (planZipEntry != null) {
                  fromFile = ImportExportHelper.getAppFileToWrite(domain, parent, appPlanDir);
                  if (fromFile.exists()) {
                     var10002 = domainToPartitionLogger;
                     throw new ManagementException(DomainToPartitionLogger.logDestinationAppPlanExistsLoggable(((AppDeploymentMBean)appDeploymentMBean).getName(), fromFile.getCanonicalPath()).getMessage());
                  }

                  fromFile = Paths.get(tmpDirForArchive, planZipEntry.getName()).toFile();
                  FileUtils.copyPreservePermissions(fromFile, fromFile);
                  ((AppDeploymentMBean)napp).setPlanDir(fromFile.getCanonicalPath());
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("MigrationToPartitionManager:processDeployment : planDir: " + ((AppDeploymentMBean)napp).getPlanDir());
                  }
               }
            }
         }
      }

   }

   private Object getObjectByKey(String jsonKey, JSONObject jsonObject) throws JSONException {
      Object returnObj = null;
      Iterator keys = jsonObject.keys();

      String key;
      do {
         if (!keys.hasNext()) {
            return null;
         }

         key = (String)keys.next();
      } while(key.compareTo(jsonKey) != 0);

      return jsonObject.opt(key);
   }

   private static void validateHAPolicies(DynamicDeploymentMBean ddMBean, List rgTargetList, Map vtClusterMap) throws Exception {
      Iterator rgTargetListIterator = rgTargetList.iterator();

      while(rgTargetListIterator.hasNext()) {
         String vtName = (String)rgTargetListIterator.next();
         if (vtClusterMap.containsKey(vtName)) {
            validateHAPolicies(ddMBean, (ClusterMBean)vtClusterMap.get(vtName));
         }
      }

   }

   private static void validateHAPolicies(DynamicDeploymentMBean ddMBean, ClusterMBean target) throws Exception {
      if (ddMBean != null) {
         validateMigrationBasis(ddMBean, target, false);
         if (ddMBean.getDistributionPolicy().equals("Singleton") && !ddMBean.getMigrationPolicy().equals("On-Failure") && !ddMBean.getMigrationPolicy().equals("Always")) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getInvalidStoreHAPolicies(ddMBean.getType(), ddMBean.getName(), ddMBean.getDistributionPolicy(), ddMBean.getMigrationPolicy()));
         } else if (ddMBean instanceof MessagingBridgeMBean && ddMBean.getMigrationPolicy().equals("Always")) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getAlwaysMigrationPolicyIsNotAllowedForBridges(ddMBean.getName()));
         }
      }
   }

   private static void validateMigrationBasis(DynamicDeploymentMBean ddMBean, ClusterMBean target, boolean isPathService) throws Exception {
      String basisType = target.getMigrationBasis();
      boolean isClusterConfigInvalid = false;
      if ("database".equals(basisType) && target.getDataSourceForAutomaticMigration() == null) {
         isClusterConfigInvalid = true;
      }

      if ("consensus".equals(basisType)) {
         ServerMBean[] var5 = target.getServers();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ServerMBean serverMBean = var5[var7];
            MachineMBean machine = serverMBean.getMachine();
            if (machine == null) {
               isClusterConfigInvalid = true;
            } else if (machine.getNodeManager() == null) {
               isClusterConfigInvalid = true;
            }
         }
      }

      if (isClusterConfigInvalid && isPathService) {
         DomainToPartitionLogger var10002 = domainToPartitionLogger;
         throw new ManagementException(DomainToPartitionLogger.logInvalidHAPoliciesForPathServiceLoggable(target.getName()).getMessage());
      } else if (isClusterConfigInvalid) {
         throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getCannotEnableAutoMigrationWithoutLeasing(ddMBean.getType(), ddMBean.getName(), ddMBean.getMigrationPolicy()));
      }
   }

   private Set getSingletonJMSServers(DestinationBean destinationBean, JMSSystemResourceMBean jmsSystemResourceMBean, DomainMBean domainMBean) {
      Set singletonJMSServers = new HashSet();
      if (destinationBean.getSubDeploymentName() != "" && !destinationBean.isDefaultTargetingEnabled()) {
         SubDeploymentMBean subDeploymentMBean = jmsSystemResourceMBean.lookupSubDeployment(destinationBean.getSubDeploymentName());
         if (subDeploymentMBean == null) {
            return singletonJMSServers;
         } else {
            TargetMBean[] subDeploymentTargets = subDeploymentMBean.getTargets();
            TargetMBean[] var7 = subDeploymentTargets;
            int var8 = subDeploymentTargets.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               TargetMBean subDepTarget = var7[var9];
               if (subDepTarget.getType() == "JMSServer") {
                  singletonJMSServers.add(((JMSServerMBean)subDepTarget).getName());
               } else {
                  ServerMBean[] var11;
                  int var12;
                  int var13;
                  ServerMBean serverMBean;
                  JMSServerMBean[] var15;
                  int var16;
                  int var17;
                  JMSServerMBean jmsServerMBean;
                  if (subDepTarget.getType() == "Cluster") {
                     var11 = ((ClusterMBean)subDepTarget).getServers();
                     var12 = var11.length;

                     for(var13 = 0; var13 < var12; ++var13) {
                        serverMBean = var11[var13];
                        var15 = domainMBean.getJMSServers();
                        var16 = var15.length;

                        for(var17 = 0; var17 < var16; ++var17) {
                           jmsServerMBean = var15[var17];
                           if (Arrays.asList(jmsServerMBean.getTargets()).contains(serverMBean)) {
                              singletonJMSServers.add(jmsServerMBean.getName());
                           }
                        }
                     }
                  } else if (subDepTarget.getType() != "MigratableTarget") {
                     if (subDepTarget.getType() == "Server") {
                        JMSServerMBean[] var19 = domainMBean.getJMSServers();
                        var12 = var19.length;

                        for(var13 = 0; var13 < var12; ++var13) {
                           JMSServerMBean jmsServerMBean = var19[var13];
                           if (Arrays.asList(jmsServerMBean.getTargets()).contains(subDepTarget)) {
                              singletonJMSServers.add(jmsServerMBean.getName());
                           }
                        }
                     }
                  } else {
                     var11 = ((MigratableTargetMBean)subDepTarget).getAllCandidateServers();
                     var12 = var11.length;

                     for(var13 = 0; var13 < var12; ++var13) {
                        serverMBean = var11[var13];
                        var15 = domainMBean.getJMSServers();
                        var16 = var15.length;

                        for(var17 = 0; var17 < var16; ++var17) {
                           jmsServerMBean = var15[var17];
                           if (Arrays.asList(jmsServerMBean.getTargets()).contains(serverMBean)) {
                              singletonJMSServers.add(jmsServerMBean.getName());
                           }
                        }
                     }
                  }
               }
            }

            return singletonJMSServers;
         }
      } else {
         return singletonJMSServers;
      }
   }

   private Set getJMSTargets(TargetableBean destinationBean, BasicDeploymentMBean jmsSystemResourceMBean) {
      Set candidateJMSTargets = new TreeSet(targetComparator);
      SubDeploymentMBean subDeploymentMBean = this.getSubdeployment(destinationBean, jmsSystemResourceMBean);
      if (subDeploymentMBean == null) {
         return candidateJMSTargets;
      } else {
         DomainMBean domain = (DomainMBean)jmsSystemResourceMBean.getParent();
         Object avlblJMSTargets;
         String targetType;
         if (destinationBean instanceof SAFImportedDestinationsBean) {
            avlblJMSTargets = domain.getSAFAgents();
            targetType = "SAFAgent";
         } else {
            avlblJMSTargets = domain.getJMSServers();
            targetType = "JMSServer";
         }

         TargetMBean[] subDeploymentTargets = subDeploymentMBean.getTargets();
         TargetMBean[] var9 = subDeploymentTargets;
         int var10 = subDeploymentTargets.length;

         int var11;
         TargetMBean subDepTarget;
         for(var11 = 0; var11 < var10; ++var11) {
            subDepTarget = var9[var11];
            if (subDepTarget != null && targetType.equals(subDepTarget.getType())) {
               candidateJMSTargets.add(subDepTarget);
            }
         }

         if (!candidateJMSTargets.isEmpty()) {
            return candidateJMSTargets;
         } else {
            var9 = subDeploymentTargets;
            var10 = subDeploymentTargets.length;

            for(var11 = 0; var11 < var10; ++var11) {
               subDepTarget = var9[var11];
               if (subDepTarget != null) {
                  if ("Cluster".equals(subDepTarget.getType())) {
                     this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, subDepTarget);
                     this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, ((ClusterMBean)subDepTarget).getServers());
                  } else if ("MigratableTarget".equals(subDepTarget.getType())) {
                     this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, subDepTarget);
                     this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, ((MigratableTargetMBean)subDepTarget).getCluster());
                     this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, ((MigratableTargetMBean)subDepTarget).getAllCandidateServers());
                  } else if ("Server".equals(subDepTarget.getType())) {
                     this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, subDepTarget);
                     ClusterMBean associatedCluster = ((ServerMBean)subDepTarget).getCluster();
                     if (associatedCluster != null) {
                        this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, associatedCluster);
                        this.addCandidates(candidateJMSTargets, (TargetMBean[])avlblJMSTargets, associatedCluster.getServers());
                     }
                  }
               }
            }

            return candidateJMSTargets;
         }
      }
   }

   private SubDeploymentMBean getSubdeployment(TargetableBean destinationBean, BasicDeploymentMBean jmssrMBean) {
      if (destinationBean.getSubDeploymentName() != null && !"".equals(destinationBean.getSubDeploymentName()) && !destinationBean.isDefaultTargetingEnabled()) {
         SubDeploymentMBean subDeploymentMBean = jmssrMBean.lookupSubDeployment(destinationBean.getSubDeploymentName());
         if (subDeploymentMBean == null && jmssrMBean instanceof AppDeploymentMBean) {
            SubDeploymentMBean[] var4 = jmssrMBean.getSubDeployments();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               SubDeploymentMBean outerSubDeploy = var4[var6];
               subDeploymentMBean = outerSubDeploy.lookupSubDeployment(destinationBean.getSubDeploymentName());
               if (subDeploymentMBean != null) {
                  break;
               }
            }
         }

         return subDeploymentMBean;
      } else {
         return null;
      }
   }

   private void addCandidates(Set jmsTargets, TargetMBean[] avlblJMSTargets, TargetMBean... targets) {
      TargetMBean[] var4 = targets;
      int var5 = targets.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetMBean aTarget = var4[var6];
         TargetMBean[] var8 = avlblJMSTargets;
         int var9 = avlblJMSTargets.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            TargetMBean aJMSTarget = var8[var10];
            List realTgts = new ArrayList();
            if (aJMSTarget instanceof JMSServerMBean) {
               if (this.jmsServerTgtMap.containsKey(aJMSTarget.getName())) {
                  realTgts = (List)this.jmsServerTgtMap.get(aJMSTarget.getName());
               }
            } else if (aJMSTarget instanceof SAFAgentMBean && this.safAgentTgtMap.containsKey(aJMSTarget.getName())) {
               realTgts = (List)this.safAgentTgtMap.get(aJMSTarget.getName());
            }

            if (((List)realTgts).contains(aTarget)) {
               jmsTargets.add(aJMSTarget);
            }
         }
      }

   }

   private void updateSubdeployments(JMSBean jmsBean, BasicDeploymentMBean jmssr, ResourceGroupTemplateMBean rgtMBean) throws InvalidAttributeValueException {
      this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getConnectionFactories(), jmssr, rgtMBean);
      this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getUniformDistributedQueues(), jmssr, rgtMBean);
      this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getUniformDistributedTopics(), jmssr, rgtMBean);
      this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getForeignServers(), jmssr, rgtMBean);
      this.updateSubdeploymentsWithJMSTargets((TargetableBean[])jmsBean.getSAFImportedDestinations(), jmssr, rgtMBean);
   }

   private void updateSubdeploymentsWithJMSTargets(TargetableBean[] destinationBeans, BasicDeploymentMBean jmssrMBean, ResourceGroupTemplateMBean rgtMBean) throws InvalidAttributeValueException {
      TargetableBean[] var4 = destinationBeans;
      int var5 = destinationBeans.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetableBean aDestination = var4[var6];
         this.updateSubdeploymentsWithJMSTargets(aDestination, jmssrMBean, rgtMBean);
      }

   }

   private void updateSubdeploymentsWithJMSTargets(TargetableBean destinationBean, BasicDeploymentMBean jmssrMBean, ResourceGroupTemplateMBean rgtMBean) throws InvalidAttributeValueException {
      SubDeploymentMBean subdeploy = this.getSubdeployment(destinationBean, jmssrMBean);
      if (subdeploy != null) {
         Set candidateJMSTargets = this.getJMSTargets(destinationBean, jmssrMBean);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Candidate targets for the destination '" + destinationBean + "':" + candidateJMSTargets);
         }

         String requiredDistributionPolicy = this.getRequiredDistributionPolicy(destinationBean);
         TargetMBean firstSubdeployTgt = null;
         Iterator var8 = candidateJMSTargets.iterator();

         TargetMBean aSortedTarget;
         while(var8.hasNext()) {
            aSortedTarget = (TargetMBean)var8.next();
            if (aSortedTarget instanceof JMSServerMBean) {
               JMSServerMBean theJMSServer = rgtMBean.lookupJMSServer(aSortedTarget.getName());
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Candidate JMSServer found. '" + aSortedTarget + "'Analyzing its distribution policy. Required: '" + requiredDistributionPolicy + "'");
               }

               if (theJMSServer != null) {
                  if ("ANY".equals(requiredDistributionPolicy)) {
                     firstSubdeployTgt = aSortedTarget;
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Store distribution policy check not required.Using existing JMS Server '" + aSortedTarget.getName() + "' as candidate for subdeployment");
                     }
                     break;
                  }

                  PersistentStoreMBean storeMBean = theJMSServer.getPersistentStore();
                  if (storeMBean != null && requiredDistributionPolicy.equals(storeMBean.getDistributionPolicy())) {
                     firstSubdeployTgt = aSortedTarget;
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Required store distribution policy found. Using existing JMS Server '" + aSortedTarget.getName() + "' as candidate for subdeployment");
                     }
                     break;
                  }
               }
            } else if (aSortedTarget instanceof SAFAgentMBean && rgtMBean.lookupSAFAgent(aSortedTarget.getName()) != null) {
               firstSubdeployTgt = aSortedTarget;
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Store distribution policy check not required.Using existing SAF Agent '" + aSortedTarget.getName() + "' as candidate for subdeployment");
               }
               break;
            }
         }

         if (firstSubdeployTgt == null && !candidateJMSTargets.isEmpty()) {
            var8 = candidateJMSTargets.iterator();

            while(var8.hasNext()) {
               aSortedTarget = (TargetMBean)var8.next();
               firstSubdeployTgt = this.cloneJMSTarget(aSortedTarget, requiredDistributionPolicy, rgtMBean);
               if (firstSubdeployTgt != null) {
                  break;
               }
            }
         }

         if (firstSubdeployTgt == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unable to find a JMS target or create a new onedue to conflicting store distribution policy settings. The subdeployment targets will not be updated.");
            }

         } else {
            TargetMBean[] subdeployTargets = subdeploy.getTargets();
            TargetMBean[] var17 = subdeployTargets;
            int var19 = subdeployTargets.length;

            for(int var21 = 0; var21 < var19; ++var21) {
               TargetMBean aSubdeployTarget = var17[var21];
               if (aSubdeployTarget.getName().equals(firstSubdeployTgt.getName()) && aSubdeployTarget.getType().equals(firstSubdeployTgt.getType())) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Found a matching target in subdeployment" + firstSubdeployTgt);
                  }
               } else {
                  try {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Removing target " + firstSubdeployTgt + " from subdeployment");
                     }

                     subdeploy.removeTarget(aSubdeployTarget);
                  } catch (DistributedManagementException | InvalidAttributeValueException var15) {
                     var15.printStackTrace();
                  }
               }
            }

            if (subdeployTargets != null && subdeployTargets.length > 0 && subdeploy.getTargets() != null && subdeploy.getTargets().length == 0) {
               try {
                  DomainMBean domainMBean = (DomainMBean)jmssrMBean.getParentBean();
                  if (domainMBean != null) {
                     TargetMBean target = null;
                     if (firstSubdeployTgt instanceof JMSServerMBean) {
                        target = domainMBean.lookupJMSServer(firstSubdeployTgt.getName());
                     } else if (firstSubdeployTgt instanceof SAFAgentMBean) {
                        target = domainMBean.lookupSAFAgent(firstSubdeployTgt.getName());
                     }

                     if (target != null) {
                        subdeploy.addTarget((TargetMBean)target);
                     }
                  }
               } catch (DistributedManagementException var14) {
                  var14.printStackTrace();
               }
            }

         }
      }
   }

   private String getRequiredDistributionPolicy(TargetableBean destinationBean) {
      if (!(destinationBean instanceof UniformDistributedDestinationBean) && !(destinationBean instanceof SAFImportedDestinationsBean) && !(destinationBean instanceof ForeignServerBean)) {
         return !(destinationBean instanceof QueueBean) && !(destinationBean instanceof TopicBean) ? "ANY" : "Singleton";
      } else {
         return "Distributed";
      }
   }

   private TargetMBean cloneJMSTarget(TargetMBean targetToClone, String distributionPolicy, ResourceGroupTemplateMBean rgtMBean) throws InvalidAttributeValueException {
      TargetMBean clonedJMSTarget = null;
      PersistentStoreMBean clonedStore;
      if (targetToClone instanceof JMSServerMBean) {
         if (rgtMBean.lookupJMSServer(targetToClone.getName()) != null) {
            return null;
         }

         clonedStore = this.cloneStore(targetToClone.getName(), ((JMSServerMBean)targetToClone).getPersistentStore(), distributionPolicy, rgtMBean);
         if (clonedStore == null) {
            return null;
         }

         clonedJMSTarget = rgtMBean.createJMSServer(targetToClone.getName());
         ((JMSServerMBean)clonedJMSTarget).setPersistentStore(clonedStore);
      } else if (targetToClone instanceof SAFAgentMBean) {
         if (rgtMBean.lookupSAFAgent(targetToClone.getName()) != null) {
            return null;
         }

         clonedStore = this.cloneStore(targetToClone.getName(), ((SAFAgentMBean)targetToClone).getStore(), distributionPolicy, rgtMBean);
         if (clonedStore == null) {
            return null;
         }

         clonedJMSTarget = rgtMBean.createSAFAgent(targetToClone.getName());
         ((SAFAgentMBean)clonedJMSTarget).setStore(clonedStore);
      }

      return (TargetMBean)clonedJMSTarget;
   }

   private PersistentStoreMBean cloneStore(String storeName, PersistentStoreMBean storeToClone, String distributionPolicy, ResourceGroupTemplateMBean rgtMBean) throws InvalidAttributeValueException {
      PersistentStoreMBean clonedStore = null;
      if (storeToClone == null) {
         clonedStore = rgtMBean.createFileStore(storeName + "FileStore");
         ((FileStoreMBean)clonedStore).setDirectory(storeName);
      } else if (storeToClone instanceof FileStoreMBean) {
         PersistentStoreMBean clonedStore = rgtMBean.lookupFileStore(storeToClone.getName());
         if (clonedStore != null) {
            if ("Singleton".equals(distributionPolicy)) {
               clonedStore.setDistributionPolicy(distributionPolicy);
               return clonedStore;
            }

            if (!"ANY".equals(distributionPolicy) && !distributionPolicy.equals(clonedStore.getDistributionPolicy())) {
               return null;
            }

            return clonedStore;
         }

         clonedStore = rgtMBean.createFileStore(storeToClone.getName());
         ((FileStoreMBean)clonedStore).setDirectory(storeToClone.getName());
      } else if (storeToClone instanceof JDBCStoreMBean) {
         PersistentStoreMBean clonedStore = rgtMBean.lookupJDBCStore(storeToClone.getName());
         if (clonedStore != null) {
            if ("Singleton".equals(distributionPolicy)) {
               clonedStore.setDistributionPolicy(distributionPolicy);
               return clonedStore;
            }

            if (!"ANY".equals(distributionPolicy) && !distributionPolicy.equals(clonedStore.getDistributionPolicy())) {
               return null;
            }

            return clonedStore;
         }

         clonedStore = rgtMBean.createJDBCStore(storeToClone.getName());
         JDBCSystemResourceMBean odsbean = ((JDBCStoreMBean)storeToClone).getDataSource();
         if (odsbean != null) {
            JDBCSystemResourceMBean ndsbean = rgtMBean.lookupJDBCSystemResource(odsbean.getName());
            ((JDBCStoreMBean)clonedStore).setDataSource(ndsbean);
         }

         String prefixName = ((JDBCStoreMBean)storeToClone).getPrefixName();
         if (prefixName != null) {
            ((JDBCStoreMBean)clonedStore).setPrefixName(prefixName);
         }
      }

      if (!"ANY".equals(distributionPolicy)) {
         ((PersistentStoreMBean)clonedStore).setDistributionPolicy(distributionPolicy);
      }

      ((PersistentStoreMBean)clonedStore).setMigrationPolicy("Always");
      ((PersistentStoreMBean)clonedStore).setRestartInPlace(true);
      return (PersistentStoreMBean)clonedStore;
   }

   private String readFile(File fileName) throws IOException {
      StringBuilder fileContents = new StringBuilder();
      Scanner scanner = new Scanner(fileName);
      String lineSeparator = System.getProperty("line.separator");

      String var5;
      try {
         while(scanner.hasNextLine()) {
            fileContents.append(scanner.nextLine() + lineSeparator);
         }

         var5 = fileContents.toString();
      } finally {
         scanner.close();
      }

      return var5;
   }

   private JMSBean getJMSBeanFromString(String jmsModuleAsString) throws Exception {
      BasicDescriptorManager dm = new BasicDescriptorManager(DescriptorHelper.class.getClassLoader(), true, (SecurityService)null);
      XMLInputFactory factory = XXEUtils.createXMLInputFactoryInstance();
      XMLStreamReader xmlReader = null;

      try {
         xmlReader = factory.createXMLStreamReader(new ByteArrayInputStream(jmsModuleAsString.getBytes()), (String)null);
      } catch (Exception var6) {
         xmlReader = factory.createXMLStreamReader(new ByteArrayInputStream(jmsModuleAsString.getBytes()), "UTF-8");
      }

      Descriptor descriptor = dm.createDescriptor(xmlReader, false);
      return (JMSBean)descriptor.getRootBean();
   }

   private static Attributes getManifestAttributes(File f) throws IOException {
      Attributes rtn = null;
      VirtualJarFile jar = null;

      try {
         jar = VirtualJarFactory.createVirtualJar(f);
      } catch (IOException var7) {
         closeJar(jar);
         throw var7;
      }

      try {
         Manifest m = jar.getManifest();
         if (m != null) {
            rtn = m.getMainAttributes();
         }
      } finally {
         closeJar(jar);
      }

      return rtn;
   }

   private static void closeJar(VirtualJarFile f) {
      try {
         if (f != null) {
            f.close();
         }
      } catch (IOException var2) {
      }

   }

   private String getLibraryId(Attributes attrs) {
      String VER_DELIMITER = "#";
      String LIB_DELIMITER = "@";
      String libraryName = attrs.getValue(Name.EXTENSION_NAME);
      String specName = attrs.getValue(Name.SPECIFICATION_VERSION);
      String implName = attrs.getValue(Name.IMPLEMENTATION_VERSION);
      String libraryId = (libraryName == null ? "" : libraryName) + "#" + (specName == null ? "" : specName) + "@" + (implName == null ? "" : implName);
      return libraryId;
   }
}
