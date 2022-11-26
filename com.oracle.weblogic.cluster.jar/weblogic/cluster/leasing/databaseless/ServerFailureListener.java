package weblogic.cluster.leasing.databaseless;

public interface ServerFailureListener {
   void onServerFailure(ServerFailureEvent var1);

   void onMachineFailure(MachineFailureEvent var1);
}
