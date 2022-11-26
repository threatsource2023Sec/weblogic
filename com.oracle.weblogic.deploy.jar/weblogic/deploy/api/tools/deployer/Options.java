package weblogic.deploy.api.tools.deployer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.utils.Getopt2;

public class Options implements Serializable {
   private static final long serialVersionUID = 1L;
   private transient Getopt2 opts;
   private static transient DeployerTextFormatter cat;
   public static final String OPTION_CANCEL = "cancel";
   public static final String OPTION_LIST = "list";
   public static final String OPTION_DEPLOY = "deploy";
   public static final String OPTION_UNDEPLOY = "undeploy";
   public static final String OPTION_DISTRIBUTE = "distribute";
   public static final String OPTION_START = "start";
   public static final String OPTION_STOP = "stop";
   public static final String OPTION_REDEPLOY = "redeploy";
   public static final String OPTION_LIST_APP = "listapps";
   public static final String OPTION_LIST_TASK = "listtask";
   public static final String OPTION_PURGE_TASKS = "purgetasks";
   public static final String OPTION_UPDATE = "update";
   public static final String OPTION_EXTENDLOADER = "extendloader";
   public static final String OPTION_ACTIVATE = "activate";
   public static final String OPTION_DEACTIVATE = "deactivate";
   public static final String OPTION_UNPREPARE = "unprepare";
   public static final String OPTION_REMOVE = "remove";
   public static final String OPTION_VERBOSE = "verbose";
   public static final String OPTION_DEBUG = "debug";
   public static final String OPTION_EXAMPLES = "examples";
   public static final String OPTION_NOWAIT = "nowait";
   public static final String OPTION_NOSTAGE = "nostage";
   public static final String OPTION_STAGE = "stage";
   public static final String OPTION_EXTERNAL_STAGE = "external_stage";
   public static final String OPTION_UPLOAD = "upload";
   public static final String OPTION_DELETE_FILES = "delete_files";
   public static final String OPTION_REMOTE = "remote";
   public static final String OPTION_ADMIN_MODE = "adminmode";
   public static final String OPTION_GRACEFUL = "graceful";
   public static final String OPTION_IGNORE_SESSIONS = "ignoresessions";
   public static final String OPTION_RMI_GRACE_PERIOD = "rmigraceperiod";
   public static final String OPTION_ALL_VERSIONS = "allversions";
   public static final String OPTION_SECURITY_VALIDATE = "enableSecurityValidation";
   public static final String OPTION_LIB_MODULE = "library";
   public static final String OPTION_NOEXIT = "noexit";
   public static final String OPTION_NO_VERSION = "noversion";
   public static final String OPTION_DEFAULT_SUBMODULE_TARGETS = "defaultsubmoduletargets";
   public static final String OPTION_NO_DEFAULT_SUBMODULE_TARGETS = "nodefaultsubmoduletargets";
   public static final String OPTION_USE_NONEXCLUSIVE_LOCK = "usenonexclusivelock";
   public static final String OPTION_PLAN_NOSTAGE = "plannostage";
   public static final String OPTION_PLAN_STAGE = "planstage";
   public static final String OPTION_PLAN_EXTERNAL_STAGE = "planexternal_stage";
   public static final String OPTION_SPECIFIED_TARGETS_ONLY = "specifiedTargetsOnly";
   public static final String OPTION_REMOVE_PLAN_OVERRIDE = "removePlanOverride";
   public static final String OPTION_ALT_APP_DD = "altappdd";
   public static final String OPTION_SOURCE = "source";
   public static final String OPTION_NAME = "name";
   public static final String OPTION_APP_VERSION = "appversion";
   public static final String OPTION_PLAN_VERSION = "planversion";
   public static final String OPTION_RETIRE_TIMEOUT = "retiretimeout";
   public static final String OPTION_TARGETS = "targets";
   public static final String OPTION_SUBMODULE_TARGETS = "submoduletargets";
   public static final String OPTION_SECURITY_MODEL = "securityModel";
   public static final String OPTION_LIB_SPEC_VERSION = "libspecver";
   public static final String OPTION_LIB_IMPL_VERSION = "libimplver";
   public static final String OPTION_ID = "id";
   public static final String OPTION_TIMEOUT = "timeout";
   public static final String OPTION_PLAN = "plan";
   public static final String OPTION_DEPLOYMENT_ORDER = "deploymentorder";
   public static final String OPTION_SUCCEED_IF_NAME_USED = "succeedIfNameUsed";
   public static final String OPTION_PARTITION = "partition";
   public static final String OPTION_RESOURCE_GROUP = "resourceGroup";
   public static final String OPTION_RESOURCE_GROUP_TEMPLATE = "resourceGroupTemplate";
   public static final String OPTION_SPECIFIED_MODULES = "specifiedModules";
   public static final String OPTION_EDIT_SESSION = "editsession";
   public static final String OPTION_ALT_WLS_DD = "altwlsappdd";
   public static final String OPTION_OUTPUT = "output";
   public static final String RAW_OUTPUT = "raw";
   public static final String FORMATTED_OUTPUT = "formatted";
   public static final String OPTION_SRCROOT = "sourcerootforupload";
   public static final transient HashSet allOptions = new HashSet();
   public boolean verbose;
   public boolean debug;
   public boolean examples;
   public boolean nowait;
   public String stageMode = null;
   public String planStageMode = null;
   public boolean upload;
   public boolean deleteFiles;
   public boolean remote;
   public boolean adminMode;
   public boolean graceful;
   public boolean ignoreSessions;
   public int rmiGracePeriod;
   public boolean allVersions;
   public boolean securityValidation;
   public boolean libModule;
   public String source;
   public String name;
   public String appVersion;
   public String planVersion;
   public boolean noVersion;
   public int retireTimeout = -1;
   public String securityModel;
   public String libSpecVersion;
   public String libImplVersion;
   public String id;
   public long timeout = 0L;
   public int deploymentOrder = 100;
   public String altAppDD;
   public String altWlsAppDD;
   public boolean sourceFromOpts = false;
   public boolean removeOp;
   public boolean cancelOp;
   public boolean listOp;
   public boolean deployOp;
   public boolean undeployOp;
   public boolean distributeOp;
   public boolean startOp;
   public boolean stopOp;
   public boolean redeployOp;
   public boolean listappOp;
   public boolean listtaskOp;
   public boolean purgetasksOp;
   public boolean updateOp;
   public boolean activateOp;
   public boolean deactivateOp;
   public boolean unprepareOp;
   public boolean extendLoaderOp;
   public String[] moduleTargets;
   public String[] submoduleTargets;
   public String[] delta;
   public boolean sourceFromArgs = false;
   public boolean formatted;
   public boolean noexit;
   public String plan;
   public boolean isDefaultSubmoduleTargets;
   public boolean useNonExclusiveLock = false;
   public boolean succeedIfNameUsed = false;
   public boolean specifiedTargetsOnly = false;
   public String partition;
   public String resourceGroup;
   public String resourceGroupTemplate;
   public String[] specifiedModules;
   public String editSession;
   public boolean removePlanOverride = false;
   public static final long MAX_NOTIFICATION_WAIT = 1000L;

