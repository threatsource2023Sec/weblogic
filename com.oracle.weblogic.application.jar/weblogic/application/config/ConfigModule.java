package weblogic.application.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.j2ee.descriptor.wl.ConfigurationSupportBean;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.ModuleProviderBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public abstract class ConfigModule implements Module, UpdateListener {
   private static boolean VERBOSE = false;
   private final ModuleProviderBean provider;
   private final String uri;
   private final String descriptorURI;
   private DescriptorManager manager;
   private Descriptor currentDescriptor;
   private ApplicationContextInternal appCtx;
   private final boolean strictLookup;

   public ConfigModule(ModuleProviderBean provider, CustomModuleBean bean) throws ModuleException {
      this.provider = provider;
      this.uri = bean.getUri();
      if (provider.getBindingJarUri() == null) {
         throw new ModuleException("Your module-provider needs to specify a binding-jar-uri in its weblogic-extension.xml");
      } else {
         ConfigurationSupportBean csb = bean.getConfigurationSupport();
         if (csb == null) {
            throw new ModuleException("Your config module must include configuration-support in the weblogic-extension.xml.");
         } else {
            this.descriptorURI = csb.getBaseUri();
            this.strictLookup = true;
         }
      }
   }

   public ConfigModule(String moduleUri, String descriptorUri) {
      this.provider = null;
      this.uri = moduleUri;
      this.descriptorURI = descriptorUri;
      this.strictLookup = false;
   }

   public String getId() {
      return this.uri;
   }

   public String getType() {
      return WebLogicModuleType.MODULETYPE_CONFIG;
   }

   public boolean acceptURI(String updateUri) {
      return this.descriptorURI.equals(updateUri);
   }

   public DescriptorBean[] getDescriptors() {
      return this.currentDescriptor == null ? new DescriptorBean[0] : new DescriptorBean[]{this.currentDescriptor.getRootBean()};
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return new ComponentRuntimeMBean[0];
   }

   public GenericClassLoader init(ApplicationContext ac, GenericClassLoader gcl, UpdateListener.Registration reg) {
      this.appCtx = (ApplicationContextInternal)ac;
      reg.addUpdateListener(this);
      return gcl;
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) {
      this.init(appCtx, gcl, reg);
   }

   private InputStream findResource(VirtualJarFile vjf, String path) throws IOException {
      ZipEntry ze = vjf.getEntry(path);
      return ze == null ? null : vjf.getInputStream(ze);
   }

   protected InputStream findResource(String path) throws IOException {
      VirtualJarFile vjf = null;

      try {
         vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile(this.uri);
      } catch (IOException var4) {
         vjf = null;
      }

      if (vjf != null) {
         InputStream in = this.findResource(vjf, path);
         if (in != null) {
            return in;
         }

         vjf.close();
      }

      vjf = this.appCtx.getApplicationFileManager().getVirtualJarFile();
      return this.findResource(vjf, path);
   }

   protected Descriptor parseDescriptor() throws ModuleException {
      InputStream is = null;

      Descriptor var3;
      try {
         is = this.findResource(this.descriptorURI);
         Descriptor d;
         if (is == null) {
            if (this.strictLookup) {
               throw new ModuleException("Unable to find descriptor at " + this.descriptorURI);
            }

            d = null;
            return d;
         }

         d = this.manager.createDescriptor(is);
         if (VERBOSE) {
            this.manager.writeDescriptorAsXML(d, System.out);
         }

         var3 = d;
      } catch (IOException var13) {
         throw new ModuleException(var13);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (Exception var12) {
            }
         }

      }

      return var3;
   }

   public void prepare() throws ModuleException {
      if (this.provider != null) {
         this.manager = new DescriptorManager(this.provider.getBindingJarUri());
      } else {
         this.manager = new DescriptorManager();
      }

      this.currentDescriptor = this.parseDescriptor();
      if (this.currentDescriptor != null) {
         this.readDescriptor(this.currentDescriptor.getRootBean());
      }

   }

   public void activate() throws ModuleException {
   }

   public void start() throws ModuleException {
   }

   public void deactivate() throws ModuleException {
   }

   public void unprepare() throws ModuleException {
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
      reg.removeUpdateListener(this);
   }

   public void prepareUpdate(String uri) throws ModuleException {
      Descriptor proposedDescriptor = this.parseDescriptor();

      try {
         this.currentDescriptor.prepareUpdate(proposedDescriptor);
      } catch (DescriptorUpdateRejectedException var4) {
         throw new ModuleException(var4);
      } catch (DescriptorValidateException var5) {
         throw new ModuleException(var5);
      }
   }

   public void activateUpdate(String uri) throws ModuleException {
      try {
         this.currentDescriptor.activateUpdate();
      } catch (DescriptorUpdateFailedException var6) {
         throw new ModuleException(var6);
      } finally {
         this.updatedDescriptor(this.currentDescriptor.getRootBean());
      }

   }

   public void rollbackUpdate(String uri) {
      this.currentDescriptor.rollbackUpdate();
   }

   public void remove() throws ModuleException {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
   }

   public void forceProductionToAdmin() {
   }

   public abstract void readDescriptor(DescriptorBean var1) throws ModuleException;

   public abstract void updatedDescriptor(DescriptorBean var1) throws ModuleException;
}
