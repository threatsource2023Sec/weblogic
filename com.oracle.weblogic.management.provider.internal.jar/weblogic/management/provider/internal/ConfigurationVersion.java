package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import weblogic.deploy.service.Version;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.security.internal.SerializedSystemIni;

public class ConfigurationVersion implements Version {
   private static final long serialVersionUID = 239777818908345329L;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private final Map versionMap;

   public ConfigurationVersion() {
      this(false);
   }

   public ConfigurationVersion(boolean initCurrentConfiguration) {
      this(initCurrentConfiguration, (String)null);
   }

   public ConfigurationVersion(boolean initCurrentConfiguration, String serverName) {
      this.versionMap = new HashMap();
      if (initCurrentConfiguration) {
         String serializedPath = SerializedSystemIni.getPath();
         this.addFileToVersionMap(serializedPath, serializedPath);
         File[] configFiles = BootStrap.getConfigFiles();

         for(int i = 0; configFiles != null && i < configFiles.length; ++i) {
            if (!configFiles[i].getPath().equals(BootStrap.getConfigLockFileName())) {
               String filePath = configFiles[i].getAbsolutePath();
               if (filePath.indexOf("configCache/crc.ser") == -1 && filePath.indexOf("configCache\\crc.ser") == -1 && filePath.indexOf("configCache/ver.ser") == -1 && filePath.indexOf("configCache\\ver.ser") == -1 && !DomainDir.isFileRelativeToCAMConfigDir(configFiles[i]) && (serverName == null || !DomainDir.isFileRelativeToFMWServersConfigDir(configFiles[i]) || DomainDir.isFileRelativeToFMWServerConfigDir(configFiles[i], serverName))) {
                  this.addFileToVersionMap(configFiles[i].getPath(), configFiles[i].getPath());
               }
            }
         }

         String filerealmPath = DomainDir.getPathRelativeRootDir("fileRealm.properties");
         if ((new File(filerealmPath)).exists()) {
            this.addFileToVersionMap(filerealmPath, filerealmPath);
         }

      }
   }

   public String getIdentity() {
      return "Configuration";
   }

   public Map getVersionComponents() {
      synchronized(this.versionMap) {
         return new HashMap(this.versionMap);
      }
   }

   public void addOrUpdateFile(String path, String versionPath) {
      this.addFileToVersionMap(path, versionPath);
   }

   public void removeFile(String path) {
      String versionPath = this.removeRootDirectoryFromPath(path);
      String stdVersionPath = versionPath.replace('\\', '/');
      synchronized(this.versionMap) {
         this.versionMap.remove(stdVersionPath);
      }
   }

   public int hashCode() {
      synchronized(this.versionMap) {
         return this.versionMap.hashCode();
      }
   }

   public boolean equals(Object inVersion) {
      if (inVersion == null) {
         return false;
      } else if (inVersion instanceof ConfigurationVersion) {
         Map thisVersionMap = this.getVersionComponents();
         Map inVersionMap = ((ConfigurationVersion)inVersion).getVersionComponents();
         boolean result = thisVersionMap.equals(inVersionMap);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("ConfigurationVersion equals '" + thisVersionMap + "'.equals('" + inVersionMap + "') : " + result);
         }

         return result;
      } else {
         return false;
      }
   }

   private void addFileToVersionMap(String path, String versionPath) {
      long versionNumber = 0L;
      CheckedInputStream cis = null;

      try {
         cis = new CheckedInputStream(new FileInputStream(path), new Adler32());
         byte[] tempBuf = new byte[128];

         while(true) {
            if (cis.read(tempBuf) < 0) {
               versionNumber = cis.getChecksum().getValue();
               break;
            }
         }
      } catch (IOException var18) {
      } finally {
         if (cis != null) {
            try {
               cis.close();
            } catch (IOException var16) {
            }
         }

      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Create version for path " + path + " with version " + versionNumber);
      }

      String versionString = (new Long(versionNumber)).toString();
      versionPath = this.removeRootDirectoryFromPath(versionPath);
      String stdVersionPath = versionPath.replace('\\', '/');
      synchronized(this.versionMap) {
         this.versionMap.put(stdVersionPath, versionString);
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toString()).append("(id: ");
      sb.append(this.getIdentity());
      sb.append(", version vector: ");
      synchronized(this.versionMap) {
         Iterator iterator = this.versionMap.entrySet().iterator();

         while(iterator.hasNext()) {
            if (!debugLogger.isDebugEnabled() && sb.length() >= 500) {
               sb.append("... <enable 'DebugConfigurationEdit' to see full output>");
               break;
            }

            Map.Entry each = (Map.Entry)iterator.next();
            String uri = (String)each.getKey();
            String value = (String)each.getValue();
            sb.append("[component: ");
            sb.append(uri);
            sb.append(":v:");
            sb.append(value);
            sb.append("]");
         }
      }

      sb.append(")");
      return sb.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      synchronized(this.versionMap) {
         out.writeObject(this.versionMap);
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      synchronized(this.versionMap) {
         this.versionMap.putAll((Map)in.readObject());
      }
   }

   public String removeRootDirectoryFromPath(String filePath) {
      String rootDirectoryPrefix = (new File(DomainDir.getRootDir())).getPath() + File.separator;
      return !filePath.startsWith(rootDirectoryPrefix) && !(new File(filePath)).getPath().startsWith((new File(rootDirectoryPrefix)).getPath()) ? filePath : filePath.substring(rootDirectoryPrefix.length(), filePath.length());
   }
}
