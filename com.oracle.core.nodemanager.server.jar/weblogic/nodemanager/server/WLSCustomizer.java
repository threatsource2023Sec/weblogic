package weblogic.nodemanager.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import weblogic.nodemanager.common.ConfigException;
import weblogic.nodemanager.common.StartupConfig;
import weblogic.nodemanager.util.ConcurrentFile;
import weblogic.nodemanager.util.ProcessControl;

public class WLSCustomizer implements Customizer {
   private static final String RES1 = "bea_wls_management_internal2";
   private static final String RES2 = "bea_wls_management_internal2/wl_management";
   private static final String NMSERVERPING = "NMSERVERPING";
   private static final int DEFAULT_CONNECT_TIMEOUT = 60000;
   private static final int DEFAULT_READ_TIMEOUT = 60000;
   private final ProcessControl processControl;

   public WLSCustomizer(ProcessControl processControl) {
      this.processControl = processControl;
   }

   public boolean isAlive(ServerManagerI serverMgr, String pid) {
      return this.processControl != null && this.processControl.isProcessAlive(pid) && this.canPingServer(serverMgr);
   }

   public boolean isNoStartupConfigAWarning() {
      return true;
   }

   private boolean canPingServer(ServerManagerI sm) {
      ConcurrentFile urlFile = sm.getServerDir().getURLFile();
      if (!urlFile.exists()) {
         sm.log(Level.FINEST, "No URL File exists", (Throwable)null);
         return false;
      } else {
         String wlsUrl = null;

         try {
            String s = urlFile.readLine();
            if (s == null) {
               return false;
            } else {
               if (s.endsWith("/")) {
                  wlsUrl = s + "bea_wls_management_internal2/wl_management";
               } else {
                  wlsUrl = s + "/" + "bea_wls_management_internal2/wl_management";
               }

               URL url = new URL(wlsUrl);
               HttpURLConnection conn = (HttpURLConnection)url.openConnection();
               conn.setUseCaches(false);
               conn.setRequestProperty("NMSERVERPING", "NMSERVERPING");
               conn.setConnectTimeout(60000);
               conn.setReadTimeout(60000);
               conn.connect();

               try {
                  int resCode = conn.getResponseCode();
                  sm.log(Level.FINEST, "The wls URL was available :" + wlsUrl + ", responded with code:" + resCode, (Throwable)null);
               } catch (SocketTimeoutException var13) {
                  sm.log(Level.FINEST, "The wls URL was available : " + wlsUrl + ", but didn't respond within " + '\uea60' + " millisec.", (Throwable)null);
               } finally {
                  conn.disconnect();
               }

               return true;
            }
         } catch (MalformedURLException var15) {
            sm.log(Level.FINEST, "The wls URL was unavailable :" + wlsUrl + " with " + var15, (Throwable)null);
            return false;
         } catch (IOException var16) {
            sm.log(Level.FINEST, "The wls URL was unavailable :" + wlsUrl + " with " + var16, (Throwable)null);
            return false;
         }
      }
   }

   public boolean isAdminServer(StartupConfig conf) {
      return conf.getAdminURL() == null;
   }

   public boolean isSystemComponent() {
      return false;
   }

   public StartupConfig createStartupConfig(Properties props) throws ConfigException {
      return new StartupConfig(props);
   }

   public Customizer.InstanceCustomizer createInstanceCustomizer(ServerManagerI serverMgr, StartupConfig conf) {
      WLSProcessBuilder builder = new WLSProcessBuilder(serverMgr, conf);
      return new WLSInstanceCustomizer(serverMgr, conf, builder);
   }
}
