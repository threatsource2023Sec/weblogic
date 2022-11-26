package weblogic.management.scripting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.status.ProgressObject;
import org.python.core.ArgParser;
import org.python.core.PyDictionary;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.tools.SessionHelper;
import weblogic.deploy.api.tools.deployer.ModuleTargetInfo;
import weblogic.deploy.api.tools.deployer.SubModuleTargetInfo;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.scripting.jsr88.WLSTPlan;
import weblogic.management.scripting.jsr88.WLSTPlanImpl;
import weblogic.management.scripting.jsr88.WLSTProgress;
import weblogic.management.scripting.jsr88.WLSTProgressImpl;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtil;
import weblogic.utils.StringUtils;

public class JSR88DeployHandler extends JSR88DeploymentConstants {
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   private static final String NONE = "None";
   private static final String FALSE = "false";
   private static final String TRUE = "true";
   private WebLogicDeploymentManager dm = null;
   private SessionHelper helper = null;

   public JSR88DeployHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public WebLogicDeploymentManager getWLDM(boolean remote) throws ScriptException {
      this.init(remote);
      return this.dm;
   }

   public WebLogicDeploymentManager getWLDM() throws ScriptException {
      this.init();
      return this.dm;
   }

   private void init() throws ScriptException {
      this.init(false);
   }

   private void init(boolean remote) throws ScriptException {
      if (this.dm != null) {
         this.dm.release();
         this.dm = null;
      }

      if (this.ctx.isConnected() && !this.ctx.isAdminServer) {
         throw new ScriptException(this.txtFmt.getNotConnectedAdminServer(), this.ctx.commandType);
      } else {
         try {
            if (!this.ctx.isConnected()) {
               this.dm = SessionHelper.getDisconnectedDeploymentManager();
            } else {
               String host = this.ctx.getListenAddress(this.ctx.url);
               String port = this.ctx.getListenPort(this.ctx.url);
               String protocol = this.ctx.getProtocol(this.ctx.url);
               String path = this.ctx.getPath(this.ctx.url);
               if (remote) {
                  this.dm = SessionHelper.getRemoteDeploymentManager(protocol, host, port, path, new String(this.ctx.username_bytes), new String(this.ctx.password_bytes), this.ctx.idd_bytes != null ? new String(this.ctx.idd_bytes) : null);
               } else {
                  this.dm = SessionHelper.getDeploymentManager(protocol, host, port, path, new String(this.ctx.username_bytes), new String(this.ctx.password_bytes), this.ctx.idd_bytes != null ? new String(this.ctx.idd_bytes) : null);
               }

               if (this.ctx.edit != null) {
                  WLSTEditVariables editForName = this.ctx.getEditByName(this.ctx.edit.name, false);
                  if (editForName != null) {
                     ConfigurationManagerMBean configMgr = editForName.configurationManager;
                     if (configMgr != null) {
                        this.dm.getHelper().setConfigMgr(configMgr);
                     }
                  }
               }
            }

            if (this.helper != null) {
               this.helper.close();
               this.helper = null;
            }

            this.helper = SessionHelper.getInstance(this.dm);
         } catch (DeploymentManagerCreationException var8) {
            this.ctx.throwWLSTException(this.txtFmt.getCouldNotCreateDM(), var8);
         } catch (Throwable var9) {
            this.ctx.throwWLSTException(this.txtFmt.getCouldNotCreateDM(), var9);
         }

      }
   }

   public WLSTPlan loadApplication(String appPath, String planPath, String createPlan) throws ScriptException {
      this.ctx.println(this.txtFmt.getLoadingAppFrom(appPath));
      this.init();
      WLSTPlanImpl wlstPlan = null;
      boolean isInstallDir = false;
      boolean planExists = false;
      File moduleObject = new File(appPath);
      this.ctx.printDebug(this.txtFmt.getEnsureSubdirOfApp());
      if (moduleObject.getParentFile().getName().equals("app")) {
         isInstallDir = true;
      }

      File planFile = null;
      if (planPath == null) {
         planPath = this.getPlanPath(moduleObject.getAbsolutePath(), isInstallDir);
         planFile = new File(planPath);
         if (!planFile.exists()) {
            if (this.ctx.getBoolean(createPlan)) {
               this.ctx.println(this.txtFmt.getCreatePlan(planFile.getAbsolutePath()));
            } else {
               this.ctx.throwWLSTException(this.txtFmt.getCouldNotFindPlan());
            }
         } else {
            planExists = true;
         }
      } else {
         planFile = new File(planPath);
         if (!planFile.exists()) {
            if (this.ctx.getBoolean(createPlan)) {
               this.ctx.println(this.txtFmt.getCreatePlan(planFile.getAbsolutePath()));
            } else {
               this.ctx.throwWLSTException(this.txtFmt.getCouldNotFindPlan());
            }
         } else {
            planExists = true;
         }
      }

      try {
         if (this.ctx.getBoolean(createPlan) && !planExists) {
            this.helper.initializeConfiguration(moduleObject, (File)null);
         } else {
            this.helper.initializeConfiguration(moduleObject, planFile);
         }

         WebLogicDeploymentConfiguration wdc = this.helper.getConfiguration();
         wlstPlan = new WLSTPlanImpl(wdc, this.ctx, planFile.getAbsolutePath());
         if (this.ctx.getBoolean(createPlan) && !planExists) {
            wlstPlan.save();
         }

         String msg = this.txtFmt.getLoadedAppAndPlan(moduleObject.getAbsolutePath(), planFile.getAbsolutePath());
         this.ctx.println(msg);
         if (!WLSTUtil.runningWLSTAsModule()) {
            String planObjectName = "wlstPlan_" + wlstPlan.getDeploymentPlan().getApplicationName();
            planObjectName = planObjectName.replace('.', '_');
            this.ctx.getWLSTInterpreter().set(planObjectName, wlstPlan);
            this.ctx.println(this.txtFmt.getPlanVariableAssigned(planObjectName));
         }
      } catch (ConfigurationException var12) {
         this.ctx.throwWLSTException(this.txtFmt.getCouldNotInitConfig(), var12);
      } catch (IOException var13) {
         this.ctx.throwWLSTException(this.txtFmt.getCouldNotReadConfig(), var13);
      } catch (InvalidModuleException var14) {
         this.ctx.throwWLSTException(this.txtFmt.getInvalidModule(), var14);
      }

      return wlstPlan;
   }

