package weblogic.server.embed.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.StartupClassMBean;
import weblogic.management.provider.internal.DescriptorHelper;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.server.embed.Config;
import weblogic.server.embed.EmbeddedServer;
import weblogic.server.embed.EmbeddedServerException;

public class DomainConfig implements Config {
   public static final String DOMAIN_NAME = "mydomain";
   public static final String SERVER_NAME = "myserver";
   protected static final String EMBED_SERVER_MARKER_FILE = ".embed-server-marker";
   protected static final String CONFIG_XML;
   static final String DEFAULT_USERNAME = "system";
   static final String USERNAME_PROPERTY = "weblogic.management.username";
   static final String PASSWORD_PROPERTY = "weblogic.management.password";
   static final String STARTUP_NAME = "embedded-server-startup-class";
   static final String STARTUP_CLASS;
   protected final File domainHome;
   private DomainMBean domain;
   private ServerMBean server;
   private String ts1;
   private String ts2;

   public DomainConfig(File home) {
      this.domainHome = home;
      System.setProperty("weblogic.RootDirectory", home.getAbsolutePath());
   }

   public void setSystemCredentials(String user, String pass) {
      if (EmbeddedServerImpl.get().getState() != EmbeddedServer.State.NEW) {
         throw new IllegalStateException("Cannot change credentials. Server already initialized.");
      } else {
         this.ts1 = user;
         this.ts2 = pass;
      }
   }

   public synchronized void ensureInitialized() throws EmbeddedServerException {
      if (this.domain == null) {
         this.initializeCredentials();
         this.domain = this.getDefaultDomain(this.ts1, this.ts2);
         this.server = this.domain.lookupServer("myserver");
         EmbeddedServerImpl.get().setState(EmbeddedServer.State.CONFIGURED);
      }
   }

   public void updateEditDomain(Config.Action editDomainAction) throws EmbeddedServerException {
      EmbeddedServerImpl eServer = EmbeddedServerImpl.get();
      eServer.assertServerHealthy();
      if (eServer.getState() != EmbeddedServer.State.STARTED) {
         this.ensureInitialized();
         editDomainAction.execute(this.domain, this.server);
      } else {
         eServer.ensureStarted();
         DomainMBean editDomain = null;

         try {
            editDomain = EmbeddedServerProvider.get().beginEdit(this.ts1, this.ts2);
         } catch (Exception var7) {
            throw new EmbeddedServerException("Error getting edit domain", var7);
         }

         try {
            editDomainAction.execute(editDomain, editDomain.lookupServer("myserver"));
         } catch (Throwable var6) {
            EmbeddedServerProvider.get().cancelEdit();
            throw new EmbeddedServerException("Error executing action on edit domain", var6);
         }

         try {
            EmbeddedServerProvider.get().saveEdit();
         } catch (Exception var5) {
            throw new EmbeddedServerException("Error saving updated domain", var5);
         }
      }

   }

   public void updateConfigDomain(Config.Action accessDomainAction) throws EmbeddedServerException {
      try {
         this.ensureInitialized();
         accessDomainAction.execute(this.domain, this.server);
      } catch (Throwable var3) {
         throw new EmbeddedServerException("Error executing action on config domain", var3);
      }
   }

   public File getDomainHome() {
      return this.domainHome;
   }