   Options(Getopt2 opts) throws IllegalArgumentException {
      this.opts = opts;
      this.prepare();
   }

   Options() {
   }

   public Getopt2 getOpts() {
      return this.opts;
   }

   private void prepare() {
      this.opts.setUsageArgs(cat.usageArgs());
      this.opts.setUsageFooter(cat.usageTrailer());
      this.opts.addFlag("distribute", cat.usageDistribute());
      this.opts.addFlag("start", cat.usageStart());
      this.opts.addFlag("stop", cat.usageStop());
      this.opts.addFlag("redeploy", cat.usageRedeploy());
      this.opts.addFlag("undeploy", cat.usageUndeploy());
      this.opts.addFlag("deploy", cat.usageDeploy());
      this.opts.addFlag("update", cat.usageUpdate());
      this.opts.addFlag("extendloader", cat.usageExtendLoader());
      this.opts.addAdvancedFlag("removePlanOverride", cat.usageRemovePlanOverride());
      this.opts.addAdvancedFlag("cancel", cat.usageCancel());
      this.opts.addAdvancedFlag("list", cat.usageList());
      this.opts.addAdvancedFlag("listtask", cat.usageList());
      this.opts.addAdvancedFlag("listapps", cat.usageListApps());
      this.opts.addAdvancedFlag("purgetasks", cat.usageAdPurgeTasks());
      this.opts.addFlag("examples", cat.usageExamples());
      this.opts.addOption("name", cat.exampleName(), cat.usageName());
      this.opts.addOption("targets", cat.exampleTargets(), cat.usageAdTargets());
      this.opts.addOption("plan", cat.examplePlan(), cat.usagePlan());
      this.opts.addFlag("library", cat.usageLibrary());
      this.opts.addAdvancedFlag("verbose", cat.usageVerbose());
      this.opts.addAdvancedOption("output", "raw", cat.usageOutput());
      this.opts.addAdvancedFlag("debug", cat.usageDebug());
      this.opts.addAdvancedFlag("upload", cat.usageUpload());
      this.opts.addAdvancedFlag("delete_files", cat.usageDeleteFiles());
      this.opts.addAdvancedFlag("remote", cat.usageRemote());
      this.opts.addAdvancedFlag("nostage", cat.usageNoStage());
      this.opts.addAdvancedFlag("external_stage", cat.usageExternalStage());
      this.opts.addAdvancedFlag("stage", cat.usageStage());
      this.opts.addAdvancedFlag("plannostage", cat.usagePlanNoStage());
      this.opts.addAdvancedFlag("planexternal_stage", cat.usagePlanExternalStage());
      this.opts.addAdvancedFlag("planstage", cat.usagePlanStage());
      this.opts.addAdvancedFlag("nowait", cat.usageNoWait());
      this.opts.addAdvancedOption("timeout", cat.exampleTimeout(), cat.usageTimeout());
      this.opts.addAdvancedOption("deploymentorder", cat.exampleDeploymentOrder(), cat.usageDeploymentOrder());
      this.opts.addAdvancedOption("source", cat.exampleSource(), cat.usageSource());
      this.opts.addAdvancedOption("altappdd", cat.exampleAltAppDD(), cat.usageAltAppDD());
      this.opts.addAdvancedOption("altwlsappdd", cat.exampleAltWebDD(), cat.usageAltWebDD());
      this.opts.addAdvancedOption("appversion", cat.exampleAppVersion(), cat.usageAppVersion());
      this.opts.addAdvancedOption("planversion", cat.examplePlanVersion(), cat.usagePlanVersion());
      this.opts.addAdvancedFlag("noversion", cat.usageNoVersion());
      this.opts.addAdvancedOption("retiretimeout", cat.exampleRetireTimeout(), cat.usageRetireTimeout());
      this.opts.addAdvancedOption("id", cat.exampleId(), cat.usageId());
      this.opts.addAdvancedFlag("adminmode", cat.usageAdminMode());
      this.opts.addAdvancedFlag("graceful", cat.usageGraceful());
      this.opts.addAdvancedFlag("ignoresessions", cat.usageIgnoreSessions("graceful"));
      this.opts.addAdvancedOption("rmigraceperiod", cat.exampleRmiGracePeriod(), cat.usageRmiGracePeriod("graceful"));
      this.opts.addAdvancedFlag("allversions", cat.usageAllVersions("allversions"));
      this.opts.addAdvancedOption("submoduletargets", cat.paramSubModuleTargets(), cat.usageSubModuleTargets());
      this.opts.addAdvancedOption("securityModel", "DDOnly|CustomRoles|CustomRolesAndPolicies|Advanced", cat.usageSecurityModel());
      this.opts.addAdvancedFlag("enableSecurityValidation", cat.usageSecurityEnabled());
      this.opts.addAdvancedOption("libspecver", cat.exampleLibSpecVersion(), cat.usageLibSpecVersion());
      this.opts.addAdvancedOption("libimplver", cat.exampleLibImplVersion(), cat.usageLibImplVersion());
      this.opts.addAdvancedOption("partition", cat.examplePartition(), cat.usagePartition());
      this.opts.addAdvancedOption("resourceGroup", cat.exampleResourceGroup(), cat.usageResourceGroup());
      this.opts.addAdvancedOption("resourceGroupTemplate", cat.exampleResourceGroupTemplate(), cat.usageResourceGroupTemplate());
      this.opts.addAdvancedOption("specifiedModules", cat.paramSpecifiedModules(), cat.usageSpecifiedModules());
      this.opts.addAdvancedFlag("usenonexclusivelock", cat.usageUseNonexclusiveLock());
      this.opts.addAdvancedFlag("succeedIfNameUsed", cat.usageSucceedIfNameUsed());
      this.opts.addAdvancedFlag("specifiedTargetsOnly", cat.usageSpecifiedTargetsOnly());
      this.opts.addAdvancedOption("editsession", cat.exampleEditSession(), cat.usageEditSession());
      this.opts.addAdvancedFlag("defaultsubmoduletargets", cat.usageDefaultTargets());
      this.opts.markPrivate("defaultsubmoduletargets");
      this.opts.addAdvancedFlag("nodefaultsubmoduletargets", cat.usageNoDefaultTargets());
      this.opts.markPrivate("nodefaultsubmoduletargets");
      this.opts.addAdvancedFlag("activate", cat.usageActivate());
      this.opts.markPrivate("activate");
      this.opts.addAdvancedFlag("deactivate", cat.usageDeactivate());
      this.opts.markPrivate("deactivate");
      this.opts.addAdvancedFlag("unprepare", cat.usageUnprepare());
      this.opts.markPrivate("unprepare");
      this.opts.addAdvancedFlag("remove", cat.usageRemove());
      this.opts.markPrivate("remove");
      this.opts.addAdvancedOption("sourcerootforupload", cat.exampleSourceRootForUpload(), cat.usageSourceRootForUpload());
      this.opts.markPrivate("sourcerootforupload");
   }