   private String getExistingPlanPath(String appPath) {
      File file = new File(appPath);
      String planPath = file.getParentFile().getParentFile().getAbsolutePath() + "/plan/plan.xml";
      File f = new File(planPath);
      return f.exists() ? f.getAbsolutePath() : null;
   }

   private String getPlanPath(String appPath, boolean isInstallDir) {
      File file = new File(appPath);
      String planPath = null;
      if (isInstallDir) {
         this.ctx.printDebug(this.txtFmt.getAppPathIsDir(appPath));
         planPath = file.getParentFile().getParentFile().getAbsolutePath() + "/plan/plan.xml";
         File f = new File(planPath);
         if (!f.exists()) {
            this.ctx.printDebug(this.txtFmt.getPlanDoesNotExist(planPath));
            f = new File(file.getParentFile().getAbsolutePath() + "/plan.xml");
         }

         f.getParentFile().mkdirs();
         planPath = f.getAbsolutePath();
         this.ctx.printDebug(this.txtFmt.getPlanPathEvaluated(planPath));
         return planPath;
      } else {
         planPath = file.getParentFile().getAbsolutePath() + File.separator + "plan.xml";
         this.ctx.printDebug(this.txtFmt.getPlanPathEvaluated(planPath));
         return planPath;
      }
   }

   private TargetModuleID[] createTmidsFromApp(String appName, DeploymentOptions depOptions) {
      if (appName == null) {
         return new TargetModuleID[0];
      } else {
         TargetModuleID[] tmids = null;
         String verID = null;
         if (depOptions.getArchiveVersion() != null) {
            verID = depOptions.getArchiveVersion();
         }

         String rgt = depOptions.getResourceGroupTemplate();
         String rg = depOptions.getResourceGroup();
         String partition = depOptions.getPartition();
         AppDeploymentMBean mbean = ApplicationVersionUtils.getAppDeployment(this.dm.getHelper().getDomain(), appName, verID, rgt, rg, partition);
         if (mbean == null) {
            mbean = ApplicationVersionUtils.getAppDeployment(this.dm.getHelper().getDomain(), appName, (String)null, rgt, rg, partition);
         }

         if (mbean != null) {
            List l = this.dm.getServerConnection().getModules(mbean, depOptions);
            tmids = (TargetModuleID[])((TargetModuleID[])l.toArray(new TargetModuleID[0]));
            depOptions.setTmidsFromConfig(true);
            return rgt != null ? this.createTmidForAdminServer(tmids, appName, depOptions) : tmids;
         } else {
            tmids = new TargetModuleID[0];
            return this.createTmidForAdminServer(tmids, appName, depOptions);
         }
      }
   }

   private TargetModuleID[] createTmidForAdminServer(TargetModuleID[] tmids, String appName, DeploymentOptions dOpts) {
      if (appName == null) {
         return new TargetModuleID[0];
      } else {
         TargetModuleID[] newTmids = new TargetModuleID[tmids.length + 1];
         if (0 != tmids.length) {
            System.arraycopy(tmids, 0, newTmids, 1, tmids.length);
         }

         String admin = this.dm.getHelper().getAdminServerName();
         Target[] allt = this.dm.getTargets(dOpts);
         if (allt == null) {
            return new TargetModuleID[0];
         } else {
            for(int i = 0; i < allt.length; ++i) {
               Target target = allt[i];
               if (target.getName().equals(admin)) {
                  newTmids[0] = this.dm.createTargetModuleID(appName, WebLogicModuleType.UNKNOWN, target);
                  return newTmids;
               }
            }

            return tmids;
         }
      }
   }

   private TargetModuleID[] getTMIDs(String tgtNames, WebLogicDeploymentManager dm, String name, String subModuleTargets, DeploymentOptions depOptions) throws ScriptException {
      List tl = new ArrayList();
      Target[] domainTargets = dm.getTargets(depOptions, true);
      Target[] targets = new Target[0];
      if (tgtNames != null && tgtNames.length() != 0 || subModuleTargets != null && subModuleTargets.length() != 0) {
         if (tgtNames == null) {
            tgtNames = "";
         }

         String[] targetNames = StringUtils.splitCompletely(tgtNames, ",");
         String[] smTargetNames = null;
         Target[] additionalTargets = null;
         if (subModuleTargets != null) {
            smTargetNames = StringUtils.splitCompletely(subModuleTargets, ",");
            additionalTargets = dm.getTargets(depOptions);
         }

         Set targetInfos = this.getTargetInfos(targetNames, smTargetNames, depOptions);

         Target targ;
         for(Iterator ti = targetInfos.iterator(); ti.hasNext(); tl.add(targ)) {
            ModuleTargetInfo mti = (ModuleTargetInfo)ti.next();
            targ = this.findTarget(mti, domainTargets);
            if (targ == null) {
               targ = this.findTarget(mti, additionalTargets);
               if (targ == null) {
                  String msg = subModuleTargets != null && !subModuleTargets.isEmpty() ? this.txtFmt.getCouldNotFindMatchingTargets(mti.getTarget(), subModuleTargets) : this.txtFmt.getCouldNotFindMatchingTargets(mti.getTarget());
                  this.ctx.throwWLSTException(msg);
               }
            }
         }

         targets = (Target[])((Target[])tl.toArray(new Target[0]));
         List list = this.prepareTmids(targetInfos, targets, dm, name);
         return this.getTmids(list);
      } else {
         return this.createTmidsFromApp(name, depOptions);
      }
   }

