package weblogic.coherence.container.server;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContext;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.UpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.jndi.WLContext;
import weblogic.management.DomainDir;
import weblogic.management.configuration.CoherenceCacheConfigMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.utils.classloaders.GenericClassLoader;

public class CoherenceCacheConfigModule implements Module {
   private static final String JNDI_ROOT = "cache-config";
   private String id;
   private CoherenceCacheConfigMBean bean;
   private ApplicationContext appCtx;
   private URI uriCacheConfig;
   private String jndiName;
   private boolean bound;
   private static Context ctx;

   public CoherenceCacheConfigModule(String id) {
      this.id = id;
   }

   public CoherenceCacheConfigModule(String id, CoherenceCacheConfigMBean bean) {
      this(id);
      this.bean = bean;
   }

   public String getId() {
      return this.id;
   }

   public String getType() {
      return "coherence cache configuration";
   }

   public ComponentRuntimeMBean[] getComponentRuntimeMBeans() {
      return null;
   }

   public DescriptorBean[] getDescriptors() {
      return new DescriptorBean[0];
   }

   public void initUsingLoader(ApplicationContext appCtx, GenericClassLoader gcl, UpdateListener.Registration reg) throws ModuleException {
      this.init(appCtx, gcl, reg);
   }

   public GenericClassLoader init(ApplicationContext appCtx, GenericClassLoader parent, UpdateListener.Registration reg) throws ModuleException {
      this.appCtx = appCtx;
      Context envCtx = null;

      try {
         envCtx = getContext();
         envCtx.lookup("/cache-config");
      } catch (NameNotFoundException var9) {
         try {
            envCtx.createSubcontext("cache-config");
         } catch (NamingException var8) {
            throw new AssertionError(var8);
         }
      } catch (NamingException var10) {
         throw new AssertionError(var10);
      }

      if (this.bean != null) {
         String configFile = this.bean.getRuntimeCacheConfigurationUri();
         if (configFile != null && !configFile.isEmpty()) {
            try {
               this.uriCacheConfig = configFile.startsWith("coherence" + File.separator) ? (new File(DomainDir.getConfigDir() + File.separator + configFile)).toURI() : (new URL(configFile)).toURI();
            } catch (Exception var7) {
               this.uriCacheConfig = (new File(configFile)).toURI();
            }

            this.jndiName = this.bean.getJNDIName() == null ? this.bean.getName() : this.bean.getJNDIName();
         }
      }

      return parent;
   }

   public void prepare() throws ModuleException {
      if (!this.bound && this.uriCacheConfig != null) {
         try {
            Context envCtx = getContext();
            Context cacheConfigCtx = (Context)envCtx.lookup("cache-config");
            ((WLContext)cacheConfigCtx).bind(this.jndiName, this.uriCacheConfig, true);
         } catch (NamingException var3) {
            throw new ModuleException(var3.toString(), var3);
         }

         this.bound = true;
      }
   }

   public void activate() throws ModuleException {
   }

   public void start() throws ModuleException {
   }

   public void deactivate() throws ModuleException {
   }

   public void unprepare() throws ModuleException {
      if (this.bound) {
         try {
            Context envCtx = getContext();
            Context cacheConfigCtx = (Context)envCtx.lookup("cache-config");
            cacheConfigCtx.unbind(this.jndiName);
         } catch (NamingException var3) {
            throw new ModuleException(var3.toString(), var3);
         }

         this.bound = true;
      }
   }

   public void destroy(UpdateListener.Registration reg) throws ModuleException {
   }

   public void remove() throws ModuleException {
   }

   public void adminToProduction() {
   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws ModuleException {
   }

   public void forceProductionToAdmin() throws ModuleException {
   }

   protected static Context getContext() throws NamingException {
      if (ctx == null) {
         Properties props = new Properties();
         props.setProperty("weblogic.jndi.createIntermediateContexts", Boolean.TRUE.toString());
         props.setProperty("weblogic.jndi.replicateBindings", Boolean.TRUE.toString());
         ctx = new InitialContext(props);
      }

      return ctx;
   }
}
