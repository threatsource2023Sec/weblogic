package weblogic.rmi.extensions.server;

import weblogic.rmi.internal.ClientRuntimeDescriptor;

public interface StubReference {
   String getStubName();

   RemoteReference getRemoteRef();

   ClientRuntimeDescriptor getDescriptor();

   String getStubBaseClassName();
}
