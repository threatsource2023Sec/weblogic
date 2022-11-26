package weblogic.ejb.container.deployer;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.naming.ModuleRegistry;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.metadata.EJBDescriptorBeanUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.EjbDescriptorFactory;
import weblogic.ejb.spi.EjbDescriptorReader;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.logging.Loggable;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public class EJBMetadataHandler {
   private static final DebugLogger debugLogger;
   private final ModuleContext mc;
   private final Archive archive;
   private final VirtualJarFile[] autoRefLibJars;
   private EjbDescriptorBean desc;
   private EjbDescriptorBean proposedDesc;

   EJBMetadataHandler(ModuleContext mc, Archive archive, VirtualJarFile[] autoRefLibs) {
      this.mc = mc;
      this.archive = archive;
      this.autoRefLibJars = autoRefLibs;
   }

   boolean acceptURI(String u) {
      return this.getEjbDDName(u) != null;
   }

   private boolean isEJBDDUri(String uri) {
      return uri != null && uri.equals(this.archive.getStandardDescriptorRoot() + "ejb-jar.xml");
   }

   private boolean isWLEJBDDUri(String uri) {
      return uri != null && uri.equals(this.archive.getStandardDescriptorRoot() + "weblogic-ejb-jar.xml");
   }

   boolean prepareDescriptorUpdate(String uri) throws ModuleException {
      String descriptorName = this.getEjbDDName(uri);
      if (debugLogger.isDebugEnabled()) {
         debug("prepareDescriptorUpdate: EJB descriptorName = " + descriptorName);
      }

      if (descriptorName == null) {
         return false;
      } else {
         if (this.proposedDesc == null) {
            this.proposedDesc = this.parseDescriptors();

            try {
               this.processAnnotations(this.proposedDesc, false);
            } finally {
               if (this.archive instanceof EjbJarArchive) {
                  ((EjbJarArchive)this.archive).reset();
               }

            }
         }

         if (!this.isEJBDDUri(descriptorName) && !this.isWLEJBDDUri(descriptorName)) {
            try {
               EJBDescriptorBeanUtils.loadRDBMSDescriptor(this.proposedDesc, this.mc.getVirtualJarFile(), descriptorName, descriptorName, "6.0", new ProcessorFactory(), true);
            } catch (Exception var8) {
               throw new ModuleException(var8);
            }
         }

         try {
            Descriptor currentDesc = this.getDescriptor(this.desc, descriptorName);
            currentDesc.prepareUpdate(this.getDescriptor(this.proposedDesc, descriptorName));
            return true;
         } catch (DescriptorUpdateRejectedException var7) {
            throw new ModuleException(var7);
         }
      }
   }

   boolean activateDescriptorUpdate(String uri) throws ModuleException {
      String descriptorName = this.getEjbDDName(uri);
      if (debugLogger.isDebugEnabled()) {
         debug("activateDescriptorUpdate: EJB descriptorName = " + descriptorName);
      }

      if (descriptorName == null) {
         return false;
      } else {
         this.proposedDesc = null;
         Descriptor currentDesc = this.getDescriptor(this.desc, descriptorName);

         try {
            currentDesc.activateUpdate();
            EJBLogger.logActivatedDescriptorUpdate(descriptorName, "[" + this.mc.getName() + "]");
            return true;
         } catch (DescriptorUpdateFailedException var5) {
            throw new ModuleException(var5);
         }
      }
   }

   boolean rollbackDescriptorUpdate(String uri) {
      String descriptorName = this.getEjbDDName(uri);
      if (descriptorName == null) {
         return false;
      } else {
         this.proposedDesc = null;
         Descriptor currentDesc = this.getDescriptor(this.desc, descriptorName);
         currentDesc.rollbackUpdate();
         return true;
      }
   }

   public EjbDescriptorBean getEjbDescriptorBean() {
      if (this.desc == null) {
         throw new IllegalStateException("Descriptors haven't been loaded yet!");
      } else {
         return this.desc;
      }
   }

   public void processAnnotations() throws ModuleException {
      this.processAnnotations(this.getEjbDescriptorBean(), true);
   }

   private void processAnnotations(EjbDescriptorBean desc, boolean registerProcessedClasses) throws ModuleException {
      try {
         ModuleRegistry mr = registerProcessedClasses ? this.mc.getRegistry() : null;
         EjbDescriptorFactory.getEjbDescriptorReader().processAnnotations(desc, this.archive, mr);
      } catch (IOException var5) {
         Loggable l = EJBLogger.logErrorProcessingAnnotationsLoggable(var5.getMessage());
         throw new ModuleException(l.getMessageText(), var5);
      }
   }

   DescriptorBean[] getDescriptors() {
      return this.desc.getWeblogicEjbJarBean() == null ? new DescriptorBean[]{(DescriptorBean)this.desc.getEjbJarBean()} : new DescriptorBean[]{(DescriptorBean)this.desc.getEjbJarBean(), (DescriptorBean)this.desc.getWeblogicEjbJarBean()};
   }

   public EjbDescriptorBean loadDescriptors() throws ModuleException {
      this.desc = this.parseDescriptors();
      return this.desc;
   }

   private EjbDescriptorBean parseDescriptors() throws ModuleException {
      Loggable l;
      try {
         EjbDescriptorReader descReader = EjbDescriptorFactory.getEjbDescriptorReader();
         return descReader.loadDescriptors(this.mc, this.archive, this.autoRefLibJars);
      } catch (IOException var3) {
         l = EJBLogger.logErrorReadingDDLoggable(var3.getMessage());
         throw new ModuleException(l.getMessageText(), var3);
      } catch (XMLParsingException var4) {
         l = EJBLogger.logXmlParsingErrorLoggable(var4.getMessage());
         throw new ModuleException(l.getMessageText(), var4);
      } catch (XMLStreamException | XMLProcessingException var5) {
         l = EJBLogger.logXmlProcessingErrorLoggable(var5.getMessage());
         throw new ModuleException(l.getMessageText(), var5);
      } catch (Throwable var6) {
         var6.printStackTrace();
         l = EJBLogger.logErrorReadingDDLoggable(var6.getMessage());
         throw new ModuleException(l.getMessageText(), var6);
      }
   }

   private Descriptor getDescriptor(EjbDescriptorBean edb, String descName) {
      if (this.isEJBDDUri(descName)) {
         return ((DescriptorBean)edb.getEjbJarBean()).getDescriptor();
      } else {
         return this.isWLEJBDDUri(descName) ? ((DescriptorBean)edb.getWeblogicEjbJarBean()).getDescriptor() : ((DescriptorBean)edb.getWeblogicRdbmsJarBean(descName)).getDescriptor();
      }
   }

   private String getEjbDDName(String updateUri) {
      if (!updateUri.endsWith("xml")) {
         return null;
      } else {
         String candidate = updateUri.replace('\\', '/');
         ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(this.mc.getApplicationId());
         if (appCtx.isEar()) {
            candidate = candidate.substring(this.mc.getURI().length() + 1);
         }

         if (candidate.length() == 0) {
            return null;
         } else if (!this.isEJBDDUri(candidate) && !this.isWLEJBDDUri(candidate)) {
            WeblogicEjbJarBean wlEjbJar = this.desc.getWeblogicEjbJarBean();
            if (wlEjbJar != null) {
               WeblogicEnterpriseBeanBean[] var5 = wlEjbJar.getWeblogicEnterpriseBeans();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  WeblogicEnterpriseBeanBean webb = var5[var7];
                  if (webb.isEntityDescriptorSet()) {
                     EntityDescriptorBean ed = webb.getEntityDescriptor();
                     if (ed.isPersistenceSet()) {
                        PersistenceBean pb = ed.getPersistence();
                        if (pb.isPersistenceUseSet()) {
                           PersistenceUseBean pu = pb.getPersistenceUse();
                           String cmpDescriptorName = pu.getTypeStorage();
                           cmpDescriptorName = cmpDescriptorName.replace('\\', '/');
                           if (candidate.equals(cmpDescriptorName)) {
                              EnterpriseBeansBean entb = this.desc.getEjbJarBean().getEnterpriseBeans();
                              EntityBeanBean[] var14 = entb.getEntities();
                              int var15 = var14.length;

                              for(int var16 = 0; var16 < var15; ++var16) {
                                 EntityBeanBean ebb = var14[var16];
                                 if (webb.getEjbName().equals(ebb.getEjbName())) {
                                    if (ebb.getCmpVersion().equals("2.x")) {
                                       return candidate;
                                    }

                                    return null;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            return null;
         } else {
            return candidate;
         }
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[EJBMetadataHandler] " + s);
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }
}
