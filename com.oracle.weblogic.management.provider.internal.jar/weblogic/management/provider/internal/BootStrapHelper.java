package weblogic.management.provider.internal;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import javax.mail.internet.MimeUtility;
import weblogic.common.internal.VersionInfo;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.logging.Loggable;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.internal.BootStrapStruct;
import weblogic.management.internal.ConfigLogger;
import weblogic.management.provider.MSIService;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.URLManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

class BootStrapHelper {
   public static final String OAM_APPNAME = "bea_wls_management_internal2";
   private static BootStrapStruct bootStrapStruct;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String IGNORE_DEPLOYMENT_STATUS_ON_ADMIN_PROPERTY = "weblogic.ignoreDeploymentStatusOnAdmin";

   static BootStrapStruct getBootStrapStruct() throws ConfigurationException {
      if (bootStrapStruct != null) {
         return bootStrapStruct;
      } else {
         String userName = ManagementService.getPropertyService(kernelId).getTimestamp1();
         String password = ManagementService.getPropertyService(kernelId).getTimestamp2();
         String serverName = ManagementService.getPropertyService(kernelId).getServerName();
         String idd = ManagementService.getPropertyService(kernelId).getIdentityDomain();
         URL url = null;
         HttpURLConnection con = null;
         InputStream urlIn = null;
         String adminUrl = null;

         BootStrapStruct var12;
         try {
            adminUrl = ManagementService.getPropertyService(kernelId).getAdminHttpUrl();
            url = new URL(adminUrl + "/" + "bea_wls_management_internal2" + "/Bootstrap");
            con = URLManager.createAdminHttpConnection(url, true);
            con.setRequestProperty("username", mimeEncode(userName));
            con.setRequestProperty("password", mimeEncode(password));
            con.setRequestProperty("servername", mimeEncode(serverName));
            if (idd != null) {
               con.setRequestProperty("idd", mimeEncode(idd));
            }

            con.setRequestProperty("Version", buildVersionString());
            con.setRequestProperty("action", "bootstrap");
            String ignoreDeploymentStatusOnAdmin = System.getProperty("weblogic.ignoreDeploymentStatusOnAdmin");
            if (ignoreDeploymentStatusOnAdmin != null && ignoreDeploymentStatusOnAdmin.equals("true")) {
               con.setRequestProperty("ignoreDeploymentStatus", "true");
            } else {
               con.setRequestProperty("ignoreDeploymentStatus", "false");
            }

            con.setRequestProperty("action", "bootstrap");
            con.setDoOutput(true);
            con.setDoInput(true);
            int responseCode = true;

            int responseCode;
            try {
               con.connect();
               con.getHeaderField(0);
               responseCode = con.getResponseCode();
            } catch (FileNotFoundException var25) {
               responseCode = 404;
            }

            String domainVersionHeader;
            if (responseCode == 404) {
               domainVersionHeader = con.getHeaderField("UnkSvrMsg");
               if (domainVersionHeader != null) {
                  throw new UnknownServerException(domainVersionHeader);
               }

               String adminRes = con.getHeaderField("MatchMsg");
               if (adminRes != null) {
                  throw new ConfigurationException(adminRes);
               }

               throw new ConfigurationException(serverName + " not found");
            }

            if (responseCode == 401) {
               domainVersionHeader = con.getHeaderField("ErrorMsg");
               if (domainVersionHeader == null) {
                  domainVersionHeader = "";
               }

               Loggable l = ConfigLogger.logAuthenticationFailedWhileStartingManagedServerLoggable(userName, domainVersionHeader);
               l.log();
               throw new ConfigurationException(l.getMessage());
            }

            if (responseCode == 500 || responseCode == 409) {
               domainVersionHeader = con.getHeaderField("ErrorMsg");
               if (domainVersionHeader == null) {
                  domainVersionHeader = "";
               }

               domainVersionHeader = domainVersionHeader + " " + con.getResponseMessage();
               throw new ConfigurationException(domainVersionHeader);
            }

            if (responseCode == 503) {
               Loggable l = ConfigLogger.logErrorConnectingToAdminServerLoggable(adminUrl);
               l.log();
               throw new ConfigurationException(l.getMessage());
            }

            domainVersionHeader = con.getHeaderField("DomainVersion");
            if (domainVersionHeader != null) {
               VersionInfo domainVersion = new VersionInfo(domainVersionHeader);
               if (domainVersion.laterThan(VersionInfo.theOne())) {
                  Loggable l = ManagementLogger.logDomainVersionNotSupportedLoggable(domainVersionHeader, buildVersionString(".", true));
                  l.log();
                  throw new ConfigurationException(l.getMessage());
               }
            }

            urlIn = con.getInputStream();
            ObjectInputStream in = new WLObjectInputStream(new BufferedInputStream(urlIn));
            bootStrapStruct = (BootStrapStruct)in.readObject();
            var12 = bootStrapStruct;
         } catch (Exception var26) {
            Loggable l = ConfigLogger.logErrorConnectionAdminServerForBootstrapLoggable(url, userName, var26);
            MSIService msiService = (MSIService)GlobalServiceLocator.getServiceLocator().getService(MSIService.class, new Annotation[0]);
            if (msiService.isAdminServerAvailable()) {
               l.log();
            } else {
               ManagementLogger.logErrorConnectingToAdminServer(adminUrl);
            }

            if (var26 instanceof ConfigurationException) {
               throw (ConfigurationException)var26;
            }

            throw new ConfigurationException(l.getMessage());
         } finally {
            if (urlIn != null) {
               try {
                  urlIn.close();
               } catch (IOException var24) {
               }
            }

            if (con != null) {
               try {
                  con.disconnect();
               } catch (Exception var23) {
               }
            }

         }

         return var12;
      }
   }

   private static String buildVersionString() {
      return buildVersionString(",", false);
   }

   private static String buildVersionString(String sep, boolean includePatch) {
      if (sep == null) {
         sep = ",";
      }

      StringBuffer buff = new StringBuffer();
      buff.append(new Integer(VersionInfo.theOne().getMajor()));
      buff.append(sep);
      buff.append(new Integer(VersionInfo.theOne().getMinor()));
      buff.append(sep);
      buff.append(new Integer(VersionInfo.theOne().getServicePack()));
      if (includePatch) {
         buff.append(sep);
         buff.append(new Integer(VersionInfo.theOne().getRollingPatch()));
      }

      return buff.toString();
   }

   private static String mimeEncode(String orig) {
      String result = null;

      try {
         result = MimeUtility.encodeText(orig, "UTF-8", (String)null);
      } catch (UnsupportedEncodingException var3) {
         result = orig;
      }

      return result;
   }

   static class UnknownServerException extends ConfigurationException {
      public UnknownServerException(String message) {
         super(message);
      }
   }
}
