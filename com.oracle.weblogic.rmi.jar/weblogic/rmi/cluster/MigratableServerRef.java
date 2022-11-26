package weblogic.rmi.cluster;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigratableActivatingException;
import weblogic.cluster.migration.MigratableInactiveException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.server.GlobalServiceLocator;

public final class MigratableServerRef extends ClusterableServerRef {
   public MigratableServerRef(Object o) throws RemoteException {
      super(o);
   }

   public MigratableServerRef(int oid, Object o) throws RemoteException {
      super(oid, o);
   }

   public final void invoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws Exception {
      int migratableState = false;

      int migratableState;
      try {
         ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
         MigrationManager mm = (MigrationManager)sl.getService(MigrationManager.class, new Annotation[0]);
         migratableState = mm.getMigratableState((Migratable)this.getImplementation());
      } catch (ClassCastException var7) {
         throw new AssertionError("Implementation is not of type migratable", var7);
      }

      switch (migratableState) {
         case 0:
            throw new MigratableInactiveException("Service migrated");
         case 1:
            super.invoke(md, request, response);
            return;
         case 2:
            throw new MigratableActivatingException("Service in the process of migration");
         default:
            throw new AssertionError("Migratable service in unknown state " + migratableState);
      }
   }
}