   void extractOptions() throws IllegalArgumentException {
      this.verbose = this.opts.getBooleanOption("verbose", false);
      this.debug = this.opts.getBooleanOption("debug", false);
      String s;
      if (this.debug) {
         s = System.getProperty("weblogic.deployer.debug");
         if (s == null) {
            System.setProperty("weblogic.deployer.debug", "deploy");
         }
      }

      this.examples = this.opts.getBooleanOption("examples", false);
      this.nowait = this.opts.getBooleanOption("nowait", false);
      if (this.opts.getBooleanOption("stage", false)) {
         this.stageMode = "stage";
      } else if (this.opts.getBooleanOption("nostage", false)) {
         this.stageMode = "nostage";
      } else if (this.opts.getBooleanOption("external_stage", false)) {
         this.stageMode = "external_stage";
      }

      if (this.opts.getBooleanOption("planstage", false)) {
         this.planStageMode = "stage";
      } else if (this.opts.getBooleanOption("plannostage", false)) {
         this.planStageMode = "nostage";
      } else if (this.opts.getBooleanOption("planexternal_stage", false)) {
         this.planStageMode = "external_stage";
      }

      this.upload = this.opts.getBooleanOption("upload", false);
      this.deleteFiles = this.opts.getBooleanOption("delete_files", false);
      this.remote = this.opts.getBooleanOption("remote", false);
      this.adminMode = this.opts.getBooleanOption("adminmode", false);
      this.graceful = this.opts.getBooleanOption("graceful", false);
      this.ignoreSessions = this.opts.getBooleanOption("ignoresessions", false);
      this.allVersions = this.opts.getBooleanOption("allversions", false);
      this.securityValidation = this.opts.getBooleanOption("enableSecurityValidation", false);
      this.libModule = this.opts.getBooleanOption("library", false);
      this.noexit = this.opts.getBooleanOption("noexit", false);
      this.isDefaultSubmoduleTargets = !this.opts.hasOption("nodefaultsubmoduletargets");
      this.useNonExclusiveLock = this.opts.hasOption("usenonexclusivelock");
      this.succeedIfNameUsed = this.opts.getBooleanOption("succeedIfNameUsed", false);
      this.specifiedTargetsOnly = this.opts.getBooleanOption("specifiedTargetsOnly", false);
      this.removePlanOverride = this.opts.getBooleanOption("removePlanOverride", false);
      this.formatted = this.getOutputFormat();
      this.source = this.opts.getOption("source");
      if (this.source != null) {
         this.sourceFromOpts = true;
      }

      this.name = this.opts.getOption("name");
      this.appVersion = this.opts.getOption("appversion");
      this.planVersion = this.opts.getOption("planversion");
      this.noVersion = this.opts.getBooleanOption("noversion", false);
      this.retireTimeout = this.opts.getIntegerOption("retiretimeout", this.retireTimeout);
      this.rmiGracePeriod = this.opts.getIntegerOption("rmigraceperiod", -1);
      this.securityModel = this.opts.getOption("securityModel");
      this.libSpecVersion = this.opts.getOption("libspecver");
      this.libImplVersion = this.opts.getOption("libimplver");
      this.id = this.opts.getOption("id");
      this.timeout = (long)this.opts.getIntegerOption("timeout", 0);
      this.deploymentOrder = this.opts.getIntegerOption("deploymentorder", 100);
      this.altAppDD = this.opts.getOption("altappdd");
      this.altWlsAppDD = this.opts.getOption("altwlsappdd");
      this.plan = this.opts.getOption("plan");
      this.partition = this.opts.getOption("partition");
      this.editSession = this.opts.getOption("editsession");
      this.resourceGroup = this.opts.getOption("resourceGroup");
      this.resourceGroupTemplate = this.opts.getOption("resourceGroupTemplate");
      s = this.opts.getOption("sourcerootforupload");
      if (s != null) {
         if (this.source != null || this.upload) {
            throw new IllegalArgumentException(cat.errorSourceAndSourceRootNotAllowed());
         }

         this.source = s;
         this.sourceFromOpts = true;
         this.upload = true;
      }

      this.cancelOp = this.opts.hasOption("cancel");
      this.deployOp = this.opts.hasOption("deploy");
      this.undeployOp = this.opts.hasOption("undeploy");
      this.distributeOp = this.opts.hasOption("distribute");
      this.startOp = this.opts.hasOption("start");
      this.stopOp = this.opts.hasOption("stop");
      this.redeployOp = this.opts.hasOption("redeploy");
      this.listappOp = this.opts.hasOption("listapps");
      this.listtaskOp = this.opts.hasOption("listtask");
      this.purgetasksOp = this.opts.hasOption("purgetasks");
      this.updateOp = this.opts.hasOption("update");
      this.deployOp = this.opts.hasOption("deploy");
      this.stopOp = this.opts.hasOption("stop");
      this.extendLoaderOp = this.opts.hasOption("extendloader");
      this.activateOp = this.checkDeprecated("activate", "deploy");
      this.deactivateOp = this.checkDeprecated("deactivate", "stop");
      this.unprepareOp = this.checkDeprecated("unprepare", "stop");
      this.removeOp = this.checkDeprecated("remove", "undeploy");
      this.listOp = this.opts.hasOption("list");
      if (this.listOp) {
         System.out.println(cat.warningListDeprecated());
      }

      this.checkDeprecated("sourcerootforupload", "upload");
      this.moduleTargets = this.getTargets("targets");
      this.submoduleTargets = this.getTargets("submoduletargets");
      this.specifiedModules = this.getTargets("specifiedModules");
      this.delta = this.opts.args();
      if (this.delta != null && this.delta.length == 0) {
         this.delta = null;
      }

      if (this.delta != null && this.delta.length > 0 && this.source == null && this.delta.length == 1) {
         if (this.opts.hasOption("sourcerootforupload")) {
            throw new IllegalArgumentException(cat.errorSourceAndSourceRootNotAllowed());
         }

         this.source = this.delta[0];
         this.sourceFromArgs = true;
      }

      this.setImpliedLibrary();
   }

