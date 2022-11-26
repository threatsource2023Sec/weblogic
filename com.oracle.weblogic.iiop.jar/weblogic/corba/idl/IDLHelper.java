package weblogic.corba.idl;

import java.rmi.RemoteException;
import weblogic.corba.utils.RemoteInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.rmi.cluster.ClusterableServerRef;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.utils.Debug;

public final class IDLHelper {
   public static ServerReference exportObject(org.omg.CORBA.portable.ObjectImpl obj) throws RemoteException {
      RemoteInfo rinfo = RemoteInfo.findRemoteInfo(createRepositoryId(obj._ids()[0]), obj.getClass());
      return createServerReference(obj, rinfo.getDescriptor());
   }

   private static ServerReference createServerReference(org.omg.CORBA.portable.ObjectImpl obj, RuntimeDescriptor rd) throws RemoteException {
      return rd != null ? rd.createServerReference(obj).exportObject() : (new CorbaServerRef(obj)).exportObject();
   }

   private static RepositoryId createRepositoryId(String repId) {
      return new RepositoryId(repId);
   }

   public static ServerReference exportObject(org.omg.CORBA.portable.ObjectImpl obj, String jndiName) throws RemoteException {
      ServerReference sref = exportObject(obj);
      addClusterInfo(sref, jndiName);
      return sref;
   }

   private static void addClusterInfo(ServerReference sref, String jndiName) {
      verifyIsClusterable(sref);
      ((ClusterableServerRef)sref).initialize(jndiName);
   }

   private static void verifyIsClusterable(ServerReference sref) {
      RuntimeDescriptor rtd = sref.getDescriptor();
      Debug.assertion(rtd.isClusterable(), "Cannot export non-clusterable object with jndiName");
   }
}
