package weblogic.application.internal;

import com.bea.wls.redef.runtime.ClassRedefinitionRuntimeMBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationWork;
import weblogic.application.Module;
import weblogic.application.PersistenceUnitParent;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.HealthStateBuilder;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ClassLoaderRuntimeMBean;
import weblogic.management.runtime.CoherenceClusterRuntimeMBean;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.management.runtime.ManagedExecutorServiceRuntimeMBean;
import weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean;
import weblogic.management.runtime.ManagedThreadFactoryRuntimeMBean;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.management.runtime.QueryCacheRuntimeMBean;
import weblogic.management.runtime.RequestClassRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.management.runtime.WseeRuntimeMBean;
import weblogic.management.runtime.WseeV2RuntimeMBean;
import weblogic.work.RequestClassRuntimeMBeanImpl;
import weblogic.work.WorkManager;

public final class ApplicationRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ApplicationRuntimeMBean, PersistenceUnitParent, ApplicationWork {
   private static final long serialVersionUID = 5618173476456700945L;
   private final String appName;
   private final String appId;
   private final String appVersion;
   private final boolean isInternal;
   private final String partitionName;
   private int activeVersionState = 0;
   private Set workManagerRuntimes = new HashSet();
   private Set minThreadsConstraintRuntimes = new HashSet();
   private Set maxThreadsConstraintRuntimes = new HashSet();
   private Set requestClassRuntimes = new HashSet();
   private ClassLoaderRuntimeMBean classLoaderRuntime;
   private Set managedThreadFactoryRuntimes = new HashSet();
   private Set managedExecutorRuntimes = new HashSet();
   private Set managedScheduledExecutorRuntimes = new HashSet();
   private ClassRedefinitionRuntimeMBean classRedefRuntime;
   private LibraryRuntimeMBean[] libraryRuntimes = null;
   private LibraryRuntimeMBean[] optionalPackages = null;
   private Set wseeRuntimes = new HashSet();
   private Map queryCacheRuntimes = new HashMap();
   private Map m_runtimePersistenUnit = new HashMap();
   private ReInitializableCachePool pool;
   private CoherenceClusterRuntimeMBean coherenceClusterRuntimeMBean;
   private final HashSet wseeV2Runtimes = new HashSet();

   public ApplicationRuntimeMBeanImpl(String name, SystemResourceMBean srmb) throws ManagementException {
      super(ApplicationVersionUtils.getNonPartitionName(name), ManagementUtils.getPartitionRuntime(ApplicationVersionUtils.getPartitionName(srmb.getName())), true, (DescriptorBean)null);
      this.appName = ApplicationVersionUtils.getNonPartitionName(srmb.getName());
      this.appId = srmb.getName();
      this.appVersion = null;
      String pName = ApplicationVersionUtils.getPartitionName(this.appId);
      if ("DOMAIN".equals(pName)) {
         this.partitionName = null;
      } else {
         this.partitionName = pName;
      }

      this.isInternal = false;
   }

   public ApplicationRuntimeMBeanImpl(String name, AppDeploymentMBean deployable) throws ManagementException {
      super(ApplicationVersionUtils.getNonPartitionName(name), ManagementUtils.getPartitionRuntime(ApplicationVersionUtils.getPartitionName(deployable.getApplicationIdentifier())), true, (DescriptorBean)null);
      this.appName = deployable.getApplicationName();
      this.appId = deployable.getApplicationIdentifier();
      this.appVersion = deployable.getVersionIdentifier();
      this.partitionName = deployable.getPartitionName();
      this.isInternal = deployable.isInternalApp();
      HealthMonitorService.register(this.appId + "(Application)", this, false);
   }

   public String getApplicationName() {
      return this.appName;
   }

