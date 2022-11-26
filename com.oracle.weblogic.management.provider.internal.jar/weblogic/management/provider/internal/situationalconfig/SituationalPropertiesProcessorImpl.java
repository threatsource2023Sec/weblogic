package weblogic.management.provider.internal.situationalconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.Utils;
import weblogic.management.provider.RuntimeAccessSettable;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.situationalconfig.SituationalConfigurationConfig;

@Service
@Named
public class SituationalPropertiesProcessorImpl implements SituationalPropertiesProcessor, SituationalConfigurationConfig, RuntimeAccessSettable {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private static final String basename = "situational-config";
   private static final String PROP_SUFFIX = ".properties";
   private static final String TURNOFF = "weblogic.SituationalPropsDisabled";
   private final boolean disable = Boolean.getBoolean("weblogic.SituationalPropsDisabled");
   private File rootfile;
   private String filename;
   private String serverName;
   private Properties loadedProps;
   private ServerRuntimeMBean serverRuntime;

   private SituationalPropertiesProcessorImpl() {
      if (!this.disable) {
         this.rootfile = new File(DomainDir.getOptConfigDir());
         this.filename = "situational-config.properties";
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[SitProp] Situational Properties are disabled");
      }

   }

   public ServerRuntimeMBean getServerRuntime() {
      return this.serverRuntime;
   }

   public void setServerRuntime(ServerRuntimeMBean serverRuntime) {
      if (this.serverRuntime != null) {
         throw new AssertionError("ServerRuntimeMBean may only be set once.");
      } else {
         this.serverRuntime = serverRuntime;
         serverRuntime.setInSitConfigState(this.isBaseConfigOverridden());
      }
   }

   public String toString() {
      return this.getClass().getName() + ":" + this.rootfile;
   }

   public boolean isBaseConfigOverridden() {
      return this.loadedProps != null && this.loadedProps.size() > 0;
   }

   public void loadConfiguration(DomainMBean domainBean) throws IOException {
      if (!this.disable) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitProp] loading Situational Config ");
         }

         if (this.loadedProps == null) {
            this.serverName = Utils.findServerName(domainBean);
            if (this.serverName == null) {
               throw new IOException("[SitProp]: Could not find server name to load");
            }

            File server = new File(this.rootfile, this.serverName);
            if (!server.exists()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitProp] No Server Directory for Situational Config " + server + " looking for a domain file");
               }

               if (!this.rootfile.exists()) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("[SitProp] No Domain Directory for Situational Config " + this.rootfile + " looking for a domain file");
                  }

                  this.loadedProps = new Properties();
                  return;
               }

               server = this.rootfile;
            }

            File file = new File(server, this.filename);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitProp] Loading Situational Property file:" + file);
            }

            FileInputStream fis = null;

            try {
               if (!file.exists()) {
                  if (this.loadedProps == null) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("[SitProp] No Situational Properties to load :" + file);
                     }

                     this.loadedProps = new Properties();
                     return;
                  }
               } else {
                  fis = new FileInputStream(file);
                  Properties p = new Properties();
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("[SitProp] Situational Property : about to load " + file);
                  }

                  p.load(fis);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("[SitProp] Situational Property : Loaded: " + file + " values = " + p);
                  }

                  this.loadedProps = new Properties();
                  Enumeration e = p.propertyNames();

                  while(e.hasMoreElements()) {
                     String key = (String)e.nextElement();
                     if (System.getProperty(key) != null) {
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("[SitProp] Situational Property : " + key + " was set on the command line and is being ignored");
                        }
                     } else {
                        this.loadedProps.setProperty(key, p.getProperty(key).trim());
                     }
                  }
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitProp] Situational Properties are: " + this.loadedProps);
               }
            } finally {
               if (fis != null) {
                  try {
                     fis.close();
                  } catch (Throwable var14) {
                  }
               }

            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitProp] Applied Situational Properties are: " + this.loadedProps);
         }

         Enumeration e = this.loadedProps.propertyNames();

         while(e.hasMoreElements()) {
            String key = (String)e.nextElement();
            if (System.getProperty(key) == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("[SitProp] Situational Property : " + key + " is being set as a System Property");
               }

               String value = this.loadedProps.getProperty(key);
               System.getProperties().setProperty(key, value);
               ManagementLogger.logPropertyOverride(key, value);
               if (this.serverRuntime != null) {
                  this.serverRuntime.setInSitConfigState(this.isBaseConfigOverridden());
               }
            }
         }

      }
   }

   public void unloadConfiguration(DomainMBean domainBean) {
   }
}
