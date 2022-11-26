package weblogic.application.utils;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.deploy.api.spi.BaseApplicationVersionUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.EditSessionTool;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PrimitiveContextFactory;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.StringWorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;

public final class ApplicationVersionUtils extends BaseApplicationVersionUtils {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String LIB_DELIMITER = "@";
   public static final int MAX_VERSION_ID_LENGTH = 215;
   private static final String versionIdCharsRegExpr = "[\\w\\.\\-\\_]*";
   private static final String[] invalidVersionIdStrings = new String[]{".", ".."};
   private static final WorkContextMap workCtxMap = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
   private static final int defaultPropagationMode = 214;
   private static final int adminModePropagationMode = 150;
   private static final ApplicationAccess applicationAccess = ApplicationAccess.getApplicationAccess();
   private static EditSessionTool editSessionTool = null;
   private static final int[] editSessionToolLock = new int[0];
   private static final String APP_CTX_BIND_APPLICATION_ID = "weblogic.BindApplicationId";
   private static final String APP_CTX_BIND_APPLICATION_ID_DEBUG = "weblogic.BindApplicationIdDebug";
   private static final String APP_CTX_APPNAME_PREFIX = "weblogic.app.";
   private static final String APP_CTX_ADMIN_MODE = "weblogic.app.internal.AdminMode";
   private static final String APP_VERSION_MANIFEST_ATTR_NAME = "Weblogic-Application-Version";
   private static final String LIB_SPEC_VER_MANIFEST_ATTR_NAME = "Specification-Version";
   private static final String LIB_IMPL_VER_MANIFEST_ATTR_NAME = "Implementation-Version";
   private static final String ADMIN_MODE = "weblogic.app.adminMode";
   private static final boolean defaultAdminMode = false;
   private static final String IGNORE_SESSIONS = "weblogic.app.ignoreSessions";
   private static final boolean defaultIgnoreSessions = false;
   private static final String RMI_GRACE_PERIOD = "weblogic.app.rmiGracePeriod";
   private static final int defaultRMIGracePeriod = 30;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   private static String getLastToken(String str, String delimiter) {
      if (str != null && str.length() != 0) {
         int index = str.lastIndexOf(delimiter);
         if (index == -1) {
            return null;
         } else {
            String rtnStr = str.substring(index + 1);
            return rtnStr.length() == 0 ? null : rtnStr;
         }
      } else {
         return null;
      }
   }

   public static String getNonPartitionName(String appId) {
      return getNonPartitionName(appId, (PartitionMBean[])null);
   }

   public static String getNonPartitionName(String appId, PartitionMBean[] partitions) {
      return getPartitionName(appId, partitions).equals("DOMAIN") ? appId : trimLastToken(appId, "$");
   }

   public static String getVersionId(String appId) {
      return getVersionId(appId, (PartitionMBean[])null);
   }

   public static String getVersionId(String appId, PartitionMBean[] partitions) {
      return getSecondToken(getNonPartitionName(appId, partitions), "#");
   }

   public static String getNonVersionedName(String appId) {
      return getNonVersionedName(appId, (PartitionMBean[])null);
   }

   public static String getNonVersionedName(String appId, PartitionMBean[] partitions) {
      return getFirstToken(getNonPartitionName(appId, partitions), "#");
   }

   public static String getApplicationName(String appId) {
      return getApplicationName(appId, (PartitionMBean[])null);
   }

   public static String getApplicationName(String appId, PartitionMBean[] partitions) {
      return getNonVersionedName(appId, partitions);
   }

   public static String getArchiveVersion(AppDeploymentMBean app) {
      return getArchiveVersion(app.getVersionIdentifier());
   }

   public static String getPlanVersion(AppDeploymentMBean app) {
      return getPlanVersion(app.getVersionIdentifier());
   }

   public static String getPartitionName(String deploymentName) {
      return getPartitionName(deploymentName, (PartitionMBean[])null);
   }

   public static String getPartitionName(String deploymentName, PartitionMBean[] partitions) {
      String token = getLastToken(deploymentName, "$");
      if (token == null || token.length() == 0 || !isRealPartitionName(token, partitions)) {
         token = "DOMAIN";
      }

      return token;
   }

   public static String getApplicationId(String appName, String versionId) {
      if (appName != null && appName.length() != 0) {
         return versionId != null && versionId.length() != 0 ? appName + "#" + versionId : appName;
      } else {
         return null;
      }
   }

   public static String getApplicationId(String appName, String versionId, String partitionName) {
      if (appName != null && appName.length() != 0) {
         if ("DOMAIN".equals(partitionName)) {
            partitionName = null;
         }

         if (versionId != null && versionId.length() != 0) {
            return partitionName != null && partitionName.length() != 0 ? appName + "#" + versionId + "$" + partitionName : appName + "#" + versionId;
         } else {
            return partitionName != null && partitionName.length() != 0 ? appName + "$" + partitionName : appName;
         }
      } else {
         return null;
      }
   }

   public static String getApplicationId(AppDeploymentMBean deployment) {
      if (deployment == null) {
         return null;
      } else {
         String beanName = deployment.getName();
         String applicationName = getApplicationName(beanName);
         String versionId = getVersionId(beanName);
         return getApplicationId(applicationName, versionId, deployment.getPartitionName());
      }
   }

   public static String getApplicationIdWithPartition(String versionedAppName, String partitionName) {
      if (versionedAppName != null && versionedAppName.length() != 0) {
         return partitionName != null && partitionName.length() != 0 ? versionedAppName + "$" + partitionName : versionedAppName;
      } else {
         return null;
      }
   }

   public static String getLibVersionId(String specVersion, String implVersion) {
      if ((specVersion == null || specVersion.length() == 0) && (implVersion == null || implVersion.length() == 0)) {
         return null;
      } else {
         if (specVersion == null) {
            specVersion = "";
         }

         if (implVersion == null) {
            implVersion = "";
         }

         return specVersion + "@" + implVersion;
      }
   }

