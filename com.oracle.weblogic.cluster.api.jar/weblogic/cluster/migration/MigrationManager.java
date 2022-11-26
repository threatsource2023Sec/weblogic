package weblogic.cluster.migration;

import java.rmi.RemoteException;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.rmi.spi.HostID;

@Contract
public interface MigrationManager {
   void register(Migratable var1, MigratableTargetMBean var2) throws MigrationException;

   void register(Migratable var1, MigratableTargetMBean var2, boolean var3, ReadyListener var4) throws MigrationException;

   void register(MigratableRemote var1, String var2, MigratableTargetMBean var3) throws MigrationException;

   void unregister(Migratable var1, MigratableTargetMBean var2) throws MigrationException;

   int getMigratableState(Migratable var1);

   int getMigratableTargetState(String var1);

   HostID[] getMigratableHostList(String var1);

   void register(String var1, MigratableGroupConfig var2, DynamicLoadbalancer var3, Migratable... var4) throws MigrationException, IllegalArgumentException;

   void register(ReadyListener var1, boolean var2, String var3, MigratableGroupConfig var4, DynamicLoadbalancer var5, Migratable... var6) throws MigrationException, IllegalArgumentException;

   void register(MigratableGroupConfig var1, DynamicLoadbalancer var2, Migratable... var3) throws MigrationException, IllegalArgumentException;

   void register(String var1, MigratableGroupConfig var2, Migratable... var3) throws MigrationException, IllegalArgumentException;

   void register(MigratableGroupConfig var1, Migratable... var2) throws IllegalArgumentException, MigrationException;

   void unregister(String var1, MigratableGroupConfig var2, Migratable... var3) throws IllegalArgumentException, MigrationException;

   void unregister(MigratableGroupConfig var1, Migratable... var2) throws IllegalArgumentException, MigrationException;

   DynamicLoadbalancer.ServiceStatus getServiceStatus(String var1, String var2) throws RemoteException, MigrationException, IllegalArgumentException, IllegalStateException;

   boolean manualMigrate(String var1, String var2, String var3, String var4, String var5, boolean var6, boolean var7) throws RemoteException, MigrationException, IllegalStateException, IllegalArgumentException;

   void manualMigrate(String var1, String var2, String var3, String var4) throws RemoteException, MigrationException, IllegalStateException, IllegalArgumentException;

   List getMigratableTargetsMarkedNotReadyToActivate();

   void markMigratableTargetReadyToActivate(String var1);
}
