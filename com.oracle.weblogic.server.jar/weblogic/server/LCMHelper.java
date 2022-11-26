package weblogic.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AccessController;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.diagnostics.debug.DebugLogger;
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
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class LCMHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String CONTENT_TYPE_JSON = "application/json";
   private static final String ACCEPT_JSON = "application/json";
   private static DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugLifecycleManager");
   private static Logger logger = Logger.getLogger("LifeCycle");
   static final String SOURCE_WLS = "wls";
   static final String SOURCE_OOB = "oob";
   private static final String LCMUSER = "LCMUser";
   private static final String REALM = "realm";
   private static final String CREDENTIAL_MAPPING = "Credential Mapping";
   private static final String BASIC = "Basic ";
   private static final String POST = "POST";
   private static final String GET = "GET";
   private static final String DELETE = "DELETE";
   private static final String REQUESTBY_HEADER = "X-Requested-By";
   private static final String SOURCE_HEADER = "X-Weblogic-lifecycle-source";
   private static final String ASYNC_HEADER = "X-Weblogic-lifecycle-async";
   private static final String OP_HEADER = "X-Weblogic-lifecycle-operation";
   private static final String ACCEPT = "Accept";
   private static final String CONTENT_TYPE = "Content-Type";
   private static final String CONTENT_LENGTH = "Content-Length";
   private static final String propName = "com.oracle.lifecycle.oob";
   private static final String BEARER = "Bearer ";
   static final String NAME = "name";
   static final String ID = "id";
   static final String VALUE = "value";
   static final String PROPS = "properties";
   static final String PHASE = "phase";
   static final String SERVER = "managedserver";
   static final String DOMAIN = "DOMAIN";
   private static final String DEBUG_CLASS = "LCMHelper ";

   public static void performManagedServerQuiesce(String managedServername) {
      boolean isAsync = false;
      performManagedServerOperation(managedServername, LCMHelper.Operation.QUIESCE, isAsync);
   }

   public static void performManagedServerStart(String managedServername) {
      boolean isAsync = false;
      performManagedServerOperation(managedServername, LCMHelper.Operation.START, isAsync);
   }

   public static void performManagedServerOperation(String managedServername, Operation operation) {
      boolean isAsync = true;
      performManagedServerOperation(managedServername, operation, isAsync);
   }

   public static void performManagedServerOperation(String managedServername, Operation operation, boolean isAsync) {
      URL msPostURL = null;
      LifecycleManagerEndPointMBean lcmEndPoint = getLCMEndPoint();
      if (lcmEndPoint != null) {
         try {
            switch (operation) {
               case QUIESCE:
                  msPostURL = getMSQuiesceURL(lcmEndPoint);
                  break;
               case START:
                  msPostURL = getMSStartURL(lcmEndPoint);
            }

            JSONObject quiesceDetails = new JSONObject();
            JSONArray propArray = new JSONArray();
            JSONObject propObject = new JSONObject();
            propObject.put("name", "managedserver");
            propObject.put("value", managedServername);
            propArray.put(propObject);
            quiesceDetails.put("phase", "");
            quiesceDetails.put("properties", propArray);
            if (isAsync) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper Making async " + operation + " request");
               }

               asyncDoPost(msPostURL, quiesceDetails, "wls", lcmEndPoint);
            } else {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper Making synchronous " + operation + " request");
               }

               syncDoPost(msPostURL, quiesceDetails, "wls", lcmEndPoint);
            }
         } catch (JSONException | MalformedURLException var8) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper Unable to construct the URL for LCM call", var8);
            }
         }

      }
   }

   public static void performPartitionOperation(String partitionName, Operation operation) {
      boolean isAsync = true;
      performPartitionOperation(partitionName, operation, isAsync);
   }

   public static void performPartitionOperation(String partitionName, Operation operation, boolean isAsync) {
      boolean checkForOOB = true;
      performPartitionOperation(partitionName, (JSONObject)null, operation, "wls", isAsync, checkForOOB);
   }

   private static void performPartitionOperation(String partitionName, JSONObject details, Operation operation, String source, boolean isAsync, boolean checkForOOB) {
      if (!checkForOOB || isOOBEnabled()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper performPartitionOperation : partitionName: " + partitionName + " details: " + details + " operation: " + operation + " source: " + source + " isAsync: " + isAsync + " checkForOOB: " + checkForOOB);
         }

         LifecycleManagerEndPointMBean lcmEndPoint = getLCMEndPoint();
         if (lcmEndPoint != null) {
            Objects.requireNonNull(partitionName);
            Objects.requireNonNull(operation);

            try {
               if (details == null) {
                  details = new JSONObject();
                  details.put("phase", "");
                  details.put("properties", new JSONArray());
               }

               URL partitionURL = getPartitionURL(partitionName, lcmEndPoint);
               String partitionURLStr = "";
               switch (operation) {
                  case QUIESCE:
                     partitionURLStr = getPartitionURL(partitionURL, partitionName, lcmEndPoint) + "quiesce";
                     break;
                  case START:
                     partitionURLStr = getPartitionURL(partitionURL, partitionName, lcmEndPoint) + "start";
                     break;
                  case MIGRATE:
                     partitionURLStr = getPartitionURL(partitionURL, partitionName, lcmEndPoint) + "migrate";
                     break;
                  case UPDATE:
                     partitionURLStr = getPartitionURL(partitionURL, partitionName, lcmEndPoint);
                     break;
                  default:
                     throw new IllegalArgumentException("operation: " + operation.toString() + " is not one of the legal values: " + LCMHelper.Operation.MIGRATE.toString() + ", " + LCMHelper.Operation.QUIESCE.toString() + ", " + LCMHelper.Operation.START.toString() + ", " + LCMHelper.Operation.UPDATE.toString());
               }

               URL partitionPostURL = new URL(partitionURLStr);
               if (isAsync) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("LCMHelper Making async " + operation + " request");
                  }

                  asyncDoPost(partitionPostURL, details, source, lcmEndPoint);
               } else {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("LCMHelper Making synchronous " + operation + " request");
                  }

                  syncDoPost(partitionPostURL, details, source, lcmEndPoint);
               }
            } catch (UnsupportedEncodingException | JSONException | MalformedURLException var10) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper Unable to construct the URL for LCM call", var10);
               }
            }

         }
      }
   }

   public static void otdUpdateStartMigrate(String partitionName, JSONArray propArray) throws JSONException {
      if (isLCMEnabled()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper otdUpdateStartMigrate : partitionName: " + partitionName + " propArray: " + propArray);
         }

         boolean isAsync = false;
         boolean checkForOOB = false;
         LifecycleManagerEndPointMBean lcmEndPoint = getLCMEndPoint();
         if (lcmEndPoint == null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCM partition operation migrate (start-migrate) not performed: lcmEndPoint is null.");
            }

         } else {
            JSONObject details = new JSONObject();
            details.put("runtimeName", lcmEndPoint.getRuntimeName());
            details.put("phase", "start-migrate");
            details.put("properties", propArray);
            performPartitionOperation(partitionName, details, LCMHelper.Operation.MIGRATE, "oob", isAsync, checkForOOB);
         }
      }
   }

   public static void otdUpdate(String partitionName, JSONArray propArray) throws JSONException {
      if (isLCMEnabled()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper otdUpdate : partitionName: " + partitionName + " propArray: " + propArray);
         }

         boolean isAsync = false;
         boolean checkForOOB = false;
         LifecycleManagerEndPointMBean lcmEndPoint = getLCMEndPoint();
         if (lcmEndPoint == null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCM partition operation update not performed: lcmEndPoint is null.");
            }

         } else {
            JSONObject updateDetails = new JSONObject();
            updateDetails.put("runtimeName", lcmEndPoint.getRuntimeName());
            updateDetails.put("properties", propArray);
            performPartitionOperation(partitionName, updateDetails, LCMHelper.Operation.UPDATE, "oob", isAsync, checkForOOB);
         }
      }
   }

   public static void otdUpdateEndMigrate(String partitionName, JSONArray propArray) throws JSONException {
      if (isLCMEnabled()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper otdUpdateEndMigrate : partitionName: " + partitionName + " propArray: " + propArray);
         }

         boolean isAsync = false;
         boolean checkForOOB = false;
         LifecycleManagerEndPointMBean lcmEndPoint = getLCMEndPoint();
         if (lcmEndPoint == null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCM partition operation migrate (end-migrate) not performed: lcmEndPoint is null.");
            }

         } else {
            JSONObject migrateDetails = new JSONObject();
            migrateDetails.put("runtimeName", lcmEndPoint.getRuntimeName());
            migrateDetails.put("phase", "end-migrate");
            migrateDetails.put("properties", propArray);
            performPartitionOperation(partitionName, migrateDetails, LCMHelper.Operation.MIGRATE, "oob", isAsync, checkForOOB);
         }
      }
   }

   public static String getLCMEndPointUrl(LifecycleManagerEndPointMBean lcmEndPoint) {
      String lcmEndPointUrl = stripTrailingSlashes(lcmEndPoint.getURL());
      return lcmEndPointUrl.endsWith("/lifecycle") ? lcmEndPointUrl + "/latest" : lcmEndPointUrl;
   }

   private static String stripTrailingSlashes(String str) {
      while(!str.isEmpty() && str.charAt(str.length() - 1) == '/') {
         str = str.substring(0, str.length() - 1);
      }

      return str;
   }

   private static URL getMSQuiesceURL(LifecycleManagerEndPointMBean lcmEndPoint) throws MalformedURLException {
      return new URL(String.format("%s/runtimes/%s/quiesce", getLCMEndPointUrl(lcmEndPoint), lcmEndPoint.getRuntimeName()));
   }

   private static URL getMSStartURL(LifecycleManagerEndPointMBean lcmEndPoint) throws MalformedURLException {
      return new URL(String.format("%s/runtimes/%s/start", getLCMEndPointUrl(lcmEndPoint), lcmEndPoint.getRuntimeName()));
   }

   private static void setAuthHeader(HttpURLConnection con, String userName, String password) {
      SecurityServiceManager.checkKernelPermission();
      if (userName == null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper Using JWTAuth");
         }

         con.setRequestProperty("weblogic-jwt-token", getLCMJWTAuth());
      } else {
         if (password == null) {
            throw new RuntimeException("No password found for " + userName);
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper Using Basic Auth");
         }

         BASE64Encoder encoder = new BASE64Encoder();
         String auth = userName + ":" + password;
         String basicAuth = "Basic " + encoder.encodeBuffer(auth.getBytes());
         con.setRequestProperty("Authorization", basicAuth);
      }

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
            return lifecycleManagerConfigMBean != null ? lifecycleManagerConfigMBean.getLCMInitiatedConnectTimeout() : 0;
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
            return lifecycleManagerConfigMBean != null ? lifecycleManagerConfigMBean.getLCMInitiatedReadTimeout() : 0;
         }
      }
   }

   public static boolean isOOBEnabled() {
      boolean systemPropOOBEnabled = Boolean.getBoolean("com.oracle.lifecycle.oob");
      boolean lcmConfigPropertyOOBEnabled = isOutOfBandEnabledFromLCMConfiguration();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("LCMHelper isOOBEnabled: From System property Out of band " + systemPropOOBEnabled);
         DEBUG.debug("LCMHelper isOOBEnabled: From LCM Config MBean Out of band " + lcmConfigPropertyOOBEnabled);
      }

      return systemPropOOBEnabled || lcmConfigPropertyOOBEnabled;
   }

   public static boolean isOutOfBandEnabledFromLCMConfiguration() {
      RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
      if (access == null) {
         throw new IllegalStateException("Unable to access domain");
      } else {
         DomainMBean domainMbean = access.getDomain();
         if (domainMbean == null) {
            throw new IllegalStateException("Unable to get DomainMBean");
         } else {
            LifecycleManagerConfigMBean lifecycleManagerConfigMBean = domainMbean.getLifecycleManagerConfig();
            return lifecycleManagerConfigMBean != null ? lifecycleManagerConfigMBean.isOutOfBandEnabled() : false;
         }
      }
   }

   public static boolean isLCMEnabled() {
      RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
      if (access == null) {
         throw new IllegalStateException("Unable to access domain");
      } else {
         DomainMBean domainMbean = access.getDomain();
         if (domainMbean == null) {
            throw new IllegalStateException("Unable to get DomainMBean");
         } else {
            LifecycleManagerConfigMBean lifecycleManagerConfigMBean = domainMbean.getLifecycleManagerConfig();
            return lifecycleManagerConfigMBean != null ? lifecycleManagerConfigMBean.isEnabled() : false;
         }
      }
   }

   public static LifecycleManagerEndPointMBean getLCMEndPoint() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("LCMHelper getLCMEndPoint Out of band is enabled ");
      }

      RuntimeAccess access = ManagementService.getRuntimeAccess(kernelId);
      if (access == null) {
         throw new IllegalStateException("Unable to access domain");
      } else {
         DomainMBean domainMbean = access.getDomain();
         if (domainMbean == null) {
            throw new IllegalStateException("Unable to get DomainMBean");
         } else {
            LifecycleManagerConfigMBean lifecycleManagerConfigMBean = domainMbean.getLifecycleManagerConfig();
            LifecycleManagerEndPointMBean[] lcmEndpoints = lifecycleManagerConfigMBean.getEndPoints();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper Number of LCM endpoints found : " + lcmEndpoints.length);
            }

            if (lcmEndpoints != null && lcmEndpoints.length > 0) {
               LifecycleManagerEndPointMBean[] var4 = lcmEndpoints;
               int var5 = lcmEndpoints.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  LifecycleManagerEndPointMBean endPoint = var4[var6];
                  if (endPoint.isEnabled() && endPoint.getURL() != null && endPoint.getRuntimeName() != null) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("LCMHelper Found active LCM endpoint: name= " + endPoint.getName() + " getRuntimeName= " + endPoint.getRuntimeName() + " URL= " + endPoint.getURL() + "isEnabled=" + endPoint.isEnabled());
                     }

                     return endPoint;
                  }
               }
            }

            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCMHelper:Returning Null endpoint");
            }

            return null;
         }
      }
   }

   private static URL getPartitionURL(String partitionName, LifecycleManagerEndPointMBean lcmEndPoint) throws MalformedURLException, UnsupportedEncodingException {
      return new URL(String.format("%s/runtimes/%s/partitions/%s", getLCMEndPointUrl(lcmEndPoint), URLEncoder.encode(lcmEndPoint.getRuntimeName(), "UTF-8"), URLEncoder.encode(partitionName, "UTF-8")));
   }

   private static String getPartitionURL(URL partitionURL, String partitioName, LifecycleManagerEndPointMBean lcmEndPoint) throws MalformedURLException, UnsupportedEncodingException {
      JSONObject rsp = doGet(partitionURL, lcmEndPoint);
      String link = getLinksElement(rsp, "environment");
      if (link == null) {
         throw new IllegalStateException("unable to get environment links from " + partitionURL);
      } else {
         link = link.replace("environment/", "");
         link = stripTrailingSlashes(link);
         URL envURL = new URL(link);
         rsp = doGet(envURL, lcmEndPoint);
         link = getLinksElement(rsp, "partitions");
         if (link == null) {
            throw new IllegalStateException("unable to get partition's environment link");
         } else {
            return String.format("%s/%s/", link, URLEncoder.encode(partitioName, "UTF-8"));
         }
      }
   }

   static void asyncDoPost(URL url, JSONObject postData, String source, LifecycleManagerEndPointMBean lcmEndPoint) {
      asyncDoPost(url, postData, source, (String)null, lcmEndPoint);
   }

   static void asyncDoPost(URL url, JSONObject postData, String source, String operation, LifecycleManagerEndPointMBean lcmEndPoint) {
      boolean isAsync = true;
      doPost(url, postData, source, operation, lcmEndPoint, isAsync);
   }

   static void syncDoPost(URL url, JSONObject postData, String source, LifecycleManagerEndPointMBean lcmEndPoint) {
      syncDoPost(url, postData, source, (String)null, lcmEndPoint);
   }

   static void syncDoPost(URL url, JSONObject postData, String source, String operation, LifecycleManagerEndPointMBean lcmEndPoint) {
      boolean isAsync = false;
      doPost(url, postData, source, operation, lcmEndPoint, isAsync);
   }

   static void doPost(URL url, JSONObject postData, String source, String operation, LifecycleManagerEndPointMBean lcmEndPoint, boolean isAsync) {
      if (lcmEndPoint == null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper LCMHelper doPOST  lcmEndPoint is NULL returning ");
         }

      } else {
         int connectTimeout = getConnectTimeout();
         int readTimeout = getReadTimeout();

         try {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper POST rest URL from LCM out of band : " + url);
               DEBUG.debug("LCMHelper source : " + source);
               DEBUG.debug("LCMHelper operation : " + operation);
               DEBUG.debug("LCMHelper LCMEndpoint : " + lcmEndPoint);
               DEBUG.debug("LCMHelper LCMInitiatedConnectTimeout : " + connectTimeout);
               DEBUG.debug("LCMHelper LCMInitiatedReadTimeout : " + readTimeout);
            }

            final HttpURLConnection con = openConnection(url, "POST", lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword(), "wls", operation);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", Integer.toString(postData.toString().length()));
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            Throwable var10 = null;

            try {
               wr.writeBytes(postData.toString());
               wr.flush();
               wr.close();
            } catch (Throwable var21) {
               var10 = var21;
               throw var21;
            } finally {
               if (wr != null) {
                  if (var10 != null) {
                     try {
                        wr.close();
                     } catch (Throwable var20) {
                        var10.addSuppressed(var20);
                     }
                  } else {
                     wr.close();
                  }
               }

            }

            ContextWrap contextWrap = new ContextWrap(new Runnable() {
               public void run() {
                  if (LCMHelper.DEBUG.isDebugEnabled()) {
                     LCMHelper.DEBUG.debug("LCMHelper Executing LCM REST : " + con.getURL());
                  }

                  try {
                     int rspCode = con.getResponseCode();
                     if (LCMHelper.DEBUG.isDebugEnabled()) {
                        LCMHelper.DEBUG.debug("LCMHelper LCM REST response : " + con.getResponseMessage());
                     }

                     if (rspCode != 200 && rspCode != 202 && rspCode != 201) {
                        throw new RuntimeException(con.getResponseMessage());
                     }
                  } catch (Exception var2) {
                     if (LCMHelper.DEBUG.isDebugEnabled()) {
                        LCMHelper.DEBUG.debug("LCMHelper LCM notification failed ", var2);
                     }
                  }

               }
            });
            if (isAsync) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper Request made, scheduling task to wait for reponse.");
               }

               WorkManagerFactory.getInstance().getSystem().schedule(contextWrap);
            } else {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper Request made, waiting for response.");
               }

               contextWrap.run();
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper Response recieved.");
               }
            }
         } catch (SocketTimeoutException var23) {
            logger.log(Level.WARNING, "ConnectTimeout/ReadTimeout for LCM initiated REST DELETE request for " + url, var23);
         } catch (Exception var24) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCM notification failed " + url, var24);
            }
         }

      }
   }

   static JSONObject doGet(URL url) {
      return doGet(url, getLCMEndPoint());
   }

   static JSONObject doGet(URL url, LifecycleManagerEndPointMBean lcmEndPoint) {
      if (lcmEndPoint == null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper LCMHelper doGet  lcmEndPoint is NULL returning ");
         }

         return null;
      } else {
         try {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper GET rest URL from LCM out of band : " + url);
            }

            HttpURLConnection con = openConnection(url, "GET", lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword(), (String)null, (String)null);
            int rspCode = con.getResponseCode();
            if (rspCode != 200 && rspCode != 202 && rspCode != 201) {
               throw new RuntimeException("Unexpected HTTP response code: " + rspCode);
            } else {
               StringBuilder response = new StringBuilder();
               BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
               Throwable var6 = null;

               try {
                  String inputLine;
                  try {
                     while((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                     }
                  } catch (Throwable var17) {
                     var6 = var17;
                     throw var17;
                  }
               } finally {
                  if (in != null) {
                     if (var6 != null) {
                        try {
                           in.close();
                        } catch (Throwable var16) {
                           var6.addSuppressed(var16);
                        }
                     } else {
                        in.close();
                     }
                  }

               }

               return response != null && response.length() > 0 ? new JSONObject(response.toString()) : null;
            }
         } catch (SocketTimeoutException var19) {
            logger.log(Level.WARNING, "ConnectTimeout/ReadTimeout for LCM initiated REST DELETE request for " + url, var19);
            return null;
         } catch (JSONException | IOException var20) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCM notification failed " + url, var20);
            }

            throw new RuntimeException(var20);
         }
      }
   }

   static void doDelete(URL url) {
      if (isOOBEnabled()) {
         doDelete(url, getLCMEndPoint());
      }
   }

   static void doDelete(final URL url, LifecycleManagerEndPointMBean lcmEndPoint) {
      if (isOOBEnabled()) {
         if (lcmEndPoint == null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("LCMHelper LCMHelper doGet  lcmEndPoint is NULL returning ");
            }

         } else {
            try {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper DELETE rest URL from LCM out of band : " + url);
               }

               final HttpURLConnection con = openConnection(url, "DELETE", lcmEndPoint.getRuntimeName(), lcmEndPoint.getUsername(), lcmEndPoint.getPassword(), "wls", (String)null);
               WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(new Runnable() {
                  public void run() {
                     if (LCMHelper.DEBUG.isDebugEnabled()) {
                        LCMHelper.DEBUG.debug("LCMHelper Executing LCM REST : " + con.getURL());
                     }

                     try {
                        int rspCode = con.getResponseCode();
                        if (LCMHelper.DEBUG.isDebugEnabled()) {
                           LCMHelper.DEBUG.debug("LCMHelper LCM REST response code " + rspCode);
                        }

                        if (rspCode != 200 && rspCode != 202 && rspCode != 201) {
                           throw new RuntimeException("Unexpected HTTP responde code: " + rspCode);
                        }

                        if (LCMHelper.DEBUG.isDebugEnabled()) {
                           BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                           Throwable var3 = null;

                           try {
                              String inputLine;
                              try {
                                 while((inputLine = in.readLine()) != null) {
                                    LCMHelper.DEBUG.debug(inputLine);
                                 }
                              } catch (Throwable var14) {
                                 var3 = var14;
                                 throw var14;
                              }
                           } finally {
                              if (in != null) {
                                 if (var3 != null) {
                                    try {
                                       in.close();
                                    } catch (Throwable var13) {
                                       var3.addSuppressed(var13);
                                    }
                                 } else {
                                    in.close();
                                 }
                              }

                           }
                        }
                     } catch (SocketTimeoutException var16) {
                        LCMHelper.logger.log(Level.WARNING, "ConnectTimeout/ReadTimeout for LCM initiated REST DELETE request for " + url, var16);
                     } catch (Exception var17) {
                        if (LCMHelper.DEBUG.isDebugEnabled()) {
                           LCMHelper.DEBUG.debug("LCMHelper LCM notification failed " + url, var17);
                        }
                     }

                  }
               }));
            } catch (Exception var3) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("LCMHelper LCM notification failed " + url, var3);
               }
            }

         }
      }
   }

   static String getLinksElement(JSONObject jsonObject, String key) {
      try {
         JSONArray array = jsonObject.getJSONArray("links");

         for(int i = 0; i < array.length(); ++i) {
            JSONObject obj = array.getJSONObject(i);
            if (obj.optString("rel").compareTo(key) == 0) {
               return obj.optString("href");
            }
         }
      } catch (JSONException var5) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("LCMHelper getLinksElement failed ", var5);
         }
      }

      return null;
   }

   private static HttpURLConnection openConnection(URL url, String method, String runtimeName, String userName, String password, String source, String operation) throws IOException {
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod(method);
      con.setRequestProperty("X-Requested-By", runtimeName);
      con.setRequestProperty("Accept", "application/json");
      setAuthHeader(con, userName, password);
      if (source != null) {
         con.setRequestProperty("X-Weblogic-lifecycle-source", source);
      }

      if (operation != null) {
         con.setRequestProperty("X-Weblogic-lifecycle-operation", operation);
      }

      int connectTimeout = getConnectTimeout();
      int readTimeout = getReadTimeout();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Setting ConnectTimeout on " + url + " to LifecycleManagerConfigMBean.getLCMInitiatedConnectTimeoutForElasticity()" + connectTimeout);
         DEBUG.debug("Setting ReadTimeout on " + url + " to LifecycleManagerConfigMBean.getLCMInitiatedReadTimeoutForElasticity()" + readTimeout);
      }

      con.setConnectTimeout(connectTimeout);
      con.setReadTimeout(readTimeout);
      return con;
   }

   public static enum Operation {
      QUIESCE,
      START,
      MIGRATE,
      UPDATE;
   }
}
