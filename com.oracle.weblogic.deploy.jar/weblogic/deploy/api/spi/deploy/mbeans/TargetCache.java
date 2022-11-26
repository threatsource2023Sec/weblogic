package weblogic.deploy.api.spi.deploy.mbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.shared.WebLogicTargetType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.deploy.TargetImpl;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;

public class TargetCache extends MBeanCache {
   private transient String loadedAllTargets = null;
   private transient List targets = null;

   public TargetCache(DomainMBean domain, WebLogicDeploymentManager dm) {
      super(dm);
      this.currDomain = domain;
      this.listenType = new String[]{"Servers", "Clusters", "VirtualHosts", "VirtualTargets"};
      this.addNotificationListener();
   }

   public synchronized ConfigurationMBean[] getTypedMBeans() {
      return this.getTypedMBeans((DeploymentOptions)null);
   }

   public synchronized ConfigurationMBean[] getTypedMBeans(DeploymentOptions options) {
      ConfigurationMBean[] beans = null;
      if (options == null) {
         beans = this.getTargetsFromDomain();
      } else {
         String partition = options.getPartition();
         if (partition != null && !options.getSpecifiedTargetsOnly()) {
            PartitionMBean pm = this.currDomain.lookupPartition(partition);
            if (pm != null) {
               beans = pm.getAvailableTargets();
            }
         } else {
            beans = this.getTargetsFromDomain();
         }
      }

      if (beans == null) {
         beans = new ConfigurationMBean[0];
      }

      return (ConfigurationMBean[])beans;
   }

   private ConfigurationMBean[] getTargetsFromDomain() {
      ArrayList targets = new ArrayList(Arrays.asList(this.currDomain.getTargets()));
      DomainMBean runtimeDomain = this.dm.getHelper().getRuntimeDomain();
      ServerMBean[] var3 = runtimeDomain.getServers();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerMBean sm = var3[var5];
         if (ApplicationUtils.isDynamicClusterServer(sm.getName(), runtimeDomain)) {
            targets.add(sm);
         }
      }

      return (ConfigurationMBean[])((ConfigurationMBean[])targets.toArray(new ConfigurationMBean[targets.size()]));
   }

   public synchronized List getTargets(DeploymentOptions options) throws ServerConnectionException {
      return this.getTargets(options, true);
   }

   public synchronized TargetImpl getTarget(DeploymentOptions options, String name) throws ServerConnectionException {
      Iterator var3 = this.getTargets(options, false).iterator();

      while(var3.hasNext()) {
         TargetImpl t = (TargetImpl)var3.next();
         if (t.getName().equals(name)) {
            return t;
         }
      }

      TargetImpl target = this.createAndCacheRGOrRGTTarget(name);
      if (target != null) {
         return target;
      } else {
         TargetMBean mbean = null;
         if (options != null && options.getPartition() != null) {
            PartitionMBean pm = this.currDomain.lookupPartition(options.getPartition());
            if (pm != null) {
               mbean = pm.lookupAvailableTarget(name);
            }
         } else {
            mbean = this.currDomain.lookupTarget(name);
         }

         if (mbean == null) {
            return null;
         } else {
            target = new TargetImpl(name, this.getTypeForTarget(mbean), this.getDM());
            this.targets.add(target);
            return target;
         }
      }
   }

   public TargetImpl createAndCacheRGOrRGTTarget(String name) {
      TargetImpl target = null;
      if (name.equals("resourceGroup")) {
         target = new TargetImpl(name, WebLogicTargetType.VIRTUALTARGET, this.getDM());
      } else if (name.equals("resourceGroupTemplate")) {
         target = new TargetImpl(name, WebLogicTargetType.VIRTUALTARGET, this.getDM());
      }

      if (target != null) {
         if (this.targets == null) {
            this.targets = new ArrayList();
         }

         this.targets.add(target);
      }

      return target;
   }

   public List getTargets(DeploymentOptions options, WebLogicTargetType ttype) throws ServerConnectionException {
      List targs = new ArrayList();
      Iterator var4 = this.getTargets(options).iterator();

      while(var4.hasNext()) {
         TargetImpl t = (TargetImpl)var4.next();
         if (t.getValue() == ttype.getValue()) {
            targs.add(t);
         }
      }

      return targs;
   }

   public synchronized void reset() {
      super.reset();
      this.loadedAllTargets = null;
      this.targets = null;
   }

   private WebLogicTargetType getTypeForTarget(TargetMBean bean) {
      if (bean instanceof ServerMBean) {
         return WebLogicTargetType.SERVER;
      } else if (bean instanceof ClusterMBean) {
         return WebLogicTargetType.CLUSTER;
      } else if (bean instanceof VirtualHostMBean) {
         return WebLogicTargetType.VIRTUALHOST;
      } else if (bean instanceof VirtualTargetMBean) {
         return WebLogicTargetType.VIRTUALTARGET;
      } else if (bean instanceof JMSServerMBean) {
         return WebLogicTargetType.JMSSERVER;
      } else {
         return bean instanceof SAFAgentMBean ? WebLogicTargetType.SAFAGENT : null;
      }
   }

   private synchronized List getTargets(DeploymentOptions options, boolean reloadCacheAsNecessary) throws ServerConnectionException {
      if (this.isStale()) {
         this.reset();
      }

      String partitionName = this.getPartitionNameFromDeploymentOptions(options);
      if (!this.loadedAllTargets(partitionName) && reloadCacheAsNecessary) {
         List cbeans = this.getMBeans(options);
         this.targets = new ArrayList();
         Iterator var5 = cbeans.iterator();

         while(var5.hasNext()) {
            ConfigurationMBean cbean = (ConfigurationMBean)var5.next();
            TargetMBean mbean = (TargetMBean)cbean;
            WebLogicTargetType ttype = this.getTypeForTarget(mbean);
            if (ttype != null) {
               this.targets.add(new TargetImpl(mbean.getObjectName().getName(), ttype, this.getDM()));
            }
         }

         this.loadedAllTargets = partitionName;
      }

      if (this.targets == null) {
         this.targets = new ArrayList();
      }

      return this.targets;
   }

   public void dumpTargets() {
      this.dumpTargets((DeploymentOptions)null);
   }

   public void dumpTargets(DeploymentOptions options) {
      Iterator var2 = this.getTargets(options).iterator();

      while(var2.hasNext()) {
         TargetImpl t = (TargetImpl)var2.next();
         Debug.say(t.toString());
      }

   }

   private String getPartitionNameFromDeploymentOptions(DeploymentOptions options) {
      return options != null && options.getPartition() != null ? options.getPartition() : "DOMAIN";
   }

   private boolean loadedAllTargets(String partitionName) {
      return this.loadedAllTargets != null ? this.loadedAllTargets.equals(partitionName) : false;
   }
}
