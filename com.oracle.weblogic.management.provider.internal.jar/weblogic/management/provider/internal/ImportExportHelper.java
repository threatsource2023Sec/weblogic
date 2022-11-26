package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.descriptor.BasicDescriptorManager;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.SecurityService;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.internal.ImportExportLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.UserConfigFileManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.XXEUtils;

public final class ImportExportHelper {
   public static final String PARTITION_XML = "partition-config.xml";
   public static final String ATTRIBUTES_JSON = "-attributes.json";
   private static final String RELATIONSHIP = "relationship";
   private static final BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
   private static String CONFIG_TO_SCRIPT_SECRET_FILE;
   public static final String SECRET_KEY_FILE_NAME = "expPartSecret";
   private static String operationType;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   public static void addToZipFile(String fileName, ZipOutputStream zos, byte[] data) throws FileNotFoundException, IOException {
      InputStream is = null;
      zos = (ZipOutputStream)Objects.requireNonNull(zos);
      fileName = (String)Objects.requireNonNull(fileName);
      File file = new File(fileName);

      try {
         if (data != null) {
            is = new ByteArrayInputStream(data);
         } else {
            is = new FileInputStream(file);
         }

         Path toCreate = Paths.get(file.getPath()).normalize();
         Path configDir = Paths.get("config");
         if (toCreate.startsWith(configDir)) {
            toCreate = configDir.relativize(toCreate);
         }

         String fileToCreate = toCreate.toString().replace("\\", "/");
         ZipEntry zipEntry = new ZipEntry(fileToCreate);
         zos.putNextEntry(zipEntry);
         byte[] bytes = new byte[4096];

         int length;
         while((length = ((InputStream)is).read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
         }

         zos.closeEntry();
      } finally {
         ((InputStream)is).close();
      }
   }

   public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
      addToZipFile(fileName, zos, (byte[])null);
   }

   public static String getHeaderString() {
      return "<?xml version='1.0' encoding='UTF-8'?>\n<domain xmlns=\"http://xmlns.oracle.com/weblogic/domain\">\n<name>${DOMAIN_NAME}</name>\n<domain-version>${DOMAIN_VERSION}</domain-version>\n";
   }

   public static String getFooterString() {
      return "</domain>";
   }

   public static ArrayList getAllPartitionReferences(Object obj) {
      BeanInfo beanInfo = ManagementService.getBeanInfoAccess().getBeanInfoForInstance(obj, false, (String)null);
      PropertyDescriptor[] propDescriptors = beanInfo.getPropertyDescriptors();
      ArrayList retArray = new ArrayList();
      PropertyDescriptor[] var4 = propDescriptors;
      int var5 = propDescriptors.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         PropertyDescriptor propDesc = var4[var6];
         String rel = (String)propDesc.getValue("relationship");
         if (rel != null && rel.compareTo("reference") == 0 && propDesc.getName().compareTo("Parent") != 0) {
            retArray.add(propDesc.getName());
         }
      }