   public String getApplicationVersion() {
      return this.appVersion;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public int getActiveVersionState() {
      return this.activeVersionState;
   }

   public void setActiveVersionState(int state) {
      int old = this.activeVersionState;
      this.activeVersionState = state;
      this._postSet("ActiveVersionState", old, this.activeVersionState);
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      int len = this.workManagerRuntimes.size();
      return (WorkManagerRuntimeMBean[])((WorkManagerRuntimeMBean[])this.workManagerRuntimes.toArray(new WorkManagerRuntimeMBean[len]));
   }

   public WorkManagerRuntimeMBean lookupWorkManagerRuntime(String componentName, String wmName) {
      WorkManagerRuntimeMBean[] workManagers;
      if (componentName != null) {
         ComponentRuntimeMBean crmb = this.lookupComponentRuntime(componentName);
         if (crmb == null) {
            return null;
         }

         workManagers = crmb.getWorkManagerRuntimes();
      } else {
         workManagers = this.getWorkManagerRuntimes();
      }

      for(int i = 0; i < workManagers.length; ++i) {
         if (workManagers[i].getName().equals(wmName)) {
            return workManagers[i];
         }
      }

      return null;
   }

   public WorkManagerRuntimeMBean lookupWorkManagerRuntime(WorkManager wm) {
      return wm == null ? null : this.lookupWorkManagerRuntime(wm.getModuleName(), wm.getName());
   }

   private ComponentRuntimeMBean lookupComponentRuntime(String componentName) {
      ComponentRuntimeMBean[] crmbs = this.getComponentRuntimes();

      for(int i = 0; i < crmbs.length; ++i) {
         if (crmbs[i].getName().equals(componentName)) {
            return crmbs[i];
         }
      }

      return null;
   }

   public boolean addWorkManager(WorkManagerRuntimeMBean q) {
      return this.workManagerRuntimes.add(q);
   }

   public QueryCacheRuntimeMBean[] getQueryCacheRuntimes() {
      Collection runtimes = this.queryCacheRuntimes.values();
      return (QueryCacheRuntimeMBean[])((QueryCacheRuntimeMBean[])runtimes.toArray(new QueryCacheRuntimeMBean[0]));
   }

   public void setClassLoaderRuntime(ClassLoaderRuntimeMBean r) {
      this.classLoaderRuntime = r;
   }

   public ClassLoaderRuntimeMBean getClassLoaderRuntime() {
      return this.classLoaderRuntime;
   }

   public QueryCacheRuntimeMBean lookupQueryCacheRuntime(String cacheName) {
      return (QueryCacheRuntimeMBean)this.queryCacheRuntimes.get(cacheName);
   }

   public void addQueryCacheRuntimeMBean(String name, QueryCacheRuntimeMBean bean) {
      this.queryCacheRuntimes.put(name, bean);
   }

   public void setLibraryRuntimes(LibraryRuntimeMBean[] libraryRuntimes) {
      this.libraryRuntimes = libraryRuntimes;
   }

   public void setOptionalPackageRuntimes(LibraryRuntimeMBean[] optPackages) {
      this.optionalPackages = optPackages;
   }

   public LibraryRuntimeMBean[] getOptionalPackageRuntimes() {
      return this.optionalPackages;
   }

   public LibraryRuntimeMBean[] getLibraryRuntimes() {
      return this.libraryRuntimes;
   }

   public void addWseeRuntime(WseeRuntimeMBean wseeRuntime) {
      this.wseeRuntimes.add(wseeRuntime);
   }

   public WseeRuntimeMBean[] getWseeRuntimes() {
      int len = this.wseeRuntimes.size();
      return (WseeRuntimeMBean[])((WseeRuntimeMBean[])this.wseeRuntimes.toArray(new WseeRuntimeMBean[len]));
   }

   public WseeV2RuntimeMBean[] getWseeV2Runtimes() {
      synchronized(this.wseeV2Runtimes) {
         int len = this.wseeV2Runtimes.size();
         return (WseeV2RuntimeMBean[])this.wseeV2Runtimes.toArray(new WseeV2RuntimeMBean[len]);
      }
   }

   public WseeV2RuntimeMBean lookupWseeV2Runtime(String name) {
      WseeV2RuntimeMBean mbean = null;
      synchronized(this.wseeV2Runtimes) {
         Iterator var4 = this.wseeV2Runtimes.iterator();

         while(var4.hasNext()) {
            WseeV2RuntimeMBean temp = (WseeV2RuntimeMBean)var4.next();
            if (temp.getName().equals(name)) {
               mbean = temp;
               break;
            }
         }

         return mbean;
      }
   }

   public void addWseeV2Runtime(WseeV2RuntimeMBean mbean) {
      synchronized(this.wseeV2Runtimes) {
         this.wseeV2Runtimes.add(mbean);
      }
   }

   public void removeWseeV2Runtime(WseeV2RuntimeMBean mbean) {
      synchronized(this.wseeV2Runtimes) {
         this.wseeV2Runtimes.remove(mbean);
      }
   }

   public MinThreadsConstraintRuntimeMBean lookupMinThreadsConstraintRuntime(String name) {
      MinThreadsConstraintRuntimeMBean[] min = this.getMinThreadsConstraintRuntimes();

      for(int i = 0; i < min.length; ++i) {
         if (min[i].getName().equals(name)) {
            return min[i];
         }
      }

      return null;
   }

   public RequestClassRuntimeMBean lookupRequestClassRuntime(String name) {
      RequestClassRuntimeMBean[] rc = this.getRequestClassRuntimes();

      for(int i = 0; i < rc.length; ++i) {
         if (rc[i].getName().equals(name)) {
            return rc[i];
         }
      }

      return null;
   }

   public MaxThreadsConstraintRuntimeMBean lookupMaxThreadsConstraintRuntime(String name) {
      MaxThreadsConstraintRuntimeMBean[] max = this.getMaxThreadsConstraintRuntimes();

      for(int i = 0; i < max.length; ++i) {
         if (max[i].getName().equals(name)) {
            return max[i];
         }
      }

      return null;
   }

   public boolean addMaxThreadsConstraint(MaxThreadsConstraintRuntimeMBean temp) {
      return this.maxThreadsConstraintRuntimes.add(temp);
   }

   public MaxThreadsConstraintRuntimeMBean[] getMaxThreadsConstraintRuntimes() {
      int len = this.maxThreadsConstraintRuntimes.size();
      return (MaxThreadsConstraintRuntimeMBean[])((MaxThreadsConstraintRuntimeMBean[])this.maxThreadsConstraintRuntimes.toArray(new MaxThreadsConstraintRuntimeMBean[len]));
   }

   public boolean addMinThreadsConstraint(MinThreadsConstraintRuntimeMBean temp) {
      return this.minThreadsConstraintRuntimes.add(temp);
   }

   public boolean addRequestClass(RequestClassRuntimeMBeanImpl rc) {
      return this.requestClassRuntimes.add(rc);
   }

   public RequestClassRuntimeMBean[] getRequestClassRuntimes() {
      int len = this.requestClassRuntimes.size();
      return (RequestClassRuntimeMBean[])((RequestClassRuntimeMBean[])this.requestClassRuntimes.toArray(new RequestClassRuntimeMBean[len]));
   }

   public MinThreadsConstraintRuntimeMBean[] getMinThreadsConstraintRuntimes() {
      int len = this.minThreadsConstraintRuntimes.size();
      return (MinThreadsConstraintRuntimeMBean[])((MinThreadsConstraintRuntimeMBean[])this.minThreadsConstraintRuntimes.toArray(new MinThreadsConstraintRuntimeMBean[len]));
   }

   public synchronized void removeChild(RuntimeMBeanDelegate child) {
      if (child instanceof WorkManagerRuntimeMBean) {
         this.workManagerRuntimes.remove(child);
      } else if (child instanceof RequestClassRuntimeMBean) {
         this.requestClassRuntimes.remove(child);
      } else if (child instanceof MinThreadsConstraintRuntimeMBean) {
         this.minThreadsConstraintRuntimes.remove(child);
      } else if (child instanceof MaxThreadsConstraintRuntimeMBean) {
         this.maxThreadsConstraintRuntimes.remove(child);
      } else if (child instanceof WseeRuntimeMBean) {
         this.wseeRuntimes.remove(child);
      } else if (child instanceof KodoPersistenceUnitRuntimeMBean) {
         this.m_runtimePersistenUnit.remove(((KodoPersistenceUnitRuntimeMBean)child).getPersistenceUnitName());
      } else if (child instanceof ManagedThreadFactoryRuntimeMBean) {
         this.managedThreadFactoryRuntimes.remove(child);
      } else if (child instanceof ManagedExecutorServiceRuntimeMBean) {
         this.managedExecutorRuntimes.remove(child);
      } else if (child instanceof ManagedScheduledExecutorServiceRuntimeMBean) {
         this.managedScheduledExecutorRuntimes.remove(child);
      }

      super.removeChild(child);
   }

   public void unregister() throws ManagementException {
      super.unregister();
      HealthMonitorService.unregister(this.appId + "(Application)");
      Iterator workManagers = this.workManagerRuntimes.iterator();

      while(workManagers.hasNext()) {
         RuntimeMBeanDelegate runtime = (RuntimeMBeanDelegate)workManagers.next();
         runtime.unregister();
      }

      Iterator maxs = this.maxThreadsConstraintRuntimes.iterator();

      while(maxs.hasNext()) {
         RuntimeMBeanDelegate runtime = (RuntimeMBeanDelegate)maxs.next();
         runtime.unregister();
      }

      Iterator mins = this.minThreadsConstraintRuntimes.iterator();

      while(mins.hasNext()) {
         RuntimeMBeanDelegate runtime = (RuntimeMBeanDelegate)mins.next();
         runtime.unregister();
      }

      Iterator rcs = this.requestClassRuntimes.iterator();

      while(rcs.hasNext()) {
         RuntimeMBeanDelegate runtime = (RuntimeMBeanDelegate)rcs.next();
         runtime.unregister();
      }

      Iterator wsee = this.wseeRuntimes.iterator();

      while(wsee.hasNext()) {
         RuntimeMBeanDelegate runtime = (RuntimeMBeanDelegate)wsee.next();
         runtime.unregister();
      }

      Iterator qcr = this.queryCacheRuntimes.keySet().iterator();

      while(qcr.hasNext()) {
         RuntimeMBeanDelegate runtime = (RuntimeMBeanDelegate)((RuntimeMBeanDelegate)this.queryCacheRuntimes.get((String)((String)qcr.next())));
         runtime.unregister();
      }

      Iterator var15 = this.managedThreadFactoryRuntimes.iterator();

      RuntimeMBeanDelegate runtime;
      while(var15.hasNext()) {
         ManagedThreadFactoryRuntimeMBean mbean = (ManagedThreadFactoryRuntimeMBean)var15.next();
         runtime = (RuntimeMBeanDelegate)mbean;
         runtime.unregister();
      }

      var15 = this.managedExecutorRuntimes.iterator();

      while(var15.hasNext()) {
         ManagedExecutorServiceRuntimeMBean mbean = (ManagedExecutorServiceRuntimeMBean)var15.next();
         runtime = (RuntimeMBeanDelegate)mbean;
         runtime.unregister();
      }

      var15 = this.managedScheduledExecutorRuntimes.iterator();

      while(var15.hasNext()) {
         ManagedScheduledExecutorServiceRuntimeMBean mbean = (ManagedScheduledExecutorServiceRuntimeMBean)var15.next();
         runtime = (RuntimeMBeanDelegate)mbean;
         runtime.unregister();
      }

   }

   public ComponentRuntimeMBean[] lookupComponents() {
      return this.getComponentRuntimes();
   }

   private void addCRM(Collection l, ComponentRuntimeMBean[] c) {
      if (c != null && c.length != 0) {
         for(int i = 0; i < c.length; ++i) {
            if (c[i] != null) {
               l.add(c[i]);
            }
         }

      }
   }

   private void addModules(Collection compSet, Module[] modules) {
      if (modules != null && modules.length > 0) {
         Module[] var3 = modules;
         int var4 = modules.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Module mod = var3[var5];
            this.addCRM(compSet, mod.getComponentRuntimeMBeans());
         }
      }

   }

