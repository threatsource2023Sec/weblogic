package weblogic.store.io.file;

import java.io.IOException;
import java.util.HashMap;
import weblogic.store.io.file.direct.ReplicatedIONativeImpl;

public final class ReplicatedStoreAdminImpl {
   private static ReplicatedStoreAdminImpl singleton = new ReplicatedStoreAdminImpl();

   private ReplicatedStoreAdminImpl() {
      ReplicatedIONativeImpl.loadReplicatedLib();
   }

   public static ReplicatedStoreAdminImpl getReplicatedStoreAdminImplSingleton() throws IOException {
      ReplicatedIONativeImpl.getDirectIOManagerSingletonIOException();
      return singleton;
   }

   public int attach(String dirName, String cfgFileName, int localindex) throws IOException {
      return ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().attach(cfgFileName, localindex);
   }

   public void detach() throws IOException {
      ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().detach();
   }

   public boolean isAttached() throws IOException {
      return ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().isAttached();
   }

   public int attachToDaemon(int dindex) throws IOException {
      return ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().attachToDaemon(dindex);
   }

   public int getAttachedDaemonIndex() throws IOException {
      return ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().getAttachedDaemonIndex();
   }

   public HashMap listDaemons() throws IOException {
      ReplicatedStoreAdmin replicatedStoreAdminSingleton = ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton();
      return replicatedStoreAdminSingleton.populateDaemons();
   }

   public int shutdownDaemon(int index, boolean force, boolean safe) throws IOException {
      return ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().shutdownDaemon(index, force, safe);
   }

   public HashMap listRegion(String name) throws IOException {
      ReplicatedStoreAdmin replicatedStoreAdminSingleton = ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton();
      return replicatedStoreAdminSingleton.populateRegion(name);
   }

   public HashMap listLocalRegions() throws IOException {
      ReplicatedStoreAdmin replicatedStoreAdminSingleton = ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton();
      return replicatedStoreAdminSingleton.populateLocalRegions();
   }

   public HashMap listGlobalRegions() throws IOException {
      ReplicatedStoreAdmin replicatedStoreAdminSingleton = ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton();
      return replicatedStoreAdminSingleton.populateGlobalRegions();
   }

   public int deleteRegion(String name, boolean force) throws IOException {
      return ReplicatedStoreAdmin.getReplicatedStoreAdminSingleton().deleteRegion(name, force);
   }
}