   private Target findTarget(ModuleTargetInfo mti, Target[] allt) {
      if (allt != null && allt.length != 0) {
         for(int i = 0; i < allt.length; ++i) {
            Target target = allt[i];
            if (target.getName().equals(mti.getTarget())) {
               return target;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private Set getTargetInfos(String[] modlist, String[] submodlist, DeploymentOptions opts) {
      Set targs = new HashSet();

      int i;
      for(i = 0; i < modlist.length; ++i) {
         if (this.isTargetedToRGOrRGT(opts) && !opts.getSpecifiedTargetsOnly()) {
            targs.add(new ModuleTargetInfo(modlist[i], this.getFixedTargetName(opts)));
         } else {
            targs.add(new ModuleTargetInfo(modlist[i]));
         }
      }

      if (submodlist != null) {
         for(i = 0; i < submodlist.length; ++i) {
            targs.add(new SubModuleTargetInfo(submodlist[i]));
         }
      }

      return targs;
   }

   private boolean isTargetedToRGOrRGT(DeploymentOptions opts) {
      return opts.getPartition() != null || opts.getResourceGroup() != null || opts.getResourceGroupTemplate() != null;
   }

   private String getFixedTargetName(DeploymentOptions opts) {
      if (opts.getResourceGroup() == null && opts.getPartition() == null) {
         return opts.getResourceGroupTemplate() != null ? "resourceGroupTemplate" : null;
      } else {
         return "resourceGroup";
      }
   }

   private List prepareTmids(Set targetInfos, Target[] targets, WebLogicDeploymentManager dm, String name) {
      List tmids = new ArrayList();
      if (name == null) {
         return tmids;
      } else {
         Iterator infos = targetInfos.iterator();

         while(infos.hasNext()) {
            ModuleTargetInfo mti = (ModuleTargetInfo)infos.next();
            tmids.add(mti.createTmid(name, this.findTarget(mti, targets), dm));
         }

         return tmids;
      }
   }

   private TargetModuleID[] getTmids(List tmids) {
      return (TargetModuleID[])((TargetModuleID[])tmids.toArray(new TargetModuleID[0]));
   }

   public Object distributeApplication(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("distributeApplication", args, kw, "appPath", "planPath", "targets");
      WLSTProgress progress = null;

      try {
         String appPath = ap.getString(0);
         File app = new File(appPath);
         String planPath = ap.getString(1);
         File plan = null;
         if (planPath != null) {
            plan = new File(planPath);
         }

         String targets = ap.getString(2);
         PyDictionary pyOptions = (PyDictionary)ap.getPyObject(3);
         if (targets == null) {
            targets = this.ctx.serverName;
         }

         String upload = pyOptions.get(DEPLOY_UPLOAD).toString();
         boolean doUpload = new Boolean(upload);
         WebLogicDeploymentManager dMgr = this.getWLDM(doUpload);
         DeploymentOptions depOptions = this.getDeploymentOptions();
         String subModuleTargets = this.getSubModuleTargets(pyOptions);
         if (pyOptions.items().__len__() > 0) {
            depOptions = this.getDeploymentOptions(pyOptions);
         } else {
            this.setDerivedPartitionOption(depOptions);
         }

         this.validateTargetsOption(targets, pyOptions);
         TargetModuleID[] actualTargets = this.getTMIDs(targets, dMgr, app.getName(), subModuleTargets, depOptions);
         this.ctx.println(this.txtFmt.getDistributingApplication(app.getAbsolutePath(), targets));
         ProgressObject po = dMgr.distribute(actualTargets, app, plan, depOptions);
         String block = pyOptions.get(BLOCK).toString();
         progress = new WLSTProgressImpl(po, this.ctx);
         if (block == null || block.equals("None")) {
            block = "true";
         }

         block = this.doBlock("distribution", block);
         if (block.toLowerCase(Locale.US).toString().equals("true")) {
            this.isDoneOrTimedOut(progress, pyOptions);
            if (progress.isFailed()) {
               this.ctx.println(this.txtFmt.getFailedToDistributeApp(progress.getState()));
               progress.printStatus();
               this.ctx.throwWLSTException(this.txtFmt.getFailedToDistributeApp(progress.getState()));
               return progress;
            }

            this.ctx.println(this.txtFmt.getAppDistributionComplete(progress.getState()));
            progress.printStatus();
            return progress;
         }

         this.ctx.println(this.txtFmt.getDistributionStarted());
      } catch (TargetException var19) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorDistributingApp(var19.getMessage()), var19);
      }

      return progress;
   }

   public Object appendToExtensionLoader(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("appendToExtensionLoader", args, kw, "path", "targets");
      WLSTProgress progress = null;

      try {
         String path = ap.getString(0);
         File codeSource = new File(path);
         String targets = ap.getString(1);
         PyDictionary pyOptions = (PyDictionary)ap.getPyObject(2);
         if (targets == null) {
            targets = this.ctx.serverName;
         }

         String upload = pyOptions.get(DEPLOY_UPLOAD).toString();
         boolean doUpload = new Boolean(upload);
         WebLogicDeploymentManager dMgr = this.getWLDM(doUpload);
         DeploymentOptions depOptions = this.getDeploymentOptions();
         String subModuleTargets = this.getSubModuleTargets(pyOptions);
         if (pyOptions.items().__len__() > 0) {
            depOptions = this.getDeploymentOptions(pyOptions);
         } else {
            this.setDerivedPartitionOption(depOptions);
         }

         this.validateTargetsOption(targets, pyOptions);
         TargetModuleID[] actualTargets = this.getTMIDs(targets, dMgr, codeSource.getName(), subModuleTargets, depOptions);
         this.ctx.println(this.txtFmt.getDistributingApplication(codeSource.getAbsolutePath(), targets));
         ProgressObject po = dMgr.appendToExtensionLoader(actualTargets, codeSource, depOptions);
         String block = pyOptions.get(BLOCK).toString();
         progress = new WLSTProgressImpl(po, this.ctx);
         if (block == null || block.equals("None")) {
            block = "true";
         }

         block = this.doBlock("distribution", block);
         if (block.toLowerCase(Locale.US).toString().equals("true")) {
            this.isDoneOrTimedOut(progress, pyOptions);
            if (progress.isFailed()) {
               this.ctx.println(this.txtFmt.getFailedToDistributeApp(progress.getState()));
               progress.printStatus();
               this.ctx.throwWLSTException(this.txtFmt.getFailedToDistributeApp(progress.getState()));
               return progress;
            }

            this.ctx.println(this.txtFmt.getAppDistributionComplete(progress.getState()));
            progress.printStatus();
            return progress;
         }

         this.ctx.println(this.txtFmt.getDistributionStarted());
      } catch (TargetException var17) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorDistributingApp(var17.getMessage()), var17);
      }

      return progress;
   }

