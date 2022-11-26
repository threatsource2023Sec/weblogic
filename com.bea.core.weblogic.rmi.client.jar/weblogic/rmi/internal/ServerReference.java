package weblogic.rmi.internal;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.spi.InboundRequest;

public interface ServerReference {
   Object getImplementation();

   ServerReference getDelegate();

   RuntimeDescriptor getDescriptor();

   StubReference getStubReference() throws RemoteException;

   RemoteReference getRemoteRef() throws RemoteException;

   RemoteReference getLocalRef();

   void dispatch(InboundRequest var1);

   void dispatchError(InboundRequest var1, Throwable var2);

   int getObjectID();

   ComponentInvocationContext getInvocationContext();

   void setInvocationContext(ComponentInvocationContext var1);

   boolean unexportObject(boolean var1) throws NoSuchObjectException;

   void addInterceptor(ServerReferenceInterceptor var1);

   ServerReference exportObject();

   boolean isExported();

   ClassLoader getApplicationClassLoader();

   String getApplicationName();
}
