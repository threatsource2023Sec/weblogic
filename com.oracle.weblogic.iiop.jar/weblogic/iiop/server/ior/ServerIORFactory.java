package weblogic.iiop.server.ior;

import weblogic.iiop.ObjectKey;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOPProfile;
import weblogic.iiop.ior.IOR;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.internal.RuntimeDescriptor;

public class ServerIORFactory {
   private static String localHost;
   private static int localPort = -1;

   public static IOR createWellKnownIor(String typeId, int oid) {
      return createLocalIOR(typeId, ObjectKey.createTransientObjectKey(typeId, oid));
   }

   public static IOR createLocalIOR(String repositoryID, ObjectKey objectKey) {
      ServerIORBuilder builder = ServerIORBuilder.createBuilder(repositoryID, getLocalHost(), getLocalPort());
      builder.setKey(objectKey);
      return builder.createWithRuntimeDescriptor((RuntimeDescriptor)null);
   }

   private static String getLocalHost() {
      if (localHost == null) {
         Class var0 = ServerIORFactory.class;
         synchronized(ServerIORFactory.class) {
            localHost = ServerChannelManager.findLocalServerAddress(ProtocolHandlerIIOP.PROTOCOL_IIOP);
         }
      }

      return localHost;
   }

   private static int getLocalPort() {
      if (localPort < 0) {
         Class var0 = ServerIORFactory.class;
         synchronized(ServerIORFactory.class) {
            localPort = ServerChannelManager.findLocalServerPort(ProtocolHandlerIIOP.PROTOCOL_IIOP);
         }
      }

      return localPort;
   }

   public static IOR createIOR(String typeId, String host, int port, ObjectKey objectKey, byte majorVersion, byte minorVersion) {
      ServerIORBuilder builder = ServerIORBuilder.createBuilder(typeId, host, port);
      builder.setKey(objectKey);
      builder.setGiopVersion(majorVersion, minorVersion);
      return builder.createWithRuntimeDescriptor((RuntimeDescriptor)null);
   }

   public static IOR createSecureIOR(String typeId, String host, int port, ObjectKey objectKey, byte majorVersion, byte minorVersion) {
      ServerIORBuilder builder = ServerIORBuilder.createSecureBuilder(typeId, host, port);
      builder.setKey(objectKey);
      builder.setGiopVersion(majorVersion, minorVersion);
      return builder.createWithRuntimeDescriptor((RuntimeDescriptor)null);
   }

   public static IOR createIORWithClusterComponent(IOR ior, ClusterComponent cc) {
      return new IOR(ior.getTypeId().toString(), withClusterComponent(ior.getProfile(), cc));
   }

   private static IOPProfile withClusterComponent(IOPProfile iopProfile, ClusterComponent clusterComponent) {
      IOPProfile newProfile = new IOPProfile(iopProfile);
      newProfile.setClusterComponent(clusterComponent);
      return newProfile;
   }
}
