package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import javax.inject.Singleton;
import javax.security.auth.login.LoginException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LifecycleManagerConfigMBean;
import weblogic.management.configuration.LifecycleManagerEndPointMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AdminResource;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityTokenServiceManager;
import weblogic.utils.encoders.BASE64Encoder;

@Service
@AdminServer
@Singleton
public class LCMInvoker {
   private static final String SOURCE_ELASTICITY = "elasticity";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugWatchScalingActions");
   private static final String CONTENT_TYPE = "application/json";
   private static final String ACCEPT = "application/json";
   private static final String SCALEUP = "scaleUp";
   private static final String SCALEDOWN = "scaleDown";
   private static final String QUIESCE = "quiesce";

   public String quiesce(String clusterName, String serverName) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("LCMInvoker.quiesce: serverName=" + serverName);
      }

      LifecycleManagerEndPointMBean lcmEndPoint = this.getLCMEndPoint();
      if (lcmEndPoint == null) {
         ElasticityLogger.logLCMEndPointNotFound(clusterName);
         return null;
      } else {
         try {
            String runtimeURLString = this.getRuntimeURL(lcmEndPoint, "quiesce");
            URL quiesceURL = new URL(runtimeURLString);
            JSONObject details = new JSONObject();
            details.put("phase", "");
            JSONArray propArray = new JSONArray();
            JSONObject nameObject = new JSONObject();
            nameObject.put("name", "managedserver");
            nameObject.put("value", serverName);
            JSONObject clusterNameObject = new JSONObject();
            clusterNameObject.put("name", "clusterName");
            clusterNameObject.put("value", clusterName);
            propArray.put(nameObject);
            propArray.put(clusterNameObject);
            details.put("properties", propArray);
            JSONObject resp = doPost(quiesceURL, lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword(), details, "elasticity");
            return resp.length() == 0 ? null : resp.getJSONArray("links").getJSONObject(0).getString("href");
         } catch (Exception var11) {
            ElasticityLogger.logFailedOperationWithLCM(lcmEndPoint.getName());
            throw new RuntimeException("", var11);
         }
      }
   }

   public String scaleUpDown(String clusterName, int scaleFactor, boolean up) {
      String oper = up ? "scaleUp" : "scaleDown";
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("LCMInvoker." + oper + ": clusterName=" + clusterName + ", scaleFactor=" + scaleFactor);
      }

      LifecycleManagerEndPointMBean lcmEndPoint = this.getLCMEndPoint();
      if (lcmEndPoint == null) {
         ElasticityLogger.logLCMEndPointNotFound(clusterName);
         return null;
      } else {
         try {
            String runtimeURLString = this.getRuntimeURL(lcmEndPoint, oper);
            URL scaleUpDownURL = new URL(runtimeURLString);
            JSONObject details = new JSONObject();
            JSONArray propArray = new JSONArray();
            JSONObject propObject = new JSONObject();
            propObject.put("name", "clusterName");
            propObject.put("value", clusterName);
            propArray.put(propObject);
            details.put("scaleFactor", Integer.toString(scaleFactor));
            details.put("properties", propArray);
            JSONObject resp = doPost(scaleUpDownURL, lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword(), details, (String)null);
            return resp.getJSONArray("links").getJSONObject(0).getString("href");
         } catch (Exception var12) {
            ElasticityLogger.logFailedOperationWithLCM(lcmEndPoint.getName());
            throw new RuntimeException("", var12);
         }
      }
   }

   public LifecycleManagerEndPointMBean getLCMEndPoint() {
      DomainMBean domainMbean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      LifecycleManagerConfigMBean lcmManagerConfig = domainMbean.getLifecycleManagerConfig();
      LifecycleManagerEndPointMBean[] lcmEndpoints = domainMbean.getLifecycleManagerConfig().getEndPoints();
      if (lcmEndpoints != null && lcmEndpoints.length > 0) {
         LifecycleManagerEndPointMBean[] var4 = lcmEndpoints;
         int var5 = lcmEndpoints.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            LifecycleManagerEndPointMBean endPoint = var4[var6];
            if (endPoint.isEnabled() && endPoint.getURL() != null && endPoint.getRuntimeName() != null) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Found active LCM endpoint: " + endPoint.getName());
               }

               return endPoint;
            }
         }
      }

      return null;
   }

   public static int getConnectTimeout() {
      RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
      if (access == null) {
         throw new IllegalStateException("Unable to access domain");
      } else {
         DomainMBean domainMbean = access.getDomain();
         if (domainMbean == null) {
            throw new IllegalStateException("Unable to get DomainMBean");
         } else {
            LifecycleManagerConfigMBean lifecycleManagerConfigMBean = domainMbean.getLifecycleManagerConfig();
            return lifecycleManagerConfigMBean != null ? lifecycleManagerConfigMBean.getLCMInitiatedConnectTimeoutForElasticity() : 0;
         }
      }
   }

   public static int getReadTimeout() {
      RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
      if (access == null) {
         throw new IllegalStateException("Unable to access domain");
      } else {
         DomainMBean domainMbean = access.getDomain();
         if (domainMbean == null) {
            throw new IllegalStateException("Unable to get DomainMBean");
         } else {
            LifecycleManagerConfigMBean lifecycleManagerConfigMBean = domainMbean.getLifecycleManagerConfig();
            return lifecycleManagerConfigMBean != null ? lifecycleManagerConfigMBean.getLCMInitiatedReadTimeoutForElasticity() : 0;
         }
      }
   }

   private String getRuntimeURL(LifecycleManagerEndPointMBean lcmEndPoint, String operation) throws MalformedURLException {
      String url = lcmEndPoint.getURL().trim();
      if (url.endsWith("/")) {
         url = url.substring(0, url.length() - 1);
      }

      if (url.endsWith("management/lifecycle")) {
         url = url + "/latest";
      }

      return String.format("%s/runtimes/%s/%s", url, lcmEndPoint.getRuntimeName(), operation);
   }

   private HttpURLConnection openConnection(URL url, String method, LifecycleManagerEndPointMBean lcmEndPoint, int contentLength) throws IOException {
      return openConnection(url, method, lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword(), contentLength, (String)null);
   }

   private static HttpURLConnection openConnection(URL url, String method, String runtimeName, String userName, String password, int contentLength, String source) throws IOException {
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod(method);
      con.setRequestProperty("X-Requested-By", runtimeName);
      con.setRequestProperty("Accept", "application/json");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Content-Length", Integer.toString(contentLength));
      if (userName == null) {
         con.setRequestProperty("weblogic-jwt-token", getLCMJWTAuth());
      } else {
         BASE64Encoder encoder = new BASE64Encoder();
         String auth = userName + ":" + password;
         String basicAuth = "Basic " + encoder.encodeBuffer(auth.getBytes());
         con.setRequestProperty("Authorization", basicAuth);
      }

      if (source != null) {
         con.setRequestProperty("X-Weblogic-lifecycle-source", source);
      }

      con.setUseCaches(false);
      int connectTimeout = getConnectTimeout();
      int readTimeout = getReadTimeout();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Setting ConnectTimeout on " + con.getURL() + " to LifecycleManagerConfigMBean.getLCMInitiatedConnectTimeoutForElasticity()" + connectTimeout);
         DEBUG.debug("Setting ReadTimeout on " + con.getURL() + " to LifecycleManagerConfigMBean.getLCMInitiatedReadTimeoutForElasticity()" + readTimeout);
      }

      con.setConnectTimeout(connectTimeout);
      con.setReadTimeout(readTimeout);
      return con;
   }

   private static String getLCMJWTAuth() {
      PrincipalAuthenticator pa = SecurityServiceManager.getPrincipalAuthenticator(kernelId, SecurityServiceManager.getContextSensitiveRealmName());

      AuthenticatedSubject init;
      try {
         init = pa.impersonateIdentity("LCMUser");
      } catch (LoginException var5) {
         throw new IllegalStateException(var5);
      }

      AdminResource resource = new AdminResource("Credential Mapping", "realm", "LCMUser");
      SecurityTokenServiceManager stsm = (SecurityTokenServiceManager)SecurityServiceManager.getSecurityTokenServiceManager(kernelId, SecurityServiceManager.getContextSensitiveRealmName());
      String token = (String)stsm.issueToken("weblogic.oauth2.jwt.access.token", kernelId, init, resource, (ContextHandler)null);
      return "Bearer " + token;
   }

   private static JSONObject getResponse(HttpURLConnection con) throws IOException {
      int rspCode = con.getResponseCode();
      if (rspCode != 200 && rspCode != 202 && rspCode != 201) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Received unexpected response code " + rspCode + " from " + con.getURL());
         }

         throw new RuntimeException(con.getResponseMessage());
      } else {
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
         Throwable var4 = null;

         StringBuilder response;
         try {
            response = new StringBuilder();

            String inputLine;
            while((inputLine = in.readLine()) != null) {
               response.append(inputLine);
            }
         } catch (Throwable var15) {
            var4 = var15;
            throw var15;
         } finally {
            if (in != null) {
               if (var4 != null) {
                  try {
                     in.close();
                  } catch (Throwable var13) {
                     var4.addSuppressed(var13);
                  }
               } else {
                  in.close();
               }
            }

         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMInvoker.getResponse: " + response.toString());
         }

         if (response.length() > 0) {
            try {
               return new JSONObject(response.toString());
            } catch (Exception var14) {
               throw new RuntimeException(var14);
            }
         } else {
            return null;
         }
      }
   }

   private JSONObject doDelete(LifecycleManagerEndPointMBean lcmEndPoint, URL url) {
      try {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMInvoker.doDelete: url=" + url);
         }

         HttpURLConnection con = this.openConnection(url, "DELETE", lcmEndPoint, 0);
         return getResponse(con);
      } catch (IOException var4) {
         throw new RuntimeException(var4);
      }
   }

   private JSONObject doGet(LifecycleManagerEndPointMBean lcmEndPoint, URL url) {
      return doGet(url, lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword());
   }

   public static JSONObject doGet(URL url, String runtimeName, String userName, String password) {
      try {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMInvoker.doGet: url=" + url);
         }

         HttpURLConnection con = openConnection(url, "GET", runtimeName, userName, password, 0, (String)null);
         return getResponse(con);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }
   }

   public static JSONObject doPost(URL url, String runtimeName, String userName, String password, JSONObject postData) {
      return doPost(url, runtimeName, userName, password, postData, (String)null);
   }

   private static JSONObject doPost(URL url, String runtimeName, String userName, String password, JSONObject postData, String source) {
      try {
         String postDataString = postData.toString();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMInvoker.doPost: url=" + url + " data=" + postDataString + " source=" + source);
         }

         HttpURLConnection con = openConnection(url, "POST", runtimeName, userName, password, postDataString.length(), source);
         con.setDoInput(true);
         con.setDoOutput(true);
         DataOutputStream wr = new DataOutputStream(con.getOutputStream());
         Throwable var9 = null;

         try {
            wr.writeBytes(postDataString);
            wr.flush();
            wr.close();
         } catch (Throwable var19) {
            var9 = var19;
            throw var19;
         } finally {
            if (wr != null) {
               if (var9 != null) {
                  try {
                     wr.close();
                  } catch (Throwable var18) {
                     var9.addSuppressed(var18);
                  }
               } else {
                  wr.close();
               }
            }

         }

         return getResponse(con);
      } catch (IOException var21) {
         throw new RuntimeException(var21);
      }
   }

   public String getTaskStatus(String taskUrl) {
      try {
         LifecycleManagerEndPointMBean lcmEndPoint = this.getLCMEndPoint();
         if (lcmEndPoint == null) {
            return null;
         } else {
            URL url = new URL(taskUrl);
            JSONObject resp = this.doGet(lcmEndPoint, url);
            String status = resp.getString("status");
            return status;
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6.getMessage(), var6);
      }
   }

   public void cancelTask(String taskUrl) throws Exception {
      if (taskUrl != null) {
         LifecycleManagerEndPointMBean lcmEndPoint = this.getLCMEndPoint();
         if (lcmEndPoint != null) {
            URL url = new URL(taskUrl);
            this.doDelete(lcmEndPoint, url);
         }
      }
   }

   public boolean isTaskRunning(String taskUrl) throws Exception {
      String status = this.getTaskStatus(taskUrl);
      return isRunningStatus(status);
   }

   public static boolean isRunningStatus(String status) {
      switch (status) {
         case "NONE":
         case "INITIALIZED":
         case "STARTED":
         case "REVERTING":
            return true;
         default:
            return false;
      }
   }
}
