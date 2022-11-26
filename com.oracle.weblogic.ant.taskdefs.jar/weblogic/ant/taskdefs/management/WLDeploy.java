package weblogic.ant.taskdefs.management;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.FileScanner;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.FileSet;

public class WLDeploy extends Java {
   private static final String DEPLOYER_WRAPPER_CLASS = "weblogic.ant.taskdefs.management.DeployerWrapper";
   private String adminurl;
   private boolean debug;
   private boolean external_stage;
   private boolean stage;
   private String id;
   private String name;
   private boolean nostage;
   private boolean nowait;
   private String password;
   private boolean remote;
   private File source;
   private String targets;
   private String specifiedModules;
   private int timeout = -1;
   private String user;
   private boolean verbose;
   private String action;
   private FileSet files;
   private String deltaFiles;
   private boolean upload;
   private String altappdd;
   private String altwlsappdd;
   private String userConfigFile;
   private String userKeyFile;
   private boolean failonerror = true;
   private boolean deleteFiles = false;
   private boolean isLibrary = false;
   private String libSpecVer;
   private String libImplVer;
   private boolean adminmode;
   private boolean graceful;
   private boolean ignoresessions;
   private String appversion;
   private String planversion;
   private int retiretimeout = -1;
   private String submoduletargets;
   private String securityModel;
   private String plan;
   private String output;
   private boolean usenonexclusivelock = false;
   private boolean noversion = false;
   private boolean allversions = false;
   private boolean enableSecurityValidation = false;
   private boolean planStage;
   private boolean planNostage;
   private boolean planExternal_stage;
   private boolean specifiedTargetsOnly = false;
   private String resourceGroup;
   private String partition;
   private String resourceGroupTemplate;
   private boolean removePlanOverride = false;
   private String editSession;
   private String idd;
   private boolean execFailed = false;

   public void setSpecifiedTargetsOnly(boolean val) {
      this.specifiedTargetsOnly = val;
   }

   public void setResourceGroup(String resourceGroup) {
      this.resourceGroup = resourceGroup;
   }

   public void setResourceGroupTemplate(String resourceGroupTemplate) {
      this.resourceGroupTemplate = resourceGroupTemplate;
   }

   public void setPartition(String partition) {
      this.partition = partition;
   }

   public void setEditSession(String editSession) {
      this.editSession = editSession;
   }

   public void setRemovePlanOverride(boolean removePlanOverride) {
      this.removePlanOverride = removePlanOverride;
   }

   public void setEnableSecurityValidation(boolean enableSecurityValidation) {
      this.enableSecurityValidation = enableSecurityValidation;
   }

   public void setAllversions(boolean allversions) {
      this.allversions = allversions;
   }

   public void setDeleteFiles(boolean deleteFiles) {
      this.deleteFiles = deleteFiles;
   }

   public void setOutput(String output) {
      this.output = output;
   }

   public void setNoversion(boolean noversion) {
      this.noversion = noversion;
   }

   public void setUsenonexclusivelock(boolean usenonexclusivelock) {
      this.usenonexclusivelock = usenonexclusivelock;
   }

   public void setAdminmode(boolean adminmode) {
      this.adminmode = adminmode;
   }

   public void setGraceful(boolean graceful) {
      this.graceful = graceful;
   }

   public void setIgnoresessions(boolean ignoresessions) {
      this.ignoresessions = ignoresessions;
   }

   public void setAppversion(String appversion) {
      this.appversion = appversion;
   }

   public void setPlanversion(String planversion) {
      this.planversion = planversion;
   }

   public void setRetiretimeout(int retiretimeout) {
      this.retiretimeout = retiretimeout;
   }

   public void setSubmoduletargets(String submoduletargets) {
      this.submoduletargets = submoduletargets;
   }

   public void setSecurityModel(String securityModel) {
      this.securityModel = securityModel;
   }

