package weblogic.iiop;

import java.io.IOException;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Object;
import weblogic.corba.utils.RemoteInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.ior.IOR;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.rmi.utils.io.RemoteReplacer;

public class InvocationHandlerFactory {
   static RemoteReplacer replacer = (RemoteReplacer)RemoteObjectReplacer.getReplacer();

   public static Object makeInvocationHandler(IOR ior) throws IOException {
      return makeInvocationHandler(ior, (Class)null);
   }

   static Object makeInvocationHandler(IOR ior, Class stubClass) throws IOException {
      Class interfaceClass = null;
      if (stubClass != null) {
         interfaceClass = Utils.getClassFromStub(stubClass);
      }

      RemoteInfo rinfo = null;
      RepositoryId typeid = ior.getTypeId();
      if (interfaceClass != null) {
         rinfo = RemoteInfo.findRemoteInfo(typeid, interfaceClass);
      } else {
         if (IiopConfigurationFacade.mayLoadRemoteClass(ior)) {
            rinfo = RemoteInfo.findRemoteInfo(typeid, ior.getCodebase());
         }

         if (rinfo == null) {
            rinfo = RemoteInfo.findRemoteInfo(typeid, Object.class);
         }
      }

      StubInfo stubInfo = getStubInfo(ior, rinfo);
      java.lang.Object stub = replacer.resolveStub(stubInfo);
      return stub instanceof Object && rinfo.getTheClass().isInstance(stub) ? (Object)stub : (Object)StubFactory.getStub(stubInfo);
   }

   static StubInfo getStubInfo(IOR ior, RemoteInfo remoteInfo) {
      if (remoteInfo == null) {
         throw new MARSHAL(ior.getTypeId().toString());
      } else {
         return StubInfoFactory.createFactory(ior, remoteInfo).createStubInfo();
      }
   }
}