   private String doBlock(String action, String block) {
      if (this.ctx.isEditSessionInProgress) {
         this.ctx.println(this.txtFmt.getEditSessionInProgress(action));
         return "false";
      } else {
         return block;
      }
   }

   public Object deploy(PyObject[] args, String[] kw) throws ScriptException {
      this.ctx.commandType = "deploy";
      boolean createdPlan = false;
      boolean doCreatePlan = false;
      boolean doUpload = false;
      String block = "false";
      File plan = null;

      try {
         ArgParser ap = new ArgParser("deploy", args, kw, "appName", "path", "targets");
         String appName = ap.getString(0);
         String appPath = ap.getString(1);
         File app = new File(appPath);
         String planPath = ap.getString(4);
         if (planPath != null) {
            plan = new File(planPath);
         }

         String targets = ap.getString(2);
         String stageMode = ap.getString(3);
         PyDictionary pyOptions = (PyDictionary)ap.getPyObject(5);
         ProgressObject po = null;
         String createPlan = pyOptions.get(CREATE_PLAN).toString();
         if (createPlan != null && !createPlan.equals("None")) {
            doCreatePlan = this.ctx.getBoolean(createPlan);
         } else {
            doCreatePlan = false;
         }

         String upload = pyOptions.get(DEPLOY_UPLOAD).toString();
         doUpload = new Boolean(upload);
         String remote = pyOptions.get(REMOTE).toString();
         if (!"true".equalsIgnoreCase(remote)) {
            if (!app.exists()) {
               throw new ScriptException(this.txtFmt.getApplicationPathNotFound(app.getAbsolutePath()), this.ctx.commandType);
            }

            if (app.isDirectory() && app.list().length == 0) {
               throw new ScriptException(this.txtFmt.getApplicationEmptyDirectory(app.getAbsolutePath()), this.ctx.commandType);
            }
         }

         WebLogicDeploymentManager dMgr = this.getWLDM(doUpload);
         DeploymentOptions depOptions = this.getDeploymentOptions();
         boolean isInstallDir = false;
         if (app.getParentFile() != null && app.getParentFile().getName().equals("app")) {
            isInstallDir = true;
         }

         try {
            if (doCreatePlan) {
               this.ctx.printDebug(this.txtFmt.getCreatePlanTrue());
               if (plan == null) {
                  planPath = this.getPlanPath(app.getAbsolutePath(), isInstallDir);
                  plan = new File(planPath);
               }

               this.helper.initializeConfiguration(app, (File)null);
               WebLogicDeploymentConfiguration wdc = this.helper.getConfiguration();
               WLSTPlan wlstPlan = new WLSTPlanImpl(wdc, this.ctx, plan.getAbsolutePath());
               wlstPlan.getDeploymentPlan().setApplicationName(appName);
               wlstPlan.save();
            }

            String subModuleTargets;
            if (isInstallDir && plan == null) {
               subModuleTargets = this.getExistingPlanPath(app.getAbsolutePath());
               if (subModuleTargets != null) {
                  plan = new File(subModuleTargets);
               }
            }

            subModuleTargets = this.getSubModuleTargets(pyOptions);
            if (pyOptions.items().__len__() > 0) {
               depOptions = this.getDeploymentOptions(pyOptions);
            } else {
               this.setDerivedPartitionOption(depOptions);
            }

            this.validateTargetsOption(targets, pyOptions);
            TargetModuleID[] tmids = this.getTMIDs(targets, dMgr, appName, subModuleTargets, depOptions);
            depOptions.setStageMode(stageMode);
            depOptions.setName(appName);
            this.ctx.println(this.txtFmt.getDeployingApplication(app.getAbsolutePath(), targets, doUpload));
            if (plan != null) {
               String msg = this.txtFmt.getDeployingApplicationWithPlan(app.getAbsolutePath(), plan.getAbsolutePath());
               this.ctx.printDebug(msg);
            }

            po = dMgr.deploy(tmids, app, plan, depOptions);
         } catch (TargetException var26) {
            this.ctx.throwWLSTException(this.txtFmt.getDeploymentFailed(), var26);
         } catch (Throwable var27) {
            if (this.ctx.debug) {
               var27.printStackTrace();
            }

            this.ctx.throwWLSTException(this.txtFmt.getUnexpectedError(var27.getMessage()), var27);
         }

         block = pyOptions.get(BLOCK).toString();
         if (block == null || block.equals("None")) {
            block = "true";
         }

         block = this.doBlock("deployment", block);
         WLSTProgress progress = new WLSTProgressImpl(po, this.ctx);
         if (block.toLowerCase(Locale.US).toString().equals("true")) {
            this.isDoneOrTimedOut(progress, pyOptions);
            if (progress.isFailed()) {
               this.ctx.println(this.txtFmt.getFailedToDeployApp(progress.getState()));
               progress.printStatus();
               this.ctx.throwWLSTException(this.txtFmt.getDeploymentFailed());
               return progress;
            } else {
               this.ctx.println(this.txtFmt.getAppDeploymentComplete(progress.getState()));
               progress.printStatus();
               return progress;
            }
         } else {
            this.ctx.println(this.txtFmt.getDeploymentStarted());
            return progress;
         }
      } catch (Throwable var28) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorDeployingApp(var28.getMessage()), var28);
         return null;
      }
   }

   private String getSubModuleTargets(PyDictionary pyOptions) {
      String tgts = pyOptions.get(SUB_MODULE_TARGETS).toString();
      return tgts != null && !tgts.equals("None") ? tgts : null;
   }

   private String getTargets(PyDictionary pyOptions) {
      String tgts = pyOptions.get(TARGETS).toString();
      return tgts != null && !tgts.equals("None") ? tgts : null;
   }

   private String[] getDeltas(PyDictionary pyOptions) {
      String deltaVal = pyOptions.get(DELTA).toString();
      if (deltaVal != null && !deltaVal.equals("None")) {
         String[] deltas = StringUtils.splitCompletely(deltaVal, ",");
         return deltas;
      } else {
         return null;
      }
   }

   private String getAppPath(String appname) {
      AppDeploymentMBean appMBean = this.ctx.edit.serviceMBean.getDomainConfiguration().lookupAppDeployment(appname);
      return appMBean == null ? null : appMBean.getAbsoluteSourcePath();
   }

   private void isDoneOrTimedOut(WLSTProgress progress, PyDictionary pyOptions) throws ScriptException {
      PyObject _timeout = pyOptions.get(TIME_OUT);
      int timeout = 300000;
      if (!_timeout.toString().equals("None") && _timeout != null) {
         timeout = (Integer)_timeout.__tojava__(Integer.class);
      }

      String msg = progress.getMessage();
      if (msg != null) {
         this.ctx.println(msg);
      }

      if (timeout == 0) {
         try {
            while(progress.isRunning()) {
               this.ctx.print(".");
               Thread.sleep(3000L);
            }
         } catch (InterruptedException var11) {
         }
      } else {
         Integer in = new Integer(timeout);
         long quitTime = System.currentTimeMillis() + in.longValue();

         do {
            try {
               if (progress.isRunning()) {
                  this.ctx.print(".");
                  Thread.sleep(3000L);
               }
            } catch (InterruptedException var10) {
            }

            if (quitTime <= System.currentTimeMillis()) {
               this.ctx.throwWLSTException(this.txtFmt.getActionTimedOut((long)timeout));
            }
         } while((timeout == 0 || quitTime > System.currentTimeMillis()) && progress.isRunning());
      }

   }

   public Object redeploy(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("redeploy", args, kw, "appName");
      String appName = ap.getString(0);
      String planPath = ap.getString(1);
      File plan = null;
      if (planPath != null) {
         plan = new File(planPath);
      }

      PyDictionary pyOptions = (PyDictionary)ap.getPyObject(2);
      String upload = pyOptions.get(DEPLOY_UPLOAD).toString();
      boolean doUpload = new Boolean(upload);
      doUpload = new Boolean(upload);
      WebLogicDeploymentManager dMgr = this.getWLDM(doUpload);
      DeploymentOptions depOptions = this.getDeploymentOptions();
      if (pyOptions.items().__len__() > 0) {
         depOptions = this.getDeploymentOptions(pyOptions);
      } else {
         this.setDerivedPartitionOption(depOptions);
      }

      this.validateTargetsOption(this.getTargets(pyOptions), pyOptions);
      this.ctx.println(this.txtFmt.getRedeployingApp(appName));
      String subModuleTargets = this.getSubModuleTargets(pyOptions);
      String[] deltas = this.getDeltas(pyOptions);
      TargetModuleID[] modIds = this.getTMIDs((String)null, dMgr, appName, subModuleTargets, depOptions);
      depOptions.setName(appName);
      String appPath = pyOptions.get(APP_PATH).toString();
      File app = null;
      if (appPath != null && !appPath.equals("None")) {
         app = new File(appPath);
      }

      ProgressObject po = null;
      if (deltas != null) {
         po = dMgr.redeploy(modIds, app, deltas, depOptions);
      } else {
         po = dMgr.redeploy(modIds, app, plan, depOptions);
      }

      String block = pyOptions.get(BLOCK).toString();
      WLSTProgress progress = new WLSTProgressImpl(po, this.ctx);
      if (block == null || block.equals("None")) {
         block = "true";
      }

      block = this.doBlock("redeployment", block);
      if (block.toLowerCase(Locale.US).toString().equals("true")) {
         this.isDoneOrTimedOut(progress, pyOptions);
         if (progress.isFailed()) {
            this.ctx.println(this.txtFmt.getFailedToRedeployApp(progress.getState()));
            progress.printStatus();
            this.ctx.throwWLSTException(this.txtFmt.getFailedToRedeployApp(progress.getState()));
            return progress;
         } else {
            this.ctx.println(this.txtFmt.getCompletedAppRedeploy(progress.getState()));
            progress.printStatus();
            return progress;
         }
      } else {
         this.ctx.println(this.txtFmt.getRedeploymentStarted());
         return progress;
      }
   }

   public Object undeploy(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("undeploy", args, kw, "targets");
      String appName = ap.getString(0);
      String targets = ap.getString(1);
      PyDictionary pyOptions = (PyDictionary)ap.getPyObject(2);
      WebLogicDeploymentManager dMgr = this.getWLDM();
      this.ctx.println(this.txtFmt.getUndeployingApp(appName));
      DeploymentOptions depOptions = this.getDeploymentOptions();
      if (pyOptions.items().__len__() > 0) {
         depOptions = this.getDeploymentOptions(pyOptions);
      } else {
         this.setDerivedPartitionOption(depOptions);
      }

      this.validateTargetsOption(targets, pyOptions);
      depOptions.setName(appName);
      String subModuleTargets = this.getSubModuleTargets(pyOptions);
      TargetModuleID[] modIds = this.getTMIDs(targets, dMgr, appName, subModuleTargets, depOptions);
      String[] deltas = this.getDeltas(pyOptions);
      ProgressObject po = null;
      if (deltas != null) {
         po = dMgr.undeploy(modIds, (File)null, deltas, depOptions);
      } else {
         po = dMgr.undeploy(modIds, depOptions);
      }

      WLSTProgress progress = new WLSTProgressImpl(po, this.ctx);
      String block = pyOptions.get(BLOCK).toString();
      if (block == null || block.equals("None")) {
         block = "true";
      }

      block = this.doBlock("undeployment", block);
      if (block.toLowerCase(Locale.US).toString().equals("true")) {
         this.isDoneOrTimedOut(progress, pyOptions);
         if (progress.isFailed()) {
            this.ctx.println(this.txtFmt.getFailedToUndeployApp(progress.getState()));
            progress.printStatus();
            this.ctx.throwWLSTException(this.txtFmt.getFailedToUndeployApp(progress.getState()));
            return progress;
         } else {
            this.ctx.println(this.txtFmt.getCompletedAppUndeploy(progress.getState()));
            progress.printStatus();
            return progress;
         }
      } else {
         this.ctx.println(this.txtFmt.getUndeploymentStarted());
         return progress;
      }
   }

   public Object startApplication(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("startApplication", args, kw, "appName");
      String appName = ap.getString(0);
      PyDictionary pyOptions = (PyDictionary)ap.getPyObject(1);
      WebLogicDeploymentManager dMgr = this.getWLDM();
      DeploymentOptions depOptions = this.getDeploymentOptions();
      if (pyOptions.items().__len__() > 0) {
         depOptions = this.getDeploymentOptions(pyOptions);
      } else {
         this.setDerivedPartitionOption(depOptions);
      }

      this.ctx.println(this.txtFmt.getStartingApplication(appName));
      String subModuleTargets = this.getSubModuleTargets(pyOptions);
      String targets = this.getTargets(pyOptions);
      this.validateTargetsOption(targets, pyOptions);
      TargetModuleID[] modIds = this.getTMIDs(targets, dMgr, appName, subModuleTargets, depOptions);
      ProgressObject po = dMgr.start(modIds, depOptions);
      String block = pyOptions.get(BLOCK).toString();
      WLSTProgress progress = new WLSTProgressImpl(po, this.ctx);
      if (block == null || block.equals("None")) {
         block = "true";
      }

      block = this.doBlock("startApplication", block);
      if (block.toLowerCase(Locale.US).toString().equals("true")) {
         this.isDoneOrTimedOut(progress, pyOptions);
         if (progress.isFailed()) {
            this.ctx.println(this.txtFmt.getFailedToStartApp(progress.getState()));
            progress.printStatus();
            this.ctx.throwWLSTException(this.txtFmt.getFailedToStartApp(progress.getState()));
            return progress;
         } else {
            this.ctx.println(this.txtFmt.getCompletedAppStart(progress.getState()));
            progress.printStatus();
            return progress;
         }
      } else {
         this.ctx.println(this.txtFmt.getApplicationStarted());
         return progress;
      }
   }

   public Object stopApplication(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("stopApplication", args, kw, "appName");
      String appName = ap.getString(0);
      PyDictionary pyOptions = (PyDictionary)ap.getPyObject(1);
      WebLogicDeploymentManager dMgr = this.getWLDM();
      DeploymentOptions depOptions = this.getDeploymentOptions();
      if (pyOptions.items().__len__() > 0) {
         depOptions = this.getDeploymentOptions(pyOptions);
      } else {
         this.setDerivedPartitionOption(depOptions);
      }

      this.ctx.println(this.txtFmt.getStoppingApplication(appName));
      String targets = this.getTargets(pyOptions);
      this.validateTargetsOption(targets, pyOptions);
      String subModuleTargets = this.getSubModuleTargets(pyOptions);
      TargetModuleID[] modIds = this.getTMIDs(targets, dMgr, appName, subModuleTargets, depOptions);
      ProgressObject po = dMgr.stop(modIds, depOptions);
      WLSTProgress progress = new WLSTProgressImpl(po, this.ctx);
      String block = pyOptions.get(BLOCK).toString();
      if (block == null || block.equals("None")) {
         block = "true";
      }

      block = this.doBlock("stopApplication", block);
      if (block.toLowerCase(Locale.US).toString().equals("true")) {
         this.isDoneOrTimedOut(progress, pyOptions);
         if (progress.isFailed()) {
            this.ctx.println(this.txtFmt.getFailedToStopApp(progress.getState()));
            progress.printStatus();
            this.ctx.throwWLSTException(this.txtFmt.getFailedToStopApp(progress.getState()));
            return progress;
         } else {
            this.ctx.println(this.txtFmt.getCompletedAppStop(progress.getState()));
            progress.printStatus();
            return progress;
         }
      } else {
         this.ctx.println(this.txtFmt.getStopStarted());
         return progress;
      }
   }

   public Object updateApplication(PyObject[] args, String[] kw) throws ScriptException {
      ArgParser ap = new ArgParser("updateApplication", args, kw, "appName", "planPath");
      String appName = ap.getString(0);
      String planPath = ap.getString(1);
      PyDictionary pyOptions = (PyDictionary)ap.getPyObject(2);
      String removePlanOverrideString = pyOptions.get(REMOVE_PLAN_OVERRIDE).toString();
      boolean removePlanOverride = new Boolean(removePlanOverrideString);
      File plan = null;
      if (!removePlanOverride) {
         if (planPath != null) {
            plan = new File(planPath);
         } else {
            this.ctx.throwWLSTException(this.txtFmt.getPlanPathNeededToUpdate());
         }
      }

      String upload = pyOptions.get(DEPLOY_UPLOAD).toString();
      boolean doUpload = new Boolean(upload);
      WebLogicDeploymentManager dMgr = this.getWLDM(doUpload);
      DeploymentOptions depOptions = this.getDeploymentOptions();
      if (pyOptions.items().__len__() > 0) {
         depOptions = this.getDeploymentOptions(pyOptions);
      } else {
         this.setDerivedPartitionOption(depOptions);
      }

      this.validateTargetsOption(this.getTargets(pyOptions), pyOptions);
      this.ctx.println(this.txtFmt.getUpdatingApp(appName));
      String subModuleTargets = this.getSubModuleTargets(pyOptions);
      TargetModuleID[] modIds = this.getTMIDs((String)null, dMgr, appName, subModuleTargets, depOptions);
      ProgressObject po = dMgr.update(modIds, plan, depOptions);
      String block = pyOptions.get(BLOCK).toString();
      WLSTProgress progress = new WLSTProgressImpl(po, this.ctx);
      if (block == null || block.equals("None")) {
         block = "true";
      }

      block = this.doBlock("updateApplication", block);
      if (block.toLowerCase(Locale.US).toString().equals("true")) {
         this.isDoneOrTimedOut(progress, pyOptions);
         if (progress.isFailed()) {
            this.ctx.println(this.txtFmt.getFailedToUpdateApp(progress.getState()));
            progress.printStatus();
            this.ctx.throwWLSTException(this.txtFmt.getFailedToUpdateApp(progress.getState()));
            return progress;
         } else {
            this.ctx.println(this.txtFmt.getCompletedAppUpdate(progress.getState()));
            progress.printStatus();
            return progress;
         }
      } else {
         this.ctx.println(this.txtFmt.getUpdateStarted());
         return progress;
      }
   }

   public void listApplications() throws ScriptException {
      try {
         WebLogicDeploymentManager dMgr = this.getWLDM();
         DeploymentOptions depOptions = this.getDeploymentOptions();
         this.setDerivedPartitionOption(depOptions);
         HashSet apps = new HashSet();
         TargetModuleID[] tmids = null;
         Target[] targets = dMgr.getTargets(depOptions);

         for(int i = 0; i < WebLogicModuleType.getModuleTypes(); ++i) {
            ModuleType mt = WebLogicModuleType.getModuleType(i);
            if (mt != null) {
               tmids = dMgr.getAvailableModules(mt, targets, depOptions);
               if (tmids != null) {
                  for(int j = 0; j < tmids.length; ++j) {
                     if (tmids[j].getParentTargetModuleID() == null) {
                        apps.add(tmids[j].getModuleID());
                     }
                  }
               }
            }
         }

         Iterator it = apps.iterator();

         while(it.hasNext()) {
            String tmid = (String)it.next();
            String str = " " + ApplicationVersionUtils.getDisplayName(tmid);
            this.ctx.println(str);
         }
      } catch (TargetException var9) {
         this.ctx.throwWLSTException(this.txtFmt.getDeploymentFailed(), var9);
      } catch (Throwable var10) {
         if (this.ctx.debug) {
            var10.printStackTrace();
         }

         this.ctx.throwWLSTException(this.txtFmt.getUnexpectedError(var10.getMessage()), var10);
      }

   }

   private boolean checkForUnrecognizedOptions(PyDictionary pyDic) {
      try {
         Set unrecognizedOptions = this.getUnrecognizedOptions(pyDic);
         if (unrecognizedOptions != null) {
            Iterator var3 = unrecognizedOptions.iterator();

            while(var3.hasNext()) {
               String unrecognized = (String)var3.next();
               this.ctx.println(this.txtFmt.unrecognizedOption(unrecognized));
            }

            return true;
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
      }

      return false;
   }

   private DeploymentOptions getDeploymentOptions() {
      DeploymentOptions depOptions = new DeploymentOptions();
      if (this.ctx.isEditSessionInProgress) {
         depOptions.setUseExpiredLock(true);
      }

      if (this.ctx.edit != null) {
         WLScriptContext var10001 = this.ctx;
         if (this.ctx.domainType.equals("ConfigEdit")) {
            depOptions.setEditSessionName(this.ctx.edit.name);
         }
      }

      return depOptions;
   }

   private void setDerivedPartitionOption(DeploymentOptions depOptions) {
      if (this.dm.getHelper() != null) {
         String partitionName = this.dm.getHelper().getPartitionNameFromCurrentConnection();
         if (partitionName != null) {
            depOptions.setPartition(partitionName);
         }
      }

   }

   private DeploymentOptions getDeploymentOptions(PyDictionary pyDic) {
      DeploymentOptions depOptions = this.getDeploymentOptions();
      String option = pyDic.get(CLUSTER_DEPLOYMENT_TIMEOUT).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setClusterDeploymentTimeout(Integer.parseInt(option));
      }

      option = pyDic.get(GRACEFUL_IGNORE_SESSIONS).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setGracefulIgnoreSessions(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setGracefulIgnoreSessions(false);
         }
      }

      option = pyDic.get(GRACEFUL_PRODUCTION_TO_ADMIN).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setGracefulProductionToAdmin(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setGracefulProductionToAdmin(false);
         }
      }

      option = pyDic.get(IS_LIBRARY_MODULE).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setLibrary(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setLibrary(false);
         }
      }

      option = pyDic.get(RETIRE_GRACEFULLY).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setRetireGracefully(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setRetireGracefully(false);
         }
      }

      option = pyDic.get(RETIRE_TIMEOUT).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setRetireTime(Integer.parseInt(option));
      }

      option = pyDic.get(RMI_GRACE_PERIOD).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setRMIGracePeriodSecs(Integer.parseInt(option));
      }

      option = pyDic.get(SECURITY_MODEL).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setSecurityModel(option);
      }

      option = pyDic.get(SECURITY_VALIDATION_ENABLED).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setSecurityValidationEnabled(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setSecurityValidationEnabled(false);
         }
      }

      option = pyDic.get(STAGE_MODE).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setStageMode(option);
      }

      option = pyDic.get(PLAN_STAGE_MODE).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setPlanStageMode(option);
      }

      option = pyDic.get(TEST_MODE).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setAdminMode(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setAdminMode(false);
         }
      }

      option = pyDic.get(ADMIN_MODE).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setAdminMode(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setAdminMode(false);
         }
      }

      option = pyDic.get(ARCHIVE_VERSION).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setArchiveVersion(option);
      }

      option = pyDic.get(PLAN_VERSION).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setPlanVersion(option);
      }

      option = pyDic.get(LIBRARY_SPEC_VERSION).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setLibSpecVersion(option);
      }

      option = pyDic.get(LIBRARY_IMPL_VERSION).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setLibImplVersion(option);
      }

      option = pyDic.get(ALT_DD).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setAltDD(option);
      }

      option = pyDic.get(ALT_WLS_DD).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setAltWlsDD(option);
      }

      option = pyDic.get(VERSION_IDENTIFIER).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setVersionIdentifier(option);
      }

      option = pyDic.get(FORCE_UNDEPLOYMENT_TIMEOUT).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setForceUndeployTimeout(Long.parseLong(option));
      }

      option = pyDic.get(DEFAULT_SUBMODULE_TARGETS).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setDefaultSubmoduleTargets(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setDefaultSubmoduleTargets(false);
         }
      }

      option = pyDic.get(DEPLOYMENT_PRINCIPAL_NAME).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setDeploymentPrincipalName(option);
      }

      option = pyDic.get(PARTITION).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setPartition(option);
      }

      option = pyDic.get(RESOURCE_GROUP).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setResourceGroup(option);
      }

      option = pyDic.get(RESOURCE_GROUP_TEMPLATE).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setResourceGroupTemplate(option);
      }

      option = pyDic.get(REMOTE).toString();
      if ("true".equalsIgnoreCase(option)) {
         depOptions.setRemote(true);
      }

      PyObject deploymentOrder = pyDic.get(DEPLOYMENT_ORDER);
      if (deploymentOrder instanceof PyInteger) {
         depOptions.setDeploymentOrder(((PyInteger)deploymentOrder).getValue());
      } else {
         option = pyDic.get(DEPLOYMENT_ORDER).toString();

         try {
            depOptions.setDeploymentOrder(Integer.parseInt(option));
         } catch (NumberFormatException var6) {
         }
      }

      option = pyDic.get(SPECIFIED_TARGETS_ONLY).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setSpecifiedTargetsOnly(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setSpecifiedTargetsOnly(false);
         }
      }

      option = pyDic.get(REMOVE_PLAN_OVERRIDE).toString();
      if (option != null && !option.equals("None")) {
         if (option.toLowerCase(Locale.US).equals("true")) {
            depOptions.setRemovePlanOverride(true);
         } else if (option.toLowerCase(Locale.US).equals("false")) {
            depOptions.setRemovePlanOverride(false);
         }
      }

      option = pyDic.get(SPECIFIED_MODULES).toString();
      if (option != null && !option.equals("None")) {
         depOptions.setSpecifiedModules(option);
      }

      this.checkForUnrecognizedOptions(pyDic);
      if (this.dm != null) {
         ApplicationUtils.validateAndSetPartitionParam(this.dm.getHelper(), depOptions);
      }

      return depOptions;
   }

   private void validateTargetsOption(String targets, PyDictionary pyDic) throws ScriptException {
      String resourceGroup = pyDic.get(RESOURCE_GROUP).toString();
      String resourceGroupTemplate = pyDic.get(RESOURCE_GROUP_TEMPLATE).toString();
      String partition = pyDic.get(PARTITION).toString();
      if (resourceGroup != null && !resourceGroup.equals("None") || resourceGroupTemplate != null && !resourceGroupTemplate.equals("None") || partition != null && !partition.equals("None")) {
         boolean ignore = false;
         String option = pyDic.get(SPECIFIED_TARGETS_ONLY).toString();
         if (option != null && !option.equals("None")) {
            ignore = option.toLowerCase(Locale.US).equals("true");
         }

         if (!ignore && targets != null && !targets.equals("None") && targets.length() > 0 && targets.indexOf(64) == -1) {
            throw new ScriptException(this.txtFmt.noTargetsAllowedForRGRGT(), this.ctx.commandType);
         }
      }

   }
}