   private DomainMBean getDefaultDomain(String user, String pass) throws EmbeddedServerException {
      if (!this.domainHome.exists()) {
         this.domainHome.mkdirs();
      }

      File configXml = new File(this.domainHome, CONFIG_XML);
      if (configXml.exists()) {
         if (EmbeddedServerImpl.DEBUG) {
            log("Domain already exists " + this.domainHome);
         }

         File markerFile = new File(this.domainHome, ".embed-server-marker");
         if (!markerFile.exists()) {
            throw new EmbeddedServerException("Domain in NOT valid for embedded server mode");
         } else {
            return this.loadConfigXml();
         }
      } else {
         EmbeddedServerProvider.get().initializeManagementServiceClientBeanInfoAccess();
         if (EmbeddedServerImpl.DEBUG) {
            log("Creating domain config...");
         }

         DomainMBean domain = this.createConfig(user, pass);
         ServerMBean server = domain.lookupServer("myserver");

         try {
            if (server == null) {
               server = domain.createServer("myserver");
               domain.setAdminServerName("myserver");
            }

            System.setProperty("weblogic.log.StdoutSeverity", "Off");
         } catch (Exception var8) {
            throw new EmbeddedServerException("Error configuring embedded server", var8);
         }

         try {
            server.setListenAddress("localhost");
            server.setListenPort(65432);
            StartupClassMBean startup = domain.createStartupClass("embedded-server-startup-class");
            startup.setClassName(STARTUP_CLASS);
            startup.setFailureIsFatal(true);
            startup.setLoadAfterAppsRunning(true);
            startup.addTarget(server);
         } catch (Exception var7) {
            throw new EmbeddedServerException("Error getting default domain config ", var7);
         }

         if (EmbeddedServerImpl.DEBUG) {
            log("Created domain config at - " + this.domainHome);
         }

         return domain;
      }
   }

   public DomainMBean loadConfigXml() throws EmbeddedServerException {
      try {
         if (EmbeddedServerImpl.DEBUG) {
            log("Loading offline config.xml...");
         }

         File configXml = new File(this.domainHome, CONFIG_XML);
         DomainMBean domain = (DomainMBean)DescriptorManagerHelper.loadDescriptor(configXml.getAbsolutePath(), true, false, (List)null).getRootBean();
         if (EmbeddedServerImpl.DEBUG) {
            log("Successfully loaded config.xml");
         }

         return domain;
      } catch (Exception var3) {
         throw new EmbeddedServerException("Error loading config.xml", var3);
      }
   }

   public final DomainMBean getDomainMBean() {
      return this.domain;
   }

   public final ServerMBean getServerMBean() {
      return this.server;
   }

   public final void saveConfig() throws EmbeddedServerException {
      if (EmbeddedServerImpl.DEBUG) {
         EmbeddedServerImpl.LOGGER.info("Saving domain...");
      }

      AbstractDescriptorBean descriptorBean = (AbstractDescriptorBean)this.domain;

      try {
         DescriptorHelper.saveDescriptorTree(descriptorBean.getDescriptor(), false, this.domainHome.getAbsolutePath(), "UTF-8");
      } catch (IOException var5) {
         throw new EmbeddedServerException("Error saving domain changes", var5);
      }

      File markerFile = new File(this.domainHome, ".embed-server-marker");

      try {
         markerFile.createNewFile();
      } catch (IOException var4) {
         throw new EmbeddedServerException("Error creating domain marker", var4);
      }
   }

   protected DomainMBean createConfig(String user, String pass) throws EmbeddedServerException {
      try {
         return EmbeddedServerProvider.get().createDefaultDomain(user, pass);
      } catch (Throwable var4) {
         throw new EmbeddedServerException("Error creating default domain", var4);
      }
   }

   static void log(String message) {
      EmbeddedServerImpl.LOGGER.fine(message);
   }

   private String generateRandomString() {
      Random r = new Random();
      return Long.toHexString(r.nextLong());
   }

   private void initializeCredentials() throws EmbeddedServerException {
      String ts1FromSysProp = System.getProperty("weblogic.management.username");
      if (this.ts1 == null) {
         this.ts1 = ts1FromSysProp;
         if (this.ts1 == null) {
            this.ts1 = "system";
         }
      }

      if (ts1FromSysProp == null) {
         System.setProperty("weblogic.management.username", this.ts1);
      }

      String ts2FromSysProp = System.getProperty("weblogic.management.password");
      if (this.ts2 == null) {
         this.ts2 = ts2FromSysProp;
         if (this.ts2 == null) {
            this.ts2 = this.generateRandomString();
         }
      }

      if (ts2FromSysProp == null) {
         System.setProperty("weblogic.management.password", this.ts2);
      }

      this.setSystemCredentials(this.ts1, this.ts2);
   }

   static {
      CONFIG_XML = "config" + File.separator + "config.xml";
      STARTUP_CLASS = EmbeddedServerStartupClass.class.getName();
   }
}