   public ComponentRuntimeMBean[] getComponentRuntimes() {
      ApplicationContextInternal appCtx = this.getAppCtx();
      LinkedHashSet compSet = new LinkedHashSet();
      this.addModules(compSet, appCtx.getApplicationModules());
      this.addModules(compSet, ((FlowContext)appCtx).getStartingModules());
      this.addModules(compSet, ((FlowContext)appCtx).getStoppingModules());
      return compSet.isEmpty() ? new ComponentRuntimeMBean[0] : (ComponentRuntimeMBean[])compSet.toArray(new ComponentRuntimeMBean[compSet.size()]);
   }

   private ApplicationContextInternal getAppCtx() {
      ApplicationAccess access = ApplicationAccess.getApplicationAccess();
      return access.getApplicationContext(this.appId);
   }

   public boolean isEAR() {
      return this.getAppCtx().getApplicationDD() != null;
   }

   public void setReInitializableCachePool(ReInitializableCachePool pool) {
      this.pool = pool;
   }

   public boolean hasApplicationCache() {
      return this.pool != null && this.pool.hasCache();
   }

   public void reInitializeApplicationCachesAndPools() {
      if (this.pool != null) {
         this.pool.reInitialize();
      }

   }

   public HealthState getHealthState() {
      HealthStateBuilder healthStateBuilder = new HealthStateBuilder();
      Iterator var2 = this.workManagerRuntimes.iterator();

      while(var2.hasNext()) {
         Object wm = var2.next();
         WorkManagerRuntimeMBean runtime = (WorkManagerRuntimeMBean)wm;
         healthStateBuilder.append(runtime.getHealthState());
      }

      return healthStateBuilder.get();
   }

