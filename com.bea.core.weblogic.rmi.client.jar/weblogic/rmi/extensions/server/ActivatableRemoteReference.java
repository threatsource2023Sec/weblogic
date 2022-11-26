package weblogic.rmi.extensions.server;

public interface ActivatableRemoteReference extends RemoteReference {
   Object getActivationID();
}
