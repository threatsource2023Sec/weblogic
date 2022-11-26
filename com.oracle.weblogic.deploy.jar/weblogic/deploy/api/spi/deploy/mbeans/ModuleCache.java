package weblogic.deploy.api.spi.deploy.mbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTargetModuleID;
import weblogic.deploy.api.spi.deploy.TargetModuleIDImpl;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.utils.application.WarDetector;

public class ModuleCache extends MBeanCache {
   private static final long serialVersionUID = 1678014076382735164L;
   private static final boolean ddebug = Debug.isDebug("internal");
   transient List moduleTargetCache;
   transient List TMIDCache;
   transient Map tmidMap;
   transient Map beanTargetCache;
   private AppRuntimeStateRuntimeMBean artMBean = null;

   public ModuleCache(DomainMBean domain, WebLogicDeploymentManager dm) throws ServerConnectionException {
      super(dm);
      this.currDomain = domain;
      this.moduleTargetCache = null;
      this.TMIDCache = null;
      this.tmidMap = new HashMap();
      this.listenType = new String[]{"AppDeployments", "Libraries"};
      this.beanTargetCache = null;
      this.addNotificationListener();
   }

   public synchronized ConfigurationMBean[] getTypedMBeans() {
      return this.getTypedMBeans((DeploymentOptions)null);
   }

   public synchronized ConfigurationMBean[] getTypedMBeans(DeploymentOptions options) {
      List allBeans = new ArrayList();
      AppDeploymentMBean[] beans = null;
      Object beans;
      if (options == null) {
         beans = this.currDomain.getAppDeployments();
         if (beans != null) {
            this.add(beans, allBeans);
         }

         beans = this.currDomain.getLibraries();
      } else {
         beans = AppDeploymentHelper.getAppsAndLibsForGivenScope(this.currDomain, options.getResourceGroupTemplate(), options.getResourceGroup(), options.getPartition(), false);
      }

      if (beans != null) {
         this.add((AppDeploymentMBean[])beans, allBeans);
      }

      return (ConfigurationMBean[])allBeans.toArray(new ConfigurationMBean[allBeans.size()]);
   }

   private void add(AppDeploymentMBean[] beans, List l) {
      for(int i = 0; i < beans.length; ++i) {
         AppDeploymentMBean bean = beans[i];
         l.add(bean);
      }

   }

   public synchronized List getModules(Target target) throws ServerConnectionException {
      return this.getModules(target, (DeploymentOptions)null);
   }

   public synchronized List getModules(Target target, DeploymentOptions options) throws ServerConnectionException {
      return this.getModules(target, (String)null, options);
   }

