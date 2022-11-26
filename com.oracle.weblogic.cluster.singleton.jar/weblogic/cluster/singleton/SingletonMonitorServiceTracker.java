package weblogic.cluster.singleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationDescriptor;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.singleton.SingletonServicesManager.Util;
import weblogic.deploy.event.DeploymentEvent;
import weblogic.deploy.event.DeploymentEventListener;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.j2ee.descriptor.wl.SingletonServiceBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.utils.LocatorUtilities;

public class SingletonMonitorServiceTracker implements ClusterMembersChangeListener, DeploymentEventListener, BeanUpdateListener {
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private ClusterMBean cluster;
   private DomainMBean domain;
   private HashMap allSingletons;
   private LeaseManager manager;
   private ArrayList allServers;

   SingletonMonitorServiceTracker(LeaseManager manager) {
      this.manager = manager;
   }

   synchronized void initialize(ClusterMBean cluster, DomainMBean domain) {
      this.cluster = cluster;
      cluster.addBeanUpdateListener(new ClusterMBeanUpdateListener());
      this.domain = domain;
      this.allServers = new ArrayList();
      this.populateServerList();
      this.allSingletons = new HashMap();
      this.addAppscopedSingletons();
      this.addStaticallyDeployedSingletons();
   }

   public synchronized Object get(Object name) {
      return this.allSingletons.get(name);
   }

   public synchronized void remove(Object name) {
      this.allSingletons.remove(name);
   }

   public synchronized void put(Object name, Object value) {
      this.allSingletons.put(name, value);
   }

   public synchronized Collection values() {
      return ((HashMap)this.allSingletons.clone()).values();
   }

   public synchronized HashMap getAll() {
      HashMap map = (HashMap)this.allSingletons.clone();
      return map;
   }