   private void setImpliedLibrary() {
      if (this.libImplVersion != null || this.libSpecVersion != null) {
         this.libModule = true;
      }

   }

   private boolean getOutputFormat() throws IllegalArgumentException {
      String fmt = this.opts.getOption("output", "raw");
      if ("raw".equals(fmt)) {
         return false;
      } else if ("formatted".equals(fmt)) {
         return true;
      } else {
         System.out.println(cat.badFormat(fmt));
         return false;
      }
   }

   private boolean checkDeprecated(String oldOp, String newOp) {
      boolean dep = this.opts.hasOption(oldOp);
      if (dep) {
         System.out.println(cat.deprecated(oldOp, newOp));
      }

      return dep;
   }

   private String[] getTargets(String opt) {
      List tl = new ArrayList();
      String t = this.opts.getOption(opt);
      if (t != null) {
         StringTokenizer st = new StringTokenizer(t, ",");

         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token != null && token.length() != 0) {
               tl.add(token);
            }
         }
      }

      return (String[])((String[])tl.toArray(new String[0]));
   }

   public boolean isRemote() {
      return this.remote;
   }

   public void setRemote(boolean b) {
      this.remote = b;
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public boolean isDebug() {
      return this.debug;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public boolean isUpload() {
      return this.upload;
   }

   public boolean isSucceedIfNameUsed() {
      return this.succeedIfNameUsed;
   }

   public void setSucceedIfNameUsed(boolean succeedIfNameUsed) {
      this.succeedIfNameUsed = succeedIfNameUsed;
   }

   static {
      allOptions.add("plan");
      allOptions.add("altwlsappdd");
      allOptions.add("altappdd");
      allOptions.add("id");
      allOptions.add("libimplver");
      allOptions.add("libspecver");
      allOptions.add("securityModel");
      allOptions.add("submoduletargets");
      allOptions.add("targets");
      allOptions.add("retiretimeout");
      allOptions.add("planversion");
      allOptions.add("appversion");
      allOptions.add("noversion");
      allOptions.add("name");
      allOptions.add("source");
      allOptions.add("library");
      allOptions.add("enableSecurityValidation");
      allOptions.add("ignoresessions");
      allOptions.add("rmigraceperiod");
      allOptions.add("graceful");
      allOptions.add("allversions");
      allOptions.add("adminmode");
      allOptions.add("delete_files");
      allOptions.add("stage");
      allOptions.add("nostage");
      allOptions.add("defaultsubmoduletargets");
      allOptions.add("nodefaultsubmoduletargets");
      allOptions.add("usenonexclusivelock");
      allOptions.add("planstage");
      allOptions.add("plannostage");
      allOptions.add("deploymentorder");
      allOptions.add("specifiedTargetsOnly");
      allOptions.add("partition");
      allOptions.add("resourceGroup");
      allOptions.add("resourceGroupTemplate");
      allOptions.add("removePlanOverride");
      allOptions.add("specifiedModules");
      allOptions.add("editsession");
      cat = new DeployerTextFormatter();
   }
}
