package weblogic.ejb.container.metadata;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.persistence.InstalledPersistence;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public final class EJBDescriptorBeanUtils {
   private static final EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();

   public static EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jf) throws Exception {
      return createDescriptorFromJarFile(jf, false);
   }

   public static EjbDescriptorBean createDescriptorFromJarFile(VirtualJarFile jf, boolean validate) throws Exception {
      EjbDescriptorBean ejbDescriptor = new EjbDescriptorBean();
      ProcessorFactory procFac = new ProcessorFactory();
      procFac.setValidating(validate);

      try {
         ejbDescriptor = EjbDescriptorFactory.createDescriptorFromJarFile(jf);
         loadWeblogicRDBMSJarBeans(ejbDescriptor, jf, procFac, validate);
      } catch (FileNotFoundException var6) {
         if (validate) {
            throw var6;
         }
      } catch (XMLParsingException var7) {
         ejbDescriptor.setParsingErrorMessage(var7.toString());
         if (validate) {
            throw var7;
         }
      } catch (XMLProcessingException var8) {
         ejbDescriptor.setParsingErrorMessage(var8.toString());
         if (validate) {
            throw var8;
         }
      } catch (Exception var9) {
         String emsg = StackTraceUtilsClient.throwable2StackTrace(var9);
         ejbDescriptor.setParsingErrorMessage(emsg);
         if (validate) {
            throw var9;
         }
      }

      return ejbDescriptor;
   }

   public static void loadWeblogicRDBMSJarBeans(EjbDescriptorBean ejbDescriptor, VirtualJarFile jf, ProcessorFactory procFac, boolean validate) throws Exception {
      Set loadedFiles = new HashSet();
      Iterator var5 = getEJBNames(ejbDescriptor).iterator();

      while(var5.hasNext()) {
         String ejbName = (String)var5.next();
         PersistenceBean persist = getPersistenceBean(ejbName, ejbDescriptor);
         if (persist != null && persist.isPersistenceUseSet()) {
            String id = persist.getPersistenceUse().getTypeIdentifier();
            if ("WebLogic_CMP_RDBMS".equals(id)) {
               String ver = persist.getPersistenceUse().getTypeVersion();
               String fileName = getRDBMSDescriptorFileName(persist);
               if (fileName != null && !loadedFiles.contains(fileName)) {
                  loadRDBMSDescriptor(ejbDescriptor, jf, ejbName, fileName, ver, procFac, validate);
                  loadedFiles.add(fileName);
               }
            }
         }
      }

   }

   public static Set getCMPEJBNames(EjbDescriptorBean ejbDescriptor) {
      Set ejbNames = new HashSet();
      if (ejbDescriptor.getEjbJarBean() != null) {
         EnterpriseBeansBean beans = ejbDescriptor.getEjbJarBean().getEnterpriseBeans();
         if (beans != null) {
            EntityBeanBean[] var3 = beans.getEntities();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               EntityBeanBean ebb = var3[var5];
               if ("Container".equals(ebb.getPersistenceType()) && ebb.getEjbName() != null) {
                  ejbNames.add(ebb.getEjbName());
               }
            }
         }
      }

      return ejbNames;
   }

   private static Set getEJBNames(EjbDescriptorBean ejbDescriptor) {
      Set ejbNames = new HashSet();
      if (ejbDescriptor.getEjbJarBean() != null) {
         EnterpriseBeansBean beans = ejbDescriptor.getEjbJarBean().getEnterpriseBeans();
         if (beans != null) {
            EntityBeanBean[] var3 = beans.getEntities();
            int var4 = var3.length;

            int var5;
            for(var5 = 0; var5 < var4; ++var5) {
               EntityBeanBean ebb = var3[var5];
               ejbNames.add(ebb.getEjbName());
            }

            SessionBeanBean[] var7 = beans.getSessions();
            var4 = var7.length;

            for(var5 = 0; var5 < var4; ++var5) {
               SessionBeanBean sbb = var7[var5];
               ejbNames.add(sbb.getEjbName());
            }

            MessageDrivenBeanBean[] var8 = beans.getMessageDrivens();
            var4 = var8.length;

            for(var5 = 0; var5 < var4; ++var5) {
               MessageDrivenBeanBean mdbb = var8[var5];
               ejbNames.add(mdbb.getEjbName());
            }
         }
      }

      return ejbNames;
   }

   public static WeblogicEnterpriseBeanBean getWeblogicEnterpriseBean(String ejbName, EjbDescriptorBean ejbDescriptor) {
      if (ejbDescriptor.getWeblogicEjbJarBean() != null) {
         WeblogicEnterpriseBeanBean[] beans = ejbDescriptor.getWeblogicEjbJarBean().getWeblogicEnterpriseBeans();
         if (beans != null) {
            WeblogicEnterpriseBeanBean[] var3 = beans;
            int var4 = beans.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               WeblogicEnterpriseBeanBean webb = var3[var5];
               if (ejbName.equals(webb.getEjbName())) {
                  return webb;
               }
            }
         }
      }

      return null;
   }

   public static EnterpriseBeanBean getEnterpriseBean(String ejbName, EjbDescriptorBean ejbDescriptor) {
      if (ejbDescriptor.getEjbJarBean() != null) {
         EnterpriseBeansBean ebeans = ejbDescriptor.getEjbJarBean().getEnterpriseBeans();
         if (ebeans != null) {
            SessionBeanBean[] var3 = ebeans.getSessions();
            int var4 = var3.length;

            int var5;
            for(var5 = 0; var5 < var4; ++var5) {
               SessionBeanBean sbb = var3[var5];
               if (ejbName.equals(sbb.getEjbName())) {
                  return sbb;
               }
            }

            EntityBeanBean[] var7 = ebeans.getEntities();
            var4 = var7.length;

            for(var5 = 0; var5 < var4; ++var5) {
               EntityBeanBean ebb = var7[var5];
               if (ejbName.equals(ebb.getEjbName())) {
                  return ebb;
               }
            }

            MessageDrivenBeanBean[] var8 = ebeans.getMessageDrivens();
            var4 = var8.length;

            for(var5 = 0; var5 < var4; ++var5) {
               MessageDrivenBeanBean mdb = var8[var5];
               if (ejbName.equals(mdb.getEjbName())) {
                  return mdb;
               }
            }
         }
      }

      return null;
   }

   public static void loadRDBMSDescriptor(EjbDescriptorBean ejbDescriptor, VirtualJarFile jf, String ejbName, String fileName, String ver, ProcessorFactory procFac, boolean validate) throws Exception {
      ZipEntry ze = jf.getEntry(fileName);
      if (ze == null) {
         EJBLogger.logCouldNotFindSpecifiedRDBMSDescriptorInJarFile(ejbName, fileName, jf.getName());
         if (validate) {
            throw new FileNotFoundException(fmt.cmpFileNotFound(ejbName, fileName, jf.getName()));
         }
      } else {
         InstalledPersistence ip = new InstalledPersistence();
         PersistenceType pt = ip.getInstalledType("WebLogic_CMP_RDBMS", ver);
         if (pt != null) {
            CMPDeployer cmpDeployer = pt.getCmpDeployer();
            loadDescriptor(ejbDescriptor, cmpDeployer, jf, fileName, ejbName, procFac);
         }
      }

   }

   private static void loadDescriptor(EjbDescriptorBean ejbDescriptor, CMPDeployer deployer, VirtualJarFile vjar, String fileName, String ejbName, ProcessorFactory procFac) throws Exception {
      deployer.parseXML(vjar, fileName, ejbName, procFac, ejbDescriptor);
   }

   public static PersistenceBean getPersistenceBean(String ejbName, EjbDescriptorBean ejbDescriptor) {
      WeblogicEnterpriseBeanBean bean = getWeblogicEnterpriseBean(ejbName, ejbDescriptor);
      if (bean != null && bean.isEntityDescriptorSet()) {
         EntityDescriptorBean ed = bean.getEntityDescriptor();
         if (ed.isPersistenceSet()) {
            return ed.getPersistence();
         }
      }

      return null;
   }

   private static String getRDBMSDescriptorFileName(PersistenceBean persist) {
      PersistenceUseBean use = persist.getPersistenceUse();
      return persist.isPersistenceUseSet() ? use.getTypeStorage() : null;
   }
}
