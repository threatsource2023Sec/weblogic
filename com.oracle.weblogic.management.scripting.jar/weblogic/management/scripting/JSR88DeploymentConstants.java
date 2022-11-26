package weblogic.management.scripting;

import java.util.HashSet;
import java.util.Set;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

public class JSR88DeploymentConstants {
   static final PyString CLUSTER_DEPLOYMENT_TIMEOUT = new PyString("clusterDeploymentTimeout");
   static final PyString DELTA = new PyString("delta");
   static final PyString GRACEFUL_IGNORE_SESSIONS = new PyString("gracefulIgnoreSessions");
   static final PyString GRACEFUL_PRODUCTION_TO_ADMIN = new PyString("gracefulProductionToAdmin");
   static final PyString IS_LIBRARY_MODULE = new PyString("libraryModule");
   static final PyString RETIRE_GRACEFULLY = new PyString("retireGracefully");
   static final PyString RETIRE_TIMEOUT = new PyString("retireTimeout");
   static final PyString RMI_GRACE_PERIOD = new PyString("rmiGracePeriod");
   static final PyString SECURITY_MODEL = new PyString("securityModel");
   static final PyString SECURITY_VALIDATION_ENABLED = new PyString("securityValidationEnabled");
   static final PyString STAGE_MODE = new PyString("stageMode");
   static final PyString PLAN_STAGE_MODE = new PyString("planStageMode");
   static final PyString TEST_MODE = new PyString("testMode");
   static final PyString ADMIN_MODE = new PyString("adminMode");
   static final PyString BLOCK = new PyString("block");
   static final PyString CREATE_PLAN = new PyString("createPlan");
   static final PyString DEPLOY_UPLOAD = new PyString("upload");
   static final PyString REMOTE = new PyString("remote");
   static final PyString SUB_MODULE_TARGETS = new PyString("subModuleTargets");
   static final PyString ARCHIVE_VERSION = new PyString("archiveVersion");
   static final PyString PLAN_VERSION = new PyString("planVersion");
   static final PyString LIBRARY_SPEC_VERSION = new PyString("libSpecVersion");
   static final PyString LIBRARY_IMPL_VERSION = new PyString("libImplVersion");
   static final PyString ALT_DD = new PyString("altDD");
   static final PyString ALT_WLS_DD = new PyString("altWlsDD");
   static final PyString VERSION_IDENTIFIER = new PyString("versionIdentifier");
   static final PyString FORCE_UNDEPLOYMENT_TIMEOUT = new PyString("forceUndeployTimeout");
   static final PyString DEFAULT_SUBMODULE_TARGETS = new PyString("defaultSubmoduleTargets");
   static final PyString TIME_OUT = new PyString("timeout");
   static final PyString DEPLOYMENT_ORDER = new PyString("deploymentOrder");
   static final PyString TARGETS = new PyString("targets");
   static final PyString APP_PATH = new PyString("appPath");
   static final PyString PARTITION = new PyString("partition");
   static final PyString RESOURCE_GROUP = new PyString("resourceGroup");
   static final PyString RESOURCE_GROUP_TEMPLATE = new PyString("resourceGroupTemplate");
   static final PyString DEPLOYMENT_PRINCIPAL_NAME = new PyString("deploymentPrincipalName");
   static final PyString SPECIFIED_TARGETS_ONLY = new PyString("specifiedTargetsOnly");
   static final PyString REMOVE_PLAN_OVERRIDE = new PyString("removePlanOverride");
   static final PyString PLAN_PATH = new PyString("planPath");
   static final PyString SPECIFIED_MODULES = new PyString("specifiedModules");
   private static Set recognizedOptions;

   private static synchronized Set getRecognizedOptions() {
      if (recognizedOptions == null) {
         recognizedOptions = new HashSet();
         recognizedOptions.add(ADMIN_MODE.toString());
         recognizedOptions.add(CLUSTER_DEPLOYMENT_TIMEOUT.toString());
         recognizedOptions.add(DELTA.toString());
         recognizedOptions.add(GRACEFUL_IGNORE_SESSIONS.toString());
         recognizedOptions.add(GRACEFUL_PRODUCTION_TO_ADMIN.toString());
         recognizedOptions.add(IS_LIBRARY_MODULE.toString());
         recognizedOptions.add(RETIRE_GRACEFULLY.toString());
         recognizedOptions.add(RETIRE_TIMEOUT.toString());
         recognizedOptions.add(RMI_GRACE_PERIOD.toString());
         recognizedOptions.add(SECURITY_MODEL.toString());
         recognizedOptions.add(SECURITY_VALIDATION_ENABLED.toString());
         recognizedOptions.add(STAGE_MODE.toString());
         recognizedOptions.add(PLAN_STAGE_MODE.toString());
         recognizedOptions.add(TEST_MODE.toString());
         recognizedOptions.add(BLOCK.toString());
         recognizedOptions.add(CREATE_PLAN.toString());
         recognizedOptions.add(DEPLOY_UPLOAD.toString());
         recognizedOptions.add(REMOTE.toString());
         recognizedOptions.add(SUB_MODULE_TARGETS.toString());
         recognizedOptions.add(ARCHIVE_VERSION.toString());
         recognizedOptions.add(PLAN_VERSION.toString());
         recognizedOptions.add(LIBRARY_SPEC_VERSION.toString());
         recognizedOptions.add(LIBRARY_IMPL_VERSION.toString());
         recognizedOptions.add(ALT_DD.toString());
         recognizedOptions.add(ALT_WLS_DD.toString());
         recognizedOptions.add(VERSION_IDENTIFIER.toString());
         recognizedOptions.add(FORCE_UNDEPLOYMENT_TIMEOUT.toString());
         recognizedOptions.add(DEFAULT_SUBMODULE_TARGETS.toString());
         recognizedOptions.add(TIME_OUT.toString());
         recognizedOptions.add(TARGETS.toString());
         recognizedOptions.add(APP_PATH.toString());
         recognizedOptions.add(PARTITION.toString());
         recognizedOptions.add(RESOURCE_GROUP.toString());
         recognizedOptions.add(RESOURCE_GROUP_TEMPLATE.toString());
         recognizedOptions.add(SPECIFIED_MODULES.toString());
         recognizedOptions.add(SPECIFIED_TARGETS_ONLY.toString());
         recognizedOptions.add(DEPLOYMENT_ORDER.toString());
         recognizedOptions.add(REMOVE_PLAN_OVERRIDE.toString());
         recognizedOptions.add(PLAN_PATH.toString());
      }

      return recognizedOptions;
   }

   protected Set getUnrecognizedOptions(PyDictionary pyDic) {
      if (pyDic == null) {
         return null;
      } else {
         PyList keys = pyDic.keys();
         if (keys == null) {
            return null;
         } else {
            Set recognized = getRecognizedOptions();
            HashSet returnSet = null;
            PyObject key = keys.pop();

            while(key != null) {
               if (!recognized.contains(key.toString())) {
                  if (returnSet == null) {
                     returnSet = new HashSet();
                  }

                  returnSet.add(key.toString());
               }

               try {
                  key = keys.pop();
               } catch (PyException var7) {
                  key = null;
               }
            }

            return returnSet;
         }
      }
   }
}
