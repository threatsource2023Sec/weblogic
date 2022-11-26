package weblogic.xml.registry;

import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.XmlBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.GenericClassLoader;

public class XMLModule implements Module {
   private static boolean debug = false;
   private AppDeploymentMBean deployableMBean;
   private ApplicationContext appCtx;
   private XmlBean xmlDD;
   private final String WEBLOGIC_APPLICATION_DESCRIPTOR = "weblogic-application.xml";
   private final String REGISTRY_DIR = "lib/xml/registry";
   private String[] changedFiles = null;
   private boolean loadDescriptorEnabled = false;
   private boolean cleanUp = false;
   private boolean cleanUpCache = false;
   private boolean reInitialize = true;
   private ClassLoader classLoader;

   public XMLModule(XmlBean xmlDD) {
      this.xmlDD = xmlDD;
   }

   public String getId() {
      return "weblogic.xml.registry.XMLModule";
   }

   public String getType() {
      return null;
   }

   public DescriptorBean[] getDescriptors() {
      return this.xmlDD != null ? new DescriptorBean[]{(DescriptorBean)this.xmlDD} : new DescriptorBean[0];
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public void initUsingLoader(ApplicationContext ac, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.init(ac, parent, reg);
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.classLoader = parent;
      this.appCtx = appCtx;
      this.deployableMBean = appCtx.getAppDeploymentMBean();
      return parent;
   }

   public void setDelta(String[] changedFiles, long[] lastModifiedTimes) {
      this.changedFiles = changedFiles;
      if (debug) {
         Debug.say("ChangedFiles is null " + (changedFiles == null));
      }

      if (changedFiles == null) {
         this.reInitialize = true;
      } else {
         if (debug) {
            Debug.say("Changed Files length is : " + changedFiles.length);
         }

         for(int i = 0; i < changedFiles.length; ++i) {
            if (changedFiles[i].endsWith("weblogic-application.xml")) {
               if (debug) {
                  Debug.say("----> weblogic-application.xml descriptor has changes");
               }

               this.reInitialize = true;
               this.cleanUp = true;
            }

            if (changedFiles[i].startsWith("lib/xml/registry")) {
               this.cleanUpCache = true;
            }
         }

      }
   }

   public void prepare() throws ModuleException {
      if (this.cleanUp) {
         try {
            XMLRegistry.cleanUpAppScopedXMLRegistry(this.deployableMBean.getName());
         } catch (XMLRegistryException var4) {
            throw new ModuleException(var4.getMessage());
         }
      }

      if (this.reInitialize) {
         if (debug) {
            Debug.say("Preparing XMLModule ------>>>>>> \n ");
         }

         try {
            String pathName = ((ApplicationContextInternal)this.appCtx).getStagingPath();
            if (debug) {
               Debug.say("---------> Path is " + pathName);
            }

            XMLRegistry.initializeAppScopedXMLRegistry(this.xmlDD, this.deployableMBean, pathName);
         } catch (XMLRegistryException var3) {
            throw new ModuleException(var3.getMessage());
         }
      }

      if (!this.cleanUp && this.cleanUpCache) {
         try {
            XMLRegistry appScopedXMLRegistry = XMLRegistry.getXMLRegistry(this.appCtx.getApplicationId());
            appScopedXMLRegistry.cleanUpCache(this.changedFiles);
         } catch (XMLRegistryException var2) {
            throw new ModuleException(var2.getMessage());
         }
      }

      this.cleanUp = false;
      this.cleanUpCache = false;
      this.reInitialize = false;
   }

   public void unprepare() throws IllegalStateException, ModuleException {
      try {
         if (debug) {
            Debug.say("----> Rolling Back XML Module ");
         }

         XMLRegistry.cleanUpAppScopedXMLRegistry(this.deployableMBean.getName());
      } catch (XMLRegistryException var2) {
         throw new ModuleException(var2);
      } catch (Exception var3) {
         throw new ModuleException(var3);
      }
   }

   public void activate() {
   }

   public void start() {
   }

   public void destroy(UpdateListener.Registration reg) {
   }

   public void deactivate() throws IllegalStateException, ModuleException {
   }

   public void remove() throws IllegalStateException, ModuleException {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }
}