   public static String getLibSpecVersion(String archiveVersion) {
      return getFirstToken(archiveVersion, "@");
   }

   public static String getLibImplVersion(String archiveVersion) {
      return getSecondToken(archiveVersion, "@");
   }

   public static boolean isLibraryVersion(String archiveVersion) {
      return archiveVersion != null && archiveVersion.indexOf("@") != -1;
   }

   public static void setCurrentVersionId(String appName, String versionId) {
      if (appName != null && appName.length() != 0 && versionId != null && versionId.length() != 0) {
         try {
            workCtxMap.put(getWorkCtxAppName(appName), PrimitiveContextFactory.create(versionId), 214);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("*** setCurrentVersionId(appName=" + appName + ", versionId=" + versionId + ")");
            }
         } catch (PropertyReadOnlyException var3) {
            Debug.assertion(false, "WorkContext property for '" + appName + "' is read-only");
         }

      }
   }

   public static void setCurrentVersionId(String appId) {
      String appName = getApplicationName(appId);
      String versionId = getVersionId(appId);
      setCurrentVersionId(appName, versionId);
   }

   private static String getWorkCtxAppName(String appName) {
      return "weblogic.app." + appName;
   }

   public static void setCurrentAdminMode(boolean adminMode) {
      try {
         workCtxMap.put("weblogic.app.internal.AdminMode", PrimitiveContextFactory.create(Boolean.toString(adminMode)), 150);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("*** setAdminMode(adminMode=" + adminMode + ")");
         }
      } catch (PropertyReadOnlyException var2) {
         Debug.assertion(false, "WorkContext property for AdminMode is read-only");
      }

   }

   public static void unsetCurrentVersionId(String appId) {
      String appName = getApplicationName(appId);
      if (appName != null && appName.length() != 0) {
         String key = getWorkCtxAppName(appName);
         if (!workCtxMap.isEmpty() && workCtxMap.get(key) != null) {
            try {
               workCtxMap.remove(key);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("*** unsetCurrentVersionId(appName=" + appName + ")");
               }
            } catch (PropertyReadOnlyException var4) {
               Debug.assertion(false, "WorkContext property for '" + appName + "' is read-only");
            } catch (NoWorkContextException var5) {
               Debug.assertion(false, "No WorkContext is available");
            }

         }
      }
   }

   public static void unsetCurrentAdminMode() {
      if (!workCtxMap.isEmpty() && workCtxMap.get("weblogic.app.internal.AdminMode") != null) {
         try {
            workCtxMap.remove("weblogic.app.internal.AdminMode");
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("*** unsetAdminMode");
            }
         } catch (PropertyReadOnlyException var1) {
            Debug.assertion(false, "WorkContext property for AdminMode is read-only");
         } catch (NoWorkContextException var2) {
            Debug.assertion(false, "No WorkContext is available");
         }

      }
   }

   public static String getCurrentVersionId(String appName) {
      if (appName != null && appName.length() != 0) {
         String id;
         if (!workCtxMap.isEmpty()) {
            Object object = workCtxMap.get(getWorkCtxAppName(appName));
            if (object != null && object instanceof StringWorkContext) {
               id = ((StringWorkContext)object).toString();
               if (id.length() > 0) {
                  return id;
               }
            }
         }

         String curAppId = getCurrentApplicationId();
         if (curAppId == null) {
            return null;
         } else {
            id = getApplicationName(curAppId);
            return appName.equals(id) ? getVersionId(curAppId) : null;
         }
      } else {
         return null;
      }
   }

   public static String getCurrentVersionId() {
      String appId = applicationAccess.getCurrentApplicationName();
      return getVersionId(appId);
   }

   public static String getCurrentApplicationId() {
      return applicationAccess.getCurrentApplicationName();
   }

   public static boolean getCurrentAdminMode() {
      if (workCtxMap.isEmpty()) {
         return false;
      } else {
         boolean adminMode = false;
         Object object = workCtxMap.get("weblogic.app.internal.AdminMode");
         if (object != null && object instanceof StringWorkContext) {
            adminMode = Boolean.valueOf(((StringWorkContext)object).toString());
         }

         return adminMode;
      }
   }

   public static boolean isAdminModeRequest() {
      boolean isAdminMode = getCurrentAdminMode();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("*** isAdminRequest(): isAdminModeSet : " + isAdminMode);
      }

      if (isAdminMode) {
         return true;
      } else {
         ServerChannel channel = ServerHelper.getServerChannel();
         boolean isAdminChannel = channel != null && ChannelHelper.isAdminChannel(channel);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("*** isAdminRequest(): isAdminChannel() : " + isAdminChannel);
         }

         if (isAdminChannel) {
            return true;
         } else {
            AuthenticatedSubject currentUser = SecurityServiceManager.getCurrentSubject(kernelId);
            boolean doesUserHaveAnyAdminRoles = SubjectUtils.doesUserHaveAnyAdminRoles(currentUser);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("*** isAdminRequest(): doesUserHaveAnyAdminRoles('" + currentUser + "') : " + doesUserHaveAnyAdminRoles);
            }

            if (doesUserHaveAnyAdminRoles) {
               return true;
            } else {
               ApplicationRuntimeMBean appRuntime = getCurrentApplicationRuntime();
               boolean isRequestedAppAdminMode = appRuntime != null && appRuntime.getActiveVersionState() == 1;
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("*** isAdminRequest(): isRequestedAppAdminMode() : " + isRequestedAppAdminMode);
               }

               if (isRequestedAppAdminMode) {
                  return true;
               } else {
                  boolean adminModeContextParam = getAdminModeAppCtxParam(applicationAccess.getCurrentApplicationContext());
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("*** isAdminRequest(): hasAdminModeCtxParam() : " + adminModeContextParam);
                  }

                  if (adminModeContextParam) {
                     return true;
                  } else {
                     boolean isRequestedPartitionAdminMode = ServerHelper.getServerChannel() == null && appRuntime == null && isCurrentPartitionAdminMode();
                     return isRequestedPartitionAdminMode;
                  }
               }
            }
         }
      }
   }

   private static boolean isCurrentPartitionAdminMode() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (cic.isGlobalRuntime()) {
         int stateVal = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getStateVal();
         return stateVal == 1 || stateVal == 7 || stateVal == 18;
      } else {
         PartitionRuntimeMBean mbean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(cic.getPartitionName());
         if (mbean == null) {
            return false;
         } else {
            PartitionRuntimeMBean.State state = mbean.getInternalState();
            return state != null && (state == State.STARTING || state == State.SHUTTING_DOWN || state == State.FORCE_SHUTTING_DOWN || state == State.STARTING_IN_ADMIN);
         }
      }
   }

   public static String getActiveVersionId(String appName, boolean adminMode) {
      AppDeploymentMBean mbean = getActiveAppDeployment(getDomain(), appName, adminMode);
      return mbean == null ? null : mbean.getVersionIdentifier();
   }

   public static String getActiveVersionId(String appName) {
      return getActiveVersionId(appName, false);
   }

   public static String getActiveVersionId() {
      String appId = applicationAccess.getCurrentApplicationName();
      return getActiveVersionId(getApplicationName(appId));
   }

   public static String getDisplayName(String appName, String versionId) {
      if (appName == null) {
         return "";
      } else {
         String displayName = appName;
         if (versionId != null) {
            displayName = appName + " " + getDisplayVersionId(versionId);
         }

         return displayName;
      }
   }

   public static String getDisplayName(BasicDeploymentMBean dep) {
      if (dep == null) {
         return "";
      } else {
         return dep instanceof AppDeploymentMBean ? getAppDeploymentDisplayName((AppDeploymentMBean)dep) : getSystemResourceDisplayName((SystemResourceMBean)dep);
      }
   }

   private static String getSystemResourceDisplayName(SystemResourceMBean srmBean) {
      return getDisplayName(srmBean.getName(), (String)null);
   }

   private static String getAppDeploymentDisplayName(AppDeploymentMBean admBean) {
      return getDisplayName(admBean.getApplicationName(), admBean.getVersionIdentifier());
   }

   public static String getDisplayName(String appId) {
      if (appId == null) {
         return "";
      } else {
         String appName = getApplicationName(appId);
         String versionId = getVersionId(appId);
         return getDisplayName(appName, versionId);
      }
   }

   public static String getDisplayVersionId(String versionId) {
      if (versionId == null) {
         return "";
      } else {
         String displayId = null;
         String archiveVersion = getArchiveVersion(versionId);
         String planVersion = getPlanVersion(versionId);
         if (isLibraryVersion(archiveVersion)) {
            String libSpecVersion = getLibSpecVersion(archiveVersion);
            String libImplVersion = getLibImplVersion(archiveVersion);
            if (libSpecVersion != null) {
               displayId = "[LibSpecVersion=" + libSpecVersion;
            }

            if (libImplVersion != null) {
               if (displayId == null) {
                  displayId = "[";
               }

               displayId = displayId + ",LibImplVersion=" + libImplVersion;
            }
         } else if (planVersion == null) {
            displayId = "[Version=" + archiveVersion;
         } else {
            displayId = "[ArchiveVersion=" + archiveVersion;
         }

         if (planVersion != null) {
            displayId = displayId + ",PlanVersion=" + planVersion;
         }

         if (displayId != null) {
            displayId = displayId + "]";
         }

         return displayId;
      }
   }

   public static String replaceDelimiter(String appId, char delimiter) {
      return appId == null ? null : appId.replace("#".charAt(0), delimiter);
   }

   public static boolean isSameComponent(String compName1, String version1, String compName2, String version2) {
      if (compName1 != null && compName1.length() != 0) {
         if (compName2 != null && compName2.length() != 0) {
            String str1 = "#";
            String str2 = "#";
            if (version1 != null && version1.length() > 0) {
               str1 = str1 + version1;
            }

            if (version2 != null && version2.length() > 0) {
               str2 = str2 + version2;
            }

            return compName1.replaceAll(str1, str2).equals(compName2);
         } else {
            return false;
         }
      } else {
         return compName2 == null || compName2.length() == 0;
      }
   }

   public static void setBindApplicationId(String appId) {
      if (appId != null && appId.length() != 0) {
         try {
            workCtxMap.put("weblogic.BindApplicationId", PrimitiveContextFactory.create(appId));
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("*** setBindAppId(appId=" + appId + ")");
               String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
               StringWriter sw = new StringWriter();
               PrintWriter pw = new PrintWriter(sw);
               (new Exception("appId is set. : " + appId + ",serverName:" + serverName)).printStackTrace(pw);
               pw.flush();
               workCtxMap.put("weblogic.BindApplicationIdDebug", PrimitiveContextFactory.create(sw.toString()));
            }
         } catch (PropertyReadOnlyException var4) {
            Debug.assertion(false, "WorkContext property for BindApplicationId is read-only");
         }

      }
   }

   public static void unsetBindApplicationId() {
      if (!workCtxMap.isEmpty() && workCtxMap.get("weblogic.BindApplicationId") != null) {
         try {
            workCtxMap.remove("weblogic.BindApplicationId");
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("*** unsetBindAppId");
               workCtxMap.remove("weblogic.BindApplicationIdDebug");
            }
         } catch (PropertyReadOnlyException var1) {
            Debug.assertion(false, "WorkContext property for BindApplicationId is read-only");
         } catch (NoWorkContextException var2) {
            Debug.assertion(false, "No WorkContext is available");
         }

      }
   }

   public static String getDetailedInfoAboutVersionContext() {
      String appId = getBindApplicationId();
      String verId = getVersionId(appId);
      String source = "";
      String serverName;
      if (appId != null) {
         serverName = getVersionedAppIdFromWorkCtx();
         String clAppId = getVersionedAppIdFromCL();
         if (serverName != null) {
            source = "WorkCtx";
         } else if (clAppId != null) {
            source = "ContextClassLoader[" + Thread.currentThread().getContextClassLoader().toString() + "]";
         } else {
            source = "none";
         }
      }

      if (debugLogger.isDebugEnabled() && appId != null) {
         if (source.equals("WorkCtx")) {
            serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
            Object object = workCtxMap.get("weblogic.BindApplicationIdDebug");
            String callStack = null;
            if (object != null && object instanceof StringWorkContext) {
               callStack = ((StringWorkContext)object).toString();
            }

            debugLogger.debug("*** getDetailedInfoAboutVersionContext: assertion failed. version identifier should not exist in this place. actualAppId:" + appId + ",ver:" + verId + " derived from WorkCtx ,current server name:" + serverName + " ,creation callstack:" + callStack);
         } else {
            debugLogger.debug("*** getDetailedInfoAboutVersionContext: assertion failed. version identifier should not exist in this place. actualAppId:" + appId + ",ver:" + verId + " derived from WorkCtx , source:" + source);
         }
      }

      return "[" + appId + ",ver:" + verId + ",source:" + source + "]";
   }

   private static String getVersionedAppIdFromWorkCtx() {
      if (workCtxMap.isEmpty()) {
         return null;
      } else {
         Object object = workCtxMap.get("weblogic.BindApplicationId");
         String id = null;
         if (object != null && object instanceof StringWorkContext) {
            id = ((StringWorkContext)object).toString();
            if (id.length() > 0) {
               return id;
            }
         }

         return null;
      }
   }

   private static String getVersionedAppIdFromCL() {
      String appId = applicationAccess.getCurrentApplicationName();
      return getVersionId(appId) != null ? appId : null;
   }

   public static String getBindApplicationId() {
      if (workCtxMap.isEmpty()) {
         return null;
      } else {
         Object object = workCtxMap.get("weblogic.BindApplicationId");
         String id = null;
         if (object != null && object instanceof StringWorkContext) {
            id = ((StringWorkContext)object).toString();
            if (id.length() > 0) {
               return id;
            }
         }

         String appId = applicationAccess.getCurrentApplicationName();
         return getVersionId(appId) != null ? appId : null;
      }
   }

   public static String getManifestVersion(VirtualJarFile appJar) {
      if (appJar == null) {
         return null;
      } else {
         Manifest manifest = null;

         try {
            manifest = appJar.getManifest();
         } catch (IOException var5) {
            return null;
         }

         if (manifest != null) {
            Attributes mainAttrs = manifest.getMainAttributes();
            if (mainAttrs != null) {
               try {
                  String s = mainAttrs.getValue("Weblogic-Application-Version");
                  if (s != null) {
                     s = s.trim();
                  }

                  return s;
               } catch (IllegalArgumentException var4) {
                  return null;
               }
            }
         }

         return null;
      }
   }

   public static String getManifestVersion(String path) {
      if (path == null) {
         return null;
      } else {
         VirtualJarFile appJar = null;

         Object var3;
         try {
            appJar = ApplicationFileManager.newInstance(path).getVirtualJarFile();
            String var2 = getManifestVersion(appJar);
            return var2;
         } catch (IOException var13) {
            var3 = null;
         } finally {
            if (appJar != null) {
               try {
                  appJar.close();
               } catch (IOException var12) {
               }
            }

         }

         return (String)var3;
      }
   }

   public static String getLibVersionId(VirtualJarFile libJar) {
      if (libJar == null) {
         return null;
      } else {
         Manifest manifest = null;

         try {
            manifest = libJar.getManifest();
         } catch (IOException var6) {
            return null;
         }

         if (manifest != null) {
            Attributes mainAttrs = manifest.getMainAttributes();
            if (mainAttrs != null) {
               try {
                  String specVersion = mainAttrs.getValue("Specification-Version");
                  if (specVersion != null) {
                     specVersion = specVersion.trim();
                  }

                  String implVersion = mainAttrs.getValue("Implementation-Version");
                  if (implVersion != null) {
                     implVersion = implVersion.trim();
                  }

                  return getLibVersionId(specVersion, implVersion);
               } catch (IllegalArgumentException var5) {
                  return null;
               }
            }
         }

         return null;
      }
   }

   public static String getLibName(String path) {
      if (path == null) {
         return null;
      } else {
         VirtualJarFile libJar = null;

         Object var3;
         try {
            libJar = ApplicationFileManager.newInstance(path).getVirtualJarFile();
            String var2 = getLibName(libJar);
            return var2;
         } catch (IOException var13) {
            var3 = null;
         } finally {
            if (libJar != null) {
               try {
                  libJar.close();
               } catch (IOException var12) {
               }
            }

         }

         return (String)var3;
      }
   }

   public static String getLibVersionId(String path) {
      if (path == null) {
         return null;
      } else {
         VirtualJarFile libJar = null;

         Object var3;
         try {
            libJar = ApplicationFileManager.newInstance(path).getVirtualJarFile();
            String var2 = getLibVersionId(libJar);
            return var2;
         } catch (IOException var13) {
            var3 = null;
         } finally {
            if (libJar != null) {
               try {
                  libJar.close();
               } catch (IOException var12) {
               }
            }

         }

         return (String)var3;
      }
   }

   public static AppDeploymentMBean[] getAppDeployments(String appName) {
      return getAppDeployments(getDomain(), appName);
   }

   public static AppDeploymentMBean[] getAppDeployments(String appName, String rgt, String rg, String partition) {
      return getAppDeployments(getDomain(), appName, rgt, rg, partition, false);
   }

   public static AppDeploymentMBean[] getAppDeployments(DomainMBean domain, String appName) {
      return getAppDeployments(domain, appName, false);
   }

   public static AppDeploymentMBean[] getAppDeployments(DomainMBean domain, String appName, boolean appOnly) {
      return getAppDeployments(domain, appName, (String)null, (String)null, (String)null, appOnly);
   }

   public static AppDeploymentMBean[] getAppDeployments(DomainMBean domain, String appName, String rgt, String rg, String partition, boolean appOnly) {
      if (domain != null && appName != null) {
         AppDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibsForGivenScope(domain, rgt, rg, partition, appOnly);
         if (apps == null) {
            return null;
         } else {
            ArrayList rtnList = new ArrayList();

            for(int i = 0; i < apps.length; ++i) {
               if (apps[i].getApplicationName().equals(appName) && (partition == null || partition.equals(apps[i].getPartitionName()))) {
                  rtnList.add(apps[i]);
               }
            }

            if (rtnList.isEmpty()) {
               return null;
            } else {
               AppDeploymentMBean[] rtnApps = new AppDeploymentMBean[rtnList.size()];
               rtnList.toArray(rtnApps);
               return rtnApps;
            }
         }
      } else {
         return null;
      }
   }

   private static DomainMBean getDomain() {
      return ManagementUtils.getDomainMBean();
   }

   public static AppDeploymentMBean getAppDeployment(String appName, String versionId) {
      return getAppDeployment(getDomain(), appName, versionId);
   }

   public static AppDeploymentMBean getAppDeployment(DomainMBean domain, String appName, String versionId) {
      return getAppDeployment(getDomain(), appName, versionId, (String)null, (String)null, (String)null);
   }

   public static AppDeploymentMBean getAppDeployment(DomainMBean domain, String appName, String versionId, String rgt, String rg, String partition) {
      if (domain != null && appName != null) {
         String appId = getApplicationId(appName, versionId);
         AppDeploymentMBean mbean = AppDeploymentHelper.lookupAppOrLibInGivenScope(appId, rgt, rg, partition, domain);
         if (mbean != null) {
            return mbean;
         } else {
            String archiveVersion = getArchiveVersion(versionId);
            String planVersion = getPlanVersion(versionId);
            if (versionId != null && planVersion != null) {
               return null;
            } else {
               AppDeploymentMBean[] apps = AppDeploymentHelper.getAppsAndLibsForGivenScope(domain, rgt, rg, partition, false);
               if (apps == null) {
                  return null;
               } else {
                  AppDeploymentMBean rtnApp = null;

                  for(int i = 0; i < apps.length; ++i) {
                     AppDeploymentMBean curApp = apps[i];
                     if (curApp.getApplicationName().equals(appName) && (partition == null || partition.equals(curApp.getPartitionName()))) {
                        if (versionId == null) {
                           return curApp;
                        }

                        String curArchiveVersion = getArchiveVersion(curApp.getVersionIdentifier());
                        if (archiveVersion.equals(curArchiveVersion)) {
                           if (isActiveVersion(curApp)) {
                              return curApp;
                           }

                           if (rtnApp == null) {
                              rtnApp = curApp;
                           }
                        }
                     }
                  }

                  return rtnApp;
               }
            }
         }
      } else {
         return null;
      }
   }

   public static boolean isActiveVersion(AppDeploymentMBean app) {
      return isActiveVersion(app.getName());
   }

   public static boolean isActiveVersion(String appId) {
      if (ApplicationVersionUtils.ApplicationRuntimeStateInitializer.appRTStateMgr.isActiveVersion(appId)) {
         return true;
      } else {
         String pNameToUseForLookup = getPartitionName(appId);
         if ("DOMAIN".equals(pNameToUseForLookup)) {
            pNameToUseForLookup = null;
         }

         ApplicationRuntimeMBean appRT = getApplicationRuntime(getApplicationName(appId), getVersionId(appId), pNameToUseForLookup);
         return appRT != null && (appRT.getActiveVersionState() == 1 || appRT.getActiveVersionState() == 2);
      }
   }

   public static boolean isAdminMode(AppDeploymentMBean app) {
      return app == null ? false : isAdminMode(app.getName());
   }

   public static boolean isAdminMode(String appId) {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return ApplicationVersionUtils.ApplicationRuntimeStateInitializer.appRTStateMgr.isAdminMode(appId);
      } else {
         String pNameToUseForLookup = getPartitionName(appId);
         if ("DOMAIN".equals(pNameToUseForLookup)) {
            pNameToUseForLookup = null;
         }

         ApplicationRuntimeMBean appRT = getApplicationRuntime(getApplicationName(appId), getVersionId(appId), pNameToUseForLookup);
         return appRT != null && appRT.getActiveVersionState() == 1;
      }
   }

   public static BasicDeploymentMBean getDeployment(DomainMBean proposedDomain, String appId) {
      BasicDeploymentMBean depMBean = ManagementUtils.getAppDeploymentMBeanByName(appId, proposedDomain);
      return (BasicDeploymentMBean)(depMBean != null ? depMBean : proposedDomain.lookupSystemResource(appId));
   }

   public static AppDeploymentMBean getActiveAppDeployment(DomainMBean inDomain, String appName, boolean adminMode) {
      return getActiveAppDeployment(inDomain, appName, (String)null, (String)null, (String)null, adminMode);
   }

   public static AppDeploymentMBean getActiveAppDeployment(DomainMBean inDomain, String appName, String rgt, String rg, String partition, boolean adminMode) {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         return getActiveAppDeploymentAdmin(inDomain, appName, rgt, rg, partition, adminMode);
      } else {
         ApplicationRuntimeMBean appRT = getActiveApplicationRuntime(appName, partition, adminMode);
         if (appRT == null) {
            return null;
         } else {
            String versionId = appRT.getApplicationVersion();
            return getAppDeployment(inDomain, appName, versionId, rgt, rg, partition);
         }
      }
   }

   public static AppDeploymentMBean getActiveAppDeployment(String appName, boolean adminMode) {
      return getActiveAppDeployment(getDomain(), appName, adminMode);
   }

   public static AppDeploymentMBean getActiveAppDeployment(String appName) {
      return getActiveAppDeployment(appName, false);
   }

   public static AppDeploymentMBean getActiveAppDeployment(DomainMBean domain, String appName) {
      return getActiveAppDeployment(domain, appName, false);
   }

   public static AppDeploymentMBean getActiveAppDeploymentAdmin(DomainMBean inDomain, String appName, boolean adminMode) {
      return getActiveAppDeploymentAdmin(inDomain, appName, (String)null, (String)null, (String)null, adminMode);
   }

   public static AppDeploymentMBean getActiveAppDeploymentAdmin(DomainMBean inDomain, String appName, String rgt, String rg, String partition, boolean adminMode) {
      if (appName == null) {
         return null;
      } else {
         DomainMBean domain = inDomain;
         if (inDomain == null) {
            domain = getDomain();
         }

         AppDeploymentMBean[] deps = AppDeploymentHelper.getAppsAndLibsForGivenScope(domain, rgt, rg, partition, false);
         if (deps == null) {
            return null;
         } else {
            for(int i = 0; i < deps.length; ++i) {
               AppDeploymentMBean dep = deps[i];
               if (dep.getApplicationName().equals(appName) && isAdminMode(dep) == adminMode && (partition == null || partition.equals(dep.getPartitionName()))) {
                  if (dep.getVersionIdentifier() == null) {
                     return dep;
                  }

                  if (partition == null) {
                     if (ApplicationVersionUtils.ApplicationRuntimeStateInitializer.appRTStateMgr.isActiveVersion(dep)) {
                        return dep;
                     }
                  } else if (partition.equals(dep.getPartitionName()) && ApplicationVersionUtils.ApplicationRuntimeStateInitializer.appRTStateMgr.isActiveVersion(getApplicationId(appName, dep.getVersionIdentifier(), partition))) {
                     return dep;
                  }
               }
            }

            return null;
         }
      }
   }

   public static ApplicationRuntimeMBean[] getApplicationRuntimes(String appName) {
      if (appName == null) {
         return null;
      } else {
         ApplicationRuntimeMBean[] apps = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getApplicationRuntimes();
         if (apps == null) {
            return null;
         } else {
            ArrayList rtnList = new ArrayList();

            for(int i = 0; i < apps.length; ++i) {
               if (apps[i].getApplicationName().equals(appName)) {
                  rtnList.add(apps[i]);
               }
            }

            if (rtnList.isEmpty()) {
               return null;
            } else {
               ApplicationRuntimeMBean[] rtnApps = new ApplicationRuntimeMBean[rtnList.size()];
               rtnList.toArray(rtnApps);
               return rtnApps;
            }
         }
      }
   }

   public static ApplicationRuntimeMBean getApplicationRuntime(String appName, String versionId) {
      return getApplicationRuntime(appName, versionId, false, 0);
   }

   public static ApplicationRuntimeMBean getApplicationRuntime(String appName, String versionId, String partition) {
      return getApplicationRuntime(appName, versionId, partition, false, 0);
   }

   public static ApplicationRuntimeMBean getActiveApplicationRuntime(String appName, boolean adminMode) {
      return getActiveApplicationRuntime(appName, (String)null, adminMode);
   }

   public static ApplicationRuntimeMBean getActiveApplicationRuntime(String appName, String partition, boolean adminMode) {
      return getApplicationRuntime(appName, (String)null, partition, true, adminMode ? 1 : 2);
   }

   public static ApplicationRuntimeMBean getActiveApplicationRuntime(String appName) {
      return getActiveApplicationRuntime(appName, false);
   }

   private static ApplicationRuntimeMBean getApplicationRuntime(String appName, String versionId, boolean activeVersion, int activeVersionState) {
      return getApplicationRuntime(appName, versionId, (String)null, activeVersion, activeVersionState);
   }

   private static ApplicationRuntimeMBean getApplicationRuntime(String appName, String versionId, String partition, boolean activeVersion, int activeVersionState) {
      if (appName == null) {
         return null;
      } else {
         ApplicationRuntimeMBean[] apps = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getApplicationRuntimes();
         if (apps == null) {
            return null;
         } else {
            for(int i = 0; i < apps.length; ++i) {
               ApplicationRuntimeMBean mbean = apps[i];
               if (appName.equals(mbean.getApplicationName()) && (partition == null || partition.equals(mbean.getPartitionName()))) {
                  if (versionId == null) {
                     if (!activeVersion || mbean.getActiveVersionState() == activeVersionState) {
                        return mbean;
                     }
                  } else if (versionId.equals(mbean.getApplicationVersion())) {
                     return mbean;
                  }
               }
            }

            return null;
         }
      }
   }

   public static ApplicationRuntimeMBean getCurrentApplicationRuntime() {
      ApplicationContextInternal appCtx = applicationAccess.getCurrentApplicationContext();
      return appCtx == null ? null : appCtx.getRuntime();
   }

   public static void setAppContextParam(ApplicationContext inAppCtx, String paramName, boolean paramValue) {
      if (inAppCtx != null) {
         ApplicationContextInternal appCtx = (ApplicationContextInternal)inAppCtx;
         Map params = appCtx.getApplicationParameters();
         if (params == null || params == Collections.EMPTY_MAP) {
            params = new HashMap();
            appCtx.setApplicationParameters((Map)params);
         }

         ((Map)params).put(paramName, Boolean.toString(paramValue));
      }
   }

   public static void setAdminModeAppCtxParam(ApplicationContext appCtx, boolean value) {
      setAppContextParam(appCtx, "weblogic.app.adminMode", value);
   }

   public static void setIgnoreSessionsAppCtxParam(ApplicationContext appCtx, boolean value) {
      setAppContextParam(appCtx, "weblogic.app.ignoreSessions", value);
   }

   public static void setAppContextParam(ApplicationContext inAppCtx, String paramName, int paramValue) {
      if (inAppCtx != null) {
         ApplicationContextInternal appCtx = (ApplicationContextInternal)inAppCtx;
         Map params = appCtx.getApplicationParameters();
         if (params == null || params == Collections.EMPTY_MAP) {
            params = new HashMap();
            appCtx.setApplicationParameters((Map)params);
         }

         ((Map)params).put(paramName, Integer.toString(paramValue));
      }
   }

   public static void setRMIGracePeriodAppCtxParam(ApplicationContext appCtx, int secs) {
      setAppContextParam(appCtx, "weblogic.app.rmiGracePeriod", secs);
   }

   public static void setActiveVersionState(ApplicationContextInternal appCtx, int state) throws DeploymentException {
      ApplicationRuntimeMBean appRTMBean = appCtx.getRuntime();
      ApplicationRuntimeMBean prevActiveAppRTMBean = null;
      if (appRTMBean != null) {
         try {
            if (appRTMBean.getApplicationVersion() != null && (state == 2 || state == 1)) {
               prevActiveAppRTMBean = getActiveApplicationRuntime(appCtx.getApplicationName(), appCtx.getPartitionName(), state == 1);
            }

            appRTMBean.setActiveVersionState(state);
            if (prevActiveAppRTMBean != null) {
               if (appCtx.isStaticDeploymentOperation()) {
                  if (state == 1) {
                     PropertyChangeListener[] list = ((RuntimeMBeanDelegate)appRTMBean).getPropertyChangeListeners();
                     if (list != null) {
                        for(int i = 0; i < list.length; ++i) {
                           if (list[i].getClass().getName().equals("weblogic.servlet.internal.ContextVersionManager$StateChangeListener")) {
                              ((RuntimeMBeanDelegate)prevActiveAppRTMBean).addPropertyChangeListener(list[i]);
                           }
                        }
                     }
                  }
               } else {
                  prevActiveAppRTMBean.setActiveVersionState(0);
               }
            }
         } catch (Exception var6) {
            Loggable l = J2EELogger.logCouldNotSetAppActiveVersionStateLoggable(getDisplayName(appCtx.getApplicationId()), var6);
            l.log();
            throw new DeploymentException(l.getMessage(), var6);
         }
      }

   }

   public static void unsetAppContextParam(ApplicationContext inAppCtx, String paramName) {
      if (inAppCtx != null) {
         ApplicationContextInternal appCtx = (ApplicationContextInternal)inAppCtx;
         Map params = appCtx.getApplicationParameters();
         if (params != null && params != Collections.EMPTY_MAP && params.remove(paramName) != null && params.size() == 0) {
            appCtx.setApplicationParameters((Map)null);
         }

      }
   }

   public static void unsetAdminModeAppCtxParam(ApplicationContext appCtx) {
      unsetAppContextParam(appCtx, "weblogic.app.adminMode");
   }

   public static void unsetIgnoreSessionsAppCtxParam(ApplicationContext appCtx) {
      unsetAppContextParam(appCtx, "weblogic.app.ignoreSessions");
   }

   public static void unsetRMIGracePeriodAppCtxParam(ApplicationContext appCtx) {
      unsetAppContextParam(appCtx, "weblogic.app.rmiGracePeriod");
   }

   public static boolean getAppContextParam(ApplicationContext inAppCtx, String paramName, boolean defValue) {
      if (inAppCtx == null) {
         return false;
      } else {
         ApplicationContextInternal appCtx = (ApplicationContextInternal)inAppCtx;
         String value = appCtx.getApplicationParameter(paramName);
         return value == null ? defValue : Boolean.valueOf(value);
      }
   }

   public static boolean getAdminModeAppCtxParam(ApplicationContext appCtx) {
      return getAppContextParam(appCtx, "weblogic.app.adminMode", false);
   }

   public static boolean getIgnoreSessionsAppCtxParam(ApplicationContext appCtx) {
      return getAppContextParam(appCtx, "weblogic.app.ignoreSessions", false);
   }

   public static int getAppContextParam(ApplicationContext inAppCtx, String paramName, int defValue) {
      if (inAppCtx == null) {
         return defValue;
      } else {
         ApplicationContextInternal appCtx = (ApplicationContextInternal)inAppCtx;
         String value = appCtx.getApplicationParameter(paramName);
         return value == null ? defValue : Integer.parseInt(value);
      }
   }

   public static int getRMIGracePeriodAppCtxParam(ApplicationContext appCtx) {
      return getAppContextParam(appCtx, "weblogic.app.rmiGracePeriod", 30);
   }

   public static boolean isVersionIdValid(String versionId) {
      if (versionId != null && versionId.length() != 0) {
         if (versionId.length() > 215) {
            return false;
         } else {
            String archiveVersion = getArchiveVersion(versionId);
            String planVersion = getPlanVersion(versionId);
            String libSpecVersion = null;
            String libImplVersion = null;
            if (isLibraryVersion(archiveVersion)) {
               libSpecVersion = getLibSpecVersion(archiveVersion);
               libImplVersion = getLibImplVersion(archiveVersion);
               archiveVersion = null;
            }

            return isVersionIdComponentValid(archiveVersion) && isVersionIdComponentValid(libSpecVersion) && isVersionIdComponentValid(libImplVersion) && isVersionIdComponentValid(planVersion);
         }
      } else {
         return true;
      }
   }

   private static boolean isVersionIdComponentValid(String versionId) {
      if (versionId != null && versionId.length() != 0) {
         for(int i = 0; i < invalidVersionIdStrings.length; ++i) {
            if (versionId.equals(invalidVersionIdStrings[i])) {
               return false;
            }
         }

         return versionId.matches("[\\w\\.\\-\\_]*");
      } else {
         return true;
      }
   }

   public static HashMap getDebugWorkContexts() {
      if (workCtxMap.isEmpty()) {
         return null;
      } else {
         HashMap map = new HashMap();

         String key;
         Object value;
         for(Iterator iter = workCtxMap.keys(); iter.hasNext(); map.put(key, value)) {
            key = (String)iter.next();
            value = workCtxMap.get(key);
            if (value instanceof StringWorkContext) {
               value = ((StringWorkContext)value).toString();
            }
         }

         return map;
      }
   }

   public static void removeAppWorkContextEntries() {
      if (!workCtxMap.isEmpty()) {
         ArrayList appCtxKeys = new ArrayList();
         Iterator iter = workCtxMap.keys();

         while(iter.hasNext()) {
            String key = (String)iter.next();
            if (key.startsWith("weblogic.app.")) {
               appCtxKeys.add(key);
            }
         }

         iter = appCtxKeys.iterator();

         while(iter.hasNext()) {
            try {
               workCtxMap.remove((String)iter.next());
            } catch (PropertyReadOnlyException var3) {
               Debug.assertion(false, "WorkContext property for is read-only");
            } catch (NoWorkContextException var4) {
               Debug.assertion(false, "No WorkContext is available");
            }
         }

      }
   }

   private static boolean isRealPartitionName(String pName, PartitionMBean[] partitions) {
      if (pName != null && pName.length() != 0) {
         if (partitions != null) {
            PartitionMBean[] var2 = partitions;
            int var3 = partitions.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               PartitionMBean pm = var2[var4];
               if (pm.getName().equals(pName)) {
                  return true;
               }
            }
         }

         DomainMBean domain = getDomain();
         PartitionMBean pm = domain.lookupPartition(pName);
         if (pm != null) {
            return true;
         } else {
            EditAccess editAccess = getEditSessionTool().getEditContext();

            try {
               if (editAccess != null) {
                  domain = editAccess.getDomainBeanWithoutLock();
                  if (domain != null) {
                     pm = domain.lookupPartition(pName);
                     if (pm != null) {
                        return true;
                     }
                  }
               }
            } catch (Exception var7) {
               return false;
            }

            editAccess = ManagementServiceRestricted.getEditAccess(kernelId);

            try {
               if (editAccess != null) {
                  domain = editAccess.getDomainBeanWithoutLock();
                  if (domain != null) {
                     pm = domain.lookupPartition(pName);
                     if (pm != null) {
                        return true;
                     }
                  }
               }

               return false;
            } catch (Exception var6) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static EditSessionTool getEditSessionTool() {
      synchronized(editSessionToolLock) {
         if (editSessionTool == null) {
            editSessionTool = (EditSessionTool)GlobalServiceLocator.getServiceLocator().getService(EditSessionTool.class, new Annotation[0]);
         }
      }

      return editSessionTool;
   }

   @Service
   private static class ApplicationVersionUtilsServiceImpl implements ApplicationVersionUtilsService {
      public String getPartitionName(String deploymentName, PartitionMBean[] partitions) {
         return ApplicationVersionUtils.getPartitionName(deploymentName, partitions);
      }

      public String getApplicationName(String appId, PartitionMBean[] partitions) {
         return ApplicationVersionUtils.getApplicationName(appId, partitions);
      }

      public String getVersionId(String appId, PartitionMBean[] partitions) {
         return ApplicationVersionUtils.getVersionId(appId, partitions);
      }

      public String getApplicationId(String appName, String versionId) {
         return ApplicationVersionUtils.getApplicationId(appName, versionId);
      }

      public String getApplicationName(String appId) {
         return ApplicationVersionUtils.getApplicationName(appId);
      }

      public String getPartitionName(String deploymentName) {
         return ApplicationVersionUtils.getPartitionName(deploymentName);
      }

      public String getVersionId(String appId) {
         return ApplicationVersionUtils.getVersionId(appId);
      }

      public String getCurrentVersionId(String appName) {
         return ApplicationVersionUtils.getCurrentVersionId(appName);
      }

      public String getCurrentApplicationId() {
         return ApplicationVersionUtils.getCurrentApplicationId();
      }

      public HashMap getDebugWorkContexts() {
         return ApplicationVersionUtils.getDebugWorkContexts();
      }

      public void setCurrentVersionId(String appName, String versionId) {
         ApplicationVersionUtils.setCurrentVersionId(appName, versionId);
      }

      public boolean isAdminModeRequest() {
         return ApplicationVersionUtils.isAdminModeRequest();
      }

      public String getBindApplicationId() {
         return ApplicationVersionUtils.getBindApplicationId();
      }

      public String getDetailedInfoAboutVersionContext() {
         return ApplicationVersionUtils.getDetailedInfoAboutVersionContext();
      }

      public String getCurrentVersionId() {
         return ApplicationVersionUtils.getCurrentVersionId();
      }

      public String getDisplayName(String appName, String versionId) {
         return ApplicationVersionUtils.getDisplayName(appName, versionId);
      }

      public String getDisplayName(BasicDeploymentMBean dep) {
         return ApplicationVersionUtils.getDisplayName(dep);
      }

      public String getDisplayName(String appId) {
         return ApplicationVersionUtils.getDisplayName(appId);
      }

      public ApplicationRuntimeMBean getCurrentApplicationRuntime() {
         return ApplicationVersionUtils.getCurrentApplicationRuntime();
      }

      public void setBindApplicationId(String appId) {
         ApplicationVersionUtils.setBindApplicationId(appId);
      }

      public void unsetBindApplicationId() {
         ApplicationVersionUtils.unsetBindApplicationId();
      }
   }

   private static class ApplicationRuntimeStateInitializer {
      private static final ApplicationRuntimeStateManager appRTStateMgr = (ApplicationRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(ApplicationRuntimeStateManager.class, new Annotation[0]);
   }
}
