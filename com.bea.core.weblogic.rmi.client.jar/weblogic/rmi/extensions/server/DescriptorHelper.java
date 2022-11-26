package weblogic.rmi.extensions.server;

import java.io.InputStream;
import java.rmi.RemoteException;
import weblogic.rmi.internal.DescriptorManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.utils.collections.ArrayMap;

public class DescriptorHelper {
   public static RuntimeDescriptor getDescriptor(Class remoteClass) throws RemoteException {
      return DescriptorManager.getDescriptor(remoteClass);
   }

   public static RuntimeDescriptor getDescriptorForRMIC(Class remoteClass) throws RemoteException {
      return DescriptorManager.getDescriptorForRMIC(remoteClass);
   }

   public static ArrayMap getDescriptorAsMap(InputStream in) throws Exception {
      return (new RMIDDParser(in)).getDescriptorAsMap();
   }
}