      return retArray;
   }

   public List getAllPartitionReferences() {
      return (new AttributeAggregator("weblogic.management.configuration.PartitionMBean", "relationship", "reference")).returnReferencedAttributes();
   }

   public static SystemResourceMBean[] getSystemResourcesForPartition(PartitionMBean pMBean) {
      return new SystemResourceMBean[0];
   }

   public static void extractPartitionFSFromZip(PartitionMBean partition, ZipFile pArchive, Set writtenFileSet) throws IOException {
      Path partRootConfig = Paths.get(partition.getSystemFileSystem().getRoot(), "config");
      if (!Files.exists(partRootConfig, new LinkOption[0])) {
         Files.createDirectories(partRootConfig);
      }

      Enumeration zes = pArchive.entries();

      while(zes.hasMoreElements()) {
         ZipEntry ze = (ZipEntry)zes.nextElement();
         InputStream ins = pArchive.getInputStream(ze);
         Path zipEntryPath = Paths.get(ze.getName());
         if (zipEntryPath.startsWith("pfs/")) {
            Path toCreate = Paths.get(partRootConfig.toString(), Paths.get("pfs/").relativize(zipEntryPath).toString());
            if (ze.isDirectory()) {
               if (!Files.exists(toCreate, new LinkOption[0])) {
                  Files.createDirectories(toCreate);
               }
            } else {
               Path toCreateParent = toCreate.getParent();
               if (!Files.exists(toCreateParent, new LinkOption[0])) {
                  Files.createDirectories(toCreateParent);
               }

               Files.copy(ins, toCreate, new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            }

            writtenFileSet.add(toCreate.toFile());
         }
      }

   }

   public static void addPartitionFSToExportZip(PartitionMBean partition, ZipOutputStream zipOut, File srcFile) throws Exception {
      Path partRootConfig = Paths.get(partition.getSystemFileSystem().getRoot(), "config");
      if (srcFile == null) {
         srcFile = partRootConfig.toFile();
         if (!Files.exists(partRootConfig, new LinkOption[0])) {
            return;
         }
      }

      if (srcFile.list().length == 0) {
         Path srcFilePath = Paths.get(srcFile.getAbsolutePath());
         Path zipFileEntry = partRootConfig.relativize(srcFilePath);
         if (!zipFileEntry.toString().isEmpty()) {
            ZipEntry zipEntry = new ZipEntry("pfs/" + zipFileEntry.toString() + "/");
            zipOut.putNextEntry(zipEntry);
            zipOut.closeEntry();
         }
      }

      File[] var11 = srcFile.listFiles();
      int var12 = var11.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         File partSysFile = var11[var13];
         if (partSysFile.isDirectory()) {
            addPartitionFSToExportZip(partition, zipOut, partSysFile);
         } else {
            Path partSysFilePath = Paths.get(partSysFile.getAbsolutePath());
            Path zipFileEntry = partRootConfig.relativize(partSysFilePath);
            File fileToAdd = new File("pfs" + File.separator + zipFileEntry.toString());
            addToZipFile(fileToAdd.getPath(), zipOut, Files.readAllBytes(partSysFilePath));
         }
      }

   }

   public static void addRDPPathToExportZip(PartitionMBean partition, ZipOutputStream zipOut) throws FileNotFoundException, IOException {
      String rdpPath = partition.getResourceDeploymentPlanPath();
      if (rdpPath != null && rdpPath.length() > 0) {
         addToZipFile(Paths.get(rdpPath).getFileName().toString(), zipOut, Files.readAllBytes(Paths.get(rdpPath)));
      }

   }

   public static void importRDPPath(PartitionMBean partition, ZipFile archive, Set writtenFileSet) throws ManagementException, IOException {
      String rdpPath = partition.getResourceDeploymentPlanPath();
      if (rdpPath != null && rdpPath.length() > 0) {
         String rdpPathFileName = Paths.get(rdpPath).getFileName().toString();
         ZipEntry rdpPathEntry = null;
         if ((rdpPathEntry = archive.getEntry(rdpPathFileName)) == null) {
            Loggable l = ImportExportLogger.logErrorRddNotInZipLoggable(partition.getName(), rdpPathFileName);
            l.log();
            throw new ManagementException(l.getMessage());
         }

         String uploadDirectory = partition.getUploadDirectoryName();
         if (uploadDirectory == null) {
            uploadDirectory = ((DomainMBean)partition.getParent()).getRootDirectory();
         }

         Path toSetPath = Paths.get(uploadDirectory, rdpPathFileName);
         FileUtils.writeToFile(archive.getInputStream(rdpPathEntry), toSetPath.toFile());
         writtenFileSet.add(toSetPath.toFile());
         partition.setResourceDeploymentPlanPath(toSetPath.normalize().toString());
      }

   }

   private static void addAppToExportZip(String appZipEntryName, String appAbsPathName, ZipOutputStream zos) throws IOException {
      Path appAbsPath = Paths.get(appAbsPathName);
      File appFile = new File(appAbsPathName);
      if (appFile.isDirectory()) {
         ZipEntry zipEntry = new ZipEntry(removeRootFromPath(appZipEntryName) + "/");
         zos.putNextEntry(zipEntry);
         zos.closeEntry();
         File[] var6 = appFile.listFiles();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            File app = var6[var8];
            if (app.isDirectory()) {
               addAppToExportZip(Paths.get(appZipEntryName, app.getName()).toString(), app.getAbsolutePath(), zos);
            } else {
               addToZipFile(removeRootFromPath(Paths.get(appZipEntryName, app.getName()).toString()), zos, Files.readAllBytes(Paths.get(app.getAbsolutePath())));
            }
         }
      } else {
         addToZipFile(removeRootFromPath(appZipEntryName), zos, Files.readAllBytes(Paths.get(appAbsPathName)));
      }

   }

   public static synchronized void exportPartition(String partitionName, String partitionArchive, boolean includeApps, String keyFile) throws Exception {
      DomainMBean domain = getDomain();
      domain = (DomainMBean)Objects.requireNonNull(domain);
      partitionName = (String)Objects.requireNonNull(partitionName);
      partitionArchive = (String)Objects.requireNonNull(partitionArchive);
      if (!Files.isDirectory(Paths.get(partitionArchive), new LinkOption[0])) {
         throw new FileNotFoundException(partitionArchive + "does not exist");
      } else {
         File partitionZip = null;
         File attributesJsonFile = new File(partitionArchive + File.separator + partitionName + "-attributes.json");
         ZipOutputStream zos = null;
         setOperationType("Export");
         String secretKeyFileName = System.getProperty("java.io.tmpdir") + File.separator + "expPartSecret" + UUID.randomUUID().toString();
         setCONFIG_TO_SCRIPT_SECRET_FILE(replaceEscapeCharacters(secretKeyFileName));
         boolean var27 = false;

         try {
            var27 = true;
            DomainMBean clonedDomain = (DomainMBean)((Descriptor)domain.getDescriptor().clone()).getRootBean();
            PartitionMBean pMbean = clonedDomain.lookupPartition(partitionName);
            if (pMbean == null) {
               Loggable l;
               if (ManagementService.getDomainAccess(kernelId).lookupDomainPartitionRuntime(partitionName) == null) {
                  if (ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isRestartRequired()) {
                     l = ImportExportLogger.logExportFailForRestartRequiredLoggable(partitionName, ManagementService.getRuntimeAccess(kernelId).getServerName());
                     throw new ManagementException(l.getMessage());
                  }

                  l = ImportExportLogger.logPartitionToExportNotInDomainLoggable(partitionName);
                  throw new ManagementException(l.getMessage());
               }

               l = ImportExportLogger.logPartitionToExportNotInDomainLoggable(partitionName);
               l.log();
               throw new RuntimeException(l.getMessage());
            }

            DescriptorManager dm = new DescriptorManager();
            partitionZip = new File(partitionArchive + File.separator + partitionName + ".zip");
            zos = new ZipOutputStream(new FileOutputStream(partitionZip));
            String dataFile = ExportCustomizeableValuesHelper.processExportCustomizeableValuesAnnotation(pMbean, partitionName, (String)null);
            JSONObject jsonData = new JSONObject(dataFile);
            StringBuilder partitionInfo = new StringBuilder();
            List referencedAttributes = getAllPartitionReferences(pMbean);
            Iterator var16 = referencedAttributes.iterator();

            String rgSysResJson;
            while(var16.hasNext()) {
               rgSysResJson = (String)var16.next();
               if (!rgSysResJson.equals("Parent")) {
                  pMbean.unSet(rgSysResJson);
               }
            }

            pMbean.unSet("PartitionID");
            pMbean.unSet("InternalAppDeployments");
            pMbean.unSet("InternalLibraries");
            pMbean.unSet("AdminVirtualTarget");
            addPartitionFSToExportZip(pMbean, zos, (File)null);
            addRDPPathToExportZip(pMbean, zos);
            pMbean.unSet("SystemFileSystem");
            partitionInfo.append(getHeaderString());
            String pInfo = DescriptorUtils.toString(dm, pMbean);
            partitionInfo.append(pInfo);
            rgSysResJson = exportSystemResources(getSystemResourcesForPartition(pMbean), zos, keyFile, pMbean);
            if (rgSysResJson != null && rgSysResJson.length() > 0) {
               appendJsonObject(jsonData, new JSONObject(rgSysResJson));
            }

            addToZipFile(partitionName + "-attributes.json", zos, jsonData.toString(4).getBytes());
            FileUtils.writeToFile(new ByteArrayInputStream(jsonData.toString(4).getBytes()), attributesJsonFile);
            if (includeApps) {
               AppDeploymentMBean[] appDeploymentMBeans = AppDeploymentHelper.getAppsAndLibs(pMbean);
               AppDeploymentMBean[] var19 = appDeploymentMBeans;
               int var20 = appDeploymentMBeans.length;

               for(int var21 = 0; var21 < var20; ++var21) {
                  AppDeploymentMBean ap = var19[var21];
                  addAppToExportZip(ap.getSourcePath(), ap.getAbsoluteSourcePath(), zos);
                  if (ap.getAbsolutePlanPath() != null) {
                     addToZipFile(removeRootFromPath(ap.getPlanPath()), zos, Files.readAllBytes(Paths.get(ap.getAbsolutePlanPath())));
                  }
               }
            }

            partitionInfo.append(getFooterString());
            String configChangeFile = partitionInfo.toString().replace("${DOMAIN_NAME}", domain.getName());
            configChangeFile = configChangeFile.replace("${DOMAIN_VERSION}", domain.getDomainVersion());
            String beanDescriptorWithNewPwd = handleEncryptedAttributes(pMbean, configChangeFile, false, keyFile);
            configChangeFile = beanDescriptorWithNewPwd.replace(domain.getName(), "${DOMAIN_NAME}");
            configChangeFile = configChangeFile.replace(domain.getDomainVersion(), "${DOMAIN_VERSION}");
            addToZipFile("partition-config.xml", zos, configChangeFile.getBytes());
            addToZipFile(CONFIG_TO_SCRIPT_SECRET_FILE, zos);
            var27 = false;
         } catch (Exception var28) {
            if (partitionZip != null && partitionZip.exists()) {
               partitionZip.delete();
            }

            if (attributesJsonFile != null && attributesJsonFile.exists()) {
               attributesJsonFile.delete();
            }

            throw var28;
         } finally {
            if (var27) {
               if (zos != null) {
                  zos.close();
               }

               File secretKeyFile = new File(secretKeyFileName);
               if (secretKeyFile != null && secretKeyFile.exists()) {
                  secretKeyFile.delete();
               }

            }
         }

         if (zos != null) {
            zos.close();
         }

         File secretKeyFile = new File(secretKeyFileName);
         if (secretKeyFile != null && secretKeyFile.exists()) {
            secretKeyFile.delete();
         }

      }
   }

   private static void appendJsonObject(JSONObject orig, JSONObject toAppend) throws JSONException {
      if (orig != null && toAppend != null) {
         Iterator toAppIter = toAppend.keys();

         while(toAppIter.hasNext()) {
            String nextKey = toAppIter.next().toString();
            if (orig.has(nextKey)) {
               orig.accumulate(nextKey, toAppend.get(nextKey));
            } else {
               orig.put(nextKey, toAppend.get(nextKey));
            }
         }

      }
   }

   private static String exportSystemResources(SystemResourceMBean[] systemResources, ZipOutputStream zos, String keyFile, PartitionMBean pMBean) throws Exception {
      JSONObject sysResOverrides = new JSONObject();
      SystemResourceMBean[] var5 = systemResources;
      int var6 = systemResources.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         SystemResourceMBean sr = var5[var7];
         if (!((AbstractDescriptorBean)sr)._isTransient()) {
            String systemResourceDesc = new String(Files.readAllBytes(Paths.get(sr.getSourcePath())));
            String beanDescriptorWithNewPwd = handleEncryptedAttributes(sr, systemResourceDesc, true, keyFile);
            String dataFile = ExportCustomizeableValuesHelper.processExportCustomizeableValuesAnnotation(sr, sr.getName(), beanDescriptorWithNewPwd);
            appendJsonObject(sysResOverrides, new JSONObject(dataFile));
            addToZipFile(sr.getDescriptorFileName(), zos, beanDescriptorWithNewPwd.getBytes());
         }
      }

      return sysResOverrides.toString(4);
   }

   public static String handleEncryptedAttributes(DescriptorBean bean, String partitionInfo, boolean isSystemResorApp, String keyFile) throws SecurityException, Exception {
      ImpExpSecurityServiceImpl impExpSec = ImportExportHelper.ImpExpSecurityServiceImpl.getInstance();
      impExpSec.createEncryptionService(keyFile);
      BasicDescriptorManager dm = new BasicDescriptorManager(ImportExportHelper.class.getClassLoader(), true, impExpSec);
      dm.addInitialNamespace("sec", "http://xmlns.oracle.com/weblogic/security");
      dm.addInitialNamespace("wls", "http://xmlns.oracle.com/weblogic/security/wls");
      ByteArrayOutputStream boAs = new ByteArrayOutputStream();
      Descriptor descriptor = null;

      try {
         XMLInputFactory factory = XXEUtils.createXMLInputFactoryInstance();
         XMLStreamReader xmlReader = factory.createXMLStreamReader(new ByteArrayInputStream(partitionInfo.getBytes()), (String)null);
         descriptor = dm.createDescriptor(xmlReader, false);
         DescriptorBean rootBean = descriptor.getRootBean();
         Set scannedObjs = new HashSet();
         if (getOperationType().compareTo("Import") == 0) {
            scannedObjs.clear();
            walkBeanTree(bean, rootBean, isSystemResorApp, scannedObjs);
            return DescriptorUtils.toString(DescriptorManagerHelper.getDescriptorManager(true), bean);
         } else {
            scannedObjs.clear();
            walkBeanTree(bean, rootBean, isSystemResorApp, scannedObjs);
            dm.writeDescriptorAsXML(descriptor, boAs);
            return new String(boAs.toByteArray());
         }
      } catch (IOException var12) {
         var12.printStackTrace();
         throw new Exception(var12.fillInStackTrace());
      }
   }

   private static boolean isEncrypted(PropertyDescriptor pd) {
      Boolean obj = (Boolean)pd.getValue("encrypted");
      return obj != null ? obj : false;
   }

   public static String getCONFIG_TO_SCRIPT_SECRET_FILE() {
      return CONFIG_TO_SCRIPT_SECRET_FILE;
   }

   public static void setCONFIG_TO_SCRIPT_SECRET_FILE(String cONFIG_TO_SCRIPT_SECRET_FILE) {
      CONFIG_TO_SCRIPT_SECRET_FILE = cONFIG_TO_SCRIPT_SECRET_FILE;
   }

   private static void walkBeanTree(Object bean, Object toSetObj, boolean isSystemResorApp, Set scannedObjs) throws SecurityException, Exception {
      if (!((AbstractDescriptorBean)bean)._isTransient()) {
         if (!scannedObjs.contains(bean)) {
            scannedObjs.add(bean);
            PropertyDescriptor[] var4 = beanInfoAccess.getBeanInfoForInstance(bean, false, (String)null).getPropertyDescriptors();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               PropertyDescriptor propDesc = var4[var6];
               if (propDesc.getName() != "Parent" && propDesc.getName() != "Name") {
                  String rel = (String)propDesc.getValue("relationship");
                  if (rel == null || rel.compareTo("reference") != 0) {
                     Object child = null;
                     Method getMethod = null;

                     try {
                        getMethod = propDesc.getReadMethod();
                        child = getMethod.invoke(bean);
                     } catch (InvocationTargetException var13) {
                        var13.printStackTrace();
                        throw var13;
                     }

                     if (child != null) {
                        if (child.getClass().isArray()) {
                           for(int i = 0; i < Array.getLength(child); ++i) {
                              Object childBean = Array.get(child, i);
                              if (!(childBean instanceof DescriptorBean)) {
                                 if (isEncrypted(propDesc)) {
                                    setEncryptedAttributes(propDesc, child, bean, toSetObj);
                                 }
                                 break;
                              }

                              if (childBean instanceof SystemResourceMBean && !isSystemResorApp) {
                                 break;
                              }

                              walkBeanTree(childBean, toSetObj, isSystemResorApp, scannedObjs);
                           }
                        } else if (child instanceof DescriptorBean) {
                           if (!(child instanceof SystemResourceMBean) || isSystemResorApp) {
                              walkBeanTree(child, toSetObj, isSystemResorApp, scannedObjs);
                           }
                        } else if (isEncrypted(propDesc)) {
                           setEncryptedAttributes(propDesc, child, bean, toSetObj);
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private static void setEncryptedAttributes(PropertyDescriptor propDesc, Object bean, Object parent, Object toSetObj) throws Exception, SecurityException {
      String attrName = propDesc.getName();
      Object origParent = parent;
      if (getOperationType().compareTo("Import") == 0) {
         parent = ((AbstractDescriptorBean)toSetObj).findByQualifiedName((AbstractDescriptorBean)parent);
         if (parent == null) {
            throw new Exception(String.format("Trying to set values for %s in %s object that does not exist in the partition being imported", attrName, ((WebLogicMBean)origParent).getType()));
         }
      }

      Method getPE;
      if (attrName.endsWith("Encrypted")) {
         attrName = StringUtils.replaceGlobal(attrName, "Encrypted", "");
         getPE = parent.getClass().getMethod("get" + attrName);
         Object[] p1 = null;
         bean = getPE.invoke(parent, (Object[])p1);
      } else {
         attrName = attrName + "Encrypted";
         getPE = parent.getClass().getMethod("get" + attrName);
         if (getPE != null) {
            return;
         }
      }

      setAttributeVal(bean.toString().toCharArray(), propDesc, origParent, toSetObj);
   }

   private static void setAttributeVal(char[] passwordValue, PropertyDescriptor pDesc, Object parent, Object toSetObj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, DescriptorException {
      AbstractDescriptorBean toSetBean = null;
      if (getOperationType().compareTo("Export") == 0) {
         toSetBean = ((AbstractDescriptorBean)toSetObj).findByQualifiedName((AbstractDescriptorBean)parent);
      } else {
         toSetBean = (AbstractDescriptorBean)parent;
      }

      toSetBean = (AbstractDescriptorBean)Objects.requireNonNull(toSetBean);
      String attrName = pDesc.getName();
      String methodName = null;
      Method writeMethod = null;
      if (attrName.endsWith("Encrypted")) {
         attrName = StringUtils.replaceGlobal(attrName, "Encrypted", "");
         Class[] clzz = new Class[]{String.class};
         Class parentClass = parent.getClass();
         methodName = parentClass.getMethod("set" + attrName, clzz).getName();
         writeMethod = toSetBean.getClass().getMethod(methodName, clzz);
         writeMethod.invoke(toSetBean, new String(passwordValue));
      } else {
         methodName = pDesc.getWriteMethod().getName();
         writeMethod = toSetBean.getClass().getMethod(methodName, pDesc.getWriteMethod().getParameterTypes());
         writeMethod.invoke(toSetBean, (new String(passwordValue)).getBytes());
      }

   }

   private static String replaceEscapeCharacters(String s) {
      return s == null ? null : StringUtils.replaceGlobal(s, File.separator, "/");
   }

   public static String getOperationType() {
      return operationType;
   }

   public static void setOperationType(String operationType) {
      String tmp = (String)Objects.requireNonNull(operationType);
      if (!tmp.matches("(Export|Import)")) {
         throw new IllegalArgumentException("parameter to the function setOperationType should either be Export or Import");
      } else {
         ImportExportHelper.operationType = tmp;
      }
   }

   public static String getStringFromInputStream(InputStream ins) throws IOException {
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

   public static String removeRootFromPath(String filePath) {
      filePath = (String)Objects.requireNonNull(filePath);
      Path zipEntryPath = Paths.get(filePath);
      Path toCreate;
      if (zipEntryPath.isAbsolute()) {
         toCreate = zipEntryPath.getRoot().relativize(zipEntryPath);
      } else {
         toCreate = zipEntryPath;
      }

      return toCreate.toString();
   }

   public static String getUploadDirectoryName(DomainMBean domain, ConfigurationMBean parent) {
      String uploadDirectoryName = null;
      String serverName;
      String partitionName;
      if (parent instanceof ResourceGroupTemplateMBean) {
         ResourceGroupTemplateMBean rgt = (ResourceGroupTemplateMBean)parent;
         uploadDirectoryName = rgt.getUploadDirectoryName();
         if (uploadDirectoryName == null) {
            serverName = domain.getAdminServerName();
            partitionName = rgt.getName();
            if (serverName != null && partitionName != null) {
               String templateUploadPath = "upload" + File.separator + ((AbstractDescriptorBean)rgt)._getBeanElementName() + File.separator + partitionName + File.separator;
               uploadDirectoryName = DomainDir.removeRootDirectoryFromPath(DomainDir.getPathRelativeServerDir(serverName, templateUploadPath));
            }
         }
      } else if (parent instanceof ResourceGroupMBean) {
         PartitionMBean partition = (PartitionMBean)parent.getParent();
         uploadDirectoryName = partition.getUploadDirectoryName();
         if (uploadDirectoryName == null) {
            serverName = domain.getAdminServerName();
            partitionName = partition.getName();
            if (serverName != null && partitionName != null) {
               PartitionFileSystemMBean pfs = partition.getSystemFileSystem();
               uploadDirectoryName = DomainDir.removeRootDirectoryFromPath(pfs.getRoot() + File.separator + "servers" + File.separator + serverName + File.separator + "upload" + File.separator);
            }
         }
      }

      return uploadDirectoryName;
   }

   public static AppDeploymentMBean[] getAppsAndLibs(DescriptorBean parent) {
      AppDeploymentMBean[] apps = null;
      if (parent instanceof ResourceGroupTemplateMBean) {
         ResourceGroupTemplateMBean rgt = (ResourceGroupTemplateMBean)parent;
         apps = AppDeploymentHelper.getAppsAndLibs(rgt, false);
      } else if (parent instanceof ResourceGroupMBean) {
         ResourceGroupMBean rg = (ResourceGroupMBean)parent;
         apps = AppDeploymentHelper.getAppsAndLibs(rg, false);
      }

      return apps;
   }

   public static File getAppFileToWrite(DomainMBean domain, ConfigurationMBean parent, String filePath) throws IOException {
      File toFile = null;
      String uploadDirectoryName = getUploadDirectoryName(domain, parent);
      File uploadDirectoryFile = null;
      if (uploadDirectoryName == null) {
         uploadDirectoryFile = new File(filePath);
      } else {
         uploadDirectoryFile = new File(uploadDirectoryName);
      }

      if (!uploadDirectoryFile.exists()) {
         uploadDirectoryFile.mkdirs();
      }

      String uploadDirectoryName1 = uploadDirectoryFile.getCanonicalPath();
      String uploadFileName = uploadDirectoryFile.getPath();
      toFile = Paths.get(uploadDirectoryName1, removeRootFromPath(filePath)).toFile();
      if (Paths.get(filePath).isAbsolute()) {
         if (filePath.startsWith(uploadDirectoryName1)) {
            toFile = Paths.get(filePath).toFile();
         }
      } else if (filePath.startsWith(uploadFileName)) {
         toFile = new File(filePath);
      }

      return toFile;
   }

   public static ZipEntry getZipEntry(String zipEntryName, ZipFile zArchive) {
      String toSearch = removeRootFromPath(zipEntryName.replace("\\", "/"));
      return zArchive.getEntry(toSearch.replace("\\", "/"));
   }

   public static JSONObject getJSONObjectFromAttributesJson(String elementXmlType, String elementName, Object jsonObject) throws JSONException {
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
                     returnObj = getJSONObjectFromAttributesJson(elementXmlType, elementName, jo.get(key));
                  }
               }

               return returnObj;
            }
         }
      } else if (jsonObject instanceof JSONArray) {
         for(int i = 0; i < ((JSONArray)jsonObject).length(); ++i) {
            returnObj = getJSONObjectFromAttributesJson(elementXmlType, elementName, ((JSONArray)jsonObject).opt(i));
         }
      }

      return returnObj;
   }

   public static void setAppAttributesFromJson(Object appJsonObject, AppDeploymentMBean app) throws JSONException, ManagementException {
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
            setAppAttributesFromJson(appsArray.get(i), app);
         }
      }

   }

   private static String getNewRgtName(String rgtName, DomainMBean targetDomain) {
      int increment = 1;
      Set rgtNames = new HashSet();
      ResourceGroupTemplateMBean[] var4 = targetDomain.getResourceGroupTemplates();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ResourceGroupTemplateMBean targetRgt = var4[var6];
         rgtNames.add(targetRgt.getName());
      }

      String rgtNewName;
      for(rgtNewName = rgtName + increment; rgtNames.contains(rgtNewName); rgtNewName = rgtName + increment) {
         ++increment;
      }

      return rgtNewName;
   }

   private static void createRGT(DomainMBean targetDomain, ResourceGroupTemplateMBean rgtb, String rgtbNameInJson, ZipFile pArchive, String jsonAttributesString, Set writtenFileSet, String keyFile) throws Exception {
      ResourceGroupTemplateMBean resourceGroupTemplateMBean = (ResourceGroupTemplateMBean)targetDomain.createChildCopy(rgtb.getType(), rgtb);
      if (jsonAttributesString != null && !jsonAttributesString.isEmpty()) {
         ExportCustomizeableValuesHelper.setAttributesOnObject(targetDomain, resourceGroupTemplateMBean, rgtbNameInJson, jsonAttributesString);
      }

      importSystemResources(resourceGroupTemplateMBean.getSystemResources(), keyFile, writtenFileSet, targetDomain, jsonAttributesString);
      importApplicationBinaries(targetDomain, resourceGroupTemplateMBean, pArchive, jsonAttributesString, writtenFileSet);
   }

   public static void resolveRGTReferences(PartitionMBean partition, DomainMBean targetDomain, Map rGTNameMap) {
   }

   private static void copyRGTSystemResources(ResourceGroupTemplateMBean oldRGT, ResourceGroupTemplateMBean newRGT, Set writtenFileSet) throws IOException {
      SystemResourceMBean[] var3 = oldRGT.getSystemResources();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SystemResourceMBean sysResource = var3[var5];
         Path fromFilePath = Paths.get(DomainDir.getPendingDir(), sysResource.getDescriptorFileName());
         File fromFile = fromFilePath.toFile();
         SystemResourceMBean[] var9 = newRGT.getSystemResources();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            SystemResourceMBean newSystemResource = var9[var11];
            if (newSystemResource.getName().compareTo(sysResource.getName()) == 0) {
               newSystemResource.unSet("DescriptorFileName");
               newSystemResource.setDescriptorFileName(newSystemResource.getDescriptorFileName());
               File toFile = Paths.get(DomainDir.getPendingDir(), newSystemResource.getDescriptorFileName()).toFile();
               FileUtils.copyPreserveTimestampsPreservePermissions(fromFile, toFile);
               writtenFileSet.add(toFile);
               break;
            }
         }
      }

   }

   public static Map importResourceGroupTemplate(DomainMBean sourceDomain, DomainMBean targetDomain, Boolean createNew, ZipFile pArchive, String jsonAttributesString, Set writtenFileSet, String keyFile) throws Exception {
      Map rGTNameMapOldNew = new HashMap();
      ResourceGroupTemplateMBean[] var8 = sourceDomain.getResourceGroupTemplates();
      int var9 = var8.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         ResourceGroupTemplateMBean rgtb = var8[var10];
         boolean rgtExists = false;
         ResourceGroupTemplateMBean[] var13 = targetDomain.getResourceGroupTemplates();
         int var14 = var13.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            ResourceGroupTemplateMBean eRgtm = var13[var15];
            rgtExists = eRgtm.getName().compareTo(rgtb.getName()) == 0;
            if (rgtExists) {
               break;
            }
         }

         if (rgtExists) {
            if (createNew == null) {
               Loggable l = ImportExportLogger.logRGTAlreadyExistDuringImportLoggable(rgtb.getName());
               l.log();
               throw new ManagementException(l.getMessage());
            }

            if (!createNew) {
               rGTNameMapOldNew.put(rgtb.getName(), rgtb.getName());
               File toDeleteFile = Paths.get(DomainDir.getPendingDir(), ((AbstractDescriptorBean)rgtb)._getBeanElementName() + "s", rgtb.getName()).toFile();
               if (toDeleteFile.exists()) {
                  boolean retVal = FileUtils.remove(toDeleteFile);
                  if (!retVal) {
                     Loggable l = ImportExportLogger.logFailedToDeleterPendingFileLoggable(toDeleteFile.getPath());
                     l.log();
                     throw new ManagementException(l.getMessage());
                  }
               }
            }

            if (createNew) {
               ResourceGroupTemplateMBean clonedRGT = (ResourceGroupTemplateMBean)((AbstractDescriptorBean)rgtb).clone();
               String newName = getNewRgtName(rgtb.getName(), targetDomain);
               rGTNameMapOldNew.put(rgtb.getName(), newName);
               clonedRGT.unSet("Name");
               clonedRGT.setName(newName);
               ((DescriptorImpl)clonedRGT.getDescriptor()).resolveReferences();
               copyRGTSystemResources(rgtb, clonedRGT, writtenFileSet);
               createRGT(targetDomain, clonedRGT, rgtb.getName(), pArchive, jsonAttributesString, writtenFileSet, keyFile);
            }
         }

         if (!rgtExists) {
            createRGT(targetDomain, rgtb, rgtb.getName(), pArchive, jsonAttributesString, writtenFileSet, keyFile);
         }
      }

      return rGTNameMapOldNew;
   }

   public static void importApplicationBinaries(DomainMBean domain, ConfigurationMBean parent, ZipFile pArchive, String jsonAttributesString, Set writtenFileSet) throws Exception {
      AppDeploymentMBean[] apps = getAppsAndLibs(parent);
      AppDeploymentMBean[] var6 = apps;
      int var7 = apps.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         AppDeploymentMBean app = var6[var8];
         if (!((AbstractDescriptorBean)app)._isTransient()) {
            if (jsonAttributesString != null && !jsonAttributesString.isEmpty()) {
               JSONObject readJson = new JSONObject(jsonAttributesString);
               if (!(parent instanceof AbstractDescriptorBean)) {
                  throw new ManagementException("Passed parent should implement AbstractDescriptorBean interface");
               }

               String parentXmlName = ((AbstractDescriptorBean)parent)._getBeanElementName();
               JSONObject readJsonObject = getJSONObjectFromAttributesJson(parentXmlName, parent.getName(), readJson);
               if (readJsonObject != null) {
                  String appXmlName = ((AbstractDescriptorBean)app)._getBeanElementName();
                  Object appsFromJson = readJsonObject.opt(appXmlName);
                  if (appsFromJson != null) {
                     setAppAttributesFromJson(appsFromJson, app);
                  }
               }
            }

            String appSrcPath = app.getSourcePath();
            if (appSrcPath != null && appSrcPath.length() > 0) {
               ZipEntry appZipEntry = getZipEntry(appSrcPath, pArchive);
               Loggable l;
               if (appZipEntry != null) {
                  File toFile = getAppFileToWrite(domain, parent, appSrcPath);
                  if (toFile.exists()) {
                     l = ImportExportLogger.logFailToOverWriteImportedFileLoggable(app.getName(), toFile.getCanonicalPath());
                     l.log();
                     throw new ManagementException(l.getMessage());
                  }

                  File fromFile = Paths.get(DomainDir.getPendingDir(), appZipEntry.getName()).toFile();
                  if (fromFile.isDirectory()) {
                     FileUtils.copyPreservePermissions(fromFile, toFile);
                  } else {
                     FileUtils.writeToFile(pArchive.getInputStream(appZipEntry), toFile);
                  }

                  writtenFileSet.add(toFile);
                  app.setStagingMode("stage");
                  app.setSourcePath(toFile.getCanonicalPath());
                  if (fromFile != null && fromFile.exists()) {
                     FileUtils.remove(fromFile);
                  }

                  String appAbsPlanPath = app.getAbsolutePlanPath();
                  if (appAbsPlanPath != null && appAbsPlanPath.length() > 0) {
                     String appPlanPath = app.getPlanPath();
                     ZipEntry planZipEntry = getZipEntry(appPlanPath, pArchive);
                     if (planZipEntry != null) {
                        toFile = getAppFileToWrite(domain, parent, appAbsPlanPath);
                        if (toFile.exists()) {
                           Loggable l = ImportExportLogger.logFailToOverWriteImportedFileLoggable(app.getName(), toFile.getCanonicalPath());
                           l.log();
                           throw new ManagementException(l.getMessage());
                        }

                        fromFile = Paths.get(DomainDir.getPendingDir(), planZipEntry.getName()).toFile();
                        FileUtils.writeToFile(pArchive.getInputStream(planZipEntry), toFile);
                        writtenFileSet.add(toFile);
                        String appPlanDir = app.getPlanDir();
                        if (appPlanDir != null && !appPlanDir.isEmpty()) {
                           app.setPlanDir(toFile.getParent());
                        }

                        app.setPlanPath(toFile.getCanonicalPath());
                        FileUtils.copyPreservePermissions(fromFile, new File(app.getLocalPlanPath()));
                        fromFile.delete();
                     }
                  }
               } else {
                  String stagingMode = app.getStagingMode();
                  if (stagingMode != AppDeploymentMBean.DEFAULT_STAGE && stagingMode != "nostage" && stagingMode != "external_stage") {
                     if (stagingMode == "stage") {
                        l = ImportExportLogger.logStagedApplicationNotFoundLoggable(app.getName(), stagingMode);
                        l.log();
                        throw new ManagementException(l.getMessage());
                     }
                  } else if (!Files.exists(Paths.get(appSrcPath), new LinkOption[0])) {
                     l = ImportExportLogger.logApplicationNotFoundLoggable(app.getName(), stagingMode, appSrcPath);
                     l.log();
                     throw new ManagementException(l.getMessage());
                  }
               }
            }
         }
      }

   }

   public static void importSystemResources(SystemResourceMBean[] systemResourceMBeans, String keyFile, Set writtenFileSet, DomainMBean domain, String attributesString) throws SecurityException, Exception {
      SystemResourceMBean[] var5 = systemResourceMBeans;
      int var6 = systemResourceMBeans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         SystemResourceMBean sr = var5[var7];
         if (!((AbstractDescriptorBean)sr)._isTransient()) {
            Path sysResourcePendingPath = Paths.get(DomainDir.getPendingDir(), sr.getDescriptorFileName());
            String systemResourceDesc = new String(Files.readAllBytes(sysResourcePendingPath));
            String newSystemResource = ExportCustomizeableValuesHelper.setAttributesOnSystemResourceObject(domain, sr, sr.getName(), attributesString, systemResourceDesc);
            handleEncryptedAttributes(sr, newSystemResource == null ? systemResourceDesc : newSystemResource, true, keyFile);
            newSystemResource = DescriptorUtils.toString(sr.getResource());
            Path toCreate = Paths.get(DomainDir.getPendingDir(), removeRootFromPath(sr.getDescriptorFileName()));
            FileUtils.writeToFile(new ByteArrayInputStream(newSystemResource.getBytes()), toCreate.toFile());
            writtenFileSet.add(toCreate.toFile());
         }
      }

   }

   public static class ImpExpSecurityServiceImpl implements SecurityService {
      private ClearOrEncryptedService encryptionService = null;
      private static ImpExpSecurityServiceImpl instance = null;

      public static ImpExpSecurityServiceImpl getInstance() {
         if (instance == null) {
            instance = new ImpExpSecurityServiceImpl();
         }

         return instance;
      }

      private ImpExpSecurityServiceImpl() {
         System.setProperty("weblogic.management.confirmKeyfileCreation", "true");
      }

      public boolean isEncrypted(byte[] bEncrypted) throws DescriptorException {
         return getInstance().encryptionService.isEncryptedBytes(bEncrypted);
      }

      public byte[] encrypt(String sValue) throws DescriptorException {
         return getInstance().encryptionService.encrypt(sValue).getBytes();
      }

      public String decrypt(byte[] bEncrypted) throws DescriptorException {
         ClearOrEncryptedService domainEncrService = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());

         try {
            ClearOrEncryptedService ImpExpSecService = getInstance().encryptionService;
            byte[] decryptedPassword = ImpExpSecService.decryptBytes(bEncrypted);
            return (new String(bEncrypted)).compareTo(new String(decryptedPassword)) == 0 ? new String(domainEncrService.decryptBytes(bEncrypted)) : new String(decryptedPassword);
         } catch (EncryptionServiceException var5) {
            return new String(domainEncrService.decryptBytes(bEncrypted));
         }
      }

      public void createEncryptionService(String keyFile) throws IOException {
         if (keyFile != null && keyFile.length() > 0) {
            String password = new String(Files.readAllBytes(Paths.get(keyFile)));
            getInstance().encryptionService = UserConfigFileManager.getEncryptedService(ImportExportHelper.CONFIG_TO_SCRIPT_SECRET_FILE, password);
         } else {
            getInstance().encryptionService = UserConfigFileManager.getEncryptedService(ImportExportHelper.CONFIG_TO_SCRIPT_SECRET_FILE, (String)null);
         }

      }
   }
}
