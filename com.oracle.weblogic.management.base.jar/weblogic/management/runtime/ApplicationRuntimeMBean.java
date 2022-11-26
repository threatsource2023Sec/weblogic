package weblogic.management.runtime;

import com.bea.wls.redef.runtime.ClassRedefinitionRuntimeMBean;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.work.WorkManager;

@Contract
public interface ApplicationRuntimeMBean extends RuntimeMBean, HealthFeedback {
   int UNPREPARED = 0;
   int PREPARED = 1;
   int ACTIVATED = 2;

   String getApplicationName();

   String getApplicationVersion();

   int getActiveVersionState();

   void setActiveVersionState(int var1);

   /** @deprecated */
   @Deprecated
   ComponentRuntimeMBean[] lookupComponents();

   ComponentRuntimeMBean[] getComponentRuntimes();

   LibraryRuntimeMBean[] getLibraryRuntimes();

   LibraryRuntimeMBean[] getOptionalPackageRuntimes();

   WorkManagerRuntimeMBean[] getWorkManagerRuntimes();

   WorkManagerRuntimeMBean lookupWorkManagerRuntime(String var1, String var2);

   WorkManagerRuntimeMBean lookupWorkManagerRuntime(WorkManager var1);

   ClassLoaderRuntimeMBean getClassLoaderRuntime();

   /** @deprecated */
   @Deprecated
   WseeRuntimeMBean[] getWseeRuntimes();

   /** @deprecated */
   @Deprecated
   void addWseeRuntime(WseeRuntimeMBean var1);

   WseeV2RuntimeMBean[] getWseeV2Runtimes();

   WseeV2RuntimeMBean lookupWseeV2Runtime(String var1);

   MinThreadsConstraintRuntimeMBean lookupMinThreadsConstraintRuntime(String var1);

   RequestClassRuntimeMBean lookupRequestClassRuntime(String var1);

   MaxThreadsConstraintRuntimeMBean lookupMaxThreadsConstraintRuntime(String var1);

   MaxThreadsConstraintRuntimeMBean[] getMaxThreadsConstraintRuntimes();

   MinThreadsConstraintRuntimeMBean[] getMinThreadsConstraintRuntimes();

   RequestClassRuntimeMBean[] getRequestClassRuntimes();

   boolean isEAR();

   boolean hasApplicationCache();

   void reInitializeApplicationCachesAndPools();

   QueryCacheRuntimeMBean[] getQueryCacheRuntimes();

   QueryCacheRuntimeMBean lookupQueryCacheRuntime(String var1);

   /** @deprecated */
   @Deprecated
   KodoPersistenceUnitRuntimeMBean[] getKodoPersistenceUnitRuntimes();

   /** @deprecated */
   @Deprecated
   KodoPersistenceUnitRuntimeMBean getKodoPersistenceUnitRuntime(String var1);

   PersistenceUnitRuntimeMBean[] getPersistenceUnitRuntimes();

   PersistenceUnitRuntimeMBean getPersistenceUnitRuntime(String var1);

   ClassRedefinitionRuntimeMBean getClassRedefinitionRuntime();

   HealthState getHealthState();

   HealthState getOverallHealthState();

   CompositeData getHealthStateJMX() throws OpenDataException;

   CompositeData getOverallHealthStateJMX() throws OpenDataException;

   CoherenceClusterRuntimeMBean getCoherenceClusterRuntime();

   void setCoherenceClusterRuntime(CoherenceClusterRuntimeMBean var1);

   boolean isInternal();

   String getPartitionName();

   ManagedThreadFactoryRuntimeMBean[] getManagedThreadFactoryRuntimes();

   ManagedThreadFactoryRuntimeMBean lookupManagedThreadFactoryRuntime(String var1, String var2);

   boolean addManagedThreadFactoryRuntime(ManagedThreadFactoryRuntimeMBean var1);

   ManagedExecutorServiceRuntimeMBean[] getManagedExecutorServiceRuntimes();

   ManagedExecutorServiceRuntimeMBean lookupManagedExecutorServiceRuntime(String var1, String var2);

   boolean addManagedExecutorServiceRuntime(ManagedExecutorServiceRuntimeMBean var1);

   ManagedScheduledExecutorServiceRuntimeMBean[] getManagedScheduledExecutorServiceRuntimes();

   ManagedScheduledExecutorServiceRuntimeMBean lookupManagedScheduledExecutorServiceRuntime(String var1, String var2);

   boolean addManagedScheduledExecutorServiceRuntime(ManagedScheduledExecutorServiceRuntimeMBean var1);
}
