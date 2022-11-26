package weblogic.rmi.extensions.activation;

public interface Activatable {
   Object getActivationID();

   Activator getActivator();
}
