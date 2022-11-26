package weblogic.management.mbeans.custom;

import com.bea.xml.XmlValidationError;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.configuration.ConfigurationExtensionMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.internal.PendingDirectoryManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.DescriptorInfo;
import weblogic.management.provider.internal.DescriptorInfoUtils;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ConfigurationExtension extends ConfigurationMBeanCustomizer {
   private static final String SCHEMA_VALIDATION_ENABLED_PROP = "weblogic.configuration.schemaValidationEnabled";
   private static final boolean schemaValidationEnabled = getBooleanProperty("weblogic.configuration.schemaValidationEnabled", true);
   private DescriptorBean extensionRootBean;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ConfigurationExtension(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   protected Descriptor loadDescriptor(DescriptorManager dm, InputStream in, List holder) throws Exception {
      return dm.createDescriptor(in, holder);
   }

   public synchronized DescriptorBean getExtensionRoot(Class descriptorClass, String desciptorAttribute, String subDir) {
      if (this.extensionRootBean != null) {
         return this.extensionRootBean;
      } else {
         InputStream is = null;
         String configFilename = null;

         DescriptorInfo descInfo;
         try {
            String errorMsg;
            try {
               ConfigurationExtensionMBean extensionBean = (ConfigurationExtensionMBean)this.getMbean();
               Descriptor rootDescriptor = extensionBean.getDescriptor();
               errorMsg = null;
               boolean editing = rootDescriptor.isEditable();
               String preferredPendingRoot = null;
               if (rootDescriptor instanceof DescriptorImpl) {
                  preferredPendingRoot = ((DescriptorImpl)rootDescriptor).getPreferredRoot();
               }

               PendingDirectoryManager pendingDirMgr = PendingDirectoryManager.getInstance(preferredPendingRoot);
               DescriptorImpl descImpl = (DescriptorImpl)rootDescriptor;
               Map context = descImpl.getContext();
               Boolean loadExtension = (Boolean)context.get("DescriptorExtensionLoad");
               String descriptorFilename;
               if (loadExtension != null && !loadExtension) {
                  descriptorFilename = null;
                  return descriptorFilename;
               }

               descriptorFilename = extensionBean.getDescriptorFileName();
               configFilename = DomainDir.getConfigDir() + File.separator + descriptorFilename;
               String subDirFilename = null;
               String subDirConfigFilename = null;
               if (subDir != null) {
                  subDirFilename = subDir + File.separator + descriptorFilename;
                  subDirConfigFilename = DomainDir.getConfigDir() + File.separator + subDirFilename;
               }

               DescriptorManager dm = null;
               Descriptor extensionDescriptor;
               if (!editing && (loadExtension == null || !loadExtension)) {
                  String fileName = DomainDir.getConfigDir() + File.separator + descriptorFilename;
                  is = new FileInputStream(fileName);
                  dm = DescriptorManagerHelper.getDescriptorManager(false);
                  ArrayList errs = new ArrayList();
                  extensionDescriptor = this.loadDescriptor((DescriptorManager)dm, (InputStream)is, errs);
                  this.checkErrors(fileName, errs);
               } else {
                  if (!extensionBean.isSet("DescriptorFileName")) {
                     extensionBean.setDescriptorFileName(descriptorFilename);
                  }

                  EditableDescriptorManager edm = (EditableDescriptorManager)DescriptorManagerHelper.getDescriptorManager(true);
                  dm = edm;
                  boolean deleted = false;
                  Iterator iter = DescriptorInfoUtils.getDeletedDescriptorInfos(rootDescriptor);

                  String descriptorFileNameKey;
                  while(iter != null && iter.hasNext()) {
                     DescriptorInfo deletedDescInfo = (DescriptorInfo)iter.next();
                     descriptorFileNameKey = deletedDescInfo.getConfigurationExtension().getDescriptorFileName();
                     if (descriptorFilename.equals(descriptorFileNameKey)) {
                        deleted = true;
                     }
                  }

                  Map temporaryFiles = DescriptorInfoUtils.getExtensionTemporaryFiles(rootDescriptor);
                  descriptorFileNameKey = "config/" + descriptorFilename.replaceAll("\\\\", "/");
                  ArrayList errs;
                  if (!deleted && (pendingDirMgr.fileExists(descriptorFilename) || (new File(configFilename)).exists())) {
                     is = pendingDirMgr.getFileAsStream(descriptorFilename);
                     errs = new ArrayList();
                     extensionDescriptor = this.loadDescriptor(edm, (InputStream)is, errs);
                     this.checkErrors(descriptorFilename, errs);
                  } else if (deleted || subDir == null || !pendingDirMgr.fileExists(subDirFilename) && !(new File(subDirConfigFilename)).exists()) {
                     if (!deleted && temporaryFiles != null && temporaryFiles.containsKey(descriptorFileNameKey)) {
                        is = new FileInputStream((String)temporaryFiles.get(descriptorFileNameKey));
                        errs = new ArrayList();
                        extensionDescriptor = this.loadDescriptor(edm, (InputStream)is, errs);
                        this.checkErrors(descriptorFilename, errs);
                     } else if (extensionBean instanceof AbstractDescriptorBean && ((AbstractDescriptorBean)extensionBean)._isTransient() && this.getDelegateMBean(extensionBean) != null) {
                        SystemResourceMBean delegateDescriptorBean = this.getDelegateMBean(extensionBean);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        Descriptor delegateDescriptor = delegateDescriptorBean.getResource().getDescriptor();
                        boolean modified = delegateDescriptor.isModified();
                        edm.writeDescriptorAsXML(delegateDescriptor, os, "UTF-8");
                        ((DescriptorImpl)delegateDescriptor).setModified(modified);
                        ArrayList errs = new ArrayList();
                        byte[] ba = os.toByteArray();
                        extensionDescriptor = this.loadDescriptor(edm, new ByteArrayInputStream(ba), errs);
                        this.checkErrors(descriptorFilename, errs);
                     } else {
                        if (descriptorClass == null) {
                           errs = null;
                           return errs;
                        }

                        this.extensionRootBean = edm.createDescriptorRoot(descriptorClass, "UTF-8").getRootBean();
                        extensionDescriptor = this.extensionRootBean.getDescriptor();
                        RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
                        if (rt != null) {
                           Descriptor rtTree = rt.getDomain().getDescriptor();
                           Iterator it = DescriptorInfoUtils.getNotFoundDescriptorInfos(rtTree);

                           while(it != null && it.hasNext()) {
                              DescriptorInfo rtDescInfo = (DescriptorInfo)it.next();
                              ConfigurationExtensionMBean rtExtBean = rtDescInfo.getConfigurationExtension();
                              if (extensionBean.getName().equals(rtExtBean.getName())) {
                                 ((DescriptorImpl)extensionDescriptor).setModified(false);
                              }
                           }
                        }
                     }
                  } else {
                     is = pendingDirMgr.getFileAsStream(subDirFilename);
                     errs = new ArrayList();
                     extensionDescriptor = this.loadDescriptor(edm, (InputStream)is, errs);
                     this.checkErrors(subDirFilename, errs);
                  }
               }

               this.extensionRootBean = extensionDescriptor.getRootBean();
               DescriptorInfo descInfo = new DescriptorInfo(extensionDescriptor, descriptorClass, this.extensionRootBean, (DescriptorManager)dm, extensionBean);
               DescriptorInfoUtils.addDescriptorInfo(descInfo, descImpl);
               DescriptorInfoUtils.setDescriptorConfigExtension(extensionDescriptor, extensionBean, desciptorAttribute);
               DescriptorBean var53 = this.extensionRootBean;
               return var53;
            } catch (FileNotFoundException var41) {
               ConfigurationExtensionMBean extensionBean = (ConfigurationExtensionMBean)this.getMbean();
               errorMsg = extensionBean.getDescriptorFileName();
               String fileName = DomainDir.getConfigDir() + File.separator + errorMsg;
               File f = new File(fileName);
               if (f.exists()) {
                  ManagementLogger.logCouldNotFindSystemResource(this.getMbean().getName());
                  descInfo = new DescriptorInfo((Descriptor)null, descriptorClass, (DescriptorBean)null, (DescriptorManager)null, (ConfigurationExtensionMBean)this.getMbean());
                  DescriptorInfoUtils.addNotFoundDescriptorInfo(descInfo, (DescriptorImpl)this.getMbean().getDescriptor());
               }

               descInfo = null;
            } catch (Exception var42) {
               String existingErrorMsg = var42.getMessage() != null ? var42.getMessage() + ". " : "";
               errorMsg = existingErrorMsg + "Error encountered in file: " + configFilename;
               throw new ManagementRuntimeException(errorMsg, var42);
            }
         } finally {
            if (is != null) {
               try {
                  ((InputStream)is).close();
               } catch (Exception var40) {
               }
            }

         }

         return descInfo;
      }
   }

   private void checkErrors(String fileName, ArrayList errs) {
      if (errs.size() > 0) {
         Iterator i = errs.iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if (o instanceof XmlValidationError) {
               XmlValidationError ve = (XmlValidationError)o;
               ManagementLogger.logConfigurationValidationProblem(fileName, ve.getMessage());
            } else {
               ManagementLogger.logConfigurationValidationProblem(fileName, o.toString());
            }
         }

         if (schemaValidationEnabled) {
            String option = "-Dweblogic.configuration.schemaValidationEnabled=false";
            Loggable l = ManagementLogger.logConfigurationSchemaFailureLoggable(fileName, option);
            throw new RuntimeException(l.getMessage());
         }
      }

   }

   public String getFileNamePrefix() {
      return "";
   }

   public static boolean getBooleanProperty(String prop, boolean _default) {
      String value = System.getProperty(prop);
      return value != null ? Boolean.parseBoolean(value) : _default;
   }

   private SystemResourceMBean getDelegateMBean(ConfigurationExtensionMBean extensionBean) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      Method method = extensionBean.getClass().getMethod("_getDelegateBean");
      Object delegate = method.invoke(extensionBean);
      return delegate != null && delegate instanceof SystemResourceMBean ? (SystemResourceMBean)delegate : null;
   }
}
