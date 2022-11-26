package weblogic.nodemanager.server;

import java.io.File;

public class DomainDir extends File {
   public static final String SALT_FILE_NAME = "SerializedSystemIni.dat";
   public static final String SECRET_FILE_NAME = "nm_password.properties";
   public static final String MIGRATION_SCRIPT_DIR = "service_migration";
   public static final String DOMAIN_BIN = "bin";
   public static final String PATCHING_SCRIPT_DIR = "patching";
   public static final String SERVER_MIGRATION_DIR = "server_migration";
   public static final String CONFIG_FILE_NAME = "config.xml";
   public static final String COHERENCE_SERVERS_DIR_NAME = "servers_coherence";
   public static final String SYSTEM_COMPONENT_SERVERS_DIR_NAME = "system_components";

   public DomainDir(String path) {
      super(path);
   }

   public File getSaltFile() {
      File securityDir = new File(this.getPath(), "security");
      return new File(securityDir, "SerializedSystemIni.dat");
   }

   public File getOldSaltFile() {
      return new File(this.getPath(), "SerializedSystemIni.dat");
   }

   public File getSecretFile() {
      File dir = new File(new File(this.getPath(), "config"), "nodemanager");
      return new File(dir, "nm_password.properties");
   }

   public ServerDir getServerDir(String serverName) {
      return new ServerDir(this, serverName);
   }

   public ServerDir getServerDir(String serverName, String serverType) {
      return new ServerDir(this, this.getServersDir(serverType), serverName);
   }

   public File getConfigFile() {
      return new File(new File(this.getPath(), "config"), "config.xml");
   }

   public File getServersDir() {
      return this.getServersDir("WebLogic");
   }

   public File getServersDir(String serverType) {
      if (serverType == null) {
         throw new IllegalArgumentException();
      } else if ("WebLogic".equals(serverType)) {
         return new File(this, "servers");
      } else {
         return "Coherence".equals(serverType) ? new File(this, "servers_coherence") : new File(new File(this, "system_components"), serverType);
      }
   }

   public boolean isValid() {
      return this.getSaltFile().exists() || this.getOldSaltFile().exists();
   }

   public ServerDir[] getServerDirs() {
      return this.getServerDirs("WebLogic");
   }

   public ServerDir[] getServerDirs(String serversType) {
      ServerDir[] servers = null;
      File dir = this.getServersDir(serversType);
      String[] names = dir.list();
      if (names != null) {
         servers = new ServerDir[names.length];

         for(int i = 0; i < names.length; ++i) {
            servers[i] = new ServerDir(this, names[i]);
         }
      }

      return servers;
   }

   public File getMigrationScriptDir() {
      return new File(this.getDomainBinDir(), "service_migration");
   }

   public File getDomainBinDir() {
      return new File(this.getPath(), "bin");
   }

   public String getIfConfigDir() {
      File binDir = this.getDomainBinDir();
      return (new File(binDir, "server_migration")).getPath();
   }
}