   public HealthState getOverallHealthState() {
      HealthStateBuilder healthStateBuilder = new HealthStateBuilder(this.getHealthState());
      ComponentRuntimeMBean[] var2 = this.getComponentRuntimes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ComponentRuntimeMBean componentMBean = var2[var4];
         if (componentMBean instanceof HealthFeedback) {
            healthStateBuilder.append(((HealthFeedback)componentMBean).getHealthState());
         }
      }

      return healthStateBuilder.get();
   }

   public CompositeData getHealthStateJMX() throws OpenDataException {
      return this.getHealthState().toCompositeData();
   }

   public CompositeData getOverallHealthStateJMX() throws OpenDataException {
      return this.getOverallHealthState().toCompositeData();
   }

   public KodoPersistenceUnitRuntimeMBean[] getKodoPersistenceUnitRuntimes() {
      Collection kodoMBeans = new ArrayList();
      Iterator var2 = this.m_runtimePersistenUnit.values().iterator();

      while(var2.hasNext()) {
         Object each = var2.next();
         if (each instanceof KodoPersistenceUnitRuntimeMBean) {
            kodoMBeans.add(each);
         }
      }

      KodoPersistenceUnitRuntimeMBean[] result = new KodoPersistenceUnitRuntimeMBean[kodoMBeans.size()];
      return (KodoPersistenceUnitRuntimeMBean[])((KodoPersistenceUnitRuntimeMBean[])kodoMBeans.toArray(result));
   }

   public KodoPersistenceUnitRuntimeMBean getKodoPersistenceUnitRuntime(String name) {
      Object result = this.m_runtimePersistenUnit.get(name);
      return result != null && result instanceof KodoPersistenceUnitRuntimeMBean ? (KodoPersistenceUnitRuntimeMBean)result : null;
   }

   public void addKodoPersistenceUnit(KodoPersistenceUnitRuntimeMBean childMBean) {
      this.m_runtimePersistenUnit.put(childMBean.getPersistenceUnitName(), childMBean);
   }

   public PersistenceUnitRuntimeMBean[] getPersistenceUnitRuntimes() {
      PersistenceUnitRuntimeMBean[] result = new PersistenceUnitRuntimeMBean[this.m_runtimePersistenUnit.size()];
      result = (PersistenceUnitRuntimeMBean[])((PersistenceUnitRuntimeMBean[])this.m_runtimePersistenUnit.values().toArray(result));
      return result;
   }

   public PersistenceUnitRuntimeMBean getPersistenceUnitRuntime(String unitName) {
      return (PersistenceUnitRuntimeMBean)this.m_runtimePersistenUnit.get(this.name);
   }

   public void addPersistenceUnit(PersistenceUnitRuntimeMBean childMBean) {
      this.m_runtimePersistenUnit.put(childMBean.getPersistenceUnitName(), childMBean);
   }

   public ClassRedefinitionRuntimeMBean getClassRedefinitionRuntime() {
      return this.classRedefRuntime;
   }

   public void setClassRedefinitionRuntime(ClassRedefinitionRuntimeMBean rmb) {
      this.classRedefRuntime = rmb;
   }

   public CoherenceClusterRuntimeMBean getCoherenceClusterRuntime() {
      return this.coherenceClusterRuntimeMBean;
   }

   public void setCoherenceClusterRuntime(CoherenceClusterRuntimeMBean mbean) {
      this.coherenceClusterRuntimeMBean = mbean;
   }

   public boolean isInternal() {
      return this.isInternal;
   }

   public boolean addManagedThreadFactoryRuntime(ManagedThreadFactoryRuntimeMBean mtfRuntime) {
      return this.managedThreadFactoryRuntimes.add(mtfRuntime);
   }

   public ManagedThreadFactoryRuntimeMBean[] getManagedThreadFactoryRuntimes() {
      int len = this.managedThreadFactoryRuntimes.size();
      return (ManagedThreadFactoryRuntimeMBean[])((ManagedThreadFactoryRuntimeMBean[])this.managedThreadFactoryRuntimes.toArray(new ManagedThreadFactoryRuntimeMBean[len]));
   }

   public ManagedThreadFactoryRuntimeMBean lookupManagedThreadFactoryRuntime(String componentName, String name) {
      ManagedThreadFactoryRuntimeMBean[] mtfs;
      if (componentName != null) {
         ComponentRuntimeMBean crmb = this.lookupComponentRuntime(componentName);
         if (crmb == null || !(crmb instanceof ComponentConcurrentRuntimeMBean)) {
            return null;
         }

         mtfs = ((ComponentConcurrentRuntimeMBean)crmb).getManagedThreadFactoryRuntimes();
      } else {
         mtfs = this.getManagedThreadFactoryRuntimes();
      }

      for(int i = 0; i < mtfs.length; ++i) {
         if (mtfs[i].getName().equals(name)) {
            return mtfs[i];
         }
      }

      return null;
   }

   public ManagedExecutorServiceRuntimeMBean[] getManagedExecutorServiceRuntimes() {
      int len = this.managedExecutorRuntimes.size();
      return (ManagedExecutorServiceRuntimeMBean[])((ManagedExecutorServiceRuntimeMBean[])this.managedExecutorRuntimes.toArray(new ManagedExecutorServiceRuntimeMBean[len]));
   }

   public ManagedExecutorServiceRuntimeMBean lookupManagedExecutorServiceRuntime(String componentName, String name) {
      ManagedExecutorServiceRuntimeMBean[] mes;
      if (componentName != null) {
         ComponentRuntimeMBean crmb = this.lookupComponentRuntime(componentName);
         if (crmb == null || !(crmb instanceof ComponentConcurrentRuntimeMBean)) {
            return null;
         }

         mes = ((ComponentConcurrentRuntimeMBean)crmb).getManagedExecutorServiceRuntimes();
      } else {
         mes = this.getManagedExecutorServiceRuntimes();
      }

      for(int i = 0; i < mes.length; ++i) {
         if (mes[i].getName().equals(name)) {
            return mes[i];
         }
      }

      return null;
   }

   public boolean addManagedExecutorServiceRuntime(ManagedExecutorServiceRuntimeMBean bean) {
      return this.managedExecutorRuntimes.add(bean);
   }

   public ManagedScheduledExecutorServiceRuntimeMBean[] getManagedScheduledExecutorServiceRuntimes() {
      int len = this.managedScheduledExecutorRuntimes.size();
      return (ManagedScheduledExecutorServiceRuntimeMBean[])((ManagedScheduledExecutorServiceRuntimeMBean[])this.managedScheduledExecutorRuntimes.toArray(new ManagedScheduledExecutorServiceRuntimeMBean[len]));
   }

   public ManagedScheduledExecutorServiceRuntimeMBean lookupManagedScheduledExecutorServiceRuntime(String componentName, String name) {
      ManagedScheduledExecutorServiceRuntimeMBean[] mses;
      if (componentName != null) {
         ComponentRuntimeMBean crmb = this.lookupComponentRuntime(componentName);
         if (crmb == null || !(crmb instanceof ComponentConcurrentRuntimeMBean)) {
            return null;
         }

         mses = ((ComponentConcurrentRuntimeMBean)crmb).getManagedScheduledExecutorServiceRuntimes();
      } else {
         mses = this.getManagedScheduledExecutorServiceRuntimes();
      }

      for(int i = 0; i < mses.length; ++i) {
         if (mses[i].getName().equals(name)) {
            return mses[i];
         }
      }

      return null;
   }

   public boolean addManagedScheduledExecutorServiceRuntime(ManagedScheduledExecutorServiceRuntimeMBean mbean) {
      return this.managedScheduledExecutorRuntimes.add(mbean);
   }

   public interface ReInitializableCachePool {
      boolean hasCache();

      void reInitialize();
   }
}
