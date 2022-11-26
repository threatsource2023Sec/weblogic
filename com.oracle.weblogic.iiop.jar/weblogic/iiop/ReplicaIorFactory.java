package weblogic.iiop;

import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.server.ior.ServerIORBuilder;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.RuntimeDescriptor;

public abstract class ReplicaIorFactory {
   static ReplicaIorFactory instance = new ReplicaIorFactoryImpl();

   static ReplicaIorFactory getFactory() {
      return instance;
   }

   public static IOR createIor(RemoteReference reference, String typeid, String appname, ClusterComponent cc, RuntimeDescriptor rd, Object aid) {
      return getFactory().doCreateIor(reference, typeid, appname, cc, rd, aid);
   }

   protected abstract IOR doCreateIor(RemoteReference var1, String var2, String var3, ClusterComponent var4, RuntimeDescriptor var5, Object var6);

   private static class ReplicaIorFactoryImpl extends ReplicaIorFactory {
      private ReplicaIorFactoryImpl() {
      }

      protected IOR doCreateIor(RemoteReference reference, String typeid, String appname, ClusterComponent cc, RuntimeDescriptor rd, Object aid) {
         IOR ior;
         if (aid == null && reference instanceof IORDelegate) {
            ior = ((IORDelegate)reference).getIOR();
         } else {
            ServerChannel channel = ServerChannelManager.findServerChannel(reference.getHostID(), ProtocolHandlerIIOP.PROTOCOL_IIOP);
            ServerIORBuilder builder = this.createIORBuilder(typeid, channel);
            builder.setKey(this.getObjectKey(reference, typeid, aid, reference.getObjectID()));
            builder.setApplicationName(appname);
            builder.addClusterComponent(cc);
            ior = builder.createWithRuntimeDescriptor(rd);
         }

         return ior;
      }

      private ServerIORBuilder createIORBuilder(String typeId, ServerChannel channel) {
         return channel == null ? ServerIORBuilder.createBuilder(typeId, (String)null, -1) : ServerIORBuilder.createBuilder(typeId, channel.getPublicAddress(), channel.getPublicPort());
      }

      private ObjectKey getObjectKey(RemoteReference reference, String typeid, Object aid, int oid) {
         ObjectKey oKey;
         if (aid != null) {
            oKey = ObjectKey.createActivatableObjectKey(typeid, oid, aid);
         } else if (reference instanceof ActivatableRemoteReference) {
            oKey = ObjectKey.createActivatableObjectKey(typeid, oid, ((ActivatableRemoteReference)reference).getActivationID());
         } else {
            oKey = ObjectKey.createTransientObjectKey(typeid, oid, (ServerIdentity)reference.getHostID());
         }

         return oKey;
      }

      // $FF: synthetic method
      ReplicaIorFactoryImpl(Object x0) {
         this();
      }
   }
}