   public void setPlan(String plan) {
      this.plan = plan;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public void setAdminUrl(String adminurl) {
      this.adminurl = adminurl;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public void setExternalStage(boolean external_stage) {
      this.external_stage = external_stage;
   }

   public void setStage(boolean stage) {
      this.stage = stage;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setNoStage(boolean nostage) {
      this.nostage = nostage;
   }

   public void setNoWait(boolean nowait) {
      this.nowait = nowait;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setRemote(boolean remote) {
      this.remote = remote;
   }

   public void setSource(File source) {
      this.source = source;
   }

   public void setTargets(String targets) {
      this.targets = targets;
   }

   public void setSpecifiedModules(String specifiedModules) {
      this.specifiedModules = specifiedModules;
   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void setUserConfigFile(String file) {
      this.userConfigFile = file;
   }

   public void setUserKeyFile(String file) {
      this.userKeyFile = file;
   }

   public void addFiles(FileSet files) {
      this.files = files;
   }

   public void setDeltaFiles(String deltaFiles) {
      this.deltaFiles = deltaFiles;
   }

   public void setUpload(boolean upload) {
      this.upload = upload;
   }

   public void setAltappdd(String altappdd) {
      this.altappdd = altappdd;
   }

   public void setAltwlsappdd(String altwlsappdd) {
      this.altwlsappdd = altwlsappdd;
   }

   public void setFailonerror(boolean failonerror) {
      this.failonerror = failonerror;
   }

   public void setLibrary(boolean isLibrary) {
      this.isLibrary = isLibrary;
   }

   public void setLibSpecVer(String libSpecVer) {
      this.libSpecVer = libSpecVer;
   }

   public void setLibImplVer(String libImplVer) {
      this.libImplVer = libImplVer;
   }

   public void setPlanStage(boolean stage) {
      this.planStage = stage;
   }

   public void setPlanNoStage(boolean nostage) {
      this.planNostage = nostage;
   }

   public void setPlanExternalStage(boolean external_stage) {
      this.planExternal_stage = external_stage;
   }

   public void setIdd(String idd) {
      this.idd = idd;
   }

   public void execute() throws BuildException {
      try {
         List flags = new ArrayList();
         if (this.debug) {
            flags.add("-debug");
         }

         if (this.external_stage) {
            flags.add("-external_stage");
         }

         if (this.stage) {
            flags.add("-stage");
         }

         if (this.nostage) {
            flags.add("-nostage");
         }

         if (this.nowait) {
            flags.add("-nowait");
         }

         if (this.remote) {
            flags.add("-remote");
         }

         if (this.verbose) {
            flags.add("-verbose");
         }

         if (this.upload) {
            flags.add("-upload");
         }

         if (this.noversion) {
            flags.add("-noversion");
         }

         if (this.usenonexclusivelock) {
            flags.add("-usenonexclusivelock");
         }

         if (this.deleteFiles) {
            flags.add("-delete_files");
         }

         if (this.allversions) {
            flags.add("-allversions");
         }

         if (this.enableSecurityValidation) {
            flags.add("-enableSecurityValidation");
         }

         if (this.planExternal_stage) {
            flags.add("-planexternal_stage");
         }

         if (this.planStage) {
            flags.add("-planstage");
         }

         if (this.planNostage) {
            flags.add("-plannostage");
         }

         if (this.specifiedTargetsOnly) {
            flags.add("-specifiedtargetsonly");
         }

         if (this.removePlanOverride) {
            flags.add("-removePlanOverride");
         }

         flags.add("-noexit");
         if (this.name != null) {
            flags.add("-name");
            flags.add(this.name);
         }

         if (this.source != null) {
            flags.add("-source");
            flags.add(this.source.toString());
         }

         if (this.targets != null) {
            flags.add("-targets");
            flags.add(this.targets);
         }

         if (this.specifiedModules != null) {
            flags.add("-specifiedModules");
            flags.add(this.specifiedModules);
         }

         if (this.editSession != null) {
            flags.add("-editsession");
            flags.add(this.editSession);
         }

         if (this.partition != null) {
            flags.add("-partition");
            flags.add(this.partition);
         }

         if (this.resourceGroup != null) {
            flags.add("-resourceGroup");
            flags.add(this.resourceGroup);
         }

         if (this.resourceGroupTemplate != null) {
            flags.add("-resourceGroupTemplate");
            flags.add(this.resourceGroupTemplate);
         }

         if (this.adminurl != null) {
            flags.add("-adminurl");
            flags.add(this.adminurl);
         }

         if (this.user != null) {
            flags.add("-user");
            flags.add(this.user);
            if (this.password != null) {
               flags.add("-password");
               flags.add(this.password);
            }
         } else {
            if (this.userConfigFile != null) {
               flags.add("-userconfigfile");
               flags.add(this.userConfigFile);
            }

            if (this.userKeyFile != null) {
               flags.add("-userkeyfile");
               flags.add(this.userKeyFile);
            }
         }

         if (this.id != null) {
            flags.add("-id");
            flags.add(this.id);
         }

         if (this.timeout != -1) {
            flags.add("-timeout");
            flags.add(this.timeout + "");
         }

         if (this.action != null) {
            flags.add("-" + this.action);
         }

         if (this.altappdd != null) {
            flags.add("-altappdd");
            flags.add(this.altappdd);
         }

         if (this.altwlsappdd != null) {
            flags.add("-altwlsappdd");
            flags.add(this.altwlsappdd);
         }

         if (this.isLibrary) {
            flags.add("-library");
         }

         if (this.libSpecVer != null) {
            flags.add("-libspecver");
            flags.add(this.libSpecVer);
         }

         if (this.libImplVer != null) {
            flags.add("-libimplver");
            flags.add(this.libImplVer);
         }

         if (this.appversion != null) {
            flags.add("-appversion");
            flags.add(this.appversion);
         }

         if (this.planversion != null) {
            flags.add("-planversion");
            flags.add(this.planversion);
         }

         if (this.submoduletargets != null) {
            flags.add("-submoduletargets");
            flags.add(this.submoduletargets);
         }

         if (this.securityModel != null) {
            flags.add("-securityModel");
            flags.add(this.securityModel);
         }

         if (this.plan != null) {
            flags.add("-plan");
            flags.add(this.plan);
         }

         if (this.output != null) {
            flags.add("-output");
            flags.add(this.output);
         }

         if (this.adminmode) {
            flags.add("-adminmode");
         }

         if (this.graceful) {
            flags.add("-graceful");
         }

         if (this.ignoresessions) {
            flags.add("-ignoresessions");
         }

         if (this.retiretimeout != -1) {
            flags.add("-retiretimeout");
            flags.add(this.retiretimeout + "");
         }

         if (this.idd != null) {
            flags.add("-idd");
            flags.add(this.idd);
         }

         String[] args = this.getArgs(flags);
         System.out.print("weblogic.Deployer ");

         for(int i = 0; i < args.length; ++i) {
            System.out.print(args[i]);
            if ("-password".equals(args[i])) {
               System.out.print(" ******** ");
               ++i;
            } else {
               System.out.print(" ");
            }
         }

         System.out.println("");
         this.executeDeployer(args);
         if (this.execFailed) {
            throw new BuildException("webLogic.Deployer execution failed");
         }
      } catch (Exception var4) {
         Throwable t = var4.getCause();
         if (this.failonerror) {
            if (t == null) {
               throw new BuildException(var4);
            }

            throw new BuildException(t);
         }

         if (this.verbose) {
            if (t == null) {
               System.out.println(var4.getMessage());
            } else {
               System.out.println(t.getMessage());
            }
         }
      }

   }

   protected String[] getArgs(List flags) {
      List args = new ArrayList(flags);
      boolean isRedeployAction = "redeploy".equals(this.action);
      if (this.deltaFiles != null && isRedeployAction) {
         List delta = this.splitDeltaFiles(this.deltaFiles);
         args.addAll(delta);
         return (String[])((String[])args.toArray(new String[args.size()]));
      } else {
         if (this.files != null) {
            if (isRedeployAction) {
               this.log("Option 'files' is deprecated for redeploy operation. Please use option 'deltaFiles'.");
            }

            FileScanner scanner = this.files.getDirectoryScanner(this.project);
            String[] argFiles = scanner.getIncludedFiles();
            File basedir = scanner.getBasedir();

            for(int i = 0; i < argFiles.length; ++i) {
               File f = new File(basedir, argFiles[i]);
               args.add(f.getPath());
            }
         }

         return (String[])((String[])args.toArray(new String[args.size()]));
      }
   }

   private void executeDeployer(String[] args) throws Exception {
      ClassLoader cl = this.getClass().getClassLoader();
      Thread.currentThread().setContextClassLoader(cl);
      if (cl instanceof URLClassLoader) {
         URLClassLoader ucl = (URLClassLoader)cl;
         URL[] urls = ucl.getURLs();
         URL[] var5 = urls;
         int var6 = urls.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            URL url = var5[var7];
            this.createClasspath().setPath(url.toURI().getPath());
         }
      } else if (cl instanceof AntClassLoader) {
         AntClassLoader acl = (AntClassLoader)cl;
         this.createClasspath().setPath(acl.getClasspath());
      }

      File classesdir = new File(this.getProject().getProperty("dest") + "/classes");
      this.createClasspath().setLocation(classesdir);
      this.setFork(true);
      this.setClassname("weblogic.ant.taskdefs.management.DeployerWrapper");

      for(int i = 0; i < args.length; ++i) {
         this.createArg().setValue(args[i]);
      }

      if (this.executeJava() != 0) {
         this.execFailed = true;
      }

   }

   private List splitDeltaFiles(String givenDelta) {
      List allSplits = new ArrayList();
      StringTokenizer st = new StringTokenizer(givenDelta, " ,");

      while(st.hasMoreTokens()) {
         allSplits.add(st.nextToken());
      }

      return allSplits;
   }
}