   public synchronized List getModules(Target target, String appName, DeploymentOptions options) throws ServerConnectionException {
      this.reset();
      if (!this.moduleTargetCache.contains(target)) {
         if (ddebug) {
            Debug.say("Populating tmid cache for target " + target);
         }

         this.moduleTargetCache.add(target);
         Iterator var8 = this.getMBeans(options).iterator();

         label122:
         while(true) {
            boolean isTargeted;
            WebLogicTargetModuleID tmid;
            AppDeploymentMBean mbean;
            TargetInfoMBean[] modMBeans;
            do {
               do {
                  do {
                     if (!var8.hasNext()) {
                        this.addUnconfiguredModules(target);
                        break label122;
                     }

                     ConfigurationMBean cbean = (ConfigurationMBean)var8.next();
                     mbean = (AppDeploymentMBean)cbean;
                  } while(mbean == null);
               } while(appName != null && !appName.equals(mbean.getName()));

               if (ddebug) {
                  Debug.say("checking for tmid for " + mbean.getName());
               }

               isTargeted = this.targeted(mbean, target);
               tmid = null;
               if (isTargeted) {
                  tmid = this.getTMID(mbean, target, (TargetInfoMBean)null);
                  if (tmid != null) {
                     if (this.TMIDCache.add(tmid)) {
                        if (ddebug) {
                           Debug.say("Added " + tmid.toString() + " to cache");
                        }
                     } else if (ddebug) {
                        Debug.say(tmid.toString() + " already in cache");
                     }
                  }
               }

               modMBeans = this.getChildModules(mbean);
            } while(modMBeans == null);

            for(int i = 0; i < modMBeans.length; ++i) {
               TargetInfoMBean modMBean = modMBeans[i];
               if (modMBean != null) {
                  isTargeted = this.targeted(modMBean, target);
                  WebLogicTargetModuleID mtmid = null;
                  if (isTargeted) {
                     if (tmid == null) {
                        tmid = this.addImpliedParent(mbean, target, (TargetInfoMBean)null);
                     }

                     mtmid = this.getTMID(modMBean, target, mbean);
                     if (mtmid != null) {
                        this.TMIDCache.add(mtmid);
                     }
                  }

                  TargetInfoMBean[] subMBeans = this.getChildModules(modMBean);
                  if (subMBeans != null) {
                     for(int j = 0; j < subMBeans.length; ++j) {
                        TargetInfoMBean subModMBean = subMBeans[j];
                        if (subModMBean != null) {
                           isTargeted = this.targeted(subModMBean, target);
                           if (isTargeted) {
                              if (mtmid == null) {
                                 if (tmid == null) {
                                    tmid = this.addImpliedParent(mbean, target, (TargetInfoMBean)null);
                                 }

                                 mtmid = this.addImpliedParent(modMBean, target, mbean);
                              }

                              WebLogicTargetModuleID smtmid = this.getTMID(subModMBean, target, modMBean);
                              if (smtmid != null) {
                                 this.TMIDCache.add(smtmid);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (ddebug) {
         this.dump();
      }

      List modSet = new ArrayList();
      Iterator var18 = this.TMIDCache.iterator();

      while(var18.hasNext()) {
         TargetModuleID mod = (TargetModuleID)var18.next();
         if (mod.getTarget().getName().equals(target.getName())) {
            modSet.add(mod);
         }
      }

      return modSet;
   }

   private AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntimeMBean() {
      if (this.artMBean == null) {
         try {
            this.artMBean = this.dm.getHelper().getAppRuntimeStateMBean();
         } catch (Exception var2) {
            if (ddebug) {
               Debug.say("Can't get app runtime state: " + var2.toString());
            }
         }
      }

      return this.artMBean;
   }

   private void addUnconfiguredModules(Target target) {
      if (this.getAppRuntimeStateRuntimeMBean() != null) {
         List roots = new ArrayList();
         Iterator var3 = this.TMIDCache.iterator();

         while(var3.hasNext()) {
            TargetModuleID root = (TargetModuleID)var3.next();
            if (root.getParentTargetModuleID() == null && root.getTarget().getName().equals(target.getName())) {
               roots.add(root);
            }
         }

         for(int i = 0; i < roots.size(); ++i) {
            WebLogicTargetModuleID id = (WebLogicTargetModuleID)roots.get(i);
            if (id.getValue() == ModuleType.EAR.getValue()) {
               String[] modNames = this.getAppRuntimeStateRuntimeMBean().getModuleIds(id.getModuleID());
               if (modNames == null) {
                  continue;
               }

               for(int j = 0; j < modNames.length; ++j) {
                  String name = modNames[j];
                  this.addMissingModule(name, id);
               }
            }

            this.addUnconfiguredSubmodules(id, id.getModuleID());
         }

      }
   }

   private void addMissingModule(String name, WebLogicTargetModuleID id) {
      if (!this.isChild(name, id.getChildTargetModuleID())) {
         this.addImpliedChild(id, name, WebLogicModuleType.getTypeFromString(this.getAppRuntimeStateRuntimeMBean().getModuleType(id.getModuleID(), name)));
      }

   }

   private void addUnconfiguredSubmodules(WebLogicTargetModuleID id, String app) {
      int j;
      if (id.getValue() == WebLogicModuleType.JMS.getValue()) {
         String[] modNames = this.getAppRuntimeStateRuntimeMBean().getSubmoduleIds(app, id.getModuleID());
         if (modNames == null) {
            return;
         }

         for(j = 0; j < modNames.length; ++j) {
            String name = modNames[j];
            this.addMissingModule(name, id);
         }
      } else {
         TargetModuleID[] cid = id.getChildTargetModuleID();
         if (cid == null) {
            return;
         }

         for(j = 0; j < cid.length; ++j) {
            WebLogicTargetModuleID moduleID = (WebLogicTargetModuleID)cid[j];
            this.addUnconfiguredSubmodules(moduleID, app);
         }
      }

   }

   private void addImpliedChild(TargetModuleID id, String name, ModuleType type) {
      TargetModuleIDImpl t = new TargetModuleIDImpl(name, id.getTarget(), id, type, this.getDM());
      t.setTargeted(false);
      if (type == ModuleType.WAR) {
         t.setWebURL(name);
      }

      this.TMIDCache.add(t);
   }

   private boolean isChild(String name, TargetModuleID[] id) {
      if (id == null) {
         return false;
      } else {
         for(int i = 0; i < id.length; ++i) {
            TargetModuleID moduleID = id[i];
            if (name.equals(moduleID.getModuleID())) {
               return true;
            }
         }

         return false;
      }
   }

   private WebLogicTargetModuleID addImpliedParent(TargetInfoMBean ti, Target target, TargetInfoMBean parent) {
      WebLogicTargetModuleID tmid = this.getTMID(ti, target, parent);
      if (tmid != null) {
         tmid.setTargeted(false);
         this.TMIDCache.add(tmid);
      }

      return tmid;
   }

   public TargetInfoMBean[] getChildModules(TargetInfoMBean mbean) {
      TargetInfoMBean[] tims = null;
      if (mbean instanceof AppDeploymentMBean) {
         tims = ((AppDeploymentMBean)mbean).getSubDeployments();
      } else if (mbean instanceof SubDeploymentMBean) {
         tims = ((SubDeploymentMBean)mbean).getSubDeployments();
      }

      if (tims == null) {
         tims = new TargetInfoMBean[0];
      }

      return (TargetInfoMBean[])tims;
   }

   public TargetInfoMBean getTargetInfoMBean(TargetModuleIDImpl tmid) {
      this.getModules(tmid.getTarget());
      return (TargetInfoMBean)this.tmidMap.get(tmid);
   }

   public boolean targeted(TargetInfoMBean mbean, Target target) {
      boolean isTargeted = false;
      String[] targets;
      if (!this.beanTargetCache.containsKey(mbean)) {
         targets = new String[0];
         TargetMBean[] targs = ApplicationUtils.getActualTargets(mbean);
         if (targs != null) {
            targets = new String[targs.length];

            for(int i = 0; i < targs.length; ++i) {
               if (targs[i] == null) {
                  targets[i] = null;
               } else {
                  targets[i] = targs[i].getObjectName().getName();
               }
            }
         }

         this.beanTargetCache.put(mbean, targets);
      }

      targets = (String[])this.beanTargetCache.get(mbean);
      if (targets != null) {
         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] != null && targets[i].equals(target.getName())) {
               isTargeted = true;
               break;
            }
         }
      }

      if (ddebug && !isTargeted) {
         Debug.say(mbean.getName() + " is not targeted to " + target.getName());
      }

      return isTargeted;
   }

   public TargetModuleID getTMID(AppDeploymentMBean app, Target target) {
      if (this.targeted(app, target)) {
         Iterator var3 = this.getModules(target).iterator();

         TargetModuleID o;
         do {
            if (!var3.hasNext()) {
               return this.getTMID(app, target, (TargetInfoMBean)null);
            }

            o = (TargetModuleID)var3.next();
         } while(!o.getModuleID().equals(app.getName()));

         return o;
      } else {
         return null;
      }
   }

   public TargetModuleID getResultTmids(String appName, Target target) {
      return this.getResultTmids(appName, target, (DeploymentOptions)null);
   }

   public TargetModuleID getResultTmids(String appName, Target target, DeploymentOptions options) {
      Iterator var4 = this.getModules(target, appName, options).iterator();

      TargetModuleID o;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         o = (TargetModuleID)var4.next();
      } while(!o.getModuleID().equals(appName));

      return o;
   }

   private WebLogicTargetModuleID getTMID(TargetInfoMBean app, Target target, TargetInfoMBean parent) {
      if (ddebug) {
         Debug.say("Getting TMID for " + app.getName() + ", target=(" + target.toString() + ") " + target.getName());
      }

      ModuleType mt = this.getModuleType(app);
      return mt == null ? null : this.getNewTMID(app, mt, target, parent);
   }

   private WebLogicTargetModuleID getNewTMID(TargetInfoMBean mbean, ModuleType type, Target target, TargetInfoMBean parent) {
      if (ddebug) {
         Debug.say("Creating TMID for " + mbean.getName() + ": type=" + type.toString() + ": value=" + type.getValue() + ", target=(" + target.toString() + ") " + target.getName());
      }

      TargetModuleID ptmid = this.getParentTmid(target, parent);
      TargetModuleIDImpl t = new TargetModuleIDImpl(mbean.getName(), target, ptmid, type, this.getDM());
      this.tmidMap.put(t, mbean);
      return t;
   }

   private TargetModuleID getParentTmid(Target target, TargetInfoMBean tib) {
      if (tib == null) {
         return null;
      } else {
         Iterator var3 = this.TMIDCache.iterator();

         TargetModuleIDImpl mod;
         do {
            if (!var3.hasNext()) {
               return this.getTMID(tib, target, tib.getParent() instanceof TargetInfoMBean ? (TargetInfoMBean)tib.getParent() : null);
            }

            TargetModuleID rawMod = (TargetModuleID)var3.next();
            mod = (TargetModuleIDImpl)rawMod;
         } while(!mod.getTarget().getName().equals(target.getName()) || !tib.getName().equals(mod.getModuleID()) || !this.compareParents(mod, tib));

         return mod;
      }
   }

   private boolean compareParents(TargetModuleID mod, WebLogicMBean tib) {
      TargetModuleID ptmid = mod.getParentTargetModuleID();
      WebLogicMBean ptib = tib.getParent();
      String n1 = ptmid == null ? "" : ptmid.getModuleID();
      String n2 = ptib instanceof TargetInfoMBean ? ptib.getName() : "";
      if (n1.equals(n2)) {
         return ptmid != null ? this.compareParents(ptmid, ptib) : true;
      } else {
         return false;
      }
   }

   private ModuleType getModuleType(TargetInfoMBean mbean) {
      if (mbean == null) {
         return null;
      } else {
         String mt = mbean.getModuleType();
         if (ddebug) {
            Debug.say("Checking type for " + mbean.getName() + ", with type attr = " + mt);
         }

         if (mt != null) {
            return this.getTypeFromString(mt);
         } else {
            if (mbean instanceof AppDeploymentMBean) {
               String path = ((AppDeploymentMBean)mbean).getSourcePath();
               if (path != null) {
                  if (path.endsWith(".ear")) {
                     return ModuleType.EAR;
                  }

                  if (WarDetector.instance.suffixed(path)) {
                     return ModuleType.WAR;
                  }

                  if (path.endsWith(".jar")) {
                     return ModuleType.EJB;
                  }

                  if (path.endsWith(".rar")) {
                     return ModuleType.RAR;
                  }

                  if (path.endsWith("-jms.xml")) {
                     return WebLogicModuleType.JMS;
                  }

                  if (path.endsWith("-jdbc.xml")) {
                     return WebLogicModuleType.JDBC;
                  }

                  if (path.endsWith("-interception.xml")) {
                     return WebLogicModuleType.INTERCEPT;
                  }
               }
            }

            if (mbean instanceof SubDeploymentMBean && mbean.getParent() instanceof SubDeploymentMBean) {
               return WebLogicModuleType.SUBMODULE;
            } else {
               return (ModuleType)(mbean instanceof SubDeploymentMBean && ((AppDeploymentMBean)mbean.getParent()).getSourcePath().endsWith("-jms.xml") ? WebLogicModuleType.SUBMODULE : this.getModuleTypeFromState(mbean));
            }
         }
      }
   }

   private ModuleType getModuleTypeFromState(TargetInfoMBean mbean) {
      try {
         AppRuntimeStateRuntimeMBean artMbean = this.dm.getHelper().getAppRuntimeStateMBean();
         String appid;
         if (mbean instanceof AppDeploymentMBean) {
            SubDeploymentMBean[] subs = ((AppDeploymentMBean)mbean).getSubDeployments();
            if (subs != null && subs.length > 0) {
               return ModuleType.EAR;
            } else {
               appid = mbean.getName();
               return this.getTypeFromString(artMbean.getModuleType(appid, appid));
            }
         } else {
            appid = mbean.getParent().getName();
            return this.getTypeFromString(artMbean.getModuleType(appid, mbean.getName()));
         }
      } catch (Exception var5) {
         return WebLogicModuleType.UNKNOWN;
      }
   }

   private ModuleType getTypeFromString(String mt) {
      if (ModuleType.EAR.toString().equals(mt)) {
         return ModuleType.EAR;
      } else if (ModuleType.WAR.toString().equals(mt)) {
         return ModuleType.WAR;
      } else if (ModuleType.EJB.toString().equals(mt)) {
         return ModuleType.EJB;
      } else if (ModuleType.RAR.toString().equals(mt)) {
         return ModuleType.RAR;
      } else if (ModuleType.CAR.toString().equals(mt)) {
         return ModuleType.CAR;
      } else if (WebLogicModuleType.JMS.toString().equals(mt)) {
         return WebLogicModuleType.JMS;
      } else if (WebLogicModuleType.JDBC.toString().equals(mt)) {
         return WebLogicModuleType.JDBC;
      } else if (WebLogicModuleType.INTERCEPT.toString().equals(mt)) {
         return WebLogicModuleType.INTERCEPT;
      } else {
         return WebLogicModuleType.SUBMODULE.toString().equals(mt) ? WebLogicModuleType.SUBMODULE : WebLogicModuleType.UNKNOWN;
      }
   }

   public synchronized void reset() {
      super.reset();
      this.moduleTargetCache = new ArrayList();
      this.TMIDCache = new ArrayList();
      this.tmidMap = new HashMap();
      this.beanTargetCache = new HashMap();
   }

   private void dump() {
      Debug.say("Current cache of TMIDs:");
      Iterator var1 = this.TMIDCache.iterator();

      while(var1.hasNext()) {
         TargetModuleID mod = (TargetModuleID)var1.next();
         Debug.say("  " + mod);
      }

   }
}
