package weblogic.rmi.extensions.server;

import weblogic.rmi.internal.ServerReference;

public interface CollocatedRemoteReference extends RemoteReference {
   ServerReference getServerReference();
}