   private synchronized void addStaticallyDeployedSingletons() {
      MigratableTargetMBean[] targets = this.domain.getMigratableTargets();

      for(int i = 0; i < targets.length; ++i) {
         if (!targets[i].getMigrationPolicy().equals("manual") && this.cluster.equals(targets[i].getCluster())) {
            if (DEBUG) {
               this.p("Now monitoring migratable target:" + targets[i].getName());
            }

            this.allSingletons.put(targets[i].getName(), new SingletonDataObject(targets[i].getName()));
         }
      }

      SingletonServiceMBean[] services = this.domain.getSingletonServices();

      for(int i = 0; i < services.length; ++i) {
         if (services[i].getCluster() != null && services[i].getCluster().getName().equals(this.cluster.getName()) && !this.allSingletons.containsKey(services[i].getName())) {
            if (DEBUG) {
               this.p("Now monitoring singleton service:" + services[i].getName());
            }

            this.allSingletons.put(services[i].getName(), new SingletonDataObject(services[i].getName()));
         }
      }

      ServerMBean[] servers = this.domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         JTAMigratableTargetMBean bean = servers[i].getJTAMigratableTarget();
         if (bean != null && (bean.getMigrationPolicy().equals("failure-recovery") || bean.getMigrationPolicy().equals("shutdown-recovery")) && this.cluster.equals(bean.getCluster())) {
            this.allSingletons.put(bean.getName(), new SingletonDataObject(bean.getName(), true));
         }
      }

   }

   private synchronized void addAppscopedSingletons() {
      AppDeploymentMBean[] apps = this.domain.getAppDeployments();

      for(int i = 0; i < apps.length; ++i) {
         if (DEBUG) {
            this.p("addAppscopedSingletons for " + apps[i]);
         }

         ApplicationContextInternal aci = ApplicationAccess.getApplicationAccess().getApplicationContext(apps[i].getApplicationIdentifier());
         if (aci != null) {
            WeblogicApplicationBean wad = null;

            try {
               ApplicationDescriptor ad = aci.getApplicationDescriptor();
               if (ad == null) {
                  continue;
               }

               wad = ad.getWeblogicApplicationDescriptor();
            } catch (IOException var10) {
               if (DEBUG) {
                  this.p("Error while obtaining Weblogic descriptor for " + apps[i], var10);
               }
               continue;
            } catch (XMLStreamException var11) {
               if (DEBUG) {
                  this.p("Error while obtaining Weblogic descriptor for " + apps[i], var11);
               }
               continue;
            }

            if (wad != null) {
               SingletonServiceBean[] services = wad.getSingletonServices();
               if (services != null) {
                  ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
                  String versionId = avus.getVersionId(apps[i].getApplicationIdentifier());

                  for(int j = 0; j < services.length; ++j) {
                     String sname = Util.getAppscopedSingletonServiceName(services[j].getName(), versionId, apps[i].getPartitionName());
                     if (!this.allSingletons.containsKey(sname)) {
                        this.allSingletons.put(sname, new SingletonDataObject(sname, apps[i].getApplicationIdentifier(), versionId, this.getApplicationTargets(apps[i])));
                        if (DEBUG) {
                           this.p("addAppscopedSingletons service: " + services[j] + " added.");
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private synchronized void populateServerList() {
      ServerMBean[] servers = this.domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         if (servers[i].getCluster() != null && servers[i].getCluster().getName().equals(this.cluster.getName())) {
            if (DEBUG) {
               this.p("Adding member of our cluster: " + servers[i].getName());
            }

            this.allServers.add(servers[i]);
         }
      }

   }

   public List getServerList(String singleton) {
      SingletonDataObject dataObj = (SingletonDataObject)this.allSingletons.get(singleton);
      return (List)(dataObj != null && dataObj.isAppScopedSingleton() ? dataObj.getApplicationScopedTargets() : this.allServers);
   }

   public synchronized void register(String name) {
      this.allSingletons.put(name, new SingletonDataObject(name));
   }

   public void registerJTA(String name) {
      this.allSingletons.put(name, new SingletonDataObject(name, true));
   }

   public void unregister(String name) {
      this.allSingletons.remove(name);
   }

   private List getApplicationTargets(AppDeploymentMBean mbean) {
      TargetMBean[] targets = mbean.getTargets();
      List servers = new ArrayList();
      if (targets != null && targets.length > 0) {
         for(int i = 0; i < targets.length; ++i) {
            if (DEBUG) {
               this.p("getApplicationTargets processing target[" + i + "] " + targets[i]);
            }

            if (targets[i] instanceof ClusterMBean && targets[i].getName().equals(this.cluster.getName())) {
               ServerMBean[] srvrMbeans = this.cluster.getServers();

               for(int j = 0; j < srvrMbeans.length; ++j) {
                  servers.add(srvrMbeans[j]);
               }
            } else if (targets[i] instanceof ServerMBean) {
               if (this.isPartOfCluster(targets[i])) {
                  servers.add((ServerMBean)targets[i]);
               }
            } else if (targets[i] instanceof VirtualHostMBean) {
               VirtualHostMBean vh = (VirtualHostMBean)targets[i];
               Set serverNames = vh.getServerNames();
               Iterator var20 = serverNames.iterator();

               while(var20.hasNext()) {
                  String name = (String)var20.next();
                  ServerMBean serverMBean = this.domain.lookupServer(name);
                  if (serverMBean != null && this.isPartOfCluster(serverMBean)) {
                     servers.add(serverMBean);
                  }
               }
            } else if (targets[i] instanceof VirtualTargetMBean) {
               VirtualTargetMBean vt = (VirtualTargetMBean)targets[i];
               TargetMBean[] targetMBeans = vt.getTargets();
               TargetMBean[] var7 = targetMBeans;
               int var8 = targetMBeans.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  TargetMBean t = var7[var9];
                  if (t instanceof ClusterMBean && t.getName().equals(this.cluster.getName())) {
                     ServerMBean[] srvrMbeans = this.cluster.getServers();

                     for(int j = 0; j < srvrMbeans.length; ++j) {
                        servers.add(srvrMbeans[j]);
                     }
                  } else if (t instanceof ServerMBean && this.isPartOfCluster(t)) {
                     servers.add((ServerMBean)t);
                  }
               }
            }
         }
      }

      if (DEBUG) {
         StringBuffer sb = new StringBuffer();
         Iterator var16 = servers.iterator();

         while(var16.hasNext()) {
            Object o = var16.next();
            sb.append(o);
            sb.append(" ");
         }

         this.p("getApplicationTargets added target servers: " + sb.toString());
      }

      return servers;
   }

   private boolean isPartOfCluster(TargetMBean target) {
      ServerMBean[] srvrMbeans = this.cluster.getServers();
      String targetName = target.getName();

      for(int i = 0; i < srvrMbeans.length; ++i) {
         if (targetName.equals(srvrMbeans[i].getName())) {
            return true;
         }
      }

      return false;
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
   }

   public synchronized void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

      for(int i = 0; i < updates.length; ++i) {
         Object changedObject;
         ConfigurationMBean bean;
         MigratableTargetMBean mtBean;
         SingletonServiceMBean singletonMBean;
         if (updates[i].getUpdateType() == 2) {
            changedObject = updates[i].getAddedObject();
            if (changedObject instanceof ServerMBean) {
               bean = (ConfigurationMBean)changedObject;
               if (DEBUG) {
                  this.p("Adding new server: " + bean);
               }

               this.allServers.add(bean);
            } else if (changedObject instanceof MigratableTargetMBean) {
               mtBean = (MigratableTargetMBean)changedObject;
               if (!mtBean.getMigrationPolicy().equals("manual") && this.cluster.equals(mtBean.getCluster())) {
                  if (DEBUG) {
                     this.p("Now monitoring migratable target:" + mtBean.getName());
                  }

                  this.allSingletons.put(mtBean.getName(), new SingletonDataObject(mtBean.getName()));
               }
            } else if (changedObject instanceof SingletonServiceMBean) {
               singletonMBean = (SingletonServiceMBean)changedObject;
               if (this.cluster.equals(singletonMBean.getCluster()) && !this.allSingletons.containsKey(singletonMBean.getName())) {
                  if (DEBUG) {
                     this.p("Now monitoring Singleton Service:" + singletonMBean.getName());
                  }

                  this.allSingletons.put(singletonMBean.getName(), new SingletonDataObject(singletonMBean.getName()));
               }
            }
         } else if (updates[i].getUpdateType() == 3) {
            changedObject = updates[i].getRemovedObject();
            if (changedObject instanceof ServerMBean) {
               bean = (ConfigurationMBean)changedObject;
               if (DEBUG) {
                  this.p("Removing server: " + bean);
               }

               this.allServers.remove(bean);
            } else if (changedObject instanceof MigratableTargetMBean) {
               mtBean = (MigratableTargetMBean)changedObject;
               if (this.allSingletons.containsKey(mtBean.getName())) {
                  if (DEBUG) {
                     this.p("Removing migratable target:" + mtBean.getName());
                  }

                  this.allSingletons.remove(mtBean.getName());
               }
            } else if (changedObject instanceof SingletonServiceMBean) {
               singletonMBean = (SingletonServiceMBean)changedObject;
               if (this.allSingletons.containsKey(singletonMBean.getName())) {
                  if (DEBUG) {
                     this.p("Removing Singleton Service:" + singletonMBean.getName());
                  }

                  this.allSingletons.remove(singletonMBean.getName());
               }
            }
         }
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   public void applicationRedeployed(DeploymentEvent evt) {
   }

   public synchronized void applicationDeleted(DeploymentEvent evt) {
      AppDeploymentMBean app = evt.getAppDeployment();
      if (app == null) {
         if (DEBUG) {
            this.p("No app deployment mbean for " + evt);
         }

      } else {
         Iterator itr = this.values().iterator();

         while(itr.hasNext()) {
            SingletonDataObject dataObj = (SingletonDataObject)itr.next();
            if (dataObj.isAppScopedSingleton() && dataObj.getAppName().equals(app.getApplicationIdentifier())) {
               this.allSingletons.remove(dataObj.getName());
            }
         }

      }
   }

   public synchronized void applicationActivated(DeploymentEvent evt) {
      AppDeploymentMBean app = evt.getAppDeployment();
      if (app == null) {
         if (DEBUG) {
            this.p("No app deployment mbean for " + evt);
         }

      } else {
         ApplicationContextInternal ctx = ApplicationAccess.getApplicationAccess().getApplicationContext(app.getApplicationIdentifier());
         if (ctx == null) {
            if (DEBUG) {
               this.p("no internal context for " + app.getApplicationName());
            }

         } else {
            WeblogicApplicationBean descriptor = ctx.getWLApplicationDD();
            if (descriptor == null) {
               if (DEBUG) {
                  this.p("No weblogic descriptor for " + app.getApplicationName());
               }

            } else {
               SingletonServiceBean[] ssbs = descriptor.getSingletonServices();
               if (ssbs != null && ssbs.length != 0) {
                  List targets = this.getApplicationTargets(app);
                  ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
                  String versionId = avus.getVersionId(app.getApplicationIdentifier());

                  for(int i = 0; i < ssbs.length; ++i) {
                     if (!this.allSingletons.containsKey(ssbs[i].getName())) {
                        String sname = Util.getAppscopedSingletonServiceName(ssbs[i].getName(), versionId, app.getPartitionName());
                        this.allSingletons.put(sname, new SingletonDataObject(sname, app.getApplicationIdentifier(), versionId, targets));
                     }
                  }

               }
            }
         }
      }
   }

   public synchronized void applicationDeployed(DeploymentEvent evt) {
      this.applicationActivated(evt);
   }

   private void p(Object o) {
      SingletonServicesDebugLogger.debug("SingletonMonitorServiceTracker: " + o);
   }

   private void p(Object o, Exception e) {
      SingletonServicesDebugLogger.debug("SingletonMonitorServiceTracker: " + o, e);
   }

   private class ClusterMBeanUpdateListener implements BeanUpdateListener {
      private ClusterMBeanUpdateListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

         for(int i = 0; i < updates.length; ++i) {
            if (updates[i].getUpdateType() == 2 || updates[i].getUpdateType() == 3) {
               Object changedObject = updates[i].getAddedObject();
               if (changedObject instanceof ServerMBean) {
                  Iterator itr = SingletonMonitorServiceTracker.this.allSingletons.values().iterator();

                  while(itr.hasNext()) {
                     SingletonDataObject singleton = (SingletonDataObject)itr.next();
                     if (singleton.isAppScopedSingleton()) {
                        AppDeploymentMBean app = SingletonMonitorServiceTracker.this.domain.lookupAppDeployment(singleton.getAppName());
                        List targets = SingletonMonitorServiceTracker.this.getApplicationTargets(app);
                        singleton.setTargets(targets);
                     }
                  }
               }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      // $FF: synthetic method
      ClusterMBeanUpdateListener(Object x1) {
         this();
      }
   }
}
